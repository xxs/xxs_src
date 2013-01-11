/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IRankManager;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RankManager extends BaseSupport
/*    */   implements IRankManager
/*    */ {
/*    */   public List rank_goods(int page, int pageSize, String condition, String sort)
/*    */   {
/* 15 */     String sql = "select items.name name, sum(items.price * items.num) amount, sum(items.num) num from " + getTableName("order") + " orders , " + getTableName("order_items") + " items where items.order_id = orders.order_id";
/*    */ 
/* 19 */     if ((condition == null) || (condition.equals("")))
/* 20 */       condition = " and orders.status = 3";
/*    */     else {
/* 22 */       condition = condition + " and orders.status = 3";
/*    */     }
/* 24 */     sql = sql + condition;
/* 25 */     sql = sql + " group by name,num";
/* 26 */     sort = sort == null ? "num desc" : sort;
/* 27 */     sql = sql + " order by " + sort;
/*    */ 
/* 29 */     List list = this.daoSupport.queryForListPage(sql, page, pageSize, new Object[0]);
/* 30 */     return list;
/*    */   }
/*    */ 
/*    */   public List rank_member(int page, int pageSize, String condition, String sort)
/*    */   {
/* 36 */     List list = null;
/* 37 */     String sql = "select member.uname uname, member.name name, sum(items.price * items.num) amount, sum(items.num) num from " + getTableName("order") + " orders , " + getTableName("order_items") + " items, " + getTableName("member") + " member where items.order_id = orders.order_id and member.member_id = orders.member_id";
/* 38 */     if ((condition == null) || (condition.equals("")))
/* 39 */       condition = " and orders.status = 3";
/*    */     else {
/* 41 */       condition = condition + " and orders.status = 3";
/*    */     }
/* 43 */     sql = sql + condition;
/* 44 */     sql = sql + " group by member.uname, member.name";
/* 45 */     sort = sort == null ? "sum(items.num) desc" : sort;
/* 46 */     sql = sql + " order by " + sort;
/* 47 */     if ("3".equals(EopSetting.DBTYPE))
/* 48 */       list = this.daoSupport.queryForList("select * from (select top " + pageSize + " ROW_NUMBER() Over(order by sum(items.num) desc) as rowNum," + sql.substring(6) + ") tb", new Object[0]);
/*    */     else {
/* 50 */       list = this.daoSupport.queryForListPage(sql, page, pageSize, new Object[0]);
/*    */     }
/* 52 */     return list;
/*    */   }
/*    */ 
/*    */   public List rank_buy(int page, int pageSize, String sort)
/*    */   {
/* 57 */     String mysql = "select name, view_count, buy_count, FORMAT(buy_count / (case view_count when null then 1 when 0 then 1 else view_count end)*100, 2) per from goods where disabled = 0 and buy_count > 0";
/* 58 */     String sqlserver = "select name, view_count, buy_count, Round(buy_count / (case view_count when null then 1 when 0 then 1 else view_count end)*100, 2) per from goods where disabled = 0 and buy_count > 0";
/* 59 */     String groupby = " group by name,view_count,buy_count";
/* 60 */     mysql = mysql + groupby;
/* 61 */     sqlserver = sqlserver + groupby;
/* 62 */     sort = sort == null ? "view_count desc" : sort;
/* 63 */     sort = " order by " + sort;
/* 64 */     mysql = mysql + sort;
/* 65 */     sqlserver = sqlserver + sort;
/* 66 */     List list = null;
/* 67 */     if ("1".equals(EopSetting.DBTYPE))
/* 68 */       list = this.baseDaoSupport.queryForListPage(mysql, page, pageSize, new Object[0]);
/* 69 */     else if ("3".equals(EopSetting.DBTYPE))
/* 70 */       list = this.baseDaoSupport.queryForListPage(sqlserver, page, pageSize, new Object[0]);
/* 71 */     return list;
/*    */   }
/*    */ 
/*    */   public Map rank_all()
/*    */   {
/* 76 */     Map map = this.baseDaoSupport.queryForMap("select sum(order_amount) amount, count(order_id) num from order where status = 3", new Object[0]);
/* 77 */     int view_count = this.baseDaoSupport.queryForInt("select sum(view_count) view_count from goods where disabled = 0", new Object[0]);
/* 78 */     map.put("view_count", Integer.valueOf(view_count));
/* 79 */     int member_count = this.baseDaoSupport.queryForInt("select count(member_id) member_count from member", new Object[0]);
/* 80 */     map.put("member_count", Integer.valueOf(member_count));
/* 81 */     int buy_member_count = this.baseDaoSupport.queryForInt("select count(distinct member_id) buy_member_count from order where status = 3 and member_id > 0", new Object[0]);
/* 82 */     map.put("buy_member_count", Integer.valueOf(buy_member_count));
/* 83 */     return map;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.RankManager
 * JD-Core Version:    0.6.0
 */