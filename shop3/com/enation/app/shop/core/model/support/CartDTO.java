/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CartDTO
/*    */ {
/*    */   private List itemList;
/*    */   private List ruleList;
/*    */   private Double orderPrice;
/*    */ 
/*    */   public List getItemList()
/*    */   {
/* 11 */     return this.itemList;
/*    */   }
/*    */   public void setItemList(List itemList) {
/* 14 */     this.itemList = itemList;
/*    */   }
/*    */   public Double getOrderPrice() {
/* 17 */     return this.orderPrice;
/*    */   }
/*    */   public void setOrderPrice(Double orderPrice) {
/* 20 */     this.orderPrice = orderPrice;
/*    */   }
/*    */   public List getRuleList() {
/* 23 */     return this.ruleList;
/*    */   }
/*    */   public void setRuleList(List ruleList) {
/* 26 */     this.ruleList = ruleList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.CartDTO
 * JD-Core Version:    0.6.0
 */