/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.MultiSite;
/*     */ import com.enation.app.base.core.service.IMultiSiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MultiSiteAction extends WWAction
/*     */ {
/*     */   private IMultiSiteManager multiSiteManager;
/*     */   private EopSite eopSite;
/*     */   private MultiSite site;
/*     */   private String parentname;
/*     */   private int siteid;
/*     */   private int multi_site;
/*     */   private IThemeManager themeManager;
/*     */   private List<Theme> themeList;
/*     */   private String previewBasePath;
/*     */   private String domain;
/*     */   private List<MultiSite> siteList;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  41 */     this.eopSite = EopContext.getContext().getCurrentSite();
/*     */ 
/*  43 */     this.siteList = this.multiSiteManager.list();
/*  44 */     return "main";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*     */     try
/*     */     {
/*  53 */       if (this.multi_site == 1) {
/*  54 */         this.multiSiteManager.open(this.domain);
/*  55 */         this.json = "{result:1,message:'多站点功能开启成功'}";
/*     */       } else {
/*  57 */         this.multiSiteManager.close();
/*  58 */         this.json = "{result:1,message:'多站点功能关闭成功'}";
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e) {
/*  62 */       this.logger.error(e.getMessage(), e.fillInStackTrace());
/*  63 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  65 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String listJson()
/*     */   {
/*     */     try
/*     */     {
/*  76 */       JSONArray jsonArray1 = JSONArray.fromObject(this.multiSiteManager.list());
/*  77 */       this.json = ("{'result':'0','data':" + jsonArray1.toString() + "}");
/*     */     } catch (Exception e) {
/*  79 */       this.json = "{'result':'1'}";
/*     */     }
/*  81 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list() {
/*  85 */     this.siteList = this.multiSiteManager.list();
/*  86 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  90 */     this.themeList = this.themeManager.list(0);
/*  91 */     String contextPath = EopContext.getContext().getContextPath();
/*  92 */     this.previewBasePath = (EopSetting.IMG_SERVER_DOMAIN + contextPath + "/themes/");
/*  93 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  97 */     this.site = this.multiSiteManager.get(this.siteid);
/*  98 */     if (this.site.getParentid().intValue() != 0)
/*  99 */       this.parentname = this.multiSiteManager.get(this.site.getParentid().intValue()).getName();
/*     */     else
/* 101 */       this.parentname = "顶级站点";
/* 102 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String addSave() {
/*     */     try {
/* 107 */       this.multiSiteManager.add(this.site);
/* 108 */       this.msgs.add("新增子站点成功");
/*     */     }
/*     */     catch (RuntimeException e) {
/* 111 */       this.msgs.add("操作失败：" + e.getMessage());
/*     */     }
/* 113 */     this.urls.put("多站点管理", "multiSite.do");
/* 114 */     return "message";
/*     */   }
/*     */ 
/*     */   public String editSave() {
/* 118 */     this.multiSiteManager.update(this.site);
/* 119 */     this.msgs.add("编辑子站点成功");
/* 120 */     this.urls.put("多站点管理", "multiSite.do");
/* 121 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/* 126 */       this.multiSiteManager.delete(this.siteid);
/* 127 */       this.msgs.add("站点成功删除");
/*     */     }
/*     */     catch (Exception e) {
/* 130 */       this.msgs.add("站点删除失败");
/*     */     }
/* 132 */     this.urls.put("多站点管理", "multiSite.do");
/* 133 */     return "message";
/*     */   }
/*     */ 
/*     */   public IMultiSiteManager getMultiSiteManager() {
/* 137 */     return this.multiSiteManager;
/*     */   }
/*     */ 
/*     */   public void setMultiSiteManager(IMultiSiteManager multiSiteManager) {
/* 141 */     this.multiSiteManager = multiSiteManager;
/*     */   }
/*     */ 
/*     */   public MultiSite getSite() {
/* 145 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(MultiSite site) {
/* 149 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public int getSiteid() {
/* 153 */     return this.siteid;
/*     */   }
/*     */ 
/*     */   public void setSiteid(int siteid) {
/* 157 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/* 161 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/* 165 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public List<Theme> getThemeList() {
/* 169 */     return this.themeList;
/*     */   }
/*     */ 
/*     */   public void setThemeList(List<Theme> themeList) {
/* 173 */     this.themeList = themeList;
/*     */   }
/*     */ 
/*     */   public String getPreviewBasePath() {
/* 177 */     return this.previewBasePath;
/*     */   }
/*     */ 
/*     */   public void setPreviewBasePath(String previewBasePath) {
/* 181 */     this.previewBasePath = previewBasePath;
/*     */   }
/*     */ 
/*     */   public List<MultiSite> getSiteList() {
/* 185 */     return this.siteList;
/*     */   }
/*     */ 
/*     */   public void setSiteList(List<MultiSite> siteList) {
/* 189 */     this.siteList = siteList;
/*     */   }
/*     */ 
/*     */   public EopSite getEopSite() {
/* 193 */     return this.eopSite;
/*     */   }
/*     */ 
/*     */   public void setEopSite(EopSite eopSite) {
/* 197 */     this.eopSite = eopSite;
/*     */   }
/*     */ 
/*     */   public String getDomain() {
/* 201 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(String domain) {
/* 205 */     this.domain = domain;
/*     */   }
/*     */ 
/*     */   public int getMulti_site() {
/* 209 */     return this.multi_site;
/*     */   }
/*     */ 
/*     */   public void setMulti_site(int multiSite) {
/* 213 */     this.multi_site = multiSite;
/*     */   }
/*     */ 
/*     */   public String getParentname() {
/* 217 */     return this.parentname;
/*     */   }
/*     */ 
/*     */   public void setParentname(String parentname) {
/* 221 */     this.parentname = parentname;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.MultiSiteAction
 * JD-Core Version:    0.6.0
 */