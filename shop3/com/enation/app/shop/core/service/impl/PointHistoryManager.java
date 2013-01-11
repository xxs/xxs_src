/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.PointHistory;
/*    */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PointHistoryManager extends BaseSupport
/*    */   implements IPointHistoryManager
/*    */ {
/*    */   public Page pagePointHistory(int pageNo, int pageSize, int pointType)
/*    */   {
/* 25 */     IUserService userService = UserServiceFactory.getUserService();
/* 26 */     Member member = userService.getCurrentMember();
/*    */ 
/* 28 */     String sql = "select * from point_history where member_id = ? and point_type=? order by time desc";
/*    */ 
/* 30 */     Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id(), Integer.valueOf(pointType) });
/*    */ 
/* 32 */     return webpage;
/*    */   }
/*    */ 
/*    */   public Page pagePointHistory(int pageNo, int pageSize) {
/* 36 */     IUserService userService = UserServiceFactory.getUserService();
/* 37 */     Member member = userService.getCurrentMember();
/*    */ 
/* 39 */     String sql = "select * from point_history where member_id = ? order by time desc";
/*    */ 
/* 41 */     Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
/*    */ 
/* 43 */     return webpage;
/*    */   }
/*    */ 
/*    */   public Long getConsumePoint(int pointType)
/*    */   {
/* 48 */     IUserService userService = UserServiceFactory.getUserService();
/* 49 */     Member member = userService.getCurrentMember();
/* 50 */     Long result = Long.valueOf(this.baseDaoSupport.queryForLong("select SUM(point) from point_history where  member_id = ?  and  type=0  and point_type=?", new Object[] { member.getMember_id(), Integer.valueOf(pointType) }));
/*    */ 
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */   public Long getGainedPoint(int pointType)
/*    */   {
/* 59 */     IUserService userService = UserServiceFactory.getUserService();
/* 60 */     Member member = userService.getCurrentMember();
/* 61 */     Long result = Long.valueOf(this.baseDaoSupport.queryForLong("select SUM(point) from point_history where    member_id = ? and  type=1  and point_type=?", new Object[] { member.getMember_id(), Integer.valueOf(pointType) }));
/*    */ 
/* 65 */     return result;
/*    */   }
/*    */ 
/*    */   public void addPointHistory(PointHistory pointHistory)
/*    */   {
/* 70 */     this.baseDaoSupport.insert("point_history", pointHistory);
/*    */   }
/*    */ 
/*    */   public List<PointHistory> listPointHistory(int member_id, int pointtype)
/*    */   {
/* 75 */     String sql = "select * from point_history where member_id = ? and point_type = ? order by time desc";
/* 76 */     List list = this.baseDaoSupport.queryForList(sql, PointHistory.class, new Object[] { Integer.valueOf(member_id), Integer.valueOf(pointtype) });
/* 77 */     return list;
/*    */   }
/*    */ 
/*    */   public Page pagePointFreeze(int pageNo, int pageSize)
/*    */   {
/* 88 */     IUserService userService = UserServiceFactory.getUserService();
/* 89 */     Member member = userService.getCurrentMember();
/* 90 */     String sql = "select * from freeze_point where memberid = ? order by id desc";
/* 91 */     Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
/*    */ 
/* 93 */     return webpage;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.PointHistoryManager
 * JD-Core Version:    0.6.0
 */