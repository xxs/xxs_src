/*    */ package com.enation.app.shop.component.email.plugin;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberRegisterEvent;
/*    */ import com.enation.app.shop.core.service.IMemberPointManger;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.jms.EmailModel;
/*    */ import com.enation.framework.jms.EmailProducer;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.EncryptionUtil1;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberRegisterSendEmailPlugin extends AutoRegisterPlugin
/*    */   implements IMemberRegisterEvent
/*    */ {
/*    */   private EmailProducer mailMessageProducer;
/*    */   private IMemberPointManger memberPointManger;
/*    */ 
/*    */   public void onRegister(Member member)
/*    */   {
/* 33 */     sendRegEmail(member);
/*    */   }
/*    */ 
/*    */   private void sendRegEmail(Member member)
/*    */   {
/* 38 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 40 */     String domain = RequestUtil.getDomain();
/*    */ 
/* 42 */     String checkurl = domain + "/memberemailcheck.html?s=" + EncryptionUtil1.authcode(new StringBuilder().append(member.getMember_id()).append(",").append(member.getRegtime()).toString(), "ENCODE", "", 0);
/* 43 */     EmailModel emailModel = new EmailModel();
/* 44 */     emailModel.getData().put("username", member.getUname());
/* 45 */     emailModel.getData().put("checkurl", checkurl);
/* 46 */     emailModel.getData().put("sitename", site.getSitename());
/* 47 */     emailModel.getData().put("logo", site.getLogofile());
/* 48 */     emailModel.getData().put("domain", domain);
/*    */ 
/* 50 */     if (this.memberPointManger.checkIsOpen("email_check")) {
/* 51 */       int point = this.memberPointManger.getItemPoint("email_check_num");
/* 52 */       int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
/* 53 */       emailModel.getData().put("point", Integer.valueOf(point));
/* 54 */       emailModel.getData().put("mp", Integer.valueOf(mp));
/*    */     }
/*    */ 
/* 57 */     emailModel.setTitle(member.getUname() + "您好，" + site.getSitename() + "会员注册成功!");
/* 58 */     emailModel.setTo(member.getEmail());
/* 59 */     emailModel.setTemplate("reg_email_template.html");
/*    */ 
/* 61 */     this.mailMessageProducer.send(emailModel);
/*    */   }
/*    */ 
/*    */   public EmailProducer getMailMessageProducer()
/*    */   {
/* 66 */     return this.mailMessageProducer;
/*    */   }
/*    */ 
/*    */   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
/* 70 */     this.mailMessageProducer = mailMessageProducer;
/*    */   }
/*    */ 
/*    */   public IMemberPointManger getMemberPointManger() {
/* 74 */     return this.memberPointManger;
/*    */   }
/*    */ 
/*    */   public void setMemberPointManger(IMemberPointManger memberPointManger) {
/* 78 */     this.memberPointManger = memberPointManger;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.email.plugin.MemberRegisterSendEmailPlugin
 * JD-Core Version:    0.6.0
 */