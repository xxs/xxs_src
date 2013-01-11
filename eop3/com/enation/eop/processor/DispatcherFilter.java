/*     */ package com.enation.eop.processor;
/*     */ 
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopContextIniter;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.context.SaasEopContextIniter;
/*     */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*     */ import com.enation.eop.sdk.utils.JspUtil;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javazoom.upload.UploadException;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class DispatcherFilter
/*     */   implements Filter
/*     */ {
/*     */   public void init(FilterConfig config)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  59 */     HttpServletRequest httpRequest = (HttpServletRequest)request;
/*  60 */     HttpServletResponse httpResponse = (HttpServletResponse)response;
/*  61 */     String uri = httpRequest.getServletPath();
/*     */     try
/*     */     {
/*  66 */       if (uri.startsWith("/statics")) { chain.doFilter(httpRequest, httpResponse);
/*     */         return;
/*     */       }
/*  71 */       if ((!uri.startsWith("/install")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("NO"))) { httpResponse.sendRedirect(httpRequest.getContextPath() + "/install");
/*     */         return; }
/*  75 */       if ((!uri.startsWith("/install.html")) && (uri.startsWith("/install")) && (!uri.startsWith("/install/images")) && (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")))
/*     */       {
/*  78 */         httpResponse.getWriter().write("如要重新安装，请先删除/install/install.lock文件，并重起web容器");
/*     */         return;
/*     */       }
/*  82 */       if ("2".equals(EopSetting.RUNMODE))
/*  83 */         SaasEopContextIniter.init(httpRequest, httpResponse);
/*     */       else {
/*  85 */         EopContextIniter.init(httpRequest, httpResponse);
/*     */       }
/*     */ 
/*  88 */       Processor loginprocessor = (Processor)SpringContextHolder.getBean("autoLoginProcessor");
/*  89 */       if (loginprocessor != null) {
/*  90 */         loginprocessor.process(1, httpResponse, httpRequest);
/*     */       }
/*     */ 
/*  93 */       Processor processor = ProcessorFactory.newProcessorInstance(uri, httpRequest);
/*     */ 
/*  96 */       if (processor == null) {
/*  97 */         chain.doFilter(request, response);
/*     */       }
/*     */       else
/*     */       {
/* 101 */         if ((uri.equals("/")) || (uri.endsWith(".html"))) {
/* 102 */           boolean result = HttpCacheManager.getIsCached(uri);
/* 103 */           if (result) return; 
/*     */         }
/* 105 */         Response eopResponse = processor.process(0, httpResponse, httpRequest);
/*     */ 
/* 108 */         InputStream in = eopResponse.getInputStream();
/*     */ 
/* 110 */         if (in != null)
/*     */         {
/* 112 */           byte[] inbytes = IOUtils.toByteArray(in);
/* 113 */           httpResponse.setContentType(eopResponse.getContentType());
/* 114 */           httpResponse.setCharacterEncoding("UTF-8");
/* 115 */           httpResponse.setHeader("Content-Length", "" + inbytes.length);
/*     */ 
/* 117 */           OutputStream output = httpResponse.getOutputStream();
/* 118 */           IOUtils.write(inbytes, output);
/*     */         } else {
/* 120 */           chain.doFilter(request, response);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RuntimeException exception)
/*     */     {
/* 127 */       exception.printStackTrace();
/* 128 */       response.setContentType("text/html; charset=UTF-8");
/* 129 */       request.setAttribute("message", exception.getMessage());
/* 130 */       String content = JspUtil.getJspOutput("/commons/error.jsp", httpRequest, httpResponse);
/*     */ 
/* 132 */       response.getWriter().print(content);
/*     */     }
/*     */     finally
/*     */     {
/* 136 */       ThreadContextHolder.remove();
/* 137 */       FreeMarkerPaser.remove();
/* 138 */       EopContext.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ 
/*     */   private HttpServletRequest wrapRequest(HttpServletRequest request, String url)
/*     */     throws UploadException, IOException
/*     */   {
/* 149 */     List safeUrlList = EopSetting.safeUrlList;
/* 150 */     for (String safeUrl : safeUrlList) {
/* 151 */       if (safeUrl.equals(url)) {
/* 152 */         return request;
/*     */       }
/*     */     }
/* 155 */     return new SafeHttpRequestWrapper(request);
/*     */   }
/*     */ 
/*     */   public String get404Html(String url) {
/* 159 */     String themeFld = EopSetting.EOP_PATH + "/themes/";
/* 160 */     Map data = new HashMap();
/* 161 */     data.put("url", url);
/*     */     try
/*     */     {
/* 164 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);
/* 165 */       Template temp = cfg.getTemplate("404.html");
/* 166 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 168 */       Writer out = new OutputStreamWriter(stream);
/* 169 */       temp.process(data, out);
/*     */ 
/* 171 */       out.flush();
/* 172 */       String html = stream.toString();
/* 173 */       return html;
/*     */     }
/*     */     catch (Exception e) {
/* 176 */       e.printStackTrace();
/* 177 */     }return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.DispatcherFilter
 * JD-Core Version:    0.6.0
 */