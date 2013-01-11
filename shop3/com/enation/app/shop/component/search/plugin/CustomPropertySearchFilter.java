/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IMultiSelector;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class CustomPropertySearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter, IMultiSelector
/*     */ {
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */ 
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */   public Map<String, List<SearchSelector>> createMultiSelector(Cat cat, String url, String urlFragment)
/*     */   {
/*  41 */     if (cat == null) return null;
/*     */ 
/*  45 */     if (!StringUtil.isEmpty(urlFragment))
/*     */     {
/*  47 */       url = UrlUtils.addUrl(url, "prop", urlFragment);
/*  48 */       url = "/search-" + UrlUtils.getParamStr(url);
/*     */     }
/*     */ 
/*  51 */     List attrList = this.goodsTypeManager.getAttrListByTypeId(cat.getType_id().intValue());
/*     */ 
/*  53 */     attrList = attrList == null ? new ArrayList() : attrList;
/*     */ 
/*  55 */     Map map = new LinkedHashMap();
/*  56 */     String[] s_ar = StringUtil.isEmpty(urlFragment) ? new String[0] : StringUtils.split(urlFragment, ",");
/*  57 */     int i = 0;
/*     */ 
/*  59 */     for (Attribute attr : attrList)
/*     */     {
/*  61 */       String attrName = attr.getName();
/*     */ 
/*  63 */       if (attr.getType() == 3) {
/*  64 */         List selectorList = new ArrayList();
/*  65 */         String[] optionAr = attr.getOptionAr();
/*  66 */         int j = 0;
/*     */ 
/*  68 */         boolean haveSelected = false;
/*  69 */         SearchSelector allSelector = new SearchSelector();
/*  70 */         allSelector.setName("全部");
/*  71 */         allSelector.setSelected(false);
/*  72 */         allSelector.setUrl(UrlUtils.getExParamUrl(UrlUtils.getPropExSelf(i, url), "page"));
/*  73 */         selectorList.add(allSelector);
/*     */ 
/*  76 */         for (String option : optionAr)
/*     */         {
/*  79 */           SearchSelector selector = new SearchSelector();
/*  80 */           selector.setName(option);
/*  81 */           selector.setSelected(isSelected(s_ar, i, j));
/*  82 */           if (selector.getIsSelected()) haveSelected = true;
/*     */ 
/*  85 */           String propurl = UrlUtils.appendParamValue(url, getFilterId(), i + "_" + j);
/*     */ 
/*  88 */           selector.setUrl(propurl);
/*  89 */           selectorList.add(selector);
/*     */ 
/*  91 */           j++;
/*     */ 
/*  93 */           if ((j == optionAr.length) && (!haveSelected)) {
/*  94 */             allSelector.setSelected(true);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  99 */         map.put(attrName, selectorList);
/*     */       }
/*     */ 
/* 102 */       i++;
/*     */     }
/* 104 */     return map;
/*     */   }
/*     */ 
/*     */   private boolean isSelected(String[] s_ar, int attrIndex, int optionIndex)
/*     */   {
/* 116 */     for (int i = 0; i < s_ar.length; i++) {
/* 117 */       String[] value = s_ar[i].split("\\_");
/* 118 */       int attr_index = Integer.valueOf(value[0]).intValue();
/* 119 */       int option_index = Integer.valueOf(value[1]).intValue();
/*     */ 
/* 121 */       if ((attrIndex == attr_index) && (option_index == optionIndex)) {
/* 122 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*     */   {
/* 133 */     if (cat == null) return;
/* 134 */     if (StringUtil.isEmpty(urlFragment)) return;
/*     */ 
/* 138 */     if ((urlFragment != null) && (!urlFragment.equals(""))) {
/* 139 */       List prop_list = this.goodsTypeManager.getAttrListByTypeId(cat.getType_id().intValue());
/* 140 */       prop_list = prop_list == null ? new ArrayList() : prop_list;
/* 141 */       String[] s_ar = StringUtils.split(urlFragment, ",");
/*     */ 
/* 143 */       for (int i = 0; i < s_ar.length; i++) {
/* 144 */         String[] value = s_ar[i].split("\\_");
/* 145 */         int index = Integer.valueOf(value[0]).intValue();
/* 146 */         Attribute attr = (Attribute)prop_list.get(index);
/* 147 */         int type = attr.getType();
/* 148 */         if ((type == 2) || (type == 5))
/*     */           continue;
/* 150 */         sql.append(" and g.p" + (index + 1));
/*     */ 
/* 152 */         if (type == 1) {
/* 153 */           sql.append(" like'%");
/* 154 */           sql.append(value[1]);
/* 155 */           sql.append("%'");
/*     */         }
/* 157 */         if ((type == 3) || (type == 4)) {
/* 158 */           sql.append("='");
/* 159 */           sql.append(value[1]);
/* 160 */           sql.append("'");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/* 171 */     return "prop";
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 177 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 183 */     return "goodsPropertySearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 189 */     return "商品属性过滤器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 195 */     return "searchFilter";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 201 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void register()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager()
/*     */   {
/* 214 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
/*     */   {
/* 219 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.CustomPropertySearchFilter
 * JD-Core Version:    0.6.0
 */