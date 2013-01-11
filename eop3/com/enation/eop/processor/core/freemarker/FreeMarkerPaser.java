/*     */ package com.enation.eop.processor.core.freemarker;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.utils.EopUtil;
/*     */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.Template;
/*     */ import freemarker.template.TemplateException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public final class FreeMarkerPaser
/*     */ {
/*  30 */   private static final Log log = LogFactory.getLog(FreeMarkerPaser.class);
/*  31 */   private static ThreadLocal<FreeMarkerPaser> managerLocal = new ThreadLocal();
/*     */   private Class clazz;
/* 109 */   private boolean wrapPath = true;
/*     */   private static Configuration cfg;
/*     */   private Map<String, Object> data;
/*     */   private String pathPrefix;
/*     */   private String pageName;
/*     */   private String pageExt;
/*     */   private String pageFolder;
/*     */ 
/*     */   public FreeMarkerPaser()
/*     */   {
/*  34 */     this.data = new HashMap();
/*  35 */     this.clazz = null;
/*  36 */     this.pageFolder = null;
/*     */   }
/*     */ 
/*     */   public void setClz(Class clz) {
/*  40 */     this.clazz = clz;
/*     */   }
/*     */ 
/*     */   public static final FreeMarkerPaser getInstance()
/*     */   {
/*  48 */     if (managerLocal.get() == null) {
/*  49 */       throw new RuntimeException("freemarker paser is null");
/*     */     }
/*  51 */     FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();
/*     */ 
/*  53 */     fmp.setPageFolder(null);
/*  54 */     fmp.setWrapPath(true);
/*     */ 
/*  56 */     return fmp;
/*     */   }
/*     */ 
/*     */   public static final FreeMarkerPaser getCurrInstance() {
/*  60 */     if (managerLocal.get() == null) {
/*  61 */       throw new RuntimeException("freemarker paser is null");
/*     */     }
/*  63 */     FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();
/*     */ 
/*  65 */     return fmp;
/*     */   }
/*     */ 
/*     */   public static final void set(FreeMarkerPaser fp) {
/*  69 */     managerLocal.set(fp);
/*     */   }
/*     */   public static final void remove() {
/*  72 */     managerLocal.remove();
/*     */   }
/*     */ 
/*     */   public FreeMarkerPaser(Class clz)
/*     */   {
/*  77 */     this.clazz = clz;
/*  78 */     this.data = new HashMap();
/*     */   }
/*     */ 
/*     */   public FreeMarkerPaser(Class clz, String folder) {
/*  82 */     this.clazz = clz;
/*  83 */     this.pageFolder = folder;
/*  84 */     this.data = new HashMap();
/*     */   }
/*     */ 
/*     */   public void putData(String key, Object value)
/*     */   {
/*  94 */     if ((key != null) && (value != null))
/*  95 */       this.data.put(key, value);
/*     */   }
/*     */ 
/*     */   public void putData(Map map) {
/*  99 */     if (map != null)
/* 100 */       this.data.putAll(map);
/*     */   }
/*     */ 
/*     */   public Object getData(String key) {
/* 104 */     if (key == null) return null;
/*     */ 
/* 106 */     return this.data.get(key);
/*     */   }
/*     */ 
/*     */   public void setWrapPath(boolean wp)
/*     */   {
/* 112 */     this.wrapPath = wp;
/*     */   }
/*     */ 
/*     */   public String proessPageContent()
/*     */   {
/*     */     try {
/* 118 */       String name = this.clazz.getSimpleName();
/* 119 */       this.pageExt = (this.pageExt == null ? ".html" : this.pageExt);
/* 120 */       name = this.pageName == null ? name : this.pageName;
/*     */ 
/* 122 */       cfg = getCfg();
/* 123 */       cfg.setNumberFormat("0.##");
/* 124 */       Template temp = cfg.getTemplate(name + this.pageExt);
/* 125 */       ByteOutputStream stream = new ByteOutputStream();
/* 126 */       Writer out = new OutputStreamWriter(stream);
/* 127 */       temp.process(this.data, out);
/* 128 */       out.flush();
/* 129 */       String content = stream.toString();
/* 130 */       if (this.wrapPath)
/*     */       {
/* 134 */         content = EopUtil.wrapjavascript(content, getResPath());
/* 135 */         content = EopUtil.wrapcss(content, getResPath());
/*     */       }
/*     */ 
/* 140 */       return content;
/*     */     } catch (IOException e) {
/* 142 */       e.printStackTrace();
/*     */     } catch (TemplateException e) {
/* 144 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 147 */     return "widget  processor error";
/*     */   }
/*     */ 
/*     */   private Configuration getCfg()
/*     */   {
/* 186 */     if (cfg == null) {
/* 187 */       cfg = FreeMarkerUtil.getCfg();
/*     */     }
/*     */ 
/* 191 */     this.pathPrefix = (this.pathPrefix == null ? "" : this.pathPrefix);
/*     */ 
/* 193 */     if (this.pageFolder == null)
/*     */     {
/* 195 */       cfg.setClassForTemplateLoading(this.clazz, this.pathPrefix);
/*     */     }
/*     */     else
/*     */     {
/* 199 */       cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), this.pageFolder);
/*     */     }
/* 201 */     cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 202 */     cfg.setDefaultEncoding("UTF-8");
/* 203 */     cfg.setLocale(Locale.CHINA);
/* 204 */     cfg.setEncoding(Locale.CHINA, "UTF-8");
/* 205 */     return cfg;
/*     */   }
/*     */ 
/*     */   public void setPathPrefix(String path)
/*     */   {
/* 220 */     this.pathPrefix = path;
/*     */   }
/*     */ 
/*     */   public void setPageName(String pageName)
/*     */   {
/* 230 */     this.pageName = pageName;
/*     */   }
/*     */ 
/*     */   public void setPageExt(String pageExt)
/*     */   {
/* 239 */     this.pageExt = pageExt;
/*     */   }
/*     */ 
/*     */   public void setPageFolder(String pageFolder) {
/* 243 */     this.pageFolder = pageFolder;
/*     */   }
/*     */ 
/*     */   private String getResPath()
/*     */   {
/* 252 */     String ctx = EopSetting.CONTEXT_PATH;
/* 253 */     ctx = ctx.equals("/") ? "" : ctx;
/* 254 */     if (this.pageFolder == null) {
/* 255 */       return ctx + "/resource/" + this.clazz.getPackage().getName().replaceAll("\\.", "/") + "/";
/*     */     }
/* 257 */     return ctx + this.pageFolder + "/";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.freemarker.FreeMarkerPaser
 * JD-Core Version:    0.6.0
 */