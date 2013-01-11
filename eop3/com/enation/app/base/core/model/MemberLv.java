/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MemberLv
/*    */   implements Serializable
/*    */ {
/*    */   private Integer lv_id;
/*    */   private String name;
/*    */   private Integer default_lv;
/*    */   private Integer discount;
/*    */   private Double lvPrice;
/*    */   private int point;
/*    */   private boolean selected;
/*    */ 
/*    */   public MemberLv()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MemberLv(Integer lv_id, String name)
/*    */   {
/* 19 */     this.lv_id = lv_id;
/* 20 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Integer getDefault_lv()
/*    */   {
/* 32 */     return this.default_lv;
/*    */   }
/*    */   public void setDefault_lv(Integer default_lv) {
/* 35 */     this.default_lv = default_lv;
/*    */   }
/* 39 */   @PrimaryKeyField
/*    */   public Integer getLv_id() { return this.lv_id; }
/*    */ 
/*    */   public void setLv_id(Integer lv_id) {
/* 42 */     this.lv_id = lv_id;
/*    */   }
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 48 */     this.name = name;
/*    */   }
/*    */   @NotDbField
/*    */   public boolean getSelected() {
/* 53 */     return this.selected;
/*    */   }
/*    */   public void setSelected(boolean selected) {
/* 56 */     this.selected = selected;
/*    */   }
/*    */   public Integer getDiscount() {
/* 59 */     return this.discount;
/*    */   }
/*    */   public void setDiscount(Integer discount) {
/* 62 */     this.discount = discount;
/*    */   }
/*    */   @NotDbField
/*    */   public Double getLvPrice() {
/* 67 */     return this.lvPrice;
/*    */   }
/*    */   public void setLvPrice(Double lvPrice) {
/* 70 */     this.lvPrice = lvPrice;
/*    */   }
/*    */   public int getPoint() {
/* 73 */     return this.point;
/*    */   }
/*    */   public void setPoint(int point) {
/* 76 */     this.point = point;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.MemberLv
 * JD-Core Version:    0.6.0
 */