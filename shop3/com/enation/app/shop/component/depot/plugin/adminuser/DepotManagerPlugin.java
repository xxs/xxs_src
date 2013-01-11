/*     */ package com.enation.app.shop.component.depot.plugin.adminuser;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.user.IAdminUserDeleteEvent;
/*     */ import com.enation.app.base.core.plugin.user.IAdminUserInputDisplayEvent;
/*     */ import com.enation.app.base.core.plugin.user.IAdminUserLoginEvent;
/*     */ import com.enation.app.base.core.plugin.user.IAdminUserOnAddEvent;
/*     */ import com.enation.app.base.core.plugin.user.IAdminUserOnEditEvent;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.IntegerMapper;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class DepotManagerPlugin extends AutoRegisterPlugin
/*     */   implements IAdminUserOnAddEvent, IAdminUserOnEditEvent, IAdminUserInputDisplayEvent, IAdminUserDeleteEvent, IAdminUserLoginEvent
/*     */ {
/*     */   private IDepotManager depotManager;
/*     */   private IDaoSupport baseDaoSupport;
/*     */ 
/*     */   public String getInputHtml(AdminUser user)
/*     */   {
/*  39 */     List roomList = this.depotManager.list();
/*  40 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  41 */     freeMarkerPaser.putData("roomList", roomList);
/*     */ 
/*  43 */     if (user != null) {
/*  44 */       Integer depotid = (Integer)this.baseDaoSupport.queryForObject("select depotid from depot_user where userid=?", new IntegerMapper(), new Object[] { user.getUserid() });
/*  45 */       freeMarkerPaser.putData("depotid", depotid);
/*     */     }
/*     */ 
/*  48 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onEdit(Integer userid)
/*     */   {
/*  53 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  54 */     String depotid = request.getParameter("depotid");
/*  55 */     if (!StringUtil.isEmpty(depotid)) {
/*  56 */       String sql = "select count(0) from depot_user where  userid=?";
/*  57 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[] { userid });
/*  58 */       if (count > 0)
/*  59 */         this.baseDaoSupport.execute("update depot_user set depotid=? where userid=?", new Object[] { depotid, userid });
/*     */       else
/*  61 */         this.baseDaoSupport.execute("insert into depot_user(userid,depotid)values(?,?)", new Object[] { userid, depotid });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAdd(Integer userid)
/*     */   {
/*  67 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  68 */     String depotid = request.getParameter("depotid");
/*  69 */     if (!StringUtil.isEmpty(depotid))
/*  70 */       this.baseDaoSupport.execute("insert into depot_user(userid,depotid)values(?,?)", new Object[] { userid, depotid });
/*     */   }
/*     */ 
/*     */   public void onDelete(int userid)
/*     */   {
/*  77 */     this.baseDaoSupport.execute("delete from depot_user where userid=?", new Object[] { Integer.valueOf(userid) });
/*     */   }
/*     */ 
/*     */   public void onLogin(AdminUser user)
/*     */   {
/*  82 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/*  84 */     Integer depotid = (Integer)this.baseDaoSupport.queryForObject("select depotid from depot_user where userid=?", new IntegerMapper(), new Object[] { user.getUserid() });
/*  85 */     DepotUser stockUser = new DepotUser();
/*  86 */     stockUser.setFounder(user.getFounder());
/*  87 */     stockUser.setPassword(user.getPassword());
/*  88 */     stockUser.setRealname(user.getRealname());
/*  89 */     stockUser.setRemark(user.getRemark());
/*  90 */     stockUser.setRoleids(user.getRoleids());
/*  91 */     stockUser.setSiteid(user.getSiteid());
/*  92 */     stockUser.setState(user.getState());
/*  93 */     stockUser.setUserdept(user.getUserdept());
/*  94 */     stockUser.setUserid(user.getUserid());
/*  95 */     stockUser.setUsername(user.getUsername());
/*  96 */     stockUser.setUserno(user.getUserno());
/*  97 */     stockUser.setDateline(user.getDateline());
/*  98 */     if (depotid != null)
/*  99 */       stockUser.setDepotid(depotid);
/* 100 */     stockUser.setAuthList(user.getAuthList());
/* 101 */     sessonContext.setAttribute("admin_user_key", stockUser);
/*     */   }
/*     */ 
/*     */   public IDepotManager getDepotManager()
/*     */   {
/* 108 */     return this.depotManager;
/*     */   }
/*     */ 
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 112 */     this.depotManager = depotManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport() {
/* 116 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 120 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.depot.plugin.adminuser.DepotManagerPlugin
 * JD-Core Version:    0.6.0
 */