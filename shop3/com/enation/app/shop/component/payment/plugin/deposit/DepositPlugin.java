/*     */ package com.enation.app.shop.component.payment.plugin.deposit;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*     */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class DepositPlugin extends AbstractPaymentPlugin
/*     */   implements IPaymentEvent
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private IAdvanceLogsManager advanceLogsManager;
/*     */ 
/*     */   public String onCallBack()
/*     */   {
/*  36 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  37 */     String ordersn = request.getParameter("ordersn");
/*     */     try
/*     */     {
/*  40 */       paySuccess(ordersn, "" + DateUtil.getDateline());
/*     */     } catch (RuntimeException e) {
/*  42 */       if (this.logger.isDebugEnabled()) {
/*  43 */         this.logger.debug(e.getMessage(), e);
/*     */       }
/*  45 */       return e.getMessage();
/*     */     }
/*     */ 
/*  48 */     return "支付成功";
/*     */   }
/*     */ 
/*     */   public String onPay(PayCfg payCfg, Order order)
/*     */   {
/*  53 */     IUserService userService = UserServiceFactory.getUserService();
/*  54 */     Member member = userService.getCurrentMember();
/*  55 */     if (member == null)
/*     */     {
/*  57 */       String url = "";
/*     */       try {
/*  59 */         url = URLEncoder.encode("widget?type=paywidget&orderid=" + order.getOrder_id() + "&paymentid=" + payCfg.getId(), "UTF-8");
/*     */       } catch (UnsupportedEncodingException e) {
/*  61 */         e.printStackTrace();
/*     */       }
/*     */ 
/*  64 */       return "<script>location.href='member_login.html?forward=" + url + "'</script>";
/*     */     }
/*     */ 
/*  67 */     return "<script>location.href='" + getReturnUrl(payCfg) + "&ordersn=" + order.getSn() + "';</script>正在支付...";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  77 */     return "deposit";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  83 */     return "预存款支付";
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/*  89 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/*  93 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IAdvanceLogsManager getAdvanceLogsManager() {
/*  97 */     return this.advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
/* 101 */     this.advanceLogsManager = advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public String onReturn()
/*     */   {
/* 107 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 108 */     String ordersn = request.getParameter("ordersn");
/*     */     try
/*     */     {
/* 111 */       paySuccess(ordersn, "" + DateUtil.getDateline());
/*     */     } catch (RuntimeException e) {
/* 113 */       if (this.logger.isDebugEnabled()) {
/* 114 */         this.logger.debug(e.getMessage(), e);
/*     */       }
/* 116 */       return e.getMessage();
/*     */     }
/*     */ 
/* 119 */     return ordersn;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.deposit.DepositPlugin
 * JD-Core Version:    0.6.0
 */