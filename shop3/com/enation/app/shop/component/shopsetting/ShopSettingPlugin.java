/*    */ package com.enation.app.shop.component.shopsetting;
/*    */ 
/*    */ import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ 
/*    */ public class ShopSettingPlugin extends AutoRegisterPlugin
/*    */   implements IOnSettingInputShow
/*    */ {
/*    */   public String onShow()
/*    */   {
/* 22 */     return "ShopSetting";
/*    */   }
/*    */ 
/*    */   public String getSettingGroupName()
/*    */   {
/* 28 */     return "shop";
/*    */   }
/*    */ 
/*    */   public String getTabName()
/*    */   {
/* 36 */     return "网店参数";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 42 */     return 4;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.shopsetting.ShopSettingPlugin
 * JD-Core Version:    0.6.0
 */