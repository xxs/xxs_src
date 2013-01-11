/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SiteThemeAction extends WWAction
/*     */ {
/*     */   private List<Theme> listTheme;
/*     */   private Theme theme;
/*     */   private IThemeManager themeManager;
/*     */   private EopSite eopSite;
/*     */   private ISiteManager siteManager;
/*     */   private String previewpath;
/*     */   private String previewBasePath;
/*     */   private Integer themeid;
/*     */ 
/*     */   public String execute()
/*     */     throws Exception
/*     */   {
/*  35 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  36 */     String contextPath = EopContext.getContext().getContextPath();
/*  37 */     this.previewBasePath = (contextPath + "/themes/");
/*  38 */     this.theme = this.themeManager.getTheme(site.getThemeid());
/*  39 */     this.listTheme = this.themeManager.list();
/*  40 */     this.previewpath = (this.previewBasePath + this.theme.getPath() + "/preview.png");
/*  41 */     return "success";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  47 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save() {
/*  51 */     this.msgs.add("模板创建成功");
/*  52 */     this.urls.put("品牌列表", "siteTheme.do");
/*  53 */     this.themeManager.addBlank(this.theme);
/*  54 */     return "message";
/*     */   }
/*     */ 
/*     */   public String change() throws Exception {
/*  58 */     this.siteManager.changeTheme(this.themeid);
/*  59 */     return execute();
/*     */   }
/*     */ 
/*     */   public List<Theme> getListTheme() {
/*  63 */     return this.listTheme;
/*     */   }
/*     */ 
/*     */   public void setListTheme(List<Theme> listTheme) {
/*  67 */     this.listTheme = listTheme;
/*     */   }
/*     */ 
/*     */   public Theme getTheme() {
/*  71 */     return this.theme;
/*     */   }
/*     */ 
/*     */   public void setTheme(Theme theme) {
/*  75 */     this.theme = theme;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/*  79 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/*  83 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public EopSite getEopSite() {
/*  87 */     return this.eopSite;
/*     */   }
/*     */ 
/*     */   public void setEopSite(EopSite eopSite) {
/*  91 */     this.eopSite = eopSite;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/*  95 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/*  99 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public String getPreviewpath() {
/* 103 */     return this.previewpath;
/*     */   }
/*     */ 
/*     */   public void setPreviewpath(String previewpath) {
/* 107 */     this.previewpath = previewpath;
/*     */   }
/*     */ 
/*     */   public String getPreviewBasePath() {
/* 111 */     return this.previewBasePath;
/*     */   }
/*     */ 
/*     */   public void setPreviewBasePath(String previewBasePath) {
/* 115 */     this.previewBasePath = previewBasePath;
/*     */   }
/*     */ 
/*     */   public Integer getThemeid() {
/* 119 */     return this.themeid;
/*     */   }
/*     */ 
/*     */   public void setThemeid(Integer themeid) {
/* 123 */     this.themeid = themeid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.SiteThemeAction
 * JD-Core Version:    0.6.0
 */