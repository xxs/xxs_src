/*    */ package com.enation.framework.plugin.page;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PluginPageContext
/*    */ {
/* 18 */   private static Map<String, List<String>> plugin_pages = new HashMap();
/*    */ 
/*    */   public static void addPage(String type, String page)
/*    */   {
/* 30 */     List pagelist = (List)plugin_pages.get(type);
/* 31 */     if (pagelist == null)
/* 32 */       pagelist = new ArrayList();
/* 33 */     pagelist.add(page);
/* 34 */     plugin_pages.put(type, pagelist);
/* 35 */     System.out.println("加载" + type + " 类页面： " + page);
/*    */   }
/*    */ 
/*    */   public static List<String> getPages(String page_type)
/*    */   {
/* 46 */     return (List)plugin_pages.get(page_type);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.plugin.page.PluginPageContext
 * JD-Core Version:    0.6.0
 */