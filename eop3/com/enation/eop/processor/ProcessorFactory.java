/*     */ package com.enation.eop.processor;
/*     */ 
/*     */ import com.enation.eop.processor.backend.BackgroundProcessor;
/*     */ import com.enation.eop.processor.facade.FacadePageProcessor;
/*     */ import com.enation.eop.processor.facade.ResourceProcessor;
/*     */ import com.enation.eop.processor.facade.SiteMapProcessor;
/*     */ import com.enation.eop.processor.facade.WebResourceProcessor;
/*     */ import com.enation.eop.processor.facade.WidgetProcessor;
/*     */ import com.enation.eop.processor.facade.WidgetSettingProcessor;
/*     */ import com.enation.eop.resource.IAppManager;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public abstract class ProcessorFactory
/*     */ {
/*     */   public static Processor newProcessorInstance(String uri, HttpServletRequest httpRequest)
/*     */   {
/*  32 */     Processor processor = null;
/*     */ 
/*  34 */     if (uri.startsWith("/statics")) return null;
/*  35 */     if ((uri.startsWith("/install")) && (!uri.startsWith("/install.html"))) return null;
/*     */ 
/*  38 */     if (uri.toLowerCase().equals("/sitemap.xml")) {
/*  39 */       return new SiteMapProcessor();
/*     */     }
/*     */ 
/*  42 */     if (uri.toLowerCase().equals("/robots.txt")) return null;
/*     */ 
/*  44 */     IAppManager appManager = (IAppManager)SpringContextHolder.getBean("appManager");
/*  45 */     List appList = appManager.list();
/*  46 */     String path = httpRequest.getServletPath();
/*  47 */     for (EopApp app : appList) {
/*  48 */       if (app.getDeployment() == 1)
/*     */         continue;
/*  50 */       if (path.startsWith(app.getPath() + "/admin")) {
/*  51 */         if (isExinclude(path)) return null;
/*     */ 
/*  53 */         processor = new BackgroundProcessor();
/*  54 */         return processor;
/*     */       }
/*  56 */       if (path.startsWith(app.getPath())) {
/*  57 */         return null;
/*     */       }
/*     */     }
/*     */ 
/*  61 */     if (uri.startsWith("/validcode")) return null;
/*  62 */     if (uri.startsWith("/commons")) return null;
/*  63 */     if (uri.startsWith("/editor/")) return null;
/*  64 */     if (uri.startsWith("/eop/")) return null;
/*  65 */     if (uri.startsWith("/test/")) return null;
/*  66 */     if (uri.endsWith("favicon.ico")) return null;
/*     */ 
/*  68 */     if (uri.indexOf("/headerresource") >= 0) {
/*  69 */       return new ResourceProcessor();
/*     */     }
/*  71 */     if (uri.startsWith("/resource/")) {
/*  72 */       return new WebResourceProcessor();
/*     */     }
/*     */ 
/*  75 */     if (isExinclude(uri)) return null;
/*     */ 
/*  77 */     if (uri.startsWith("/admin/")) {
/*  78 */       if (!uri.startsWith("/admin/themes/"))
/*  79 */         processor = new BackgroundProcessor();
/*     */     }
/*  81 */     else if (uri.startsWith("/widget"))
/*     */     {
/*  83 */       if (uri.startsWith("/widgetSetting/"))
/*  84 */         processor = new WidgetSettingProcessor();
/*  85 */       else if (!uri.startsWith("/widgetBundle/"))
/*     */       {
/*  88 */         processor = new WidgetProcessor();
/*     */       }
/*     */     }
/*     */     else {
/*  92 */       if ((uri.endsWith(".action")) || (uri.endsWith(".do"))) return null;
/*  93 */       if (EopSetting.TEMPLATEENGINE.equals("on")) {
/*  94 */         processor = new FacadePageProcessor();
/*     */       }
/*     */     }
/*  97 */     return processor;
/*     */   }
/*     */ 
/*     */   private static boolean isExinclude(String uri)
/*     */   {
/* 104 */     String[] exts = { "jpg", "gif", "js", "png", "css", "doc", "xls", "swf" };
/* 105 */     for (String ext : exts) {
/* 106 */       if (uri.toUpperCase().endsWith(ext.toUpperCase())) {
/* 107 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 111 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.ProcessorFactory
 * JD-Core Version:    0.6.0
 */