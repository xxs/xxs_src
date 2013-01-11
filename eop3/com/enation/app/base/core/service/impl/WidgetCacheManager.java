/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.ISettingService;
/*    */ import com.enation.app.base.core.service.IWidgetCacheManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.cache.CacheFactory;
/*    */ import com.enation.framework.cache.ICache;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WidgetCacheManager
/*    */   implements IWidgetCacheManager
/*    */ {
/*    */   private ISettingService settingService;
/*    */ 
/*    */   public void close()
/*    */   {
/* 29 */     if ("2".equals(EopSetting.RUNMODE))
/* 30 */       cleanSaasCache();
/*    */     else {
/* 32 */       cleanSlCache();
/*    */     }
/*    */ 
/* 35 */     Map settings = new HashMap();
/* 36 */     Map value = new HashMap();
/* 37 */     value.put("state", "close");
/* 38 */     settings.put("widgetCache", value);
/* 39 */     this.settingService.save(settings);
/*    */   }
/*    */ 
/*    */   private void cleanSaasCache()
/*    */   {
/* 48 */     ICache widgetCache = CacheFactory.getCache("widgetCache");
/*    */ 
/* 51 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 52 */     String site_key = "widget_" + site.getUserid() + "_" + site.getId();
/*    */ 
/* 55 */     widgetCache.remove(site_key);
/*    */   }
/*    */ 
/*    */   private void cleanSlCache()
/*    */   {
/* 64 */     ICache widgetCache = CacheFactory.getCache("widgetCache");
/* 65 */     widgetCache.clear();
/*    */   }
/*    */ 
/*    */   public void open()
/*    */   {
/* 74 */     Map settings = new HashMap();
/* 75 */     Map value = new HashMap();
/* 76 */     value.put("state", "open");
/* 77 */     settings.put("widgetCache", value);
/* 78 */     this.settingService.save(settings);
/*    */   }
/*    */ 
/*    */   public boolean isOpen()
/*    */   {
/* 86 */     Map settings = new HashMap();
/* 87 */     String state = this.settingService.getSetting("widgetCache", "state");
/* 88 */     return "open".equals(state);
/*    */   }
/*    */ 
/*    */   public ISettingService getSettingService()
/*    */   {
/* 93 */     return this.settingService;
/*    */   }
/*    */ 
/*    */   public void setSettingService(ISettingService settingService)
/*    */   {
/* 98 */     this.settingService = settingService;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.WidgetCacheManager
 * JD-Core Version:    0.6.0
 */