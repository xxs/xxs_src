/*    */ package com.enation.app.shop.component.search.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*    */ import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
/*    */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class TagSearchFilter extends AutoRegisterPlugin
/*    */   implements IGoodsSearchFilter, IPutWidgetParamsEvent
/*    */ {
/*    */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*    */   {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*    */   {
/* 33 */     if (!StringUtil.isEmpty(urlFragment))
/* 34 */       sql.append(" and goods_id in (select rel_id from es_tag_rel where tag_id in(" + urlFragment + ") )");
/*    */   }
/*    */ 
/*    */   public void putParams(Map<String, Object> params, String urlFragment, String url)
/*    */   {
/* 40 */     if (!StringUtil.isEmpty(urlFragment))
/* 41 */       params.put("tag", urlFragment);
/*    */   }
/*    */ 
/*    */   public String getFilterId()
/*    */   {
/* 48 */     return "tag";
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 54 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 60 */     return "tagSearchFilter";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 66 */     return "商品标签搜索过虑器";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 72 */     return "searchFilter";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 78 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.search.plugin.TagSearchFilter
 * JD-Core Version:    0.6.0
 */