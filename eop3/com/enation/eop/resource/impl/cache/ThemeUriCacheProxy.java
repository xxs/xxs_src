/*     */ package com.enation.eop.resource.impl.cache;
/*     */ 
/*     */ import com.enation.eop.processor.core.UrlNotFoundException;
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ThemeUriCacheProxy extends AbstractCacheProxy<List<ThemeUri>>
/*     */   implements IThemeUriManager
/*     */ {
/*     */   private IThemeUriManager themeUriManager;
/*     */   public static final String LIST_KEY_PREFIX = "theme_uri_list_";
/*     */ 
/*     */   public void clean()
/*     */   {
/*  28 */     this.themeUriManager.clean();
/*     */   }
/*     */   public void add(ThemeUri uri) {
/*  31 */     this.themeUriManager.add(uri);
/*  32 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void edit(List<ThemeUri> uriList) {
/*  36 */     this.themeUriManager.edit(uriList);
/*  37 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void edit(ThemeUri themeUri) {
/*  41 */     this.themeUriManager.edit(themeUri);
/*  42 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public ThemeUriCacheProxy(IThemeUriManager themeUriManager)
/*     */   {
/*  47 */     super("themeUriCache");
/*  48 */     this.themeUriManager = themeUriManager;
/*     */   }
/*     */ 
/*     */   private void cleanCache() {
/*  52 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  53 */     Integer userid = site.getUserid();
/*  54 */     Integer siteid = site.getId();
/*  55 */     this.cache.remove("theme_uri_list_" + userid + "_" + siteid);
/*     */   }
/*     */ 
/*     */   public List<ThemeUri> list() {
/*  59 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  60 */     Integer userid = site.getUserid();
/*  61 */     Integer siteid = site.getId();
/*  62 */     List uriList = (List)this.cache.get("theme_uri_list_" + userid + "_" + siteid);
/*     */ 
/*  64 */     if (uriList == null)
/*     */     {
/*  68 */       uriList = this.themeUriManager.list();
/*     */ 
/*  70 */       this.cache.put("theme_uri_list_" + userid + "_" + siteid, uriList);
/*     */     }
/*     */ 
/*  77 */     return uriList;
/*     */   }
/*     */ 
/*     */   public ThemeUri getPath(String uri)
/*     */   {
/*  86 */     if (uri.equals("/")) {
/*  87 */       uri = "/index.html";
/*     */     }
/*     */ 
/*  93 */     List uriList = list();
/*  94 */     for (ThemeUri themeUri : uriList) {
/*  95 */       Matcher m = themeUri.getPattern().matcher(uri);
/*     */ 
/* 101 */       if (m.find())
/*     */       {
/* 108 */         return themeUri;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 115 */     throw new UrlNotFoundException();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 125 */     Pattern p = Pattern.compile("/goods-(\\d+).html", 34);
/* 126 */     Matcher m = p.matcher("/goods-1.html");
/* 127 */     if (m.find()) {
/* 128 */       System.out.println("found...");
/* 129 */       String s = m.replaceAll("/goods.jsp");
/* 130 */       System.out.println(s);
/*     */     } else {
/* 132 */       System.out.println("not found...");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(int id)
/*     */   {
/* 138 */     this.themeUriManager.delete(id);
/* 139 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public ThemeUri get(Integer id) {
/* 143 */     return this.themeUriManager.get(id);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.cache.ThemeUriCacheProxy
 * JD-Core Version:    0.6.0
 */