/*    */ package com.enation.app.base.core.service.solution;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopProduct;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ 
/*    */ public class InstallerManager
/*    */ {
/*    */   private IDaoSupport<EopProduct> daoSupport;
/*    */ 
/*    */   public void add(Installer installer)
/*    */   {
/* 16 */     String sql = "select count(0) from eop_installer where ip=?";
/* 17 */     int count = this.daoSupport.queryForInt(sql, new Object[] { installer.getIp() });
/* 18 */     if (count == 0)
/* 19 */       this.daoSupport.insert("eop_installer", installer);
/*    */   }
/*    */ 
/*    */   public void add1(Installer installer) {
/* 23 */     this.daoSupport.insert("eop_installer", installer);
/*    */   }
/*    */ 
/*    */   public Page list(int pageNo, int pageSize)
/*    */   {
/* 28 */     return this.daoSupport.queryForPage("select * from eop_installer order by installtime desc", pageNo, pageSize, new Object[0]);
/*    */   }
/*    */ 
/*    */   public IDaoSupport<EopProduct> getDaoSupport()
/*    */   {
/* 33 */     return this.daoSupport;
/*    */   }
/*    */ 
/*    */   public void setDaoSupport(IDaoSupport<EopProduct> daoSupport) {
/* 37 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.InstallerManager
 * JD-Core Version:    0.6.0
 */