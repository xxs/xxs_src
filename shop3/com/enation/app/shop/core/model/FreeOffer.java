/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class FreeOffer
/*     */ {
/*     */   private Integer fo_id;
/*     */   private Integer fo_category_id;
/*     */   private String cat_name;
/*     */   private String fo_name;
/*     */   private Integer publishable;
/*     */   private Integer recommend;
/*     */   private Integer sorder;
/*     */   private Integer limit_purchases;
/*     */   private Long startdate;
/*     */   private Long enddate;
/*     */   private String lv_ids;
/*     */   private Double price;
/*     */   private String synopsis;
/*     */   private String list_thumb;
/*     */   private String pic;
/*     */   private Integer score;
/*     */   private Double weight;
/*     */   private Integer repertory;
/*     */   private String descript;
/*     */   private Integer disabled;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getFo_id()
/*     */   {
/*  38 */     return this.fo_id;
/*     */   }
/*     */   public void setFo_id(Integer foId) {
/*  41 */     this.fo_id = foId;
/*     */   }
/*     */   public Integer getFo_category_id() {
/*  44 */     return this.fo_category_id;
/*     */   }
/*     */   public void setFo_category_id(Integer foCategoryId) {
/*  47 */     this.fo_category_id = foCategoryId;
/*     */   }
/*     */   public String getFo_name() {
/*  50 */     return this.fo_name;
/*     */   }
/*     */   public void setFo_name(String foName) {
/*  53 */     this.fo_name = foName;
/*     */   }
/*     */   public Integer getPublishable() {
/*  56 */     return this.publishable;
/*     */   }
/*     */   public void setPublishable(Integer publishable) {
/*  59 */     this.publishable = publishable;
/*     */   }
/*     */   public Integer getRecommend() {
/*  62 */     return this.recommend;
/*     */   }
/*     */   public void setRecommend(Integer recommend) {
/*  65 */     this.recommend = recommend;
/*     */   }
/*     */   public Integer getSorder() {
/*  68 */     return this.sorder;
/*     */   }
/*     */   public void setSorder(Integer sorder) {
/*  71 */     this.sorder = sorder;
/*     */   }
/*     */   public Integer getLimit_purchases() {
/*  74 */     return this.limit_purchases;
/*     */   }
/*     */   public void setLimit_purchases(Integer limitPurchases) {
/*  77 */     this.limit_purchases = limitPurchases;
/*     */   }
/*     */   public Long getStartdate() {
/*  80 */     return this.startdate;
/*     */   }
/*     */   public void setStartdate(Long startdate) {
/*  83 */     this.startdate = startdate;
/*     */   }
/*     */   public Long getEnddate() {
/*  86 */     return this.enddate;
/*     */   }
/*     */   public void setEnddate(Long enddate) {
/*  89 */     this.enddate = enddate;
/*     */   }
/*     */ 
/*     */   public String getLv_ids() {
/*  93 */     return this.lv_ids;
/*     */   }
/*     */   public void setLv_ids(String lvIds) {
/*  96 */     this.lv_ids = lvIds;
/*     */   }
/*     */   public Double getPrice() {
/*  99 */     return this.price;
/*     */   }
/*     */   public void setPrice(Double price) {
/* 102 */     this.price = price;
/*     */   }
/*     */   public String getSynopsis() {
/* 105 */     return this.synopsis;
/*     */   }
/*     */   public void setSynopsis(String synopsis) {
/* 108 */     this.synopsis = synopsis;
/*     */   }
/*     */   public String getList_thumb() {
/* 111 */     return this.list_thumb;
/*     */   }
/*     */   public void setList_thumb(String listThumb) {
/* 114 */     this.list_thumb = listThumb;
/*     */   }
/*     */   public String getPic() {
/* 117 */     return this.pic;
/*     */   }
/*     */   public void setPic(String pic) {
/* 120 */     this.pic = pic;
/*     */   }
/*     */   public Integer getScore() {
/* 123 */     if (this.score == null)
/* 124 */       this.score = Integer.valueOf(0);
/* 125 */     return this.score;
/*     */   }
/*     */   public void setScore(Integer score) {
/* 128 */     this.score = score;
/*     */   }
/*     */   public Double getWeight() {
/* 131 */     return this.weight;
/*     */   }
/*     */   public void setWeight(Double weight) {
/* 134 */     this.weight = weight;
/*     */   }
/*     */   public Integer getRepertory() {
/* 137 */     return this.repertory;
/*     */   }
/*     */   public void setRepertory(Integer repertory) {
/* 140 */     this.repertory = repertory;
/*     */   }
/*     */   public String getDescript() {
/* 143 */     return this.descript;
/*     */   }
/*     */   public void setDescript(String descript) {
/* 146 */     this.descript = descript;
/*     */   }
/*     */ 
/*     */   public Integer getDisabled() {
/* 150 */     return this.disabled;
/*     */   }
/*     */   public void setDisabled(Integer disabled) {
/* 153 */     this.disabled = disabled;
/*     */   }
/* 157 */   @NotDbField
/*     */   public String getCat_name() { return this.cat_name; }
/*     */ 
/*     */   public void setCat_name(String catname) {
/* 160 */     this.cat_name = catname;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.FreeOffer
 * JD-Core Version:    0.6.0
 */