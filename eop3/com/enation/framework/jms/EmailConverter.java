/*    */ package com.enation.framework.jms;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.jms.JMSException;
/*    */ import javax.jms.Message;
/*    */ import javax.jms.ObjectMessage;
/*    */ import javax.jms.Session;
/*    */ import org.apache.activemq.command.ActiveMQObjectMessage;
/*    */ import org.springframework.jms.support.converter.MessageConverter;
/*    */ 
/*    */ public class EmailConverter
/*    */   implements MessageConverter
/*    */ {
/*    */   public Message toMessage(Object obj, Session session)
/*    */     throws JMSException
/*    */   {
/* 24 */     if ((obj instanceof EmailModel)) {
/* 25 */       ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage)session.createObjectMessage();
/* 26 */       Map map = new HashMap();
/* 27 */       map.put("EmailModel", (EmailModel)obj);
/* 28 */       objMsg.setObjectProperty("Map", map);
/* 29 */       return objMsg;
/*    */     }
/* 31 */     throw new JMSException("Object:[" + obj + "] is not Member");
/*    */   }
/*    */ 
/*    */   public Object fromMessage(Message msg)
/*    */     throws JMSException
/*    */   {
/* 42 */     if ((msg instanceof ObjectMessage)) {
/* 43 */       return ((Map)((ObjectMessage)msg).getObjectProperty("Map")).get("EmailModel");
/*    */     }
/* 45 */     throw new JMSException("Msg:[" + msg + "] is not Map");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.jms.EmailConverter
 * JD-Core Version:    0.6.0
 */