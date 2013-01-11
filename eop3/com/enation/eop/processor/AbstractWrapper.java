/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.RequestWrapper;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public abstract class AbstractWrapper extends RequestWrapper
/*    */ {
/*    */   protected String uri;
/*    */   protected HttpServletResponse httpResponse;
/*    */   protected HttpServletRequest httpRequest;
/*    */ 
/*    */   public AbstractWrapper(Request request)
/*    */   {
/* 22 */     super(request);
/*    */   }
/*    */ 
/*    */   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 32 */     this.uri = uri;
/* 33 */     this.httpRequest = httpRequest;
/* 34 */     this.httpResponse = httpResponse;
/*    */ 
/* 36 */     return wrap();
/*    */   }
/*    */ 
/*    */   protected Response wrap() {
/* 40 */     Response response = super.execute(this.uri, this.httpResponse, this.httpRequest);
/* 41 */     return wrap(response);
/*    */   }
/*    */ 
/*    */   protected abstract Response wrap(Response paramResponse);
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.AbstractWrapper
 * JD-Core Version:    0.6.0
 */