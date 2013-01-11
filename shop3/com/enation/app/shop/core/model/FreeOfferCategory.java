/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class FreeOfferCategory
/*    */ {
/*    */   private Integer cat_id;
/*    */   private String cat_name;
/*    */   private Integer publishable;
/*    */   private Integer sorder;
/*    */   private Integer disabled;
/*    */   private Integer userid;
/*    */   private Integer siteid;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getCat_id()
/*    */   {
/* 24 */     return this.cat_id;
/*    */   }
/*    */ 
/*    */   public void setCat_id(Integer catId) {
/* 28 */     this.cat_id = catId;
/*    */   }
/*    */ 
/*    */   public String getCat_name() {
/* 32 */     return this.cat_name;
/*    */   }
/*    */ 
/*    */   public void setCat_name(String catName) {
/* 36 */     this.cat_name = catName;
/*    */   }
/*    */ 
/*    */   public Integer getPublishable() {
/* 40 */     return this.publishable;
/*    */   }
/*    */ 
/*    */   public void setPublishable(Integer publishable) {
/* 44 */     this.publishable = publishable;
/*    */   }
/*    */ 
/*    */   public Integer getSorder() {
/* 48 */     return this.sorder;
/*    */   }
/*    */ 
/*    */   public void setSorder(Integer sorder) {
/* 52 */     this.sorder = sorder;
/*    */   }
/*    */ 
/*    */   public Integer getDisabled() {
/* 56 */     return this.disabled;
/*    */   }
/*    */ 
/*    */   public void setDisabled(Integer disabled) {
/* 60 */     this.disabled = disabled;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 64 */     return this.userid;
/*    */   }
/*    */ 
/*    */   public void setUserid(Integer userid) {
/* 68 */     this.userid = userid;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 72 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 76 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.FreeOfferCategory
 * JD-Core Version:    0.6.0
 */