/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Allocation
/*    */ {
/*    */   private int orderid;
/*    */   private int shipDepotId;
/*    */   private List<AllocationItem> itemList;
/*    */ 
/*    */   public int getOrderid()
/*    */   {
/* 16 */     return this.orderid;
/*    */   }
/*    */   public void setOrderid(int orderid) {
/* 19 */     this.orderid = orderid;
/*    */   }
/*    */   public int getShipDepotId() {
/* 22 */     return this.shipDepotId;
/*    */   }
/*    */   public void setShipDepotId(int shipDepotId) {
/* 25 */     this.shipDepotId = shipDepotId;
/*    */   }
/*    */   public List<AllocationItem> getItemList() {
/* 28 */     return this.itemList;
/*    */   }
/*    */   public void setItemList(List<AllocationItem> itemList) {
/* 31 */     this.itemList = itemList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Allocation
 * JD-Core Version:    0.6.0
 */