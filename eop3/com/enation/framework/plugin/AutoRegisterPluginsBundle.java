/*     */ package com.enation.framework.plugin;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public abstract class AutoRegisterPluginsBundle
/*     */   implements IPluginBundle
/*     */ {
/*  22 */   protected static final Log loger = LogFactory.getLog(AutoRegisterPluginsBundle.class);
/*     */   private List<IPlugin> plugins;
/*     */   private Map<String, List<IPlugin>> saasPlugins;
/*     */ 
/*     */   public synchronized void registerPlugin(IPlugin plugin)
/*     */   {
/*  33 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  34 */       registerPlugin2(plugin);
/*     */     }
/*     */ 
/*  37 */     if ("1".equals(EopSetting.RUNMODE))
/*  38 */       registerPlugin1(plugin);
/*     */   }
/*     */ 
/*     */   private void registerPlugin1(IPlugin plugin)
/*     */   {
/*  47 */     if (this.plugins == null) {
/*  48 */       this.plugins = new ArrayList();
/*     */     }
/*     */ 
/*  51 */     if (!this.plugins.contains(plugin)) {
/*  52 */       this.plugins.add(plugin);
/*     */     }
/*     */ 
/*  55 */     if (loger.isDebugEnabled())
/*  56 */       loger.debug("为插件桩" + getName() + "注册插件：" + plugin.getClass());
/*     */   }
/*     */ 
/*     */   private void registerPlugin2(IPlugin plugin)
/*     */   {
/*  65 */     if (this.saasPlugins == null) {
/*  66 */       this.saasPlugins = new HashMap();
/*     */     }
/*     */ 
/*  69 */     String key = getKey();
/*     */ 
/*  71 */     List pluginList = (List)this.saasPlugins.get(key);
/*     */ 
/*  73 */     if (pluginList == null) {
/*  74 */       pluginList = new ArrayList();
/*  75 */       this.saasPlugins.put(key, pluginList);
/*     */     }
/*  77 */     if (!pluginList.contains(plugin))
/*  78 */       pluginList.add(plugin);
/*     */   }
/*     */ 
/*     */   public synchronized void unRegisterPlugin(IPlugin _plugin)
/*     */   {
/*  87 */     if ("2".equals(EopSetting.RUNMODE))
/*     */     {
/*  89 */       if (this.saasPlugins == null) {
/*  90 */         return;
/*     */       }
/*     */ 
/*  93 */       String key = getKey();
/*  94 */       List pluginList = (List)this.saasPlugins.get(key);
/*  95 */       if (pluginList == null) {
/*  96 */         return;
/*     */       }
/*  98 */       pluginList.remove(_plugin);
/*     */     }
/* 103 */     else if (this.plugins != null) {
/* 104 */       this.plugins.remove(_plugin);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized List<IPlugin> getPlugins()
/*     */   {
/* 119 */     if ("2".equals(EopSetting.RUNMODE))
/*     */     {
/* 121 */       if (this.saasPlugins == null) {
/* 122 */         return new ArrayList();
/*     */       }
/*     */ 
/* 125 */       String key = getKey();
/* 126 */       List pluginList = (List)this.saasPlugins.get(key);
/* 127 */       if (pluginList == null) {
/* 128 */         return new ArrayList();
/*     */       }
/* 130 */       return pluginList;
/*     */     }
/*     */ 
/* 135 */     return this.plugins;
/*     */   }
/*     */ 
/*     */   private String getKey()
/*     */   {
/* 142 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 143 */     int userid = site.getUserid().intValue();
/* 144 */     int siteid = site.getId().intValue();
/* 145 */     String key = userid + "_" + siteid;
/*     */ 
/* 147 */     return key;
/*     */   }
/*     */ 
/*     */   public abstract String getName();
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.plugin.AutoRegisterPluginsBundle
 * JD-Core Version:    0.6.0
 */