/*     */ package com.enation.framework.taglib;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.web.context.WebApplicationContext;
/*     */ 
/*     */ public abstract class EnationTagSupport extends BodyTagSupport
/*     */ {
/*  24 */   protected Log logger = LogFactory.getLog(getClass());
/*     */ 
/*     */   protected Long getShopId()
/*     */   {
/*  31 */     return (Long)getRequest().getAttribute("shopid");
/*     */   }
/*     */ 
/*     */   protected ServletContext getServletContext()
/*     */   {
/*  41 */     ServletContext servletContext = this.pageContext.getServletContext();
/*     */ 
/*  43 */     return servletContext;
/*     */   }
/*     */ 
/*     */   public HttpServletRequest getRequest()
/*     */   {
/*  52 */     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*     */ 
/*  54 */     return request;
/*     */   }
/*     */ 
/*     */   public HttpServletResponse getResponse()
/*     */   {
/*  59 */     HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();
/*  60 */     return response;
/*     */   }
/*     */ 
/*     */   protected JspWriter getWriter()
/*     */   {
/*  69 */     return this.pageContext.getOut();
/*     */   }
/*     */ 
/*     */   protected void print(String s)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       getWriter().write(s);
/*     */     } catch (IOException e) {
/*  80 */       this.logger.error("taglib print error", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void println(String s)
/*     */   {
/*  89 */     print(s + "\n");
/*     */   }
/*     */ 
/*     */   protected WebApplicationContext getWebApplicationContext()
/*     */     throws RuntimeException
/*     */   {
/* 100 */     ServletContext servletContext = getServletContext();
/* 101 */     Object ob = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
/*     */ 
/* 103 */     if (ob == null) {
/* 104 */       this.logger.error("Get WebApplicationContext from ServletContext." + WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE + " is null.");
/*     */ 
/* 108 */       throw new RuntimeException(" get obj form spring is null ");
/*     */     }
/*     */ 
/* 111 */     WebApplicationContext context = (WebApplicationContext)ob;
/*     */ 
/* 113 */     return context;
/*     */   }
/*     */ 
/*     */   protected Object getBean(String name)
/*     */   {
/* 119 */     return getWebApplicationContext().getBean(name);
/*     */   }
/*     */ 
/*     */   protected Object getBean(String name, Object[] args)
/*     */   {
/* 124 */     return getWebApplicationContext().getBean(name, args);
/*     */   }
/*     */ 
/*     */   protected String getContextPath()
/*     */   {
/* 129 */     return getRequest().getContextPath();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.taglib.EnationTagSupport
 * JD-Core Version:    0.6.0
 */