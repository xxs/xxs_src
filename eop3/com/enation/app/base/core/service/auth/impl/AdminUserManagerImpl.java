/*     */ package com.enation.app.base.core.service.auth.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.MultiSite;
/*     */ import com.enation.app.base.core.plugin.user.AdminUserPluginBundle;
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.UserContext;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class AdminUserManagerImpl extends BaseSupport<AdminUser>
/*     */   implements IAdminUserManager
/*     */ {
/*     */   private AdminUserPluginBundle adminUserPluginBundle;
/*     */   private IPermissionManager permissionManager;
/*     */ 
/*     */   public void clean()
/*     */   {
/*  36 */     this.baseDaoSupport.execute("truncate table adminuser", new Object[0]);
/*     */   }
/*  40 */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer add(AdminUser adminUser) { adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));
/*     */ 
/*  42 */     this.baseDaoSupport.insert("adminuser", adminUser);
/*  43 */     int userid = this.baseDaoSupport.getLastId("adminuser");
/*     */ 
/*  46 */     this.permissionManager.giveRolesToUser(userid, adminUser.getRoleids());
/*  47 */     this.adminUserPluginBundle.onAdd(Integer.valueOf(userid));
/*  48 */     return Integer.valueOf(userid);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer add(int userid, int siteid, AdminUser adminUser)
/*     */   {
/*  61 */     adminUser.setState(1);
/*  62 */     this.baseDaoSupport.insert("adminuser", adminUser);
/*  63 */     return Integer.valueOf(this.baseDaoSupport.getLastId("adminuser"));
/*     */   }
/*     */ 
/*     */   public int checkLast() {
/*  67 */     int count = this.baseDaoSupport.queryForInt("select count(0) from adminuser", new Object[0]);
/*  68 */     return count;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer id)
/*     */   {
/*  75 */     if (checkLast() == 1) {
/*  76 */       throw new RuntimeException("必须最少保留一个管理员");
/*     */     }
/*     */ 
/*  80 */     this.permissionManager.cleanUserRoles(id.intValue());
/*     */ 
/*  83 */     this.baseDaoSupport.execute("delete from adminuser where userid=?", new Object[] { id });
/*  84 */     this.adminUserPluginBundle.onDelete(id);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(AdminUser adminUser) {
/*  90 */     this.permissionManager.giveRolesToUser(adminUser.getUserid().intValue(), adminUser.getRoleids());
/*     */ 
/*  93 */     if (!StringUtil.isEmpty(adminUser.getPassword()))
/*  94 */       adminUser.setPassword(StringUtil.md5(adminUser.getPassword()));
/*  95 */     int userId = adminUser.getUserid().intValue();
/*  96 */     adminUser.setUserid(null);
/*  97 */     this.baseDaoSupport.update("adminuser", adminUser, "userid=" + userId);
/*  98 */     this.adminUserPluginBundle.onEdit(Integer.valueOf(userId));
/*     */   }
/*     */ 
/*     */   public AdminUser get(Integer id) {
/* 102 */     return (AdminUser)this.baseDaoSupport.queryForObject("select * from adminuser where userid=?", AdminUser.class, new Object[] { id });
/*     */   }
/*     */ 
/*     */   public List list() {
/* 106 */     return this.baseDaoSupport.queryForList("select * from adminuser order by dateline", AdminUser.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<Map> list(Integer userid, Integer siteid)
/*     */   {
/* 111 */     String sql = "select * from es_adminuser_" + userid + "_" + siteid;
/* 112 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<AdminUser> listByRoleId(int roleid)
/*     */   {
/* 118 */     String sql = "select u.* from " + getTableName("adminuser") + " u ," + getTableName("user_role") + " ur where ur.userid=u.userid and ur.roleid=?";
/* 119 */     return this.daoSupport.queryForList(sql, AdminUser.class, new Object[] { Integer.valueOf(roleid) });
/*     */   }
/*     */ 
/*     */   public int login(String username, String password)
/*     */   {
/* 132 */     return loginBySys(username, StringUtil.md5(password));
/*     */   }
/*     */ 
/*     */   public int loginBySys(String username, String password)
/*     */   {
/* 144 */     String sql = "select * from adminuser where username=?";
/* 145 */     List userList = this.baseDaoSupport.queryForList(sql, AdminUser.class, new Object[] { username });
/* 146 */     if ((userList == null) || (userList.size() == 0)) {
/* 147 */       throw new RuntimeException("此用户不存在");
/*     */     }
/* 149 */     AdminUser user = (AdminUser)userList.get(0);
/*     */ 
/* 152 */     if (!password.equals(user.getPassword())) {
/* 153 */       throw new RuntimeException("密码错误");
/*     */     }
/*     */ 
/* 156 */     if (user.getState() == 0) {
/* 157 */       throw new RuntimeException("此用户已经被禁用");
/*     */     }
/*     */ 
/* 160 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/* 163 */     if ((site.getMulti_site().intValue() == 1) && 
/* 164 */       (user.getFounder() != 1)) {
/* 165 */       MultiSite childSite = EopContext.getContext().getCurrentChildSite();
/* 166 */       if ((user.getSiteid() == null) || (childSite.getSiteid() != user.getSiteid())) {
/* 167 */         throw new RuntimeException("非此站点管理员");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 173 */     List authList = this.permissionManager.getUesrAct(user.getUserid().intValue());
/* 174 */     user.setAuthList(authList);
/*     */ 
/* 178 */     int logincount = site.getLogincount();
/* 179 */     long lastlogin = site.getLastlogin().longValue() * 1000L;
/* 180 */     Date today = new Date();
/* 181 */     if (DateUtil.toString(new Date(lastlogin), "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
/* 182 */       logincount++;
/*     */     else {
/* 184 */       logincount = 1;
/*     */     }
/*     */ 
/* 187 */     site.setLogincount(logincount);
/* 188 */     site.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
/* 189 */     this.daoSupport.execute("update eop_site set logincount=?, lastlogin=? where id=?", new Object[] { Integer.valueOf(logincount), site.getLastlogin(), site.getId() });
/*     */ 
/* 192 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 194 */     UserContext userContext = new UserContext(site.getUserid(), site.getId(), user.getUserid());
/*     */ 
/* 196 */     sessonContext.setAttribute("usercontext", userContext);
/* 197 */     sessonContext.setAttribute("admin_user_key", user);
/* 198 */     this.adminUserPluginBundle.onLogin(user);
/* 199 */     return user.getUserid().intValue();
/*     */   }
/*     */ 
/*     */   public AdminUser getCurrentUser()
/*     */   {
/* 204 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 206 */     return (AdminUser)sessonContext.getAttribute("admin_user_key");
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager()
/*     */   {
/* 211 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 215 */     this.permissionManager = permissionManager;
/*     */   }
/*     */   public AdminUserPluginBundle getAdminUserPluginBundle() {
/* 218 */     return this.adminUserPluginBundle;
/*     */   }
/*     */   public void setAdminUserPluginBundle(AdminUserPluginBundle adminUserPluginBundle) {
/* 221 */     this.adminUserPluginBundle = adminUserPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.AdminUserManagerImpl
 * JD-Core Version:    0.6.0
 */