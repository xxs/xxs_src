/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.resource.IAdminThemeManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.AdminTheme;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SiteAdminThemeAction extends WWAction
/*     */ {
/*     */   private IAdminThemeManager adminThemeManager;
/*     */   private ISiteManager siteManager;
/*     */   private List<AdminTheme> listTheme;
/*     */   private AdminTheme adminTheme;
/*     */   private EopSite eopSite;
/*     */   private String previewpath;
/*     */   private String previewBasePath;
/*     */   private Integer themeid;
/*     */ 
/*     */   public String execute()
/*     */     throws Exception
/*     */   {
/*  38 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  39 */     String contextPath = EopContext.getContext().getContextPath();
/*  40 */     this.previewBasePath = (EopSetting.IMG_SERVER_DOMAIN + contextPath + "/adminthemes/");
/*  41 */     this.adminTheme = this.adminThemeManager.get(site.getAdminthemeid());
/*  42 */     this.listTheme = this.adminThemeManager.list();
/*  43 */     this.previewpath = (this.previewBasePath + this.adminTheme.getPath() + "/preview.png");
/*  44 */     return "success";
/*     */   }
/*     */ 
/*     */   public String change() throws Exception {
/*  48 */     this.siteManager.changeAdminTheme(this.themeid);
/*  49 */     return execute();
/*     */   }
/*     */ 
/*     */   public EopSite getEopSite() {
/*  53 */     return this.eopSite;
/*     */   }
/*     */ 
/*     */   public void setEopSite(EopSite eopSite) {
/*  57 */     this.eopSite = eopSite;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/*  61 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/*  65 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public String getPreviewpath() {
/*  69 */     return this.previewpath;
/*     */   }
/*     */ 
/*     */   public void setPreviewpath(String previewpath) {
/*  73 */     this.previewpath = previewpath;
/*     */   }
/*     */ 
/*     */   public String getPreviewBasePath() {
/*  77 */     return this.previewBasePath;
/*     */   }
/*     */ 
/*     */   public void setPreviewBasePath(String previewBasePath) {
/*  81 */     this.previewBasePath = previewBasePath;
/*     */   }
/*     */ 
/*     */   public Integer getThemeid() {
/*  85 */     return this.themeid;
/*     */   }
/*     */ 
/*     */   public void setThemeid(Integer themeid) {
/*  89 */     this.themeid = themeid;
/*     */   }
/*     */ 
/*     */   public List<AdminTheme> getListTheme() {
/*  93 */     return this.listTheme;
/*     */   }
/*     */ 
/*     */   public void setListTheme(List<AdminTheme> listTheme) {
/*  97 */     this.listTheme = listTheme;
/*     */   }
/*     */ 
/*     */   public AdminTheme getAdminTheme() {
/* 101 */     return this.adminTheme;
/*     */   }
/*     */ 
/*     */   public void setAdminTheme(AdminTheme adminTheme) {
/* 105 */     this.adminTheme = adminTheme;
/*     */   }
/*     */ 
/*     */   public IAdminThemeManager getAdminThemeManager() {
/* 109 */     return this.adminThemeManager;
/*     */   }
/*     */ 
/*     */   public void setAdminThemeManager(IAdminThemeManager adminThemeManager) {
/* 113 */     this.adminThemeManager = adminThemeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.SiteAdminThemeAction
 * JD-Core Version:    0.6.0
 */