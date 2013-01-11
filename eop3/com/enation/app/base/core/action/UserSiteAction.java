/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.ISolutionExporter;
/*     */ import com.enation.app.base.core.service.solution.ISolutionImporter;
/*     */ import com.enation.eop.resource.IDomainManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopSiteDomain;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class UserSiteAction extends WWAction
/*     */ {
/*     */   private ISiteManager siteManager;
/*     */   private IDomainManager domainManager;
/*     */   private EopSite eopSite;
/*     */   private Integer id;
/*     */   private Integer domainid;
/*     */   private Integer statusid;
/*     */   private File cologo;
/*     */   private String cologoFileName;
/*     */   private File ico;
/*     */   private String icoFileName;
/*     */   private File bklogo;
/*     */   private String bklogoFileName;
/*     */   private File bkloginpic;
/*     */   private String bkloginpicFileName;
/*     */   private List<EopSiteDomain> siteDomainList;
/*     */   private EopSiteDomain eopSiteDomain;
/*     */   private String sitedomain;
/*     */   private IUserManager userManager;
/*     */   private ISolutionImporter solutionImporter;
/*     */   private ISolutionExporter solutionExporter;
/*     */   private int exportData;
/*     */   private int exportTheme;
/*     */   private int exportProfile;
/*     */   private int exportAttr;
/*     */   private String name;
/*     */   private File zip;
/*     */   private String zipFileName;
/*     */   private Integer siteid;
/*     */   private String vcode;
/*     */   private String order;
/*     */   private String search;
/*     */   private Integer managerid;
/*     */ 
/*     */   public String toExport()
/*     */   {
/*  75 */     return "export";
/*     */   }
/*     */ 
/*     */   public String toImport() {
/*  79 */     return "import";
/*     */   }
/*     */ 
/*     */   public String backup()
/*     */   {
/*     */     try
/*     */     {
/*  89 */       this.name = ("backup_" + System.currentTimeMillis());
/*  90 */       this.solutionExporter.export(this.name, true, true, true, true);
/*  91 */       this.json = ("{result:1,name:'" + this.name + "'}");
/*     */     } catch (RuntimeException e) {
/*  93 */       e.printStackTrace();
/*  94 */       this.json = "{result:0}";
/*     */     }
/*  96 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String restore()
/*     */   {
/*     */     try
/*     */     {
/* 106 */       String productid = "temp_" + System.currentTimeMillis();
/*     */ 
/* 108 */       String zipPath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/backup/" + this.name + ".zip";
/*     */ 
/* 112 */       this.solutionImporter.imported(productid, zipPath, true);
/* 113 */       this.json = "{result:1}";
/*     */     } catch (Exception e) {
/* 115 */       e.printStackTrace();
/* 116 */       this.json = "{result:0}";
/*     */     }
/* 118 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String export()
/*     */   {
/*     */     try {
/* 124 */       this.solutionExporter.export(this.name, this.exportData == 1, this.exportTheme == 1, this.exportProfile == 1, this.exportAttr == 1);
/*     */ 
/* 126 */       this.json = ("{result:1,path:'" + EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/backup/" + this.name + ".zip" + "'}");
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 130 */       e.printStackTrace();
/* 131 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 133 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String imported() {
/*     */     try {
/* 138 */       String productid = "temp_" + System.currentTimeMillis();
/*     */ 
/* 140 */       String zipPath = UploadUtil.upload(this.zip, this.zipFileName, "solution");
/* 141 */       String header = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath();
/* 142 */       header = header.replaceAll("\\\\", "/");
/* 143 */       zipPath = zipPath.replaceAll(EopSetting.FILE_STORE_PREFIX, header);
/* 144 */       this.solutionImporter.imported(productid, zipPath, true);
/* 145 */       this.json = "{result:1}";
/*     */     }
/*     */     catch (Exception e) {
/* 148 */       e.printStackTrace();
/* 149 */       this.json = "{result:0}";
/*     */     }
/* 151 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String toInitData()
/*     */   {
/* 161 */     return "init_data";
/*     */   }
/*     */ 
/*     */   public String initData()
/*     */   {
/*     */     try
/*     */     {
/* 172 */       WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 174 */       Object realCode = sessonContext.getAttribute("valid_codeinitdata");
/*     */ 
/* 178 */       if (!this.vcode.equals(realCode)) {
/* 179 */         this.json = "{result:0,message:'验证码输入错误'}";
/* 180 */         return "json_message";
/*     */       }
/* 182 */       this.siteManager.initData();
/* 183 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/* 185 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 187 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 191 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 195 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public EopSiteDomain getEopSiteDomain() {
/* 199 */     return this.eopSiteDomain;
/*     */   }
/*     */ 
/*     */   public void setEopSiteDomain(EopSiteDomain eopSiteDomain) {
/* 203 */     this.eopSiteDomain = eopSiteDomain;
/*     */   }
/*     */ 
/*     */   public List<EopSiteDomain> getSiteDomainList() {
/* 207 */     return this.siteDomainList;
/*     */   }
/*     */ 
/*     */   public void setSiteDomainList(List<EopSiteDomain> siteDomainList) {
/* 211 */     this.siteDomainList = siteDomainList;
/*     */   }
/*     */ 
/*     */   public File getIco() {
/* 215 */     return this.ico;
/*     */   }
/*     */ 
/*     */   public void setIco(File ico) {
/* 219 */     this.ico = ico;
/*     */   }
/*     */ 
/*     */   public String getIcoFileName() {
/* 223 */     return this.icoFileName;
/*     */   }
/*     */ 
/*     */   public void setIcoFileName(String icoFileName) {
/* 227 */     this.icoFileName = icoFileName;
/*     */   }
/*     */ 
/*     */   public String getCologoFileName() {
/* 231 */     return this.cologoFileName;
/*     */   }
/*     */ 
/*     */   public void setCologoFileName(String cologoFileName) {
/* 235 */     this.cologoFileName = cologoFileName;
/*     */   }
/*     */ 
/*     */   public File getCologo() {
/* 239 */     return this.cologo;
/*     */   }
/*     */ 
/*     */   public void setCologo(File cologo) {
/* 243 */     this.cologo = cologo;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/* 247 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 251 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public EopSite getEopSite() {
/* 255 */     return this.eopSite;
/*     */   }
/*     */ 
/*     */   public void setEopSite(EopSite eopSite) {
/* 259 */     this.eopSite = eopSite;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/* 266 */     this.webpage = this.siteManager.list(getPage(), getPageSize(), this.order, this.search);
/*     */ 
/* 268 */     return "success";
/*     */   }
/*     */ 
/*     */   public String add() {
/* 272 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/* 276 */     IUserService userService = UserServiceFactory.getUserService();
/* 277 */     this.eopSite = this.siteManager.get(userService.getCurrentSiteId());
/*     */ 
/* 279 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String edit_multi() {
/* 283 */     IUserService userService = UserServiceFactory.getUserService();
/* 284 */     this.eopSite = this.siteManager.get(userService.getCurrentSiteId());
/* 285 */     return "edit_multi";
/*     */   }
/*     */ 
/*     */   public String domainlist() {
/* 289 */     this.siteDomainList = this.domainManager.listSiteDomain();
/* 290 */     return "domainlist";
/*     */   }
/*     */ 
/*     */   public String domain() {
/* 294 */     IUserService userService = UserServiceFactory.getUserService();
/* 295 */     this.eopSite = this.siteManager.get(userService.getCurrentSiteId());
/* 296 */     this.siteDomainList = this.domainManager.listSiteDomain();
/* 297 */     return "domain";
/*     */   }
/*     */ 
/*     */   public String deleteDomain() {
/*     */     try {
/* 302 */       this.siteManager.deleteDomain(this.domainid);
/* 303 */       this.json = "{result:1,message:'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 305 */       this.logger.error(e.getMessage(), e);
/* 306 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 308 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String editdomain() throws Exception {
/* 312 */     if (this.statusid.intValue() == 0)
/* 313 */       this.statusid = Integer.valueOf(1);
/*     */     else {
/* 315 */       this.statusid = Integer.valueOf(0);
/*     */     }
/*     */ 
/* 318 */     this.eopSiteDomain = new EopSiteDomain();
/* 319 */     this.eopSiteDomain.setStatus(this.statusid);
/* 320 */     this.eopSiteDomain.setId(this.domainid);
/*     */     try
/*     */     {
/* 323 */       this.domainManager.edit(this.eopSiteDomain);
/* 324 */       this.json = "{result:1,message:'修改成功'}";
/*     */     } catch (RuntimeException e) {
/* 326 */       this.logger.error(e.getStackTrace());
/* 327 */       this.json = "{result:0,message:'修改失败'}";
/*     */     }
/*     */ 
/* 330 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String editSave()
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 369 */       if (this.cologo != null) {
/* 370 */         String logo = UploadUtil.upload(this.cologo, this.cologoFileName, "user");
/* 371 */         this.eopSite.setLogofile(logo);
/*     */       }
/*     */ 
/* 374 */       if (this.ico != null) {
/* 375 */         String icoPath = UploadUtil.upload(this.ico, this.icoFileName, "user");
/* 376 */         this.eopSite.setIcofile(icoPath);
/*     */       }
/*     */ 
/* 379 */       if (this.bklogo != null) {
/* 380 */         String logo = UploadUtil.upload(this.bklogo, this.bklogoFileName, "user");
/* 381 */         this.eopSite.setBklogofile(logo);
/*     */       }
/*     */ 
/* 384 */       if (this.bkloginpic != null) {
/* 385 */         String loginpic = UploadUtil.upload(this.bkloginpic, this.bkloginpicFileName, "user");
/*     */ 
/* 387 */         this.eopSite.setBkloginpicfile(loginpic);
/*     */       }
/*     */ 
/* 390 */       this.eopSite.setQq(Integer.valueOf(this.eopSite.getQq() == null ? 0 : this.eopSite.getQq().intValue()));
/* 391 */       this.eopSite.setMsn(Integer.valueOf(this.eopSite.getMsn() == null ? 0 : this.eopSite.getMsn().intValue()));
/* 392 */       this.eopSite.setWw(Integer.valueOf(this.eopSite.getWw() == null ? 0 : this.eopSite.getWw().intValue()));
/* 393 */       this.eopSite.setTel(Integer.valueOf(this.eopSite.getTel() == null ? 0 : this.eopSite.getTel().intValue()));
/* 394 */       this.eopSite.setWt(Integer.valueOf(this.eopSite.getWt() == null ? 0 : this.eopSite.getWt().intValue()));
/*     */ 
/* 396 */       this.siteManager.edit(this.eopSite);
/*     */ 
/* 398 */       this.msgs.add("修改成功");
/* 399 */       this.urls.put("我的站点", "userSite!edit.do");
/*     */     } catch (RuntimeException e) {
/* 401 */       this.logger.error("修改站点", e);
/* 402 */       this.msgs.add(e.getMessage());
/* 403 */       this.urls.put("我的站点", "userSite!edit.do");
/*     */     }
/* 405 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() throws Exception {
/*     */     try {
/* 410 */       this.siteManager.delete(this.id);
/* 411 */       this.msgs.add("删除成功");
/*     */     } catch (RuntimeException e) {
/* 413 */       e.printStackTrace();
/* 414 */       this.msgs.add(e.getMessage());
/*     */     }
/* 416 */     this.urls.put("站点列表", "userSite.do");
/* 417 */     return "message";
/*     */   }
/*     */ 
/*     */   public String adddomain() throws Exception {
/* 421 */     return "adddomain";
/*     */   }
/*     */ 
/*     */   public String addDomainSave() throws Exception {
/* 425 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 426 */     this.eopSiteDomain = new EopSiteDomain();
/* 427 */     this.eopSiteDomain.setUserid(userid);
/* 428 */     this.eopSiteDomain.setDomain(this.sitedomain);
/* 429 */     this.eopSiteDomain.setSiteid(this.siteid);
/* 430 */     int result = -1;
/*     */     try {
/* 432 */       result = this.siteManager.addDomain(this.eopSiteDomain);
/*     */     } catch (RuntimeException e) {
/* 434 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/* 435 */       return "json_message";
/*     */     }
/*     */ 
/* 438 */     if (result > 0)
/* 439 */       this.json = "{result:1,message:'增加成功'}";
/*     */     else {
/* 441 */       this.json = "{result:0,message:'新增域名失败'}";
/*     */     }
/*     */ 
/* 444 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String changeDefaultSite()
/*     */     throws Exception
/*     */   {
/* 450 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 451 */     this.userManager.changeDefaultSite(userid, this.managerid, this.id);
/*     */ 
/* 453 */     return "message";
/*     */   }
/*     */ 
/*     */   public Integer getDomainid() {
/* 457 */     return this.domainid;
/*     */   }
/*     */ 
/*     */   public void setDomainid(Integer domainid) {
/* 461 */     this.domainid = domainid;
/*     */   }
/*     */ 
/*     */   public Integer getStatusid() {
/* 465 */     return this.statusid;
/*     */   }
/*     */ 
/*     */   public void setStatusid(Integer statusid) {
/* 469 */     this.statusid = statusid;
/*     */   }
/*     */ 
/*     */   public String getSitedomain() {
/* 473 */     return this.sitedomain;
/*     */   }
/*     */ 
/*     */   public void setSitedomain(String sitedomain) {
/* 477 */     this.sitedomain = sitedomain;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 481 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 485 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public Integer getSiteid() {
/* 489 */     return this.siteid;
/*     */   }
/*     */ 
/*     */   public void setSiteid(Integer siteid) {
/* 493 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public IDomainManager getDomainManager() {
/* 497 */     return this.domainManager;
/*     */   }
/*     */ 
/*     */   public void setDomainManager(IDomainManager domainManager) {
/* 501 */     this.domainManager = domainManager;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 505 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 509 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getSearch() {
/* 513 */     return this.search;
/*     */   }
/*     */ 
/*     */   public void setSearch(String search) {
/* 517 */     this.search = search;
/*     */   }
/*     */ 
/*     */   public Integer getManagerid() {
/* 521 */     return this.managerid;
/*     */   }
/*     */ 
/*     */   public void setManagerid(Integer managerid) {
/* 525 */     this.managerid = managerid;
/*     */   }
/*     */ 
/*     */   public File getBklogo() {
/* 529 */     return this.bklogo;
/*     */   }
/*     */ 
/*     */   public void setBklogo(File bklogo) {
/* 533 */     this.bklogo = bklogo;
/*     */   }
/*     */ 
/*     */   public String getBklogoFileName() {
/* 537 */     return this.bklogoFileName;
/*     */   }
/*     */ 
/*     */   public void setBklogoFileName(String bklogoFileName) {
/* 541 */     this.bklogoFileName = bklogoFileName;
/*     */   }
/*     */ 
/*     */   public File getBkloginpic() {
/* 545 */     return this.bkloginpic;
/*     */   }
/*     */ 
/*     */   public void setBkloginpic(File bkloginpic) {
/* 549 */     this.bkloginpic = bkloginpic;
/*     */   }
/*     */ 
/*     */   public String getBkloginpicFileName() {
/* 553 */     return this.bkloginpicFileName;
/*     */   }
/*     */ 
/*     */   public void setBkloginpicFileName(String bkloginpicFileName) {
/* 557 */     this.bkloginpicFileName = bkloginpicFileName;
/*     */   }
/*     */ 
/*     */   public String getVcode() {
/* 561 */     return this.vcode;
/*     */   }
/*     */ 
/*     */   public void setVcode(String vcode) {
/* 565 */     this.vcode = vcode;
/*     */   }
/*     */ 
/*     */   public int getExportData() {
/* 569 */     return this.exportData;
/*     */   }
/*     */ 
/*     */   public void setExportData(int exportData) {
/* 573 */     this.exportData = exportData;
/*     */   }
/*     */ 
/*     */   public int getExportTheme() {
/* 577 */     return this.exportTheme;
/*     */   }
/*     */ 
/*     */   public void setExportTheme(int exportTheme) {
/* 581 */     this.exportTheme = exportTheme;
/*     */   }
/*     */ 
/*     */   public int getExportProfile() {
/* 585 */     return this.exportProfile;
/*     */   }
/*     */ 
/*     */   public void setExportProfile(int exportProfile) {
/* 589 */     this.exportProfile = exportProfile;
/*     */   }
/*     */ 
/*     */   public int getExportAttr() {
/* 593 */     return this.exportAttr;
/*     */   }
/*     */ 
/*     */   public void setExportAttr(int exportAttr) {
/* 597 */     this.exportAttr = exportAttr;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 601 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 605 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public File getZip() {
/* 609 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(File zip) {
/* 613 */     this.zip = zip;
/*     */   }
/*     */ 
/*     */   public String getZipFileName() {
/* 617 */     return this.zipFileName;
/*     */   }
/*     */ 
/*     */   public void setZipFileName(String zipFileName) {
/* 621 */     this.zipFileName = zipFileName;
/*     */   }
/*     */ 
/*     */   public ISolutionImporter getSolutionImporter() {
/* 625 */     return this.solutionImporter;
/*     */   }
/*     */ 
/*     */   public void setSolutionImporter(ISolutionImporter solutionImporter) {
/* 629 */     this.solutionImporter = solutionImporter;
/*     */   }
/*     */ 
/*     */   public ISolutionExporter getSolutionExporter() {
/* 633 */     return this.solutionExporter;
/*     */   }
/*     */ 
/*     */   public void setSolutionExporter(ISolutionExporter solutionExporter) {
/* 637 */     this.solutionExporter = solutionExporter;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.UserSiteAction
 * JD-Core Version:    0.6.0
 */