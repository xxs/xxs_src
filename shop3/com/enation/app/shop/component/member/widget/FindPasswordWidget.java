/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.jms.EmailModel;
/*     */ import com.enation.framework.jms.EmailProducer;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.EncryptionUtil1;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_findpassword")
/*     */ @Scope("prototype")
/*     */ public class FindPasswordWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private EmailProducer mailMessageProducer;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  42 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  44 */     putData("pagetitle", "忘记密码 - " + site.getSitename());
/*  45 */     putData("keywords", "忘记密码," + site.getSitename());
/*     */ 
/*  47 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  48 */     String action = request.getParameter("action");
/*  49 */     showMenu(false);
/*  50 */     if ("find".equals(action))
/*  51 */       find();
/*  52 */     else if ("restorepwd".equals(action))
/*  53 */       restorePwd();
/*     */   }
/*     */ 
/*     */   private void find()
/*     */   {
/*  58 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  59 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  60 */     String email = request.getParameter("email");
/*  61 */     if ((email == null) || (!StringUtil.validEmail(email))) {
/*  62 */       showError("请输入正确的邮箱地址");
/*  63 */       return;
/*     */     }
/*  65 */     Member member = this.memberManager.getMemberByEmail(email);
/*  66 */     if (member == null) {
/*  67 */       showError("[" + email + "]不存在!");
/*  68 */       return;
/*     */     }
/*  70 */     String domain = RequestUtil.getDomain();
/*  71 */     String initCode = member.getMember_id() + "," + member.getRegtime();
/*  72 */     String edit_url = domain + "/modifypassword.html?s=" + EncryptionUtil1.authcode(initCode, "ENCODE", "", 0);
/*     */ 
/*  74 */     EmailModel emailModel = new EmailModel();
/*  75 */     emailModel.getData().put("logo", site.getLogofile());
/*  76 */     emailModel.getData().put("sitename", site.getSitename());
/*  77 */     emailModel.getData().put("username", member.getUname());
/*  78 */     emailModel.getData().put("checkurl", edit_url);
/*  79 */     emailModel.setTitle("找回您的登录密码--" + site.getSitename());
/*  80 */     emailModel.setTo(member.getEmail());
/*  81 */     emailModel.setTemplate("findpass_email_template.html");
/*  82 */     this.mailMessageProducer.send(emailModel);
/*     */ 
/*  84 */     this.memberManager.updateFindCode(member.getMember_id(), DateUtil.getDateline() + "");
/*  85 */     showSuccess("请登录" + email + "查收邮件并完成密码修改。", "登录页面", "login.html");
/*     */   }
/*     */ 
/*     */   public void restorePwd()
/*     */   {
/* 103 */     Object flag = ThreadContextHolder.getSessionContext().getAttribute("can_find_pwd");
/* 104 */     if ("yes".equals(flag)) {
/* 105 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 106 */       String memberid = request.getParameter("memberid");
/* 107 */       String password = request.getParameter("password");
/* 108 */       this.memberManager.updatePassword(Integer.valueOf(memberid), password);
/* 109 */       ThreadContextHolder.getSessionContext().removeAttribute("can_find_pwd");
/* 110 */       showSuccess("密码成功更新", "登录页面", "member_login.html");
/*     */     }
/*     */     else {
/* 113 */       showError("非法操作!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 119 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 123 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public EmailProducer getMailMessageProducer()
/*     */   {
/* 128 */     return this.mailMessageProducer;
/*     */   }
/*     */ 
/*     */   public void setMailMessageProducer(EmailProducer mailMessageProducer)
/*     */   {
/* 133 */     this.mailMessageProducer = mailMessageProducer;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.FindPasswordWidget
 * JD-Core Version:    0.6.0
 */