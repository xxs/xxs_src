/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ 
/*     */ public class RefundLog
/*     */ {
/*     */   private Integer refund_id;
/*     */   private int order_id;
/*     */   private String order_sn;
/*     */   private Integer member_id;
/*     */   private int type;
/*     */   private String pay_method;
/*     */   private String pay_user;
/*     */   private String account;
/*     */   private Double money;
/*     */   private long pay_date;
/*     */   private String remark;
/*     */   private String op_user;
/*     */   private Long create_time;
/*     */   private String sn;
/*     */   private String pay_date_str;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getRefund_id()
/*     */   {
/*  43 */     return this.refund_id;
/*     */   }
/*     */   public void setRefund_id(Integer refundId) {
/*  46 */     this.refund_id = refundId;
/*     */   }
/*     */   public int getOrder_id() {
/*  49 */     return this.order_id;
/*     */   }
/*     */   public void setOrder_id(int order_id) {
/*  52 */     this.order_id = order_id;
/*     */   }
/*     */   public String getOrder_sn() {
/*  55 */     return this.order_sn;
/*     */   }
/*     */   public void setOrder_sn(String order_sn) {
/*  58 */     this.order_sn = order_sn;
/*     */   }
/*     */   public Integer getMember_id() {
/*  61 */     return this.member_id;
/*     */   }
/*     */   public void setMember_id(Integer member_id) {
/*  64 */     this.member_id = member_id;
/*     */   }
/*     */   public int getType() {
/*  67 */     return this.type;
/*     */   }
/*     */   public void setType(int type) {
/*  70 */     this.type = type;
/*     */   }
/*     */   public String getPay_method() {
/*  73 */     return this.pay_method;
/*     */   }
/*     */   public void setPay_method(String pay_method) {
/*  76 */     this.pay_method = pay_method;
/*     */   }
/*     */   public String getPay_user() {
/*  79 */     return this.pay_user;
/*     */   }
/*     */   public void setPay_user(String pay_user) {
/*  82 */     this.pay_user = pay_user;
/*     */   }
/*     */   public String getAccount() {
/*  85 */     return this.account;
/*     */   }
/*     */   public void setAccount(String account) {
/*  88 */     this.account = account;
/*     */   }
/*     */   public Double getMoney() {
/*  91 */     return this.money;
/*     */   }
/*     */   public void setMoney(Double money) {
/*  94 */     this.money = money;
/*     */   }
/*     */   public long getPay_date() {
/*  97 */     return this.pay_date;
/*     */   }
/*     */   public void setPay_date(long pay_date) {
/* 100 */     this.pay_date = pay_date;
/*     */   }
/*     */   public String getRemark() {
/* 103 */     return this.remark;
/*     */   }
/*     */   public void setRemark(String remark) {
/* 106 */     this.remark = remark;
/*     */   }
/*     */   public String getOp_user() {
/* 109 */     return this.op_user;
/*     */   }
/*     */   public void setOp_user(String op_user) {
/* 112 */     this.op_user = op_user;
/*     */   }
/*     */   public Long getCreate_time() {
/* 115 */     return this.create_time;
/*     */   }
/*     */   public void setCreate_time(Long create_time) {
/* 118 */     this.create_time = create_time;
/*     */   }
/*     */   public String getSn() {
/* 121 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/* 124 */     this.sn = sn;
/*     */   }
/* 128 */   @NotDbField
/*     */   public String getPay_date_str() { return this.pay_date_str; }
/*     */ 
/*     */   public void setPay_date_str(String pay_date_str)
/*     */   {
/* 132 */     this.pay_date_str = pay_date_str;
/*     */ 
/* 134 */     if (!StringUtil.isEmpty(pay_date_str))
/* 135 */       this.pay_date = DateUtil.getDatelineLong(pay_date_str);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.RefundLog
 * JD-Core Version:    0.6.0
 */