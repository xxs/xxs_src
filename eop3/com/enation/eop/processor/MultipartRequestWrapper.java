/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletRequestWrapper;
/*    */ import javazoom.upload.MultipartFormDataRequest;
/*    */ 
/*    */ public class MultipartRequestWrapper extends HttpServletRequestWrapper
/*    */ {
/*    */   private MultipartFormDataRequest mrequest;
/*    */ 
/*    */   public MultipartRequestWrapper(HttpServletRequest request, MultipartFormDataRequest _mrequest)
/*    */   {
/* 13 */     super(request);
/* 14 */     this.mrequest = _mrequest;
/*    */   }
/*    */ 
/*    */   public String getParameter(String name)
/*    */   {
/* 20 */     return this.mrequest.getParameter(name);
/*    */   }
/*    */ 
/*    */   public Enumeration getParameterNames()
/*    */   {
/* 26 */     return this.mrequest.getParameterNames();
/*    */   }
/*    */ 
/*    */   public String[] getParameterValues(String name)
/*    */   {
/* 31 */     return this.mrequest.getParameterValues(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.MultipartRequestWrapper
 * JD-Core Version:    0.6.0
 */