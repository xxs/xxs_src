/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.service.EopInstallManager;
/*     */ import com.enation.app.base.core.service.IDataSourceCreator;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.component.IComponentManager;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
/*     */ 
/*     */ public class EopInstallAction extends WWAction
/*     */ {
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   private SimpleJdbcTemplate simpleJdbcTemplate;
/*     */   private EopInstallManager eopInstallManager;
/*     */   private DataSource dataSource;
/*     */   private IDataSourceCreator dataSourceCreator;
/*     */   private IComponentManager componentManager;
/*     */   private String dbhost;
/*     */   private String uname;
/*     */   private String pwd;
/*     */   private String dbtype;
/*     */   private String dbname;
/*     */   private String domain;
/*     */   private String productid;
/*     */   private String staticdomain;
/*     */   private String staticpath;
/*     */   private String solutionpath;
/*     */   private int resourcemode;
/*     */   private int devmodel;
/*     */   private String osVersion;
/*     */   private String javaVersion;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  62 */     return "step1";
/*     */   }
/*     */ 
/*     */   public String step2()
/*     */   {
/*  72 */     return "step2";
/*     */   }
/*     */ 
/*     */   public String step3()
/*     */   {
/*  81 */     saveEopParams();
/*  82 */     if ("mysql".equals(this.dbtype))
/*  83 */       saveMysqlDBParams();
/*  84 */     else if ("oracle".equals(this.dbtype))
/*  85 */       saveOracleDBParams();
/*  86 */     else if ("sqlserver".equals(this.dbtype)) {
/*  87 */       saveSQLServerDBParams();
/*     */     }
/*  89 */     Properties props = System.getProperties();
/*  90 */     this.osVersion = (props.getProperty("os.name") + "(" + props.getProperty("os.version") + ")");
/*  91 */     this.javaVersion = props.getProperty("java.version");
/*  92 */     return "step3";
/*     */   }
/*     */ 
/*     */   public String installSuccess() {
/*  96 */     FileUtil.write(EopSetting.EOP_PATH + "/install/install.lock", "如果要重新安装，请删除此文件，并重新起动web容器");
/*  97 */     EopSetting.INSTALL_LOCK = "yes";
/*  98 */     if ("1".equals(EopSetting.RUNMODE)) {
/*  99 */       this.componentManager.startComponents();
/*     */     }
/* 101 */     return "success";
/*     */   }
/*     */ 
/*     */   private void saveMysqlDBParams()
/*     */   {
/* 109 */     Properties props = new Properties();
/* 110 */     props.setProperty("jdbc.driverClassName", "com.mysql.jdbc.Driver");
/* 111 */     props.setProperty("jdbc.url", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true");
/* 112 */     props.setProperty("jdbc.username", this.uname);
/* 113 */     props.setProperty("jdbc.password", this.pwd);
/* 114 */     saveProperties(props);
/*     */   }
/*     */ 
/*     */   private void saveProperties(Properties props)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       String path = StringUtil.getRootPath("config/jdbc.properties");
/* 124 */       File file = new File(path);
/*     */ 
/* 126 */       props.store(new FileOutputStream(file), "jdbc.properties");
/*     */     } catch (FileNotFoundException e) {
/* 128 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 130 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveOracleDBParams()
/*     */   {
/* 138 */     Properties props = new Properties();
/* 139 */     props.setProperty("jdbc.driverClassName", "oracle.jdbc.driver.OracleDriver");
/* 140 */     props.setProperty("jdbc.url", "jdbc:oracle:thin:@" + this.dbhost + ":" + this.dbname);
/* 141 */     props.setProperty("jdbc.username", this.uname);
/* 142 */     props.setProperty("jdbc.password", this.pwd);
/* 143 */     saveProperties(props);
/*     */   }
/*     */ 
/*     */   private void saveSQLServerDBParams()
/*     */   {
/* 150 */     Properties props = new Properties();
/* 151 */     props.setProperty("jdbc.driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
/* 152 */     props.setProperty("jdbc.url", "jdbc:sqlserver://" + this.dbhost + ";databaseName=" + this.dbname);
/* 153 */     props.setProperty("jdbc.username", this.uname);
/* 154 */     props.setProperty("jdbc.password", this.pwd);
/* 155 */     saveProperties(props);
/*     */   }
/*     */ 
/*     */   private void prosessPath()
/*     */   {
/* 162 */     if (this.staticpath != null) {
/* 163 */       this.staticpath = (this.staticpath.endsWith("/") ? this.staticpath.substring(0, this.staticpath.length() - 1) : this.staticpath);
/*     */     }
/* 165 */     if (this.solutionpath != null) {
/* 166 */       this.solutionpath = (this.solutionpath.endsWith("/") ? this.solutionpath.substring(0, this.solutionpath.length() - 1) : this.solutionpath);
/*     */     }
/*     */ 
/* 169 */     if (this.staticdomain != null)
/* 170 */       this.staticdomain = (this.staticdomain.endsWith("/") ? this.staticdomain.substring(0, this.staticdomain.length() - 1) : this.staticdomain);
/*     */   }
/*     */ 
/*     */   private void saveEopParams()
/*     */   {
/* 177 */     prosessPath();
/* 178 */     InputStream in = FileUtil.getResourceAsStream("eop.properties");
/* 179 */     String webroot = StringUtil.getRootPath();
/* 180 */     Properties props = new Properties();
/*     */     try {
/* 182 */       props.load(in);
/*     */ 
/* 184 */       props.setProperty("script_cache", "false");
/* 185 */       props.setProperty("script_compress", "true");
/* 186 */       props.setProperty("http_cache", "0");
/*     */ 
/* 188 */       if (this.devmodel == 1) {
/* 189 */         props.setProperty("development_model", "true");
/* 190 */         this.resourcemode = 2;
/*     */       } else {
/* 192 */         props.setProperty("development_model", "false");
/*     */       }
/*     */ 
/* 196 */       if (this.resourcemode == 2) {
/* 197 */         props.setProperty("path.imageserver", webroot + "/statics");
/* 198 */         props.setProperty("domain.imageserver", "http://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath() + "/statics");
/*     */       }
/*     */ 
/* 202 */       if (this.resourcemode == 1) {
/*     */         try {
/* 204 */           FileUtil.copyFolder(webroot + "/statics", this.staticpath);
/* 205 */           props.setProperty("path.imageserver", this.staticpath);
/* 206 */           props.setProperty("domain.imageserver", this.staticdomain);
/*     */         } catch (Exception e) {
/* 208 */           throw new RuntimeException();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 213 */       if ("2".equals(EopSetting.RUNMODE)) {
/* 214 */         props.setProperty("storage.app_data", this.solutionpath + "/commons");
/* 215 */         props.setProperty("storage.products", this.solutionpath);
/*     */       } else {
/* 217 */         props.setProperty("storage.app_data", webroot + "/products/commons");
/* 218 */         props.setProperty("storage.products", webroot + "/products");
/*     */       }
/* 220 */       props.setProperty("resourcemode", "" + this.resourcemode);
/* 221 */       if ("mysql".equals(this.dbtype))
/* 222 */         props.setProperty("dbtype", "1");
/* 223 */       else if ("oracle".equals(this.dbtype))
/* 224 */         props.setProperty("dbtype", "2");
/* 225 */       else if ("sqlserver".equals(this.dbtype))
/* 226 */         props.setProperty("dbtype", "3");
/* 227 */       props.setProperty("storage.EOPServer", webroot);
/* 228 */       props.setProperty("path.context_path", getRequest().getContextPath());
/*     */ 
/* 230 */       String path = StringUtil.getRootPath("eop.properties");
/* 231 */       File file = new File(path);
/* 232 */       props.store(new FileOutputStream(file), "eop.properties");
/* 233 */       EopSetting.init(props);
/*     */     }
/*     */     catch (IOException e) {
/* 236 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String doInstall()
/*     */   {
/*     */     try {
/* 243 */       if ("2".equals(EopSetting.RUNMODE))
/* 244 */         this.eopInstallManager.install(this.uname, this.pwd, this.domain, this.productid);
/*     */       else {
/* 246 */         this.eopInstallManager.install(this.uname, this.pwd, "localhost", this.productid);
/*     */       }
/* 248 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/* 250 */       e.printStackTrace();
/* 251 */       this.json = "{result:0}";
/*     */     }
/* 253 */     return "json_message";
/*     */   }
/*     */ 
/*     */   private boolean createAndTest(String driver, String url)
/*     */   {
/*     */     try
/*     */     {
/* 265 */       DataSource newDataSource = this.dataSourceCreator.createDataSource(driver, url, this.uname, this.pwd);
/*     */ 
/* 267 */       if ("mysql".equals(this.dbtype)) {
/* 268 */         this.jdbcTemplate.setDataSource(newDataSource);
/* 269 */         this.jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + this.dbname + "` DEFAULT CHARACTER SET UTF8");
/* 270 */         newDataSource = this.dataSourceCreator.createDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8", this.uname, this.pwd);
/* 271 */         this.jdbcTemplate.execute("use " + this.dbname);
/*     */       }
/*     */ 
/* 274 */       this.dataSource = newDataSource;
/* 275 */       this.jdbcTemplate.setDataSource(newDataSource);
/* 276 */       this.simpleJdbcTemplate = new SimpleJdbcTemplate(newDataSource);
/* 277 */       this.jdbcTemplate.execute("CREATE TABLE JAVAMALLTESTTABLE (ID INT not null)");
/* 278 */       this.jdbcTemplate.execute("DROP TABLE JAVAMALLTESTTABLE");
/*     */ 
/* 280 */       return true;
/*     */     }
/*     */     catch (RuntimeException e) {
/* 283 */       e.printStackTrace();
/* 284 */       this.logger.error(e.fillInStackTrace());
/* 285 */     }return false;
/*     */   }
/*     */ 
/*     */   private boolean mysqlTestConnection()
/*     */   {
/* 295 */     return createAndTest("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/test?useUnicode=true&characterEncoding=utf8");
/*     */   }
/*     */ 
/*     */   private boolean oracleTestConnection()
/*     */   {
/* 304 */     return createAndTest("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@" + this.dbhost + ":" + this.dbname);
/*     */   }
/*     */ 
/*     */   private boolean sqlserverTestConnection() {
/* 308 */     return createAndTest("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://" + this.dbhost + ";databaseName=" + this.dbname);
/*     */   }
/*     */ 
/*     */   public String testConnection() {
/* 312 */     boolean result = false;
/*     */ 
/* 314 */     if ("mysql".equals(this.dbtype))
/* 315 */       result = mysqlTestConnection();
/* 316 */     else if ("oracle".equals(this.dbtype))
/* 317 */       result = oracleTestConnection();
/* 318 */     else if ("sqlserver".equals(this.dbtype)) {
/* 319 */       result = sqlserverTestConnection();
/*     */     }
/* 321 */     if (result)
/* 322 */       this.json = "{result:1}";
/*     */     else {
/* 324 */       this.json = "{result:0}";
/*     */     }
/*     */ 
/* 327 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String testReady() {
/*     */     try {
/* 332 */       if ("mysql".equals(this.dbtype))
/* 333 */         this.jdbcTemplate.execute("drop table if exists test");
/* 334 */       this.json = "{result:1}";
/*     */     }
/*     */     catch (RuntimeException e) {
/* 337 */       this.json = "{result:0}";
/*     */     }
/*     */ 
/* 340 */     return "json_message";
/*     */   }
/*     */ 
/*     */   private DataSource switchNewDBSource()
/*     */   {
/* 349 */     testConnection();
/* 350 */     DataSource newDataSource = this.dataSourceCreator.createDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://" + this.dbhost + "/" + this.dbname + "?useUnicode=true&characterEncoding=utf8", this.uname, this.pwd);
/* 351 */     this.dataSource = newDataSource;
/* 352 */     this.jdbcTemplate.setDataSource(newDataSource);
/* 353 */     this.jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + this.dbname + "` DEFAULT CHARACTER SET UTF8");
/* 354 */     this.jdbcTemplate.execute("use " + this.dbname);
/* 355 */     return newDataSource;
/*     */   }
/*     */ 
/*     */   public JdbcTemplate getJdbcTemplate() {
/* 359 */     return this.jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/* 363 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public String getDbhost() {
/* 367 */     return this.dbhost;
/*     */   }
/*     */ 
/*     */   public void setDbhost(String dbhost) {
/* 371 */     this.dbhost = dbhost;
/*     */   }
/*     */ 
/*     */   public String getUname() {
/* 375 */     return this.uname;
/*     */   }
/*     */ 
/*     */   public void setUname(String uname) {
/* 379 */     this.uname = uname;
/*     */   }
/*     */ 
/*     */   public String getPwd() {
/* 383 */     return this.pwd;
/*     */   }
/*     */ 
/*     */   public void setPwd(String pwd)
/*     */   {
/* 388 */     this.pwd = pwd;
/*     */   }
/*     */ 
/*     */   public String getDbtype()
/*     */   {
/* 393 */     return this.dbtype;
/*     */   }
/*     */ 
/*     */   public void setDbtype(String dbtype)
/*     */   {
/* 398 */     this.dbtype = dbtype;
/*     */   }
/*     */ 
/*     */   public String getDbname()
/*     */   {
/* 403 */     return this.dbname;
/*     */   }
/*     */ 
/*     */   public void setDbname(String dbname)
/*     */   {
/* 408 */     this.dbname = dbname;
/*     */   }
/*     */ 
/*     */   public EopInstallManager getEopInstallManager()
/*     */   {
/* 413 */     return this.eopInstallManager;
/*     */   }
/*     */ 
/*     */   public void setEopInstallManager(EopInstallManager eopInstallManager)
/*     */   {
/* 418 */     this.eopInstallManager = eopInstallManager;
/*     */   }
/*     */ 
/*     */   public String getOsVersion()
/*     */   {
/* 423 */     return this.osVersion;
/*     */   }
/*     */ 
/*     */   public void setOsVersion(String osVersion)
/*     */   {
/* 428 */     this.osVersion = osVersion;
/*     */   }
/*     */ 
/*     */   public String getJavaVersion()
/*     */   {
/* 433 */     return this.javaVersion;
/*     */   }
/*     */ 
/*     */   public void setJavaVersion(String javaVersion)
/*     */   {
/* 438 */     this.javaVersion = javaVersion;
/*     */   }
/*     */ 
/*     */   public String getDomain()
/*     */   {
/* 443 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(String domain)
/*     */   {
/* 448 */     this.domain = domain;
/*     */   }
/*     */ 
/*     */   public String getProductid()
/*     */   {
/* 453 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(String productid)
/*     */   {
/* 458 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public SimpleJdbcTemplate getSimpleJdbcTemplate()
/*     */   {
/* 463 */     return this.simpleJdbcTemplate;
/*     */   }
/*     */ 
/*     */   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate)
/*     */   {
/* 468 */     this.simpleJdbcTemplate = simpleJdbcTemplate;
/*     */   }
/*     */ 
/*     */   public DataSource getDataSource()
/*     */   {
/* 473 */     return this.dataSource;
/*     */   }
/*     */ 
/*     */   public void setDataSource(DataSource dataSource)
/*     */   {
/* 478 */     this.dataSource = dataSource;
/*     */   }
/*     */ 
/*     */   public String getStaticdomain()
/*     */   {
/* 483 */     return this.staticdomain;
/*     */   }
/*     */ 
/*     */   public void setStaticdomain(String staticdomain)
/*     */   {
/* 488 */     this.staticdomain = staticdomain;
/*     */   }
/*     */ 
/*     */   public String getStaticpath()
/*     */   {
/* 493 */     return this.staticpath;
/*     */   }
/*     */ 
/*     */   public void setStaticpath(String staticpath)
/*     */   {
/* 498 */     this.staticpath = staticpath;
/*     */   }
/*     */ 
/*     */   public int getResourcemode()
/*     */   {
/* 503 */     return this.resourcemode;
/*     */   }
/*     */ 
/*     */   public void setResourcemode(int resourcemode)
/*     */   {
/* 508 */     this.resourcemode = resourcemode;
/*     */   }
/*     */ 
/*     */   public String getSolutionpath()
/*     */   {
/* 513 */     return this.solutionpath;
/*     */   }
/*     */ 
/*     */   public void setSolutionpath(String solutionpath)
/*     */   {
/* 518 */     this.solutionpath = solutionpath;
/*     */   }
/*     */ 
/*     */   public IDataSourceCreator getDataSourceCreator()
/*     */   {
/* 523 */     return this.dataSourceCreator;
/*     */   }
/*     */ 
/*     */   public void setDataSourceCreator(IDataSourceCreator dataSourceCreator)
/*     */   {
/* 528 */     this.dataSourceCreator = dataSourceCreator;
/*     */   }
/*     */ 
/*     */   public IComponentManager getComponentManager()
/*     */   {
/* 533 */     return this.componentManager;
/*     */   }
/*     */ 
/*     */   public void setComponentManager(IComponentManager componentManager)
/*     */   {
/* 538 */     this.componentManager = componentManager;
/*     */   }
/*     */ 
/*     */   public int getDevmodel()
/*     */   {
/* 543 */     return this.devmodel;
/*     */   }
/*     */ 
/*     */   public void setDevmodel(int devmodel)
/*     */   {
/* 548 */     this.devmodel = devmodel;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.EopInstallAction
 * JD-Core Version:    0.6.0
 */