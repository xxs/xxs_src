/*     */ package com.enation.app.base.core.service.solution;
/*     */ 
/*     */ public class InstallerFactory
/*     */ {
/*     */   public static final String TYPE_APP = "apps";
/*     */   public static final String TYPE_MENU = "menus";
/*     */   public static final String TYPE_ADMINTHEME = "adminThemes";
/*     */   public static final String TYPE_THEME = "themes";
/*     */   public static final String TYPE_URL = "urls";
/*     */   public static final String TYPE_WIDGET = "widgets";
/*     */   public static final String TYPE_INDEX_ITEM = "indexitems";
/*     */   public static final String TYPE_COMPONENT = "components";
/*     */   public static final String TYPE_SITE = "site";
/*     */   private IInstaller menuInstaller;
/*     */   private IInstaller adminThemeInstaller;
/*     */   private IInstaller themeInstaller;
/*     */   private IInstaller uriInstaller;
/*     */   private IInstaller widgetInstaller;
/*     */   private IInstaller appInstaller;
/*     */   private IInstaller indexItemInstaller;
/*     */   private IInstaller componentInstaller;
/*     */   private IInstaller siteInstaller;
/*     */ 
/*     */   public IInstaller getInstaller(String type)
/*     */   {
/*  34 */     if ("apps".equals(type)) {
/*  35 */       return this.appInstaller;
/*     */     }
/*     */ 
/*  38 */     if ("menus".equals(type)) {
/*  39 */       return this.menuInstaller;
/*     */     }
/*     */ 
/*  43 */     if ("adminThemes".equals(type)) {
/*  44 */       return this.adminThemeInstaller;
/*     */     }
/*     */ 
/*  47 */     if ("themes".equals(type)) {
/*  48 */       return this.themeInstaller;
/*     */     }
/*     */ 
/*  52 */     if ("urls".equals(type)) {
/*  53 */       return this.uriInstaller;
/*     */     }
/*     */ 
/*  56 */     if ("widgets".equals(type)) {
/*  57 */       return this.widgetInstaller;
/*     */     }
/*     */ 
/*  60 */     if ("indexitems".equals(type)) {
/*  61 */       return this.indexItemInstaller;
/*     */     }
/*     */ 
/*  64 */     if ("components".equals(type)) {
/*  65 */       return this.componentInstaller;
/*     */     }
/*     */ 
/*  68 */     if ("site".equals(type)) {
/*  69 */       return this.siteInstaller;
/*     */     }
/*  71 */     throw new RuntimeException(" get Installer instance error[incorrect type param]");
/*     */   }
/*     */ 
/*     */   public void setMenuInstaller(IInstaller menuInstaller) {
/*  75 */     this.menuInstaller = menuInstaller;
/*     */   }
/*     */ 
/*     */   public void setAdminThemeInstaller(IInstaller adminThemeInstaller) {
/*  79 */     this.adminThemeInstaller = adminThemeInstaller;
/*     */   }
/*     */ 
/*     */   public void setThemeInstaller(IInstaller themeInstaller) {
/*  83 */     this.themeInstaller = themeInstaller;
/*     */   }
/*     */ 
/*     */   public void setUriInstaller(IInstaller uriInstaller) {
/*  87 */     this.uriInstaller = uriInstaller;
/*     */   }
/*     */ 
/*     */   public void setWidgetInstaller(IInstaller widgetInstaller) {
/*  91 */     this.widgetInstaller = widgetInstaller;
/*     */   }
/*     */ 
/*     */   public IInstaller getAppInstaller() {
/*  95 */     return this.appInstaller;
/*     */   }
/*     */ 
/*     */   public void setAppInstaller(IInstaller appInstaller) {
/*  99 */     this.appInstaller = appInstaller;
/*     */   }
/*     */ 
/*     */   public void setIndexItemInstaller(IInstaller indexItemInstaller) {
/* 103 */     this.indexItemInstaller = indexItemInstaller;
/*     */   }
/*     */ 
/*     */   public void setComponentInstaller(IInstaller componentInstaller) {
/* 107 */     this.componentInstaller = componentInstaller;
/*     */   }
/*     */ 
/*     */   public IInstaller getSiteInstaller() {
/* 111 */     return this.siteInstaller;
/*     */   }
/*     */ 
/*     */   public void setSiteInstaller(IInstaller siteInstaller) {
/* 115 */     this.siteInstaller = siteInstaller;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.InstallerFactory
 * JD-Core Version:    0.6.0
 */