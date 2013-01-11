/*     */ package com.enation.app.base;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.impl.SqlExportService;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.App;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.cache.CacheFactory;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ 
/*     */ public class BaseApp extends App
/*     */ {
/*     */   private IDBRouter baseDBRouter;
/*     */   private SqlExportService sqlExportService;
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */ 
/*     */   public BaseApp()
/*     */   {
/*  28 */     this.tables.add("adv");
/*  29 */     this.tables.add("access");
/*  30 */     this.tables.add("adcolumn");
/*  31 */     this.tables.add("admintheme");
/*  32 */     this.tables.add("auth_action");
/*  33 */     this.tables.add("friends_link");
/*  34 */     this.tables.add("guestbook");
/*  35 */     this.tables.add("menu");
/*  36 */     this.tables.add("theme");
/*  37 */     this.tables.add("themeuri");
/*     */ 
/*  39 */     this.tables.add("adminuser");
/*  40 */     this.tables.add("role");
/*  41 */     this.tables.add("role_auth");
/*  42 */     this.tables.add("settings");
/*  43 */     this.tables.add("site_menu");
/*  44 */     this.tables.add("user_role");
/*  45 */     this.tables.add("smtp");
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  51 */     return "base";
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  55 */     return "base应用";
/*     */   }
/*     */ 
/*     */   public String getNameSpace() {
/*  59 */     return "/core";
/*     */   }
/*     */ 
/*     */   public void saasInstall()
/*     */   {
/*  66 */     this.baseDBRouter.doSaasInstall("file:com/enation/app/base/base.xml");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String dumpSql()
/*     */   {
/*  76 */     return "";
/*     */   }
/*     */ 
/*     */   public String dumpSql(Document setup) {
/*  80 */     StringBuffer sql = new StringBuffer();
/*     */ 
/*  83 */     sql.append(this.sqlExportService.dumpSql(this.tables, "clean", setup));
/*  84 */     sql.append(createBaseAppSql());
/*  85 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   private String createBaseAppSql()
/*     */   {
/*  95 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  96 */     String logofile = site.getLogofile();
/*  97 */     String icofile = site.getIcofile();
/*  98 */     String upath = EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath();
/*     */ 
/* 100 */     if (icofile != null) {
/* 101 */       icofile = icofile.replaceAll(upath, "fs:");
/*     */     }
/* 103 */     if (logofile != null) {
/* 104 */       logofile = logofile.replaceAll(upath, "fs:");
/*     */     }
/* 106 */     String sql = "update eop_site set sitename='" + site.getSitename() + "',logofile='" + logofile + "',icofile='" + icofile + "',keywords='" + site.getKeywords() + "',descript='" + site.getDescript() + "' where userid=<userid> and id=<siteid>;\n";
/*     */ 
/* 112 */     return sql;
/*     */   }
/*     */ 
/*     */   public void install()
/*     */   {
/* 119 */     doInstall("file:com/enation/app/base/base.xml");
/*     */   }
/*     */ 
/*     */   protected void cleanCache() {
/* 123 */     super.cleanCache();
/*     */ 
/* 125 */     CacheFactory.getCache("widgetCache").remove("widget_" + this.userid + "_" + this.siteid);
/*     */ 
/* 129 */     CacheFactory.getCache("sitemap").remove("sitemap_" + this.userid + "_" + this.siteid);
/*     */ 
/* 133 */     CacheFactory.getCache("themeUriCache").remove("theme_uri_list_" + this.userid + "_" + this.siteid);
/*     */ 
/* 137 */     CacheFactory.getCache("siteMenuList").remove("siteMenuList_" + this.userid + "_" + this.siteid);
/*     */   }
/*     */ 
/*     */   public void sessionDestroyed(String seesionid, EopSite site)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseDBRouter()
/*     */   {
/* 151 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 155 */     this.baseDBRouter = baseDBRouter;
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseSaasDBRouter() {
/* 159 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseSaasDBRouter(IDBRouter baseSaasDBRouter) {
/* 163 */     this.baseDBRouter = baseSaasDBRouter;
/*     */   }
/*     */ 
/*     */   public ISqlFileExecutor getSqlFileExecutor() {
/* 167 */     return this.sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 171 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public SqlExportService getSqlExportService() {
/* 175 */     return this.sqlExportService;
/*     */   }
/*     */ 
/*     */   public void setSqlExportService(SqlExportService sqlExportService) {
/* 179 */     this.sqlExportService = sqlExportService;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.BaseApp
 * JD-Core Version:    0.6.0
 */