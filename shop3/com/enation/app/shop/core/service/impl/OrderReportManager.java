/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.DeliveryItem;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.model.RefundLog;
/*     */ import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.impl.IRowMapperColumnFilter;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class OrderReportManager extends BaseSupport
/*     */   implements IOrderReportManager
/*     */ {
/*     */   private IAdminUserManager adminUserManager;
/*     */   private OrderPluginBundle orderPluginBundle;
/*     */   private IPermissionManager permissionManager;
/*     */ 
/*     */   public Delivery getDelivery(Integer deliveryId)
/*     */   {
/*  40 */     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("delivery") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.delivery_id = ?";
/*  41 */     Delivery delivery = (Delivery)this.daoSupport.queryForObject(sql, Delivery.class, new Object[] { deliveryId });
/*  42 */     return delivery;
/*     */   }
/*     */ 
/*     */   public List<Delivery> getDeliveryList(int orderId) {
/*  46 */     String sql = "select * from " + getTableName("delivery") + " where order_id=" + orderId;
/*  47 */     return this.daoSupport.queryForList(sql, Delivery.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public PaymentLog getPayment(Integer paymentId)
/*     */   {
/*  52 */     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("payment_logs") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.payment_id = ?";
/*  53 */     PaymentLog paymentLog = (PaymentLog)this.daoSupport.queryForObject(sql, PaymentLog.class, new Object[] { paymentId });
/*  54 */     return paymentLog;
/*     */   }
/*     */ 
/*     */   public List<DeliveryItem> listDeliveryItem(Integer deliveryId)
/*     */   {
/*  59 */     String sql = "select * from delivery_item where delivery_id = ?";
/*  60 */     return this.baseDaoSupport.queryForList(sql, DeliveryItem.class, new Object[] { deliveryId });
/*     */   }
/*     */ 
/*     */   public Page listPayment(int pageNo, int pageSize, String order)
/*     */   {
/*  68 */     String sql = "select * from payment_logs order by create_time desc";
/*  69 */     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<PaymentLog> listPayLogs(Integer orderId)
/*     */   {
/*  77 */     return this.baseDaoSupport.queryForList("select * from payment_logs where order_id = ? ", PaymentLog.class, new Object[] { orderId });
/*     */   }
/*     */ 
/*     */   public Page listRefund(int pageNo, int pageSize, String order)
/*     */   {
/*  86 */     order = order == null ? "l.payment_id desc" : order;
/*  87 */     String sql = "select * from refund_logs ";
/*  88 */     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, RefundLog.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<RefundLog> listRefundLogs(Integer order_id)
/*     */   {
/*  96 */     return this.baseDaoSupport.queryForList("select * from refund_logs where order_id = ? ", RefundLog.class, new Object[] { order_id });
/*     */   }
/*     */ 
/*     */   private Page listDelivery(int pageNo, int pageSize, String order, int type)
/*     */   {
/* 101 */     order = order == null ? "delivery_id desc" : order;
/* 102 */     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("delivery") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.type = " + type;
/* 103 */     sql = sql + " order by " + order;
/* 104 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, Delivery.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page listReturned(int pageNo, int pageSize, String order)
/*     */   {
/* 109 */     return listDelivery(pageNo, pageSize, order, 2);
/*     */   }
/*     */ 
/*     */   public Page listShipping(int pageNo, int pageSize, String order)
/*     */   {
/* 114 */     return listDelivery(pageNo, pageSize, order, 1);
/*     */   }
/*     */ 
/*     */   public List listDelivery(Integer orderId, Integer type)
/*     */   {
/* 119 */     return this.baseDaoSupport.queryForList("select * from delivery where order_id = ? and type = ?", new Object[] { orderId, type });
/*     */   }
/*     */ 
/*     */   public Page listAllocation(int pageNo, int pageSize)
/*     */   {
/* 128 */     DepotUser depotUser = (DepotUser)this.adminUserManager.getCurrentUser();
/* 129 */     int depotid = depotUser.getDepotid().intValue();
/* 130 */     String sql = "select o.sn,o.status,a.*,i.name as name,i.cat_id,i.addon  from  " + getTableName("allocation_item") + " a left join " + getTableName("order_items") + " i on a.itemid= i.item_id left join  " + getTableName("order") + " o on a.orderid= o.order_id where a.depotid=?  order by o.create_time desc";
/*     */ 
/* 134 */     IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
/*     */     {
/*     */       public void filter(Map colValues, ResultSet rs) throws SQLException
/*     */       {
/* 138 */         int catid = rs.getInt("cat_id");
/* 139 */         OrderReportManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);
/*     */       }
/*     */     };
/* 145 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, columnFilter, new Object[] { Integer.valueOf(depotid) });
/*     */   }
/*     */ 
/*     */   public Page listAllocation(int depotid, int status, int pageNo, int pageSize)
/*     */   {
/* 154 */     String sql = "select o.sn,o.status,a.*,i.name as name,i.cat_id,i.addon  from  " + getTableName("allocation_item") + " a left join " + getTableName("order_items") + " i on a.itemid= i.item_id left join  " + getTableName("order") + " o on a.orderid= o.order_id  where  a.iscmpl=? ";
/*     */ 
/* 157 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/* 158 */     if (!isSuperAdmin) {
/* 159 */       DepotUser depotUser = (DepotUser)this.adminUserManager.getCurrentUser();
/* 160 */       depotid = depotUser.getDepotid().intValue();
/* 161 */       sql = sql + " and a.depotid=" + depotid;
/*     */     }
/* 163 */     sql = sql + " order by o.create_time desc";
/*     */ 
/* 166 */     IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
/*     */     {
/*     */       public void filter(Map colValues, ResultSet rs) throws SQLException
/*     */       {
/* 170 */         int catid = rs.getInt("cat_id");
/* 171 */         OrderReportManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);
/*     */       }
/*     */     };
/* 177 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, columnFilter, new Object[] { Integer.valueOf(status) });
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 183 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager)
/*     */   {
/* 188 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public OrderPluginBundle getOrderPluginBundle()
/*     */   {
/* 193 */     return this.orderPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle)
/*     */   {
/* 198 */     this.orderPluginBundle = orderPluginBundle;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 202 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 206 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderReportManager
 * JD-Core Version:    0.6.0
 */