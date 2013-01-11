/*    */ package com.enation.framework.gzip;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ import javax.servlet.ServletOutputStream;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class GZIPResponseStream extends ServletOutputStream
/*    */ {
/* 11 */   protected ByteArrayOutputStream baos = null;
/* 12 */   protected GZIPOutputStream gzipstream = null;
/* 13 */   protected boolean closed = false;
/* 14 */   protected HttpServletResponse response = null;
/* 15 */   protected ServletOutputStream output = null;
/*    */ 
/*    */   public GZIPResponseStream(HttpServletResponse response) throws IOException
/*    */   {
/* 19 */     this.closed = false;
/* 20 */     this.response = response;
/* 21 */     this.output = response.getOutputStream();
/* 22 */     this.baos = new ByteArrayOutputStream();
/* 23 */     this.gzipstream = new GZIPOutputStream(this.baos);
/*    */   }
/*    */ 
/*    */   public void close() throws IOException {
/* 27 */     if (this.closed) {
/* 28 */       throw new IOException("This output stream has already been closed");
/*    */     }
/* 30 */     this.gzipstream.finish();
/* 31 */     byte[] bytes = this.baos.toByteArray();
/* 32 */     this.response.addHeader("Content-Length", Integer.toString(bytes.length));
/* 33 */     this.response.addHeader("Content-Encoding", "gzip");
/* 34 */     this.output.write(bytes);
/* 35 */     this.output.flush();
/* 36 */     this.output.close();
/* 37 */     this.closed = true;
/*    */   }
/*    */ 
/*    */   public void flush() throws IOException {
/* 41 */     if (this.closed) {
/* 42 */       throw new IOException("Cannot flush a closed output stream");
/*    */     }
/* 44 */     this.gzipstream.flush();
/*    */   }
/*    */ 
/*    */   public void write(int b) throws IOException {
/* 48 */     if (this.closed) {
/* 49 */       throw new IOException("Cannot write to a closed output stream");
/*    */     }
/* 51 */     this.gzipstream.write((byte)b);
/*    */   }
/*    */ 
/*    */   public void write(byte[] b) throws IOException {
/* 55 */     write(b, 0, b.length);
/*    */   }
/*    */ 
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 59 */     if (this.closed) {
/* 60 */       throw new IOException("Cannot write to a closed output stream");
/*    */     }
/* 62 */     this.gzipstream.write(b, off, len);
/*    */   }
/*    */ 
/*    */   public boolean closed() {
/* 66 */     return this.closed;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.gzip.GZIPResponseStream
 * JD-Core Version:    0.6.0
 */