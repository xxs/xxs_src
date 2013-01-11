/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public abstract class RequestUtil
/*     */ {
/*     */   public static Map<String, String> paramToMap(HttpServletRequest request)
/*     */   {
/*  24 */     Map params = new HashMap();
/*     */ 
/*  26 */     Map rMap = request.getParameterMap();
/*  27 */     Iterator rIter = rMap.keySet().iterator();
/*     */ 
/*  29 */     while (rIter.hasNext()) {
/*  30 */       Object key = rIter.next();
/*  31 */       String value = request.getParameter(key.toString());
/*  32 */       if ((key != null) && (value != null)) {
/*  33 */         params.put(key.toString(), value.toString());
/*     */       }
/*     */     }
/*  36 */     return params;
/*     */   }
/*     */ 
/*     */   public static String getRequestUrl(HttpServletRequest request)
/*     */   {
/*  42 */     String pathInfo = request.getPathInfo();
/*  43 */     String queryString = request.getQueryString();
/*     */ 
/*  45 */     String uri = request.getServletPath();
/*     */ 
/*  47 */     if (uri == null) {
/*  48 */       uri = request.getRequestURI();
/*  49 */       uri = uri.substring(request.getContextPath().length());
/*     */     }
/*     */ 
/*  52 */     return new StringBuilder().append(uri).append(pathInfo == null ? "" : pathInfo).append(queryString == null ? "" : new StringBuilder().append("?").append(queryString).toString()).toString();
/*     */   }
/*     */ 
/*     */   public static String getWholeUrl(HttpServletRequest request)
/*     */   {
/*  61 */     String servername = request.getServerName();
/*  62 */     String path = request.getServletPath();
/*  63 */     int port = request.getServerPort();
/*     */ 
/*  65 */     String portstr = "";
/*  66 */     if (port != 80) {
/*  67 */       portstr = new StringBuilder().append(":").append(port).toString();
/*     */     }
/*  69 */     String contextPath = request.getContextPath();
/*  70 */     if (contextPath.equals("/")) {
/*  71 */       contextPath = "";
/*     */     }
/*     */ 
/*  75 */     String url = new StringBuilder().append("http://").append(servername).append(portstr).append(contextPath).append("/").append(path).toString();
/*     */ 
/*  77 */     return url;
/*     */   }
/*     */ 
/*     */   public static String getDomain()
/*     */   {
/*  88 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  89 */     String port = new StringBuilder().append("").append(request.getServerPort()).toString();
/*  90 */     if (port.equals("80"))
/*  91 */       port = "";
/*     */     else {
/*  93 */       port = new StringBuilder().append(":").append(port).toString();
/*     */     }
/*     */ 
/*  96 */     String contextPath = request.getContextPath();
/*  97 */     if (contextPath.equals("/")) {
/*  98 */       contextPath = "";
/*     */     }
/*     */ 
/* 101 */     String domain = new StringBuilder().append("http://").append(request.getServerName()).append(port).toString();
/* 102 */     domain = new StringBuilder().append(domain).append(contextPath).toString();
/* 103 */     return domain;
/*     */   }
/*     */ 
/*     */   public static Integer getIntegerValue(HttpServletRequest request, String name)
/*     */   {
/* 113 */     String value = request.getParameter(name);
/* 114 */     if (StringUtil.isEmpty(value)) {
/* 115 */       return null;
/*     */     }
/* 117 */     return Integer.valueOf(value);
/*     */   }
/*     */ 
/*     */   public static Double getDoubleValue(HttpServletRequest request, String name)
/*     */   {
/* 124 */     String value = request.getParameter(name);
/* 125 */     if (StringUtil.isEmpty(value)) {
/* 126 */       return null;
/*     */     }
/* 128 */     return Double.valueOf(value);
/*     */   }
/*     */ 
/*     */   public static int getIntValue(HttpServletRequest request, String name)
/*     */   {
/* 143 */     String value = request.getParameter(name);
/* 144 */     if (StringUtil.isEmpty(value)) {
/* 145 */       return 0;
/*     */     }
/* 147 */     return Integer.valueOf(value).intValue();
/*     */   }
/*     */ 
/*     */   public static String getRequestMethod(HttpServletRequest request)
/*     */   {
/* 152 */     String method = request.getParameter("_method");
/* 153 */     method = method == null ? "get" : method;
/* 154 */     method = method.toUpperCase();
/* 155 */     return method;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.RequestUtil
 * JD-Core Version:    0.6.0
 */