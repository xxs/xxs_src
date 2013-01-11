/*     */ package com.enation.framework.component.context;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WidgetContext
/*     */ {
/*     */   private static Map<String, Boolean> widgetState;
/*     */   private static Map<String, Map<String, Boolean>> saasWidgetState;
/*     */ 
/*     */   public static void putWidgetState(String widgetId, Boolean state)
/*     */   {
/*  53 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  54 */       String key = getKey();
/*  55 */       Map stateMap = (Map)saasWidgetState.get(key);
/*  56 */       if (stateMap == null) {
/*  57 */         stateMap = new HashMap();
/*     */       }
/*     */ 
/*  60 */       stateMap.put(widgetId, state);
/*  61 */       saasWidgetState.put(key, stateMap);
/*     */     }
/*     */     else {
/*  64 */       widgetState.put(widgetId, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Boolean getWidgetState(String widgetId)
/*     */   {
/*  77 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  78 */       String key = getKey();
/*  79 */       Map stateMap = (Map)saasWidgetState.get(key);
/*  80 */       if (stateMap == null) {
/*  81 */         stateMap = new HashMap();
/*  82 */         saasWidgetState.put(key, stateMap);
/*     */       }
/*     */ 
/*  85 */       Boolean state = (Boolean)stateMap.get(widgetId);
/*  86 */       if (state == null) return Boolean.valueOf(false);
/*  87 */       return state;
/*     */     }
/*     */ 
/*  90 */     Boolean state = (Boolean)widgetState.get(widgetId);
/*  91 */     if (state == null) return Boolean.valueOf(true);
/*  92 */     return state;
/*     */   }
/*     */ 
/*     */   private static String getKey()
/*     */   {
/*  98 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  99 */     int userid = site.getUserid().intValue();
/* 100 */     int siteid = site.getId().intValue();
/* 101 */     String key = userid + "_" + siteid;
/*     */ 
/* 103 */     return key;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  37 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  38 */       saasWidgetState = new HashMap();
/*     */     }
/*     */     else
/*  41 */       widgetState = new HashMap();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.context.WidgetContext
 * JD-Core Version:    0.6.0
 */