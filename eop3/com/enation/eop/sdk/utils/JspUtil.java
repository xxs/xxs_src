/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public abstract class JspUtil
/*    */ {
/*    */   public static String getJspOutput(String jsppath, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 13 */     WrapperResponse wrapperResponse = new WrapperResponse(response);
/*    */     try
/*    */     {
/* 16 */       request.getRequestDispatcher(jsppath).include(request, wrapperResponse);
/*    */     }
/*    */     catch (ServletException e)
/*    */     {
/* 20 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e) {
/* 23 */       e.printStackTrace();
/*    */     }
/* 25 */     return wrapperResponse.getContent();
/*    */   }
/*    */ 
/*    */   public static String getJspOutput1(String jsppath, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 30 */     WrapperResponse wrapperResponse = new WrapperResponse(response);
/*    */     try
/*    */     {
/* 33 */       request.getRequestDispatcher(jsppath).forward(request, wrapperResponse);
/*    */     } catch (ServletException e) {
/* 35 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 37 */       e.printStackTrace();
/*    */     }
/* 39 */     return wrapperResponse.getContent();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.JspUtil
 * JD-Core Version:    0.6.0
 */