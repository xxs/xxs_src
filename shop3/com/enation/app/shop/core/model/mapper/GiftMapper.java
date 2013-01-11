/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.FreeOffer;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class GiftMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 22 */     FreeOffer freeOffer = new FreeOffer();
/*    */ 
/* 24 */     freeOffer.setDescript(rs.getString("descript"));
/* 25 */     freeOffer.setDisabled(Integer.valueOf(rs.getInt("disabled")));
/* 26 */     freeOffer.setEnddate(Long.valueOf(rs.getLong("enddate")));
/* 27 */     freeOffer.setFo_category_id(Integer.valueOf(rs.getInt("fo_category_id")));
/* 28 */     freeOffer.setFo_id(Integer.valueOf(rs.getInt("fo_id")));
/* 29 */     freeOffer.setFo_name(rs.getString("fo_name"));
/* 30 */     freeOffer.setLimit_purchases(Integer.valueOf(rs.getInt("limit_purchases")));
/* 31 */     freeOffer.setList_thumb(rs.getString("list_thumb"));
/* 32 */     freeOffer.setLv_ids(rs.getString("lv_ids"));
/* 33 */     String pic = rs.getString("pic");
/* 34 */     if (pic != null) {
/* 35 */       pic = UploadUtil.replacePath(pic);
/*    */     }
/* 37 */     freeOffer.setPic(pic);
/* 38 */     freeOffer.setPrice(Double.valueOf(rs.getDouble("price")));
/* 39 */     freeOffer.setPublishable(Integer.valueOf(rs.getInt("publishable")));
/* 40 */     freeOffer.setRecommend(Integer.valueOf(rs.getInt("recommend")));
/* 41 */     freeOffer.setRepertory(Integer.valueOf(rs.getInt("repertory")));
/* 42 */     freeOffer.setScore(Integer.valueOf(rs.getInt("score")));
/* 43 */     freeOffer.setSorder(Integer.valueOf(rs.getInt("sorder")));
/* 44 */     freeOffer.setStartdate(Long.valueOf(rs.getLong("Startdate")));
/* 45 */     freeOffer.setSynopsis(rs.getString("synopsis"));
/* 46 */     freeOffer.setWeight(Double.valueOf(rs.getDouble("weight")));
/* 47 */     return freeOffer;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.GiftMapper
 * JD-Core Version:    0.6.0
 */