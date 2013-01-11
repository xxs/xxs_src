/*    */ package com.enation.app.shop.component.promotion.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class EnoughPriceReducePrice extends AutoRegisterPlugin
/*    */   implements IPromotionPlugin
/*    */ {
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String[] getConditions()
/*    */   {
/* 21 */     return new String[] { "order", "memberLv" };
/*    */   }
/*    */ 
/*    */   public String getMethods()
/*    */   {
/* 27 */     return "reducePrice";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 32 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 37 */     return "enoughPriceReducePrice";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 42 */     return "满就减———购物车中商品总金额大于指定金额,就可立减某金额";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 47 */     return "order";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 52 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.EnoughPriceReducePrice
 * JD-Core Version:    0.6.0
 */