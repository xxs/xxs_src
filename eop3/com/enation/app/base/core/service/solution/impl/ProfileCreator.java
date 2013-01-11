/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.eop.resource.IIndexItemManager;
/*     */ import com.enation.eop.resource.IMenuManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.IThemeUriManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopApp;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.IndexItem;
/*     */ import com.enation.eop.resource.model.Menu;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.output.Format;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ public class ProfileCreator
/*     */ {
/*     */   private IThemeUriManager themeUriManager;
/*     */   private IMenuManager menuManager;
/*     */   private IThemeManager themeManager;
/*     */   private IIndexItemManager indexItemManager;
/*     */   private IUserManager userManager;
/*     */   private ISiteManager siteManager;
/*     */ 
/*     */   public void createProfile(String path)
/*     */   {
/*  37 */     Element product = new Element("product");
/*     */ 
/*  39 */     List appList = this.siteManager.getSiteApps();
/*     */ 
/*  45 */     Element apps = new Element("apps");
/*  46 */     for (EopApp app : appList)
/*     */     {
/*  48 */       Element appEl = new Element("app");
/*  49 */       appEl.setAttribute("id", app.getAppid());
/*  50 */       appEl.setAttribute("version", app.getVersion());
/*  51 */       apps.addContent(appEl);
/*     */     }
/*     */ 
/*  54 */     product.addContent(apps);
/*     */ 
/*  60 */     Element site = new Element("site");
/*  61 */     fillSiteElement(site);
/*  62 */     product.addContent(site);
/*     */ 
/*  67 */     Element urlsEl = new Element("urls");
/*  68 */     fillUrlElement(urlsEl);
/*  69 */     product.addContent(urlsEl);
/*     */ 
/*  74 */     Element menusEl = new Element("menus");
/*  75 */     fillMenuElement(menusEl);
/*  76 */     product.addContent(menusEl);
/*     */ 
/*  81 */     Element themesEl = new Element("themes");
/*  82 */     fillThemesElement(themesEl);
/*  83 */     product.addContent(themesEl);
/*     */ 
/*  85 */     Element indexItemEl = new Element("indexitems");
/*  86 */     fillIndexItemElement(indexItemEl);
/*  87 */     product.addContent(indexItemEl);
/*     */ 
/*  89 */     Document pfDocument = new Document(product);
/*  90 */     outputDocumentToFile(pfDocument, path);
/*     */   }
/*     */ 
/*     */   private void addSiteElement(Element site, String name, String value)
/*     */   {
/*  95 */     if (value != null) {
/*  96 */       Element element = new Element("field");
/*  97 */       element.setAttribute("name", name);
/*  98 */       element.setAttribute("value", value);
/*  99 */       site.addContent(element);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fillSiteElement(Element site)
/*     */   {
/* 108 */     EopSite eopSite = EopContext.getContext().getCurrentSite();
/* 109 */     addSiteElement(site, "sitename", eopSite.getSitename());
/* 110 */     addSiteElement(site, "title", eopSite.getTitle());
/* 111 */     addSiteElement(site, "keywords", eopSite.getKeywords());
/* 112 */     addSiteElement(site, "descript", eopSite.getDescript());
/* 113 */     addSiteElement(site, "copyright", eopSite.getCopyright());
/* 114 */     addSiteElement(site, "logofile", eopSite.getLogofile());
/* 115 */     addSiteElement(site, "bklogofile", eopSite.getBklogofile());
/* 116 */     addSiteElement(site, "bkloginpicfile", eopSite.getBkloginpicfile());
/* 117 */     addSiteElement(site, "icofile", eopSite.getIcofile());
/* 118 */     addSiteElement(site, "username", eopSite.getUsername());
/* 119 */     addSiteElement(site, "usersex", eopSite.getUsersex() == null ? null : eopSite.getUsersex().toString());
/* 120 */     addSiteElement(site, "usertel", eopSite.getUsertel());
/* 121 */     addSiteElement(site, "usermobile", eopSite.getUsermobile());
/* 122 */     addSiteElement(site, "usertel1", eopSite.getUsertel1());
/* 123 */     addSiteElement(site, "useremail", eopSite.getUseremail());
/* 124 */     addSiteElement(site, "state", eopSite.getState() == null ? null : eopSite.getState().toString());
/* 125 */     addSiteElement(site, "qqlist", eopSite.getQqlist());
/* 126 */     addSiteElement(site, "msnlist", eopSite.getMsnlist());
/* 127 */     addSiteElement(site, "wwlist", eopSite.getWwlist());
/* 128 */     addSiteElement(site, "tellist", eopSite.getTellist());
/* 129 */     addSiteElement(site, "worktime", eopSite.getWorktime());
/* 130 */     addSiteElement(site, "address", eopSite.getAddress());
/* 131 */     addSiteElement(site, "zipcode", eopSite.getZipcode());
/* 132 */     addSiteElement(site, "linkman", eopSite.getLinkman());
/* 133 */     addSiteElement(site, "email", eopSite.getEmail());
/* 134 */     addSiteElement(site, "siteon", eopSite.getSiteon().toString() == null ? null : eopSite.getSiteon().toString());
/* 135 */     addSiteElement(site, "closereson", eopSite.getClosereson());
/*     */   }
/*     */ 
/*     */   private void fillUrlElement(Element uris)
/*     */   {
/* 143 */     List uriList = this.themeUriManager.list();
/*     */ 
/* 145 */     for (ThemeUri themeUri : uriList)
/*     */     {
/* 147 */       String uri = themeUri.getUri();
/* 148 */       String path = themeUri.getPath();
/* 149 */       String pagename = themeUri.getPagename();
/* 150 */       pagename = pagename == null ? "" : pagename;
/* 151 */       Integer sitemaptype = Integer.valueOf(themeUri.getSitemaptype());
/* 152 */       Integer point = Integer.valueOf(themeUri.getPoint());
/* 153 */       Element urlEl = new Element("url");
/* 154 */       urlEl.setAttribute("from", uri);
/* 155 */       urlEl.setAttribute("to", path);
/* 156 */       urlEl.setAttribute("name", pagename);
/* 157 */       urlEl.setAttribute("point", point.toString());
/* 158 */       urlEl.setAttribute("sitemaptype", sitemaptype.toString());
/* 159 */       uris.addContent(urlEl);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fillMenuElement(Element menuParentEl)
/*     */   {
/* 169 */     List menuTree = this.menuManager.getMenuTree(Integer.valueOf(0));
/* 170 */     fillChildMenu(menuTree, menuParentEl);
/*     */   }
/*     */ 
/*     */   private void fillChildMenu(List<Menu> menuList, Element parentEl)
/*     */   {
/* 179 */     for (Menu menu : menuList)
/* 180 */       if (menu.getMenutype().intValue() != 1) {
/* 181 */         Element menuEl = new Element("menu");
/* 182 */         menuEl.setAttribute("text", menu.getTitle());
/* 183 */         String url = menu.getUrl();
/* 184 */         if (!StringUtil.isEmpty(url)) {
/* 185 */           menuEl.setAttribute("url", url);
/*     */         }
/*     */ 
/* 188 */         String target = menu.getTarget();
/* 189 */         if (!StringUtil.isEmpty(target)) {
/* 190 */           menuEl.setAttribute("target", target);
/*     */         }
/*     */ 
/* 193 */         List children = menu.getChildren();
/* 194 */         if ((children != null) && (!children.isEmpty())) {
/* 195 */           fillChildMenu(children, menuEl);
/*     */         }
/* 197 */         parentEl.addContent(menuEl);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void fillThemesElement(Element parentEl)
/*     */   {
/* 206 */     List themeList = this.themeManager.list();
/* 207 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 208 */     for (Theme theme : themeList) {
/* 209 */       Element themeEl = new Element("theme");
/* 210 */       themeEl.setAttribute("id", theme.getPath());
/* 211 */       themeEl.setAttribute("name", theme.getThemename());
/* 212 */       if (site.getThemeid().intValue() == theme.getId().intValue()) {
/* 213 */         themeEl.setAttribute("default", "yes");
/*     */       }
/* 215 */       parentEl.addContent(themeEl);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fillIndexItemElement(Element parentEl)
/*     */   {
/* 224 */     List itemList = this.indexItemManager.list();
/* 225 */     if (itemList == null)
/* 226 */       return;
/* 227 */     for (IndexItem item : itemList) {
/* 228 */       Element itemEl = new Element("item");
/* 229 */       Element titleEl = new Element("title");
/* 230 */       titleEl.setText(item.getTitle());
/* 231 */       itemEl.addContent(titleEl);
/*     */ 
/* 233 */       Element urlEl = new Element("url");
/* 234 */       urlEl.setText(item.getUrl());
/* 235 */       itemEl.addContent(urlEl);
/* 236 */       parentEl.addContent(itemEl);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void outputDocumentToFile(Document myDocument, String path) {
/*     */     try {
/* 242 */       Format format = Format.getCompactFormat();
/* 243 */       format.setEncoding("UTF-8");
/* 244 */       format.setIndent("    ");
/* 245 */       XMLOutputter outputter = new XMLOutputter(format);
/* 246 */       outputter.output(myDocument, new FileOutputStream(path));
/*     */     } catch (IOException e) {
/* 248 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public IThemeUriManager getThemeUriManager() {
/* 253 */     return this.themeUriManager;
/*     */   }
/*     */ 
/*     */   public void setThemeUriManager(IThemeUriManager themeUriManager) {
/* 257 */     this.themeUriManager = themeUriManager;
/*     */   }
/*     */ 
/*     */   public IMenuManager getMenuManager() {
/* 261 */     return this.menuManager;
/*     */   }
/*     */ 
/*     */   public void setMenuManager(IMenuManager menuManager) {
/* 265 */     this.menuManager = menuManager;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/* 269 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/* 273 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public IIndexItemManager getIndexItemManager() {
/* 277 */     return this.indexItemManager;
/*     */   }
/*     */ 
/*     */   public void setIndexItemManager(IIndexItemManager indexItemManager) {
/* 281 */     this.indexItemManager = indexItemManager;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/* 285 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 289 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 293 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 297 */     this.userManager = userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ProfileCreator
 * JD-Core Version:    0.6.0
 */