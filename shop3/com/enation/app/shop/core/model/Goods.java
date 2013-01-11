/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Goods
/*     */   implements Serializable
/*     */ {
/*     */   private Integer goods_id;
/*     */   private String name;
/*     */   private String sn;
/*     */   private Integer brand_id;
/*     */   private Integer cat_id;
/*     */   private Integer type_id;
/*     */   private String goods_type;
/*     */   private String unit;
/*     */   private Double weight;
/*     */   private Integer market_enable;
/*     */   private String image_default;
/*     */   private String image_file;
/*     */   private String brief;
/*     */   private String intro;
/*     */   private Double price;
/*     */   private Double mktprice;
/*     */   private Integer store;
/*     */   private String adjuncts;
/*     */   private String params;
/*     */   private String specs;
/*     */   private Long create_time;
/*     */   private Long last_modify;
/*     */   private Integer view_count;
/*     */   private Integer buy_count;
/*     */   private Integer disabled;
/*     */   private String page_title;
/*     */   private String meta_keywords;
/*     */   private String meta_description;
/*     */   private Integer point;
/*     */   private Integer sord;
/*     */   private Integer istejia;
/*     */   private Integer no_discount;
/*     */   private Double max_degree;
/*     */   private Double min_degree;
/*     */ 
/*     */   public Double getMax_degree()
/*     */   {
/*  57 */     return this.max_degree;
/*     */   }
/*     */   public void setMax_degree(Double max_degree) {
/*  60 */     this.max_degree = max_degree;
/*     */   }
/*     */   public Double getMin_degree() {
/*  63 */     return this.min_degree;
/*     */   }
/*     */   public void setMin_degree(Double min_degree) {
/*  66 */     this.min_degree = min_degree;
/*     */   }
/*     */   public Integer getBrand_id() {
/*  69 */     if (this.brand_id == null) this.brand_id = Integer.valueOf(0);
/*  70 */     return this.brand_id;
/*     */   }
/*     */   public void setBrand_id(Integer brand_id) {
/*  73 */     this.brand_id = brand_id;
/*     */   }
/*     */   public String getBrief() {
/*  76 */     return this.brief;
/*     */   }
/*     */   public void setBrief(String brief) {
/*  79 */     this.brief = brief;
/*     */   }
/*     */   public Integer getBuy_count() {
/*  82 */     return this.buy_count;
/*     */   }
/*     */   public void setBuy_count(Integer buy_count) {
/*  85 */     this.buy_count = buy_count;
/*     */   }
/*     */ 
/*     */   public Integer getDisabled()
/*     */   {
/*  90 */     return this.disabled;
/*     */   }
/*     */   public void setDisabled(Integer disabled) {
/*  93 */     this.disabled = disabled;
/*     */   }
/*  97 */   @PrimaryKeyField
/*     */   public Integer getGoods_id() { return this.goods_id; }
/*     */ 
/*     */   public void setGoods_id(Integer goods_id) {
/* 100 */     this.goods_id = goods_id;
/*     */   }
/*     */   public String getImage_default() {
/* 103 */     return this.image_default;
/*     */   }
/*     */   public void setImage_default(String image_default) {
/* 106 */     this.image_default = image_default;
/*     */   }
/*     */   public String getImage_file() {
/* 109 */     return this.image_file;
/*     */   }
/*     */   public void setImage_file(String image_file) {
/* 112 */     this.image_file = image_file;
/*     */   }
/*     */   public String getIntro() {
/* 115 */     return this.intro;
/*     */   }
/*     */   public void setIntro(String intro) {
/* 118 */     this.intro = intro;
/*     */   }
/*     */ 
/*     */   public Integer getMarket_enable() {
/* 122 */     return this.market_enable;
/*     */   }
/*     */   public void setMarket_enable(Integer market_enable) {
/* 125 */     this.market_enable = market_enable;
/*     */   }
/*     */   public String getMeta_description() {
/* 128 */     return this.meta_description;
/*     */   }
/*     */   public void setMeta_description(String meta_description) {
/* 131 */     this.meta_description = meta_description;
/*     */   }
/*     */   public String getMeta_keywords() {
/* 134 */     return this.meta_keywords;
/*     */   }
/*     */   public void setMeta_keywords(String meta_keywords) {
/* 137 */     this.meta_keywords = meta_keywords;
/*     */   }
/*     */   public Double getMktprice() {
/* 140 */     return this.mktprice;
/*     */   }
/*     */   public void setMktprice(Double mktprice) {
/* 143 */     this.mktprice = mktprice;
/*     */   }
/*     */   public String getName() {
/* 146 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/* 149 */     this.name = name;
/*     */   }
/*     */   public String getPage_title() {
/* 152 */     return this.page_title;
/*     */   }
/*     */   public void setPage_title(String page_title) {
/* 155 */     this.page_title = page_title;
/*     */   }
/*     */   public String getParams() {
/* 158 */     return this.params;
/*     */   }
/*     */   public void setParams(String params) {
/* 161 */     this.params = params;
/*     */   }
/*     */   public Double getPrice() {
/* 164 */     return this.price;
/*     */   }
/*     */   public void setPrice(Double price) {
/* 167 */     this.price = price;
/*     */   }
/*     */   public String getSn() {
/* 170 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/* 173 */     this.sn = sn;
/*     */   }
/*     */   public Integer getType_id() {
/* 176 */     return this.type_id;
/*     */   }
/*     */   public void setType_id(Integer type_id) {
/* 179 */     this.type_id = type_id;
/*     */   }
/*     */   public String getGoods_type() {
/* 182 */     return this.goods_type;
/*     */   }
/*     */   public void setGoods_type(String goodsType) {
/* 185 */     this.goods_type = goodsType;
/*     */   }
/*     */   public String getUnit() {
/* 188 */     return this.unit;
/*     */   }
/*     */   public void setUnit(String unit) {
/* 191 */     this.unit = unit;
/*     */   }
/*     */   public Integer getView_count() {
/* 194 */     return this.view_count;
/*     */   }
/*     */   public void setView_count(Integer view_count) {
/* 197 */     this.view_count = view_count;
/*     */   }
/*     */   public Double getWeight() {
/* 200 */     this.weight = (this.weight == null ? (this.weight = Double.valueOf(0.0D)) : this.weight);
/* 201 */     return this.weight;
/*     */   }
/*     */   public void setWeight(Double weight) {
/* 204 */     this.weight = weight;
/*     */   }
/*     */   public Integer getCat_id() {
/* 207 */     return this.cat_id;
/*     */   }
/*     */   public void setCat_id(Integer cat_id) {
/* 210 */     this.cat_id = cat_id;
/*     */   }
/*     */   public Integer getStore() {
/* 213 */     return this.store;
/*     */   }
/*     */   public void setStore(Integer store) {
/* 216 */     this.store = store;
/*     */   }
/*     */   public Long getCreate_time() {
/* 219 */     return this.create_time;
/*     */   }
/*     */   public void setCreate_time(Long create_time) {
/* 222 */     this.create_time = create_time;
/*     */   }
/*     */   public Long getLast_modify() {
/* 225 */     return this.last_modify;
/*     */   }
/*     */   public void setLast_modify(Long last_modify) {
/* 228 */     this.last_modify = last_modify;
/*     */   }
/*     */   public String getSpecs() {
/* 231 */     return this.specs;
/*     */   }
/*     */   public void setSpecs(String specs) {
/* 234 */     this.specs = specs;
/*     */   }
/*     */   public String getAdjuncts() {
/* 237 */     return this.adjuncts;
/*     */   }
/*     */   public void setAdjuncts(String adjuncts) {
/* 240 */     this.adjuncts = adjuncts;
/*     */   }
/*     */   public Integer getPoint() {
/* 243 */     this.point = Integer.valueOf(this.point == null ? 0 : this.point.intValue());
/* 244 */     return this.point;
/*     */   }
/*     */   public void setPoint(Integer point) {
/* 247 */     this.point = point;
/*     */   }
/*     */   public Integer getSord() {
/* 250 */     return this.sord;
/*     */   }
/*     */   public void setSord(Integer sord) {
/* 253 */     this.sord = sord;
/*     */   }
/*     */ 
/*     */   public Integer getIstejia() {
/* 257 */     return this.istejia;
/*     */   }
/*     */ 
/*     */   public void setIstejia(Integer istejia) {
/* 261 */     this.istejia = istejia;
/*     */   }
/*     */   public Integer getNo_discount() {
/* 264 */     return this.no_discount;
/*     */   }
/*     */   public void setNo_discount(Integer no_discount) {
/* 267 */     this.no_discount = no_discount;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Goods
 * JD-Core Version:    0.6.0
 */