/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class OrderItem
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8531790664258169824L;
/*     */   private Integer item_id;
/*     */   private Integer order_id;
/*     */   private Integer goods_id;
/*     */   private Integer product_id;
/*     */   private Integer num;
/*     */   private Integer ship_num;
/*     */   private String name;
/*     */   private String sn;
/*     */   private String image;
/*     */   private Integer store;
/*     */   private String addon;
/*     */   private int cat_id;
/*     */   private Double price;
/*     */   private int gainedpoint;
/*     */   private int state;
/*     */   private String change_goods_name;
/*     */   private Integer change_goods_id;
/*     */   private String unit;
/*     */   private String other;
/*     */ 
/*     */   public int getState()
/*     */   {
/*  48 */     return this.state;
/*     */   }
/*     */   public void setState(int state) {
/*  51 */     this.state = state;
/*     */   }
/*     */   public String getChange_goods_name() {
/*  54 */     return this.change_goods_name;
/*     */   }
/*     */   public void setChange_goods_name(String change_goods_name) {
/*  57 */     this.change_goods_name = change_goods_name;
/*     */   }
/*     */   public Integer getChange_goods_id() {
/*  60 */     return this.change_goods_id;
/*     */   }
/*     */   public void setChange_goods_id(Integer change_goods_id) {
/*  63 */     this.change_goods_id = change_goods_id;
/*     */   }
/*     */   public Integer getGoods_id() {
/*  66 */     return this.goods_id;
/*     */   }
/*     */   public void setGoods_id(Integer goods_id) {
/*  69 */     this.goods_id = goods_id;
/*     */   }
/*  73 */   @PrimaryKeyField
/*     */   public Integer getItem_id() { return this.item_id; }
/*     */ 
/*     */   public void setItem_id(Integer item_id) {
/*  76 */     this.item_id = item_id;
/*     */   }
/*     */   public String getName() {
/*  79 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  82 */     this.name = name;
/*     */   }
/*     */   public Integer getNum() {
/*  85 */     return this.num;
/*     */   }
/*     */   public void setNum(Integer num) {
/*  88 */     this.num = num;
/*     */   }
/*     */   public Integer getOrder_id() {
/*  91 */     return this.order_id;
/*     */   }
/*     */   public void setOrder_id(Integer order_id) {
/*  94 */     this.order_id = order_id;
/*     */   }
/*     */   public Integer getShip_num() {
/*  97 */     return this.ship_num;
/*     */   }
/*     */   public void setShip_num(Integer ship_num) {
/* 100 */     this.ship_num = ship_num;
/*     */   }
/*     */ 
/*     */   public Double getPrice()
/*     */   {
/* 105 */     return this.price;
/*     */   }
/*     */   public void setPrice(Double price) {
/* 108 */     this.price = price;
/*     */   }
/*     */   public int getGainedpoint() {
/* 111 */     return this.gainedpoint;
/*     */   }
/*     */   public void setGainedpoint(int gainedpoint) {
/* 114 */     this.gainedpoint = gainedpoint;
/*     */   }
/*     */   public String getSn() {
/* 117 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/* 120 */     this.sn = sn;
/*     */   }
/*     */   @NotDbField
/*     */   public Integer getStore() {
/* 125 */     return this.store;
/*     */   }
/*     */   public void setStore(Integer store) {
/* 128 */     this.store = store;
/*     */   }
/*     */   public String getAddon() {
/* 131 */     return this.addon;
/*     */   }
/*     */   public void setAddon(String addon) {
/* 134 */     this.addon = addon;
/*     */   }
/*     */ 
/*     */   public int getCat_id() {
/* 138 */     return this.cat_id;
/*     */   }
/*     */   public void setCat_id(int cat_id) {
/* 141 */     this.cat_id = cat_id;
/*     */   }
/*     */   @NotDbField
/*     */   public String getOther() {
/* 146 */     return this.other;
/*     */   }
/*     */   public void setOther(String other) {
/* 149 */     this.other = other;
/*     */   }
/*     */ 
/*     */   public String getImage()
/*     */   {
/* 156 */     if (!StringUtil.isEmpty(this.image)) {
/* 157 */       this.image = UploadUtil.replacePath(this.image);
/*     */     }
/*     */ 
/* 160 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void setImage(String image) {
/* 164 */     this.image = image;
/*     */   }
/*     */   public Integer getProduct_id() {
/* 167 */     return this.product_id;
/*     */   }
/*     */   public void setProduct_id(Integer product_id) {
/* 170 */     this.product_id = product_id;
/*     */   }
/*     */   public String getUnit() {
/* 173 */     return this.unit;
/*     */   }
/*     */   public void setUnit(String unit) {
/* 176 */     this.unit = unit;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.OrderItem
 * JD-Core Version:    0.6.0
 */