/*    */ package com.enation.app.shop.component.ordercore.plugin.base;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
/*    */ import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
/*    */ import com.enation.app.shop.core.service.IMemberManager;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.app.shop.core.service.IOrderReportManager;
/*    */ import com.enation.app.shop.core.service.OrderStatus;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class OrderDetailBasePlugin extends AutoRegisterPlugin
/*    */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */   private IMemberManager memberManager;
/*    */   private IOrderReportManager orderReportManager;
/*    */ 
/*    */   public boolean canBeExecute(Order order)
/*    */   {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   public String getTabName(Order order)
/*    */   {
/* 40 */     return "基本信息";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 46 */     return 0;
/*    */   }
/*    */ 
/*    */   public String onShowOrderDetailHtml(Order order)
/*    */   {
/* 52 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*    */ 
/* 54 */     List itemList = this.orderManager.listGoodsItems(order.getOrder_id());
/* 55 */     freeMarkerPaser.setClz(getClass());
/* 56 */     if (order.getMember_id() != null) {
/* 57 */       Member member = this.memberManager.get(order.getMember_id());
/* 58 */       freeMarkerPaser.putData("member", member);
/*    */     }
/*    */ 
/* 61 */     List deliveryList = this.orderReportManager.getDeliveryList(order.getOrder_id().intValue());
/*    */ 
/* 63 */     freeMarkerPaser.putData("deliveryList", deliveryList);
/* 64 */     freeMarkerPaser.putData("itemList", itemList);
/* 65 */     freeMarkerPaser.putData(OrderStatus.getOrderStatusMap());
/*    */ 
/* 67 */     freeMarkerPaser.setPageName("base");
/* 68 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 73 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 77 */     this.orderManager = orderManager;
/*    */   }
/*    */ 
/*    */   public IMemberManager getMemberManager() {
/* 81 */     return this.memberManager;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 85 */     this.memberManager = memberManager;
/*    */   }
/*    */ 
/*    */   public IOrderReportManager getOrderReportManager() {
/* 89 */     return this.orderReportManager;
/*    */   }
/*    */ 
/*    */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 93 */     this.orderReportManager = orderReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.base.OrderDetailBasePlugin
 * JD-Core Version:    0.6.0
 */