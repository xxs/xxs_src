/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class EopUtil
/*    */ {
/*    */   public static String wrapcss(String html, String wrapPath)
/*    */   {
/* 29 */     String pattern = "<link([^<|^>]*?)href=\"([^http|/eop|].*?)\"([^<|^>]*?)>";
/*    */ 
/* 31 */     Pattern p = Pattern.compile(pattern, 34);
/* 32 */     Matcher m = p.matcher(html);
/* 33 */     if (m.find()) {
/* 34 */       html = m.replaceAll("<link$1href=\"" + wrapPath + "$2\"$3>");
/*    */     }
/*    */ 
/* 37 */     return html;
/*    */   }
/*    */ 
/*    */   public static String wrapjavascript(String html, String wrapPath)
/*    */   {
/* 50 */     String pattern = "<script([^<|^>]*?)src=\"([^http|/eop].*?)\"([^<|^>]*?)>";
/*    */ 
/* 52 */     Pattern p = Pattern.compile(pattern, 34);
/* 53 */     Matcher m = p.matcher(html);
/*    */ 
/* 55 */     if (m.find()) {
/* 56 */       html = m.replaceAll("<script$1src=\"" + wrapPath + "$2\"$3>");
/*    */     }
/*    */ 
/* 61 */     return html;
/*    */   }
/*    */ 
/*    */   public static String wrapimage(String content, String wrapPath)
/*    */   {
/* 74 */     String pattern = "<img([^<|^>]*?)src=\"([^http|/eop].*?)\"([^<|^>]*?)>";
/*    */ 
/* 76 */     Pattern p = Pattern.compile(pattern, 34);
/* 77 */     Matcher m = p.matcher(content);
/*    */ 
/* 79 */     if (m.find()) {
/* 80 */       content = m.replaceAll("<img$1src=\"" + wrapPath + "$2\"$3>");
/*    */     }
/*    */ 
/* 83 */     return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.EopUtil
 * JD-Core Version:    0.6.0
 */