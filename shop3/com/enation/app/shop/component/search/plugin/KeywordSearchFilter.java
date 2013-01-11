/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class KeywordSearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter, IPutWidgetParamsEvent
/*     */ {
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  31 */     List selectorList = new ArrayList();
/*  32 */     String keyword = urlFragment == null ? "" : urlFragment;
/*  33 */     keyword = keyword.replaceAll("-xie-", "/");
/*     */ 
/*  35 */     SearchSelector selector0 = new SearchSelector();
/*  36 */     selector0.setName("全部");
/*  37 */     selector0.setUrl("");
/*  38 */     selector0.setSelected(false);
/*  39 */     selectorList.add(selector0);
/*     */ 
/*  41 */     SearchSelector selector1 = new SearchSelector();
/*  42 */     selector1.setName(keyword);
/*  43 */     selector1.setUrl("");
/*  44 */     selector1.setSelected(true);
/*  45 */     selectorList.add(selector1);
/*     */ 
/*  47 */     return selectorList;
/*     */   }
/*     */ 
/*     */   public void putParams(Map<String, Object> params, String urlFragment, String url) {
/*  51 */     String keyword = urlFragment == null ? "" : urlFragment;
/*  52 */     keyword = keyword.replaceAll("-xie-", "/");
/*  53 */     params.put("keyword", keyword);
/*     */   }
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment) {
/*  57 */     String keyword = urlFragment == null ? "" : urlFragment;
/*     */ 
/*  65 */     if (!StringUtil.isEmpty(keyword)) {
/*  66 */       keyword = keyword.replaceAll("-xie-", "/").trim();
/*  67 */       keyword = keyword.replaceAll("%", "").trim();
/*  68 */       keyword = keyword.replaceAll("'", "").trim();
/*     */ 
/*  70 */       String[] keys = StringUtils.split(keyword, "\\s");
/*     */ 
/*  72 */       for (String key : keys) {
/*  73 */         sql.append(" and g.name like '%");
/*  74 */         sql.append(key);
/*  75 */         sql.append("%'");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  83 */     String keyword = "测试的  123   0000   11   22";
/*  84 */     String[] keys = keyword.split("\\s");
/*  85 */     for (String key : keys)
/*  86 */       System.out.println(key);
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/*  92 */     return "keyword";
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  98 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 104 */     return "keywordSearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 110 */     return "关键字搜索过滤器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 116 */     return "searchFilter";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 122 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.search.plugin.KeywordSearchFilter
 * JD-Core Version:    0.6.0
 */