/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*    */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MemberPriceManager extends BaseSupport<GoodsLvPrice>
/*    */   implements IMemberPriceManager
/*    */ {
/*    */   public List<GoodsLvPrice> listPriceByGid(int goodsid)
/*    */   {
/* 24 */     String sql = "select * from goods_lv_price where goodsid =?";
/* 25 */     return this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, new Object[] { Integer.valueOf(goodsid) });
/*    */   }
/*    */ 
/*    */   public List<GoodsLvPrice> listPriceByLvid(int lvid)
/*    */   {
/* 34 */     String sql = "select * from goods_lv_price where lvid =?";
/* 35 */     return this.baseDaoSupport.queryForList(sql, GoodsLvPrice.class, new Object[] { Integer.valueOf(lvid) });
/*    */   }
/*    */ 
/*    */   public void save(List<GoodsLvPrice> goodsPriceList)
/*    */   {
/* 44 */     if ((goodsPriceList != null) && (goodsPriceList.size() > 0)) {
/* 45 */       this.baseDaoSupport.execute("delete from  goods_lv_price  where goodsid=?", new Object[] { Integer.valueOf(((GoodsLvPrice)goodsPriceList.get(0)).getGoodsid()) });
/*    */ 
/* 47 */       for (GoodsLvPrice goodsPrice : goodsPriceList)
/* 48 */         this.baseDaoSupport.insert("goods_lv_price", goodsPrice);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberPriceManager
 * JD-Core Version:    0.6.0
 */