/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ public class Theme extends Resource
/*    */ {
/*    */   private String themename;
/*    */   private String path;
/*    */   private String author;
/*    */   private String version;
/* 15 */   private String thumb = "preview.png";
/*    */   private Integer siteid;
/*    */ 
/*    */   public String getThumb()
/*    */   {
/* 30 */     return this.thumb;
/*    */   }
/*    */ 
/*    */   public void setThumb(String thumb) {
/* 34 */     this.thumb = thumb;
/*    */   }
/*    */ 
/*    */   public String getThemename() {
/* 38 */     return this.themename;
/*    */   }
/*    */ 
/*    */   public void setThemename(String themename) {
/* 42 */     this.themename = themename;
/*    */   }
/*    */ 
/*    */   public String getPath() {
/* 46 */     return this.path;
/*    */   }
/*    */ 
/*    */   public void setPath(String path) {
/* 50 */     this.path = path;
/*    */   }
/*    */ 
/*    */   public String getAuthor() {
/* 54 */     return this.author;
/*    */   }
/*    */ 
/*    */   public void setAuthor(String author) {
/* 58 */     this.author = author;
/*    */   }
/*    */ 
/*    */   public String getVersion() {
/* 62 */     return this.version;
/*    */   }
/*    */ 
/*    */   public void setVersion(String version) {
/* 66 */     this.version = version;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 70 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 74 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Theme
 * JD-Core Version:    0.6.0
 */