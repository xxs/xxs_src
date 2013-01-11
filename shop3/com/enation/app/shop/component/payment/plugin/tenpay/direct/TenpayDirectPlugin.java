/*     */ package com.enation.app.shop.component.payment.plugin.tenpay.direct;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.tenpay.PayRequestHandler;
/*     */ import com.tenpay.PayResponseHandler;
/*     */ import com.tenpay.util.TenpayUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class TenpayDirectPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   public String onCallBack()
/*     */   {
/*  32 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  33 */     HttpServletResponse response = ThreadContextHolder.getHttpResponse();
/*     */ 
/*  35 */     Map params = getConfigParams();
/*     */ 
/*  37 */     String key = (String)params.get("tenpaykey");
/*     */ 
/*  40 */     PayResponseHandler resHandler = new PayResponseHandler(request, response);
/*     */ 
/*  42 */     resHandler.setKey(key);
/*     */ 
/*  45 */     if (resHandler.isTenpaySign())
/*     */     {
/*  47 */       String transaction_id = resHandler.getParameter("transaction_id");
/*     */ 
/*  50 */       String total_fee = resHandler.getParameter("total_fee");
/*     */ 
/*  53 */       String pay_result = resHandler.getParameter("pay_result");
/*     */ 
/*  55 */       if ("0".equals(pay_result))
/*     */       {
/*  68 */         String strHtml = "<html><head>\r\n<meta name=\"TENCENT_ONLINE_PAYMENT\" content=\"China TENCENT\">\r\n</head><body></body></html>";
/*     */ 
/*  71 */         return strHtml;
/*     */       }
/*     */ 
/*  74 */       System.out.println("支付失败");
/*     */     }
/*     */     else
/*     */     {
/*  78 */       System.out.println("认证签名失败");
/*     */     }
/*     */ 
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/*  88 */     Map params = this.paymentManager.getConfigParams(getId());
/*     */ 
/*  90 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  91 */     HttpServletResponse response = ThreadContextHolder.getHttpResponse();
/*     */ 
/* 102 */     String bargainor_id = (String)params.get("tenpaybid");
/*     */ 
/* 105 */     String key = (String)params.get("tenpaykey");
/*     */ 
/* 108 */     String return_url = getCallBackUrl(payCfg);
/*     */ 
/* 111 */     String currTime = TenpayUtil.getCurrTime();
/*     */ 
/* 114 */     String strDate = currTime.substring(0, 8);
/*     */ 
/* 117 */     String strTime = currTime.substring(8, currTime.length());
/*     */ 
/* 120 */     String strRandom = TenpayUtil.buildRandom(4) + "";
/*     */ 
/* 123 */     String strReq = strTime + strRandom;
/*     */ 
/* 126 */     String sp_billno = strReq;
/*     */ 
/* 129 */     String transaction_id = bargainor_id + strDate + strReq;
/*     */ 
/* 132 */     PayRequestHandler reqHandler = new PayRequestHandler(request, response);
/*     */ 
/* 135 */     reqHandler.setKey(key);
/*     */ 
/* 138 */     reqHandler.init();
/*     */ 
/* 143 */     reqHandler.setParameter("cs", "utf-8");
/* 144 */     reqHandler.setParameter("bargainor_id", bargainor_id);
/* 145 */     reqHandler.setParameter("sp_billno", order.getSn());
/* 146 */     reqHandler.setParameter("transaction_id", transaction_id);
/* 147 */     reqHandler.setParameter("return_url", return_url);
/* 148 */     reqHandler.setParameter("desc", "订单编号：" + order.getSn());
/* 149 */     reqHandler.setParameter("total_fee", formatPrice(Double.valueOf(order.getNeedPayMoney().doubleValue() * 100.0D)));
/*     */ 
/* 152 */     reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
/*     */ 
/* 155 */     String requestUrl = "";
/*     */     try {
/* 157 */       requestUrl = reqHandler.getRequestURL();
/* 158 */       reqHandler.doSend();
/*     */     }
/*     */     catch (IOException e) {
/* 161 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 165 */     String debuginfo = reqHandler.getDebugInfo();
/*     */ 
/* 167 */     return requestUrl;
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 181 */     return "tenpayDirectPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 187 */     return "财付通即时到账接口";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.tenpay.direct.TenpayDirectPlugin
 * JD-Core Version:    0.6.0
 */