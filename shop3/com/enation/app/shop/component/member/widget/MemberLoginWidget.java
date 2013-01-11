/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.util.EncryptionUtil1;
/*     */ import com.enation.framework.util.HttpUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_login")
/*     */ @Scope("prototype")
/*     */ public class MemberLoginWidget extends AbstractMemberWidget
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private IMemberManager memberManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  41 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  43 */     putData("pagetitle", "用户登录 - " + site.getSitename());
/*  44 */     putData("keywords", "用户登录," + site.getSitename());
/*     */ 
/*  46 */     this.request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  48 */     String username = this.request.getParameter("username");
/*  49 */     String password = this.request.getParameter("password");
/*  50 */     String validcode = this.request.getParameter("validcode");
/*     */ 
/*  52 */     String remember = this.request.getParameter("remember");
/*     */ 
/*  54 */     if (this.logger.isDebugEnabled()) {
/*  55 */       this.logger.debug("login action is " + this.action);
/*     */     }
/*     */ 
/*  59 */     if ("login".equals(this.action)) {
/*  60 */       login(username, password, validcode, remember);
/*  61 */     } else if ("ajaxlogin".equals(this.action)) {
/*  62 */       ajaxlogin(username, password, validcode, remember);
/*     */     } else {
/*  64 */       Member memberLogin = UserServiceFactory.getUserService().getCurrentMember();
/*  65 */       if (memberLogin != null) {
/*  66 */         showSuccess("您已经登录!", "会员中心首页", "member_index.html");
/*  67 */         return;
/*     */       }
/*  69 */       String forward = this.request.getParameter("forward");
/*  70 */       forward = StringUtil.toUTF8(forward);
/*  71 */       putData("forward", forward);
/*  72 */       setPageName("login");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ajaxlogin(String username, String password, String validcode, String remember)
/*     */   {
/*  85 */     String json = "";
/*  86 */     if (validcode(validcode) == 1)
/*     */     {
/*  88 */       int result = this.memberManager.login(username, password);
/*  89 */       if (result == 1)
/*     */       {
/*  91 */         if ((remember != null) && (remember.equals("1"))) {
/*  92 */           String cookieValue = EncryptionUtil1.authcode("{username:\"" + username + "\",password:\"" + StringUtil.md5(password) + "\"}", "ENCODE", "", 0);
/*  93 */           HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "MJUser", cookieValue, 20160);
/*     */         }
/*     */       }
/*  96 */       String forward = this.request.getParameter("forward");
/*  97 */       json = "{result:" + result + ",forward:'" + forward + "'}";
/*     */     }
/*     */     else {
/* 100 */       json = "{result:-1}";
/*     */     }
/* 102 */     showJson(json);
/*     */   }
/*     */ 
/*     */   private void login(String username, String password, String validcode, String remember)
/*     */   {
/* 113 */     if (this.logger.isDebugEnabled()) {
/* 114 */       this.logger.debug("do login");
/*     */     }
/*     */ 
/* 117 */     if (validcode(validcode) == 1)
/*     */     {
/* 119 */       int result = this.memberManager.login(username, password);
/*     */ 
/* 121 */       if (this.logger.isDebugEnabled()) {
/* 122 */         this.logger.debug("login result is " + result);
/*     */       }
/*     */ 
/* 126 */       if (result == 1)
/*     */       {
/* 128 */         if ((remember != null) && (remember.equals("1"))) {
/* 129 */           String cookieValue = EncryptionUtil1.authcode("{username:\"" + username + "\",password:\"" + StringUtil.md5(password) + "\"}", "ENCODE", "", 0);
/* 130 */           HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "JavaShopUser", cookieValue, 20160);
/*     */         }
/* 132 */         String forward = this.request.getParameter("forward");
/* 133 */         if ((forward != null) && (!forward.equals(""))) {
/* 134 */           if ((forward.contains("member_login")) || (forward.contains("member_register")) || (forward.contains("logout"))) {
/* 135 */             forward = "/";
/*     */           }
/* 137 */           String message = this.request.getParameter("message");
/* 138 */           putUrl(message, forward);
/* 139 */           showSuccess("登录成功", message, forward);
/*     */         } else {
/* 141 */           showSuccess("登录成功", "会员中心首页", "member_index.html");
/*     */         }
/*     */       } else {
/* 144 */         showError("用户名或密码输入错误");
/*     */       }
/*     */     } else {
/* 147 */       showError("验证码输入错误");
/*     */     }
/*     */   }
/*     */ 
/*     */   private int validcode(String validcode)
/*     */   {
/* 159 */     if (validcode == null) {
/* 160 */       return 0;
/*     */     }
/*     */ 
/* 163 */     String code = (String)ThreadContextHolder.getSessionContext().getAttribute("valid_codememberlogin");
/* 164 */     if (code == null) {
/* 165 */       return 0;
/*     */     }
/* 167 */     if (!code.equals(validcode)) {
/* 168 */       return 0;
/*     */     }
/*     */ 
/* 172 */     return 1;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 176 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 180 */     this.memberManager = memberManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberLoginWidget
 * JD-Core Version:    0.6.0
 */