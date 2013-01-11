/*    */ package com.enation.framework.database;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class DBRuntimeException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -368646349014580765L;
/* 16 */   private static final Log loger = LogFactory.getLog(DBRuntimeException.class);
/*    */ 
/*    */   public DBRuntimeException(String message)
/*    */   {
/* 21 */     super(message);
/*    */   }
/*    */ 
/*    */   public DBRuntimeException(Exception e, String sql) {
/* 25 */     super("数据库运行期异常");
/* 26 */     e.printStackTrace();
/* 27 */     if (loger.isErrorEnabled())
/* 28 */       loger.error("数据库运行期异常，相关sql语句为" + sql);
/*    */   }
/*    */ 
/*    */   public DBRuntimeException(String message, String sql)
/*    */   {
/* 35 */     super(message);
/* 36 */     if (loger.isErrorEnabled())
/* 37 */       loger.error(message + "，相关sql语句为" + sql);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.DBRuntimeException
 * JD-Core Version:    0.6.0
 */