/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Favorite
/*    */   implements Serializable
/*    */ {
/*    */   private int favorite_id;
/*    */   private int member_id;
/*    */   private int goods_id;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public int getFavorite_id()
/*    */   {
/* 19 */     return this.favorite_id;
/*    */   }
/*    */ 
/*    */   public void setFavorite_id(int favoriteId) {
/* 23 */     this.favorite_id = favoriteId;
/*    */   }
/*    */ 
/*    */   public int getMember_id() {
/* 27 */     return this.member_id;
/*    */   }
/*    */ 
/*    */   public void setMember_id(int memberId) {
/* 31 */     this.member_id = memberId;
/*    */   }
/*    */ 
/*    */   public int getGoods_id() {
/* 35 */     return this.goods_id;
/*    */   }
/*    */ 
/*    */   public void setGoods_id(int goodsId) {
/* 39 */     this.goods_id = goodsId;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Favorite
 * JD-Core Version:    0.6.0
 */