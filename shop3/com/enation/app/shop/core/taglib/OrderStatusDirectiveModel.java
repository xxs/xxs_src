/*    */ package com.enation.app.shop.core.taglib;
/*    */ 
/*    */ import com.enation.app.shop.core.service.OrderStatus;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OrderStatusDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 21 */     int status = StringUtil.toInt(params.get("status").toString(), true);
/* 22 */     String type = params.get("type").toString();
/* 23 */     if ("order".equals(type)) {
/* 24 */       String text = OrderStatus.getOrderStatusText(status);
/* 25 */       env.getOut().write(text);
/*    */     }
/* 27 */     if ("pay".equals(type)) {
/* 28 */       String text = OrderStatus.getPayStatusText(status);
/* 29 */       env.getOut().write(text);
/*    */     }
/* 31 */     if ("ship".equals(type)) {
/* 32 */       String text = OrderStatus.getShipStatusText(status);
/* 33 */       env.getOut().write(text);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 39 */     System.out.println(0.1000000000000001D);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.taglib.OrderStatusDirectiveModel
 * JD-Core Version:    0.6.0
 */