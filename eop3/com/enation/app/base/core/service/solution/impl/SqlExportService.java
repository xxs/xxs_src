/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.ISetupCreator;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.dom4j.Document;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
/*     */ import org.springframework.jdbc.BadSqlGrammarException;
/*     */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
/*     */ 
/*     */ public class SqlExportService
/*     */ {
/*     */   private SimpleJdbcTemplate simpleJdbcTemplate;
/*     */   private ISetupCreator setupCreator;
/*     */ 
/*     */   public String dumpSql()
/*     */   {
/*  35 */     List tables = getAllTableNames();
/*  36 */     return dumpSql();
/*     */   }
/*     */ 
/*     */   public String dumpSql(List<String> tables)
/*     */   {
/*  41 */     return dumpSql(tables, "es");
/*     */   }
/*     */ 
/*     */   public String dumpSql(List<String> tables, String prefix) {
/*  45 */     String sql = "";
/*     */ 
/*  47 */     if (tables == null)
/*  48 */       throw new RuntimeException("不存在数据库或者是没有表");
/*  49 */     int i = 0; for (int len = tables.size(); i < len; i++) {
/*  50 */       String tabname = ((String)tables.get(i)).toString();
/*     */ 
/*  52 */       if ((tabname.startsWith("eop_")) || 
/*  53 */         (tabname.endsWith("widgetbundle")))
/*     */         continue;
/*  55 */       if ((tabname.endsWith("border")) || 
/*  56 */         ((tabname.endsWith("menu")) && (!tabname.endsWith("site_menu"))) || 
/*  57 */         (tabname.endsWith("themeuri")) || 
/*  58 */         (tabname.endsWith("theme")) || 
/*  59 */         (tabname.endsWith("admintheme")))
/*     */         continue;
/*     */       String querySql;
/*     */       String querySql;
/*  62 */       if ("2".equals(EopSetting.RUNMODE)) {
/*  63 */         EopSite site = EopContext.getContext().getCurrentSite();
/*  64 */         querySql = "show tables like '" + prefix + tabname + "_" + site.getUserid() + "_" + site.getId() + "'";
/*     */       } else {
/*  66 */         querySql = "show tables like '" + prefix + tabname + "'";
/*     */       }
/*     */ 
/*  69 */       List tblist = this.simpleJdbcTemplate.queryForList(querySql, new Object[0]);
/*  70 */       if ((tblist == null) || (tblist.isEmpty()))
/*     */       {
/*     */         continue;
/*     */       }
/*  74 */       sql = sql + dumpTableSql(new StringBuilder().append(prefix).append(tabname).toString());
/*     */     }
/*     */ 
/*  77 */     return sql;
/*     */   }
/*     */ 
/*     */   public String dumpSql(List<String> tables, String target, Document setup) {
/*  81 */     String sql = "";
/*     */ 
/*  83 */     if (tables == null)
/*  84 */       throw new RuntimeException("不存在数据库或者是没有表");
/*  85 */     int i = 0; for (int len = tables.size(); i < len; i++) {
/*  86 */       String tabname = ((String)tables.get(i)).toString();
/*     */ 
/*  88 */       if ((tabname.startsWith("eop_")) || 
/*  89 */         (tabname.endsWith("widgetbundle")))
/*     */         continue;
/*  91 */       if ((tabname.endsWith("border")) || 
/*  92 */         ((tabname.endsWith("menu")) && (!tabname.endsWith("site_menu"))) || 
/*  93 */         (tabname.endsWith("themeuri")) || 
/*  94 */         (tabname.endsWith("theme")) || 
/*  95 */         (tabname.endsWith("admintheme")))
/*     */         continue;
/*     */       String querySql;
/*     */       String querySql;
/*  98 */       if ("2".equals(EopSetting.RUNMODE)) {
/*  99 */         EopSite site = EopContext.getContext().getCurrentSite();
/* 100 */         querySql = "show tables like 'es_" + tabname + "_" + site.getUserid() + "_" + site.getId() + "'";
/*     */       } else {
/* 102 */         querySql = "show tables like 'es_" + tabname + "'";
/*     */       }
/*     */ 
/* 105 */       List tblist = this.simpleJdbcTemplate.queryForList(querySql, new Object[0]);
/* 106 */       if ((tblist == null) || (tblist.isEmpty())) {
/*     */         continue;
/*     */       }
/* 109 */       if ((tabname.toLowerCase().equals("data_cat")) || (tabname.toLowerCase().equals("data_model")) || (tabname.toLowerCase().equals("data_field")))
/* 110 */         this.setupCreator.addTable(setup, "clean", "es_" + tabname);
/*     */       else {
/* 112 */         this.setupCreator.addTable(setup, target, "es_" + tabname);
/*     */       }
/*     */ 
/* 115 */       sql = sql + dumpTableSql(new StringBuilder().append("es_").append(tabname).toString());
/*     */     }
/*     */ 
/* 118 */     return sql;
/*     */   }
/*     */ 
/*     */   public List getAllTableNames()
/*     */   {
/* 127 */     List tableList = new ArrayList();
/* 128 */     List list = this.simpleJdbcTemplate.queryForList("show tables", new Object[0]);
/* 129 */     int i = 0; for (int len = list.size(); i < len; i++) {
/* 130 */       Map map = (Map)list.get(i);
/* 131 */       Object[] keyArray = map.keySet().toArray();
/* 132 */       for (int j = 0; j < keyArray.length; j++) {
/* 133 */         if (map.get(keyArray[j].toString()) != null)
/* 134 */           tableList.add(map.get(keyArray[j].toString()).toString());
/*     */       }
/*     */     }
/* 137 */     return tableList;
/*     */   }
/*     */ 
/*     */   public String dumpTableSql(String table)
/*     */     throws BadSqlGrammarException, EmptyResultDataAccessException
/*     */   {
/* 153 */     String sql = "";
/*     */ 
/* 160 */     sql = sql + getInsertSql(table);
/*     */ 
/* 166 */     return sql;
/*     */   }
/*     */ 
/*     */   public String getCreateTableSql(String table)
/*     */     throws BadSqlGrammarException
/*     */   {
/* 176 */     Map map = this.simpleJdbcTemplate.queryForMap("SHOW CREATE TABLE " + table + ";", new Object[0]);
/* 177 */     Object temp = map.get("Create Table");
/*     */     String sql;
/*     */     String sql;
/* 179 */     if ((temp instanceof String))
/* 180 */       sql = (String)temp;
/*     */     else {
/* 182 */       sql = new String((byte[])(byte[])temp);
/*     */     }
/*     */ 
/* 185 */     return sql + ";\n";
/*     */   }
/*     */ 
/*     */   public String getTableStatus(String table)
/*     */     throws EmptyResultDataAccessException
/*     */   {
/* 194 */     String sql = "";
/* 195 */     Map map = this.simpleJdbcTemplate.queryForMap("SHOW TABLE STATUS LIKE '" + table + "'", new Object[0]);
/* 196 */     if ((map != null) && 
/* 197 */       (map.get("Auto_increment") != null) && (!map.get("Auto_increment").equals(""))) {
/* 198 */       sql = "  AUTO_INCREMENT=" + map.get("Auto_increment").toString();
/*     */     }
/*     */ 
/* 201 */     sql = sql + ";\n\n";
/* 202 */     return sql;
/*     */   }
/*     */ 
/*     */   public String getInsertSql(String table)
/*     */     throws BadSqlGrammarException
/*     */   {
/* 212 */     String rname = table;
/* 213 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/* 215 */     Integer userid = site.getUserid();
/* 216 */     Integer siteid = site.getId();
/* 217 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 218 */       rname = table + "_" + userid + "_" + siteid;
/*     */     }
/* 220 */     StringBuffer sql = new StringBuffer();
/*     */ 
/* 222 */     int total = this.simpleJdbcTemplate.queryForInt("SELECT COUNT(0) FROM " + rname, new Object[0]);
/* 223 */     int pageSize = 200;
/* 224 */     int pageTotal = (int)Math.ceil(total / pageSize);
/*     */ 
/* 226 */     ParameterizedRowMapper mapper = new ParameterizedRowMapper(table)
/*     */     {
/*     */       private String getValueString(String value, int type) {
/* 229 */         String separator = "";
/*     */ 
/* 231 */         switch (type) {
/*     */         case 12:
/* 233 */           separator = "'";
/* 234 */           break;
/*     */         case -1:
/* 237 */           separator = "'";
/* 238 */           break;
/*     */         case 1:
/* 240 */           separator = "'";
/* 241 */           break;
/*     */         case 91:
/* 243 */           separator = "'";
/* 244 */           break;
/*     */         case 92:
/* 246 */           separator = "'";
/* 247 */           break;
/*     */         case 93:
/* 249 */           separator = "'";
/*     */         }
/*     */ 
/* 253 */         return separator + value + separator;
/*     */       }
/*     */ 
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/* 258 */         StringBuffer fieldstr = new StringBuffer();
/* 259 */         StringBuffer valuestr = new StringBuffer();
/* 260 */         StringBuffer sb = new StringBuffer();
/*     */ 
/* 262 */         ResultSetMetaData rsmd = rs.getMetaData();
/* 263 */         sb.append("INSERT INTO " + this.val$table + "_<userid>_<siteid> (");
/* 264 */         int count = rsmd.getColumnCount();
/* 265 */         String comma = "";
/* 266 */         for (int i = 1; i <= count; i++)
/*     */         {
/* 268 */           String fieldname = rsmd.getColumnName(i);
/* 269 */           int type = rsmd.getColumnType(i);
/*     */ 
/* 271 */           if (i != 1) {
/* 272 */             fieldstr.append(",");
/*     */           }
/* 274 */           fieldstr.append("" + fieldname + "");
/*     */ 
/* 276 */           String value = rs.getString(fieldname);
/* 277 */           if (value != null)
/* 278 */             valuestr.append(comma + getValueString(SqlExportService.this.mysql_escape_string(value), type));
/*     */           else {
/* 280 */             valuestr.append(comma + "null");
/*     */           }
/* 282 */           comma = ",";
/*     */         }
/*     */ 
/* 286 */         sb.append(fieldstr);
/* 287 */         sb.append(") VALUES (");
/* 288 */         sb.append(valuestr);
/* 289 */         sb.append(");\n");
/* 290 */         return sb.toString();
/*     */       }
/*     */     };
/* 295 */     List list = null;
/*     */ 
/* 297 */     for (int i = 1; i <= pageTotal; i++) {
/* 298 */       String querysql = "SELECT * FROM " + rname + " LIMIT " + (i - 1) * pageSize + ", " + pageSize;
/* 299 */       list = this.simpleJdbcTemplate.query(querysql, mapper, new Object[0]);
/* 300 */       for (int j = 0; j < list.size(); j++) {
/* 301 */         sql.append(list.get(j).toString());
/*     */       }
/*     */     }
/*     */ 
/* 305 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   public String getMySqlVersion()
/*     */   {
/* 313 */     String version = "";
/* 314 */     Map map = this.simpleJdbcTemplate.queryForMap("SELECT version() as version", new Object[0]);
/* 315 */     version = map.get("version").toString();
/* 316 */     return version;
/*     */   }
/*     */ 
/*     */   private String mysql_escape_string(String str)
/*     */   {
/* 325 */     if ((str == null) || (str.length() == 0))
/* 326 */       return str;
/* 327 */     if ("1".equals(EopSetting.DBTYPE)) {
/* 328 */       str = str.replaceAll("'", "\\\\'");
/*     */     }
/*     */ 
/* 331 */     if ("3".equals(EopSetting.DBTYPE)) {
/* 332 */       str = str.replaceAll("'", "''");
/*     */     }
/*     */ 
/* 335 */     str = str.replaceAll("\"", "\\\"");
/* 336 */     str = str.replaceAll("\r", "");
/* 337 */     str = str.replaceAll("\n", "");
/* 338 */     return str;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 343 */     System.out.println("<p'dd'a>".replaceAll("'", "\\\\'"));
/*     */   }
/*     */ 
/*     */   public SimpleJdbcTemplate getSimpleJdbcTemplate() {
/* 347 */     return this.simpleJdbcTemplate;
/*     */   }
/*     */ 
/*     */   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
/* 351 */     this.simpleJdbcTemplate = simpleJdbcTemplate;
/*     */   }
/*     */ 
/*     */   public ISetupCreator getSetupCreator()
/*     */   {
/* 356 */     return this.setupCreator;
/*     */   }
/*     */ 
/*     */   public void setSetupCreator(ISetupCreator setupCreator)
/*     */   {
/* 361 */     this.setupCreator = setupCreator;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SqlExportService
 * JD-Core Version:    0.6.0
 */