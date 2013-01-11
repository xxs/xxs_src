/*     */ package com.enation.app.shop.core.model.support;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Promotion;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CartItem
/*     */ {
/*     */   private Integer id;
/*     */   private Integer product_id;
/*     */   private Integer goods_id;
/*     */   private String name;
/*     */   private Double mktprice;
/*     */   private Double price;
/*     */   private Double coupPrice;
/*     */   private Double subtotal;
/*     */   private int num;
/*     */   private Integer limitnum;
/*     */   private String image_default;
/*     */   private Integer point;
/*     */   private Integer itemtype;
/*     */   private String sn;
/*     */   private String addon;
/*     */   private String specs;
/*     */   private int catid;
/*     */   private Map others;
/*     */   private Integer istejia;
/*     */   private Integer no_discount;
/*     */   private String unit;
/*     */   private List<Promotion> pmtList;
/*     */ 
/*     */   public void setPmtList(List<Promotion> pmtList)
/*     */   {
/*  50 */     this.pmtList = pmtList;
/*     */   }
/*     */   @NotDbField
/*     */   public List<Promotion> getPmtList() {
/*  55 */     return this.pmtList;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public Map getOthers() {
/*  61 */     if (this.others == null) this.others = new HashMap();
/*  62 */     return this.others;
/*     */   }
/*     */ 
/*     */   public void setOthers(Map others) {
/*  66 */     this.others = others;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/*  70 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  74 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer getProduct_id() {
/*  78 */     return this.product_id;
/*     */   }
/*     */ 
/*     */   public void setProduct_id(Integer productId) {
/*  82 */     this.product_id = productId;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  86 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  90 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Double getMktprice() {
/*  94 */     return this.mktprice;
/*     */   }
/*     */ 
/*     */   public void setMktprice(Double mktprice) {
/*  98 */     this.mktprice = mktprice;
/*     */   }
/*     */ 
/*     */   public Double getPrice() {
/* 102 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double price) {
/* 106 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public Double getCoupPrice() {
/* 110 */     return this.coupPrice;
/*     */   }
/*     */ 
/*     */   public void setCoupPrice(Double coupPrice) {
/* 114 */     this.coupPrice = coupPrice;
/*     */   }
/*     */ 
/*     */   public Double getSubtotal() {
/* 118 */     this.subtotal = CurrencyUtil.mul(this.num, this.coupPrice.doubleValue());
/* 119 */     return this.subtotal;
/*     */   }
/*     */ 
/*     */   public void setSubtotal(Double subtotal) {
/* 123 */     this.subtotal = subtotal;
/*     */   }
/*     */ 
/*     */   public int getNum() {
/* 127 */     return this.num;
/*     */   }
/*     */ 
/*     */   public void setNum(int num) {
/* 131 */     this.num = num;
/*     */   }
/*     */ 
/*     */   public String getImage_default() {
/* 135 */     return this.image_default;
/*     */   }
/*     */ 
/*     */   public void setImage_default(String imageDefault) {
/* 139 */     this.image_default = imageDefault;
/*     */   }
/*     */ 
/*     */   public Integer getGoods_id() {
/* 143 */     return this.goods_id;
/*     */   }
/*     */ 
/*     */   public void setGoods_id(Integer goodsId) {
/* 147 */     this.goods_id = goodsId;
/*     */   }
/*     */ 
/*     */   public Integer getPoint() {
/* 151 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(Integer point) {
/* 155 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public Integer getLimitnum()
/*     */   {
/* 160 */     return this.limitnum;
/*     */   }
/*     */ 
/*     */   public void setLimitnum(Integer limitnum) {
/* 164 */     this.limitnum = limitnum;
/*     */   }
/*     */ 
/*     */   public Integer getItemtype() {
/* 168 */     return this.itemtype;
/*     */   }
/*     */ 
/*     */   public void setItemtype(Integer itemtype) {
/* 172 */     this.itemtype = itemtype;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 176 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 180 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public String getAddon() {
/* 184 */     return this.addon;
/*     */   }
/*     */ 
/*     */   public void setAddon(String addon) {
/* 188 */     this.addon = addon;
/*     */   }
/*     */ 
/*     */   public String getSpecs() {
/* 192 */     return this.specs;
/*     */   }
/*     */ 
/*     */   public void setSpecs(String specs) {
/* 196 */     this.specs = specs;
/*     */   }
/*     */ 
/*     */   public int getCatid() {
/* 200 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(int catid) {
/* 204 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public Integer getIstejia() {
/* 208 */     return this.istejia;
/*     */   }
/*     */ 
/*     */   public void setIstejia(Integer istejia) {
/* 212 */     this.istejia = istejia;
/*     */   }
/*     */ 
/*     */   public Integer getNo_discount() {
/* 216 */     return this.no_discount;
/*     */   }
/*     */ 
/*     */   public void setNo_discount(Integer no_discount) {
/* 220 */     this.no_discount = no_discount;
/*     */   }
/*     */ 
/*     */   public String getUnit() {
/* 224 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String unit) {
/* 228 */     this.unit = unit;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.CartItem
 * JD-Core Version:    0.6.0
 */