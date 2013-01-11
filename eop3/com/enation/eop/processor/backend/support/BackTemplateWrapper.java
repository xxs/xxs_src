/*    */ package com.enation.eop.processor.backend.support;
/*    */ 
/*    */ import com.enation.eop.processor.AbstractFacadePagetParser;
/*    */ import com.enation.eop.processor.FacadePage;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.sdk.utils.JspUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class BackTemplateWrapper extends AbstractFacadePagetParser
/*    */ {
/*    */   public BackTemplateWrapper(FacadePage page, Request request)
/*    */   {
/* 18 */     super(page, request);
/*    */   }
/*    */ 
/*    */   protected Response parse(Response response)
/*    */   {
/* 23 */     this.httpRequest.setAttribute("content", response.getContent());
/* 24 */     String content = JspUtil.getJspOutput("/admin/main_frame.jsp", this.httpRequest, this.httpResponse);
/* 25 */     response.setContent(content);
/* 26 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.support.BackTemplateWrapper
 * JD-Core Version:    0.6.0
 */