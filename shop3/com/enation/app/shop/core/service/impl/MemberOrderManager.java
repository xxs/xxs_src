/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.service.IMemberOrderManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MemberOrderManager extends BaseSupport
/*     */   implements IMemberOrderManager
/*     */ {
/*     */   public Page pageOrders(int pageNo, int pageSize)
/*     */   {
/*  23 */     IUserService userService = UserServiceFactory.getUserService();
/*  24 */     Member member = userService.getCurrentMember();
/*     */ 
/*  26 */     String sql = "select * from order where member_id = ? and disabled=0 order by create_time desc";
/*  27 */     Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
/*  28 */     List list = (List)(List)rpage.getResult();
/*  29 */     return rpage;
/*     */   }
/*     */ 
/*     */   public Page pageOrders(int pageNo, int pageSize, String status, String keyword) {
/*  33 */     IUserService userService = UserServiceFactory.getUserService();
/*  34 */     Member member = userService.getCurrentMember();
/*     */ 
/*  36 */     String sql = "select * from " + getTableName("order") + " where member_id = '" + member.getMember_id() + "' and disabled=0";
/*  37 */     if (!StringUtil.isEmpty(status)) {
/*  38 */       int statusNumber = -999;
/*  39 */       statusNumber = StringUtil.toInt(status);
/*     */ 
/*  41 */       if (statusNumber == 0)
/*  42 */         sql = sql + " AND status!=8 AND pay_status=0";
/*     */       else {
/*  44 */         sql = sql + " AND status='" + statusNumber + "'";
/*     */       }
/*     */     }
/*  47 */     if (!StringUtil.isEmpty(keyword)) {
/*  48 */       sql = sql + " AND order_id in (SELECT i.order_id FROM " + getTableName("order_items") + " i LEFT JOIN order o ON i.order_id=o.order_id WHERE o.member_id='" + member.getMember_id() + "' AND i.name like '%" + keyword + "%')";
/*     */     }
/*  50 */     sql = sql + " order by create_time desc";
/*     */ 
/*  52 */     Page rpage = this.daoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
/*     */ 
/*  54 */     return rpage;
/*     */   }
/*     */ 
/*     */   public Page pageGoods(int pageNo, int pageSize, String keyword) {
/*  58 */     IUserService userService = UserServiceFactory.getUserService();
/*  59 */     Member member = userService.getCurrentMember();
/*     */ 
/*  61 */     String sql = "select * from goods where goods_id in (SELECT i.goods_id FROM es_order_items i LEFT JOIN order o ON i.order_id=o.order_id WHERE o.member_id='" + member.getMember_id() + "' AND o.status in (" + 7 + "," + 6 + " )) ";
/*     */ 
/*  63 */     if (!StringUtil.isEmpty(keyword)) {
/*  64 */       sql = sql + " AND (sn='" + keyword + "' OR name like '%" + keyword + "%')";
/*     */     }
/*  66 */     sql = sql + " order by goods_id desc";
/*  67 */     Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/*  68 */     List list = (List)(List)rpage.getResult();
/*  69 */     return rpage;
/*     */   }
/*     */ 
/*     */   public Order getOrder(int order_id)
/*     */   {
/*  74 */     Order order = (Order)this.baseDaoSupport.queryForObject("select * from order where order_id = ?", Order.class, new Object[] { Integer.valueOf(order_id) });
/*  75 */     return order;
/*     */   }
/*     */ 
/*     */   public Delivery getOrderDelivery(int order_id)
/*     */   {
/*  80 */     Delivery delivery = (Delivery)this.baseDaoSupport.queryForObject("select * from delivery where order_id = ?", Delivery.class, new Object[] { Integer.valueOf(order_id) });
/*  81 */     return delivery;
/*     */   }
/*     */ 
/*     */   public List listOrderLog(int order_id)
/*     */   {
/*  86 */     String sql = "select * from order_log where order_id = ?";
/*  87 */     List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(order_id) });
/*  88 */     list = list == null ? new ArrayList() : list;
/*  89 */     return list;
/*     */   }
/*     */ 
/*     */   public List listGoodsItems(int order_id)
/*     */   {
/*  94 */     String sql = "select * from order_items where order_id = ?";
/*  95 */     List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(order_id) });
/*  96 */     list = list == null ? new ArrayList() : list;
/*  97 */     return list;
/*     */   }
/*     */ 
/*     */   public List listGiftItems(int orderid)
/*     */   {
/* 102 */     String sql = "select * from order_gift where order_id=?";
/* 103 */     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(orderid) });
/*     */   }
/*     */ 
/*     */   public boolean isBuy(int goodsid)
/*     */   {
/* 108 */     IUserService userService = UserServiceFactory.getUserService();
/* 109 */     Member member = userService.getCurrentMember();
/* 110 */     if (member == null) return false;
/* 111 */     String sql = "select count(0) from " + getTableName("order_items") + " where  order_id in(select order_id from " + getTableName("order") + " where member_id=?) and goods_id =? ";
/*     */ 
/* 113 */     int count = this.daoSupport.queryForInt(sql, new Object[] { member.getMember_id(), Integer.valueOf(goodsid) });
/* 114 */     return count > 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberOrderManager
 * JD-Core Version:    0.6.0
 */