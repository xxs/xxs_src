/*    */ package com.enation.framework.gzip;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class GZIPFilter
/*    */   implements Filter
/*    */ {
/*    */   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 13 */     if ((req instanceof HttpServletRequest)) {
/* 14 */       HttpServletRequest request = (HttpServletRequest)req;
/* 15 */       HttpServletResponse response = (HttpServletResponse)res;
/*    */ 
/* 17 */       String ae = request.getHeader("accept-encoding");
/* 18 */       if ((ae != null) && (ae.indexOf("gzip") != -1)) {
/* 19 */         GZIPResponseWrapper gZIPResponseWrapper = new GZIPResponseWrapper(response);
/* 20 */         chain.doFilter(req, gZIPResponseWrapper);
/* 21 */         gZIPResponseWrapper.finishResponse();
/* 22 */         return;
/*    */       }
/* 24 */       chain.doFilter(req, res);
/*    */     } else {
/* 26 */       System.out.println("not servlet request...");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig filterConfig) {
/* 31 */     System.out.println("The GZIP Is Enable");
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.gzip.GZIPFilter
 * JD-Core Version:    0.6.0
 */