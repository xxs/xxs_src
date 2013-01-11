/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.eop.resource.IAppManager;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.sdk.IApp;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class AppInstaller
/*     */   implements IInstaller
/*     */ {
/*  23 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private IAppManager appManager;
/*     */   private IDaoSupport daoSupport;
/*     */ 
/*     */   private void saasInstall(IApp app)
/*     */   {
/*  33 */     app.saasInstall();
/*     */   }
/*     */ 
/*     */   private void install(IApp app, Element el)
/*     */   {
/*  46 */     app.install();
/*     */ 
/*  50 */     EopApp eopApp = new EopApp();
/*  51 */     eopApp.setApp_name(app.getName());
/*  52 */     eopApp.setAppid(app.getId());
/*  53 */     eopApp.setPath(app.getNameSpace());
/*  54 */     eopApp.setDeployment(0);
/*  55 */     eopApp.setDescript(app.getName());
/*     */ 
/*  58 */     eopApp.setVersion(el.getAttribute("version"));
/*  59 */     this.appManager.add(eopApp);
/*     */   }
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  73 */     if (!EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
/*  74 */       this.daoSupport.execute("truncate table eop_app", new Object[0]);
/*     */     }
/*     */ 
/*  79 */     NodeList nodeList = fragment.getChildNodes();
/*  80 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/*  81 */       Node node = nodeList.item(i);
/*  82 */       if (node.getNodeType() == 1) {
/*  83 */         Element el = (Element)node;
/*  84 */         String appid = el.getAttribute("id");
/*     */ 
/*  86 */         if (this.logger.isDebugEnabled()) {
/*  87 */           this.logger.debug("安装应用[" + appid + "]...");
/*     */         }
/*     */ 
/*  90 */         IApp app = (IApp)SpringContextHolder.getBean(appid);
/*  91 */         if (app != null)
/*     */         {
/*  94 */           if (!EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
/*  95 */             install(app, el);
/*     */           }
/*     */ 
/*  99 */           if ("2".equals(EopSetting.RUNMODE)) {
/* 100 */             saasInstall(app);
/*     */           }
/*     */         }
/*     */ 
/* 104 */         if (this.logger.isDebugEnabled())
/* 105 */           this.logger.debug("安装应用[" + appid + "]完成.");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public IAppManager getAppManager() {
/* 111 */     return this.appManager;
/*     */   }
/*     */   public void setAppManager(IAppManager appManager) {
/* 114 */     this.appManager = appManager;
/*     */   }
/*     */   public IDaoSupport getDaoSupport() {
/* 117 */     return this.daoSupport;
/*     */   }
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 120 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AppInstaller
 * JD-Core Version:    0.6.0
 */