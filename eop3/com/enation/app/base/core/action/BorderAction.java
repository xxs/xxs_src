/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.eop.resource.IBorderManager;
/*    */ import com.enation.eop.resource.model.Border;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BorderAction extends WWAction
/*    */ {
/*    */   private IBorderManager borderManager;
/*    */   private List<Border> borderList;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 20 */     this.borderList = this.borderManager.list();
/* 21 */     return "list";
/*    */   }
/*    */   public void setBorderManager(IBorderManager borderManager) {
/* 24 */     this.borderManager = borderManager;
/*    */   }
/*    */   public void setBorderList(List<Border> borderList) {
/* 27 */     this.borderList = borderList;
/*    */   }
/*    */   public IBorderManager getBorderManager() {
/* 30 */     return this.borderManager;
/*    */   }
/*    */   public List<Border> getBorderList() {
/* 33 */     return this.borderList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.BorderAction
 * JD-Core Version:    0.6.0
 */