/*     */ package com.enation.eop.processor.core;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ 
/*     */ public class RemoteRequest
/*     */   implements Request
/*     */ {
/*     */   public HttpClient httpClient;
/*     */   private Map<String, String> params;
/*     */ 
/*     */   public void setExecuteParams(Map<String, String> params)
/*     */   {
/*  33 */     this.params = params;
/*     */   }
/*     */ 
/*     */   private HttpClient getHttpClient(HttpServletRequest httpRequest) {
/*  37 */     HttpSession session = httpRequest.getSession();
/*  38 */     if (session.getAttribute("httpClient") == null)
/*     */     {
/*  40 */       HttpClient httpclient = new DefaultHttpClient();
/*  41 */       session.setAttribute("httpClient", httpclient);
/*     */     }
/*     */ 
/*  45 */     return (HttpClient)session.getAttribute("httpClient");
/*     */   }
/*     */ 
/*     */   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*     */   {
/*  58 */     String method = httpRequest.getMethod();
/*     */ 
/*  60 */     method = method.toUpperCase();
/*     */ 
/*  62 */     HttpClient httpclient = getHttpClient(httpRequest);
/*  63 */     HttpUriRequest httpUriRequest = null;
/*     */ 
/*  66 */     HttpPost httppost = new HttpPost(uri);
/*     */ 
/*  68 */     HttpEntity entity = HttpEntityFactory.buildEntity(httpRequest, this.params);
/*  69 */     httppost.setEntity(entity);
/*  70 */     httpUriRequest = httppost;
/*     */     try
/*     */     {
/*  81 */       HttpResponse httpresponse = httpclient.execute(httpUriRequest);
/*  82 */       HttpEntity rentity = httpresponse.getEntity();
/*     */ 
/*  85 */       Response response = new InputStreamResponse(rentity.getContent());
/*  86 */       System.out.println(rentity.getContentType().getValue());
/*     */ 
/*  91 */       return response;
/*     */     }
/*     */     catch (Exception e) {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   public Response execute(String uri)
/*     */   {
/* 108 */     HttpClient httpclient = new DefaultHttpClient();
/* 109 */     HttpGet httpget = new HttpGet(uri);
/*     */     try
/*     */     {
/* 112 */       HttpResponse response = httpclient.execute(httpget);
/* 113 */       HttpEntity entity = response.getEntity();
/* 114 */       String content = EntityUtils.toString(entity, "utf-8");
/* 115 */       Response eopresponse = new StringResponse();
/* 116 */       eopresponse.setContent(content);
/* 117 */       return eopresponse;
/*     */     }
/*     */     catch (ClientProtocolException e)
/*     */     {
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.RemoteRequest
 * JD-Core Version:    0.6.0
 */