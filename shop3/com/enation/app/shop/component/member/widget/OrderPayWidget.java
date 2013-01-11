/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("orderpay")
/*     */ @Scope("prototype")
/*     */ public class OrderPayWidget extends AbstractWidget
/*     */ {
/*     */   private IOrderManager orderManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  45 */     if (StringUtil.isEmpty(this.action)) {
/*  46 */       pay();
/*  47 */       return;
/*     */     }
/*     */ 
/*  54 */     if ("savepay".equals(this.action))
/*  55 */       savePay();
/*     */   }
/*     */ 
/*     */   private void savePay()
/*     */   {
/*  64 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  65 */     String ordersn = request.getParameter("ordersn");
/*  66 */     String bank = request.getParameter("bank");
/*  67 */     String paydate = request.getParameter("paydate");
/*  68 */     String sn = request.getParameter("sn");
/*  69 */     String paymoney = request.getParameter("paymoney");
/*  70 */     String remark = request.getParameter("remark");
/*     */ 
/*  73 */     if (StringUtil.isEmpty(paymoney)) {
/*  74 */       showError("付款金额不能为空，请确认后再提交付款信息！");
/*  75 */       return;
/*     */     }
/*  77 */     if (!StringUtil.checkFloat(paymoney, "0+")) {
/*  78 */       showError("付款金额格式不正确，请确认后再提交付款信息！");
/*  79 */       return;
/*     */     }
/*  81 */     if ((ordersn == null) || (ordersn.equals(""))) {
/*  82 */       showError("订单号错误，请确认后再提交付款信息！");
/*  83 */       return;
/*     */     }
/*  85 */     Order order = this.orderManager.get(ordersn);
/*  86 */     if (order == null) {
/*  87 */       showError("订单号错误，请确认后再提交付款信息！");
/*  88 */       return;
/*     */     }
/*  90 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/*  91 */       showError("订单状态错误，请确认后再提交付款信息！");
/*  92 */       return;
/*     */     }
/*     */ 
/*  95 */     PaymentLog paymentLog = new PaymentLog();
/*  96 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  97 */     if (member != null) {
/*  98 */       paymentLog.setMember_id(member.getMember_id());
/*  99 */       paymentLog.setPay_user(member.getUname());
/*     */     } else {
/* 101 */       paymentLog.setPay_user("匿名购买者");
/*     */     }
/* 103 */     paymentLog.setPay_date(DateUtil.getDatelineLong(paydate));
/* 104 */     paymentLog.setRemark(remark);
/* 105 */     paymentLog.setMoney(Double.valueOf(paymoney));
/* 106 */     paymentLog.setOrder_sn(order.getSn());
/* 107 */     paymentLog.setPay_method(bank);
/* 108 */     paymentLog.setSn(sn);
/* 109 */     paymentLog.setOrder_id(order.getOrder_id().intValue());
/* 110 */     this.orderFlowManager.pay(paymentLog, false);
/*     */ 
/* 114 */     String url = null;
/* 115 */     if (member != null)
/* 116 */       url = "member_orderdetail_" + ordersn + ".html";
/*     */     else {
/* 118 */       url = "orderdetail_" + ordersn + ".html";
/*     */     }
/* 120 */     showSuccess("提交付款记录成功！", "查看订单", url);
/*     */   }
/*     */ 
/*     */   private void pay()
/*     */   {
/* 125 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 127 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 129 */     String sn = request.getParameter("sn");
/*     */ 
/* 131 */     if ((sn == null) || (sn.equals(""))) {
/* 132 */       showError("订单号错误，请确认后再提交付款信息！");
/* 133 */       return;
/*     */     }
/* 135 */     Order order = this.orderManager.get(sn);
/* 136 */     if (order == null) {
/* 137 */       showError("订单号错误，请确认后再提交付款信息！");
/* 138 */       return;
/*     */     }
/* 140 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/* 141 */       showError("订单状态错误，请确认后再提交付款信息！");
/* 142 */       return;
/*     */     }
/* 144 */     putData("isLogin", Boolean.valueOf(member != null));
/* 145 */     putData("order", order);
/* 146 */     putData("currentDate", Long.valueOf(System.currentTimeMillis()));
/* 147 */     setActionPageName("pay");
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 152 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 156 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager()
/*     */   {
/* 161 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager)
/*     */   {
/* 166 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.OrderPayWidget
 * JD-Core Version:    0.6.0
 */