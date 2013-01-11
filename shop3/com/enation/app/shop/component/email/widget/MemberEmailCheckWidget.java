/*    */ package com.enation.app.shop.component.email.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.IMemberManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.EncryptionUtil1;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_email_check")
/*    */ @Scope("prototype")
/*    */ public class MemberEmailCheckWidget extends AbstractWidget
/*    */ {
/*    */   private IMemberManager memberManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/*    */     try
/*    */     {
/* 27 */       String s = ThreadContextHolder.getHttpRequest().getParameter("s");
/*    */ 
/* 29 */       String str = EncryptionUtil1.authcode(s, "DECODE", "", 0);
/* 30 */       String[] array = StringUtils.split(str, ",");
/* 31 */       if (array.length != 2) throw new RuntimeException("验证字串不正确");
/* 32 */       int memberid = Integer.valueOf(array[0]).intValue();
/* 33 */       long regtime = Long.valueOf(array[1]).longValue();
/*    */ 
/* 35 */       Member member = this.memberManager.get(Integer.valueOf(memberid));
/* 36 */       if (member.getRegtime().longValue() != regtime) {
/* 37 */         showError("验证字串不正确");
/* 38 */         return;
/*    */       }
/*    */ 
/* 42 */       if (member.getIs_cheked().intValue() == 0) {
/* 43 */         this.memberManager.checkEmailSuccess(member);
/* 44 */         showSuccess(member.getUname() + "您好，您的邮箱验证成功!", "会员中心首页", "member_index.html");
/*    */       } else {
/* 46 */         showError(member.getUname() + "您好，验证失败，您已经验证过该邮箱!", "会员中心首页", "member_index.html");
/*    */       }
/*    */     }
/*    */     catch (RuntimeException e) {
/* 50 */       showError("验证地址不正确");
/*    */     }
/*    */   }
/*    */ 
/*    */   public IMemberManager getMemberManager()
/*    */   {
/* 57 */     return this.memberManager;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 61 */     this.memberManager = memberManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.email.widget.MemberEmailCheckWidget
 * JD-Core Version:    0.6.0
 */