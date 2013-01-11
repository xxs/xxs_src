/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.eop.resource.IDataLogManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ 
/*    */ public class DataLogAction extends WWAction
/*    */ {
/*    */   private IDataLogManager dataLogManager;
/*    */   private String start;
/*    */   private String end;
/*    */   private Integer[] ids;
/*    */ 
/*    */   public String list()
/*    */   {
/* 13 */     this.webpage = this.dataLogManager.list(this.start, this.end, getPage(), getPageSize());
/* 14 */     return "list";
/*    */   }
/*    */ 
/*    */   public String delete() {
/*    */     try {
/* 19 */       this.dataLogManager.delete(this.ids);
/* 20 */       this.json = "{result:0,message:'删除成功'}";
/*    */     } catch (RuntimeException e) {
/* 22 */       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
/*    */     }
/* 24 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public IDataLogManager getDataLogManager() {
/* 28 */     return this.dataLogManager;
/*    */   }
/*    */ 
/*    */   public void setDataLogManager(IDataLogManager dataLogManager) {
/* 32 */     this.dataLogManager = dataLogManager;
/*    */   }
/*    */ 
/*    */   public String getStart() {
/* 36 */     return this.start;
/*    */   }
/*    */ 
/*    */   public void setStart(String start) {
/* 40 */     this.start = start;
/*    */   }
/*    */ 
/*    */   public String getEnd() {
/* 44 */     return this.end;
/*    */   }
/*    */ 
/*    */   public void setEnd(String end) {
/* 48 */     this.end = end;
/*    */   }
/*    */ 
/*    */   public Integer[] getIds() {
/* 52 */     return this.ids;
/*    */   }
/*    */ 
/*    */   public void setIds(Integer[] ids) {
/* 56 */     this.ids = ids;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.DataLogAction
 * JD-Core Version:    0.6.0
 */