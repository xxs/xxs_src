/*    */ package com.enation.framework.cache;
/*    */ 
/*    */ public final class CacheFactory
/*    */ {
/*    */   public static final String APP_CACHE_NAME_KEY = "appCache";
/*    */   public static final String THEMEURI_CACHE_NAME_KEY = "themeUriCache";
/*    */   public static final String SITE_CACHE_NAME_KEY = "siteCache";
/*    */   public static final String WIDGET_CACHE_NAME_KEY = "widgetCache";
/*    */ 
/*    */   public static <T> ICache<T> getCache(String name)
/*    */   {
/* 18 */     ICache ehCache = new EhCacheImpl(name);
/* 19 */     return ehCache;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.cache.CacheFactory
 * JD-Core Version:    0.6.0
 */