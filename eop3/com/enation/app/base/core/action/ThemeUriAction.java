/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ThemeUriAction extends WWAction
/*     */ {
/*     */   private IThemeUriManager themeUriManager;
/*     */   private List uriList;
/*     */   private ThemeUri themeUri;
/*     */   private int id;
/*     */   private int[] ids;
/*     */   private String[] uri;
/*     */   private String[] path;
/*     */   private String[] pagename;
/*     */   private int[] point;
/*     */   private int[] httpcache;
/*     */ 
/*     */   public String list()
/*     */   {
/*  23 */     this.uriList = this.themeUriManager.list();
/*  24 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  29 */     return "input";
/*     */   }
/*     */   public String edit() {
/*  32 */     this.themeUri = this.themeUriManager.get(Integer.valueOf(this.id));
/*  33 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  38 */       this.themeUriManager.add(this.themeUri);
/*  39 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  41 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  43 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try {
/*  49 */       this.themeUriManager.edit(this.themeUri);
/*  50 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  52 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  54 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String batchEdit()
/*     */   {
/*     */     try {
/*  60 */       List uriList = new ArrayList();
/*  61 */       if (this.uri != null) {
/*  62 */         int i = 0; for (int len = this.uri.length; i < len; i++) {
/*  63 */           ThemeUri themeUri = new ThemeUri();
/*  64 */           themeUri.setUri(this.uri[i]);
/*  65 */           themeUri.setId(Integer.valueOf(this.ids[i]));
/*  66 */           themeUri.setPath(this.path[i]);
/*  67 */           themeUri.setPagename(this.pagename[i]);
/*  68 */           themeUri.setPoint(this.point[i]);
/*  69 */           themeUri.setHttpcache(this.httpcache[i]);
/*  70 */           uriList.add(themeUri);
/*     */         }
/*     */       }
/*  73 */       this.themeUriManager.edit(uriList);
/*  74 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  76 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  78 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try {
/*  84 */       this.themeUriManager.delete(this.id);
/*  85 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  87 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*     */ 
/*  90 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IThemeUriManager getThemeUriManager() {
/*  94 */     return this.themeUriManager;
/*     */   }
/*     */ 
/*     */   public void setThemeUriManager(IThemeUriManager themeUriManager) {
/*  98 */     this.themeUriManager = themeUriManager;
/*     */   }
/*     */ 
/*     */   public List getUriList() {
/* 102 */     return this.uriList;
/*     */   }
/*     */ 
/*     */   public void setUriList(List uriList) {
/* 106 */     this.uriList = uriList;
/*     */   }
/*     */ 
/*     */   public ThemeUri getThemeUri() {
/* 110 */     return this.themeUri;
/*     */   }
/*     */ 
/*     */   public void setThemeUri(ThemeUri themeUri) {
/* 114 */     this.themeUri = themeUri;
/*     */   }
/*     */ 
/*     */   public String[] getUri() {
/* 118 */     return this.uri;
/*     */   }
/*     */ 
/*     */   public void setUri(String[] uri) {
/* 122 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   public String[] getPath() {
/* 126 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String[] path) {
/* 130 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String[] getPagename() {
/* 134 */     return this.pagename;
/*     */   }
/*     */ 
/*     */   public void setPagename(String[] pagename) {
/* 138 */     this.pagename = pagename;
/*     */   }
/*     */ 
/*     */   public int[] getPoint() {
/* 142 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(int[] point) {
/* 146 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public int getId() {
/* 150 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/* 154 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public int[] getIds() {
/* 158 */     return this.ids;
/*     */   }
/*     */ 
/*     */   public void setIds(int[] ids) {
/* 162 */     this.ids = ids;
/*     */   }
/*     */ 
/*     */   public int[] getHttpcache()
/*     */   {
/* 167 */     return this.httpcache;
/*     */   }
/*     */ 
/*     */   public void setHttpcache(int[] httpcache)
/*     */   {
/* 172 */     this.httpcache = httpcache;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.ThemeUriAction
 * JD-Core Version:    0.6.0
 */