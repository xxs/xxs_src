/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Logi;
/*    */ import com.enation.app.shop.core.service.ILogiManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LogiManager extends BaseSupport<Logi>
/*    */   implements ILogiManager
/*    */ {
/*    */   public void delete(String id)
/*    */   {
/* 14 */     if ((id == null) || (id.equals("")))
/* 15 */       return;
/* 16 */     String sql = "delete from logi_company where id in (" + id + ")";
/* 17 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Logi getLogiById(Integer id)
/*    */   {
/* 22 */     String sql = "select * from logi_company where id=?";
/* 23 */     Logi a = (Logi)this.baseDaoSupport.queryForObject(sql, Logi.class, new Object[] { id });
/* 24 */     return a;
/*    */   }
/*    */ 
/*    */   public Page pageLogi(String order, Integer page, Integer pageSize)
/*    */   {
/* 29 */     order = order == null ? " id desc" : order;
/* 30 */     String sql = "select * from logi_company";
/* 31 */     sql = sql + " order by  " + order;
/* 32 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);
/* 33 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void saveAdd(Logi logi)
/*    */   {
/* 38 */     this.baseDaoSupport.insert("logi_company", logi);
/*    */   }
/*    */ 
/*    */   public void saveEdit(Logi logi)
/*    */   {
/* 43 */     this.baseDaoSupport.update("logi_company", logi, "id=" + logi.getId());
/*    */   }
/*    */ 
/*    */   public List list() {
/* 47 */     String sql = "select * from logi_company";
/* 48 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.LogiManager
 * JD-Core Version:    0.6.0
 */