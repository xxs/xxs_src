/*     */ package com.enation.framework.component.impl;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.component.ComponentView;
/*     */ import com.enation.framework.component.IComponent;
/*     */ import com.enation.framework.component.IComponentManager;
/*     */ import com.enation.framework.component.PluginView;
/*     */ import com.enation.framework.component.WidgetView;
/*     */ import com.enation.framework.component.context.ComponentContext;
/*     */ import com.enation.framework.component.context.WidgetContext;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import com.enation.framework.plugin.IPluginBundle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ComponentManager extends BaseSupport
/*     */   implements IComponentManager
/*     */ {
/*     */   public List<ComponentView> list()
/*     */   {
/*  49 */     List viewList = new ArrayList();
/*  50 */     List componentViews = ComponentContext.getComponents();
/*     */ 
/*  52 */     List dbList = getDbList();
/*     */ 
/*  54 */     if (componentViews != null) {
/*  55 */       for (ComponentView view : componentViews)
/*     */       {
/*  57 */         ComponentView componentView = (ComponentView)view.clone();
/*     */ 
/*  59 */         if (this.logger.isDebugEnabled()) {
/*  60 */           this.logger.debug("load component[" + componentView.getName() + "] start ");
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/*  65 */           componentView.setInstall_state(0);
/*  66 */           componentView.setEnable_state(0);
/*     */ 
/*  70 */           loadComponentState(componentView, dbList);
/*     */ 
/*  73 */           if (this.logger.isDebugEnabled())
/*  74 */             this.logger.debug("load component[" + componentView.getName() + "] end ");
/*     */         }
/*     */         catch (Exception e) {
/*  77 */           this.logger.error("加载组件[" + componentView.getName() + "]出错", e);
/*  78 */           componentView.setEnable_state(2);
/*  79 */           componentView.setError_message(e.getMessage());
/*     */         }
/*  81 */         viewList.add(componentView);
/*     */       }
/*     */     }
/*     */ 
/*  85 */     return viewList;
/*     */   }
/*     */ 
/*     */   private void loadComponentState(ComponentView componentView, List<ComponentView> dbList)
/*     */   {
/*  94 */     for (ComponentView dbView : dbList)
/*  95 */       if (dbView.getComponentid().equals(componentView.getComponentid()))
/*     */       {
/*  99 */         if (this.logger.isDebugEnabled()) {
/* 100 */           this.logger.debug("load component[" + componentView.getName() + "]state->install state:" + dbView.getInstall_state() + ",enable_state:" + dbView.getEnable_state());
/*     */         }
/*     */ 
/* 105 */         componentView.setInstall_state(dbView.getInstall_state());
/* 106 */         componentView.setEnable_state(dbView.getEnable_state());
/* 107 */         componentView.setId(dbView.getId());
/*     */ 
/* 110 */         if (componentView.getInstall_state() != 2)
/*     */         {
/* 112 */           if (dbView.getEnable_state() != 0)
/*     */           {
/* 114 */             if (dbView.getEnable_state() != 1)
/*     */             {
/* 117 */               componentView.setError_message(dbView.getError_message());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void install(String componentid)
/*     */   {
/* 126 */     if (this.logger.isDebugEnabled()) {
/* 127 */       this.logger.debug("install component[" + componentid + "]...");
/*     */     }
/*     */     try
/*     */     {
/* 131 */       ComponentView componentView = getComponentView(componentid);
/*     */ 
/* 133 */       if (componentView != null) {
/* 134 */         componentView.getComponent().install();
/*     */ 
/* 136 */         if (!isInDb(componentView.getComponentid())) {
/* 137 */           ComponentView temp = (ComponentView)componentView.clone();
/* 138 */           temp.setInstall_state(1);
/*     */ 
/* 140 */           this.baseDaoSupport.insert("component", temp);
/*     */         }
/*     */         else {
/* 143 */           this.baseDaoSupport.execute("update component set install_state=1 where id=?", new Object[] { Integer.valueOf(componentView.getId()) });
/*     */         }
/*     */       }
/*     */     } catch (RuntimeException e) {
/* 147 */       this.logger.error("安装组件[" + componentid + "]出错", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isInDb(String componentid)
/*     */   {
/* 154 */     String sql = "select count(0)  from component where componentid=?";
/* 155 */     return this.baseDaoSupport.queryForInt(sql, new Object[] { componentid }) > 0;
/*     */   }
/*     */ 
/*     */   public void unInstall(String componentid)
/*     */   {
/* 160 */     if (this.logger.isDebugEnabled()) {
/* 161 */       this.logger.debug("install component[" + componentid + "]...");
/*     */     }
/*     */ 
/* 164 */     ComponentView componentView = getComponentView(componentid);
/* 165 */     if (componentView != null) {
/* 166 */       componentView.getComponent().unInstall();
/*     */ 
/* 168 */       if (!isInDb(componentView.getComponentid())) {
/* 169 */         ComponentView temp = (ComponentView)componentView.clone();
/* 170 */         temp.setInstall_state(0);
/* 171 */         this.baseDaoSupport.insert("component", temp);
/*     */       } else {
/* 173 */         this.baseDaoSupport.execute("update component set install_state=1 where id=?", new Object[] { Integer.valueOf(componentView.getId()) });
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ComponentView getComponentView(String componentid)
/*     */   {
/* 180 */     List componentList = ComponentContext.getComponents();
/*     */ 
/* 183 */     for (ComponentView componentView : componentList) {
/* 184 */       if (componentView.getComponentid().equals(componentid)) {
/* 185 */         return componentView;
/*     */       }
/*     */     }
/*     */ 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public void start(String componentid)
/*     */   {
/* 200 */     if (this.logger.isDebugEnabled()) {
/* 201 */       this.logger.debug("start component[" + componentid + "]...");
/*     */     }
/*     */ 
/* 204 */     ComponentView componentView = getComponentView(componentid);
/*     */ 
/* 210 */     List pluginList = componentView.getPluginList();
/* 211 */     for (PluginView pluginView : pluginList) {
/* 212 */       String pluginid = pluginView.getId();
/* 213 */       plugin = (IPlugin)SpringContextHolder.getBean(pluginid);
/*     */ 
/* 215 */       List bundleList = pluginView.getBundleList();
/* 216 */       if (bundleList != null)
/* 217 */         for (String bundleId : bundleList) {
/* 218 */           IPluginBundle pluginBundle = (IPluginBundle)SpringContextHolder.getBean(bundleId);
/* 219 */           pluginBundle.registerPlugin(plugin);
/*     */         }
/*     */     }
/*     */     IPlugin plugin;
/* 230 */     List widgetList = componentView.getWidgetList();
/* 231 */     for (WidgetView widgetView : widgetList) {
/* 232 */       String widgetid = widgetView.getId();
/* 233 */       WidgetContext.putWidgetState(widgetid, Boolean.valueOf(true));
/*     */     }
/*     */ 
/* 241 */     String sql = "update component set enable_state=1 where componentid=?";
/* 242 */     this.baseDaoSupport.execute(sql, new Object[] { componentid });
/*     */ 
/* 249 */     if (this.logger.isDebugEnabled())
/* 250 */       this.logger.debug("start component[" + componentid + "] complete");
/*     */   }
/*     */ 
/*     */   public void stop(String componentid)
/*     */   {
/* 265 */     if (this.logger.isDebugEnabled()) {
/* 266 */       this.logger.debug("stop component[" + componentid + "]...");
/*     */     }
/*     */ 
/* 270 */     ComponentView componentView = getComponentView(componentid);
/*     */ 
/* 276 */     List pluginList = componentView.getPluginList();
/* 277 */     for (PluginView pluginView : pluginList) {
/* 278 */       String pluginid = pluginView.getId();
/* 279 */       plugin = (IPlugin)SpringContextHolder.getBean(pluginid);
/* 280 */       List bundleList = pluginView.getBundleList();
/* 281 */       for (String bundleId : bundleList) {
/* 282 */         IPluginBundle pluginBundle = (IPluginBundle)SpringContextHolder.getBean(bundleId);
/* 283 */         pluginBundle.unRegisterPlugin(plugin);
/*     */       }
/*     */     }
/*     */     IPlugin plugin;
/* 292 */     List widgetList = componentView.getWidgetList();
/* 293 */     for (WidgetView widgetView : widgetList) {
/* 294 */       String widgetid = widgetView.getId();
/* 295 */       WidgetContext.putWidgetState(widgetid, Boolean.valueOf(false));
/*     */     }
/*     */ 
/* 301 */     String sql = "update component set enable_state=0 where componentid=?";
/* 302 */     this.baseDaoSupport.execute(sql, new Object[] { componentid });
/*     */ 
/* 308 */     if (this.logger.isDebugEnabled())
/* 309 */       this.logger.debug("stop component[" + componentid + "] complete");
/*     */   }
/*     */ 
/*     */   public void startComponents()
/*     */   {
/* 324 */     if (this.logger.isDebugEnabled()) {
/* 325 */       this.logger.debug("start components start...");
/*     */     }
/*     */ 
/* 328 */     List dbList = getDbList();
/* 329 */     for (ComponentView componentView : dbList) {
/* 330 */       if ((componentView.getInstall_state() != 2) && 
/* 331 */         (componentView.getEnable_state() == 1)) {
/* 332 */         start(componentView.getComponentid());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 338 */     if (this.logger.isDebugEnabled())
/* 339 */       this.logger.debug("start components end!");
/*     */   }
/*     */ 
/*     */   public void saasStartComponents()
/*     */   {
/* 348 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 349 */     int userid = site.getUserid().intValue();
/* 350 */     int siteid = site.getId().intValue();
/*     */ 
/* 352 */     boolean isstart = ComponentContext.getSiteComponentState(userid, siteid);
/* 353 */     if (!isstart) {
/* 354 */       startComponents();
/* 355 */       ComponentContext.siteComponentStart(userid, siteid);
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<ComponentView> getDbList()
/*     */   {
/* 367 */     String sql = "select * from component ";
/* 368 */     return this.baseDaoSupport.queryForList(sql, ComponentView.class, new Object[0]);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.impl.ComponentManager
 * JD-Core Version:    0.6.0
 */