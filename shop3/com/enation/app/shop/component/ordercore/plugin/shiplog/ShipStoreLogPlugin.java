/*    */ package com.enation.app.shop.component.ordercore.plugin.shiplog;
/*    */ 
/*    */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*    */ import com.enation.app.shop.core.model.AllocationItem;
/*    */ import com.enation.app.shop.core.model.Delivery;
/*    */ import com.enation.app.shop.core.model.DeliveryItem;
/*    */ import com.enation.app.shop.core.model.DepotUser;
/*    */ import com.enation.app.shop.core.model.Order;
/*    */ import com.enation.app.shop.core.model.StoreLog;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderShipEvent;
/*    */ import com.enation.app.shop.core.service.IStoreLogManager;
/*    */ import com.enation.eop.resource.model.AdminUser;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.DateUtil;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ShipStoreLogPlugin extends AutoRegisterPlugin
/*    */   implements IOrderShipEvent
/*    */ {
/*    */   private IStoreLogManager storeLogManager;
/*    */   private IAdminUserManager adminUserManager;
/*    */ 
/*    */   public void itemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
/*    */   {
/* 39 */     String other = allocationItem.getOther();
/* 40 */     int depotid = allocationItem.getDepotid();
/* 41 */     int num = allocationItem.getNum();
/* 42 */     int goodsid = allocationItem.getGoodsid();
/*    */ 
/* 44 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 45 */     DepotUser depotUser = (DepotUser)adminUser;
/* 46 */     StoreLog storeLog = new StoreLog();
/* 47 */     storeLog.setGoodsid(Integer.valueOf(goodsid));
/*    */ 
/* 49 */     storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotid)));
/* 50 */     storeLog.setOp_type(Integer.valueOf(2));
/* 51 */     storeLog.setDepotid(Integer.valueOf(depotid));
/* 52 */     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 53 */     storeLog.setNum(Integer.valueOf(num));
/* 54 */     storeLog.setUserid(adminUser.getUserid());
/* 55 */     storeLog.setUsername(adminUser.getUsername());
/* 56 */     this.storeLogManager.add(storeLog);
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
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */   public IStoreLogManager getStoreLogManager()
/*    */   {
/* 80 */     return this.storeLogManager;
/*    */   }
/*    */ 
/*    */   public void setStoreLogManager(IStoreLogManager storeLogManager) {
/* 84 */     this.storeLogManager = storeLogManager;
/*    */   }
/*    */ 
/*    */   public IAdminUserManager getAdminUserManager() {
/* 88 */     return this.adminUserManager;
/*    */   }
/*    */ 
/*    */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 92 */     this.adminUserManager = adminUserManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.shiplog.ShipStoreLogPlugin
 * JD-Core Version:    0.6.0
 */