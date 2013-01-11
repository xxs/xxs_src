/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsManager;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class MemberOrderModifyWidget extends AbstractMemberWidget
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */   private IGoodsManager goodsManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 32 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 33 */     String sn = parseSn(request.getServletPath());
/* 34 */     System.out.println(sn + "sn--------");
/*    */   }
/*    */ 
/*    */   private static String parseSn(String url)
/*    */   {
/* 40 */     String pattern = "(.*)order_modify_(\\w+)(.*)";
/* 41 */     String value = null;
/* 42 */     Pattern p = Pattern.compile(pattern, 34);
/* 43 */     Matcher m = p.matcher(url);
/* 44 */     if (m.find()) {
/* 45 */       value = m.replaceAll("$2");
/*    */     }
/* 47 */     return value;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 51 */     System.out.println(parseSn("/member_order_modify_20111206045320.html"));
/* 52 */     System.out.println(parseSn("/order_modify_20111206045320.html"));
/*    */   }
/*    */ 
/*    */   public IGoodsManager getGoodsManager() {
/* 56 */     return this.goodsManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 60 */     this.goodsManager = goodsManager;
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 65 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 69 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberOrderModifyWidget
 * JD-Core Version:    0.6.0
 */