/*    */ package com.enation.app.base.core.service.impl.database;
/*    */ 
/*    */ import com.enation.app.base.core.service.IDataBaseCreater;
/*    */ import com.enation.framework.database.ISqlFileExecutor;
/*    */ 
/*    */ public class OracleDataBaseCreater
/*    */   implements IDataBaseCreater
/*    */ {
/*    */   private ISqlFileExecutor sqlFileExecutor;
/*    */ 
/*    */   public void create()
/*    */   {
/* 18 */     this.sqlFileExecutor.execute("file:com/enation/eop/eop_mysql.sql");
/* 19 */     this.sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mysql.sql");
/* 20 */     this.sqlFileExecutor.execute("file:com/enation/app/cms/cms_mysql.sql");
/*    */   }
/*    */   public ISqlFileExecutor getSqlFileExecutor() {
/* 23 */     return this.sqlFileExecutor;
/*    */   }
/*    */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 26 */     this.sqlFileExecutor = sqlFileExecutor;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.database.OracleDataBaseCreater
 * JD-Core Version:    0.6.0
 */