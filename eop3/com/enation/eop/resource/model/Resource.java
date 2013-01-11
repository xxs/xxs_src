/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Resource
/*    */ {
/*    */   private Integer id;
/* 15 */   private Integer deleteflag = Integer.valueOf(0);
/*    */   private String productId;
/*    */ 
/*    */   public Integer getDeleteflag()
/*    */   {
/* 21 */     return this.deleteflag;
/*    */   }
/*    */ 
/*    */   public void setDeleteflag(Integer deleteflag) {
/* 25 */     this.deleteflag = deleteflag;
/*    */   }
/* 29 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 32 */     this.id = id;
/*    */   }
/*    */ 
/*    */   @NotDbField
/*    */   public String getProductId() {
/* 38 */     return this.productId;
/*    */   }
/*    */ 
/*    */   public void setProductId(String productId) {
/* 42 */     this.productId = productId;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Resource
 * JD-Core Version:    0.6.0
 */