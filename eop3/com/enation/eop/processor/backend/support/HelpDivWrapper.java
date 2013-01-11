/*    */ package com.enation.eop.processor.backend.support;
/*    */ 
/*    */ import com.enation.eop.processor.AbstractFacadePagetParser;
/*    */ import com.enation.eop.processor.FacadePage;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class HelpDivWrapper extends AbstractFacadePagetParser
/*    */ {
/*    */   public HelpDivWrapper(FacadePage page, Request request)
/*    */   {
/* 14 */     super(page, request);
/*    */   }
/*    */ 
/*    */   protected Response parse(Response response)
/*    */   {
/* 19 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 20 */     String content = response.getContent();
/* 21 */     content = content + "<div id=\"HelpCtn\" class=\"popup-info-box\"><div class=\"bl\"><div class=\"br\">";
/* 22 */     content = content + "<div class=\"bd user-info\"><span id=\"HelpClose\" class=\"closebtn\" ></span>";
/* 23 */     content = content + "<span id=\"HelpBody\"></span>";
/* 24 */     content = content + "</div>";
/* 25 */     content = content + "</div>";
/* 26 */     content = content + "</div>";
/* 27 */     content = content + "<div class=\"bt\">";
/* 28 */     content = content + "<div class=\"corner bt-l\"></div>";
/* 29 */     content = content + "<div class=\"mid\"></div>";
/* 30 */     content = content + "<div class=\"corner bt-r\"></div>";
/* 31 */     content = content + "</div>";
/* 32 */     content = content + "</div>";
/* 33 */     response.setContent(content);
/* 34 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.support.HelpDivWrapper
 * JD-Core Version:    0.6.0
 */