/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.plugin.search.GoodsDataFilterBundle;
/*     */ import com.enation.app.shop.core.plugin.search.GoodsSearchPluginBundle;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IGoodsSortFilter;
/*     */ import com.enation.app.shop.core.plugin.search.IMultiSelector;
/*     */ import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsSearchManager2;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.eop.processor.core.UrlNotFoundException;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ public class GoodsSearchManager2 extends BaseSupport
/*     */   implements IGoodsSearchManager2
/*     */ {
/*     */   private GoodsSearchPluginBundle goodsSearchPluginBundle;
/*     */   private GoodsDataFilterBundle goodsDataFilterBundle;
/*     */   private IGoodsCatManager goodsCatManager;
/*     */ 
/*     */   public Map<String, Object> getSelector(String url)
/*     */   {
/*  42 */     Cat cat = getCat(url);
/*     */ 
/*  44 */     Map selectorMap = new HashMap();
/*     */ 
/*  46 */     List filterList = this.goodsSearchPluginBundle.getPluginList();
/*  47 */     for (IGoodsSearchFilter filter : filterList)
/*     */     {
/*  51 */       String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
/*  52 */       String exSelfurl = UrlUtils.getExParamUrl(url, filter.getFilterId()).replaceAll(".html", "");
/*     */ 
/*  54 */       List selectorList = filter.createSelectorList(cat, exSelfurl, urlFragment);
/*     */ 
/*  56 */       if (selectorList != null) {
/*  57 */         selectorMap.put(filter.getFilterId(), selectorList);
/*     */       }
/*     */ 
/*  63 */       if ((filter instanceof IMultiSelector)) {
/*  64 */         Map multiSelector = ((IMultiSelector)filter).createMultiSelector(cat, exSelfurl, urlFragment);
/*  65 */         if (multiSelector != null)
/*  66 */           selectorMap.put(filter.getFilterId(), multiSelector);
/*     */       }
/*     */     }
/*  69 */     return selectorMap;
/*     */   }
/*     */ 
/*     */   public void putParams(Map<String, Object> params, String url)
/*     */   {
/*  77 */     List filterList = this.goodsSearchPluginBundle.getPluginList();
/*  78 */     for (IGoodsSearchFilter filter : filterList)
/*  79 */       if ((filter instanceof IPutWidgetParamsEvent)) {
/*  80 */         String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
/*  81 */         String exSelfurl = UrlUtils.getExParamUrl(url, filter.getFilterId()).replaceAll(".html", "");
/*  82 */         IPutWidgetParamsEvent event = (IPutWidgetParamsEvent)filter;
/*  83 */         event.putParams(params, urlFragment, exSelfurl);
/*     */       }
/*     */   }
/*     */ 
/*     */   public Cat getCat(String url)
/*     */   {
/*  95 */     Cat cat = null;
/*  96 */     String catidstr = UrlUtils.getParamStringValue(url, "cat");
/*  97 */     if ((!StringUtil.isEmpty(catidstr)) && (!"0".equals(catidstr))) {
/*  98 */       Integer catid = Integer.valueOf(catidstr);
/*  99 */       cat = this.goodsCatManager.getById(catid.intValue());
/* 100 */       if (cat == null) {
/* 101 */         throw new UrlNotFoundException();
/*     */       }
/*     */     }
/*     */ 
/* 105 */     return cat;
/*     */   }
/*     */ 
/*     */   public Page search(int pageNo, int pageSize, String url) {
/* 109 */     List list = list(pageNo, pageSize, url);
/* 110 */     int count = count(url);
/* 111 */     Page webPage = new Page(0L, count, pageSize, list);
/* 112 */     return webPage;
/*     */   }
/*     */ 
/*     */   public List list(int pageNo, int pageSize, String url)
/*     */   {
/* 117 */     StringBuffer sql = new StringBuffer();
/* 118 */     sql.append("select g.* from ");
/* 119 */     sql.append(getTableName("goods"));
/* 120 */     sql.append(" g where g.goods_type = 'normal' and g.disabled=0 and market_enable=1 ");
/*     */ 
/* 128 */     filterTerm(sql, url);
/*     */ 
/* 131 */     RowMapper mapper = new RowMapper()
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/* 135 */         Map goodsMap = new HashMap();
/*     */ 
/* 137 */         goodsMap.put("name", rs.getString("name"));
/* 138 */         goodsMap.put("goods_id", Integer.valueOf(rs.getInt("goods_id")));
/* 139 */         goodsMap.put("image_default", rs.getString("image_default"));
/* 140 */         goodsMap.put("mktprice", Double.valueOf(rs.getDouble("mktprice")));
/* 141 */         goodsMap.put("price", Double.valueOf(rs.getDouble("price")));
/* 142 */         goodsMap.put("create_time", Long.valueOf(rs.getLong("create_time")));
/* 143 */         goodsMap.put("last_modify", Long.valueOf(rs.getLong("last_modify")));
/* 144 */         goodsMap.put("type_id", Integer.valueOf(rs.getInt("type_id")));
/* 145 */         goodsMap.put("store", Integer.valueOf(rs.getInt("store")));
/* 146 */         goodsMap.put("have_spec", Integer.valueOf(rs.getInt("have_spec")));
/* 147 */         goodsMap.put("sn", rs.getString("sn"));
/* 148 */         goodsMap.put("intro", rs.getString("intro"));
/* 149 */         goodsMap.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
/* 150 */         goodsMap.put("istejia", Integer.valueOf(rs.getInt("istejia")));
/*     */ 
/* 152 */         GoodsSearchManager2.this.filterGoods(goodsMap, rs);
/*     */ 
/* 154 */         return goodsMap;
/*     */       }
/*     */     };
/* 159 */     List goodslist = this.daoSupport.queryForList(sql.toString(), pageNo, pageSize, mapper);
/*     */ 
/* 161 */     return goodslist;
/*     */   }
/*     */ 
/*     */   private final void filterGoods(Map<String, Object> goods, ResultSet rs)
/*     */   {
/* 173 */     List filterList = this.goodsDataFilterBundle.getPluginList();
/* 174 */     if (filterList == null) return;
/* 175 */     for (IGoodsDataFilter filter : filterList)
/* 176 */       filter.filter(goods, rs);
/*     */   }
/*     */ 
/*     */   private void applyMemPrice(List<Map> proList, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 187 */     for (Map pro : proList) {
/* 188 */       double price = Double.valueOf(pro.get("price").toString()).doubleValue() * discount;
/* 189 */       for (GoodsLvPrice lvPrice : memPriceList) {
/* 190 */         if (((Integer)pro.get("product_id")).intValue() == lvPrice.getProductid()) {
/* 191 */           price = lvPrice.getPrice().doubleValue();
/*     */         }
/*     */       }
/*     */ 
/* 195 */       pro.put("price", Double.valueOf(price));
/*     */     }
/*     */   }
/*     */ 
/*     */   private int count(String url)
/*     */   {
/* 205 */     StringBuffer sql = new StringBuffer("select count(0) from " + getTableName("goods") + " g where g.disabled=0 and market_enable=1 ");
/*     */ 
/* 207 */     filterTerm(sql, url);
/* 208 */     return this.daoSupport.queryForInt(sql.toString(), new Object[0]);
/*     */   }
/*     */ 
/*     */   private String noSpace(String text) {
/* 212 */     String[] s = text.split(" ");
/* 213 */     String r = "";
/* 214 */     for (int i = 0; i < s.length; i++) {
/* 215 */       if (!"".equals(s[i]))
/* 216 */         r = r + s[i];
/*     */     }
/* 218 */     return r;
/*     */   }
/*     */ 
/*     */   private void filterTerm(StringBuffer sql, String url)
/*     */   {
/* 230 */     Cat cat = getCat(url);
/*     */ 
/* 233 */     StringBuffer sortStr = new StringBuffer();
/*     */ 
/* 235 */     List filterList = this.goodsSearchPluginBundle.getPluginList();
/* 236 */     for (IGoodsSearchFilter filter : filterList) {
/* 237 */       String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
/* 238 */       if ((filter instanceof IGoodsSortFilter))
/* 239 */         filter.filter(sortStr, cat, urlFragment);
/*     */       else {
/* 241 */         filter.filter(sql, cat, urlFragment);
/*     */       }
/*     */     }
/* 244 */     if (!noSpace(sql.toString()).startsWith("selectcount"))
/* 245 */       sql.append(sortStr);
/*     */   }
/*     */ 
/*     */   public GoodsSearchPluginBundle getGoodsSearchPluginBundle()
/*     */   {
/* 251 */     return this.goodsSearchPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsSearchPluginBundle(GoodsSearchPluginBundle goodsSearchPluginBundle)
/*     */   {
/* 256 */     this.goodsSearchPluginBundle = goodsSearchPluginBundle;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 261 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
/*     */   {
/* 266 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public GoodsDataFilterBundle getGoodsDataFilterBundle()
/*     */   {
/* 271 */     return this.goodsDataFilterBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle)
/*     */   {
/* 276 */     this.goodsDataFilterBundle = goodsDataFilterBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsSearchManager2
 * JD-Core Version:    0.6.0
 */