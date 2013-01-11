/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class MemberOrderItem
/*    */ {
/*    */   private int id;
/*    */   private Integer member_id;
/*    */   private Integer goods_id;
/*    */   private Integer order_id;
/*    */   private Integer item_id;
/*    */   private Integer commented;
/*    */   private Long comment_time;
/*    */ 
/*    */   public MemberOrderItem()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MemberOrderItem(int id, Integer member_id, Integer goods_id, Integer order_id, Integer item_id, Integer commented, Long comment_time)
/*    */   {
/* 22 */     this.id = id;
/* 23 */     this.member_id = member_id;
/* 24 */     this.goods_id = goods_id;
/* 25 */     this.order_id = order_id;
/* 26 */     this.item_id = item_id;
/* 27 */     this.commented = commented;
/* 28 */     this.comment_time = comment_time;
/*    */   }
/* 32 */   @PrimaryKeyField
/*    */   public int getId() { return this.id; }
/*    */ 
/*    */   public void setId(int id) {
/* 35 */     this.id = id;
/*    */   }
/*    */   public Integer getMember_id() {
/* 38 */     return this.member_id;
/*    */   }
/*    */   public void setMember_id(Integer member_id) {
/* 41 */     this.member_id = member_id;
/*    */   }
/*    */   public Integer getOrder_id() {
/* 44 */     return this.order_id;
/*    */   }
/*    */   public void setOrder_id(Integer order_id) {
/* 47 */     this.order_id = order_id;
/*    */   }
/*    */   public Integer getItem_id() {
/* 50 */     return this.item_id;
/*    */   }
/*    */   public void setItem_id(Integer item_id) {
/* 53 */     this.item_id = item_id;
/*    */   }
/*    */   public Integer getCommented() {
/* 56 */     return this.commented;
/*    */   }
/*    */   public void setCommented(Integer commented) {
/* 59 */     this.commented = commented;
/*    */   }
/*    */   public Long getComment_time() {
/* 62 */     return this.comment_time;
/*    */   }
/*    */   public void setComment_time(Long comment_time) {
/* 65 */     this.comment_time = comment_time;
/*    */   }
/*    */ 
/*    */   public Integer getGoods_id() {
/* 69 */     return this.goods_id;
/*    */   }
/*    */ 
/*    */   public void setGoods_id(Integer goods_id) {
/* 73 */     this.goods_id = goods_id;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.MemberOrderItem
 * JD-Core Version:    0.6.0
 */