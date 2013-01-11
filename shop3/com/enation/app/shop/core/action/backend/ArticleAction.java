/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Article;
/*     */ import com.enation.app.shop.core.service.IArticleCatManager;
/*     */ import com.enation.app.shop.core.service.IArticleManager;
/*     */ import com.enation.app.shop.core.service.impl.ArticleRuntimeException;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ArticleAction extends WWAction
/*     */ {
/*     */   private Article article;
/*     */   private IArticleCatManager articleCatManager;
/*     */   private IArticleManager articleManager;
/*     */   private Integer cat_id;
/*     */   private List catList;
/*     */   private List articleList;
/*     */   private int article_id;
/*     */   private String id;
/*     */ 
/*     */   public String list()
/*     */   {
/*  29 */     this.articleList = this.articleManager.listByCatId(this.cat_id);
/*  30 */     return "list";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  35 */       this.articleManager.delete(this.id);
/*  36 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  38 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  40 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  44 */     this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
/*  45 */     return "add_input";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*  49 */     if (this.article.getCat_id() == null) {
/*  50 */       this.msgs.add("文章分类不能为空");
/*  51 */       this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  52 */       return "message";
/*     */     }
/*     */     try {
/*  55 */       this.articleManager.saveAdd(this.article);
/*     */     } catch (ArticleRuntimeException ex) {
/*  57 */       this.msgs.add("文章分类不存在");
/*  58 */       this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  59 */       return "message";
/*     */     }
/*  61 */     this.msgs.add("文章添加成功");
/*  62 */     this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  63 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  68 */     this.article = this.articleManager.get(Integer.valueOf(this.article_id));
/*  69 */     this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
/*     */ 
/*  71 */     return "edit_input";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*  75 */     if (this.article.getCat_id() == null) {
/*  76 */       this.msgs.add("文章分类不能为空");
/*  77 */       this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  78 */       return "message";
/*     */     }
/*     */     try
/*     */     {
/*  82 */       this.articleManager.saveEdit(this.article);
/*     */     } catch (ArticleRuntimeException ex) {
/*  84 */       this.msgs.add("文章分类不存在");
/*  85 */       this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  86 */       return "message";
/*     */     }
/*  88 */     this.msgs.add("文章修改成功");
/*  89 */     this.urls.put("文章列表", "article!list.do?cat_id=" + this.article.getCat_id());
/*  90 */     return "message";
/*     */   }
/*     */ 
/*     */   public Article getArticle() {
/*  94 */     return this.article;
/*     */   }
/*     */ 
/*     */   public void setArticle(Article article) {
/*  98 */     this.article = article;
/*     */   }
/*     */ 
/*     */   public IArticleCatManager getArticleCatManager()
/*     */   {
/* 103 */     return this.articleCatManager;
/*     */   }
/*     */ 
/*     */   public void setArticleCatManager(IArticleCatManager articleCatManager) {
/* 107 */     this.articleCatManager = articleCatManager;
/*     */   }
/*     */ 
/*     */   public List getCatList() {
/* 111 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList) {
/* 115 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public List getArticleList() {
/* 119 */     return this.articleList;
/*     */   }
/*     */ 
/*     */   public void setArticleList(List articleList) {
/* 123 */     this.articleList = articleList;
/*     */   }
/*     */ 
/*     */   public IArticleManager getArticleManager() {
/* 127 */     return this.articleManager;
/*     */   }
/*     */ 
/*     */   public void setArticleManager(IArticleManager articleManager) {
/* 131 */     this.articleManager = articleManager;
/*     */   }
/*     */ 
/*     */   public Integer getCat_id() {
/* 135 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(Integer cat_id) {
/* 139 */     this.cat_id = cat_id;
/*     */   }
/*     */ 
/*     */   public int getArticle_id() {
/* 143 */     return this.article_id;
/*     */   }
/*     */ 
/*     */   public void setArticle_id(int articleId) {
/* 147 */     this.article_id = articleId;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 151 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 155 */     this.id = id;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.ArticleAction
 * JD-Core Version:    0.6.0
 */