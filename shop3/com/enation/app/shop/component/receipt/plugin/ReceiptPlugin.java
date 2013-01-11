/*    */ package com.enation.app.shop.component.receipt.plugin;
/*    */ 
/*    */ import com.enation.app.shop.component.receipt.Receipt;
/*    */ import com.enation.app.shop.component.receipt.service.IReceiptManager;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.model.support.CartItem;
/*    */ import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ReceiptPlugin extends AutoRegisterPlugin
/*    */   implements IAfterOrderCreateEvent
/*    */ {
/*    */   private IReceiptManager receiptManager;
/*    */ 
/*    */   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
/*    */   {
/* 33 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*    */ 
/* 35 */     String havereceipt = request.getParameter("receipt");
/* 36 */     if (StringUtil.isEmpty(havereceipt)) return;
/*    */ 
/* 40 */     String appi = request.getParameter("receiptType");
/* 41 */     int invoice_app = 0;
/* 42 */     if (!StringUtil.isEmpty(appi)) {
/* 43 */       invoice_app = Integer.parseInt(appi);
/*    */     }
/* 45 */     if (invoice_app == 1) {
/* 46 */       String invoice_title = "个人";
/* 47 */       String invoice_content = request.getParameter("receiptContent");
/* 48 */       if (!StringUtil.isEmpty(invoice_content)) {
/* 49 */         Receipt receipt = new Receipt();
/* 50 */         receipt.setOrder_id(order.getOrder_id());
/* 51 */         receipt.setTitle(invoice_title);
/* 52 */         receipt.setContent(invoice_content);
/* 53 */         this.receiptManager.add(receipt);
/*    */       }
/* 55 */     } else if (invoice_app == 2)
/*    */     {
/* 57 */       String invoice_title = request.getParameter("receiptTitle");
/* 58 */       String invoice_content = request.getParameter("receiptContent");
/* 59 */       if ((!StringUtil.isEmpty(invoice_title)) && (!StringUtil.isEmpty(invoice_content))) {
/* 60 */         Receipt invoice = new Receipt();
/* 61 */         invoice.setOrder_id(order.getOrder_id());
/* 62 */         invoice.setTitle(invoice_title);
/* 63 */         invoice.setContent(invoice_content);
/* 64 */         this.receiptManager.add(invoice);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public IReceiptManager getReceiptManager()
/*    */   {
/* 72 */     return this.receiptManager;
/*    */   }
/*    */ 
/*    */   public void setReceiptManager(IReceiptManager receiptManager) {
/* 76 */     this.receiptManager = receiptManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.plugin.ReceiptPlugin
 * JD-Core Version:    0.6.0
 */