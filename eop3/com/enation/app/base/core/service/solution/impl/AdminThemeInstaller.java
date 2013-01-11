/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.IAdminThemeInfoFileLoader;
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.app.base.core.service.solution.InstallUtil;
/*     */ import com.enation.eop.resource.IAdminThemeManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.model.AdminTheme;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class AdminThemeInstaller
/*     */   implements IInstaller
/*     */ {
/*  24 */   private final Logger logger = Logger.getLogger(getClass());
/*     */   private ISiteManager siteManager;
/*     */   private IAdminThemeManager adminThemeManager;
/*     */   private IAdminThemeInfoFileLoader adminThemeInfoFileLoader;
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  32 */     if (this.logger.isDebugEnabled()) {
/*  33 */       this.logger.debug("user install admintheme[" + fragment + "] from " + productId);
/*     */     }
/*     */ 
/*  36 */     if (fragment == null) throw new RuntimeException("install admintheme error[node is null]");
/*     */ 
/*  38 */     NodeList themeList = fragment.getChildNodes();
/*     */ 
/*  41 */     install(themeList, productId);
/*     */   }
/*     */ 
/*     */   private void install(Element themeNode, String productId)
/*     */   {
/*  46 */     String isdefault = themeNode.getAttribute("default");
/*  47 */     AdminTheme adminTheme = new AdminTheme();
/*  48 */     String path = themeNode.getAttribute("id");
/*  49 */     InstallUtil.putMessaage("正在安装后台主题" + path + "...");
/*  50 */     String commonAttr = themeNode.getAttribute("isCommonTheme");
/*  51 */     commonAttr = commonAttr == null ? "" : commonAttr;
/*  52 */     Boolean isCommonTheme = Boolean.valueOf(commonAttr.toUpperCase().equals("TRUE"));
/*  53 */     Document iniFileDoc = this.adminThemeInfoFileLoader.load(productId, path, isCommonTheme);
/*  54 */     Node themeN = null;
/*     */     try {
/*  56 */       themeN = iniFileDoc.getFirstChild();
/*  57 */       if (themeN == null) throw new RuntimeException("adminthem node is null"); 
/*     */     }
/*     */     catch (Exception e) {
/*  59 */       e.printStackTrace();
/*     */     }
/*  61 */     Node authornode = null;
/*     */     try {
/*  63 */       NodeList list = ((Element)themeN).getElementsByTagName("author");
/*  64 */       if ((list == null) || (list.item(0) == null)) throw new RuntimeException("author node is null");
/*  65 */       authornode = list.item(0);
/*     */     } catch (Exception e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*  69 */     String author = authornode.getTextContent();
/*     */ 
/*  71 */     Node versionnode = null;
/*     */     try {
/*  73 */       NodeList list = ((Element)themeN).getElementsByTagName("version");
/*  74 */       if ((list == null) || (list.item(0) == null)) throw new RuntimeException("author node is null");
/*  75 */       versionnode = list.item(0);
/*     */     } catch (Exception e) {
/*  77 */       e.printStackTrace();
/*     */     }
/*  79 */     String version = versionnode.getTextContent();
/*  80 */     adminTheme.setPath(path);
/*  81 */     adminTheme.setThemename(themeNode.getAttribute("name"));
/*  82 */     adminTheme.setThumb("preview.png");
/*  83 */     adminTheme.setAuthor(author);
/*  84 */     adminTheme.setVersion(version);
/*     */     try
/*     */     {
/*  87 */       Integer themeid = this.adminThemeManager.add(adminTheme, isCommonTheme.booleanValue());
/*  88 */       if ("yes".equals(isdefault)) {
/*  89 */         if (this.logger.isDebugEnabled())
/*  90 */           this.logger.debug("change theme[" + themeid + "] ");
/*  91 */         this.siteManager.changeAdminTheme(themeid);
/*     */       }
/*  93 */       InstallUtil.putMessaage("完成!");
/*     */     } catch (Exception e) {
/*  95 */       e.printStackTrace();
/*  96 */       throw new RuntimeException("install admin theme error");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void install(NodeList nodeList, String productId)
/*     */   {
/* 102 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 103 */       Node node = nodeList.item(i);
/* 104 */       if (node.getNodeType() == 1)
/* 105 */         install((Element)node, productId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAdminThemeInfoFileLoader(IAdminThemeInfoFileLoader adminThemeInfoFileLoader)
/*     */   {
/* 112 */     this.adminThemeInfoFileLoader = adminThemeInfoFileLoader;
/*     */   }
/*     */ 
/*     */   public IAdminThemeManager getAdminThemeManager() {
/* 116 */     return this.adminThemeManager;
/*     */   }
/*     */ 
/*     */   public void setAdminThemeManager(IAdminThemeManager adminThemeManager) {
/* 120 */     this.adminThemeManager = adminThemeManager;
/*     */   }
/*     */ 
/*     */   public IAdminThemeInfoFileLoader getAdminThemeInfoFileLoader() {
/* 124 */     return this.adminThemeInfoFileLoader;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 128 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 132 */     this.siteManager = siteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminThemeInstaller
 * JD-Core Version:    0.6.0
 */