/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Product
/*     */ {
/*     */   private Integer product_id;
/*     */   private Integer goods_id;
/*     */   private String name;
/*     */   private String sn;
/*     */   private Integer store;
/*     */   private Double price;
/*     */   private Double cost;
/*     */   private Double weight;
/*     */   private String specs;
/*     */   private List<GoodsLvPrice> goodsLvPrices;
/*     */   private List<SpecValue> specList;
/*     */ 
/*     */   public Product()
/*     */   {
/*  31 */     this.specList = new ArrayList();
/*     */   }
/*     */   @NotDbField
/*     */   public List<SpecValue> getSpecList() {
/*  36 */     return this.specList;
/*     */   }
/*     */   public void setSpecList(List<SpecValue> specList) {
/*  39 */     this.specList = specList;
/*     */   }
/*     */ 
/*     */   public void addSpec(SpecValue spec)
/*     */   {
/*  44 */     this.specList.add(spec);
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getSpecsvIdJson()
/*     */   {
/*  53 */     String json = "[";
/*  54 */     int i = 0;
/*  55 */     for (SpecValue value : this.specList) {
/*  56 */       if (i != 0) json = json + ",";
/*  57 */       json = json + value.getSpec_value_id();
/*  58 */       i++;
/*     */     }
/*  60 */     json = json + "]";
/*  61 */     return json;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getProduct_id() {
/*  66 */     return this.product_id;
/*     */   }
/*     */   public void setProduct_id(Integer productId) {
/*  69 */     this.product_id = productId;
/*     */   }
/*     */   public Integer getGoods_id() {
/*  72 */     return this.goods_id;
/*     */   }
/*     */   public void setGoods_id(Integer goodsId) {
/*  75 */     this.goods_id = goodsId;
/*     */   }
/*     */   public String getSn() {
/*  78 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/*  81 */     this.sn = sn;
/*     */   }
/*     */   public Integer getStore() {
/*  84 */     return this.store;
/*     */   }
/*     */   public void setStore(Integer store) {
/*  87 */     this.store = store;
/*     */   }
/*     */   public Double getPrice() {
/*  90 */     return this.price;
/*     */   }
/*     */   public void setPrice(Double price) {
/*  93 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public Double getCost() {
/*  97 */     return this.cost;
/*     */   }
/*     */ 
/*     */   public void setCost(Double cost) {
/* 101 */     this.cost = cost;
/*     */   }
/*     */ 
/*     */   public Double getWeight() {
/* 105 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(Double weight) {
/* 109 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */   public String getSpecs() {
/* 113 */     return this.specs;
/*     */   }
/*     */ 
/*     */   public void setSpecs(String specs) {
/* 117 */     this.specs = specs;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 121 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 125 */     this.name = name;
/*     */   }
/*     */   @NotDbField
/*     */   public List<GoodsLvPrice> getGoodsLvPrices() {
/* 130 */     return this.goodsLvPrices;
/*     */   }
/*     */ 
/*     */   public void setGoodsLvPrices(List<GoodsLvPrice> goodsLvPrices) {
/* 134 */     this.goodsLvPrices = goodsLvPrices;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Product
 * JD-Core Version:    0.6.0
 */