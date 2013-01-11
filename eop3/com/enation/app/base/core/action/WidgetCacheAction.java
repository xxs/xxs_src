/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.service.IWidgetCacheManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ 
/*    */ public class WidgetCacheAction extends WWAction
/*    */ {
/*    */   private IWidgetCacheManager widgetCacheManager;
/*    */ 
/*    */   public String open()
/*    */   {
/*    */     try
/*    */     {
/* 24 */       this.widgetCacheManager.open();
/* 25 */       this.json = "{result:1}";
/*    */     } catch (RuntimeException e) {
/* 27 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*    */     }
/* 29 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public String close()
/*    */   {
/*    */     try
/*    */     {
/* 39 */       this.widgetCacheManager.close();
/* 40 */       this.json = "{result:1}";
/*    */     } catch (RuntimeException e) {
/* 42 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*    */     }
/* 44 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public String getState()
/*    */   {
/*    */     try {
/* 50 */       boolean isOpen = this.widgetCacheManager.isOpen();
/* 51 */       String state = isOpen ? "open" : "close";
/* 52 */       this.json = ("{result:1,state:'" + state + "'}");
/*    */     } catch (RuntimeException e) {
/* 54 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*    */     }
/* 56 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public IWidgetCacheManager getWidgetCacheManager()
/*    */   {
/* 61 */     return this.widgetCacheManager;
/*    */   }
/*    */ 
/*    */   public void setWidgetCacheManager(IWidgetCacheManager widgetCacheManager)
/*    */   {
/* 66 */     this.widgetCacheManager = widgetCacheManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.WidgetCacheAction
 * JD-Core Version:    0.6.0
 */