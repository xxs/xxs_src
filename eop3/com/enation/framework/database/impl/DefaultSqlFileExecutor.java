/*     */ package com.enation.framework.database.impl;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public final class DefaultSqlFileExecutor
/*     */   implements ISqlFileExecutor
/*     */ {
/*     */   private JdbcTemplate jdbcTemplate;
/*  26 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public void execute(String sqlPath)
/*     */   {
/*  31 */     execute(sqlPath, false);
/*     */   }
/*     */ 
/*     */   public void execute(String sqlPath, boolean exampleData)
/*     */   {
/*     */     String content;
/*     */     String content;
/*  37 */     if (sqlPath.startsWith("file:")) {
/*  38 */       content = FileUtil.readFile(sqlPath.replaceAll("file:", ""));
/*     */     }
/*     */     else {
/*  41 */       content = sqlPath;
/*     */     }
/*     */ 
/*  44 */     batchExecute(content, exampleData);
/*     */   }
/*     */ 
/*     */   private void batchExecute(String content)
/*     */   {
/*  50 */     batchExecute(content, false);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   private void batchExecute(String content, boolean exampleData)
/*     */   {
/*  59 */     content = StringUtil.delSqlComment(content);
/*  60 */     content = content.replaceAll("\r", "");
/*  61 */     String spliter = ";\n";
/*  62 */     if ((EopSetting.DBTYPE.equals("2")) || (EopSetting.DBTYPE.equals("3"))) {
/*  63 */       if (EopSetting.DBTYPE.equals("2"))
/*  64 */         spliter = "\n/\n";
/*     */       else {
/*  66 */         spliter = "\ngo\n";
/*     */       }
/*     */     }
/*  69 */     String[] sql_ar = StringUtils.split(content, spliter);
/*     */ 
/*  81 */     if ((EopSetting.DBTYPE.equals("3")) && 
/*  82 */       (sql_ar.length == 1)) {
/*  83 */       sql_ar = content.split(";\n");
/*     */     }
/*     */ 
/*  87 */     if ((StringUtil.isEmpty(content)) || (sql_ar == null) || (sql_ar.length == 0)) return;
/*     */ 
/*  89 */     if (this.logger.isDebugEnabled()) {
/*  90 */       this.logger.debug("开始执行sql....");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       for (int i = 0; i < sql_ar.length; i++) {
/*  96 */         String s = sql_ar[i];
/*  97 */         if (!StringUtil.isEmpty(s)) {
/*  98 */           s = s.trim().replaceAll("\n", " ");
/*     */ 
/* 103 */           if (!s.startsWith("declare"))
/* 104 */             this.jdbcTemplate.execute(s);
/*     */         }
/*     */         else {
/* 107 */           System.out.println("is empty");
/*     */         }
/*     */       }
/*     */     } catch (RuntimeException e) {
/* 111 */       this.logger.error("执行sql出错", e.fillInStackTrace());
/* 112 */       throw e;
/*     */     }
/*     */ 
/* 115 */     if (this.logger.isDebugEnabled())
/* 116 */       this.logger.debug("执行完成");
/*     */   }
/*     */ 
/*     */   private String mysql_escape_string(String str)
/*     */   {
/* 123 */     if ((str == null) || (str.length() == 0))
/* 124 */       return str;
/* 125 */     str = str.replaceAll("'", "\\'");
/* 126 */     str = str.replaceAll("\"", "\\\"");
/* 127 */     return str;
/*     */   }
/*     */ 
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/* 131 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 135 */     String str = "abcajfjf[user]fj;ksafj;sajfoiju[rule]rrifj[delete]sdjfdf";
/* 136 */     System.out.println(str.replaceAll("([)(.*)(])", "$1"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.DefaultSqlFileExecutor
 * JD-Core Version:    0.6.0
 */