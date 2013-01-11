/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Brand
/*     */   implements Serializable
/*     */ {
/*     */   private Integer brand_id;
/*     */   private String name;
/*     */   private String logo;
/*     */   private String keywords;
/*     */   private String brief;
/*     */   private String url;
/*     */   private Integer disabled;
/*     */   private File file;
/*     */   private String fileFileName;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  48 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  52 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getLogo() {
/*  56 */     return this.logo;
/*     */   }
/*     */ 
/*     */   public void setLogo(String logo) {
/*  60 */     this.logo = logo;
/*     */   }
/*     */ 
/*     */   public String getKeywords() {
/*  64 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords) {
/*  68 */     this.keywords = keywords;
/*     */   }
/*     */ 
/*     */   public String getBrief() {
/*  72 */     return this.brief;
/*     */   }
/*     */ 
/*     */   public void setBrief(String brief) {
/*  76 */     this.brief = brief;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  80 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/*  84 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public Integer getDisabled()
/*     */   {
/*  89 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Integer disabled)
/*     */   {
/*  94 */     this.disabled = disabled;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getBrand_id() {
/*  99 */     return this.brand_id;
/*     */   }
/*     */ 
/*     */   public void setBrand_id(Integer brand_id) {
/* 103 */     this.brand_id = brand_id;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public File getFile() {
/* 109 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void setFile(File file)
/*     */   {
/* 114 */     this.file = file;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getFileFileName() {
/* 120 */     return this.fileFileName;
/*     */   }
/*     */ 
/*     */   public void setFileFileName(String fileFileName)
/*     */   {
/* 125 */     this.fileFileName = fileFileName;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Brand
 * JD-Core Version:    0.6.0
 */