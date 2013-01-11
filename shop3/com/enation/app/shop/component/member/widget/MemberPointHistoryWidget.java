/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_pointhistory")
/*     */ @Scope("prototype")
/*     */ public class MemberPointHistoryWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IPointHistoryManager pointHistoryManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IMemberManager memberManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  48 */     setMenu("pointhistory");
/*  49 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  50 */     this.action = (this.action == null ? "" : this.action);
/*     */ 
/*  53 */     IUserService userService = UserServiceFactory.getUserService();
/*  54 */     Member member = userService.getCurrentMember();
/*     */ 
/*  56 */     member = this.memberManager.get(member.getMember_id());
/*  57 */     String page = request.getParameter("page");
/*  58 */     int pointType = StringUtil.toInt(request.getParameter("pointtype"), true);
/*     */ 
/*  60 */     page = (page == null) || (page.equals("")) ? "1" : page;
/*  61 */     int pageSize = 20;
/*  62 */     Page pointHistoryPage = this.pointHistoryManager.pagePointHistory(Integer.valueOf(page).intValue(), pageSize, pointType);
/*     */ 
/*  64 */     Long totalcount = Long.valueOf(pointHistoryPage.getTotalCount());
/*  65 */     List pointHistoryList = (List)pointHistoryPage.getResult();
/*  66 */     pointHistoryList = pointHistoryList == null ? new ArrayList() : pointHistoryList;
/*     */ 
/*  69 */     Long consumePoint = this.pointHistoryManager.getConsumePoint(pointType);
/*  70 */     Long gainedPoint = this.pointHistoryManager.getGainedPoint(pointType);
/*     */ 
/*  72 */     int point = 0;
/*  73 */     if (pointType == 0) {
/*  74 */       point = member.getPoint().intValue();
/*     */     }
/*     */ 
/*  77 */     if (pointType == 1) {
/*  78 */       point = member.getMp().intValue();
/*     */     }
/*  80 */     putData("totalcount", totalcount);
/*  81 */     putData("pageSize", Integer.valueOf(pageSize));
/*  82 */     putData("page", page);
/*  83 */     putData("pointHistoryList", pointHistoryList);
/*  84 */     putData("point", Integer.valueOf(point));
/*  85 */     putData("consumePoint", consumePoint);
/*  86 */     putData("gainedPoint", gainedPoint);
/*  87 */     setPageName("member_pointhistory");
/*     */   }
/*     */ 
/*     */   public IPointHistoryManager getPointHistoryManager() {
/*  91 */     return this.pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/*  95 */     this.pointHistoryManager = pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 101 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 106 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 112 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 119 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 124 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/* 129 */     return this.memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberPointHistoryWidget
 * JD-Core Version:    0.6.0
 */