/*     */ package com.enation.app.shop.component.product.plugin.goods;
/*     */ 
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GeneralProductPlugin extends AutoRegisterPlugin
/*     */   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsDeleteEvent
/*     */ {
/*     */   private IProductManager productManager;
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*  36 */     if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
/*  37 */     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
/*  38 */     Integer brandid = null;
/*  39 */     if (goods.get("brand_id") != null) {
/*  40 */       brandid = Integer.valueOf(goods.get("brand_id").toString());
/*     */     }
/*  42 */     String sn = (String)goods.get("sn");
/*     */ 
/*  44 */     Product product = new Product();
/*  45 */     product.setGoods_id(goodsId);
/*  46 */     product.setCost(Double.valueOf("" + goods.get("cost")));
/*  47 */     product.setPrice(Double.valueOf("" + goods.get("price")));
/*  48 */     product.setSn(sn);
/*  49 */     product.setStore(Integer.valueOf("" + goods.get("store")));
/*  50 */     product.setWeight(Double.valueOf("" + goods.get("weight")));
/*  51 */     product.setName((String)goods.get("name"));
/*     */ 
/*  53 */     List productList = new ArrayList();
/*     */ 
/*  56 */     String[] lvPriceStr = request.getParameterValues("lvPrice");
/*  57 */     String[] lvidStr = request.getParameterValues("lvid");
/*     */ 
/*  60 */     if ((lvidStr != null) && (lvidStr.length > 0)) {
/*  61 */       List goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
/*  62 */       product.setGoodsLvPrices(goodsLvPrices);
/*     */     }
/*     */ 
/*  65 */     productList.add(product);
/*  66 */     this.productManager.add(productList);
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*  73 */     if (goods.get("goods_id") == null) throw new RuntimeException("商品id不能为空");
/*  74 */     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
/*  75 */     Product product = this.productManager.getByGoodsId(goodsId);
/*  76 */     product.setGoods_id(goodsId);
/*  77 */     product.setCost(Double.valueOf("" + goods.get("cost")));
/*  78 */     product.setPrice(Double.valueOf("" + goods.get("price")));
/*  79 */     product.setSn((String)goods.get("sn"));
/*  80 */     product.setStore(Integer.valueOf("" + goods.get("store")));
/*  81 */     product.setWeight(Double.valueOf("" + goods.get("weight")));
/*  82 */     product.setName((String)goods.get("name"));
/*     */ 
/*  84 */     List productList = new ArrayList();
/*     */ 
/*  87 */     String[] lvPriceStr = request.getParameterValues("lvPrice");
/*  88 */     String[] lvidStr = request.getParameterValues("lvid");
/*     */ 
/*  91 */     if ((lvidStr != null) && (lvidStr.length > 0)) {
/*  92 */       List goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
/*  93 */       product.setGoodsLvPrices(goodsLvPrices);
/*     */     }
/*     */ 
/*  97 */     productList.add(product);
/*  98 */     this.productManager.add(productList);
/*     */   }
/*     */ 
/*     */   private List<GoodsLvPrice> createGoodsLvPrices(String[] lvPriceStr, String[] lvidStr, int goodsid)
/*     */   {
/* 114 */     List goodsLvPrices = new ArrayList();
/* 115 */     for (int i = 0; i < lvidStr.length; i++) {
/* 116 */       int lvid = StringUtil.toInt(lvidStr[i]);
/* 117 */       Double lvPrice = StringUtil.toDouble(lvPriceStr[i]);
/*     */ 
/* 119 */       if (lvPrice.doubleValue() != 0.0D) {
/* 120 */         GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
/* 121 */         goodsLvPrice.setGoodsid(goodsid);
/* 122 */         goodsLvPrice.setPrice(lvPrice);
/* 123 */         goodsLvPrice.setLvid(lvid);
/* 124 */         goodsLvPrices.add(goodsLvPrice);
/*     */       }
/*     */     }
/*     */ 
/* 128 */     return goodsLvPrices;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager()
/*     */   {
/* 135 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager)
/*     */   {
/* 140 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/* 146 */     this.productManager.delete(goodsid);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.product.plugin.goods.GeneralProductPlugin
 * JD-Core Version:    0.6.0
 */