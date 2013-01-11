/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Delivery;
/*    */ 
/*    */ public class DeliveryDTO
/*    */ {
/*    */   private Delivery delivery;
/*    */   private String reason;
/*    */   private String[] sn;
/*    */   private String[] name;
/*    */   private String[] num;
/*    */   private Integer[] item_id;
/*    */ 
/*    */   public String[] getName()
/*    */   {
/* 15 */     return this.name;
/*    */   }
/*    */   public void setName(String[] name) {
/* 18 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String[] getNum() {
/* 22 */     return this.num;
/*    */   }
/*    */   public void setNum(String[] num) {
/* 25 */     this.num = num;
/*    */   }
/*    */   public String[] getSn() {
/* 28 */     return this.sn;
/*    */   }
/*    */   public void setSn(String[] sn) {
/* 31 */     this.sn = sn;
/*    */   }
/*    */ 
/*    */   public Delivery getDelivery() {
/* 35 */     return this.delivery;
/*    */   }
/*    */   public void setDelivery(Delivery delivery) {
/* 38 */     this.delivery = delivery;
/*    */   }
/*    */   public Integer[] getItem_id() {
/* 41 */     return this.item_id;
/*    */   }
/*    */   public void setItem_id(Integer[] item_id) {
/* 44 */     this.item_id = item_id;
/*    */   }
/*    */   public String getReason() {
/* 47 */     return this.reason;
/*    */   }
/*    */   public void setReason(String reason) {
/* 50 */     this.reason = reason;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.DeliveryDTO
 * JD-Core Version:    0.6.0
 */