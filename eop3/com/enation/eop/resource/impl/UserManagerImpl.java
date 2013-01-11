/*     */ package com.enation.eop.resource.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class UserManagerImpl
/*     */   implements IUserManager
/*     */ {
/*     */   private IDaoSupport daoSupport;
/*     */   private ISiteManager siteManager;
/*     */   private IAdminUserManager adminUserManager;
/*  31 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public void changeDefaultSite(Integer userid, Integer managerid, Integer defaultsiteid)
/*     */   {
/*  35 */     UserUtil.validUser(userid);
/*  36 */     String sql = "update eop_user set defaultsiteid=? where id=?";
/*  37 */     this.daoSupport.execute(sql, new Object[] { defaultsiteid, userid });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer createUser(EopUser user)
/*     */   {
/*  49 */     String sql = "select count(0) from eop_user where username=?";
/*  50 */     int count = this.daoSupport.queryForInt(sql, new Object[] { user.getUsername() });
/*  51 */     if (count > 0) throw new RuntimeException("用户" + user.getUsername() + "已存在");
/*  52 */     user.setPassword(StringUtil.md5(user.getPassword()));
/*     */ 
/*  54 */     this.daoSupport.insert("eop_user", user);
/*  55 */     Integer userid = Integer.valueOf(this.daoSupport.getLastId("eop_user"));
/*     */ 
/*  57 */     return userid;
/*     */   }
/*     */ 
/*     */   private boolean checkUserName(String username)
/*     */   {
/*  69 */     String sql = "select * from eop_user where username=?";
/*  70 */     List list = this.daoSupport.queryForList(sql, new Object[] { username });
/*     */ 
/*  72 */     return (list != null) && (!list.isEmpty()) && (list.size() != 0);
/*     */   }
/*     */ 
/*     */   public int login(String username, String password)
/*     */   {
/*  82 */     int result = 0;
/*     */     try {
/*  84 */       EopUser user = get(username);
/*  85 */       if (user.getPassword().equals(StringUtil.md5(password))) {
/*  86 */         result = 1;
/*  87 */         WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/*  89 */         sessonContext.setAttribute("eop_user_key", user);
/*     */       }
/*     */       else {
/*  92 */         result = 0;
/*     */       }
/*     */     } catch (RuntimeException e) {
/*  95 */       this.logger.error(e.fillInStackTrace());
/*     */     }
/*     */ 
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */   public int checkIsLogin()
/*     */   {
/* 103 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 105 */     EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
/* 106 */     if (user != null) {
/* 107 */       return 1;
/*     */     }
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */   public void logout()
/*     */   {
/* 114 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/* 115 */     sessonContext.removeAttribute("eop_user_key");
/*     */ 
/* 117 */     ThreadContextHolder.getSessionContext().removeAttribute("userAdmin");
/*     */   }
/*     */ 
/*     */   public EopUser getCurrentUser()
/*     */   {
/* 128 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/* 129 */     EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
/* 130 */     return user;
/*     */   }
/*     */ 
/*     */   public EopUser get(Integer userid)
/*     */   {
/* 135 */     String sql = "select * from eop_user where deleteflag = 0 and id = ?";
/* 136 */     return (EopUser)this.daoSupport.queryForObject(sql, EopUser.class, new Object[] { userid });
/*     */   }
/*     */ 
/*     */   public IDaoSupport<EopUser> getDaoSupport()
/*     */   {
/* 142 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport<EopUser> daoSupport)
/*     */   {
/* 148 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager()
/*     */   {
/* 154 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager)
/*     */   {
/* 160 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 166 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager)
/*     */   {
/* 172 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public void edit(EopUser user)
/*     */   {
/* 178 */     this.daoSupport.update("eop_user", user, "id = " + user.getId());
/*     */   }
/*     */ 
/*     */   public EopUser get(String username)
/*     */   {
/* 185 */     return (EopUser)this.daoSupport.queryForObject("select * from eop_user where username=?", EopUser.class, new Object[] { username });
/*     */   }
/*     */ 
/*     */   public Page list(String keyword, int pageNo, int pageSize)
/*     */   {
/* 190 */     String sql = "select u.*,d.regdate  regdate from eop_user u left join eop_userdetail d on  u.id= d.userid";
/* 191 */     if (!StringUtil.isEmpty(keyword)) {
/* 192 */       sql = sql + " where  u.username like '%" + keyword + "%'";
/*     */     }
/*     */ 
/* 195 */     sql = sql + " order by  d.regdate desc";
/* 196 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer userid)
/*     */   {
/* 208 */     List siteList = this.siteManager.list(userid);
/* 209 */     for (Map site : siteList) {
/* 210 */       this.siteManager.delete((Integer)site.get("id"));
/*     */     }
/*     */ 
/* 213 */     this.daoSupport.execute("delete from eop_userdetail where userid = ?", new Object[] { userid });
/* 214 */     this.daoSupport.execute("delete from eop_useradmin where userid = ?", new Object[] { userid });
/* 215 */     this.daoSupport.execute("delete from eop_user where id = ?", new Object[] { userid });
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.UserManagerImpl
 * JD-Core Version:    0.6.0
 */