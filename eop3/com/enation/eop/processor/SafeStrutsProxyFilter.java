/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
/*    */ 
/*    */ public class SafeStrutsProxyFilter extends StrutsPrepareAndExecuteFilter
/*    */ {
/*    */   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 16 */     req = new SafeHttpRequestWrapper((HttpServletRequest)req);
/* 17 */     super.doFilter(req, res, chain);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.SafeStrutsProxyFilter
 * JD-Core Version:    0.6.0
 */