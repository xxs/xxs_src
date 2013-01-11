/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.service.IAccessRecorder;
/*    */ import com.enation.eop.resource.ISiteManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class BaseIndexItemAction extends WWAction
/*    */ {
/*    */   private ISiteManager siteManager;
/*    */   private IAccessRecorder accessRecorder;
/*    */   private Map access;
/*    */   private EopSite site;
/*    */   private int canget;
/*    */ 
/*    */   public String base()
/*    */   {
/* 22 */     this.site = EopContext.getContext().getCurrentSite();
/* 23 */     return "base";
/*    */   }
/*    */ 
/*    */   public String access() {
/* 27 */     this.access = this.accessRecorder.census();
/* 28 */     return "access";
/*    */   }
/*    */ 
/*    */   public String point()
/*    */   {
/* 33 */     this.site = EopContext.getContext().getCurrentSite();
/* 34 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 35 */     long lastgetpoint = site.getLastgetpoint();
/* 36 */     long now = System.currentTimeMillis() / 1000L;
/* 37 */     int onemonth = 2592000;
/* 38 */     if (now - lastgetpoint > onemonth)
/* 39 */       this.canget = 1;
/*    */     else {
/* 41 */       this.canget = 0;
/*    */     }
/* 43 */     return "point";
/*    */   }
/*    */ 
/*    */   public ISiteManager getSiteManager()
/*    */   {
/* 48 */     return this.siteManager;
/*    */   }
/*    */   public void setSiteManager(ISiteManager siteManager) {
/* 51 */     this.siteManager = siteManager;
/*    */   }
/*    */   public EopSite getSite() {
/* 54 */     return this.site;
/*    */   }
/*    */   public void setSite(EopSite site) {
/* 57 */     this.site = site;
/*    */   }
/*    */ 
/*    */   public IAccessRecorder getAccessRecorder() {
/* 61 */     return this.accessRecorder;
/*    */   }
/*    */ 
/*    */   public void setAccessRecorder(IAccessRecorder accessRecorder) {
/* 65 */     this.accessRecorder = accessRecorder;
/*    */   }
/*    */ 
/*    */   public Map getAccess() {
/* 69 */     return this.access;
/*    */   }
/*    */ 
/*    */   public void setAccess(Map access) {
/* 73 */     this.access = access;
/*    */   }
/*    */ 
/*    */   public int getCanget() {
/* 77 */     return this.canget;
/*    */   }
/*    */ 
/*    */   public void setCanget(int canget) {
/* 81 */     this.canget = canget;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.BaseIndexItemAction
 * JD-Core Version:    0.6.0
 */