/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class User
/*    */ {
/*    */   private Integer userId;
/*    */   private String name;
/*    */   private String code;
/*    */ 
/*    */   public String getCode()
/*    */   {
/* 19 */     return this.code;
/*    */   }
/*    */ 
/*    */   public void setCode(String code)
/*    */   {
/* 26 */     this.code = code;
/*    */   }
/*    */ 
/*    */   public void finalize()
/*    */     throws Throwable
/*    */   {
/*    */   }
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getUserId()
/*    */   {
/* 39 */     return this.userId;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 43 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String newVal)
/*    */   {
/* 51 */     this.name = newVal;
/*    */   }
/*    */ 
/*    */   public void setUserId(Integer newVal)
/*    */   {
/* 59 */     this.userId = newVal;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.User
 * JD-Core Version:    0.6.0
 */