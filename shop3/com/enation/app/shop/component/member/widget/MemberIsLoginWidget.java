/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_islogin")
/*    */ @Scope("prototype")
/*    */ public class MemberIsLoginWidget extends AbstractWidget
/*    */ {
/*    */   public boolean cacheAble()
/*    */   {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 34 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 35 */     if (member == null) {
/* 36 */       putData2("isLogin", Boolean.valueOf(false));
/* 37 */       putData("isLogin", Boolean.valueOf(false));
/*    */     } else {
/* 39 */       putData2("isLogin", Boolean.valueOf(true));
/* 40 */       putData("isLogin", Boolean.valueOf(true));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberIsLoginWidget
 * JD-Core Version:    0.6.0
 */