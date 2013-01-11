/*    */ package com.enation.eop.sdk.webapp.taglib.html.support;
/*    */ 
/*    */ import com.enation.framework.pager.AbstractPageHtmlBuilder;
/*    */ 
/*    */ public class GridAjaxPageHtmlBuilder extends AbstractPageHtmlBuilder
/*    */ {
/*    */   private String gridid;
/*    */ 
/*    */   public GridAjaxPageHtmlBuilder(long pageNum, long totalCount, int pageSize, String _gridid)
/*    */   {
/* 19 */     super(pageNum, totalCount, pageSize);
/* 20 */     this.gridid = _gridid;
/*    */   }
/*    */ 
/*    */   public String buildPageHtml()
/*    */   {
/* 29 */     return super.buildPageHtml() + "<script>$(function(){$(\".gridbody[gridid='" + this.gridid + "']>.page\").gridAjaxPager('" + this.url + "');});</script>";
/*    */   }
/*    */ 
/*    */   protected String getUrlStr(long page)
/*    */   {
/* 39 */     StringBuffer linkHtml = new StringBuffer();
/* 40 */     linkHtml.append("href='javascript:;'");
/* 41 */     linkHtml.append(" pageNo='");
/* 42 */     linkHtml.append(page);
/* 43 */     linkHtml.append("'>");
/* 44 */     return linkHtml.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.GridAjaxPageHtmlBuilder
 * JD-Core Version:    0.6.0
 */