/*    */ package com.enation.eop.sdk.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ 
/*    */ public abstract class AbstractMemberWidget extends AbstractWidget
/*    */ {
/* 25 */   private boolean showMenu = true;
/*    */ 
/*    */   public boolean cacheAble()
/*    */   {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   protected void showMenu(boolean show)
/*    */   {
/* 30 */     this.showMenu = show;
/*    */   }
/*    */ 
/*    */   protected void setMenu(String menuName) {
/* 34 */     putData("menu", menuName);
/*    */   }
/*    */ 
/*    */   public boolean getShowMenu() {
/* 38 */     return this.showMenu;
/*    */   }
/*    */ 
/*    */   protected Member getCurrentMember() {
/* 42 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 43 */     return member;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.widget.AbstractMemberWidget
 * JD-Core Version:    0.6.0
 */