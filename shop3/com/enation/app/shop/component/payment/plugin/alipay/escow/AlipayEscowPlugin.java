/*     */ package com.enation.app.shop.component.payment.plugin.alipay.escow;
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
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class AlipayEscowPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*  25 */   private static String paygateway = "https://www.alipay.com/cooperate/gateway.do?";
/*     */ 
/*     */   public String onCallBack()
/*     */   {
/*  29 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  30 */     Map configparams = getConfigParams();
/*  31 */     String partner = (String)configparams.get("partner");
/*  32 */     String key = (String)configparams.get("key");
/*  33 */     String encoding = (String)configparams.get("callback_encoding");
/*     */ 
/*  36 */     Map params = new HashMap();
/*  37 */     Map requestParams = request.getParameterMap();
/*  38 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/*  39 */       String name = (String)iter.next();
/*  40 */       String[] values = (String[])(String[])requestParams.get(name);
/*  41 */       String valueStr = "";
/*  42 */       for (int i = 0; i < values.length; i++) {
/*  43 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/*  47 */       if (!StringUtil.isEmpty(encoding)) {
/*  48 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/*  50 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/*  57 */     String trade_no = request.getParameter("trade_no");
/*  58 */     String order_no = request.getParameter("out_trade_no");
/*  59 */     String total_fee = request.getParameter("price");
/*  60 */     String subject = request.getParameter("subject");
/*  61 */     if (!StringUtil.isEmpty(encoding)) {
/*  62 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/*  64 */     String body = "";
/*  65 */     if (request.getParameter("body") != null) {
/*  66 */       body = request.getParameter("body");
/*  67 */       if (!StringUtil.isEmpty(encoding)) {
/*  68 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/*  71 */     String buyer_email = request.getParameter("buyer_email");
/*  72 */     String receive_name = "";
/*  73 */     if (request.getParameter("receive_name") != null) {
/*  74 */       receive_name = request.getParameter("receive_name");
/*  75 */       if (!StringUtil.isEmpty(encoding)) {
/*  76 */         receive_name = StringUtil.to(receive_name, encoding);
/*     */       }
/*     */     }
/*  79 */     String receive_address = "";
/*  80 */     if (request.getParameter("receive_address") != null) {
/*  81 */       receive_address = request.getParameter("receive_address");
/*  82 */       if (!StringUtil.isEmpty(encoding)) {
/*  83 */         receive_address = StringUtil.to(receive_address, encoding);
/*     */       }
/*     */     }
/*     */ 
/*  87 */     String receive_zip = "";
/*  88 */     if (request.getParameter("receive_zip") != null) {
/*  89 */       receive_zip = request.getParameter("receive_zip");
/*  90 */       if (!StringUtil.isEmpty(encoding)) {
/*  91 */         receive_zip = StringUtil.to(receive_zip, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  96 */     String receive_phone = "";
/*  97 */     if (request.getParameter("receive_phone") != null) {
/*  98 */       receive_phone = request.getParameter("receive_phone");
/*  99 */       if (!StringUtil.isEmpty(encoding)) {
/* 100 */         receive_phone = StringUtil.to(receive_phone, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     String receive_mobile = "";
/* 106 */     if (request.getParameter("receive_mobile") != null) {
/* 107 */       receive_mobile = request.getParameter("receive_mobile");
/* 108 */       if (!StringUtil.isEmpty(encoding)) {
/* 109 */         receive_mobile = StringUtil.to(receive_mobile, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/* 118 */     if (AlipayNotify.callbackverify(params, key, partner))
/*     */     {
/* 120 */       paySuccess(order_no, trade_no);
/*     */ 
/* 128 */       if (trade_status.equals("WAIT_BUYER_PAY"))
/*     */       {
/* 135 */         return "success";
/* 136 */       }if (trade_status.equals("WAIT_SELLER_SEND_GOODS"))
/*     */       {
/* 142 */         return "success";
/* 143 */       }if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS"))
/*     */       {
/* 149 */         return "success";
/* 150 */       }if (trade_status.equals("TRADE_FINISHED"))
/*     */       {
/* 156 */         return "success";
/*     */       }
/*     */ 
/* 159 */       return "success";
/*     */     }
/*     */ 
/* 166 */     return "fail";
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/* 175 */     Map data = new HashMap();
/* 176 */     Map params = getConfigParams();
/*     */ 
/* 178 */     String out_trade_no = order.getSn();
/* 179 */     String partner = (String)params.get("partner");
/* 180 */     String key = (String)params.get("key");
/* 181 */     String seller_email = (String)params.get("seller_email");
/*     */ 
/* 183 */     System.out.println("out_trade_no[" + out_trade_no + "]-partner[" + partner + "]-key[" + key + "]-seller_email[" + seller_email + "]");
/*     */ 
/* 185 */     String show_url = getShowUrl(order);
/* 186 */     String notify_url = getCallBackUrl(payCfg);
/* 187 */     String return_url = getReturnUrl(payCfg);
/*     */ 
/* 193 */     String subject = "订单:" + order.getSn();
/*     */ 
/* 195 */     String body = "网店订单";
/*     */ 
/* 199 */     String price = String.valueOf(order.getNeedPayMoney());
/*     */ 
/* 202 */     String logistics_fee = "0.00";
/*     */ 
/* 204 */     String logistics_type = "EXPRESS";
/*     */ 
/* 206 */     String logistics_payment = "SELLER_PAY";
/*     */ 
/* 209 */     String quantity = "1";
/*     */ 
/* 216 */     String receive_name = order.getShip_name();
/* 217 */     String receive_address = order.getShip_addr();
/* 218 */     String receive_zip = order.getShip_zip();
/* 219 */     String receive_phone = order.getShip_tel();
/* 220 */     String receive_mobile = order.getShip_email();
/*     */ 
/* 227 */     Map sParaTemp = new HashMap();
/* 228 */     sParaTemp.put("payment_type", "1");
/* 229 */     sParaTemp.put("show_url", show_url);
/* 230 */     sParaTemp.put("out_trade_no", out_trade_no);
/* 231 */     sParaTemp.put("subject", subject);
/* 232 */     sParaTemp.put("body", body);
/* 233 */     sParaTemp.put("price", price);
/* 234 */     sParaTemp.put("logistics_fee", logistics_fee);
/* 235 */     sParaTemp.put("logistics_type", logistics_type);
/* 236 */     sParaTemp.put("logistics_payment", logistics_payment);
/* 237 */     sParaTemp.put("quantity", quantity);
/* 238 */     sParaTemp.put("receive_name", receive_name);
/* 239 */     sParaTemp.put("receive_address", receive_address);
/* 240 */     sParaTemp.put("receive_zip", receive_zip);
/* 241 */     sParaTemp.put("receive_phone", receive_phone);
/* 242 */     sParaTemp.put("receive_mobile", receive_mobile);
/*     */ 
/* 244 */     sParaTemp.put("service", "create_partner_trade_by_buyer");
/* 245 */     sParaTemp.put("partner", partner);
/* 246 */     sParaTemp.put("return_url", return_url);
/* 247 */     sParaTemp.put("notify_url", notify_url);
/* 248 */     sParaTemp.put("seller_email", seller_email);
/* 249 */     sParaTemp.put("_input_charset", AlipayConfig.input_charset);
/*     */ 
/* 251 */     return AlipaySubmit.buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", key);
/*     */   }
/*     */ 
/*     */   public void register()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 266 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 271 */     return "alipayEscowPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 276 */     return "支付宝担保交易接口";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 281 */     return "payment";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 286 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 297 */     Map cfgparams = this.paymentManager.getConfigParams(getId());
/* 298 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 300 */     String key = (String)cfgparams.get("key");
/* 301 */     String partner = (String)cfgparams.get("partner");
/* 302 */     String encoding = (String)cfgparams.get("return_encoding");
/*     */ 
/* 306 */     Map params = new HashMap();
/* 307 */     Map requestParams = request.getParameterMap();
/* 308 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 309 */       String name = (String)iter.next();
/* 310 */       String[] values = (String[])(String[])requestParams.get(name);
/* 311 */       String valueStr = "";
/* 312 */       for (int i = 0; i < values.length; i++) {
/* 313 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/* 316 */       if (StringUtil.isEmpty(encoding)) {
/* 317 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/* 319 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 325 */     String trade_no = request.getParameter("trade_no");
/* 326 */     String order_no = request.getParameter("out_trade_no");
/* 327 */     String total_fee = request.getParameter("price");
/*     */ 
/* 329 */     String subject = request.getParameter("subject");
/* 330 */     if (!StringUtil.isEmpty(encoding)) {
/* 331 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/* 333 */     String body = "";
/* 334 */     if (request.getParameter("body") != null) {
/* 335 */       body = request.getParameter("body");
/* 336 */       if (!StringUtil.isEmpty(encoding)) {
/* 337 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/* 340 */     String buyer_email = request.getParameter("buyer_email");
/* 341 */     String receive_name = "";
/* 342 */     if (request.getParameter("receive_name") != null) {
/* 343 */       receive_name = request.getParameter("receive_name");
/* 344 */       if (!StringUtil.isEmpty(encoding)) {
/* 345 */         receive_name = StringUtil.to(receive_name, encoding);
/*     */       }
/*     */     }
/* 348 */     String receive_address = "";
/* 349 */     if (request.getParameter("receive_address") != null) {
/* 350 */       receive_address = request.getParameter("receive_address");
/* 351 */       if (!StringUtil.isEmpty(encoding)) {
/* 352 */         receive_address = StringUtil.to(receive_address, encoding);
/*     */       }
/*     */     }
/*     */ 
/* 356 */     String receive_zip = "";
/* 357 */     if (request.getParameter("receive_zip") != null) {
/* 358 */       receive_zip = request.getParameter("receive_zip");
/* 359 */       if (!StringUtil.isEmpty(encoding)) {
/* 360 */         receive_zip = StringUtil.to(receive_zip, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 365 */     String receive_phone = "";
/* 366 */     if (request.getParameter("receive_phone") != null) {
/* 367 */       receive_phone = request.getParameter("receive_phone");
/* 368 */       if (!StringUtil.isEmpty(encoding)) {
/* 369 */         receive_phone = StringUtil.to(receive_phone, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 374 */     String receive_mobile = "";
/* 375 */     if (request.getParameter("receive_mobile") != null) {
/* 376 */       receive_mobile = request.getParameter("receive_mobile");
/* 377 */       if (!StringUtil.isEmpty(encoding)) {
/* 378 */         receive_mobile = StringUtil.to(receive_mobile, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 383 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/* 388 */     boolean verify_result = AlipayNotify.returnverify(params, key, partner);
/*     */ 
/* 390 */     if (verify_result)
/*     */     {
/* 396 */       if (trade_status.equals("WAIT_SELLER_SEND_GOODS"));
/* 401 */       paySuccess(order_no, trade_no);
/* 402 */       return order_no;
/*     */     }
/*     */ 
/* 406 */     paySuccess(order_no, trade_no);
/* 407 */     return order_no;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.alipay.escow.AlipayEscowPlugin
 * JD-Core Version:    0.6.0
 */