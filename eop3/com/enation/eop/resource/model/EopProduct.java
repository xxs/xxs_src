/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class EopProduct
/*     */ {
/*     */   private Integer id;
/*     */   private String productid;
/*     */   private String product_name;
/*     */   private String author;
/*     */   private String descript;
/*     */   private Long createtime;
/*     */   private String version;
/*     */   private String preview;
/*     */   private Integer catid;
/*     */   private Integer typeid;
/*     */   private Integer colorid;
/*     */   private Integer pstate;
/*     */   private String copyright;
/*     */   private Integer sort;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getId()
/*     */   {
/*  31 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  35 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getProductid() {
/*  39 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(String productid) {
/*  43 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public String getProduct_name() {
/*  47 */     return this.product_name;
/*     */   }
/*     */ 
/*     */   public void setProduct_name(String productName) {
/*  51 */     this.product_name = productName;
/*     */   }
/*     */ 
/*     */   public String getAuthor() {
/*  55 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author) {
/*  59 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public String getDescript() {
/*  63 */     return this.descript;
/*     */   }
/*     */ 
/*     */   public void setDescript(String descript) {
/*  67 */     this.descript = descript;
/*     */   }
/*     */ 
/*     */   public Long getCreatetime() {
/*  71 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Long createtime) {
/*  75 */     this.createtime = createtime;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/*  79 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/*  83 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public String getPreview() {
/*  87 */     return this.preview;
/*     */   }
/*     */ 
/*     */   public void setPreview(String preview) {
/*  91 */     this.preview = preview;
/*     */   }
/*     */ 
/*     */   public Integer getCatid() {
/*  95 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(Integer catid) {
/*  99 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public Integer getTypeid() {
/* 103 */     return this.typeid;
/*     */   }
/*     */ 
/*     */   public void setTypeid(Integer typeid) {
/* 107 */     this.typeid = typeid;
/*     */   }
/*     */ 
/*     */   public Integer getSort() {
/* 111 */     return this.sort;
/*     */   }
/*     */ 
/*     */   public void setSort(Integer sort) {
/* 115 */     this.sort = sort;
/*     */   }
/*     */ 
/*     */   public Integer getColorid() {
/* 119 */     return this.colorid;
/*     */   }
/*     */ 
/*     */   public void setColorid(Integer colorid) {
/* 123 */     this.colorid = colorid;
/*     */   }
/*     */ 
/*     */   public Integer getPstate() {
/* 127 */     return this.pstate;
/*     */   }
/*     */ 
/*     */   public void setPstate(Integer pstate) {
/* 131 */     this.pstate = pstate;
/*     */   }
/*     */ 
/*     */   public String getCopyright() {
/* 135 */     return this.copyright;
/*     */   }
/*     */ 
/*     */   public void setCopyright(String copyright) {
/* 139 */     this.copyright = copyright;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopProduct
 * JD-Core Version:    0.6.0
 */