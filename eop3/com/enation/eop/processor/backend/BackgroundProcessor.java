/*    */ package com.enation.eop.processor.backend;
/*    */ 
/*    */ import com.enation.eop.processor.FacadePage;
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.backend.support.BackPageGetter;
/*    */ import com.enation.eop.processor.backend.support.MenuJsonGetter;
/*    */ import com.enation.eop.processor.core.LocalRequest;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class BackgroundProcessor
/*    */   implements Processor
/*    */ {
/* 32 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public Response process(int model, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 39 */     String uri = httpRequest.getServletPath();
/*    */ 
/* 43 */     if ((!uri.startsWith("/admin/login")) && (!uri.startsWith("/admin/index.jsp")) && (!uri.equals("/admin/")) && (!uri.equals("/admin")))
/*    */     {
/* 49 */       IUserService userService = UserServiceFactory.getUserService();
/* 50 */       if (!userService.isUserLoggedIn()) {
/* 51 */         List msgs = new ArrayList();
/* 52 */         Map urls = new HashMap();
/* 53 */         msgs.add("您尚未登陆或登陆已经超时，请重新登录。");
/* 54 */         String ctx = httpRequest.getContextPath();
/* 55 */         urls.put("点进这里进入登录页面", ctx + "/admin/");
/* 56 */         httpRequest.setAttribute("msgs", msgs);
/* 57 */         httpRequest.setAttribute("urls", urls);
/* 58 */         httpRequest.setAttribute("target", "_top");
/*    */ 
/* 62 */         Request request = new LocalRequest();
/* 63 */         Response response = request.execute("/admin/message.jsp", httpResponse, httpRequest);
/*    */ 
/* 65 */         return response;
/*    */       }
/*    */     }
/*    */ 
/* 69 */     Processor processor = null;
/*    */ 
/* 71 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 73 */     model = 0;
/* 74 */     FacadePage page = new FacadePage(site);
/*    */ 
/* 76 */     page.setUri(uri);
/*    */ 
/* 78 */     if (uri.startsWith("/admin/menu.do"))
/* 79 */       processor = new MenuJsonGetter(page);
/* 80 */     else if (uri.startsWith("/admin/login.do"))
/* 81 */       processor = new LoginProcessor();
/* 82 */     else if (uri.startsWith("/admin/logout.do"))
/* 83 */       processor = new LogoutProcessor();
/* 84 */     else if (uri.startsWith("/admin/plugin"))
/* 85 */       processor = new AjaxPluginProcessor();
/*    */     else {
/* 87 */       processor = new BackPageGetter(page);
/*    */     }
/*    */ 
/* 90 */     Response response = processor.process(model, httpResponse, httpRequest);
/* 91 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.BackgroundProcessor
 * JD-Core Version:    0.6.0
 */