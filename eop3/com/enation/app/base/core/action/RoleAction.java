/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.Role;
/*     */ import com.enation.app.base.core.service.auth.IAuthActionManager;
/*     */ import com.enation.app.base.core.service.auth.IRoleManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RoleAction extends WWAction
/*     */ {
/*     */   private IRoleManager roleManager;
/*     */   private IAuthActionManager authActionManager;
/*     */   private List roleList;
/*     */   private List authList;
/*     */   private int roleid;
/*     */   private Role role;
/*     */   private int[] acts;
/*     */   private int isEdit;
/*     */ 
/*     */   public String list()
/*     */   {
/*  30 */     this.roleList = this.roleManager.list();
/*  31 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  38 */     this.authList = this.authActionManager.list();
/*  39 */     return "input";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  45 */     this.authList = this.authActionManager.list();
/*  46 */     this.isEdit = 1;
/*  47 */     this.role = this.roleManager.get(this.roleid);
/*  48 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*  56 */     this.roleManager.add(this.role, this.acts);
/*  57 */     this.msgs.add("角色添加成功");
/*  58 */     this.urls.put("角色列表", "role!list.do");
/*     */ 
/*  60 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*  66 */     this.roleManager.edit(this.role, this.acts);
/*  67 */     this.msgs.add("角色修改成功");
/*  68 */     this.urls.put("角色列表", "role!list.do");
/*  69 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*  74 */     this.roleManager.delete(this.roleid);
/*  75 */     this.msgs.add("角色删除成功");
/*  76 */     this.urls.put("角色列表", "role!list.do");
/*  77 */     return "message";
/*     */   }
/*     */ 
/*     */   public IRoleManager getRoleManager()
/*     */   {
/*  82 */     return this.roleManager;
/*     */   }
/*     */   public void setRoleManager(IRoleManager roleManager) {
/*  85 */     this.roleManager = roleManager;
/*     */   }
/*     */ 
/*     */   public IAuthActionManager getAuthActionManager()
/*     */   {
/*  91 */     return this.authActionManager;
/*     */   }
/*     */ 
/*     */   public void setAuthActionManager(IAuthActionManager authActionManager)
/*     */   {
/*  97 */     this.authActionManager = authActionManager;
/*     */   }
/*     */ 
/*     */   public List getRoleList()
/*     */   {
/* 103 */     return this.roleList;
/*     */   }
/*     */ 
/*     */   public void setRoleList(List roleList)
/*     */   {
/* 109 */     this.roleList = roleList;
/*     */   }
/*     */ 
/*     */   public List getAuthList()
/*     */   {
/* 115 */     return this.authList;
/*     */   }
/*     */ 
/*     */   public void setAuthList(List authList)
/*     */   {
/* 121 */     this.authList = authList;
/*     */   }
/*     */ 
/*     */   public int getRoleid()
/*     */   {
/* 127 */     return this.roleid;
/*     */   }
/*     */ 
/*     */   public void setRoleid(int roleid)
/*     */   {
/* 133 */     this.roleid = roleid;
/*     */   }
/*     */ 
/*     */   public Role getRole()
/*     */   {
/* 139 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(Role role) {
/* 143 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public int[] getActs() {
/* 147 */     return this.acts;
/*     */   }
/*     */   public void setActs(int[] acts) {
/* 150 */     this.acts = acts;
/*     */   }
/*     */ 
/*     */   public int getIsEdit() {
/* 154 */     return this.isEdit;
/*     */   }
/*     */ 
/*     */   public void setIsEdit(int isEdit) {
/* 158 */     this.isEdit = isEdit;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.RoleAction
 * JD-Core Version:    0.6.0
 */