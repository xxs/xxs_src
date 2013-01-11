/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WarnTask
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private Integer catid;
/*    */   private Integer goodsid;
/*    */   private Integer depotid;
/*    */   private Integer flag;
/*    */   private String sphere;
/*    */   private String cylinder;
/*    */   private String productids;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 19 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 22 */     this.id = id;
/*    */   }
/*    */   public Integer getCatid() {
/* 25 */     return this.catid;
/*    */   }
/*    */   public void setCatid(Integer catid) {
/* 28 */     this.catid = catid;
/*    */   }
/*    */   public Integer getGoodsid() {
/* 31 */     return this.goodsid;
/*    */   }
/*    */   public void setGoodsid(Integer goodsid) {
/* 34 */     this.goodsid = goodsid;
/*    */   }
/*    */   public Integer getDepotid() {
/* 37 */     return this.depotid;
/*    */   }
/*    */   public void setDepotid(Integer depotid) {
/* 40 */     this.depotid = depotid;
/*    */   }
/*    */   public Integer getFlag() {
/* 43 */     return this.flag;
/*    */   }
/*    */   public void setFlag(Integer flag) {
/* 46 */     this.flag = flag;
/*    */   }
/*    */   public String getSphere() {
/* 49 */     return this.sphere;
/*    */   }
/*    */   public void setSphere(String sphere) {
/* 52 */     this.sphere = sphere;
/*    */   }
/*    */   public String getCylinder() {
/* 55 */     return this.cylinder;
/*    */   }
/*    */   public void setCylinder(String cylinder) {
/* 58 */     this.cylinder = cylinder;
/*    */   }
/*    */   public String getProductids() {
/* 61 */     return this.productids;
/*    */   }
/*    */   public void setProductids(String productids) {
/* 64 */     this.productids = productids;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.WarnTask
 * JD-Core Version:    0.6.0
 */