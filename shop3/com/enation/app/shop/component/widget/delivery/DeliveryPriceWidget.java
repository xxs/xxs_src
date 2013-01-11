/*     */ package com.enation.app.shop.component.widget.delivery;
/*     */ 
/*     */ import com.enation.app.base.core.model.Regions;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.DlyType;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.ip.IPSeeker;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("delivery_price")
/*     */ @Scope("prototype")
/*     */ public class DeliveryPriceWidget extends AbstractWidget
/*     */ {
/*     */   private IRegionsManager regionsManager;
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   private IGoodsManager goodsManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  44 */     if ("getPriceJson".equals(this.action)) {
/*  45 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  46 */       Integer goodsid = RequestUtil.getIntegerValue(request, "goodsid");
/*  47 */       Integer regionid = RequestUtil.getIntegerValue(request, "regionid");
/*  48 */       getPriceJson(regionid, goodsid);
/*     */     } else {
/*  50 */       showAreaPrice();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showAreaPrice()
/*     */   {
/*  62 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  63 */     String ip = request.getRemoteAddr();
/*  64 */     String area = getIpArea(ip);
/*     */ 
/*  66 */     if (this.logger.isDebugEnabled()) {
/*  67 */       this.logger.debug(" area is " + area);
/*     */     }
/*     */ 
/*  70 */     Regions region = this.regionsManager.getByName(area);
/*     */ 
/*  72 */     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/*  73 */     if (goods == null) throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
/*     */ 
/*  75 */     List priceList = null;
/*     */ 
/*  77 */     if (region == null)
/*  78 */       priceList = getPriceList(goods, Integer.valueOf(1));
/*     */     else {
/*  80 */       priceList = getPriceList(goods, region.getRegion_id());
/*     */     }
/*     */ 
/*  83 */     List provinceList = this.regionsManager.listProvince();
/*     */ 
/*  85 */     putData("provinceList", provinceList);
/*  86 */     putData("priceList", priceList);
/*  87 */     putData("area", area);
/*     */   }
/*     */ 
/*     */   private List<Map> getPriceList(Map goods, Integer regionid)
/*     */   {
/*  98 */     List dlytypeList = this.dlyTypeManager.list();
/*  99 */     List priceList = new ArrayList();
/* 100 */     for (DlyType type : dlytypeList) {
/* 101 */       Integer typeid = type.getType_id();
/* 102 */       Double goodsprice = Double.valueOf("" + goods.get("price"));
/* 103 */       double weight = Double.valueOf("" + goods.get("weight")).doubleValue();
/* 104 */       Double[] price = this.dlyTypeManager.countPrice(typeid, Double.valueOf(weight), goodsprice, "" + regionid, false);
/* 105 */       Map map = new HashMap(2);
/* 106 */       map.put("name", type.getName());
/* 107 */       map.put("price", price[0]);
/* 108 */       priceList.add(map);
/*     */     }
/* 110 */     return priceList;
/*     */   }
/*     */ 
/*     */   private String getIpArea(String ip)
/*     */   {
/* 120 */     if (ip.equals("127.0.0.1")) return "北京市";
/*     */ 
/* 122 */     String country = new IPSeeker().getCountry(ip);
/* 123 */     int end = country.indexOf("省");
/* 124 */     if (end == -1) {
/* 125 */       end = country.indexOf("市");
/*     */     }
/* 127 */     if (end != -1) {
/* 128 */       country = country.substring(0, end + 1);
/*     */     }
/* 130 */     return country;
/*     */   }
/*     */ 
/*     */   private void getPriceJson(Integer regionid, Integer goodsid)
/*     */   {
/* 140 */     Map goods = this.goodsManager.get(goodsid);
/* 141 */     List priceList = getPriceList(goods, regionid);
/* 142 */     String json = JSONArray.fromObject(priceList).toString();
/* 143 */     showJson(json);
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 147 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 151 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager() {
/* 155 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/* 159 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 163 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 167 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.delivery.DeliveryPriceWidget
 * JD-Core Version:    0.6.0
 */