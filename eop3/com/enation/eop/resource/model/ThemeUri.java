/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import java.io.Serializable;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ThemeUri extends Resource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3537303897321923063L;
/*     */   private Integer themeid;
/*     */   private String uri;
/*     */   private String path;
/*     */   private String pagename;
/*     */   private int point;
/*     */   private int sitemaptype;
/*     */   private String keywords;
/*     */   private String description;
/*     */   private int httpcache;
/*     */   private Pattern pattern;
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  34 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path) {
/*  38 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public Integer getThemeid() {
/*  42 */     return this.themeid;
/*     */   }
/*     */ 
/*     */   public void setThemeid(Integer themeid) {
/*  46 */     this.themeid = themeid;
/*     */   }
/*     */ 
/*     */   public String getUri() {
/*  50 */     return this.uri;
/*     */   }
/*     */ 
/*     */   public void setUri(String uri) {
/*  54 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   public String getPagename() {
/*  58 */     return this.pagename;
/*     */   }
/*     */ 
/*     */   public void setPagename(String pagename) {
/*  62 */     this.pagename = pagename;
/*     */   }
/*     */ 
/*     */   public int getPoint() {
/*  66 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(int point) {
/*  70 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public int getSitemaptype() {
/*  74 */     return this.sitemaptype;
/*     */   }
/*     */ 
/*     */   public void setSitemaptype(int sitemaptype) {
/*  78 */     this.sitemaptype = sitemaptype;
/*     */   }
/*     */ 
/*     */   public String getKeywords() {
/*  82 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords) {
/*  86 */     this.keywords = keywords;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  90 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/*  94 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public int getHttpcache() {
/*  98 */     return this.httpcache;
/*     */   }
/*     */ 
/*     */   public void setHttpcache(int httpcache) {
/* 102 */     this.httpcache = httpcache;
/*     */   }
/*     */   @NotDbField
/*     */   public Pattern getPattern() {
/* 107 */     if (this.pattern == null) {
/* 108 */       this.pattern = Pattern.compile("^" + this.uri + "$", 34);
/*     */     }
/* 110 */     return this.pattern;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.ThemeUri
 * JD-Core Version:    0.6.0
 */