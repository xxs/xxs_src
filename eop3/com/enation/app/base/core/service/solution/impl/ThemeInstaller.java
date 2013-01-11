/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.app.base.core.service.solution.InstallUtil;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class ThemeInstaller
/*     */   implements IInstaller
/*     */ {
/*     */   private IInstaller borderInstaller;
/*     */   private IThemeManager themeManager;
/*     */   private ISiteManager siteManager;
/*     */   private String productId;
/*  49 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  32 */     this.productId = productId;
/*     */ 
/*  34 */     String contextPath = EopContext.getContext().getContextPath();
/*     */ 
/*  36 */     String targetPath = EopSetting.EOP_PATH + contextPath + "/themes/";
/*  37 */     String sourcePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/themes/";
/*     */ 
/*  39 */     FileUtil.copyFile(sourcePath + "findpass_email_template.html", targetPath + "findpass_email_template.html");
/*  40 */     FileUtil.copyFile(sourcePath + "order_create_email_template.html", targetPath + "order_create_email_template.html");
/*  41 */     FileUtil.copyFile(sourcePath + "price_filter.xml", targetPath + "price_filter.xml");
/*  42 */     FileUtil.copyFile(sourcePath + "reg_email_template.html", targetPath + "reg_email_template.html");
/*  43 */     FileUtil.copyFile(sourcePath + "success.html", targetPath + "success.html");
/*  44 */     FileUtil.copyFile(sourcePath + "widgets_default.xml", targetPath + "widgets_default.xml");
/*     */ 
/*  46 */     NodeList themeList = fragment.getChildNodes();
/*  47 */     install(themeList);
/*     */   }
/*     */ 
/*     */   private void install(Element themeNode) {
/*  51 */     String isdefault = themeNode.getAttribute("default");
/*  52 */     Theme theme = new Theme();
/*  53 */     theme.setProductId(this.productId);
/*  54 */     theme.setPath(themeNode.getAttribute("id"));
/*  55 */     theme.setThemename(themeNode.getAttribute("name"));
/*  56 */     theme.setThumb("preview.png");
/*  57 */     theme.setSiteid(Integer.valueOf(0));
/*  58 */     InstallUtil.putMessaage("安装主题" + theme.getThemename() + "...");
/*  59 */     String commonAttr = themeNode.getAttribute("isCommonTheme");
/*  60 */     commonAttr = commonAttr == null ? "" : commonAttr;
/*  61 */     Boolean isCommonTheme = Boolean.valueOf(commonAttr.toUpperCase().equals("TRUE"));
/*  62 */     Integer themeid = this.themeManager.add(theme, isCommonTheme.booleanValue());
/*  63 */     if (this.logger.isDebugEnabled()) {
/*  64 */       this.logger.debug("install " + theme.getThemename() + ",default :" + isdefault);
/*     */     }
/*  66 */     if ("yes".equals(isdefault)) {
/*  67 */       if (this.logger.isDebugEnabled())
/*  68 */         this.logger.debug("change theme[" + themeid + "] ");
/*  69 */       this.siteManager.changeTheme(themeid);
/*     */     }
/*     */ 
/*  72 */     this.borderInstaller.install(this.productId, themeNode);
/*  73 */     InstallUtil.putMessaage("完成!");
/*     */   }
/*     */ 
/*     */   private void install(NodeList nodeList) {
/*  77 */     for (int i = 0; i < nodeList.getLength(); i++) {
/*  78 */       Node node = nodeList.item(i);
/*  79 */       if (node.getNodeType() == 1)
/*  80 */         install((Element)node);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInstaller getBorderInstaller()
/*     */   {
/*  86 */     return this.borderInstaller;
/*     */   }
/*     */ 
/*     */   public void setBorderInstaller(IInstaller borderInstaller) {
/*  90 */     this.borderInstaller = borderInstaller;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/*  94 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/*  98 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 102 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 106 */     this.siteManager = siteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ThemeInstaller
 * JD-Core Version:    0.6.0
 */