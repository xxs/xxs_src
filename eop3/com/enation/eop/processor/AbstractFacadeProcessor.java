/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.RequestFactory;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public abstract class AbstractFacadeProcessor
/*    */   implements Processor
/*    */ {
/*    */   protected FacadePage page;
/*    */   protected HttpServletRequest httpRequest;
/*    */   protected HttpServletResponse httpResponse;
/*    */   protected int mode;
/*    */   protected Request request;
/*    */ 
/*    */   public AbstractFacadeProcessor(FacadePage page)
/*    */   {
/* 29 */     this.page = page;
/*    */   }
/*    */ 
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 41 */     this.mode = mode;
/* 42 */     this.httpRequest = httpRequest;
/* 43 */     this.httpResponse = httpResponse;
/* 44 */     this.request = RequestFactory.getRequest(mode);
/*    */ 
/* 46 */     return process();
/*    */   }
/*    */ 
/*    */   protected abstract Response process();
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.AbstractFacadeProcessor
 * JD-Core Version:    0.6.0
 */