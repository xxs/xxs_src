/*    */ package com.enation.eop.sdk.context;
/*    */ 
/*    */ import com.enation.app.base.core.model.MultiSite;
/*    */ import com.enation.app.base.core.service.IMultiSiteManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.eop.resource.ISiteManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.framework.component.IComponentManager;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class SaasEopContextIniter
/*    */ {
/*    */   public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
/*    */   {
/* 20 */     FreeMarkerPaser.set(new FreeMarkerPaser());
/* 21 */     FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();
/*    */ 
/* 25 */     HttpSession session = httpRequest.getSession();
/* 26 */     ThreadContextHolder.getSessionContext().setSession(session);
/* 27 */     EopContext.setHttpRequest(httpRequest);
/* 28 */     ThreadContextHolder.setHttpRequest(httpRequest);
/* 29 */     ThreadContextHolder.setHttpResponse(httpResponse);
/*    */ 
/* 31 */     httpRequest.setAttribute("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 32 */     httpRequest.setAttribute("ext", EopSetting.EXTENSION);
/* 33 */     String servletPath = httpRequest.getServletPath();
/*    */ 
/* 36 */     if (servletPath.startsWith("/statics")) return;
/*    */ 
/* 38 */     EopSite site = null;
/* 39 */     if ((servletPath.startsWith("/install")) && (!servletPath.startsWith("/install.html"))) {
/* 40 */       site = new EopSite();
/* 41 */       site.setUserid(Integer.valueOf(1));
/* 42 */       site.setId(Integer.valueOf(1));
/* 43 */       site.setThemeid(Integer.valueOf(1));
/* 44 */       EopContext context = new EopContext();
/* 45 */       context.setCurrentSite(site);
/* 46 */       EopContext.setContext(context);
/*    */     }
/*    */     else
/*    */     {
/* 52 */       String domain = httpRequest.getServerName();
/* 53 */       ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
/* 54 */       site = siteManager.get(domain);
/* 55 */       EopContext context = new EopContext();
/* 56 */       context.setCurrentSite(site);
/* 57 */       EopContext.setContext(context);
/* 58 */       if (site.getMulti_site().intValue() == 1) {
/* 59 */         IMultiSiteManager multiSiteManager = (IMultiSiteManager)SpringContextHolder.getBean("multiSiteManager");
/* 60 */         MultiSite multiSite = multiSiteManager.get(domain);
/* 61 */         context.setCurrentChildSite(multiSite);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 70 */     fmp.putData("ctx", httpRequest.getContextPath());
/* 71 */     fmp.putData("ext", EopSetting.EXTENSION);
/* 72 */     fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 73 */     fmp.putData("site", site);
/*    */ 
/* 75 */     if (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
/* 76 */       IComponentManager componentManager = (IComponentManager)SpringContextHolder.getBean("componentManager");
/* 77 */       componentManager.saasStartComponents();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.context.SaasEopContextIniter
 * JD-Core Version:    0.6.0
 */