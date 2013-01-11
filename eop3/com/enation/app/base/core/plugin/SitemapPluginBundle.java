/*    */ package com.enation.app.base.core.plugin;
/*    */ 
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import com.enation.framework.plugin.IPlugin;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SitemapPluginBundle extends AutoRegisterPluginsBundle
/*    */ {
/*    */   public String getName()
/*    */   {
/* 11 */     return "站点地图插件桩";
/*    */   }
/*    */ 
/*    */   public void onRecreateMap()
/*    */   {
/* 18 */     List plugins = getPlugins();
/*    */ 
/* 20 */     if (plugins != null)
/* 21 */       for (IPlugin plugin : plugins)
/* 22 */         if ((plugin instanceof IRecreateMapEvent)) {
/* 23 */           IRecreateMapEvent event = (IRecreateMapEvent)plugin;
/* 24 */           event.onRecreateMap();
/*    */         }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.SitemapPluginBundle
 * JD-Core Version:    0.6.0
 */