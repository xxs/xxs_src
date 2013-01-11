/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PayCfg
/*    */   implements Serializable
/*    */ {
/*    */   protected Integer id;
/*    */   protected String name;
/*    */   protected String config;
/*    */   protected String biref;
/*    */   protected Double pay_fee;
/*    */   protected String type;
/*    */ 
/*    */   public String getBiref()
/*    */   {
/* 29 */     return this.biref;
/*    */   }
/*    */ 
/*    */   public void setBiref(String biref) {
/* 33 */     this.biref = biref;
/*    */   }
/*    */   public String getConfig() {
/* 36 */     return this.config;
/*    */   }
/*    */   public void setConfig(String config) {
/* 39 */     this.config = config;
/*    */   }
/* 43 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 46 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 49 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 52 */     this.name = name;
/*    */   }
/*    */   public Double getPay_fee() {
/* 55 */     return this.pay_fee;
/*    */   }
/*    */   public void setPay_fee(Double pay_fee) {
/* 58 */     this.pay_fee = pay_fee;
/*    */   }
/*    */   public String getType() {
/* 61 */     return this.type;
/*    */   }
/*    */   public void setType(String type) {
/* 64 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PayCfg
 * JD-Core Version:    0.6.0
 */