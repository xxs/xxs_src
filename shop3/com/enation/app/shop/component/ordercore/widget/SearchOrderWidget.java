/*     */ package com.enation.app.shop.component.ordercore.widget;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.app.shop.core.service.OrderStatus;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("searchorder")
/*     */ @Scope("prototype")
/*     */ public class SearchOrderWidget extends AbstractWidget
/*     */ {
/*     */   private IOrderManager orderManager;
/*     */   private IOrderReportManager orderReportManager;
/*     */   private IPromotionManager promotionManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  45 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  47 */     putData("pagetitle", "游客订单查询 - " + site.getSitename());
/*  48 */     putData("keywords", "游客订单查询," + site.getSitename());
/*     */ 
/*  50 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  51 */     if ((this.action != null) && (!this.action.equals("")))
/*     */     {
/*  53 */       if (this.action.equals("search")) {
/*  54 */         String ship_name = request.getParameter("ship_name");
/*  55 */         if ((ship_name != null) && (!ship_name.equals(""))) {
/*  56 */           String encoding = EopSetting.ENCODING;
/*  57 */           if (!StringUtil.isEmpty(encoding)) {
/*  58 */             ship_name = StringUtil.toUTF8(ship_name);
/*     */           }
/*     */         }
/*  61 */         String ship_tel = request.getParameter("ship_tel");
/*     */ 
/*  63 */         if (StringUtil.isEmpty(ship_name)) {
/*  64 */           showError("请输入收货人姓名!");
/*  65 */           return;
/*     */         }
/*  67 */         if (StringUtil.isEmpty(ship_tel)) {
/*  68 */           showError("请输入手机或固定号码!");
/*  69 */           return;
/*     */         }
/*     */ 
/*  72 */         String page = request.getParameter("page");
/*  73 */         page = (page == null) || (page.equals("")) ? "1" : page;
/*     */ 
/*  75 */         int pageSize = 10;
/*     */ 
/*  77 */         Page ordersPage = this.orderManager.searchForGuest(Integer.parseInt(page), pageSize, ship_name, ship_tel);
/*     */ 
/*  79 */         putData("ordersPage", ordersPage);
/*  80 */         putData("totalCount", Long.valueOf(ordersPage.getTotalCount()));
/*  81 */         putData("ship_name", ship_name);
/*  82 */         putData("ship_tel", ship_tel);
/*  83 */         putData("pageSize", Integer.valueOf(pageSize));
/*  84 */         putData("page", page);
/*  85 */         putData(OrderStatus.getOrderStatusMap());
/*  86 */         setActionPageName("order_search");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/*  92 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IOrderReportManager getOrderReportManager() {
/*  96 */     return this.orderReportManager;
/*     */   }
/*     */ 
/*     */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 100 */     this.orderReportManager = orderReportManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager() {
/* 104 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 108 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.widget.SearchOrderWidget
 * JD-Core Version:    0.6.0
 */