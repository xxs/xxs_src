/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.JspUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class LocalRequest
/*    */   implements Request
/*    */ {
/*    */   public void setExecuteParams(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 32 */     String content = JspUtil.getJspOutput(uri, httpRequest, httpResponse);
/*    */ 
/* 34 */     Response response = new StringResponse();
/* 35 */     response.setContent(content);
/*    */ 
/* 37 */     return response;
/*    */   }
/*    */ 
/*    */   public Response execute(String uri)
/*    */   {
/* 45 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.LocalRequest
 * JD-Core Version:    0.6.0
 */