/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GoodsEditDTO
/*    */ {
/*    */   private Map goods;
/*    */   private Map<Integer, String> htmlMap;
/*    */ 
/*    */   public Map getGoods()
/*    */   {
/* 12 */     return this.goods;
/*    */   }
/*    */   public void setGoods(Map goods) {
/* 15 */     this.goods = goods;
/*    */   }
/*    */   public Map<Integer, String> getHtmlMap() {
/* 18 */     return this.htmlMap;
/*    */   }
/*    */   public void setHtmlMap(Map<Integer, String> htmlMap) {
/* 21 */     this.htmlMap = htmlMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsEditDTO
 * JD-Core Version:    0.6.0
 */