/*    */ package com.enation.framework.jms;
/*    */ 
/*    */ import com.enation.app.base.core.model.Smtp;
/*    */ import com.enation.app.base.core.service.ISmtpManager;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*    */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import java.io.File;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.Writer;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import org.springframework.mail.javamail.JavaMailSender;
/*    */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*    */ import org.springframework.mail.javamail.MimeMessageHelper;
/*    */ 
/*    */ public class EmailConsumer
/*    */ {
/*    */   private JavaMailSender mailSender;
/*    */   private ISmtpManager smtpManager;
/*    */ 
/*    */   public void sendEmail(EmailModel emailModel)
/*    */   {
/*    */     try
/*    */     {
/* 34 */       EopContext.setContext(emailModel.getEopContext());
/*    */ 
/* 36 */       Smtp smtp = this.smtpManager.getCurrentSmtp();
/* 37 */       JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)this.mailSender;
/* 38 */       javaMailSender.setHost(smtp.getHost());
/* 39 */       javaMailSender.setUsername(smtp.getUsername());
/* 40 */       javaMailSender.setPassword(smtp.getPassword());
/*    */ 
/* 43 */       MimeMessage message = this.mailSender.createMimeMessage();
/* 44 */       MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
/*    */ 
/* 46 */       helper.setSubject(emailModel.getTitle());
/* 47 */       helper.setTo(emailModel.getTo());
/* 48 */       helper.setFrom(smtp.getMail_from());
/*    */ 
/* 50 */       Configuration cfg = FreeMarkerUtil.getCfg();
/*    */ 
/* 52 */       String pageFolder = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/";
/* 53 */       cfg.setDirectoryForTemplateLoading(new File(pageFolder));
/*    */ 
/* 56 */       Template temp = cfg.getTemplate(emailModel.getTemplate());
/* 57 */       ByteOutputStream stream = new ByteOutputStream();
/*    */ 
/* 59 */       Writer out = new OutputStreamWriter(stream);
/* 60 */       temp.process(emailModel.getData(), out);
/*    */ 
/* 62 */       out.flush();
/* 63 */       String html = stream.toString();
/* 64 */       helper.setText(html, true);
/* 65 */       javaMailSender.send(message);
/* 66 */       this.smtpManager.sendOneMail(smtp);
/* 67 */       EopContext.remove();
/*    */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public JavaMailSender getMailSender() {
/* 74 */     return this.mailSender;
/*    */   }
/*    */ 
/*    */   public void setMailSender(JavaMailSender mailSender) {
/* 78 */     this.mailSender = mailSender;
/*    */   }
/*    */ 
/*    */   public ISmtpManager getSmtpManager() {
/* 82 */     return this.smtpManager;
/*    */   }
/*    */ 
/*    */   public void setSmtpManager(ISmtpManager smtpManager) {
/* 86 */     this.smtpManager = smtpManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.jms.EmailConsumer
 * JD-Core Version:    0.6.0
 */