/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetParamParser;
/*    */ import com.enation.eop.processor.widget.WidgetXmlUtil;
/*    */ import com.enation.eop.resource.IThemeManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.Theme;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlWidgetParamParser
/*    */   implements IWidgetParamParser
/*    */ {
/*    */   private IThemeManager themeManager;
/*    */ 
/*    */   public Map<String, Map<String, Map<String, String>>> parse()
/*    */   {
/* 24 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 25 */     Theme theme = this.themeManager.getTheme(site.getThemeid());
/* 26 */     String contextPath = EopContext.getContext().getContextPath();
/* 27 */     String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/" + theme.getPath() + "/widgets.xml";
/*    */ 
/* 33 */     return WidgetXmlUtil.parse(path);
/*    */   }
/*    */   public void setThemeManager(IThemeManager themeManager) {
/* 36 */     this.themeManager = themeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.XmlWidgetParamParser
 * JD-Core Version:    0.6.0
 */