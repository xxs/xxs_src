/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Goods;
/*    */ 
/*    */ public class GoodsDTO
/*    */ {
/*    */   private Goods goods;
/*    */   private String[] photos;
/*    */ 
/*    */   public Goods getGoods()
/*    */   {
/*  9 */     return this.goods;
/*    */   }
/*    */   public void setGoods(Goods goods) {
/* 12 */     this.goods = goods;
/*    */   }
/*    */   public String[] getPhotos() {
/* 15 */     return this.photos;
/*    */   }
/*    */   public void setPhotos(String[] photos) {
/* 18 */     this.photos = photos;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsDTO
 * JD-Core Version:    0.6.0
 */