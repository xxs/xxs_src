/*    */ package com.enation.eop.sdk.database;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.database.IDBRouter;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class BaseSupport<T>
/*    */ {
/* 12 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   private IDBRouter baseDBRouter;
/*    */   protected IDaoSupport<T> baseDaoSupport;
/*    */   protected IDaoSupport<T> daoSupport;
/*    */ 
/*    */   protected String getTableName(String moude)
/*    */   {
/* 24 */     return this.baseDBRouter.getTableName(moude);
/*    */   }
/*    */ 
/*    */   protected void checkIsOwner(Integer userid)
/*    */   {
/* 34 */     if (userid == null) {
/* 35 */       throw new PermssionRuntimeException();
/*    */     }
/*    */ 
/* 38 */     Integer suserid = EopContext.getContext().getCurrentSite().getUserid();
/*    */ 
/* 40 */     if (suserid.intValue() != userid.intValue())
/* 41 */       throw new PermssionRuntimeException();
/*    */   }
/*    */ 
/*    */   public IDaoSupport<T> getBaseDaoSupport()
/*    */   {
/* 46 */     return this.baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
/* 50 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setDaoSupport(IDaoSupport<T> daoSupport) {
/* 54 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ 
/*    */   public IDBRouter getBaseDBRouter() {
/* 58 */     return this.baseDBRouter;
/*    */   }
/*    */ 
/*    */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 62 */     this.baseDBRouter = baseDBRouter;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.database.BaseSupport
 * JD-Core Version:    0.6.0
 */