/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class PointHistory
/*     */ {
/*     */   private int id;
/*     */   private int member_id;
/*     */   private int point;
/*     */   private Long time;
/*     */   private String reason;
/*     */   private String cnreason;
/*     */   private Integer related_id;
/*     */   private int type;
/*     */   private String operator;
/*     */   private Integer mp;
/*     */   private int point_type;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getId()
/*     */   {
/*  30 */     return this.id;
/*     */   }
/*     */   public void setId(int id) {
/*  33 */     this.id = id;
/*     */   }
/*     */   public int getMember_id() {
/*  36 */     return this.member_id;
/*     */   }
/*     */   public void setMember_id(int memberId) {
/*  39 */     this.member_id = memberId;
/*     */   }
/*     */   public int getPoint() {
/*  42 */     return this.point;
/*     */   }
/*     */   public void setPoint(int point) {
/*  45 */     this.point = point;
/*     */   }
/*     */   public Long getTime() {
/*  48 */     return this.time;
/*     */   }
/*     */   public void setTime(Long time) {
/*  51 */     this.time = time;
/*     */   }
/*     */   public String getReason() {
/*  54 */     return this.reason;
/*     */   }
/*     */   public void setReason(String reason) {
/*  57 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   public int getType() {
/*  61 */     return this.type;
/*     */   }
/*     */   public void setType(int type) {
/*  64 */     this.type = type;
/*     */   }
/*     */   public String getOperator() {
/*  67 */     return this.operator;
/*     */   }
/*     */   public void setOperator(String operator) {
/*  70 */     this.operator = operator;
/*     */   }
/*  74 */   @NotDbField
/*     */   public String getCnreason() { if (this.reason.equals("order_pay_use")) this.cnreason = "订单消费积分";
/*  75 */     if (this.reason.equals("order_pay_get")) this.cnreason = "订单获得积分";
/*  76 */     if (this.reason.equals("order_refund_use")) this.cnreason = "退还订单消费积分";
/*  77 */     if (this.reason.equals("order_refund_get")) this.cnreason = "扣掉订单所得积分";
/*  78 */     if (this.reason.equals("order_cancel_refund_consume_gift")) this.cnreason = "Score deduction for gifts refunded for order cancelling.";
/*  79 */     if (this.reason.equals("exchange_coupon")) this.cnreason = "兑换优惠券";
/*  80 */     if (this.reason.equals("operator_adjust")) this.cnreason = "管理员改变积分";
/*  81 */     if (this.reason.equals("consume_gift")) this.cnreason = "积分换赠品";
/*  82 */     if (this.reason.equals("recommend_member")) this.cnreason = "发表评论奖励积分";
/*  83 */     return this.cnreason; }
/*     */ 
/*     */   public void setCnreason(String cnreason) {
/*  86 */     this.cnreason = cnreason;
/*     */   }
/*     */   public Integer getRelated_id() {
/*  89 */     return this.related_id;
/*     */   }
/*     */   public void setRelated_id(Integer relatedId) {
/*  92 */     this.related_id = relatedId;
/*     */   }
/*     */   public Integer getMp() {
/*  95 */     return this.mp;
/*     */   }
/*     */   public void setMp(Integer mp) {
/*  98 */     this.mp = mp;
/*     */   }
/*     */   public int getPoint_type() {
/* 101 */     return this.point_type;
/*     */   }
/*     */   public void setPoint_type(int point_type) {
/* 104 */     this.point_type = point_type;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PointHistory
 * JD-Core Version:    0.6.0
 */