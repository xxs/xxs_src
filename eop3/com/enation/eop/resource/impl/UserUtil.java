/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.processor.core.EopException;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ 
/*    */ public final class UserUtil
/*    */ {
/*    */   private static IUserService userService;
/*    */ 
/*    */   public static void validUser(Integer userid)
/*    */   {
/* 20 */     userService = UserServiceFactory.getUserService();
/*    */ 
/* 22 */     if (!userid.equals(userService.getCurrentUserId()))
/* 23 */       throw new EopException("非法操作");
/*    */   }
/*    */ 
/*    */   public IUserService getUserService()
/*    */   {
/* 29 */     return userService;
/*    */   }
/*    */ 
/*    */   public void setUserService(IUserService userService) {
/* 33 */     userService = userService;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.UserUtil
 * JD-Core Version:    0.6.0
 */