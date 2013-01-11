/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IMemberOrderManager;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_goods")
/*    */ @Scope("prototype")
/*    */ public class MemberGoodsWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IMemberOrderManager memberOrderManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 37 */     setMenu("goods");
/* 38 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 39 */     String page = request.getParameter("page");
/* 40 */     page = (page == null) || (page.equals("")) ? "1" : page;
/* 41 */     int pageSize = 10;
/*    */ 
/* 43 */     String keyword = request.getParameter("keyword");
/* 44 */     if (!StringUtil.isEmpty(keyword)) {
/* 45 */       keyword = StringUtil.toUTF8(keyword);
/*    */     }
/*    */ 
/* 48 */     Page goodsPage = this.memberOrderManager.pageGoods(Integer.valueOf(page).intValue(), pageSize, keyword);
/*    */ 
/* 50 */     Long totalCount = Long.valueOf(goodsPage.getTotalCount());
/*    */ 
/* 52 */     putData("goodsPage", goodsPage);
/* 53 */     putData("totalCount", totalCount);
/* 54 */     putData("pageSize", Integer.valueOf(pageSize));
/* 55 */     putData("page", page);
/* 56 */     putData("keyword", keyword);
/*    */   }
/*    */ 
/*    */   public void setMemberOrderManager(IMemberOrderManager memberOrderManager) {
/* 60 */     this.memberOrderManager = memberOrderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberGoodsWidget
 * JD-Core Version:    0.6.0
 */