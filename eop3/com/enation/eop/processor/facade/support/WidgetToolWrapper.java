/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.AbstractFacadePageWrapper;
/*    */ import com.enation.eop.processor.FacadePage;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ 
/*    */ public class WidgetToolWrapper extends AbstractFacadePageWrapper
/*    */ {
/*    */   private static final String toolsElement = "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>";
/*    */ 
/*    */   public WidgetToolWrapper(FacadePage page, Request request)
/*    */   {
/* 22 */     super(page, request);
/*    */   }
/*    */ 
/*    */   protected Response wrap(Response response) {
/* 26 */     String content = response.getContent();
/* 27 */     content = content.replaceAll("</body>", "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>");
/* 28 */     content = content.replaceAll("</BODY>", "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>");
/*    */ 
/* 30 */     response.setContent(content);
/* 31 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.WidgetToolWrapper
 * JD-Core Version:    0.6.0
 */