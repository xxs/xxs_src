/*     */ package com.enation.app.base.core.service.auth.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.AuthAction;
/*     */ import com.enation.app.base.core.model.Role;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PermissionManager extends BaseSupport
/*     */   implements IPermissionManager
/*     */ {
/*     */   public void giveRolesToUser(int userid, int[] roleids)
/*     */   {
/*  27 */     this.baseDaoSupport.execute("delete from user_role where userid=?", new Object[] { Integer.valueOf(userid) });
/*  28 */     if (roleids == null) return;
/*  29 */     for (int roleid : roleids)
/*  30 */       this.baseDaoSupport.execute("insert into user_role(roleid,userid)values(?,?)", new Object[] { Integer.valueOf(roleid), Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public List<AuthAction> getUesrAct(int userid, String acttype)
/*     */   {
/*  43 */     String sql = "select * from " + getTableName("auth_action") + " where type=? ";
/*     */ 
/*  45 */     sql = sql + " and actid in(select authid from  " + getTableName("role_auth") + " where roleid in ";
/*     */ 
/*  47 */     sql = sql + " (select roleid from " + getTableName("user_role") + " where userid=?)";
/*  48 */     sql = sql + " )";
/*     */ 
/*  50 */     return this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { acttype, Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public List<AuthAction> getUesrAct(int userid)
/*     */   {
/*  57 */     String sql = "select * from " + getTableName("auth_action") + " where ";
/*     */ 
/*  59 */     sql = sql + "   actid in(select authid from  " + getTableName("role_auth") + " where roleid in ";
/*     */ 
/*  61 */     sql = sql + " (select roleid from " + getTableName("user_role") + " where userid=?)";
/*  62 */     sql = sql + " )";
/*     */ 
/*  64 */     return this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public boolean checkHaveAuth(int actid)
/*     */   {
/*  73 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/*  75 */     AdminUser user = (AdminUser)sessonContext.getAttribute("admin_user_key");
/*  76 */     if (user.getFounder() == 1) return true;
/*  77 */     List authList = user.getAuthList();
/*  78 */     for (AuthAction auth : authList) {
/*  79 */       if (auth.getActid().intValue() == actid) {
/*  80 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   public List<Role> getUserRoles(int userid)
/*     */   {
/*  94 */     return this.baseDaoSupport.queryForList("select roleid from  user_role where userid=?", new Object[] { Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public void cleanUserRoles(int userid)
/*     */   {
/* 103 */     this.baseDaoSupport.execute("delete from user_role where userid=?", new Object[] { Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public boolean checkHaveRole(int userid, int roleid)
/*     */   {
/* 108 */     int count = this.baseDaoSupport.queryForInt("select count(0) from  user_role where userid=? and roleid=?", new Object[] { Integer.valueOf(userid), Integer.valueOf(roleid) });
/* 109 */     return count > 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.PermissionManager
 * JD-Core Version:    0.6.0
 */