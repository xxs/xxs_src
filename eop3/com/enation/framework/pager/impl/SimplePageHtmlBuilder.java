/*    */ package com.enation.framework.pager.impl;
/*    */ 
/*    */ import com.enation.framework.pager.AbstractPageHtmlBuilder;
/*    */ 
/*    */ public class SimplePageHtmlBuilder extends AbstractPageHtmlBuilder
/*    */ {
/*    */   public SimplePageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
/*    */   {
/* 13 */     super(_pageNum, _totalCount, _pageSize);
/*    */   }
/*    */ 
/*    */   protected String getUrlStr(long page)
/*    */   {
/* 22 */     StringBuffer linkHtml = new StringBuffer();
/* 23 */     linkHtml.append("href='");
/* 24 */     linkHtml.append(this.url);
/* 25 */     linkHtml.append("page=");
/* 26 */     linkHtml.append(page);
/* 27 */     linkHtml.append("'>");
/* 28 */     return linkHtml.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.impl.SimplePageHtmlBuilder
 * JD-Core Version:    0.6.0
 */