/*    */ package com.enation.app.shop.core.plugin.search;
/*    */ 
/*    */ import com.enation.framework.util.StringUtil;
/*    */ 
/*    */ public class SearchSelector
/*    */ {
/*    */   private String name;
/*    */   private String url;
/*    */   private boolean isSelected;
/*    */   private String value;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */   public String getUrl() {
/* 24 */     if ((!StringUtil.isEmpty(this.url)) && (this.url.startsWith("/"))) {
/* 25 */       this.url = this.url.substring(1, this.url.length());
/*    */     }
/* 27 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 30 */     this.url = url;
/*    */   }
/*    */   public boolean getIsSelected() {
/* 33 */     return this.isSelected;
/*    */   }
/*    */   public void setSelected(boolean isSelected) {
/* 36 */     this.isSelected = isSelected;
/*    */   }
/*    */   public String getValue() {
/* 39 */     return this.value;
/*    */   }
/*    */   public void setValue(String value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.search.SearchSelector
 * JD-Core Version:    0.6.0
 */