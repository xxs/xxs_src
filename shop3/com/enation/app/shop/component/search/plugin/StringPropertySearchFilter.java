/*    */ package com.enation.app.shop.component.search.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*    */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class StringPropertySearchFilter extends AutoRegisterPlugin
/*    */   implements IGoodsSearchFilter
/*    */ {
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ 
/*    */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*    */   {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getFilterId()
/*    */   {
/* 40 */     return "sprop";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 46 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 52 */     return "stringPropertySearchFilter";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 58 */     return "字串属性搜索过虑器";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 64 */     return "searchFilter";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 70 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.StringPropertySearchFilter
 * JD-Core Version:    0.6.0
 */