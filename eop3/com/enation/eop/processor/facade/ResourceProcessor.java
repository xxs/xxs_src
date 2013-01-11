/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.InputStreamResponse;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.framework.resource.impl.ResourceBuilder;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ResourceProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 27 */     String id = httpRequest.getParameter("id");
/* 28 */     String type = httpRequest.getParameter("type");
/*    */ 
/* 30 */     String content = null;
/*    */ 
/* 32 */     if ("javascript".equals(type)) {
/* 33 */       content = ResourceBuilder.getScript(id);
/*    */     }
/*    */ 
/* 36 */     if ("css".equals(type)) {
/* 37 */       content = ResourceBuilder.getCss(id);
/*    */     }
/*    */ 
/* 41 */     if (content != null) {
/* 42 */       InputStream in = null;
/*    */       try {
/* 44 */         in = new ByteArrayInputStream(content.getBytes("UTF-8"));
/*    */       } catch (UnsupportedEncodingException e) {
/* 46 */         e.printStackTrace();
/*    */       }
/* 48 */       Response response = new InputStreamResponse(in);
/* 49 */       if ("javscript".equals(type)) {
/* 50 */         response.setContentType("application/x-javascript");
/*    */       }
/* 52 */       if ("css".equals(type)) {
/* 53 */         response.setContentType("text/css");
/*    */       }
/* 55 */       return response;
/*    */     }
/* 57 */     Response response = new StringResponse();
/* 58 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.ResourceProcessor
 * JD-Core Version:    0.6.0
 */