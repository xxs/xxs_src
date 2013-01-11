/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.InputStreamResponse;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import java.io.InputStream;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class WebResourceProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 22 */     String path = httpRequest.getServletPath();
/*    */ 
/* 24 */     path = path.replaceAll("/resource/", "");
/*    */     try
/*    */     {
/* 28 */       InputStream in = getClass().getClassLoader().getResourceAsStream(path);
/* 29 */       Response response = new InputStreamResponse(in);
/*    */ 
/* 34 */       if (path.toLowerCase().endsWith(".js")) response.setContentType("application/x-javascript");
/* 35 */       if (path.toLowerCase().endsWith(".css")) response.setContentType("text/css");
/* 36 */       if (path.toLowerCase().endsWith(".jpg")) response.setContentType("image/jpeg");
/* 37 */       if (path.toLowerCase().endsWith(".gif")) response.setContentType("image/gif");
/* 38 */       if (path.toLowerCase().endsWith(".png")) response.setContentType("image/png");
/* 39 */       if (path.toLowerCase().endsWith(".swf")) response.setContentType("application/x-shockwave-flash");
/*    */ 
/* 41 */       return response;
/*    */     }
/*    */     catch (RuntimeException e) {
/* 44 */       e.printStackTrace();
/* 45 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.WebResourceProcessor
 * JD-Core Version:    0.6.0
 */