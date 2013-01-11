/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_ask")
/*    */ @Scope("prototype")
/*    */ public class MemberAskWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IMemberCommentManager memberCommentManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 33 */     setMenu("ask");
/* 34 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 35 */     String page = request.getParameter("page");
/*    */ 
/* 37 */     page = (page == null) || (page.equals("")) ? "1" : page;
/* 38 */     setPageName("myask");
/* 39 */     int pageSize = 5;
/* 40 */     IUserService userService = UserServiceFactory.getUserService();
/* 41 */     Member member = userService.getCurrentMember();
/* 42 */     Page commentsPage = this.memberCommentManager.getMemberComments(Integer.valueOf(page).intValue(), pageSize, 2, member.getMember_id().intValue());
/* 43 */     Long totalCount = Long.valueOf(commentsPage.getTotalCount());
/*    */ 
/* 45 */     List commentsList = (List)commentsPage.getResult();
/* 46 */     commentsList = commentsList == null ? new ArrayList() : commentsList;
/*    */ 
/* 48 */     putData("totalCount", totalCount);
/* 49 */     putData("pageSize", Integer.valueOf(pageSize));
/* 50 */     putData("page", page);
/* 51 */     putData("commentsList", commentsList);
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
/* 59 */     this.memberCommentManager = memberCommentManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberAskWidget
 * JD-Core Version:    0.6.0
 */