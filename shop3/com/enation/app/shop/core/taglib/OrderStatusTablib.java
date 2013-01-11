/*    */ package com.enation.app.shop.core.taglib;
/*    */ 
/*    */ import com.enation.app.shop.core.service.OrderStatus;
/*    */ import com.enation.framework.taglib.EnationTagSupport;
/*    */ import javax.servlet.jsp.JspException;
/*    */ 
/*    */ public class OrderStatusTablib extends EnationTagSupport
/*    */ {
/*    */   private int status;
/*    */   private String type;
/*    */ 
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 15 */     if ("order".equals(this.type)) {
/* 16 */       String text = OrderStatus.getOrderStatusText(this.status);
/* 17 */       print(text);
/*    */     }
/* 19 */     if ("pay".equals(this.type)) {
/* 20 */       String text = OrderStatus.getPayStatusText(this.status);
/* 21 */       print(text);
/*    */     }
/* 23 */     if ("ship".equals(this.type)) {
/* 24 */       String text = OrderStatus.getShipStatusText(this.status);
/* 25 */       print(text);
/*    */     }
/* 27 */     return 1;
/*    */   }
/*    */ 
/*    */   public int getStatus() {
/* 31 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void setStatus(int status) {
/* 35 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 39 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 43 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.taglib.OrderStatusTablib
 * JD-Core Version:    0.6.0
 */