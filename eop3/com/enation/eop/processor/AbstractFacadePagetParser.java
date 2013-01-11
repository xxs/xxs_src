/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Request;
/*    */ 
/*    */ public abstract class AbstractFacadePagetParser extends AbstractParser
/*    */ {
/*    */   protected FacadePage page;
/*    */ 
/*    */   public AbstractFacadePagetParser(FacadePage page, Request request)
/*    */   {
/* 21 */     super(request);
/* 22 */     this.page = page;
/*    */   }
/*    */ 
/*    */   public FacadePage getPage() {
/* 26 */     return this.page;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.AbstractFacadePagetParser
 * JD-Core Version:    0.6.0
 */