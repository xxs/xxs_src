/*    */ package com.enation.eop.processor.backend;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class LogoutProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 21 */     WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/* 22 */     Response response = new StringResponse();
/* 23 */     sessonContext.removeAttribute("usercontext");
/* 24 */     response.setContent("<script>location.href='index.jsp'</script>");
/* 25 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.LogoutProcessor
 * JD-Core Version:    0.6.0
 */