/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.DlyCenter;
/*    */ import com.enation.app.shop.core.service.IDlyCenterManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DlyCenterManager extends BaseSupport<DlyCenter>
/*    */   implements IDlyCenterManager
/*    */ {
/*    */   public void add(DlyCenter dlyCenter)
/*    */   {
/* 20 */     this.baseDaoSupport.insert("dly_center", dlyCenter);
/*    */   }
/*    */ 
/*    */   public void delete(Integer[] id)
/*    */   {
/* 25 */     if ((id == null) || (id.length == 0)) return;
/* 26 */     String ids = StringUtil.arrayToString(id, ",");
/* 27 */     this.baseDaoSupport.execute("update dly_center set disabled = 'true' where dly_center_id in (" + ids + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public void edit(DlyCenter dlyCenter)
/*    */   {
/* 33 */     this.baseDaoSupport.update("dly_center", dlyCenter, "dly_center_id = " + dlyCenter.getDly_center_id());
/*    */   }
/*    */ 
/*    */   public List<DlyCenter> list()
/*    */   {
/* 39 */     return this.baseDaoSupport.queryForList("select * from dly_center where disabled = 'false'", DlyCenter.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public DlyCenter get(Integer dlyCenterId)
/*    */   {
/* 44 */     return (DlyCenter)this.baseDaoSupport.queryForObject("select * from dly_center where dly_center_id = ?", DlyCenter.class, new Object[] { dlyCenterId });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.DlyCenterManager
 * JD-Core Version:    0.6.0
 */