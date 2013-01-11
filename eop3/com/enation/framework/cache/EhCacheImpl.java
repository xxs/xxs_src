/*     */ package com.enation.framework.cache;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import net.sf.ehcache.Cache;
/*     */ import net.sf.ehcache.CacheException;
/*     */ import net.sf.ehcache.CacheManager;
/*     */ import net.sf.ehcache.Element;
/*     */ 
/*     */ public class EhCacheImpl
/*     */   implements ICache
/*     */ {
/*     */   private Cache cache;
/*     */ 
/*     */   public EhCacheImpl(String name)
/*     */   {
/*     */     try
/*     */     {
/*  24 */       CacheManager manager = CacheManager.getInstance();
/*  25 */       this.cache = manager.getCache(name);
/*     */ 
/*  27 */       if (this.cache == null) {
/*  28 */         manager.addCache(name);
/*  29 */         this.cache = manager.getCache(name);
/*     */       }
/*     */     } catch (CacheException e) {
/*  32 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(Object key)
/*     */   {
/*  45 */     Object obj = null;
/*     */     try {
/*  47 */       if (key != null) {
/*  48 */         Element element = this.cache.get((Serializable)key);
/*  49 */         if (element != null)
/*  50 */           obj = element.getValue();
/*     */       }
/*     */     }
/*     */     catch (CacheException e) {
/*  54 */       e.printStackTrace();
/*     */     }
/*  56 */     return obj;
/*     */   }
/*     */ 
/*     */   public void put(Object key, Object value)
/*     */   {
/*     */     try
/*     */     {
/*  69 */       Element element = new Element((Serializable)key, (Serializable)value);
/*  70 */       this.cache.put(element);
/*     */     } catch (IllegalArgumentException e) {
/*  72 */       e.printStackTrace();
/*     */     } catch (IllegalStateException e) {
/*  74 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void remove(Object key)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       this.cache.remove((Serializable)key);
/*     */     } catch (ClassCastException e) {
/*  89 */       e.printStackTrace();
/*     */     } catch (IllegalStateException e) {
/*  91 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*     */     try
/*     */     {
/* 103 */       this.cache.removeAll();
/*     */     } catch (IllegalStateException e) {
/* 105 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 110 */     EhCacheImpl cache = new EhCacheImpl("queryCache");
/*     */ 
/* 112 */     System.out.println(cache.get("test"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.cache.EhCacheImpl
 * JD-Core Version:    0.6.0
 */