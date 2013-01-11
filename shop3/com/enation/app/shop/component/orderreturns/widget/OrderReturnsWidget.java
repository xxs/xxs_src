/*    */ package com.enation.app.shop.component.orderreturns.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OrderReturnsWidget extends AbstractWidget
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 26 */     if (StringUtil.isEmpty(this.action))
/* 27 */       defaultAction();
/*    */   }
/*    */ 
/*    */   private void defaultAction()
/*    */   {
/* 38 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 39 */     int memberid = member.getMember_id().intValue();
/* 40 */     List orderList = this.orderManager.listByStatus(6, memberid);
/* 41 */     putData("orderList", orderList);
/* 42 */     setPageName("order_returns");
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 56 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 60 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.orderreturns.widget.OrderReturnsWidget
 * JD-Core Version:    0.6.0
 */