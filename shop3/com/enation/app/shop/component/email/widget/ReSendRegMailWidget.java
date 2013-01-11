/*     */ package com.enation.app.shop.component.email.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.jms.EmailModel;
/*     */ import com.enation.framework.jms.EmailProducer;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.EncryptionUtil1;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import java.util.Map;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.mail.javamail.JavaMailSender;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("resend_regmail")
/*     */ @Scope("prototype")
/*     */ public class ReSendRegMailWidget extends AbstractWidget
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private JavaMailSender mailSender;
/*     */   private EmailProducer mailMessageProducer;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*     */     try
/*     */     {
/*  36 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  37 */       if (member == null) {
/*  38 */         showErrorJson("请您先登录再重新发送激活邮件!");
/*  39 */         return;
/*     */       }
/*  41 */       member = this.memberManager.get(member.getMember_id());
/*  42 */       if (member == null) {
/*  43 */         showErrorJson("用户不存在,请您先登录再重新发送激活邮件!");
/*  44 */         return;
/*     */       }
/*  46 */       if ((member.getLast_send_email() != null) && (System.currentTimeMillis() / 1000L - member.getLast_send_email().intValue() < 7200L)) {
/*  47 */         showErrorJson("对不起，两小时之内只能重新发送一次激活邮件!");
/*  48 */         return;
/*     */       }
/*     */ 
/*  52 */       EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  54 */       String domain = RequestUtil.getDomain();
/*     */ 
/*  56 */       String checkurl = domain + "/memberemailcheck.html?s=" + EncryptionUtil1.authcode(new StringBuilder().append(member.getMember_id()).append(",").append(member.getRegtime()).toString(), "ENCODE", "", 0);
/*  57 */       EmailModel emailModel = new EmailModel();
/*  58 */       emailModel.getData().put("username", member.getUname());
/*  59 */       emailModel.getData().put("checkurl", checkurl);
/*  60 */       emailModel.getData().put("sitename", site.getSitename());
/*  61 */       emailModel.getData().put("logo", site.getLogofile());
/*  62 */       emailModel.getData().put("domain", domain);
/*     */ 
/*  64 */       if (this.memberPointManger.checkIsOpen("email_check")) {
/*  65 */         int point = this.memberPointManger.getItemPoint("email_check_num");
/*  66 */         int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
/*  67 */         emailModel.getData().put("point", Integer.valueOf(point));
/*  68 */         emailModel.getData().put("mp", Integer.valueOf(mp));
/*     */       }
/*     */ 
/*  71 */       emailModel.setTitle(member.getUname() + "您好，" + site.getSitename() + "会员注册成功!");
/*  72 */       emailModel.setTo(member.getEmail());
/*  73 */       emailModel.setTemplate("reg_email_template.html");
/*  74 */       this.mailMessageProducer.send(emailModel);
/*  75 */       member.setLast_send_email(Integer.valueOf(DateUtil.getDateline()));
/*  76 */       this.memberManager.edit(member);
/*  77 */       showSuccessJson("激活邮件发送成功，请登录您的邮箱 " + member.getEmail() + " 进行查收！");
/*     */     } catch (RuntimeException e) {
/*  79 */       showErrorJson(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/*  90 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/*  94 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public JavaMailSender getMailSender() {
/*  98 */     return this.mailSender;
/*     */   }
/*     */ 
/*     */   public void setMailSender(JavaMailSender mailSender) {
/* 102 */     this.mailSender = mailSender;
/*     */   }
/*     */ 
/*     */   public EmailProducer getMailMessageProducer() {
/* 106 */     return this.mailMessageProducer;
/*     */   }
/*     */ 
/*     */   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
/* 110 */     this.mailMessageProducer = mailMessageProducer;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger() {
/* 114 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger) {
/* 118 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.email.widget.ReSendRegMailWidget
 * JD-Core Version:    0.6.0
 */