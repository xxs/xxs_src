/*    */ package com.enation.app.shop.component.promotion.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ 
/*    */ public class GoodsTimesPointPlugin extends AutoRegisterPlugin
/*    */   implements IPromotionPlugin
/*    */ {
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String[] getConditions()
/*    */   {
/* 27 */     return new String[] { "goods", "memberLv" };
/*    */   }
/*    */ 
/*    */   public String getMethods()
/*    */   {
/* 32 */     return "timesPoint";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 37 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 42 */     return "goodsTimesPointPlugin";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 47 */     return "积分翻倍——顾客购买指定的商品，可获得翻倍积分或者x倍积分";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 52 */     return "goods";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 57 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.GoodsTimesPointPlugin
 * JD-Core Version:    0.6.0
 */