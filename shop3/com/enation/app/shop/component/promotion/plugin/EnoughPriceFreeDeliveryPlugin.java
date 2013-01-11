/*    */ package com.enation.app.shop.component.promotion.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class EnoughPriceFreeDeliveryPlugin extends AutoRegisterPlugin
/*    */   implements IPromotionPlugin
/*    */ {
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String[] getConditions()
/*    */   {
/* 30 */     return new String[] { "order", "memberLv" };
/*    */   }
/*    */ 
/*    */   public String getMethods()
/*    */   {
/* 36 */     return "freeFreight";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 42 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 48 */     return "enoughPriceFreeDeliveryPlugin";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 54 */     return "免运费———购物车中商品总金额大于指定金额,免运费。";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 60 */     return "order";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 66 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.EnoughPriceFreeDeliveryPlugin
 * JD-Core Version:    0.6.0
 */