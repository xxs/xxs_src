/*    */ package com.enation.app.shop.component.product.plugin.order;
/*    */ 
/*    */ import com.enation.app.shop.core.model.AllocationItem;
/*    */ import com.enation.app.shop.core.model.Delivery;
/*    */ import com.enation.app.shop.core.model.DeliveryItem;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderShipEvent;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GenericOrderPlugin extends AutoRegisterPlugin
/*    */   implements IOrderShipEvent
/*    */ {
/*    */   private IDaoSupport baseDaoSupport;
/*    */ 
/*    */   public void itemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
/*    */   {
/* 31 */     int depotid = allocationItem.getDepotid();
/* 32 */     int num = allocationItem.getNum();
/* 33 */     int goodsid = allocationItem.getGoodsid();
/* 34 */     int productid = allocationItem.getProductid();
/*    */ 
/* 36 */     this.baseDaoSupport.execute("update product_store set store=store-? where goodsid=? and productid=? and depotid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid) });
/*    */   }
/*    */ 
/*    */   public void ship(Delivery delivery, List<DeliveryItem> itemList)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void returned(Delivery delivery, List<DeliveryItem> itemList)
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(int catid)
/*    */   {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   public IDaoSupport getBaseDaoSupport() {
/* 56 */     return this.baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 60 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.product.plugin.order.GenericOrderPlugin
 * JD-Core Version:    0.6.0
 */