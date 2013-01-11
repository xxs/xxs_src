/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.service.IMemberOrderManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.app.shop.core.service.OrderStatus;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_order")
/*     */ @Scope("prototype")
/*     */ public class MemberOrderWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMemberOrderManager memberOrderManager;
/*     */   private IOrderManager orderManager;
/*     */   private IPromotionManager promotionManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  50 */     if ((this.action == null) || (this.action.equals("")) || (this.action.equals("list"))) {
/*  51 */       this.action = "list";
/*  52 */       list();
/*  53 */     } else if (this.action.equals("cancel")) {
/*  54 */       cancel();
/*  55 */     } else if (this.action.equals("savecancel")) {
/*  56 */       saveCancle();
/*  57 */     } else if (this.action.equals("confirmRog")) {
/*  58 */       confirmRog();
/*  59 */     } else if (this.action.equals("restore")) {
/*  60 */       restore();
/*  61 */     } else if (this.action.equals("thaw")) {
/*  62 */       thaw();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void thaw()
/*     */   {
/*     */     try
/*     */     {
/*  72 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  73 */       int orderid = StringUtil.toInt(request.getParameter("orderid"), true);
/*  74 */       this.memberPointManger.thaw(Integer.valueOf(orderid));
/*  75 */       showSuccessJson("成功解冻");
/*     */     } catch (RuntimeException e) {
/*  77 */       this.logger.error("手动解冻积分" + e.getMessage(), e);
/*  78 */       showErrorJson(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void list()
/*     */   {
/*  85 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  86 */     String page = request.getParameter("page");
/*  87 */     page = (page == null) || (page.equals("")) ? "1" : page;
/*  88 */     setPageName("order_list");
/*  89 */     int pageSize = 10;
/*     */ 
/*  91 */     String status = request.getParameter("status");
/*  92 */     String keyword = request.getParameter("keyword");
/*  93 */     if ((!StringUtil.isEmpty(keyword)) && (!StringUtil.isEmpty(EopSetting.ENCODING)))
/*     */     {
/*  95 */       keyword = StringUtil.to(keyword, EopSetting.ENCODING);
/*     */     }
/*     */ 
/*  98 */     Page ordersPage = this.memberOrderManager.pageOrders(Integer.valueOf(page).intValue(), pageSize, status, keyword);
/*     */ 
/* 100 */     Long totalCount = Long.valueOf(ordersPage.getTotalCount());
/*     */ 
/* 102 */     List ordersList = (List)ordersPage.getResult();
/* 103 */     ordersList = ordersList == null ? new ArrayList() : ordersList;
/*     */ 
/* 105 */     putData("totalCount", totalCount);
/* 106 */     putData("pageSize", Integer.valueOf(pageSize));
/* 107 */     putData("page", page);
/* 108 */     putData("ordersList", ordersList);
/* 109 */     if (status != null) {
/* 110 */       putData("status", Integer.valueOf(status));
/*     */     }
/* 112 */     putData("keyword", keyword);
/* 113 */     putData(OrderStatus.getOrderStatusMap());
/*     */   }
/*     */ 
/*     */   private void cancel()
/*     */   {
/* 120 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 122 */     IUserService userService = UserServiceFactory.getUserService();
/* 123 */     if (userService.getCurrentMember() == null) {
/* 124 */       showError("对不起，您没有权限进行此项操作！");
/* 125 */       return;
/*     */     }
/* 127 */     String sn = request.getParameter("sn");
/* 128 */     String from = request.getParameter("from");
/* 129 */     Order order = this.orderManager.get(sn);
/* 130 */     if (order == null) {
/* 131 */       showError("对不起，此订单不存在！");
/* 132 */       return;
/*     */     }
/* 134 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/* 135 */       showError("对不起，此订单不能取消！");
/* 136 */       return;
/*     */     }
/* 138 */     if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
/* 139 */       showError("对不起，您没有权限进行此项操作！");
/* 140 */       return;
/*     */     }
/* 142 */     putData("sn", sn);
/* 143 */     putData("from", from);
/* 144 */     setActionPageName("order_cancel");
/*     */   }
/*     */ 
/*     */   private void saveCancle()
/*     */   {
/* 150 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 151 */     String ajax = request.getParameter("ajax");
/*     */     try
/*     */     {
/* 154 */       String sn = request.getParameter("sn");
/* 155 */       String cancel_reason = request.getParameter("cancel_reason");
/* 156 */       this.orderFlowManager.cancel(sn, cancel_reason);
/* 157 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 159 */       if (StringUtil.isEmpty(ajax))
/*     */       {
/* 161 */         if (member != null) {
/* 162 */           showSuccess("订单取消成功", "查看此订单", "member_orderdetail_" + sn + ".html");
/*     */         }
/*     */         else
/* 165 */           showSuccess("订单取消成功", "查看此订单", "orderdetail_" + sn + ".html");
/*     */       }
/*     */       else
/* 168 */         showSuccessJson("订单取消成功");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 172 */       if (StringUtil.isEmpty(ajax))
/* 173 */         showError(re.getMessage());
/*     */       else
/* 175 */         showErrorJson(re.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void restore()
/*     */   {
/* 187 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 188 */     IUserService userService = UserServiceFactory.getUserService();
/* 189 */     if (userService.getCurrentMember() == null) {
/* 190 */       showErrorJson("对不起，您没有权限进行此项操作！");
/* 191 */       return;
/*     */     }
/* 193 */     String sn = request.getParameter("sn");
/* 194 */     String from = request.getParameter("from");
/* 195 */     Order order = this.orderManager.get(sn);
/* 196 */     if (order == null) {
/* 197 */       showErrorJson("对不起，此订单不存在！");
/* 198 */       return;
/*     */     }
/* 200 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 8)) {
/* 201 */       showErrorJson("对不起，此订单不能还原！");
/* 202 */       return;
/*     */     }
/* 204 */     if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
/* 205 */       showErrorJson("对不起，您没有权限进行此项操作！");
/* 206 */       return;
/*     */     }
/*     */     try {
/* 209 */       this.orderFlowManager.restore(sn);
/* 210 */       showSuccessJson("还原订单成功！");
/*     */     } catch (RuntimeException re) {
/* 212 */       showErrorJson(re.getMessage());
/* 213 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void confirmRog()
/*     */   {
/* 222 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 223 */     String orderId = request.getParameter("orderId");
/* 224 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 226 */     if (member != null)
/* 227 */       this.orderFlowManager.rogConfirm(Integer.parseInt(orderId), member.getMember_id(), member.getUname(), member.getUname(), DateUtil.getDateline());
/*     */     else {
/* 229 */       this.orderFlowManager.rogConfirm(Integer.parseInt(orderId), null, "游客", "游客", DateUtil.getDateline());
/*     */     }
/* 231 */     showSuccessJson("确认收货成功！");
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IMemberOrderManager getMemberOrderManager() {
/* 239 */     return this.memberOrderManager;
/*     */   }
/*     */ 
/*     */   public void setMemberOrderManager(IMemberOrderManager memberOrderManager) {
/* 243 */     this.memberOrderManager = memberOrderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 247 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager() {
/* 251 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 255 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 259 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 263 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 268 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/* 274 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 280 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberOrderWidget
 * JD-Core Version:    0.6.0
 */