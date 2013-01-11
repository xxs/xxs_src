/*    */ package com.enation.app.shop.core.service;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.EncryptionUtil1;
/*    */ import com.enation.framework.util.HttpUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URLDecoder;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class AutoLoginProcessor
/*    */   implements Processor
/*    */ {
/*    */   private IMemberManager memberManager;
/*    */ 
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/*    */     try
/*    */     {
/* 26 */       String url = httpRequest.getRequestURI();
/* 27 */       if (url != null) {
/* 28 */         url = url.toLowerCase();
/* 29 */         if ((url.endsWith("/")) || (url.endsWith(".html")) || (url.endsWith(".do")))
/*    */         {
/* 31 */           if (UserServiceFactory.getUserService().getCurrentMember() == null) {
/* 32 */             String cookieValue = HttpUtil.getCookieValue(ThreadContextHolder.getHttpRequest(), "JavaShopUser");
/*    */ 
/* 34 */             if ((cookieValue != null) && (!cookieValue.equals(""))) {
/* 35 */               cookieValue = URLDecoder.decode(cookieValue, "UTF-8");
/*    */ 
/* 37 */               cookieValue = EncryptionUtil1.authcode(cookieValue, "DECODE", "", 0);
/*    */ 
/* 39 */               if ((cookieValue != null) && (!cookieValue.equals("")))
/*    */               {
/* 41 */                 Map map = (Map)JSONObject.toBean(JSONObject.fromObject(cookieValue), Map.class);
/*    */ 
/* 44 */                 if (map != null) {
/* 45 */                   String username = map.get("username").toString();
/*    */ 
/* 47 */                   String password = map.get("password").toString();
/*    */ 
/* 49 */                   int result = this.memberManager.loginWithCookie(username, password);
/*    */ 
/* 51 */                   if (result != 1)
/* 52 */                     HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "JavaShopUser", "", 0);
/*    */                 }
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 67 */     this.memberManager = memberManager;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 75 */     String str1 = "db21SPFxwCWgshcLqsIxFzS9YeEusB/qzRdC1OKk2R47uLdLCuai1BPUMh5xNJhSkwuu1v09po2qNuLPsWjLg/+p4aaeZZ70LyCEGwcwMZGuHCY9zmKDv1sXBZKQ6OXjFV04MQ";
/* 76 */     System.out.println(EncryptionUtil1.authcode(str1, "DECODE", "", 0));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.AutoLoginProcessor
 * JD-Core Version:    0.6.0
 */