/*    */ package com.enation.app.shop.component.receipt.plugin;
/*    */ 
/*    */ import com.enation.app.shop.component.receipt.Receipt;
/*    */ import com.enation.app.shop.component.receipt.service.IReceiptManager;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
/*    */ import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class OrderDetailReceiptPlugin extends AutoRegisterPlugin
/*    */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IReceiptManager receiptManager;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 32 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 33 */     freeMarkerPaser.setClz(getClass());
/*    */ 
/* 35 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 36 */     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);
/*    */ 
/* 38 */     Receipt receipt = this.receiptManager.getByOrderid(Integer.valueOf(orderId));
/*    */ 
/* 40 */     freeMarkerPaser.putData("receipt", receipt);
/* 41 */     freeMarkerPaser.setPageName("receipt_detail");
/* 42 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String onShowOrderDetailHtml(Order order)
/*    */   {
/* 48 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 49 */     int orderId = order.getOrder_id().intValue();
/* 50 */     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
/* 51 */     freeMarkerPaser.setPageName("receipt");
/* 52 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Order order)
/*    */   {
/* 58 */     return "发票";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 64 */     return 11;
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(Order order)
/*    */   {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   public IReceiptManager getReceiptManager()
/*    */   {
/* 75 */     return this.receiptManager;
/*    */   }
/*    */ 
/*    */   public void setReceiptManager(IReceiptManager receiptManager) {
/* 79 */     this.receiptManager = receiptManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.plugin.OrderDetailReceiptPlugin
 * JD-Core Version:    0.6.0
 */