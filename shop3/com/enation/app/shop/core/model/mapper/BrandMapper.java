/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Brand;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class BrandMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 21 */     Brand brand = new Brand();
/* 22 */     brand.setBrand_id(Integer.valueOf(rs.getInt("brand_id")));
/* 23 */     brand.setBrief(rs.getString("brief"));
/* 24 */     String logo = rs.getString("logo");
/* 25 */     if (logo != null) {
/* 26 */       logo = UploadUtil.replacePath(logo);
/*    */     }
/* 28 */     brand.setLogo(logo);
/* 29 */     brand.setName(rs.getString("name"));
/* 30 */     brand.setUrl(rs.getString("url"));
/* 31 */     return brand;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.BrandMapper
 * JD-Core Version:    0.6.0
 */