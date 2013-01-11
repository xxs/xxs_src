/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class MySQLSolution extends DBSolution
/*     */ {
/*     */   public String toLocalType(String type, String size)
/*     */   {
/*  26 */     if ("int".equals(type)) {
/*  27 */       if ("1".equals(size)) {
/*  28 */         return "smallint(1)";
/*     */       }
/*  30 */       return "int(" + size + ")";
/*     */     }
/*     */ 
/*  33 */     if ("memo".equals(type)) {
/*  34 */       return "longtext";
/*     */     }
/*  36 */     if ("datetime".equals(type)) {
/*  37 */       return "datetime";
/*     */     }
/*  39 */     if ("long".equals(type)) {
/*  40 */       return "bigint";
/*     */     }
/*  42 */     return type + "(" + size + ")";
/*     */   }
/*     */ 
/*     */   public String getCreateSQL(Element action)
/*     */   {
/*  50 */     String table = getTableName(action.elementText("table"));
/*  51 */     List fields = action.elements("field");
/*     */ 
/*  53 */     String sql = getDropSQL(table) + "!-->";
/*  54 */     sql = sql + "create table " + table + " (";
/*     */ 
/*  56 */     String pk = "";
/*  57 */     for (int i = 0; i < fields.size(); i++) {
/*  58 */       String nl = "";
/*  59 */       Element field = (Element)fields.get(i);
/*  60 */       String name = field.elementText("name");
/*  61 */       String size = field.elementText("size");
/*  62 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/*  64 */       String option = field.elementText("option");
/*  65 */       String def = field.elementText("default");
/*     */ 
/*  67 */       if ("1".equals(option.substring(1, 2))) {
/*  68 */         nl = " not null";
/*     */       }
/*  70 */       if (def != null) {
/*  71 */         nl = nl + " default " + def;
/*     */       }
/*  73 */       if ("1".equals(option.substring(0, 1))) {
/*  74 */         pk = "primary key (" + name + "),";
/*  75 */         nl = nl + " auto_increment";
/*     */       }
/*     */ 
/*  78 */       sql = sql + name + " " + type + nl + ",";
/*     */     }
/*  80 */     sql = sql + pk;
/*  81 */     sql = sql.substring(0, sql.length() - 1) + ") ENGINE = MYISAM;";
/*  82 */     return sql;
/*     */   }
/*     */ 
/*     */   protected Object getFuncValue(String name, String value)
/*     */   {
/*  93 */     if ("time".equals(name)) {
/*  94 */       Date date = new Date(Long.parseLong(value));
/*  95 */       return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "'";
/*     */     }
/*     */ 
/*  99 */     return super.getFuncValue(name, value);
/*     */   }
/*     */ 
/*     */   public String[] getFuncName()
/*     */   {
/* 107 */     String[] name = { "time" };
/* 108 */     return name;
/*     */   }
/*     */ 
/*     */   public String getFieldValue(int fieldType, Object fieldValue)
/*     */   {
/* 113 */     if (93 == fieldType) {
/* 114 */       Timestamp value = (Timestamp)fieldValue;
/* 115 */       return "time(" + value.getTime() + ")";
/*     */     }
/* 117 */     return super.getFieldValue(fieldType, fieldValue);
/*     */   }
/*     */ 
/*     */   public String getDropSQL(String table)
/*     */   {
/* 122 */     String sql = "drop table if exists " + table + ";\n" + "!-->";
/* 123 */     return sql;
/*     */   }
/*     */ 
/*     */   public String getSaasCreateSQL(Element action, int userid, int siteid)
/*     */   {
/* 128 */     String table = getSaasTableName(action.elementText("table"), userid, siteid);
/* 129 */     List fields = action.elements("field");
/*     */ 
/* 131 */     String sql = getDropSQL(table) + "!-->";
/* 132 */     sql = sql + "create table " + table + " (";
/*     */ 
/* 134 */     String pk = "";
/* 135 */     for (int i = 0; i < fields.size(); i++) {
/* 136 */       String nl = "";
/* 137 */       Element field = (Element)fields.get(i);
/* 138 */       String name = field.elementText("name");
/* 139 */       String size = field.elementText("size");
/* 140 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/* 142 */       String option = field.elementText("option");
/* 143 */       String def = field.elementText("default");
/*     */ 
/* 145 */       if ("1".equals(option.substring(1, 2))) {
/* 146 */         nl = " not null";
/*     */       }
/* 148 */       if (def != null) {
/* 149 */         nl = nl + " default " + def;
/*     */       }
/* 151 */       if ("1".equals(option.substring(0, 1))) {
/* 152 */         pk = "primary key (" + name + "),";
/* 153 */         nl = nl + " auto_increment";
/*     */       }
/*     */ 
/* 156 */       sql = sql + name + " " + type + nl + ",";
/*     */     }
/* 158 */     sql = sql + pk;
/* 159 */     sql = sql.substring(0, sql.length() - 1) + ") ENGINE = MYISAM;";
/* 160 */     return sql;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.MySQLSolution
 * JD-Core Version:    0.6.0
 */