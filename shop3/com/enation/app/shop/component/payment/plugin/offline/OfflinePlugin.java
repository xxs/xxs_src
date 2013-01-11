/*    */ package com.enation.app.shop.component.payment.plugin.offline;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.model.PayCfg;
/*    */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*    */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("offline")
/*    */ public class OfflinePlugin extends AbstractPaymentPlugin
/*    */   implements IPaymentEvent
/*    */ {
/*    */   public String onCallBack()
/*    */   {
/* 22 */     return "";
/*    */   }
/*    */ 
/*    */   public String onPay(PayCfg payCfg, Order order)
/*    */   {
/* 28 */     return "";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 35 */     return "offline";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return "线下支付";
/*    */   }
/*    */ 
/*    */   public String onReturn()
/*    */   {
/* 50 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.offline.OfflinePlugin
 * JD-Core Version:    0.6.0
 */