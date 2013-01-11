/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class FreezePoint
/*     */ {
/*     */   private int id;
/*     */   private int memberid;
/*     */   private Integer childid;
/*     */   private int point;
/*     */   private int mp;
/*     */   private Integer orderid;
/*     */   private int dateline;
/*     */   private String type;
/*     */   private int order_status;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getId()
/*     */   {
/*  33 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/*  37 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public int getPoint()
/*     */   {
/*  42 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(int point) {
/*  46 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public int getMp() {
/*  50 */     return this.mp;
/*     */   }
/*     */ 
/*     */   public void setMp(int mp) {
/*  54 */     this.mp = mp;
/*     */   }
/*     */ 
/*     */   public int getDateline()
/*     */   {
/*  59 */     return this.dateline;
/*     */   }
/*     */ 
/*     */   public void setDateline(int dateline) {
/*  63 */     this.dateline = dateline;
/*     */   }
/*     */ 
/*     */   public int getMemberid()
/*     */   {
/*  69 */     return this.memberid;
/*     */   }
/*     */ 
/*     */   public void setMemberid(int memberid) {
/*  73 */     this.memberid = memberid;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  78 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  82 */     this.type = type;
/*     */   }
/*     */   @NotDbField
/*     */   public int getOrder_status() {
/*  87 */     return this.order_status;
/*     */   }
/*     */ 
/*     */   public void setOrder_status(int order_status) {
/*  91 */     this.order_status = order_status;
/*     */   }
/*     */ 
/*     */   public Integer getChildid() {
/*  95 */     return this.childid;
/*     */   }
/*     */ 
/*     */   public void setChildid(Integer childid) {
/*  99 */     this.childid = childid;
/*     */   }
/*     */ 
/*     */   public Integer getOrderid() {
/* 103 */     return this.orderid;
/*     */   }
/*     */ 
/*     */   public void setOrderid(Integer orderid) {
/* 107 */     this.orderid = orderid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.FreezePoint
 * JD-Core Version:    0.6.0
 */