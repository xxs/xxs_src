/*    */ package com.enation.app.shop.core.plugin.payment;
/*    */ 
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PaymentPluginBundle extends AutoRegisterPluginsBundle
/*    */ {
/*    */   public String getName()
/*    */   {
/* 12 */     return "支付插件桩";
/*    */   }
/*    */ 
/*    */   public List getPluginList() {
/* 16 */     return getPlugins();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.payment.PaymentPluginBundle
 * JD-Core Version:    0.6.0
 */