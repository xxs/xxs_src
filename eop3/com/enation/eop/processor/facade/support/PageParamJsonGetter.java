/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.IPageParamJsonGetter;
/*    */ import com.enation.eop.processor.widget.IWidgetParamParser;
/*    */ import com.enation.eop.processor.widget.WidgetXmlUtil;
/*    */ import com.enation.eop.resource.IThemeManager;
/*    */ import com.enation.eop.resource.IThemeUriManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.ThemeUri;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PageParamJsonGetter
/*    */   implements IPageParamJsonGetter
/*    */ {
/*    */   private IWidgetParamParser widgetParamParser;
/*    */   private IThemeManager themeManager;
/*    */ 
/*    */   public String getJson(String uri)
/*    */   {
/* 29 */     if (uri.indexOf('?') > 0) {
/* 30 */       uri = uri.substring(0, uri.indexOf('?'));
/*    */     }
/*    */ 
/* 33 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 37 */     IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
/* 38 */     ThemeUri themeUri = themeUriManager.getPath(uri);
/* 39 */     uri = themeUri.getPath();
/*    */ 
/* 42 */     Map pages = this.widgetParamParser.parse();
/*    */ 
/* 46 */     Map params = (Map)pages.get(uri);
/* 47 */     String json = WidgetXmlUtil.mapToJson(params);
/* 48 */     json = "{'pageId':'" + uri + "',params:" + json + "}";
/* 49 */     return json;
/*    */   }
/*    */ 
/*    */   public void setWidgetParamParser(IWidgetParamParser widgetParamParser)
/*    */   {
/* 54 */     this.widgetParamParser = widgetParamParser;
/*    */   }
/*    */ 
/*    */   public void setThemeManager(IThemeManager themeManager)
/*    */   {
/* 59 */     this.themeManager = themeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.PageParamJsonGetter
 * JD-Core Version:    0.6.0
 */