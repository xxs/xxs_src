/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IWelcomeInfoManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.DoubleMapper;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WelcomeInfoManager extends BaseSupport
/*    */   implements IWelcomeInfoManager
/*    */ {
/*    */   public Map mapWelcomInfo()
/*    */   {
/* 21 */     IUserService userService = UserServiceFactory.getUserService();
/* 22 */     Member member = userService.getCurrentMember();
/* 23 */     Map map = new HashMap();
/* 24 */     int oNum = this.baseDaoSupport.queryForInt("SELECT count(*) as oNum  FROM order where pay_status=0 and member_id=? and status != 4", new Object[] { member.getMember_id() });
/* 25 */     map.put("oNum", Integer.valueOf(oNum));
/* 26 */     int totalOrder = this.baseDaoSupport.queryForInt("SELECT count(*) as totalOrder  FROM order where member_id=? and disabled=0 ", new Object[] { member.getMember_id() });
/* 27 */     map.put("totalOrder", Integer.valueOf(totalOrder));
/* 28 */     int mNum = this.baseDaoSupport.queryForInt("SELECT count(*) as mNum FROM message where to_id=? and unread='0' and to_type=0 and disabled='false'", new Object[] { member.getMember_id() });
/* 29 */     map.put("mNum", Integer.valueOf(mNum));
/* 30 */     int pNum = this.baseDaoSupport.queryForInt("SELECT sum(point) as pNum FROM member where member_id=?", new Object[] { member.getMember_id() });
/* 31 */     map.put("pNum", Integer.valueOf(pNum));
/* 32 */     Object oaNum = this.baseDaoSupport.queryForObject("SELECT advance FROM member where member_id=?", new DoubleMapper(), new Object[] { member.getMember_id() });
/* 33 */     Double aNum = Double.valueOf(oaNum == null ? 0.0D : ((Double)oaNum).doubleValue());
/* 34 */     map.put("aNum", aNum);
/*    */ 
/* 37 */     int couponNum = this.baseDaoSupport.queryForInt("SELECT count(*) as couponNum FROM member_coupon where memberid=?", new Object[] { member.getMember_id() });
/* 38 */     map.put("couponNum", Integer.valueOf(couponNum));
/*    */ 
/* 41 */     Long curTime = Long.valueOf(new Date().getTime());
/*    */ 
/* 44 */     String sql = "SELECT sum(discount_price) FROM  member_coupon WHERE used=1 and memberid = ?";
/* 45 */     Double couponprice = (Double)this.baseDaoSupport.queryForObject(sql, new DoubleMapper(), new Object[] { member.getMember_id() });
/* 46 */     map.put("couponprice", couponprice);
/*    */ 
/* 49 */     int commentRNum = this.baseDaoSupport.queryForInt("SELECT count(*) as commentRNum FROM comments where author_id=? and display='true'", new Object[] { member.getMember_id() });
/*    */ 
/* 51 */     map.put("commentRNum", Integer.valueOf(commentRNum));
/* 52 */     List pa = this.baseDaoSupport.queryForList("SELECT name FROM promotion_activity where enable='1' and end_time>=? and begin_time<=?", Object.class, new Object[] { curTime, curTime });
/* 53 */     pa = pa == null ? new ArrayList() : pa;
/* 54 */     map.put("pa", pa);
/* 55 */     return map;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.WelcomeInfoManager
 * JD-Core Version:    0.6.0
 */