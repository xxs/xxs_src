/*    */ package com.enation.eop.processor.core;
/*    */ 
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class InputStreamResponse
/*    */   implements Response
/*    */ {
/*    */   private String contentType;
/*    */   private InputStream inputStream;
/*    */   private int statusCode;
/*    */ 
/*    */   public InputStreamResponse(InputStream in)
/*    */   {
/* 19 */     this.inputStream = in;
/*    */   }
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 24 */     if (this.inputStream == null) {
/* 25 */       return "";
/*    */     }
/* 27 */     return StringUtil.inputStream2String(this.inputStream);
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
/* 65 */     return this.inputStream;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.InputStreamResponse
 * JD-Core Version:    0.6.0
 */