/*    */ package com.enation.app.shop.component.depot.plugin.goods;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
/*    */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsModifedUpdateCachePlugin extends AutoRegisterPlugin
/*    */   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent
/*    */ {
/*    */   public void updateCache(Map goods)
/*    */   {
/* 18 */     int catid = Integer.valueOf(goods.get("cat_id").toString()).intValue();
/* 19 */     int goodsid = Integer.valueOf(goods.get("goods_id").toString()).intValue();
/*    */ 
/* 21 */     String link = "";
/*    */ 
/* 23 */     link = "/goods-" + goodsid + ".html";
/*    */ 
/* 25 */     HttpCacheManager.updateUrlModified(link);
/* 26 */     HttpCacheManager.updateUriModified("/search-(.*).html");
/*    */   }
/*    */ 
/*    */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*    */   {
/* 32 */     updateCache(goods);
/*    */   }
/*    */ 
/*    */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*    */     throws RuntimeException
/*    */   {
/* 38 */     updateCache(goods);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.depot.plugin.goods.GoodsModifedUpdateCachePlugin
 * JD-Core Version:    0.6.0
 */