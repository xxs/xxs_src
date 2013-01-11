/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.opensymphony.xwork2.ActionInvocation;
/*    */ import com.opensymphony.xwork2.interceptor.Interceptor;
/*    */ 
/*    */ public class EopPermissionInterceptor
/*    */   implements Interceptor
/*    */ {
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String intercept(ActionInvocation inv)
/*    */     throws Exception
/*    */   {
/* 25 */     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
/* 26 */     if (userid.intValue() != 1) {
/* 27 */       return "error";
/*    */     }
/*    */ 
/* 30 */     String result = inv.invoke();
/* 31 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.EopPermissionInterceptor
 * JD-Core Version:    0.6.0
 */