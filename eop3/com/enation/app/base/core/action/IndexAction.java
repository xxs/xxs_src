/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.eop.resource.IIndexItemManager;
/*    */ import com.enation.eop.resource.model.IndexItem;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IndexAction extends WWAction
/*    */ {
/*    */   private IIndexItemManager indexItemManager;
/*    */   private List<IndexItem> itemList;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 20 */     this.itemList = this.indexItemManager.list();
/* 21 */     return "index";
/*    */   }
/*    */ 
/*    */   public IIndexItemManager getIndexItemManager() {
/* 25 */     return this.indexItemManager;
/*    */   }
/*    */ 
/*    */   public void setIndexItemManager(IIndexItemManager indexItemManager) {
/* 29 */     this.indexItemManager = indexItemManager;
/*    */   }
/*    */ 
/*    */   public List<IndexItem> getItemList() {
/* 33 */     return this.itemList;
/*    */   }
/*    */ 
/*    */   public void setItemList(List<IndexItem> itemList) {
/* 37 */     this.itemList = itemList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.IndexAction
 * JD-Core Version:    0.6.0
 */