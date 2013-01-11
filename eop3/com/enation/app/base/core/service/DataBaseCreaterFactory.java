/*    */ package com.enation.app.base.core.service;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ 
/*    */ public abstract class DataBaseCreaterFactory
/*    */ {
/*    */   public static IDataBaseCreater getDataBaseCreater()
/*    */   {
/* 16 */     if (EopSetting.DBTYPE.equals("1"))
/* 17 */       return (IDataBaseCreater)SpringContextHolder.getBean("mysqlDataBaseCreater");
/* 18 */     if (EopSetting.DBTYPE.equals("2"))
/* 19 */       return (IDataBaseCreater)SpringContextHolder.getBean("oracleDataBaseCreater");
/* 20 */     if (EopSetting.DBTYPE.equals("3")) {
/* 21 */       return (IDataBaseCreater)SpringContextHolder.getBean("mssqlDataBaseCreater");
/*    */     }
/* 23 */     throw new RuntimeException("未知的数据库类型");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.DataBaseCreaterFactory
 * JD-Core Version:    0.6.0
 */