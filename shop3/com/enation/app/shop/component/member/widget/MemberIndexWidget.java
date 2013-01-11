/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.mapper.GoodsListMapper;
/*     */ import com.enation.app.shop.core.service.IFavoriteManager;
/*     */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IWelcomeInfoManager;
/*     */ import com.enation.app.shop.core.service.OrderStatus;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_index")
/*     */ @Scope("prototype")
/*     */ public class MemberIndexWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IWelcomeInfoManager welcomeInfoManager;
/*     */   private IMemberCommentManager memberCommentManager;
/*     */   private IFavoriteManager favoriteManager;
/*     */   private IMemberManager memberManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */   private IOrderManager orderManager;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  40 */     setPageName("index");
/*  41 */     Member member = getCurrentMember();
/*  42 */     member = this.memberManager.get(member.getMember_id());
/*  43 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/*     */ 
/*  45 */     Map wel = this.welcomeInfoManager.mapWelcomInfo();
/*     */ 
/*  47 */     putData("member", member);
/*  48 */     putData("wel", wel);
/*     */ 
/*  50 */     int discussTotal = this.memberCommentManager.getMemberCommentTotal(member.getMember_id().intValue(), 1);
/*  51 */     putData("discussTotal", Integer.valueOf(discussTotal));
/*     */ 
/*  54 */     int askTotal = this.memberCommentManager.getMemberCommentTotal(member.getMember_id().intValue(), 2);
/*  55 */     putData("askTotal", Integer.valueOf(askTotal));
/*     */ 
/*  58 */     int favoriteTotal = this.favoriteManager.getCount(member.getMember_id());
/*  59 */     putData("favoriteTotal", Integer.valueOf(favoriteTotal));
/*     */ 
/*  62 */     String sql = "select * from goods where disabled=0 and market_enable=1  order by buy_count asc";
/*  63 */     List list = this.baseDaoSupport.queryForList(sql.toString(), 1, 8, new GoodsListMapper());
/*  64 */     putData("goodsList", list);
/*     */ 
/*  67 */     putData("freezepoint", Integer.valueOf(this.memberPointManger.getFreezeMpByMemberId(member.getMember_id().intValue())));
/*     */ 
/*  69 */     Page orderPage = this.orderManager.listByStatus(1, 100, 0, member.getMember_id().intValue());
/*  70 */     putData("orderPage", orderPage);
/*  71 */     putData("orderTotal", Integer.valueOf(this.orderManager.getMemberOrderNum(member.getMember_id().intValue())));
/*     */ 
/*  73 */     putData(OrderStatus.getOrderStatusMap());
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IWelcomeInfoManager getWelcomeInfoManager()
/*     */   {
/*  82 */     return this.welcomeInfoManager;
/*     */   }
/*     */ 
/*     */   public void setWelcomeInfoManager(IWelcomeInfoManager welcomeInfoManager) {
/*  86 */     this.welcomeInfoManager = welcomeInfoManager;
/*     */   }
/*     */ 
/*     */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager)
/*     */   {
/*  91 */     this.memberCommentManager = memberCommentManager;
/*     */   }
/*     */ 
/*     */   public IFavoriteManager getFavoriteManager()
/*     */   {
/*  96 */     return this.favoriteManager;
/*     */   }
/*     */ 
/*     */   public void setFavoriteManager(IFavoriteManager favoriteManager)
/*     */   {
/* 101 */     this.favoriteManager = favoriteManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 106 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 111 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/* 116 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 121 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 126 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager)
/*     */   {
/* 131 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IMemberCommentManager getMemberCommentManager()
/*     */   {
/* 136 */     return this.memberCommentManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberIndexWidget
 * JD-Core Version:    0.6.0
 */