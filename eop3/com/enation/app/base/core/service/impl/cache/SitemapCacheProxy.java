/*     */ package com.enation.app.base.core.service.impl.cache;
/*     */ 
/*     */ import com.enation.app.base.core.model.SiteMapUrl;
/*     */ import com.enation.app.base.core.service.ISitemapManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class SitemapCacheProxy extends AbstractCacheProxy
/*     */   implements ISitemapManager
/*     */ {
/*     */   public static final String CACHE_KEY = "sitemap";
/*     */ 
/*     */   public SitemapCacheProxy()
/*     */   {
/*  35 */     super("sitemap");
/*     */   }
/*     */ 
/*     */   public int delete(String loc) {
/*  39 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  40 */     Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());
/*     */ 
/*  42 */     document = document == null ? init() : document;
/*     */ 
/*  44 */     List list = list = document.getRootElement().elements();
/*  45 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  46 */       Element urlElement = (Element)o;
/*  47 */       String mloc = urlElement.element("loc").getText();
/*  48 */       if (mloc.equals(loc)) {
/*  49 */         document.getRootElement().remove(urlElement);
/*  50 */         this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
/*     */ 
/*  52 */         return 1;
/*     */       }
/*     */     }
/*  55 */     return 0;
/*     */   }
/*     */ 
/*     */   public void addUrl(SiteMapUrl url) {
/*  59 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  60 */     Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());
/*     */ 
/*  62 */     document = document == null ? init() : document;
/*  63 */     List list = list = document.getRootElement().elements();
/*  64 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  65 */       Element urlElement = (Element)o;
/*  66 */       String mloc = urlElement.element("loc").getText();
/*  67 */       if (mloc.equals(url.getLoc())) {
/*  68 */         return;
/*     */       }
/*     */     }
/*  71 */     Element urlsetElement = document.getRootElement();
/*  72 */     Element urlElement = urlsetElement.addElement("url");
/*  73 */     Element locElement = urlElement.addElement("loc");
/*  74 */     Element lastmodElement = urlElement.addElement("lastmod");
/*  75 */     Element changefreqElement = urlElement.addElement("changefreq");
/*  76 */     Element priorityElement = urlElement.addElement("priority");
/*  77 */     locElement.setText(url.getLoc());
/*  78 */     lastmodElement.setText(DateUtil.toString(new Date(url.getLastmod().longValue()), "yyyy-MM-dd"));
/*     */ 
/*  81 */     changefreqElement.setText("weekly");
/*     */ 
/*  83 */     priorityElement.setText("0.5");
/*  84 */     write(document);
/*  85 */     this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
/*     */   }
/*     */ 
/*     */   public void editUrl(String loc, Long lastmod)
/*     */   {
/*  90 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  91 */     Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());
/*     */ 
/*  93 */     document = document == null ? init() : document;
/*  94 */     List list = list = document.getRootElement().elements();
/*  95 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*  96 */       Element urlElement = (Element)o;
/*  97 */       String mloc = urlElement.element("loc").getText();
/*  98 */       if (mloc.equals(loc)) {
/*  99 */         urlElement.element("lastmod").setText(DateUtil.toString(new Date(lastmod.longValue()), "yyyy-MM-dd"));
/*     */ 
/* 101 */         write(document);
/* 102 */         this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
/*     */ 
/* 104 */         break;
/*     */       } }
/*     */   }
/*     */ 
/*     */   public String getsitemap()
/*     */   {
/* 110 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 111 */     Document document = (Document)this.cache.get("sitemap_" + site.getUserid() + "_" + site.getId());
/*     */ 
/* 113 */     document = document == null ? init() : document;
/* 114 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 115 */     return document.asXML().replaceAll("<loc>/", "<loc>http://" + request.getServerName() + "/");
/*     */   }
/*     */ 
/*     */   private Document read()
/*     */   {
/* 121 */     Document document = null;
/* 122 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 123 */     SAXReader saxReader = new SAXReader();
/*     */     try {
/* 125 */       if (FileUtil.exist(EopSetting.EOP_PATH + "/user/" + site.getUserid() + "/" + site.getId() + "/sitemap.xml"))
/*     */       {
/* 127 */         document = saxReader.read(new File(EopSetting.EOP_PATH + "/user/" + site.getUserid() + "/" + site.getId() + "/sitemap.xml"));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/* 133 */       System.out.println(e.getMessage());
/*     */     }
/* 135 */     return document;
/*     */   }
/*     */ 
/*     */   private void write(Document document)
/*     */   {
/* 140 */     String contextPath = EopContext.getContext().getContextPath();
/*     */     try
/*     */     {
/* 143 */       if (!contextPath.startsWith("/")) {
/* 144 */         contextPath = "/" + contextPath;
/*     */       }
/*     */ 
/* 147 */       XMLWriter output = new XMLWriter(new FileWriter(new File(EopSetting.EOP_PATH + contextPath + "/sitemap.xml")));
/*     */ 
/* 149 */       output.write(document);
/* 150 */       output.close();
/*     */     } catch (IOException e) {
/* 152 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Document init()
/*     */   {
/* 158 */     Document document = read();
/* 159 */     if (null == document) {
/* 160 */       String docStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
/* 161 */       docStr = docStr + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
/* 162 */       docStr = docStr + "</urlset>";
/*     */       try {
/* 164 */         document = DocumentHelper.parseText(docStr);
/*     */       } catch (DocumentException e) {
/* 166 */         e.printStackTrace();
/*     */       }
/* 168 */       write(document);
/*     */     }
/* 170 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 171 */     this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
/*     */ 
/* 173 */     return document;
/*     */   }
/*     */ 
/*     */   public void clean() {
/* 177 */     Document document = null;
/* 178 */     String docStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
/* 179 */     docStr = docStr + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
/* 180 */     docStr = docStr + "</urlset>";
/*     */     try {
/* 182 */       document = DocumentHelper.parseText(docStr);
/*     */     } catch (DocumentException e) {
/* 184 */       e.printStackTrace();
/*     */     }
/* 186 */     write(document);
/* 187 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 188 */     this.cache.put("sitemap_" + site.getUserid() + "_" + site.getId(), document);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SitemapCacheProxy
 * JD-Core Version:    0.6.0
 */