/*    */ package com.enation.framework.jms;
/*    */ 
/*    */ import javax.jms.Queue;
/*    */ import org.springframework.jms.core.JmsTemplate;
/*    */ 
/*    */ public class EmailProducer
/*    */ {
/*    */   private JmsTemplate template;
/*    */   private Queue destination;
/*    */ 
/*    */   public void setTemplate(JmsTemplate template)
/*    */   {
/* 16 */     this.template = template;
/*    */   }
/*    */ 
/*    */   public void setDestination(Queue destination) {
/* 20 */     this.destination = destination;
/*    */   }
/*    */ 
/*    */   public void send(EmailModel emailModel) {
/* 24 */     this.template.convertAndSend(this.destination, emailModel);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.jms.EmailProducer
 * JD-Core Version:    0.6.0
 */