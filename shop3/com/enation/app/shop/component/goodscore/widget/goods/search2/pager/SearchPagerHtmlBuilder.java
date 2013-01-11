/*    */ package com.enation.app.shop.component.goodscore.widget.goods.search2.pager;
/*    */ 
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.pager.AbstractPageHtmlBuilder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class SearchPagerHtmlBuilder extends AbstractPageHtmlBuilder
/*    */ {
/*    */   public SearchPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
/*    */   {
/* 29 */     super(_pageNum, _totalCount, _pageSize);
/*    */   }
/*    */ 
/*    */   protected void init()
/*    */   {
/* 39 */     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);
/*    */ 
/* 41 */     this.pageCount = (this.totalCount / this.pageSize);
/* 42 */     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);
/*    */ 
/* 44 */     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
/* 45 */     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);
/*    */ 
/* 47 */     this.url = this.request.getServletPath();
/*    */ 
/* 49 */     if (!StringUtil.isEmpty(EopSetting.ENCODING)) {
/* 50 */       this.url = StringUtil.to(this.url, EopSetting.ENCODING);
/*    */     }
/*    */ 
/* 53 */     this.url = UrlUtils.getParamStr(this.url);
/* 54 */     this.url = UrlUtils.getExParamUrl(this.url, "page");
/*    */   }
/*    */ 
/*    */   protected String getUrlStr(long page)
/*    */   {
/* 66 */     String page_url = "search-" + UrlUtils.getExParamUrl(this.url, "page");
/* 67 */     page_url = page_url + "-page-" + page;
/*    */ 
/* 69 */     page_url = page_url + ".html";
/*    */ 
/* 72 */     StringBuffer linkHtml = new StringBuffer();
/* 73 */     linkHtml.append("href='");
/* 74 */     linkHtml.append(page_url);
/* 75 */     linkHtml.append("'>");
/* 76 */     return linkHtml.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search2.pager.SearchPagerHtmlBuilder
 * JD-Core Version:    0.6.0
 */