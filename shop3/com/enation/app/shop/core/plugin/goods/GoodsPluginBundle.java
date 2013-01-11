/*     */ package com.enation.app.shop.core.plugin.goods;
/*     */ 
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.opensymphony.xwork2.ActionContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class GoodsPluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public String getName()
/*     */   {
/*  31 */     return "商品插件桩";
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getTabList()
/*     */   {
/*  36 */     Map tabMap = new TreeMap();
/*  37 */     List plugins = getPlugins();
/*     */ 
/*  39 */     if (plugins != null)
/*     */     {
/*  41 */       List tabList = new ArrayList();
/*  42 */       for (IPlugin plugin : plugins) {
/*  43 */         if ((plugin instanceof IGoodsTabShowEvent)) {
/*  44 */           IGoodsTabShowEvent event = (IGoodsTabShowEvent)plugin;
/*  45 */           String name = event.getTabName();
/*  46 */           if ((!StringUtil.isEmpty(name)) && 
/*  47 */             ((plugin instanceof IGoodsTabShowEvent))) {
/*  48 */             IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
/*  49 */             tabMap.put(Integer.valueOf(tabEvent.getOrder()), name);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  58 */     return tabMap;
/*     */   }
/*     */ 
/*     */   public void onBeforeAdd(Map goods)
/*     */   {
/*  67 */     ActionContext ctx = ActionContext.getContext();
/*  68 */     HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */ 
/*  70 */     List plugins = getPlugins();
/*     */ 
/*  72 */     if (plugins != null)
/*  73 */       for (IPlugin plugin : plugins)
/*     */       {
/*  75 */         if ((plugin instanceof IGoodsBeforeAddEvent)) {
/*  76 */           if (loger.isDebugEnabled()) {
/*  77 */             loger.debug("调用插件 : " + plugin.getClass() + " onBeforeGoodsAdd 开始...");
/*     */           }
/*  79 */           IGoodsBeforeAddEvent event = (IGoodsBeforeAddEvent)plugin;
/*  80 */           event.onBeforeGoodsAdd(goods, request);
/*  81 */           if (loger.isDebugEnabled()) {
/*  82 */             loger.debug("调用插件 : " + plugin.getClass() + " onBeforeGoodsAdd 结束.");
/*     */           }
/*     */         }
/*  85 */         else if (loger.isDebugEnabled()) {
/*  86 */           loger.debug(" no,skip...");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onAfterAdd(Map goods)
/*     */   {
/* 105 */     ActionContext ctx = ActionContext.getContext();
/* 106 */     HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */ 
/* 108 */     List plugins = getPlugins();
/*     */ 
/* 110 */     if (plugins != null)
/* 111 */       for (IPlugin plugin : plugins)
/*     */       {
/* 113 */         if (loger.isDebugEnabled()) {
/* 114 */           loger.debug("check plugin : " + plugin.getClass() + " is IGoodsAfterAddEvent ?");
/*     */         }
/*     */ 
/* 118 */         if ((plugin instanceof IGoodsAfterAddEvent)) {
/* 119 */           if (loger.isDebugEnabled()) {
/* 120 */             loger.debug(" yes ,do event...");
/*     */           }
/* 122 */           IGoodsAfterAddEvent event = (IGoodsAfterAddEvent)plugin;
/* 123 */           event.onAfterGoodsAdd(goods, request);
/*     */         }
/* 125 */         else if (loger.isDebugEnabled()) {
/* 126 */           loger.debug(" no,skip...");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> onFillAddInputData()
/*     */   {
/* 144 */     Map htmlMap = new TreeMap();
/*     */ 
/* 146 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 148 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 149 */     freeMarkerPaser.putData("goods", new HashMap(0));
/* 150 */     List plugins = getPlugins();
/*     */ 
/* 152 */     if (plugins != null) {
/* 153 */       for (IPlugin plugin : plugins)
/*     */       {
/* 156 */         if ((plugin instanceof IGetGoodsAddHtmlEvent)) {
/* 157 */           IGetGoodsAddHtmlEvent event = (IGetGoodsAddHtmlEvent)plugin;
/* 158 */           freeMarkerPaser.setClz(event.getClass());
/* 159 */           String html = event.getAddHtml(request);
/* 160 */           if ((!StringUtil.isEmpty(html)) && 
/* 161 */             ((plugin instanceof IGoodsTabShowEvent))) {
/* 162 */             IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
/* 163 */             htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 171 */     return htmlMap;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> onFillEditInputData(Map goods)
/*     */   {
/* 180 */     Map htmlMap = new TreeMap();
/* 181 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 182 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 183 */     freeMarkerPaser.putData("goods", goods);
/* 184 */     List plugins = getPlugins();
/*     */ 
/* 186 */     if (plugins != null) {
/* 187 */       for (IPlugin plugin : plugins)
/*     */       {
/* 189 */         if ((plugin instanceof IGetGoodsEditHtmlEvent)) {
/* 190 */           IGetGoodsEditHtmlEvent event = (IGetGoodsEditHtmlEvent)plugin;
/* 191 */           freeMarkerPaser.setClz(event.getClass());
/* 192 */           freeMarkerPaser.setPageName(null);
/* 193 */           String html = event.getEditHtml(goods, request);
/* 194 */           if ((!StringUtil.isEmpty(html)) && 
/* 195 */             ((plugin instanceof IGoodsTabShowEvent))) {
/* 196 */             IGoodsTabShowEvent tabEvent = (IGoodsTabShowEvent)plugin;
/* 197 */             htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 203 */     return htmlMap;
/*     */   }
/*     */ 
/*     */   public void onBeforeEdit(Map goods)
/*     */   {
/* 212 */     ActionContext ctx = ActionContext.getContext();
/* 213 */     HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */ 
/* 215 */     List plugins = getPlugins();
/*     */ 
/* 217 */     if (plugins != null)
/* 218 */       for (IPlugin plugin : plugins)
/*     */       {
/* 220 */         if ((plugin instanceof IGoodsBeforeEditEvent))
/*     */         {
/* 222 */           if (loger.isDebugEnabled()) {
/* 223 */             loger.debug("调用插件[" + plugin.getClass() + "] onBeforeGoodsEdit 开始...");
/*     */           }
/* 225 */           IGoodsBeforeEditEvent event = (IGoodsBeforeEditEvent)plugin;
/* 226 */           event.onBeforeGoodsEdit(goods, request);
/* 227 */           if (loger.isDebugEnabled())
/* 228 */             loger.debug("调用插件[" + plugin.getClass() + "] onBeforeGoodsEdit 结束.");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onAfterEdit(Map goods)
/*     */   {
/* 240 */     ActionContext ctx = ActionContext.getContext();
/* 241 */     HttpServletRequest request = (HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */ 
/* 243 */     List plugins = getPlugins();
/*     */ 
/* 245 */     if (plugins != null)
/* 246 */       for (IPlugin plugin : plugins)
/*     */       {
/* 248 */         if ((plugin instanceof IGoodsAfterEditEvent)) {
/* 249 */           IGoodsAfterEditEvent event = (IGoodsAfterEditEvent)plugin;
/* 250 */           event.onAfterGoodsEdit(goods, request);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onVisit(Map goods)
/*     */   {
/* 262 */     List plugins = getPlugins();
/*     */ 
/* 264 */     if (plugins != null)
/* 265 */       for (IPlugin plugin : plugins)
/*     */       {
/* 267 */         if ((plugin instanceof IGoodsVisitEvent)) {
/* 268 */           IGoodsVisitEvent event = (IGoodsVisitEvent)plugin;
/* 269 */           event.onVisit(goods);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/* 284 */     List plugins = getPlugins();
/*     */ 
/* 286 */     if (plugins != null)
/* 287 */       for (IPlugin plugin : plugins)
/*     */       {
/* 289 */         if ((plugin instanceof IGoodsDeleteEvent)) {
/* 290 */           IGoodsDeleteEvent event = (IGoodsDeleteEvent)plugin;
/* 291 */           event.onGoodsDelete(goodsid);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public String onGetSelector()
/*     */   {
/* 304 */     StringBuffer sql = new StringBuffer();
/* 305 */     List plugins = getPlugins();
/*     */ 
/* 307 */     if (plugins != null) {
/* 308 */       for (IPlugin plugin : plugins)
/*     */       {
/* 310 */         if ((plugin instanceof IGoodsSearchFilter)) {
/* 311 */           IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
/* 312 */           sql.append(event.getSelector());
/*     */         }
/*     */       }
/*     */     }
/* 316 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   public String onGetFrom()
/*     */   {
/* 322 */     StringBuffer sql = new StringBuffer();
/* 323 */     List plugins = getPlugins();
/*     */ 
/* 325 */     if (plugins != null) {
/* 326 */       for (IPlugin plugin : plugins)
/*     */       {
/* 328 */         if ((plugin instanceof IGoodsSearchFilter)) {
/* 329 */           IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
/* 330 */           sql.append(event.getFrom());
/*     */         }
/*     */       }
/*     */     }
/* 334 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   public void onSearchFilter(StringBuffer sql)
/*     */   {
/* 339 */     List plugins = getPlugins();
/*     */ 
/* 341 */     if (plugins != null)
/* 342 */       for (IPlugin plugin : plugins)
/*     */       {
/* 344 */         if ((plugin instanceof IGoodsSearchFilter)) {
/* 345 */           IGoodsSearchFilter event = (IGoodsSearchFilter)plugin;
/* 346 */           event.filter(sql);
/*     */         }
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.GoodsPluginBundle
 * JD-Core Version:    0.6.0
 */