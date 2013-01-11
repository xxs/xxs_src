/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class OrderLog
/*    */ {
/*    */   private Integer log_id;
/*    */   private Integer order_id;
/*    */   private Integer op_id;
/*    */   private String op_name;
/*    */   private String message;
/*    */   private Long op_time;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getLog_id()
/*    */   {
/* 20 */     return this.log_id;
/*    */   }
/*    */   public void setLog_id(Integer logId) {
/* 23 */     this.log_id = logId;
/*    */   }
/*    */   public Integer getOrder_id() {
/* 26 */     return this.order_id;
/*    */   }
/*    */   public void setOrder_id(Integer orderId) {
/* 29 */     this.order_id = orderId;
/*    */   }
/*    */   public Integer getOp_id() {
/* 32 */     return this.op_id;
/*    */   }
/*    */   public void setOp_id(Integer opId) {
/* 35 */     this.op_id = opId;
/*    */   }
/*    */   public String getOp_name() {
/* 38 */     return this.op_name;
/*    */   }
/*    */   public void setOp_name(String opName) {
/* 41 */     this.op_name = opName;
/*    */   }
/*    */   public String getMessage() {
/* 44 */     return this.message;
/*    */   }
/*    */   public void setMessage(String message) {
/* 47 */     this.message = message;
/*    */   }
/*    */   public Long getOp_time() {
/* 50 */     return this.op_time;
/*    */   }
/*    */   public void setOp_time(Long opTime) {
/* 53 */     this.op_time = opTime;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.OrderLog
 * JD-Core Version:    0.6.0
 */