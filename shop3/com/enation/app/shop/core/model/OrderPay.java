/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ public class OrderPay
/*    */ {
/*    */   private int payid;
/*    */   private int order_id;
/*    */   private String bank;
/*    */   private String paydate;
/*    */   private String sn;
/*    */   private Double paymoney;
/*    */   private String remark;
/*    */   private long add_time;
/*    */   private int confirmed;
/*    */ 
/*    */   public int getPayid()
/*    */   {
/* 16 */     return this.payid;
/*    */   }
/*    */   public void setPayid(int payid) {
/* 19 */     this.payid = payid;
/*    */   }
/*    */   public int getOrder_id() {
/* 22 */     return this.order_id;
/*    */   }
/*    */   public void setOrder_id(int order_id) {
/* 25 */     this.order_id = order_id;
/*    */   }
/*    */   public String getBank() {
/* 28 */     return this.bank;
/*    */   }
/*    */   public void setBank(String bank) {
/* 31 */     this.bank = bank;
/*    */   }
/*    */   public String getPaydate() {
/* 34 */     return this.paydate;
/*    */   }
/*    */   public void setPaydate(String paydate) {
/* 37 */     this.paydate = paydate;
/*    */   }
/*    */   public String getSn() {
/* 40 */     return this.sn;
/*    */   }
/*    */   public void setSn(String sn) {
/* 43 */     this.sn = sn;
/*    */   }
/*    */   public Double getPaymoney() {
/* 46 */     return this.paymoney;
/*    */   }
/*    */   public void setPaymoney(Double paymoney) {
/* 49 */     this.paymoney = paymoney;
/*    */   }
/*    */   public String getRemark() {
/* 52 */     return this.remark;
/*    */   }
/*    */   public void setRemark(String remark) {
/* 55 */     this.remark = remark;
/*    */   }
/*    */   public long getAdd_time() {
/* 58 */     return this.add_time;
/*    */   }
/*    */   public void setAdd_time(long add_time) {
/* 61 */     this.add_time = add_time;
/*    */   }
/*    */   public int getConfirmed() {
/* 64 */     return this.confirmed;
/*    */   }
/*    */   public void setConfirmed(int confirmed) {
/* 67 */     this.confirmed = confirmed;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.OrderPay
 * JD-Core Version:    0.6.0
 */