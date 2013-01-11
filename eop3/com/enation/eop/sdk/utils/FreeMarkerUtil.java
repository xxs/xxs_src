/*     */ package com.enation.eop.sdk.utils;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.directive.DirectiveFactory;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.cache.MruCacheStorage;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.Template;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class FreeMarkerUtil
/*     */ {
/*     */   public static Configuration getServletCfg(String pageFolder)
/*     */   {
/*  40 */     Configuration cfg = new Configuration();
/*  41 */     cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), pageFolder);
/*     */ 
/*  43 */     cfg.setObjectWrapper(new DefaultObjectWrapper());
/*  44 */     return cfg;
/*     */   }
/*     */ 
/*     */   public static Configuration getCfg()
/*     */   {
/*  49 */     Configuration cfg = new Configuration();
/*  50 */     cfg.setTemplateUpdateDelay(6000);
/*  51 */     cfg.setCacheStorage(new MruCacheStorage(20, 250));
/*     */ 
/*  53 */     Map directiveMap = DirectiveFactory.getCommonDirective();
/*  54 */     Iterator keyIter = directiveMap.keySet().iterator();
/*     */ 
/*  56 */     while (keyIter.hasNext()) {
/*  57 */       String key = (String)keyIter.next();
/*     */ 
/*  59 */       cfg.setSharedVariable(key, (TemplateModel)directiveMap.get(key));
/*     */     }
/*     */ 
/*  63 */     cfg.setObjectWrapper(new DefaultObjectWrapper());
/*  64 */     cfg.setDefaultEncoding("UTF-8");
/*  65 */     cfg.setLocale(Locale.CHINA);
/*  66 */     cfg.setEncoding(Locale.CHINA, "UTF-8");
/*     */ 
/*  69 */     return cfg;
/*     */   }
/*     */ 
/*     */   public static Configuration getFolderCfg(String pageFolder)
/*     */     throws IOException
/*     */   {
/*  77 */     Configuration cfg = getCfg();
/*  78 */     cfg.setDirectoryForTemplateLoading(new File(pageFolder));
/*     */ 
/*  80 */     return cfg;
/*     */   }
/*     */ 
/*     */   public static void test()
/*     */   {
/*     */     try
/*     */     {
/*  87 */       Configuration cfg = getFolderCfg("D:/workspace/eopnew/eop/WebContent/WEB-INF/classes/com/enation/app/shop/core/widget/goodscat");
/*     */ 
/*  89 */       Template temp = cfg.getTemplate("GoodsCat.html");
/*  90 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/*  92 */       Writer out = new OutputStreamWriter(stream, "UTF-8");
/*  93 */       temp.process(new HashMap(), out);
/*     */ 
/*  95 */       out.flush();
/*  96 */       String html = stream.toString();
/*  97 */       System.out.println("+++++++++" + html);
/*     */     } catch (IOException e) {
/*  99 */       e.printStackTrace();
/*     */     } catch (TemplateException e) {
/* 101 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException, TemplateException
/*     */   {
/* 108 */     Configuration cfg = getFolderCfg("D:/workspace/eopnew/eop/WebContent/WEB-INF/classes/com/enation/app/shop/core/widget/goodscat");
/*     */ 
/* 110 */     Template temp = cfg.getTemplate("GoodsCat.html");
/* 111 */     ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 113 */     Writer out = new OutputStreamWriter(stream, "UTF-8");
/* 114 */     temp.process(new HashMap(), out);
/*     */ 
/* 116 */     out.flush();
/* 117 */     String html = stream.toString();
/* 118 */     System.out.println(html);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.FreeMarkerUtil
 * JD-Core Version:    0.6.0
 */