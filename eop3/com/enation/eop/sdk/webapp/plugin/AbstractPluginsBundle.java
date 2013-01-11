/*    */ package com.enation.eop.sdk.webapp.plugin;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class AbstractPluginsBundle
/*    */   implements IPluginBundle
/*    */ {
/*    */   protected Map<String, List<IPlugin>> plugins;
/*    */ 
/*    */   public void registerPlugin(IPlugin plugin)
/*    */   {
/* 29 */     if (this.plugins == null) {
/* 30 */       this.plugins = new HashMap();
/*    */     }
/*    */ 
/* 33 */     List plugin_list = (List)this.plugins.get(plugin.getType());
/*    */ 
/* 35 */     if (plugin_list == null) {
/* 36 */       plugin_list = new ArrayList();
/*    */     }
/*    */ 
/* 39 */     plugin_list.add(plugin);
/* 40 */     this.plugins.put(plugin.getType(), plugin_list);
/*    */   }
/*    */ 
/*    */   protected void performPlugins(String type, Object[] param)
/*    */   {
/* 53 */     List plugin_list = (List)this.plugins.get(type);
/* 54 */     if (plugin_list != null)
/* 55 */       for (IPlugin plugin : plugin_list)
/* 56 */         plugin.perform(param);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.plugin.AbstractPluginsBundle
 * JD-Core Version:    0.6.0
 */