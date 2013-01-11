/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.component.ComponentView;
/*     */ import com.enation.framework.component.IComponentManager;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ComponentAction extends WWAction
/*     */ {
/*     */   private IComponentManager componentManager;
/*     */   private List<ComponentView> componentList;
/*     */   private String componentid;
/*     */ 
/*     */   public String list()
/*     */   {
/*  23 */     this.componentList = this.componentManager.list();
/*  24 */     return "list";
/*     */   }
/*     */ 
/*     */   public String install()
/*     */   {
/*     */     try
/*     */     {
/*  36 */       this.componentManager.install(this.componentid);
/*  37 */       showSuccessJson("安装成功");
/*     */     } catch (RuntimeException e) {
/*  39 */       this.logger.error("安装组件[" + this.componentid + "]", e);
/*  40 */       showErrorJson(e.getMessage());
/*     */     }
/*  42 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String unInstall()
/*     */   {
/*     */     try
/*     */     {
/*  54 */       this.componentManager.unInstall(this.componentid);
/*  55 */       showSuccessJson("卸载成功");
/*     */     } catch (RuntimeException e) {
/*  57 */       this.logger.error("卸载组件[" + this.componentid + "]", e);
/*  58 */       showErrorJson(e.getMessage());
/*     */     }
/*  60 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String start()
/*     */   {
/*     */     try
/*     */     {
/*  72 */       this.componentManager.start(this.componentid);
/*  73 */       showSuccessJson("启动成功");
/*     */     } catch (RuntimeException e) {
/*  75 */       this.logger.error("启动组件[" + this.componentid + "]", e);
/*  76 */       showErrorJson(e.getMessage());
/*     */     }
/*  78 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String stop()
/*     */   {
/*     */     try
/*     */     {
/*  88 */       this.componentManager.stop(this.componentid);
/*  89 */       showSuccessJson("停用成功");
/*     */     } catch (RuntimeException e) {
/*  91 */       this.logger.error("停用组件[" + this.componentid + "]", e);
/*  92 */       showErrorJson(e.getMessage());
/*     */     }
/*  94 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IComponentManager getComponentManager()
/*     */   {
/* 100 */     return this.componentManager;
/*     */   }
/*     */   public void setComponentManager(IComponentManager componentManager) {
/* 103 */     this.componentManager = componentManager;
/*     */   }
/*     */ 
/*     */   public List<ComponentView> getComponentList() {
/* 107 */     return this.componentList;
/*     */   }
/*     */ 
/*     */   public void setComponentList(List<ComponentView> componentList) {
/* 111 */     this.componentList = componentList;
/*     */   }
/*     */ 
/*     */   public String getComponentid() {
/* 115 */     return this.componentid;
/*     */   }
/*     */ 
/*     */   public void setComponentid(String componentid) {
/* 119 */     this.componentid = componentid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.ComponentAction
 * JD-Core Version:    0.6.0
 */