/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PackageProduct
/*    */   implements Serializable
/*    */ {
/*    */   private int product_id;
/*    */   private int goods_id;
/*    */   private Double discount;
/*    */   private int pkgnum;
/*    */ 
/*    */   public int getProduct_id()
/*    */   {
/* 15 */     return this.product_id;
/*    */   }
/*    */   public void setProduct_id(int productId) {
/* 18 */     this.product_id = productId;
/*    */   }
/*    */   public int getGoods_id() {
/* 21 */     return this.goods_id;
/*    */   }
/*    */   public void setGoods_id(int goodsId) {
/* 24 */     this.goods_id = goodsId;
/*    */   }
/*    */   public Double getDiscount() {
/* 27 */     return this.discount;
/*    */   }
/*    */   public void setDiscount(Double discount) {
/* 30 */     this.discount = discount;
/*    */   }
/*    */   public int getPkgnum() {
/* 33 */     return this.pkgnum;
/*    */   }
/*    */   public void setPkgnum(int pkgnum) {
/* 36 */     this.pkgnum = pkgnum;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PackageProduct
 * JD-Core Version:    0.6.0
 */