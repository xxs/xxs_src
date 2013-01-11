/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class ProductCat
/*    */ {
/*    */   private Integer id;
/*    */   private String name;
/*    */   private Integer num;
/*    */   private Integer sort;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 17 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */   @PrimaryKeyField
/*    */   public Integer getId() {
/* 26 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 30 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Integer getNum() {
/* 34 */     return this.num;
/*    */   }
/*    */ 
/*    */   public void setNum(Integer num) {
/* 38 */     this.num = num;
/*    */   }
/*    */ 
/*    */   public Integer getSort() {
/* 42 */     return this.sort;
/*    */   }
/*    */ 
/*    */   public void setSort(Integer sort) {
/* 46 */     this.sort = sort;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.ProductCat
 * JD-Core Version:    0.6.0
 */