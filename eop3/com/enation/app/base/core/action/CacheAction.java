/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.framework.action.WWAction;
/*    */ import net.sf.ehcache.Cache;
/*    */ import net.sf.ehcache.CacheManager;
/*    */ import net.sf.ehcache.statistics.LiveCacheStatistics;
/*    */ 
/*    */ public class CacheAction extends WWAction
/*    */ {
/*    */   public String execute()
/*    */   {
/* 12 */     CacheManager manager = CacheManager.getInstance();
/* 13 */     Cache cache = manager.getCache("widgetCache");
/*    */ 
/* 15 */     LiveCacheStatistics statistis = cache.getLiveCacheStatistics();
/* 16 */     boolean memory = statistis.isStatisticsEnabled();
/*    */ 
/* 26 */     return "list";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.CacheAction
 * JD-Core Version:    0.6.0
 */