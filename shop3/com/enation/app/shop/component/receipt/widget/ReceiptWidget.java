/*    */ package com.enation.app.shop.component.receipt.widget;
/*    */ 
/*    */ import com.enation.app.shop.component.receipt.Receipt;
/*    */ import com.enation.app.shop.component.receipt.service.IReceiptManager;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ @Scope("prototype")
/*    */ public class ReceiptWidget extends AbstractWidget
/*    */ {
/*    */   private IReceiptManager receiptManager;
/*    */   private IOrderManager orderManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 31 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 32 */     String url = request.getServletPath();
/*    */ 
/* 36 */     if ((url.startsWith("/member_orderdetail_")) || (url.startsWith("/orderdetail_"))) {
/* 37 */       String sn = parseSn(url);
/* 38 */       Order order = this.orderManager.get(sn);
/* 39 */       Receipt receipt = this.receiptManager.getByOrderid(order.getOrder_id());
/* 40 */       putData("receipt", receipt);
/*    */     }
/*    */ 
/* 43 */     setActionPageName("receipt");
/*    */   }
/*    */ 
/*    */   private static String parseSn(String url)
/*    */   {
/* 48 */     String pattern = "(.*)orderdetail_(\\w+)(.*)";
/* 49 */     String value = null;
/* 50 */     Pattern p = Pattern.compile(pattern, 34);
/* 51 */     Matcher m = p.matcher(url);
/* 52 */     if (m.find()) {
/* 53 */       value = m.replaceAll("$2");
/*    */     }
/* 55 */     return value;
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IReceiptManager getReceiptManager()
/*    */   {
/* 65 */     return this.receiptManager;
/*    */   }
/*    */ 
/*    */   public void setReceiptManager(IReceiptManager receiptManager) {
/* 69 */     this.receiptManager = receiptManager;
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager() {
/* 73 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 77 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.widget.ReceiptWidget
 * JD-Core Version:    0.6.0
 */