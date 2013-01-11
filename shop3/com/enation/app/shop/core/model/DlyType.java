/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.app.shop.core.model.support.DlyTypeConfig;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DlyType
/*     */   implements Serializable
/*     */ {
/*     */   private Integer type_id;
/*     */   private String name;
/*     */   private Integer protect;
/*     */   private Float protect_rate;
/*     */   private Float min_price;
/*     */   private Integer has_cod;
/*     */   private Integer corp_id;
/*     */   private Integer ordernum;
/*     */   private Integer disabled;
/*     */   private Integer is_same;
/*     */   private String detail;
/*     */   private String config;
/*     */   private String expressions;
/*     */   private String json;
/*     */   private DlyTypeConfig typeConfig;
/*     */   private List typeAreaList;
/*     */   private Double price;
/*     */ 
/*     */   @NotDbField
/*     */   public String getJson()
/*     */   {
/*  51 */     return this.json;
/*     */   }
/*     */ 
/*     */   public void setJson(String json)
/*     */   {
/*  56 */     this.json = json;
/*     */   }
/*     */ 
/*     */   public String getDetail()
/*     */   {
/*  61 */     return this.detail;
/*     */   }
/*     */ 
/*     */   public void setDetail(String detail) {
/*  65 */     this.detail = detail;
/*     */   }
/*     */ 
/*     */   public Float getMin_price() {
/*  69 */     return this.min_price;
/*     */   }
/*     */ 
/*     */   public void setMin_price(Float min_price) {
/*  73 */     this.min_price = min_price;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  81 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Float getProtect_rate() {
/*  85 */     return this.protect_rate;
/*     */   }
/*     */ 
/*     */   public void setProtect_rate(Float protect_rate) {
/*  89 */     this.protect_rate = protect_rate;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getType_id() {
/*  94 */     return this.type_id;
/*     */   }
/*     */ 
/*     */   public void setType_id(Integer type_id) {
/*  98 */     this.type_id = type_id;
/*     */   }
/*     */ 
/*     */   public Integer getProtect() {
/* 102 */     return Integer.valueOf(this.protect == null ? 0 : this.protect.intValue());
/*     */   }
/*     */ 
/*     */   public void setProtect(Integer protect) {
/* 106 */     this.protect = protect;
/*     */   }
/*     */ 
/*     */   public Integer getHas_cod() {
/* 110 */     return this.has_cod;
/*     */   }
/*     */ 
/*     */   public void setHas_cod(Integer hasCod) {
/* 114 */     this.has_cod = hasCod;
/*     */   }
/*     */ 
/*     */   public Integer getCorp_id() {
/* 118 */     return this.corp_id;
/*     */   }
/*     */ 
/*     */   public void setCorp_id(Integer corpId) {
/* 122 */     this.corp_id = corpId;
/*     */   }
/*     */ 
/*     */   public Integer getOrdernum() {
/* 126 */     return this.ordernum;
/*     */   }
/*     */ 
/*     */   public void setOrdernum(Integer ordernum) {
/* 130 */     this.ordernum = ordernum;
/*     */   }
/*     */ 
/*     */   public Integer getDisabled() {
/* 134 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Integer disabled) {
/* 138 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Integer getIs_same() {
/* 142 */     return this.is_same;
/*     */   }
/*     */ 
/*     */   public void setIs_same(Integer isSame) {
/* 146 */     this.is_same = isSame;
/*     */   }
/*     */ 
/*     */   public String getExpressions()
/*     */   {
/* 151 */     return this.expressions;
/*     */   }
/*     */ 
/*     */   public void setExpressions(String expressions) {
/* 155 */     this.expressions = expressions;
/*     */   }
/*     */ 
/*     */   public String getConfig() {
/* 159 */     return this.config;
/*     */   }
/*     */ 
/*     */   public void setConfig(String config) {
/* 163 */     this.config = config;
/*     */   }
/*     */   @NotDbField
/*     */   public DlyTypeConfig getTypeConfig() {
/* 168 */     return this.typeConfig;
/*     */   }
/*     */ 
/*     */   public void setTypeConfig(DlyTypeConfig typeConfig) {
/* 172 */     this.typeConfig = typeConfig;
/*     */   }
/*     */   @NotDbField
/*     */   public List getTypeAreaList() {
/* 177 */     return this.typeAreaList;
/*     */   }
/*     */ 
/*     */   public void setTypeAreaList(List typeAreaList) {
/* 181 */     this.typeAreaList = typeAreaList;
/*     */   }
/*     */   @NotDbField
/*     */   public Double getPrice() {
/* 186 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double price)
/*     */   {
/* 191 */     this.price = price;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.DlyType
 * JD-Core Version:    0.6.0
 */