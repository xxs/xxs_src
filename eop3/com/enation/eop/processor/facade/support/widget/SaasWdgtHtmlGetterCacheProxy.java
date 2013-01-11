/*     */ package com.enation.eop.processor.facade.support.widget;
/*     */ 
/*     */ import com.enation.app.base.core.service.IWidgetCacheManager;
/*     */ import com.enation.eop.processor.widget.IWidgetHtmlGetter;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.widget.IWidget;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SaasWdgtHtmlGetterCacheProxy extends AbstractCacheProxy<Map<String, String>>
/*     */   implements IWidgetHtmlGetter
/*     */ {
/*     */   private IWidgetHtmlGetter widgetHtmlGetter;
/*     */ 
/*     */   public SaasWdgtHtmlGetterCacheProxy(IWidgetHtmlGetter _widgetHtmlGetter)
/*     */   {
/*  37 */     super("widgetCache");
/*  38 */     this.widgetHtmlGetter = _widgetHtmlGetter;
/*     */   }
/*     */ 
/*     */   private boolean getCacheOpen() {
/*  42 */     IWidgetCacheManager widgetCacheManager = (IWidgetCacheManager)SpringContextHolder.getBean("widgetCacheManager");
/*  43 */     return widgetCacheManager.isOpen();
/*     */   }
/*     */ 
/*     */   public String process(Map<String, String> params, String pageUri)
/*     */   {
/*  49 */     String widgetType = (String)params.get("type");
/*  50 */     String html = null;
/*     */ 
/*  53 */     IWidget widget = (IWidget)SpringContextHolder.getBean(widgetType);
/*  54 */     if (widget == null) {
/*  55 */       return "widget[" + widgetType + "] is null";
/*     */     }
/*     */ 
/*  61 */     if ((getCacheOpen()) && (widget.cacheAble()))
/*     */     {
/*  67 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  68 */       String site_key = "widget_" + site.getUserid() + "_" + site.getId();
/*     */ 
/*  71 */       Map htmlCache = (Map)this.cache.get(site_key);
/*     */ 
/*  74 */       if (htmlCache == null) {
/*  75 */         htmlCache = new HashMap();
/*  76 */         this.cache.put(site_key, htmlCache);
/*     */       }
/*     */ 
/*  80 */       String key = pageUri + "_" + (String)params.get("widgetid");
/*     */ 
/*  83 */       html = (String)htmlCache.get(key);
/*     */ 
/*  86 */       if (html == null)
/*     */       {
/*  90 */         html = this.widgetHtmlGetter.process(params, pageUri);
/*     */ 
/*  96 */         htmlCache.put(key, html);
/*     */       }
/*     */       else
/*     */       {
/* 103 */         widget.update(params);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 109 */       html = this.widgetHtmlGetter.process(params, pageUri);
/*     */     }
/*     */ 
/* 112 */     return html;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.SaasWdgtHtmlGetterCacheProxy
 * JD-Core Version:    0.6.0
 */