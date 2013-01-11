/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class HtmlUtil
/*    */ {
/*    */   public static String appendTo(String html, String nodeName, String content)
/*    */   {
/* 20 */     return html.replaceAll("</" + nodeName + ">", content + "</" + nodeName + ">");
/*    */   }
/*    */ 
/*    */   public static String insertTo(String html, String nodeName, String content)
/*    */   {
/* 31 */     return html.replaceAll("<" + nodeName + ">", "<" + nodeName + ">" + content);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 35 */     String html = "<html><head>adfbb</head><body>adfadsf</body></html>";
/* 36 */     html = insertTo(html, "head", "abc");
/* 37 */     System.out.println(html);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.HtmlUtil
 * JD-Core Version:    0.6.0
 */