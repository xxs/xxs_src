/*    */ package com.enation.framework.gzip;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.ServletOutputStream;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpServletResponseWrapper;
/*    */ 
/*    */ public class GZIPResponseWrapper extends HttpServletResponseWrapper
/*    */ {
/* 11 */   protected HttpServletResponse origResponse = null;
/* 12 */   protected ServletOutputStream stream = null;
/* 13 */   protected PrintWriter writer = null;
/*    */ 
/*    */   public GZIPResponseWrapper(HttpServletResponse response) {
/* 16 */     super(response);
/* 17 */     this.origResponse = response;
/*    */   }
/*    */ 
/*    */   public ServletOutputStream createOutputStream() throws IOException {
/* 21 */     return new GZIPResponseStream(this.origResponse);
/*    */   }
/*    */ 
/*    */   public void finishResponse() {
/*    */     try {
/* 26 */       if (this.writer != null) {
/* 27 */         this.writer.close();
/*    */       }
/* 29 */       else if (this.stream != null)
/* 30 */         this.stream.close();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void flushBuffer() throws IOException {
/* 38 */     this.stream.flush();
/*    */   }
/*    */ 
/*    */   public ServletOutputStream getOutputStream() throws IOException {
/* 42 */     if (this.writer != null) {
/* 43 */       throw new IllegalStateException("getWriter() has already been called!");
/*    */     }
/*    */ 
/* 46 */     if (this.stream == null)
/* 47 */       this.stream = createOutputStream();
/* 48 */     return this.stream;
/*    */   }
/*    */ 
/*    */   public PrintWriter getWriter() throws IOException {
/* 52 */     if (this.writer != null) {
/* 53 */       return this.writer;
/*    */     }
/* 55 */     if (this.stream != null) {
/* 56 */       throw new IllegalStateException("getOutputStream() has already been called!");
/*    */     }
/* 58 */     this.stream = createOutputStream();
/* 59 */     this.writer = new PrintWriter(new OutputStreamWriter(this.stream, "UTF-8"));
/* 60 */     return this.writer;
/*    */   }
/*    */ 
/*    */   public void setContentLength(int length)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.gzip.GZIPResponseWrapper
 * JD-Core Version:    0.6.0
 */