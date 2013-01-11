/*    */ package com.enation.app.shop.component.search.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*    */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.database.IDBRouter;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class CatSearchFilter extends AutoRegisterPlugin
/*    */   implements IGoodsSearchFilter
/*    */ {
/*    */   private IDBRouter baseDBRouter;
/*    */ 
/*    */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*    */   {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public void filter(StringBuffer sql, Cat cat, String urlFragment) {
/* 32 */     FreeMarkerPaser.getInstance().putData("cat", cat);
/* 33 */     if (!StringUtil.isEmpty(urlFragment)) {
/* 34 */       String cat_path = cat.getCat_path();
/* 35 */       if (cat_path != null) {
/* 36 */         sql.append(" and  g.cat_id in(");
/* 37 */         sql.append("select c.cat_id from " + this.baseDBRouter.getTableName("goods_cat"));
/* 38 */         sql.append(" c where c.cat_path like '" + cat_path + "%')");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public IDBRouter getBaseDBRouter() {
/* 44 */     return this.baseDBRouter;
/*    */   }
/*    */ 
/*    */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 48 */     this.baseDBRouter = baseDBRouter;
/*    */   }
/*    */ 
/*    */   public String getFilterId() {
/* 52 */     return "cat";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 57 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId() {
/* 61 */     return "catSearchFilter";
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 65 */     return "商品分类筛选器";
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 69 */     return "searchFilter";
/*    */   }
/*    */ 
/*    */   public String getVersion() {
/* 73 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.CatSearchFilter
 * JD-Core Version:    0.6.0
 */