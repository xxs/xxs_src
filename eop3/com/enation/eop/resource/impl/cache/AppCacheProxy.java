/*    */ package com.enation.eop.resource.impl.cache;
/*    */ 
/*    */ import com.enation.eop.processor.core.EopException;
/*    */ import com.enation.eop.resource.IAppManager;
/*    */ import com.enation.eop.resource.model.EopApp;
/*    */ import com.enation.framework.cache.AbstractCacheProxy;
/*    */ import com.enation.framework.cache.ICache;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AppCacheProxy extends AbstractCacheProxy<List<EopApp>>
/*    */   implements IAppManager
/*    */ {
/*    */   private IAppManager appManager;
/*    */   private static final String APP_LIST_CACHE_KEY = "applist";
/*    */ 
/*    */   public AppCacheProxy(IAppManager appManager)
/*    */   {
/* 26 */     super("appCache");
/* 27 */     this.appManager = appManager;
/*    */   }
/*    */ 
/*    */   public void add(EopApp app)
/*    */   {
/* 33 */     this.cache.clear();
/* 34 */     this.appManager.add(app);
/*    */   }
/*    */ 
/*    */   public EopApp get(String appid)
/*    */   {
/* 40 */     if (this.logger.isDebugEnabled()) {
/* 41 */       this.logger.debug("get app : " + appid);
/*    */     }
/* 43 */     List appList = list();
/*    */ 
/* 45 */     for (EopApp app : appList) {
/* 46 */       if (app.getAppid().equals(appid)) {
/* 47 */         return app;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 52 */     throw new EopException("App not found");
/*    */   }
/*    */ 
/*    */   public List<EopApp> list()
/*    */   {
/* 58 */     List appList = (List)this.cache.get("applist");
/*    */ 
/* 60 */     if (appList == null) {
/* 61 */       if (this.logger.isDebugEnabled()) {
/* 62 */         this.logger.debug("get applist from database");
/*    */       }
/* 64 */       appList = this.appManager.list();
/* 65 */       this.cache.put("applist", appList);
/*    */     }
/*    */ 
/* 71 */     return appList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.cache.AppCacheProxy
 * JD-Core Version:    0.6.0
 */