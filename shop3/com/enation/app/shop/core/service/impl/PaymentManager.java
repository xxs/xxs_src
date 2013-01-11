/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
/*     */ import com.enation.app.shop.core.plugin.payment.PaymentPluginBundle;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ObjectNotFoundException;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ public class PaymentManager extends BaseSupport<PayCfg>
/*     */   implements IPaymentManager
/*     */ {
/*     */   private PaymentPluginBundle paymentPluginBundle;
/*     */ 
/*     */   public List list()
/*     */   {
/*  33 */     String sql = "select * from payment_cfg";
/*  34 */     return this.baseDaoSupport.queryForList(sql, PayCfg.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public PayCfg get(Integer id)
/*     */   {
/*  39 */     String sql = "select * from payment_cfg where id=?";
/*  40 */     PayCfg payment = (PayCfg)this.baseDaoSupport.queryForObject(sql, PayCfg.class, new Object[] { id });
/*  41 */     return payment;
/*     */   }
/*     */ 
/*     */   public PayCfg get(String pluginid) {
/*  45 */     String sql = "select * from payment_cfg where type=?";
/*  46 */     PayCfg payment = (PayCfg)this.baseDaoSupport.queryForObject(sql, PayCfg.class, new Object[] { pluginid });
/*  47 */     return payment;
/*     */   }
/*     */ 
/*     */   public Double countPayPrice(Integer id)
/*     */   {
/*  53 */     return Double.valueOf(0.0D);
/*     */   }
/*     */ 
/*     */   public void add(String name, String type, String biref, Map<String, String> configParmas)
/*     */   {
/*  59 */     if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
/*  60 */     if (StringUtil.isEmpty(type)) throw new IllegalArgumentException("payment type is  null");
/*  61 */     if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");
/*     */ 
/*  63 */     PayCfg payCfg = new PayCfg();
/*  64 */     payCfg.setName(name);
/*  65 */     payCfg.setType(type);
/*  66 */     payCfg.setBiref(biref);
/*  67 */     payCfg.setConfig(JSONObject.fromObject(configParmas).toString());
/*  68 */     this.baseDaoSupport.insert("payment_cfg", payCfg);
/*     */   }
/*     */ 
/*     */   public Map<String, String> getConfigParams(Integer paymentId)
/*     */   {
/*  73 */     PayCfg payment = get(paymentId);
/*  74 */     String config = payment.getConfig();
/*  75 */     if (null == config) return new HashMap();
/*  76 */     JSONObject jsonObject = JSONObject.fromObject(config);
/*  77 */     Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
/*  78 */     return itemMap;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getConfigParams(String pluginid)
/*     */   {
/*  83 */     PayCfg payment = get(pluginid);
/*  84 */     String config = payment.getConfig();
/*  85 */     if (null == config) return new HashMap();
/*  86 */     JSONObject jsonObject = JSONObject.fromObject(config);
/*  87 */     Map itemMap = (Map)JSONObject.toBean(jsonObject, Map.class);
/*  88 */     return itemMap;
/*     */   }
/*     */ 
/*     */   public void edit(Integer paymentId, String name, String type, String biref, Map<String, String> configParmas)
/*     */   {
/*  95 */     if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
/*  96 */     if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");
/*     */ 
/*  98 */     PayCfg payCfg = new PayCfg();
/*  99 */     payCfg.setName(name);
/* 100 */     payCfg.setBiref(biref);
/* 101 */     payCfg.setType(type);
/* 102 */     payCfg.setConfig(JSONObject.fromObject(configParmas).toString());
/* 103 */     this.baseDaoSupport.update("payment_cfg", payCfg, "id=" + paymentId);
/*     */   }
/*     */ 
/*     */   public void delete(Integer[] idArray)
/*     */   {
/* 108 */     if ((idArray == null) || (idArray.length == 0)) return;
/*     */ 
/* 110 */     String idStr = StringUtil.arrayToString(idArray, ",");
/* 111 */     String sql = "delete from payment_cfg where id in(" + idStr + ")";
/* 112 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<IPlugin> listAvailablePlugins()
/*     */   {
/* 119 */     return this.paymentPluginBundle.getPluginList();
/*     */   }
/*     */ 
/*     */   public String getPluginInstallHtml(String pluginId, Integer paymentId)
/*     */   {
/* 124 */     IPlugin installPlugin = null;
/* 125 */     List plguinList = listAvailablePlugins();
/* 126 */     for (IPlugin plugin : plguinList) {
/* 127 */       if ((plugin instanceof AbstractPaymentPlugin))
/*     */       {
/* 129 */         if (((AbstractPaymentPlugin)plugin).getId().equals(pluginId)) {
/* 130 */           installPlugin = plugin;
/* 131 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if (installPlugin == null) throw new ObjectNotFoundException("plugin[" + pluginId + "] not found!");
/* 139 */     FreeMarkerPaser fp = FreeMarkerPaser.getInstance();
/* 140 */     fp.setClz(installPlugin.getClass());
/*     */ 
/* 142 */     if (paymentId != null) {
/* 143 */       Map params = getConfigParams(paymentId);
/* 144 */       Iterator keyIter = params.keySet().iterator();
/*     */ 
/* 146 */       while (keyIter.hasNext()) {
/* 147 */         String key = (String)keyIter.next();
/* 148 */         String value = (String)params.get(key);
/* 149 */         fp.putData(key, value);
/*     */       }
/*     */     }
/* 152 */     return fp.proessPageContent();
/*     */   }
/*     */ 
/*     */   public PaymentPluginBundle getPaymentPluginBundle()
/*     */   {
/* 157 */     return this.paymentPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setPaymentPluginBundle(PaymentPluginBundle paymentPluginBundle) {
/* 161 */     this.paymentPluginBundle = paymentPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.PaymentManager
 * JD-Core Version:    0.6.0
 */