/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.plugin.SitemapPluginBundle;
/*    */ import com.enation.app.base.core.service.ISitemapManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CreateSitemapAction extends WWAction
/*    */ {
/*    */   private ISitemapManager sitemapManager;
/*    */   private SitemapPluginBundle sitemapPluginBundle;
/*    */ 
/*    */   public String recreate()
/*    */   {
/* 19 */     clean();
/* 20 */     this.sitemapPluginBundle.onRecreateMap();
/* 21 */     this.msgs.add("站点地图创建成功");
/* 22 */     this.urls.put("访问站点地图", "/sitemap.xml");
/* 23 */     return "message";
/*    */   }
/*    */   private void clean() {
/* 26 */     this.sitemapManager.clean();
/*    */   }
/*    */ 
/*    */   public ISitemapManager getSitemapManager() {
/* 30 */     return this.sitemapManager;
/*    */   }
/*    */   public void setSitemapManager(ISitemapManager sitemapManager) {
/* 33 */     this.sitemapManager = sitemapManager;
/*    */   }
/*    */   public SitemapPluginBundle getSitemapPluginBundle() {
/* 36 */     return this.sitemapPluginBundle;
/*    */   }
/*    */   public void setSitemapPluginBundle(SitemapPluginBundle sitemapPluginBundle) {
/* 39 */     this.sitemapPluginBundle = sitemapPluginBundle;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.CreateSitemapAction
 * JD-Core Version:    0.6.0
 */