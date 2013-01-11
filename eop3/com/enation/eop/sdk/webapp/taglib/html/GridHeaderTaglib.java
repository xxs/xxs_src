/*    */ package com.enation.eop.sdk.webapp.taglib.html;
/*    */ 
/*    */ import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;
/*    */ 
/*    */ public class GridHeaderTaglib extends HtmlTaglib
/*    */ {
/*    */   protected String postStart()
/*    */   {
/*  9 */     return "<thead><tr>";
/*    */   }
/*    */ 
/*    */   protected String postEnd()
/*    */   {
/* 14 */     return "</tr></thead>";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridHeaderTaglib
 * JD-Core Version:    0.6.0
 */