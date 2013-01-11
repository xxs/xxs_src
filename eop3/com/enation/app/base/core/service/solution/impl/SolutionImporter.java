/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.ISolutionImporter;
/*     */ import com.enation.app.base.core.service.solution.ISolutionInstaller;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.tools.ant.Project;
/*     */ import org.apache.tools.ant.taskdefs.Delete;
/*     */ import org.apache.tools.ant.taskdefs.Expand;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class SolutionImporter
/*     */   implements ISolutionImporter
/*     */ {
/*  27 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private ISolutionInstaller solutionInstaller;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void imported(String productid, String zipPath, boolean cleanZip)
/*     */   {
/*     */     try
/*     */     {
/*  33 */       if (EopSetting.RUNMODE.equals("1")) {
/*  34 */         EopSetting.INSTALL_LOCK = "NO";
/*     */       }
/*  36 */       if ("1".equals(EopSetting.RESOURCEMODE)) {
/*  37 */         FileUtil.delete(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes");
/*  38 */         FileUtil.delete(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes");
/*     */       }
/*     */ 
/*  41 */       if ("2".equals(EopSetting.RESOURCEMODE)) {
/*  42 */         FileUtil.delete(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes");
/*     */       }
/*     */ 
/*  46 */       String temppath = expand(productid, zipPath, cleanZip);
/*  47 */       File tempdir = new File(temppath);
/*     */ 
/*  49 */       EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  52 */       this.solutionInstaller.install(site.getUserid(), site.getId(), productid);
/*  53 */       this.solutionInstaller.install(site.getUserid(), site.getId(), "base");
/*     */ 
/*  55 */       Project prj = new Project();
/*     */ 
/*  57 */       Delete delete = new Delete();
/*  58 */       delete.setProject(prj);
/*  59 */       delete.setDir(tempdir);
/*  60 */       delete.execute();
/*     */ 
/*  62 */       if (EopSetting.RUNMODE.equals("1"))
/*  63 */         EopSetting.INSTALL_LOCK = "YES";
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  67 */       e.printStackTrace();
/*  68 */       this.logger.error("导入解决方案", e.fillInStackTrace());
/*  69 */       if (EopSetting.RUNMODE.equals("1")) {
/*  70 */         EopSetting.INSTALL_LOCK = "YES";
/*     */       }
/*  72 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String expand(String productid, String zipPath, boolean cleanZip)
/*     */   {
/*  84 */     String temppath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productid;
/*  85 */     File tempdir = new File(temppath);
/*  86 */     tempdir.mkdirs();
/*     */ 
/*  88 */     File zipFile = new File(zipPath);
/*  89 */     Project prj = new Project();
/*  90 */     Expand expand = new Expand();
/*  91 */     expand.setEncoding("UTF-8");
/*  92 */     expand.setProject(prj);
/*  93 */     expand.setSrc(zipFile);
/*  94 */     expand.setOverwrite(true);
/*  95 */     expand.setDest(tempdir);
/*  96 */     expand.execute();
/*     */ 
/*  98 */     if (cleanZip)
/*     */     {
/* 100 */       Delete delete = new Delete();
/* 101 */       delete.setDir(zipFile);
/* 102 */       delete.execute();
/*     */     }
/*     */ 
/* 105 */     return temppath;
/*     */   }
/*     */ 
/*     */   public ISolutionInstaller getSolutionInstaller() {
/* 109 */     return this.solutionInstaller;
/*     */   }
/*     */ 
/*     */   public void setSolutionInstaller(ISolutionInstaller solutionInstaller) {
/* 113 */     this.solutionInstaller = solutionInstaller;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SolutionImporter
 * JD-Core Version:    0.6.0
 */