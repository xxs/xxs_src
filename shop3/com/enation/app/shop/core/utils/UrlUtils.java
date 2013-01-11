/*     */ package com.enation.app.shop.core.utils;
/*     */ 
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public abstract class UrlUtils
/*     */ {
/*  18 */   private static String[] searchFields = { "cat", "brand", "price", "sort", "prop", "nattr", "page", "tag", "keyword" };
/*     */ 
/*     */   public static String getParamStr(String servletPath)
/*     */   {
/*  22 */     String pattern = "/search-(.*).html";
/*  23 */     Pattern p = Pattern.compile(pattern, 34);
/*  24 */     Matcher m = p.matcher(servletPath);
/*  25 */     String str = servletPath;
/*  26 */     if (m.find()) {
/*  27 */       str = m.replaceAll("$1");
/*     */     }
/*  29 */     return str;
/*     */   }
/*     */ 
/*     */   public static String addUrl(String url, String name, String value)
/*     */   {
/*  41 */     String pattern = "/search-(.*)";
/*  42 */     Pattern p = Pattern.compile(pattern, 34);
/*  43 */     Matcher m = p.matcher(url);
/*  44 */     String str = "";
/*  45 */     if (m.find()) {
/*  46 */       str = m.replaceAll("$1");
/*     */     }
/*  48 */     if ((url != null) && (url.equals("/search"))) {
/*  49 */       return "/search-" + name + "-" + value + ".html";
/*     */     }
/*  51 */     if ((str == null) || (str.equals(""))) {
/*  52 */       return url;
/*     */     }
/*  54 */     str = getExParamUrl(str, "page");
/*     */ 
/*  56 */     String newUrl = "";
/*  57 */     String temp = null;
/*  58 */     for (String field : searchFields) {
/*  59 */       temp = getParamStringValue(str, field);
/*  60 */       if (temp != null) {
/*  61 */         newUrl = newUrl + "-" + field + "-" + temp;
/*     */       }
/*  63 */       if ((name != null) && (name.equals(field))) {
/*  64 */         newUrl = newUrl + "-" + field + "-" + value;
/*     */       }
/*     */     }
/*  67 */     return "search" + newUrl + ".html";
/*     */   }
/*     */ 
/*     */   public static String getParamStringValue(String param, String name)
/*     */   {
/*  82 */     param = getParamStr(param);
/*  83 */     String pattern = "(.*)" + name + "\\-(.[^\\-]*)(.*)";
/*  84 */     String value = null;
/*  85 */     Pattern p = Pattern.compile(pattern, 34);
/*  86 */     Matcher m = p.matcher(param);
/*  87 */     if (m.find()) {
/*  88 */       value = m.replaceAll("$2");
/*     */     }
/*  90 */     return value;
/*     */   }
/*     */ 
/*     */   public static int getParamInitValue(String param, String name)
/*     */   {
/*  96 */     String temp_str = getParamStringValue(param, name);
/*     */ 
/*  98 */     int value = Integer.valueOf(temp_str).intValue();
/*  99 */     return value;
/*     */   }
/*     */ 
/*     */   public static String getExParamUrl(String url, String name)
/*     */   {
/* 111 */     String pattern = "(.*)" + name + "\\-(.[^\\-]*)(\\-|.*)(.*)";
/*     */ 
/* 113 */     String value = "";
/* 114 */     Pattern p = Pattern.compile(pattern, 34);
/* 115 */     Matcher m = p.matcher(url);
/*     */ 
/* 117 */     if (m.find())
/* 118 */       value = m.replaceAll("$1$4");
/*     */     else {
/* 120 */       value = url;
/*     */     }
/* 122 */     if (value != null) {
/* 123 */       if (value.startsWith("-")) {
/* 124 */         value = value.substring(1, value.length());
/*     */       }
/* 126 */       if (value.endsWith("-")) {
/* 127 */         value = value.substring(0, value.length() - 1);
/*     */       }
/*     */     }
/* 130 */     return value;
/*     */   }
/*     */ 
/*     */   public static String appendParamValue(String url, String name, String value)
/*     */   {
/* 148 */     String old_value = getParamStringValue(url, name);
/* 149 */     String new_url = getExParamUrl(url, name);
/*     */ 
/* 151 */     if (old_value != null) {
/* 152 */       if ("prop".equals(name)) {
/* 153 */         old_value = old_value.replaceAll(value.split("_")[0] + "_(\\d+)", "");
/* 154 */         old_value = old_value.replace(",,", ",");
/* 155 */         if (old_value.startsWith(",")) {
/* 156 */           old_value = old_value.substring(1, old_value.length());
/*     */         }
/*     */ 
/* 159 */         if (old_value.endsWith(",")) {
/* 160 */           old_value = old_value.substring(0, old_value.length() - 1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 165 */       if (!old_value.equals(""))
/* 166 */         value = "," + value;
/*     */     }
/*     */     else {
/* 169 */       old_value = "";
/*     */     }
/*     */ 
/* 173 */     new_url = addUrl(new_url, name, old_value + value);
/*     */ 
/* 175 */     return new_url;
/*     */   }
/*     */ 
/*     */   public static String getUrlPrefix(String url)
/*     */   {
/* 188 */     String pattern = "/(.*)-(.*)";
/*     */ 
/* 190 */     String value = null;
/* 191 */     Pattern p = Pattern.compile(pattern, 34);
/* 192 */     Matcher m = p.matcher(url);
/*     */ 
/* 194 */     if (m.find())
/* 195 */       value = m.replaceAll("$1");
/*     */     else {
/* 197 */       value = null;
/*     */     }
/* 199 */     return value;
/*     */   }
/*     */ 
/*     */   public static String getPropExSelf(int index, String url)
/*     */   {
/* 211 */     if (StringUtil.isEmpty(url)) {
/* 212 */       return url;
/*     */     }
/* 214 */     String propstr = getParamStringValue(url, "prop");
/* 215 */     if (StringUtil.isEmpty(propstr)) return url;
/*     */ 
/* 217 */     String newprostr = "";
/* 218 */     String[] propar = propstr.split(",");
/* 219 */     for (String prop : propar) {
/* 220 */       String[] ar = prop.split("_");
/* 221 */       if (!ar[0].equals("" + index)) {
/* 222 */         if (!newprostr.equals(""))
/* 223 */           newprostr = newprostr + ",";
/* 224 */         newprostr = newprostr + prop;
/*     */       }
/*     */     }
/* 227 */     if (!StringUtil.isEmpty(newprostr))
/* 228 */       url = url.replaceAll(propstr, newprostr);
/*     */     else {
/* 230 */       url = url.replaceAll("-prop-" + propstr, "");
/*     */     }
/* 232 */     return url;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 240 */     String url = "cat{102}prop{2_1,1_2,0_3}name{2010}page{9}";
/* 241 */     String newUrl = "cat-102-page-9-prop-2_1,1_2,0_3";
/*     */ 
/* 258 */     String temp = "/search-cat-1";
/* 259 */     System.out.println(addUrl(temp, "circlar", "3"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.utils.UrlUtils
 * JD-Core Version:    0.6.0
 */