/*    */ package com.enation.eop.sdk.context;
/*    */ 
/*    */ import com.enation.app.base.core.model.MultiSite;
/*    */ import com.enation.app.base.core.service.IMultiSiteManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.eop.resource.ISiteManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class EopContextIniter
/*    */ {
/*    */   public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
/*    */   {
/* 24 */     FreeMarkerPaser.set(new FreeMarkerPaser());
/* 25 */     FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();
/*    */ 
/* 29 */     HttpSession session = httpRequest.getSession();
/* 30 */     ThreadContextHolder.getSessionContext().setSession(session);
/* 31 */     EopContext.setHttpRequest(httpRequest);
/* 32 */     ThreadContextHolder.setHttpRequest(httpRequest);
/* 33 */     ThreadContextHolder.setHttpResponse(httpResponse);
/* 34 */     httpRequest.setAttribute("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 35 */     httpRequest.setAttribute("ext", EopSetting.EXTENSION);
/* 36 */     String servletPath = httpRequest.getServletPath();
/*    */ 
/* 39 */     if (servletPath.startsWith("/statics")) {
/* 40 */       return;
/*    */     }
/* 42 */     if (servletPath.startsWith("/install")) {
/* 43 */       EopSite site = new EopSite();
/* 44 */       site.setUserid(Integer.valueOf(1));
/* 45 */       site.setId(Integer.valueOf(1));
/* 46 */       site.setThemeid(Integer.valueOf(1));
/* 47 */       EopContext context = new EopContext();
/* 48 */       context.setCurrentSite(site);
/* 49 */       EopContext.setContext(context);
/*    */     } else {
/* 51 */       EopContext context = new EopContext();
/* 52 */       EopSite site = new EopSite();
/* 53 */       site.setUserid(Integer.valueOf(1));
/* 54 */       site.setId(Integer.valueOf(1));
/* 55 */       site.setThemeid(Integer.valueOf(1));
/* 56 */       context.setCurrentSite(site);
/* 57 */       EopContext.setContext(context);
/*    */ 
/* 59 */       ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
/* 60 */       site = siteManager.get("localhost");
/*    */ 
/* 62 */       context.setCurrentSite(site);
/* 63 */       if (site.getMulti_site().intValue() == 1) {
/* 64 */         String domain = httpRequest.getServerName();
/* 65 */         IMultiSiteManager multiSiteManager = (IMultiSiteManager)SpringContextHolder.getBean("multiSiteManager");
/* 66 */         MultiSite multiSite = multiSiteManager.get(domain);
/* 67 */         context.setCurrentChildSite(multiSite);
/*    */       }
/*    */ 
/* 70 */       EopContext.setContext(context);
/* 71 */       fmp.putData("site", site);
/*    */     }
/*    */ 
/* 77 */     fmp.putData("ctx", httpRequest.getContextPath());
/* 78 */     fmp.putData("ext", EopSetting.EXTENSION);
/* 79 */     fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.context.EopContextIniter
 * JD-Core Version:    0.6.0
 */