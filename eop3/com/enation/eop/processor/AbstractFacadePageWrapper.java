/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Request;
/*    */ 
/*    */ public abstract class AbstractFacadePageWrapper extends AbstractWrapper
/*    */ {
/*    */   protected FacadePage page;
/*    */ 
/*    */   public AbstractFacadePageWrapper(FacadePage page, Request request)
/*    */   {
/* 23 */     super(request);
/* 24 */     this.page = page;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.AbstractFacadePageWrapper
 * JD-Core Version:    0.6.0
 */