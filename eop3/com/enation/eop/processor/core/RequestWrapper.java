/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class RequestWrapper
/*    */   implements Request
/*    */ {
/* 17 */   protected Log logger = LogFactory.getLog(getClass());
/*    */   protected Request request;
/*    */ 
/*    */   public RequestWrapper(Request request)
/*    */   {
/* 26 */     this.request = request;
/*    */   }
/*    */ 
/*    */   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 38 */     return this.request.execute(uri, httpResponse, httpRequest);
/*    */   }
/*    */ 
/*    */   public Response execute(String uri)
/*    */   {
/* 46 */     return this.request.execute(uri);
/*    */   }
/*    */ 
/*    */   public Request getRequest() {
/* 50 */     return this.request;
/*    */   }
/*    */ 
/*    */   public void setExecuteParams(Map<String, String> params)
/*    */   {
/* 55 */     this.request.setExecuteParams(params);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.RequestWrapper
 * JD-Core Version:    0.6.0
 */