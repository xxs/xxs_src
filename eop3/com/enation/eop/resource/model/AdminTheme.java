/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ public class AdminTheme extends Resource
/*    */ {
/*    */   private String themename;
/*    */   private String path;
/*    */   private String author;
/*    */   private String version;
/* 15 */   private String thumb = "preview.png";
/*    */   private int framemode;
/*    */ 
/*    */   public String getThumb()
/*    */   {
/* 36 */     return this.thumb;
/*    */   }
/*    */ 
/*    */   public void setThumb(String thumb) {
/* 40 */     this.thumb = thumb;
/*    */   }
/*    */ 
/*    */   public String getThemename() {
/* 44 */     return this.themename;
/*    */   }
/*    */ 
/*    */   public void setThemename(String themename) {
/* 48 */     this.themename = themename;
/*    */   }
/*    */ 
/*    */   public String getPath() {
/* 52 */     return this.path;
/*    */   }
/*    */ 
/*    */   public void setPath(String path) {
/* 56 */     this.path = path;
/*    */   }
/*    */ 
/*    */   public int getFramemode() {
/* 60 */     return this.framemode;
/*    */   }
/*    */ 
/*    */   public void setFramemode(int framemode) {
/* 64 */     this.framemode = framemode;
/*    */   }
/*    */ 
/*    */   public String getAuthor() {
/* 68 */     return this.author;
/*    */   }
/*    */ 
/*    */   public void setAuthor(String author) {
/* 72 */     this.author = author;
/*    */   }
/*    */ 
/*    */   public String getVersion() {
/* 76 */     return this.version;
/*    */   }
/*    */ 
/*    */   public void setVersion(String version) {
/* 80 */     this.version = version;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.AdminTheme
 * JD-Core Version:    0.6.0
 */