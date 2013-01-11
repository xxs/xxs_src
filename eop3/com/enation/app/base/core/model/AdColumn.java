/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class AdColumn
/*     */ {
/*     */   private Integer acid;
/*     */   private String cname;
/*     */   private String width;
/*     */   private String height;
/*     */   private String description;
/*     */   private Integer anumber;
/*     */   private Integer atype;
/*     */   private Integer rule;
/*     */   private Integer userid;
/*     */   private Integer siteid;
/*     */   private String disabled;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getAcid()
/*     */   {
/*  28 */     return this.acid;
/*     */   }
/*     */ 
/*     */   public void setAcid(Integer acid) {
/*  32 */     this.acid = acid;
/*     */   }
/*     */ 
/*     */   public String getCname() {
/*  36 */     return this.cname;
/*     */   }
/*     */ 
/*     */   public void setCname(String cname) {
/*  40 */     this.cname = cname;
/*     */   }
/*     */ 
/*     */   public String getWidth() {
/*  44 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(String width) {
/*  48 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public String getHeight() {
/*  52 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(String height) {
/*  56 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  60 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/*  64 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Integer getAnumber() {
/*  68 */     return this.anumber;
/*     */   }
/*     */ 
/*     */   public void setAnumber(Integer anumber) {
/*  72 */     this.anumber = anumber;
/*     */   }
/*     */ 
/*     */   public Integer getAtype() {
/*  76 */     return this.atype;
/*     */   }
/*     */ 
/*     */   public void setAtype(Integer atype) {
/*  80 */     this.atype = atype;
/*     */   }
/*     */ 
/*     */   public Integer getRule() {
/*  84 */     return this.rule;
/*     */   }
/*     */ 
/*     */   public void setRule(Integer rule) {
/*  88 */     this.rule = rule;
/*     */   }
/*     */ 
/*     */   public Integer getUserid() {
/*  92 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/*  96 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public Integer getSiteid() {
/* 100 */     return this.siteid;
/*     */   }
/*     */ 
/*     */   public void setSiteid(Integer siteid) {
/* 104 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/* 108 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/* 112 */     this.disabled = disabled;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.AdColumn
 * JD-Core Version:    0.6.0
 */