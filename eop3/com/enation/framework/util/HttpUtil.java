/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class HttpUtil
/*     */ {
/*     */   public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int time)
/*     */   {
/*     */     try
/*     */     {
/*  35 */       if (cookieValue != null)
/*  36 */         cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
/*     */     }
/*     */     catch (Exception ex) {
/*  39 */       ex.printStackTrace();
/*     */     }
/*     */ 
/*  43 */     Cookie cookie = new Cookie(cookieName, cookieValue);
/*  44 */     cookie.setMaxAge(time);
/*  45 */     cookie.setPath("/");
/*  46 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public static void addCookie(HttpServletResponse response, String domain, String path, String cookieName, String cookieValue, int time) {
/*     */     try {
/*  51 */       cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
/*     */     } catch (Exception ex) {
/*     */     }
/*  54 */     Cookie cookie = new Cookie(cookieName, cookieValue);
/*  55 */     cookie.setMaxAge(time);
/*  56 */     cookie.setDomain(domain);
/*  57 */     cookie.setPath(path);
/*  58 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public static void addCookie1(HttpServletResponse response, String cookieName, String cookieValue, int time)
/*     */   {
/*  64 */     Cookie cookie = new Cookie(cookieName, cookieValue);
/*  65 */     cookie.setMaxAge(time);
/*  66 */     cookie.setPath("/");
/*  67 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public static String getCookieValue(HttpServletRequest request, String cookieName, String domain, String path) {
/*  71 */     Cookie[] cookies = request.getCookies();
/*     */ 
/*  73 */     if (cookies != null) {
/*  74 */       for (int i = 0; i < cookies.length; i++) {
/*  75 */         if ((domain.equals(cookies[i].getDomain())) && (path.equals(cookies[i].getPath())) && (cookieName.equals(cookies[i].getName()))) {
/*  76 */           return cookies[i].getValue();
/*     */         }
/*     */       }
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getCookieValue(HttpServletRequest request, String cookieName)
/*     */   {
/*  93 */     Cookie[] cookies = request.getCookies();
/*     */ 
/*  95 */     if (cookies != null) {
/*  96 */       for (int i = 0; i < cookies.length; i++)
/*     */       {
/*  98 */         if (cookieName.equals(cookies[i].getName())) {
/*  99 */           return cookies[i].getValue();
/*     */         }
/*     */       }
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 107 */     String value = "%40g.139-341-na-1%2C183-493-na-1%3B";
/*     */     try {
/* 109 */       value = URLDecoder.decode(value, "UTF-8");
/* 110 */       System.out.println(value);
/*     */     } catch (UnsupportedEncodingException e) {
/* 112 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.HttpUtil
 * JD-Core Version:    0.6.0
 */