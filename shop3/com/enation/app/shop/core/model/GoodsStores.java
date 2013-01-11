/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class GoodsStores
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
/*     */   private Double price;
/*     */   private Double mktprice;
/*     */   private Integer store;
/*     */   private Long create_time;
/*     */   private Long last_modify;
/*     */   private Integer disabled;
/*     */   private Integer point;
/*     */   private Integer istejia;
/*     */   private Integer no_discount;
/*     */   private String color;
/*     */   private Double sphere;
/*     */   private Integer realstore;
/*     */   private Double cylinder;
/*     */   private String brandname;
/*     */   private String catname;
/*     */ 
/*     */   public Integer getGoods_id()
/*     */   {
/*  38 */     return this.goods_id;
/*     */   }
/*     */   public void setGoods_id(Integer goods_id) {
/*  41 */     this.goods_id = goods_id;
/*     */   }
/*     */   public String getName() {
/*  44 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  47 */     this.name = name;
/*     */   }
/*     */   public String getSn() {
/*  50 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/*  53 */     this.sn = sn;
/*     */   }
/*     */   public Integer getBrand_id() {
/*  56 */     return this.brand_id;
/*     */   }
/*     */   public void setBrand_id(Integer brand_id) {
/*  59 */     this.brand_id = brand_id;
/*     */   }
/*     */   public Integer getCat_id() {
/*  62 */     return this.cat_id;
/*     */   }
/*     */   public void setCat_id(Integer cat_id) {
/*  65 */     this.cat_id = cat_id;
/*     */   }
/*     */   public Integer getType_id() {
/*  68 */     return this.type_id;
/*     */   }
/*     */   public void setType_id(Integer type_id) {
/*  71 */     this.type_id = type_id;
/*     */   }
/*     */   public String getGoods_type() {
/*  74 */     return this.goods_type;
/*     */   }
/*     */   public void setGoods_type(String goods_type) {
/*  77 */     this.goods_type = goods_type;
/*     */   }
/*     */   public String getUnit() {
/*  80 */     return this.unit;
/*     */   }
/*     */   public void setUnit(String unit) {
/*  83 */     this.unit = unit;
/*     */   }
/*     */   public Double getWeight() {
/*  86 */     return this.weight;
/*     */   }
/*     */   public void setWeight(Double weight) {
/*  89 */     this.weight = weight;
/*     */   }
/*     */   public Integer getMarket_enable() {
/*  92 */     return this.market_enable;
/*     */   }
/*     */   public void setMarket_enable(Integer market_enable) {
/*  95 */     this.market_enable = market_enable;
/*     */   }
/*     */   public Double getPrice() {
/*  98 */     return this.price;
/*     */   }
/*     */   public void setPrice(Double price) {
/* 101 */     this.price = price;
/*     */   }
/*     */   public Double getMktprice() {
/* 104 */     return this.mktprice;
/*     */   }
/*     */   public void setMktprice(Double mktprice) {
/* 107 */     this.mktprice = mktprice;
/*     */   }
/*     */   public Integer getStore() {
/* 110 */     return this.store;
/*     */   }
/*     */   public void setStore(Integer store) {
/* 113 */     this.store = store;
/*     */   }
/*     */   public Long getCreate_time() {
/* 116 */     return this.create_time;
/*     */   }
/*     */   public void setCreate_time(Long create_time) {
/* 119 */     this.create_time = create_time;
/*     */   }
/*     */   public Long getLast_modify() {
/* 122 */     return this.last_modify;
/*     */   }
/*     */   public void setLast_modify(Long last_modify) {
/* 125 */     this.last_modify = last_modify;
/*     */   }
/*     */   public Integer getDisabled() {
/* 128 */     return this.disabled;
/*     */   }
/*     */   public void setDisabled(Integer disabled) {
/* 131 */     this.disabled = disabled;
/*     */   }
/*     */   public Integer getPoint() {
/* 134 */     return this.point;
/*     */   }
/*     */   public void setPoint(Integer point) {
/* 137 */     this.point = point;
/*     */   }
/*     */   public Integer getIstejia() {
/* 140 */     return this.istejia;
/*     */   }
/*     */   public void setIstejia(Integer istejia) {
/* 143 */     this.istejia = istejia;
/*     */   }
/*     */   public Integer getNo_discount() {
/* 146 */     return this.no_discount;
/*     */   }
/*     */   public void setNo_discount(Integer no_discount) {
/* 149 */     this.no_discount = no_discount;
/*     */   }
/*     */   public String getColor() {
/* 152 */     return this.color;
/*     */   }
/*     */   public void setColor(String color) {
/* 155 */     this.color = color;
/*     */   }
/*     */   public Integer getRealstore() {
/* 158 */     return this.realstore;
/*     */   }
/*     */   public void setRealstore(Integer realstore) {
/* 161 */     this.realstore = realstore;
/*     */   }
/*     */   public String getBrandname() {
/* 164 */     return this.brandname;
/*     */   }
/*     */   public void setBrandname(String brandname) {
/* 167 */     this.brandname = brandname;
/*     */   }
/*     */   public String getCatname() {
/* 170 */     return this.catname;
/*     */   }
/*     */   public void setCatname(String catname) {
/* 173 */     this.catname = catname;
/*     */   }
/*     */   public Double getSphere() {
/* 176 */     return this.sphere;
/*     */   }
/*     */   public void setSphere(Double sphere) {
/* 179 */     this.sphere = sphere;
/*     */   }
/*     */   public Double getCylinder() {
/* 182 */     return this.cylinder;
/*     */   }
/*     */   public void setCylinder(Double cylinder) {
/* 185 */     this.cylinder = cylinder;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.GoodsStores
 * JD-Core Version:    0.6.0
 */