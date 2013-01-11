/*    */ package com.enation.eop.processor.backend.support;
/*    */ 
/*    */ import com.enation.eop.processor.AbstractFacadePagetParser;
/*    */ import com.enation.eop.processor.FacadePage;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ 
/*    */ public class GetPointJsWrapper extends AbstractFacadePagetParser
/*    */ {
/*    */   public GetPointJsWrapper(FacadePage page, Request request)
/*    */   {
/* 15 */     super(page, request);
/*    */   }
/*    */ 
/*    */   protected Response parse(Response response) {
/* 19 */     String content = response.getContent();
/* 20 */     content = content + "<script>$(function(){Eop.Point.init();});</script>";
/* 21 */     response.setContent(content);
/* 22 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.support.GetPointJsWrapper
 * JD-Core Version:    0.6.0
 */