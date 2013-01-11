/*    */ package com.enation.app.shop.component.spec.plugin.order;
/*    */ 
/*    */ import com.enation.app.shop.core.model.AllocationItem;
/*    */ import com.enation.app.shop.core.model.OrderItem;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderAllocationItemEvent;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsSpecAllocationPlugin extends AutoRegisterPlugin
/*    */   implements IOrderAllocationItemEvent
/*    */ {
/*    */   private IOrderAllocationItemEvent genericAllocationPlugin;
/*    */ 
/*    */   public String getAllocationStoreHtml(OrderItem item)
/*    */   {
/* 34 */     return this.genericAllocationPlugin.getAllocationStoreHtml(item);
/*    */   }
/*    */ 
/*    */   public String getAllocationViewHtml(OrderItem item)
/*    */   {
/* 40 */     return this.genericAllocationPlugin.getAllocationViewHtml(item);
/*    */   }
/*    */ 
/*    */   public void onAllocation(AllocationItem allocationItem)
/*    */   {
/* 45 */     this.genericAllocationPlugin.onAllocation(allocationItem);
/*    */   }
/*    */ 
/*    */   public void filterAlloViewItem(Map colValues, ResultSet rs)
/*    */     throws SQLException
/*    */   {
/* 52 */     String addon = rs.getString("addon");
/*    */ 
/* 54 */     if (!StringUtil.isEmpty(addon)) {
/* 55 */       JSONArray specArray = JSONArray.fromObject(addon);
/* 56 */       List specList = (List)JSONArray.toCollection(specArray, Map.class);
/* 57 */       FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 58 */       freeMarkerPaser.setClz(getClass());
/* 59 */       freeMarkerPaser.putData("specList", specList);
/* 60 */       freeMarkerPaser.setPageName("order_item_spec");
/* 61 */       String html = freeMarkerPaser.proessPageContent();
/* 62 */       colValues.put("other", html);
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(int catid)
/*    */   {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   public IOrderAllocationItemEvent getGenericAllocationPlugin()
/*    */   {
/* 81 */     return this.genericAllocationPlugin;
/*    */   }
/*    */ 
/*    */   public void setGenericAllocationPlugin(IOrderAllocationItemEvent genericAllocationPlugin)
/*    */   {
/* 86 */     this.genericAllocationPlugin = genericAllocationPlugin;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.order.GoodsSpecAllocationPlugin
 * JD-Core Version:    0.6.0
 */