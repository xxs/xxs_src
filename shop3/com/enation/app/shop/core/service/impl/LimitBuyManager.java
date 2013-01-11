/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.LimitBuy;
/*     */ import com.enation.app.shop.core.model.LimitBuyGoods;
/*     */ import com.enation.app.shop.core.service.ILimitBuyManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class LimitBuyManager extends BaseSupport
/*     */   implements ILimitBuyManager
/*     */ {
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(LimitBuy limitBuy)
/*     */   {
/*  21 */     List limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();
/*     */ 
/*  23 */     limitBuy.setAdd_time(DateUtil.getDatelineLong());
/*  24 */     this.baseDaoSupport.insert("limitbuy", limitBuy);
/*  25 */     Integer limitBuyId = Integer.valueOf(this.baseDaoSupport.getLastId("limitbuy"));
/*  26 */     addGoods(limitBuyGoodsList, limitBuyId);
/*     */   }
/*     */ 
/*     */   private void addGoods(List<LimitBuyGoods> limitBuyGoodsList, Integer limitBuyId)
/*     */   {
/*  31 */     if ((limitBuyGoodsList == null) || (limitBuyGoodsList.isEmpty())) throw new RuntimeException("添加限时购买的商品列表不能为空");
/*  32 */     for (LimitBuyGoods limitBuyGoods : limitBuyGoodsList) {
/*  33 */       this.baseDaoSupport.execute("insert into limitbuy_goods (limitbuyid,goodsid,price)values(?,?,?)", new Object[] { limitBuyId, limitBuyGoods.getGoodsid(), limitBuyGoods.getPrice() });
/*     */ 
/*  35 */       updateGoodsLimit(limitBuyId, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer id) {
/*  41 */     updateGoodsLimit(id, 0);
/*  42 */     this.baseDaoSupport.execute("delete from limitbuy_goods where limitbuyid=?", new Object[] { id });
/*  43 */     this.baseDaoSupport.execute("delete from limitbuy where id=?", new Object[] { id });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(LimitBuy limitBuy)
/*     */   {
/*  50 */     List limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();
/*  51 */     this.baseDaoSupport.update("limitbuy", limitBuy, "id=" + limitBuy.getId());
/*  52 */     Integer limitBuyId = limitBuy.getId();
/*  53 */     this.baseDaoSupport.execute("delete from limitbuy_goods where limitbuyid=?", new Object[] { limitBuyId });
/*     */ 
/*  55 */     updateGoodsLimit(limitBuyId, 0);
/*  56 */     addGoods(limitBuyGoodsList, limitBuyId);
/*     */   }
/*     */ 
/*     */   private void updateGoodsLimit(Integer limitid, int islimit)
/*     */   {
/*  66 */     this.daoSupport.execute("update " + getTableName("goods") + " set islimit=? where goods_id in(select goodsid from " + getTableName("limitbuy_goods") + " where limitbuyid=?)", new Object[] { Integer.valueOf(islimit), limitid });
/*     */   }
/*     */ 
/*     */   public Page list(int pageNo, int pageSize)
/*     */   {
/*  71 */     String sql = "select * from limitbuy order by add_time desc";
/*  72 */     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, LimitBuy.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<LimitBuy> listEnable()
/*     */   {
/*  77 */     long now = DateUtil.getDatelineLong();
/*     */ 
/*  79 */     String sql = "select * from limitbuy where start_time<? and end_time>? order by add_time desc";
/*  80 */     List limitBuyBuyList = this.baseDaoSupport.queryForList(sql, LimitBuy.class, new Object[] { Long.valueOf(now), Long.valueOf(now) });
/*     */ 
/*  84 */     sql = "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice  from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid in";
/*  85 */     sql = sql + "(select id from " + getTableName("limitbuy") + " where start_time<? and end_time>?)";
/*  86 */     List goodsList = this.daoSupport.queryForList(sql, new Object[] { Long.valueOf(now), Long.valueOf(now) });
/*  87 */     for (LimitBuy limitBuy : limitBuyBuyList) {
/*  88 */       List list = findGoods(goodsList, limitBuy.getId());
/*  89 */       limitBuy.setGoodsList(list);
/*     */     }
/*  91 */     return limitBuyBuyList;
/*     */   }
/*     */   public List<Map> listEnableGoods() {
/*  94 */     long now = DateUtil.getDatelineLong();
/*     */ 
/*  96 */     String sql = "select g.* ,lg.limitbuyid limitbuyid ,lg.price limitprice  from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid in";
/*  97 */     sql = sql + "(select id from " + getTableName("limitbuy") + " where start_time<? and end_time>?)";
/*  98 */     List goodsList = this.daoSupport.queryForList(sql, new Object[] { Long.valueOf(now), Long.valueOf(now) });
/*  99 */     return goodsList;
/*     */   }
/*     */   private List<Map> findGoods(List<Map> goodsList, Integer limitbuyid) {
/* 102 */     List list = new ArrayList();
/* 103 */     for (Map goods : goodsList) {
/* 104 */       if (limitbuyid.compareTo((Integer)goods.get("limitbuyid")) == 0) {
/* 105 */         list.add(goods);
/*     */       }
/*     */     }
/*     */ 
/* 109 */     return list;
/*     */   }
/*     */ 
/*     */   public LimitBuy get(Integer id) {
/* 113 */     String sql = "select * from limitbuy where  id=?";
/* 114 */     LimitBuy limitBuy = (LimitBuy)this.baseDaoSupport.queryForObject(sql, LimitBuy.class, new Object[] { id });
/* 115 */     sql = "select g.* ,lg.limitbuyid limitbuyid from " + getTableName("limitbuy_goods") + " lg  ," + getTableName("goods") + " g where lg.goodsid= g.goods_id and lg.limitbuyid =?";
/* 116 */     List goodsList = this.daoSupport.queryForList(sql, new Object[] { id });
/* 117 */     limitBuy.setGoodsList(goodsList);
/* 118 */     return limitBuy;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.LimitBuyManager
 * JD-Core Version:    0.6.0
 */