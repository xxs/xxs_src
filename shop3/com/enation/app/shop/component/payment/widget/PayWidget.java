/*     */ package com.enation.app.shop.component.payment.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("paywidget")
/*     */ @Scope("prototype")
/*     */ public class PayWidget extends AbstractWidget
/*     */ {
/*     */   private IPaymentManager paymentManager;
/*     */   private IOrderManager orderManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  44 */     setPageName("pay");
/*  45 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  46 */     String url = RequestUtil.getRequestUrl(httpRequest);
/*  47 */     String[] actions = getAction(url);
/*  48 */     if (null == actions) {
/*  49 */       showError("参数不正确");
/*     */     }
/*     */     else
/*     */     {
/*  53 */       String action = actions[0];
/*  54 */       String pluginid = actions[1];
/*     */ 
/*  56 */       if ("to".equals(action)) {
/*  57 */         pay();
/*     */       }
/*  59 */       else if ("callback".equals(action)) {
/*  60 */         callback(pluginid);
/*     */       }
/*  62 */       else if ("return".equals(action))
/*  63 */         payReturn(pluginid);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void pay()
/*     */   {
/*  73 */     Integer orderId = getIntParam("orderid");
/*  74 */     Integer paymentId = getIntParam("paymentid");
/*     */ 
/*  76 */     Order order = this.orderManager.get(orderId);
/*  77 */     PayCfg payCfg = this.paymentManager.get(paymentId);
/*     */ 
/*  79 */     IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(payCfg.getType());
/*  80 */     String payhtml = paymentPlugin.onPay(payCfg, order);
/*     */ 
/*  83 */     if (order.getPayment_id().intValue() != paymentId.intValue()) {
/*  84 */       this.orderManager.updatePayMethod(orderId.intValue(), paymentId.intValue(), payCfg.getType(), payCfg.getName());
/*     */     }
/*  86 */     showJson(payhtml);
/*     */   }
/*     */ 
/*     */   private void callback(String pluginid)
/*     */   {
/*  91 */     IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(pluginid);
/*  92 */     String payhtml = paymentPlugin.onCallBack();
/*  93 */     showJson(payhtml);
/*     */   }
/*     */ 
/*     */   private void payReturn(String paluginid)
/*     */   {
/*     */     try {
/*  99 */       IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(paluginid);
/* 100 */       String ordersn = paymentPlugin.onReturn();
/* 101 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 102 */       if (member == null) {
/* 103 */         showSuccess("支付成功", "查看此订单", "orderdetail_" + ordersn + ".html");
/*     */       }
/*     */       else {
/* 106 */         showSuccess("支付成功", "查看此订单", "member_orderdetail_" + ordersn + ".html");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 112 */       this.logger.error("支付发生错误", e);
/* 113 */       showError("支付失败" + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private String[] getAction(String url) {
/* 118 */     String action = null;
/* 119 */     String pluginid = null;
/* 120 */     String pattern = "/(\\w+)_(\\w+)_(\\w+).html(.*)";
/* 121 */     Pattern p = Pattern.compile(pattern, 34);
/* 122 */     Matcher m = p.matcher(url);
/* 123 */     if (m.find()) {
/* 124 */       action = m.replaceAll("$2");
/* 125 */       pluginid = m.replaceAll("$3");
/* 126 */       return new String[] { action, pluginid };
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   private Integer getIntParam(String name)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/* 136 */       return Integer.valueOf(httpRequest.getParameter(name));
/*     */     } catch (RuntimeException e) {
/* 138 */       e.printStackTrace();
/* 139 */     }return null;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager()
/*     */   {
/* 144 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 148 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 152 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 156 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 161 */     String action = null;
/* 162 */     String pluginid = null;
/* 163 */     String pattern = "/pay(\\w+)_(\\w+).html";
/* 164 */     Pattern p = Pattern.compile(pattern, 34);
/* 165 */     Matcher m = p.matcher("/payReturn_alipay.html");
/* 166 */     if (m.find()) {
/* 167 */       action = m.replaceAll("$1");
/* 168 */       pluginid = m.replaceAll("$2");
/*     */     }
/*     */ 
/* 173 */     System.out.println(action);
/* 174 */     System.out.println(pluginid);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.widget.PayWidget
 * JD-Core Version:    0.6.0
 */