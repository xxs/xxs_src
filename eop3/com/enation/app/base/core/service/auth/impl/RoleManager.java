/*     */ package com.enation.app.base.core.service.auth.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.AuthAction;
/*     */ import com.enation.app.base.core.model.Role;
/*     */ import com.enation.app.base.core.service.auth.IRoleManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class RoleManager extends BaseSupport<Role>
/*     */   implements IRoleManager
/*     */ {
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(Role role, int[] authids)
/*     */   {
/*  30 */     this.baseDaoSupport.insert("role", role);
/*     */ 
/*  33 */     if (authids == null) return;
/*     */ 
/*  36 */     int roleid = this.baseDaoSupport.getLastId("role");
/*     */ 
/*  40 */     for (int authid : authids)
/*  41 */       this.baseDaoSupport.execute("insert into role_auth(roleid,authid)values(?,?)", new Object[] { Integer.valueOf(roleid), Integer.valueOf(authid) });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(int roleid)
/*     */   {
/*  54 */     this.baseDaoSupport.execute("delete from user_role where roleid=?", new Object[] { Integer.valueOf(roleid) });
/*     */ 
/*  57 */     this.baseDaoSupport.execute("delete from role_auth where roleid =?", new Object[] { Integer.valueOf(roleid) });
/*     */ 
/*  60 */     this.baseDaoSupport.execute("delete from role where roleid =?", new Object[] { Integer.valueOf(roleid) });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(Role role, int[] authids) {
/*  66 */     if (role.getRoleid().intValue() == 0) throw new IllegalArgumentException("编辑角色时id不可为空");
/*  67 */     if (StringUtil.isEmpty(role.getRolename())) throw new IllegalArgumentException("编辑角色时名称不可为空");
/*     */ 
/*  70 */     this.baseDaoSupport.execute("delete from role_auth where roleid=?", new Object[] { role.getRoleid() });
/*     */ 
/*  73 */     for (int authid : authids) {
/*  74 */       this.baseDaoSupport.execute("insert into role_auth(roleid,authid)values(?,?)", new Object[] { role.getRoleid(), Integer.valueOf(authid) });
/*     */     }
/*     */ 
/*  77 */     this.baseDaoSupport.update("role", role, "roleid=" + role.getRoleid());
/*     */   }
/*     */ 
/*     */   public List<Role> list()
/*     */   {
/*  86 */     return this.baseDaoSupport.queryForList("select * from role", Role.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Role get(int roleid)
/*     */   {
/*  97 */     String sql = "select * from " + getTableName("auth_action") + " where actid in(select authid from " + getTableName("role_auth") + " where roleid =?)";
/*  98 */     List actlist = this.daoSupport.queryForList(sql, AuthAction.class, new Object[] { Integer.valueOf(roleid) });
/*  99 */     Role role = (Role)this.baseDaoSupport.queryForObject("select * from role where roleid=?", Role.class, new Object[] { Integer.valueOf(roleid) });
/*     */ 
/* 101 */     if (actlist != null) {
/* 102 */       int[] actids = new int[actlist.size()];
/* 103 */       int i = 0; for (int len = actlist.size(); i < len; i++) {
/* 104 */         actids[i] = ((AuthAction)actlist.get(i)).getActid().intValue();
/*     */       }
/* 106 */       role.setActids(actids);
/*     */     }
/* 108 */     return role;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.RoleManager
 * JD-Core Version:    0.6.0
 */