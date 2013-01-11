/*    */ package com.enation.app.base.core.service.impl.database;
/*    */ 
/*    */ import com.enation.app.base.core.service.IDataBaseCreater;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.database.ISqlFileExecutor;
/*    */ 
/*    */ public class MssqlDataBaseCreater
/*    */   implements IDataBaseCreater
/*    */ {
/*    */   private ISqlFileExecutor sqlFileExecutor;
/*    */ 
/*    */   public void create()
/*    */   {
/* 16 */     if (EopSetting.RUNMODE.equals("2")) {
/* 17 */       this.sqlFileExecutor.execute("USE [master];");
/*    */ 
/* 19 */       this.sqlFileExecutor.execute("IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'eop')  DROP DATABASE [eop]");
/* 20 */       this.sqlFileExecutor.execute("CREATE DATABASE [eop]");
/* 21 */       this.sqlFileExecutor.execute("USE [eop]");
/*    */     }
/*    */ 
/* 24 */     this.sqlFileExecutor.execute("file:com/enation/eop/eop_mssql.sql");
/* 25 */     this.sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mssql.sql");
/* 26 */     this.sqlFileExecutor.execute("file:com/enation/app/cms/cms_mssql.sql");
/*    */   }
/*    */   public ISqlFileExecutor getSqlFileExecutor() {
/* 29 */     return this.sqlFileExecutor;
/*    */   }
/*    */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 32 */     this.sqlFileExecutor = sqlFileExecutor;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.database.MssqlDataBaseCreater
 * JD-Core Version:    0.6.0
 */