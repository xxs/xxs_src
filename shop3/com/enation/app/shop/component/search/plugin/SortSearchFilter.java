/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSortFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class SortSearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter, IGoodsSortFilter, IPutWidgetParamsEvent
/*     */ {
/*  44 */   private static final String[] sortItemList = { "默认排序", "价格从高到低", "价格从低到高", "评价从高到低", "评价从低到高", "销量从高到低", "销量从低到高", "上架时间从新到旧", "上架时间从旧到新" };
/*     */ 
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  50 */     String sort = urlFragment == null ? "" : urlFragment;
/*     */ 
/*  52 */     List selectorList = new ArrayList();
/*  53 */     int i = 0;
/*  54 */     for (String item : sortItemList)
/*     */     {
/*  56 */       SearchSelector selector = new SearchSelector();
/*  57 */       selector.setName(item);
/*  58 */       selector.setUrl(UrlUtils.addUrl(url, "sort", "" + (i + 1)));
/*  59 */       selector.setValue(String.valueOf(i + 1));
/*     */ 
/*  61 */       if (sort.equals(String.valueOf(i + 1))) {
/*  62 */         selector.setSelected(true);
/*     */       }
/*     */ 
/*  65 */       selectorList.add(selector);
/*  66 */       i++;
/*     */     }
/*     */ 
/*  69 */     return selectorList;
/*     */   }
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*     */   {
/*  74 */     String order = urlFragment;
/*     */ 
/*  77 */     if ((order == null) || (order.equals(""))) {
/*  78 */       order = "6";
/*     */     }
/*  80 */     if ("1".equals(order))
/*  81 */       order = "sord desc";
/*  82 */     else if ("2".equals(order))
/*  83 */       order = "price desc";
/*  84 */     else if ("3".equals(order))
/*  85 */       order = "price asc";
/*  86 */     else if ("4".equals(order))
/*  87 */       order = "grade desc";
/*  88 */     else if ("5".equals(order))
/*  89 */       order = "grade asc";
/*  90 */     else if ("6".equals(order))
/*  91 */       order = "buy_count desc";
/*  92 */     else if ("7".equals(order))
/*  93 */       order = "buy_count asc";
/*  94 */     else if ("8".equals(order))
/*  95 */       order = "last_modify desc";
/*  96 */     else if ("9".equals(order))
/*  97 */       order = "last_modify asc";
/*  98 */     else if ((order == null) || (order.equals("")) || (order.equals("0"))) {
/*  99 */       order = "sord desc";
/*     */     }
/*     */ 
/* 102 */     sql.append(" order by " + order);
/*     */   }
/*     */ 
/*     */   public void putParams(Map<String, Object> params, String urlFragment, String url)
/*     */   {
/* 112 */     String currSort = urlFragment;
/* 113 */     Map sorter = new HashMap(4);
/*     */ 
/* 115 */     Map price = new HashMap(3);
/* 116 */     price.put("url", UrlUtils.addUrl(url, "sort", "2".equals(currSort) ? "3" : "2"));
/* 117 */     price.put("selected", Boolean.valueOf(("2".equals(currSort)) || ("3".equals(currSort))));
/* 118 */     price.put("sorttype", "2".equals(currSort) ? "desc" : "asc");
/*     */ 
/* 121 */     Map grade = new HashMap(3);
/* 122 */     grade.put("url", UrlUtils.addUrl(url, "sort", "4".equals(currSort) ? "5" : "4"));
/* 123 */     grade.put("selected", Boolean.valueOf(("4".equals(currSort)) || ("5".equals(currSort))));
/* 124 */     grade.put("sorttype", "4".equals(currSort) ? "desc" : "asc");
/*     */ 
/* 127 */     Map sales = new HashMap(3);
/* 128 */     sales.put("url", UrlUtils.addUrl(url, "sort", "6".equals(currSort) ? "7" : "6"));
/* 129 */     sales.put("selected", Boolean.valueOf(("6".equals(currSort)) || ("7".equals(currSort))));
/* 130 */     sales.put("sorttype", "6".equals(currSort) ? "desc" : "asc");
/*     */ 
/* 132 */     Map time = new HashMap(3);
/* 133 */     time.put("url", UrlUtils.addUrl(url, "sort", "8".equals(currSort) ? "9" : "8"));
/* 134 */     time.put("selected", Boolean.valueOf(("8".equals(currSort)) || ("9".equals(currSort))));
/* 135 */     time.put("sorttype", "8".equals(currSort) ? "desc" : "asc");
/*     */ 
/* 137 */     sorter.put("price", price);
/* 138 */     sorter.put("grade", grade);
/* 139 */     sorter.put("sales", sales);
/* 140 */     sorter.put("time", time);
/*     */ 
/* 142 */     params.put("sort", currSort);
/* 143 */     params.put("sorter", sorter);
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/* 148 */     return "sort";
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 154 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 160 */     return "sortSearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 166 */     return "商品排序过滤器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 172 */     return "searchFilter";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 178 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.search.plugin.SortSearchFilter
 * JD-Core Version:    0.6.0
 */