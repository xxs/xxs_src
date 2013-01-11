/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.resource.IAppManager;
/*    */ import com.enation.eop.resource.model.EopApp;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AppManagerImpl
/*    */   implements IAppManager
/*    */ {
/*    */   private IDaoSupport<EopApp> daoSupport;
/*    */ 
/*    */   public EopApp get(String appid)
/*    */   {
/* 21 */     String sql = "select * from eop_app where id=?";
/* 22 */     return (EopApp)this.daoSupport.queryForObject(sql, EopApp.class, new Object[] { appid });
/*    */   }
/*    */ 
/*    */   public List<EopApp> list()
/*    */   {
/* 27 */     String sql = "select * from eop_app";
/* 28 */     return this.daoSupport.queryForList(sql, EopApp.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public IDaoSupport<EopApp> getDaoSupport() {
/* 32 */     return this.daoSupport;
/*    */   }
/*    */ 
/*    */   public void setDaoSupport(IDaoSupport<EopApp> daoSupport) {
/* 36 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ 
/*    */   public void add(EopApp app)
/*    */   {
/* 41 */     this.daoSupport.insert("eop_app", app);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.AppManagerImpl
 * JD-Core Version:    0.6.0
 */