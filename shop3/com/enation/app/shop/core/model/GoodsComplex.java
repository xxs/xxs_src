/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class GoodsComplex
/*    */   implements Serializable
/*    */ {
/*    */   private int goods_1;
/*    */   private int goods_2;
/*    */   private String manual;
/*    */   private int rate;
/*    */ 
/*    */   public int getGoods_1()
/*    */   {
/* 17 */     return this.goods_1;
/*    */   }
/*    */ 
/*    */   public void setGoods_1(int goods_1) {
/* 21 */     this.goods_1 = goods_1;
/*    */   }
/*    */ 
/*    */   public int getGoods_2() {
/* 25 */     return this.goods_2;
/*    */   }
/*    */ 
/*    */   public void setGoods_2(int goods_2) {
/* 29 */     this.goods_2 = goods_2;
/*    */   }
/*    */ 
/*    */   public String getManual() {
/* 33 */     return this.manual;
/*    */   }
/*    */ 
/*    */   public void setManual(String manual) {
/* 37 */     this.manual = manual;
/*    */   }
/*    */ 
/*    */   public int getRate() {
/* 41 */     return this.rate;
/*    */   }
/*    */ 
/*    */   public void setRate(int rate) {
/* 45 */     this.rate = rate;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.GoodsComplex
 * JD-Core Version:    0.6.0
 */