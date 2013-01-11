/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*     */ import com.enation.app.base.core.service.dbsolution.IDBSolution;
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.app.base.core.service.solution.IProfileLoader;
/*     */ import com.enation.app.base.core.service.solution.ISetupLoader;
/*     */ import com.enation.app.base.core.service.solution.ISolutionInstaller;
/*     */ import com.enation.app.base.core.service.solution.InstallerFactory;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.EopProduct;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.dom4j.Element;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class SolutionInstaller
/*     */   implements ISolutionInstaller
/*     */ {
/*  36 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private IDaoSupport<EopProduct> daoSupport;
/*     */   private ISiteManager siteManager;
/*     */   private IProfileLoader profileLoader;
/*     */   private InstallerFactory installerFactory;
/*     */   private ISetupLoader setupLoader;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void install(Integer userid, Integer siteid, String productId)
/*     */   {
/*  47 */     if ((!productId.toUpperCase().equals("BASE")) && (!productId.startsWith("temp_")))
/*     */     {
/*  49 */       this.siteManager.setSiteProduct(userid, siteid, productId);
/*     */     }
/*     */ 
/*  52 */     String[] types = { "apps", "menus", "adminThemes", "themes", "urls", "widgets", "indexitems", "site" };
/*     */ 
/*  66 */     org.w3c.dom.Document proFileDoc = this.profileLoader.load(productId);
/*     */ 
/*  69 */     for (String type : types) {
/*  70 */       install(type, proFileDoc, productId);
/*     */     }
/*     */ 
/*  74 */     org.dom4j.Document setupDoc = this.setupLoader.load(productId);
/*     */ 
/*  76 */     if (this.logger.isDebugEnabled()) {
/*  77 */       this.logger.debug("execute setup.xml...");
/*     */     }
/*     */ 
/*  80 */     String tablenameperfix = "";
/*  81 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  82 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  83 */       tablenameperfix = "_" + site.getUserid() + "_" + site.getId();
/*     */     }
/*     */ 
/*  94 */     List listRecreate = setupDoc.getRootElement().element("recreate").elements();
/*     */ 
/*  97 */     IDBSolution dbsolution = DBSolutionFactory.getDBSolution();
/*  98 */     Connection conn = DBSolutionFactory.getConnection(null);
/*  99 */     dbsolution.setConnection(conn);
/* 100 */     for (Iterator i$ = listRecreate.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 101 */       Element table = (Element)o;
/* 102 */       dbsolution.dropTable(table.getText() + tablenameperfix); }
/*     */     try
/*     */     {
/* 105 */       conn.close();
/*     */     } catch (SQLException e) {
/* 107 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 111 */     IInstaller installer = (IInstaller)SpringContextHolder.getBean("exampleDataInstaller");
/*     */ 
/* 113 */     installer.install(productId, null);
/*     */ 
/* 117 */     installer = (IInstaller)SpringContextHolder.getBean("adminUserInstaller");
/* 118 */     installer.install(productId, null);
/*     */ 
/* 124 */     install("components", proFileDoc, productId);
/*     */ 
/* 128 */     if (EopSetting.RUNMODE.equals("2"))
/*     */     {
/* 130 */       if (!"base".equals(productId))
/* 131 */         FileUtil.copyFile(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/profile.xml", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/profile.xml");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void install(String type, org.w3c.dom.Document proFileDoc, String productId)
/*     */   {
/* 140 */     if (this.logger.isDebugEnabled()) {
/* 141 */       this.logger.debug("install [" + type + "]");
/*     */     }
/*     */ 
/* 144 */     NodeList nodeList = proFileDoc.getElementsByTagName(type);
/* 145 */     if ((nodeList == null) || (nodeList.getLength() <= 0)) {
/* 146 */       return;
/*     */     }
/* 148 */     if (nodeList != null) {
/* 149 */       IInstaller installer = this.installerFactory.getInstaller(type);
/* 150 */       if (this.logger.isDebugEnabled()) {
/* 151 */         this.logger.debug("user installer [" + installer + "]");
/*     */       }
/* 153 */       installer.install(productId, nodeList.item(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IDaoSupport<EopProduct> getDaoSupport() {
/* 158 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport<EopProduct> daoSupport) {
/* 162 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 166 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 170 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public IProfileLoader getProfileLoader() {
/* 174 */     return this.profileLoader;
/*     */   }
/*     */ 
/*     */   public void setProfileLoader(IProfileLoader profileLoader) {
/* 178 */     this.profileLoader = profileLoader;
/*     */   }
/*     */ 
/*     */   public InstallerFactory getInstallerFactory() {
/* 182 */     return this.installerFactory;
/*     */   }
/*     */ 
/*     */   public void setInstallerFactory(InstallerFactory installerFactory) {
/* 186 */     this.installerFactory = installerFactory;
/*     */   }
/*     */ 
/*     */   public ISetupLoader getSetupLoader() {
/* 190 */     return this.setupLoader;
/*     */   }
/*     */ 
/*     */   public void setSetupLoader(ISetupLoader setupLoader) {
/* 194 */     this.setupLoader = setupLoader;
/*     */   }
/*     */ 
/*     */   public Logger getLogger() {
/* 198 */     return this.logger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SolutionInstaller
 * JD-Core Version:    0.6.0
 */