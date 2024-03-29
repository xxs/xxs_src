/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.resource.IUserDetailManager;
/*    */ import com.enation.eop.resource.model.EopUserDetail;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ 
/*    */ public class UserDetailManagerImpl
/*    */   implements IUserDetailManager
/*    */ {
/*    */   private IDaoSupport<EopUserDetail> daoSupport;
/*    */ 
/*    */   public void add(EopUserDetail eopUserDetail)
/*    */   {
/* 15 */     this.daoSupport.insert("eop_userdetail", eopUserDetail);
/*    */   }
/*    */ 
/*    */   public void edit(EopUserDetail eopUserDetail)
/*    */   {
/* 20 */     this.daoSupport.update("eop_userdetail", eopUserDetail, " userid = " + eopUserDetail.getUserid());
/*    */   }
/*    */ 
/*    */   public EopUserDetail get(Integer userid)
/*    */   {
/* 25 */     return (EopUserDetail)this.daoSupport.queryForObject("select * from eop_userdetail where userid = ?", EopUserDetail.class, new Object[] { userid });
/*    */   }
/*    */ 
/*    */   public IDaoSupport<EopUserDetail> getDaoSupport()
/*    */   {
/* 30 */     return this.daoSupport;
/*    */   }
/*    */ 
/*    */   public void setDaoSupport(IDaoSupport<EopUserDetail> daoSupport) {
/* 34 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.UserDetailManagerImpl
 * JD-Core Version:    0.6.0
 */