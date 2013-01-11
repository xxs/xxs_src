/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ public class ArticleCatRuntimeException extends RuntimeException
/*    */ {
/*    */   private int error_code;
/*    */ 
/*    */   public ArticleCatRuntimeException(int code)
/*    */   {
/*  6 */     this.error_code = code;
/*    */   }
/*    */ 
/*    */   public int getError_code()
/*    */   {
/* 12 */     return this.error_code;
/*    */   }
/*    */ 
/*    */   public void setError_code(int error_code) {
/* 16 */     this.error_code = error_code;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleCatRuntimeException
 * JD-Core Version:    0.6.0
 */