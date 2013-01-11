/*    */ package com.enation.eop.sdk.webapp;
/*    */ 
/*    */ import com.enation.eop.sdk.EopServlet;
/*    */ import com.enation.eop.sdk.utils.JspUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class AdminServlet
/*    */   implements EopServlet
/*    */ {
/*    */   public void service(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException
/*    */   {
/* 19 */     String servletPath = request.getServletPath();
/* 20 */     request.setAttribute("content", JspUtil.getJspOutput(servletPath, request, response));
/* 21 */     String content = JspUtil.getJspOutput("/eop/main_frame.jsp", request, response);
/*    */ 
/* 23 */     response.setContentType("text/html; charset=UTF-8");
/* 24 */     response.getWriter().print(content);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.AdminServlet
 * JD-Core Version:    0.6.0
 */