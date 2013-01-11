/*     */ package com.enation.app.shop.component.payment.plugin.tenpay.med;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.tenpay.MediPayRequestHandler;
/*     */ import com.tenpay.MediPayResponseHandler;
/*     */ import com.tenpay.util.TenpayUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class TenpayMedPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   public String onCallBack()
/*     */   {
/*  30 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  31 */     HttpServletResponse response = ThreadContextHolder.getHttpResponse();
/*     */ 
/*  33 */     Map params = getConfigParams();
/*     */ 
/*  35 */     String key = (String)params.get("key");
/*     */ 
/*  38 */     MediPayResponseHandler resHandler = new MediPayResponseHandler(request, response);
/*     */ 
/*  40 */     resHandler.setKey(key);
/*     */ 
/*  43 */     if (resHandler.isTenpaySign())
/*     */     {
/*  45 */       String cft_tid = resHandler.getParameter("cft_tid");
/*     */ 
/*  48 */       String total_fee = resHandler.getParameter("total_fee");
/*     */ 
/*  51 */       String retcode = resHandler.getParameter("retcode");
/*     */ 
/*  54 */       String status = resHandler.getParameter("status");
/*     */ 
/*  56 */       if ("0".equals(retcode))
/*     */       {
/*  64 */         int iStatus = TenpayUtil.toInt(status);
/*  65 */         switch (iStatus)
/*     */         {
/*     */         case 1:
/*  68 */           break;
/*     */         case 2:
/*  71 */           break;
/*     */         case 3:
/*  73 */           String orderSn = request.getParameter("mch_vno");
/*  74 */           paySuccess(orderSn, cft_tid);
/*  75 */           break;
/*     */         case 4:
/*  78 */           break;
/*     */         case 5:
/*  81 */           break;
/*     */         case 6:
/*  84 */           break;
/*     */         case 7:
/*  87 */           break;
/*     */         case 8:
/*  90 */           break;
/*     */         case 9:
/*  93 */           break;
/*     */         case 10:
/*  96 */           break;
/*     */         }
/*     */ 
/* 108 */         String strHtml = "<html><head>\r\n<meta name=\"TENCENT_ONLINE_PAYMENT\" content=\"China TENCENT\">\r\n</head><body></body></html>";
/*     */ 
/* 111 */         return strHtml;
/*     */       }
/*     */ 
/* 115 */       return "支付失败";
/*     */     }
/*     */ 
/* 119 */     return "认证签名失败";
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/* 127 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 129 */     Map params = getConfigParams();
/*     */ 
/* 133 */     String key = (String)params.get("key");
/*     */ 
/* 136 */     String chnid = (String)params.get("chnid");
/*     */ 
/* 139 */     String seller = (String)params.get("chnid");
/*     */ 
/* 142 */     String mch_returl = getCallBackUrl(payCfg);
/*     */ 
/* 145 */     String show_url = getCallBackUrl(payCfg);
/*     */ 
/* 149 */     String mch_vno = "" + order.getOrder_id();
/*     */ 
/* 152 */     MediPayRequestHandler reqHandler = new MediPayRequestHandler(request, ThreadContextHolder.getHttpResponse());
/*     */ 
/* 155 */     reqHandler.setKey(key);
/*     */ 
/* 158 */     reqHandler.init();
/*     */ 
/* 163 */     reqHandler.setParameter("chnid", chnid);
/* 164 */     reqHandler.setParameter("encode_type", "2");
/* 165 */     reqHandler.setParameter("mch_desc", "支付订单" + order.getSn());
/* 166 */     reqHandler.setParameter("mch_name", "网站订单[" + order.getSn() + "]");
/* 167 */     reqHandler.setParameter("mch_price", formatPrice(Double.valueOf(order.getNeedPayMoney().doubleValue() * 100.0D)));
/* 168 */     reqHandler.setParameter("mch_returl", mch_returl);
/* 169 */     reqHandler.setParameter("mch_type", "1");
/* 170 */     reqHandler.setParameter("mch_vno", mch_vno);
/* 171 */     reqHandler.setParameter("need_buyerinfo", "2");
/* 172 */     reqHandler.setParameter("seller", seller);
/* 173 */     reqHandler.setParameter("show_url", show_url);
/* 174 */     reqHandler.setParameter("transport_desc", order.getShipping_type());
/* 175 */     reqHandler.setParameter("transport_fee", "");
/*     */     try
/*     */     {
/* 180 */       String requestUrl = reqHandler.getRequestURL();
/* 181 */       return "<script>location.href=\"" + requestUrl + "\"</script>";
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 184 */       e.printStackTrace();
/* 185 */     }return "财付通支付错误!";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 194 */     return "tenpayMedPlugin";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 199 */     return "财付通（中介担保）";
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 206 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.tenpay.med.TenpayMedPlugin
 * JD-Core Version:    0.6.0
 */