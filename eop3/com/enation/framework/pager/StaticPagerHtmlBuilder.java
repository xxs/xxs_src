/*     */ package com.enation.framework.pager;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class StaticPagerHtmlBuilder
/*     */ {
/*     */   protected String url;
/*     */   private HttpServletRequest request;
/*     */   private long pageNum;
/*     */   private long totalCount;
/*     */   private int pageSize;
/*     */   private long pageCount;
/*  24 */   private int showCount = 10;
/*     */ 
/*     */   public StaticPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
/*  27 */     this.pageNum = _pageNum;
/*  28 */     this.totalCount = _totalCount;
/*  29 */     this.pageSize = _pageSize;
/*  30 */     this.request = ThreadContextHolder.getHttpRequest();
/*     */   }
/*     */ 
/*     */   public String buildPageHtml() {
/*  34 */     init();
/*  35 */     StringBuffer pageStr = new StringBuffer("");
/*  36 */     pageStr.append("<table align=\"right\" class=\"pager\"><tbody><tr>");
/*  37 */     pageStr.append(getHeadString());
/*  38 */     pageStr.append(getBodyString());
/*  39 */     pageStr.append(getFooterString());
/*  40 */     pageStr.append("</tr></tbody></table>");
/*  41 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  55 */     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);
/*     */ 
/*  57 */     this.pageCount = (this.totalCount / this.pageSize);
/*  58 */     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);
/*     */ 
/*  60 */     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
/*  61 */     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);
/*     */ 
/*  64 */     this.url = this.request.getServletPath();
/*     */   }
/*     */ 
/*     */   protected String getHeadString()
/*     */   {
/*  76 */     StringBuffer headString = new StringBuffer("");
/*  77 */     headString.append("<td>");
/*     */ 
/*  81 */     if (this.pageNum > 1L)
/*     */     {
/*  83 */       headString.append("<a title=\"上一页\"");
/*  84 */       headString.append(" onmouseout=\"this.className = 'prev'\" ");
/*  85 */       headString.append("  onmouseover=\"this.className = 'onprev'\" ");
/*  86 */       headString.append(" class=\"prev\" ");
/*  87 */       headString.append(" href=\"");
/*  88 */       headString.append(getUrlStr(this.pageNum - 1L));
/*  89 */       headString.append("\" >上一页");
/*  90 */       headString.append("</a>\n");
/*     */     }
/*     */     else {
/*  93 */       headString.append("<span title=\"已经是第一页\" ");
/*  94 */       headString.append(" class=\"prev\"> 已经是第一页</span>");
/*     */     }
/*  96 */     headString.append("</td>");
/*  97 */     return headString.toString();
/*     */   }
/*     */ 
/*     */   protected String getFooterString()
/*     */   {
/* 108 */     StringBuffer footerStr = new StringBuffer("");
/* 109 */     footerStr.append("<td style=\"padding-right: 20px;\">");
/* 110 */     if (this.pageNum < this.pageCount)
/*     */     {
/* 113 */       footerStr.append("<a title=\"下一页\" onmouseout=\"this.className = 'next'\" onmouseover=\"this.className = 'onnext'\" class=\"next\" ");
/* 114 */       footerStr.append(" href=\"");
/* 115 */       footerStr.append(getUrlStr(this.pageNum + 1L));
/* 116 */       footerStr.append("\"");
/* 117 */       footerStr.append("下一页</a>");
/*     */     }
/*     */     else {
/* 120 */       footerStr.append("<span title=\"已经是最后一页\" class=\"next\">已经是最后一页</span>");
/*     */     }
/* 122 */     footerStr.append("</td>\n");
/* 123 */     return footerStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getBodyString()
/*     */   {
/* 135 */     StringBuffer pageStr = new StringBuffer();
/*     */ 
/* 137 */     long start = this.pageNum - this.showCount / 2;
/* 138 */     start = start <= 1L ? 1L : start;
/*     */ 
/* 140 */     long end = start + this.showCount;
/* 141 */     end = end > this.pageCount ? this.pageCount : end;
/* 142 */     pageStr.append("<td>");
/*     */ 
/* 144 */     for (long i = start; i <= end; i += 1L)
/*     */     {
/* 147 */       if (i != this.pageNum) {
/* 148 */         pageStr.append("<a");
/* 149 */         pageStr.append(" href=\"");
/*     */ 
/* 151 */         pageStr.append(getUrlStr(i));
/* 152 */         pageStr.append("\">");
/*     */ 
/* 154 */         pageStr.append(i);
/* 155 */         pageStr.append("</a>\n");
/*     */       } else {
/* 157 */         pageStr.append(" <strong class=\"pagecurrent\">");
/* 158 */         pageStr.append(i);
/* 159 */         pageStr.append("</strong> ");
/*     */       }
/*     */     }
/*     */ 
/* 163 */     pageStr.append("</td>");
/* 164 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getUrlStr(long page)
/*     */   {
/* 175 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/* 176 */     String uri = RequestUtil.getRequestUrl(httpRequest);
/* 177 */     String pattern = "(.*)-(\\d+)-(\\d+).html";
/* 178 */     Pattern p = Pattern.compile(pattern, 6);
/* 179 */     Matcher m = p.matcher(uri);
/* 180 */     String page_url = "";
/* 181 */     if (m.find()) {
/* 182 */       page_url = m.replaceAll("$1-$2");
/*     */     }
/*     */ 
/* 185 */     page_url = this.request.getContextPath() + page_url + "-" + page;
/* 186 */     return page_url + ".html";
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 191 */     String url = "/articleList-1-2.html";
/* 192 */     String pattern = "/(.*)-(\\d+)-(\\d+).html";
/* 193 */     Pattern p = Pattern.compile(pattern, 6);
/* 194 */     Matcher m = p.matcher(url);
/* 195 */     if (m.find());
/* 203 */     System.out.println(url);
/*     */   }
/*     */ 
/*     */   private String findUrl(String url)
/*     */   {
/* 216 */     String pattern = "(.*)(p(\\d))(.*).html";
/* 217 */     Pattern p = Pattern.compile(pattern, 34);
/* 218 */     Matcher m = p.matcher(url);
/* 219 */     if (m.find()) {
/* 220 */       String s = m.replaceAll("$1");
/* 221 */       return s + "-";
/*     */     }
/* 223 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.StaticPagerHtmlBuilder
 * JD-Core Version:    0.6.0
 */