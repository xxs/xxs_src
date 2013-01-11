/*     */ package com.enation.eop.sdk.database;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.impl.JdbcDaoSupport;
/*     */ import com.enation.framework.util.ReflectionUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*     */ 
/*     */ public class BaseJdbcDaoSupport<T> extends JdbcDaoSupport<T>
/*     */ {
/*     */   private IDBRouter dbRouter;
/*     */ 
/*     */   public void setDbRouter(IDBRouter dbRouter)
/*     */   {
/*  28 */     this.dbRouter = dbRouter;
/*     */   }
/*     */ 
/*     */   public void insert(String table, Object po) {
/*  32 */     Map poMap = ReflectionUtil.po2Map(po);
/*  33 */     table = this.dbRouter.getTableName(table);
/*  34 */     super.insert(table, poMap);
/*     */   }
/*     */ 
/*     */   public void execute(String sql, Object[] args) {
/*  38 */     sql = wrapExeSql(sql);
/*  39 */     super.execute(sql, args);
/*     */   }
/*     */ 
/*     */   public int getLastId(String table) {
/*  43 */     table = this.dbRouter.getTableName(table);
/*  44 */     return super.getLastId(table);
/*     */   }
/*     */ 
/*     */   public void insert(String table, Map fields) {
/*  48 */     table = this.dbRouter.getTableName(table);
/*  49 */     super.insert(table, fields);
/*     */   }
/*     */ 
/*     */   public int queryForInt(String sql, Object[] args) {
/*  53 */     sql = wrapSelSql(sql);
/*  54 */     return super.queryForInt(sql, args);
/*     */   }
/*     */ 
/*     */   public List<Map> queryForList(String sql, Object[] args) {
/*  58 */     sql = wrapSelSql(sql);
/*  59 */     return super.queryForList(sql, args);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, RowMapper mapper, Object[] args) {
/*  63 */     sql = wrapSelSql(sql);
/*  64 */     return super.queryForList(sql, mapper, args);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, Class clazz, Object[] args) {
/*  68 */     sql = wrapSelSql(sql);
/*  69 */     return super.queryForList(sql, clazz, args);
/*     */   }
/*     */ 
/*     */   public List<Map> queryForListPage(String sql, int pageNo, int pageSize, Object[] args)
/*     */   {
/*  74 */     sql = wrapSelSql(sql);
/*  75 */     return super.queryForListPage(sql, pageNo, pageSize, args);
/*     */   }
/*     */ 
/*     */   public List<T> queryForList(String sql, int pageNo, int pageSize, RowMapper mapper)
/*     */   {
/*  80 */     sql = wrapSelSql(sql);
/*  81 */     return super.queryForList(sql, pageNo, pageSize, mapper);
/*     */   }
/*     */ 
/*     */   public long queryForLong(String sql, Object[] args) {
/*  85 */     sql = wrapSelSql(sql);
/*  86 */     return super.queryForLong(sql, args);
/*     */   }
/*     */ 
/*     */   public String queryForString(String sql) {
/*  90 */     sql = wrapSelSql(sql);
/*  91 */     return super.queryForString(sql);
/*     */   }
/*     */ 
/*     */   public Map queryForMap(String sql, Object[] args) {
/*  95 */     sql = wrapSelSql(sql);
/*  96 */     return super.queryForMap(sql, args);
/*     */   }
/*     */ 
/*     */   public T queryForObject(String sql, Class clazz, Object[] args) {
/* 100 */     sql = wrapSelSql(sql);
/*     */ 
/* 102 */     return super.queryForObject(sql, clazz, args);
/*     */   }
/*     */ 
/*     */   public T queryForObject(String sql, ParameterizedRowMapper mapper, Object[] args)
/*     */   {
/* 107 */     sql = wrapSelSql(sql);
/* 108 */     return super.queryForObject(sql, mapper, args);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, Object[] args)
/*     */   {
/* 113 */     sql = wrapSelSql(sql);
/* 114 */     return super.queryForPage(sql, pageNo, pageSize, args);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, RowMapper rowMapper, Object[] args)
/*     */   {
/* 119 */     sql = wrapSelSql(sql);
/* 120 */     return super.queryForPage(sql, pageNo, pageSize, rowMapper, args);
/*     */   }
/*     */ 
/*     */   public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object[] args)
/*     */   {
/* 125 */     sql = wrapSelSql(sql);
/* 126 */     return super.queryForPage(sql, pageNo, pageSize, clazz, args);
/*     */   }
/*     */ 
/*     */   public void update(String table, Map fields, Map where) {
/* 130 */     table = this.dbRouter.getTableName(table);
/* 131 */     super.update(table, fields, where);
/*     */   }
/*     */ 
/*     */   public void update(String table, Map fields, String where) {
/* 135 */     table = this.dbRouter.getTableName(table);
/* 136 */     super.update(table, fields, where);
/*     */   }
/*     */ 
/*     */   public void update(String table, T po, Map where) {
/* 140 */     table = this.dbRouter.getTableName(table);
/* 141 */     super.update(table, po, where);
/*     */   }
/*     */ 
/*     */   public void update(String table, T po, String where) {
/* 145 */     table = this.dbRouter.getTableName(table);
/* 146 */     super.update(table, po, where);
/*     */   }
/*     */ 
/*     */   private Integer getCurrentUserId()
/*     */   {
/* 155 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 156 */     return userid;
/*     */   }
/*     */ 
/*     */   private Integer getCurrentSiteId()
/*     */   {
/* 165 */     Integer id = EopContext.getContext().getCurrentSite().getId();
/* 166 */     return id;
/*     */   }
/*     */ 
/*     */   public String wrapExeSql(String sql) {
/* 170 */     sql = sql.toLowerCase();
/*     */     String pattern;
/* 172 */     if (sql.indexOf("update") >= 0) {
/* 173 */       pattern = "(update\\s+)(\\w+)(.+)";
/*     */     }
/*     */     else
/*     */     {
/*     */       String pattern;
/* 175 */       if (sql.indexOf("delete") >= 0) {
/* 176 */         pattern = "(delete\\s+from\\s+)(\\w+)(.+)";
/*     */       }
/*     */       else
/*     */       {
/*     */         String pattern;
/* 177 */         if (sql.indexOf("insert") >= 0) {
/* 178 */           pattern = "(insert\\s+into\\s+)(\\w+)(.+)";
/*     */         }
/*     */         else
/*     */         {
/*     */           String pattern;
/* 179 */           if (sql.indexOf("truncate") >= 0)
/* 180 */             pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
/*     */           else
/* 182 */             return sql;
/*     */         }
/*     */       }
/*     */     }
/*     */     String pattern;
/* 185 */     Pattern p = Pattern.compile(pattern, 34);
/* 186 */     Matcher m = p.matcher(sql);
/* 187 */     if (m.find()) {
/* 188 */       String tname = m.group(2);
/* 189 */       sql = m.replaceAll("$1 " + this.dbRouter.getTableName(tname) + " $3");
/*     */     }
/*     */ 
/* 193 */     return sql;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 210 */     String pattern = "(truncate\\s+table\\s+)(\\w+)(.*?)";
/* 211 */     Pattern p = Pattern.compile(pattern, 34);
/* 212 */     Matcher m = p.matcher("truncate table menu");
/* 213 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 215 */     if (m.find()) {
/* 216 */       String tname = m.group(2);
/* 217 */       System.out.println(m.replaceAll("$1 es_" + tname + "_2 $3"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String rpJoinTbName(String sql)
/*     */   {
/* 229 */     String pattern = "(join\\s+)(\\w+)(\\s+)";
/* 230 */     Pattern p = Pattern.compile(pattern, 34);
/* 231 */     Matcher m = p.matcher(sql);
/* 232 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 234 */     if (m.find()) {
/* 235 */       String tname = m.group(2);
/*     */ 
/* 237 */       m.appendReplacement(sb, "join " + this.dbRouter.getTableName(tname) + " ");
/*     */     }
/*     */ 
/* 240 */     m.appendTail(sb);
/*     */ 
/* 242 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String rpFromTbName(String sql)
/*     */   {
/* 253 */     String pattern = "(from\\s+)(\\w+)(\\s*)";
/* 254 */     Pattern p = Pattern.compile(pattern, 34);
/* 255 */     Matcher m = p.matcher(sql);
/* 256 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 258 */     if (m.find()) {
/* 259 */       String tname = m.group(2);
/* 260 */       m.appendReplacement(sb, "from " + this.dbRouter.getTableName(tname) + " ");
/*     */     }
/*     */ 
/* 263 */     m.appendTail(sb);
/* 264 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String rpSelTbName(String sql)
/*     */   {
/* 274 */     sql = rpJoinTbName(sql);
/* 275 */     sql = rpFromTbName(sql);
/* 276 */     return sql;
/*     */   }
/*     */ 
/*     */   public String addUidTerm(String sql)
/*     */   {
/*     */     String term;
/*     */     String term;
/* 282 */     if (sql.indexOf("where") > 1) {
/* 283 */       term = " and userid=" + getCurrentUserId() + " and siteid=" + getCurrentSiteId() + " ";
/*     */     }
/*     */     else
/* 286 */       term = " where userid=" + getCurrentUserId() + " and siteid=" + getCurrentSiteId() + " ";
/*     */     String pattern;
/*     */     String pattern;
/* 291 */     if (sql.indexOf("group") > 1) {
/* 292 */       pattern = "(.+)(group\\s+by)";
/*     */     }
/*     */     else
/*     */     {
/*     */       String pattern;
/* 293 */       if (sql.indexOf("order") > 1)
/* 294 */         pattern = "(.+)(order\\s+by)";
/*     */       else {
/* 296 */         pattern = "(.+)($)";
/*     */       }
/*     */     }
/* 299 */     Pattern p = Pattern.compile(pattern, 34);
/* 300 */     Matcher m = p.matcher(sql);
/* 301 */     if (m.find()) {
/* 302 */       sql = m.replaceAll("$1 " + term + " $2");
/*     */     }
/* 304 */     return sql;
/*     */   }
/*     */ 
/*     */   public String wrapSelSql(String sql)
/*     */   {
/* 314 */     sql = rpSelTbName(sql);
/*     */ 
/* 316 */     return sql;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.database.BaseJdbcDaoSupport
 * JD-Core Version:    0.6.0
 */