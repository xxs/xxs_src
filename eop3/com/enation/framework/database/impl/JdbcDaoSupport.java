/*     */ package com.enation.framework.database.impl;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.DBRuntimeException;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ObjectNotFoundException;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.ReflectionUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
/*     */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class JdbcDaoSupport<T>
/*     */   implements IDaoSupport<T>
/*     */ {
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   private SimpleJdbcTemplate simpleJdbcTemplate;
/*  37 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public void execute(String sql, Object[] args) {
/*     */     try {
/*  41 */       this.simpleJdbcTemplate.update(sql, args);
/*     */     } catch (Exception e) {
/*  43 */       throw new DBRuntimeException(e, sql);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getLastId(String table) {
/*  48 */     if (EopSetting.DBTYPE.equals("1")) {
/*  49 */       return this.jdbcTemplate.queryForInt("SELECT last_insert_id() as id");
/*     */     }
/*  51 */     if (EopSetting.DBTYPE.equals("2")) {
/*  52 */       int result = 0;
/*  53 */       result = this.jdbcTemplate.queryForInt("SELECT s_" + table + ".currval as id from DUAL");
/*     */ 
/*  55 */       return result;
/*  56 */     }if (EopSetting.DBTYPE.equals("3")) {
/*  57 */       int result = 0;
/*  58 */       result = this.jdbcTemplate.queryForInt("select @@identity");
/*  59 */       return result;
/*     */     }
/*     */ 
/*  62 */     throw new RuntimeException("未知的数据库类型");
/*     */   }
/*     */ 
/*     */   public void insert(String table, Map fields) {
/*  66 */     String sql = "";
/*     */     try
/*     */     {
/*  70 */       Assert.hasText(table, "表名不能为空");
/*  71 */       Assert.notEmpty(fields, "字段不能为空");
/*  72 */       table = quoteCol(table);
/*     */ 
/*  74 */       Object[] cols = fields.keySet().toArray();
/*  75 */       Object[] values = new Object[cols.length];
/*  76 */       for (int i = 0; i < cols.length; i++) {
/*  77 */         if (fields.get(cols[i]) == null)
/*  78 */           values[i] = null;
/*     */         else {
/*  80 */           values[i] = fields.get(cols[i]).toString();
/*     */         }
/*  82 */         cols[i] = quoteCol(cols[i].toString());
/*     */       }
/*     */ 
/*  85 */       sql = "INSERT INTO " + table + " (" + StringUtil.implode(", ", cols);
/*     */ 
/*  88 */       sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values);
/*     */ 
/*  90 */       sql = sql + ")";
/*     */ 
/*  92 */       this.jdbcTemplate.update(sql, values);
/*     */     }
/*     */     catch (Exception e) {
/*  95 */       throw new DBRuntimeException(e, sql);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insert(String table, Object po) {
/* 100 */     insert(table, ReflectionUtil.po2Map(po));
/*     */   }
/*     */ 
/*     */   public int queryForInt(String sql, Object[] args) {
/*     */     try {
/* 105 */       return this.simpleJdbcTemplate.queryForInt(sql, args);
/*     */     } catch (RuntimeException e) {
/* 107 */       this.logger.error(e.getMessage(), e);
/* 108 */     }throw e;
/*     */   }
/*     */ 
/*     */   public String queryForString(String sql)
/*     */   {
/* 115 */     String s = "";
/*     */     try {
/* 117 */       s = (String)this.jdbcTemplate.queryForObject(sql, String.class);
/*     */     } catch (RuntimeException e) {
/* 119 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 122 */     return s;
/*     */   }
/*     */ 
/*     */   public List queryForList(String sql, Object[] args)
/*     */   {
/* 127 */     return this.jdbcTemplate.queryForList(sql, args);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, RowMapper mapper, Object[] args) {
/*     */     try {
/* 132 */       return this.jdbcTemplate.query(sql, args, mapper); } catch (Exception ex) {
/*     */     }
/* 134 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, Class clazz, Object[] args)
/*     */   {
/* 139 */     return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
/*     */   }
/*     */ 
/*     */   public List queryForListPage(String sql, int pageNo, int pageSize, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       Assert.hasText(sql, "SQL语句不能为空");
/* 149 */       Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
/* 150 */       String listSql = buildPageSql(sql, pageNo, pageSize);
/* 151 */       return queryForList(listSql, args); } catch (Exception ex) {
/*     */     }
/* 153 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, int pageNo, int pageSize, RowMapper mapper)
/*     */   {
/*     */     try
/*     */     {
/* 163 */       Assert.hasText(sql, "SQL语句不能为空");
/* 164 */       Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
/* 165 */       String listSql = buildPageSql(sql, pageNo, pageSize);
/* 166 */       return queryForList(listSql, mapper, new Object[0]); } catch (Exception ex) {
/*     */     }
/* 168 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public long queryForLong(String sql, Object[] args)
/*     */   {
/* 175 */     return this.jdbcTemplate.queryForLong(sql, args);
/*     */   }
/*     */ 
/*     */   public Map queryForMap(String sql, Object[] args)
/*     */   {
/*     */     try {
/* 181 */       Map map = this.jdbcTemplate.queryForMap(sql, args);
/* 182 */       if (EopSetting.DBTYPE.equals("2")) {
/* 183 */         Map newMap = new HashMap();
/* 184 */         Iterator keyItr = map.keySet().iterator();
/* 185 */         while (keyItr.hasNext()) {
/* 186 */           String key = (String)keyItr.next();
/* 187 */           Object value = map.get(key);
/* 188 */           newMap.put(key.toLowerCase(), value);
/*     */         }
/* 190 */         return newMap;
/*     */       }
/* 192 */       return map;
/*     */     } catch (Exception ex) {
/* 194 */       ex.printStackTrace();
/* 195 */     }throw new ObjectNotFoundException(ex, sql);
/*     */   }
/*     */ 
/*     */   public T queryForObject(String sql, Class clazz, Object[] args)
/*     */   {
/*     */     try {
/* 201 */       return this.simpleJdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 208 */       this.logger.error("查询出错", ex);
/* 209 */     }return null;
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/* 219 */       Assert.hasText(sql, "SQL语句不能为空");
/* 220 */       Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
/* 221 */       String listSql = buildPageSql(sql, pageNo, pageSize);
/* 222 */       String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
/*     */ 
/* 224 */       List list = queryForList(listSql, args);
/* 225 */       int totalCount = queryForInt(countSql, args);
/* 226 */       return new Page(0L, totalCount, pageSize, list); } catch (Exception ex) {
/*     */     }
/* 228 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, RowMapper rowMapper, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/* 236 */       Assert.hasText(sql, "SQL语句不能为空");
/* 237 */       Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
/* 238 */       String listSql = buildPageSql(sql, pageNo, pageSize);
/* 239 */       String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
/*     */ 
/* 241 */       List list = queryForList(listSql, rowMapper, args);
/* 242 */       int totalCount = queryForInt(countSql, args);
/* 243 */       return new Page(0L, totalCount, pageSize, list); } catch (Exception ex) {
/*     */     }
/* 245 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/* 253 */       Assert.hasText(sql, "SQL语句不能为空");
/* 254 */       Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
/* 255 */       String listSql = buildPageSql(sql, pageNo, pageSize);
/* 256 */       String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
/*     */ 
/* 258 */       List list = queryForList(listSql, clazz, args);
/* 259 */       int totalCount = queryForInt(countSql, args);
/* 260 */       return new Page(0L, totalCount, pageSize, list); } catch (Exception ex) {
/*     */     }
/* 262 */     throw new DBRuntimeException(ex, sql);
/*     */   }
/*     */ 
/*     */   public void update(String table, Map fields, Map where)
/*     */   {
/* 269 */     String whereSql = "";
/*     */ 
/* 271 */     if (where != null) {
/* 272 */       Object[] wherecols = where.keySet().toArray();
/* 273 */       for (int i = 0; i < wherecols.length; i++) {
/* 274 */         wherecols[i] = (quoteCol(wherecols[i].toString()) + "=" + quoteValue(where.get(wherecols[i]).toString()));
/*     */       }
/*     */ 
/* 277 */       whereSql = whereSql + StringUtil.implode(" AND ", wherecols);
/*     */     }
/* 279 */     update(table, fields, whereSql);
/*     */   }
/*     */ 
/*     */   public void update(String table, T po, Map where)
/*     */   {
/* 284 */     String whereSql = "";
/*     */ 
/* 286 */     if (where != null) {
/* 287 */       Object[] wherecols = where.keySet().toArray();
/* 288 */       for (int i = 0; i < wherecols.length; i++) {
/* 289 */         wherecols[i] = (quoteCol(wherecols[i].toString()) + "=" + quoteValue(where.get(wherecols[i]).toString()));
/*     */       }
/*     */ 
/* 292 */       whereSql = whereSql + StringUtil.implode(" AND ", wherecols);
/*     */     }
/* 294 */     update(table, ReflectionUtil.po2Map(po), whereSql);
/*     */   }
/*     */ 
/*     */   public void update(String table, T po, String where)
/*     */   {
/* 299 */     update(table, ReflectionUtil.po2Map(po), where);
/*     */   }
/*     */ 
/*     */   public void update(String table, Map fields, String where)
/*     */   {
/* 304 */     String sql = "";
/*     */     try {
/* 306 */       Assert.hasText(table, "表名不能为空");
/* 307 */       Assert.notEmpty(fields, "字段不能为空");
/* 308 */       Assert.hasText(where, "where条件不能为空");
/* 309 */       table = quoteCol(table);
/*     */ 
/* 311 */       String key = null;
/* 312 */       Object value = null;
/*     */ 
/* 314 */       if ("3".equals(EopSetting.DBTYPE)) {
/*     */         try {
/* 316 */           where = where.replaceAll(" ", "");
/* 317 */           key = org.apache.commons.lang.StringUtils.split(where, "=")[0];
/* 318 */           if (key != null)
/* 319 */             value = fields.get(key);
/* 320 */           fields.remove(key);
/*     */         } catch (Exception e) {
/* 322 */           e.printStackTrace();
/* 323 */           System.out.println("SQLServer数据库更新异常，可能需要单独处理！");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 328 */       Object[] cols = fields.keySet().toArray();
/*     */ 
/* 330 */       Object[] values = new Object[cols.length];
/* 331 */       for (int i = 0; i < cols.length; i++) {
/* 332 */         if (fields.get(cols[i]) == null)
/* 333 */           values[i] = null;
/*     */         else {
/* 335 */           values[i] = fields.get(cols[i]).toString();
/*     */         }
/* 337 */         cols[i] = (quoteCol(cols[i].toString()) + "=?");
/*     */       }
/*     */ 
/* 341 */       sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols) + " WHERE " + where;
/*     */ 
/* 344 */       this.simpleJdbcTemplate.update(sql, values);
/* 345 */       if (value != null)
/* 346 */         fields.put(key, value);
/*     */     } catch (Exception e) {
/* 348 */       throw new DBRuntimeException(e, sql);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String buildPageSql(String sql, int page, int pageSize)
/*     */   {
/* 355 */     String sql_str = null;
/*     */ 
/* 358 */     String db_type = EopSetting.DBTYPE;
/* 359 */     if (db_type.equals("1"))
/* 360 */       db_type = "mysql";
/* 361 */     else if (db_type.equals("2"))
/* 362 */       db_type = "oracle";
/* 363 */     else if (db_type.equals("3")) {
/* 364 */       db_type = "sqlserver";
/*     */     }
/*     */ 
/* 367 */     if (db_type.equals("mysql")) {
/* 368 */       sql_str = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
/* 369 */     } else if (db_type.equals("oracle")) {
/* 370 */       StringBuffer local_sql = new StringBuffer("SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");
/*     */ 
/* 372 */       local_sql.append(sql);
/* 373 */       local_sql.append(") t1) t2 WHERE t2.sn1 BETWEEN ");
/* 374 */       local_sql.append((page - 1) * pageSize + 1);
/* 375 */       local_sql.append(" AND ");
/* 376 */       local_sql.append(page * pageSize);
/* 377 */       sql_str = local_sql.toString();
/* 378 */     } else if (db_type.equals("sqlserver")) {
/* 379 */       StringBuffer local_sql = new StringBuffer();
/*     */ 
/* 381 */       String order = SqlPaser.findOrderStr(sql);
/*     */ 
/* 384 */       if (order != null)
/* 385 */         sql = sql.replaceAll(order, "");
/*     */       else {
/* 387 */         order = "order by id desc";
/*     */       }
/*     */ 
/* 390 */       local_sql.append("select * from (");
/* 391 */       local_sql.append(SqlPaser.insertSelectField("ROW_NUMBER() Over(" + order + ") as rowNum", sql));
/*     */ 
/* 393 */       local_sql.append(") tb where rowNum between ");
/* 394 */       local_sql.append((page - 1) * pageSize + 1);
/* 395 */       local_sql.append(" AND ");
/* 396 */       local_sql.append(page * pageSize);
/*     */ 
/* 399 */       return local_sql.toString();
/*     */     }
/*     */ 
/* 403 */     return sql_str.toString();
/*     */   }
/*     */ 
/*     */   private String quoteCol(String col)
/*     */   {
/* 415 */     if ((col == null) || (col.equals(""))) {
/* 416 */       return "";
/*     */     }
/*     */ 
/* 424 */     return col;
/*     */   }
/*     */ 
/*     */   private String quoteValue(String value)
/*     */   {
/* 435 */     if ((value == null) || (value.equals(""))) {
/* 436 */       return "''";
/*     */     }
/* 438 */     return "'" + value.replaceAll("'", "''") + "'";
/*     */   }
/*     */ 
/*     */   private String getStr(int num, String str)
/*     */   {
/* 451 */     StringBuffer sb = new StringBuffer("");
/* 452 */     for (int i = 0; i < num; i++) {
/* 453 */       sb.append(str);
/*     */     }
/* 455 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String removeSelect(String sql)
/*     */   {
/* 463 */     sql = sql.toLowerCase();
/* 464 */     Pattern p = Pattern.compile("\\(.*\\)", 2);
/* 465 */     Matcher m = p.matcher(sql);
/* 466 */     StringBuffer sb = new StringBuffer();
/* 467 */     while (m.find()) {
/* 468 */       int c = m.end() - m.start();
/* 469 */       m.appendReplacement(sb, getStr(c, "~"));
/*     */     }
/* 471 */     m.appendTail(sb);
/*     */ 
/* 473 */     String replacedSql = sb.toString();
/*     */ 
/* 475 */     return sql.substring(replacedSql.indexOf("from"));
/*     */   }
/*     */ 
/*     */   private String removeOrders(String hql)
/*     */   {
/* 484 */     Assert.hasText(hql);
/* 485 */     Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
/*     */ 
/* 487 */     Matcher m = p.matcher(hql);
/* 488 */     StringBuffer sb = new StringBuffer();
/* 489 */     while (m.find()) {
/* 490 */       m.appendReplacement(sb, "");
/*     */     }
/* 492 */     m.appendTail(sb);
/* 493 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/* 497 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
/* 501 */     this.simpleJdbcTemplate = simpleJdbcTemplate;
/*     */   }
/*     */ 
/*     */   public T queryForObject(String sql, ParameterizedRowMapper mapper, Object[] args)
/*     */   {
/*     */     try {
/* 507 */       Object t = this.simpleJdbcTemplate.queryForObject(sql, mapper, args);
/* 508 */       return t; } catch (RuntimeException e) {
/*     */     }
/* 510 */     return null;
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, IRowMapperColumnFilter filter, Object[] args)
/*     */   {
/* 518 */     FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
/* 519 */     return queryForList(sql, filterColumnMapRowMapper, args);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, int pageNo, int pageSize, IRowMapperColumnFilter filter)
/*     */   {
/* 526 */     FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
/* 527 */     return queryForList(sql, pageNo, pageSize, filterColumnMapRowMapper);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, IRowMapperColumnFilter filter, Object[] args)
/*     */   {
/* 534 */     FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
/* 535 */     return queryForPage(sql, pageNo, pageSize, filterColumnMapRowMapper, args);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.JdbcDaoSupport
 * JD-Core Version:    0.6.0
 */