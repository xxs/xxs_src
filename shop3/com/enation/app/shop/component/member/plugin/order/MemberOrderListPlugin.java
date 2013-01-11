/*    */ package com.enation.app.shop.component.member.plugin.order;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberOrderListPlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 35 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 36 */     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
/*    */ 
/* 39 */     List orderList = this.orderManager.listOrderByMemberId(memberid);
/*    */ 
/* 41 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 42 */     freeMarkerPaser.setClz(getClass());
/* 43 */     freeMarkerPaser.putData("orderList", orderList);
/* 44 */     freeMarkerPaser.setPageName("order_list");
/* 45 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String onShowMemberDetailHtml(Member member)
/*    */   {
/* 56 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*    */ 
/* 58 */     freeMarkerPaser.putData("memberid", member.getMember_id());
/*    */ 
/* 60 */     freeMarkerPaser.setPageName("member_order");
/* 61 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member)
/*    */   {
/* 67 */     return "他的订单";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 73 */     return 5;
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 84 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager)
/*    */   {
/* 89 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.order.MemberOrderListPlugin
 * JD-Core Version:    0.6.0
 */