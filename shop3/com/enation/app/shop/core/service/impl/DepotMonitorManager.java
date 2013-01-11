/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.service.IDepotMonitorManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ public class DepotMonitorManager extends BaseSupport
/*     */   implements IDepotMonitorManager
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */ 
/*     */   public List getTaskList()
/*     */   {
/*  25 */     String sql = "select  count(0)  num, depotid,d.`name` from  " + getTableName("depot") + " d left join " + getTableName("goods_depot") + " gd on d.id = gd.depotid left join " + getTableName("goods") + " g on gd.goodsid=g.goods_id   where  gd.iscmpl=0 and g.disabled=0 group by depotid";
/*  26 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/*     */ 
/*  28 */     return list;
/*     */   }
/*     */ 
/*     */   public List getAllocationList()
/*     */   {
/*  33 */     String sql = "select count(0) num,ai.depotid,d.name  from  " + getTableName("depot") + " d  left join " + getTableName("allocation_item") + " ai on ai.depotid=d.id  where ai.iscmpl=0 group by depotid";
/*  34 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/*  35 */     return list;
/*     */   }
/*     */ 
/*     */   public List getSendList()
/*     */   {
/*  40 */     String sql = "select count(0) num,o.depotid,d.name  from  " + getTableName("depot") + " d  left join " + getTableName("order") + " o on o.depotid=d.id  where o.status=4 group by depotid";
/*  41 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/*  42 */     return list;
/*     */   }
/*     */ 
/*     */   public int getTotalByStatus(int status)
/*     */   {
/*  47 */     String sql = "select count(0) from " + getTableName("order") + " where status=?";
/*  48 */     return this.daoSupport.queryForInt(sql, new Object[] { Integer.valueOf(status) });
/*     */   }
/*     */ 
/*     */   public List depotidDepotByGoodsid(int goodsid, int catid) {
/*  52 */     String tablename = "product_store";
/*     */ 
/*  54 */     if (tablename == "") {
/*  55 */       return null;
/*     */     }
/*  57 */     String sql = "select d.name, d.name,sum(s.store) num from " + getTableName("depot") + " d left join " + getTableName(tablename) + " s on d.id=s.depotid where s.goodsid=" + goodsid + " group by depotid";
/*     */ 
/*  59 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/*  60 */     return list;
/*     */   }
/*     */ 
/*     */   public List searchOrderSalesAmout(int startDate, int endDate)
/*     */   {
/*  67 */     String sql = "";
/*  68 */     if (EopSetting.DBTYPE.equals("1"))
/*  69 */       sql = "select FROM_UNIXTIME(sale_cmpl_time,'%Y-%m-%d') as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
/*  70 */     else if (EopSetting.DBTYPE.equals("3"))
/*  71 */       sql = "select substring(convert(varchar(10),dateadd(ss,sale_cmpl_time + 28800,'1970-01-01'),120),1,10) as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
/*     */     else {
/*  73 */       sql = "select to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(sale_cmpl_time),'yyyy-mm-dd') as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
/*     */     }
/*  75 */     if ((startDate > 0) && (endDate > 0)) {
/*  76 */       sql = sql + " and sale_cmpl_time>=" + startDate;
/*  77 */       sql = sql + " and sale_cmpl_time<" + endDate;
/*     */     }
/*  79 */     sql = sql + " group by isdate";
/*     */ 
/*  81 */     RowMapper mapper = new RowMapper()
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/*  85 */         Date isdate = rs.getDate("isdate");
/*  86 */         Double amount = Double.valueOf(rs.getDouble("amount"));
/*  87 */         Map map = new HashMap();
/*  88 */         map.put("isdate", isdate);
/*  89 */         map.put("amount", amount);
/*  90 */         return map;
/*     */       }
/*     */     };
/*  93 */     List list = this.daoSupport.queryForList(sql, mapper, new Object[0]);
/*  94 */     return list;
/*     */   }
/*     */ 
/*     */   public List searchOrderSaleNumber(int startDate, int endDate, int catid) {
/*  98 */     String sql = "select * from (select g.goods_id, g.name,sum(oi.num) as total   from " + getTableName("order_items") + " oi left join " + getTableName("goods") + " g on oi.goods_id=g.goods_id left join " + getTableName("order") + " o on oi.order_id=o.order_id where o.sale_cmpl=1";
/*     */ 
/* 100 */     if ((startDate > 0) && (endDate > 0)) {
/* 101 */       sql = sql + " and o.sale_cmpl_time>=" + startDate;
/* 102 */       sql = sql + " and o.sale_cmpl_time<" + endDate;
/*     */     }
/* 104 */     if (catid > 0) {
/* 105 */       Cat cat = this.goodsCatManager.getById(catid);
/* 106 */       sql = sql + " and  g.cat_id in(";
/* 107 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
/*     */     }
/*     */ 
/* 110 */     sql = sql + " group by oi.goods_id) t order by t.total desc";
/*     */ 
/* 113 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/* 114 */     return list;
/*     */   }
/*     */ 
/*     */   public Page searchStoreLog(int startDate, int endDate, int depotid, int depotType, int opType, int page, int pageSize) {
/* 118 */     String sql = "select g.name,d.name as depotname,sl.*  from " + getTableName("store_log") + " sl left join " + getTableName("goods") + " g on sl.goodsid=g.goods_id left join " + getTableName("depot") + " d on sl.depotid=d.id where 1=1 ";
/* 119 */     if ((startDate > 0) && (endDate > 0)) {
/* 120 */       sql = sql + " and sl.dateline>=" + startDate;
/* 121 */       sql = sql + " and sl.dateline<" + endDate;
/*     */     }
/* 123 */     if (depotid > 0) {
/* 124 */       sql = sql + " and sl.depotid=" + depotid;
/*     */     }
/* 126 */     if (depotType != -1) {
/* 127 */       sql = sql + " and sl.depot_type=" + depotType;
/*     */     }
/* 129 */     if (opType != -1) {
/* 130 */       sql = sql + " and sl.op_type=" + opType;
/*     */     }
/* 132 */     sql = sql + " order by dateline desc";
/* 133 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 134 */     return webpage;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 139 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 143 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.DepotMonitorManager
 * JD-Core Version:    0.6.0
 */