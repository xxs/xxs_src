/*    */ package com.enation.framework.pager.impl;
/*    */ 
/*    */ import com.enation.framework.pager.AbstractPageHtmlBuilder;
/*    */ 
/*    */ public class AjaxPagerHtmlBuilder extends AbstractPageHtmlBuilder
/*    */ {
/*    */   public AjaxPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
/*    */   {
/*  8 */     super(_pageNum, _totalCount, _pageSize);
/*    */   }
/*    */ 
/*    */   protected String getUrlStr(long page)
/*    */   {
/* 14 */     StringBuffer linkHtml = new StringBuffer();
/* 15 */     linkHtml.append("href='javascript:;'");
/* 16 */     linkHtml.append("page='");
/* 17 */     linkHtml.append(page);
/* 18 */     linkHtml.append("'>");
/* 19 */     return linkHtml.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.impl.AjaxPagerHtmlBuilder
 * JD-Core Version:    0.6.0
 */