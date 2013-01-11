/*     */ package com.enation.app.shop.component.ordercore.plugin.allocation;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Depot;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
/*     */ import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IOrderAllocationManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class OrderDetailAllocationPlugin extends AutoRegisterPlugin
/*     */   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
/*     */ {
/*     */   private IOrderAllocationManager orderAllocationManager;
/*     */   private IOrderManager orderManager;
/*     */   private IDepotManager depotManager;
/*     */ 
/*     */   public boolean canBeExecute(Order order)
/*     */   {
/*  41 */     return order.getShip_status().intValue() != 0;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/*  49 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  50 */     freeMarkerPaser.setClz(getClass());
/*     */ 
/*  52 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  53 */     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);
/*     */ 
/*  55 */     long all_time = this.orderManager.get(Integer.valueOf(orderId)).getAllocation_time().longValue();
/*  56 */     String all_depotname = this.depotManager.get(this.orderManager.get(Integer.valueOf(orderId)).getDepotid().intValue()).getName();
/*  57 */     List allocationList = this.orderAllocationManager.listAllocation(orderId);
/*     */ 
/*  60 */     freeMarkerPaser.putData("all_time", Long.valueOf(all_time));
/*  61 */     freeMarkerPaser.putData("all_depotname", all_depotname);
/*  62 */     freeMarkerPaser.putData("allocationList", allocationList);
/*     */ 
/*  64 */     freeMarkerPaser.setPageName("allocation_list");
/*  65 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String onShowOrderDetailHtml(Order order)
/*     */   {
/*  71 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  72 */     int orderId = order.getOrder_id().intValue();
/*  73 */     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
/*  74 */     freeMarkerPaser.setPageName("allocation");
/*     */ 
/*  76 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getTabName(Order order)
/*     */   {
/*  83 */     return "配货信息";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/*  90 */     return 9;
/*     */   }
/*     */ 
/*     */   public IOrderAllocationManager getOrderAllocationManager() {
/*  94 */     return this.orderAllocationManager;
/*     */   }
/*     */ 
/*     */   public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager)
/*     */   {
/*  99 */     this.orderAllocationManager = orderAllocationManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 103 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 107 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IDepotManager getDepotManager() {
/* 111 */     return this.depotManager;
/*     */   }
/*     */ 
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 115 */     this.depotManager = depotManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.allocation.OrderDetailAllocationPlugin
 * JD-Core Version:    0.6.0
 */