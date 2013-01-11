/*    */ package com.enation.app.shop.component.point.plugin;
/*    */ 
/*    */ import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class PointSettingPlugin extends AutoRegisterPlugin
/*    */   implements IOnSettingInputShow
/*    */ {
/*    */   public String getSettingGroupName()
/*    */   {
/* 21 */     return "point";
/*    */   }
/*    */ 
/*    */   public String onShow()
/*    */   {
/* 27 */     return "setting";
/*    */   }
/*    */ 
/*    */   public String getTabName()
/*    */   {
/* 35 */     return "积分设置";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 42 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.point.plugin.PointSettingPlugin
 * JD-Core Version:    0.6.0
 */