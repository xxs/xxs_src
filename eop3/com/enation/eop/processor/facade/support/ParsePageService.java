/*     */ package com.enation.eop.processor.facade.support;
/*     */ 
/*     */ import com.enation.eop.processor.facade.support.widget.WidgetHtmlGetter;
/*     */ import com.enation.eop.processor.widget.IWidgetHtmlGetter;
/*     */ import com.enation.eop.processor.widget.IWidgetParamParser;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ParsePageService
/*     */ {
/*     */   private IWidgetParamParser widgetParamParser;
/*     */   private IThemeManager themeManager;
/*     */   private IThemeUriManager themeUriManager;
/*     */ 
/*     */   public void parse()
/*     */   {
/*  26 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  27 */     String uri = RequestUtil.getRequestUrl(request);
/*  28 */     pase(uri);
/*     */   }
/*     */ 
/*     */   private void putData(String key, String value)
/*     */   {
/*  38 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  39 */     request.setAttribute(key, value);
/*     */   }
/*     */ 
/*     */   public void pase(String uri)
/*     */   {
/*  45 */     String mode = "no";
/*     */ 
/*  47 */     if (uri.indexOf("?mode") > 0) {
/*  48 */       mode = "edit";
/*     */     }
/*     */ 
/*  52 */     if (uri.indexOf(63) > 0) {
/*  53 */       uri = uri.substring(0, uri.indexOf(63));
/*     */     }
/*     */ 
/*  56 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  57 */     Theme theme = this.themeManager.getTheme(site.getThemeid());
/*  58 */     String themePath = theme.getPath();
/*     */ 
/*  60 */     ThemeUri themeUri = this.themeUriManager.getPath(uri);
/*  61 */     String tplFileName = themeUri.getPath();
/*     */ 
/*  65 */     Map pages = this.widgetParamParser.parse();
/*     */ 
/*  74 */     Map widgets = (Map)pages.get(tplFileName);
/*     */     IWidgetHtmlGetter htmlGetter;
/*  76 */     if (widgets != null)
/*     */     {
/*  78 */       htmlGetter = new WidgetHtmlGetter();
/*     */ 
/*  82 */       Map mainparams = (Map)widgets.get("main");
/*  83 */       if (mainparams != null) {
/*  84 */         String content = htmlGetter.process(mainparams, tplFileName);
/*  85 */         putData("widget_" + (String)mainparams.get("id"), content);
/*  86 */         widgets.remove("main");
/*     */       }
/*     */ 
/*  89 */       Set idSet = widgets.keySet();
/*     */ 
/*  91 */       for (String id : idSet) {
/*  92 */         Map params = (Map)widgets.get(id);
/*  93 */         params.put("mode", mode);
/*     */ 
/*  95 */         boolean isCurrUrl = matchUrl(uri, id);
/*     */ 
/*  98 */         if ((tplFileName.equals("/default.html")) && (id.startsWith("/")) && (!isCurrUrl))
/*     */         {
/*     */           continue;
/*     */         }
/* 102 */         String content = htmlGetter.process(params, tplFileName);
/*     */ 
/* 104 */         if ((tplFileName.equals("/default.html")) && (id.startsWith("/")) && (isCurrUrl))
/* 105 */           putData("widget_main", content);
/*     */         else {
/* 107 */           putData("widget_" + id, content);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     Map commonWidgets = (Map)pages.get("common");
/*     */     IWidgetHtmlGetter htmlGetter;
/* 115 */     if (commonWidgets != null) {
/* 116 */       htmlGetter = new WidgetHtmlGetter();
/* 117 */       Set idSet = commonWidgets.keySet();
/* 118 */       for (String id : idSet) {
/* 119 */         Map params = (Map)commonWidgets.get(id);
/* 120 */         String content = htmlGetter.process(params, tplFileName);
/* 121 */         putData("widget_" + id, content);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 129 */       StringBuffer context = new StringBuffer();
/*     */ 
/* 132 */       if (EopSetting.RESOURCEMODE.equals("1")) {
/* 133 */         context.append(EopSetting.IMG_SERVER_DOMAIN);
/*     */       }
/* 135 */       if (EopSetting.RESOURCEMODE.equals("2")) {
/* 136 */         context.append(EopSetting.CONTEXT_PATH);
/*     */       }
/* 138 */       String contextPath = EopContext.getContext().getContextPath();
/* 139 */       context.append(contextPath);
/* 140 */       context.append(EopSetting.THEMES_STORAGE_PATH);
/* 141 */       context.append("/");
/* 142 */       context.append(themePath + "/");
/* 143 */       putData("context", context.toString());
/* 144 */       putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 149 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean matchUrl(String uri, String targetUri)
/*     */   {
/* 156 */     Pattern p = Pattern.compile(targetUri, 34);
/* 157 */     Matcher m = p.matcher(uri);
/* 158 */     return m.find();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 163 */     String url = "/goods-1.html";
/* 164 */     if (url.indexOf(63) > 0)
/* 165 */       url = url.substring(0, url.indexOf(63));
/* 166 */     System.out.println(url);
/*     */   }
/*     */ 
/*     */   public void setWidgetParamParser(IWidgetParamParser widgetParamParser) {
/* 170 */     this.widgetParamParser = widgetParamParser;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/* 174 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public IThemeUriManager getThemeUriManager() {
/* 178 */     return this.themeUriManager;
/*     */   }
/*     */ 
/*     */   public void setThemeUriManager(IThemeUriManager themeUriManager) {
/* 182 */     this.themeUriManager = themeUriManager;
/*     */   }
/*     */ 
/*     */   public IWidgetParamParser getWidgetParamParser() {
/* 186 */     return this.widgetParamParser;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/* 190 */     return this.themeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.ParsePageService
 * JD-Core Version:    0.6.0
 */