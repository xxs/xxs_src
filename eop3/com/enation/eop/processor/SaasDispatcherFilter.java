/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.context.SaasEopContextIniter;
/*    */ import com.enation.eop.sdk.utils.JspUtil;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SaasDispatcherFilter
/*    */   implements Filter
/*    */ {
/* 28 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public void init(FilterConfig config)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 38 */     HttpServletRequest httpRequest = (HttpServletRequest)request;
/* 39 */     HttpServletResponse httpResponse = (HttpServletResponse)response;
/*    */     try
/*    */     {
/* 43 */       httpRequest = new SafeHttpRequestWrapper(httpRequest);
/*    */ 
/* 47 */       String uri = httpRequest.getServletPath();
/*    */ 
/* 49 */       if (uri.startsWith("/statics")) {
/* 50 */         chain.doFilter(httpRequest, httpResponse);
/* 51 */         return;
/*    */       }
/*    */ 
/* 54 */       if ((!uri.startsWith("/install")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("NO"))) {
/* 55 */         httpResponse.sendRedirect(httpRequest.getContextPath() + "/install");
/* 56 */         return;
/*    */       }
/* 58 */       if ((!uri.startsWith("/install.html")) && (uri.startsWith("/install")) && (!uri.startsWith("/install/images")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")))
/*    */       {
/* 61 */         httpResponse.getWriter().write("如要重新安装，请先删除/install/install.lock文件，并重起web容器");
/* 62 */         return;
/*    */       }
/* 64 */       SaasEopContextIniter.init(httpRequest, httpResponse);
/* 65 */       Processor processor = ProcessorFactory.newProcessorInstance(uri, httpRequest);
/*    */ 
/* 68 */       if (processor == null) {
/* 69 */         chain.doFilter(request, response);
/*    */       }
/*    */       else {
/* 72 */         Response eopResponse = processor.process(0, httpResponse, httpRequest);
/*    */ 
/* 75 */         InputStream in = eopResponse.getInputStream();
/*    */ 
/* 77 */         if (in != null) {
/* 78 */           BufferedInputStream bis = new BufferedInputStream(in);
/* 79 */           response.setContentType(eopResponse.getContentType() + "; charset=UTF-8");
/*    */ 
/* 81 */           OutputStream output = response.getOutputStream();
/* 82 */           BufferedOutputStream bos = new BufferedOutputStream(output);
/*    */ 
/* 84 */           byte[] data = new byte[4096];
/*    */ 
/* 86 */           int size = 0;
/*    */ 
/* 88 */           if (bis != null) {
/* 89 */             size = bis.read(data);
/* 90 */             while (size != -1) {
/* 91 */               bos.write(data, 0, size);
/* 92 */               size = bis.read(data);
/*    */             }
/*    */           }
/* 95 */           bis.close();
/* 96 */           bos.flush();
/* 97 */           bos.close();
/*    */         }
/*    */         else
/*    */         {
/* 102 */           chain.doFilter(request, response);
/*    */         }
/*    */       }
/*    */ 
/* 106 */       FreeMarkerPaser.remove();
/*    */     } catch (RuntimeException exception) {
/* 108 */       this.logger.error(exception.getMessage(), exception);
/* 109 */       request.setAttribute("message", exception.getMessage());
/* 110 */       String content = JspUtil.getJspOutput("/commons/error.jsp", httpRequest, httpResponse);
/* 111 */       response.setContentType("text/html; charset=UTF-8");
/* 112 */       response.getWriter().print(content);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.SaasDispatcherFilter
 * JD-Core Version:    0.6.0
 */