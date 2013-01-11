/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.framework.action.WWAction;
/*    */ import com.enation.framework.resource.ResourceStateManager;
/*    */ 
/*    */ public class ResourceStateAction extends WWAction
/*    */ {
/*    */   private boolean haveNewDisplaoy;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 15 */     this.haveNewDisplaoy = ResourceStateManager.getHaveNewDisploy();
/* 16 */     return "input";
/*    */   }
/*    */ 
/*    */   public String save()
/*    */   {
/* 21 */     ResourceStateManager.setDisplayState(1);
/* 22 */     showSuccessJson("更新成功");
/* 23 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public boolean isHaveNewDisplaoy()
/*    */   {
/* 28 */     return this.haveNewDisplaoy;
/*    */   }
/*    */ 
/*    */   public void setHaveNewDisplaoy(boolean haveNewDisplaoy)
/*    */   {
/* 33 */     this.haveNewDisplaoy = haveNewDisplaoy;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.ResourceStateAction
 * JD-Core Version:    0.6.0
 */