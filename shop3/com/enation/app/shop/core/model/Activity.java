/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Activity
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private String name;
/*    */   private Integer enable;
/*    */   private Long begin_time;
/*    */   private Long end_time;
/*    */   private String brief;
/*    */   private Integer disabled;
/*    */ 
/*    */   public Long getBegin_time()
/*    */   {
/* 24 */     return this.begin_time;
/*    */   }
/*    */   public void setBegin_time(Long begin_time) {
/* 27 */     this.begin_time = begin_time;
/*    */   }
/*    */   public String getBrief() {
/* 30 */     return this.brief;
/*    */   }
/*    */   public void setBrief(String brief) {
/* 33 */     this.brief = brief;
/*    */   }
/*    */   public Integer getDisabled() {
/* 36 */     return this.disabled;
/*    */   }
/*    */   public void setDisabled(Integer disabled) {
/* 39 */     this.disabled = disabled;
/*    */   }
/*    */   public Integer getEnable() {
/* 42 */     return this.enable;
/*    */   }
/*    */   public void setEnable(Integer enable) {
/* 45 */     this.enable = enable;
/*    */   }
/*    */   public Long getEnd_time() {
/* 48 */     return this.end_time;
/*    */   }
/*    */   public void setEnd_time(Long end_time) {
/* 51 */     this.end_time = end_time;
/*    */   }
/* 55 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 58 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 64 */     this.name = name;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Activity
 * JD-Core Version:    0.6.0
 */