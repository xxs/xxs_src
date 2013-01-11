/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.MultiSite;
/*     */ import com.enation.app.base.core.service.IMultiSiteManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopSiteDomain;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class MultiSiteManager extends BaseSupport<MultiSite>
/*     */   implements IMultiSiteManager
/*     */ {
/*     */   private IThemeManager themeManager;
/*     */   private ISiteManager siteManager;
/*     */ 
/*     */   public IThemeManager getThemeManager()
/*     */   {
/*  30 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/*  34 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void open(String domain)
/*     */   {
/*  41 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  42 */     if (site.getMulti_site().intValue() == 1) return;
/*     */ 
/*  46 */     Integer siteid = site.getId();
/*  47 */     String sql = "update eop_site set multi_site=1 where id=?";
/*  48 */     this.daoSupport.execute(sql, new Object[] { siteid });
/*  49 */     site.setMulti_site(Integer.valueOf(1));
/*     */ 
/*  52 */     sql = "select count(0) from site";
/*  53 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*  54 */     if (count > 0) {
/*  55 */       sql = "update site set domain=? where code=?";
/*  56 */       this.baseDaoSupport.execute(sql, new Object[] { domain, Integer.valueOf(100000) });
/*  57 */       return;
/*     */     }
/*     */ 
/*  64 */     MultiSite mainChildSite = new MultiSite();
/*  65 */     mainChildSite.setCode(Integer.valueOf(100000));
/*  66 */     mainChildSite.setSitelevel(Integer.valueOf(1));
/*  67 */     mainChildSite.setDomain(domain);
/*  68 */     mainChildSite.setName(site.getSitename());
/*  69 */     mainChildSite.setParentid(Integer.valueOf(0));
/*  70 */     mainChildSite.setThemeid(site.getThemeid());
/*  71 */     this.baseDaoSupport.insert("site", mainChildSite);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void close()
/*     */   {
/*  78 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  79 */     Integer siteid = site.getId();
/*  80 */     String sql = "update eop_site set multi_site=0 where id=?";
/*  81 */     this.daoSupport.execute(sql, new Object[] { siteid });
/*     */ 
/*  83 */     site.setMulti_site(Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   private int createCode(int maxcode, int level)
/*     */   {
/*  88 */     if (level == 1) {
/*  89 */       return maxcode + 100000;
/*     */     }
/*     */ 
/*  92 */     if (level == 2) {
/*  93 */       return maxcode + 1000;
/*     */     }
/*     */ 
/*  97 */     if (level == 3) {
/*  98 */       return maxcode + 10;
/*     */     }
/*     */ 
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(MultiSite site)
/*     */   {
/* 109 */     MultiSite parent = get(site.getParentid().intValue());
/*     */ 
/* 112 */     String sql = "select max(code) code from site where parentid=? ";
/* 113 */     int maxcode = this.baseDaoSupport.queryForInt(sql, new Object[] { site.getParentid() });
/* 114 */     maxcode = maxcode == 0 ? (maxcode = parent.getCode().intValue()) : maxcode;
/* 115 */     int level = parent.getSitelevel().intValue() + 1;
/* 116 */     site.setCode(Integer.valueOf(createCode(maxcode, level)));
/* 117 */     site.setSitelevel(Integer.valueOf(level));
/*     */ 
/* 119 */     this.baseDaoSupport.insert("site", site);
/* 120 */     int siteid = this.baseDaoSupport.getLastId("site");
/*     */ 
/* 122 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 123 */     EopSiteDomain eopSiteDomain = new EopSiteDomain();
/* 124 */     eopSiteDomain.setUserid(userid);
/* 125 */     eopSiteDomain.setDomain(site.getDomain());
/* 126 */     eopSiteDomain.setSiteid(EopContext.getContext().getCurrentSite().getId());
/*     */ 
/* 128 */     this.siteManager.addDomain(eopSiteDomain);
/*     */     try
/*     */     {
/* 134 */       site.setSiteid(Integer.valueOf(siteid));
/* 135 */       Theme theme = this.themeManager.getTheme(site.getThemeid());
/* 136 */       String contextPath = EopContext.getContext().getContextPath();
/*     */ 
/* 139 */       String basePath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath();
/*     */ 
/* 141 */       String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath() + "_" + siteid;
/*     */ 
/* 143 */       FileUtil.copyFolder(basePath, targetPath);
/*     */ 
/* 145 */       basePath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath();
/*     */ 
/* 147 */       targetPath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath() + "_" + siteid;
/*     */ 
/* 149 */       FileUtil.copyFolder(basePath, targetPath);
/*     */ 
/* 151 */       theme.setPath(theme.getPath() + "_" + siteid);
/* 152 */       theme.setSiteid(Integer.valueOf(siteid));
/* 153 */       theme.setId(null);
/* 154 */       this.baseDaoSupport.insert("theme", theme);
/* 155 */       int themeid = this.baseDaoSupport.getLastId("theme");
/* 156 */       site.setThemeid(Integer.valueOf(themeid));
/* 157 */       update(site);
/*     */     } catch (Exception e) {
/* 159 */       e.printStackTrace();
/* 160 */       throw new RuntimeException("创建主题出错");
/*     */     }
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(int id) {
/* 167 */     MultiSite childsite = get(id);
/* 168 */     List list = this.themeManager.list(id);
/* 169 */     String contextPath = EopContext.getContext().getContextPath();
/* 170 */     for (Theme theme : list)
/*     */     {
/* 172 */       String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath() + "_" + id;
/*     */ 
/* 174 */       FileUtil.removeFile(new File(targetPath));
/*     */ 
/* 177 */       targetPath = EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath() + "_" + id;
/*     */ 
/* 179 */       FileUtil.removeFile(new File(targetPath));
/*     */     }
/*     */ 
/* 184 */     this.siteManager.deleteDomain(childsite.getDomain());
/*     */ 
/* 187 */     this.baseDaoSupport.execute("delete from theme where siteid = ?", new Object[] { Integer.valueOf(id) });
/*     */ 
/* 190 */     this.baseDaoSupport.execute("delete from site where siteid = ?", new Object[] { Integer.valueOf(id) });
/*     */   }
/*     */ 
/*     */   public List list()
/*     */   {
/* 196 */     List list = this.baseDaoSupport.queryForList("select * from site ", new Object[0]);
/* 197 */     List siteList = new ArrayList();
/* 198 */     for (Map site : list) {
/* 199 */       Long parentid = (Long)site.get("parentid");
/* 200 */       if (parentid.intValue() == 0) {
/* 201 */         putChildren(site, list);
/* 202 */         siteList.add(site);
/*     */       }
/*     */     }
/* 205 */     return siteList;
/*     */   }
/*     */ 
/*     */   public void putChildren(Map site, List<Map> sitelist) {
/* 209 */     List children = new ArrayList();
/* 210 */     for (Map child : sitelist) {
/* 211 */       Long parentid = (Long)child.get("parentid");
/* 212 */       Long siteid = (Long)site.get("siteid");
/* 213 */       if (parentid.compareTo(siteid) == 0) {
/* 214 */         putChildren(child, sitelist);
/* 215 */         children.add(child);
/*     */       }
/*     */     }
/* 218 */     site.put("children", children);
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void update(MultiSite site) {
/* 223 */     MultiSite site_old = get(site.getSiteid().intValue());
/* 224 */     String domain = site_old.getDomain();
/* 225 */     this.siteManager.editDomain(domain, site.getDomain());
/*     */ 
/* 227 */     this.baseDaoSupport.update("site", site, "siteid = " + site.getSiteid());
/*     */   }
/*     */ 
/*     */   public MultiSite get(int id)
/*     */   {
/* 233 */     return (MultiSite)this.baseDaoSupport.queryForObject("select * from site where siteid = ?", MultiSite.class, new Object[] { Integer.valueOf(id) });
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager()
/*     */   {
/* 238 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 242 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public MultiSite get(String domain) {
/* 246 */     return (MultiSite)this.baseDaoSupport.queryForObject("select * from site where domain = ?", MultiSite.class, new Object[] { domain });
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.MultiSiteManager
 * JD-Core Version:    0.6.0
 */