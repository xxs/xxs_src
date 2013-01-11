/*    */ package com.enation.eop.sdk.listener;
/*    */ 
/*    */ import com.enation.eop.resource.IAppManager;
/*    */ import com.enation.eop.resource.model.EopApp;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.IApp;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class EopSessionListener
/*    */   implements HttpSessionListener
/*    */ {
/* 19 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public void sessionCreated(HttpSessionEvent se)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionDestroyed(HttpSessionEvent se)
/*    */   {
/* 27 */     if (this.logger.isDebugEnabled())
/* 28 */       this.logger.debug("session destroyed..");
/*    */     EopSite site;
/*    */     String sessionid;
/* 32 */     if ("YES".equals(EopSetting.INSTALL_LOCK.toUpperCase()))
/*    */     {
/* 34 */       if (this.logger.isDebugEnabled()) {
/* 35 */         this.logger.debug("installed...");
/*    */       }
/*    */ 
/* 38 */       site = (EopSite)se.getSession().getAttribute("site_key");
/* 39 */       sessionid = se.getSession().getId();
/* 40 */       IAppManager appManager = (IAppManager)SpringContextHolder.getBean("appManager");
/* 41 */       List appList = appManager.list();
/* 42 */       for (EopApp eopApp : appList)
/*    */       {
/* 44 */         String appid = eopApp.getAppid();
/*    */ 
/* 46 */         if (this.logger.isDebugEnabled()) {
/* 47 */           this.logger.debug("call app[" + appid + "] destory...");
/*    */         }
/*    */ 
/* 51 */         IApp app = (IApp)SpringContextHolder.getBean(appid);
/* 52 */         app.sessionDestroyed(sessionid, site);
/*    */       }
/*    */     }
/* 55 */     else if (this.logger.isDebugEnabled()) {
/* 56 */       this.logger.debug("not installed...");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.listener.EopSessionListener
 * JD-Core Version:    0.6.0
 */