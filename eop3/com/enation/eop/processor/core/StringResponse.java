/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ 
/*    */ public class StringResponse
/*    */   implements Response
/*    */ {
/*    */   private String content;
/*    */   private String contentType;
/*    */   private int statusCode;
/*    */ 
/*    */   public StringResponse()
/*    */   {
/* 18 */     this.contentType = "text/html";
/*    */   }
/*    */ 
/*    */   public void finalize() throws Throwable
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 27 */     return this.content;
/*    */   }
/*    */ 
/*    */   public int getStatusCode() {
/* 31 */     return this.statusCode;
/*    */   }
/*    */ 
/*    */   public String getContentType() {
/* 35 */     return this.contentType;
/*    */   }
/*    */ 
/*    */   public void setContent(String content)
/*    */   {
/* 43 */     this.content = content;
/*    */   }
/*    */ 
/*    */   public void setStatusCode(int code)
/*    */   {
/* 51 */     this.statusCode = code;
/*    */   }
/*    */ 
/*    */   public void setContentType(String contentType)
/*    */   {
/* 59 */     this.contentType = contentType;
/*    */   }
/*    */ 
/*    */   public InputStream getInputStream()
/*    */   {
/*    */     try
/*    */     {
/* 66 */       InputStream in = new ByteArrayInputStream(this.content.getBytes("UTF-8"));
/* 67 */       return in;
/*    */     } catch (UnsupportedEncodingException e) {
/* 69 */       e.printStackTrace();
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.StringResponse
 * JD-Core Version:    0.6.0
 */