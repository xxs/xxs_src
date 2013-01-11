/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.eop.processor.IPageParamJsonGetter;
/*    */ import com.enation.eop.processor.IPagePaser;
/*    */ import com.enation.eop.processor.IPageUpdater;
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.eop.processor.facade.support.HeaderResourcePageWrapper;
/*    */ import com.enation.eop.processor.facade.support.PageEditModeWrapper;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class FacadePageProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 42 */     String method = RequestUtil.getRequestMethod(httpRequest);
/* 43 */     String uri = RequestUtil.getRequestUrl(httpRequest);
/* 44 */     Response response = new StringResponse();
/*    */ 
/* 47 */     if (method.equals("GET"))
/*    */     {
/* 50 */       IPagePaser paser = (IPagePaser)SpringContextHolder.getBean("facadePagePaser");
/*    */ 
/* 53 */       if ((UserServiceFactory.getUserService().isUserLoggedIn()) && (httpRequest.getParameter("mode") != null))
/*    */       {
/* 55 */         paser = new PageEditModeWrapper(paser);
/*    */       }
/*    */ 
/* 58 */       paser = new HeaderResourcePageWrapper(paser);
/*    */ 
/* 60 */       String content = paser.pase(uri);
/* 61 */       response.setContent(content);
/*    */     }
/*    */ 
/* 66 */     if (method.equals("PUT")) {
/* 67 */       String params = httpRequest.getParameter("widgetParams");
/* 68 */       String content = httpRequest.getParameter("bodyHtml");
/* 69 */       IPageUpdater pageUpdater = (IPageUpdater)SpringContextHolder.getBean("facadePageUpdater");
/*    */ 
/* 71 */       pageUpdater.update(uri, content, params);
/* 72 */       response.setContent("{'state':0,'message':'页面保存成功'}");
/*    */     }
/*    */ 
/* 76 */     if (method.equals("PARAMJSON")) {
/* 77 */       IPageParamJsonGetter pageParamJsonGetter = (IPageParamJsonGetter)SpringContextHolder.getBean("pageParamJsonGetter");
/*    */ 
/* 79 */       String json = pageParamJsonGetter.getJson(uri);
/* 80 */       response.setContent(json);
/*    */     }
/*    */ 
/* 83 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.FacadePageProcessor
 * JD-Core Version:    0.6.0
 */