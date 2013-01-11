/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.FreeOfferCategory;
/*    */ import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FreeOfferCategoryManager extends BaseSupport<FreeOfferCategory>
/*    */   implements IFreeOfferCategoryManager
/*    */ {
/*    */   public void clean(String bid)
/*    */   {
/* 24 */     if ((bid == null) || (bid.equals("")))
/* 25 */       return;
/* 26 */     String sql = "delete  from  freeoffer_category   where cat_id in (" + bid + ")";
/*    */ 
/* 28 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public void delete(String bid)
/*    */   {
/* 33 */     if ((bid == null) || (bid.equals("")))
/* 34 */       return;
/* 35 */     String sql = "update freeoffer_category set disabled=1  where cat_id in (" + bid + ")";
/*    */ 
/* 37 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public FreeOfferCategory get(int cat_id)
/*    */   {
/* 43 */     String sql = "select * from freeoffer_category where cat_id=?";
/* 44 */     return (FreeOfferCategory)this.baseDaoSupport.queryForObject(sql, FreeOfferCategory.class, new Object[] { Integer.valueOf(cat_id) });
/*    */   }
/*    */ 
/*    */   public List getFreeOfferCategoryList()
/*    */   {
/* 50 */     String sql = "select * from freeoffer_category";
/* 51 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Page pageTrash(String name, String order, int page, int pageSize)
/*    */   {
/* 56 */     order = order == null ? " cat_id desc" : order;
/* 57 */     String sql = "select * from freeoffer_category";
/* 58 */     name = " disabled=1 and cat_name like '%" + name + "%'";
/* 59 */     sql = sql + " where " + name;
/* 60 */     sql = sql + " order by  " + order;
/* 61 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 62 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void revert(String bid)
/*    */   {
/* 67 */     if ((bid == null) || (bid.equals("")))
/* 68 */       return;
/* 69 */     String sql = "update freeoffer_category set disabled=0  where cat_id in (" + bid + ")";
/*    */ 
/* 71 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public void saveAdd(FreeOfferCategory freeOfferCategory)
/*    */   {
/* 76 */     this.baseDaoSupport.insert("freeoffer_category", freeOfferCategory);
/*    */   }
/*    */ 
/*    */   public Page searchFreeOfferCategory(String name, String order, int page, int pageSize)
/*    */   {
/* 82 */     order = order == null ? " cat_id desc" : order;
/* 83 */     String sql = "select * from freeoffer_category";
/* 84 */     name = " disabled=0 and cat_name like '%" + name + "%'";
/* 85 */     sql = sql + " where " + name;
/* 86 */     sql = sql + " order by  " + order;
/* 87 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 88 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void update(FreeOfferCategory freeOfferCategory)
/*    */   {
/* 93 */     this.baseDaoSupport.update("freeoffer_category", freeOfferCategory, "cat_id=" + freeOfferCategory.getCat_id());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.FreeOfferCategoryManager
 * JD-Core Version:    0.6.0
 */