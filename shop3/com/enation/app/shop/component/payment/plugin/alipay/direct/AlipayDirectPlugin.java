/*     */ package com.enation.app.shop.component.payment.plugin.alipay.direct;
/*     */ 
/*     */ import com.alipay.config.AlipayConfig;
/*     */ import com.alipay.util.AlipayNotify;
/*     */ import com.alipay.util.AlipaySubmit;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class AlipayDirectPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   public String onCallBack()
/*     */   {
/*  34 */     Map cfgparams = this.paymentManager.getConfigParams(getId());
/*  35 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  36 */     String key = (String)cfgparams.get("key");
/*  37 */     String partner = (String)cfgparams.get("partner");
/*  38 */     String encoding = (String)cfgparams.get("callback_encoding");
/*     */ 
/*  41 */     Map params = new HashMap();
/*  42 */     Map requestParams = request.getParameterMap();
/*  43 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/*  44 */       String name = (String)iter.next();
/*  45 */       String[] values = (String[])(String[])requestParams.get(name);
/*  46 */       String valueStr = "";
/*  47 */       for (int i = 0; i < values.length; i++) {
/*  48 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/*  52 */       if (!StringUtil.isEmpty(encoding)) {
/*  53 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/*  55 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/*  62 */     String trade_no = request.getParameter("trade_no");
/*  63 */     String order_no = request.getParameter("out_trade_no");
/*  64 */     String total_fee = request.getParameter("total_fee");
/*     */ 
/*  66 */     String subject = request.getParameter("subject");
/*  67 */     if (!StringUtil.isEmpty(encoding)) {
/*  68 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/*  70 */     String body = "";
/*  71 */     if (request.getParameter("body") != null) {
/*  72 */       body = request.getParameter("body");
/*  73 */       if (!StringUtil.isEmpty(encoding)) {
/*  74 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/*     */ 
/*  78 */     String buyer_email = request.getParameter("buyer_email");
/*  79 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/*  85 */     if (AlipayNotify.callbackverify(params, key, partner))
/*     */     {
/*  88 */       paySuccess(order_no, trade_no);
/*     */ 
/*  91 */       if ((trade_status.equals("TRADE_FINISHED")) || (trade_status.equals("TRADE_SUCCESS")))
/*     */       {
/*  95 */         this.logger.info("异步校验订单[" + order_no + "]成功");
/*  96 */         return "success";
/*     */       }
/*  98 */       this.logger.info("异步校验订单[" + order_no + "]成功");
/*  99 */       return "success";
/*     */     }
/*     */ 
/* 106 */     return "fail";
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/* 114 */     Map params = this.paymentManager.getConfigParams(getId());
/* 115 */     String seller_email = (String)params.get("seller_email");
/* 116 */     String partner = (String)params.get("partner");
/* 117 */     String key = (String)params.get("key");
/*     */ 
/* 119 */     String show_url = getShowUrl(order);
/* 120 */     String notify_url = getCallBackUrl(payCfg);
/* 121 */     String return_url = getReturnUrl(payCfg);
/*     */ 
/* 130 */     String out_trade_no = order.getSn();
/*     */ 
/* 132 */     String subject = "订单:" + order.getSn();
/*     */ 
/* 134 */     String body = "网店订单";
/*     */ 
/* 136 */     String total_fee = String.valueOf(order.getNeedPayMoney());
/*     */ 
/* 142 */     String paymethod = "";
/*     */ 
/* 144 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 147 */     String defaultbank = request.getParameter("bank");
/* 148 */     defaultbank = defaultbank == null ? "" : defaultbank;
/*     */ 
/* 151 */     Pattern pattern = Pattern.compile("[0-9]*");
/* 152 */     if (pattern.matcher(defaultbank).matches()) {
/* 153 */       defaultbank = "";
/*     */     }
/*     */ 
/* 159 */     String anti_phishing_key = "";
/*     */ 
/* 161 */     String exter_invoke_ip = "";
/*     */ 
/* 174 */     String extra_common_param = "";
/*     */ 
/* 176 */     String buyer_email = order.getShip_email();
/*     */ 
/* 182 */     String royalty_type = "";
/*     */ 
/* 184 */     String royalty_parameters = "";
/*     */ 
/* 196 */     Map sParaTemp = new HashMap();
/* 197 */     sParaTemp.put("payment_type", "1");
/* 198 */     sParaTemp.put("out_trade_no", out_trade_no);
/* 199 */     sParaTemp.put("subject", subject);
/* 200 */     sParaTemp.put("body", body);
/* 201 */     sParaTemp.put("total_fee", total_fee);
/* 202 */     sParaTemp.put("show_url", show_url);
/* 203 */     sParaTemp.put("paymethod", paymethod);
/* 204 */     sParaTemp.put("defaultbank", defaultbank);
/* 205 */     sParaTemp.put("anti_phishing_key", anti_phishing_key);
/* 206 */     sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
/* 207 */     sParaTemp.put("extra_common_param", extra_common_param);
/* 208 */     sParaTemp.put("buyer_email", buyer_email);
/* 209 */     sParaTemp.put("royalty_type", royalty_type);
/* 210 */     sParaTemp.put("royalty_parameters", royalty_parameters);
/*     */ 
/* 214 */     sParaTemp.put("service", "create_direct_pay_by_user");
/* 215 */     sParaTemp.put("partner", partner);
/* 216 */     sParaTemp.put("return_url", return_url);
/* 217 */     sParaTemp.put("notify_url", notify_url);
/* 218 */     sParaTemp.put("seller_email", seller_email);
/* 219 */     sParaTemp.put("_input_charset", AlipayConfig.input_charset);
/*     */ 
/* 223 */     return AlipaySubmit.buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", key);
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 233 */     Map cfgparams = this.paymentManager.getConfigParams(getId());
/* 234 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 235 */     String key = (String)cfgparams.get("key");
/* 236 */     String partner = (String)cfgparams.get("partner");
/* 237 */     String encoding = (String)cfgparams.get("return_encoding");
/*     */ 
/* 240 */     Map params = new HashMap();
/* 241 */     Map requestParams = request.getParameterMap();
/* 242 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 243 */       String name = (String)iter.next();
/* 244 */       String[] values = (String[])(String[])requestParams.get(name);
/* 245 */       String valueStr = "";
/* 246 */       for (int i = 0; i < values.length; i++) {
/* 247 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/* 251 */       if (!StringUtil.isEmpty(encoding)) {
/* 252 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/* 254 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 260 */     String trade_no = request.getParameter("trade_no");
/* 261 */     String order_no = request.getParameter("out_trade_no");
/* 262 */     String total_fee = request.getParameter("total_fee");
/* 263 */     String subject = request.getParameter("subject");
/*     */ 
/* 265 */     if (!StringUtil.isEmpty(encoding)) {
/* 266 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/*     */ 
/* 269 */     String body = "";
/* 270 */     if (request.getParameter("body") != null) {
/* 271 */       body = request.getParameter("body");
/* 272 */       if (!StringUtil.isEmpty(encoding)) {
/* 273 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/*     */ 
/* 277 */     String buyer_email = request.getParameter("buyer_email");
/* 278 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/* 283 */     boolean verify_result = AlipayNotify.returnverify(params, key, partner);
/*     */ 
/* 285 */     if (verify_result)
/*     */     {
/* 287 */       paySuccess(order_no, trade_no);
/* 288 */       return order_no;
/*     */     }
/*     */ 
/* 291 */     throw new RuntimeException("验证失败");
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 302 */     return "alipayDirectPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 308 */     return "支付宝即时到帐接口";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.alipay.direct.AlipayDirectPlugin
 * JD-Core Version:    0.6.0
 */