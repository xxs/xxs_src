/*     */ package com.enation.eop.sdk.webapp.bean;
/*     */ 
/*     */ import com.enation.framework.database.Page;
/*     */ 
/*     */ public abstract class Grid
/*     */ {
/*     */   private int iDisplayStart;
/*     */   private int iDisplayLength;
/*     */   private String iSortCol_0;
/*     */   private int iSortingCols;
/*     */   private String sSearch;
/*     */   private String sEcho;
/*     */   private String json;
/*     */   private Page webpage;
/*     */ 
/*     */   public void setJson(String json)
/*     */   {
/*  53 */     this.json = json;
/*     */   }
/*     */ 
/*     */   public Page getWebpage()
/*     */   {
/*  59 */     if (this.webpage == null)
/*  60 */       this.webpage = execute(getPageNo(), getPageSize(), getOrder());
/*  61 */     return this.webpage;
/*     */   }
/*     */   public void setWebpage(Page webpage) {
/*  64 */     this.webpage = webpage;
/*     */   }
/*     */ 
/*     */   public int getiDisplayStart() {
/*  68 */     return this.iDisplayStart;
/*     */   }
/*     */   public void setiDisplayStart(int iDisplayStart) {
/*  71 */     this.iDisplayStart = iDisplayStart;
/*     */   }
/*     */   public int getiDisplayLength() {
/*  74 */     return this.iDisplayLength;
/*     */   }
/*     */   public void setiDisplayLength(int iDisplayLength) {
/*  77 */     this.iDisplayLength = iDisplayLength;
/*     */   }
/*     */   public String getiSortCol_0() {
/*  80 */     return this.iSortCol_0;
/*     */   }
/*     */   public void setiSortCol_0(String iSortCol_0) {
/*  83 */     this.iSortCol_0 = iSortCol_0;
/*     */   }
/*     */   public int getiSortingCols() {
/*  86 */     return this.iSortingCols;
/*     */   }
/*     */   public void setiSortingCols(int iSortingCols) {
/*  89 */     this.iSortingCols = iSortingCols;
/*     */   }
/*     */   public String getsSearch() {
/*  92 */     return this.sSearch;
/*     */   }
/*     */   public void setsSearch(String sSearch) {
/*  95 */     this.sSearch = sSearch;
/*     */   }
/*     */   public String getsEcho() {
/*  98 */     return this.sEcho;
/*     */   }
/*     */   public void setsEcho(String sEcho) {
/* 101 */     this.sEcho = sEcho;
/*     */   }
/*     */ 
/*     */   public int getPageNo()
/*     */   {
/* 108 */     if (getiDisplayLength() == 0) {
/* 109 */       return 1;
/*     */     }
/* 111 */     return getiDisplayStart() / getiDisplayLength() + 1;
/*     */   }
/*     */   public int getPageSize() {
/* 114 */     if (getiDisplayLength() == 0) {
/* 115 */       return 10;
/*     */     }
/* 117 */     return getiDisplayLength();
/*     */   }
/*     */   public String getOrder() {
/* 120 */     return "";
/*     */   }
/*     */ 
/*     */   public abstract Page execute(int paramInt1, int paramInt2, String paramString);
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.bean.Grid
 * JD-Core Version:    0.6.0
 */