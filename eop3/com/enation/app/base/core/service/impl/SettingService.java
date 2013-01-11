/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.base.core.service.SettingRuntimeException;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Deprecated
/*     */ public class SettingService
/*     */   implements ISettingService
/*     */ {
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private SettingPluginBundle settingPluginBundle;
/*     */ 
/*     */   public void add(String groupname, String name, String value)
/*     */   {
/*  32 */     Map params = new HashMap();
/*  33 */     params.put(name, value);
/*     */   }
/*     */ 
/*     */   public void save(String groupname, String name, String value)
/*     */   {
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void save(Map<String, Map<String, String>> settings)
/*     */     throws SettingRuntimeException
/*     */   {
/*  52 */     Iterator settingkeyItor = settings.keySet().iterator();
/*     */ 
/*  54 */     while (settingkeyItor.hasNext())
/*     */     {
/*  56 */       String settingKey = (String)settingkeyItor.next();
/*  57 */       JSONObject jsonObject = JSONObject.fromObject(settings);
/*     */ 
/*  59 */       this.baseDaoSupport.execute("update settings set cfg_value=? where cfg_group=?", new Object[] { jsonObject.toString(), settingKey });
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Map<String, String>> getSetting()
/*     */   {
/*  73 */     String sql = "select * from settings";
/*  74 */     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
/*  75 */     Map cfg = new HashMap();
/*     */ 
/*  77 */     for (Map map : list) {
/*  78 */       String setting_value = (String)map.get("cfg_value");
/*  79 */       JSONObject jsonObject = JSONObject.fromObject(setting_value);
/*  80 */       Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
/*  81 */       cfg.put(map.get("cfg_group"), itemMap);
/*     */     }
/*     */ 
/*  84 */     return cfg;
/*     */   }
/*     */ 
/*     */   public SettingPluginBundle getSettingPluginBundle() {
/*  88 */     return this.settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
/*  92 */     this.settingPluginBundle = settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  97 */     String setting_value = "{\"thumbnail_pic_height\":\"40\",\"thumbnail_pic_width\":\"50\"}";
/*  98 */     JSONObject jsonObject = JSONObject.fromObject(setting_value);
/*  99 */     Map map1 = (Map)JSONObject.toBean(jsonObject, Map.class);
/* 100 */     System.out.println(map1.get("thumbnail_pic_height"));
/*     */   }
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 103 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public String getSetting(String group, String code)
/*     */   {
/* 108 */     return (String)((Map)getSetting().get(group)).get(code);
/*     */   }
/*     */ 
/*     */   public void delete(String groupname)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.SettingService
 * JD-Core Version:    0.6.0
 */