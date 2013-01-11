/*     */ package com.enation.eop.sdk.webapp.taglib.html.support;
/*     */ 
/*     */ import com.enation.eop.sdk.webapp.plugin.AbstractPluginsBundle;
/*     */ import com.enation.eop.sdk.webapp.taglib.html.GridCellTaglib;
/*     */ import com.enation.eop.sdk.webapp.taglib.html.GridTaglib;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ 
/*     */ public class GridCellProvider extends AbstractPluginsBundle
/*     */ {
/*     */   private String sort;
/*     */   private String sortDefault;
/*     */   private String order;
/*     */   private int isSort;
/*     */   private String style;
/*     */   private String width;
/*     */   private String height;
/*     */   private String align;
/*     */   private String clazz;
/*     */   private GridCellTaglib celltaglib;
/*     */   private String plugin_type;
/*     */   private HttpServletRequest request;
/*     */   private HttpServletResponse response;
/*     */ 
/*     */   public void initCellProvider(GridCellTaglib _celltaglib)
/*     */   {
/*  44 */     this.celltaglib = _celltaglib;
/*  45 */     this.sort = this.celltaglib.getSort();
/*  46 */     this.sortDefault = this.celltaglib.getSortDefault();
/*     */ 
/*  48 */     this.isSort = this.celltaglib.getIsSort();
/*  49 */     this.style = this.celltaglib.getStyle();
/*  50 */     this.width = this.celltaglib.getWidth();
/*  51 */     this.height = this.celltaglib.getHeight();
/*  52 */     this.align = this.celltaglib.getAlign();
/*  53 */     this.request = this.celltaglib.getRequest();
/*  54 */     this.response = this.celltaglib.getResponse();
/*  55 */     this.order = this.request.getParameter("order");
/*  56 */     this.clazz = this.celltaglib.getClazz();
/*  57 */     this.plugin_type = this.celltaglib.getPlugin_type();
/*     */   }
/*     */ 
/*     */   public String getStartHtml() {
/*  61 */     if (this.order == null) this.isSort = 0; else this.isSort = 1;
/*     */ 
/*  63 */     this.order = ((this.order == null) || (this.order.equals("")) ? this.sort + " desc" : this.order);
/*  64 */     String str = "";
/*  65 */     String parentName = this.celltaglib.getParent().getClass().getName();
/*     */ 
/*  67 */     if ((parentName != null) && (parentName.endsWith("GridHeaderTaglib"))) {
/*  68 */       str = "<th ";
/*  69 */       if (this.width != null) {
/*  70 */         str = str + "width='" + this.width + "'";
/*     */       }
/*     */ 
/*  73 */       if (this.height != null) {
/*  74 */         str = str + "height='" + this.height + "'";
/*     */       }
/*     */ 
/*  77 */       if (this.align != null)
/*  78 */         str = str + "align='" + this.align + "'";
/*     */     }
/*     */     else {
/*  81 */       str = "<td ";
/*  82 */       if (this.align != null) {
/*  83 */         str = str + "align='" + this.align + "'";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  88 */     if (this.clazz != null) {
/*  89 */       str = str + " class='" + this.clazz + "'";
/*     */     }
/*     */ 
/*  92 */     if (this.style != null) {
/*  93 */       str = str + " style='" + this.style + "'";
/*     */     }
/*  95 */     str = str + ">";
/*     */ 
/*  97 */     init();
/*  98 */     if (this.sort != null) {
/*  99 */       str = str + getSortOpStart();
/*     */     }
/* 101 */     return str;
/*     */   }
/*     */ 
/*     */   private void init() {
/* 105 */     GridTaglib tag = (GridTaglib)this.celltaglib.getParent().getParent();
/*     */   }
/*     */ 
/*     */   private int getCanSort()
/*     */   {
/* 113 */     if (this.isSort == 0) {
/* 114 */       if ((this.sortDefault != null) && (this.sortDefault.equals("yes"))) {
/* 115 */         return 1;
/*     */       }
/* 117 */       return 0;
/*     */     }
/*     */ 
/* 121 */     String regEx = "(\\s+)";
/* 122 */     Pattern p = Pattern.compile(regEx);
/* 123 */     String[] op = p.split(this.order);
/*     */ 
/* 126 */     if (op[0].equals(this.sort)) {
/* 127 */       return 1;
/*     */     }
/*     */ 
/* 130 */     return 0;
/*     */   }
/*     */ 
/*     */   private String getUrl()
/*     */   {
/* 138 */     String contextPath = this.request.getContextPath();
/* 139 */     String queryString = this.request.getQueryString();
/*     */ 
/* 141 */     String servletPath = null;
/*     */ 
/* 143 */     servletPath = this.request.getServletPath();
/*     */ 
/* 146 */     queryString = queryString == null ? "" : queryString;
/* 147 */     String regEx = "(&||\\?)order=.*";
/* 148 */     Pattern p = Pattern.compile(regEx);
/* 149 */     Matcher m = p.matcher(queryString);
/* 150 */     queryString = m.replaceAll("");
/*     */ 
/* 152 */     queryString = "?" + queryString + "&";
/*     */ 
/* 154 */     return contextPath + servletPath + queryString;
/*     */   }
/*     */ 
/*     */   private String getSortOpStart()
/*     */   {
/* 167 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 169 */     if (this.order.endsWith("asc")) {
/* 170 */       buffer.append("<a href=\"");
/*     */ 
/* 172 */       buffer.append(getUrl());
/* 173 */       buffer.append("order=");
/* 174 */       buffer.append(this.sort);
/* 175 */       buffer.append(" desc");
/* 176 */       buffer.append("\"");
/*     */ 
/* 178 */       if (getCanSort() == 1) {
/* 179 */         buffer.append(" class=\"desc\"");
/*     */       }
/* 181 */       buffer.append(">");
/*     */     }
/*     */ 
/* 184 */     if (this.order.endsWith("desc"))
/*     */     {
/* 186 */       buffer.append("<a href=\"");
/*     */ 
/* 188 */       buffer.append(getUrl());
/* 189 */       buffer.append("order=");
/* 190 */       buffer.append(this.sort);
/* 191 */       buffer.append(" asc");
/* 192 */       buffer.append("\"");
/*     */ 
/* 194 */       if (getCanSort() == 1) {
/* 195 */         buffer.append(" class=\"asc\"");
/*     */       }
/* 197 */       buffer.append(">");
/*     */     }
/*     */ 
/* 201 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   private String getSortOpEnd()
/*     */   {
/* 213 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 215 */     buffer.append("</a>");
/*     */ 
/* 229 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public String getEndHtml()
/*     */   {
/* 237 */     String str = "";
/* 238 */     if (this.sort != null) {
/* 239 */       if (getCanSort() == 1)
/* 240 */         str = str + getSortOpEnd();
/*     */       else {
/* 242 */         str = str + "</a>";
/*     */       }
/*     */     }
/* 245 */     int isTh = 0;
/*     */ 
/* 247 */     String parentName = this.celltaglib.getParent().getClass().getName();
/* 248 */     if ((parentName != null) && (parentName.endsWith("GridHeaderTaglib"))) {
/* 249 */       str = str + "</th>";
/* 250 */       isTh = 1;
/*     */     } else {
/* 252 */       str = str + "</td>";
/*     */     }
/* 254 */     StringBuffer buffer = new StringBuffer();
/* 255 */     if (this.plugin_type != null)
/* 256 */       performPlugins("grid_cell_plugin", new Object[] { this.plugin_type, this.request, this.response, buffer, Integer.valueOf(isTh) });
/* 257 */     str = str + buffer.toString();
/*     */ 
/* 259 */     return str;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.GridCellProvider
 * JD-Core Version:    0.6.0
 */