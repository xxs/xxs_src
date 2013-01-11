/*    */ package com.enation.eop.sdk.user.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserContext;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public final class UserServiceImpl
/*    */   implements IUserService
/*    */ {
/*    */   private UserContext userContext;
/* 14 */   protected static final Logger logger = Logger.getLogger(UserServiceImpl.class);
/*    */ 
/*    */   public UserServiceImpl()
/*    */   {
/* 21 */     WebSessionContext webSessionContext = ThreadContextHolder.getSessionContext();
/*    */ 
/* 23 */     this.userContext = ((UserContext)webSessionContext.getAttribute("usercontext"));
/*    */   }
/*    */ 
/*    */   public Integer getCurrentSiteId()
/*    */   {
/* 32 */     if (isUserLoggedIn()) {
/* 33 */       return this.userContext.getSiteid();
/*    */     }
/* 35 */     throw new IllegalStateException("The current user is not logged in.");
/*    */   }
/*    */ 
/*    */   public Integer getCurrentUserId()
/*    */   {
/* 40 */     if (isUserLoggedIn()) {
/* 41 */       return this.userContext.getUserid();
/*    */     }
/* 43 */     throw new IllegalStateException("The current user is not logged in.");
/*    */   }
/*    */ 
/*    */   public boolean isUserLoggedIn()
/*    */   {
/* 49 */     return this.userContext != null;
/*    */   }
/*    */ 
/*    */   public Integer getCurrentManagerId()
/*    */   {
/* 56 */     if (isUserLoggedIn()) {
/* 57 */       return this.userContext.getManagerid();
/*    */     }
/* 59 */     throw new IllegalStateException("The current user is not logged in.");
/*    */   }
/*    */ 
/*    */   public Member getCurrentMember()
/*    */   {
/* 64 */     Member member = (Member)ThreadContextHolder.getSessionContext().getAttribute("curr_member");
/*    */ 
/* 86 */     return member;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.user.impl.UserServiceImpl
 * JD-Core Version:    0.6.0
 */