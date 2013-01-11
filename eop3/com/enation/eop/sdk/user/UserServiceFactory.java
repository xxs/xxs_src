/*    */ package com.enation.eop.sdk.user;
/*    */ 
/*    */ import com.enation.eop.sdk.user.impl.UserServiceImpl;
/*    */ 
/*    */ public final class UserServiceFactory
/*    */ {
/* 12 */   public static int isTest = 0;
/*    */   private static IUserService userService;
/*    */ 
/*    */   public static void set(IUserService _userService)
/*    */   {
/* 15 */     userService = _userService;
/*    */   }
/*    */ 
/*    */   public static IUserService getUserService() {
/* 19 */     if (userService != null) {
/* 20 */       return userService;
/*    */     }
/* 22 */     return new UserServiceImpl();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.user.UserServiceFactory
 * JD-Core Version:    0.6.0
 */