/*    */ package com.enation.app.shop.component.product.plugin.allocation;
/*    */ 
/*    */ import com.enation.app.shop.core.model.AllocationItem;
/*    */ import com.enation.app.shop.core.model.OrderItem;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderAllocationItemEvent;
/*    */ import com.enation.app.shop.core.service.IGoodsStoreManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GenericAllocationPlugin extends AutoRegisterPlugin
/*    */   implements IOrderAllocationItemEvent
/*    */ {
/*    */   private IGoodsStoreManager goodsStoreManager;
/*    */ 
/*    */   public void filterAlloViewItem(Map colValues, ResultSet rs)
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getAllocationStoreHtml(OrderItem item)
/*    */   {
/* 37 */     List storeList = this.goodsStoreManager.listProductStore(item.getProduct_id());
/* 38 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
/* 39 */     freeMarkerPaser.setClz(getClass());
/* 40 */     freeMarkerPaser.putData("item", item);
/* 41 */     freeMarkerPaser.putData("storeList", storeList);
/* 42 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public void onAllocation(AllocationItem allocationItem)
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getAllocationViewHtml(OrderItem item)
/*    */   {
/* 58 */     List storeList = this.goodsStoreManager.listProductAllo(item.getOrder_id(), item.getItem_id());
/*    */ 
/* 60 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
/* 61 */     freeMarkerPaser.setClz(getClass());
/* 62 */     freeMarkerPaser.putData("item", item);
/* 63 */     freeMarkerPaser.putData("storeList", storeList);
/* 64 */     freeMarkerPaser.setPageName("view_allocation");
/* 65 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(int catid)
/*    */   {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   public IGoodsStoreManager getGoodsStoreManager()
/*    */   {
/* 78 */     return this.goodsStoreManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
/* 82 */     this.goodsStoreManager = goodsStoreManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.product.plugin.allocation.GenericAllocationPlugin
 * JD-Core Version:    0.6.0
 */