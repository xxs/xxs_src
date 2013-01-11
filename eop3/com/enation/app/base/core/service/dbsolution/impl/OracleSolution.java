/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class OracleSolution extends DBSolution
/*     */ {
/*     */   public String toLocalType(String type, String size)
/*     */   {
/*  29 */     if ("int".equals(type)) {
/*  30 */       if ("1".equals(size))
/*  31 */         return "NUMBER(2)";
/*  32 */       return "NUMBER(" + size + ")";
/*     */     }
/*  34 */     if ("memo".equals(type)) {
/*  35 */       return "CLOB";
/*     */     }
/*  37 */     if ("datetime".equals(type)) {
/*  38 */       return "TIMESTAMP";
/*     */     }
/*  40 */     if ("long".equals(type)) {
/*  41 */       return "NUMBER(20)";
/*     */     }
/*  43 */     if ("decimal".equals(type)) {
/*  44 */       return "NUMBER(20,2)";
/*     */     }
/*  46 */     return type.toUpperCase() + "(" + size + ")";
/*     */   }
/*     */ 
/*     */   private String getBlockSQL(String sql) {
/*  50 */     return "BEGIN\n\tEXECUTE IMMEDIATE '" + sql + "';\n" + "\tEXCEPTION WHEN OTHERS THEN NULL;\n" + "END;" + "!-->" + "\n";
/*     */   }
/*     */ 
/*     */   private String getTriggerSQL(String table, String field)
/*     */   {
/*  56 */     String trigger = getBlockSQL(new StringBuilder().append("DROP TRIGGER TIB_").append(table).toString()) + "CREATE TRIGGER \"TIB_" + table + "\" BEFORE INSERT\n" + "\tON " + table + " FOR EACH ROW\n" + "\tDECLARE\n" + "\tINTEGRITY_ERROR  EXCEPTION;\n" + "\tERRNO            INTEGER;\n" + "\tERRMSG           CHAR(200);\n" + "\tMAXID\t\t\tINTEGER;\n" + "BEGIN\n" + "\tIF :NEW." + field + " IS NULL THEN\n" + "\t\tSELECT MAX(" + field + ") INTO MAXID FROM " + table + ";\n" + "\t\tSELECT S_" + table + ".NEXTVAL INTO :NEW." + field + " FROM DUAL;\n" + "\t\tIF MAXID>:NEW." + field + " THEN\n" + "\t\t\tLOOP\n" + "\t\t\t\tSELECT S_" + table + ".NEXTVAL INTO :NEW." + field + " FROM DUAL;\n" + "\t\t\t\tEXIT WHEN MAXID<:NEW." + field + ";\n" + "\t\t\tEND LOOP;\n" + "\t\tEND IF;\n" + "\tEND IF;\n" + "EXCEPTION\n" + "\tWHEN INTEGRITY_ERROR THEN\n" + "\t\tRAISE_APPLICATION_ERROR(ERRNO, ERRMSG);\n" + "END;";
/*     */ 
/*  72 */     return trigger;
/*     */   }
/*     */ 
/*     */   public String getCreateSQL(Element action)
/*     */   {
/*  78 */     String table = getTableName(action.elementText("table").toUpperCase());
/*  79 */     List fields = action.elements("field");
/*     */ 
/*  81 */     String sql = getDropSQL(table) + "!-->";
/*     */ 
/*  83 */     sql = sql + "CREATE TABLE " + table + " (";
/*     */ 
/*  85 */     String sequence = "";
/*  86 */     String key = "";
/*  87 */     for (int i = 0; i < fields.size(); i++) {
/*  88 */       String nl = "";
/*  89 */       Element field = (Element)fields.get(i);
/*  90 */       String name = "\"" + field.elementText("name").toUpperCase() + "\"";
/*  91 */       String size = field.elementText("size");
/*  92 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/*  94 */       String option = field.elementText("option");
/*  95 */       String def = field.elementText("default");
/*     */ 
/*  97 */       if ("1".equals(option.substring(0, 1))) {
/*  98 */         sequence = getBlockSQL("DROP SEQUENCE S_" + table);
/*  99 */         sequence = sequence + "CREATE SEQUENCE S_" + table + "!-->" + "\n";
/*     */ 
/* 101 */         sequence = sequence + getTriggerSQL(table, name);
/*     */ 
/* 103 */         key = "CONSTRAINT PK_" + table + " PRIMARY KEY (" + name + ")";
/*     */       }
/*     */ 
/* 106 */       if ("1".equals(option.substring(1, 2))) {
/* 107 */         nl = " NOT NULL";
/*     */       }
/* 109 */       if (def != null) {
/* 110 */         nl = " default " + def + nl;
/*     */       }
/* 112 */       sql = sql + name + " " + type + nl + ",";
/*     */     }
/*     */ 
/* 115 */     if (!StringUtil.isEmpty(key)) {
/* 116 */       sql = sql + key + ")" + "!-->" + "\n";
/*     */     }
/*     */     else
/* 119 */       sql = sql.substring(0, sql.length() - 1) + ")" + "!-->" + "\n";
/* 120 */     sql = sql + sequence;
/* 121 */     return sql;
/*     */   }
/*     */ 
/*     */   public void setPrefix(String prefix)
/*     */   {
/* 126 */     this.prefix = prefix.toUpperCase();
/*     */   }
/*     */ 
/*     */   protected String getConvertedSQL(String sql)
/*     */   {
/* 134 */     sql = sql.replaceAll("&", "&'||'");
/* 135 */     System.out.println(sql);
/* 136 */     return sql;
/*     */   }
/*     */ 
/*     */   protected Object getFuncValue(String name, String value)
/*     */   {
/* 144 */     if ("time".equals(name)) {
/* 145 */       Date date = new Date(Long.parseLong(value));
/* 146 */       return "TIMESTAMP'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "'";
/*     */     }
/*     */ 
/* 150 */     return super.getFuncValue(name, value);
/*     */   }
/*     */ 
/*     */   public String getFieldValue(int fieldType, Object fieldValue)
/*     */   {
/* 156 */     Object value = fieldValue;
/* 157 */     if ((fieldValue instanceof Clob)) {
/*     */       try {
/* 159 */         Clob clob = (Clob)fieldValue;
/* 160 */         Reader inStream = clob.getCharacterStream();
/* 161 */         char[] buf = new char[(int)clob.length()];
/* 162 */         inStream.read(buf);
/* 163 */         value = new String(buf);
/* 164 */         inStream.close();
/*     */       } catch (Exception e) {
/* 166 */         e.printStackTrace();
/*     */       }
/* 168 */     } else if ((fieldValue instanceof TIMESTAMP)) {
/* 169 */       TIMESTAMP time = (TIMESTAMP)fieldValue;
/*     */       try {
/* 171 */         value = "time(" + time.dateValue().getTime() + ")";
/*     */       }
/*     */       catch (SQLException e) {
/* 174 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 177 */     return super.getFieldValue(fieldType, value);
/*     */   }
/*     */ 
/*     */   public String[] getFuncName()
/*     */   {
/* 184 */     String[] name = { "time" };
/* 185 */     return name;
/*     */   }
/*     */ 
/*     */   public String getDropSQL(String table)
/*     */   {
/* 190 */     table = table.toUpperCase();
/* 191 */     String sql = getBlockSQL(new StringBuilder().append("DROP TRIGGER TIB_").append(table).toString()) + getBlockSQL(new StringBuilder().append("DROP TABLE ").append(table).toString()) + getBlockSQL(new StringBuilder().append("DROP SEQUENCE S_").append(table).toString());
/*     */ 
/* 194 */     return sql;
/*     */   }
/*     */ 
/*     */   public String getSaasCreateSQL(Element action, int userid, int siteid)
/*     */   {
/* 213 */     String table = getSaasTableName(action.elementText("table").toUpperCase(), userid, siteid);
/* 214 */     List fields = action.elements("field");
/*     */ 
/* 216 */     String sql = getDropSQL(table) + "!-->";
/*     */ 
/* 218 */     sql = sql + "CREATE TABLE " + table + " (";
/*     */ 
/* 220 */     String sequence = "";
/* 221 */     String key = "";
/* 222 */     for (int i = 0; i < fields.size(); i++) {
/* 223 */       String nl = "";
/* 224 */       Element field = (Element)fields.get(i);
/* 225 */       String name = "\"" + field.elementText("name").toUpperCase() + "\"";
/* 226 */       String size = field.elementText("size");
/* 227 */       String type = toLocalType(field.elementText("type").toLowerCase(), size);
/*     */ 
/* 229 */       String option = field.elementText("option");
/* 230 */       String def = field.elementText("default");
/*     */ 
/* 232 */       if ("1".equals(option.substring(0, 1))) {
/* 233 */         sequence = getBlockSQL("DROP SEQUENCE S_" + table);
/* 234 */         sequence = sequence + "CREATE SEQUENCE S_" + table + "!-->" + "\n";
/*     */ 
/* 236 */         sequence = sequence + getTriggerSQL(table, name);
/*     */ 
/* 238 */         key = "CONSTRAINT PK_" + table + " PRIMARY KEY (" + name + ")";
/*     */       }
/*     */ 
/* 241 */       if ("1".equals(option.substring(1, 2))) {
/* 242 */         nl = " NOT NULL";
/*     */       }
/* 244 */       if (def != null) {
/* 245 */         nl = " default " + def + nl;
/*     */       }
/* 247 */       sql = sql + name + " " + type + nl + ",";
/*     */     }
/*     */ 
/* 250 */     if (!StringUtil.isEmpty(key)) {
/* 251 */       sql = sql + key + ")" + "!-->" + "\n";
/*     */     }
/*     */     else
/* 254 */       sql = sql.substring(0, sql.length() - 1) + ")" + "!-->" + "\n";
/* 255 */     sql = sql + sequence;
/* 256 */     return sql;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.OracleSolution
 * JD-Core Version:    0.6.0
 */