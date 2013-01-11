/*     */ package com.enation.app.shop.component.payment.plugin.alipay.dualfun;
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
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class AlipayDualfunPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/*  34 */     Map params = this.paymentManager.getConfigParams(getId());
/*     */ 
/*  36 */     String seller_email = (String)params.get("seller_email");
/*  37 */     String partner = (String)params.get("partner");
/*  38 */     String key = (String)params.get("key");
/*     */ 
/*  40 */     String show_url = getShowUrl(order);
/*  41 */     String notify_url = getCallBackUrl(payCfg);
/*  42 */     String return_url = getReturnUrl(payCfg);
/*     */ 
/*  49 */     String out_trade_no = "" + order.getSn();
/*     */ 
/*  51 */     String subject = "订单:" + order.getSn();
/*     */ 
/*  53 */     String body = "网店订单";
/*  54 */     String price = String.valueOf(order.getNeedPayMoney());
/*     */ 
/*  56 */     String logistics_fee = "0.00";
/*     */ 
/*  58 */     String logistics_type = "EXPRESS";
/*     */ 
/*  60 */     String logistics_payment = "SELLER_PAY";
/*     */ 
/*  63 */     String quantity = "1";
/*     */ 
/*  70 */     String receive_name = order.getShip_name();
/*  71 */     String receive_address = order.getShip_addr();
/*  72 */     String receive_zip = order.getShip_zip();
/*  73 */     String receive_phone = order.getShip_tel();
/*  74 */     String receive_mobile = order.getShip_email();
/*     */ 
/*  81 */     Map sParaTemp = new HashMap();
/*  82 */     sParaTemp.put("payment_type", "1");
/*  83 */     sParaTemp.put("show_url", show_url);
/*  84 */     sParaTemp.put("out_trade_no", out_trade_no);
/*  85 */     sParaTemp.put("subject", subject);
/*  86 */     sParaTemp.put("body", body);
/*  87 */     sParaTemp.put("price", price);
/*  88 */     sParaTemp.put("logistics_fee", logistics_fee);
/*  89 */     sParaTemp.put("logistics_type", logistics_type);
/*  90 */     sParaTemp.put("logistics_payment", logistics_payment);
/*  91 */     sParaTemp.put("quantity", quantity);
/*  92 */     sParaTemp.put("receive_name", receive_name);
/*  93 */     sParaTemp.put("receive_address", receive_address);
/*  94 */     sParaTemp.put("receive_zip", receive_zip);
/*  95 */     sParaTemp.put("receive_phone", receive_phone);
/*  96 */     sParaTemp.put("receive_mobile", receive_mobile);
/*     */ 
/*  99 */     sParaTemp.put("service", "trade_create_by_buyer");
/* 100 */     sParaTemp.put("partner", partner);
/* 101 */     sParaTemp.put("return_url", return_url);
/* 102 */     sParaTemp.put("notify_url", notify_url);
/* 103 */     sParaTemp.put("seller_email", seller_email);
/* 104 */     sParaTemp.put("_input_charset", AlipayConfig.input_charset);
/*     */ 
/* 106 */     return AlipaySubmit.buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", key);
/*     */   }
/*     */ 
/*     */   public String onCallBack()
/*     */   {
/* 111 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 112 */     Map configparams = getConfigParams();
/* 113 */     String partner = (String)configparams.get("partner");
/* 114 */     String key = (String)configparams.get("key");
/* 115 */     String encoding = (String)configparams.get("callback_encoding");
/*     */ 
/* 118 */     Map params = new HashMap();
/* 119 */     Map requestParams = request.getParameterMap();
/* 120 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 121 */       String name = (String)iter.next();
/* 122 */       String[] values = (String[])(String[])requestParams.get(name);
/* 123 */       String valueStr = "";
/* 124 */       for (int i = 0; i < values.length; i++) {
/* 125 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/* 128 */       if (!StringUtil.isEmpty(encoding)) {
/* 129 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/* 131 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 137 */     String trade_no = request.getParameter("trade_no");
/* 138 */     String order_no = request.getParameter("out_trade_no");
/* 139 */     String total_fee = request.getParameter("price");
/*     */ 
/* 143 */     String subject = request.getParameter("subject");
/* 144 */     if (!StringUtil.isEmpty(encoding)) {
/* 145 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/* 147 */     String body = "";
/* 148 */     if (request.getParameter("body") != null) {
/* 149 */       body = request.getParameter("body");
/* 150 */       if (!StringUtil.isEmpty(encoding)) {
/* 151 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/* 154 */     String buyer_email = request.getParameter("buyer_email");
/* 155 */     String receive_name = "";
/* 156 */     if (request.getParameter("receive_name") != null) {
/* 157 */       receive_name = request.getParameter("receive_name");
/* 158 */       if (!StringUtil.isEmpty(encoding)) {
/* 159 */         receive_name = StringUtil.to(receive_name, encoding);
/*     */       }
/*     */     }
/* 162 */     String receive_address = "";
/* 163 */     if (request.getParameter("receive_address") != null) {
/* 164 */       receive_address = request.getParameter("receive_address");
/* 165 */       if (!StringUtil.isEmpty(encoding)) {
/* 166 */         receive_address = StringUtil.to(receive_address, encoding);
/*     */       }
/*     */     }
/*     */ 
/* 170 */     String receive_zip = "";
/* 171 */     if (request.getParameter("receive_zip") != null) {
/* 172 */       receive_zip = request.getParameter("receive_zip");
/* 173 */       if (!StringUtil.isEmpty(encoding)) {
/* 174 */         receive_zip = StringUtil.to(receive_zip, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 179 */     String receive_phone = "";
/* 180 */     if (request.getParameter("receive_phone") != null) {
/* 181 */       receive_phone = request.getParameter("receive_phone");
/* 182 */       if (!StringUtil.isEmpty(encoding)) {
/* 183 */         receive_phone = StringUtil.to(receive_phone, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 188 */     String receive_mobile = "";
/* 189 */     if (request.getParameter("receive_mobile") != null) {
/* 190 */       receive_mobile = request.getParameter("receive_mobile");
/* 191 */       if (!StringUtil.isEmpty(encoding)) {
/* 192 */         receive_mobile = StringUtil.to(receive_mobile, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 198 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/* 202 */     if (AlipayNotify.callbackverify(params, key, partner))
/*     */     {
/* 205 */       paySuccess(order_no, trade_no);
/*     */ 
/* 207 */       this.logger.info("异步校验订单[" + order_no + "]成功");
/* 208 */       if (trade_status.equals("WAIT_BUYER_PAY"))
/*     */       {
/* 215 */         return "success";
/* 216 */       }if (trade_status.equals("WAIT_SELLER_SEND_GOODS"))
/*     */       {
/* 223 */         return "success";
/* 224 */       }if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS"))
/*     */       {
/* 231 */         return "success";
/* 232 */       }if (trade_status.equals("TRADE_FINISHED"))
/*     */       {
/* 239 */         return "success";
/*     */       }
/*     */ 
/* 242 */       return "success";
/*     */     }
/*     */ 
/* 249 */     return "fail";
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 256 */     Map cfgparams = this.paymentManager.getConfigParams(getId());
/* 257 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 258 */     this.logger.info("返回来的body" + request.getParameter("body"));
/* 259 */     String key = (String)cfgparams.get("key");
/* 260 */     String partner = (String)cfgparams.get("partner");
/* 261 */     String encoding = (String)cfgparams.get("return_encoding");
/*     */ 
/* 265 */     Map params = new HashMap();
/* 266 */     Map requestParams = request.getParameterMap();
/* 267 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 268 */       String name = (String)iter.next();
/* 269 */       String[] values = (String[])(String[])requestParams.get(name);
/* 270 */       String valueStr = "";
/* 271 */       for (int i = 0; i < values.length; i++) {
/* 272 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/*     */ 
/* 276 */       if (!StringUtil.isEmpty(encoding)) {
/* 277 */         valueStr = StringUtil.to(valueStr, encoding);
/*     */       }
/* 279 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 285 */     String trade_no = request.getParameter("trade_no");
/* 286 */     String order_no = request.getParameter("out_trade_no");
/* 287 */     String total_fee = request.getParameter("total_fee");
/*     */ 
/* 290 */     String subject = request.getParameter("subject");
/* 291 */     if (!StringUtil.isEmpty(encoding)) {
/* 292 */       subject = StringUtil.to(subject, encoding);
/*     */     }
/* 294 */     String body = "";
/* 295 */     if (request.getParameter("body") != null) {
/* 296 */       body = request.getParameter("body");
/* 297 */       if (!StringUtil.isEmpty(encoding)) {
/* 298 */         body = StringUtil.to(body, encoding);
/*     */       }
/*     */     }
/* 301 */     String buyer_email = request.getParameter("buyer_email");
/* 302 */     String receive_name = "";
/* 303 */     if (request.getParameter("receive_name") != null) {
/* 304 */       receive_name = request.getParameter("receive_name");
/* 305 */       if (!StringUtil.isEmpty(encoding)) {
/* 306 */         receive_name = StringUtil.to(receive_name, encoding);
/*     */       }
/*     */     }
/* 309 */     String receive_address = "";
/* 310 */     if (request.getParameter("receive_address") != null) {
/* 311 */       receive_address = request.getParameter("receive_address");
/* 312 */       if (!StringUtil.isEmpty(encoding)) {
/* 313 */         receive_address = StringUtil.to(receive_address, encoding);
/*     */       }
/*     */     }
/*     */ 
/* 317 */     String receive_zip = "";
/* 318 */     if (request.getParameter("receive_zip") != null) {
/* 319 */       receive_zip = request.getParameter("receive_zip");
/* 320 */       if (!StringUtil.isEmpty(encoding)) {
/* 321 */         receive_zip = StringUtil.to(receive_zip, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 326 */     String receive_phone = "";
/* 327 */     if (request.getParameter("receive_phone") != null) {
/* 328 */       receive_phone = request.getParameter("receive_phone");
/* 329 */       if (!StringUtil.isEmpty(encoding)) {
/* 330 */         receive_phone = StringUtil.to(receive_phone, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 335 */     String receive_mobile = "";
/* 336 */     if (request.getParameter("receive_mobile") != null) {
/* 337 */       receive_mobile = request.getParameter("receive_mobile");
/* 338 */       if (!StringUtil.isEmpty(encoding)) {
/* 339 */         receive_mobile = StringUtil.to(receive_mobile, encoding);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 344 */     String trade_status = request.getParameter("trade_status");
/*     */ 
/* 349 */     boolean verify_result = AlipayNotify.returnverify(params, key, partner);
/*     */ 
/* 351 */     if (verify_result)
/*     */     {
/* 357 */       if ((!trade_status.equals("WAIT_SELLER_SEND_GOODS")) || 
/* 363 */         (trade_status.equals("TRADE_FINISHED")));
/* 369 */       paySuccess(order_no, trade_no);
/* 370 */       return order_no;
/*     */     }
/*     */ 
/* 376 */     throw new RuntimeException("验证失败");
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 385 */     return "alipayDualfunPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 390 */     return "支付宝标准双接口";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.alipay.dualfun.AlipayDualfunPlugin
 * JD-Core Version:    0.6.0
 */