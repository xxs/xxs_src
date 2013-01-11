/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.DeliveryItem;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class OrderReportAction extends WWAction
/*     */ {
/*     */   private IOrderReportManager orderReportManager;
/*     */   private String order;
/*     */   private int id;
/*     */   private PaymentLog payment;
/*     */   private Delivery delivery;
/*     */   private List<DeliveryItem> listDeliveryItem;
/*     */   private IPaymentManager paymentManager;
/*     */   private List paymentList;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private int orderId;
/*     */   private List paymentLogsList;
/*     */   private List refundLogsList;
/*     */ 
/*     */   public String paymentList()
/*     */   {
/*  43 */     this.webpage = this.orderReportManager.listPayment(getPage(), getPageSize(), this.order);
/*  44 */     return "paymentList";
/*     */   }
/*     */ 
/*     */   public String paymentLogs() {
/*  48 */     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
/*  49 */     this.paymentLogsList = this.orderReportManager.listPayLogs(Integer.valueOf(this.payment.getOrder_id()));
/*  50 */     return "paymentLogs";
/*     */   }
/*     */ 
/*     */   public String paymentDetail() {
/*  54 */     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
/*  55 */     this.paymentList = this.paymentManager.list();
/*  56 */     return "paymentDetail";
/*     */   }
/*     */ 
/*     */   public String refundList() {
/*  60 */     this.webpage = this.orderReportManager.listRefund(getPage(), getPageSize(), this.order);
/*  61 */     return "refundList";
/*     */   }
/*     */ 
/*     */   public String refundLogs() {
/*  65 */     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
/*  66 */     this.refundLogsList = this.orderReportManager.listRefundLogs(Integer.valueOf(this.payment.getOrder_id()));
/*  67 */     return "refundLogs";
/*     */   }
/*     */ 
/*     */   public String refundDetail() {
/*  71 */     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
/*  72 */     this.paymentList = this.paymentManager.list();
/*  73 */     return "refundDetail";
/*     */   }
/*     */ 
/*     */   public String allocationList()
/*     */   {
/*  82 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  83 */     String depotid = request.getParameter("depotid");
/*  84 */     if (depotid == null) {
/*  85 */       depotid = "0";
/*     */     }
/*  87 */     this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 0, getPage(), getPageSize());
/*  88 */     return "allocationList";
/*     */   }
/*     */ 
/*     */   public String allocationedList()
/*     */   {
/*  95 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  96 */     String depotid = request.getParameter("depotid");
/*  97 */     if (depotid == null) {
/*  98 */       depotid = "0";
/*     */     }
/* 100 */     this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 1, getPage(), getPageSize());
/* 101 */     return "allocationList";
/*     */   }
/*     */ 
/*     */   public String shippingList()
/*     */   {
/* 107 */     this.webpage = this.orderReportManager.listShipping(getPage(), getPageSize(), this.order);
/* 108 */     return "shippingList";
/*     */   }
/*     */ 
/*     */   public String shippingDetail() {
/* 112 */     this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
/* 113 */     this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
/* 114 */     return "shippingDetail";
/*     */   }
/*     */ 
/*     */   public String returnedList() {
/* 118 */     this.webpage = this.orderReportManager.listReturned(getPage(), getPageSize(), this.order);
/* 119 */     return "returnedList";
/*     */   }
/*     */ 
/*     */   public String returnedDetail() {
/* 123 */     this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
/* 124 */     this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
/* 125 */     return "returnedDetail";
/*     */   }
/*     */ 
/*     */   public IOrderReportManager getOrderReportManager()
/*     */   {
/* 133 */     return this.orderReportManager;
/*     */   }
/*     */ 
/*     */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 137 */     this.orderReportManager = orderReportManager;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 141 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 145 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public int getId() {
/* 149 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/* 153 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public PaymentLog getPayment() {
/* 157 */     return this.payment;
/*     */   }
/*     */ 
/*     */   public void setPayment(PaymentLog payment) {
/* 161 */     this.payment = payment;
/*     */   }
/*     */ 
/*     */   public Delivery getDelivery() {
/* 165 */     return this.delivery;
/*     */   }
/*     */ 
/*     */   public void setDelivery(Delivery delivery) {
/* 169 */     this.delivery = delivery;
/*     */   }
/*     */ 
/*     */   public List<DeliveryItem> getListDeliveryItem() {
/* 173 */     return this.listDeliveryItem;
/*     */   }
/*     */ 
/*     */   public void setListDeliveryItem(List<DeliveryItem> listDeliveryItem) {
/* 177 */     this.listDeliveryItem = listDeliveryItem;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager() {
/* 181 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 185 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public List getPaymentList() {
/* 189 */     return this.paymentList;
/*     */   }
/*     */ 
/*     */   public void setPaymentList(List paymentList) {
/* 193 */     this.paymentList = paymentList;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 197 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 201 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public int getOrderId() {
/* 205 */     return this.orderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(int orderId) {
/* 209 */     this.orderId = orderId;
/*     */   }
/*     */ 
/*     */   public List getPaymentLogsList() {
/* 213 */     return this.paymentLogsList;
/*     */   }
/*     */ 
/*     */   public void setPaymentLogsList(List paymentLogsList) {
/* 217 */     this.paymentLogsList = paymentLogsList;
/*     */   }
/*     */ 
/*     */   public List getRefundLogsList() {
/* 221 */     return this.refundLogsList;
/*     */   }
/*     */ 
/*     */   public void setRefundLogsList(List refundLogsList) {
/* 225 */     this.refundLogsList = refundLogsList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderReportAction
 * JD-Core Version:    0.6.0
 */