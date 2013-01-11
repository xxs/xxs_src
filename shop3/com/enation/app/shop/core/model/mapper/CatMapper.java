/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class CatMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 22 */     Cat cat = new Cat();
/* 23 */     cat.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/* 24 */     cat.setCat_order(rs.getInt("cat_order"));
/* 25 */     cat.setCat_path(rs.getString("cat_path"));
/* 26 */     String image = rs.getString("image");
/* 27 */     if (image != null) {
/* 28 */       image = UploadUtil.replacePath(image);
/*    */     }
/* 30 */     cat.setImage(image);
/* 31 */     cat.setList_show(rs.getString("list_show"));
/* 32 */     cat.setName(rs.getString("name"));
/* 33 */     cat.setParent_id(Integer.valueOf(rs.getInt("parent_id")));
/* 34 */     cat.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 35 */     cat.setType_name(rs.getString("type_name"));
/* 36 */     return cat;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.CatMapper
 * JD-Core Version:    0.6.0
 */