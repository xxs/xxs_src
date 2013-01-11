/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpServletResponseWrapper;
/*    */ 
/*    */ public class WrapperResponse extends HttpServletResponseWrapper
/*    */ {
/*    */   private MyPrintWriter tmpWriter;
/*    */   private ByteArrayOutputStream output;
/*    */ 
/*    */   public WrapperResponse(HttpServletResponse httpServletResponse)
/*    */   {
/* 23 */     super(httpServletResponse);
/* 24 */     this.output = new ByteArrayOutputStream();
/* 25 */     this.tmpWriter = new MyPrintWriter(this.output);
/*    */   }
/*    */ 
/*    */   public void finalize() throws Throwable {
/* 29 */     super.finalize();
/* 30 */     this.output.close();
/* 31 */     this.tmpWriter.close();
/*    */   }
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 36 */     this.tmpWriter.flush();
/* 37 */     String s = "";
/*    */ 
/* 39 */     s = this.tmpWriter.getByteArrayOutputStream().toString();
/*    */ 
/* 46 */     return s;
/*    */   }
/*    */ 
/*    */   public PrintWriter getWriter() throws IOException
/*    */   {
/* 51 */     return this.tmpWriter;
/*    */   }
/*    */ 
/*    */   public void close() throws IOException {
/* 55 */     this.tmpWriter.close();
/*    */   }
/*    */   private static class MyPrintWriter extends PrintWriter {
/*    */     ByteArrayOutputStream myOutput;
/*    */ 
/*    */     public MyPrintWriter(ByteArrayOutputStream output) {
/* 62 */       super();
/* 63 */       this.myOutput = output;
/*    */     }
/*    */ 
/*    */     public ByteArrayOutputStream getByteArrayOutputStream() {
/* 67 */       return this.myOutput;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.WrapperResponse
 * JD-Core Version:    0.6.0
 */