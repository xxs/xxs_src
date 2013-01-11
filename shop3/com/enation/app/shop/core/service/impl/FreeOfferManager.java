/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.FreeOffer;
/*     */ import com.enation.app.shop.core.model.mapper.GiftMapper;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FreeOfferManager extends BaseSupport<FreeOffer>
/*     */   implements IFreeOfferManager
/*     */ {
/*     */   public void delete(String bid)
/*     */   {
/*  29 */     if ((bid == null) || (bid.equals("")))
/*  30 */       return;
/*  31 */     String sql = "update freeoffer set disabled=1  where fo_id in (" + bid + ")";
/*     */ 
/*  33 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void revert(String bid)
/*     */   {
/*  38 */     if ((bid == null) || (bid.equals("")))
/*  39 */       return;
/*  40 */     String sql = "update freeoffer set disabled=0  where fo_id in (" + bid + ")";
/*     */ 
/*  42 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void clean(String bid)
/*     */   {
/*  47 */     if ((bid == null) || (bid.equals("")))
/*  48 */       return;
/*  49 */     String sql = "delete  from  freeoffer   where fo_id in (" + bid + ")";
/*  50 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public FreeOffer get(int fo_id)
/*     */   {
/*  55 */     String sql = "select * from freeoffer where fo_id=?";
/*  56 */     FreeOffer freeOffer = (FreeOffer)this.baseDaoSupport.queryForObject(sql, FreeOffer.class, new Object[] { Integer.valueOf(fo_id) });
/*  57 */     String pic = freeOffer.getPic();
/*  58 */     if (pic != null) {
/*  59 */       pic = UploadUtil.replacePath(pic);
/*     */     }
/*  61 */     freeOffer.setPic(pic);
/*  62 */     return freeOffer;
/*     */   }
/*     */ 
/*     */   public Page list(int page, int pageSize)
/*     */   {
/*  67 */     long now = System.currentTimeMillis();
/*  68 */     String sql = "select f.*,'' cat_name from freeoffer f where disabled=0 and publishable=0 and startdate<=" + now + " and enddate>=" + now + "  order by sorder asc ";
/*  69 */     return this.baseDaoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
/*     */   }
/*     */ 
/*     */   public void saveAdd(FreeOffer freeOffer)
/*     */   {
/*  74 */     this.baseDaoSupport.insert("freeoffer", freeOffer);
/*     */   }
/*     */ 
/*     */   public void update(FreeOffer freeOffer)
/*     */   {
/*  80 */     this.baseDaoSupport.update("freeoffer", freeOffer, "fo_id=" + freeOffer.getFo_id());
/*     */   }
/*     */ 
/*     */   private String getListSql()
/*     */   {
/*  85 */     String sql = "select fo.*,c.cat_name as cat_name from " + getTableName("freeoffer") + " fo left join " + getTableName("freeoffer_category") + " c on fo.fo_category_id=c.cat_id ";
/*     */ 
/*  89 */     return sql;
/*     */   }
/*     */ 
/*     */   public Page pageTrash(String name, String order, int page, int pageSize)
/*     */   {
/*  94 */     order = order == null ? " fo_id desc" : order;
/*  95 */     String sql = getListSql();
/*  96 */     name = " fo.disabled=1 and fo_name like '%" + name + "%'";
/*  97 */     sql = sql + " and " + name;
/*  98 */     sql = sql + " order by  " + order;
/*  99 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
/* 100 */     return webpage;
/*     */   }
/*     */ 
/*     */   public Page list(String name, String order, int page, int pageSize)
/*     */   {
/* 106 */     order = order == null ? " fo_id desc" : order;
/* 107 */     String sql = getListSql();
/* 108 */     name = " fodisabled=0 and fo_name like '%" + name + "%'";
/* 109 */     sql = sql + " and " + name;
/* 110 */     sql = sql + " order by  " + order;
/* 111 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new GiftMapper(), new Object[0]);
/* 112 */     return webpage;
/*     */   }
/*     */ 
/*     */   public List getOrderGift(int orderId)
/*     */   {
/* 117 */     String sql = "select * from order_gift where order_id = ?";
/* 118 */     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(orderId) });
/*     */   }
/*     */ 
/*     */   public List list(Integer[] ids)
/*     */   {
/* 126 */     if ((ids == null) || (ids.length == 0)) return new ArrayList();
/*     */ 
/* 128 */     return this.baseDaoSupport.queryForList("select * from freeoffer where fo_id in(" + StringUtil.arrayToString(ids, ",") + ") ", new GiftMapper(), new Object[0]);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.FreeOfferManager
 * JD-Core Version:    0.6.0
 */