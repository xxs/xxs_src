/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Seo
/*    */ {
/*    */   private Integer id;
/*    */   private String title;
/*    */   private String meta_keywords;
/*    */   private String meta_description;
/*    */   private Integer use_static;
/*    */   private Integer noindex_catalog;
/*    */   private Integer userid;
/*    */   private Integer siteid;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 25 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 29 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 33 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 37 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public String getMeta_keywords() {
/* 41 */     return this.meta_keywords;
/*    */   }
/*    */ 
/*    */   public void setMeta_keywords(String metaKeywords) {
/* 45 */     this.meta_keywords = metaKeywords;
/*    */   }
/*    */ 
/*    */   public String getMeta_description() {
/* 49 */     return this.meta_description;
/*    */   }
/*    */ 
/*    */   public void setMeta_description(String metaDescription) {
/* 53 */     this.meta_description = metaDescription;
/*    */   }
/*    */ 
/*    */   public Integer getUse_static() {
/* 57 */     return this.use_static;
/*    */   }
/*    */ 
/*    */   public void setUse_static(Integer useStatic) {
/* 61 */     this.use_static = useStatic;
/*    */   }
/*    */ 
/*    */   public Integer getNoindex_catalog() {
/* 65 */     return this.noindex_catalog;
/*    */   }
/*    */ 
/*    */   public void setNoindex_catalog(Integer noindexCatalog) {
/* 69 */     this.noindex_catalog = noindexCatalog;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 73 */     return this.userid;
/*    */   }
/*    */ 
/*    */   public void setUserid(Integer userid) {
/* 77 */     this.userid = userid;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 81 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 85 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Seo
 * JD-Core Version:    0.6.0
 */