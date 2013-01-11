/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class SpecValue
/*    */ {
/*    */   private Integer spec_value_id;
/*    */   private Integer spec_id;
/*    */   private String spec_value;
/*    */   private String spec_image;
/*    */   private Integer spec_order;
/*    */   private Integer spec_type;
/*    */ 
/*    */   public Integer getSpec_type()
/*    */   {
/* 22 */     return this.spec_type;
/*    */   }
/*    */   public void setSpec_type(Integer specType) {
/* 25 */     this.spec_type = specType;
/*    */   }
/*    */   @PrimaryKeyField
/*    */   public Integer getSpec_value_id() {
/* 30 */     return this.spec_value_id;
/*    */   }
/*    */   public void setSpec_value_id(Integer specValueId) {
/* 33 */     this.spec_value_id = specValueId;
/*    */   }
/*    */   public Integer getSpec_id() {
/* 36 */     return this.spec_id;
/*    */   }
/*    */   public void setSpec_id(Integer specId) {
/* 39 */     this.spec_id = specId;
/*    */   }
/*    */   public String getSpec_value() {
/* 42 */     return this.spec_value;
/*    */   }
/*    */   public void setSpec_value(String specValue) {
/* 45 */     this.spec_value = specValue;
/*    */   }
/*    */   public String getSpec_image() {
/* 48 */     return this.spec_image;
/*    */   }
/*    */   public void setSpec_image(String specImage) {
/* 51 */     this.spec_image = specImage;
/*    */   }
/*    */   public Integer getSpec_order() {
/* 54 */     return this.spec_order;
/*    */   }
/*    */   public void setSpec_order(Integer specOrder) {
/* 57 */     this.spec_order = specOrder;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.SpecValue
 * JD-Core Version:    0.6.0
 */