/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.base.core.service.SettingRuntimeException;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SaasSettingCacheProxy
/*     */   implements ISettingService
/*     */ {
/*     */   private ISettingService settingService;
/*     */   private ICache<Map<String, Map<String, String>>> cache;
/*     */ 
/*     */   public SaasSettingCacheProxy(ISettingService settingService)
/*     */   {
/*  24 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public void add(String groupname, String name, String value)
/*     */   {
/*  30 */     this.settingService.add(groupname, name, value);
/*  31 */     String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
/*  32 */     Map settings = (Map)this.cache.get(uKey);
/*     */ 
/*  34 */     settings = this.settingService.getSetting();
/*  35 */     this.cache.put(uKey, settings);
/*     */   }
/*     */ 
/*     */   public void save(String groupname, String name, String value)
/*     */   {
/*  42 */     this.settingService.save(groupname, name, value);
/*  43 */     String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
/*  44 */     Map settings = (Map)this.cache.get(uKey);
/*     */ 
/*  46 */     settings = this.settingService.getSetting();
/*  47 */     this.cache.put(uKey, settings);
/*     */   }
/*     */ 
/*     */   public void delete(String groupname)
/*     */   {
/*  54 */     this.settingService.delete(groupname);
/*  55 */     String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
/*  56 */     Map settings = (Map)this.cache.get(uKey);
/*     */ 
/*  58 */     if ((settings != null) || (settings.size() <= 0))
/*  59 */       settings.remove(groupname);
/*     */   }
/*     */ 
/*     */   public Map<String, Map<String, String>> getSetting()
/*     */   {
/*  66 */     String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
/*  67 */     Map settings = (Map)this.cache.get(uKey);
/*     */ 
/*  69 */     if ((settings == null) || (settings.size() <= 0)) {
/*  70 */       settings = this.settingService.getSetting();
/*  71 */       this.cache.put(uKey, settings);
/*     */     }
/*  73 */     return settings;
/*     */   }
/*     */ 
/*     */   public void save(Map<String, Map<String, String>> settings)
/*     */     throws SettingRuntimeException
/*     */   {
/*  80 */     String uKey = getCurrentUserid() + "_" + getCurrentSiteid();
/*  81 */     this.settingService.save(settings);
/*  82 */     this.cache.put(uKey, this.settingService.getSetting());
/*     */   }
/*     */ 
/*     */   protected Integer getCurrentUserid()
/*     */   {
/*  91 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  93 */     Integer userid = site.getUserid();
/*  94 */     return userid;
/*     */   }
/*     */ 
/*     */   protected Integer getCurrentSiteid()
/*     */   {
/* 103 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 104 */     return site.getId();
/*     */   }
/*     */ 
/*     */   public String getSetting(String group, String code)
/*     */   {
/* 109 */     Map settings = getSetting();
/* 110 */     if (settings == null) return null;
/*     */ 
/* 112 */     Map setting = (Map)settings.get(group);
/* 113 */     if (setting == null) return null;
/*     */ 
/* 115 */     return (String)setting.get(code);
/*     */   }
/*     */ 
/*     */   public void setCache(ICache<Map<String, Map<String, String>>> cache) {
/* 119 */     this.cache = cache;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.SaasSettingCacheProxy
 * JD-Core Version:    0.6.0
 */