/*     */ package com.enation.app.shop.component.goodscore.plugin.goodsbase;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsBasePlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsTabShowEvent
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  31 */     int catid = StringUtil.toInt(request.getParameter("catid"), true);
/*     */ 
/*  34 */     List parentList = this.goodsCatManager.getParents(catid);
/*     */ 
/*  37 */     Cat currentCat = (Cat)parentList.get(parentList.size() - 1);
/*     */ 
/*  40 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  41 */     freeMarkerPaser.setPageName("goods_add");
/*  42 */     freeMarkerPaser.putData("typeid", currentCat.getType_id());
/*  43 */     freeMarkerPaser.putData("catid", Integer.valueOf(catid));
/*     */ 
/*  45 */     freeMarkerPaser.putData("optype", request.getParameter("optype"));
/*  46 */     freeMarkerPaser.putData("parentList", parentList);
/*     */ 
/*  49 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/*  58 */     int catid = StringUtil.toInt(goods.get("cat_id").toString(), true);
/*     */ 
/*  61 */     List parentList = this.goodsCatManager.getParents(catid);
/*     */ 
/*  64 */     Cat currentCat = (Cat)parentList.get(parentList.size() - 1);
/*     */ 
/*  67 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  68 */     freeMarkerPaser.putData("goodsView", goods);
/*  69 */     freeMarkerPaser.putData("parentList", parentList);
/*  70 */     freeMarkerPaser.putData("typeid", currentCat.getType_id());
/*  71 */     freeMarkerPaser.putData("catid", Integer.valueOf(catid));
/*  72 */     freeMarkerPaser.putData("optype", request.getParameter("optype"));
/*  73 */     freeMarkerPaser.setPageName("goods_edit");
/*  74 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 109 */     return "基本信息";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 115 */     return 1;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 120 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
/*     */   {
/* 125 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.goodsbase.GoodsBasePlugin
 * JD-Core Version:    0.6.0
 */