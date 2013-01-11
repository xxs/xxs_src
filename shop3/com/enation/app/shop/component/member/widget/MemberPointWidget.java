/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_point")
/*     */ @Scope("prototype")
/*     */ public class MemberPointWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IPointHistoryManager pointHistoryManager;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  41 */     setMenu("point");
/*  42 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  43 */     this.action = (this.action == null ? "" : this.action);
/*  44 */     IUserService userService = UserServiceFactory.getUserService();
/*  45 */     Member member = userService.getCurrentMember();
/*  46 */     member = this.memberManager.get(member.getMember_id());
/*     */ 
/*  48 */     if (this.action.equals("")) {
/*  49 */       setPageName("member_pointhistory");
/*     */ 
/*  52 */       putData("freezepoint", Integer.valueOf(this.memberPointManger.getFreezePointByMemberId(member.getMember_id().intValue())));
/*  53 */       putData("freezemp", Integer.valueOf(this.memberPointManger.getFreezeMpByMemberId(member.getMember_id().intValue())));
/*  54 */       putData("member", member);
/*     */ 
/*  57 */       MemberLv memberLv = this.memberLvManager.get(member.getLv_id());
/*  58 */       putData("memberLv", memberLv);
/*     */ 
/*  61 */       MemberLv nextLv = this.memberLvManager.getNextLv(member.getPoint().intValue());
/*  62 */       putData("nextLv", nextLv);
/*     */     }
/*  64 */     else if (this.action.equals("list"))
/*     */     {
/*  67 */       String page = request.getParameter("page");
/*  68 */       page = (page == null) || (page.equals("")) ? "1" : page;
/*  69 */       int pageSize = 20;
/*  70 */       Page pointHistoryPage = this.pointHistoryManager.pagePointHistory(Integer.valueOf(page).intValue(), pageSize);
/*     */ 
/*  72 */       List pointHistoryList = (List)pointHistoryPage.getResult();
/*  73 */       pointHistoryList = pointHistoryList == null ? new ArrayList() : pointHistoryList;
/*     */ 
/*  76 */       putData("totalCount", Long.valueOf(pointHistoryPage.getTotalCount()));
/*  77 */       putData("pageSize", Integer.valueOf(pageSize));
/*  78 */       putData("pageNo", page);
/*  79 */       putData("pointHistoryList", pointHistoryList);
/*     */ 
/*  81 */       setActionPageName("page_list");
/*  82 */     } else if (this.action.equals("freeze"))
/*     */     {
/*  85 */       String page = request.getParameter("page");
/*  86 */       page = (page == null) || (page.equals("")) ? "1" : page;
/*  87 */       int pageSize = 20;
/*  88 */       Page pointHistoryPage = this.pointHistoryManager.pagePointFreeze(Integer.valueOf(page).intValue(), pageSize);
/*     */ 
/*  90 */       List pointFreezeList = (List)pointHistoryPage.getResult();
/*  91 */       pointFreezeList = pointFreezeList == null ? new ArrayList() : pointFreezeList;
/*     */ 
/*  94 */       putData("totalCount", Long.valueOf(pointHistoryPage.getTotalCount()));
/*  95 */       putData("pageSize", Integer.valueOf(pageSize));
/*  96 */       putData("pageNo", page);
/*  97 */       putData("pointFreezeList", pointFreezeList);
/*  98 */       putData("point", member.getPoint());
/*  99 */       setActionPageName("point_freeze");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 111 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 115 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger() {
/* 119 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger) {
/* 123 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 127 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 131 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IPointHistoryManager getPointHistoryManager() {
/* 135 */     return this.pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/* 139 */     this.pointHistoryManager = pointHistoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberPointWidget
 * JD-Core Version:    0.6.0
 */