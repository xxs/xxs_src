/*    */ package com.enation.framework.cache;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class AbstractCacheProxy<T>
/*    */ {
/* 14 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   protected ICache<T> cache;
/*    */ 
/*    */   public AbstractCacheProxy(String cacheName)
/*    */   {
/* 19 */     this.cache = CacheFactory.getCache(cacheName);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.cache.AbstractCacheProxy
 * JD-Core Version:    0.6.0
 */