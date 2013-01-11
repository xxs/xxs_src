/*    */ package com.enation.framework.pager.impl;
/*    */ 
/*    */ import com.enation.framework.pager.AbstractPageHtmlBuilder;
/*    */ 
/*    */ public class WidgetPagerHtmlBuilder extends AbstractPageHtmlBuilder
/*    */ {
/*    */   public WidgetPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
/*    */   {
/*  7 */     super(_pageNum, _totalCount, _pageSize);
/*    */   }
/*    */ 
/*    */   protected String getUrlStr(long page)
/*    */   {
/* 13 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.impl.WidgetPagerHtmlBuilder
 * JD-Core Version:    0.6.0
 */