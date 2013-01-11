/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.AdvanceLogs;
/*    */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AdvanceLogsManager extends BaseSupport
/*    */   implements IAdvanceLogsManager
/*    */ {
/*    */   public Page pageAdvanceLogs(int pageNo, int pageSize)
/*    */   {
/* 25 */     IUserService userService = UserServiceFactory.getUserService();
/* 26 */     Member member = userService.getCurrentMember();
/* 27 */     Page page = this.baseDaoSupport.queryForPage("select * from advance_logs where member_id=? order by mtime DESC", pageNo, pageSize, new Object[] { member.getMember_id() });
/* 28 */     return page;
/*    */   }
/*    */ 
/*    */   public void add(AdvanceLogs advanceLogs)
/*    */   {
/* 33 */     this.baseDaoSupport.insert("advance_logs", advanceLogs);
/*    */   }
/*    */ 
/*    */   public List listAdvanceLogsByMemberId(int member_id)
/*    */   {
/* 39 */     return this.baseDaoSupport.queryForList("select * from advance_logs where member_id=? order by mtime desc", AdvanceLogs.class, new Object[] { Integer.valueOf(member_id) });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.AdvanceLogsManager
 * JD-Core Version:    0.6.0
 */