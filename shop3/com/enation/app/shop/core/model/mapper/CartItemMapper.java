/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.support.CartItem;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class CartItemMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 18 */     CartItem cartItem = new CartItem();
/* 19 */     cartItem.setId(Integer.valueOf(rs.getInt("cart_id")));
/* 20 */     cartItem.setProduct_id(Integer.valueOf(rs.getInt("product_id")));
/* 21 */     cartItem.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/* 22 */     cartItem.setName(rs.getString("name"));
/* 23 */     cartItem.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
/* 24 */     cartItem.setPrice(Double.valueOf(rs.getDouble("price")));
/* 25 */     cartItem.setCoupPrice(Double.valueOf(rs.getDouble("price")));
/* 26 */     cartItem.setCatid(rs.getInt("catid"));
/*    */ 
/* 28 */     cartItem.setIstejia(Integer.valueOf(rs.getInt("istejia")));
/* 29 */     cartItem.setNo_discount(Integer.valueOf(rs.getInt("no_discount")));
/*    */ 
/* 31 */     String image_default = rs.getString("image_default");
/*    */ 
/* 33 */     if (image_default != null) {
/* 34 */       image_default = UploadUtil.replacePath(image_default);
/*    */     }
/* 36 */     cartItem.setImage_default(image_default);
/* 37 */     cartItem.setNum(rs.getInt("num"));
/* 38 */     cartItem.setPoint(Integer.valueOf(rs.getInt("point")));
/* 39 */     cartItem.setItemtype(Integer.valueOf(rs.getInt("itemtype")));
/*    */ 
/* 41 */     cartItem.setAddon(rs.getString("addon"));
/*    */ 
/* 44 */     if (cartItem.getItemtype().intValue() == 2) {
/* 45 */       cartItem.setLimitnum(Integer.valueOf(rs.getInt("limitnum")));
/*    */     }
/*    */ 
/* 48 */     cartItem.setSn(rs.getString("sn"));
/*    */ 
/* 50 */     if (cartItem.getItemtype().intValue() == 0) {
/* 51 */       String specs = rs.getString("specs");
/* 52 */       cartItem.setSpecs(specs);
/*    */     }
/*    */ 
/* 59 */     cartItem.setUnit(rs.getString("unit"));
/* 60 */     return cartItem;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.CartItemMapper
 * JD-Core Version:    0.6.0
 */