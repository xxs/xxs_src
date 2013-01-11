/*     */ package com.enation.eop.processor.backend.support;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.eop.processor.AbstractFacadeProcessor;
/*     */ import com.enation.eop.processor.FacadePage;
/*     */ import com.enation.eop.processor.core.Request;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.resource.IAdminThemeManager;
/*     */ import com.enation.eop.resource.model.AdminTheme;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.HttpUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class BackPageGetter extends AbstractFacadeProcessor
/*     */ {
/*     */   private IAdminThemeManager adminThemeManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */ 
/*     */   public BackPageGetter(FacadePage page)
/*     */   {
/*  29 */     super(page);
/*     */   }
/*     */ 
/*     */   protected Response process()
/*     */   {
/*  35 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  37 */     this.adminThemeManager = ((IAdminThemeManager)SpringContextHolder.getBean("adminThemeManager"));
/*  38 */     this.adminUserManager = ((IAdminUserManager)SpringContextHolder.getBean("adminUserManager"));
/*     */ 
/*  40 */     AdminTheme theme = this.adminThemeManager.get(site.getAdminthemeid());
/*  41 */     String path = "default";
/*  42 */     if (theme != null) {
/*  43 */       path = theme.getPath();
/*     */     }
/*  45 */     StringBuffer context = new StringBuffer();
/*     */ 
/*  51 */     context.append(EopSetting.ADMINTHEMES_STORAGE_PATH);
/*  52 */     context.append("/");
/*  53 */     context.append(path);
/*     */ 
/*  69 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  70 */     String contextPath = httpRequest.getContextPath();
/*     */ 
/*  73 */     httpRequest.setAttribute("context", contextPath + context.toString());
/*  74 */     httpRequest.setAttribute("title", site.getSitename());
/*  75 */     httpRequest.setAttribute("ico", site.getIcofile());
/*  76 */     httpRequest.setAttribute("logo", site.getLogofile());
/*  77 */     httpRequest.setAttribute("version", EopSetting.VERSION);
/*  78 */     httpRequest.setAttribute("bkloginpicfile", site.getBkloginpicfile());
/*  79 */     httpRequest.setAttribute("bklogo", site.getBklogofile() == null ? site.getLogofile() : site.getBklogofile());
/*     */ 
/*  81 */     String uri = this.page.getUri();
/*     */ 
/*  83 */     if (uri.startsWith("/admin/main")) {
/*  84 */       AdminUser user = this.adminUserManager.getCurrentUser();
/*  85 */       user = this.adminUserManager.get(user.getUserid());
/*  86 */       httpRequest.setAttribute("user", user);
/*  87 */       uri = context.toString() + "/main.jsp";
/*     */ 
/*  89 */       this.request = new HelpDivWrapper(this.page, this.request);
/*     */     }
/*  91 */     else if ((uri.equals("/admin/")) || (uri.equals("/admin/index.jsp")))
/*     */     {
/*  93 */       String username = HttpUtil.getCookieValue(httpRequest, "loginname");
/*  94 */       httpRequest.setAttribute("username", username);
/*  95 */       uri = context.toString() + "/login.jsp";
/*     */     }
/*     */     else {
/*  98 */       if (EopSetting.EXTENSION.equals("action")) {
/*  99 */         uri = uri.replace(".do", ".action");
/*     */       }
/*     */ 
/* 102 */       String ajax = httpRequest.getParameter("ajax");
/* 103 */       if (!"yes".equals(ajax)) {
/* 104 */         this.request = new BackTemplateWrapper(this.page, this.request);
/* 105 */         this.request = new HelpDivWrapper(this.page, this.request);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 111 */     Response response = this.request.execute(uri, this.httpResponse, httpRequest);
/*     */ 
/* 113 */     return response;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.support.BackPageGetter
 * JD-Core Version:    0.6.0
 */