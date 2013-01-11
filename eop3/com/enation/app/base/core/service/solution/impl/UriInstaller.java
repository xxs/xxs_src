/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.SiteMapUrl;
/*     */ import com.enation.app.base.core.service.ISitemapManager;
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.app.base.core.service.solution.InstallUtil;
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class UriInstaller
/*     */   implements IInstaller
/*     */ {
/*  26 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private IThemeUriManager themeUriManager;
/*     */   private ISitemapManager sitemapManager;
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  33 */     NodeList uriList = fragment.getChildNodes();
/*  34 */     InstallUtil.putMessaage("正在安装uri映射...");
/*  35 */     installUri(uriList);
/*  36 */     InstallUtil.putMessaage("uri映射安装完成!");
/*     */   }
/*     */ 
/*     */   private void installUri(NodeList nodeList)
/*     */   {
/*  47 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/*  48 */       Node node = nodeList.item(i);
/*  49 */       if (node.getNodeType() == 1)
/*  50 */         installUri((Element)node);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void installUri(Element ele)
/*     */   {
/*     */     try
/*     */     {
/*  67 */       ThemeUri themeUri = new ThemeUri();
/*     */ 
/*  69 */       themeUri.setUri(ele.getAttribute("from"));
/*  70 */       themeUri.setPath(ele.getAttribute("to"));
/*  71 */       String name = ele.getAttribute("name");
/*  72 */       String point = ele.getAttribute("point");
/*  73 */       String sitemaptype = ele.getAttribute("sitemaptype");
/*     */ 
/*  76 */       if (name != null) {
/*  77 */         themeUri.setPagename(name);
/*     */       }
/*  79 */       if (!StringUtil.isEmpty(point)) {
/*  80 */         themeUri.setPoint(Integer.valueOf(point).intValue());
/*     */       }
/*     */ 
/*  83 */       if (!StringUtil.isEmpty(sitemaptype)) {
/*  84 */         themeUri.setSitemaptype(Integer.valueOf(sitemaptype).intValue());
/*     */       }
/*     */ 
/*  87 */       this.themeUriManager.add(themeUri);
/*     */ 
/*  89 */       if ("1".equals(sitemaptype)) {
/*  90 */         SiteMapUrl url = new SiteMapUrl();
/*  91 */         url.setLoc(ele.getAttribute("from"));
/*  92 */         url.setLastmod(Long.valueOf(System.currentTimeMillis()));
/*  93 */         this.sitemapManager.addUrl(url);
/*     */       }
/*  95 */       NodeList children = ele.getChildNodes();
/*     */ 
/*  97 */       if (children != null)
/*  98 */         installUri(children);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 102 */       this.logger.error(e.getMessage(), e);
/* 103 */       e.printStackTrace();
/* 104 */       throw new RuntimeException("install uri error");
/*     */     }
/*     */   }
/*     */ 
/*     */   public IThemeUriManager getThemeUriManager()
/*     */   {
/* 110 */     return this.themeUriManager;
/*     */   }
/*     */ 
/*     */   public void setThemeUriManager(IThemeUriManager themeUriManager)
/*     */   {
/* 115 */     this.themeUriManager = themeUriManager;
/*     */   }
/*     */ 
/*     */   public ISitemapManager getSitemapManager()
/*     */   {
/* 120 */     return this.sitemapManager;
/*     */   }
/*     */ 
/*     */   public void setSitemapManager(ISitemapManager sitemapManager)
/*     */   {
/* 125 */     this.sitemapManager = sitemapManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.UriInstaller
 * JD-Core Version:    0.6.0
 */