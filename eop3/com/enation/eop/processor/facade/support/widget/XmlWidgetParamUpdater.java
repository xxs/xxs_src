/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetParamUpdater;
/*    */ import com.enation.eop.processor.widget.WidgetXmlUtil;
/*    */ import com.enation.eop.resource.IThemeManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.Theme;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlWidgetParamUpdater
/*    */   implements IWidgetParamUpdater
/*    */ {
/*    */   private IThemeManager themeManager;
/*    */ 
/*    */   public void update(String pageId, List<Map<String, String>> params)
/*    */   {
/* 33 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 34 */     Theme theme = this.themeManager.getTheme(site.getThemeid());
/* 35 */     String contextPath = EopContext.getContext().getContextPath();
/* 36 */     String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/" + theme.getPath() + "/widgets.xml";
/*    */ 
/* 42 */     WidgetXmlUtil.save(path, pageId, params);
/*    */   }
/*    */ 
/*    */   public void setThemeManager(IThemeManager themeManager) {
/* 46 */     this.themeManager = themeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.XmlWidgetParamUpdater
 * JD-Core Version:    0.6.0
 */