/*    */ package com.enation.app.shop.core.service.promotion;
/*    */ 
/*    */ public class PromotionConditions
/*    */ {
/*    */   public static final String ORDER = "order";
/*    */   public static final String MEMBERLV = "memberLv";
/*    */   public static final String GOODS = "goods";
/* 12 */   private boolean hasOrder = false;
/* 13 */   private boolean hasMemberLv = false;
/* 14 */   private boolean hasGoods = false;
/*    */ 
/*    */   public PromotionConditions(String[] conditions) {
/* 17 */     if (conditions != null)
/* 18 */       for (String cond : conditions) {
/* 19 */         if ("order".equals(cond)) {
/* 20 */           this.hasOrder = true;
/*    */         }
/*    */ 
/* 23 */         if ("memberLv".equals(cond)) {
/* 24 */           this.hasMemberLv = true;
/*    */         }
/*    */ 
/* 27 */         if ("goods".equals(cond))
/* 28 */           this.hasGoods = true;
/*    */       }
/*    */   }
/*    */ 
/*    */   public boolean getHasOrder()
/*    */   {
/* 35 */     return this.hasOrder;
/*    */   }
/*    */ 
/*    */   public boolean getHasMemberLv()
/*    */   {
/* 40 */     return this.hasMemberLv;
/*    */   }
/*    */ 
/*    */   public void setHasMemberLv(boolean hasMemberLv)
/*    */   {
/* 45 */     this.hasMemberLv = hasMemberLv;
/*    */   }
/*    */ 
/*    */   public void setHasOrder(boolean hasOrder)
/*    */   {
/* 50 */     this.hasOrder = hasOrder;
/*    */   }
/*    */ 
/*    */   public boolean getHasGoods()
/*    */   {
/* 55 */     return this.hasGoods;
/*    */   }
/*    */ 
/*    */   public void setHasGoods(boolean hasGoods)
/*    */   {
/* 60 */     this.hasGoods = hasGoods;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.promotion.PromotionConditions
 * JD-Core Version:    0.6.0
 */