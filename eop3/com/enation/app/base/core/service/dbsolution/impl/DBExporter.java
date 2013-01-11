/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class DBExporter extends DBPorter
/*     */ {
/*  20 */   private String prefix = "";
/*  21 */   private String beginLine = "\t";
/*  22 */   private String endLine = "\n";
/*     */ 
/*     */   public DBExporter(DBSolution solution) {
/*  25 */     super(solution);
/*     */   }
/*     */ 
/*     */   private String beginLine(int count)
/*     */   {
/*  34 */     String result = "";
/*  35 */     for (int i = 0; i < count; i++) {
/*  36 */       result = new StringBuilder().append(result).append(this.beginLine).toString();
/*     */     }
/*  38 */     return result;
/*     */   }
/*     */ 
/*     */   protected String getFieldOption(ResultSetMetaData rsmd, int index)
/*     */     throws SQLException
/*     */   {
/*  51 */     String auto = "0";
/*  52 */     String nullable = "0";
/*     */ 
/*  54 */     if (rsmd.isAutoIncrement(index))
/*  55 */       auto = "1";
/*  56 */     if (rsmd.isNullable(index) == 0) {
/*  57 */       nullable = "1";
/*     */     }
/*  59 */     return new StringBuilder().append(auto).append(nullable).toString();
/*     */   }
/*     */ 
/*     */   private void createAction(String table, StringBuilder xmlFile, String command)
/*     */   {
/*  71 */     xmlFile.append(new StringBuilder().append(this.beginLine).append("<action>").append(this.endLine).toString());
/*  72 */     xmlFile.append(new StringBuilder().append(beginLine(2)).append("<command>").append(command).append("</command>").append(this.endLine).toString());
/*     */ 
/*  74 */     xmlFile.append(new StringBuilder().append(beginLine(2)).append("<table>").append(table.toLowerCase()).append("</table>").append(this.endLine).toString());
/*     */   }
/*     */ 
/*     */   private boolean createTableXml(String table, StringBuilder xmlFile)
/*     */   {
/*  85 */     createAction(table, xmlFile, "create");
/*     */     try
/*     */     {
/*  88 */       createFieldXml(table, xmlFile);
/*     */     } catch (SQLException e) {
/*  90 */       e.printStackTrace();
/*  91 */       return false;
/*     */     }
/*     */ 
/*  94 */     xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean createTableXml(String table, int userid, int siteid, StringBuilder xmlFile) {
/*  99 */     createAction(table, xmlFile, "create");
/*     */     try
/*     */     {
/* 102 */       createFieldXml(new StringBuilder().append(table).append("_").append(userid).append("_").append(siteid).toString(), xmlFile);
/*     */     } catch (SQLException e) {
/* 104 */       e.printStackTrace();
/* 105 */       return false;
/*     */     }
/*     */ 
/* 108 */     xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   protected String getFieldTypeName(int type)
/*     */   {
/* 119 */     String result = "";
/* 120 */     switch (type) {
/*     */     case 4:
/*     */     case 5:
/* 123 */       result = "int";
/* 124 */       break;
/*     */     case 12:
/* 126 */       result = "varchar";
/* 127 */       break;
/*     */     case -16:
/*     */     case -1:
/*     */     case 2005:
/* 131 */       result = "memo";
/* 132 */       break;
/*     */     case -5:
/* 134 */       result = "long";
/* 135 */       break;
/*     */     case 3:
/* 137 */       result = "decimal";
/* 138 */       break;
/*     */     case 91:
/* 140 */       result = "date";
/* 141 */       break;
/*     */     case 93:
/* 143 */       result = "datetime";
/* 144 */       break;
/*     */     default:
/* 146 */       result = "varchar";
/*     */     }
/* 148 */     return result;
/*     */   }
/*     */ 
/*     */   private void createFieldXml(String table, StringBuilder xmlFile)
/*     */     throws SQLException
/*     */   {
/* 160 */     Statement st = this.solution.conn.createStatement();
/* 161 */     ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(this.prefix).append(table).toString());
/*     */ 
/* 163 */     DatabaseMetaData metaData = this.solution.conn.getMetaData();
/* 164 */     Map columns = new HashMap();
/* 165 */     ResultSet mdrs = metaData.getColumns(null, null, table.toUpperCase(), "%");
/*     */ 
/* 169 */     while (mdrs.next()) {
/* 170 */       columns.put(mdrs.getString("COLUMN_NAME"), mdrs.getString("COLUMN_DEF"));
/*     */     }
/*     */ 
/* 173 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 174 */     for (int i = 1; i <= rsmd.getColumnCount(); i++) {
/* 175 */       String columnName = rsmd.getColumnName(i);
/*     */ 
/* 177 */       xmlFile.append(new StringBuilder().append(beginLine(2)).append("<field>").toString());
/*     */ 
/* 179 */       xmlFile.append(new StringBuilder().append("<name>").append(rsmd.getColumnName(i).toLowerCase()).append("</name>").toString());
/* 180 */       xmlFile.append(new StringBuilder().append("<type>").append(getFieldTypeName(rsmd.getColumnType(i))).append("</type>").toString());
/*     */ 
/* 182 */       xmlFile.append(new StringBuilder().append("<size>").append(rsmd.getPrecision(i)).append("</size>").toString());
/* 183 */       xmlFile.append(new StringBuilder().append("<option>").append(getFieldOption(rsmd, i)).append("</option>").toString());
/*     */ 
/* 185 */       if (columns.get(columnName) != null) {
/* 186 */         String value = this.solution.getFieldValue(rsmd.getColumnType(i), columns.get(columnName));
/* 187 */         value = value.replaceAll("\\(", "");
/* 188 */         value = value.replaceAll("\\)", "");
/* 189 */         xmlFile.append(new StringBuilder().append("<default>").append(value).append("</default>").toString());
/*     */       }
/* 191 */       xmlFile.append(new StringBuilder().append("</field>").append(this.endLine).toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean saveDocument(String xml, String text)
/*     */   {
/*     */     try
/*     */     {
/* 204 */       FileWriter file = new FileWriter(xml);
/* 205 */       file.write(text);
/* 206 */       file.close();
/* 207 */       return true; } catch (Exception e) {
/*     */     }
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean insertDataXml(String table, StringBuilder xmlFile)
/*     */   {
/*     */     try
/*     */     {
/* 222 */       Statement st = this.solution.conn.createStatement();
/* 223 */       ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(table).toString());
/* 224 */       ResultSetMetaData rsmd = rs.getMetaData();
/*     */ 
/* 226 */       while (rs.next()) {
/* 227 */         String fields = "";
/* 228 */         String values = "";
/* 229 */         for (int i = 1; i <= rsmd.getColumnCount(); i++) {
/* 230 */           Object value = rs.getObject(i);
/* 231 */           if (value != null) {
/* 232 */             fields = new StringBuilder().append(fields).append(rsmd.getColumnName(i)).append(",").toString();
/* 233 */             values = new StringBuilder().append(values).append(this.solution.getFieldValue(rsmd.getColumnType(i), value)).append(",").toString();
/*     */           }
/*     */         }
/* 236 */         createAction(table, xmlFile, "insert");
/* 237 */         xmlFile.append(new StringBuilder().append(beginLine(2)).append("<fields>").append(fields.substring(0, fields.length() - 1).toLowerCase()).append("</fields>").append(this.endLine).toString());
/*     */ 
/* 240 */         xmlFile.append(new StringBuilder().append(beginLine(2)).append("<values>").append(this.solution.encode(values.substring(0, values.length() - 1))).append("</values>").append(this.endLine).toString());
/*     */ 
/* 244 */         xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
/*     */       }
/*     */     }
/*     */     catch (SQLException e) {
/* 248 */       e.printStackTrace();
/* 249 */       return false;
/*     */     }
/*     */ 
/* 252 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean insertDataXml(String table, int userid, int siteid, StringBuilder xmlFile) {
/*     */     try {
/* 257 */       Statement st = this.solution.conn.createStatement();
/* 258 */       ResultSet rs = st.executeQuery(new StringBuilder().append("select * from ").append(table).append("_").append(userid).append("_").append(siteid).toString());
/* 259 */       ResultSetMetaData rsmd = rs.getMetaData();
/*     */ 
/* 261 */       while (rs.next()) {
/* 262 */         String fields = "";
/* 263 */         String values = "";
/* 264 */         for (int i = 1; i <= rsmd.getColumnCount(); i++) {
/* 265 */           Object value = rs.getObject(i);
/* 266 */           if (value != null) {
/* 267 */             fields = new StringBuilder().append(fields).append(rsmd.getColumnName(i)).append(",").toString();
/* 268 */             values = new StringBuilder().append(values).append(this.solution.getFieldValue(rsmd.getColumnType(i), value)).append(",").toString();
/*     */           }
/*     */         }
/* 271 */         createAction(table, xmlFile, "insert");
/* 272 */         xmlFile.append(new StringBuilder().append(beginLine(2)).append("<fields>").append(fields.substring(0, fields.length() - 1).toLowerCase()).append("</fields>").append(this.endLine).toString());
/*     */ 
/* 275 */         xmlFile.append(new StringBuilder().append(beginLine(2)).append("<values>").append(this.solution.encode(values.substring(0, values.length() - 1))).append("</values>").append(this.endLine).toString());
/*     */ 
/* 279 */         xmlFile.append(new StringBuilder().append(this.beginLine).append("</action>").append(this.endLine).toString());
/*     */       }
/*     */     }
/*     */     catch (SQLException e) {
/* 283 */       e.printStackTrace();
/* 284 */       return false;
/*     */     }
/*     */ 
/* 287 */     return true;
/*     */   }
/*     */ 
/*     */   public String doExport(String prefix, String[] tables, boolean dataOnly)
/*     */   {
/* 299 */     StringBuilder xml = new StringBuilder();
/*     */ 
/* 301 */     if (!dataOnly) {
/* 302 */       for (int i = 0; i < tables.length; i++) {
/* 303 */         if (tables[i].toUpperCase().startsWith("EOP_"))
/* 304 */           createTableXml(tables[i], xml);
/*     */         else
/* 306 */           createTableXml(new StringBuilder().append(prefix).append(tables[i]).toString(), xml);
/*     */       }
/*     */     }
/* 309 */     for (int i = 0; i < tables.length; i++) {
/* 310 */       if (tables[i].toUpperCase().startsWith("EOP_"))
/* 311 */         insertDataXml(tables[i], xml);
/*     */       else
/* 313 */         insertDataXml(new StringBuilder().append(prefix).append(tables[i]).toString(), xml);
/*     */     }
/* 315 */     return xml.toString();
/*     */   }
/*     */ 
/*     */   public String doExport(String prefix, String[] tables, boolean dataOnly, int userid, int siteid) {
/* 319 */     StringBuilder xml = new StringBuilder();
/*     */ 
/* 321 */     if (!dataOnly) {
/* 322 */       for (int i = 0; i < tables.length; i++) {
/* 323 */         if (tables[i].toUpperCase().startsWith("EOP_"))
/* 324 */           createTableXml(tables[i], xml);
/*     */         else
/* 326 */           createTableXml(new StringBuilder().append(prefix).append(tables[i]).toString(), userid, siteid, xml);
/*     */       }
/*     */     }
/* 329 */     for (int i = 0; i < tables.length; i++) {
/* 330 */       if (tables[i].toUpperCase().startsWith("EOP_"))
/* 331 */         insertDataXml(tables[i], xml);
/*     */       else
/* 333 */         insertDataXml(new StringBuilder().append(prefix).append(tables[i]).toString(), userid, siteid, xml);
/*     */     }
/* 335 */     return xml.toString();
/*     */   }
/*     */ 
/*     */   public boolean doExport(String prefix, String[] tables, String xml)
/*     */   {
/* 347 */     this.prefix = prefix;
/*     */ 
/* 349 */     StringBuilder xmlFile = new StringBuilder();
/* 350 */     xmlFile.append(new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(this.endLine).toString());
/* 351 */     xmlFile.append(new StringBuilder().append("<dbsolution>").append(this.endLine).toString());
/* 352 */     xmlFile.append(doExport(prefix, tables, false));
/* 353 */     xmlFile.append(new StringBuilder().append("</dbsolution>").append(this.endLine).toString());
/*     */ 
/* 355 */     return saveDocument(xml, xmlFile.toString());
/*     */   }
/*     */ 
/*     */   public boolean doExport(String prefix, String[] tables, String xml, int userid, int siteid) {
/* 359 */     this.prefix = prefix;
/*     */ 
/* 361 */     StringBuilder xmlFile = new StringBuilder();
/* 362 */     xmlFile.append(new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(this.endLine).toString());
/* 363 */     xmlFile.append(new StringBuilder().append("<dbsolution>").append(this.endLine).toString());
/* 364 */     xmlFile.append(doExport(prefix, tables, false, userid, siteid));
/* 365 */     xmlFile.append(new StringBuilder().append("</dbsolution>").append(this.endLine).toString());
/*     */ 
/* 367 */     return saveDocument(xml, xmlFile.toString());
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBExporter
 * JD-Core Version:    0.6.0
 */