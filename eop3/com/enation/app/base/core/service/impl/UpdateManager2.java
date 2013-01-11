/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.UpdateLog;
/*     */ import com.enation.app.base.core.model.VersionState;
/*     */ import com.enation.app.base.core.service.IBatchSqlExecutor;
/*     */ import com.enation.app.base.core.service.IUpdateManager;
/*     */ import com.enation.eop.processor.core.RemoteRequest;
/*     */ import com.enation.eop.processor.core.Request;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.enation.framework.util.XMLUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class UpdateManager2
/*     */   implements IUpdateManager
/*     */ {
/*     */   private IDaoSupport daoSupport;
/*  55 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private ISiteManager siteManager;
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */   private IBatchSqlExecutor batchSqlExecutor;
/*     */ 
/*     */   public VersionState checkNewVersion()
/*     */   {
/*  68 */     if (this.logger.isDebugEnabled()) {
/*  69 */       this.logger.debug("检测当前产品是否有新版本...");
/*     */     }
/*     */ 
/*  72 */     VersionState versionState = new VersionState();
/*  73 */     String newVersion = getNewVersion();
/*  74 */     String currentVersion = EopSetting.VERSION;
/*     */ 
/*  77 */     if (this.logger.isDebugEnabled()) {
/*  78 */       this.logger.debug("产品当前版本为：[" + currentVersion + "]最新版本为：[" + newVersion + "]");
/*     */     }
/*     */ 
/*  82 */     if (!StringUtil.isEmpty(newVersion))
/*     */     {
/*  84 */       if (versionLargerThen(newVersion, currentVersion)) {
/*  85 */         versionState.setHaveNewVersion(true);
/*  86 */         versionState.setNewVersion(newVersion);
/*  87 */         List updateLogList = getUpdateLog();
/*  88 */         versionState.setUpdateLogList(updateLogList);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     if (this.logger.isDebugEnabled()) {
/*  95 */       this.logger.debug("检测新版本完成.");
/*     */     }
/*     */ 
/* 100 */     return versionState;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/* 109 */     if (this.logger.isDebugEnabled()) {
/* 110 */       this.logger.debug("执行升级操作...");
/*     */     }
/*     */ 
/* 115 */     String newVersion = getNewVersion();
/* 116 */     if (this.logger.isDebugEnabled()) {
/* 117 */       this.logger.debug("产品最新版本为：[" + newVersion + "]");
/*     */     }
/*     */ 
/* 123 */     Document document = loadUpdateXmlDoc("/version.xml");
/*     */ 
/* 126 */     Element appsNode = XMLUtil.getChildByTagName(document, "apps");
/*     */ 
/* 130 */     List appList = this.siteManager.getSiteApps();
/*     */ 
/* 133 */     for (EopApp app : appList)
/*     */     {
/* 137 */       String appVersion = app.getVersion();
/*     */ 
/* 140 */       Element appNode = XMLUtil.getChildByTagName(appsNode, app.getAppid());
/*     */ 
/* 143 */       String appLastVersion = appNode.getAttribute("lastversion");
/*     */ 
/* 145 */       if (this.logger.isDebugEnabled()) {
/* 146 */         this.logger.debug("升级应用[" + app.getAppid() + "]，产品中此应用当前版本[" + appVersion + "]，最新版本[" + appLastVersion + "]");
/*     */       }
/*     */ 
/* 149 */       update(app.getAppid(), appVersion, appNode);
/*     */ 
/* 156 */       this.daoSupport.execute("update eop_app set version=? where appid=?", new Object[] { appLastVersion, app.getAppid() });
/*     */ 
/* 158 */       if (this.logger.isDebugEnabled()) {
/* 159 */         this.logger.debug("升级应用[" + app.getAppid() + "]完成。");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 166 */     updateVersion(newVersion);
/*     */ 
/* 168 */     if (this.logger.isDebugEnabled())
/* 169 */       this.logger.debug("产品升级完成，当前版本为:[" + EopSetting.VERSION + "]");
/*     */   }
/*     */ 
/*     */   private void update(String appid, String currentVersion, Element appNode)
/*     */   {
/* 186 */     if (this.logger.isDebugEnabled()) {
/* 187 */       this.logger.debug("升级应用[" + appid + "]...");
/*     */     }
/*     */ 
/* 190 */     NodeList versionNodeList = appNode.getChildNodes();
/*     */ 
/* 193 */     if ((versionNodeList == null) || (versionNodeList.getLength() == 0)) {
/* 194 */       if (this.logger.isDebugEnabled()) {
/* 195 */         this.logger.debug("服务器端应用[" + appid + "]的版本列表为空...");
/*     */       }
/* 197 */       return;
/*     */     }
/*     */ 
/* 202 */     for (int i = 0; i < versionNodeList.getLength(); i++) {
/* 203 */       Node node = versionNodeList.item(i);
/* 204 */       if (node.getNodeType() == 1) {
/* 205 */         String version = node.getTextContent();
/*     */ 
/* 208 */         if (versionLargerThen(version, currentVersion)) {
/* 209 */           if (this.logger.isDebugEnabled()) {
/* 210 */             this.logger.debug("为应用[" + appid + "]升级版本[" + version + "]...");
/*     */           }
/*     */ 
/* 213 */           update(appid, version);
/*     */         }
/* 215 */         else if (this.logger.isDebugEnabled()) {
/* 216 */           this.logger.debug("应用[" + appid + "]版本小于此版本，[" + version + "]跳过...");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void update(String appid, String version)
/*     */   {
/* 234 */     download(appid, version);
/* 235 */     unZip(appid, version);
/* 236 */     exeUpdate(appid, version);
/*     */   }
/*     */ 
/*     */   private void exeUpdate(String appid, String version)
/*     */   {
/* 247 */     String patchPath = EopSetting.EOP_PATH + "/patch/" + appid + "/" + version;
/* 248 */     String updateSql = patchPath + "/sql/update.sql";
/* 249 */     String cmsUpdateSql = patchPath + "/sql/cms_update.sql";
/*     */ 
/* 257 */     if (new File(updateSql).exists())
/*     */     {
/* 259 */       if (this.logger.isDebugEnabled()) {
/* 260 */         this.logger.debug("执行[" + updateSql + "]...");
/*     */       }
/*     */ 
/* 263 */       String content = FileUtil.read(updateSql, "UTF-8");
/* 264 */       executeSql(content);
/*     */ 
/* 266 */       if (this.logger.isDebugEnabled()) {
/* 267 */         this.logger.debug("执行[" + updateSql + "]成功.");
/*     */       }
/*     */ 
/*     */     }
/* 272 */     else if (this.logger.isDebugEnabled()) {
/* 273 */       this.logger.debug("[" + updateSql + "]不存在跳过.");
/*     */     }
/*     */ 
/* 283 */     if (new File(cmsUpdateSql).exists()) {
/* 284 */       if (this.logger.isDebugEnabled()) {
/* 285 */         this.logger.debug("执行[" + cmsUpdateSql + "]...");
/*     */       }
/*     */ 
/* 288 */       String content = FileUtil.read(cmsUpdateSql, "UTF-8");
/*     */ 
/* 290 */       if (this.logger.isDebugEnabled()) {
/* 291 */         this.logger.debug("执行内容[" + content + "]...");
/*     */       }
/*     */ 
/* 294 */       this.batchSqlExecutor.executeForCms(content);
/*     */ 
/* 296 */       if (this.logger.isDebugEnabled()) {
/* 297 */         this.logger.debug("执行[" + cmsUpdateSql + "]成功.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 303 */     if (this.logger.isDebugEnabled()) {
/* 304 */       this.logger.debug("复制[" + patchPath + "/web" + "]...");
/*     */     }
/*     */ 
/* 307 */     FileUtil.copyFolder(patchPath + "/web", EopSetting.EOP_PATH);
/* 308 */     if (this.logger.isDebugEnabled())
/* 309 */       this.logger.debug("复制[" + patchPath + "/web" + "]成功.");
/*     */   }
/*     */ 
/*     */   private void updateVersion(String version)
/*     */   {
/* 319 */     if (this.logger.isDebugEnabled()) {
/* 320 */       this.logger.debug("更新eop.properties中的版本信息...");
/*     */     }
/*     */     try
/*     */     {
/* 324 */       String path = StringUtil.getRootPath("eop.properties");
/* 325 */       File file = new File(path);
/*     */ 
/* 328 */       EopSetting.VERSION = version;
/* 329 */       InputStream in = new FileInputStream(file);
/* 330 */       Properties props = new Properties();
/* 331 */       props.load(in);
/* 332 */       props.setProperty("version", version);
/* 333 */       props.store(new FileOutputStream(file), "eop.properties");
/* 334 */       if (this.logger.isDebugEnabled())
/* 335 */         this.logger.debug("更新eop.properties中的版本成功.");
/*     */     }
/*     */     catch (Exception e) {
/* 338 */       this.logger.error("更新eop.properties出错", e.fillInStackTrace());
/* 339 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void executeSql(String content)
/*     */   {
/* 353 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 354 */       this.batchSqlExecutor.exeucte(content);
/*     */     } else {
/* 356 */       content = content.replaceAll("_<userid>", "");
/* 357 */       content = content.replaceAll("_<siteid>", "");
/* 358 */       content = content.replaceAll("/user/<userid>/<siteid>", "");
/* 359 */       content = content.replaceAll("<userid>", "1");
/* 360 */       content = content.replaceAll("<siteid>", "1");
/* 361 */       this.sqlFileExecutor.execute(content);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void download(String appid, String version)
/*     */   {
/* 375 */     String remoteZip = EopSetting.SERVICE_DOMAIN_NAME + "/" + appid + "/patch/" + version + ".zip";
/*     */     try
/*     */     {
/* 380 */       if (this.logger.isDebugEnabled()) {
/* 381 */         this.logger.debug("由[" + remoteZip + "]下载升级包...");
/*     */       }
/*     */ 
/* 384 */       URL url = new URL(remoteZip);
/* 385 */       String outPath = EopSetting.EOP_PATH + "/patch/" + appid;
/* 386 */       URLConnection conn = url.openConnection();
/* 387 */       InputStream is = conn.getInputStream();
/* 388 */       byte[] bts = new byte[2048];
/* 389 */       File file = new File(outPath);
/* 390 */       if (!file.exists()) {
/* 391 */         file.mkdirs();
/*     */       }
/*     */ 
/* 395 */       FileOutputStream fout = new FileOutputStream(outPath + "/" + version + ".zip");
/*     */       int n;
/* 397 */       while ((n = is.read(bts)) != -1) {
/* 398 */         fout.write(bts, 0, n);
/* 399 */         fout.flush();
/* 400 */         bts = new byte[2048];
/*     */       }
/*     */     } catch (Exception e) {
/* 403 */       this.logger.error("下载升级包[" + remoteZip + "]出错");
/* 404 */       e.printStackTrace();
/* 405 */       throw new RuntimeException("下载升级版本zip出错", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void unZip(String appid, String version)
/*     */   {
/* 417 */     String zipPath = EopSetting.EOP_PATH + "/patch/" + appid + "/" + version + ".zip";
/* 418 */     if (this.logger.isDebugEnabled()) {
/* 419 */       this.logger.debug("解压升级包[" + zipPath + "]...");
/*     */     }
/*     */ 
/* 422 */     FileUtil.unZip(zipPath, EopSetting.EOP_PATH + "/patch/" + appid + "/" + version, true);
/* 423 */     if (this.logger.isDebugEnabled())
/* 424 */       this.logger.debug("解压升级包[" + zipPath + "]完成");
/*     */   }
/*     */ 
/*     */   private List<UpdateLog> getUpdateLog()
/*     */   {
/* 436 */     if (this.logger.isDebugEnabled()) {
/* 437 */       this.logger.debug("获取产品所有应用的更新日志...");
/*     */     }
/*     */ 
/* 441 */     List updateLogList = new ArrayList();
/*     */ 
/* 445 */     Document document = loadUpdateXmlDoc("/version.xml");
/*     */ 
/* 448 */     Element appsNode = XMLUtil.getChildByTagName(document, "apps");
/*     */ 
/* 452 */     List appList = this.siteManager.getSiteApps();
/* 453 */     for (EopApp app : appList)
/*     */     {
/* 455 */       String verison = app.getVersion();
/*     */ 
/* 458 */       Element appNode = XMLUtil.getChildByTagName(appsNode, app.getAppid());
/*     */ 
/* 461 */       UpdateLog updateLog = getAppUpdateLog(app.getAppid(), verison, appNode);
/* 462 */       if (updateLog != null) {
/* 463 */         updateLogList.add(updateLog);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 468 */     if (this.logger.isDebugEnabled()) {
/* 469 */       this.logger.debug("获取产品所有应用的更新日志完成.");
/*     */     }
/*     */ 
/* 473 */     return updateLogList;
/*     */   }
/*     */ 
/*     */   private UpdateLog getAppUpdateLog(String appid, String currentVersion, Node appNode)
/*     */   {
/* 486 */     if (this.logger.isDebugEnabled()) {
/* 487 */       this.logger.debug("获取获取应用[" + appid + "]的日志...");
/*     */     }
/*     */ 
/* 490 */     NodeList versionNodeList = appNode.getChildNodes();
/*     */ 
/* 493 */     if ((versionNodeList == null) || (versionNodeList.getLength() == 0)) return null;
/* 494 */     UpdateLog updateLog = new UpdateLog();
/* 495 */     updateLog.setAppId(appid);
/* 496 */     List logList = new ArrayList();
/* 497 */     for (int i = 0; i < versionNodeList.getLength(); i++) {
/* 498 */       Node node = versionNodeList.item(i);
/* 499 */       if (node.getNodeType() == 1) {
/* 500 */         String version = node.getTextContent();
/*     */ 
/* 502 */         if (!versionLargerThen(version, currentVersion))
/*     */           continue;
/* 504 */         List oneLogList = getVersionUpdateLog(appid, version);
/* 505 */         logList.addAll(oneLogList);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 510 */     updateLog.setLogList(logList);
/* 511 */     if (this.logger.isDebugEnabled()) {
/* 512 */       this.logger.debug("获取获取应用[" + appid + "]的日志成功.");
/*     */     }
/*     */ 
/* 515 */     return updateLog;
/*     */   }
/*     */ 
/*     */   private List<String> getVersionUpdateLog(String appid, String version)
/*     */   {
/* 527 */     if (this.logger.isDebugEnabled()) {
/* 528 */       this.logger.debug("获取获取应用[" + appid + "]版本[" + version + "]的日志...");
/*     */     }
/* 530 */     List logList = new ArrayList();
/* 531 */     Document doc = loadUpdateXmlDoc("/" + appid + "/" + version + ".xml");
/* 532 */     NodeList itemList = doc.getElementsByTagName("item");
/*     */ 
/* 534 */     if (itemList != null) {
/* 535 */       int i = 0; for (int len = itemList.getLength(); i < len; i++) {
/* 536 */         Node node = itemList.item(i);
/* 537 */         if (node.getNodeType() == 1) {
/* 538 */           String log = node.getTextContent();
/* 539 */           logList.add(log);
/*     */         }
/*     */       }
/*     */     }
/* 543 */     if (this.logger.isDebugEnabled()) {
/* 544 */       this.logger.debug("获取获取应用[" + appid + "]版本[" + version + "]的日志成功.");
/*     */     }
/* 546 */     return logList;
/*     */   }
/*     */ 
/*     */   private String getNewVersion()
/*     */   {
/* 559 */     String productid = EopSetting.PRODUCTID;
/* 560 */     Document document = loadUpdateXmlDoc("/version.xml");
/*     */ 
/* 562 */     Node productNode = XMLUtil.getChildByTagName(document, "products");
/* 563 */     NodeList productList = productNode.getChildNodes();
/* 564 */     int i = 0; for (int len = productList.getLength(); i < len; i++) {
/* 565 */       Node proNode = productList.item(i);
/* 566 */       if (proNode.getNodeType() == 1) {
/* 567 */         String proName = proNode.getNodeName();
/* 568 */         if (productid.equals(proName)) {
/* 569 */           return proNode.getTextContent();
/*     */         }
/*     */       }
/*     */     }
/* 573 */     throw new RuntimeException("产品ID不正确");
/*     */   }
/*     */ 
/*     */   private Document loadUpdateXmlDoc(String filePath)
/*     */   {
/* 587 */     String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;
/*     */ 
/* 590 */     Request request = new RemoteRequest();
/* 591 */     Response response = request.execute(serviceDomain + filePath);
/* 592 */     if (response != null) {
/* 593 */       InputStream xmlStream = response.getInputStream();
/*     */       try
/*     */       {
/* 596 */         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 598 */         DocumentBuilder builder = factory.newDocumentBuilder();
/* 599 */         Document document = builder.parse(xmlStream);
/* 600 */         return document;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 604 */         throw new RuntimeException("load version.xml error");
/*     */       }
/*     */     }
/* 607 */     throw new RuntimeException("load version.xml error");
/*     */   }
/*     */ 
/*     */   private boolean versionLargerThen(String ver1, String ver2)
/*     */   {
/* 620 */     if (StringUtil.isEmpty(ver1)) throw new IllegalArgumentException("ver1版本不能为空");
/* 621 */     if (StringUtil.isEmpty(ver2)) throw new IllegalArgumentException("ver2版本不能为空");
/* 622 */     if (ver1.length() != ver2.length()) throw new IllegalArgumentException("ver2与ver2版本号格式不相同");
/* 623 */     if (ver1.length() != 5) throw new IllegalArgumentException("版本号格式不正确，应为如：2.1.0");
/*     */ 
/* 625 */     String[] ver1a = ver1.split("\\.");
/* 626 */     Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
/* 627 */     String[] ver2a = ver2.split("\\.");
/* 628 */     Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());
/*     */ 
/* 630 */     return ver1i.intValue() > ver2i.intValue();
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager()
/*     */   {
/* 637 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 641 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public ISqlFileExecutor getSqlFileExecutor()
/*     */   {
/* 647 */     return this.sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor)
/*     */   {
/* 653 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public IBatchSqlExecutor getBatchSqlExecutor()
/*     */   {
/* 659 */     return this.batchSqlExecutor;
/*     */   }
/*     */ 
/*     */   public void setBatchSqlExecutor(IBatchSqlExecutor batchSqlExecutor)
/*     */   {
/* 665 */     this.batchSqlExecutor = batchSqlExecutor;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport()
/*     */   {
/* 671 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport)
/*     */   {
/* 677 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.UpdateManager2
 * JD-Core Version:    0.6.0
 */