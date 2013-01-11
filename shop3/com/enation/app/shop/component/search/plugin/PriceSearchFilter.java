/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ @Component
/*     */ public class PriceSearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter
/*     */ {
/*     */   private static Map<String, List<Price>> priceMap;
/*     */ 
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  41 */     String catid = "0";
/*  42 */     if (cat != null) catid = cat.getCat_id().toString();
/*     */ 
/*  44 */     String currMin = "";
/*  45 */     String currMax = "";
/*     */ 
/*  47 */     if (!StringUtil.isEmpty(urlFragment))
/*     */     {
/*  49 */       String[] price = urlFragment.split("_");
/*  50 */       if ((price != null) && (price.length >= 1)) {
/*  51 */         currMin = price[0];
/*     */       }
/*  53 */       if ((price != null) && (price.length >= 2)) {
/*  54 */         currMax = price[1];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  59 */     List list = new ArrayList();
/*     */ 
/*  64 */     SearchSelector allselector = new SearchSelector();
/*  65 */     allselector.setName("全部");
/*  66 */     allselector.setUrl(UrlUtils.getExParamUrl(url, "page") + ".html");
/*  67 */     if (StringUtil.isEmpty(urlFragment))
/*  68 */       allselector.setSelected(true);
/*     */     else {
/*  70 */       allselector.setSelected(false);
/*     */     }
/*  72 */     list.add(allselector);
/*     */ 
/*  75 */     Map pMap = getPriceMap();
/*  76 */     List priceList = (List)pMap.get(catid);
/*  77 */     if (priceList == null) return null;
/*     */ 
/*  79 */     for (Price price : priceList)
/*     */     {
/*  81 */       String max = price.getMax();
/*  82 */       String min = price.getMin();
/*     */ 
/*  84 */       min = min == null ? "" : min;
/*  85 */       max = max == null ? "" : max;
/*     */ 
/*  87 */       String text = price.getText();
/*     */ 
/*  90 */       SearchSelector selector = new SearchSelector();
/*  91 */       selector.setName(text);
/*  92 */       String priceUrl = min + "_" + max;
/*  93 */       selector.setUrl(UrlUtils.addUrl(url, "price", priceUrl));
/*     */ 
/*  95 */       if ((currMin.equals(min)) && (currMax.equals(max))) {
/*  96 */         selector.setSelected(true);
/*     */       }
/*     */ 
/* 100 */       list.add(selector);
/*     */     }
/*     */ 
/* 104 */     return list;
/*     */   }
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*     */   {
/* 110 */     if (!StringUtil.isEmpty(urlFragment))
/*     */     {
/* 112 */       String[] price = urlFragment.split("_");
/* 113 */       if ((price != null) && (price.length >= 1) && (!StringUtil.isEmpty(price[0]))) {
/* 114 */         sql.append(" and  g.price>=" + price[0]);
/*     */       }
/* 116 */       if ((price != null) && (price.length >= 2) && (!StringUtil.isEmpty(price[1])))
/* 117 */         sql.append(" and g.price<" + price[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Map<String, List<Price>> getPriceMap()
/*     */   {
/* 127 */     if (priceMap != null) return priceMap;
/* 128 */     String xmlFile = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/price_filter.xml";
/*     */     try
/*     */     {
/* 132 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 134 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 135 */       Document document = builder.parse(xmlFile);
/*     */ 
/* 137 */       priceMap = new HashMap();
/*     */ 
/* 139 */       NodeList catList = document.getElementsByTagName("cat");
/* 140 */       for (int i = 0; i < catList.getLength(); i++)
/*     */       {
/* 142 */         List priceList = new ArrayList();
/*     */ 
/* 144 */         Element catNode = (Element)catList.item(i);
/* 145 */         NodeList priceNodeList = catNode.getElementsByTagName("price");
/* 146 */         for (int j = 0; j < priceNodeList.getLength(); j++)
/*     */         {
/* 149 */           Element priceEl = (Element)priceNodeList.item(j);
/* 150 */           String text = priceEl.getTextContent();
/* 151 */           String minPrice = priceEl.getAttribute("min");
/* 152 */           String maxPrice = priceEl.getAttribute("max");
/* 153 */           Price price = new Price();
/* 154 */           price.setText(text);
/*     */ 
/* 156 */           if (!StringUtil.isEmpty(minPrice)) {
/* 157 */             price.setMin(minPrice);
/*     */           }
/* 159 */           if (!StringUtil.isEmpty(maxPrice)) {
/* 160 */             price.setMax(maxPrice);
/*     */           }
/*     */ 
/* 163 */           priceList.add(price);
/*     */         }
/*     */ 
/* 167 */         priceMap.put(catNode.getAttribute("id"), priceList);
/*     */       }
/*     */ 
/* 171 */       return priceMap;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 175 */       e.printStackTrace();
/* 176 */     }throw new RuntimeException("load  price_filter.xml   error");
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/* 183 */     return "price";
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 189 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 195 */     return "priceSearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 201 */     return "价格搜索过虑器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 207 */     return "searchFilter";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 213 */     return "1.0";
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
 * Qualified Name:     com.enation.app.shop.component.search.plugin.PriceSearchFilter
 * JD-Core Version:    0.6.0
 */