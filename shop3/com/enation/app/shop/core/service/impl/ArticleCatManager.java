/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.ArticleCat;
/*     */ import com.enation.app.shop.core.service.IArticleCatManager;
/*     */ import com.enation.app.shop.core.service.IArticleManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ArticleCatManager extends BaseSupport
/*     */   implements IArticleCatManager
/*     */ {
/*     */   private IArticleManager articleManager;
/*     */ 
/*     */   public ArticleCat getById(int cat_id)
/*     */   {
/*  35 */     return (ArticleCat)this.baseDaoSupport.queryForObject("select * from article_cat where cat_id=?", ArticleCat.class, new Object[] { Integer.valueOf(cat_id) });
/*     */   }
/*     */ 
/*     */   public void saveAdd(ArticleCat cat)
/*     */   {
/*  49 */     if (cat.getParent_id() == null) {
/*  50 */       cat.setParent_id(Integer.valueOf(0));
/*     */     }
/*  52 */     else if ((cat.getCat_id() != null) && (cat.getParent_id() == cat.getCat_id())) {
/*  53 */       throw new ArticleCatRuntimeException(2);
/*     */     }
/*     */ 
/*  58 */     if (cat.getName() != null) {
/*  59 */       String sql = "select count(0) from article_cat where name = '" + cat.getName() + "' and parent_id=" + cat.getParent_id();
/*  60 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*  61 */       if (count > 0)
/*  62 */         throw new ArticleCatRuntimeException(1);
/*     */     }
/*  64 */     this.baseDaoSupport.insert("article_cat", cat);
/*  65 */     int cat_id = this.baseDaoSupport.getLastId("article_cat");
/*     */ 
/*  67 */     String sql = "";
/*     */ 
/*  69 */     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
/*  70 */       sql = "select * from article_cat where cat_id=?";
/*  71 */       ArticleCat parent = (ArticleCat)this.baseDaoSupport.queryForObject(sql, ArticleCat.class, new Object[] { cat.getParent_id() });
/*     */ 
/*  73 */       if (parent != null)
/*  74 */         cat.setCat_path(parent.getCat_path() + cat_id + "|");
/*     */     }
/*     */     else {
/*  77 */       cat.setCat_path(cat.getParent_id() + "|" + cat_id + "|");
/*     */     }
/*     */ 
/*  80 */     sql = "update article_cat set cat_path='" + cat.getCat_path() + "' where cat_id=" + cat_id;
/*     */ 
/*  82 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void update(ArticleCat cat)
/*     */   {
/*  89 */     if (cat.getParent_id() == null) {
/*  90 */       cat.setParent_id(Integer.valueOf(0));
/*     */     }
/*  92 */     else if ((cat.getCat_id() != null) && (cat.getParent_id() == cat.getCat_id())) {
/*  93 */       throw new ArticleCatRuntimeException(2);
/*     */     }
/*     */ 
/*  97 */     if (cat.getName() != null) {
/*  98 */       String sql = "select count(0) from article_cat where cat_id != " + cat.getCat_id() + " and name = '" + cat.getName() + "' and parent_id=" + cat.getParent_id();
/*  99 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/* 100 */       if (count > 0) {
/* 101 */         throw new ArticleCatRuntimeException(1);
/*     */       }
/*     */     }
/* 104 */     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
/* 105 */       String sql = "select * from article_cat where cat_id=?";
/* 106 */       ArticleCat parent = (ArticleCat)this.baseDaoSupport.queryForObject(sql, ArticleCat.class, new Object[] { cat.getParent_id() });
/*     */ 
/* 108 */       if (parent != null)
/* 109 */         cat.setCat_path(parent.getCat_path() + cat.getCat_id() + "|");
/*     */     }
/*     */     else {
/* 112 */       cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id() + "|");
/*     */     }
/*     */ 
/* 115 */     HashMap map = new HashMap();
/* 116 */     map.put("name", cat.getName());
/* 117 */     map.put("parent_id", cat.getParent_id());
/* 118 */     map.put("cat_order", Integer.valueOf(cat.getCat_order()));
/* 119 */     map.put("cat_path", cat.getCat_path());
/*     */ 
/* 121 */     this.baseDaoSupport.update("article_cat", map, "cat_id=" + cat.getCat_id());
/*     */   }
/*     */ 
/*     */   public int delete(int cat_id)
/*     */   {
/* 135 */     String sql = "select count(0) from article_cat where parent_id = ?";
/* 136 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(cat_id) });
/*     */ 
/* 139 */     String sqla = "select count(0) from article where cat_id = ?";
/* 140 */     int counta = this.baseDaoSupport.queryForInt(sqla, new Object[] { Integer.valueOf(cat_id) });
/*     */ 
/* 142 */     if ((count > 0) || (counta > 0)) {
/* 143 */       return 1;
/*     */     }
/*     */ 
/* 149 */     sql = "delete from article_cat where cat_id=" + cat_id;
/*     */ 
/* 151 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 153 */     return 0;
/*     */   }
/*     */ 
/*     */   public void saveSort(int[] cat_ids, int[] cat_sorts)
/*     */   {
/* 163 */     String sql = "";
/* 164 */     if ((cat_ids != null) && (cat_sorts != null) && (cat_ids.length == cat_sorts.length))
/* 165 */       for (int i = 0; i < cat_ids.length; i++) {
/* 166 */         sql = "update article_cat set cat_order=" + cat_sorts[i] + " where cat_id=" + cat_ids[i];
/*     */ 
/* 168 */         this.baseDaoSupport.execute(sql, new Object[0]);
/*     */       }
/*     */   }
/*     */ 
/*     */   public List listChildById(Integer cat_id)
/*     */   {
/* 182 */     String sql = "select * from  article_cat  order by parent_id,cat_order";
/* 183 */     List allCatList = this.baseDaoSupport.queryForList(sql, ArticleCat.class, new Object[0]);
/* 184 */     List topCatList = new ArrayList();
/* 185 */     for (ArticleCat cat : allCatList) {
/* 186 */       if (cat.getParent_id().compareTo(cat_id) == 0) {
/* 187 */         if (this.logger.isDebugEnabled()) {
/* 188 */           this.logger.debug("发现子[" + cat.getName() + "-" + cat.getCat_id() + "]");
/*     */         }
/* 190 */         List children = getChildren(allCatList, cat.getCat_id());
/* 191 */         cat.setChildren(children);
/* 192 */         topCatList.add(cat);
/*     */       }
/*     */     }
/* 195 */     return topCatList;
/*     */   }
/*     */ 
/*     */   private List<ArticleCat> getChildren(List<ArticleCat> catList, Integer parentid) {
/* 199 */     if (this.logger.isDebugEnabled()) {
/* 200 */       this.logger.debug("查找[" + parentid + "]的子");
/*     */     }
/* 202 */     List children = new ArrayList();
/* 203 */     for (ArticleCat cat : catList) {
/* 204 */       if (cat.getParent_id().compareTo(parentid) == 0) {
/* 205 */         if (this.logger.isDebugEnabled()) {
/* 206 */           this.logger.debug(cat.getName() + "-" + cat.getCat_id() + "是子");
/*     */         }
/* 208 */         cat.setChildren(getChildren(catList, cat.getCat_id()));
/* 209 */         children.add(cat);
/*     */       }
/*     */     }
/* 212 */     return children;
/*     */   }
/*     */ 
/*     */   public List listHelp(int cat_id)
/*     */   {
/* 219 */     int userid = EopContext.getContext().getCurrentSite().getUserid().intValue();
/* 220 */     int siteid = EopContext.getContext().getCurrentSite().getId().intValue();
/* 221 */     String sql = "select cat_id, name  from  article_cat c  where c.parent_id = " + cat_id;
/*     */ 
/* 223 */     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
/* 224 */     for (Map map : list) {
/* 225 */       List articleList = this.articleManager.listByCatId(Integer.valueOf(map.get("cat_id").toString()));
/* 226 */       map.put("articleList", articleList);
/*     */     }
/* 228 */     return list;
/*     */   }
/*     */ 
/*     */   public IArticleManager getArticleManager()
/*     */   {
/* 234 */     return this.articleManager;
/*     */   }
/*     */ 
/*     */   public void setArticleManager(IArticleManager articleManager) {
/* 238 */     this.articleManager = articleManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleCatManager
 * JD-Core Version:    0.6.0
 */