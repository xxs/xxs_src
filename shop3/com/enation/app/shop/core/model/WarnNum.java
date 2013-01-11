/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WarnNum
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private Integer goods_id;
/*    */   private Double sphere;
/*    */   private Double cylinder;
/*    */   private Integer product_id;
/*    */   private String color;
/*    */   private Integer warn_num;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 24 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 28 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Integer getGoods_id() {
/* 32 */     return this.goods_id;
/*    */   }
/*    */ 
/*    */   public void setGoods_id(Integer goods_id) {
/* 36 */     this.goods_id = goods_id;
/*    */   }
/*    */ 
/*    */   public Double getSphere() {
/* 40 */     return this.sphere;
/*    */   }
/*    */ 
/*    */   public void setSphere(Double sphere) {
/* 44 */     this.sphere = sphere;
/*    */   }
/*    */ 
/*    */   public Double getCylinder() {
/* 48 */     return this.cylinder;
/*    */   }
/*    */ 
/*    */   public void setCylinder(Double cylinder) {
/* 52 */     this.cylinder = cylinder;
/*    */   }
/*    */ 
/*    */   public Integer getProduct_id() {
/* 56 */     return this.product_id;
/*    */   }
/*    */ 
/*    */   public void setProduct_id(Integer product_id) {
/* 60 */     this.product_id = product_id;
/*    */   }
/*    */ 
/*    */   public String getColor() {
/* 64 */     return this.color;
/*    */   }
/*    */ 
/*    */   public void setColor(String color) {
/* 68 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public Integer getWarn_num() {
/* 72 */     return this.warn_num;
/*    */   }
/*    */ 
/*    */   public void setWarn_num(Integer warn_num) {
/* 76 */     this.warn_num = warn_num;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.WarnNum
 * JD-Core Version:    0.6.0
 */