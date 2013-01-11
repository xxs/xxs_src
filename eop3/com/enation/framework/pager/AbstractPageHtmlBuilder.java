/*     */ package com.enation.framework.pager;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public abstract class AbstractPageHtmlBuilder
/*     */   implements IPageHtmlBuilder
/*     */ {
/*     */   protected String url;
/*     */   protected HttpServletRequest request;
/*     */   protected long pageNum;
/*     */   protected long totalCount;
/*     */   protected int pageSize;
/*     */   protected long pageCount;
/*  24 */   private int showCount = 5;
/*     */ 
/*     */   public AbstractPageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
/*  27 */     this.pageNum = _pageNum;
/*  28 */     this.totalCount = _totalCount;
/*  29 */     this.pageSize = _pageSize;
/*  30 */     this.request = ThreadContextHolder.getHttpRequest();
/*     */   }
/*     */ 
/*     */   public String buildPageHtml() {
/*  34 */     init();
/*  35 */     StringBuffer pageStr = new StringBuffer("");
/*  36 */     pageStr.append("<div class=\"page\" >");
/*  37 */     pageStr.append(getHeadString());
/*  38 */     pageStr.append(getBodyString());
/*  39 */     pageStr.append(getFooterString());
/*  40 */     pageStr.append("</div>");
/*  41 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   protected void initUrl()
/*     */   {
/*  53 */     this.url = (this.request.getContextPath() + RequestUtil.getRequestUrl(this.request));
/*     */ 
/*  55 */     this.url = this.url.replaceAll("(&||\\?)page=(\\d+)", "");
/*  56 */     this.url = this.url.replaceAll("(&||\\?)rmd=(\\d+)", "");
/*  57 */     this.url += "?";
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/*  67 */     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);
/*     */ 
/*  69 */     this.pageCount = (this.totalCount / this.pageSize);
/*  70 */     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);
/*     */ 
/*  72 */     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
/*  73 */     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);
/*     */ 
/*  75 */     if (this.url == null)
/*  76 */       initUrl();
/*     */   }
/*     */ 
/*     */   protected String getHeadString()
/*     */   {
/*  87 */     StringBuffer headString = new StringBuffer("");
/*  88 */     headString.append("<span class=\"info\" >");
/*  89 */     headString.append("共");
/*  90 */     headString.append(this.totalCount);
/*  91 */     headString.append("条记录");
/*  92 */     headString.append("</span>\n");
/*     */ 
/*  94 */     headString.append("<span class=\"info\">");
/*  95 */     headString.append(this.pageNum);
/*  96 */     headString.append("/");
/*  97 */     headString.append(this.pageCount);
/*  98 */     headString.append("</span>\n");
/*     */ 
/* 100 */     headString.append("<ul>");
/* 101 */     if (this.pageNum > 1L)
/*     */     {
/* 103 */       headString.append("<li><a ");
/* 104 */       headString.append(" class=\"unselected\" ");
/* 105 */       headString.append(getUrlStr(1L));
/* 106 */       headString.append("|&lt;");
/* 107 */       headString.append("</a></li>\n");
/*     */ 
/* 109 */       headString.append("<li><a  ");
/* 110 */       headString.append(" class=\"unselected\" ");
/* 111 */       headString.append(getUrlStr(this.pageNum - 1L));
/* 112 */       headString.append("&lt;&lt;");
/* 113 */       headString.append("</a></li>\n");
/*     */     }
/*     */ 
/* 116 */     return headString.toString();
/*     */   }
/*     */ 
/*     */   protected String getFooterString()
/*     */   {
/* 124 */     StringBuffer footerStr = new StringBuffer("");
/* 125 */     if (this.pageNum < this.pageCount)
/*     */     {
/* 127 */       footerStr.append("<li><a ");
/* 128 */       footerStr.append(" class=\"unselected\" ");
/* 129 */       footerStr.append(getUrlStr(this.pageNum + 1L));
/* 130 */       footerStr.append("&gt;&gt;");
/* 131 */       footerStr.append("</a></li>\n");
/*     */ 
/* 133 */       footerStr.append("<li><a ");
/* 134 */       footerStr.append(" class=\"unselected\" ");
/* 135 */       footerStr.append(getUrlStr(this.pageCount));
/* 136 */       footerStr.append("&gt;|");
/* 137 */       footerStr.append("</a></li>\n");
/*     */     }
/*     */ 
/* 140 */     footerStr.append("</ul>");
/* 141 */     return footerStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getBodyString()
/*     */   {
/* 150 */     StringBuffer pageStr = new StringBuffer();
/*     */ 
/* 152 */     long start = this.pageNum - this.showCount / 2;
/* 153 */     start = start <= 1L ? 1L : start;
/*     */ 
/* 155 */     long end = start + this.showCount;
/* 156 */     end = end > this.pageCount ? this.pageCount : end;
/*     */ 
/* 158 */     for (long i = start; i <= end; i += 1L)
/*     */     {
/* 160 */       pageStr.append("<li><a ");
/* 161 */       if (i != this.pageNum) {
/* 162 */         pageStr.append(" class=\"unselected\"");
/* 163 */         pageStr.append(getUrlStr(i));
/*     */       } else {
/* 165 */         pageStr.append(" class=\"selected\">");
/*     */       }
/*     */ 
/* 169 */       pageStr.append(i);
/* 170 */       pageStr.append("</a></li>\n");
/*     */     }
/*     */ 
/* 174 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   protected abstract String getUrlStr(long paramLong);
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.AbstractPageHtmlBuilder
 * JD-Core Version:    0.6.0
 */