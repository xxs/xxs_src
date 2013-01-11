/*    */ package com.enation.app.base.core.plugin.job;
/*    */ 
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import com.enation.framework.plugin.IPlugin;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class JobExecutePluginsBundle extends AutoRegisterPluginsBundle
/*    */ {
/*    */   public String getName()
/*    */   {
/* 17 */     return "任务执行器插件桩";
/*    */   }
/*    */ 
/*    */   public void everyHourExcecute()
/*    */   {
/* 25 */     List plugins = getPlugins();
/*    */ 
/* 27 */     if (plugins != null)
/* 28 */       for (IPlugin plugin : plugins)
/* 29 */         if ((plugin instanceof IEveryHourExecuteEvent))
/*    */         {
/* 31 */           if (loger.isDebugEnabled()) {
/* 32 */             loger.debug("调度任务插件[" + plugin.getClass() + "]everyHour开始执行...");
/*    */           }
/*    */ 
/* 35 */           IEveryHourExecuteEvent event = (IEveryHourExecuteEvent)plugin;
/* 36 */           event.everyHour();
/*    */ 
/* 38 */           if (loger.isDebugEnabled())
/* 39 */             loger.debug("调度任务插件[" + plugin.getClass() + "]everyHour执行完成...");
/*    */         }
/*    */   }
/*    */ 
/*    */   public void everyDayExcecute()
/*    */   {
/*    */     try
/*    */     {
/* 54 */       List plugins = getPlugins();
/*    */ 
/* 56 */       if (plugins != null) {
/* 57 */         for (IPlugin plugin : plugins)
/* 58 */           if ((plugin instanceof IEveryDayExecuteEvent))
/*    */           {
/* 60 */             if (loger.isDebugEnabled()) {
/* 61 */               loger.debug("调度任务插件[" + plugin.getClass() + "]everyDay开始执行...");
/*    */             }
/*    */ 
/* 64 */             System.out.println("调度任务插件[" + plugin.getClass() + "]everyDay开始执行...");
/*    */ 
/* 66 */             IEveryDayExecuteEvent event = (IEveryDayExecuteEvent)plugin;
/* 67 */             event.everyDay();
/*    */ 
/* 69 */             if (loger.isDebugEnabled())
/* 70 */               loger.debug("调度任务插件[" + plugin.getClass() + "]everyDay执行完成...");
/*    */           }
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 76 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void everyMonthExcecute()
/*    */   {
/* 85 */     List plugins = getPlugins();
/*    */ 
/* 87 */     if (plugins != null)
/* 88 */       for (IPlugin plugin : plugins)
/* 89 */         if ((plugin instanceof IEveryMonthExecuteEvent))
/*    */         {
/* 91 */           if (loger.isDebugEnabled()) {
/* 92 */             loger.debug("调度任务插件[" + plugin.getClass() + "]everyMonth开始执行...");
/*    */           }
/*    */ 
/* 95 */           IEveryMonthExecuteEvent event = (IEveryMonthExecuteEvent)plugin;
/* 96 */           event.everyMonth();
/*    */ 
/* 98 */           if (loger.isDebugEnabled())
/* 99 */             loger.debug("调度任务插件[" + plugin.getClass() + "]everyMonth执行完成...");
/*    */         }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.job.JobExecutePluginsBundle
 * JD-Core Version:    0.6.0
 */