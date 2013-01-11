/*    */ package com.enation.app.shop.component.ordercore.plugin.log;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
/*    */ import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
/*    */ import com.enation.app.shop.core.service.IOrderReportManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class OrderDetailPayLogPlugin extends AutoRegisterPlugin
/*    */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IOrderReportManager orderReportManager;
/*    */ 
/*    */   public boolean canBeExecute(Order order)
/*    */   {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   public String getTabName(Order order)
/*    */   {
/* 40 */     return "收退款记录";
/*    */   }
/*    */ 
/*    */   public String onShowOrderDetailHtml(Order order)
/*    */   {
/* 45 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 46 */     int orderId = order.getOrder_id().intValue();
/* 47 */     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
/* 48 */     freeMarkerPaser.setPageName("paylog");
/* 49 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String execute()
/*    */   {
/* 59 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 60 */     freeMarkerPaser.setClz(getClass());
/*    */ 
/* 62 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 63 */     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);
/*    */ 
/* 65 */     List payLogList = this.orderReportManager.listPayLogs(Integer.valueOf(orderId));
/* 66 */     List refundList = this.orderReportManager.listRefundLogs(Integer.valueOf(orderId));
/*    */ 
/* 68 */     freeMarkerPaser.putData("payLogList", payLogList);
/* 69 */     freeMarkerPaser.putData("refundList", refundList);
/*    */ 
/* 71 */     freeMarkerPaser.setPageName("paylog_list");
/* 72 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 81 */     return 5;
/*    */   }
/*    */ 
/*    */   public IOrderReportManager getOrderReportManager()
/*    */   {
/* 86 */     return this.orderReportManager;
/*    */   }
/*    */ 
/*    */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 90 */     this.orderReportManager = orderReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.log.OrderDetailPayLogPlugin
 * JD-Core Version:    0.6.0
 */