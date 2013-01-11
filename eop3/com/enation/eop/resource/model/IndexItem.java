/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class IndexItem
/*    */ {
/*    */   private Integer id;
/*    */   private String title;
/*    */   private String url;
/*    */   private int sort;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 18 */     return this.title;
/*    */   }
/*    */   public void setTitle(String title) {
/* 21 */     this.title = title;
/*    */   }
/*    */   public String getUrl() {
/* 24 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 27 */     this.url = url;
/*    */   }
/*    */   public int getSort() {
/* 30 */     return this.sort;
/*    */   }
/*    */   public void setSort(int sort) {
/* 33 */     this.sort = sort;
/*    */   }
/* 37 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 40 */     this.id = id;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.IndexItem
 * JD-Core Version:    0.6.0
 */