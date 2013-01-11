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
/*    */ public class OrderDetailShipLogPlugin extends AutoRegisterPlugin
/*    */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IOrderReportManager orderReportManager;
/*    */ 
/*    */   public boolean canBeExecute(Order order)
/*    */   {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   public String execute()
/*    */   {
/* 41 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 42 */     freeMarkerPaser.setClz(getClass());
/*    */ 
/* 44 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 45 */     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);
/*    */ 
/* 47 */     List shipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(1));
/* 48 */     List reshipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(2));
/* 49 */     List chshipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(3));
/*    */ 
/* 51 */     freeMarkerPaser.putData("shipLogList", shipLogList);
/* 52 */     freeMarkerPaser.putData("reshipLogList", reshipLogList);
/* 53 */     freeMarkerPaser.putData("chshipLogList", chshipLogList);
/*    */ 
/* 55 */     freeMarkerPaser.setPageName("shiplog_list");
/* 56 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String onShowOrderDetailHtml(Order order)
/*    */   {
/* 62 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 63 */     int orderId = order.getOrder_id().intValue();
/* 64 */     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
/* 65 */     freeMarkerPaser.setPageName("shiplog");
/* 66 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Order order)
/*    */   {
/* 72 */     return "发退货记录";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 78 */     return 7;
/*    */   }
/*    */ 
/*    */   public IOrderReportManager getOrderReportManager()
/*    */   {
/* 83 */     return this.orderReportManager;
/*    */   }
/*    */ 
/*    */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 87 */     this.orderReportManager = orderReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.log.OrderDetailShipLogPlugin
 * JD-Core Version:    0.6.0
 */