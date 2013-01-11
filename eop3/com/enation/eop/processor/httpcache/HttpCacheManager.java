/*     */ package com.enation.eop.processor.httpcache;
/*     */ 
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.cache.CacheFactory;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class HttpCacheManager
/*     */ {
/*     */   private static ICache<Long> urlCache;
/*     */   private static ICache<Long> uriCache;
/*     */ 
/*     */   public static boolean getIsCached(String url)
/*     */   {
/*  40 */     long now = System.currentTimeMillis();
/*  41 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  42 */     HttpServletResponse response = ThreadContextHolder.getHttpResponse();
/*  43 */     if (EopSetting.HTTPCACHE == 0) {
/*  44 */       response.setStatus(200);
/*  45 */       response.setDateHeader("Last-Modified", now);
/*  46 */       response.setDateHeader("Expires", 0L);
/*  47 */       response.setHeader("Cache-Control", "max-age=0");
/*     */ 
/*  49 */       return false;
/*     */     }
/*     */ 
/*  52 */     if (url.equals("/")) url = "/index.html";
/*     */ 
/*  54 */     response.setDateHeader("Expires", 0L);
/*  55 */     response.setHeader("Cache-Control", "max-age=0");
/*     */ 
/*  57 */     long modifiedSince = request.getDateHeader("If-Modified-Since");
/*     */ 
/*  59 */     Long lastModified = getLastModified(url);
/*     */ 
/*  71 */     if (lastModified == null)
/*     */     {
/*  73 */       response.setStatus(200);
/*  74 */       response.setDateHeader("Last-Modified", now);
/*     */ 
/*  77 */       return false;
/*     */     }
/*  79 */     if (lastModified.longValue() / 1000L == modifiedSince / 1000L) {
/*  80 */       response.setStatus(304);
/*  81 */       response.setDateHeader("Last-Modified", lastModified.longValue());
/*  82 */       return true;
/*     */     }
/*  84 */     response.setStatus(200);
/*  85 */     response.setDateHeader("Last-Modified", lastModified.longValue());
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   public static void sessionChange()
/*     */   {
/*  95 */     ThreadContextHolder.getSessionContext().setAttribute("sessionchangetime", Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public static void updateUriModified(String uri)
/*     */   {
/* 103 */     long now = System.currentTimeMillis();
/*     */ 
/* 105 */     getUriCache().put(uri, Long.valueOf(now));
/*     */   }
/*     */ 
/*     */   public static void updateUrlModified(String url)
/*     */   {
/* 114 */     long now = System.currentTimeMillis();
/*     */ 
/* 116 */     getUrlCache().put(url, Long.valueOf(now));
/*     */   }
/*     */ 
/*     */   private static Long getLastModified(String url)
/*     */   {
/* 128 */     ThemeUri themeUri = getCachedThemeUri(url);
/* 129 */     if (themeUri == null)
/*     */     {
/* 131 */       return null;
/*     */     }
/*     */ 
/* 137 */     String uri = themeUri.getUri();
/* 138 */     Long uriLastModified = (Long)getUriCache().get(uri);
/* 139 */     Long urlLastModified = (Long)getUrlCache().get(url);
/*     */ 
/* 142 */     Long sessionchangetime = (Long)ThreadContextHolder.getSessionContext().getAttribute("sessionchangetime");
/* 143 */     if (sessionchangetime != null) {
/* 144 */       if (urlLastModified != null) {
/* 145 */         if (sessionchangetime.longValue() > urlLastModified.longValue())
/* 146 */           return sessionchangetime;
/*     */       }
/*     */       else {
/* 149 */         return sessionchangetime;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 155 */     if (urlLastModified == null) {
/* 156 */       if (uriLastModified == null) {
/* 157 */         long now = System.currentTimeMillis();
/* 158 */         getUrlCache().put(url, Long.valueOf(System.currentTimeMillis()));
/*     */       }
/*     */       else
/*     */       {
/* 163 */         getUrlCache().put(url, uriLastModified);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 170 */     if (uriLastModified == null)
/*     */     {
/* 172 */       return urlLastModified;
/*     */     }
/*     */ 
/* 176 */     if (urlLastModified == null) {
/* 177 */       return uriLastModified;
/*     */     }
/*     */ 
/* 181 */     if (uriLastModified.longValue() > urlLastModified.longValue())
/*     */     {
/* 183 */       return uriLastModified;
/*     */     }
/*     */ 
/* 186 */     return urlLastModified;
/*     */   }
/*     */ 
/*     */   private static ThemeUri getCachedThemeUri(String url)
/*     */   {
/* 200 */     IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
/* 201 */     List themeUriList = themeUriManager.list();
/* 202 */     for (ThemeUri themeUri : themeUriList) {
/* 203 */       Matcher m = themeUri.getPattern().matcher(url);
/* 204 */       if (m.find()) {
/* 205 */         if (themeUri.getHttpcache() == 1) {
/* 206 */           return themeUri;
/*     */         }
/* 208 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */   private static ICache<Long> getUrlCache()
/*     */   {
/* 220 */     if (urlCache == null) {
/* 221 */       urlCache = CacheFactory.getCache("httpUrlCache");
/*     */     }
/*     */ 
/* 224 */     return urlCache;
/*     */   }
/*     */ 
/*     */   private static ICache<Long> getUriCache() {
/* 228 */     if (uriCache == null) {
/* 229 */       uriCache = CacheFactory.getCache("httpUriCache");
/*     */     }
/*     */ 
/* 232 */     return uriCache;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.httpcache.HttpCacheManager
 * JD-Core Version:    0.6.0
 */