/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.DynamicField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Cart extends DynamicField
/*    */ {
/*    */   private Integer cart_id;
/*    */   private Integer goods_id;
/*    */   private Integer product_id;
/*    */   private Integer num;
/*    */   private Double weight;
/*    */   private String session_id;
/*    */   private Integer itemtype;
/*    */   private String name;
/*    */   private Double price;
/*    */   private String addon;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getCart_id()
/*    */   {
/* 26 */     return this.cart_id;
/*    */   }
/*    */   public void setCart_id(Integer cartId) {
/* 29 */     this.cart_id = cartId;
/*    */   }
/*    */   public Integer getProduct_id() {
/* 32 */     return this.product_id;
/*    */   }
/*    */   public void setProduct_id(Integer productId) {
/* 35 */     this.product_id = productId;
/*    */   }
/*    */   public Integer getNum() {
/* 38 */     return this.num;
/*    */   }
/*    */   public void setNum(Integer num) {
/* 41 */     this.num = num;
/*    */   }
/*    */   public String getSession_id() {
/* 44 */     return this.session_id;
/*    */   }
/*    */   public void setSession_id(String sessionId) {
/* 47 */     this.session_id = sessionId;
/*    */   }
/*    */   public Integer getItemtype() {
/* 50 */     return this.itemtype;
/*    */   }
/*    */   public void setItemtype(Integer itemtype) {
/* 53 */     this.itemtype = itemtype;
/*    */   }
/*    */   public Double getWeight() {
/* 56 */     return this.weight;
/*    */   }
/*    */   public void setWeight(Double weight) {
/* 59 */     this.weight = weight;
/*    */   }
/*    */   public String getName() {
/* 62 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 65 */     this.name = name;
/*    */   }
/*    */   public Double getPrice() {
/* 68 */     return this.price;
/*    */   }
/*    */   public void setPrice(Double price) {
/* 71 */     this.price = price;
/*    */   }
/*    */   public String getAddon() {
/* 74 */     return this.addon;
/*    */   }
/*    */   public void setAddon(String addon) {
/* 77 */     this.addon = addon;
/*    */   }
/*    */   public Integer getGoods_id() {
/* 80 */     return this.goods_id;
/*    */   }
/*    */   public void setGoods_id(Integer goods_id) {
/* 83 */     this.goods_id = goods_id;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Cart
 * JD-Core Version:    0.6.0
 */