/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class MemberDepositWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IPaymentManager paymentManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  36 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  37 */     String action = request.getParameter("action");
/*  38 */     if (StringUtil.isEmpty(action))
/*  39 */       topay();
/*  40 */     else if ("pay".equals(action))
/*  41 */       pay();
/*  42 */     else if ("callback".equals(action))
/*  43 */       callback();
/*     */   }
/*     */ 
/*     */   private void topay()
/*     */   {
/*  53 */     List payList = this.paymentManager.list();
/*  54 */     putData("payList", payList);
/*     */   }
/*     */ 
/*     */   private void pay()
/*     */   {
/*  64 */     Integer paymentId = getIntParam("paymentid");
/*     */ 
/*  66 */     PayCfg payCfg = this.paymentManager.get(paymentId);
/*  67 */     Double money = getDoubleParam("money");
/*     */ 
/*  70 */     Order order = new Order();
/*  71 */     order.setOrder_id(Integer.valueOf(-1));
/*  72 */     order.setSn("预存款充值");
/*  73 */     order.setOrder_amount(money);
/*     */ 
/*  76 */     IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(payCfg.getType());
/*  77 */     AbstractPaymentPlugin payPlugin = (AbstractPaymentPlugin)paymentPlugin;
/*  78 */     payPlugin.setCallBackUrl(getCallBackUrl());
/*     */ 
/*  80 */     String payhtml = paymentPlugin.onPay(payCfg, order);
/*  81 */     this.freeMarkerPaser.setClz(getClass());
/*  82 */     setPageName("pay");
/*  83 */     putData("payhtml", payhtml);
/*     */   }
/*     */ 
/*     */   private Integer getIntParam(String name)
/*     */   {
/*     */     try {
/*  89 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  90 */       return Integer.valueOf(request.getParameter(name));
/*     */     } catch (RuntimeException e) {
/*  92 */       e.printStackTrace();
/*  93 */     }return null;
/*     */   }
/*     */ 
/*     */   private void callback()
/*     */   {
/*  99 */     Integer paymentId = getIntParam("paymentid");
/* 100 */     PayCfg payCfg = this.paymentManager.get(paymentId);
/* 101 */     IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(payCfg.getType());
/* 102 */     String payhtml = paymentPlugin.onCallBack();
/* 103 */     showSuccess("充值成功", "查看我的预存款", "member_advanceLogs.html");
/*     */   }
/*     */ 
/*     */   private Double getDoubleParam(String name) {
/*     */     try {
/* 108 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 109 */       return Double.valueOf(request.getParameter(name));
/*     */     } catch (RuntimeException e) {
/* 111 */       e.printStackTrace();
/* 112 */     }return null;
/*     */   }
/*     */ 
/*     */   protected String getCallBackUrl()
/*     */   {
/* 125 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 126 */     String serverName = request.getServerName();
/* 127 */     String paymentId = request.getParameter("paymentid");
/* 128 */     int port = request.getLocalPort();
/* 129 */     String contextPath = request.getContextPath();
/* 130 */     String orderId = request.getParameter("orderid");
/* 131 */     return "http://" + serverName + ":" + port + contextPath + "/member_deposit.html?action=callback&paymentid=" + paymentId;
/*     */   }
/*     */ 
/*     */   protected String getShowUrl()
/*     */   {
/* 141 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 142 */     String paymentId = request.getParameter("paymentid");
/* 143 */     String serverName = request.getServerName();
/* 144 */     int port = request.getLocalPort();
/* 145 */     String contextPath = request.getContextPath();
/* 146 */     return "http://" + serverName + ":" + port + contextPath + "/widget?type=paywidget&action=show&paymentid=" + paymentId;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager()
/*     */   {
/* 152 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 156 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberDepositWidget
 * JD-Core Version:    0.6.0
 */