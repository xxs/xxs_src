/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopProduct;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class ProductMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 19 */     EopProduct product = new EopProduct();
/* 20 */     product.setId(Integer.valueOf(rs.getInt("id")));
/* 21 */     product.setProductid(rs.getString("productid"));
/* 22 */     product.setProduct_name(rs.getString("product_name"));
/* 23 */     product.setAuthor(rs.getString("author"));
/* 24 */     product.setCatid(Integer.valueOf(rs.getInt("catid")));
/* 25 */     product.setColorid(Integer.valueOf(rs.getInt("colorid")));
/* 26 */     product.setCreatetime(Long.valueOf(rs.getLong("createtime")));
/* 27 */     product.setDescript(rs.getString("descript"));
/* 28 */     product.setTypeid(Integer.valueOf(rs.getInt("typeid")));
/* 29 */     product.setPstate(Integer.valueOf(rs.getInt("pstate")));
/* 30 */     String preview = rs.getString("preview");
/*    */ 
/* 35 */     preview = UploadUtil.replacePath(preview);
/* 36 */     product.setPreview(preview);
/* 37 */     product.setVersion(rs.getString("version"));
/* 38 */     product.setSort(Integer.valueOf(rs.getInt("sort")));
/*    */ 
/* 40 */     return product;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.ProductMapper
 * JD-Core Version:    0.6.0
 */