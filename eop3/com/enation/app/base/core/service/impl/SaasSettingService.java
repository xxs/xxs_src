/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.setting.SettingPluginBundle;
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.base.core.service.SettingRuntimeException;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
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
/*     */ public class SaasSettingService extends BaseSupport
/*     */   implements ISettingService
/*     */ {
/*     */   private SettingPluginBundle settingPluginBundle;
/*     */ 
/*     */   public void add(String groupname, String name, String value)
/*     */   {
/*  34 */     Map params = new HashMap();
/*  35 */     params.put(name, value);
/*  36 */     JSONObject jsonObject = JSONObject.fromObject(params);
/*  37 */     this.baseDaoSupport.execute("insert into settings(cfg_value,cfg_group)values(?,?)", new Object[] { jsonObject.toString(), groupname });
/*     */   }
/*     */ 
/*     */   public void save(String groupname, String name, String value)
/*     */   {
/*  44 */     Map params = new HashMap();
/*  45 */     params.put(name, value);
/*  46 */     JSONObject jsonObject = JSONObject.fromObject(params);
/*  47 */     this.baseDaoSupport.execute("update settings set cfg_value=? where cfg_group=?", new Object[] { jsonObject.toString(), groupname });
/*     */   }
/*     */ 
/*     */   public void delete(String groupname)
/*     */   {
/*  54 */     this.baseDaoSupport.execute("delete from settings where cfg_group=?", new Object[] { groupname });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void save(Map<String, Map<String, String>> settings)
/*     */     throws SettingRuntimeException
/*     */   {
/*  63 */     Iterator settingkeyItor = settings.keySet().iterator();
/*     */ 
/*  65 */     while (settingkeyItor.hasNext())
/*     */     {
/*  67 */       String settingKey = (String)settingkeyItor.next();
/*  68 */       JSONObject jsonObject = JSONObject.fromObject(settings.get(settingKey));
/*     */ 
/*  70 */       this.baseDaoSupport.execute("update settings set cfg_value=? where cfg_group=?", new Object[] { jsonObject.toString(), settingKey });
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Map<String, String>> getSetting()
/*     */   {
/*  80 */     String sql = "select * from settings";
/*  81 */     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
/*  82 */     Map cfg = new HashMap();
/*     */ 
/*  84 */     for (Map map : list) {
/*  85 */       String setting_value = (String)map.get("cfg_value");
/*  86 */       if (StringUtil.isEmpty(setting_value)) {
/*  87 */         cfg.put(map.get("cfg_group"), new HashMap());
/*     */       } else {
/*  89 */         JSONObject jsonObject = JSONObject.fromObject(setting_value);
/*  90 */         Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
/*  91 */         cfg.put(map.get("cfg_group"), itemMap);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  96 */     return cfg;
/*     */   }
/*     */ 
/*     */   public SettingPluginBundle getSettingPluginBundle()
/*     */   {
/* 101 */     return this.settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
/* 105 */     this.settingPluginBundle = settingPluginBundle;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 110 */     String setting_value = "{\"thumbnail_pic_height\":\"40\",\"thumbnail_pic_width\":\"50\"}";
/* 111 */     JSONObject jsonObject = JSONObject.fromObject(setting_value);
/* 112 */     Map map1 = (Map)JSONObject.toBean(jsonObject, Map.class);
/* 113 */     System.out.println(map1.get("thumbnail_pic_height"));
/*     */   }
/*     */ 
/*     */   public String getSetting(String group, String code)
/*     */   {
/* 119 */     Map settings = getSetting();
/* 120 */     if (settings == null) return null;
/*     */ 
/* 122 */     Map setting = (Map)settings.get(group);
/* 123 */     if (setting == null) return null;
/*     */ 
/* 125 */     return (String)setting.get(code);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.SaasSettingService
 * JD-Core Version:    0.6.0
 */