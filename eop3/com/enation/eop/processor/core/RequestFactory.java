/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ public abstract class RequestFactory
/*    */ {
/*    */   public static Request getRequest(int model)
/*    */   {
/* 19 */     Request request = null;
/*    */ 
/* 21 */     if (model == 1) {
/* 22 */       request = new RemoteRequest();
/*    */     }
/* 24 */     if (model == 0) {
/* 25 */       request = new LocalRequest();
/*    */     }
/* 27 */     return request;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.RequestFactory
 * JD-Core Version:    0.6.0
 */