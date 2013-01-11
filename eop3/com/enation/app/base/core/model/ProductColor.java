/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class ProductColor
/*    */ {
/*    */   private Integer id;
/*    */   private String colorname;
/*    */   private Integer num;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 12 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 16 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Integer getNum() {
/* 20 */     return this.num;
/*    */   }
/*    */ 
/*    */   public void setNum(Integer num) {
/* 24 */     this.num = num;
/*    */   }
/*    */ 
/*    */   public String getColorname() {
/* 28 */     return this.colorname;
/*    */   }
/*    */ 
/*    */   public void setColorname(String colorname) {
/* 32 */     this.colorname = colorname;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.ProductColor
 * JD-Core Version:    0.6.0
 */