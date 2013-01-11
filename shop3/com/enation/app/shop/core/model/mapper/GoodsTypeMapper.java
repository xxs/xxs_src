/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.GoodsType;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class GoodsTypeMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 14 */     GoodsType goodsType = new GoodsType();
/* 15 */     goodsType.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 16 */     goodsType.setName(rs.getString("name"));
/* 17 */     goodsType.setHave_parm(rs.getInt("have_parm"));
/* 18 */     goodsType.setHave_prop(rs.getInt("have_prop"));
/* 19 */     goodsType.setIs_physical(rs.getInt("is_physical"));
/* 20 */     goodsType.setJoin_brand(rs.getInt("join_brand"));
/* 21 */     goodsType.setProps(rs.getString("props"));
/* 22 */     goodsType.setParams(rs.getString("params"));
/*    */ 
/* 24 */     return goodsType;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GoodsTypeMapper
 * JD-Core Version:    0.6.0
 */