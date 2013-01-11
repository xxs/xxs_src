/*    */ package com.enation.framework.database.impl;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class SqlPaser
/*    */ {
/*    */   public static String insertSelectField(String field, String sql)
/*    */   {
/* 15 */     sql = "select " + field + "," + sql.substring(6, sql.length());
/* 16 */     return sql;
/*    */   }
/*    */ 
/*    */   public static String findOrderStr(String sql)
/*    */   {
/* 27 */     String pattern = "(order\\s*by[\\w|\\W|\\s|\\S]*)";
/* 28 */     Pattern p = Pattern.compile(pattern, 34);
/* 29 */     Matcher m = p.matcher(sql);
/*    */ 
/* 31 */     if (m.find()) {
/* 32 */       return m.group();
/*    */     }
/*    */ 
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 40 */     String sql = "select * from abc where 12=12 order by id asc ";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.SqlPaser
 * JD-Core Version:    0.6.0
 */