/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Article
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private String title;
/*    */   private String content;
/*    */   private Long create_time;
/*    */   private Integer cat_id;
/*    */ 
/*    */   public Integer getCat_id()
/*    */   {
/* 22 */     return this.cat_id;
/*    */   }
/*    */ 
/*    */   public void setCat_id(Integer cat_id) {
/* 26 */     this.cat_id = cat_id;
/*    */   }
/*    */ 
/*    */   public String getContent() {
/* 30 */     return this.content;
/*    */   }
/*    */ 
/*    */   public void setContent(String content) {
/* 34 */     this.content = content;
/*    */   }
/*    */ 
/*    */   public Long getCreate_time() {
/* 38 */     return this.create_time;
/*    */   }
/*    */ 
/*    */   public void setCreate_time(Long create_time) {
/* 42 */     this.create_time = create_time;
/*    */   }
/*    */   @PrimaryKeyField
/*    */   public Integer getId() {
/* 47 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 51 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 55 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 59 */     this.title = title;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Article
 * JD-Core Version:    0.6.0
 */