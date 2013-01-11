/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AdvanceLogs
/*     */   implements Serializable
/*     */ {
/*     */   private int log_id;
/*     */   private int member_id;
/*     */   private Double money;
/*     */   private String message;
/*     */   private Long mtime;
/*     */   private int payment_id;
/*     */   private int order_id;
/*     */   private String paymethod;
/*     */   private String memo;
/*     */   private Double import_money;
/*     */   private Double explode_money;
/*     */   private Double member_advance;
/*     */   private Double shop_advance;
/*     */   private String disabled;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getLog_id()
/*     */   {
/*  30 */     return this.log_id;
/*     */   }
/*     */   public void setLog_id(int logId) {
/*  33 */     this.log_id = logId;
/*     */   }
/*     */   public int getMember_id() {
/*  36 */     return this.member_id;
/*     */   }
/*     */   public void setMember_id(int memberId) {
/*  39 */     this.member_id = memberId;
/*     */   }
/*     */   public Double getMoney() {
/*  42 */     return this.money;
/*     */   }
/*     */   public void setMoney(Double money) {
/*  45 */     this.money = money;
/*     */   }
/*     */   public String getMessage() {
/*  48 */     return this.message;
/*     */   }
/*     */   public void setMessage(String message) {
/*  51 */     this.message = message;
/*     */   }
/*     */   public Long getMtime() {
/*  54 */     return this.mtime;
/*     */   }
/*     */   public void setMtime(Long mtime) {
/*  57 */     this.mtime = mtime;
/*     */   }
/*     */   public int getPayment_id() {
/*  60 */     return this.payment_id;
/*     */   }
/*     */   public void setPayment_id(int paymentId) {
/*  63 */     this.payment_id = paymentId;
/*     */   }
/*     */   public int getOrder_id() {
/*  66 */     return this.order_id;
/*     */   }
/*     */   public void setOrder_id(int orderId) {
/*  69 */     this.order_id = orderId;
/*     */   }
/*     */   public String getPaymethod() {
/*  72 */     return this.paymethod;
/*     */   }
/*     */   public void setPaymethod(String paymethod) {
/*  75 */     this.paymethod = paymethod;
/*     */   }
/*     */   public String getMemo() {
/*  78 */     return this.memo;
/*     */   }
/*     */   public void setMemo(String memo) {
/*  81 */     this.memo = memo;
/*     */   }
/*     */   public Double getImport_money() {
/*  84 */     return this.import_money;
/*     */   }
/*     */   public void setImport_money(Double importMoney) {
/*  87 */     this.import_money = importMoney;
/*     */   }
/*     */   public Double getExplode_money() {
/*  90 */     return this.explode_money;
/*     */   }
/*     */   public void setExplode_money(Double explodeMoney) {
/*  93 */     this.explode_money = explodeMoney;
/*     */   }
/*     */   public Double getMember_advance() {
/*  96 */     return this.member_advance;
/*     */   }
/*     */   public void setMember_advance(Double memberAdvance) {
/*  99 */     this.member_advance = memberAdvance;
/*     */   }
/*     */   public Double getShop_advance() {
/* 102 */     return this.shop_advance;
/*     */   }
/*     */   public void setShop_advance(Double shopAdvance) {
/* 105 */     this.shop_advance = shopAdvance;
/*     */   }
/*     */   public String getDisabled() {
/* 108 */     return this.disabled;
/*     */   }
/*     */   public void setDisabled(String disabled) {
/* 111 */     this.disabled = disabled;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.AdvanceLogs
 * JD-Core Version:    0.6.0
 */