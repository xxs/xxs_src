/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Promotion
/*     */   implements Serializable
/*     */ {
/*     */   private int pmt_id;
/*     */   private String pmts_id;
/*     */   private int pmta_id;
/*     */   private Long pmt_time_begin;
/*     */   private Long pmt_time_end;
/*     */   private Double order_money_from;
/*     */   private Double order_money_to;
/*     */   private int seq;
/*     */   private int pmt_type;
/*     */   private int pmt_belong;
/*     */   private int pmt_bond_type;
/*     */   private String pmt_describe;
/*     */   private String pmt_solution;
/*     */   private int pmt_ifcoupon;
/*     */   private Long pmt_update_time;
/*     */   private String pmt_basic_type;
/*     */   private String disabled;
/*     */   private String pmt_ifsale;
/*     */   private int pmt_distype;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getPmt_id()
/*     */   {
/*  35 */     return this.pmt_id;
/*     */   }
/*     */ 
/*     */   public void setPmt_id(int pmtId) {
/*  39 */     this.pmt_id = pmtId;
/*     */   }
/*     */ 
/*     */   public String getPmts_id() {
/*  43 */     return this.pmts_id;
/*     */   }
/*     */ 
/*     */   public void setPmts_id(String pmtsId) {
/*  47 */     this.pmts_id = pmtsId;
/*     */   }
/*     */ 
/*     */   public int getPmta_id() {
/*  51 */     return this.pmta_id;
/*     */   }
/*     */ 
/*     */   public void setPmta_id(int pmtaId) {
/*  55 */     this.pmta_id = pmtaId;
/*     */   }
/*     */ 
/*     */   public Long getPmt_time_begin() {
/*  59 */     return this.pmt_time_begin;
/*     */   }
/*     */ 
/*     */   public void setPmt_time_begin(Long pmtTimeBegin) {
/*  63 */     this.pmt_time_begin = pmtTimeBegin;
/*     */   }
/*     */ 
/*     */   public Long getPmt_time_end() {
/*  67 */     return this.pmt_time_end;
/*     */   }
/*     */ 
/*     */   public void setPmt_time_end(Long pmtTimeEnd) {
/*  71 */     this.pmt_time_end = pmtTimeEnd;
/*     */   }
/*     */ 
/*     */   public Double getOrder_money_from() {
/*  75 */     return this.order_money_from;
/*     */   }
/*     */ 
/*     */   public void setOrder_money_from(Double orderMoneyFrom) {
/*  79 */     this.order_money_from = orderMoneyFrom;
/*     */   }
/*     */ 
/*     */   public Double getOrder_money_to() {
/*  83 */     return this.order_money_to;
/*     */   }
/*     */ 
/*     */   public void setOrder_money_to(Double orderMoneyTo) {
/*  87 */     this.order_money_to = orderMoneyTo;
/*     */   }
/*     */ 
/*     */   public int getSeq() {
/*  91 */     return this.seq;
/*     */   }
/*     */ 
/*     */   public void setSeq(int seq) {
/*  95 */     this.seq = seq;
/*     */   }
/*     */ 
/*     */   public int getPmt_type() {
/*  99 */     return this.pmt_type;
/*     */   }
/*     */ 
/*     */   public void setPmt_type(int pmtType) {
/* 103 */     this.pmt_type = pmtType;
/*     */   }
/*     */ 
/*     */   public int getPmt_belong() {
/* 107 */     return this.pmt_belong;
/*     */   }
/*     */ 
/*     */   public void setPmt_belong(int pmtBelong) {
/* 111 */     this.pmt_belong = pmtBelong;
/*     */   }
/*     */ 
/*     */   public int getPmt_bond_type() {
/* 115 */     return this.pmt_bond_type;
/*     */   }
/*     */ 
/*     */   public void setPmt_bond_type(int pmtBondType) {
/* 119 */     this.pmt_bond_type = pmtBondType;
/*     */   }
/*     */ 
/*     */   public String getPmt_describe() {
/* 123 */     return this.pmt_describe;
/*     */   }
/*     */ 
/*     */   public void setPmt_describe(String pmtDescribe) {
/* 127 */     this.pmt_describe = pmtDescribe;
/*     */   }
/*     */ 
/*     */   public String getPmt_solution() {
/* 131 */     return this.pmt_solution;
/*     */   }
/*     */ 
/*     */   public void setPmt_solution(String pmtSolution) {
/* 135 */     this.pmt_solution = pmtSolution;
/*     */   }
/*     */ 
/*     */   public int getPmt_ifcoupon() {
/* 139 */     return this.pmt_ifcoupon;
/*     */   }
/*     */ 
/*     */   public void setPmt_ifcoupon(int pmtIfcoupon) {
/* 143 */     this.pmt_ifcoupon = pmtIfcoupon;
/*     */   }
/*     */ 
/*     */   public Long getPmt_update_time() {
/* 147 */     return this.pmt_update_time;
/*     */   }
/*     */ 
/*     */   public void setPmt_update_time(Long pmtUpdateTime) {
/* 151 */     this.pmt_update_time = pmtUpdateTime;
/*     */   }
/*     */ 
/*     */   public String getPmt_basic_type() {
/* 155 */     return this.pmt_basic_type;
/*     */   }
/*     */ 
/*     */   public void setPmt_basic_type(String pmtBasicType) {
/* 159 */     this.pmt_basic_type = pmtBasicType;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/* 163 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/* 167 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getPmt_ifsale() {
/* 171 */     return this.pmt_ifsale;
/*     */   }
/*     */ 
/*     */   public void setPmt_ifsale(String pmtIfsale) {
/* 175 */     this.pmt_ifsale = pmtIfsale;
/*     */   }
/*     */ 
/*     */   public int getPmt_distype() {
/* 179 */     return this.pmt_distype;
/*     */   }
/*     */ 
/*     */   public void setPmt_distype(int pmtDistype) {
/* 183 */     this.pmt_distype = pmtDistype;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Promotion
 * JD-Core Version:    0.6.0
 */