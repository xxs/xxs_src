/*     */ package com.enation.app.shop.component.goodscore.widget.goods.search;
/*     */ 
/*     */ import com.enation.app.base.component.widget.nav.Nav;
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.component.goodscore.widget.goods.search2.pager.SearchPagerDirectiveModel;
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.service.IFavoriteManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsSearchManager;
/*     */ import com.enation.app.shop.core.utils.UrlUtils;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Deprecated
/*     */ @Component("goods_search")
/*     */ @Scope("prototype")
/*     */ public class GoodsSearchWidget extends AbstractWidget
/*     */ {
/*     */   private IGoodsSearchManager goodsSearchManager;
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   private IFavoriteManager favoriteManager;
/*     */   private int cat_id;
/*     */   private String tagids;
/*     */   private String distype;
/*     */   private String order;
/*     */   private String brandStr;
/*     */   private String propStr;
/*     */   private String attrStr;
/*     */   private String keyword;
/*     */   private String minPrice;
/*     */   private String maxPrice;
/*  77 */   int page = 1;
/*     */   private List<Attribute> propList;
/*     */   private List brandList;
/*     */   private List catList;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> wparams)
/*     */   {
/*  94 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  95 */     Nav nav = new Nav();
/*  96 */     nav.setTitle("首页");
/*  97 */     nav.setLink("index.html");
/*  98 */     nav.setTips("首页");
/*  99 */     putNav(nav);
/*     */ 
/* 101 */     Nav nav1 = new Nav();
/* 102 */     nav1.setTitle("商品列表");
/* 103 */     nav1.setTips("商品列表");
/* 104 */     putNav(nav1);
/*     */ 
/* 106 */     String uri = request.getServletPath();
/* 107 */     String p = UrlUtils.getParamStr(uri);
/*     */ 
/* 109 */     initParam(p);
/*     */ 
/* 114 */     this.distype = ((this.distype == null) || (this.distype.equals("")) ? (this.distype = "list") : this.distype);
/*     */ 
/* 117 */     String cat_path = null;
/* 118 */     int type_id = 0;
/*     */ 
/* 120 */     if (this.cat_id != 0)
/*     */     {
/* 122 */       Cat cat = this.goodsCatManager.getById(this.cat_id);
/* 123 */       cat_path = cat.getCat_path();
/* 124 */       type_id = cat.getType_id().intValue();
/*     */ 
/* 128 */       List[] props = this.goodsSearchManager.getPropListByCat(type_id, cat_path, this.brandStr, this.propStr, this.attrStr);
/*     */ 
/* 130 */       this.propList = props[0];
/* 131 */       this.brandList = props[1];
/*     */ 
/* 133 */       int att_index = 0;
/* 134 */       for (Attribute attr : this.propList) {
/* 135 */         Map[] opations = attr.getOptionMap();
/* 136 */         int j = 0;
/* 137 */         for (Map op : opations)
/*     */         {
/* 139 */           op.put("url", att_index + "_" + j);
/* 140 */           j++;
/*     */         }
/* 142 */         String value = getProValue(att_index, this.propStr);
/* 143 */         attr.setValue(value);
/* 144 */         att_index++;
/*     */       }
/*     */ 
/* 147 */       List catList = this.goodsCatManager.listChildren(Integer.valueOf(this.cat_id));
/* 148 */       catList = (catList.size() == 0) || (catList.isEmpty()) ? (catList = null) : catList;
/* 149 */       putData("catList", catList);
/* 150 */       putData("propList", this.propList);
/* 151 */       putData("brandList", this.brandList);
/* 152 */       putData("cat", cat);
/*     */     }
/*     */ 
/* 155 */     Map params = new HashMap();
/* 156 */     params.put("cat_path", cat_path);
/* 157 */     params.put("order", this.order);
/* 158 */     params.put("brandStr", this.brandStr);
/* 159 */     params.put("propStr", this.propStr);
/* 160 */     params.put("keyword", this.keyword);
/* 161 */     params.put("minPrice", this.minPrice);
/* 162 */     params.put("maxPrice", this.maxPrice);
/* 163 */     params.put("tagids", this.tagids);
/* 164 */     params.put("attrStr", this.attrStr);
/* 165 */     params.put("typeid", "" + type_id);
/* 166 */     String pagesizes = (String)wparams.get("pagesize");
/* 167 */     Integer pageSize = Integer.valueOf(pagesizes == null ? 20 : Integer.valueOf(pagesizes).intValue());
/*     */ 
/* 169 */     Page webpage = this.goodsSearchManager.search(this.page, pageSize.intValue(), params);
/*     */ 
/* 171 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 172 */     if (member != null) {
/* 173 */       putData("favoriteList", this.favoriteManager.list());
/*     */     }
/*     */ 
/* 177 */     putData("uri", uri);
/* 178 */     putData("webpage", webpage);
/* 179 */     putData("brandStr", this.brandStr);
/* 180 */     putData("order", this.order);
/* 181 */     putData("distype", this.distype);
/* 182 */     putData("pageno", Integer.valueOf(this.page));
/* 183 */     putData("pagesize", pageSize);
/* 184 */     putData("total", Long.valueOf(webpage.getTotalCount()));
/* 185 */     putData("GoodsPic", new ImageUrlDirectiveModel());
/* 186 */     putData("mpager", new SearchPagerDirectiveModel());
/* 187 */     putData("searchUrl", new SearchUrlDirectiveModel());
/* 188 */     putData("catid", Integer.valueOf(this.cat_id));
/*     */   }
/*     */ 
/*     */   private String getProValue(int index, String proStr)
/*     */   {
/* 202 */     if (!StringUtil.isEmpty(proStr)) {
/* 203 */       String[] proar = StringUtils.split(proStr, ",");
/*     */ 
/* 205 */       for (int i = 0; i < proar.length; i++) {
/* 206 */         String str = proar[i];
/* 207 */         String[] ar = StringUtils.split(str, "_");
/* 208 */         if ((ar != null) && (ar.length == 2) && 
/* 209 */           (ar[0].equals("" + index))) {
/* 210 */           return ar[1];
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 216 */     return "";
/*     */   }
/*     */ 
/*     */   private void initParam(String p)
/*     */   {
/* 222 */     String cat_str = UrlUtils.getParamStringValue(p, "cat");
/*     */ 
/* 224 */     if (cat_str != null) {
/* 225 */       this.cat_id = Integer.valueOf(cat_str).intValue();
/*     */     }
/*     */ 
/* 231 */     String page_str = UrlUtils.getParamStringValue(p, "page");
/* 232 */     if ((page_str != null) && (!page_str.equals(""))) {
/* 233 */       this.page = Integer.valueOf(page_str).intValue();
/*     */     }
/*     */ 
/* 236 */     this.distype = UrlUtils.getParamStringValue(p, "distype");
/* 237 */     this.distype = (this.distype == null ? (this.distype = "index") : this.distype);
/* 238 */     this.order = UrlUtils.getParamStringValue(p, "order");
/* 239 */     this.propStr = UrlUtils.getParamStringValue(p, "prop");
/* 240 */     this.attrStr = UrlUtils.getParamStringValue(p, "attr");
/* 241 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 242 */     String encode = request.getCharacterEncoding();
/* 243 */     if (!"UTF-8".equals(encode)) {
/* 244 */       this.propStr = StringUtil.toUTF8(this.propStr);
/*     */     }
/*     */ 
/* 247 */     this.keyword = UrlUtils.getParamStringValue(p, "keyword");
/* 248 */     this.brandStr = UrlUtils.getParamStringValue(p, "brand");
/* 249 */     this.minPrice = UrlUtils.getParamStringValue(p, "minprice");
/* 250 */     this.maxPrice = UrlUtils.getParamStringValue(p, "maxprice");
/* 251 */     this.tagids = UrlUtils.getParamStringValue(p, "tag");
/*     */   }
/*     */ 
/*     */   public IGoodsSearchManager getGoodsSearchManager()
/*     */   {
/* 261 */     return this.goodsSearchManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsSearchManager(IGoodsSearchManager goodsSearchManager) {
/* 265 */     this.goodsSearchManager = goodsSearchManager;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager() {
/* 269 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 273 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public IFavoriteManager getFavoriteManager() {
/* 277 */     return this.favoriteManager;
/*     */   }
/*     */ 
/*     */   public void setFavoriteManager(IFavoriteManager favoriteManager) {
/* 281 */     this.favoriteManager = favoriteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search.GoodsSearchWidget
 * JD-Core Version:    0.6.0
 */