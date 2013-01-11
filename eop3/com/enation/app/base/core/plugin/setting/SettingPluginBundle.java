/*    */ package com.enation.app.base.core.plugin.setting;
/*    */ 
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import com.enation.framework.plugin.IPlugin;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class SettingPluginBundle extends AutoRegisterPluginsBundle
/*    */ {
/* 25 */   protected static final Log loger = LogFactory.getLog(SettingPluginBundle.class);
/*    */ 
/*    */   public String getName()
/*    */   {
/* 32 */     return "系统设置插件桩";
/*    */   }
/*    */ 
/*    */   public void registerPlugin(IPlugin plugin)
/*    */   {
/* 39 */     super.registerPlugin(plugin);
/*    */   }
/*    */ 
/*    */   public Map<Integer, String> onInputShow(Map<String, Map<String, String>> settings)
/*    */   {
/* 44 */     Map htmlMap = new TreeMap();
/*    */ 
/* 46 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 47 */     List plugins = getPlugins();
/*    */ 
/* 49 */     if (plugins != null) {
/* 50 */       for (IPlugin plugin : plugins) {
/* 51 */         if ((plugin instanceof IOnSettingInputShow)) {
/* 52 */           IOnSettingInputShow event = (IOnSettingInputShow)plugin;
/* 53 */           String groupname = event.getSettingGroupName();
/* 54 */           String pageName = event.onShow();
/*    */ 
/* 56 */           freeMarkerPaser.setClz(event.getClass());
/* 57 */           freeMarkerPaser.setPageName(pageName);
/* 58 */           freeMarkerPaser.putData(groupname, settings.get(groupname));
/* 59 */           htmlMap.put(Integer.valueOf(event.getOrder()), freeMarkerPaser.proessPageContent());
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 64 */     return htmlMap;
/*    */   }
/*    */ 
/*    */   public Map<Integer, String> getTabs()
/*    */   {
/* 71 */     Map tabMap = new TreeMap();
/* 72 */     List plugins = getPlugins();
/*    */ 
/* 74 */     if (plugins != null) {
/* 75 */       for (IPlugin plugin : plugins) {
/* 76 */         if ((plugin instanceof IOnSettingInputShow)) {
/* 77 */           IOnSettingInputShow event = (IOnSettingInputShow)plugin;
/* 78 */           String name = event.getTabName();
/* 79 */           tabMap.put(Integer.valueOf(event.getOrder()), name);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 84 */     return tabMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.setting.SettingPluginBundle
 * JD-Core Version:    0.6.0
 */