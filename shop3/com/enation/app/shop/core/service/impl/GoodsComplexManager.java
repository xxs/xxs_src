/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.GoodsComplex;
/*    */ import com.enation.app.shop.core.service.IGoodsComplexManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ public class GoodsComplexManager extends BaseSupport
/*    */   implements IGoodsComplexManager
/*    */ {
/*    */   public List listAllComplex(int goods_id)
/*    */   {
/* 22 */     String sql = "select   g.goods_id, g.sn, g.name, g.image_default image, g.price, g.mktprice from " + getTableName("goods") + " g where g.goods_id in (select goods_2 from " + getTableName("goods_complex") + " where goods_1=" + goods_id + " )" + " or goods_id in( select goods_1 from " + getTableName("goods_complex") + " where goods_2=" + goods_id + " and manual='both'  )";
/*    */ 
/* 25 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/* 26 */     for (Map map : list) {
/* 27 */       String image = (String)map.get("image");
/* 28 */       image = UploadUtil.replacePath(image);
/* 29 */       map.put("image", image);
/*    */     }
/* 31 */     return list;
/*    */   }
/*    */ 
/*    */   public void addCoodsComplex(GoodsComplex complex)
/*    */   {
/* 36 */     this.baseDaoSupport.insert("goods_complex", complex);
/*    */   }
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void globalGoodsComplex(int goods_1, List<GoodsComplex> list)
/*    */   {
/* 43 */     String deleteSql = "delete from goods_complex where goods_1 = ?";
/* 44 */     this.baseDaoSupport.execute(deleteSql, new Object[] { Integer.valueOf(goods_1) });
/*    */ 
/* 47 */     for (GoodsComplex complex : list)
/* 48 */       addCoodsComplex(complex);
/*    */   }
/*    */ 
/*    */   public List listComplex(int goods_id)
/*    */   {
/* 56 */     String sql = "select * from " + getTableName("goods") + " g, " + getTableName("goods_complex") + " c where goods_2 =goods_id and  goods_1=?";
/* 57 */     List list = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(goods_id) });
/* 58 */     for (Map map : list) {
/* 59 */       String image = (String)map.get("image");
/* 60 */       image = UploadUtil.replacePath(image);
/* 61 */       map.put("image", image);
/*    */     }
/* 63 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsComplexManager
 * JD-Core Version:    0.6.0
 */