/*    */ package com.enation.app.shop.component.email.plugin;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.model.support.CartItem;
/*    */ import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.jms.EmailModel;
/*    */ import com.enation.framework.jms.EmailProducer;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.DateUtil;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class OrderEmailPlugin extends AutoRegisterPlugin
/*    */   implements IAfterOrderCreateEvent
/*    */ {
/*    */   private EmailProducer mailMessageProducer;
/*    */ 
/*    */   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
/*    */   {
/* 41 */     IUserService userService = UserServiceFactory.getUserService();
/* 42 */     Member member = userService.getCurrentMember();
/* 43 */     if (member != null)
/*    */     {
/* 46 */       EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 48 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*    */ 
/* 50 */       String domain = RequestUtil.getDomain();
/*    */ 
/* 52 */       EmailModel emailModel = new EmailModel();
/* 53 */       emailModel.getData().put("username", member.getUname());
/* 54 */       emailModel.getData().put("sn", order.getSn());
/* 55 */       emailModel.getData().put("createtime", DateUtil.toString(order.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
/* 56 */       emailModel.getData().put("sitename", site.getSitename());
/* 57 */       emailModel.getData().put("logo", site.getLogofile());
/* 58 */       emailModel.getData().put("domain", domain);
/*    */ 
/* 60 */       emailModel.setTitle("订单提交成功--" + site.getSitename());
/* 61 */       emailModel.setTo(member.getEmail());
/* 62 */       emailModel.setTemplate("order_create_email_template.html");
/* 63 */       this.mailMessageProducer.send(emailModel);
/*    */     }
/*    */   }
/*    */ 
/*    */   public EmailProducer getMailMessageProducer()
/*    */   {
/* 69 */     return this.mailMessageProducer;
/*    */   }
/*    */ 
/*    */   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
/* 73 */     this.mailMessageProducer = mailMessageProducer;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.email.plugin.OrderEmailPlugin
 * JD-Core Version:    0.6.0
 */