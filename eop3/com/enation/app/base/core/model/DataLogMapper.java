/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.model.Image;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class DataLogMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 23 */     DataLog datalog = new DataLog();
/* 24 */     datalog.setContent(rs.getString("content"));
/* 25 */     datalog.setDateline(Long.valueOf(rs.getLong("dateline")));
/* 26 */     datalog.setDomain(rs.getString("domain"));
/* 27 */     datalog.setId(Integer.valueOf(rs.getInt("id")));
/* 28 */     datalog.setLogtype(rs.getString("logtype"));
/* 29 */     datalog.setOptype(rs.getString("optype"));
/* 30 */     String pics = rs.getString("pics");
/* 31 */     datalog.setPics(pics);
/* 32 */     datalog.setSitename(rs.getString("sitename"));
/* 33 */     datalog.setSiteid(Integer.valueOf(rs.getInt("siteid")));
/* 34 */     datalog.setUserid(Integer.valueOf(rs.getInt("userid")));
/* 35 */     datalog.setUrl(rs.getString("url"));
/*    */ 
/* 38 */     if ((pics != null) && (!"".equals(pics))) {
/* 39 */       List imgList = new ArrayList();
/* 40 */       String[] picar = StringUtils.split(pics, ",");
/*    */ 
/* 42 */       for (String picstr : picar) {
/* 43 */         String[] pic = picstr.split("\\|");
/*    */ 
/* 45 */         String thumbpic = pic[0];
/* 46 */         String originalpic = pic[1];
/*    */ 
/* 48 */         if (thumbpic != null) {
/* 49 */           thumbpic = thumbpic.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + datalog.getUserid() + "/" + datalog.getSiteid());
/*    */         }
/*    */ 
/* 52 */         if (originalpic != null) {
/* 53 */           originalpic = originalpic.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + datalog.getUserid() + "/" + datalog.getSiteid());
/*    */         }
/* 55 */         Image image = new Image();
/* 56 */         image.setOriginal(originalpic);
/* 57 */         image.setThumb(thumbpic);
/* 58 */         imgList.add(image);
/*    */       }
/* 60 */       datalog.setPicList(imgList);
/*    */     }
/*    */ 
/* 63 */     return datalog;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.DataLogMapper
 * JD-Core Version:    0.6.0
 */