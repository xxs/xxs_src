/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Gnotify
/*     */   implements Serializable
/*     */ {
/*     */   private int gnotify_id;
/*     */   private int goods_id;
/*     */   private int member_id;
/*     */   private int product_id;
/*     */   private String email;
/*     */   private String status;
/*     */   private Long send_time;
/*     */   private Long create_time;
/*     */   private String disabled;
/*     */   private String remark;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getGnotify_id()
/*     */   {
/*  27 */     return this.gnotify_id;
/*     */   }
/*     */ 
/*     */   public void setGnotify_id(int gnotifyId) {
/*  31 */     this.gnotify_id = gnotifyId;
/*     */   }
/*     */ 
/*     */   public int getGoods_id() {
/*  35 */     return this.goods_id;
/*     */   }
/*     */ 
/*     */   public void setGoods_id(int goodsId) {
/*  39 */     this.goods_id = goodsId;
/*     */   }
/*     */ 
/*     */   public int getMember_id() {
/*  43 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(int memberId) {
/*  47 */     this.member_id = memberId;
/*     */   }
/*     */ 
/*     */   public int getProduct_id() {
/*  51 */     return this.product_id;
/*     */   }
/*     */ 
/*     */   public void setProduct_id(int productId) {
/*  55 */     this.product_id = productId;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  59 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/*  63 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/*  67 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/*  71 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Long getSend_time() {
/*  75 */     return this.send_time;
/*     */   }
/*     */ 
/*     */   public void setSend_time(Long sendTime) {
/*  79 */     this.send_time = sendTime;
/*     */   }
/*     */ 
/*     */   public Long getCreate_time() {
/*  83 */     return this.create_time;
/*     */   }
/*     */ 
/*     */   public void setCreate_time(Long createTime) {
/*  87 */     this.create_time = createTime;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/*  91 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/*  95 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  99 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 103 */     this.remark = remark;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Gnotify
 * JD-Core Version:    0.6.0
 */