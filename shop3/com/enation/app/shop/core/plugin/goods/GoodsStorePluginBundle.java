/*     */ package com.enation.app.shop.core.plugin.goods;
/*     */ 
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class GoodsStorePluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public String getStoreHtml(Map goods)
/*     */   {
/*  28 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  29 */     freeMarkerPaser.putData("goods", goods);
/*  30 */     String html = null;
/*  31 */     List plugins = getPlugins();
/*     */ 
/*  33 */     if (plugins != null) {
/*  34 */       for (IPlugin plugin : plugins) {
/*  35 */         if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */         {
/*  37 */           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*  38 */           freeMarkerPaser.setClz(event.getClass());
/*     */ 
/*  40 */           if (event.canBeExecute(goods)) {
/*  41 */             html = event.getStoreHtml(goods);
/*     */ 
/*  43 */             if (loger.isDebugEnabled()) {
/*  44 */               loger.debug("处理商品[" + goods.get("name") + "]获取商品存维护页面html事件:执行插件[" + plugin.getClass() + "]");
/*     */             }
/*     */ 
/*     */           }
/*  48 */           else if (loger.isDebugEnabled()) {
/*  49 */             loger.debug("处理商品[" + goods.get("name") + "]获取商品存维护页面html事件:插件[" + plugin.getClass() + "]不被执行");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  58 */     return html;
/*     */   }
/*     */ 
/*     */   public String getStockHtml(Map goods)
/*     */   {
/*  69 */     List plugins = getPlugins();
/*     */ 
/*  71 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  72 */     freeMarkerPaser.putData("goods", goods);
/*  73 */     String html = null;
/*  74 */     if (plugins != null) {
/*  75 */       for (IPlugin plugin : plugins) {
/*  76 */         if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */         {
/*  78 */           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*  79 */           freeMarkerPaser.setClz(event.getClass());
/*     */ 
/*  81 */           if (event.canBeExecute(goods)) {
/*  82 */             html = event.getStockHtml(goods);
/*     */ 
/*  84 */             if (loger.isDebugEnabled()) {
/*  85 */               loger.debug("处理商品[" + goods.get("name") + "]获取商品进货页面html事件:执行插件[" + plugin.getClass() + "]");
/*     */             }
/*     */ 
/*     */           }
/*  89 */           else if (loger.isDebugEnabled()) {
/*  90 */             loger.debug("处理商品[" + goods.get("name") + "]获取商品进货页面html事件:插件[" + plugin.getClass() + "]不被执行");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  99 */     return html;
/*     */   }
/*     */ 
/*     */   public String getShipHtml(Map goods)
/*     */   {
/* 113 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 114 */     freeMarkerPaser.putData("goods", goods);
/* 115 */     String html = null;
/* 116 */     List plugins = getPlugins();
/*     */ 
/* 118 */     if (plugins != null) {
/* 119 */       for (IPlugin plugin : plugins) {
/* 120 */         if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */         {
/* 122 */           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/* 123 */           freeMarkerPaser.setClz(event.getClass());
/*     */ 
/* 125 */           if (event.canBeExecute(goods)) {
/* 126 */             html = event.getShipHtml(goods);
/*     */ 
/* 128 */             if (loger.isDebugEnabled()) {
/* 129 */               loger.debug("处理商品[" + goods.get("name") + "]获取商品出货页面html事件:执行插件[" + plugin.getClass() + "]");
/*     */             }
/*     */ 
/*     */           }
/* 133 */           else if (loger.isDebugEnabled()) {
/* 134 */             loger.debug("处理商品[" + goods.get("name") + "]获取商品出货页面html事件:插件[" + plugin.getClass() + "]不被执行");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     return html;
/*     */   }
/*     */ 
/*     */   public void onStoreSave(Map goods)
/*     */   {
/* 156 */     List plugins = getPlugins();
/*     */ 
/* 158 */     for (IPlugin plugin : plugins)
/* 159 */       if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */       {
/* 161 */         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*     */ 
/* 163 */         if (event.canBeExecute(goods)) {
/* 164 */           event.onStoreSave(goods);
/* 165 */           if (loger.isDebugEnabled()) {
/* 166 */             loger.debug("处理商品[" + goods.get("name") + "]库存保存事件:执行插件[" + plugin.getClass() + "]");
/*     */           }
/*     */ 
/*     */         }
/* 170 */         else if (loger.isDebugEnabled()) {
/* 171 */           loger.debug("处理商品[" + goods.get("name") + "]库存保存事件:插件[" + plugin.getClass() + "]不被执行");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getWarnHtml(Map goods)
/*     */   {
/* 188 */     List plugins = getPlugins();
/*     */ 
/* 190 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 191 */     freeMarkerPaser.putData("goods", goods);
/* 192 */     String html = null;
/* 193 */     if (plugins != null) {
/* 194 */       for (IPlugin plugin : plugins) {
/* 195 */         if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */         {
/* 197 */           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/* 198 */           freeMarkerPaser.setClz(event.getClass());
/*     */ 
/* 200 */           if (event.canBeExecute(goods)) {
/* 201 */             html = event.getWarnNumHtml(goods);
/*     */ 
/* 203 */             if (loger.isDebugEnabled()) {
/* 204 */               loger.debug("处理商品[" + goods.get("name") + "]获取商品报警设置页面html事件:执行插件[" + plugin.getClass() + "]");
/*     */             }
/*     */ 
/*     */           }
/* 208 */           else if (loger.isDebugEnabled()) {
/* 209 */             loger.debug("处理商品[" + goods.get("name") + "]获取商品报警设置页面html事件:插件[" + plugin.getClass() + "]不被执行");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 215 */     return html;
/*     */   }
/*     */ 
/*     */   public void onWarnSave(Map goods)
/*     */   {
/* 224 */     List plugins = getPlugins();
/*     */ 
/* 226 */     for (IPlugin plugin : plugins)
/* 227 */       if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */       {
/* 229 */         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*     */ 
/* 231 */         if (event.canBeExecute(goods)) {
/* 232 */           event.onWarnSave(goods);
/* 233 */           if (loger.isDebugEnabled()) {
/* 234 */             loger.debug("处理商品[" + goods.get("name") + "]报警保存事件:执行插件[" + plugin.getClass() + "]");
/*     */           }
/*     */ 
/*     */         }
/* 238 */         else if (loger.isDebugEnabled()) {
/* 239 */           loger.debug("处理商品[" + goods.get("name") + "]库存保存事件:插件[" + plugin.getClass() + "]不被执行");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onStockSave(Map goods)
/*     */   {
/* 256 */     List plugins = getPlugins();
/*     */ 
/* 258 */     for (IPlugin plugin : plugins)
/* 259 */       if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */       {
/* 261 */         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*     */ 
/* 263 */         if (event.canBeExecute(goods)) {
/* 264 */           event.onStockSave(goods);
/* 265 */           if (loger.isDebugEnabled()) {
/* 266 */             loger.debug("处理商品[" + goods.get("name") + "]进货事件:执行插件[" + plugin.getClass() + "]");
/*     */           }
/*     */ 
/*     */         }
/* 270 */         else if (loger.isDebugEnabled()) {
/* 271 */           loger.debug("处理商品[" + goods.get("name") + "]进货事件:插件[" + plugin.getClass() + "]不被执行");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onShipSave(Map goods)
/*     */   {
/* 288 */     List plugins = getPlugins();
/*     */ 
/* 290 */     for (IPlugin plugin : plugins)
/* 291 */       if ((plugin instanceof AbstractGoodsStorePlugin))
/*     */       {
/* 293 */         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
/*     */ 
/* 295 */         if (event.canBeExecute(goods)) {
/* 296 */           event.onShipSave(goods);
/* 297 */           if (loger.isDebugEnabled()) {
/* 298 */             loger.debug("处理商品[" + goods.get("name") + "]出货事件:执行插件[" + plugin.getClass() + "]");
/*     */           }
/*     */ 
/*     */         }
/* 302 */         else if (loger.isDebugEnabled()) {
/* 303 */           loger.debug("处理商品[" + goods.get("name") + "]出货事件:插件[" + plugin.getClass() + "]不被执行");
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 317 */     return "商品库存插件桩";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.GoodsStorePluginBundle
 * JD-Core Version:    0.6.0
 */