/*     */ package com.enation.app.shop.component.search.plugin;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Brand;
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.SearchSelector;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class BrandSearchFilter extends AutoRegisterPlugin
/*     */   implements IGoodsSearchFilter
/*     */ {
/*     */   private IBrandManager brandManager;
/*     */ 
/*     */   public void filter(StringBuffer sql, Cat cat, String urlFragment)
/*     */   {
/*  28 */     if ((!StringUtil.isEmpty(urlFragment)) && (!"0".equals(urlFragment)))
/*  29 */       sql.append(" and g.brand_id=" + urlFragment);
/*     */   }
/*     */ 
/*     */   public String getFilterId()
/*     */   {
/*  35 */     return "brand";
/*     */   }
/*     */ 
/*     */   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
/*     */   {
/*  41 */     List selectorList = new ArrayList();
/*     */ 
/*  44 */     List brandList = null;
/*     */ 
/*  46 */     if (cat != null)
/*  47 */       brandList = this.brandManager.listByTypeId(cat.getType_id());
/*     */     else {
/*  49 */       brandList = this.brandManager.list();
/*     */     }
/*     */ 
/*  56 */     SearchSelector allselector = new SearchSelector();
/*  57 */     allselector.setName("全部");
/*  58 */     allselector.setUrl(UrlUtils.getExParamUrl(url, "page") + ".html");
/*  59 */     if ((StringUtil.isEmpty(urlFragment)) || ("0".equals(urlFragment)))
/*  60 */       allselector.setSelected(true);
/*     */     else {
/*  62 */       allselector.setSelected(false);
/*     */     }
/*  64 */     selectorList.add(allselector);
/*     */ 
/*  66 */     for (Brand brand : brandList) {
/*  67 */       SearchSelector selector = new SearchSelector();
/*  68 */       selector.setName(brand.getName());
/*  69 */       selector.setUrl(UrlUtils.addUrl(url, "brand", brand.getBrand_id().toString()));
/*  70 */       allselector.setValue(urlFragment);
/*  71 */       if (brand.getBrand_id().toString().equals(urlFragment))
/*  72 */         selector.setSelected(true);
/*     */       else {
/*  74 */         selector.setSelected(false);
/*     */       }
/*  76 */       selectorList.add(selector);
/*     */     }
/*     */ 
/*  79 */     return selectorList;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  84 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  89 */     return "brandSearchFilter";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  94 */     return "品牌搜索过虑器";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  99 */     return "goodssearch";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 104 */     return "1.0";
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
/*     */   public IBrandManager getBrandManager() {
/* 116 */     return this.brandManager;
/*     */   }
/*     */ 
/*     */   public void setBrandManager(IBrandManager brandManager) {
/* 120 */     this.brandManager = brandManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.BrandSearchFilter
 * JD-Core Version:    0.6.0
 */