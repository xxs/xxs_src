/*    */ package com.enation.app.base.core.service.impl.cache;
/*    */ 
/*    */ import com.enation.app.base.core.model.SiteMenu;
/*    */ import com.enation.app.base.core.service.ISiteMenuManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.cache.AbstractCacheProxy;
/*    */ import com.enation.framework.cache.ICache;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SiteMenuCacheProxy extends AbstractCacheProxy<List<SiteMenu>>
/*    */   implements ISiteMenuManager
/*    */ {
/*    */   public static final String MENU_LIST_CACHE_KEY = "siteMenuList";
/*    */   private ISiteMenuManager siteMenuManager;
/*    */ 
/*    */   public SiteMenuCacheProxy(ISiteMenuManager siteMenuManager)
/*    */   {
/* 15 */     super("siteMenuList");
/* 16 */     this.siteMenuManager = siteMenuManager;
/*    */   }
/*    */ 
/*    */   private void cleanCache()
/*    */   {
/* 21 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 22 */     this.cache.remove("siteMenuList_" + site.getUserid() + "_" + site.getId());
/*    */   }
/*    */ 
/*    */   public void add(SiteMenu siteMenu)
/*    */   {
/* 27 */     this.siteMenuManager.add(siteMenu);
/* 28 */     cleanCache();
/*    */   }
/*    */ 
/*    */   public void delete(Integer id)
/*    */   {
/* 33 */     this.siteMenuManager.delete(id);
/* 34 */     cleanCache();
/*    */   }
/*    */ 
/*    */   public void edit(SiteMenu siteMenu)
/*    */   {
/* 40 */     this.siteMenuManager.edit(siteMenu);
/* 41 */     cleanCache();
/*    */   }
/*    */ 
/*    */   public SiteMenu get(Integer menuid)
/*    */   {
/* 46 */     return this.siteMenuManager.get(menuid);
/*    */   }
/*    */ 
/*    */   public List<SiteMenu> list(Integer parentid)
/*    */   {
/* 51 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 52 */     List menuList = (List)this.cache.get("siteMenuList_" + site.getUserid() + "_" + site.getId());
/*    */ 
/* 55 */     if (menuList == null) {
/* 56 */       menuList = this.siteMenuManager.list(parentid);
/* 57 */       this.cache.put("siteMenuList_" + site.getUserid() + "_" + site.getId(), menuList);
/* 58 */       if (this.logger.isDebugEnabled()) {
/* 59 */         this.logger.debug("load sitemenu from database");
/*    */       }
/*    */     }
/* 62 */     else if (this.logger.isDebugEnabled()) {
/* 63 */       this.logger.debug("load sitemenu from cache");
/*    */     }
/*    */ 
/* 67 */     return menuList;
/*    */   }
/*    */ 
/*    */   public void updateSort(Integer[] menuid, Integer[] sort)
/*    */   {
/* 72 */     this.siteMenuManager.updateSort(menuid, sort);
/* 73 */     cleanCache();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SiteMenuCacheProxy
 * JD-Core Version:    0.6.0
 */