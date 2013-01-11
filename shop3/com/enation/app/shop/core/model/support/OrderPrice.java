/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OrderPrice
/*    */ {
/*    */   private Double originalPrice;
/*    */   private Double goodsPrice;
/*    */   private Double orderPrice;
/*    */   private Double discountPrice;
/*    */   private Double shippingPrice;
/*    */   private Double protectPrice;
/*    */   private Double needPayMoney;
/*    */   private Double weight;
/*    */   private Integer point;
/*    */   private Map<String, Object> discountItem;
/*    */ 
/*    */   public OrderPrice()
/*    */   {
/* 28 */     this.discountItem = new HashMap();
/*    */   }
/*    */ 
/*    */   public Double getOriginalPrice() {
/* 32 */     return this.originalPrice;
/*    */   }
/*    */   public void setOriginalPrice(Double originalPrice) {
/* 35 */     this.originalPrice = originalPrice;
/*    */   }
/*    */   public Double getGoodsPrice() {
/* 38 */     return this.goodsPrice;
/*    */   }
/*    */   public void setGoodsPrice(Double goodsPrice) {
/* 41 */     this.goodsPrice = goodsPrice;
/*    */   }
/*    */   public Double getOrderPrice() {
/* 44 */     return this.orderPrice;
/*    */   }
/*    */   public void setOrderPrice(Double orderPrice) {
/* 47 */     this.orderPrice = orderPrice;
/*    */   }
/*    */   public Double getDiscountPrice() {
/* 50 */     this.discountPrice = Double.valueOf(this.discountPrice == null ? 0.0D : this.discountPrice.doubleValue());
/* 51 */     return this.discountPrice;
/*    */   }
/*    */   public void setDiscountPrice(Double discountPrice) {
/* 54 */     this.discountPrice = discountPrice;
/*    */   }
/*    */   public Integer getPoint() {
/* 57 */     return this.point;
/*    */   }
/*    */   public void setPoint(Integer point) {
/* 60 */     this.point = point;
/*    */   }
/*    */   public Double getProtectPrice() {
/* 63 */     return this.protectPrice;
/*    */   }
/*    */   public void setProtectPrice(Double protectPrice) {
/* 66 */     this.protectPrice = protectPrice;
/*    */   }
/*    */   public Double getWeight() {
/* 69 */     return this.weight;
/*    */   }
/*    */   public void setWeight(Double weight) {
/* 72 */     this.weight = weight;
/*    */   }
/*    */   public Double getShippingPrice() {
/* 75 */     return this.shippingPrice;
/*    */   }
/*    */   public void setShippingPrice(Double shippingPrice) {
/* 78 */     this.shippingPrice = shippingPrice;
/*    */   }
/*    */   public Map<String, Object> getDiscountItem() {
/* 81 */     return this.discountItem;
/*    */   }
/*    */   public void setDiscountItem(Map<String, Object> discountItem) {
/* 84 */     this.discountItem = discountItem;
/*    */   }
/*    */ 
/*    */   public Double getNeedPayMoney() {
/* 88 */     return this.needPayMoney;
/*    */   }
/*    */   public void setNeedPayMoney(Double needPayMoney) {
/* 91 */     this.needPayMoney = needPayMoney;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.OrderPrice
 * JD-Core Version:    0.6.0
 */