/*     */ package com.enation.app.shop.core.plugin.payment;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class AbstractPaymentPlugin extends AutoRegisterPlugin
/*     */ {
/*     */   protected IPaymentManager paymentManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private IOrderManager orderManager;
/*     */   private IMemberManager memberManager;
/*     */   private IAdvanceLogsManager advanceLogsManager;
/*  36 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private String callbackUrl;
/*     */   private String showUrl;
/*     */ 
/*     */   protected String getCallBackUrl(PayCfg payCfg)
/*     */   {
/*  45 */     if (this.callbackUrl != null)
/*  46 */       return this.callbackUrl;
/*  47 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  48 */     String serverName = request.getServerName();
/*  49 */     int port = request.getLocalPort();
/*  50 */     String contextPath = request.getContextPath();
/*  51 */     return "http://" + serverName + ":" + port + contextPath + "/payok_callback_" + payCfg.getType() + ".html";
/*     */   }
/*     */ 
/*     */   protected String getReturnUrl(PayCfg payCfg) {
/*  55 */     if (this.callbackUrl != null) return this.callbackUrl;
/*  56 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  57 */     String serverName = request.getServerName();
/*  58 */     int port = request.getLocalPort();
/*  59 */     String contextPath = request.getContextPath();
/*  60 */     return "http://" + serverName + ":" + port + contextPath + "/pay_return_" + payCfg.getType() + ".html";
/*     */   }
/*     */ 
/*     */   protected String formatPrice(Double price)
/*     */   {
/*  69 */     NumberFormat nFormat = NumberFormat.getNumberInstance();
/*  70 */     nFormat.setMaximumFractionDigits(0);
/*  71 */     nFormat.setGroupingUsed(false);
/*  72 */     return nFormat.format(price);
/*     */   }
/*     */ 
/*     */   protected String getShowUrl(Order order)
/*     */   {
/*  80 */     if (this.showUrl != null) return this.showUrl;
/*     */ 
/*  82 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  83 */     String serverName = request.getServerName();
/*  84 */     int port = request.getLocalPort();
/*  85 */     String contextPath = request.getContextPath();
/*  86 */     return "http://" + serverName + ":" + port + contextPath + "/orderdetail_" + order.getSn() + ".html";
/*     */   }
/*     */ 
/*     */   public void setCallBackUrl(String url)
/*     */   {
/*  94 */     this.callbackUrl = url;
/*     */   }
/*     */ 
/*     */   public void setShowUrl(String url)
/*     */   {
/* 102 */     this.showUrl = url;
/*     */   }
/*     */ 
/*     */   protected Map<String, String> getConfigParams()
/*     */   {
/* 110 */     return this.paymentManager.getConfigParams(getId());
/*     */   }
/*     */ 
/*     */   protected void paySuccess(String ordersn, String tradeno)
/*     */   {
/* 119 */     Order order = this.orderManager.get(ordersn);
/* 120 */     if (order.getPay_status().intValue() != 0) {
/* 121 */       return;
/*     */     }
/* 123 */     PaymentLog paymentLog = new PaymentLog();
/* 124 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 125 */     if (member != null) {
/* 126 */       paymentLog.setMember_id(member.getMember_id());
/* 127 */       paymentLog.setPay_user(member.getUname());
/*     */     } else {
/* 129 */       paymentLog.setPay_user("匿名购买者");
/*     */     }
/*     */ 
/* 132 */     paymentLog.setRemark("在线支付");
/* 133 */     paymentLog.setMoney(order.getNeedPayMoney());
/* 134 */     paymentLog.setOrder_sn(order.getSn());
/* 135 */     paymentLog.setPay_method(order.getPayment_name());
/* 136 */     paymentLog.setSn(tradeno);
/* 137 */     paymentLog.setOrder_id(order.getOrder_id().intValue());
/* 138 */     paymentLog.setPay_date(DateUtil.getDatelineLong());
/* 139 */     this.orderFlowManager.pay(paymentLog, true);
/*     */   }
/*     */ 
/*     */   public abstract String getId();
/*     */ 
/*     */   public abstract String getName();
/*     */ 
/*     */   public IPaymentManager getPaymentManager()
/*     */   {
/* 161 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 165 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 169 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 173 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 177 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 181 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 186 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 190 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IAdvanceLogsManager getAdvanceLogsManager() {
/* 194 */     return this.advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
/* 198 */     this.advanceLogsManager = advanceLogsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin
 * JD-Core Version:    0.6.0
 */