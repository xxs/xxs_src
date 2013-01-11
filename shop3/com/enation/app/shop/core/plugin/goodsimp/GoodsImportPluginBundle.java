/*    */ package com.enation.app.shop.core.plugin.goodsimp;
/*    */ 
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import com.enation.framework.plugin.IPlugin;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ public class GoodsImportPluginBundle extends AutoRegisterPluginsBundle
/*    */ {
/*    */   public String getName()
/*    */   {
/* 20 */     return "商品批量导入插件桩";
/*    */   }
/*    */ 
/*    */   public void onBeforeImport(Document configDoc)
/*    */   {
/*    */     try {
/* 26 */       List plugins = getPlugins();
/*    */ 
/* 28 */       if (plugins != null) {
/* 29 */         for (IPlugin plugin : plugins)
/* 30 */           if ((plugin instanceof IBeforeGoodsImportEvent)) {
/* 31 */             if (loger.isDebugEnabled()) {
/* 32 */               loger.debug("调用插件 : " + plugin.getClass() + " onBeforeImport 开始...");
/*    */             }
/* 34 */             IBeforeGoodsImportEvent event = (IBeforeGoodsImportEvent)plugin;
/* 35 */             event.onBeforeImport(configDoc);
/* 36 */             if (loger.isDebugEnabled())
/* 37 */               loger.debug("调用插件 : " + plugin.getClass() + " onBeforeImport 结束.");
/*    */           }
/*    */       }
/*    */     }
/*    */     catch (RuntimeException e)
/*    */     {
/* 43 */       if (loger.isErrorEnabled())
/* 44 */         loger.error("调用商品导入插件[导入前]事件错误", e);
/* 45 */       throw e;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goodsimp.GoodsImportPluginBundle
 * JD-Core Version:    0.6.0
 */