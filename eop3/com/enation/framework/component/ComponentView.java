/*     */ package com.enation.framework.component;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ComponentView
/*     */   implements Cloneable
/*     */ {
/*     */   private String name;
/*     */   private int id;
/*     */   private String componentid;
/*     */   private String version;
/*     */   private String javashop_version;
/*     */   private String author;
/*     */   private String description;
/*     */   private IComponent component;
/*     */   private List<PluginView> pluginList;
/*     */   private List<WidgetView> widgetList;
/*     */   private int install_state;
/*     */   private int enable_state;
/*     */   private String error_message;
/*     */ 
/*     */   public ComponentView()
/*     */   {
/*  12 */     this.pluginList = new ArrayList();
/*  13 */     this.widgetList = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void addPlugin(PluginView plugin)
/*     */   {
/*  39 */     this.pluginList.add(plugin);
/*     */   }
/*     */ 
/*     */   public void addWidget(WidgetView widget)
/*     */   {
/*  44 */     this.widgetList.add(widget);
/*     */   }
/*     */ 
/*     */   public void setComponent(IComponent component) {
/*  48 */     this.component = component;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public IComponent getComponent() {
/*  54 */     return this.component;
/*     */   }
/*     */   @NotDbField
/*     */   public List<PluginView> getPluginList() {
/*  59 */     return this.pluginList;
/*     */   }
/*     */ 
/*     */   public void setPluginList(List<PluginView> pluginList)
/*     */   {
/*  64 */     this.pluginList = pluginList;
/*     */   }
/*     */   @NotDbField
/*     */   public List<WidgetView> getWidgetList() {
/*  69 */     return this.widgetList;
/*     */   }
/*     */ 
/*     */   public void setWidgetList(List<WidgetView> widgetList)
/*     */   {
/*  74 */     this.widgetList = widgetList;
/*     */   }
/*     */ 
/*     */   public int getInstall_state()
/*     */   {
/*  79 */     return this.install_state;
/*     */   }
/*     */ 
/*     */   public void setInstall_state(int install_state)
/*     */   {
/*  84 */     this.install_state = install_state;
/*     */   }
/*     */ 
/*     */   public int getEnable_state()
/*     */   {
/*  89 */     return this.enable_state;
/*     */   }
/*     */ 
/*     */   public void setEnable_state(int enable_state)
/*     */   {
/*  94 */     this.enable_state = enable_state;
/*     */   }
/*     */ 
/*     */   public String getError_message()
/*     */   {
/*  99 */     return this.error_message;
/*     */   }
/*     */ 
/*     */   public void setError_message(String error_message)
/*     */   {
/* 104 */     this.error_message = error_message;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 109 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 114 */     this.name = name;
/*     */   }
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public int getId() {
/* 120 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 125 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getComponentid()
/*     */   {
/* 130 */     return this.componentid;
/*     */   }
/*     */ 
/*     */   public void setComponentid(String componentid)
/*     */   {
/* 135 */     this.componentid = componentid;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 142 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 147 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public String getJavashop_version()
/*     */   {
/* 152 */     return this.javashop_version;
/*     */   }
/*     */ 
/*     */   public void setJavashop_version(String javashop_version)
/*     */   {
/* 157 */     this.javashop_version = javashop_version;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 162 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/* 167 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 172 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 177 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 185 */       return super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 187 */       e.printStackTrace();
/* 188 */     }return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.ComponentView
 * JD-Core Version:    0.6.0
 */