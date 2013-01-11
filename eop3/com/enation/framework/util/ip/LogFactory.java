/*    */ package com.enation.framework.util.ip;
/*    */ 
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LogFactory
/*    */ {
/*  9 */   private static final Logger logger = Logger.getLogger("stdout");
/*    */ 
/*    */   public static void log(String info, Level level, Throwable ex)
/*    */   {
/* 14 */     logger.log(level, info, ex);
/*    */   }
/*    */ 
/*    */   public static Level getLogLevel() {
/* 18 */     return logger.getLevel();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 10 */     logger.setLevel(Level.DEBUG);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ip.LogFactory
 * JD-Core Version:    0.6.0
 */