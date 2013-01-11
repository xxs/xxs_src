/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.dbsolution.IDBSolution;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public abstract class DBSolution
/*     */   implements IDBSolution
/*     */ {
/*     */   public static final String EXECUTECHAR = "!-->";
/*  24 */   protected String prefix = "";
/*     */ 
/*  26 */   protected List<String> functions = new ArrayList();
/*     */   protected Connection conn;
/*     */   protected String sqlExchange;
/*     */ 
/*     */   public String getSqlExchange()
/*     */   {
/*  33 */     return this.sqlExchange;
/*     */   }
/*     */ 
/*     */   public void setSqlExchange(String sqlExchange) {
/*  37 */     this.sqlExchange = sqlExchange;
/*     */   }
/*     */ 
/*     */   protected String getTableName(String table)
/*     */   {
/*  56 */     return StringUtil.addPrefix(table, this.prefix);
/*     */   }
/*     */ 
/*     */   protected String getSaasTableName(String table, int userid, int siteid) {
/*  60 */     if (table.toLowerCase().startsWith("eop_")) {
/*  61 */       return getTableName(table);
/*     */     }
/*  63 */     return getTableName(table) + "_" + userid + "_" + siteid;
/*     */   }
/*     */ 
/*     */   public String encode(String text)
/*     */   {
/*  73 */     text = text.replaceAll("&", "&amp;");
/*  74 */     text = text.replaceAll("<", "&lt;");
/*  75 */     text = text.replaceAll(">", "&gt;");
/*  76 */     return text;
/*     */   }
/*     */ 
/*     */   public String decode(String text)
/*     */   {
/*  86 */     text = text.replaceAll("&amp;", "&");
/*  87 */     text = text.replaceAll("&lt;", "<");
/*  88 */     text = text.replaceAll("&gt;", ">");
/*  89 */     return text;
/*     */   }
/*     */ 
/*     */   public String encodeValue(String value)
/*     */   {
/*  99 */     value = value.replace("'", "!quote;");
/* 100 */     return value.replaceAll(",", "!comma;");
/*     */   }
/*     */ 
/*     */   public String decodeValue(String value)
/*     */   {
/* 110 */     value = value.replace("!quote;", "'");
/* 111 */     return value.replaceAll("!comma;", ",");
/*     */   }
/*     */ 
/*     */   protected String[] getFuncName()
/*     */   {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object getFuncValue(String name, String value)
/*     */   {
/* 131 */     return value;
/*     */   }
/*     */ 
/*     */   protected String getConvertedSQL(String sql)
/*     */   {
/* 141 */     return sql;
/*     */   }
/*     */ 
/*     */   protected void initFunctions() {
/* 145 */     this.functions.add("time");
/*     */   }
/*     */ 
/*     */   protected Object doFunction(String name, String value) {
/* 149 */     if ("time".equals(name)) {
/* 150 */       return new Timestamp(Long.parseLong(value));
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean beforeInsert(String table, String fields, String values)
/*     */   {
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   protected void afterInsert(String table, String fields, String values)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void beforeImport()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void afterImport()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected Object getFuncValue(String value)
/*     */   {
/* 184 */     for (int i = 0; i < this.functions.size(); i++) {
/* 185 */       if (((String)this.functions.get(i)).length() < value.length()) {
/* 186 */         String prefix = value.substring(0, ((String)this.functions.get(i)).length()).toLowerCase();
/*     */ 
/* 188 */         if (((String)this.functions.get(i)).equals(prefix)) {
/* 189 */           String param = value.substring(prefix.length() + 1);
/* 190 */           param = param.substring(0, param.length() - 1);
/* 191 */           return doFunction(prefix, param);
/*     */         }
/*     */       }
/*     */     }
/* 195 */     return value;
/*     */   }
/*     */ 
/*     */   public String getFieldValue(int fieldType, Object fieldValue)
/*     */   {
/* 205 */     String value = encodeValue("" + fieldValue);
/* 206 */     if ((12 == fieldType) || (-9 == fieldType) || (1 == fieldType))
/*     */     {
/* 208 */       return "'" + value + "'";
/* 209 */     }if ((-9 == fieldType) || (-1 == fieldType) || (2005 == fieldType))
/*     */     {
/* 211 */       return "'" + value + "'";
/* 212 */     }return "" + value;
/*     */   }
/*     */ 
/*     */   public boolean dbExport(String[] tables, String xml)
/*     */   {
/* 217 */     DBExporter dbExporter = new DBExporter(this);
/* 218 */     return dbExporter.doExport(this.prefix, tables, xml);
/*     */   }
/*     */ 
/*     */   public String dbExport(String[] tables, boolean dataOnly)
/*     */   {
/* 223 */     DBExporter dbExporter = new DBExporter(this);
/* 224 */     return dbExporter.doExport(this.prefix, tables, dataOnly);
/*     */   }
/*     */ 
/*     */   public String dbSaasExport(String[] tables, boolean dataOnly, int userid, int siteid)
/*     */   {
/* 229 */     DBExporter dbExporter = new DBExporter(this);
/* 230 */     return dbExporter.doExport(this.prefix, tables, dataOnly, userid, siteid);
/*     */   }
/*     */ 
/*     */   public boolean dbImport(String xml)
/*     */   {
/* 235 */     initFunctions();
/* 236 */     DBImporter dbImporter = new DBImporter(this);
/*     */ 
/* 238 */     return dbImporter.doImport(xml);
/*     */   }
/*     */ 
/*     */   public boolean dbSaasImport(String xml, int userid, int siteid)
/*     */   {
/* 243 */     initFunctions();
/* 244 */     DBImporter dbImporter = new DBImporter(this);
/*     */ 
/* 246 */     return dbImporter.doSaasImport(xml, userid, siteid);
/*     */   }
/*     */ 
/*     */   public void setPrefix(String prefix)
/*     */   {
/* 251 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */   public boolean executeSqls(String sql)
/*     */   {
/* 261 */     String[] sqls = sql.split("!-->");
/*     */     try
/*     */     {
/* 264 */       Statement stat = this.conn.createStatement();
/* 265 */       for (int i = 0; i < sqls.length; i++) {
/* 266 */         sql = sqls[i].trim();
/* 267 */         if (!"".equals(sql)) {
/* 268 */           stat.execute(sql);
/*     */         }
/*     */       }
/* 271 */       stat.close();
/*     */     } catch (Exception e) {
/* 273 */       e.printStackTrace();
/* 274 */       System.out.println("出错语句：" + sql);
/* 275 */       return false;
/*     */     }
/* 277 */     return true;
/*     */   }
/*     */ 
/*     */   public String getInertSQL(Element action) {
/* 281 */     String sql = "insert into " + getTableName(action.elementText("table")) + " (";
/*     */ 
/* 283 */     sql = sql + action.elementText("fields") + ") values (";
/* 284 */     sql = sql + decode(action.elementText("values")) + ")" + "!-->";
/*     */ 
/* 286 */     return sql;
/*     */   }
/*     */ 
/*     */   public int dropTable(String table)
/*     */   {
/* 291 */     if (executeSqls(getDropSQL(table)))
/* 292 */       return 1;
/* 293 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setConnection(Connection conn)
/*     */   {
/* 298 */     this.conn = conn;
/*     */   }
/*     */ 
/*     */   public abstract String getCreateSQL(Element paramElement);
/*     */ 
/*     */   public abstract String getSaasCreateSQL(Element paramElement, int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract String getDropSQL(String paramString);
/*     */ 
/*     */   public abstract String toLocalType(String paramString1, String paramString2);
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBSolution
 * JD-Core Version:    0.6.0
 */