/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PaymentLog
/*     */   implements Serializable
/*     */ {
/*     */   private Integer payment_id;
/*     */   private int order_id;
/*     */   private String order_sn;
/*     */   private Integer member_id;
/*     */   private String pay_user;
/*     */   private Double money;
/*     */   private long pay_date;
/*     */   private String pay_method;
/*     */   private String remark;
/*     */   private int type;
/*     */   private Integer status;
/*     */   private Long create_time;
/*     */   private String sn;
/*     */ 
/*     */   public Integer getPayment_id()
/*     */   {
/*  32 */     return this.payment_id;
/*     */   }
/*     */ 
/*     */   public void setPayment_id(Integer payment_id) {
/*  36 */     this.payment_id = payment_id;
/*     */   }
/*     */ 
/*     */   public String getOrder_sn() {
/*  40 */     return this.order_sn;
/*     */   }
/*     */ 
/*     */   public void setOrder_sn(String order_sn) {
/*  44 */     this.order_sn = order_sn;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/*  48 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer member_id) {
/*  52 */     this.member_id = member_id;
/*     */   }
/*     */ 
/*     */   public String getPay_user() {
/*  56 */     return this.pay_user;
/*     */   }
/*     */ 
/*     */   public void setPay_user(String pay_user) {
/*  60 */     this.pay_user = pay_user;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/*  64 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(Double money) {
/*  68 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public String getPay_method() {
/*  72 */     return this.pay_method;
/*     */   }
/*     */ 
/*     */   public void setPay_method(String pay_method) {
/*  76 */     this.pay_method = pay_method;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  80 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  84 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public Integer getStatus()
/*     */   {
/*  89 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status) {
/*  93 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Long getCreate_time() {
/*  97 */     return this.create_time;
/*     */   }
/*     */ 
/*     */   public void setCreate_time(Long create_time) {
/* 101 */     this.create_time = create_time;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 105 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 109 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public long getPay_date() {
/* 113 */     return this.pay_date;
/*     */   }
/*     */ 
/*     */   public void setPay_date(long pay_date) {
/* 117 */     this.pay_date = pay_date;
/*     */   }
/*     */ 
/*     */   public int getOrder_id() {
/* 121 */     return this.order_id;
/*     */   }
/*     */ 
/*     */   public void setOrder_id(int order_id) {
/* 125 */     this.order_id = order_id;
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 129 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 133 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PaymentLog
 * JD-Core Version:    0.6.0
 */