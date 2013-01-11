/*    */ package com.enation.app.shop.core.action.backend;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsManager;
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ShopIndexItemAction extends WWAction
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */   private IGoodsManager goodsManager;
/*    */   private Map orderss;
/*    */   private Map goodsss;
/*    */ 
/*    */   public String order()
/*    */   {
/* 23 */     this.orderss = this.orderManager.censusState();
/* 24 */     return "order";
/*    */   }
/*    */ 
/*    */   public String goods()
/*    */   {
/* 30 */     this.goodsss = this.goodsManager.census();
/* 31 */     return "goods";
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 36 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager)
/*    */   {
/* 41 */     this.orderManager = orderManager;
/*    */   }
/*    */ 
/*    */   public IGoodsManager getGoodsManager()
/*    */   {
/* 46 */     return this.goodsManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsManager(IGoodsManager goodsManager)
/*    */   {
/* 51 */     this.goodsManager = goodsManager;
/*    */   }
/*    */ 
/*    */   public Map getOrderss()
/*    */   {
/* 56 */     return this.orderss;
/*    */   }
/*    */ 
/*    */   public void setOrderss(Map orderss)
/*    */   {
/* 61 */     this.orderss = orderss;
/*    */   }
/*    */ 
/*    */   public Map getGoodsss()
/*    */   {
/* 66 */     return this.goodsss;
/*    */   }
/*    */ 
/*    */   public void setGoodsss(Map goodsss)
/*    */   {
/* 71 */     this.goodsss = goodsss;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.ShopIndexItemAction
 * JD-Core Version:    0.6.0
 */