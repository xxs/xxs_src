/*     */ package com.enation.app.shop.core.plugin.order;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Allocation;
/*     */ import com.enation.app.shop.core.model.AllocationItem;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.DeliveryItem;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.OrderItem;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class OrderPluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public String getName()
/*     */   {
/*  31 */     return "订单插件桩";
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getTabList(Order order)
/*     */   {
/*  39 */     List plugins = getPlugins();
/*     */ 
/*  41 */     Map tabMap = new TreeMap();
/*  42 */     if (plugins != null)
/*     */     {
/*  45 */       for (IPlugin plugin : plugins) {
/*  46 */         if ((plugin instanceof IOrderTabShowEvent))
/*     */         {
/*  49 */           IOrderTabShowEvent event = (IOrderTabShowEvent)plugin;
/*     */ 
/*  54 */           if (!event.canBeExecute(order))
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/*  59 */           String name = event.getTabName(order);
/*  60 */           tabMap.put(Integer.valueOf(event.getOrder()), name);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  68 */     return tabMap;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getDetailHtml(Order order)
/*     */   {
/*  78 */     Map htmlMap = new TreeMap();
/*  79 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  80 */     freeMarkerPaser.putData("order", order);
/*  81 */     List plugins = getPlugins();
/*     */ 
/*  83 */     if (plugins != null) {
/*  84 */       for (IPlugin plugin : plugins)
/*     */       {
/*  87 */         if ((plugin instanceof IShowOrderDetailHtmlEvent)) {
/*  88 */           IShowOrderDetailHtmlEvent event = (IShowOrderDetailHtmlEvent)plugin;
/*  89 */           freeMarkerPaser.setClz(event.getClass());
/*  90 */           if ((plugin instanceof IOrderTabShowEvent))
/*     */           {
/*  92 */             IOrderTabShowEvent tabEvent = (IOrderTabShowEvent)plugin;
/*     */ 
/*  97 */             if (!tabEvent.canBeExecute(order)) {
/*     */               continue;
/*     */             }
/* 100 */             String html = event.onShowOrderDetailHtml(order);
/* 101 */             htmlMap.put(Integer.valueOf(tabEvent.getOrder()), html);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 108 */     return htmlMap;
/*     */   }
/*     */ 
/*     */   public void onBeforeCreate(Order order, List<CartItem> itemList, String sessionid)
/*     */   {
/*     */     try {
/* 114 */       List plugins = getPlugins();
/*     */ 
/* 116 */       if (plugins != null) {
/* 117 */         for (IPlugin plugin : plugins)
/* 118 */           if ((plugin instanceof IBeforeOrderCreateEvent)) {
/* 119 */             if (loger.isDebugEnabled()) {
/* 120 */               loger.debug("调用插件 : " + plugin.getClass() + " onBeforeCreate 开始...");
/*     */             }
/* 122 */             IBeforeOrderCreateEvent event = (IBeforeOrderCreateEvent)plugin;
/* 123 */             event.onBeforeOrderCreate(order, itemList, sessionid);
/* 124 */             if (loger.isDebugEnabled())
/* 125 */               loger.debug("调用插件 : " + plugin.getClass() + " onBeforeCreate 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 131 */       if (loger.isErrorEnabled())
/* 132 */         loger.error("调用订单插件[Before创建]事件错误", e);
/* 133 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAfterCreate(Order order, List<CartItem> itemList, String sessionid) {
/*     */     try {
/* 138 */       List plugins = getPlugins();
/*     */ 
/* 140 */       if (plugins != null) {
/* 141 */         for (IPlugin plugin : plugins)
/* 142 */           if ((plugin instanceof IAfterOrderCreateEvent)) {
/* 143 */             if (loger.isDebugEnabled()) {
/* 144 */               loger.debug("调用插件 : " + plugin.getClass() + " onAfterCreate 开始...");
/*     */             }
/* 146 */             IAfterOrderCreateEvent event = (IAfterOrderCreateEvent)plugin;
/* 147 */             event.onAfterOrderCreate(order, itemList, sessionid);
/* 148 */             if (loger.isDebugEnabled())
/* 149 */               loger.debug("调用插件 : " + plugin.getClass() + " onAfterCreate 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 155 */       if (loger.isErrorEnabled())
/* 156 */         loger.error("调用订单插件[After创建]事件错误", e);
/* 157 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onFilter(Integer orderid, List<OrderItem> itemList) {
/*     */     try {
/* 163 */       List plugins = getPlugins();
/*     */ 
/* 165 */       if (plugins != null) {
/* 166 */         for (IPlugin plugin : plugins)
/* 167 */           if ((plugin instanceof IOrderItemFilter)) {
/* 168 */             if (loger.isDebugEnabled()) {
/* 169 */               loger.debug("调用插件 : " + plugin.getClass() + " onFilter 开始...");
/*     */             }
/* 171 */             IOrderItemFilter event = (IOrderItemFilter)plugin;
/* 172 */             event.filter(orderid, itemList);
/* 173 */             if (loger.isDebugEnabled())
/* 174 */               loger.debug("调用插件 : " + plugin.getClass() + " onFilter 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 180 */       if (loger.isErrorEnabled())
/* 181 */         loger.error("调用订单插件[filter]事件错误", e);
/* 182 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onShip(Delivery delivery, List<DeliveryItem> itemList)
/*     */   {
/*     */     try
/*     */     {
/* 196 */       List plugins = getPlugins();
/*     */ 
/* 198 */       if (plugins != null) {
/* 199 */         for (IPlugin plugin : plugins)
/* 200 */           if ((plugin instanceof IOrderShipEvent)) {
/* 201 */             if (loger.isDebugEnabled()) {
/* 202 */               loger.debug("调用插件 : " + plugin.getClass() + " onShip 开始...");
/*     */             }
/* 204 */             IOrderShipEvent event = (IOrderShipEvent)plugin;
/* 205 */             event.ship(delivery, itemList);
/* 206 */             if (loger.isDebugEnabled())
/* 207 */               loger.debug("调用插件 : " + plugin.getClass() + " onShip 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 213 */       if (loger.isErrorEnabled())
/* 214 */         loger.error("调用订单插件[ship]事件错误", e);
/* 215 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onItemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
/*     */   {
/*     */     try
/*     */     {
/* 227 */       List plugins = getPlugins();
/*     */ 
/* 229 */       if (plugins != null) {
/* 230 */         for (IPlugin plugin : plugins)
/* 231 */           if ((plugin instanceof IOrderShipEvent)) {
/* 232 */             if (loger.isDebugEnabled()) {
/* 233 */               loger.debug("调用插件 : " + plugin.getClass() + " onItemShip 开始...");
/*     */             }
/* 235 */             IOrderShipEvent event = (IOrderShipEvent)plugin;
/* 236 */             if (event.canBeExecute(allocationItem.getCat_id())) {
/* 237 */               event.itemShip(order, deliveryItem, allocationItem);
/*     */             }
/*     */ 
/* 240 */             if (loger.isDebugEnabled())
/* 241 */               loger.debug("调用插件 : " + plugin.getClass() + " onItemShip 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 247 */       if (loger.isErrorEnabled())
/* 248 */         loger.error("调用订单插件[onItemShip]事件错误", e);
/* 249 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onReturned(Delivery delivery, List<DeliveryItem> itemList)
/*     */   {
/*     */     try
/*     */     {
/* 257 */       List plugins = getPlugins();
/*     */ 
/* 259 */       if (plugins != null) {
/* 260 */         for (IPlugin plugin : plugins)
/* 261 */           if ((plugin instanceof IOrderShipEvent)) {
/* 262 */             if (loger.isDebugEnabled()) {
/* 263 */               loger.debug("调用插件 : " + plugin.getClass() + " onReturned 开始...");
/*     */             }
/* 265 */             IOrderShipEvent event = (IOrderShipEvent)plugin;
/* 266 */             event.returned(delivery, itemList);
/* 267 */             if (loger.isDebugEnabled())
/* 268 */               loger.debug("调用插件 : " + plugin.getClass() + " onReturned 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 274 */       if (loger.isErrorEnabled()) {
/* 275 */         loger.error("调用订单插件[returned]事件错误", e);
/*     */       }
/* 277 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onDelete(Integer[] orderId)
/*     */   {
/*     */     try
/*     */     {
/* 288 */       List plugins = getPlugins();
/*     */ 
/* 290 */       if (plugins != null) {
/* 291 */         for (IPlugin plugin : plugins)
/* 292 */           if ((plugin instanceof IOrderDeleteEvent)) {
/* 293 */             if (loger.isDebugEnabled()) {
/* 294 */               loger.debug("调用插件 : " + plugin.getClass() + " onDelete 开始...");
/*     */             }
/* 296 */             IOrderDeleteEvent event = (IOrderDeleteEvent)plugin;
/* 297 */             event.delete(orderId);
/* 298 */             if (loger.isDebugEnabled())
/* 299 */               loger.debug("调用插件 : " + plugin.getClass() + " onDelete 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 305 */       if (loger.isErrorEnabled())
/* 306 */         loger.error("调用订单插件[delete]事件错误", e);
/* 307 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAllocationItem(AllocationItem allocationItem)
/*     */   {
/*     */     try
/*     */     {
/* 319 */       List plugins = getPlugins();
/*     */ 
/* 321 */       if (plugins != null) {
/* 322 */         for (IPlugin plugin : plugins)
/* 323 */           if ((plugin instanceof IOrderAllocationItemEvent)) {
/* 324 */             if (loger.isDebugEnabled()) {
/* 325 */               loger.debug("调用插件 : " + plugin.getClass() + " onAllocationItem 开始...");
/*     */             }
/* 327 */             IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
/* 328 */             if (event.canBeExecute(allocationItem.getCat_id())) {
/* 329 */               event.onAllocation(allocationItem);
/*     */             }
/* 331 */             if (loger.isDebugEnabled())
/* 332 */               loger.debug("调用插件 : " + plugin.getClass() + " onAllocationItem 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 338 */       loger.error("调用订单插件[onAllocationItem]事件错误", e);
/*     */ 
/* 340 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAllocation(Allocation allocation)
/*     */   {
/*     */     try
/*     */     {
/* 351 */       List plugins = getPlugins();
/*     */ 
/* 353 */       if (plugins != null) {
/* 354 */         for (IPlugin plugin : plugins)
/* 355 */           if ((plugin instanceof IOrderAllocationEvent)) {
/* 356 */             if (loger.isDebugEnabled()) {
/* 357 */               loger.debug("调用插件 : " + plugin.getClass() + " onAllocation 开始...");
/*     */             }
/* 359 */             IOrderAllocationEvent event = (IOrderAllocationEvent)plugin;
/* 360 */             event.onAllocation(allocation);
/* 361 */             if (loger.isDebugEnabled())
/* 362 */               loger.debug("调用插件 : " + plugin.getClass() + " onAllocation 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 368 */       loger.error("调用订单插件[onAllocation]事件错误", e);
/*     */ 
/* 370 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void filterAlloItem(int catid, Map values, ResultSet rs)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 455 */       List plugins = getPlugins();
/*     */ 
/* 457 */       if (plugins != null) {
/* 458 */         for (IPlugin plugin : plugins) {
/* 459 */           if ((plugin instanceof IOrderAllocationItemEvent)) {
/* 460 */             if (loger.isDebugEnabled()) {
/* 461 */               loger.debug("调用插件 : " + plugin.getClass() + " filterAlloViewItem 开始...");
/*     */             }
/* 463 */             IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
/* 464 */             if (event.canBeExecute(catid)) {
/* 465 */               ((IOrderAllocationItemEvent)plugin).filterAlloViewItem(values, rs);
/*     */             }
/* 467 */             if (loger.isDebugEnabled())
/* 468 */               loger.debug("调用插件 : " + plugin.getClass() + " filterAlloViewItem 结束.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 475 */       if (loger.isErrorEnabled())
/* 476 */         loger.error("调用订单插件[filterAlloViewItem]事件错误", e);
/* 477 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getAllocationHtml(OrderItem item)
/*     */   {
/* 492 */     String html = null;
/*     */     try {
/* 494 */       List plugins = getPlugins();
/*     */ 
/* 496 */       if (plugins != null) {
/* 497 */         for (IPlugin plugin : plugins) {
/* 498 */           if ((plugin instanceof IOrderAllocationItemEvent)) {
/* 499 */             if (loger.isDebugEnabled()) {
/* 500 */               loger.debug("调用插件 : " + plugin.getClass() + " getAllocationHtml 开始...");
/*     */             }
/* 502 */             IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
/* 503 */             if (event.canBeExecute(item.getCat_id())) {
/* 504 */               html = ((IOrderAllocationItemEvent)plugin).getAllocationStoreHtml(item);
/*     */             }
/* 506 */             if (loger.isDebugEnabled()) {
/* 507 */               loger.debug("调用插件 : " + plugin.getClass() + " getAllocationHtml 结束.");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 513 */       return html;
/*     */     } catch (RuntimeException e) {
/* 515 */       if (loger.isErrorEnabled())
/* 516 */         loger.error("调用订单插件[getAllocationHtml]事件错误", e);
/*     */     }
/* 518 */     throw e;
/*     */   }
/*     */ 
/*     */   public String getAllocationViewHtml(OrderItem item)
/*     */   {
/* 528 */     String html = null;
/*     */     try {
/* 530 */       List plugins = getPlugins();
/*     */ 
/* 532 */       if (plugins != null) {
/* 533 */         for (IPlugin plugin : plugins) {
/* 534 */           if ((plugin instanceof IOrderAllocationItemEvent)) {
/* 535 */             if (loger.isDebugEnabled()) {
/* 536 */               loger.debug("调用插件 : " + plugin.getClass() + " getAllocationViewHtml 开始...");
/*     */             }
/* 538 */             IOrderAllocationItemEvent event = (IOrderAllocationItemEvent)plugin;
/* 539 */             if (event.canBeExecute(item.getCat_id())) {
/* 540 */               html = ((IOrderAllocationItemEvent)plugin).getAllocationViewHtml(item);
/*     */             }
/* 542 */             if (loger.isDebugEnabled()) {
/* 543 */               loger.debug("调用插件 : " + plugin.getClass() + " getAllocationViewHtml 结束.");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 549 */       return html;
/*     */     } catch (RuntimeException e) {
/* 551 */       if (loger.isErrorEnabled())
/* 552 */         loger.error("调用订单插件[getAllocationViewHtml]事件错误", e); 
/*     */     }
/* 553 */     throw e;
/*     */   }
/*     */ 
/*     */   public void onPay(Order order, boolean isOnline)
/*     */   {
/*     */     try
/*     */     {
/* 565 */       List plugins = getPlugins();
/*     */ 
/* 567 */       if (plugins != null) {
/* 568 */         for (IPlugin plugin : plugins) {
/* 569 */           if ((plugin instanceof IOrderPayEvent)) {
/* 570 */             if (loger.isDebugEnabled()) {
/* 571 */               loger.debug("调用插件 : " + plugin.getClass() + " pay 开始...");
/*     */             }
/* 573 */             IOrderPayEvent event = (IOrderPayEvent)plugin;
/* 574 */             event.pay(order, isOnline);
/*     */ 
/* 576 */             if (loger.isDebugEnabled())
/* 577 */               loger.debug("调用插件 : " + plugin.getClass() + " pay 结束.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 584 */       if (loger.isErrorEnabled())
/* 585 */         loger.error("调用订单插件[pay]事件错误", e);
/* 586 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onCanel(Order order) {
/*     */     try {
/* 592 */       List plugins = getPlugins();
/*     */ 
/* 594 */       if (plugins != null) {
/* 595 */         for (IPlugin plugin : plugins) {
/* 596 */           if ((plugin instanceof IOrderCanelEvent)) {
/* 597 */             if (loger.isDebugEnabled()) {
/* 598 */               loger.debug("调用插件 : " + plugin.getClass() + " canel 开始...");
/*     */             }
/* 600 */             IOrderCanelEvent event = (IOrderCanelEvent)plugin;
/* 601 */             event.canel(order);
/*     */ 
/* 603 */             if (loger.isDebugEnabled())
/* 604 */               loger.debug("调用插件 : " + plugin.getClass() + " canel 结束.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 611 */       if (loger.isErrorEnabled())
/* 612 */         loger.error("调用订单插件[canel]事件错误", e);
/* 613 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onRestore(Order order) {
/*     */     try {
/* 619 */       List plugins = getPlugins();
/*     */ 
/* 621 */       if (plugins != null) {
/* 622 */         for (IPlugin plugin : plugins) {
/* 623 */           if ((plugin instanceof IOrderRestoreEvent)) {
/* 624 */             if (loger.isDebugEnabled()) {
/* 625 */               loger.debug("调用插件 : " + plugin.getClass() + " canel 开始...");
/*     */             }
/* 627 */             IOrderRestoreEvent event = (IOrderRestoreEvent)plugin;
/* 628 */             event.restore(order);
/*     */ 
/* 630 */             if (loger.isDebugEnabled())
/* 631 */               loger.debug("调用插件 : " + plugin.getClass() + " canel 结束.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 638 */       if (loger.isErrorEnabled())
/* 639 */         loger.error("调用订单插件[canel]事件错误", e);
/* 640 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onConfirmPay(Order order)
/*     */   {
/*     */     try
/*     */     {
/* 649 */       List plugins = getPlugins();
/*     */ 
/* 651 */       if (plugins != null) {
/* 652 */         for (IPlugin plugin : plugins) {
/* 653 */           if ((plugin instanceof IOrderConfirmPayEvent)) {
/* 654 */             if (loger.isDebugEnabled()) {
/* 655 */               loger.debug("调用插件 : " + plugin.getClass() + " confirmPay 开始...");
/*     */             }
/* 657 */             IOrderConfirmPayEvent event = (IOrderConfirmPayEvent)plugin;
/* 658 */             event.confirmPay(order);
/*     */ 
/* 660 */             if (loger.isDebugEnabled())
/* 661 */               loger.debug("调用插件 : " + plugin.getClass() + " confirmPay 结束.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 668 */       if (loger.isErrorEnabled())
/* 669 */         loger.error("调用订单插件[confirmPay]事件错误", e);
/* 670 */       throw e;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.order.OrderPluginBundle
 * JD-Core Version:    0.6.0
 */