/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class MemberAdvanceLogsWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IAdvanceLogsManager advanceLogsManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 40 */     setPageName("member_advanceLogs");
/* 41 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 42 */     String page = request.getParameter("page");
/* 43 */     page = (page == null) || (page.equals("")) ? "1" : page;
/* 44 */     int pageSize = 20;
/* 45 */     Page advanceLogsPage = this.advanceLogsManager.pageAdvanceLogs(Integer.valueOf(page).intValue(), pageSize);
/* 46 */     Long pageCount = Long.valueOf(advanceLogsPage.getTotalPageCount());
/* 47 */     List advanceLogsList = (List)advanceLogsPage.getResult();
/* 48 */     advanceLogsList = advanceLogsList == null ? new ArrayList() : advanceLogsList;
/* 49 */     IUserService userService = UserServiceFactory.getUserService();
/* 50 */     Member member = userService.getCurrentMember();
/* 51 */     putData("advanceLogsList", advanceLogsList);
/* 52 */     putData("member_advance", member.getAdvance());
/* 53 */     putData("pageSize", Integer.valueOf(pageSize));
/* 54 */     putData("pageCount", pageCount);
/* 55 */     putData("page", page);
/* 56 */     putData("totalCount", Long.valueOf(advanceLogsPage.getTotalCount()));
/*    */   }
/*    */ 
/*    */   public IAdvanceLogsManager getAdvanceLogsManager() {
/* 60 */     return this.advanceLogsManager;
/*    */   }
/*    */ 
/*    */   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
/* 64 */     this.advanceLogsManager = advanceLogsManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberAdvanceLogsWidget
 * JD-Core Version:    0.6.0
 */