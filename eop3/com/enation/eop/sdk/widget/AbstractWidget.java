/*     */ package com.enation.eop.sdk.widget;
/*     */ 
/*     */ import com.enation.app.base.component.widget.nav.Nav;
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class AbstractWidget extends BaseSupport
/*     */   implements IWidget
/*     */ {
/*  36 */   protected boolean showHtml = true;
/*     */   protected FreeMarkerPaser freeMarkerPaser;
/*     */   private Map<String, String> urls;
/*     */   protected String folder;
/*  40 */   protected boolean disableCustomPage = false;
/*     */   protected String action;
/*  42 */   private boolean enable = true;
/*     */   protected String pageName;
/*     */   private Map<String, String> actionPageMap;
/*     */ 
/*     */   public String process(Map<String, String> params)
/*     */   {
/*  57 */     this.actionPageMap = new HashMap();
/*     */ 
/*  59 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  61 */     String mustbelogin = (String)params.get("mustbelogin");
/*  62 */     if ("yes".equals(mustbelogin)) {
/*  63 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  64 */       if (member == null) {
/*  65 */         String forward = RequestUtil.getRequestUrl(request);
/*  66 */         if (!StringUtil.isEmpty(forward)) {
/*     */           try {
/*  68 */             if (forward.startsWith("/")) forward = forward.substring(1, forward.length());
/*  69 */             forward = URLEncoder.encode(forward, "UTF-8");
/*     */           }
/*     */           catch (UnsupportedEncodingException e) {
/*  72 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */ 
/*  76 */         HttpServletResponse response = ThreadContextHolder.getHttpResponse();
/*     */         try {
/*  78 */           response.sendRedirect("login.html?forward=" + forward);
/*     */         }
/*     */         catch (IOException e) {
/*  81 */           e.printStackTrace();
/*     */         }
/*  83 */         return null;
/*     */       }
/*     */     }
/*     */ 
/*  87 */     this.action = request.getParameter("action");
/*     */ 
/*  90 */     String html = show(params);
/*  91 */     return html;
/*     */   }
/*     */ 
/*     */   public void update(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean cacheAble()
/*     */   {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   private void putRequestParam(String reqparams, Map<String, String> params)
/*     */   {
/* 117 */     if (!StringUtil.isEmpty(reqparams)) {
/* 118 */       HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/* 119 */       String[] reqparamArray = StringUtils.split(reqparams, ",");
/* 120 */       for (String paramname : reqparamArray) {
/* 121 */         String value = httpRequest.getParameter(paramname);
/* 122 */         params.put(paramname, value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String show(Map<String, String> params)
/*     */   {
/* 137 */     this.freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 138 */     this.freeMarkerPaser.setClz(getClass());
/* 139 */     this.freeMarkerPaser.setPageFolder(null);
/* 140 */     this.freeMarkerPaser.setPageName(null);
/*     */ 
/* 148 */     String reqparams = (String)params.get("reqparams");
/* 149 */     putRequestParam(reqparams, params);
/*     */ 
/* 151 */     this.freeMarkerPaser.putData(params);
/*     */ 
/* 160 */     String customPage = (String)params.get("custom_page");
/* 161 */     this.folder = ((String)params.get("folder"));
/*     */ 
/* 164 */     String showHtmlStr = (String)params.get("showhtml");
/* 165 */     this.showHtml = true;
/*     */ 
/* 175 */     this.disableCustomPage = false;
/* 176 */     display(params);
/*     */ 
/* 178 */     if (!this.disableCustomPage)
/*     */     {
/* 181 */       if (!StringUtil.isEmpty(customPage)) {
/* 182 */         this.pageName = customPage;
/*     */       }
/*     */ 
/* 187 */       if ("yes".equals(params.get("actionpage"))) {
/* 188 */         if (!StringUtil.isEmpty(this.action)) {
/* 189 */           this.pageName = (customPage + "_" + this.action);
/*     */         }
/*     */       }
/* 192 */       else if (!StringUtil.isEmpty(this.action)) {
/* 193 */         String actionPage = (String)params.get("action_" + this.action);
/* 194 */         if (StringUtil.isEmpty(actionPage)) {
/* 195 */           actionPage = (String)this.actionPageMap.get(this.action);
/*     */         }
/* 197 */         if (!StringUtil.isEmpty(actionPage)) {
/* 198 */           this.pageName = actionPage;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 203 */       if (!StringUtil.isEmpty(this.pageName)) {
/* 204 */         this.freeMarkerPaser.setPageName(this.pageName);
/*     */       }
/*     */ 
/* 210 */       if (!StringUtil.isEmpty(this.folder)) {
/* 211 */         EopSite site = EopContext.getContext().getCurrentSite();
/* 212 */         String contextPath = EopContext.getContext().getContextPath();
/* 213 */         this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath() + "/" + this.folder);
/*     */       }
/* 215 */       else if (!StringUtil.isEmpty(customPage)) {
/* 216 */         EopSite site = EopContext.getContext().getCurrentSite();
/* 217 */         String contextPath = EopContext.getContext().getContextPath();
/* 218 */         this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 226 */     if ((!StringUtil.isEmpty(showHtmlStr)) && (showHtmlStr.equals("false"))) {
/* 227 */       this.showHtml = false;
/*     */     }
/*     */ 
/* 231 */     if ((this.showHtml) || ("yes".equals(params.get("ischild"))))
/*     */     {
/* 233 */       String html = this.freeMarkerPaser.proessPageContent();
/* 234 */       if ("yes".equals(params.get("ischild"))) {
/* 235 */         putData("widget_" + (String)params.get("widgetid"), html);
/*     */       }
/* 237 */       return html;
/*     */     }
/* 239 */     return "";
/*     */   }
/*     */ 
/*     */   protected String getThemePath()
/*     */   {
/* 249 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 250 */     String contextPath = EopContext.getContext().getContextPath();
/* 251 */     return contextPath + "/themes/" + site.getThemepath();
/*     */   }
/*     */ 
/*     */   protected void disableCustomPage()
/*     */   {
/* 257 */     this.disableCustomPage = true;
/*     */   }
/*     */   protected void enableCustomPage() {
/* 260 */     this.disableCustomPage = false;
/*     */   }
/*     */ 
/*     */   public String setting(Map<String, String> params) {
/* 264 */     this.freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 265 */     this.freeMarkerPaser.setClz(getClass());
/* 266 */     config(params);
/*     */ 
/* 268 */     if (this.showHtml) {
/* 269 */       return this.freeMarkerPaser.proessPageContent();
/*     */     }
/* 271 */     return "";
/*     */   }
/*     */ 
/*     */   protected abstract void display(Map<String, String> paramMap);
/*     */ 
/*     */   protected abstract void config(Map<String, String> paramMap);
/*     */ 
/*     */   protected void putData(String key, Object value)
/*     */   {
/* 308 */     this.freeMarkerPaser.putData(key, value);
/*     */   }
/*     */ 
/*     */   protected void putData(Map<String, Object> map)
/*     */   {
/* 319 */     this.freeMarkerPaser.putData(map);
/*     */   }
/*     */ 
/*     */   protected Object getData(String key)
/*     */   {
/* 326 */     return this.freeMarkerPaser.getData(key);
/*     */   }
/*     */ 
/*     */   protected void setPathPrefix(String path)
/*     */   {
/* 334 */     this.freeMarkerPaser.setPathPrefix(path);
/*     */   }
/*     */ 
/*     */   public void setPageName(String pageName)
/*     */   {
/* 346 */     this.disableCustomPage = false;
/* 347 */     this.pageName = pageName;
/*     */   }
/*     */ 
/*     */   public void setActionPageName(String pageName)
/*     */   {
/* 353 */     this.disableCustomPage = false;
/* 354 */     this.actionPageMap.put(this.action, pageName);
/*     */   }
/*     */ 
/*     */   public void makeSureSetPageName(String pageName)
/*     */   {
/* 364 */     this.freeMarkerPaser.setPageName(pageName);
/*     */   }
/*     */ 
/*     */   public void setPageExt(String pageExt)
/*     */   {
/* 373 */     this.freeMarkerPaser.setPageExt(pageExt);
/*     */   }
/*     */ 
/*     */   public void setPageFolder(String pageFolder) {
/* 377 */     this.freeMarkerPaser.setPageFolder(pageFolder);
/*     */   }
/*     */ 
/*     */   protected void putNav(Nav nav)
/*     */   {
/* 385 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 386 */     List navList = (List)request.getAttribute("site_nav_list");
/* 387 */     navList = navList == null ? new ArrayList() : navList;
/* 388 */     navList.add(nav);
/* 389 */     request.setAttribute("site_nav_list", navList);
/*     */   }
/*     */ 
/*     */   protected void setMsg(String msg)
/*     */   {
/* 398 */     putData("msg", msg);
/*     */   }
/*     */ 
/*     */   protected void putUrl(String text, String url)
/*     */   {
/* 408 */     if (this.urls == null) {
/* 409 */       this.urls = new HashMap();
/*     */     }
/* 411 */     this.urls.put(text, url);
/* 412 */     putData("urls", this.urls);
/* 413 */     putData("jumpurl", url);
/*     */   }
/*     */ 
/*     */   protected void showError(String msg)
/*     */   {
/* 421 */     disableCustomPage();
/* 422 */     setPageFolder(getThemePath());
/* 423 */     this.freeMarkerPaser.setPageName("error");
/* 424 */     setMsg(msg);
/*     */   }
/*     */ 
/*     */   protected void showJson(String json)//------------------------
/*     */   {
/* 429 */     disableCustomPage();
/* 430 */     setPageFolder("/commons/");
/* 431 */     this.freeMarkerPaser.setPageName("json");
/* 432 */     putData("json", json);
/*     */   }
/*     */ 
/*     */   protected void showErrorJson(String message)
/*     */   {
/* 437 */     showJson("{result:0,message:'" + message + "'}");
/*     */   }
/*     */ 
/*     */   protected void showSuccessJson(String message) {
/* 441 */     showJson("{result:1,message:'" + message + "'}");
/*     */   }
/*     */ 
/*     */   protected void showError(String msg, String urlText, String url)
/*     */   {
/* 451 */     disableCustomPage();
/* 452 */     this.pageName = null;
/* 453 */     setPageFolder(getThemePath());
/* 454 */     this.freeMarkerPaser.setPageName("error");
/* 455 */     setMsg(msg);
/* 456 */     if ((urlText != null) && (url != null))
/* 457 */       putUrl(urlText, url);
/*     */   }
/*     */ 
/*     */   protected void showSuccess(String msg)
/*     */   {
/* 465 */     disableCustomPage();
/* 466 */     this.pageName = null;
/* 467 */     setPageFolder(getThemePath());
/* 468 */     this.freeMarkerPaser.setPageName("success");
/* 469 */     setMsg(msg);
/*     */   }
/*     */ 
/*     */   protected void showSuccess(String msg, String urlText, String url)
/*     */   {
/* 481 */     disableCustomPage();
/* 482 */     setPageFolder(getThemePath());
/* 483 */     this.freeMarkerPaser.setPageName("success");
/* 484 */     setMsg(msg);
/* 485 */     if ((urlText != null) && (url != null))
/* 486 */       putUrl(urlText, url);
/*     */   }
/*     */ 
/*     */   protected void putData2(String key, Object value)
/*     */   {
/* 496 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 497 */     Object params_temp = request.getAttribute("eop_page_params");
/*     */ 
/* 499 */     Map pageParams = null;
/*     */ 
/* 501 */     if (params_temp == null) pageParams = new HashMap(); else {
/* 502 */       pageParams = (Map)params_temp;
/*     */     }
/* 504 */     pageParams.put(key, value);
/* 505 */     request.setAttribute("eop_page_params", pageParams);
/*     */   }
/*     */ 
/*     */   protected int getPageNo()
/*     */   {
/* 513 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 514 */     String page = request.getParameter("page");
/* 515 */     return StringUtil.toInt(page, 1);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.widget.AbstractWidget
 * JD-Core Version:    0.6.0
 */