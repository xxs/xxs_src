/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Article;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class ArticleMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 14 */     Article article = new Article();
/* 15 */     article.setTitle(rs.getString("title"));
/* 16 */     article.setContent(rs.getString("content"));
/* 17 */     article.setId(Integer.valueOf(rs.getInt("id")));
/* 18 */     article.setCreate_time(Long.valueOf(rs.getLong("create_time")));
/*    */ 
/* 20 */     return article;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.ArticleMapper
 * JD-Core Version:    0.6.0
 */