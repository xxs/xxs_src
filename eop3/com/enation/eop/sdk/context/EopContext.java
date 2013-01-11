/*     */ package com.enation.eop.sdk.context;
/*     */ 
/*     */ import com.enation.app.base.core.model.MultiSite;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class EopContext
/*     */ {
/*  10 */   private static ThreadLocal<HttpServletRequest> HttpRequestHolder = new ThreadLocal();
/*  11 */   private static ThreadLocal<EopContext> EopContextHolder = new ThreadLocal();
/*     */   private EopSite currentSite;
/*     */   private MultiSite currentChildSite;
/*     */ 
/*     */   public static void setContext(EopContext context)
/*     */   {
/*  14 */     EopContextHolder.set(context);
/*     */   }
/*     */ 
/*     */   public static void remove()
/*     */   {
/*  19 */     EopContextHolder.remove();
/*     */   }
/*     */ 
/*     */   public static EopContext getContext()
/*     */   {
/*  25 */     EopContext context = (EopContext)EopContextHolder.get();
/*  26 */     return context;
/*     */   }
/*     */ 
/*     */   public static void setHttpRequest(HttpServletRequest request) {
/*  30 */     HttpRequestHolder.set(request);
/*     */   }
/*     */ 
/*     */   public static HttpServletRequest getHttpRequest()
/*     */   {
/*  35 */     return (HttpServletRequest)HttpRequestHolder.get();
/*     */   }
/*     */ 
/*     */   public EopSite getCurrentSite()
/*     */   {
/*  45 */     return this.currentSite;
/*     */   }
/*     */ 
/*     */   public void setCurrentSite(EopSite site) {
/*  49 */     this.currentSite = site;
/*     */   }
/*     */ 
/*     */   public MultiSite getCurrentChildSite()
/*     */   {
/*  54 */     return this.currentChildSite;
/*     */   }
/*     */ 
/*     */   public void setCurrentChildSite(MultiSite currentChildSite) {
/*  58 */     this.currentChildSite = currentChildSite;
/*     */   }
/*     */ 
/*     */   public String getContextPath()
/*     */   {
/*  64 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  65 */       EopSite site = getCurrentSite();
/*  66 */       StringBuffer context = new StringBuffer("/user");
/*  67 */       context.append("/");
/*  68 */       context.append(site.getUserid());
/*  69 */       context.append("/");
/*  70 */       context.append(site.getId());
/*  71 */       return context.toString();
/*     */     }
/*  73 */     return "";
/*     */   }
/*     */ 
/*     */   public String getResDomain()
/*     */   {
/*  90 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  91 */     String domain = null;
/*     */ 
/*  98 */     if (("1".equals(EopSetting.RESOURCEMODE)) && (!EopSetting.DEVELOPMENT_MODEL))
/*  99 */       domain = EopSetting.IMG_SERVER_DOMAIN;
/*     */     else {
/* 101 */       domain = request.getContextPath();
/*     */     }
/*     */ 
/* 105 */     if (domain.endsWith("/")) domain = domain.substring(0, domain.length() - 1);
/*     */ 
/* 110 */     domain = domain + getContext().getContextPath();
/* 111 */     return domain;
/*     */   }
/*     */ 
/*     */   public String getResPath()
/*     */   {
/* 126 */     String path = EopSetting.IMG_SERVER_PATH;
/*     */ 
/* 129 */     if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
/*     */ 
/* 133 */     path = path + getContext().getContextPath();
/* 134 */     return path;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.context.EopContext
 * JD-Core Version:    0.6.0
 */