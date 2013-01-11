/*    */ package com.enation.app.shop.component.payment.plugin.cod;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.model.PayCfg;
/*    */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*    */ import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("cod")
/*    */ public class CodPlugin extends AbstractPaymentPlugin
/*    */   implements IPaymentEvent
/*    */ {
/*    */   public String onCallBack()
/*    */   {
/* 21 */     return "";
/*    */   }
/*    */ 
/*    */   public String onPay(PayCfg payCfg, Order order)
/*    */   {
/* 27 */     return "";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 35 */     return "cod";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return "货到付款";
/*    */   }
/*    */ 
/*    */   public String onReturn()
/*    */   {
/* 46 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.cod.CodPlugin
 * JD-Core Version:    0.6.0
 */