/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*    */ import com.enation.app.shop.core.service.IMemberOrderItemManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_comments")
/*    */ @Scope("prototype")
/*    */ public class MemberCommentsWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IMemberCommentManager memberCommentManager;
/*    */   private IMemberOrderItemManager memberOrderItemManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 38 */     setMenu("comments");
/* 39 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 40 */     String page = request.getParameter("page");
/* 41 */     page = (page == null) || (page.equals("")) ? "1" : page;
/* 42 */     setPageName("mycomments");
/* 43 */     IUserService userService = UserServiceFactory.getUserService();
/* 44 */     Member member = userService.getCurrentMember();
/* 45 */     int pageSize = 5;
/*    */ 
/* 47 */     if (StringUtil.isEmpty(this.action)) {
/* 48 */       Page commentsPage = this.memberCommentManager.getMemberComments(Integer.valueOf(page).intValue(), pageSize, 1, member.getMember_id().intValue());
/* 49 */       Long totalCount = Long.valueOf(commentsPage.getTotalCount());
/*    */ 
/* 51 */       List commentsList = (List)commentsPage.getResult();
/* 52 */       commentsList = commentsList == null ? new ArrayList() : commentsList;
/*    */ 
/* 54 */       putData("totalCount", totalCount);
/* 55 */       putData("pageSize", Integer.valueOf(pageSize));
/* 56 */       putData("page", page);
/* 57 */       putData("commentsList", commentsList);
/*    */     }
/* 59 */     else if ("goods".equals(this.action)) {
/* 60 */       pageSize = 10;
/* 61 */       Page goodsPage = this.memberOrderItemManager.getGoodsList(member.getMember_id().intValue(), 0, Integer.valueOf(page).intValue(), pageSize);
/* 62 */       putData("goodsPage", goodsPage);
/* 63 */       putData("totalCount", Long.valueOf(goodsPage.getTotalCount()));
/* 64 */       putData("pageSize", Integer.valueOf(pageSize));
/* 65 */       putData("page", page);
/*    */ 
/* 67 */       setActionPageName("comments_goods");
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
/*    */   {
/* 77 */     this.memberOrderItemManager = memberOrderItemManager;
/*    */   }
/*    */ 
/*    */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
/* 81 */     this.memberCommentManager = memberCommentManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberCommentsWidget
 * JD-Core Version:    0.6.0
 */