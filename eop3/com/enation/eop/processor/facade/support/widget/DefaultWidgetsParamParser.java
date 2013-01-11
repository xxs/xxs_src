/*     */ package com.enation.eop.processor.facade.support.widget;
/*     */ 
/*     */ import com.enation.eop.processor.widget.IWidgetParamParser;
/*     */ import com.enation.eop.processor.widget.WidgetXmlUtil;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DefaultWidgetsParamParser
/*     */   implements IWidgetParamParser
/*     */ {
/*     */   private IWidgetParamParser widgetParamParser;
/*     */ 
/*     */   public DefaultWidgetsParamParser(IWidgetParamParser widgetParamParser)
/*     */   {
/*  18 */     this.widgetParamParser = widgetParamParser;
/*     */   }
/*     */ 
/*     */   public Map<String, Map<String, Map<String, String>>> parse()
/*     */   {
/*  24 */     String contextPath = EopContext.getContext().getContextPath();
/*  25 */     String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/widgets_default.xml";
/*     */ 
/*  31 */     Map pageParams = this.widgetParamParser.parse();
/*  32 */     File file = new File(path);
/*     */ 
/*  35 */     if (!file.exists()) {
/*  36 */       return pageParams;
/*     */     }
/*     */ 
/*  40 */     Map defaultPageParams = WidgetXmlUtil.parse(path);
/*     */ 
/*  43 */     Iterator pageIdItor = pageParams.keySet().iterator();
/*     */ 
/*  53 */     while (pageIdItor.hasNext()) {
/*  54 */       String pageId = (String)pageIdItor.next();
/*     */ 
/*  57 */       Map defaultWidgetParams = (Map)defaultPageParams.get(pageId);
/*  58 */       Map widgetParams = (Map)pageParams.get(pageId);
/*     */ 
/*  61 */       if (defaultWidgetParams == null) {
/*  62 */         defaultPageParams.put(pageId, widgetParams);
/*  63 */         continue;
/*     */       }
/*     */ 
/*  69 */       Iterator widgetItor = widgetParams.keySet().iterator();
/*  70 */       while (widgetItor.hasNext()) {
/*  71 */         String widgetId = (String)widgetItor.next();
/*     */ 
/*  73 */         Map oneDefWgtParams = (Map)defaultWidgetParams.get(widgetId);
/*  74 */         Map oneWgtParams = (Map)widgetParams.get(widgetId);
/*     */ 
/*  77 */         if (oneDefWgtParams == null) {
/*  78 */           defaultWidgetParams.put(widgetId, oneWgtParams);
/*     */         }
/*     */         else {
/*  81 */           oneDefWgtParams.putAll(oneWgtParams);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  92 */     return defaultPageParams;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  96 */     Map params1 = new HashMap();
/*  97 */     Map params2 = new HashMap();
/*     */ 
/* 100 */     params1.put("p1", "p1_1");
/* 101 */     params1.put("p2", "p1_2");
/* 102 */     params1.put("p3", "p1_3");
/*     */ 
/* 107 */     params2.put("p2", "p1_2");
/* 108 */     params2.put("p3", "p2_3");
/* 109 */     params2.put("p4", "p2_3");
/*     */ 
/* 111 */     params2.putAll(params1);
/*     */ 
/* 113 */     System.out.println((String)params2.get("p4"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.DefaultWidgetsParamParser
 * JD-Core Version:    0.6.0
 */