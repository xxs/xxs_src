/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class DlyArea
/*    */   implements Serializable
/*    */ {
/*    */   private Integer area_id;
/*    */   private String name;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getArea_id()
/*    */   {
/* 17 */     return this.area_id;
/*    */   }
/*    */ 
/*    */   public void setArea_id(Integer areaId) {
/* 21 */     this.area_id = areaId;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 25 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 29 */     this.name = name;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.DlyArea
 * JD-Core Version:    0.6.0
 */