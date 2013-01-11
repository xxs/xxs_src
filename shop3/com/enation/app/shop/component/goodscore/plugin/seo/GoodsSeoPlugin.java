/*     */ package com.enation.app.shop.component.goodscore.plugin.seo;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsSeoPlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsTabShowEvent
/*     */ {
/*     */   public void addTabs()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void registerPages()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  41 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  42 */     freeMarkerPaser.setPageName("seo");
/*  43 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request) {
/*  47 */     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
/*  48 */     freeMarkerPaser.setPageName("seo");
/*  49 */     freeMarkerPaser.putData("goods", goods);
/*  50 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Goods goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  78 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  83 */     return "goodsseo";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  88 */     return "商品SEO优化插件";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  93 */     return "";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  98 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 109 */     return "SEO";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 115 */     return 11;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.seo.GoodsSeoPlugin
 * JD-Core Version:    0.6.0
 */