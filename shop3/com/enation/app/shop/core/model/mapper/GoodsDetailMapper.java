/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.support.GoodsView;
/*    */ import com.enation.app.shop.core.utils.GoodsUtils;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class GoodsDetailMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 21 */     GoodsView goods = new GoodsView();
/* 22 */     goods.setName(rs.getString("name"));
/* 23 */     goods.setSn(rs.getString("sn"));
/* 24 */     goods.setUnit(rs.getString("unit"));
/* 25 */     goods.setWeight(Double.valueOf(rs.getDouble("weight")));
/* 26 */     goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/* 27 */     goods.setImage_default(rs.getString("image_default"));
/* 28 */     goods.setImage_file(rs.getString("image_file"));
/* 29 */     goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
/* 30 */     goods.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/* 31 */     goods.setPrice(Double.valueOf(rs.getDouble("price")));
/* 32 */     goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
/* 33 */     goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/* 34 */     goods.setBrand_name(rs.getString("brand_name"));
/* 35 */     goods.setParams(rs.getString("params"));
/* 36 */     goods.setIntro(rs.getString("intro"));
/* 37 */     goods.setBrief(rs.getString("brief"));
/* 38 */     goods.setPage_title(rs.getString("page_title"));
/* 39 */     goods.setMeta_keywords(rs.getString("meta_keywords"));
/* 40 */     goods.setMeta_description(rs.getString("meta_description"));
/* 41 */     goods.setSpecs(rs.getString("specs"));
/* 42 */     List specList = GoodsUtils.getSpecList(goods.getSpecs());
/* 43 */     goods.setSpecList(specList);
/* 44 */     goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 45 */     goods.setBrand_id(Integer.valueOf(rs.getInt("brand_id")));
/* 46 */     goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/* 47 */     goods.setAdjuncts(rs.getString("adjuncts"));
/* 48 */     if ((goods.getAdjuncts() != null) && (goods.getAdjuncts().equals(""))) {
/* 49 */       goods.setAdjuncts(null);
/*    */     }
/* 51 */     goods.setStore(Integer.valueOf(rs.getInt("store")));
/* 52 */     goods.setDisabled(Integer.valueOf(rs.getInt("disabled")));
/* 53 */     goods.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*    */ 
/* 56 */     Map propMap = new HashMap();
/*    */ 
/* 58 */     for (int i = 0; i < 20; i++) {
/* 59 */       String value = rs.getString("p" + (i + 1));
/* 60 */       propMap.put("p" + i, value);
/*    */     }
/* 62 */     goods.setPropMap(propMap);
/* 63 */     return goods;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GoodsDetailMapper
 * JD-Core Version:    0.6.0
 */