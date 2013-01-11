/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.model.AdColumn;
/*    */ import com.enation.app.base.core.service.IAdColumnManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AdColumnAction extends WWAction
/*    */ {
/*    */   private IAdColumnManager adColumnManager;
/*    */   private AdColumn adColumn;
/*    */   private Long acid;
/*    */   private String id;
/*    */ 
/*    */   public String list()
/*    */   {
/* 20 */     this.webpage = this.adColumnManager.pageAdvPos(getPage(), getPageSize());
/* 21 */     return "list";
/*    */   }
/*    */ 
/*    */   public String detail() {
/* 25 */     this.adColumn = this.adColumnManager.getADcolumnDetail(this.acid);
/* 26 */     return "detail";
/*    */   }
/*    */ 
/*    */   public String delete() {
/*    */     try {
/* 31 */       this.adColumnManager.delAdcs(this.id);
/* 32 */       this.json = "{'result':0,'message':'删除成功'}";
/*    */     } catch (RuntimeException e) {
/* 34 */       this.json = "{'result':1,'message':'删除失败'}";
/*    */     }
/* 36 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public String add() {
/* 40 */     return "add";
/*    */   }
/*    */ 
/*    */   public String addSave() {
/* 44 */     this.adColumnManager.addAdvc(this.adColumn);
/* 45 */     this.msgs.add("新增广告位成功");
/* 46 */     this.urls.put("广告位列表", "adColumn!list.do");
/* 47 */     return "message";
/*    */   }
/*    */ 
/*    */   public String edit() {
/* 51 */     this.adColumn = this.adColumnManager.getADcolumnDetail(this.acid);
/* 52 */     return "edit";
/*    */   }
/*    */ 
/*    */   public String editSave() {
/* 56 */     this.adColumnManager.updateAdvc(this.adColumn);
/* 57 */     this.msgs.add("修改广告位成功");
/* 58 */     this.urls.put("广告位列表", "adColumn!list.do");
/* 59 */     return "message";
/*    */   }
/*    */ 
/*    */   public IAdColumnManager getAdColumnManager() {
/* 63 */     return this.adColumnManager;
/*    */   }
/*    */ 
/*    */   public void setAdColumnManager(IAdColumnManager adColumnManager) {
/* 67 */     this.adColumnManager = adColumnManager;
/*    */   }
/*    */ 
/*    */   public AdColumn getAdColumn() {
/* 71 */     return this.adColumn;
/*    */   }
/*    */ 
/*    */   public void setAdColumn(AdColumn adColumn) {
/* 75 */     this.adColumn = adColumn;
/*    */   }
/*    */ 
/*    */   public Long getAcid() {
/* 79 */     return this.acid;
/*    */   }
/*    */ 
/*    */   public void setAcid(Long acid) {
/* 83 */     this.acid = acid;
/*    */   }
/*    */ 
/*    */   public String getId() {
/* 87 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String id) {
/* 91 */     this.id = id;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.AdColumnAction
 * JD-Core Version:    0.6.0
 */