/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.OrderMeta;
/*     */ import com.enation.app.shop.core.service.IMemberOrderManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IOrderMetaManager;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.app.shop.core.service.OrderStatus;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_orderdetail")
/*     */ @Scope("prototype")
/*     */ public class MemberOrderDetailWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMemberOrderManager memberOrderManager;
/*     */   private IOrderManager orderManager;
/*     */   private IOrderMetaManager orderMetaManager;
/*     */   private IOrderReportManager orderReportManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  49 */     setMenu("order");
/*  50 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  51 */     String sn = parseSn(request.getServletPath());
/*     */ 
/*  53 */     Order order = this.orderManager.get(sn);
/*  54 */     List orderLogList = this.memberOrderManager.listOrderLog(order.getOrder_id().intValue());
/*  55 */     List orderItemsList = this.orderManager.listGoodsItems(order.getOrder_id());
/*     */ 
/*  59 */     putData("order", order);
/*  60 */     putData("orderLogList", orderLogList);
/*  61 */     putData("orderItemsList", orderItemsList);
/*     */ 
/*  64 */     List deliveryList = this.orderReportManager.getDeliveryList(order.getOrder_id().intValue());
/*  65 */     putData("deliveryList", deliveryList);
/*     */ 
/*  69 */     List metaList = this.orderMetaManager.list(order.getOrder_id().intValue());
/*  70 */     putData("metaList", metaList);
/*     */ 
/*  73 */     Map metaMap = new HashMap();
/*  74 */     for (OrderMeta meta : metaList) {
/*  75 */       metaMap.put(meta.getMeta_key(), meta.getMeta_value());
/*     */     }
/*  77 */     putData("meta", metaMap);
/*     */ 
/*  79 */     putData("isLogin", Boolean.valueOf(getCurrentMember() != null));
/*     */ 
/*  81 */     putData(OrderStatus.getOrderStatusMap());
/*  82 */     setPageName("order_detail");
/*     */   }
/*     */ 
/*     */   public boolean getShowMenu()
/*     */   {
/*  91 */     if (getCurrentMember() == null) {
/*  92 */       putData("isLogin", Boolean.valueOf(false));
/*  93 */       return false;
/*     */     }
/*  95 */     putData("isLogin", Boolean.valueOf(true));
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   private static String parseSn(String url)
/*     */   {
/* 102 */     String pattern = "(.*)orderdetail_(\\w+)(.*)";
/* 103 */     String value = null;
/* 104 */     Pattern p = Pattern.compile(pattern, 34);
/* 105 */     Matcher m = p.matcher(url);
/* 106 */     if (m.find()) {
/* 107 */       value = m.replaceAll("$2");
/*     */     }
/* 109 */     return value;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 113 */     System.out.println(parseSn("/member_orderdetail_10010101.html"));
/* 114 */     System.out.println(parseSn("/orderdetail_10010101.html"));
/*     */   }
/*     */ 
/*     */   public IMemberOrderManager getMemberOrderManager()
/*     */   {
/* 119 */     return this.memberOrderManager;
/*     */   }
/*     */ 
/*     */   public void setMemberOrderManager(IMemberOrderManager memberOrderManager) {
/* 123 */     this.memberOrderManager = memberOrderManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 127 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 131 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IOrderReportManager getOrderReportManager()
/*     */   {
/* 137 */     return this.orderReportManager;
/*     */   }
/*     */ 
/*     */   public void setOrderReportManager(IOrderReportManager orderReportManager)
/*     */   {
/* 142 */     this.orderReportManager = orderReportManager;
/*     */   }
/*     */ 
/*     */   public IOrderMetaManager getOrderMetaManager()
/*     */   {
/* 149 */     return this.orderMetaManager;
/*     */   }
/*     */ 
/*     */   public void setOrderMetaManager(IOrderMetaManager orderMetaManager)
/*     */   {
/* 154 */     this.orderMetaManager = orderMetaManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberOrderDetailWidget
 * JD-Core Version:    0.6.0
 */