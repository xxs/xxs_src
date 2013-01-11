/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class FriendsLinkMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int arg1)
/*    */     throws SQLException
/*    */   {
/* 19 */     FriendsLink friendsLink = new FriendsLink();
/* 20 */     friendsLink.setLink_id(Integer.valueOf(rs.getInt("link_id")));
/* 21 */     String logo = rs.getString("logo");
/* 22 */     if (logo != null) logo = UploadUtil.replacePath(logo);
/* 23 */     friendsLink.setLogo(logo);
/* 24 */     friendsLink.setName(rs.getString("name"));
/* 25 */     friendsLink.setSort(Integer.valueOf(rs.getInt("sort")));
/* 26 */     friendsLink.setUrl(rs.getString("url"));
/* 27 */     return friendsLink;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.FriendsLinkMapper
 * JD-Core Version:    0.6.0
 */