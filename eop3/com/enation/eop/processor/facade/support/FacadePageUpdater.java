/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.IPageUpdater;
/*    */ import com.enation.eop.processor.widget.IWidgetParamUpdater;
/*    */ import com.enation.eop.processor.widget.WidgetXmlUtil;
/*    */ import com.enation.eop.resource.IThemeManager;
/*    */ import com.enation.eop.resource.IThemeUriManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.Theme;
/*    */ import com.enation.eop.resource.model.ThemeUri;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FacadePageUpdater
/*    */   implements IPageUpdater
/*    */ {
/*    */   private IThemeManager themeManager;
/*    */   private IWidgetParamUpdater widgetParamUpdater;
/*    */ 
/*    */   public void update(String uri, String pageContent, String paramJson)
/*    */   {
/* 30 */     if (uri.indexOf('?') > 0) {
/* 31 */       uri = uri.substring(0, uri.indexOf('?'));
/*    */     }
/*    */ 
/* 34 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 37 */     IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
/* 38 */     ThemeUri themuri = themeUriManager.getPath(uri);
/* 39 */     uri = themuri.getPath();
/*    */ 
/* 42 */     List mapList = WidgetXmlUtil.jsonToMapList(paramJson);
/*    */ 
/* 45 */     this.widgetParamUpdater.update(uri, mapList);
/*    */ 
/* 48 */     String themePath = this.themeManager.getTheme(site.getThemeid()).getPath();
/* 49 */     String contextPath = EopContext.getContext().getContextPath();
/* 50 */     String pagePath = EopSetting.EOP_PATH + contextPath + "/" + EopSetting.THEMES_STORAGE_PATH + "/" + themePath + "/" + uri;
/*    */ 
/* 67 */     pageContent = "<#include 'common/header.html' />" + pageContent + "<#include 'common/footer.html' />";
/* 68 */     FileUtil.write(pagePath, pageContent);
/*    */   }
/*    */ 
/*    */   public void setThemeManager(IThemeManager themeManager) {
/* 72 */     this.themeManager = themeManager;
/*    */   }
/*    */ 
/*    */   public void setWidgetParamUpdater(IWidgetParamUpdater widgetParamUpdater) {
/* 76 */     this.widgetParamUpdater = widgetParamUpdater;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.FacadePageUpdater
 * JD-Core Version:    0.6.0
 */