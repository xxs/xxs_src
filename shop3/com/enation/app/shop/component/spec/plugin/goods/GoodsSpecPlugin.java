/*     */ package com.enation.app.shop.component.spec.plugin.goods;
/*     */ 
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.model.SpecValue;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsSpecPlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsDeleteEvent, IGoodsTabShowEvent, IAjaxExecuteEnable
/*     */ {
/*     */   private IProductManager productManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IOrderManager orderManager;
/*     */ 
/*     */   public void addTabs()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void processGoods(Map goods, HttpServletRequest request)
/*     */   {
/*  52 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  53 */     String haveSpec = httpRequest.getParameter("haveSpec");
/*  54 */     goods.put("have_spec", haveSpec);
/*     */ 
/*  56 */     if ("0".equals(haveSpec))
/*     */     {
/*  58 */       goods.put("cost", httpRequest.getParameter("cost"));
/*  59 */       goods.put("price", httpRequest.getParameter("price"));
/*  60 */       goods.put("weight", httpRequest.getParameter("weight"));
/*  61 */       if (!"yes".equals(httpRequest.getParameter("isedit"))) {
/*  62 */         goods.put("store", Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */     }
/*  66 */     else if ("1".equals(haveSpec))
/*     */     {
/*  68 */       if (!"yes".equals(httpRequest.getParameter("isedit")))
/*     */       {
/*  70 */         goods.put("cost", Integer.valueOf(0));
/*  71 */         goods.put("price", Integer.valueOf(0));
/*  72 */         goods.put("weight", Integer.valueOf(0));
/*  73 */         goods.put("store", Integer.valueOf(0));
/*     */       }
/*     */ 
/*  77 */       String specs_img = httpRequest.getParameter("spec_imgs");
/*  78 */       specs_img = specs_img == null ? "{}" : specs_img;
/*  79 */       goods.put("specs", specs_img);
/*     */ 
/*  82 */       String[] prices = httpRequest.getParameterValues("prices");
/*  83 */       String[] costs = httpRequest.getParameterValues("costs");
/*  84 */       String[] stores = httpRequest.getParameterValues("stores");
/*  85 */       String[] weights = httpRequest.getParameterValues("weights");
/*     */ 
/*  87 */       if ((prices != null) && (prices.length > 0)) goods.put("price", prices[0]);
/*  88 */       if ((costs != null) && (costs.length > 0)) goods.put("cost", costs[0]);
/*  89 */       if ((stores != null) && (stores.length > 0)) goods.put("store", stores[0]);
/*  90 */       if ((weights != null) && (weights.length > 0)) goods.put("weight", weights[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*  98 */     processGoods(goods, request);
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/* 103 */     processSpec(goods, request);
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/* 108 */     processGoods(goods, request);
/*     */   }
/*     */ 
/*     */   private void processSpec(Map goods, HttpServletRequest request)
/*     */   {
/* 122 */     if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
/* 123 */     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
/* 124 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 126 */     String haveSpec = httpRequest.getParameter("haveSpec");
/*     */ 
/* 128 */     if ("1".equals(haveSpec))
/*     */     {
/* 136 */       String[] specidsAr = httpRequest.getParameterValues("specids");
/* 137 */       String[] specvidsAr = httpRequest.getParameterValues("specvids");
/*     */ 
/* 139 */       String[] productids = httpRequest.getParameterValues("productids");
/* 140 */       String[] sns = httpRequest.getParameterValues("sns");
/* 141 */       String[] prices = httpRequest.getParameterValues("prices");
/* 142 */       String[] costs = httpRequest.getParameterValues("costs");
/*     */ 
/* 144 */       String[] weights = httpRequest.getParameterValues("weights");
/*     */ 
/* 146 */       List productList = new ArrayList();
/* 147 */       int i = 0;
/* 148 */       for (String sn : sns)
/*     */       {
/* 151 */         Integer productId = StringUtil.isEmpty(productids[i]) ? null : Integer.valueOf(productids[i]);
/* 152 */         if ((sn == null) || (sn.equals(""))) {
/* 153 */           sn = goods.get("sn") + "-" + (i + 1);
/*     */         }
/*     */ 
/* 158 */         List valueList = new ArrayList();
/* 159 */         int j = 0;
/* 160 */         String[] specids = specidsAr[i].split(",");
/* 161 */         String[] specvids = specvidsAr[i].split(",");
/*     */ 
/* 164 */         for (String specid : specids) {
/* 165 */           SpecValue specvalue = new SpecValue();
/* 166 */           specvalue.setSpec_value_id(Integer.valueOf(specvids[j].trim()));
/* 167 */           specvalue.setSpec_id(Integer.valueOf(specid.trim()));
/* 168 */           valueList.add(specvalue);
/* 169 */           j++;
/*     */         }
/*     */ 
/* 173 */         Product product = new Product();
/* 174 */         product.setGoods_id(goodsId);
/* 175 */         product.setSpecList(valueList);
/* 176 */         product.setName((String)goods.get("name"));
/* 177 */         product.setSn(sn);
/* 178 */         product.setProduct_id(productId);
/*     */ 
/* 180 */         String[] specvalues = httpRequest.getParameterValues("specvalue_" + i);
/* 181 */         product.setSpecs(StringUtil.arrayToString(specvalues, "、"));
/*     */ 
/* 183 */         if ((null == prices[i]) || ("".equals(prices[i])))
/* 184 */           product.setPrice(Double.valueOf(0.0D));
/*     */         else {
/* 186 */           product.setPrice(Double.valueOf(prices[i]));
/*     */         }
/* 188 */         if (!"yes".equals(httpRequest.getParameter("isedit"))) {
/* 189 */           product.setStore(Integer.valueOf(0));
/*     */         }
/*     */ 
/* 193 */         if ((null == costs[i]) || ("".equals(costs[i])))
/* 194 */           product.setCost(Double.valueOf(0.0D));
/*     */         else {
/* 196 */           product.setCost(Double.valueOf(costs[i]));
/*     */         }
/*     */ 
/* 199 */         if ((null == weights[i]) || ("".equals(weights[i])))
/* 200 */           product.setWeight(Double.valueOf(0.0D));
/*     */         else {
/* 202 */           product.setWeight(Double.valueOf(weights[i]));
/*     */         }
/*     */ 
/* 208 */         String[] lvPriceStr = httpRequest.getParameterValues("lvPrice_" + i);
/* 209 */         String[] lvidStr = httpRequest.getParameterValues("lvid_" + i);
/*     */ 
/* 213 */         if ((lvidStr != null) && (lvidStr.length > 0)) {
/* 214 */           List goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
/* 215 */           product.setGoodsLvPrices(goodsLvPrices);
/*     */         }
/*     */ 
/* 218 */         productList.add(product);
/* 219 */         i++;
/*     */       }
/*     */ 
/* 222 */       this.productManager.add(productList);
/*     */     }
/*     */     else {
/* 225 */       Product product = this.productManager.getByGoodsId(goodsId);
/* 226 */       if (product == null) {
/* 227 */         product = new Product();
/*     */       }
/*     */ 
/* 230 */       product.setGoods_id(goodsId);
/* 231 */       product.setCost(Double.valueOf("" + goods.get("cost")));
/* 232 */       product.setPrice(Double.valueOf("" + goods.get("price")));
/* 233 */       product.setSn((String)goods.get("sn"));
/* 234 */       product.setWeight(Double.valueOf("" + goods.get("weight")));
/* 235 */       product.setName((String)goods.get("name"));
/*     */ 
/* 238 */       String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
/* 239 */       String[] lvidStr = httpRequest.getParameterValues("lvid");
/*     */ 
/* 242 */       if ((lvidStr != null) && (lvidStr.length > 0)) {
/* 243 */         List goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
/* 244 */         product.setGoodsLvPrices(goodsLvPrices);
/*     */       }
/*     */ 
/* 247 */       List productList = new ArrayList();
/* 248 */       productList.add(product);
/* 249 */       this.productManager.add(productList);
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<GoodsLvPrice> createGoodsLvPrices(String[] lvPriceStr, String[] lvidStr, int goodsid)
/*     */   {
/* 264 */     List goodsLvPrices = new ArrayList();
/* 265 */     for (int i = 0; i < lvidStr.length; i++) {
/* 266 */       int lvid = StringUtil.toInt(lvidStr[i]);
/* 267 */       Double lvPrice = StringUtil.toDouble(lvPriceStr[i]);
/*     */ 
/* 269 */       if (lvPrice.doubleValue() != 0.0D) {
/* 270 */         GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
/* 271 */         goodsLvPrice.setGoodsid(goodsid);
/* 272 */         goodsLvPrice.setPrice(lvPrice);
/* 273 */         goodsLvPrice.setLvid(lvid);
/* 274 */         goodsLvPrices.add(goodsLvPrice);
/*     */       }
/*     */     }
/*     */ 
/* 278 */     return goodsLvPrices;
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/* 285 */     processSpec(goods, request);
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/* 296 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 297 */     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
/* 298 */     List specNameList = this.productManager.listSpecName(goods_id.intValue());
/* 299 */     List productList = this.productManager.list(goods_id);
/*     */ 
/* 301 */     List lvList = this.memberLvManager.list();
/* 302 */     freeMarkerPaser.putData("lvList", lvList);
/*     */ 
/* 304 */     freeMarkerPaser.putData("productList", productList);
/* 305 */     freeMarkerPaser.putData("specNameList", specNameList);
/* 306 */     freeMarkerPaser.setPageName("spec");
/*     */ 
/* 308 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/* 318 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/* 321 */     freeMarkerPaser.setPageName("spec");
/* 322 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/* 329 */     this.productManager.delete(goodsid);
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 334 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 339 */     return "goodsspec";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 344 */     return "通用商品规格插件";
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 348 */     return "";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 353 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager()
/*     */   {
/* 364 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager)
/*     */   {
/* 370 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 375 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 380 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 386 */     return "规格";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 392 */     return 4;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/* 397 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 398 */     String action = request.getParameter("action");
/* 399 */     if ("check-pro-in-order".equals(action)) {
/* 400 */       int productid = StringUtil.toInt(request.getParameter("productid"), true);
/* 401 */       boolean isinorder = this.orderManager.checkProInOrder(productid);
/* 402 */       if (isinorder) {
/* 403 */         return "{result:1}";
/*     */       }
/* 405 */       return "{result:0}";
/*     */     }
/* 407 */     if ("check-goods-in-order".equals(action)) {
/* 408 */       int goodsid = StringUtil.toInt(request.getParameter("goodsid"), true);
/* 409 */       boolean isinorder = this.orderManager.checkGoodsInOrder(goodsid);
/* 410 */       if (isinorder) {
/* 411 */         return "{result:1}";
/*     */       }
/* 413 */       return "{result:0}";
/*     */     }
/*     */ 
/* 416 */     return "";
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 420 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 424 */     this.orderManager = orderManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.goods.GoodsSpecPlugin
 * JD-Core Version:    0.6.0
 */