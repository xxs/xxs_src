/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class DeliveryItem
/*    */ {
/*    */   private Integer item_id;
/*    */   private int order_itemid;
/*    */   private Integer delivery_id;
/*    */   private Integer goods_id;
/*    */   private Integer product_id;
/*    */   private String sn;
/*    */   private String name;
/*    */   private Integer num;
/*    */   private Integer itemtype;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getItem_id()
/*    */   {
/* 25 */     return this.item_id;
/*    */   }
/*    */   public void setItem_id(Integer itemId) {
/* 28 */     this.item_id = itemId;
/*    */   }
/*    */   public Integer getDelivery_id() {
/* 31 */     return this.delivery_id;
/*    */   }
/*    */   public void setDelivery_id(Integer deliveryId) {
/* 34 */     this.delivery_id = deliveryId;
/*    */   }
/*    */   public Integer getProduct_id() {
/* 37 */     return this.product_id;
/*    */   }
/*    */   public void setProduct_id(Integer productId) {
/* 40 */     this.product_id = productId;
/*    */   }
/*    */   public String getSn() {
/* 43 */     return this.sn;
/*    */   }
/*    */   public void setSn(String sn) {
/* 46 */     this.sn = sn;
/*    */   }
/*    */   public String getName() {
/* 49 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 52 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Integer getGoods_id() {
/* 56 */     return this.goods_id;
/*    */   }
/*    */   public void setGoods_id(Integer goodsId) {
/* 59 */     this.goods_id = goodsId;
/*    */   }
/*    */   public Integer getNum() {
/* 62 */     return this.num;
/*    */   }
/*    */   public void setNum(Integer num) {
/* 65 */     this.num = num;
/*    */   }
/*    */   public Integer getItemtype() {
/* 68 */     return this.itemtype;
/*    */   }
/*    */   public void setItemtype(Integer itemtype) {
/* 71 */     this.itemtype = itemtype;
/*    */   }
/*    */   public int getOrder_itemid() {
/* 74 */     return this.order_itemid;
/*    */   }
/*    */   public void setOrder_itemid(int order_itemid) {
/* 77 */     this.order_itemid = order_itemid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.DeliveryItem
 * JD-Core Version:    0.6.0
 */