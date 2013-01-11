/*    */ package com.enation.eop.sdk.webapp.taglib;
/*    */ 
/*    */ import javax.servlet.jsp.JspException;
/*    */ 
/*    */ public abstract class HtmlTaglib extends BaseTaglibSupport
/*    */ {
/*  7 */   protected String startHtml = "";
/*  8 */   protected String endHtml = "";
/*    */ 
/*    */   protected void startAppend(String html) {
/* 11 */     this.startHtml += html;
/*    */   }
/*    */   protected void endAppend(String html) {
/* 14 */     this.endHtml += html;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException {
/* 18 */     this.startHtml = "";
/* 19 */     print(postStart());
/* 20 */     return 1;
/*    */   }
/*    */ 
/*    */   public int doAfterBody() {
/* 24 */     String content = postEnd();
/* 25 */     print(content);
/* 26 */     return 0;
/*    */   }
/*    */ 
/*    */   protected abstract String postStart();
/*    */ 
/*    */   protected abstract String postEnd();
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.HtmlTaglib
 * JD-Core Version:    0.6.0
 */