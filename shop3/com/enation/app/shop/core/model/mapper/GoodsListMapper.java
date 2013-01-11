/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.support.GoodsView;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class GoodsListMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 26 */     GoodsView goods = new GoodsView();
/* 27 */     goods.setName(rs.getString("name"));
/* 28 */     goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/* 29 */     goods.setImage_default(rs.getString("image_default"));
/* 30 */     goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
/* 31 */     goods.setPrice(Double.valueOf(rs.getDouble("price")));
/* 32 */     goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
/* 33 */     goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/* 34 */     goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 35 */     goods.setPoint(Integer.valueOf(rs.getInt("point")));
/* 36 */     goods.setStore(Integer.valueOf(rs.getInt("store")));
/* 37 */     goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*    */ 
/* 39 */     goods.setSn(rs.getString("sn"));
/* 40 */     goods.setIntro(rs.getString("intro"));
/* 41 */     goods.setStore(Integer.valueOf(rs.getInt("store")));
/* 42 */     goods.setImage_file(UploadUtil.replacePath(rs.getString("image_file")));
/*    */ 
/* 44 */     Map propMap = new HashMap();
/*    */ 
/* 46 */     for (int i = 0; i < 20; i++) {
/* 47 */       String value = rs.getString("p" + (i + 1));
/* 48 */       propMap.put("p" + (i + 1), value);
/*    */     }
/* 50 */     goods.setPropMap(propMap);
/*    */ 
/* 52 */     return goods;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GoodsListMapper
 * JD-Core Version:    0.6.0
 */