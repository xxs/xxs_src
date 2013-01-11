/*     */ package com.enation.eop.processor.core;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.fileupload.FileItem;
/*     */ import org.apache.commons.fileupload.FileItemFactory;
/*     */ import org.apache.commons.fileupload.FileUploadException;
/*     */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.entity.mime.MultipartEntity;
/*     */ import org.apache.http.entity.mime.content.InputStreamBody;
/*     */ import org.apache.http.entity.mime.content.StringBody;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ 
/*     */ public abstract class HttpEntityFactory
/*     */ {
/*     */   public static HttpEntity buildEntity(HttpServletRequest request, Map<String, String> otherParams)
/*     */   {
/*  35 */     boolean isMultipart = ServletFileUpload.isMultipartContent(request);
/*  36 */     HttpEntity entity = null;
/*  37 */     if (isMultipart)
/*  38 */       entity = buildMultipartFormEntity(request, otherParams);
/*     */     else {
/*  40 */       entity = buildFormEntity(request, otherParams);
/*     */     }
/*  42 */     return entity;
/*     */   }
/*     */ 
/*     */   private static HttpEntity buildFormEntity(HttpServletRequest request, Map<String, String> otherParams)
/*     */   {
/*     */     try
/*     */     {
/*  50 */       Enumeration paramNames = request.getParameterNames();
/*  51 */       if (paramNames == null) return null;
/*     */ 
/*  53 */       List formparams = new ArrayList();
/*     */ 
/*  56 */       while (paramNames.hasMoreElements()) {
/*  57 */         String name = (String)paramNames.nextElement();
/*  58 */         String value = request.getParameter(name);
/*  59 */         formparams.add(new BasicNameValuePair(name, value));
/*     */       }
/*     */ 
/*  62 */       if (otherParams != null) {
/*  63 */         Iterator iterator = otherParams.keySet().iterator();
/*  64 */         while (iterator.hasNext()) {
/*  65 */           String key = (String)iterator.next();
/*  66 */           String value = (String)otherParams.get(key);
/*  67 */           formparams.add(new BasicNameValuePair(key, value));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  72 */       UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
/*     */ 
/*  75 */       return entity;
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  78 */       e.printStackTrace();
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   private static HttpEntity buildMultipartFormEntity(HttpServletRequest request, Map<String, String> otherParams)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       MultipartEntity entity = new MultipartEntity();
/*     */ 
/*  91 */       FileItemFactory factory = new DiskFileItemFactory();
/*  92 */       ServletFileUpload upload = new ServletFileUpload(factory);
/*  93 */       List items = upload.parseRequest(request);
/*  94 */       for (FileItem item : items) {
/*  95 */         String fieldName = item.getFieldName();
/*     */         StringBody strBody;
/*  97 */         if (item.isFormField())
/*     */         {
/*  99 */           strBody = new StringBody(item.getString());
/*     */         }
/*     */         else {
/* 102 */           String name = item.getName();
/* 103 */           InputStream dataIn = new ByteArrayInputStream(item.get());
/* 104 */           isbody = new InputStreamBody(dataIn, name);
/*     */         }
/*     */       }
/*     */       InputStreamBody isbody;
/* 111 */       if (otherParams != null) {
/* 112 */         Iterator iterator = otherParams.keySet().iterator();
/*     */         StringBody strBody;
/* 113 */         while (iterator.hasNext()) {
/* 114 */           String key = (String)iterator.next();
/* 115 */           String value = (String)otherParams.get(key);
/* 116 */           strBody = new StringBody(value);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 124 */       return entity;
/*     */     } catch (FileUploadException e) {
/* 126 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 128 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 131 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.HttpEntityFactory
 * JD-Core Version:    0.6.0
 */