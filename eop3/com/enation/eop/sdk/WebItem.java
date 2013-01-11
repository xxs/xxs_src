/*    */ package com.enation.eop.sdk;
/*    */ 
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class WebItem
/*    */ {
/*    */   private String style;
/*    */   private String target;
/*    */   private String text;
/*    */   private String url;
/*    */   private String className;
/*    */ 
/*    */   public WebItem()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WebItem(String text)
/*    */   {
/* 20 */     this.text = text;
/*    */   }
/*    */ 
/*    */   public String getStyle()
/*    */   {
/* 30 */     return this.style;
/*    */   }
/*    */   public void setStyle(String style) {
/* 33 */     this.style = style;
/*    */   }
/*    */   public String getTarget() {
/* 36 */     return this.target;
/*    */   }
/*    */   public void setTarget(String target) {
/* 39 */     this.target = target;
/*    */   }
/*    */   public String getText() {
/* 42 */     return this.text;
/*    */   }
/*    */   public void setText(String text) {
/* 45 */     this.text = text;
/*    */   }
/*    */   public String getUrl() {
/* 48 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 51 */     this.url = url;
/*    */   }
/*    */ 
/*    */   public String getClassName() {
/* 55 */     return this.className;
/*    */   }
/*    */   public void setClassName(String className) {
/* 58 */     this.className = className;
/*    */   }
/*    */ 
/*    */   public String toJson() {
/* 62 */     JSONObject jsonObject = JSONObject.fromObject(this);
/* 63 */     return jsonObject.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.WebItem
 * JD-Core Version:    0.6.0
 */