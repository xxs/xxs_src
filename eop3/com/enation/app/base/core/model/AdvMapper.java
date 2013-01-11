/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class AdvMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 20 */     Adv adv = new Adv();
/* 21 */     adv.setAcid(Integer.valueOf(rs.getInt("acid")));
/* 22 */     adv.setAid(Integer.valueOf(rs.getInt("aid")));
/* 23 */     adv.setAname(rs.getString("aname"));
/* 24 */     String atturl = rs.getString("atturl");
/* 25 */     if (atturl != null) atturl = UploadUtil.replacePath(atturl);
/* 26 */     adv.setAtturl(atturl);
/* 27 */     adv.setAtype(Integer.valueOf(rs.getInt("atype")));
/* 28 */     adv.setBegintime(Long.valueOf(rs.getLong("begintime")));
/* 29 */     adv.setClickcount(Integer.valueOf(rs.getInt("clickcount")));
/* 30 */     adv.setCompany(rs.getString("company"));
/* 31 */     adv.setContact(rs.getString("contact"));
/* 32 */     adv.setDisabled(rs.getString("disabled"));
/* 33 */     adv.setEndtime(Long.valueOf(rs.getLong("endtime")));
/* 34 */     adv.setIsclose(Integer.valueOf(rs.getInt("isclose")));
/* 35 */     adv.setLinkman(rs.getString("linkman"));
/* 36 */     adv.setUrl(rs.getString("url"));
/*    */ 
/* 38 */     adv.setCname(rs.getString("cname"));
/*    */ 
/* 40 */     return adv;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.AdvMapper
 * JD-Core Version:    0.6.0
 */