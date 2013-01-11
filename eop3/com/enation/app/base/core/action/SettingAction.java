/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class SettingAction extends WWAction
/*     */ {
/*     */   private String showtab;
/*     */   private ISettingService settingService;
/*     */   private SettingPluginBundle settingPluginBundle;
/*     */   private Map<Integer, String> htmls;
/*     */   private String[] codes;
/*     */   private String[] cfg_values;
/*     */   private Map tabs;
/*     */   public static final String SETTING_PAGE_ID = "setting_input";
/*     */ 
/*     */   public Map getTabs()
/*     */   {
/*  33 */     return this.tabs;
/*     */   }
/*     */ 
/*     */   public String edit_input()
/*     */   {
/*  43 */     Map settings = this.settingService.getSetting();
/*  44 */     this.htmls = this.settingPluginBundle.onInputShow(settings);
/*  45 */     this.tabs = this.settingPluginBundle.getTabs();
/*     */ 
/*  47 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*  55 */     HttpServletRequest request = getRequest();
/*  56 */     Enumeration names = request.getParameterNames();
/*  57 */     Map settings = new HashMap();
/*     */ 
/*  59 */     while (names.hasMoreElements())
/*     */     {
/*  62 */       String name = (String)names.nextElement();
/*  63 */       String[] name_ar = name.split("\\.");
/*  64 */       if (name_ar.length != 2)
/*     */         continue;
/*  66 */       String groupName = name_ar[0];
/*  67 */       String paramName = name_ar[1];
/*  68 */       String paramValue = request.getParameter(name);
/*     */ 
/*  70 */       Map params = (Map)settings.get(groupName);
/*  71 */       if (params == null) {
/*  72 */         params = new HashMap();
/*  73 */         settings.put(groupName, params);
/*     */       }
/*  75 */       params.put(paramName, paramValue);
/*     */     }
/*     */ 
/*  79 */     this.settingService.save(settings);
/*  80 */     this.msgs.add("配置修改成功");
/*  81 */     this.urls.put("系统设置", "setting!edit_input.do");
/*  82 */     return "message";
/*     */   }
/*     */ 
/*     */   public ISettingService getSettingService() {
/*  86 */     return this.settingService;
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService) {
/*  90 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public String[] getCfg_values()
/*     */   {
/*  95 */     return this.cfg_values;
/*     */   }
/*     */ 
/*     */   public void setCfg_values(String[] cfg_values)
/*     */   {
/* 101 */     this.cfg_values = cfg_values;
/*     */   }
/*     */ 
/*     */   public String[] getCodes()
/*     */   {
/* 107 */     return this.codes;
/*     */   }
/*     */ 
/*     */   public void setCodes(String[] codes)
/*     */   {
/* 113 */     this.codes = codes;
/*     */   }
/*     */ 
/*     */   public void setTabs(Map tabs)
/*     */   {
/* 119 */     this.tabs = tabs;
/*     */   }
/*     */ 
/*     */   public String getShowtab()
/*     */   {
/* 125 */     return this.showtab;
/*     */   }
/*     */ 
/*     */   public void setShowtab(String showtab) {
/* 129 */     this.showtab = showtab;
/*     */   }
/*     */ 
/*     */   public SettingPluginBundle getSettingPluginBundle() {
/* 133 */     return this.settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
/* 137 */     this.settingPluginBundle = settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getHtmls() {
/* 141 */     return this.htmls;
/*     */   }
/*     */ 
/*     */   public void setHtmls(Map<Integer, String> htmls) {
/* 145 */     this.htmls = htmls;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.SettingAction
 * JD-Core Version:    0.6.0
 */