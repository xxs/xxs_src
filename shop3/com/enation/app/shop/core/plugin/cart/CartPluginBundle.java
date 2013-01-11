/*     */ package com.enation.app.shop.core.plugin.cart;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cart;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CartPluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public OrderPrice coutPrice(OrderPrice orderpice)
/*     */   {
/*     */     try
/*     */     {
/*  15 */       List plugins = getPlugins();
/*     */ 
/*  17 */       if (plugins != null) {
/*  18 */         for (IPlugin plugin : plugins) {
/*  19 */           if ((plugin instanceof ICountPriceEvent)) {
/*  20 */             if (loger.isDebugEnabled()) {
/*  21 */               loger.debug("调用插件 : " + plugin.getClass() + "cart.countPrice开始...");
/*     */             }
/*  23 */             ICountPriceEvent event = (ICountPriceEvent)plugin;
/*  24 */             orderpice = event.countPrice(orderpice);
/*  25 */             if (loger.isDebugEnabled()) {
/*  26 */               loger.debug("调用插件 : " + plugin.getClass() + " cart.countPrice结束.");
/*     */             }
/*     */           }
/*  29 */           else if (loger.isDebugEnabled()) {
/*  30 */             loger.debug(" no,skip...");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  38 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  42 */     return orderpice;
/*     */   }
/*     */ 
/*     */   public void filterList(List<CartItem> itemList, String sessionid) {
/*     */     try {
/*  47 */       List plugins = getPlugins();
/*     */ 
/*  49 */       if (plugins != null) {
/*  50 */         for (IPlugin plugin : plugins) {
/*  51 */           if ((plugin instanceof ICartItemFilter)) {
/*  52 */             if (loger.isDebugEnabled()) {
/*  53 */               loger.debug("调用插件 : " + plugin.getClass() + "cart.add开始...");
/*     */             }
/*  55 */             ICartItemFilter event = (ICartItemFilter)plugin;
/*  56 */             event.filter(itemList, sessionid);
/*  57 */             if (loger.isDebugEnabled()) {
/*  58 */               loger.debug("调用插件 : " + plugin.getClass() + " cart.add结束.");
/*     */             }
/*     */           }
/*  61 */           else if (loger.isDebugEnabled()) {
/*  62 */             loger.debug(" no,skip...");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  70 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAdd(Cart cart)
/*     */   {
/*     */     try {
/*  77 */       List plugins = getPlugins();
/*     */ 
/*  79 */       if (plugins != null) {
/*  80 */         for (IPlugin plugin : plugins) {
/*  81 */           if ((plugin instanceof ICartAddEvent)) {
/*  82 */             if (loger.isDebugEnabled()) {
/*  83 */               loger.debug("调用插件 : " + plugin.getClass() + "cart.add开始...");
/*     */             }
/*  85 */             ICartAddEvent event = (ICartAddEvent)plugin;
/*  86 */             event.add(cart);
/*  87 */             if (loger.isDebugEnabled()) {
/*  88 */               loger.debug("调用插件 : " + plugin.getClass() + " cart.add结束.");
/*     */             }
/*     */           }
/*  91 */           else if (loger.isDebugEnabled()) {
/*  92 */             loger.debug(" no,skip...");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 100 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onDelete(String sessionid, Integer cartid)
/*     */   {
/*     */     try
/*     */     {
/* 108 */       List plugins = getPlugins();
/*     */ 
/* 110 */       if (plugins != null) {
/* 111 */         for (IPlugin plugin : plugins) {
/* 112 */           if ((plugin instanceof ICartDeleteEvent)) {
/* 113 */             if (loger.isDebugEnabled()) {
/* 114 */               loger.debug("调用插件 : " + plugin.getClass() + "cart.delete开始...");
/*     */             }
/* 116 */             ICartDeleteEvent event = (ICartDeleteEvent)plugin;
/* 117 */             event.delete(sessionid, cartid);
/* 118 */             if (loger.isDebugEnabled()) {
/* 119 */               loger.debug("调用插件 : " + plugin.getClass() + " cart.delete结束.");
/*     */             }
/*     */           }
/* 122 */           else if (loger.isDebugEnabled()) {
/* 123 */             loger.debug(" no,skip...");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 131 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 140 */     return "购物车插件桩";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.cart.CartPluginBundle
 * JD-Core Version:    0.6.0
 */