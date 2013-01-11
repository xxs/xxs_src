/*     */ package com.enation.eop.processor.facade.support;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MultiSite;
/*     */ import com.enation.app.base.core.service.IAccessRecorder;
/*     */ import com.enation.eop.processor.IPagePaser;
/*     */ import com.enation.eop.processor.core.UrlNotFoundException;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.processor.facade.support.widget.SaasWdgtHtmlGetterCacheProxy;
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
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class FacadePagePaser
/*     */   implements IPagePaser
/*     */ {
/*     */   private IWidgetParamParser widgetParamParser;
/*     */ 
/*     */   public synchronized String pase(String uri)
/*     */   {
/*     */     try
/*     */     {
/*  54 */       return doPase(uri);
/*     */     } catch (UrlNotFoundException e) {
/*  56 */       HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
/*  57 */       httpResponse.setStatus(404);
/*  58 */     }return get404Html();
/*     */   }
/*     */ 
/*     */   public String get404Html()
/*     */   {
/*  63 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  66 */     Map widgetData = new HashMap();
/*  67 */     widgetData.put("site", site);
/*  68 */     String originalUri = "/404.html";
/*     */ 
/*  71 */     Map pages = this.widgetParamParser.parse();
/*     */ 
/*  73 */     IWidgetHtmlGetter htmlGetter = new WidgetHtmlGetter();
/*  74 */     htmlGetter = new SaasWdgtHtmlGetterCacheProxy(htmlGetter);
/*     */ 
/*  76 */     Map commonWidgets = (Map)pages.get("common");
/*  77 */     if (commonWidgets != null)
/*     */     {
/*  79 */       Set idSet = commonWidgets.keySet();
/*  80 */       for (String id : idSet) {
/*  81 */         Map params = (Map)commonWidgets.get(id);
/*  82 */         String content = htmlGetter.process(params, originalUri);
/*  83 */         widgetData.put(new StringBuilder().append("widget_").append(id).toString(), content);
/*     */       }
/*     */     }
/*  86 */     return parse(originalUri, widgetData);
/*     */   }
/*     */ 
/*     */   public String doPase(String uri) {
/*  90 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  92 */     String originalUri = uri;
/*  93 */     if (EopSetting.EXTENSION.equals("action")) {
/*  94 */       uri = uri.replace(".do", ".action");
/*     */     }
/*     */ 
/*  97 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  98 */     if ((site.getIsimported() == 1) && (site.getSitestate() == 0)) {
/*  99 */       long now = DateUtil.getDatelineLong();
/* 100 */       int day_7 = 604800;
/* 101 */       if (site.getCreatetime().longValue() + day_7 < now) {
/* 102 */         return getOverdueHtml(site);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 107 */     if (site.getSiteon().intValue() == 1) {
/* 108 */       return site.getClosereson() == null ? "" : site.getClosereson();
/*     */     }
/*     */ 
/* 112 */     Map widgetData = new HashMap();
/* 113 */     widgetData.put("site", site);
/* 114 */     String mode = "no";
/*     */ 
/* 117 */     if (uri.indexOf("?mode") > 0) {
/* 118 */       mode = "edit";
/*     */     }
/*     */ 
/* 122 */     if (uri.indexOf(63) > 0) {
/* 123 */       uri = uri.substring(0, uri.indexOf(63));
/*     */     }
/*     */ 
/* 128 */     IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
/*     */ 
/* 130 */     ThemeUri themeUri = themeUriManager.getPath(uri);
/*     */ 
/* 132 */     String tplFileName = themeUri.getPath();
/* 133 */     String pageid = tplFileName.substring(1, tplFileName.indexOf("."));
/* 134 */     request.setAttribute("pageid", pageid);
/* 135 */     request.setAttribute("tplFileName", pageid);
/*     */ 
/* 137 */     if ((!StringUtil.isEmpty(themeUri.getPagename())) || (!StringUtil.isEmpty(themeUri.getKeywords())) || (!StringUtil.isEmpty(themeUri.getDescription()))) {
/* 138 */       FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/* 140 */       if (!StringUtil.isEmpty(themeUri.getPagename())) {
/* 141 */         freeMarkerPaser.putData("pagetitle", new StringBuilder().append(themeUri.getPagename()).append("-").append(StringUtil.isEmpty(site.getTitle()) ? site.getSitename() : site.getTitle()).toString());
/*     */       }
/*     */ 
/* 145 */       if (!StringUtil.isEmpty(themeUri.getKeywords())) {
/* 146 */         freeMarkerPaser.putData("keywords", themeUri.getKeywords());
/*     */       }
/* 148 */       if (!StringUtil.isEmpty(themeUri.getDescription()))
/* 149 */         freeMarkerPaser.putData("description", themeUri.getDescription());
/*     */     }
/*     */     int result;
/* 152 */     if (EopSetting.RUNMODE.equals("2"))
/*     */     {
/* 154 */       IAccessRecorder accessRecorder = (IAccessRecorder)SpringContextHolder.getBean("accessRecorder");
/* 155 */       result = accessRecorder.record(themeUri);
/*     */     }
/*     */ 
/* 160 */     Map pages = this.widgetParamParser.parse();
/*     */ 
/* 164 */     Map widgets = (Map)pages.get(tplFileName);
/*     */ 
/* 166 */     IWidgetHtmlGetter htmlGetter = new WidgetHtmlGetter();
/* 167 */     htmlGetter = new SaasWdgtHtmlGetterCacheProxy(htmlGetter);
/*     */ 
/* 169 */     String ajax = request.getParameter("ajax");
/*     */ 
/* 171 */     if (widgets != null)
/*     */     {
/* 174 */       String widgetid = request.getParameter("widgetid");
/* 175 */       if (("yes".equals(ajax)) && (!StringUtil.isEmpty(widgetid))) {
/* 176 */         Map wgtParams = (Map)widgets.get(widgetid);
/* 177 */         String content = htmlGetter.process(wgtParams, originalUri);
/* 178 */         return content;
/*     */       }
/*     */ 
/* 182 */       Map mainparams = (Map)widgets.get("main");
/* 183 */       if (mainparams != null)
/*     */       {
/* 185 */         String content = htmlGetter.process(mainparams, originalUri);
/* 186 */         widgetData.put(new StringBuilder().append("widget_").append((String)mainparams.get("widgetid")).toString(), content);
/* 187 */         widgets.remove("main");
/*     */       }
/*     */ 
/* 190 */       Set idSet = widgets.keySet();
/*     */ 
/* 192 */       for (String id : idSet)
/*     */       {
/* 200 */         boolean isCurrUrl = matchUrl(uri, id);
/*     */ 
/* 202 */         if (((tplFileName.equals("/default.html")) || (tplFileName.equals("/member.html"))) && (id.startsWith("/")) && (!isCurrUrl))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 211 */         Map params = (Map)widgets.get(id);
/* 212 */         params.put("mode", mode);
/* 213 */         String content = htmlGetter.process(params, originalUri);
/*     */ 
/* 215 */         if (((tplFileName.equals("/default.html")) || (tplFileName.equals("/member.html"))) && (id.startsWith("/")) && (isCurrUrl)) {
/* 216 */           request.setAttribute("pageid", params.get("type"));
/* 217 */           widgetData.put("widget_main", content);
/*     */         } else {
/* 219 */           widgetData.put(new StringBuilder().append("widget_").append(id).toString(), content);
/*     */         }
/*     */       }
/*     */     }
/*     */     Map commonWidgets;
/* 224 */     if (!"yes".equals(ajax))
/*     */     {
/* 226 */       commonWidgets = (Map)pages.get("common");
/* 227 */       if (commonWidgets != null)
/*     */       {
/* 229 */         Set idSet = commonWidgets.keySet();
/* 230 */         for (String id : idSet) {
/* 231 */           Map params = (Map)commonWidgets.get(id);
/* 232 */           String content = htmlGetter.process(params, originalUri);
/* 233 */           widgetData.put(new StringBuilder().append("widget_").append(id).toString(), content);
/*     */         }
/*     */       }
/*     */     }
/* 237 */     return parse(tplFileName, widgetData);
/*     */   }
/*     */ 
/*     */   public String parse(String tplFileName, Map<String, Object> widgetData)
/*     */   {
/*     */     try {
/* 243 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 244 */       IThemeManager themeManager = (IThemeManager)SpringContextHolder.getBean("themeManager");
/* 245 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 246 */       Integer themeid = null;
/*     */ 
/* 248 */       if (site.getMulti_site().intValue() == 1) {
/* 249 */         MultiSite childSite = EopContext.getContext().getCurrentChildSite();
/* 250 */         themeid = childSite.getThemeid();
/*     */       } else {
/* 252 */         themeid = site.getThemeid();
/*     */       }
/*     */ 
/* 255 */       Theme theme = themeManager.getTheme(themeid);
/* 256 */       String themePath = theme.getPath();
/*     */ 
/* 259 */       String contextPath = EopContext.getContext().getContextPath();
/* 260 */       String themeFld = new StringBuilder().append(EopSetting.EOP_PATH).append(contextPath).append(EopSetting.THEMES_STORAGE_PATH).append("/").append(themePath).toString();
/*     */ 
/* 264 */       StringBuffer context = new StringBuffer();
/*     */ 
/* 267 */       if (EopSetting.RESOURCEMODE.equals("1")) {
/* 268 */         context.append(EopSetting.IMG_SERVER_DOMAIN);
/*     */       }
/* 270 */       if (EopSetting.RESOURCEMODE.equals("2")) {
/* 271 */         if ("/".equals(EopSetting.CONTEXT_PATH))
/* 272 */           context.append("");
/*     */         else {
/* 274 */           context.append(EopSetting.CONTEXT_PATH);
/*     */         }
/*     */       }
/* 277 */       context.append(contextPath);
/* 278 */       context.append(EopSetting.THEMES_STORAGE_PATH);
/* 279 */       context.append("/");
/* 280 */       context.append(new StringBuilder().append(themePath).append("/").toString());
/* 281 */       widgetData.put("context", context.toString());
/* 282 */       widgetData.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 283 */       widgetData.put("logo", site.getLogofile());
/* 284 */       widgetData.put("ctx", request.getContextPath());
/* 285 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 286 */       widgetData.put("member", member);
/*     */ 
/* 290 */       Object params_temp = request.getAttribute("eop_page_params");
/*     */ 
/* 292 */       if (params_temp != null) {
/* 293 */         widgetData.putAll((Map)params_temp);
/*     */       }
/*     */ 
/* 297 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
/* 298 */       Template temp = cfg.getTemplate(tplFileName);
/* 299 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 301 */       Writer out = new OutputStreamWriter(stream);
/* 302 */       temp.process(widgetData, out);
/*     */ 
/* 304 */       out.flush();
/* 305 */       String html = stream.toString();
/*     */ 
/* 314 */       return html;
/*     */     } catch (Exception e) {
/* 316 */       e.printStackTrace();
/* 317 */     }return "page pase error";
/*     */   }
/*     */ 
/*     */   private boolean matchUrl(String uri, String targetUri)
/*     */   {
/* 323 */     Pattern p = Pattern.compile(targetUri, 34);
/* 324 */     Matcher m = p.matcher(uri);
/* 325 */     return m.find();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 330 */     String url = "/goods-1.html";
/* 331 */     if (url.indexOf(63) > 0)
/* 332 */       url = url.substring(0, url.indexOf(63));
/*     */   }
/*     */ 
/*     */   public void setWidgetParamParser(IWidgetParamParser widgetParamParser) {
/* 336 */     this.widgetParamParser = widgetParamParser;
/*     */   }
/*     */ 
/*     */   private String getWidgetHtml(String themePath, EopSite site) {
/* 340 */     String contextPath = EopContext.getContext().getContextPath();
/*     */     try {
/* 342 */       String themeFld = new StringBuilder().append(EopSetting.EOP_PATH).append("/user/1/1").append(EopSetting.THEMES_STORAGE_PATH).append("/default/").toString();
/*     */ 
/* 344 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
/* 345 */       Template temp = cfg.getTemplate("gameover.html");
/* 346 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 348 */       Writer out = new OutputStreamWriter(stream);
/* 349 */       Map map = new HashMap();
/* 350 */       map.put("site", site);
/* 351 */       map.put("content", "");
/* 352 */       temp.process(map, out);
/*     */ 
/* 354 */       out.flush();
/* 355 */       String html = stream.toString();
/*     */ 
/* 357 */       return html;
/*     */     } catch (Exception e) {
/*     */     }
/* 360 */     return new StringBuilder().append("挂件解析出错").append(e.getMessage()).toString();
/*     */   }
/*     */ 
/*     */   private String getOverdueHtml(EopSite site)
/*     */   {
/*     */     try {
/* 366 */       String themeFld = new StringBuilder().append(EopSetting.EOP_PATH).append("/user/1/1").append(EopSetting.THEMES_STORAGE_PATH).append("/default/").toString();
/*     */ 
/* 368 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
/* 369 */       Template temp = cfg.getTemplate("overdue.html");
/* 370 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 372 */       Writer out = new OutputStreamWriter(stream);
/* 373 */       Map map = new HashMap();
/* 374 */       map.put("site", site);
/* 375 */       temp.process(map, out);
/*     */ 
/* 377 */       out.flush();
/* 378 */       String html = stream.toString();
/*     */ 
/* 380 */       return html;
/*     */     } catch (Exception e) {
/*     */     }
/* 383 */     return new StringBuilder().append("挂件解析出错").append(e.getMessage()).toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.FacadePagePaser
 * JD-Core Version:    0.6.0
 */