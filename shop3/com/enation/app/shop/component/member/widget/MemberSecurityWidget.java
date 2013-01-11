/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.base.core.service.IRegionsManager;
/*    */ import com.enation.app.shop.core.service.IMemberManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("member_security")
/*    */ @Scope("prototype")
/*    */ public class MemberSecurityWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IRegionsManager regionsManager;
/*    */   private IMemberManager memberManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 40 */     setPageName("member_security");
/* 41 */     setMenu("security");
/* 42 */     showMenu(true);
/* 43 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 44 */     IUserService userService = UserServiceFactory.getUserService();
/* 45 */     Member member = userService.getCurrentMember();
/* 46 */     String action = request.getParameter("action");
/* 47 */     action = action == null ? "" : action;
/* 48 */     if (action.equals("save")) {
/* 49 */       showMenu(false);
/* 50 */       String oldPassword = request.getParameter("oldpassword");
/* 51 */       oldPassword = oldPassword == null ? "" : StringUtil.md5(oldPassword);
/*    */ 
/* 54 */       if (oldPassword.equals(member.getPassword())) {
/* 55 */         String password = request.getParameter("password");
/* 56 */         String passwd_re = request.getParameter("passwd_re");
/*    */ 
/* 58 */         if (passwd_re.equals(password)) {
/*    */           try {
/* 60 */             this.memberManager.updatePassword(password);
/* 61 */             showSuccess("修改密码成功", "修改密码", "member_security.html");
/*    */           }
/*    */           catch (Exception e) {
/* 64 */             if (this.logger.isDebugEnabled()) {
/* 65 */               this.logger.error(e.getStackTrace());
/*    */             }
/* 67 */             showError("修改密码失败", "修改密码", "member_security.html");
/*    */           }
/*    */         }
/*    */         else
/*    */         {
/* 72 */           showError("修改失败！两次输入的密码不一致", "修改密码", "member_security.html");
/*    */         }
/*    */       }
/*    */       else {
/* 76 */         showError("修改失败！原始密码不符", "修改密码", "member_security.html");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public IRegionsManager getRegionsManager()
/*    */   {
/* 83 */     return this.regionsManager;
/*    */   }
/*    */ 
/*    */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 87 */     this.regionsManager = regionsManager;
/*    */   }
/*    */ 
/*    */   public IMemberManager getMemberManager() {
/* 91 */     return this.memberManager;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 95 */     this.memberManager = memberManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberSecurityWidget
 * JD-Core Version:    0.6.0
 */