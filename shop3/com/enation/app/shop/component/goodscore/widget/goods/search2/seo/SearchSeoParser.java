/*     */ package com.enation.app.shop.component.goodscore.widget.goods.search2.seo;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.Template;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SearchSeoParser
/*     */ {
/*     */   private Map selectorMap;
/*  29 */   private Cat cat = null;
/*     */ 
/*  31 */   private String tag = null;
/*     */ 
/*  34 */   private HashMap propMap = new HashMap();
/*     */ 
/*  37 */   private HashMap fieldMap = new HashMap();
/*     */ 
/*  39 */   private String title = "";
/*  40 */   private String keywords = "";
/*  41 */   private String description = "";
/*     */ 
/*     */   public SearchSeoParser(Map selectorMap, Cat cat, String tag) {
/*  44 */     this.selectorMap = selectorMap;
/*  45 */     this.cat = cat;
/*  46 */     this.tag = tag;
/*  47 */     init();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  54 */     Iterator iterator = this.selectorMap.keySet().iterator();
/*  55 */     while (iterator.hasNext()) {
/*  56 */       String key = iterator.next().toString();
/*  57 */       if (key.equals("prop")) {
/*  58 */         Map multiSelector = (Map)(Map)this.selectorMap.get(key);
/*  59 */         Iterator multiIterator = multiSelector.keySet().iterator();
/*  60 */         while (multiIterator.hasNext()) {
/*  61 */           String value = "";
/*  62 */           String multiKey = multiIterator.next().toString();
/*  63 */           List selectorList = (List)(List)multiSelector.get(multiKey);
/*     */ 
/*  65 */           for (int i = 1; i < selectorList.size(); i++) {
/*  66 */             SearchSelector selector = (SearchSelector)selectorList.get(i);
/*     */ 
/*  68 */             if (selector.getIsSelected()) {
/*  69 */               value = selector.getName();
/*  70 */               break;
/*     */             }
/*     */           }
/*  73 */           if ((!StringUtil.isEmpty(value)) && 
/*  74 */             (!this.propMap.containsKey(multiKey))) {
/*  75 */             this.propMap.put(multiKey, value);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*  80 */       else if ((!key.equals("cat")) && (!key.equals("tag"))) {
/*  81 */         String value = "";
/*  82 */         List selectorList = (List)(List)this.selectorMap.get(key);
/*     */ 
/*  84 */         for (int i = 1; i < selectorList.size(); i++) {
/*  85 */           SearchSelector selector = (SearchSelector)selectorList.get(i);
/*     */ 
/*  87 */           if (selector.getIsSelected()) {
/*  88 */             value = selector.getName();
/*  89 */             break;
/*     */           }
/*     */         }
/*  92 */         if ((!StringUtil.isEmpty(value)) && 
/*  93 */           (!this.fieldMap.containsKey(key))) {
/*  94 */           this.fieldMap.put(key, value);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 100 */     if ((this.tag != null) && (this.tag.equals("4")))
/* 101 */       this.fieldMap.put("tag", "特价");
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */   {
/*     */     try
/*     */     {
/* 116 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 117 */       Configuration cfg = new Configuration();
/* 118 */       cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 119 */       cfg.setDefaultEncoding("UTF-8");
/* 120 */       cfg.setLocale(Locale.CHINA);
/* 121 */       cfg.setEncoding(Locale.CHINA, "UTF-8");
/* 122 */       cfg.setClassForTemplateLoading(getClass(), "");
/*     */ 
/* 125 */       HashMap data = new HashMap();
/* 126 */       data.put("cat", this.cat);
/* 127 */       data.put("tag", this.tag);
/* 128 */       data.put("propMap", this.propMap);
/* 129 */       data.put("fieldMap", this.fieldMap);
/* 130 */       data.put("sitename", site.getSitename());
/* 131 */       ByteOutputStream stream = new ByteOutputStream();
/* 132 */       Writer out = new OutputStreamWriter(stream);
/* 133 */       cfg.getTemplate("title.html").process(data, out);
/* 134 */       out.flush();
/* 135 */       this.title = StringUtil.replaceEnter(stream.toString());
/*     */ 
/* 137 */       stream = new ByteOutputStream();
/* 138 */       out = new OutputStreamWriter(stream);
/* 139 */       cfg.getTemplate("keywords.html").process(data, out);
/* 140 */       out.flush();
/* 141 */       this.keywords = StringUtil.replaceEnter(stream.toString());
/*     */ 
/* 143 */       stream = new ByteOutputStream();
/* 144 */       out = new OutputStreamWriter(stream);
/* 145 */       cfg.getTemplate("description.html").process(data, out);
/* 146 */       out.flush();
/* 147 */       this.description = StringUtil.replaceEnter(stream.toString());
/*     */     } catch (Exception ex) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/* 153 */     return this.title;
/*     */   }
/*     */ 
/*     */   public String getKeywords() {
/* 157 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 161 */     return this.description;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search2.seo.SearchSeoParser
 * JD-Core Version:    0.6.0
 */