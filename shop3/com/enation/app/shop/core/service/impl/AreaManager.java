/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.DlyArea;
/*    */ import com.enation.app.shop.core.service.IAreaManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AreaManager extends BaseSupport<DlyArea>
/*    */   implements IAreaManager
/*    */ {
/*    */   public void delete(String id)
/*    */   {
/* 15 */     if ((id == null) || (id.equals("")))
/* 16 */       return;
/* 17 */     String sql = "delete from dly_area where area_id in (" + id + ")";
/* 18 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public List getAll()
/*    */   {
/* 23 */     String sql = "select * from dly_area order by area_id desc";
/* 24 */     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
/* 25 */     return list;
/*    */   }
/*    */ 
/*    */   public Page pageArea(String order, int page, int pageSize)
/*    */   {
/* 30 */     order = order == null ? " area_id desc" : order;
/* 31 */     String sql = "select  * from  dly_area";
/* 32 */     sql = sql + " order by  " + order;
/* 33 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 34 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void saveAdd(String name)
/*    */   {
/* 39 */     DlyArea dlyArea = new DlyArea();
/* 40 */     dlyArea.setName(name);
/* 41 */     this.baseDaoSupport.insert("dly_area", dlyArea);
/*    */   }
/*    */ 
/*    */   public void saveEdit(Integer areaId, String name)
/*    */   {
/* 46 */     String sql = "update dly_area set name = ? where area_id=?";
/* 47 */     this.baseDaoSupport.execute(sql, new Object[] { name, areaId });
/*    */   }
/*    */ 
/*    */   public DlyArea getDlyAreaById(Integer areaId)
/*    */   {
/* 52 */     String sql = "select * from dly_area where area_id=?";
/* 53 */     DlyArea a = (DlyArea)this.baseDaoSupport.queryForObject(sql, DlyArea.class, new Object[] { areaId });
/* 54 */     return a;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.AreaManager
 * JD-Core Version:    0.6.0
 */