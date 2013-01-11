/*    */ package com.enation.app.shop.component.spec.plugin.goods;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Spec
/*    */   implements Serializable
/*    */ {
/*    */   private Integer spec_id;
/*    */   private Integer goods_id;
/*    */   private String sn;
/*    */   private Integer store;
/*    */   private Double price;
/*    */   private String specs;
/*    */ 
/*    */   public Integer getGoods_id()
/*    */   {
/* 25 */     return this.goods_id;
/*    */   }
/*    */   public void setGoods_id(Integer goods_id) {
/* 28 */     this.goods_id = goods_id;
/*    */   }
/*    */   public Double getPrice() {
/* 31 */     return this.price;
/*    */   }
/*    */   public void setPrice(Double price) {
/* 34 */     this.price = price;
/*    */   }
/*    */   public String getSn() {
/* 37 */     return this.sn;
/*    */   }
/*    */   public void setSn(String sn) {
/* 40 */     this.sn = sn;
/*    */   }
/*    */   public Integer getSpec_id() {
/* 43 */     return this.spec_id;
/*    */   }
/*    */   public void setSpec_id(Integer spec_id) {
/* 46 */     this.spec_id = spec_id;
/*    */   }
/*    */   public Integer getStore() {
/* 49 */     return this.store;
/*    */   }
/*    */   public void setStore(Integer store) {
/* 52 */     this.store = store;
/*    */   }
/*    */   public String getSpecs() {
/* 55 */     return this.specs;
/*    */   }
/*    */   public void setSpecs(String specs) {
/* 58 */     this.specs = specs;
/*    */   }
/*    */ 
/*    */   public String[] getSpecAr() {
/* 62 */     String[] spec_ar = null;
/*    */ 
/* 64 */     if (this.specs != null) {
/* 65 */       String[] tempAr = this.specs.split(",");
/* 66 */       spec_ar = new String[tempAr.length];
/*    */ 
/* 68 */       for (int i = 0; i < tempAr.length; i++) {
/* 69 */         spec_ar[i] = tempAr[i].replaceAll("\"", "");
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 74 */     return spec_ar;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.goods.Spec
 * JD-Core Version:    0.6.0
 */