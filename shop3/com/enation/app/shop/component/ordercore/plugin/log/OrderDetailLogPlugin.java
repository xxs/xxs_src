/*    */ package com.enation.app.shop.component.ordercore.plugin.log;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
/*    */ import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
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
/*    */ public class OrderDetailLogPlugin extends AutoRegisterPlugin
/*    */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */ 
/*    */   public boolean canBeExecute(Order order)
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public String execute()
/*    */   {
/* 47 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 48 */     freeMarkerPaser.setClz(getClass());
/*    */ 
/* 50 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 51 */     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);
/*    */ 
/* 53 */     List logList = this.orderManager.listLogs(Integer.valueOf(orderId));
/* 54 */     freeMarkerPaser.putData("logList", logList);
/*    */ 
/* 57 */     freeMarkerPaser.setPageName("log_list");
/* 58 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String onShowOrderDetailHtml(Order order)
/*    */   {
/* 65 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 66 */     int orderId = order.getOrder_id().intValue();
/* 67 */     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
/* 68 */     freeMarkerPaser.setPageName("log");
/*    */ 
/* 70 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Order order)
/*    */   {
/* 76 */     return "订单日志";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 82 */     return 3;
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 87 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 91 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.log.OrderDetailLogPlugin
 * JD-Core Version:    0.6.0
 */