/*     */ package com.enation.app.shop.core.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class SqlParser
/*     */ {
/*     */   private static final String Comma = ",";
/*     */   private static final String FourSpace = "    ";
/*  22 */   private static boolean isSingleLine = true;
/*     */   private String sql;
/*     */   private String cols;
/*     */   private String tables;
/*     */   private String conditions;
/*     */   private String groupCols;
/*     */   private String orderCols;
/*     */ 
/*     */   public SqlParser(String sql)
/*     */   {
/*  60 */     this.sql = sql.trim();
/*     */ 
/*  62 */     parseCols();
/*  63 */     parseTables();
/*  64 */     parseConditions();
/*  65 */     parseGroupCols();
/*  66 */     parseOrderCols();
/*     */   }
/*     */ 
/*     */   private void parseCols()
/*     */   {
/*  74 */     String regex = "(select)(.+)(from)";
/*     */ 
/*  76 */     this.cols = getMatchedString(regex, this.sql);
/*     */   }
/*     */ 
/*     */   private void parseTables()
/*     */   {
/*  84 */     String regex = "";
/*     */ 
/*  86 */     if (isContains(this.sql, "\\s+where\\s+")) {
/*  87 */       regex = "(from)(.+)(where)";
/*     */     }
/*     */     else {
/*  90 */       regex = "(from)(.+)($)";
/*     */     }
/*     */ 
/*  93 */     this.tables = getMatchedString(regex, this.sql);
/*     */   }
/*     */ 
/*     */   private void parseConditions()
/*     */   {
/* 101 */     String regex = "";
/*     */ 
/* 103 */     if (isContains(this.sql, "\\s+where\\s+"))
/*     */     {
/* 106 */       if (isContains(this.sql, "group\\s+by"))
/*     */       {
/* 108 */         regex = "(where)(.+)(group\\s+by)";
/*     */       }
/* 110 */       else if (isContains(this.sql, "order\\s+by"))
/*     */       {
/* 112 */         regex = "(where)(.+)(order\\s+by)";
/*     */       }
/*     */       else
/*     */       {
/* 116 */         regex = "(where)(.+)($)";
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 121 */       return;
/*     */     }
/*     */ 
/* 124 */     this.conditions = getMatchedString(regex, this.sql);
/*     */   }
/*     */ 
/*     */   private void parseGroupCols()
/*     */   {
/* 132 */     String regex = "";
/*     */ 
/* 134 */     if (isContains(this.sql, "group\\s+by"))
/*     */     {
/* 137 */       if (isContains(this.sql, "order\\s+by"))
/*     */       {
/* 139 */         regex = "(group\\s+by)(.+)(order\\s+by)";
/*     */       }
/*     */       else
/*     */       {
/* 143 */         regex = "(group\\s+by)(.+)($)";
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 148 */       return;
/*     */     }
/*     */ 
/* 151 */     this.groupCols = getMatchedString(regex, this.sql);
/*     */   }
/*     */ 
/*     */   private void parseOrderCols()
/*     */   {
/* 159 */     String regex = "";
/*     */ 
/* 161 */     if (isContains(this.sql, "order\\s+by"))
/*     */     {
/* 163 */       regex = "(order\\s+by)(.+)($)";
/*     */     }
/*     */     else
/*     */     {
/* 167 */       return;
/*     */     }
/*     */ 
/* 170 */     this.orderCols = getMatchedString(regex, this.sql);
/*     */   }
/*     */ 
/*     */   private static String getMatchedString(String regex, String text)
/*     */   {
/* 181 */     Pattern pattern = Pattern.compile(regex, 2);
/*     */ 
/* 183 */     Matcher matcher = pattern.matcher(text);
/*     */ 
/* 185 */     if (matcher.find()) {
/* 186 */       return matcher.group(2);
/*     */     }
/*     */ 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   private static boolean isContains(String lineText, String word)
/*     */   {
/* 199 */     Pattern pattern = Pattern.compile(word, 2);
/* 200 */     Matcher matcher = pattern.matcher(lineText);
/*     */ 
/* 203 */     return matcher.find();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 209 */     if ((this.cols == null) && (this.tables == null) && (this.conditions == null) && (this.groupCols == null) && (this.orderCols == null)) {
/* 210 */       return this.sql;
/*     */     }
/*     */ 
/* 213 */     StringBuffer sb = new StringBuffer();
/* 214 */     sb.append("原SQL为" + this.sql + "\n");
/* 215 */     sb.append("解析后的SQL为\n");
/*     */ 
/* 218 */     for (String str : getParsedSqlList()) {
/* 219 */       sb.append(str);
/*     */     }
/*     */ 
/* 222 */     sb.append("\n");
/*     */ 
/* 224 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String getAddEnterStr(String str, String splitStr)
/*     */   {
/* 234 */     Pattern p = Pattern.compile(splitStr, 2);
/*     */ 
/* 238 */     Matcher m = p.matcher(str);
/* 239 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 242 */     boolean result = m.find();
/*     */ 
/* 245 */     while (result) {
/* 246 */       m.appendReplacement(sb, m.group(0) + "\n     ");
/* 247 */       result = m.find();
/*     */     }
/*     */ 
/* 250 */     m.appendTail(sb);
/*     */ 
/* 252 */     return "    " + sb.toString();
/*     */   }
/*     */ 
/*     */   public List<String> getParsedSqlList()
/*     */   {
/* 260 */     List sqlList = new ArrayList();
/*     */ 
/* 263 */     if ((this.cols == null) && (this.tables == null) && (this.conditions == null) && (this.groupCols == null) && (this.orderCols == null)) {
/* 264 */       sqlList.add(this.sql);
/* 265 */       return sqlList;
/*     */     }
/*     */ 
/* 268 */     if (this.cols != null) {
/* 269 */       sqlList.add("select\n");
/*     */ 
/* 272 */       if (isSingleLine) {
/* 273 */         sqlList.add(getAddEnterStr(this.cols, ","));
/*     */       }
/*     */       else {
/* 276 */         sqlList.add("    " + this.cols);
/*     */       }
/*     */     }
/*     */ 
/* 280 */     if (this.tables != null) {
/* 281 */       sqlList.add(" \nfrom\n");
/*     */ 
/* 283 */       if (isSingleLine) {
/* 284 */         sqlList.add(getAddEnterStr(this.tables, ","));
/*     */       }
/*     */       else {
/* 287 */         sqlList.add("    " + this.tables);
/*     */       }
/*     */     }
/*     */ 
/* 291 */     if (this.conditions != null) {
/* 292 */       sqlList.add(" \nwhere\n");
/*     */ 
/* 294 */       if (isSingleLine) {
/* 295 */         sqlList.add(getAddEnterStr(this.conditions, "(and|or)"));
/*     */       }
/*     */       else
/*     */       {
/* 300 */         sqlList.add("    " + this.conditions);
/*     */       }
/*     */     }
/*     */ 
/* 304 */     if (this.groupCols != null) {
/* 305 */       sqlList.add(" \ngroup by\n");
/*     */ 
/* 307 */       if (isSingleLine) {
/* 308 */         sqlList.add(getAddEnterStr(this.groupCols, ","));
/*     */       }
/*     */       else {
/* 311 */         sqlList.add("    " + this.groupCols);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 317 */     if (this.orderCols != null) {
/* 318 */       sqlList.add(" \norder by\n");
/*     */ 
/* 320 */       if (isSingleLine) {
/* 321 */         sqlList.add(getAddEnterStr(this.orderCols, ","));
/*     */       }
/*     */       else {
/* 324 */         sqlList.add("    " + this.orderCols);
/*     */       }
/*     */     }
/*     */ 
/* 328 */     return sqlList;
/*     */   }
/*     */ 
/*     */   public static void setSingleLine(boolean isSingleLine)
/*     */   {
/* 337 */     isSingleLine = isSingleLine;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 345 */     List ls = new ArrayList();
/* 346 */     ls.add("select * from dual");
/* 347 */     ls.add("SELECT * frOm dual");
/* 348 */     ls.add("Select C1,c2 From tb");
/* 349 */     ls.add("select c1,c2 from tb");
/* 350 */     ls.add("select count(*) from t1");
/* 351 */     ls.add("select c1,c2,c3 from t1 where condi1=1 ");
/* 352 */     ls.add("Select c1,c2,c3 From t1 Where condi1=1 ");
/* 353 */     ls.add("select c1,c2,c3 from t1,t2 where condi3=3 or condi4=5 order   by o1,o2");
/* 354 */     ls.add("Select c1,c2,c3 from t1,t2 Where condi3=3 or condi4=5 Order   by o1,o2");
/* 355 */     ls.add("select c1,c2,c3 from t1,t2,t3 where condi1=5 and condi6=6 or condi7=7 group  by g1,g2");
/* 356 */     ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2");
/* 357 */     ls.add("Select c1,c2,c3 From t1,t2,t3 Where condi1=5 and condi6=6 or condi7=7 Group  by g1,g2,g3 order  by g2,g3");
/*     */     String sql;
/* 360 */     for (Iterator i$ = ls.iterator(); i$.hasNext(); sql = (String)i$.next());
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.utils.SqlParser
 * JD-Core Version:    0.6.0
 */