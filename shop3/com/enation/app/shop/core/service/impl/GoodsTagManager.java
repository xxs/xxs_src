/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.model.Goods;
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.app.shop.core.service.IGoodsTagManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ 
/*    */ public class GoodsTagManager extends BaseSupport<Goods>
/*    */   implements IGoodsTagManager
/*    */ {
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   public Page getGoodsList(int tagid, int page, int pageSize)
/*    */   {
/* 17 */     StringBuffer sql = new StringBuffer();
/* 18 */     sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
/* 19 */     sql.append(" and r.tag_id = ?");
/* 20 */     sql.append(" order by r.ordernum desc");
/* 21 */     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(tagid) });
/*    */ 
/* 23 */     return webpage;
/*    */   }
/*    */ 
/*    */   public Page getGoodsList(int tagid, int catid, int page, int pageSize)
/*    */   {
/* 28 */     Cat cat = this.goodsCatManager.getById(Integer.valueOf(catid).intValue());
/* 29 */     if (cat == null) {
/* 30 */       return null;
/*    */     }
/* 32 */     StringBuffer sql = new StringBuffer();
/* 33 */     sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
/* 34 */     sql.append(" and r.tag_id = ?");
/*    */ 
/* 36 */     sql.append(" and  g.cat_id in(");
/* 37 */     sql.append("select c.cat_id from " + getTableName("goods_cat") + " ");
/* 38 */     sql.append(" c where c.cat_path like '" + cat.getCat_path() + "%')");
/*    */ 
/* 40 */     sql.append(" order by r.ordernum desc");
/* 41 */     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(tagid) });
/*    */ 
/* 43 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void addTag(int tagId, int goodsId)
/*    */   {
/* 48 */     if (this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM tag_rel WHERE tag_id=? AND rel_id=?", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) }) <= 0)
/*    */     {
/* 51 */       this.baseDaoSupport.execute("INSERT INTO tag_rel(tag_id,rel_id,ordernum) VALUES(?,?,0)", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) });
/*    */ 
/* 55 */       if (tagId == 4)
/* 56 */         this.baseDaoSupport.execute("update goods set istejia = 1 where goods_id=?", new Object[] { Integer.valueOf(goodsId) });
/*    */     }
/*    */   }
/*    */ 
/*    */   public void removeTag(int tagId, int goodsId)
/*    */   {
/* 63 */     this.baseDaoSupport.execute("DELETE FROM tag_rel WHERE tag_id=? AND rel_id=?", new Object[] { Integer.valueOf(tagId), Integer.valueOf(goodsId) });
/*    */ 
/* 66 */     this.baseDaoSupport.execute("update goods set istejia = ? where goods_id = ?", new Object[] { Integer.valueOf(0), Integer.valueOf(goodsId) });
/*    */   }
/*    */ 
/*    */   public void updateOrderNum(Integer[] goodsIds, Integer[] tagids, Integer[] ordernum)
/*    */   {
/* 74 */     if ((goodsIds != null) && (goodsIds.length > 0))
/* 75 */       for (int i = 0; i < goodsIds.length; i++) {
/* 76 */         if ((goodsIds[i] == null) || (tagids[i] == null) || (ordernum[i] == null))
/*    */           continue;
/*    */         try {
/* 79 */           this.baseDaoSupport.execute("UPDATE tag_rel set ordernum=? WHERE tag_id=? AND rel_id=?", new Object[] { ordernum[i], tagids[i], goodsIds[i] });
/*    */         }
/*    */         catch (Exception ex)
/*    */         {
/*    */         }
/*    */       }
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
/*    */   {
/* 91 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsTagManager
 * JD-Core Version:    0.6.0
 */