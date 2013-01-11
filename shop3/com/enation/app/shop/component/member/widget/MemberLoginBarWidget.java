/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_login_bar")
/*    */ @Scope("prototype")
/*    */ public class MemberLoginBarWidget extends AbstractWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean cacheAble()
/*    */   {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params) {
/* 40 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 41 */     if (member == null)
/*    */     {
/* 44 */       putData("loginForward", StringUtil.toUTF8(ThreadContextHolder.getHttpRequest().getServletPath()));
/*    */ 
/* 46 */       putData2("isLogin", Boolean.valueOf(false));
/* 47 */       putData("isLogin", Boolean.valueOf(false));
/*    */     } else {
/* 49 */       putData2("isLogin", Boolean.valueOf(true));
/* 50 */       putData("isLogin", Boolean.valueOf(true));
/* 51 */       putData("member", member);
/*    */     }
/* 53 */     setPageName("member_login_bar");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberLoginBarWidget
 * JD-Core Version:    0.6.0
 */