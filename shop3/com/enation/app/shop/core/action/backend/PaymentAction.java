/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.model.RefundLog;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PaymentAction extends WWAction
/*     */ {
/*     */   private PaymentLog payment;
/*     */   private RefundLog refund;
/*     */   private Integer orderId;
/*     */   private IOrderManager orderManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private IPaymentManager paymentManager;
/*     */   private Order ord;
/*     */   private List paymentList;
/*     */   private List payLogList;
/*     */   private IOrderReportManager orderReportManager;
/*     */ 
/*     */   public String showPayDialog()
/*     */   {
/*  42 */     this.ord = this.orderManager.get(this.orderId);
/*  43 */     this.payLogList = this.orderReportManager.listPayLogs(this.orderId);
/*  44 */     return "pay_dialog";
/*     */   }
/*     */ 
/*     */   public String showRefundDialog()
/*     */   {
/*  53 */     this.ord = this.orderManager.get(this.orderId);
/*     */ 
/*  55 */     return "refund_dialog";
/*     */   }
/*     */ 
/*     */   public String pay()
/*     */   {
/*     */     try
/*     */     {
/*  63 */       Order order = this.orderFlowManager.payConfirm(this.orderId.intValue());
/*  64 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]已确认收款成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
/*     */     }
/*     */     catch (RuntimeException e) {
/*  67 */       if (this.logger.isDebugEnabled()) {
/*  68 */         this.logger.debug(e);
/*     */       }
/*  70 */       this.json = ("{result:0,message:\"确认收款失败：" + e.getMessage() + "\"}");
/*     */     }
/*  72 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String cancel_pay()
/*     */   {
/*     */     try
/*     */     {
/*  79 */       this.refund.setOrder_id(this.orderId.intValue());
/*  80 */       this.orderFlowManager.refund(this.refund);
/*  81 */       Order order = this.orderManager.get(this.orderId);
/*  82 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]退款成功',payStatus:" + order.getPay_status() + "}");
/*     */     } catch (RuntimeException e) {
/*  84 */       if (this.logger.isDebugEnabled()) {
/*  85 */         this.logger.debug(e);
/*     */       }
/*  87 */       this.json = ("{result:0,message:\"退款失败：" + e.getMessage() + "\"}");
/*     */     }
/*  89 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public PaymentLog getPayment() {
/*  93 */     return this.payment;
/*     */   }
/*     */ 
/*     */   public void setPayment(PaymentLog payment) {
/*  97 */     this.payment = payment;
/*     */   }
/*     */ 
/*     */   public Integer getOrderId() {
/* 101 */     return this.orderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(Integer orderId) {
/* 105 */     this.orderId = orderId;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 109 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 113 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 117 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 121 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager() {
/* 125 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 129 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public Order getOrd() {
/* 133 */     return this.ord;
/*     */   }
/*     */ 
/*     */   public void setOrd(Order ord) {
/* 137 */     this.ord = ord;
/*     */   }
/*     */ 
/*     */   public List getPaymentList() {
/* 141 */     return this.paymentList;
/*     */   }
/*     */ 
/*     */   public void setPaymentList(List paymentList) {
/* 145 */     this.paymentList = paymentList;
/*     */   }
/*     */ 
/*     */   public IOrderReportManager getOrderReportManager()
/*     */   {
/* 150 */     return this.orderReportManager;
/*     */   }
/*     */ 
/*     */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 154 */     this.orderReportManager = orderReportManager;
/*     */   }
/*     */ 
/*     */   public List getPayLogList() {
/* 158 */     return this.payLogList;
/*     */   }
/*     */ 
/*     */   public void setPayLogList(List payLogList) {
/* 162 */     this.payLogList = payLogList;
/*     */   }
/*     */ 
/*     */   public RefundLog getRefund() {
/* 166 */     return this.refund;
/*     */   }
/*     */ 
/*     */   public void setRefund(RefundLog refund) {
/* 170 */     this.refund = refund;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.PaymentAction
 * JD-Core Version:    0.6.0
 */