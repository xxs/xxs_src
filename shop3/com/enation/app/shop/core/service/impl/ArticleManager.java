/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Article;
/*    */ import com.enation.app.shop.core.service.IArticleManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.database.StringMapper;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArticleManager extends BaseSupport
/*    */   implements IArticleManager
/*    */ {
/*    */   public void saveAdd(Article article)
/*    */   {
/* 14 */     if (article.getCat_id() != null) {
/* 15 */       String sql = "select count(*) from article_cat where cat_id=?";
/* 16 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[] { article.getCat_id() });
/* 17 */       if (count <= 0)
/* 18 */         throw new ArticleRuntimeException(2);
/*    */     }
/* 20 */     article.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/* 21 */     this.baseDaoSupport.insert("article", article);
/*    */   }
/*    */ 
/*    */   public void saveEdit(Article article) {
/* 25 */     if (article.getCat_id() != null) {
/* 26 */       String sql = "select count(*) from article_cat where cat_id=?";
/* 27 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[] { article.getCat_id() });
/* 28 */       if (count <= 0)
/* 29 */         throw new ArticleRuntimeException(2);
/*    */     }
/* 31 */     this.baseDaoSupport.update("article", article, "id=" + article.getId());
/*    */   }
/*    */ 
/*    */   public Article get(Integer id)
/*    */   {
/* 36 */     String sql = "select * from article where id=?";
/* 37 */     Article article = null;
/*    */     try {
/* 39 */       article = (Article)this.baseDaoSupport.queryForObject(sql, Article.class, new Object[] { id });
/*    */     }
/*    */     catch (Exception ex) {
/* 42 */       article = null;
/*    */     }
/* 44 */     if (article == null)
/* 45 */       throw new ArticleRuntimeException(1);
/* 46 */     return article;
/*    */   }
/*    */ 
/*    */   public List listByCatId(Integer cat_id)
/*    */   {
/* 58 */     String cat_path = (String)this.baseDaoSupport.queryForObject("select cat_path from article_cat where cat_id = ?", new StringMapper(), new Object[] { cat_id });
/* 59 */     String sql = "select a.*,b.cat_path from " + getTableName("article") + " a left join " + getTableName("article_cat") + " b on b.cat_id = a.cat_id where cat_path like '" + cat_path + "%' order by create_time desc";
/* 60 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/* 61 */     return list;
/*    */   }
/*    */ 
/*    */   public String getCatName(Integer cat_id) {
/* 65 */     String sql = "select name from article_cat where cat_id=?";
/* 66 */     String cat_name = null;
/*    */     try {
/* 68 */       cat_name = (String)this.baseDaoSupport.queryForObject(sql, String.class, new Object[] { cat_id });
/*    */     } catch (Exception ex) {
/* 70 */       cat_name = null;
/*    */     }
/* 72 */     if (cat_name == null)
/* 73 */       throw new ArticleRuntimeException(1);
/* 74 */     return cat_name;
/*    */   }
/*    */ 
/*    */   public void delete(String id)
/*    */   {
/* 79 */     if ((id == null) || (id.equals("")))
/* 80 */       return;
/* 81 */     String sql = "delete from article  where id in (" + id + ")";
/*    */ 
/* 83 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Page pageByCatId(Integer pageNo, Integer pageSize, Integer catId)
/*    */   {
/* 89 */     String cat_path = (String)this.baseDaoSupport.queryForObject("select cat_path from article_cat where cat_id = ?", new StringMapper(), new Object[] { catId });
/* 90 */     String sql = "select a.*,b.cat_path from " + getTableName("article") + " a left join " + getTableName("article_cat") + " b on b.cat_id = a.cat_id where cat_path like '" + cat_path + "%' order by create_time desc";
/* 91 */     Page page = this.daoSupport.queryForPage(sql, pageNo.intValue(), pageSize.intValue(), new Object[0]);
/* 92 */     return page;
/*    */   }
/*    */ 
/*    */   public List topListByCatId(Integer catId, Integer topNum)
/*    */   {
/* 97 */     String sql = "select * from article where cat_id=? order by create_time limit 0," + topNum;
/* 98 */     List list = this.baseDaoSupport.queryForList(sql, new Object[] { catId });
/* 99 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.ArticleManager
 * JD-Core Version:    0.6.0
 */