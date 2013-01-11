/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ 
/*    */ public class AllocationItem
/*    */ {
/*    */   private Integer allocationid;
/*    */   private int itemid;
/*    */   private int cat_id;
/*    */   private int orderid;
/*    */   private int depotid;
/*    */   private int goodsid;
/*    */   private int productid;
/*    */   private int num;
/*    */   private String other;
/*    */   private int iscmpl;
/*    */ 
/*    */   public int getIscmpl()
/*    */   {
/* 24 */     return this.iscmpl;
/*    */   }
/*    */   public void setIscmpl(int iscmpl) {
/* 27 */     this.iscmpl = iscmpl;
/*    */   }
/*    */   public Integer getAllocationid() {
/* 30 */     return this.allocationid;
/*    */   }
/*    */   public void setAllocationid(Integer allocationid) {
/* 33 */     this.allocationid = allocationid;
/*    */   }
/*    */   public int getOrderid() {
/* 36 */     return this.orderid;
/*    */   }
/*    */   public void setOrderid(int orderid) {
/* 39 */     this.orderid = orderid;
/*    */   }
/*    */   public int getDepotid() {
/* 42 */     return this.depotid;
/*    */   }
/*    */   public void setDepotid(int depotid) {
/* 45 */     this.depotid = depotid;
/*    */   }
/*    */   public int getGoodsid() {
/* 48 */     return this.goodsid;
/*    */   }
/*    */   public void setGoodsid(int goodsid) {
/* 51 */     this.goodsid = goodsid;
/*    */   }
/*    */   public int getProductid() {
/* 54 */     return this.productid;
/*    */   }
/*    */   public void setProductid(int productid) {
/* 57 */     this.productid = productid;
/*    */   }
/*    */   public int getNum() {
/* 60 */     return this.num;
/*    */   }
/*    */   public void setNum(int num) {
/* 63 */     this.num = num;
/*    */   }
/* 67 */   @NotDbField
/*    */   public int getCat_id() { return this.cat_id; }
/*    */ 
/*    */   public void setCat_id(int cat_id) {
/* 70 */     this.cat_id = cat_id;
/*    */   }
/*    */   public String getOther() {
/* 73 */     return this.other;
/*    */   }
/*    */   public void setOther(String other) {
/* 76 */     this.other = other;
/*    */   }
/*    */   public int getItemid() {
/* 79 */     return this.itemid;
/*    */   }
/*    */   public void setItemid(int itemid) {
/* 82 */     this.itemid = itemid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.AllocationItem
 * JD-Core Version:    0.6.0
 */