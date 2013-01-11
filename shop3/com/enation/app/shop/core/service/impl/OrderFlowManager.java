/*      */ package com.enation.app.shop.core.service.impl;
/*      */ 
/*      */ import com.enation.app.base.core.model.Member;
/*      */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*      */ import com.enation.app.shop.core.model.Allocation;
/*      */ import com.enation.app.shop.core.model.AllocationItem;
/*      */ import com.enation.app.shop.core.model.Delivery;
/*      */ import com.enation.app.shop.core.model.DeliveryItem;
/*      */ import com.enation.app.shop.core.model.Logi;
/*      */ import com.enation.app.shop.core.model.Order;
/*      */ import com.enation.app.shop.core.model.OrderItem;
/*      */ import com.enation.app.shop.core.model.OrderLog;
/*      */ import com.enation.app.shop.core.model.PaymentLog;
/*      */ import com.enation.app.shop.core.model.Product;
/*      */ import com.enation.app.shop.core.model.RefundLog;
/*      */ import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
/*      */ import com.enation.app.shop.core.service.ILogiManager;
/*      */ import com.enation.app.shop.core.service.IMemberManager;
/*      */ import com.enation.app.shop.core.service.IMemberPointManger;
/*      */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*      */ import com.enation.app.shop.core.service.IOrderManager;
/*      */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*      */ import com.enation.app.shop.core.service.IPromotionManager;
/*      */ import com.enation.eop.resource.model.AdminUser;
/*      */ import com.enation.eop.sdk.database.BaseSupport;
/*      */ import com.enation.eop.sdk.user.IUserService;
/*      */ import com.enation.eop.sdk.user.UserServiceFactory;
/*      */ import com.enation.framework.database.DoubleMapper;
/*      */ import com.enation.framework.database.IDaoSupport;
/*      */ import com.enation.framework.util.DateUtil;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.transaction.annotation.Propagation;
/*      */ import org.springframework.transaction.annotation.Transactional;
/*      */ 
/*      */ public class OrderFlowManager extends BaseSupport
/*      */   implements IOrderFlowManager
/*      */ {
/*      */   private IOrderManager orderManager;
/*      */   private IMemberManager memberManager;
/*      */   private IPointHistoryManager pointHistoryManager;
/*      */   private IMemberPointManger memberPointManger;
/*      */   private OrderPluginBundle orderPluginBundle;
/*      */   private ILogiManager logiManager;
/*      */   private IAdminUserManager adminUserManager;
/*      */   private IPromotionManager promotionManager;
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void pay(PaymentLog payment, boolean isOnline)
/*      */   {
/*   64 */     if (payment == null) throw new IllegalArgumentException("param paymentLog is NULL");
/*   65 */     if (payment.getOrder_sn() == null) throw new IllegalArgumentException("param PaymentLog's order_sn is NULL");
/*   66 */     if (payment.getMoney() == null) throw new IllegalArgumentException("param  PaymentLog's money is NULL");
/*   67 */     Order order = this.orderManager.get(payment.getOrder_sn());
/*      */ 
/*   69 */     checkDisabled(order);
/*   70 */     if (order.getPay_status().intValue() == 1) {
/*   71 */       if (this.logger.isDebugEnabled()) {
/*   72 */         this.logger.debug("订单[" + order.getSn() + "]支付状态为[已经支付]，不能再对其进行支付操作");
/*      */       }
/*   74 */       return;
/*      */     }
/*      */ 
/*   78 */     if (this.logger.isDebugEnabled()) {
/*   79 */       this.logger.debug("支付订单:" + order.getOrder_id());
/*      */     }
/*      */ 
/*   83 */     if (isOnline)
/*   84 */       payment.setType(1);
/*      */     else {
/*   86 */       payment.setType(2);
/*      */     }
/*      */ 
/*   89 */     payment.setStatus(Integer.valueOf(0));
/*   90 */     payment.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*      */ 
/*   92 */     this.baseDaoSupport.insert("payment_logs", payment);
/*   93 */     this.baseDaoSupport.execute("update order set status=?,pay_status=?  where order_id=?", new Object[] { Integer.valueOf(1), Integer.valueOf(1), order.getOrder_id() });
/*      */ 
/*   95 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*   96 */     if (member == null) {
/*   97 */       if ((order.getMember_id() != null) && (order.getMember_id().intValue() > 0))
/*      */       {
/*   99 */         log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), order.getMember_id(), "会员：" + this.memberManager.get(order.getMember_id()).getUname());
/*      */       }
/*  101 */       else log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), null, "游客");
/*      */     }
/*      */     else {
/*  104 */       log(order.getOrder_id(), "支付订单，金额" + payment.getMoney(), member.getMember_id(), "会员：" + member.getUname());
/*      */     }
/*      */ 
/*  108 */     this.orderPluginBundle.onPay(order, isOnline);
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void refund(RefundLog refund)
/*      */   {
/*  121 */     if (refund == null) throw new IllegalArgumentException("param paymentLog is NULL");
/*      */ 
/*  123 */     if (refund.getMoney() == null) throw new IllegalArgumentException("param PaymentLog's money is NULL");
/*  124 */     Order order = this.orderManager.get(Integer.valueOf(refund.getOrder_id()));
/*  125 */     checkDisabled(order);
/*  126 */     if (order.getPay_status().intValue() == 3) {
/*  127 */       if (this.logger.isDebugEnabled()) {
/*  128 */         this.logger.debug("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
/*      */       }
/*  130 */       throw new IllegalStateException("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
/*      */     }
/*      */ 
/*  133 */     if (this.logger.isDebugEnabled()) {
/*  134 */       this.logger.debug("订单:" + order.getOrder_id() + "退款");
/*      */     }
/*      */ 
/*  138 */     double m = order.getOrder_amount().doubleValue();
/*  139 */     double nm = refund.getMoney().doubleValue();
/*  140 */     double om = order.getPaymoney().doubleValue();
/*      */ 
/*  143 */     int payStatus = 0;
/*  144 */     if (nm == om)
/*  145 */       payStatus = 3;
/*  146 */     if (nm < om)
/*  147 */       payStatus = 4;
/*  148 */     if (nm > om) {
/*  149 */       if (this.logger.isDebugEnabled()) {
/*  150 */         this.logger.debug("退款金额[" + nm + "]超过订单支付金额[" + m + "]");
/*      */       }
/*  152 */       throw new RuntimeException("退款金额[" + nm + "]超过订单支付金额[" + om + "]");
/*      */     }
/*  154 */     refund.setOrder_sn(order.getSn());
/*  155 */     refund.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  156 */     refund.setMember_id(order.getMember_id());
/*  157 */     this.baseDaoSupport.insert("refund_logs", refund);
/*      */ 
/*  161 */     if (this.logger.isDebugEnabled()) {
/*  162 */       this.logger.debug("更新订单状态[-1],支付状态[" + payStatus + "]");
/*      */     }
/*  164 */     this.baseDaoSupport.execute("update order set status=?,pay_status=?,paymoney=paymoney-? where order_id=?", new Object[] { Integer.valueOf(-1), Integer.valueOf(payStatus), refund.getMoney(), order.getOrder_id() });
/*      */ 
/*  166 */     log(order.getOrder_id(), "订单退款，金额" + refund.getMoney());
/*      */   }
/*      */ 
/*      */   private void log(Integer order_id, String message)
/*      */   {
/*  178 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  179 */     OrderLog orderLog = new OrderLog();
/*  180 */     orderLog.setMessage(message);
/*  181 */     orderLog.setOp_id(adminUser.getUserid());
/*  182 */     orderLog.setOp_name(adminUser.getUsername());
/*  183 */     orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
/*  184 */     orderLog.setOrder_id(order_id);
/*  185 */     this.baseDaoSupport.insert("order_log", orderLog);
/*      */   }
/*      */ 
/*      */   private void log(Integer order_id, String message, Integer op_id, String op_name)
/*      */   {
/*  190 */     OrderLog orderLog = new OrderLog();
/*  191 */     orderLog.setMessage(message);
/*  192 */     orderLog.setOp_id(op_id);
/*  193 */     orderLog.setOp_name(op_name);
/*  194 */     orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
/*  195 */     orderLog.setOrder_id(order_id);
/*  196 */     this.baseDaoSupport.insert("order_log", orderLog);
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void allocation(Allocation allocation)
/*      */   {
/*  207 */     List itemList = allocation.getItemList();
/*  208 */     int orderid = allocation.getOrderid();
/*      */ 
/*  210 */     for (AllocationItem item : itemList) {
/*  211 */       item.setOrderid(orderid);
/*      */ 
/*  213 */       this.orderPluginBundle.onAllocationItem(item);
/*      */ 
/*  215 */       this.baseDaoSupport.insert("allocation_item", item);
/*      */     }
/*      */ 
/*  220 */     this.baseDaoSupport.execute("update order set depotid=?,status=?,ship_status=?,allocation_time=? where order_id=?", new Object[] { Integer.valueOf(allocation.getShipDepotId()), Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(DateUtil.getDateline()), Integer.valueOf(allocation.getOrderid()) });
/*      */ 
/*  223 */     this.orderPluginBundle.onAllocation(allocation);
/*      */ 
/*  225 */     if (this.logger.isDebugEnabled())
/*  226 */       this.logger.debug("订单[" + allocation.getOrderid() + "]配货成功");
/*      */   }
/*      */ 
/*      */   public String getAllocationHtml(int itemid)
/*      */   {
/*  234 */     OrderItem item = this.orderManager.getItem(itemid);
/*  235 */     String html = this.orderPluginBundle.getAllocationHtml(item);
/*  236 */     return html;
/*      */   }
/*      */ 
/*      */   public String getAllocationViewHtml(int itemid)
/*      */   {
/*  242 */     OrderItem item = this.orderManager.getItem(itemid);
/*  243 */     String html = this.orderPluginBundle.getAllocationViewHtml(item);
/*  244 */     return html;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void shipping(Delivery delivery, List<DeliveryItem> itemList)
/*      */   {
/*  261 */     if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
/*  262 */     if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
/*  263 */     if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");
/*      */ 
/*  265 */     if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
/*  266 */     if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));
/*      */ 
/*  268 */     if (this.logger.isDebugEnabled()) {
/*  269 */       this.logger.debug("订单[" + delivery.getOrder_id() + "]发货");
/*      */     }
/*      */ 
/*  272 */     Integer orderId = delivery.getOrder_id();
/*  273 */     Order order = this.orderManager.get(orderId);
/*  274 */     delivery.setOrder(order);
/*      */ 
/*  276 */     checkDisabled(order);
/*  277 */     if (order.getShip_status().intValue() == 3) {
/*  278 */       if (this.logger.isDebugEnabled()) {
/*  279 */         this.logger.debug("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
/*      */       }
/*  281 */       throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
/*      */     }
/*      */ 
/*  284 */     if (delivery.getLogi_id() != null) {
/*  285 */       Logi logi = this.logiManager.getLogiById(delivery.getLogi_id());
/*  286 */       delivery.setLogi_code(logi.getCode());
/*  287 */       delivery.setLogi_name(logi.getName());
/*      */     }
/*      */ 
/*  290 */     delivery.setType(Integer.valueOf(1));
/*  291 */     delivery.setMember_id(order.getMember_id());
/*  292 */     delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  293 */     this.baseDaoSupport.insert("delivery", delivery);
/*  294 */     Integer delivery_id = Integer.valueOf(this.baseDaoSupport.getLastId("delivery"));
/*      */ 
/*  296 */     int shipStatus = 3;
/*      */ 
/*  299 */     int goodsShipStatus = processGoodsShipItem(orderId, delivery_id, itemList);
/*  300 */     shipStatus = goodsShipStatus;
/*      */ 
/*  304 */     shipStatus = shipStatus == 3 ? 3 : 5;
/*      */ 
/*  313 */     for (Iterator i$ = itemList.iterator(); i$.hasNext(); ) { deliverItem = (DeliveryItem)i$.next();
/*  314 */       List alloitemList = getAllocationList(orderId.intValue(), deliverItem.getOrder_itemid());
/*  315 */       for (AllocationItem alloItem : alloitemList)
/*  316 */         this.orderPluginBundle.onItemShip(order, deliverItem, alloItem);
/*      */     }
/*      */     DeliveryItem deliverItem;
/*  327 */     this.orderPluginBundle.onShip(delivery, itemList);
/*      */ 
/*  331 */     if (this.logger.isDebugEnabled()) {
/*  332 */       this.logger.debug("更新订单[" + orderId + "]状态[" + 5 + "]，发货状态[" + shipStatus + "]");
/*      */     }
/*      */ 
/*  335 */     this.baseDaoSupport.execute("update order set status=?,ship_status=? where order_id=?", new Object[] { Integer.valueOf(5), Integer.valueOf(shipStatus), orderId });
/*  336 */     log(delivery.getOrder_id(), "订单发货，物流公司：" + delivery.getLogi_name() + "，物流单据号：" + delivery.getLogi_no());
/*      */   }
/*      */ 
/*      */   private List<AllocationItem> getAllocationList(int orderid, int itemid)
/*      */   {
/*  348 */     String sql = "select a.*,g.cat_id from " + getTableName("allocation_item") + "  a ," + getTableName("goods") + " g where a.orderid=? and a.itemid=? and a.goodsid=g.goods_id ";
/*  349 */     return this.daoSupport.queryForList(sql, AllocationItem.class, new Object[] { Integer.valueOf(orderid), Integer.valueOf(itemid) });
/*      */   }
/*      */ 
/*      */   private int processGoodsReturnItem(Integer orderId, Integer delivery_id, List<DeliveryItem> itemList)
/*      */   {
/*  364 */     List orderItemList = this.orderManager.listGoodsItems(orderId);
/*  365 */     int shipStatus = 4;
/*  366 */     for (DeliveryItem item : itemList)
/*      */     {
/*  368 */       if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
/*  369 */       if (item.getProduct_id() == null) throw new IllegalArgumentException(item.getName() + " product id is  NULL");
/*  370 */       if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");
/*      */ 
/*  372 */       if (this.logger.isDebugEnabled()) {
/*  373 */         this.logger.debug("检测item[" + item.getName() + "]退货数量是否合法");
/*      */       }
/*      */ 
/*  376 */       int itemStatus = checkGoodsReturnNum(orderItemList, item);
/*      */ 
/*  379 */       shipStatus = (shipStatus == 4) && (itemStatus == 4) ? 4 : itemStatus;
/*      */ 
/*  381 */       item.setDelivery_id(delivery_id);
/*      */ 
/*  383 */       this.baseDaoSupport.insert("delivery_item", item);
/*      */ 
/*  386 */       this.baseDaoSupport.execute("update order_items set ship_num=ship_num-? where order_id=? and spec_id=?", new Object[] { item.getNum(), orderId, item.getProduct_id() });
/*      */ 
/*  389 */       this.baseDaoSupport.execute("update goods set store=store+? where goods_id=?", new Object[] { item.getNum(), item.getGoods_id() });
/*  390 */       this.baseDaoSupport.execute("update product set store=store+? where product_id=?", new Object[] { item.getNum(), item.getProduct_id() });
/*      */     }
/*      */ 
/*  393 */     return shipStatus;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   private int processGoodsShipItem(Integer orderId, Integer delivery_id, List<DeliveryItem> itemList)
/*      */   {
/*  410 */     List productList = listProductbyOrderId(orderId);
/*  411 */     List orderItemList = this.orderManager.listGoodsItems(orderId);
/*      */ 
/*  413 */     int shipStatus = 3;
/*      */ 
/*  415 */     for (DeliveryItem item : itemList)
/*      */     {
/*  417 */       if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
/*  418 */       if (item.getProduct_id() == null) throw new IllegalArgumentException(item.getName() + " product id is  NULL");
/*  419 */       if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");
/*      */ 
/*  421 */       if (this.logger.isDebugEnabled()) {
/*  422 */         this.logger.debug("检测item[" + item.getName() + "]发货数量是否合法");
/*      */       }
/*      */ 
/*  425 */       int itemStatus = checkGoodsShipNum(orderItemList, item);
/*      */ 
/*  428 */       shipStatus = (shipStatus == 3) && (itemStatus == 3) ? 3 : itemStatus;
/*      */ 
/*  431 */       if (this.logger.isDebugEnabled()) {
/*  432 */         this.logger.debug("检测item[" + item.getName() + "]库存");
/*      */       }
/*      */ 
/*  437 */       item.setDelivery_id(delivery_id);
/*      */ 
/*  439 */       this.baseDaoSupport.insert("delivery_item", item);
/*      */ 
/*  443 */       this.baseDaoSupport.execute("update order_items set ship_num=ship_num+? where order_id=? and product_id=?", new Object[] { item.getNum(), orderId, item.getProduct_id() });
/*      */ 
/*  449 */       this.baseDaoSupport.execute("update goods set store=store-? where goods_id=?", new Object[] { item.getNum(), item.getGoods_id() });
/*  450 */       this.baseDaoSupport.execute("update product set store=store-? where product_id=?", new Object[] { item.getNum(), item.getProduct_id() });
/*      */ 
/*  453 */       if (this.logger.isDebugEnabled()) {
/*  454 */         this.logger.debug("更新" + item.getName() + "[" + item.getProduct_id() + "," + item.getGoods_id() + "-[" + item.getNum() + "]");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  459 */     return shipStatus;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void returned(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> giftItemList)
/*      */   {
/*  470 */     if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
/*  471 */     if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
/*  472 */     if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");
/*      */ 
/*  474 */     if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
/*  475 */     if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));
/*      */ 
/*  477 */     if (this.logger.isDebugEnabled()) {
/*  478 */       this.logger.debug("订单[" + delivery.getOrder_id() + "]退货");
/*      */     }
/*      */ 
/*  481 */     Integer orderId = delivery.getOrder_id();
/*  482 */     Order order = this.orderManager.get(orderId);
/*  483 */     checkDisabled(order);
/*  484 */     if (order.getShip_status().intValue() == 4) {
/*  485 */       if (this.logger.isDebugEnabled()) {
/*  486 */         this.logger.debug("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
/*      */       }
/*  488 */       throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
/*      */     }
/*      */ 
/*  491 */     if (delivery.getLogi_id() != null) {
/*  492 */       Logi logi = this.logiManager.getLogiById(delivery.getLogi_id());
/*  493 */       delivery.setLogi_code(logi.getCode());
/*  494 */       delivery.setLogi_name(logi.getName());
/*      */     }
/*      */ 
/*  497 */     delivery.setType(Integer.valueOf(2));
/*  498 */     delivery.setMember_id(order.getMember_id());
/*  499 */     delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  500 */     this.baseDaoSupport.insert("delivery", delivery);
/*  501 */     Integer delivery_id = Integer.valueOf(this.baseDaoSupport.getLastId("delivery"));
/*      */ 
/*  506 */     int shipStatus = 4;
/*  507 */     int goodsShipStatus = processGoodsReturnItem(orderId, delivery_id, itemList);
/*  508 */     shipStatus = goodsShipStatus;
/*      */ 
/*  511 */     shipStatus = shipStatus == 4 ? 4 : 6;
/*      */ 
/*  519 */     this.orderPluginBundle.onReturned(delivery, itemList);
/*      */ 
/*  522 */     if (this.logger.isDebugEnabled()) {
/*  523 */       this.logger.debug("更新订单[" + orderId + "]状态[" + -2 + "]，发货状态[" + shipStatus + "]");
/*      */     }
/*      */ 
/*  526 */     this.baseDaoSupport.execute("update order set status=?,ship_status=? where order_id=?", new Object[] { Integer.valueOf(-2), Integer.valueOf(shipStatus), orderId });
/*      */ 
/*  529 */     log(delivery.getOrder_id(), "订单退货，物流单据号：" + delivery.getLogi_no());
/*      */   }
/*      */ 
/*      */   public void change(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> gifitemList)
/*      */   {
/*  536 */     if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
/*  537 */     if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
/*  538 */     if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");
/*      */ 
/*  540 */     if (delivery.getMoney() == null) delivery.setMoney(Double.valueOf(0.0D));
/*  541 */     if (delivery.getProtect_price() == null) delivery.setProtect_price(Double.valueOf(0.0D));
/*      */ 
/*  543 */     if (this.logger.isDebugEnabled()) {
/*  544 */       this.logger.debug("订单[" + delivery.getOrder_id() + "]换货");
/*      */     }
/*      */ 
/*  547 */     Integer orderId = delivery.getOrder_id();
/*  548 */     Order order = this.orderManager.get(orderId);
/*  549 */     checkDisabled(order);
/*      */ 
/*  552 */     delivery.setType(Integer.valueOf(3));
/*  553 */     delivery.setMember_id(order.getMember_id());
/*  554 */     delivery.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  555 */     this.baseDaoSupport.insert("delivery", delivery);
/*      */ 
/*  557 */     if (this.logger.isDebugEnabled()) {
/*  558 */       this.logger.debug("更新订单[" + orderId + "]状态[" + -7 + "]");
/*      */     }
/*      */ 
/*  561 */     this.baseDaoSupport.execute("update order set status=?, ship_status=? where order_id=?", new Object[] { Integer.valueOf(-7), Integer.valueOf(8), orderId });
/*  562 */     log(delivery.getOrder_id(), "订单换货，物流单据号：" + delivery.getLogi_no());
/*      */   }
/*      */ 
/*      */   private int checkGoodsReturnNum(List<OrderItem> orderItemList, DeliveryItem item)
/*      */   {
/*  575 */     int status = 3;
/*  576 */     for (OrderItem orderItem : orderItemList)
/*      */     {
/*  578 */       if (orderItem.getProduct_id().compareTo(item.getProduct_id()) == 0)
/*      */       {
/*  580 */         Integer shipNum = orderItem.getShip_num();
/*  581 */         if (shipNum.intValue() < item.getNum().intValue()) {
/*  582 */           if (this.logger.isDebugEnabled()) {
/*  583 */             this.logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
/*      */           }
/*  585 */           throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
/*      */         }
/*  587 */         if (shipNum.compareTo(item.getNum()) == 0) {
/*  588 */           status = 4;
/*      */         }
/*      */ 
/*  591 */         if (shipNum.intValue() > item.getNum().intValue()) {
/*  592 */           status = 6;
/*      */         }
/*      */       }
/*      */     }
/*  596 */     return status;
/*      */   }
/*      */ 
/*      */   private int checkGiftReturnNum(List<Map> orderItemList, DeliveryItem item)
/*      */   {
/*  608 */     int status = 3;
/*  609 */     for (Map orderItem : orderItemList)
/*      */     {
/*  611 */       if (Integer.valueOf(orderItem.get("gift_id").toString()).compareTo(item.getGoods_id()) == 0)
/*      */       {
/*  613 */         Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString());
/*  614 */         if (shipNum.intValue() < item.getNum().intValue()) {
/*  615 */           if (this.logger.isDebugEnabled()) {
/*  616 */             this.logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
/*      */           }
/*  618 */           throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
/*      */         }
/*  620 */         if (shipNum.compareTo(item.getNum()) == 0) {
/*  621 */           status = 4;
/*      */         }
/*      */ 
/*  624 */         if (shipNum.intValue() > item.getNum().intValue()) {
/*  625 */           status = 6;
/*      */         }
/*      */       }
/*      */     }
/*  629 */     return status;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void complete(Integer orderId)
/*      */   {
/*  639 */     if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
/*  640 */     this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(7), orderId });
/*  641 */     this.baseDaoSupport.execute("update order set complete_time=? where order_id=?", new Object[] { Long.valueOf(DateUtil.getDatelineLong()), orderId });
/*  642 */     log(orderId, "订单完成");
/*      */   }
/*      */ 
/*      */   public void cancel(Integer orderId)
/*      */   {
/*  649 */     if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
/*  650 */     this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(8), orderId });
/*  651 */     log(orderId, "订单作废");
/*  652 */     Order order = this.orderManager.get(orderId);
/*  653 */     this.orderPluginBundle.onCanel(order);
/*      */   }
/*      */ 
/*      */   public void confirmOrder(Integer orderId)
/*      */   {
/*  661 */     if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
/*  662 */     this.baseDaoSupport.execute("update order set status=?,sale_cmpl=1,sale_cmpl_time=? where order_id=?", new Object[] { Integer.valueOf(9), Integer.valueOf(DateUtil.getDateline()), orderId });
/*  663 */     log(orderId, "订单确认");
/*      */   }
/*      */ 
/*      */   public List<OrderItem> listNotShipGoodsItem(Integer orderId)
/*      */   {
/*  670 */     String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi left join " + getTableName("product") + " p on oi.product_id = p.product_id";
/*  671 */     sql = sql + "  where order_id=? and oi.ship_num<oi.num";
/*  672 */     List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
/*  673 */     this.orderPluginBundle.onFilter(orderId, itemList);
/*  674 */     return itemList;
/*      */   }
/*      */ 
/*      */   public List<OrderItem> listNotAllocationGoodsItem(Integer orderid)
/*      */   {
/*  679 */     String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi  left join " + getTableName("product") + " p on oi.product_id = p.product_id";
/*  680 */     sql = sql + " left join on " + getTableName("order") + " o on oi.order_id = o.order_id  where o.order_id=?";
/*  681 */     List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderid });
/*  682 */     this.orderPluginBundle.onFilter(orderid, itemList);
/*  683 */     return itemList;
/*      */   }
/*      */ 
/*      */   public List<OrderItem> listShipGoodsItem(Integer orderId)
/*      */   {
/*  689 */     String sql = "select oi.*,p.store,p.sn from " + getTableName("order_items") + "  oi left join " + getTableName("product") + " p on oi.product_id = p.product_id";
/*  690 */     sql = sql + "  where order_id=? and ship_num!=0";
/*  691 */     List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
/*  692 */     this.orderPluginBundle.onFilter(orderId, itemList);
/*  693 */     return itemList;
/*      */   }
/*      */ 
/*      */   private int checkGoodsShipNum(List<OrderItem> orderItemList, DeliveryItem item)
/*      */   {
/*  710 */     int status = 2;
/*  711 */     for (OrderItem orderItem : orderItemList)
/*      */     {
/*  713 */       if (orderItem.getItem_id().compareTo(Integer.valueOf(item.getOrder_itemid())) == 0)
/*      */       {
/*  715 */         Integer num = orderItem.getNum();
/*  716 */         Integer shipNum = orderItem.getShip_num();
/*  717 */         if (num.intValue() < item.getNum().intValue() + shipNum.intValue()) {
/*  718 */           if (this.logger.isDebugEnabled()) {
/*  719 */             this.logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
/*      */           }
/*  721 */           throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
/*      */         }
/*  723 */         if (num.compareTo(Integer.valueOf(item.getNum().intValue() + shipNum.intValue())) == 0) {
/*  724 */           status = 3;
/*      */         }
/*      */ 
/*  727 */         if (num.intValue() > item.getNum().intValue() + shipNum.intValue()) {
/*  728 */           status = 5;
/*      */         }
/*      */       }
/*      */     }
/*  732 */     return status;
/*      */   }
/*      */ 
/*      */   private int checkGiftShipNum(List<Map> orderItemList, DeliveryItem item)
/*      */   {
/*  746 */     int status = 2;
/*  747 */     for (Map orderItem : orderItemList)
/*      */     {
/*  749 */       if (Integer.valueOf(orderItem.get("gift_id").toString()).compareTo(item.getGoods_id()) == 0)
/*      */       {
/*  751 */         Integer num = Integer.valueOf(orderItem.get("num").toString());
/*  752 */         Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString());
/*  753 */         if (num.intValue() < item.getNum().intValue() + shipNum.intValue()) {
/*  754 */           if (this.logger.isDebugEnabled()) {
/*  755 */             this.logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
/*      */           }
/*  757 */           throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
/*      */         }
/*  759 */         if (num.compareTo(Integer.valueOf(item.getNum().intValue() + shipNum.intValue())) == 0) {
/*  760 */           status = 3;
/*      */         }
/*      */ 
/*  763 */         if (num.intValue() > item.getNum().intValue() + shipNum.intValue()) {
/*  764 */           status = 5;
/*      */         }
/*      */       }
/*      */     }
/*  768 */     return status;
/*      */   }
/*      */ 
/*      */   private void checkGoodsStore(Integer orderId, List<Product> productList, DeliveryItem item)
/*      */   {
/*  782 */     for (Product product : productList)
/*  783 */       if ((product.getProduct_id().compareTo(item.getProduct_id()) == 0) && 
/*  784 */         (product.getStore().compareTo(item.getNum()) < 0)) {
/*  785 */         if (this.logger.isDebugEnabled()) {
/*  786 */           this.logger.debug("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
/*      */         }
/*      */ 
/*  789 */         throw new RuntimeException("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
/*      */       }
/*      */   }
/*      */ 
/*      */   private List<Product> listProductbyOrderId(Integer orderId)
/*      */   {
/*  804 */     String sql = "select * from " + getTableName("product") + " where product_id in (select product_id from " + getTableName("order_items") + " where order_id=?)";
/*      */ 
/*  807 */     List productList = this.daoSupport.queryForList(sql, Product.class, new Object[] { orderId });
/*  808 */     return productList;
/*      */   }
/*      */ 
/*      */   private void checkDisabled(Order order)
/*      */   {
/*  818 */     if ((order.getStatus().intValue() == 7) || (order.getStatus().intValue() == 8))
/*  819 */       throw new IllegalStateException("订单已经完成或作废，不能完成操作");
/*      */   }
/*      */ 
/*      */   public void updateAllocation(int allocationId, int orderId)
/*      */   {
/*  834 */     String sql = "update " + getTableName("allocation_item") + " set iscmpl=1 where allocationid=" + allocationId;
/*  835 */     this.daoSupport.execute(sql, new Object[0]);
/*  836 */     sql = "update  " + getTableName("order") + " set status=" + 4 + ",ship_status=" + 2 + " where order_id=" + orderId + " and (select count(allocationid) from " + getTableName("allocation_item") + " where orderid =" + orderId + ")=(select sum(iscmpl) from  " + getTableName("allocation_item") + " where  orderid =" + orderId + ")";
/*  837 */     this.daoSupport.execute(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public Order payConfirm(int orderId)
/*      */   {
/*  848 */     Order order = this.orderManager.get(Integer.valueOf(orderId));
/*      */ 
/*  850 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  851 */     if (adminUser != null)
/*  852 */       log(Integer.valueOf(orderId), "确认付款");
/*      */     else {
/*  854 */       log(Integer.valueOf(orderId), "确认付款", null, "系统");
/*      */     }
/*      */ 
/*  857 */     String sql = "select sum(money) money from payment_logs where order_sn=?";
/*  858 */     double paymoney = ((Double)this.baseDaoSupport.queryForObject(sql, new DoubleMapper(), new Object[] { order.getSn() })).doubleValue();
/*  859 */     double ordermoney = order.getOrder_amount().doubleValue();
/*      */ 
/*  865 */     int payStatus = 0;
/*  866 */     int orderStatus = 1;
/*  867 */     if (paymoney < ordermoney) {
/*  868 */       payStatus = 5;
/*      */     }
/*  870 */     if (paymoney >= ordermoney) {
/*  871 */       payStatus = 2;
/*  872 */       orderStatus = 2;
/*      */     }
/*      */ 
/*  877 */     if (this.logger.isDebugEnabled()) {
/*  878 */       this.logger.debug("更新订单状态[" + orderStatus + "],支付状态[" + payStatus + "]");
/*      */     }
/*      */ 
/*  882 */     sql = "update " + getTableName("order") + " set paymoney=?, status=" + orderStatus + ",pay_status=" + payStatus + " where order_id=?";
/*  883 */     this.daoSupport.execute(sql, new Object[] { Double.valueOf(paymoney), order.getOrder_id() });
/*      */ 
/*  885 */     sql = "update payment_logs set status=1 where order_id=?";
/*  886 */     this.baseDaoSupport.execute(sql, new Object[] { order.getOrder_id() });
/*      */ 
/*  888 */     order.setStatus(Integer.valueOf(orderStatus));
/*  889 */     order.setPay_status(Integer.valueOf(payStatus));
/*  890 */     this.orderPluginBundle.onConfirmPay(order);
/*  891 */     return order;
/*      */   }
/*      */ 
/*      */   public void rogConfirm(int orderId, Integer op_id, String op_name, String sign_name, int sign_time)
/*      */   {
/*  905 */     Order order = this.orderManager.get(Integer.valueOf(orderId));
/*  906 */     if ((order.getPayment_id().intValue() == 2) && (order.getStatus().intValue() == 5))
/*      */     {
/*  908 */       payConfirm(orderId);
/*      */     }
/*      */ 
/*  911 */     String sql = "update order set status=6,the_sign='" + sign_name + "',signing_time=" + sign_time + " , sale_cmpl=1 , sale_cmpl_time=" + DateUtil.getDateline() + " where order_id=" + orderId;
/*  912 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  914 */     log(Integer.valueOf(orderId), "确认收货成功", op_id, op_name);
/*      */ 
/*  917 */     List itemList = this.orderManager.getItemsByOrderid(Integer.valueOf(orderId));
/*  918 */     for (Map map : itemList) {
/*  919 */       this.baseDaoSupport.execute("INSERT INTO member_order_item(member_id,goods_id,order_id,item_id,commented,comment_time) VALUES(?,?,?,?,0,0)", new Object[] { order.getMember_id(), map.get("goods_id").toString(), map.get("order_id").toString(), map.get("item_id").toString() });
/*      */ 
/*  924 */       String updatesql = "update goods set buy_count=buy_count+" + map.get("num").toString() + " where goods_id=" + map.get("goods_id").toString();
/*  925 */       this.baseDaoSupport.execute(updatesql, new Object[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cancel(String sn, String reason)
/*      */   {
/*  935 */     Order order = this.orderManager.get(sn);
/*  936 */     if (order == null) {
/*  937 */       throw new RuntimeException("对不起，此订单不存在！");
/*      */     }
/*  939 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/*  940 */       throw new RuntimeException("对不起，此订单不能取消！");
/*      */     }
/*  942 */     IUserService userService = UserServiceFactory.getUserService();
/*  943 */     if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
/*  944 */       throw new RuntimeException("对不起，您没有权限进行此项操作！");
/*      */     }
/*  946 */     order.setStatus(Integer.valueOf(8));
/*  947 */     order.setCancel_reason(reason);
/*  948 */     this.orderManager.edit(order);
/*      */ 
/*  950 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*      */ 
/*  953 */     if (member == null)
/*  954 */       log(order.getOrder_id(), "取消订单", null, "游客");
/*      */     else {
/*  956 */       log(order.getOrder_id(), "取消订单", member.getMember_id(), member.getUname());
/*      */     }
/*      */ 
/*  959 */     this.orderPluginBundle.onCanel(order);
/*      */   }
/*      */ 
/*      */   public void restore(String sn)
/*      */   {
/*  969 */     Order order = this.orderManager.get(sn);
/*  970 */     if (order == null) {
/*  971 */       throw new RuntimeException("对不起，此订单不存在！");
/*      */     }
/*  973 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 8)) {
/*  974 */       throw new RuntimeException("对不起，此订单不能还原！");
/*      */     }
/*  976 */     IUserService userService = UserServiceFactory.getUserService();
/*  977 */     if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
/*  978 */       throw new RuntimeException("对不起，您没有权限进行此项操作！");
/*      */     }
/*  980 */     order.setStatus(Integer.valueOf(0));
/*  981 */     order.setCancel_reason("");
/*  982 */     this.orderManager.edit(order);
/*      */ 
/*  984 */     this.orderPluginBundle.onRestore(order);
/*      */   }
/*      */ 
/*      */   public IOrderManager getOrderManager() {
/*  988 */     return this.orderManager;
/*      */   }
/*      */ 
/*      */   public void setOrderManager(IOrderManager orderManager) {
/*  992 */     this.orderManager = orderManager;
/*      */   }
/*      */ 
/*      */   public IMemberManager getMemberManager() {
/*  996 */     return this.memberManager;
/*      */   }
/*      */ 
/*      */   public void setMemberManager(IMemberManager memberManager) {
/* 1000 */     this.memberManager = memberManager;
/*      */   }
/*      */ 
/*      */   public IPointHistoryManager getPointHistoryManager() {
/* 1004 */     return this.pointHistoryManager;
/*      */   }
/*      */ 
/*      */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/* 1008 */     this.pointHistoryManager = pointHistoryManager;
/*      */   }
/*      */ 
/*      */   public IMemberPointManger getMemberPointManger()
/*      */   {
/* 1014 */     return this.memberPointManger;
/*      */   }
/*      */ 
/*      */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*      */   {
/* 1019 */     this.memberPointManger = memberPointManger;
/*      */   }
/*      */ 
/*      */   public OrderPluginBundle getOrderPluginBundle()
/*      */   {
/* 1024 */     return this.orderPluginBundle;
/*      */   }
/*      */ 
/*      */   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle)
/*      */   {
/* 1029 */     this.orderPluginBundle = orderPluginBundle;
/*      */   }
/*      */ 
/*      */   public ILogiManager getLogiManager()
/*      */   {
/* 1034 */     return this.logiManager;
/*      */   }
/*      */ 
/*      */   public void setLogiManager(ILogiManager logiManager)
/*      */   {
/* 1039 */     this.logiManager = logiManager;
/*      */   }
/*      */ 
/*      */   public IAdminUserManager getAdminUserManager()
/*      */   {
/* 1044 */     return this.adminUserManager;
/*      */   }
/*      */ 
/*      */   public void setAdminUserManager(IAdminUserManager adminUserManager)
/*      */   {
/* 1049 */     this.adminUserManager = adminUserManager;
/*      */   }
/*      */ 
/*      */   public IPromotionManager getPromotionManager()
/*      */   {
/* 1054 */     return this.promotionManager;
/*      */   }
/*      */ 
/*      */   public void setPromotionManager(IPromotionManager promotionManager)
/*      */   {
/* 1059 */     this.promotionManager = promotionManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderFlowManager
 * JD-Core Version:    0.6.0
 */