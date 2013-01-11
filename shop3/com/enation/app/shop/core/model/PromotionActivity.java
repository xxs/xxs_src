/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class PromotionActivity
/*    */ {
/*    */   private Integer id;
/*    */   private String name;
/*    */   private int enable;
/*    */   private Long begin_time;
/*    */   private Long end_time;
/*    */   private String brief;
/*    */   private int disabled;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 20 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 23 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 26 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 29 */     this.name = name;
/*    */   }
/*    */   public int getEnable() {
/* 32 */     return this.enable;
/*    */   }
/*    */   public void setEnable(int enable) {
/* 35 */     this.enable = enable;
/*    */   }
/*    */   public Long getBegin_time() {
/* 38 */     return this.begin_time;
/*    */   }
/*    */   public void setBegin_time(Long beginTime) {
/* 41 */     this.begin_time = beginTime;
/*    */   }
/*    */   public Long getEnd_time() {
/* 44 */     return this.end_time;
/*    */   }
/*    */   public void setEnd_time(Long endTime) {
/* 47 */     this.end_time = endTime;
/*    */   }
/*    */   public String getBrief() {
/* 50 */     return this.brief;
/*    */   }
/*    */   public void setBrief(String brief) {
/* 53 */     this.brief = brief;
/*    */   }
/*    */   public int getDisabled() {
/* 56 */     return this.disabled;
/*    */   }
/*    */   public void setDisabled(int disabled) {
/* 59 */     this.disabled = disabled;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PromotionActivity
 * JD-Core Version:    0.6.0
 */