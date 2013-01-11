/*    */ package com.enation.app.shop.component.search.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GoodsImageDataFilter extends AutoRegisterPlugin
/*    */   implements IGoodsDataFilter
/*    */ {
/*    */   public void filter(Map<String, Object> goods, ResultSet rs)
/*    */   {
/* 19 */     String image_file = (String)goods.get("image_file");
/* 20 */     if (image_file != null) {
/* 21 */       image_file = UploadUtil.replacePath(image_file);
/* 22 */       goods.put("image_file", image_file);
/*    */     }
/*    */ 
/* 25 */     String image_default = (String)goods.get("image_default");
/* 26 */     if ((image_default == null) || (image_default.equals("")))
/* 27 */       image_default = EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/*    */     else {
/* 29 */       image_default = UploadUtil.replacePath(image_default);
/*    */     }
/*    */ 
/* 32 */     goods.put("image_default", image_default);
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 38 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 44 */     return "goodsImageDataFilter";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 50 */     return "商品图片数据过滤器";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 56 */     return "searchFilter";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 62 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.search.plugin.GoodsImageDataFilter
 * JD-Core Version:    0.6.0
 */