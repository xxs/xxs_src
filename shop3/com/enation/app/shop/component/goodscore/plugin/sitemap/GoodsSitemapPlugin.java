/*     */ package com.enation.app.shop.component.goodscore.plugin.sitemap;
/*     */ 
/*     */ import com.enation.app.base.core.model.SiteMapUrl;
/*     */ import com.enation.app.base.core.plugin.IRecreateMapEvent;
/*     */ import com.enation.app.base.core.service.ISitemapManager;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsSitemapPlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsDeleteEvent, IRecreateMapEvent
/*     */ {
/*     */   private ISitemapManager sitemapManager;
/*     */   private IGoodsManager goodsManager;
/*     */ 
/*     */   public void addTabs()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  34 */     return null;
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request) {
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request) throws RuntimeException
/*     */   {
/*  47 */     SiteMapUrl url = new SiteMapUrl();
/*  48 */     url.setLoc("/goods-" + goods.get("goods_id").toString() + ".html");
/*  49 */     url.setLastmod(Long.valueOf(System.currentTimeMillis()));
/*     */ 
/*  52 */     this.sitemapManager.addUrl(url);
/*     */   }
/*     */ 
/*     */   public void onRecreateMap()
/*     */   {
/*  57 */     List list = this.goodsManager.list();
/*  58 */     for (Map map : list) {
/*  59 */       SiteMapUrl url = new SiteMapUrl();
/*  60 */       url.setLoc("/goods-" + map.get("goods_id") + ".html");
/*  61 */       url.setLastmod(Long.valueOf(System.currentTimeMillis()));
/*  62 */       this.sitemapManager.addUrl(url);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*  68 */     this.sitemapManager.editUrl("/goods-" + goods.get("goods_id").toString() + ".html", Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/*  77 */     Integer[] arr$ = goodsid; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { int i = arr$[i$].intValue();
/*  78 */       this.sitemapManager.delete("/goods-" + i + ".html");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  84 */     return "lzf";
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  88 */     return "goods-sitemap";
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  92 */     return "商品数据sitemap记录插件";
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  96 */     return "sitemap";
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 100 */     return "v2.1.5";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public ISitemapManager getSitemapManager() {
/* 108 */     return this.sitemapManager;
/*     */   }
/*     */ 
/*     */   public void setSitemapManager(ISitemapManager sitemapManager) {
/* 112 */     this.sitemapManager = sitemapManager;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 116 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 120 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.sitemap.GoodsSitemapPlugin
 * JD-Core Version:    0.6.0
 */