/*     */ package com.enation.eop.resource.impl.cache;
/*     */ 
/*     */ import com.enation.eop.processor.core.EopException;
/*     */ import com.enation.eop.resource.IDomainManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.Dns;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopSiteDomain;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SiteCacheProxy extends AbstractCacheProxy<EopSite>
/*     */   implements ISiteManager
/*     */ {
/*     */   private ISiteManager siteManager;
/*     */   private IDomainManager domainManager;
/*     */   private static final String SITE_LIST_CACHE_KEY = "eopDNS";
/*     */ 
/*     */   public SiteCacheProxy(ISiteManager siteManager)
/*     */   {
/*  32 */     super("siteCache");
/*  33 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public int addDomain(EopSiteDomain eopSiteDomain)
/*     */   {
/*  38 */     EopSite site = this.siteManager.get(eopSiteDomain.getSiteid());
/*  39 */     this.cache.put(eopSiteDomain.getDomain(), site);
/*  40 */     return this.siteManager.addDomain(eopSiteDomain);
/*     */   }
/*     */ 
/*     */   public void deleteDomain(String domain)
/*     */   {
/*  45 */     this.siteManager.deleteDomain(domain);
/*  46 */     this.cache.remove(domain);
/*     */   }
/*     */ 
/*     */   public Integer add(EopSite newSite, String domain)
/*     */   {
/*  51 */     EopSite site = (EopSite)this.cache.get(domain);
/*  52 */     if (site == null) {
/*  53 */       return this.siteManager.add(newSite, domain);
/*     */     }
/*  55 */     throw new EopException("域名:" + domain + " 已经存在！");
/*     */   }
/*     */ 
/*     */   public void deleteDomain(Integer domainid)
/*     */   {
/*  60 */     EopSiteDomain siteDomain = this.domainManager.get(domainid);
/*  61 */     this.siteManager.deleteDomain(domainid);
/*  62 */     this.cache.remove(siteDomain.getDomain());
/*     */   }
/*     */ 
/*     */   public void delete(Integer id)
/*     */   {
/*  68 */     List list = this.domainManager.listSiteDomain(id);
/*  69 */     for (EopSiteDomain siteDomain : list) {
/*  70 */       this.cache.remove(siteDomain.getDomain());
/*     */     }
/*  72 */     this.siteManager.delete(id);
/*     */   }
/*     */ 
/*     */   public List<Dns> getDnsList()
/*     */   {
/*  86 */     if (this.logger.isDebugEnabled()) {
/*  87 */       this.logger.debug("query all DNS form datagae,then put them in cache ");
/*     */     }
/*  89 */     List dnsList = this.siteManager.getDnsList();
/*  90 */     for (Dns dns : dnsList) {
/*  91 */       this.cache.put(dns.getDomain(), dns.getSite());
/*     */     }
/*  93 */     if (this.logger.isDebugEnabled()) {
/*  94 */       this.logger.debug("DNS put in cache complete!");
/*     */     }
/*  96 */     return dnsList;
/*     */   }
/*     */ 
/*     */   public EopSite get(Integer id)
/*     */   {
/* 104 */     return this.siteManager.get(id);
/*     */   }
/*     */ 
/*     */   public void editDomain(String domainOld, String domainNew) {
/* 108 */     EopSite site = this.siteManager.get(domainOld);
/* 109 */     this.siteManager.editDomain(domainOld, domainNew);
/* 110 */     this.cache.remove(domainOld);
/* 111 */     this.cache.put(domainNew, site);
/*     */   }
/*     */ 
/*     */   public EopSite get(String domain)
/*     */   {
/* 125 */     EopSite site = (EopSite)this.cache.get(domain);
/* 126 */     site = null;
/* 127 */     if (site == null)
/*     */     {
/* 133 */       site = this.siteManager.get(domain);
/* 134 */       this.cache.put(domain, site);
/*     */     }
/*     */ 
/* 137 */     if (site == null)
/*     */     {
/* 141 */       throw new EopException("域名" + domain + "不存在");
/*     */     }
/*     */ 
/* 150 */     return site;
/*     */   }
/*     */ 
/*     */   public Boolean checkInDomain(String domain)
/*     */   {
/* 155 */     return this.siteManager.checkInDomain(domain);
/*     */   }
/*     */ 
/*     */   public Page list(String keyword, int pageNo, int pageSize)
/*     */   {
/* 160 */     Page page = this.siteManager.list(keyword, pageNo, pageSize);
/* 161 */     List listsite = (List)(List)page.getResult();
/*     */ 
/* 163 */     List dnsList = getDnsList();
/* 164 */     for (Map site : listsite) {
/* 165 */       List domainList = new ArrayList();
/*     */ 
/* 169 */       for (Dns siteDomain : dnsList) {
/* 170 */         if (site.get("id").toString().equals(siteDomain.getSite().getId().toString()))
/*     */         {
/* 172 */           domainList.add(siteDomain);
/*     */         }
/*     */       }
/* 175 */       site.put("eopSiteDomainList", domainList);
/*     */     }
/* 177 */     return page;
/*     */   }
/*     */ 
/*     */   public Page list(int pageNo, int pageSize, String order, String search)
/*     */   {
/* 182 */     return this.siteManager.list(pageNo, pageSize, order, search);
/*     */   }
/*     */ 
/*     */   public void edit(EopSite eopSite)
/*     */   {
/* 187 */     this.siteManager.edit(eopSite);
/* 188 */     EopSite site = this.siteManager.get(eopSite.getId());
/* 189 */     refreshCache(site);
/*     */   }
/*     */ 
/*     */   private void refreshCache(EopSite site)
/*     */   {
/* 195 */     List list = this.domainManager.listSiteDomain();
/* 196 */     for (EopSiteDomain siteDomain : list) {
/* 197 */       this.cache.put(siteDomain.getDomain(), site);
/*     */     }
/* 199 */     EopContext.getContext().setCurrentSite(site);
/*     */   }
/*     */ 
/*     */   public void updatePoint(Integer id, int point, int sitestate) {
/* 203 */     this.siteManager.updatePoint(id, point, sitestate);
/*     */ 
/* 206 */     EopSite site = get(id);
/* 207 */     List list = this.domainManager.listSiteDomain(id);
/* 208 */     if ((list != null) && (!list.isEmpty())) {
/* 209 */       String domain = ((EopSiteDomain)list.get(0)).getDomain();
/* 210 */       site = get(domain);
/* 211 */       if (site != null) {
/* 212 */         if (point == -1)
/* 213 */           site.setPoint(point);
/*     */         else {
/* 215 */           site.setPoint(site.getPoint() + point);
/*     */         }
/*     */ 
/* 218 */         site.setSitestate(sitestate);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getPoint(Integer id, int point)
/*     */   {
/* 227 */     int result = this.siteManager.getPoint(id, point);
/*     */ 
/* 229 */     if (result == 1)
/*     */     {
/* 231 */       EopSite site = get(id);
/* 232 */       List list = this.domainManager.listSiteDomain(id);
/* 233 */       if ((list != null) && (!list.isEmpty())) {
/* 234 */         String domain = ((EopSiteDomain)list.get(0)).getDomain();
/* 235 */         site = get(domain);
/* 236 */         if (site != null) {
/* 237 */           if (point == -1)
/* 238 */             site.setPoint(point);
/*     */           else {
/* 240 */             site.setPoint(site.getPoint() + point);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 245 */     return result;
/*     */   }
/*     */ 
/*     */   public List<EopApp> getSiteApps() {
/* 249 */     return this.siteManager.getSiteApps();
/*     */   }
/*     */ 
/*     */   public void setSiteProduct(Integer userid, Integer siteid, String productid)
/*     */   {
/* 254 */     this.siteManager.setSiteProduct(userid, siteid, productid);
/*     */   }
/*     */ 
/*     */   public void changeAdminTheme(Integer themeid)
/*     */   {
/* 259 */     this.siteManager.changeAdminTheme(themeid);
/*     */   }
/*     */ 
/*     */   public void changeTheme(Integer themeid)
/*     */   {
/* 264 */     this.siteManager.changeTheme(themeid);
/*     */   }
/*     */ 
/*     */   public List listDomain(Integer userid, Integer siteid)
/*     */   {
/* 270 */     return this.siteManager.listDomain(userid, siteid);
/*     */   }
/*     */ 
/*     */   public IDomainManager getDomainManager() {
/* 274 */     return this.domainManager;
/*     */   }
/*     */ 
/*     */   public void setDomainManager(IDomainManager domainManager) {
/* 278 */     this.domainManager = domainManager;
/*     */   }
/*     */ 
/*     */   public void initData()
/*     */   {
/* 283 */     this.siteManager.initData();
/*     */   }
/*     */ 
/*     */   public List list()
/*     */   {
/* 288 */     return this.siteManager.list();
/*     */   }
/*     */ 
/*     */   public List list(Integer userid)
/*     */   {
/* 293 */     return this.siteManager.list(userid);
/*     */   }
/*     */ 
/*     */   public Integer add(EopUser user, EopSite site, String domain)
/*     */   {
/* 299 */     return this.siteManager.add(user, site, domain);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.cache.SiteCacheProxy
 * JD-Core Version:    0.6.0
 */