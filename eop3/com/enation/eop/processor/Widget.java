/*     */ package com.enation.eop.processor;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Widget
/*     */ {
/*     */   private Map<String, String> attrMap;
/*     */   private String widgetType;
/*     */   private EopApp app;
/*     */   private EopSite site;
/*     */ 
/*     */   public Map<String, String> getAttrMap()
/*     */   {
/*  25 */     return this.attrMap;
/*     */   }
/*     */ 
/*     */   public void setAttrMap(Map<String, String> attrMap)
/*     */   {
/*  30 */     this.attrMap = attrMap;
/*     */   }
/*     */ 
/*     */   public EopSite getSite()
/*     */   {
/*  35 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(EopSite site)
/*     */   {
/*  40 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public Widget()
/*     */   {
/*  45 */     this.attrMap = new HashMap();
/*     */   }
/*     */ 
/*     */   public Widget(EopApp app)
/*     */   {
/*  50 */     this.app = app;
/*  51 */     this.attrMap = new HashMap();
/*     */   }
/*     */ 
/*     */   public void setAttribute(String name, String value)
/*     */   {
/*  62 */     this.attrMap.put(name, value);
/*     */   }
/*     */ 
/*     */   public void setAttributes(Map<String, String> attrs) {
/*  66 */     this.attrMap = attrs;
/*     */   }
/*     */ 
/*     */   public String getAttribute(String name)
/*     */   {
/*  76 */     name = name == null ? null : name.toLowerCase();
/*  77 */     return (String)this.attrMap.get(name);
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAtributes()
/*     */   {
/*  86 */     return this.attrMap;
/*     */   }
/*     */ 
/*     */   public String getWidgetType()
/*     */   {
/*  91 */     return this.widgetType;
/*     */   }
/*     */ 
/*     */   public void setWidgetType(String widgetType) {
/*  95 */     this.widgetType = widgetType;
/*     */   }
/*     */ 
/*     */   public EopApp getApp() {
/*  99 */     return this.app;
/*     */   }
/*     */ 
/*     */   public void setApp(EopApp app) {
/* 103 */     this.app = app;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.Widget
 * JD-Core Version:    0.6.0
 */