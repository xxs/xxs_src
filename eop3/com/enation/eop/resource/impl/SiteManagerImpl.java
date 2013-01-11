/*     */ package com.enation.eop.resource.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.solution.ISetupLoader;
/*     */ import com.enation.eop.resource.IAppManager;
/*     */ import com.enation.eop.resource.IDomainManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.Dns;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopSiteDomain;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.StringMapper;
/*     */ import com.enation.framework.resource.ResourceStateManager;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class SiteManagerImpl
/*     */   implements ISiteManager
/*     */ {
/*     */   private IDaoSupport<EopSite> daoSupport;
/*     */   private IDomainManager domainManager;
/*     */   private IThemeManager themeManager;
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IAppManager appManager;
/*     */   private ISetupLoader setupLoader;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int addDomain(EopSiteDomain eopSiteDomain)
/*     */   {
/*  66 */     if (checkInDomain(eopSiteDomain.getDomain()).booleanValue()) {
/*  67 */       throw new IllegalArgumentException("域名[" + eopSiteDomain.getDomain() + "]已存在！");
/*     */     }
/*     */ 
/*  70 */     this.daoSupport.insert("eop_sitedomain", eopSiteDomain);
/*  71 */     return this.daoSupport.getLastId("eop_sitedomain");
/*     */   }
/*     */ 
/*     */   public Boolean checkInDomain(String domain) {
/*  75 */     String sql = "select count(0) from eop_sitedomain where domain = ?";
/*  76 */     int count = this.daoSupport.queryForInt(sql, new Object[] { domain });
/*  77 */     return Boolean.valueOf(count != 0);
/*     */   }
/*     */ 
/*     */   public void deleteDomain(String domain) {
/*  81 */     String sql = "delete from eop_sitedomain where domain=?";
/*  82 */     this.daoSupport.execute(sql, new Object[] { domain });
/*     */   }
/*     */ 
/*     */   public Integer add(EopUser user, EopSite site, String domain) {
/*  86 */     int userid = user.getId().intValue();
/*  87 */     site.setUserid(Integer.valueOf(userid));
/*     */ 
/*  89 */     if (site.getIcofile() == null) {
/*  90 */       site.setIcofile(EopSetting.IMG_SERVER_DOMAIN + "/images/default/favicon.ico");
/*     */     }
/*  92 */     if (site.getLogofile() == null) {
/*  93 */       site.setLogofile(EopSetting.IMG_SERVER_DOMAIN + "/images/default/logo.gif");
/*     */     }
/*     */ 
/*  96 */     site.setPoint(1000);
/*     */ 
/*  99 */     site.setCreatetime(Long.valueOf(DateUtil.getDateline()));
/* 100 */     site.setLastlogin(Long.valueOf(0L));
/* 101 */     site.setLastgetpoint(DateUtil.getDateline());
/*     */ 
/* 104 */     this.daoSupport.insert("eop_site", site);
/* 105 */     Integer siteid = Integer.valueOf(this.daoSupport.getLastId("eop_site"));
/*     */ 
/* 107 */     EopSiteDomain eopSiteDomain = new EopSiteDomain();
/* 108 */     eopSiteDomain.setDomain(domain);
/* 109 */     eopSiteDomain.setSiteid(siteid);
/* 110 */     eopSiteDomain.setUserid(Integer.valueOf(userid));
/*     */ 
/* 113 */     addDomain(eopSiteDomain);
/*     */ 
/* 115 */     return siteid;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer add(EopSite site, String domain) {
/* 121 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 123 */     EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
/*     */ 
/* 125 */     return add(user, site, domain);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void deleteDomain(Integer domainid) {
/* 131 */     EopSiteDomain domain = this.domainManager.get(domainid);
/* 132 */     UserUtil.validUser(domain.getUserid());
/* 133 */     int domainCount = this.domainManager.getDomainCount(domain.getSiteid());
/* 134 */     if (domainCount <= 1) {
/* 135 */       throw new RuntimeException("此站点只有一个域名不可删除，删除后将不可访问");
/*     */     }
/* 137 */     String sql = "delete from eop_sitedomain where id=?";
/* 138 */     this.daoSupport.execute(sql, new Object[] { domainid });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer id) {
/* 144 */     EopSite site = get(id);
/*     */ 
/* 146 */     Integer userid = site.getUserid();
/* 147 */     Integer siteid = site.getId();
/*     */ 
/* 150 */     String sql = "show tables like '%_" + userid + "_" + siteid + "'";
/* 151 */     List tableList = this.daoSupport.queryForList(sql, new StringMapper(), new Object[0]);
/* 152 */     for (String tbName : tableList) {
/* 153 */       sql = "drop table if exists " + tbName;
/* 154 */       this.daoSupport.execute(sql, new Object[0]);
/*     */     }
/*     */ 
/* 158 */     sql = "delete from eop_sitedomain where siteid = ?";
/* 159 */     this.daoSupport.execute(sql, new Object[] { id });
/* 160 */     sql = "delete from  eop_site  where id = ?";
/* 161 */     this.daoSupport.execute(sql, new Object[] { id });
/*     */ 
/* 164 */     String userTplFile = EopSetting.EOP_PATH + "/user/" + userid + "/" + siteid;
/*     */ 
/* 166 */     String userStyleFile = EopSetting.IMG_SERVER_PATH + "/user/" + userid + "/" + siteid;
/*     */ 
/* 168 */     FileUtil.delete(userTplFile);
/* 169 */     FileUtil.delete(userStyleFile);
/*     */   }
/*     */ 
/*     */   public List getDnsList()
/*     */   {
/* 176 */     String sql = "select a.domain as domain,a.siteid as siteid, b.* from eop_sitedomain a left join eop_site b on b.id = a.siteid";
/* 177 */     return this.daoSupport.queryForList(sql, new DnsMapper(null), new Object[0]);
/*     */   }
/*     */ 
/*     */   private String getContext(EopSite site)
/*     */   {
/* 289 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 290 */       return EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.getUserid() + "/" + site.getId();
/*     */     }
/*     */ 
/* 293 */     return EopSetting.IMG_SERVER_DOMAIN;
/*     */   }
/*     */ 
/*     */   public EopSite get(Integer id)
/*     */   {
/* 298 */     String sql = "select * from eop_site where  id = ?";
/* 299 */     EopSite site = (EopSite)this.daoSupport.queryForObject(sql, EopSite.class, new Object[] { id });
/* 300 */     String icofile = site.getIcofile();
/* 301 */     if (icofile != null)
/* 302 */       icofile = UploadUtil.replacePath(icofile);
/* 303 */     site.setIcofile(icofile);
/*     */ 
/* 305 */     String logofile = site.getLogofile();
/* 306 */     if (logofile != null) {
/* 307 */       logofile = logofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/* 309 */     site.setLogofile(logofile);
/*     */ 
/* 311 */     String bklogofile = site.getBklogofile();
/* 312 */     if (bklogofile != null) {
/* 313 */       bklogofile = bklogofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/*     */ 
/* 316 */     site.setBklogofile(bklogofile);
/*     */ 
/* 318 */     String bkloginpicfile = site.getBkloginpicfile();
/* 319 */     if (bkloginpicfile != null) {
/* 320 */       bkloginpicfile = bkloginpicfile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/*     */ 
/* 323 */     site.setBkloginpicfile(bkloginpicfile);
/* 324 */     return site;
/*     */   }
/*     */ 
/*     */   public EopSite get(String domain)
/*     */   {
/* 329 */     String sql = "select a.* from eop_site a join eop_sitedomain b on b.siteid = a.id   and b.domain= ?";
/* 330 */     List sitelist = this.daoSupport.queryForList(sql, EopSite.class, new Object[] { domain });
/*     */ 
/* 332 */     if ((sitelist == null) || (sitelist.isEmpty()))
/* 333 */       return null;
/* 334 */     EopSite site = (EopSite)sitelist.get(0);
/* 335 */     String icofile = site.getIcofile();
/* 336 */     if (icofile != null)
/*     */     {
/* 338 */       if ("2".equals(EopSetting.RUNMODE))
/* 339 */         icofile = icofile.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.getUserid() + "/" + site.getId());
/*     */       else {
/* 341 */         icofile = icofile.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN);
/*     */       }
/*     */     }
/* 344 */     site.setIcofile(icofile);
/*     */ 
/* 346 */     String logofile = site.getLogofile();
/*     */ 
/* 348 */     if (logofile != null) {
/* 349 */       logofile = logofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/*     */ 
/* 358 */     site.setLogofile(logofile);
/* 359 */     String bklogofile = site.getBklogofile();
/* 360 */     if (bklogofile != null) {
/* 361 */       bklogofile = bklogofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/*     */ 
/* 364 */     site.setBklogofile(bklogofile);
/*     */ 
/* 366 */     String bkloginpicfile = site.getBkloginpicfile();
/* 367 */     if (bkloginpicfile != null) {
/* 368 */       bkloginpicfile = bkloginpicfile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */     }
/*     */ 
/* 371 */     site.setBkloginpicfile(bkloginpicfile);
/* 372 */     return site;
/*     */   }
/*     */ 
/*     */   public Page list(String keyword, int pageNo, int pageSize)
/*     */   {
/* 388 */     String sql = "select s.id,s.sitename,s.point,s.createtime,s.sitestate,s.logincount,s.lastlogin,u.username from eop_site s,eop_user u where s.userid=u.id";
/*     */ 
/* 392 */     if (!StringUtil.isEmpty(keyword)) {
/* 393 */       sql = sql + " and (s.sitename like '%" + keyword + "%' ";
/* 394 */       sql = sql + " or u.username like '%" + keyword + "%'";
/* 395 */       sql = sql + " or s.id in(select siteid from eop_sitedomain where domain like '%" + keyword + "%') )";
/*     */     }
/*     */ 
/* 398 */     sql = sql + " order by s.createtime desc";
/*     */ 
/* 400 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page list(int pageNo, int pageSize, String order, String search) {
/* 404 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 405 */     List listdomain = this.domainManager.listUserDomain();
/* 406 */     if (search == null)
/* 407 */       search = "";
/*     */     else
/* 409 */       search = " and sitename like '%" + search + "%'";
/* 410 */     if (order == null)
/* 411 */       order = "";
/*     */     else {
/* 413 */       order = " order by " + order.replace(":", " ");
/*     */     }
/* 415 */     Page page = this.daoSupport.queryForPage("select * from eop_site where deleteflag = 0 and userid = " + userid + search + order, pageNo, pageSize, new Object[0]);
/*     */ 
/* 419 */     List listsite = (List)(List)page.getResult();
/*     */ 
/* 421 */     for (Map site : listsite) {
/* 422 */       List domainList = new ArrayList();
/* 423 */       String logofile = site.get("logofile").toString();
/* 424 */       if (logofile != null) {
/* 425 */         logofile = logofile.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.get("userid").toString() + "/" + site.get("id").toString());
/*     */       }
/*     */ 
/* 429 */       site.put("logofile", logofile);
/* 430 */       for (EopSiteDomain siteDomain : listdomain) {
/* 431 */         if (site.get("id").toString().equals(siteDomain.getSiteid().toString()))
/*     */         {
/* 433 */           domainList.add(siteDomain);
/*     */         }
/*     */       }
/* 436 */       site.put("eopSiteDomainList", domainList);
/*     */     }
/*     */ 
/* 439 */     return page;
/*     */   }
/*     */ 
/*     */   public void edit(EopSite eopSite) {
/* 443 */     eopSite.setPoint(get(eopSite.getId()).getPoint());
/* 444 */     this.daoSupport.update("eop_site", eopSite, "id = " + eopSite.getId());
/*     */   }
/*     */ 
/*     */   public void setSiteProduct(Integer userid, Integer siteid, String productid)
/*     */   {
/* 449 */     String sql = "update eop_site set productid=? where userid=? and id=?";
/* 450 */     this.daoSupport.execute(sql, new Object[] { productid, userid, siteid });
/*     */   }
/*     */ 
/*     */   public void changeAdminTheme(Integer themeid)
/*     */   {
/* 455 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 456 */     String sql = "update eop_site set adminthemeid=? where userid=? and id=?";
/*     */ 
/* 458 */     this.daoSupport.execute(sql, new Object[] { themeid, site.getUserid(), site.getId() });
/* 459 */     EopContext.getContext().getCurrentSite().setAdminthemeid(themeid);
/*     */   }
/*     */ 
/*     */   public void changeTheme(Integer themeid)
/*     */   {
/* 464 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 465 */     Theme theme = this.themeManager.getTheme(themeid);
/* 466 */     String sql = "update eop_site set themeid=?,themepath=? where userid=? and id=?";
/* 467 */     this.daoSupport.execute(sql, new Object[] { themeid, theme.getPath(), site.getUserid(), site.getId() });
/*     */ 
/* 469 */     site.setThemeid(themeid);
/* 470 */     site.setThemepath(theme.getPath());
/* 471 */     ResourceStateManager.setDisplayState(1);
/*     */   }
/*     */ 
/*     */   public List listDomain(Integer userid, Integer siteid) {
/* 475 */     String sql = "select * from eop_sitedomain where userid=? and siteid=?";
/* 476 */     return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { userid, siteid });
/*     */   }
/*     */ 
/*     */   public void updatePoint(Integer id, int point, int sitestate)
/*     */   {
/* 481 */     this.daoSupport.execute("update eop_site set point=?,sitestate=? where id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(sitestate), id });
/*     */   }
/*     */ 
/*     */   public int getPoint(Integer id, int point)
/*     */   {
/* 489 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 490 */     long lastgetpoint = site.getLastgetpoint();
/* 491 */     long now = (int)(System.currentTimeMillis() / 1000L);
/* 492 */     int onemonth = 2592000;
/* 493 */     if (now - lastgetpoint > onemonth) {
/* 494 */       this.daoSupport.execute("update eop_site set point=point+? where id=?", new Object[] { Integer.valueOf(point), id });
/*     */ 
/* 496 */       site.setPoint(site.getPoint() + point);
/* 497 */       site.setLastgetpoint(DateUtil.getDateline());
/* 498 */       this.daoSupport.execute("update eop_site set lastgetpoint=? where id=?", new Object[] { Integer.valueOf(DateUtil.getDateline()), id });
/*     */ 
/* 502 */       return 1;
/*     */     }
/* 504 */     return 0;
/*     */   }
/*     */ 
/*     */   public void initData()
/*     */   {
/*     */     String tablenameperfix;
/*     */     Iterator i$;
/* 513 */     if ("2".equals(EopSetting.RUNMODE))
/*     */     {
/* 515 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 516 */       String productId = site.getProductid();
/* 517 */       org.dom4j.Document setupDoc = this.setupLoader.load(productId);
/*     */ 
/* 519 */       tablenameperfix = "";
/*     */ 
/* 521 */       tablenameperfix = "_" + site.getUserid() + "_" + site.getId();
/*     */ 
/* 523 */       List listClean = setupDoc.getRootElement().element("clean").elements();
/*     */ 
/* 525 */       for (i$ = listClean.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 526 */         org.dom4j.Element table = (org.dom4j.Element)o;
/* 527 */         this.daoSupport.execute("truncate table " + table.getText() + tablenameperfix, new Object[0]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 532 */     String sqlPath = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/init.sql";
/*     */ 
/* 534 */     File file = new File(sqlPath);
/* 535 */     if (file.exists()) {
/* 536 */       String content = FileUtil.read(sqlPath, "UTF-8");
/*     */ 
/* 538 */       if ("2".equals(EopSetting.RUNMODE)) {
/* 539 */         EopSite site = EopContext.getContext().getCurrentSite();
/* 540 */         content = content.replaceAll("<userid>", String.valueOf(site.getUserid()));
/*     */ 
/* 542 */         content = content.replaceAll("<siteid>", String.valueOf(site.getId()));
/*     */       }
/*     */       else {
/* 545 */         content = content.replaceAll("_<userid>", "");
/* 546 */         content = content.replaceAll("_<siteid>", "");
/* 547 */         content = content.replaceAll("/user/<userid>/<siteid>", "");
/* 548 */         content = content.replaceAll("<userid>", "1");
/* 549 */         content = content.replaceAll("<siteid>", "1");
/*     */       }
/* 551 */       this.sqlFileExecutor.execute(content);
/*     */     } else {
/* 553 */       throw new RuntimeException("本站点初始化脚本不存在");
/*     */     }
/*     */   }
/*     */ 
/*     */   public ISqlFileExecutor getSqlFileExecutor() {
/* 558 */     return this.sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 562 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public List<Map> list()
/*     */   {
/* 569 */     String sql = "select * from eop_site ";
/* 570 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<Map> list(Integer userid)
/*     */   {
/* 577 */     String sql = "select * from eop_site where userid=?";
/* 578 */     List list = this.daoSupport.queryForList(sql, new Object[] { userid });
/* 579 */     for (Map site : list) {
/* 580 */       List domainList = this.domainManager.listSiteDomain(Integer.valueOf(Integer.parseInt(site.get("id").toString())));
/*     */ 
/* 582 */       site.put("eopSiteDomainList", domainList);
/*     */     }
/* 584 */     return list;
/*     */   }
/*     */ 
/*     */   public List<EopApp> getSiteApps()
/*     */   {
/* 592 */     if (EopSetting.RUNMODE.equals("2")) {
/* 593 */       List appList = listSaasApp();
/* 594 */       return appList;
/*     */     }
/* 596 */     List appList = this.appManager.list();
/* 597 */     return appList;
/*     */   }
/*     */ 
/*     */   private List<EopApp> listSaasApp()
/*     */   {
/* 602 */     List appList = new ArrayList();
/*     */ 
/* 604 */     String xmlFile = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/profile.xml";
/* 605 */     if (new File(xmlFile).exists()) {
/*     */       try
/*     */       {
/* 608 */         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 610 */         DocumentBuilder builder = factory.newDocumentBuilder();
/* 611 */         org.w3c.dom.Document document = builder.parse(xmlFile);
/* 612 */         NodeList nodeList = document.getElementsByTagName("apps");
/* 613 */         if (nodeList != null)
/*     */         {
/* 615 */           Node appsNode = nodeList.item(0);
/* 616 */           NodeList appNodeList = appsNode.getChildNodes();
/*     */ 
/* 619 */           int i = 0; for (int len = appNodeList.getLength(); i < len; i++) {
/* 620 */             Node node = appNodeList.item(i);
/* 621 */             if (node.getNodeType() == 1) {
/* 622 */               org.w3c.dom.Element appNode = (org.w3c.dom.Element)node;
/* 623 */               EopApp eopApp = new EopApp();
/* 624 */               eopApp.setAppid(appNode.getAttribute("id"));
/* 625 */               eopApp.setVersion(appNode.getAttribute("version"));
/* 626 */               appList.add(eopApp);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 631 */         return appList;
/*     */       }
/*     */       catch (Exception e) {
/* 634 */         e.printStackTrace();
/* 635 */         throw new RuntimeException("加载本站点根目录的profile.xml失败，不能导出。");
/*     */       }
/*     */     }
/* 638 */     throw new RuntimeException("本站点根目录下未含有profile.xml，不能导出。");
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 645 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 649 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public void editDomain(String domainOld, String domainNew) {
/* 653 */     this.daoSupport.execute("update eop_sitedomain set domain = ? where domain = ?", new Object[] { domainNew, domainOld });
/*     */   }
/*     */ 
/*     */   public IDaoSupport<EopSite> getDaoSupport()
/*     */   {
/* 660 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport<EopSite> daoSupport) {
/* 664 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public IDomainManager getDomainManager() {
/* 668 */     return this.domainManager;
/*     */   }
/*     */ 
/*     */   public void setDomainManager(IDomainManager domainManager) {
/* 672 */     this.domainManager = domainManager;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/* 676 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/* 680 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public IAppManager getAppManager() {
/* 684 */     return this.appManager;
/*     */   }
/*     */ 
/*     */   public void setAppManager(IAppManager appManager) {
/* 688 */     this.appManager = appManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 692 */     int one = (int)(DateUtil.toDate("2010-09-14 12:00:01", "yyyy-MM-dd HH:mm:ss").getTime() / 1000L);
/*     */ 
/* 694 */     int two = (int)(DateUtil.toDate("2010-09-14 14:00:01", "yyyy-MM-dd HH:mm:ss").getTime() / 1000L);
/*     */ 
/* 696 */     int three = (int)(DateUtil.toDate("2010-09-15 16:00:01", "yyyy-MM-dd HH:mm:ss").getTime() / 1000L);
/*     */ 
/* 699 */     System.out.println(one / 86400);
/* 700 */     System.out.println(two / 86400);
/* 701 */     System.out.println(three / 86400);
/*     */   }
/*     */ 
/*     */   public ISetupLoader getSetupLoader()
/*     */   {
/* 706 */     return this.setupLoader;
/*     */   }
/*     */ 
/*     */   public void setSetupLoader(ISetupLoader setupLoader) {
/* 710 */     this.setupLoader = setupLoader;
/*     */   }
/*     */ 
/*     */   private static class DnsMapper
/*     */     implements ParameterizedRowMapper<Dns>
/*     */   {
/*     */     private String getContext(EopSite site)
/*     */     {
/* 184 */       if ("2".equals(EopSetting.RUNMODE)) {
/* 185 */         return EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.getUserid() + "/" + site.getId();
/*     */       }
/*     */ 
/* 188 */       return EopSetting.IMG_SERVER_DOMAIN;
/*     */     }
/*     */ 
/*     */     public Dns mapRow(ResultSet rs, int rowNum) throws SQLException
/*     */     {
/* 193 */       Dns dns = new Dns();
/*     */ 
/* 195 */       dns.setDomain(rs.getString("domain"));
/* 196 */       EopSite site = new EopSite();
/* 197 */       site.setId(Integer.valueOf(rs.getInt("siteid")));
/* 198 */       site.setAdminthemeid(Integer.valueOf(rs.getInt("adminthemeid")));
/* 199 */       site.setThemeid(Integer.valueOf(rs.getInt("themeid")));
/* 200 */       site.setSitename(rs.getString("sitename"));
/* 201 */       site.setUserid(Integer.valueOf(rs.getInt("userid")));
/* 202 */       site.setKeywords(rs.getString("keywords"));
/* 203 */       site.setDescript(rs.getString("descript"));
/* 204 */       site.setThemepath(rs.getString("themepath"));
/* 205 */       site.setPoint(rs.getInt("point"));
/*     */ 
/* 207 */       site.setCreatetime(Long.valueOf(rs.getLong("createtime")));
/* 208 */       site.setLastgetpoint(rs.getLong("lastgetpoint"));
/* 209 */       site.setLastlogin(Long.valueOf(rs.getLong("lastlogin")));
/* 210 */       site.setLogincount(rs.getInt("logincount"));
/*     */ 
/* 212 */       site.setCopyright(rs.getString("copyright"));
/* 213 */       site.setTitle(rs.getString("title"));
/* 214 */       site.setUsername(rs.getString("username"));
/* 215 */       site.setUsertel(rs.getString("usertel"));
/* 216 */       site.setUsermobile(rs.getString("usermobile"));
/* 217 */       site.setUsertel1(rs.getString("usertel1"));
/* 218 */       site.setUseremail(rs.getString("useremail"));
/* 219 */       site.setIcp(rs.getString("icp"));
/* 220 */       site.setAddress(rs.getString("address"));
/* 221 */       site.setZipcode(rs.getString("zipcode"));
/* 222 */       site.setSiteon(Integer.valueOf(rs.getInt("siteon")));
/* 223 */       site.setState(Integer.valueOf(rs.getInt("state")));
/* 224 */       site.setQq(Integer.valueOf(rs.getInt("qq")));
/* 225 */       site.setMsn(Integer.valueOf(rs.getInt("msn")));
/* 226 */       site.setWw(Integer.valueOf(rs.getInt("ww")));
/* 227 */       site.setTel(Integer.valueOf(rs.getInt("tel")));
/* 228 */       site.setWt(Integer.valueOf(rs.getInt("wt")));
/* 229 */       site.setQqlist(rs.getString("qqlist"));
/* 230 */       site.setMsnlist(rs.getString("msnlist"));
/* 231 */       site.setWwlist(rs.getString("wwlist"));
/* 232 */       site.setTellist(rs.getString("tellist"));
/* 233 */       site.setWorktime(rs.getString("worktime"));
/* 234 */       site.setLinkman(rs.getString("linkman"));
/* 235 */       site.setLinktel(rs.getString("linktel"));
/* 236 */       site.setEmail(rs.getString("email"));
/* 237 */       site.setClosereson(rs.getString("closereson"));
/*     */ 
/* 241 */       site.setRelid(rs.getString("relid"));
/* 242 */       site.setImptype(rs.getInt("imptype"));
/* 243 */       site.setSitestate(rs.getInt("sitestate"));
/* 244 */       site.setIsimported(rs.getInt("isimported"));
/*     */ 
/* 247 */       String icofile = rs.getString("icofile");
/*     */ 
/* 250 */       if (icofile != null)
/*     */       {
/* 252 */         icofile = icofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */       }
/*     */ 
/* 256 */       site.setIcofile(icofile);
/*     */ 
/* 258 */       String logofile = rs.getString("logofile");
/* 259 */       if (logofile != null) {
/* 260 */         logofile = logofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */       }
/*     */ 
/* 263 */       site.setLogofile(logofile);
/*     */ 
/* 265 */       String bklogofile = rs.getString("bklogofile");
/* 266 */       if (bklogofile != null) {
/* 267 */         bklogofile = bklogofile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */       }
/*     */ 
/* 270 */       site.setBklogofile(bklogofile);
/*     */ 
/* 272 */       String bkloginpicfile = rs.getString("bkloginpicfile");
/* 273 */       if (bkloginpicfile != null) {
/* 274 */         bkloginpicfile = bkloginpicfile.replaceAll(EopSetting.FILE_STORE_PREFIX, getContext(site));
/*     */       }
/*     */ 
/* 277 */       site.setBkloginpicfile(bkloginpicfile);
/*     */ 
/* 279 */       site.setMulti_site(Integer.valueOf(rs.getInt("multi_site")));
/* 280 */       dns.setSite(site);
/*     */ 
/* 283 */       return dns;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.SiteManagerImpl
 * JD-Core Version:    0.6.0
 */