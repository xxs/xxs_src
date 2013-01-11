/*     */ package com.enation.eop.sdk.webapp.taglib.html;
/*     */ 
/*     */ import com.enation.eop.sdk.webapp.bean.Grid;
/*     */ import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;
/*     */ import com.enation.eop.sdk.webapp.taglib.html.support.GridAjaxPageHtmlBuilder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.pager.IPageHtmlBuilder;
/*     */ import com.enation.framework.pager.impl.SimplePageHtmlBuilder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ 
/*     */ public class GridTaglib extends HtmlTaglib
/*     */ {
/*     */   private String from;
/*     */   private String pager;
/*     */   private String gridid;
/*     */   private String ajax;
/*     */ 
/*     */   protected String postStart()
/*     */   {
/*  24 */     this.gridid = ("" + System.currentTimeMillis());
/*  25 */     return "<div class=\"gridbody\"  gridid='" + this.gridid + "' ><table>";
/*     */   }
/*     */ 
/*     */   protected String postEnd()
/*     */   {
/*  30 */     StringBuffer str = new StringBuffer();
/*  31 */     str.append("</table>");
/*  32 */     if ((this.pager == null) || (this.pager.equals("yes"))) {
/*  33 */       str.append(buildPageHtml());
/*     */     }
/*  35 */     str.append("</div>");
/*  36 */     return str.toString();
/*     */   }
/*     */ 
/*     */   private String buildPageHtml()
/*     */   {
/*  42 */     String tempPage = getRequest().getParameter("page");
/*  43 */     int pageNo = 1;
/*  44 */     if ((tempPage != null) && (!tempPage.toString().equals(""))) {
/*  45 */       pageNo = Integer.valueOf(tempPage.toString()).intValue();
/*     */     }
/*     */ 
/*  49 */     Object obj = this.pageContext.getAttribute(this.from);
/*  50 */     if (obj == null) {
/*  51 */       obj = getRequest().getAttribute(this.from);
/*  52 */       if (obj == null) {
/*  53 */         return "";
/*     */       }
/*  55 */       this.pageContext.setAttribute(this.from, obj);
/*     */     }
/*     */ 
/*  59 */     Page page = null;
/*  60 */     if ((obj instanceof Page))
/*  61 */       page = (Page)obj;
/*  62 */     else if ((obj instanceof Grid))
/*  63 */       page = ((Grid)obj).getWebpage();
/*     */     else {
/*  65 */       return "";
/*     */     }
/*  67 */     int pageSize = page.getPageSize();
/*  68 */     long totalCount = page.getTotalCount();
/*     */ 
/*  70 */     IPageHtmlBuilder pageHtmlBuilder = null;
/*  71 */     if ("yes".equals(this.ajax))
/*     */     {
/*  73 */       pageHtmlBuilder = new GridAjaxPageHtmlBuilder(pageNo, totalCount, pageSize, this.gridid);
/*     */     }
/*     */     else
/*     */     {
/*  77 */       pageHtmlBuilder = new SimplePageHtmlBuilder(pageNo, totalCount, pageSize);
/*     */     }
/*     */ 
/*  80 */     return pageHtmlBuilder.buildPageHtml();
/*     */   }
/*     */ 
/*     */   public String getFrom()
/*     */   {
/*  85 */     return this.from;
/*     */   }
/*     */ 
/*     */   public void setFrom(String from) {
/*  89 */     this.from = from;
/*     */   }
/*     */ 
/*     */   public String getPager() {
/*  93 */     return this.pager;
/*     */   }
/*     */ 
/*     */   public void setPager(String pager) {
/*  97 */     this.pager = pager;
/*     */   }
/*     */ 
/*     */   public String getAjax() {
/* 101 */     return this.ajax;
/*     */   }
/*     */ 
/*     */   public void setAjax(String ajax) {
/* 105 */     this.ajax = ajax;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridTaglib
 * JD-Core Version:    0.6.0
 */