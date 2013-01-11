/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.PromotionActivity;
/*    */ import com.enation.app.shop.core.service.IPromotionActivityManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.ObjectNotFoundException;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ public class PromotionActivityManager extends BaseSupport<PromotionActivity>
/*    */   implements IPromotionActivityManager
/*    */ {
/*    */   public void add(PromotionActivity activity)
/*    */   {
/* 25 */     if (activity == null) throw new IllegalArgumentException("param activity is NULL");
/* 26 */     if (activity.getName() == null) throw new IllegalArgumentException("param activity.name is NULL");
/* 27 */     if (activity.getBegin_time() == null) throw new IllegalArgumentException("param activity.begin_time is NULL");
/* 28 */     if (activity.getEnd_time() == null) throw new IllegalArgumentException("param activity.end_time is NULL");
/* 29 */     this.baseDaoSupport.insert("promotion_activity", activity);
/*    */   }
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void delete(Integer[] idArray)
/*    */   {
/* 36 */     if ((idArray != null) && (idArray.length > 0)) {
/* 37 */       String ids = StringUtil.arrayToString(idArray, ",");
/* 38 */       this.baseDaoSupport.execute("delete from promotion_activity where id in (" + ids + ")", new Object[0]);
/*    */ 
/* 41 */       this.daoSupport.execute("delete from " + getTableName("pmt_member_lv") + " where pmt_id in(select pmt_id from " + getTableName("promotion") + " where pmta_id in(?))", new Object[] { ids });
/* 42 */       this.daoSupport.execute("delete from " + getTableName("pmt_goods") + " where pmt_id in(select pmt_id from " + getTableName("promotion") + " where pmta_id in(?))", new Object[] { ids });
/* 43 */       this.baseDaoSupport.execute("delete from promotion where pmta_id in(?)", new Object[] { ids });
/*    */     }
/*    */   }
/*    */ 
/*    */   public void edit(PromotionActivity activity)
/*    */   {
/* 50 */     if (activity.getId() == null) throw new IllegalArgumentException("param activity.id is NULL");
/* 51 */     if (activity.getName() == null) throw new IllegalArgumentException("param activity.name is NULL");
/* 52 */     if (activity.getBegin_time() == null) throw new IllegalArgumentException("param activity.begin_time is NULL");
/* 53 */     if (activity.getEnd_time() == null) throw new IllegalArgumentException("param activity.end_time is NULL");
/* 54 */     this.baseDaoSupport.update("promotion_activity", activity, "id=" + activity.getId());
/*    */   }
/*    */ 
/*    */   public PromotionActivity get(Integer id)
/*    */   {
/* 61 */     if (id == null) throw new IllegalArgumentException("param activity.id is NULL");
/* 62 */     PromotionActivity activity = (PromotionActivity)this.baseDaoSupport.queryForObject("select * from promotion_activity where id = ?", PromotionActivity.class, new Object[] { id });
/*    */ 
/* 65 */     if (activity == null) throw new ObjectNotFoundException("activity is NULL");
/* 66 */     return activity;
/*    */   }
/*    */ 
/*    */   public Page list(int pageNo, int pageSize)
/*    */   {
/* 71 */     String sql = "select * from promotion_activity order by id desc";
/* 72 */     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.PromotionActivityManager
 * JD-Core Version:    0.6.0
 */