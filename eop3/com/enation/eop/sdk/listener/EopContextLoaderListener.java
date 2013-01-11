/*    */ package com.enation.eop.sdk.listener;
/*    */ 
/*    */ import com.enation.eop.resource.ISiteManager;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.component.IComponentManager;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ 
/*    */ public class EopContextLoaderListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   public void contextDestroyed(ServletContextEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent event)
/*    */   {
/* 30 */     if ((EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) && ("1".equals(EopSetting.RUNMODE))) {
/* 31 */       IComponentManager componentManager = (IComponentManager)SpringContextHolder.getBean("componentManager");
/* 32 */       componentManager.startComponents();
/*    */     }
/*    */ 
/* 36 */     if (("2".equals(EopSetting.RUNMODE)) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES"))) {
/* 37 */       ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
/* 38 */       siteManager.getDnsList();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.listener.EopContextLoaderListener
 * JD-Core Version:    0.6.0
 */