/*     */ package com.enation.app.shop.core.service.impl.cache;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GoodsCatCacheProxy extends AbstractCacheProxy<List<Cat>>
/*     */   implements IGoodsCatManager
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   public static final String CACHE_KEY = "goods_cat";
/*     */ 
/*     */   public GoodsCatCacheProxy(IGoodsCatManager goodsCatManager)
/*     */   {
/*  27 */     super("goods_cat");
/*  28 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   private void cleanCache()
/*     */   {
/*  33 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  34 */     this.cache.remove("goods_cat_" + site.getUserid() + "_" + site.getId() + "_0");
/*     */   }
/*     */ 
/*     */   public int delete(int catId)
/*     */   {
/*  41 */     int r = this.goodsCatManager.delete(catId);
/*  42 */     if (r == 0) {
/*  43 */       cleanCache();
/*     */     }
/*  45 */     return r;
/*     */   }
/*     */ 
/*     */   public Cat getById(int catId)
/*     */   {
/*  50 */     return this.goodsCatManager.getById(catId);
/*     */   }
/*     */ 
/*     */   public List<Cat> listAllChildren(Integer catId)
/*     */   {
/*  55 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  56 */     List catList = (List)this.cache.get("goods_cat_" + site.getUserid() + "_" + site.getId() + "_" + catId);
/*  57 */     if (catList == null) {
/*  58 */       catList = this.goodsCatManager.listAllChildren(catId);
/*  59 */       this.cache.put("goods_cat_" + site.getUserid() + "_" + site.getId() + "_" + catId, catList);
/*  60 */       if (this.logger.isDebugEnabled()) {
/*  61 */         this.logger.debug("load goods cat from database");
/*     */       }
/*     */     }
/*  64 */     else if (this.logger.isDebugEnabled()) {
/*  65 */       this.logger.debug("load goods cat from cache");
/*     */     }
/*     */ 
/*  68 */     return catList;
/*     */   }
/*     */ 
/*     */   public List<Cat> listChildren(Integer catId)
/*     */   {
/*  73 */     return this.goodsCatManager.listChildren(catId);
/*     */   }
/*     */ 
/*     */   public void saveAdd(Cat cat)
/*     */   {
/*  78 */     this.goodsCatManager.saveAdd(cat);
/*  79 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void saveSort(int[] catIds, int[] catSorts)
/*     */   {
/*  84 */     this.goodsCatManager.saveSort(catIds, catSorts);
/*  85 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void update(Cat cat)
/*     */   {
/*  90 */     this.goodsCatManager.update(cat);
/*  91 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public boolean checkname(String name, Integer catid)
/*     */   {
/*  96 */     return this.goodsCatManager.checkname(name, catid);
/*     */   }
/*     */ 
/*     */   public List getNavpath(int catId)
/*     */   {
/* 101 */     List list = new ArrayList();
/* 102 */     Map map = new HashMap();
/* 103 */     map.put("name", "首页");
/* 104 */     map.put("value", "0");
/* 105 */     list.add(map);
/* 106 */     Cat cat = getById(catId);
/* 107 */     String path = cat.getCat_path();
/* 108 */     path = path.substring(2, path.length() - 1);
/* 109 */     path = path.replace("|", ",");
/* 110 */     String[] ids = path.split(",");
/* 111 */     for (String id : ids) {
/* 112 */       Cat pcat = getById(StringUtil.toInt(id));
/* 113 */       Map pmap = new HashMap();
/* 114 */       pmap.put("name", pcat.getName());
/* 115 */       pmap.put("value", id);
/* 116 */       list.add(pmap);
/*     */     }
/* 118 */     return list;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 122 */     String path = "0|1|2|3|4|";
/* 123 */     path = path.substring(2, path.length() - 1);
/* 124 */     path = path.replace("|", ",");
/* 125 */     System.out.println(path);
/*     */   }
/*     */ 
/*     */   public List<Cat> getParents(int catid)
/*     */   {
/* 132 */     return this.goodsCatManager.getParents(catid);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.cache.GoodsCatCacheProxy
 * JD-Core Version:    0.6.0
 */