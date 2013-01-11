/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Logi
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private String name;
/*    */   private String code;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 19 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 23 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getCode() {
/* 35 */     return this.code;
/*    */   }
/*    */ 
/*    */   public void setCode(String code) {
/* 39 */     this.code = code;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Logi
 * JD-Core Version:    0.6.0
 */