/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import com.enation.eop.sdk.utils.DateUtil;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class SQLServerSolution extends DBSolution
/*     */ {
/*     */   protected boolean setIdentity(String table, boolean on)
/*     */   {
/*  24 */     this.sqlExchange = new StringBuilder().append("SET IDENTITY_INSERT ").append(table).append(" ").append(on ? "ON" : "OFF").toString();
/*  25 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean beforeInsert(String table, String fields, String values)
/*     */   {
/*  30 */     return setIdentity(table, true);
/*     */   }
/*     */ 
/*     */   protected void afterInsert(String table, String field, String values)
/*     */   {
/*  35 */     setIdentity(table, false);
/*     */   }
/*     */ 
/*     */   public String toLocalType(String type, String size)
/*     */   {
/*  51 */     if ("int".equals(type)) {
/*  52 */       if ("1".equals(size)) {
/*  53 */         return "smallint";
/*     */       }
/*  55 */       return "int";
/*     */     }
/*     */ 
/*  58 */     if ("memo".equals(type)) {
/*  59 */       return "text";
/*     */     }
/*  61 */     if ("datetime".equals(type)) {
/*  62 */       return "datetime";
/*     */     }
/*  64 */     if ("long".equals(type)) {
/*  65 */       return "bigint";
/*     */     }
/*  67 */     return new StringBuilder().append(type).append("(").append(size).append(")").toString();
/*     */   }
/*     */ 
/*     */   public String getCreateSQL(Element action)
/*     */   {
/*  72 */     String table = getTableName(action.elementText("table"));
/*  73 */     List fields = action.elements("field");
/*  74 */     String sql = new StringBuilder().append(getDropSQL(table)).append("!-->").toString();
/*  75 */     sql = new StringBuilder().append(sql).append("create table ").append(table).append(" (").toString();
/*     */ 
/*  77 */     String pk = "";
/*  78 */     for (int i = 0; i < fields.size(); i++) {
/*  79 */       String nl = "";
/*  80 */       Element field = (Element)fields.get(i);
/*  81 */       String name = new StringBuilder().append("[").append(field.elementText("name")).append("]").toString();
/*  82 */       String size = field.elementText("size");
/*  83 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/*  85 */       String option = field.elementText("option");
/*  86 */       String def = field.elementText("default");
/*     */ 
/*  88 */       if ("1".equals(option.substring(1, 2))) {
/*  89 */         nl = " not null";
/*     */       }
/*  91 */       if (def != null) {
/*  92 */         nl = new StringBuilder().append(nl).append(" default ").append(def).toString();
/*     */       }
/*  94 */       if ("1".equals(option.substring(0, 1))) {
/*  95 */         pk = new StringBuilder().append("constraint PK_").append(table.toUpperCase()).append(" primary key nonclustered (").append(name).append("),").toString();
/*     */ 
/*  97 */         nl = new StringBuilder().append(nl).append(" identity").toString();
/*     */       }
/*     */ 
/* 100 */       sql = new StringBuilder().append(sql).append(name).append(" ").append(type).append(nl).append(",").toString();
/*     */     }
/* 102 */     sql = new StringBuilder().append(sql).append(pk).toString();
/* 103 */     sql = new StringBuilder().append(sql.substring(0, sql.length() - 1)).append(")").toString();
/*     */ 
/* 105 */     return sql;
/*     */   }
/*     */ 
/*     */   public String[] getFuncName()
/*     */   {
/* 113 */     String[] name = { "time" };
/* 114 */     return name;
/*     */   }
/*     */ 
/*     */   public String getFieldValue(int fieldType, Object fieldValue)
/*     */   {
/* 119 */     if ((fieldValue instanceof Timestamp)) {
/* 120 */       Date value = DateUtil.toDate(fieldValue.toString(), "yyyy-MM-dd HH:mm:ss.S");
/* 121 */       return new StringBuilder().append("time(").append(value.getTime()).append(")").toString();
/*     */     }
/* 123 */     return super.getFieldValue(fieldType, fieldValue);
/*     */   }
/*     */ 
/*     */   public String getDropSQL(String table)
/*     */   {
/* 128 */     String sql = new StringBuilder().append("if exists (select 1 from sysobjects where id = object_id('").append(table).append("')").append("and type = 'U') drop table ").append(table).toString();
/*     */ 
/* 133 */     return sql;
/*     */   }
/*     */ 
/*     */   public String getSaasCreateSQL(Element action, int userid, int siteid)
/*     */   {
/* 138 */     String table = getSaasTableName(action.elementText("table"), userid, siteid);
/* 139 */     List fields = action.elements("field");
/* 140 */     String sql = new StringBuilder().append(getDropSQL(table)).append("!-->").toString();
/* 141 */     sql = new StringBuilder().append(sql).append("create table ").append(table).append(" (").toString();
/*     */ 
/* 143 */     String pk = "";
/* 144 */     for (int i = 0; i < fields.size(); i++) {
/* 145 */       String nl = "";
/* 146 */       Element field = (Element)fields.get(i);
/* 147 */       String name = new StringBuilder().append("[").append(field.elementText("name")).append("]").toString();
/* 148 */       String size = field.elementText("size");
/* 149 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/* 151 */       String option = field.elementText("option");
/* 152 */       String def = field.elementText("default");
/*     */ 
/* 154 */       if ("1".equals(option.substring(1, 2))) {
/* 155 */         nl = " not null";
/*     */       }
/* 157 */       if (def != null) {
/* 158 */         nl = new StringBuilder().append(nl).append(" default ").append(def).toString();
/*     */       }
/* 160 */       if ("1".equals(option.substring(0, 1))) {
/* 161 */         pk = new StringBuilder().append("constraint PK_").append(table.toUpperCase()).append(" primary key nonclustered (").append(name).append("),").toString();
/*     */ 
/* 163 */         nl = new StringBuilder().append(nl).append(" identity").toString();
/*     */       }
/*     */ 
/* 166 */       sql = new StringBuilder().append(sql).append(name).append(" ").append(type).append(nl).append(",").toString();
/*     */     }
/* 168 */     sql = new StringBuilder().append(sql).append(pk).toString();
/* 169 */     sql = new StringBuilder().append(sql.substring(0, sql.length() - 1)).append(")").toString();
/*     */ 
/* 171 */     return sql;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.SQLServerSolution
 * JD-Core Version:    0.6.0
 */