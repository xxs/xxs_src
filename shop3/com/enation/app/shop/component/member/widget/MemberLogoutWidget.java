/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import com.enation.framework.util.HttpUtil;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_logout")
/*    */ @Scope("prototype")
/*    */ public class MemberLogoutWidget extends AbstractMemberWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 30 */     showMenu(false);
/* 31 */     ThreadContextHolder.getSessionContext().removeAttribute("curr_member");
/* 32 */     HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "JavaShopUser", "", 0);
/* 33 */     HttpCacheManager.sessionChange();
/*    */ 
/* 35 */     showSuccess("成功退出", "商城首页", "index.html");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberLogoutWidget
 * JD-Core Version:    0.6.0
 */