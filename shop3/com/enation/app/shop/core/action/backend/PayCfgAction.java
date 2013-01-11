/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.PayCfg;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class PayCfgAction extends WWAction
/*     */ {
/*     */   private IPaymentManager paymentManager;
/*     */   private List list;
/*     */   private List pluginList;
/*     */   private Integer paymentId;
/*     */   private String pluginId;
/*     */   private Integer[] id;
/*     */   private String name;
/*     */   private String type;
/*     */   private String biref;
/*     */ 
/*     */   public String list()
/*     */   {
/*  35 */     this.list = this.paymentManager.list();
/*  36 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  45 */     this.pluginList = this.paymentManager.listAvailablePlugins();
/*  46 */     return "input";
/*     */   }
/*     */ 
/*     */   public String getPluginHtml() {
/*     */     try {
/*  51 */       this.json = this.paymentManager.getPluginInstallHtml(this.pluginId, this.paymentId);
/*     */     } catch (RuntimeException e) {
/*  53 */       this.json = e.getMessage();
/*     */     }
/*  55 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  63 */     this.pluginList = this.paymentManager.listAvailablePlugins();
/*  64 */     PayCfg cfg = this.paymentManager.get(this.paymentId);
/*  65 */     this.name = cfg.getName();
/*  66 */     this.type = cfg.getType();
/*  67 */     this.biref = cfg.getBiref();
/*  68 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*     */     try
/*     */     {
/*  78 */       HttpServletRequest request = getRequest();
/*  79 */       Enumeration names = request.getParameterNames();
/*  80 */       Map params = new HashMap();
/*  81 */       while (names.hasMoreElements()) {
/*  82 */         String name = (String)names.nextElement();
/*     */ 
/*  84 */         if (("name".equals(name)) || 
/*  85 */           ("type".equals(name)) || 
/*  86 */           ("biref".equals(name)) || 
/*  87 */           ("paymentId".equals(name)) || 
/*  88 */           ("submit".equals(name))) continue;
/*  89 */         String value = request.getParameter(name);
/*  90 */         params.put(name, value);
/*     */       }
/*     */ 
/*  93 */       this.paymentManager.add(this.name, this.type, this.biref, params);
/*  94 */       this.msgs.add("支付方式添加成功");
/*  95 */       this.urls.put("支付方式列表", "payCfg!list.do");
/*     */     } catch (RuntimeException e) {
/*  97 */       this.msgs.add(e.getMessage());
/*     */     }
/*  99 */     return "message";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     if ((this.paymentId == null) || ("".equals(this.paymentId))) {
/* 106 */       return saveAdd();
/*     */     }
/* 108 */     return saveEdit();
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try
/*     */     {
/* 120 */       HttpServletRequest request = getRequest();
/* 121 */       Enumeration names = request.getParameterNames();
/* 122 */       Map params = new HashMap();
/* 123 */       while (names.hasMoreElements()) {
/* 124 */         String name = (String)names.nextElement();
/*     */ 
/* 126 */         if (("name".equals(name)) || 
/* 127 */           ("type".equals(name)) || 
/* 128 */           ("biref".equals(name)) || 
/* 129 */           ("paymentId".equals(name)) || 
/* 130 */           ("submit".equals(name))) continue;
/* 131 */         String value = request.getParameter(name);
/* 132 */         params.put(name, value);
/*     */       }
/*     */ 
/* 135 */       this.paymentManager.edit(this.paymentId, this.name, this.type, this.biref, params);
/* 136 */       this.msgs.add("支付方式修改成功");
/* 137 */       this.urls.put("支付方式列表", "payCfg!list.do");
/*     */     } catch (RuntimeException e) {
/* 139 */       this.msgs.add(e.getMessage());
/*     */     }
/* 141 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 151 */       this.paymentManager.delete(this.id);
/* 152 */       this.json = "{result:0,message:'支付方式删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 154 */       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
/*     */     }
/* 156 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager() {
/* 160 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 164 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public List getList() {
/* 168 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List list) {
/* 172 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public Integer getPaymentId() {
/* 176 */     return this.paymentId;
/*     */   }
/*     */ 
/*     */   public void setPaymentId(Integer paymentId) {
/* 180 */     this.paymentId = paymentId;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 184 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 188 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 192 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 196 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 200 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 204 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getBiref() {
/* 208 */     return this.biref;
/*     */   }
/*     */ 
/*     */   public void setBiref(String biref) {
/* 212 */     this.biref = biref;
/*     */   }
/*     */ 
/*     */   public List getPluginList()
/*     */   {
/* 217 */     return this.pluginList;
/*     */   }
/*     */ 
/*     */   public void setPluginList(List pluginList)
/*     */   {
/* 222 */     this.pluginList = pluginList;
/*     */   }
/*     */ 
/*     */   public String getPluginId()
/*     */   {
/* 227 */     return this.pluginId;
/*     */   }
/*     */ 
/*     */   public void setPluginId(String pluginId)
/*     */   {
/* 232 */     this.pluginId = pluginId;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.PayCfgAction
 * JD-Core Version:    0.6.0
 */