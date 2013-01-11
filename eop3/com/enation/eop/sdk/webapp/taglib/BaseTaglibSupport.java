/*    */ package com.enation.eop.sdk.webapp.taglib;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*    */ 
/*    */ public class BaseTaglibSupport extends BodyTagSupport
/*    */ {
/*    */   private static final long serialVersionUID = -8939393391060656141L;
/*    */ 
/*    */   protected ServletContext getServletContext()
/*    */   {
/* 19 */     ServletContext servletContext = this.pageContext.getServletContext();
/*    */ 
/* 21 */     return servletContext;
/*    */   }
/*    */ 
/*    */   public HttpServletRequest getRequest()
/*    */   {
/* 29 */     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*    */ 
/* 31 */     return request;
/*    */   }
/*    */ 
/*    */   public HttpServletResponse getResponse() {
/* 35 */     HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();
/*    */ 
/* 37 */     return response;
/*    */   }
/*    */ 
/*    */   protected JspWriter getWriter()
/*    */   {
/* 45 */     return this.pageContext.getOut();
/*    */   }
/*    */ 
/*    */   protected void print(String s)
/*    */   {
/*    */     try
/*    */     {
/* 55 */       getWriter().write(s);
/* 56 */       getWriter().flush();
/*    */     } catch (IOException e) {
/* 58 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void println(String s)
/*    */   {
/* 67 */     print(s + "\n");
/*    */   }
/*    */ 
/*    */   protected String getContextPath() {
/* 71 */     return getRequest().getContextPath();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.BaseTaglibSupport
 * JD-Core Version:    0.6.0
 */