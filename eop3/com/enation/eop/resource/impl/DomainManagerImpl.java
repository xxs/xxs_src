/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.resource.IDomainManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.EopSiteDomain;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DomainManagerImpl
/*    */   implements IDomainManager
/*    */ {
/*    */   private IDaoSupport<EopSiteDomain> daoSupport;
/*    */ 
/*    */   public EopSiteDomain get(Integer id)
/*    */   {
/* 23 */     String sql = "select * from eop_sitedomain where id = ?";
/* 24 */     return (EopSiteDomain)this.daoSupport.queryForObject(sql, EopSiteDomain.class, new Object[] { id });
/*    */   }
/*    */ 
/*    */   public List<EopSiteDomain> listUserDomain()
/*    */   {
/* 29 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 30 */     String sql = "select * from eop_sitedomain where userid=?";
/* 31 */     return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { userid });
/*    */   }
/*    */ 
/*    */   public List<EopSiteDomain> listSiteDomain()
/*    */   {
/* 36 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 37 */     String sql = "select * from eop_sitedomain where userid=? and siteid =?";
/* 38 */     return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { site.getUserid(), site.getId() });
/*    */   }
/*    */ 
/*    */   public List<EopSiteDomain> listSiteDomain(Integer siteid)
/*    */   {
/* 43 */     String sql = "select * from eop_sitedomain where  siteid =?";
/* 44 */     return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { siteid });
/*    */   }
/*    */ 
/*    */   public void edit(EopSiteDomain domain) {
/* 48 */     this.daoSupport.update("eop_sitedomain", domain, " id = " + domain.getId());
/*    */   }
/*    */ 
/*    */   public IDaoSupport<EopSiteDomain> getDaoSupport()
/*    */   {
/* 53 */     return this.daoSupport;
/*    */   }
/*    */ 
/*    */   public void setDaoSupport(IDaoSupport<EopSiteDomain> daoSupport) {
/* 57 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ 
/*    */   public int getDomainCount(Integer siteid)
/*    */   {
/* 62 */     String sql = "select count(0)  from eop_sitedomain where  siteid =?";
/* 63 */     return this.daoSupport.queryForInt(sql, new Object[] { siteid });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.DomainManagerImpl
 * JD-Core Version:    0.6.0
 */