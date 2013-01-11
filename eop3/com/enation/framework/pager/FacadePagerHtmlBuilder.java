/*     */ package com.enation.framework.pager;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.io.PrintStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FacadePagerHtmlBuilder
/*     */ {
/*     */   protected String url;
/*     */   private HttpServletRequest request;
/*     */   private long pageNum;
/*     */   private long totalCount;
/*     */   private int pageSize;
/*     */   private long pageCount;
/*  19 */   private int showCount = 10;
/*     */ 
/*     */   public FacadePagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
/*  22 */     this.pageNum = _pageNum;
/*  23 */     this.totalCount = _totalCount;
/*  24 */     this.pageSize = _pageSize;
/*  25 */     this.request = ThreadContextHolder.getHttpRequest();
/*     */   }
/*     */ 
/*     */   public String buildPageHtml() {
/*  29 */     init();
/*  30 */     StringBuffer pageStr = new StringBuffer("");
/*  31 */     pageStr.append("<table align=\"right\" class=\"pager\"><tbody><tr>");
/*  32 */     pageStr.append(getHeadString());
/*  33 */     pageStr.append(getBodyString());
/*  34 */     pageStr.append(getFooterString());
/*  35 */     pageStr.append("</tr></tbody></table>");
/*  36 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  50 */     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);
/*     */ 
/*  52 */     this.pageCount = (this.totalCount / this.pageSize);
/*  53 */     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);
/*     */ 
/*  55 */     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
/*  56 */     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);
/*     */ 
/*  59 */     this.url = this.request.getServletPath();
/*     */   }
/*     */ 
/*     */   protected String getHeadString()
/*     */   {
/*  74 */     StringBuffer headString = new StringBuffer("");
/*  75 */     headString.append("<td>");
/*     */ 
/*  79 */     if (this.pageNum > 1L)
/*     */     {
/*  81 */       headString.append("<a title=\"上一页\"");
/*  82 */       headString.append(" onmouseout=\"this.className = 'prev'\" ");
/*  83 */       headString.append("  onmouseover=\"this.className = 'onprev'\" ");
/*  84 */       headString.append(" class=\"prev\" ");
/*  85 */       headString.append(" href=\"");
/*  86 */       headString.append(getUrlStr(this.pageNum - 1L));
/*  87 */       headString.append("\" >上一页");
/*  88 */       headString.append("</a>\n");
/*     */     }
/*     */     else {
/*  91 */       headString.append("<span title=\"已经是第一页\" ");
/*  92 */       headString.append(" class=\"prev\"> 已经是第一页</span>");
/*     */     }
/*  94 */     headString.append("</td>");
/*  95 */     return headString.toString();
/*     */   }
/*     */ 
/*     */   protected String getFooterString()
/*     */   {
/* 106 */     StringBuffer footerStr = new StringBuffer("");
/* 107 */     footerStr.append("<td style=\"padding-right: 20px;\">");
/* 108 */     if (this.pageNum < this.pageCount)
/*     */     {
/* 111 */       footerStr.append("<a title=\"下一页\" onmouseout=\"this.className = 'next'\" onmouseover=\"this.className = 'onnext'\" class=\"next\" ");
/* 112 */       footerStr.append(" href=\"");
/* 113 */       footerStr.append(getUrlStr(this.pageNum + 1L));
/* 114 */       footerStr.append("\"");
/* 115 */       footerStr.append("下一页</a>");
/*     */     }
/*     */     else {
/* 118 */       footerStr.append("<span title=\"已经是最后一页\" class=\"next\">已经是最后一页</span>");
/*     */     }
/* 120 */     footerStr.append("</td>\n");
/* 121 */     return footerStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getBodyString()
/*     */   {
/* 133 */     StringBuffer pageStr = new StringBuffer();
/*     */ 
/* 135 */     long start = this.pageNum - this.showCount / 2;
/* 136 */     start = start <= 1L ? 1L : start;
/*     */ 
/* 138 */     long end = start + this.showCount;
/* 139 */     end = end > this.pageCount ? this.pageCount : end;
/* 140 */     pageStr.append("<td>");
/*     */ 
/* 142 */     for (long i = start; i <= end; i += 1L)
/*     */     {
/* 145 */       if (i != this.pageNum) {
/* 146 */         pageStr.append("<a");
/* 147 */         pageStr.append(" href=\"");
/*     */ 
/* 149 */         pageStr.append(getUrlStr(i));
/* 150 */         pageStr.append("\">");
/*     */ 
/* 152 */         pageStr.append(i);
/* 153 */         pageStr.append("</a>\n");
/*     */       } else {
/* 155 */         pageStr.append(" <strong class=\"pagecurrent\">");
/* 156 */         pageStr.append(i);
/* 157 */         pageStr.append("</strong> ");
/*     */       }
/*     */     }
/*     */ 
/* 161 */     pageStr.append("</td>");
/* 162 */     return pageStr.toString();
/*     */   }
/*     */ 
/*     */   protected String getUrlStr(long page)
/*     */   {
/* 173 */     return "?page=" + page;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 177 */     String url = "/articleList-1-2.html";
/* 178 */     String pattern = "/(.*)-(\\d+)-(\\d+).html";
/* 179 */     Pattern p = Pattern.compile(pattern, 6);
/* 180 */     Matcher m = p.matcher(url);
/* 181 */     if (m.find());
/* 189 */     System.out.println(url);
/*     */   }
/*     */ 
/*     */   private String findUrl(String url)
/*     */   {
/* 202 */     String pattern = "(.*)(p(\\d))(.*).html";
/* 203 */     Pattern p = Pattern.compile(pattern, 34);
/* 204 */     Matcher m = p.matcher(url);
/* 205 */     if (m.find()) {
/* 206 */       String s = m.replaceAll("$1");
/* 207 */       return s + "-";
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.FacadePagerHtmlBuilder
 * JD-Core Version:    0.6.0
 */