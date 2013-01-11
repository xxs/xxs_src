/*     */ package com.enation.app.base.core.service;
/*     */ 
/*     */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*     */ import com.enation.app.base.core.service.solution.ISolutionInstaller;
/*     */ import com.enation.eop.resource.IAppManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class EopInstallManager
/*     */ {
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   private IAppManager appManager;
/*     */   private ApplicationContext context;
/*     */   private IUserManager userManager;
/*     */   private ISiteManager siteManager;
/*     */   private ISolutionInstaller solutionInstaller;
/*     */ 
/*     */   public void install(String username, String password, String domain, String productid)
/*     */   {
/*  28 */     EopSite site = new EopSite();
/*  29 */     site.setUserid(Integer.valueOf(1));
/*  30 */     site.setId(Integer.valueOf(1));
/*  31 */     EopContext context = new EopContext();
/*  32 */     context.setCurrentSite(site);
/*  33 */     EopContext.setContext(context);
/*     */ 
/*  35 */     installUser(username, password, domain, productid);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void installUser(String username, String password, String domain, String productid)
/*     */   {
/*  44 */     DBSolutionFactory.dbImport("file:com/enation/app/base/init.xml", "");
/*     */ 
/*  46 */     EopUser eopUser = new EopUser();
/*  47 */     eopUser.setAddress("在这里输入详细地址");
/*  48 */     eopUser.setUsername(username);
/*  49 */     eopUser.setCompanyname("单机版用户");
/*  50 */     eopUser.setLinkman("在这里输入联系人信息");
/*  51 */     eopUser.setTel("010-12345678");
/*  52 */     eopUser.setMobile("13888888888");
/*  53 */     eopUser.setEmail("youmail@domain.com");
/*  54 */     eopUser.setUsertype(Integer.valueOf(1));
/*  55 */     eopUser.setPassword(password);
/*  56 */     Integer userid = this.userManager.createUser(eopUser);
/*  57 */     this.userManager.login(username, password);
/*     */ 
/*  59 */     EopSite site = new EopSite();
/*  60 */     site.setSitename("javashop");
/*  61 */     site.setThemeid(Integer.valueOf(1));
/*  62 */     site.setAdminthemeid(Integer.valueOf(1));
/*  63 */     site.setSitename(productid + "新建站点");
/*  64 */     site.setUserid(userid);
/*  65 */     site.setUsername(eopUser.getUsername());
/*  66 */     site.setUsertel(eopUser.getTel());
/*  67 */     site.setUsermobile(eopUser.getMobile());
/*  68 */     site.setUseremail(eopUser.getEmail());
/*     */ 
/*  70 */     Integer siteid = this.siteManager.add(site, domain);
/*     */ 
/*  72 */     this.solutionInstaller.install(userid, siteid, productid);
/*  73 */     this.solutionInstaller.install(userid, siteid, "base");
/*     */   }
/*     */ 
/*     */   public JdbcTemplate getJdbcTemplate() {
/*  77 */     return this.jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/*  81 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */   public IAppManager getAppManager() {
/*  85 */     return this.appManager;
/*     */   }
/*     */ 
/*     */   public void setAppManager(IAppManager appManager) {
/*  89 */     this.appManager = appManager;
/*     */   }
/*     */ 
/*     */   public ApplicationContext getContext() {
/*  93 */     return this.context;
/*     */   }
/*     */ 
/*     */   public void setContext(ApplicationContext context) {
/*  97 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public ISolutionInstaller getSolutionInstaller() {
/* 101 */     return this.solutionInstaller;
/*     */   }
/*     */ 
/*     */   public void setSolutionInstaller(ISolutionInstaller solutionInstaller) {
/* 105 */     this.solutionInstaller = solutionInstaller;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 109 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 113 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 117 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 121 */     this.siteManager = siteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.EopInstallManager
 * JD-Core Version:    0.6.0
 */