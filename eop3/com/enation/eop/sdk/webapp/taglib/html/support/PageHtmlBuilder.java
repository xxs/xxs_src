/*     */ package com.enation.eop.sdk.webapp.taglib.html.support;
/*     */ 
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class PageHtmlBuilder
/*     */ {
/*     */   protected String url;
/*     */   protected HttpServletRequest request;
/*     */   private long pageNum;
/*     */   private long totalCount;
/*     */   private int pageSize;
/*     */   private long pageCount;
/*  19 */   private int showCount = 5;
/*     */ 
/*     */   public PageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize, HttpServletRequest _request) {
/*  22 */     this.pageNum = _pageNum;
/*  23 */     this.totalCount = _totalCount;
/*  24 */     this.pageSize = _pageSize;
/*  25 */     this.request = _request;
/*     */   }
/*     */ 
/*     */   public String buildPageHtml() {
/*  29 */     init();
/*  30 */     StringBuffer pageStr = new StringBuffer("");
/*  31 */     pageStr.append("<div class=\"page\" >");
/*  32 */     pageStr.append(getHeadString());
/*  33 */     pageStr.append(getBodyString());
/*  34 */     pageStr.append(getFooterString());
/*  35 */     pageStr.append("</div>");
/*  36 */     return pageStr.toString() + "\n<script>EopPager.init('" + this.url + "');</script>";
/*     */   }
/*     */ 
/*     */   protected void initUrl()
/*     */   {
/*  48 */     this.url = (this.request.getContextPath() + RequestUtil.getRequestUrl(this.request));
/*  49 */     this.url = this.url.replaceAll("(&||\\?)page=(\\d+)", "");
/*  50 */     this.url = this.url.replaceAll("(&||\\?)rmd=(\\d+)", "");
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  61 */     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);
/*     */ 
/*  63 */     this.pageCount = (this.totalCount / this.pageSize);
/*  64 */     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);
/*     */ 
/*  66 */     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
/*  67 */     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);
/*     */ 
/*  69 */     if (this.url == null)
/*  70 */       initUrl();
/*     */   }
/*     */ 
/*     */   protected String getHeadString()
/*     */   {
/*  81 */     StringBuffer headString = new StringBuffer("");
/*  82 */     headString.append("<span class=\"info\" >");
/*  83 */     headString.append("共");
/*  84 */     headString.append(this.totalCount);
/*  85 */     headString.append("条记录");
/*  86 */     headString.append("</span>\n");
/*     */ 
/*  88 */     headString.append("<span class=\"info\">");
/*  89 */     headString.append(this.pageNum);
/*  90 */     headString.append("/");
/*  91 */     headString.append(this.pageCount);
/*  92 */     headString.append("</span>\n");
/*     */ 
/*  94 */     headString.append("<ul>");
/*  95 */     if (this.pageNum > 1L)
/*     */     {
/*  97 */       headString.append("<li><a ");
/*     */ 
/*  99 */       headString.append(getUrlStr(1L));
/* 100 */       headString.append("|&lt;");
/* 101 */       headString.append("</a></li>\n");
/*     */ 
/* 103 */       headString.append("<li><a  ");
/*     */ 
/* 105 */       headString.append(getUrlStr(this.pageNum - 1L));
/*     */ 
/* 107 */       headString.append("&lt;&lt;");
/* 108 */       headString.append("</a></li>\n");
/*     */     }
/*     */ 
/* 111 */     return headString.toString();
/*     */   }
/*     */ 
/*     */   protected String getFooterString()
/*     */   {
/* 119 */     StringBuffer footerStr = new StringBuffer("");
/* 120 */     if (this.pageNum < this.pageCount)
/*     */     {
/* 122 */       footerStr.append("<li><a ");
/*     */ 
/* 124 */       footerStr.append(getUrlStr(this.pageNum + 1L));
/*     */ 
/* 126 */       footerStr.append("&gt;&gt;");
/* 127 */       footerStr.append("</a></li>\n");
/*     */ 
/* 129 */       footerStr.append("<li><a ");
/*     */ 
/* 131 */       footerStr.append(getUrlStr(this.pageCount));
/*     */ 
/* 133 */       footerStr.append("&gt;|");
/* 134 */       footerStr.append("</a></li>\n");
/*     */     }
/*     */ 
/* 137 */     footerStr.append("</ul>");
/* 138 */     return footerStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getBodyString()
/*     */   {
/* 147 */     StringBuffer pageStr = new StringBuffer();
/*     */ 
/* 149 */     long start = this.pageNum - this.showCount / 2;
/* 150 */     start = start <= 1L ? 1L : start;
/*     */ 
/* 152 */     long end = start + this.showCount;
/* 153 */     end = end > this.pageCount ? this.pageCount : end;
/*     */ 
/* 155 */     for (long i = start; i <= end; i += 1L)
/*     */     {
/* 157 */       pageStr.append("<li><a ");
/* 158 */       if (i != this.pageNum)
/*     */       {
/* 162 */         pageStr.append(getUrlStr(i));
/*     */       }
/* 164 */       else pageStr.append(" class=\"selected\">");
/*     */ 
/* 168 */       pageStr.append(i);
/* 169 */       pageStr.append("</a></li>\n");
/*     */     }
/*     */ 
/* 173 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getUrlStr(long page)
/*     */   {
/* 182 */     StringBuffer linkHtml = new StringBuffer();
/* 183 */     linkHtml.append("href='javascript:;'");
/* 184 */     linkHtml.append(" pageNo='");
/* 185 */     linkHtml.append(page);
/* 186 */     linkHtml.append("'>");
/* 187 */     return linkHtml.toString();
/*     */   }
/*     */ 
/*     */   public void setUrl(String _url)
/*     */   {
/* 192 */     this.url = _url;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.PageHtmlBuilder
 * JD-Core Version:    0.6.0
 */