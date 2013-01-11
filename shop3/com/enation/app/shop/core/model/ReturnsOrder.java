/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.database.NotDbField;
/*    */ 
/*    */ public class ReturnsOrder
/*    */ {
/*    */   private Integer id;
/*    */   private String ordersn;
/*    */   private Integer memberid;
/*    */   private Integer state;
/*    */   private String goodsns;
/*    */   private Integer type;
/*    */   private Integer add_time;
/*    */   private String photo;
/*    */   private String refuse_reason;
/*    */   private String apply_reason;
/*    */   private String membername;
/*    */ 
/*    */   public Integer getType()
/*    */   {
/* 28 */     return this.type;
/*    */   }
/*    */   public void setType(Integer type) {
/* 31 */     this.type = type;
/*    */   }
/*    */   public String getApply_reason() {
/* 34 */     return this.apply_reason;
/*    */   }
/*    */   public void setApply_reason(String apply_reason) {
/* 37 */     this.apply_reason = apply_reason;
/*    */   }
/*    */   public Integer getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 43 */     this.id = id;
/*    */   }
/*    */   public String getOrdersn() {
/* 46 */     return this.ordersn;
/*    */   }
/*    */   public void setOrdersn(String ordersn) {
/* 49 */     this.ordersn = ordersn;
/*    */   }
/*    */   public Integer getMemberid() {
/* 52 */     return this.memberid;
/*    */   }
/*    */   public void setMemberid(Integer memberid) {
/* 55 */     this.memberid = memberid;
/*    */   }
/*    */   public Integer getState() {
/* 58 */     return this.state;
/*    */   }
/*    */   public void setState(Integer state) {
/* 61 */     this.state = state;
/*    */   }
/*    */   public String getGoodsns() {
/* 64 */     return this.goodsns;
/*    */   }
/*    */   public void setGoodsns(String goodsns) {
/* 67 */     this.goodsns = goodsns;
/*    */   }
/*    */   public Integer getAdd_time() {
/* 70 */     return this.add_time;
/*    */   }
/*    */   public void setAdd_time(Integer add_time) {
/* 73 */     this.add_time = add_time;
/*    */   }
/*    */   public String getPhoto() {
/* 76 */     if ((this.photo != null) && (!this.photo.equals(""))) {
/* 77 */       this.photo = UploadUtil.replacePath(this.photo);
/*    */     }
/* 79 */     return this.photo;
/*    */   }
/*    */   public void setPhoto(String photo) {
/* 82 */     this.photo = photo;
/*    */   }
/*    */   public String getRefuse_reason() {
/* 85 */     return this.refuse_reason;
/*    */   }
/*    */   public void setRefuse_reason(String refuse_reason) {
/* 88 */     this.refuse_reason = refuse_reason;
/*    */   }
/*    */ 
/*    */   @NotDbField
/*    */   public String getMembername() {
/* 94 */     return this.membername;
/*    */   }
/*    */   public void setMembername(String membername) {
/* 97 */     this.membername = membername;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.ReturnsOrder
 * JD-Core Version:    0.6.0
 */