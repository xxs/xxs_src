/*     */ package com.enation.app.shop.core.model.support;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class GoodsView extends Goods
/*     */ {
/*     */   private Double save_price;
/*     */   private Double agio;
/*     */   private String brand_name;
/*     */   private Map propMap;
/*     */   private int hasSpec;
/*     */   private List specList;
/*     */   private Integer productid;
/*  36 */   private boolean isLast = false;
/*  37 */   private boolean isFirst = false;
/*     */ 
/*     */   public Double getAgio()
/*     */   {
/*  43 */     this.agio = Double.valueOf(CurrencyUtil.div(getPrice().doubleValue(), getMktprice().doubleValue()));
/*  44 */     return this.agio;
/*     */   }
/*     */ 
/*     */   public void setAgio(Double agio) {
/*  48 */     this.agio = agio;
/*     */   }
/*     */ 
/*     */   public Double getSave_price()
/*     */   {
/*  53 */     this.save_price = Double.valueOf(CurrencyUtil.sub(getMktprice().doubleValue(), getPrice().doubleValue()));
/*  54 */     return this.save_price;
/*     */   }
/*     */ 
/*     */   public void setSave_price(Double save_price) {
/*  58 */     this.save_price = save_price;
/*     */   }
/*     */ 
/*     */   public String getImage_default()
/*     */   {
/*  67 */     String image_default = super.getImage_default();
/*  68 */     if ((image_default == null) || (image_default.equals(""))) {
/*  69 */       return EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/*     */     }
/*  71 */     image_default = UploadUtil.replacePath(image_default);
/*     */ 
/*  74 */     return image_default;
/*     */   }
/*     */ 
/*     */   public String getThumbnail_pic()
/*     */   {
/*  85 */     String thumbnail = super.getImage_default();
/*  86 */     if ((thumbnail == null) || (thumbnail.equals(""))) {
/*  87 */       return EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/*     */     }
/*  89 */     thumbnail = getImage_default();
/*     */ 
/*  91 */     thumbnail = UploadUtil.getThumbPath(thumbnail, "_thumbnail");
/*  92 */     return thumbnail;
/*     */   }
/*     */ 
/*     */   public String getBrand_name()
/*     */   {
/*  98 */     return this.brand_name;
/*     */   }
/*     */ 
/*     */   public void setBrand_name(String brand_name) {
/* 102 */     this.brand_name = brand_name;
/*     */   }
/*     */ 
/*     */   public Map getPropMap() {
/* 106 */     return this.propMap;
/*     */   }
/*     */ 
/*     */   public void setPropMap(Map propMap) {
/* 110 */     this.propMap = propMap;
/*     */   }
/*     */ 
/*     */   public List getSpecList() {
/* 114 */     return this.specList;
/*     */   }
/*     */ 
/*     */   public void setSpecList(List specList) {
/* 118 */     this.specList = specList;
/*     */   }
/*     */ 
/*     */   public int getHasSpec() {
/* 122 */     return this.hasSpec;
/*     */   }
/*     */ 
/*     */   public void setHasSpec(int hasSpec) {
/* 126 */     this.hasSpec = hasSpec;
/*     */   }
/*     */ 
/*     */   public Integer getProductid() {
/* 130 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(Integer productid) {
/* 134 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public boolean getIsLast() {
/* 138 */     return this.isLast;
/*     */   }
/*     */ 
/*     */   public void setIsLast(boolean isLast) {
/* 142 */     this.isLast = isLast;
/*     */   }
/*     */ 
/*     */   public boolean getIsFirst() {
/* 146 */     return this.isFirst;
/*     */   }
/*     */ 
/*     */   public void setIsFirst(boolean isFirst) {
/* 150 */     this.isFirst = isFirst;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsView
 * JD-Core Version:    0.6.0
 */