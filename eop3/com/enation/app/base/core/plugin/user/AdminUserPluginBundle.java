/*     */ package com.enation.app.base.core.plugin.user;
/*     */ 
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AdminUserPluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public String getName()
/*     */   {
/*  21 */     return "管理员插件桩";
/*     */   }
/*     */ 
/*     */   public List<String> getInputHtml(AdminUser user)
/*     */   {
/*  31 */     List list = new ArrayList();
/*  32 */     List plugins = getPlugins();
/*     */     FreeMarkerPaser freeMarkerPaser;
/*  34 */     if (plugins != null) {
/*  35 */       freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  37 */       for (IPlugin plugin : plugins) {
/*  38 */         if ((plugin instanceof IAdminUserInputDisplayEvent)) {
/*  39 */           IAdminUserInputDisplayEvent event = (IAdminUserInputDisplayEvent)plugin;
/*  40 */           freeMarkerPaser.setClz(event.getClass());
/*  41 */           String html = event.getInputHtml(user);
/*  42 */           list.add(html);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  47 */     return list;
/*     */   }
/*     */ 
/*     */   public void onAdd(Integer userid)
/*     */   {
/*  57 */     List plugins = getPlugins();
/*     */ 
/*  59 */     if (plugins != null)
/*     */     {
/*  61 */       for (IPlugin plugin : plugins)
/*  62 */         if ((plugin instanceof IAdminUserOnAddEvent)) {
/*  63 */           IAdminUserOnAddEvent event = (IAdminUserOnAddEvent)plugin;
/*  64 */           event.onAdd(userid);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onEdit(Integer userid)
/*     */   {
/*  78 */     List plugins = getPlugins();
/*     */ 
/*  80 */     if (plugins != null)
/*     */     {
/*  82 */       for (IPlugin plugin : plugins)
/*  83 */         if ((plugin instanceof IAdminUserOnEditEvent)) {
/*  84 */           IAdminUserOnEditEvent event = (IAdminUserOnEditEvent)plugin;
/*  85 */           event.onEdit(userid);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onDelete(Integer userid)
/*     */   {
/*  98 */     List plugins = getPlugins();
/*     */ 
/* 100 */     if (plugins != null)
/*     */     {
/* 102 */       for (IPlugin plugin : plugins)
/* 103 */         if ((plugin instanceof IAdminUserDeleteEvent)) {
/* 104 */           IAdminUserDeleteEvent event = (IAdminUserDeleteEvent)plugin;
/* 105 */           event.onDelete(userid.intValue());
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onLogin(AdminUser user)
/*     */   {
/* 114 */     List plugins = getPlugins();
/*     */ 
/* 116 */     if (plugins != null)
/*     */     {
/* 118 */       for (IPlugin plugin : plugins)
/* 119 */         if ((plugin instanceof IAdminUserLoginEvent)) {
/* 120 */           IAdminUserLoginEvent event = (IAdminUserLoginEvent)plugin;
/* 121 */           event.onLogin(user);
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.user.AdminUserPluginBundle
 * JD-Core Version:    0.6.0
 */