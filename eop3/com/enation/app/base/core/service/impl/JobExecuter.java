/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.plugin.job.JobExecutePluginsBundle;
/*    */ import com.enation.app.base.core.service.IJobExecuter;
/*    */ 
/*    */ public class JobExecuter
/*    */   implements IJobExecuter
/*    */ {
/*    */   private JobExecutePluginsBundle jobExecutePluginsBundle;
/*    */ 
/*    */   public void everyHour()
/*    */   {
/* 16 */     this.jobExecutePluginsBundle.everyHourExcecute();
/*    */   }
/*    */ 
/*    */   public void everyDay()
/*    */   {
/*    */     try {
/* 22 */       this.jobExecutePluginsBundle.everyDayExcecute();
/*    */     }
/*    */     catch (Exception e) {
/* 25 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void everyMonth()
/*    */   {
/* 31 */     this.jobExecutePluginsBundle.everyMonthExcecute();
/*    */   }
/*    */ 
/*    */   public JobExecutePluginsBundle getJobExecutePluginsBundle() {
/* 35 */     return this.jobExecutePluginsBundle;
/*    */   }
/*    */ 
/*    */   public void setJobExecutePluginsBundle(JobExecutePluginsBundle jobExecutePluginsBundle)
/*    */   {
/* 40 */     this.jobExecutePluginsBundle = jobExecutePluginsBundle;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.JobExecuter
 * JD-Core Version:    0.6.0
 */