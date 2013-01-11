/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Depot
/*    */ {
/*    */   public Integer id;
/*    */   public String name;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 16 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 19 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 25 */     this.name = name;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Depot
 * JD-Core Version:    0.6.0
 */