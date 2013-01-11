/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ public class Skin extends Resource
/*    */ {
/*    */   private String skinname;
/*    */   private String path;
/* 21 */   private String thumb = "preview.png";
/*    */ 
/*    */   public String getSkinname()
/*    */   {
/* 13 */     return this.skinname;
/*    */   }
/*    */ 
/*    */   public void setSkinname(String skinname) {
/* 17 */     this.skinname = skinname;
/*    */   }
/*    */ 
/*    */   public String getThumb()
/*    */   {
/* 25 */     return this.thumb;
/*    */   }
/*    */ 
/*    */   public void setThumb(String thumb) {
/* 29 */     this.thumb = thumb;
/*    */   }
/*    */ 
/*    */   public String getPath() {
/* 33 */     return this.path;
/*    */   }
/*    */ 
/*    */   public void setPath(String path) {
/* 37 */     this.path = path;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Skin
 * JD-Core Version:    0.6.0
 */