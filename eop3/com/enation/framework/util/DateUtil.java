/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class DateUtil
/*     */ {
/*     */   public static Date toDate(String date, String pattern)
/*     */   {
/*  22 */     if (("" + date).equals("")) {
/*  23 */       return null;
/*     */     }
/*  25 */     if (pattern == null) {
/*  26 */       pattern = "yyyy-MM-dd";
/*     */     }
/*  28 */     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
/*  29 */     Date newDate = new Date();
/*     */     try {
/*  31 */       newDate = sdf.parse(date);
/*     */     } catch (Exception ex) {
/*  33 */       ex.printStackTrace();
/*     */     }
/*  35 */     return newDate;
/*     */   }
/*     */ 
/*     */   public static String toString(Date date, String pattern)
/*     */   {
/*  45 */     if (date == null) {
/*  46 */       return "";
/*     */     }
/*  48 */     if (pattern == null) {
/*  49 */       pattern = "yyyy-MM-dd";
/*     */     }
/*  51 */     String dateString = "";
/*  52 */     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
/*     */     try {
/*  54 */       dateString = sdf.format(date);
/*     */     } catch (Exception ex) {
/*  56 */       ex.printStackTrace();
/*     */     }
/*  58 */     return dateString;
/*     */   }
/*     */ 
/*     */   public static String toString(Long time, String pattern) {
/*  62 */     if (time.longValue() > 0L) {
/*  63 */       if (time.toString().length() == 10) {
/*  64 */         time = Long.valueOf(time.longValue() * 1000L);
/*     */       }
/*  66 */       Date date = new Date(time.longValue());
/*  67 */       String str = toString(date, pattern);
/*  68 */       return str;
/*     */     }
/*  70 */     return "";
/*     */   }
/*     */ 
/*     */   public static String[] getLastMonth()
/*     */   {
/*  81 */     Calendar cal = Calendar.getInstance();
/*  82 */     int year = cal.get(1);
/*  83 */     int month = cal.get(2) + 1;
/*     */ 
/*  86 */     cal.set(5, 1);
/*     */ 
/*  89 */     cal.add(5, -1);
/*     */ 
/*  92 */     int day = cal.get(5);
/*     */ 
/*  94 */     String months = "";
/*  95 */     String days = "";
/*     */ 
/*  97 */     if (month > 1) {
/*  98 */       month--;
/*     */     } else {
/* 100 */       year--;
/* 101 */       month = 12;
/*     */     }
/* 103 */     if (String.valueOf(month).length() <= 1)
/* 104 */       months = "0" + month;
/*     */     else {
/* 106 */       months = String.valueOf(month);
/*     */     }
/* 108 */     if (String.valueOf(day).length() <= 1)
/* 109 */       days = "0" + day;
/*     */     else {
/* 111 */       days = String.valueOf(day);
/*     */     }
/* 113 */     String firstDay = "" + year + "-" + months + "-01";
/* 114 */     String lastDay = "" + year + "-" + months + "-" + days;
/*     */ 
/* 116 */     String[] lastMonth = new String[2];
/* 117 */     lastMonth[0] = firstDay;
/* 118 */     lastMonth[1] = lastDay;
/*     */ 
/* 121 */     return lastMonth;
/*     */   }
/*     */ 
/*     */   public static String[] getCurrentMonth()
/*     */   {
/* 131 */     Calendar cal = Calendar.getInstance();
/* 132 */     int year = cal.get(1);
/* 133 */     int month = cal.get(2) + 1;
/*     */ 
/* 136 */     cal.set(5, 1);
/*     */ 
/* 139 */     cal.add(5, -1);
/*     */ 
/* 142 */     int day = cal.get(5);
/*     */ 
/* 144 */     String months = "";
/* 145 */     String days = "";
/*     */ 
/* 148 */     if (String.valueOf(month).length() <= 1)
/* 149 */       months = "0" + month;
/*     */     else {
/* 151 */       months = String.valueOf(month);
/*     */     }
/* 153 */     if (String.valueOf(day).length() <= 1)
/* 154 */       days = "0" + day;
/*     */     else {
/* 156 */       days = String.valueOf(day);
/*     */     }
/* 158 */     String firstDay = "" + year + "-" + months + "-01";
/* 159 */     String lastDay = "" + year + "-" + months + "-" + days;
/*     */ 
/* 161 */     String[] currentMonth = new String[2];
/* 162 */     currentMonth[0] = firstDay;
/* 163 */     currentMonth[1] = lastDay;
/*     */ 
/* 166 */     return currentMonth;
/*     */   }
/*     */ 
/*     */   public static int getDateline()
/*     */   {
/* 173 */     return (int)(System.currentTimeMillis() / 1000L);
/*     */   }
/*     */ 
/*     */   public static long getDatelineLong()
/*     */   {
/* 179 */     return System.currentTimeMillis() / 1000L;
/*     */   }
/*     */ 
/*     */   public static int getDateline(String date) {
/* 183 */     return (int)(toDate(date, "yyyy-MM-dd").getTime() / 1000L);
/*     */   }
/*     */   public static int getDateline(String date, String pattern) {
/* 186 */     return (int)(toDate(date, pattern).getTime() / 1000L);
/*     */   }
/*     */   public static long getDatelineLong(String date) {
/* 189 */     return toDate(date, "yyyy-MM-dd").getTime() / 1000L;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 210 */     System.out.println(new Date(1320205608000L));
/* 211 */     System.out.println(toString(new Date(1320205608000L), "yyyy-MM-dd HH:mm:ss"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.DateUtil
 * JD-Core Version:    0.6.0
 */