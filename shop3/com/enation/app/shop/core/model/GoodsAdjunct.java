/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class GoodsAdjunct
/*    */   implements Serializable
/*    */ {
/*    */   private int adjunct_id;
/*    */   private int goods_id;
/*    */   private String adjunct_name;
/*    */   private int min_num;
/*    */   private int max_num;
/*    */   private String set_price;
/*    */   private Double price;
/*    */   private String items;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public int getAdjunct_id()
/*    */   {
/* 23 */     return this.adjunct_id;
/*    */   }
/*    */   public void setAdjunct_id(int adjunctId) {
/* 26 */     this.adjunct_id = adjunctId;
/*    */   }
/*    */   public int getGoods_id() {
/* 29 */     return this.goods_id;
/*    */   }
/*    */   public void setGoods_id(int goodsId) {
/* 32 */     this.goods_id = goodsId;
/*    */   }
/*    */   public String getAdjunct_name() {
/* 35 */     return this.adjunct_name;
/*    */   }
/*    */   public void setAdjunct_name(String adjunctName) {
/* 38 */     this.adjunct_name = adjunctName;
/*    */   }
/*    */   public int getMin_num() {
/* 41 */     return this.min_num;
/*    */   }
/*    */   public void setMin_num(int minNum) {
/* 44 */     this.min_num = minNum;
/*    */   }
/*    */   public int getMax_num() {
/* 47 */     return this.max_num;
/*    */   }
/*    */   public void setMax_num(int maxNum) {
/* 50 */     this.max_num = maxNum;
/*    */   }
/*    */   public String getSet_price() {
/* 53 */     return this.set_price;
/*    */   }
/*    */   public void setSet_price(String setPrice) {
/* 56 */     this.set_price = setPrice;
/*    */   }
/*    */   public Double getPrice() {
/* 59 */     return this.price;
/*    */   }
/*    */   public void setPrice(Double price) {
/* 62 */     this.price = price;
/*    */   }
/*    */   public String getItems() {
/* 65 */     return this.items;
/*    */   }
/*    */   public void setItems(String items) {
/* 68 */     this.items = items;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.GoodsAdjunct
 * JD-Core Version:    0.6.0
 */