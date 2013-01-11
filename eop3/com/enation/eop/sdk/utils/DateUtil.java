/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DateUtil
/*    */ {
/*    */   public static Date toDate(String date, String pattern)
/*    */   {
/* 21 */     if (("" + date).equals("")) {
/* 22 */       return null;
/*    */     }
/* 24 */     if (pattern == null) {
/* 25 */       pattern = "yyyy-MM-dd";
/*    */     }
/* 27 */     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
/* 28 */     Date newDate = new Date();
/*    */     try {
/* 30 */       newDate = sdf.parse(date);
/*    */     } catch (Exception ex) {
/* 32 */       ex.printStackTrace();
/*    */     }
/* 34 */     return newDate;
/*    */   }
/*    */ 
/*    */   public static String toString(Date date, String pattern)
/*    */   {
/* 44 */     if (date == null) {
/* 45 */       return "";
/*    */     }
/* 47 */     if (pattern == null) {
/* 48 */       pattern = "yyyy-MM-dd";
/*    */     }
/* 50 */     String dateString = "";
/* 51 */     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
/*    */     try {
/* 53 */       dateString = sdf.format(date);
/*    */     } catch (Exception ex) {
/* 55 */       ex.printStackTrace();
/*    */     }
/* 57 */     return dateString;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.DateUtil
 * JD-Core Version:    0.6.0
 */