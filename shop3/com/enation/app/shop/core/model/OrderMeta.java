/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class OrderMeta
/*    */ {
/*    */   private Integer metaid;
/*    */   private int orderid;
/*    */   private String meta_key;
/*    */   private String meta_value;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getMetaid()
/*    */   {
/* 19 */     return this.metaid;
/*    */   }
/*    */ 
/*    */   public void setMetaid(Integer metaid) {
/* 23 */     this.metaid = metaid;
/*    */   }
/*    */ 
/*    */   public int getOrderid() {
/* 27 */     return this.orderid;
/*    */   }
/*    */ 
/*    */   public void setOrderid(int orderid) {
/* 31 */     this.orderid = orderid;
/*    */   }
/*    */ 
/*    */   public String getMeta_key() {
/* 35 */     return this.meta_key;
/*    */   }
/*    */ 
/*    */   public void setMeta_key(String meta_key) {
/* 39 */     this.meta_key = meta_key;
/*    */   }
/*    */ 
/*    */   public String getMeta_value() {
/* 43 */     return this.meta_value;
/*    */   }
/*    */ 
/*    */   public void setMeta_value(String meta_value) {
/* 47 */     this.meta_value = meta_value;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.OrderMeta
 * JD-Core Version:    0.6.0
 */