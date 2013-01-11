/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.service.IBatchSqlExecutor;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BatchSqlExecuteAction extends WWAction
/*    */ {
/*    */   private IBatchSqlExecutor batchSqlExecutor;
/*    */   private String sql;
/*    */   private String toCms;
/*    */ 
/*    */   public String toExe()
/*    */   {
/* 13 */     return "input";
/*    */   }
/*    */ 
/*    */   public String execute() {
/*    */     try {
/* 18 */       if (this.toCms == null)
/* 19 */         this.batchSqlExecutor.exeucte(this.sql);
/*    */       else
/* 21 */         this.batchSqlExecutor.executeForCms(this.sql);
/* 22 */       this.msgs.add("执行成功");
/*    */     } catch (RuntimeException e) {
/* 24 */       this.msgs.add(e.getMessage());
/*    */     }
/* 26 */     return "message";
/*    */   }
/*    */ 
/*    */   public String getSql()
/*    */   {
/* 31 */     return this.sql;
/*    */   }
/*    */   public void setSql(String sql) {
/* 34 */     this.sql = sql;
/*    */   }
/*    */   public IBatchSqlExecutor getBatchSqlExecutor() {
/* 37 */     return this.batchSqlExecutor;
/*    */   }
/*    */   public void setBatchSqlExecutor(IBatchSqlExecutor batchSqlExecutor) {
/* 40 */     this.batchSqlExecutor = batchSqlExecutor;
/*    */   }
/*    */ 
/*    */   public String getToCms() {
/* 44 */     return this.toCms;
/*    */   }
/*    */ 
/*    */   public void setToCms(String toCms) {
/* 48 */     this.toCms = toCms;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.BatchSqlExecuteAction
 * JD-Core Version:    0.6.0
 */