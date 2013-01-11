/*    */ package com.enation.app.shop.core.service;
/*    */ 
/*    */ public class SnDuplicateException extends RuntimeException
/*    */ {
/*    */   public SnDuplicateException(String sn)
/*    */   {
/* 12 */     super("货号[" + sn + "]重复");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.SnDuplicateException
 * JD-Core Version:    0.6.0
 */