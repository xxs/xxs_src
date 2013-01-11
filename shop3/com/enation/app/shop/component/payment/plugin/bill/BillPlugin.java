/*     */ package com.enation.app.shop.component.payment.plugin.bill;
/*     */ 
/*     */ import com.enation.app.shop.component.payment.plugin.bill.encrypt.MD5Util;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class BillPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   private IPaymentManager paymentManager;
/*     */ 
/*     */   public String getId()
/*     */   {
/*  33 */     return "billPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  39 */     return "快钱人民币支付";
/*     */   }
/*     */ 
/*     */   public String appendParam(String returnStr, String paramId, String paramValue)
/*     */   {
/*  52 */     if (!returnStr.equals(""))
/*     */     {
/*  54 */       if (!paramValue.equals(""))
/*     */       {
/*  56 */         returnStr = returnStr + "&" + paramId + "=" + paramValue;
/*     */       }
/*     */ 
/*     */     }
/*  61 */     else if (!paramValue.equals(""))
/*     */     {
/*  63 */       returnStr = paramId + "=" + paramValue;
/*     */     }
/*     */ 
/*  66 */     return returnStr;
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/*  72 */     Map params = this.paymentManager.getConfigParams(getId());
/*  73 */     String partner = (String)params.get("partner");
/*     */ 
/*  77 */     String key = (String)params.get("key");
/*     */ 
/*  79 */     String show_url = getShowUrl(order);
/*  80 */     String notify_url = getCallBackUrl(payCfg);
/*  81 */     String return_url = getReturnUrl(payCfg);
/*     */ 
/*  85 */     String merchantAcctId = partner;
/*     */ 
/*  92 */     String inputCharset = "1";
/*     */ 
/*  98 */     String bgUrl = return_url;
/*     */ 
/* 103 */     String version = "v2.0";
/*     */ 
/* 109 */     String language = "1";
/*     */ 
/* 114 */     String signType = "1";
/*     */ 
/* 118 */     String payerName = "payerName";
/*     */ 
/* 123 */     String payerContactType = "1";
/*     */ 
/* 127 */     String payerContact = "";
/*     */ 
/* 131 */     String orderId = order.getSn();
/*     */ 
/* 136 */     double oa = order.getNeedPayMoney().doubleValue() * 100.0D;
/* 137 */     String orderAmount = String.valueOf((int)oa);
/*     */ 
/* 142 */     String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
/*     */ 
/* 146 */     String productName = "订单:" + order.getSn();
/*     */ 
/* 150 */     String productNum = "1";
/*     */ 
/* 154 */     String productId = "";
/*     */ 
/* 157 */     String productDesc = "";
/*     */ 
/* 161 */     String ext1 = "";
/*     */ 
/* 165 */     String ext2 = "";
/*     */ 
/* 170 */     String payType = "00";
/*     */ 
/* 176 */     String redoFlag = "0";
/*     */ 
/* 180 */     String pid = "";
/*     */ 
/* 185 */     String signMsgVal = "";
/* 186 */     signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset);
/* 187 */     signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl);
/* 188 */     signMsgVal = appendParam(signMsgVal, "version", version);
/* 189 */     signMsgVal = appendParam(signMsgVal, "language", language);
/* 190 */     signMsgVal = appendParam(signMsgVal, "signType", signType);
/* 191 */     signMsgVal = appendParam(signMsgVal, "merchantAcctId", merchantAcctId);
/* 192 */     signMsgVal = appendParam(signMsgVal, "payerName", payerName);
/* 193 */     signMsgVal = appendParam(signMsgVal, "payerContactType", payerContactType);
/* 194 */     signMsgVal = appendParam(signMsgVal, "payerContact", payerContact);
/* 195 */     signMsgVal = appendParam(signMsgVal, "orderId", orderId);
/* 196 */     signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount);
/* 197 */     signMsgVal = appendParam(signMsgVal, "orderTime", orderTime);
/* 198 */     signMsgVal = appendParam(signMsgVal, "productName", productName);
/* 199 */     signMsgVal = appendParam(signMsgVal, "productNum", productNum);
/* 200 */     signMsgVal = appendParam(signMsgVal, "productId", productId);
/* 201 */     signMsgVal = appendParam(signMsgVal, "productDesc", productDesc);
/* 202 */     signMsgVal = appendParam(signMsgVal, "ext1", ext1);
/* 203 */     signMsgVal = appendParam(signMsgVal, "ext2", ext2);
/* 204 */     signMsgVal = appendParam(signMsgVal, "payType", payType);
/* 205 */     signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag);
/* 206 */     signMsgVal = appendParam(signMsgVal, "pid", pid);
/* 207 */     signMsgVal = appendParam(signMsgVal, "key", key);
/*     */     try
/*     */     {
/* 210 */       String signMsg = MD5Util.md5Hex(signMsgVal.getBytes("utf-8")).toUpperCase();
/* 211 */       String strHtml = "";
/* 212 */       strHtml = strHtml + "<form name=\"kqPay\" id=\"kqPay\" action=\"https://www.99bill.com/gateway/recvMerchantInfoAction.htm\" method=\"post\">";
/* 213 */       strHtml = strHtml + "<input type=\"hidden\" name=\"inputCharset\" value=\"" + inputCharset + "\"/>";
/* 214 */       strHtml = strHtml + "<input type=\"hidden\" name=\"bgUrl\" value=\"" + bgUrl + "\"/>";
/* 215 */       strHtml = strHtml + "<input type=\"hidden\" name=\"version\" value=\"" + version + "\"/>";
/* 216 */       strHtml = strHtml + "<input type=\"hidden\" name=\"language\" value=\"" + language + "\"/>";
/* 217 */       strHtml = strHtml + "<input type=\"hidden\" name=\"signType\" value=\"" + signType + "\"/>";
/* 218 */       strHtml = strHtml + "<input type=\"hidden\" name=\"signMsg\" value=\"" + signMsg + "\"/>";
/* 219 */       strHtml = strHtml + "<input type=\"hidden\" name=\"merchantAcctId\" value=\"" + merchantAcctId + "\"/>";
/* 220 */       strHtml = strHtml + "<input type=\"hidden\" name=\"payerName\" value=\"" + payerName + "\"/>";
/* 221 */       strHtml = strHtml + "<input type=\"hidden\" name=\"payerContactType\" value=\"" + payerContactType + "\"/>";
/* 222 */       strHtml = strHtml + "<input type=\"hidden\" name=\"payerContact\" value=\"" + payerContact + "\"/>";
/* 223 */       strHtml = strHtml + "<input type=\"hidden\" name=\"orderId\" value=\"" + orderId + "\"/>";
/* 224 */       strHtml = strHtml + "<input type=\"hidden\" name=\"orderAmount\" value=\"" + orderAmount + "\"/>";
/* 225 */       strHtml = strHtml + "<input type=\"hidden\" name=\"orderTime\" value=\"" + orderTime + "\"/>";
/* 226 */       strHtml = strHtml + "<input type=\"hidden\" name=\"productName\" value=\"" + productName + "\"/>";
/* 227 */       strHtml = strHtml + "<input type=\"hidden\" name=\"productNum\" value=\"" + productNum + "\"/>";
/* 228 */       strHtml = strHtml + "<input type=\"hidden\" name=\"productId\" value=\"" + productId + "\"/>";
/* 229 */       strHtml = strHtml + "<input type=\"hidden\" name=\"productDesc\" value=\"" + productDesc + "\"/>";
/* 230 */       strHtml = strHtml + "<input type=\"hidden\" name=\"ext1\" value=\"" + ext1 + "\"/>";
/* 231 */       strHtml = strHtml + "<input type=\"hidden\" name=\"ext2\" value=\"" + ext2 + "\"/>";
/* 232 */       strHtml = strHtml + "<input type=\"hidden\" name=\"payType\" value=\"" + payType + "\"/>";
/* 233 */       strHtml = strHtml + "<input type=\"hidden\" name=\"redoFlag\" value=\"" + redoFlag + "\"/>";
/* 234 */       strHtml = strHtml + "<input type=\"hidden\" name=\"pid\" value=\"" + pid + "\"/>";
/*     */ 
/* 236 */       strHtml = strHtml + "</form>";
/* 237 */       strHtml = strHtml + "<script type=\"text/javascript\">document.forms['kqPay'].submit();</script>";
/* 238 */       return strHtml;
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 242 */       e.printStackTrace();
/* 243 */     }return "验证串失败";
/*     */   }
/*     */ 
/*     */   public String onCallBack()
/*     */   {
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 255 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 257 */     String merchantAcctId = request.getParameter("merchantAcctId").trim();
/*     */ 
/* 259 */     Map params = this.paymentManager.getConfigParams(getId());
/*     */ 
/* 262 */     String key = (String)params.get("key");
/*     */ 
/* 267 */     String version = request.getParameter("version").trim();
/*     */ 
/* 273 */     String language = request.getParameter("language").trim();
/*     */ 
/* 278 */     String signType = request.getParameter("signType").trim();
/*     */ 
/* 283 */     String payType = request.getParameter("payType").trim();
/*     */ 
/* 287 */     String bankId = request.getParameter("bankId").trim();
/*     */ 
/* 290 */     String orderId = request.getParameter("orderId").trim();
/* 291 */     this.logger.debug("快钱 return-----------orderId----------" + orderId);
/*     */ 
/* 295 */     String orderTime = request.getParameter("orderTime").trim();
/*     */ 
/* 300 */     String orderAmount = request.getParameter("orderAmount").trim();
/*     */ 
/* 304 */     String dealId = request.getParameter("dealId").trim();
/*     */ 
/* 308 */     String bankDealId = request.getParameter("bankDealId").trim();
/*     */ 
/* 313 */     String dealTime = request.getParameter("dealTime").trim();
/*     */ 
/* 318 */     String payAmount = request.getParameter("payAmount").trim();
/*     */ 
/* 323 */     String fee = request.getParameter("fee").trim();
/*     */ 
/* 326 */     String ext1 = request.getParameter("ext1").trim();
/*     */ 
/* 329 */     String ext2 = request.getParameter("ext2").trim();
/*     */ 
/* 333 */     String payResult = request.getParameter("payResult").trim();
/* 334 */     this.logger.debug("快钱 return---------payResult------------" + payResult);
/*     */ 
/* 337 */     String errCode = request.getParameter("errCode").trim();
/*     */ 
/* 340 */     String signMsg = request.getParameter("signMsg").trim();
/* 341 */     this.logger.debug("快钱 return----------signMsg-----------" + signMsg);
/*     */ 
/* 345 */     String merchantSignMsgVal = "";
/* 346 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
/* 347 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
/* 348 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
/* 349 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
/* 350 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
/* 351 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
/* 352 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
/* 353 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
/* 354 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
/* 355 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
/* 356 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
/* 357 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
/* 358 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
/* 359 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
/* 360 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
/* 361 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
/* 362 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
/* 363 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
/* 364 */     merchantSignMsgVal = appendParam(merchantSignMsgVal, "key", key);
/*     */ 
/* 366 */     String merchantSignMsg = "";
/*     */     try {
/* 368 */       merchantSignMsg = MD5Util.md5Hex(merchantSignMsgVal.getBytes("utf-8")).toUpperCase();
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 371 */       this.logger.error("快钱支付验证串失败");
/* 372 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 377 */     int rtnOk = 0;
/* 378 */     String rtnUrl = "";
/*     */ 
/* 382 */     if (signMsg.toUpperCase().equals(merchantSignMsg.toUpperCase()))
/*     */     {
/* 385 */       switch (Integer.parseInt(payResult))
/*     */       {
/*     */       case 10:
/* 395 */         rtnOk = 1;
/* 396 */         rtnUrl = "http://www.meijing.com/member_orderdetail_" + orderId + ".html";
/* 397 */         paySuccess(orderId, dealId);
/* 398 */         return orderId;
/*     */       }
/*     */ 
/* 402 */       rtnOk = 1;
/* 403 */       rtnUrl = "http://www.meijing.com/member_orderdetail_" + orderId + ".html";
/* 404 */       paySuccess(orderId, dealId);
/* 405 */       return orderId;
/*     */     }
/*     */ 
/* 411 */     rtnOk = 1;
/* 412 */     rtnUrl = "";
/* 413 */     this.logger.debug("onReturn in............. fail");
/* 414 */     throw new RuntimeException("验证失败");
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager()
/*     */   {
/* 422 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 426 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.bill.BillPlugin
 * JD-Core Version:    0.6.0
 */