/*    */ package com.enation.app.shop.component.spec.plugin.order;
/*    */ 
/*    */ import com.enation.app.shop.component.product.plugin.order.GenericOrderPlugin;
/*    */ import com.enation.app.shop.core.model.OrderItem;
/*    */ import com.enation.app.shop.core.plugin.order.IOrderItemFilter;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsSpecOrderPlugin extends GenericOrderPlugin
/*    */   implements IOrderItemFilter
/*    */ {
/*    */   public void filter(Integer orderid, List<OrderItem> itemList)
/*    */   {
/* 35 */     for (OrderItem item : itemList) {
/* 36 */       String addon = item.getAddon();
/* 37 */       if (!StringUtil.isEmpty(addon)) {
/* 38 */         JSONArray specArray = JSONArray.fromObject(addon);
/* 39 */         List specList = (List)JSONArray.toCollection(specArray, Map.class);
/* 40 */         FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 41 */         freeMarkerPaser.setClz(getClass());
/* 42 */         freeMarkerPaser.putData("specList", specList);
/* 43 */         freeMarkerPaser.setPageName("order_item_spec");
/* 44 */         String html = freeMarkerPaser.proessPageContent();
/* 45 */         item.setOther(html);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.order.GoodsSpecOrderPlugin
 * JD-Core Version:    0.6.0
 */