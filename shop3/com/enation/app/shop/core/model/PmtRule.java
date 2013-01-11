/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PmtRule
/*     */   implements Serializable
/*     */ {
/*     */   private Integer rule_id;
/*     */   private Integer pmt_id;
/*     */   private Long begin_time;
/*     */   private Long end_time;
/*     */   private Double discount;
/*     */   private String brief;
/*     */   private Integer type;
/*     */   private Long update_time;
/*     */   private Double order_price;
/*     */ 
/*     */   public Long getBegin_time()
/*     */   {
/*  32 */     return this.begin_time;
/*     */   }
/*     */ 
/*     */   public void setBegin_time(Long begin_time) {
/*  36 */     this.begin_time = begin_time;
/*     */   }
/*     */ 
/*     */   public String getBrief() {
/*  40 */     return this.brief;
/*     */   }
/*     */ 
/*     */   public void setBrief(String brief) {
/*  44 */     this.brief = brief;
/*     */   }
/*     */ 
/*     */   public Long getEnd_time() {
/*  48 */     return this.end_time;
/*     */   }
/*     */ 
/*     */   public void setEnd_time(Long end_time) {
/*  52 */     this.end_time = end_time;
/*     */   }
/*     */ 
/*     */   public Double getOrder_price() {
/*  56 */     return this.order_price;
/*     */   }
/*     */ 
/*     */   public void setOrder_price(Double order_price) {
/*  60 */     this.order_price = order_price;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getPmt_id() {
/*  65 */     return this.pmt_id;
/*     */   }
/*     */ 
/*     */   public void setPmt_id(Integer pmt_id) {
/*  69 */     this.pmt_id = pmt_id;
/*     */   }
/*     */ 
/*     */   public Integer getRule_id() {
/*  73 */     return this.rule_id;
/*     */   }
/*     */ 
/*     */   public void setRule_id(Integer rule_id) {
/*  77 */     this.rule_id = rule_id;
/*     */   }
/*     */ 
/*     */   public Double getDiscount()
/*     */   {
/*  82 */     return this.discount;
/*     */   }
/*     */ 
/*     */   public void setDiscount(Double discount) {
/*  86 */     this.discount = discount;
/*     */   }
/*     */ 
/*     */   public Integer getType() {
/*  90 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(Integer type) {
/*  94 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Long getUpdate_time() {
/*  98 */     return this.update_time;
/*     */   }
/*     */ 
/*     */   public void setUpdate_time(Long update_time) {
/* 102 */     this.update_time = update_time;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PmtRule
 * JD-Core Version:    0.6.0
 */