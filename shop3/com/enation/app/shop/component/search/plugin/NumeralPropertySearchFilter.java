/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class NumeralPropertySearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter, IPutWidgetParamsEvent
/*     */ {
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  28 */     return null;
/*     */   }
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*     */   {
/*  35 */     if (StringUtil.isEmpty(urlFragment)) return;
/*     */ 
/*  38 */     String[] prop_values = urlFragment.split(",");
/*  39 */     for (String propvalue : prop_values)
/*  40 */       if (!StringUtil.isEmpty(propvalue)) {
/*  41 */         String[] ar = propvalue.split("_");
/*  42 */         if (ar.length != 2)
/*     */           continue;
/*  44 */         sql.append(" and ");
/*  45 */         sql.append(ar[0]);
/*  46 */         sql.append("=");
/*  47 */         sql.append(ar[1]);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void putParams(Map<String, Object> params, String urlFragment, String url)
/*     */   {
/*  60 */     if (StringUtil.isEmpty(urlFragment)) return;
/*     */ 
/*  62 */     String[] prop_values = urlFragment.split(",");
/*  63 */     for (String propvalue : prop_values)
/*  64 */       if (!StringUtil.isEmpty(propvalue)) {
/*  65 */         String[] ar = propvalue.split("_");
/*  66 */         if (ar.length == 2)
/*  67 */           params.put(ar[0], ar[1]);
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/*  75 */     return "nattr";
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  81 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  87 */     return "numeralPropertySearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  93 */     return "数字属性搜索过滤器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  99 */     return "searchFilter";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 105 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void register()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.NumeralPropertySearchFilter
 * JD-Core Version:    0.6.0
 */