/*     */ package com.enation.app.shop.component.goodscore.plugin.tag;
/*     */ 
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsTagPlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsTabShowEvent
/*     */ {
/*     */   private ITagManager tagManager;
/*     */ 
/*     */   public void addTabs()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  39 */     List taglist = this.tagManager.list();
/*  40 */     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
/*  41 */     freeMarkerPaser.setPageName("tag");
/*  42 */     freeMarkerPaser.putData("tagList", taglist);
/*  43 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/*  49 */     List taglist = this.tagManager.list();
/*     */ 
/*  51 */     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
/*  52 */     List tagIds = this.tagManager.list(goods_id);
/*     */ 
/*  55 */     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
/*  56 */     freeMarkerPaser.setPageName("tag");
/*  57 */     freeMarkerPaser.putData("tagList", taglist);
/*  58 */     freeMarkerPaser.putData("tagRelList", tagIds);
/*  59 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*  71 */     save(goods, request);
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*  78 */     save(goods, request);
/*     */   }
/*     */ 
/*     */   private void save(Map goods, HttpServletRequest request)
/*     */   {
/*  83 */     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
/*     */ 
/*  85 */     String[] tagstr = request.getParameterValues("tag_id");
/*  86 */     Integer[] tagids = null;
/*  87 */     if (tagstr != null) {
/*  88 */       tagids = new Integer[tagstr.length];
/*  89 */       for (int i = 0; i < tagstr.length; i++) {
/*  90 */         tagids[i] = Integer.valueOf(tagstr[i]);
/*     */       }
/*     */     }
/*  93 */     this.tagManager.saveRels(goods_id, tagids);
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 120 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 125 */     return "goodstag";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 130 */     return "商品标签";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 140 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager)
/*     */   {
/* 150 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 161 */     return "标签";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 172 */     return 13;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.tag.GoodsTagPlugin
 * JD-Core Version:    0.6.0
 */