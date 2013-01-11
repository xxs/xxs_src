/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.RequestWrapper;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public abstract class AbstractParser extends RequestWrapper
/*    */ {
/*    */   protected String uri;
/*    */   protected HttpServletResponse httpResponse;
/*    */   protected HttpServletRequest httpRequest;
/*    */ 
/*    */   public AbstractParser(Request request)
/*    */   {
/* 27 */     super(request);
/*    */   }
/*    */ 
/*    */   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 43 */     this.uri = uri;
/* 44 */     this.httpRequest = httpRequest;
/* 45 */     this.httpResponse = httpResponse;
/* 46 */     return parse();
/*    */   }
/*    */ 
/*    */   protected Response parse()
/*    */   {
/* 56 */     Response response = super.execute(this.uri, this.httpResponse, this.httpRequest);
/* 57 */     return parse(response);
/*    */   }
/*    */ 
/*    */   protected abstract Response parse(Response paramResponse);
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.AbstractParser
 * JD-Core Version:    0.6.0
 */