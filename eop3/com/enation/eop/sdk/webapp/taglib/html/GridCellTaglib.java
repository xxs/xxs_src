/*     */ package com.enation.eop.sdk.webapp.taglib.html;
/*     */ 
/*     */ import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;
/*     */ import com.enation.eop.sdk.webapp.taglib.html.support.GridCellProvider;
/*     */ 
/*     */ public class GridCellTaglib extends HtmlTaglib
/*     */ {
/*     */   private String sort;
/*     */   private String sortDefault;
/*     */   private String order;
/*     */   private int isSort;
/*     */   private int isAjax;
/*     */   private String style;
/*     */   private String width;
/*     */   private String height;
/*     */   private String align;
/*     */   private String plugin_type;
/*     */   private String clazz;
/*     */   private GridCellProvider cellProvider;
/*     */ 
/*     */   public GridCellTaglib()
/*     */   {
/*  32 */     this.cellProvider = new GridCellProvider();
/*     */   }
/*     */ 
/*     */   protected String postStart()
/*     */   {
/*  40 */     this.cellProvider.initCellProvider(this);
/*     */ 
/*  42 */     return this.cellProvider.getStartHtml();
/*     */   }
/*     */ 
/*     */   protected String postEnd()
/*     */   {
/*  51 */     return this.cellProvider.getEndHtml();
/*     */   }
/*     */ 
/*     */   public String getSort() {
/*  55 */     return this.sort;
/*     */   }
/*     */ 
/*     */   public void setSort(String sort) {
/*  59 */     this.sort = sort;
/*     */   }
/*     */ 
/*     */   public String getSortDefault() {
/*  63 */     return this.sortDefault;
/*     */   }
/*     */ 
/*     */   public void setSortDefault(String sortDefault) {
/*  67 */     this.sortDefault = sortDefault;
/*     */   }
/*     */ 
/*     */   public String getStyle() {
/*  71 */     return this.style;
/*     */   }
/*     */ 
/*     */   public void setStyle(String style) {
/*  75 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public String getHeight() {
/*  79 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(String height) {
/*  83 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public String getWidth() {
/*  87 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(String width) {
/*  91 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public String getAlign() {
/*  95 */     return this.align;
/*     */   }
/*     */ 
/*     */   public void setAlign(String align) {
/*  99 */     this.align = align;
/*     */   }
/*     */ 
/*     */   public String getPlugin_type() {
/* 103 */     return this.plugin_type;
/*     */   }
/*     */ 
/*     */   public void setPlugin_type(String plugin_type) {
/* 107 */     this.plugin_type = plugin_type;
/*     */   }
/*     */ 
/*     */   public int getIsAjax() {
/* 111 */     return this.isAjax;
/*     */   }
/*     */ 
/*     */   public void setIsAjax(int isAjax) {
/* 115 */     this.isAjax = isAjax;
/*     */   }
/*     */ 
/*     */   public int getIsSort() {
/* 119 */     return this.isSort;
/*     */   }
/*     */ 
/*     */   public void setIsSort(int isSort) {
/* 123 */     this.isSort = isSort;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 127 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 131 */     this.order = order;
/*     */   }
/*     */   public String getClazz() {
/* 134 */     return this.clazz;
/*     */   }
/*     */   public void setClazz(String clazz) {
/* 137 */     this.clazz = clazz;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridCellTaglib
 * JD-Core Version:    0.6.0
 */