/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class AdminUserInstaller
/*     */   implements IInstaller
/*     */ {
/*     */   private IUserManager userManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IDaoSupport daoSupport;
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  23 */     if ("base".equals(productId)) {
/*  24 */       EopUser user = this.userManager.getCurrentUser();
/*  25 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  26 */       int userid = site.getUserid().intValue();
/*  27 */       int siteid = site.getId().intValue();
/*  28 */       if (user != null)
/*     */       {
/*  31 */         AdminUser adminUser = new AdminUser();
/*  32 */         adminUser.setUsername(user.getUsername());
/*  33 */         adminUser.setPassword(user.getPassword());
/*  34 */         adminUser.setFounder(1);
/*  35 */         int adminUserId = this.adminUserManager.add(site.getUserid().intValue(), siteid, adminUser).intValue();
/*     */ 
/*  38 */         if (EopSetting.RUNMODE.equals("2")) {
/*  39 */           this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
/*     */         }
/*     */         else
/*     */         {
/*  43 */           this.daoSupport.execute("update es_adminuser  set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  49 */         AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  50 */         String tablename = "es_adminuser";
/*  51 */         if (EopSetting.RUNMODE.equals("2")) {
/*  52 */           tablename = tablename + "_" + userid + "_" + siteid;
/*     */         }
/*  54 */         this.daoSupport.insert(tablename, adminUser);
/*  55 */         Integer adminuserid = adminUser.getUserid();
/*     */ 
/*  58 */         if (EopSetting.RUNMODE.equals("2")) {
/*  59 */           this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { adminUser.getPassword(), adminuserid });
/*     */         }
/*     */         else
/*     */         {
/*  63 */           this.daoSupport.execute("update es_adminuser  set password=? where userid=?", new Object[] { adminUser.getPassword(), Integer.valueOf(userid) });
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  71 */       AdminUser orderUser = new AdminUser();
/*  72 */       orderUser.setUsername("order");
/*  73 */       orderUser.setPassword("order");
/*  74 */       orderUser.setState(1);
/*  75 */       orderUser.setRoleids(new int[] { 2 });
/*  76 */       this.adminUserManager.add(orderUser);
/*     */ 
/*  79 */       AdminUser devUser = new AdminUser();
/*  80 */       devUser.setUsername("developer");
/*  81 */       devUser.setPassword("developer");
/*  82 */       devUser.setState(1);
/*  83 */       devUser.setRoleids(new int[] { 3 });
/*  84 */       this.adminUserManager.add(devUser);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager()
/*     */   {
/*  91 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/*  95 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager() {
/*  99 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 103 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport() {
/* 107 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 111 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminUserInstaller
 * JD-Core Version:    0.6.0
 */