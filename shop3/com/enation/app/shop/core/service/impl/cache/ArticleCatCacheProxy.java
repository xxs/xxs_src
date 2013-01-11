/*    */ package com.enation.app.shop.core.service.impl.cache;
/*    */ 
/*    */ import com.enation.app.shop.core.model.ArticleCat;
/*    */ import com.enation.app.shop.core.service.IArticleCatManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.cache.AbstractCacheProxy;
/*    */ import com.enation.framework.cache.ICache;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ArticleCatCacheProxy extends AbstractCacheProxy<List<ArticleCat>>
/*    */   implements IArticleCatManager
/*    */ {
/*    */   public static final String cacheName = "article_cat";
/*    */   private IArticleCatManager articleCatManager;
/*    */ 
/*    */   public ArticleCatCacheProxy(IArticleCatManager articleCatManager)
/*    */   {
/* 17 */     super("article_cat");
/* 18 */     this.articleCatManager = articleCatManager;
/*    */   }
/*    */ 
/*    */   private String getKey() {
/* 22 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 23 */     return "article_cat_" + site.getUserid() + "_" + site.getId();
/*    */   }
/*    */   private void cleanCache() {
/* 26 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 27 */     this.cache.remove(getKey());
/*    */   }
/*    */ 
/*    */   public int delete(int catId)
/*    */   {
/* 32 */     int r = this.articleCatManager.delete(catId);
/* 33 */     if (r == 0) {
/* 34 */       cleanCache();
/*    */     }
/* 36 */     return r;
/*    */   }
/*    */ 
/*    */   public ArticleCat getById(int catId)
/*    */   {
/* 41 */     return this.articleCatManager.getById(catId);
/*    */   }
/*    */ 
/*    */   public List listChildById(Integer catId)
/*    */   {
/* 46 */     List catList = (List)this.cache.get(getKey());
/* 47 */     if (catList == null) {
/* 48 */       catList = this.articleCatManager.listChildById(catId);
/* 49 */       this.cache.put(getKey(), catList);
/* 50 */       if (this.logger.isDebugEnabled()) {
/* 51 */         this.logger.debug("load article cat form database");
/*    */       }
/*    */     }
/* 54 */     else if (this.logger.isDebugEnabled()) {
/* 55 */       this.logger.debug("load article cat form cache");
/*    */     }
/*    */ 
/* 59 */     return catList;
/*    */   }
/*    */ 
/*    */   public List listHelp(int catId)
/*    */   {
/* 64 */     return this.articleCatManager.listHelp(catId);
/*    */   }
/*    */ 
/*    */   public void saveAdd(ArticleCat cat)
/*    */   {
/* 69 */     this.articleCatManager.saveAdd(cat);
/* 70 */     cleanCache();
/*    */   }
/*    */ 
/*    */   public void saveSort(int[] catIds, int[] catSorts)
/*    */   {
/* 75 */     this.articleCatManager.saveSort(catIds, catSorts);
/* 76 */     cleanCache();
/*    */   }
/*    */ 
/*    */   public void update(ArticleCat cat)
/*    */   {
/* 81 */     this.articleCatManager.update(cat);
/* 82 */     cleanCache();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.cache.ArticleCatCacheProxy
 * JD-Core Version:    0.6.0
 */