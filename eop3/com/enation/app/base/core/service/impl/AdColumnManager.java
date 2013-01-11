/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.AdColumn;
/*    */ import com.enation.app.base.core.service.IAdColumnManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AdColumnManager extends BaseSupport<AdColumn>
/*    */   implements IAdColumnManager
/*    */ {
/*    */   public void addAdvc(AdColumn adColumn)
/*    */   {
/* 23 */     this.baseDaoSupport.insert("adcolumn", adColumn);
/*    */   }
/*    */ 
/*    */   public void delAdcs(String ids)
/*    */   {
/* 28 */     if ((ids == null) || (ids.equals("")))
/* 29 */       return;
/* 30 */     String sql = "delete from adcolumn where acid in (" + ids + ")";
/*    */ 
/* 32 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public AdColumn getADcolumnDetail(Long acid)
/*    */   {
/* 37 */     AdColumn adColumn = (AdColumn)this.baseDaoSupport.queryForObject("select * from adcolumn where acid = ?", AdColumn.class, new Object[] { acid });
/* 38 */     return adColumn;
/*    */   }
/*    */ 
/*    */   public List listAllAdvPos()
/*    */   {
/* 43 */     List list = this.baseDaoSupport.queryForList("select * from adcolumn", AdColumn.class, new Object[0]);
/* 44 */     return list;
/*    */   }
/*    */ 
/*    */   public Page pageAdvPos(int page, int pageSize)
/*    */   {
/* 49 */     String sql = "select * from adcolumn order by acid";
/* 50 */     Page rpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 51 */     return rpage;
/*    */   }
/*    */ 
/*    */   public void updateAdvc(AdColumn adColumn)
/*    */   {
/* 56 */     this.baseDaoSupport.update("adcolumn", adColumn, "acid = " + adColumn.getAcid());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.AdColumnManager
 * JD-Core Version:    0.6.0
 */