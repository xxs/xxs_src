/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.ISetupCreator;
/*     */ import com.enation.app.base.core.service.solution.ISolutionExporter;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.sdk.IApp;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.tools.ant.Project;
/*     */ import org.apache.tools.ant.taskdefs.Delete;
/*     */ import org.apache.tools.ant.taskdefs.Zip;
/*     */ import org.apache.tools.ant.types.FileSet;
/*     */ import org.dom4j.Document;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class SolutionExporter
/*     */   implements ISolutionExporter
/*     */ {
/*  31 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private SqlExportService sqlExportService;
/*     */   private ProfileCreator profileCreator;
/*     */   private ISetupCreator setupCreator;
/*     */   private ISiteManager siteManager;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void export(String name, boolean exportData, boolean exportTheme, boolean exportProfile, boolean exportAttr)
/*     */   {
/*  43 */     String datapath = EopSetting.IMG_SERVER_PATH + "/" + EopContext.getContext().getContextPath() + "/backup/";
/*     */ 
/*  46 */     String temppath = datapath + System.currentTimeMillis();
/*  47 */     File tempdir = new File(temppath);
/*  48 */     tempdir.mkdirs();
/*     */ 
/*  51 */     File target = new File(datapath + name + ".zip");
/*  52 */     if (target.exists()) target.delete();
/*     */ 
/*     */     try
/*     */     {
/*  63 */       if (exportData)
/*     */       {
/*  65 */         StringBuffer sqlContent = new StringBuffer();
/*  66 */         StringBuffer xmlFile = new StringBuffer();
/*  67 */         xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*  68 */         xmlFile.append("<dbsolution>\n");
/*     */ 
/*  71 */         Document setup = this.setupCreator.createSetup(temppath + "/setup.xml");
/*  72 */         List appList = this.siteManager.getSiteApps();
/*  73 */         for (EopApp eopApp : appList) {
/*  74 */           String appid = eopApp.getAppid();
/*  75 */           IApp app = (IApp)SpringContextHolder.getBean(appid);
/*  76 */           if ("1".equals(EopSetting.DBTYPE))
/*  77 */             sqlContent.append(app.dumpSql(setup));
/*  78 */           xmlFile.append(app.dumpXml(setup));
/*     */         }
/*  80 */         xmlFile.append("</dbsolution>");
/*  81 */         if ("1".equals(EopSetting.DBTYPE)) {
/*  82 */           FileUtil.write(temppath + "/example_data_mysql.sql", sqlContent.toString());
/*     */         }
/*  84 */         this.setupCreator.save(setup, temppath + "/setup.xml");
/*  85 */         FileUtil.write(temppath + "/example_data.xml", xmlFile.toString());
/*     */       }
/*     */ 
/*  93 */       if (exportTheme)
/*     */       {
/*  95 */         if ("1".equals(EopSetting.RESOURCEMODE)) {
/*  96 */           FileUtil.copyFolder(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
/*  97 */           FileUtil.copyFolder(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
/*     */         }
/*     */ 
/* 100 */         if ("2".equals(EopSetting.RESOURCEMODE)) {
/* 101 */           FileUtil.copyFolder(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 111 */       if (exportProfile)
/*     */       {
/* 113 */         this.profileCreator.createProfile(temppath + "/profile.xml");
/*     */       }
/*     */ 
/* 121 */       if (exportAttr)
/*     */       {
/* 123 */         FileUtil.copyFolder(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment", temppath + "/attachment");
/*     */       }
/*     */ 
/* 126 */       Project prj = new Project();
/* 127 */       Zip zip = new Zip();
/* 128 */       zip.setEncoding("UTF-8");
/* 129 */       zip.setProject(prj);
/* 130 */       zip.setDestFile(target);
/* 131 */       FileSet fileSet = new FileSet();
/* 132 */       fileSet.setProject(prj);
/* 133 */       fileSet.setDir(tempdir);
/* 134 */       zip.addFileset(fileSet);
/* 135 */       zip.execute();
/* 136 */       Delete delete = new Delete();
/* 137 */       delete.setDir(tempdir);
/* 138 */       delete.execute();
/*     */     }
/*     */     catch (Exception e) {
/* 141 */       e.printStackTrace();
/* 142 */       this.logger.error("导出解决方案出错", e.fillInStackTrace());
/* 143 */       throw new RuntimeException("导出解决方案出错[" + e.getMessage() + "]", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public SqlExportService getSqlExportService() {
/* 148 */     return this.sqlExportService;
/*     */   }
/*     */ 
/*     */   public void setSqlExportService(SqlExportService sqlExportService) {
/* 152 */     this.sqlExportService = sqlExportService;
/*     */   }
/*     */ 
/*     */   public ProfileCreator getProfileCreator() {
/* 156 */     return this.profileCreator;
/*     */   }
/*     */ 
/*     */   public void setProfileCreator(ProfileCreator profileCreator) {
/* 160 */     this.profileCreator = profileCreator;
/*     */   }
/*     */ 
/*     */   public ISetupCreator getSetupCreator() {
/* 164 */     return this.setupCreator;
/*     */   }
/*     */ 
/*     */   public void setSetupCreator(ISetupCreator setupCreator) {
/* 168 */     this.setupCreator = setupCreator;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 172 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 176 */     this.siteManager = siteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SolutionExporter
 * JD-Core Version:    0.6.0
 */