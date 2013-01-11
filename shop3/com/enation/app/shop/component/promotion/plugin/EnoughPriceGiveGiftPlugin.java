/*    */ package com.enation.app.shop.component.promotion.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ 
/*    */ public class EnoughPriceGiveGiftPlugin extends AutoRegisterPlugin
/*    */   implements IPromotionPlugin
/*    */ {
/*    */   public String[] getConditions()
/*    */   {
/* 21 */     return new String[] { "order", "memberLv" };
/*    */   }
/*    */ 
/*    */   public String getMethods()
/*    */   {
/* 26 */     return "giveGift";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 31 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 36 */     return "enoughPriceGiveGiftPlugin";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 41 */     return "满就送———购物车中商品总金额大于指定金额，赠送某个赠品";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 46 */     return "order";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 51 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.EnoughPriceGiveGiftPlugin
 * JD-Core Version:    0.6.0
 */