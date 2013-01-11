/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SpecDTO
/*    */ {
/*    */   private String[] spec_store;
/*    */   private String[] spec_price;
/*    */   private String[] spec_sn;
/*    */   private String spec_str;
/*    */   private List specs;
/*    */ 
/*    */   public SpecDTO()
/*    */   {
/* 21 */     this.specs = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void addSpec(String spec) {
/* 25 */     this.specs.add(spec);
/*    */   }
/*    */   public String[] getSpec_price() {
/* 28 */     return this.spec_price;
/*    */   }
/*    */   public void setSpec_price(String[] spec_price) {
/* 31 */     this.spec_price = spec_price;
/*    */   }
/*    */   public String[] getSpec_store() {
/* 34 */     return this.spec_store;
/*    */   }
/*    */   public void setSpec_store(String[] spec_store) {
/* 37 */     this.spec_store = spec_store;
/*    */   }
/*    */   public String getSpec_str() {
/* 40 */     return this.spec_str;
/*    */   }
/*    */   public void setSpec_str(String spec_str) {
/* 43 */     this.spec_str = spec_str;
/*    */   }
/*    */   public String[] getSpec_sn() {
/* 46 */     return this.spec_sn;
/*    */   }
/*    */   public void setSpec_sn(String[] spec_sn) {
/* 49 */     this.spec_sn = spec_sn;
/*    */   }
/*    */   public List getSpecs() {
/* 52 */     return this.specs;
/*    */   }
/*    */   public void setSpecs(List specs) {
/* 55 */     this.specs = specs;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.SpecDTO
 * JD-Core Version:    0.6.0
 */