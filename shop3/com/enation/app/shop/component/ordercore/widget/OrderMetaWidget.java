/*    */ package com.enation.app.shop.component.ordercore.widget;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.app.shop.core.service.IOrderMetaManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("order_meta")
/*    */ @Scope("prototype")
/*    */ public class OrderMetaWidget extends AbstractWidget
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */   private IOrderMetaManager orderMetaManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 30 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 31 */     String sn = parseSn(request.getServletPath());
/* 32 */     Order order = this.orderManager.get(sn);
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   private static String parseSn(String url)
/*    */   {
/* 43 */     String pattern = "(.*)orderdetail_(\\w+)(.*)";
/* 44 */     String value = null;
/* 45 */     Pattern p = Pattern.compile(pattern, 34);
/* 46 */     Matcher m = p.matcher(url);
/* 47 */     if (m.find()) {
/* 48 */       value = m.replaceAll("$2");
/*    */     }
/* 50 */     return value;
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager() {
/* 54 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 58 */     this.orderManager = orderManager;
/*    */   }
/*    */ 
/*    */   public IOrderMetaManager getOrderMetaManager() {
/* 62 */     return this.orderMetaManager;
/*    */   }
/*    */ 
/*    */   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
/* 66 */     this.orderMetaManager = orderMetaManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.widget.OrderMetaWidget
 * JD-Core Version:    0.6.0
 */