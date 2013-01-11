/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.SpecValue;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class SpecValueMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 21 */     SpecValue specValue = new SpecValue();
/* 22 */     specValue.setSpec_id(Integer.valueOf(rs.getInt("spec_id")));
/* 23 */     String spec_img = rs.getString("spec_image");
/* 24 */     if (spec_img != null) {
/* 25 */       spec_img = UploadUtil.replacePath(spec_img);
/*    */     }
/* 27 */     specValue.setSpec_image(spec_img);
/* 28 */     specValue.setSpec_order(Integer.valueOf(rs.getInt("spec_order")));
/* 29 */     specValue.setSpec_type(Integer.valueOf(rs.getInt("spec_type")));
/* 30 */     specValue.setSpec_value(rs.getString("spec_value"));
/* 31 */     specValue.setSpec_value_id(Integer.valueOf(rs.getInt("spec_value_id")));
/* 32 */     return specValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.SpecValueMapper
 * JD-Core Version:    0.6.0
 */