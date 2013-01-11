/*    */ package com.enation.app.shop.component.goodscore.plugin.album;
/*    */ 
/*    */ import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ImageSettingPlugin extends AutoRegisterPlugin
/*    */   implements IOnSettingInputShow
/*    */ {
/*    */   public String onShow()
/*    */   {
/* 21 */     return "setting";
/*    */   }
/*    */ 
/*    */   public String getSettingGroupName()
/*    */   {
/* 26 */     return "photo";
/*    */   }
/*    */ 
/*    */   public String getTabName()
/*    */   {
/* 32 */     return "图片设置";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 38 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.album.ImageSettingPlugin
 * JD-Core Version:    0.6.0
 */