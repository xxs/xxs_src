/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.user.AdminUserPluginBundle;
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.IRoleManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class UserAdminAction extends WWAction
/*     */ {
/*     */   private AdminUserPluginBundle adminUserPluginBundle;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IRoleManager roleManager;
/*     */   private IPermissionManager permissionManager;
/*     */   private AdminUser adminUser;
/*     */   private Integer id;
/*     */   private List roleList;
/*     */   private List userRoles;
/*     */   private int[] roleids;
/*     */   private List userList;
/*     */   private String newPassword;
/*     */   private String updatePwd;
/*     */   private int multiSite;
/*     */   private List<String> htmlList;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  36 */     this.userList = this.adminUserManager.list();
/*  37 */     return "success";
/*     */   }
/*     */ 
/*     */   public String add() throws Exception
/*     */   {
/*  42 */     this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
/*  43 */     this.roleList = this.roleManager.list();
/*  44 */     this.htmlList = this.adminUserPluginBundle.getInputHtml(null);
/*  45 */     return "add";
/*     */   }
/*     */ 
/*     */   public String addSave() throws Exception {
/*     */     try {
/*  50 */       this.adminUser.setRoleids(this.roleids);
/*  51 */       this.adminUserManager.add(this.adminUser);
/*  52 */       this.msgs.add("新增管理员成功");
/*  53 */       this.urls.put("管理员列表", "userAdmin.do");
/*     */     } catch (RuntimeException e) {
/*  55 */       this.msgs.add(e.getMessage());
/*     */     }
/*  57 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() throws Exception {
/*  61 */     this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
/*  62 */     this.roleList = this.roleManager.list();
/*  63 */     this.userRoles = this.permissionManager.getUserRoles(this.id.intValue());
/*  64 */     this.adminUser = this.adminUserManager.get(this.id);
/*  65 */     this.htmlList = this.adminUserPluginBundle.getInputHtml(this.adminUser);
/*  66 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String editSave() throws Exception {
/*     */     try {
/*  71 */       if (this.updatePwd != null) {
/*  72 */         this.adminUser.setPassword(this.newPassword);
/*     */       }
/*  74 */       this.adminUser.setRoleids(this.roleids);
/*  75 */       this.adminUserManager.edit(this.adminUser);
/*  76 */       this.msgs.add("修改管理员成功");
/*     */     } catch (RuntimeException e) {
/*  78 */       e.printStackTrace();
/*  79 */       this.logger.error(e, e.fillInStackTrace());
/*  80 */       this.msgs.add("管理员修改失败:" + e.getMessage());
/*     */     }
/*  82 */     this.urls.put("管理员列表", "userAdmin.do");
/*     */ 
/*  84 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() throws Exception
/*     */   {
/*     */     try {
/*  90 */       this.adminUserManager.delete(this.id);
/*  91 */       this.msgs.add("管理员删除成功");
/*  92 */       this.urls.put("管理员列表", "userAdmin.do");
/*     */     } catch (RuntimeException e) {
/*  94 */       this.msgs.add("管理员删除失败:" + e.getMessage());
/*  95 */       this.urls.put("管理员列表", "userAdmin.do");
/*     */     }
/*     */ 
/*  98 */     return "message";
/*     */   }
/*     */ 
/*     */   public String editPassword() throws Exception {
/* 102 */     return "editPassword";
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 108 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 112 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public IRoleManager getRoleManager() {
/* 116 */     return this.roleManager;
/*     */   }
/*     */ 
/*     */   public void setRoleManager(IRoleManager roleManager) {
/* 120 */     this.roleManager = roleManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 124 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 128 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ 
/*     */   public AdminUser getAdminUser() {
/* 132 */     return this.adminUser;
/*     */   }
/*     */ 
/*     */   public void setAdminUser(AdminUser adminUser) {
/* 136 */     this.adminUser = adminUser;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/* 140 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 144 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public List getRoleList() {
/* 148 */     return this.roleList;
/*     */   }
/*     */ 
/*     */   public void setRoleList(List roleList) {
/* 152 */     this.roleList = roleList;
/*     */   }
/*     */ 
/*     */   public List getUserRoles() {
/* 156 */     return this.userRoles;
/*     */   }
/*     */ 
/*     */   public void setUserRoles(List userRoles) {
/* 160 */     this.userRoles = userRoles;
/*     */   }
/*     */ 
/*     */   public int[] getRoleids() {
/* 164 */     return this.roleids;
/*     */   }
/*     */ 
/*     */   public void setRoleids(int[] roleids) {
/* 168 */     this.roleids = roleids;
/*     */   }
/*     */ 
/*     */   public List getUserList() {
/* 172 */     return this.userList;
/*     */   }
/*     */ 
/*     */   public void setUserList(List userList) {
/* 176 */     this.userList = userList;
/*     */   }
/*     */ 
/*     */   public String getNewPassword() {
/* 180 */     return this.newPassword;
/*     */   }
/*     */ 
/*     */   public void setNewPassword(String newPassword) {
/* 184 */     this.newPassword = newPassword;
/*     */   }
/*     */ 
/*     */   public String getUpdatePwd() {
/* 188 */     return this.updatePwd;
/*     */   }
/*     */ 
/*     */   public void setUpdatePwd(String updatePwd) {
/* 192 */     this.updatePwd = updatePwd;
/*     */   }
/*     */ 
/*     */   public int getMultiSite() {
/* 196 */     return this.multiSite;
/*     */   }
/*     */ 
/*     */   public void setMultiSite(int multiSite) {
/* 200 */     this.multiSite = multiSite;
/*     */   }
/*     */ 
/*     */   public AdminUserPluginBundle getAdminUserPluginBundle() {
/* 204 */     return this.adminUserPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setAdminUserPluginBundle(AdminUserPluginBundle adminUserPluginBundle) {
/* 208 */     this.adminUserPluginBundle = adminUserPluginBundle;
/*     */   }
/*     */ 
/*     */   public List<String> getHtmlList() {
/* 212 */     return this.htmlList;
/*     */   }
/*     */ 
/*     */   public void setHtmlList(List<String> htmlList) {
/* 216 */     this.htmlList = htmlList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.UserAdminAction
 * JD-Core Version:    0.6.0
 */