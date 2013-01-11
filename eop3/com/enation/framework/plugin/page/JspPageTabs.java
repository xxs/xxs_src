/*    */ package com.enation.framework.plugin.page;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class JspPageTabs
/*    */ {
/* 17 */   private static final Log loger = LogFactory.getLog(JspPageTabs.class);
/*    */ 
/* 21 */   private static Map<String, Map> tabs = new HashMap();
/*    */ 
/*    */   public static void addTab(String plugintype, Integer tabid, String tabname)
/*    */   {
/* 27 */     Map plugin_tab = tabs.get(plugintype) == null ? new LinkedHashMap() : (Map)tabs.get(plugintype);
/* 28 */     plugin_tab.put(tabid, tabname);
/*    */ 
/* 30 */     tabs.put(plugintype, plugin_tab);
/* 31 */     if (loger.isDebugEnabled())
/* 32 */       loger.debug("添加" + plugintype + "选项卡" + tabid + " tabname is  " + tabname);
/*    */   }
/*    */ 
/*    */   public static Map getTabs(String plugintype)
/*    */   {
/* 39 */     return (Map)tabs.get(plugintype);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.plugin.page.JspPageTabs
 * JD-Core Version:    0.6.0
 */