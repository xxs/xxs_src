/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
/*    */ import com.enation.app.shop.core.service.IOrderAllocationManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.impl.IRowMapperColumnFilter;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OrderAllocationManager extends BaseSupport
/*    */   implements IOrderAllocationManager
/*    */ {
/*    */   private OrderPluginBundle orderPluginBundle;
/*    */ 
/*    */   public OrderPluginBundle getOrderPluginBundle()
/*    */   {
/* 24 */     return this.orderPluginBundle;
/*    */   }
/*    */ 
/*    */   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
/* 28 */     this.orderPluginBundle = orderPluginBundle;
/*    */   }
/*    */ 
/*    */   public List listAllocation(int orderid)
/*    */   {
/* 44 */     String sql = "select oi.sn sn ,oi.name  gname,oi.price price,a.iscmpl iscmpl,a.num num ,d.name a_name ,oi.addon addon,oi.cat_id cat_id,oi.image image";
/* 45 */     sql = sql + " from    " + getTableName("allocation_item") + " a ";
/* 46 */     sql = sql + " LEFT JOIN " + getTableName("order_items") + " oi on a.itemid= oi.item_id";
/* 47 */     sql = sql + " LEFT JOIN " + getTableName("depot") + " d on a.depotid= d.id ";
/* 48 */     sql = sql + " where a.orderid=?";
/*    */ 
/* 51 */     IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
/*    */     {
/*    */       public void filter(Map colValues, ResultSet rs) throws SQLException {
/* 54 */         int catid = rs.getInt("cat_id");
/* 55 */         OrderAllocationManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);
/*    */       }
/*    */     };
/* 59 */     return this.daoSupport.queryForList(sql, columnFilter, new Object[] { Integer.valueOf(orderid) });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderAllocationManager
 * JD-Core Version:    0.6.0
 */