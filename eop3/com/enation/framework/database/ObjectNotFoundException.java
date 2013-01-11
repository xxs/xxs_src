/*    */ package com.enation.framework.database;
/*    */ 
/*    */ public class ObjectNotFoundException extends DBRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -3403302876974180460L;
/*    */ 
/*    */   public ObjectNotFoundException(String message)
/*    */   {
/* 12 */     super(message);
/*    */   }
/*    */ 
/*    */   public ObjectNotFoundException(Exception e, String sql) {
/* 16 */     super(e, sql);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.ObjectNotFoundException
 * JD-Core Version:    0.6.0
 */