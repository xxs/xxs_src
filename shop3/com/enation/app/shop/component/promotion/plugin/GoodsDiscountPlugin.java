/*    */ package com.enation.app.shop.component.promotion.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsDiscountPlugin extends AutoRegisterPlugin
/*    */   implements IPromotionPlugin
/*    */ {
/*    */   public String[] getConditions()
/*    */   {
/* 14 */     return new String[] { "goods", "memberLv" };
/*    */   }
/*    */ 
/*    */   public String getMethods()
/*    */   {
/* 19 */     return "discount";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 24 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 29 */     return "goodsDiscountPlugin";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 34 */     return "打折————商品直接打折，如全场女鞋8折。可以对商品任意折扣，适合低价清货促销";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 39 */     return "goods";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 44 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.GoodsDiscountPlugin
 * JD-Core Version:    0.6.0
 */