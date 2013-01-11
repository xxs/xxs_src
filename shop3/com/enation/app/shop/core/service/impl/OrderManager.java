/*      */ package com.enation.app.shop.core.service.impl;
/*      */ 
/*      */ import com.enation.app.base.core.model.Member;
/*      */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*      */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*      */ import com.enation.app.base.core.service.auth.IRoleManager;
/*      */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*      */ import com.enation.app.shop.core.model.DepotUser;
/*      */ import com.enation.app.shop.core.model.DlyType;
/*      */ import com.enation.app.shop.core.model.Goods;
/*      */ import com.enation.app.shop.core.model.Order;
/*      */ import com.enation.app.shop.core.model.OrderItem;
/*      */ import com.enation.app.shop.core.model.OrderLog;
/*      */ import com.enation.app.shop.core.model.PayCfg;
/*      */ import com.enation.app.shop.core.model.Promotion;
/*      */ import com.enation.app.shop.core.model.support.CartItem;
/*      */ import com.enation.app.shop.core.model.support.OrderPrice;
/*      */ import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
/*      */ import com.enation.app.shop.core.service.ICartManager;
/*      */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*      */ import com.enation.app.shop.core.service.IGoodsManager;
/*      */ import com.enation.app.shop.core.service.IOrderManager;
/*      */ import com.enation.app.shop.core.service.IPaymentManager;
/*      */ import com.enation.app.shop.core.service.IPromotionManager;
/*      */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*      */ import com.enation.eop.resource.model.AdminUser;
/*      */ import com.enation.eop.resource.model.EopSite;
/*      */ import com.enation.eop.sdk.context.EopContext;
/*      */ import com.enation.eop.sdk.context.EopSetting;
/*      */ import com.enation.eop.sdk.database.BaseSupport;
/*      */ import com.enation.eop.sdk.user.IUserService;
/*      */ import com.enation.eop.sdk.user.UserServiceFactory;
/*      */ import com.enation.framework.database.DoubleMapper;
/*      */ import com.enation.framework.database.IDaoSupport;
/*      */ import com.enation.framework.database.Page;
/*      */ import com.enation.framework.database.StringMapper;
/*      */ import com.enation.framework.util.CurrencyUtil;
/*      */ import com.enation.framework.util.ExcelUtil;
/*      */ import com.enation.framework.util.FileUtil;
/*      */ import com.enation.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.InputStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.springframework.jdbc.core.RowMapper;
/*      */ import org.springframework.transaction.annotation.Propagation;
/*      */ import org.springframework.transaction.annotation.Transactional;
/*      */ 
/*      */ public class OrderManager extends BaseSupport
/*      */   implements IOrderManager
/*      */ {
/*      */   private ICartManager cartManager;
/*      */   private IDlyTypeManager dlyTypeManager;
/*      */   private IPaymentManager paymentManager;
/*      */   private IPromotionManager promotionManager;
/*      */   private OrderPluginBundle orderPluginBundle;
/*      */   private IPermissionManager permissionManager;
/*      */   private IAdminUserManager adminUserManager;
/*      */   private IRoleManager roleManager;
/*      */   private IGoodsManager goodsManager;
/*      */ 
/*      */   public IGoodsManager getGoodsManager()
/*      */   {
/*   76 */     return this.goodsManager;
/*      */   }
/*      */   public void setGoodsManager(IGoodsManager goodsManager) {
/*   79 */     this.goodsManager = goodsManager;
/*      */   }
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void savePrice(double price, int orderid) {
/*   84 */     Order order = get(Integer.valueOf(orderid));
/*   85 */     double amount = order.getOrder_amount().doubleValue();
/*      */ 
/*   87 */     double discount = CurrencyUtil.sub(amount, price);
/*   88 */     this.baseDaoSupport.execute("update order set order_amount=? where order_id=?", new Object[] { Double.valueOf(price), Integer.valueOf(orderid) });
/*      */ 
/*   91 */     this.baseDaoSupport.execute("update order set discount=discount+? where order_id=?", new Object[] { Double.valueOf(discount), Integer.valueOf(orderid) });
/*      */ 
/*   94 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*   95 */     double nowamount = get(Integer.valueOf(orderid)).getOrder_amount().doubleValue();
/*   96 */     log(Integer.valueOf(orderid), new StringBuilder().append("修改订单价格从").append(amount).append("修改为").append(nowamount).toString(), null, adminUser.getUsername());
/*      */   }
/*  100 */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public double saveShipmoney(double shipmoney, int orderid) { Order order = get(Integer.valueOf(orderid));
/*  101 */     double currshipamount = order.getShipping_amount().doubleValue();
/*      */ 
/*  103 */     double shortship = CurrencyUtil.sub(shipmoney, currshipamount);
/*  104 */     double discount = CurrencyUtil.sub(currshipamount, shipmoney);
/*  105 */     this.baseDaoSupport.execute("update order set order_amount=order_amount+? where order_id=?", new Object[] { Double.valueOf(shortship), Integer.valueOf(orderid) });
/*      */ 
/*  108 */     this.baseDaoSupport.execute("update order set shipping_amount=? where order_id=?", new Object[] { Double.valueOf(shipmoney), Integer.valueOf(orderid) });
/*      */ 
/*  111 */     this.baseDaoSupport.execute("update order set discount=discount+? where order_id=?", new Object[] { Double.valueOf(discount), Integer.valueOf(orderid) });
/*      */ 
/*  114 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*      */ 
/*  116 */     double lastestShipmoney = get(Integer.valueOf(orderid)).getShipping_amount().doubleValue();
/*  117 */     log(Integer.valueOf(orderid), new StringBuilder().append("运费从").append(currshipamount).append("修改为").append(lastestShipmoney).toString(), null, adminUser.getUsername());
/*  118 */     return get(Integer.valueOf(orderid)).getOrder_amount().doubleValue();
/*      */   }
/*      */ 
/*      */   private void log(Integer order_id, String message, Integer op_id, String op_name)
/*      */   {
/*  130 */     OrderLog orderLog = new OrderLog();
/*  131 */     orderLog.setMessage(message);
/*  132 */     orderLog.setOp_id(op_id);
/*  133 */     orderLog.setOp_name(op_name);
/*  134 */     orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
/*  135 */     orderLog.setOrder_id(order_id);
/*  136 */     this.baseDaoSupport.insert("order_log", orderLog);
/*      */   }
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public Order add(Order order, String sessionid) {
/*  141 */     String opname = "游客";
/*      */ 
/*  143 */     if (order == null) {
/*  144 */       throw new RuntimeException("error: order is null");
/*      */     }
/*      */ 
/*  147 */     IUserService userService = UserServiceFactory.getUserService();
/*  148 */     Member member = userService.getCurrentMember();
/*      */ 
/*  150 */     if (member != null) {
/*  151 */       order.setMember_id(member.getMember_id());
/*  152 */       opname = member.getUname();
/*      */     }
/*      */ 
/*  157 */     boolean isProtected = order.getIs_protect().compareTo(Integer.valueOf(1)) == 0;
/*      */ 
/*  159 */     OrderPrice orderPrice = this.cartManager.countPrice(sessionid, order.getShipping_id(), new StringBuilder().append("").append(order.getRegionid()).toString(), Boolean.valueOf(isProtected));
/*      */ 
/*  161 */     order.setGoods_amount(orderPrice.getGoodsPrice());
/*  162 */     order.setWeight(orderPrice.getWeight());
/*      */ 
/*  166 */     order.setDiscount(orderPrice.getDiscountPrice());
/*  167 */     order.setOrder_amount(orderPrice.getOrderPrice());
/*  168 */     order.setProtect_price(orderPrice.getProtectPrice());
/*  169 */     order.setShipping_amount(orderPrice.getShippingPrice());
/*  170 */     order.setGainedpoint(orderPrice.getPoint().intValue());
/*      */ 
/*  173 */     DlyType dlyType = this.dlyTypeManager.getDlyTypeById(order.getShipping_id());
/*  174 */     if (dlyType == null)
/*  175 */       throw new RuntimeException("shipping not found count error");
/*  176 */     order.setShipping_type(dlyType.getName());
/*      */ 
/*  179 */     PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
/*  180 */     order.setPaymoney(this.paymentManager.countPayPrice(order.getOrder_id()));
/*  181 */     order.setPayment_name(payCfg.getName());
/*      */ 
/*  183 */     order.setPayment_type(payCfg.getType());
/*      */ 
/*  186 */     order.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  187 */     order.setSn(createSn());
/*  188 */     order.setStatus(Integer.valueOf(0));
/*  189 */     order.setDisabled(Integer.valueOf(0));
/*  190 */     order.setPay_status(Integer.valueOf(0));
/*  191 */     order.setShip_status(Integer.valueOf(0));
/*      */ 
/*  194 */     List itemList = this.cartManager.listGoods(sessionid);
/*      */ 
/*  196 */     this.orderPluginBundle.onBeforeCreate(order, itemList, sessionid);
/*      */ 
/*  198 */     this.baseDaoSupport.insert("order", order);
/*      */ 
/*  202 */     if (itemList.isEmpty()) {
/*  203 */       throw new RuntimeException("创建订单失败，购物车为空");
/*      */     }
/*  205 */     Integer orderId = Integer.valueOf(this.baseDaoSupport.getLastId("order"));
/*      */ 
/*  208 */     saveGoodsItem(itemList, orderId);
/*      */ 
/*  215 */     if (member != null) {
/*  216 */       this.promotionManager.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id());
/*      */ 
/*  218 */       List pmtList = this.promotionManager.list(orderPrice.getOrderPrice(), member.getLv_id());
/*      */ 
/*  220 */       for (Promotion pmt : pmtList) {
/*  221 */         String sql = "insert into order_pmt(pmt_id,order_id,pmt_describe)values(?,?,?)";
/*  222 */         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(pmt.getPmt_id()), orderId, pmt.getPmt_describe() });
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  229 */     OrderLog log = new OrderLog();
/*  230 */     log.setMessage("订单创建");
/*  231 */     log.setOp_name(opname);
/*  232 */     log.setOrder_id(orderId);
/*  233 */     addLog(log);
/*  234 */     order.setOrder_id(orderId);
/*  235 */     order.setOrderprice(orderPrice);
/*  236 */     this.orderPluginBundle.onAfterCreate(order, itemList, sessionid);
/*      */ 
/*  238 */     this.cartManager.clean(sessionid);
/*      */ 
/*  240 */     HttpCacheManager.sessionChange();
/*      */ 
/*  242 */     return order;
/*      */   }
/*      */ 
/*      */   private void addLog(OrderLog log)
/*      */   {
/*  251 */     log.setOp_time(Long.valueOf(System.currentTimeMillis()));
/*  252 */     this.baseDaoSupport.insert("order_log", log);
/*      */   }
/*      */ 
/*      */   private void saveGoodsItem(List<CartItem> itemList, Integer order_id)
/*      */   {
/*  262 */     for (int i = 0; i < itemList.size(); i++)
/*      */     {
/*  264 */       OrderItem orderItem = new OrderItem();
/*      */ 
/*  266 */       CartItem cartItem = (CartItem)itemList.get(i);
/*  267 */       orderItem.setPrice(cartItem.getCoupPrice());
/*  268 */       orderItem.setName(cartItem.getName());
/*  269 */       orderItem.setNum(Integer.valueOf(cartItem.getNum()));
/*      */ 
/*  271 */       orderItem.setGoods_id(cartItem.getGoods_id());
/*  272 */       orderItem.setShip_num(Integer.valueOf(0));
/*  273 */       orderItem.setProduct_id(cartItem.getProduct_id());
/*  274 */       orderItem.setOrder_id(order_id);
/*  275 */       orderItem.setGainedpoint(cartItem.getPoint().intValue());
/*  276 */       orderItem.setAddon(cartItem.getAddon());
/*      */ 
/*  279 */       orderItem.setSn(cartItem.getSn());
/*  280 */       orderItem.setImage(cartItem.getImage_default());
/*  281 */       orderItem.setCat_id(cartItem.getCatid());
/*      */ 
/*  283 */       orderItem.setUnit(cartItem.getUnit());
/*      */ 
/*  285 */       this.baseDaoSupport.insert("order_items", orderItem);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   private void saveGiftItem(List<CartItem> itemList, Integer orderid)
/*      */   {
/*  299 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  300 */     if (member == null) {
/*  301 */       throw new IllegalStateException("会员尚未登录,不能兑换赠品!");
/*      */     }
/*      */ 
/*  304 */     int point = 0;
/*  305 */     for (CartItem item : itemList) {
/*  306 */       point += item.getSubtotal().intValue();
/*  307 */       this.baseDaoSupport.execute("insert into order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)", new Object[] { orderid, item.getProduct_id(), item.getName(), item.getPoint(), Integer.valueOf(item.getNum()), Integer.valueOf(0), "exchange" });
/*      */     }
/*      */ 
/*  313 */     if (member.getPoint().intValue() < point) {
/*  314 */       throw new IllegalStateException("会员积分不足,不能兑换赠品!");
/*      */     }
/*  316 */     member.setPoint(Integer.valueOf(member.getPoint().intValue() - point));
/*  317 */     this.baseDaoSupport.execute("update member set point=? where member_id=? ", new Object[] { member.getPoint(), member.getMember_id() });
/*      */   }
/*      */ 
/*      */   public Page list(int pageNO, int pageSize, int disabled, String searchkey, String searchValue, String order)
/*      */   {
/*  326 */     StringBuffer sql = new StringBuffer("select * from order where disabled=? ");
/*      */ 
/*  329 */     if ((!StringUtil.isEmpty(searchkey)) && (!StringUtil.isEmpty(searchValue))) {
/*  330 */       sql.append(" and ");
/*  331 */       sql.append(searchkey);
/*  332 */       sql.append("=?");
/*      */     }
/*      */ 
/*  335 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  336 */     if (adminUser.getFounder() != 1) {
/*  337 */       boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));
/*  338 */       boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));
/*      */ 
/*  340 */       boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));
/*      */ 
/*  342 */       if ((isShiper) && (!haveAllo) && (!haveOrder)) {
/*  343 */         DepotUser depotUser = (DepotUser)adminUser;
/*  344 */         int depotid = depotUser.getDepotid().intValue();
/*  345 */         sql.append(new StringBuilder().append(" and depotid=").append(depotid).toString());
/*      */       }
/*      */     }
/*      */ 
/*  349 */     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
/*  350 */     sql.append(new StringBuilder().append(" order by ").append(order).toString());
/*  351 */     Page page = null;
/*      */ 
/*  353 */     if ((!StringUtil.isEmpty(searchkey)) && (!StringUtil.isEmpty(searchValue)))
/*      */     {
/*  356 */       page = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled), searchValue });
/*      */     }
/*      */     else {
/*  359 */       page = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });
/*      */     }
/*      */ 
/*  362 */     return page;
/*      */   }
/*      */ 
/*      */   public Page list(int pageNo, int pageSize, int status, int depotid, String order)
/*      */   {
/*  367 */     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
/*  368 */     String sql = new StringBuilder().append("select * from order where disabled=0 and status=").append(status).toString();
/*      */ 
/*  370 */     if (depotid > 0) {
/*  371 */       sql = new StringBuilder().append(sql).append(" and depotid=").append(depotid).toString();
/*      */     }
/*  373 */     sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
/*  374 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
/*      */ 
/*  376 */     return page;
/*      */   }
/*      */ 
/*      */   public Page listbyshipid(int pageNo, int pageSize, int status, int shipping_id, String order)
/*      */   {
/*  381 */     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
/*  382 */     String sql = new StringBuilder().append("select * from order where disabled=0 and status=").append(status).append(" and shipping_id= ").append(shipping_id).toString();
/*      */ 
/*  384 */     sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
/*  385 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
/*      */ 
/*  387 */     return page;
/*      */   }
/*      */ 
/*      */   public Page listConfirmPay(int pageNo, int pageSize, String order) {
/*  391 */     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
/*  392 */     String sql = "select * from order where disabled=0 and ((status = 5 and payment_type = 'cod') or status= 1  )";
/*      */ 
/*  395 */     sql = new StringBuilder().append(sql).append(" order by ").append(order).toString();
/*  396 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
/*      */ 
/*  398 */     return page;
/*      */   }
/*      */ 
/*      */   public Order get(Integer orderId) {
/*  402 */     String sql = "select * from order where order_id=?";
/*  403 */     Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[] { orderId });
/*      */ 
/*  405 */     return order;
/*      */   }
/*      */   public Order get(String ordersn) {
/*  408 */     String sql = "select * from order where sn=?";
/*  409 */     Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[] { ordersn });
/*  410 */     return order;
/*      */   }
/*      */ 
/*      */   public List<OrderItem> listGoodsItems(Integer orderId)
/*      */   {
/*  417 */     String sql = new StringBuilder().append("select * from ").append(getTableName("order_items")).toString();
/*  418 */     sql = new StringBuilder().append(sql).append(" where order_id = ?").toString();
/*  419 */     List itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
/*  420 */     this.orderPluginBundle.onFilter(orderId, itemList);
/*  421 */     return itemList;
/*      */   }
/*      */ 
/*      */   public List listGiftItems(Integer orderId)
/*      */   {
/*  426 */     String sql = "select * from order_gift where order_id=?";
/*  427 */     return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
/*      */   }
/*      */ 
/*      */   public List listLogs(Integer orderId)
/*      */   {
/*  435 */     String sql = "select * from order_log where order_id=?";
/*  436 */     return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
/*      */   }
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void clean(Integer[] orderId) {
/*  441 */     String ids = StringUtil.arrayToString(orderId, ",");
/*  442 */     String sql = new StringBuilder().append("delete from order where order_id in (").append(ids).append(")").toString();
/*  443 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  445 */     sql = new StringBuilder().append("delete from order_items where order_id in (").append(ids).append(")").toString();
/*  446 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  448 */     sql = new StringBuilder().append("delete from order_log where order_id in (").append(ids).append(")").toString();
/*  449 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  451 */     sql = new StringBuilder().append("delete from payment_logs where order_id in (").append(ids).append(")").toString();
/*  452 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  454 */     sql = new StringBuilder().append("delete from ").append(getTableName("delivery_item")).append(" where delivery_id in (select delivery_id from ").append(getTableName("delivery")).append(" where order_id in (").append(ids).append("))").toString();
/*      */ 
/*  458 */     this.daoSupport.execute(sql, new Object[0]);
/*      */ 
/*  460 */     sql = new StringBuilder().append("delete from delivery where order_id in (").append(ids).append(")").toString();
/*  461 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */ 
/*  469 */     this.orderPluginBundle.onDelete(orderId);
/*      */   }
/*      */ 
/*      */   private void exec(Integer[] orderId, int disabled)
/*      */   {
/*  475 */     String ids = StringUtil.arrayToString(orderId, ",");
/*  476 */     String sql = new StringBuilder().append("update order set disabled = ? where order_id in (").append(ids).append(")").toString();
/*      */ 
/*  478 */     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(disabled) });
/*      */   }
/*      */ 
/*      */   public void delete(Integer[] orderId) {
/*  482 */     exec(orderId, 1);
/*      */   }
/*      */ 
/*      */   public void revert(Integer[] orderId)
/*      */   {
/*  487 */     exec(orderId, 0);
/*      */   }
/*      */ 
/*      */   private String createSn()
/*      */   {
/*  492 */     Date now = new Date();
/*  493 */     String sn = com.enation.framework.util.DateUtil.toString(now, "yyyyMMddhhmmss");
/*      */ 
/*  495 */     return sn;
/*      */   }
/*      */ 
/*      */   public ICartManager getCartManager() {
/*  499 */     return this.cartManager;
/*      */   }
/*      */ 
/*      */   public void setCartManager(ICartManager cartManager) {
/*  503 */     this.cartManager = cartManager;
/*      */   }
/*      */ 
/*      */   public IDlyTypeManager getDlyTypeManager() {
/*  507 */     return this.dlyTypeManager;
/*      */   }
/*      */ 
/*      */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/*  511 */     this.dlyTypeManager = dlyTypeManager;
/*      */   }
/*      */ 
/*      */   public IPaymentManager getPaymentManager() {
/*  515 */     return this.paymentManager;
/*      */   }
/*      */ 
/*      */   public void setPaymentManager(IPaymentManager paymentManager) {
/*  519 */     this.paymentManager = paymentManager;
/*      */   }
/*      */ 
/*      */   public List listOrderByMemberId(int member_id) {
/*  523 */     String sql = "select * from order where member_id = ? order by create_time desc";
/*  524 */     List list = this.baseDaoSupport.queryForList(sql, Order.class, new Object[] { Integer.valueOf(member_id) });
/*      */ 
/*  526 */     return list;
/*      */   }
/*      */ 
/*      */   public Map mapOrderByMemberId(int memberId) {
/*  530 */     Integer buyTimes = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(0) from order where member_id = ?", new Object[] { Integer.valueOf(memberId) }));
/*      */ 
/*  532 */     Double buyAmount = (Double)this.baseDaoSupport.queryForObject("select sum(paymoney) from order where member_id = ?", new DoubleMapper(), new Object[] { Integer.valueOf(memberId) });
/*      */ 
/*  535 */     Map map = new HashMap();
/*  536 */     map.put("buyTimes", buyTimes);
/*  537 */     map.put("buyAmount", buyAmount);
/*  538 */     return map;
/*      */   }
/*      */ 
/*      */   public IPromotionManager getPromotionManager() {
/*  542 */     return this.promotionManager;
/*      */   }
/*      */ 
/*      */   public void setPromotionManager(IPromotionManager promotionManager) {
/*  546 */     this.promotionManager = promotionManager;
/*      */   }
/*      */ 
/*      */   public void edit(Order order) {
/*  550 */     this.baseDaoSupport.update("order", order, new StringBuilder().append("order_id = ").append(order.getOrder_id()).toString());
/*      */   }
/*      */ 
/*      */   public List<Map> listAdjItem(Integer orderid)
/*      */   {
/*  556 */     String sql = "select * from order_items where order_id=? and addon!=''";
/*  557 */     return this.baseDaoSupport.queryForList(sql, new Object[] { orderid });
/*      */   }
/*      */ 
/*      */   public Map censusState()
/*      */   {
/*  568 */     Map stateMap = new HashMap(7);
/*  569 */     String[] states = { "cancel_ship", "cancel_pay", "not_pay", "pay", "ship", "complete", "cancellation" };
/*      */ 
/*  571 */     for (String s : states) {
/*  572 */       stateMap.put(s, Integer.valueOf(0));
/*      */     }
/*      */ 
/*  576 */     String sql = new StringBuilder().append("select count(0) num,status  from ").append(getTableName("order")).append(" where disabled = 0 group by status").toString();
/*      */ 
/*  579 */     List list = this.daoSupport.queryForList(sql, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  584 */         Map map = new HashMap();
/*  585 */         map.put("status", Integer.valueOf(rs.getInt("status")));
/*  586 */         map.put("num", Integer.valueOf(rs.getInt("num")));
/*  587 */         return map;
/*      */       }
/*      */     }
/*      */     , new Object[0]);
/*      */ 
/*  593 */     for (Map state : list) {
/*  594 */       stateMap.put(getStateString((Integer)state.get("status")), state.get("num"));
/*      */     }
/*      */ 
/*  598 */     return stateMap;
/*      */   }
/*      */ 
/*      */   private String getStateString(Integer state)
/*      */   {
/*  608 */     String str = null;
/*  609 */     switch (state.intValue()) {
/*      */     case -2:
/*  611 */       str = "cancel_ship";
/*  612 */       break;
/*      */     case -1:
/*  614 */       str = "cancel_pay";
/*  615 */       break;
/*      */     case 0:
/*  617 */       str = "not_pay";
/*  618 */       break;
/*      */     case 1:
/*  620 */       str = "pay";
/*  621 */       break;
/*      */     case 2:
/*  623 */       str = "ship";
/*  624 */       break;
/*      */     case 3:
/*  626 */       str = "complete";
/*  627 */       break;
/*      */     case 4:
/*  629 */       str = "cancellation";
/*  630 */       break;
/*      */     default:
/*  632 */       str = null;
/*      */     }
/*      */ 
/*  635 */     return str;
/*      */   }
/*      */ 
/*      */   public OrderPluginBundle getOrderPluginBundle()
/*      */   {
/*  640 */     return this.orderPluginBundle;
/*      */   }
/*      */ 
/*      */   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
/*  644 */     this.orderPluginBundle = orderPluginBundle;
/*      */   }
/*      */ 
/*      */   public String export(Date start, Date end)
/*      */   {
/*  649 */     String sql = "select * from order where disabled=0 ";
/*  650 */     if (start != null) {
/*  651 */       sql = new StringBuilder().append(sql).append(" and create_time>").append(start.getTime()).toString();
/*      */     }
/*      */ 
/*  654 */     if (end != null) {
/*  655 */       sql = new StringBuilder().append(sql).append("  and create_timecreate_time<").append(end.getTime()).toString();
/*      */     }
/*      */ 
/*  658 */     List orderList = this.baseDaoSupport.queryForList(sql, Order.class, new Object[0]);
/*      */ 
/*  662 */     ExcelUtil excelUtil = new ExcelUtil();
/*      */ 
/*  665 */     InputStream in = FileUtil.getResourceAsStream("com/enation/app/shop/core/service/impl/order.xls");
/*      */ 
/*  667 */     excelUtil.openModal(in);
/*  668 */     int i = 1;
/*  669 */     for (Order order : orderList)
/*      */     {
/*  671 */       excelUtil.writeStringToCell(i, 0, order.getSn());
/*  672 */       excelUtil.writeStringToCell(i, 1, com.enation.eop.sdk.utils.DateUtil.toString(new Date(order.getCreate_time().longValue()), "yyyy-MM-dd HH:mm:ss"));
/*  673 */       excelUtil.writeStringToCell(i, 2, order.getOrderStatus());
/*  674 */       excelUtil.writeStringToCell(i, 3, new StringBuilder().append("").append(order.getOrder_amount()).toString());
/*  675 */       excelUtil.writeStringToCell(i, 4, order.getShip_name());
/*  676 */       excelUtil.writeStringToCell(i, 5, order.getPayStatus());
/*  677 */       excelUtil.writeStringToCell(i, 6, order.getShipStatus());
/*  678 */       excelUtil.writeStringToCell(i, 7, order.getShipping_type());
/*  679 */       excelUtil.writeStringToCell(i, 8, order.getPayment_name());
/*  680 */       i++;
/*      */     }
/*      */ 
/*  684 */     String filename = "";
/*  685 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  686 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  687 */       filename = new StringBuilder().append("/user/").append(site.getUserid()).append("/").append(site.getId()).append("/order").toString();
/*      */     } else {
/*  689 */       filename = "/order";
/*      */     }
/*  691 */     File file = new File(new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(filename).toString());
/*  692 */     if (!file.exists()) file.mkdirs();
/*      */ 
/*  694 */     filename = new StringBuilder().append(filename).append("/order").append(com.enation.framework.util.DateUtil.getDatelineLong()).append(".xls").toString();
/*  695 */     excelUtil.writeToFile(new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(filename).toString());
/*      */ 
/*  697 */     return new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(filename).toString();
/*      */   }
/*      */ 
/*      */   public OrderItem getItem(int itemid)
/*      */   {
/*  704 */     String sql = new StringBuilder().append("select items.*,p.store as store from ").append(getTableName("order_items")).append(" items ").toString();
/*      */ 
/*  706 */     sql = new StringBuilder().append(sql).append(" left join ").append(getTableName("product")).append(" p on p.product_id = items.product_id ").toString();
/*      */ 
/*  708 */     sql = new StringBuilder().append(sql).append(" where items.item_id = ?").toString();
/*      */ 
/*  710 */     OrderItem item = (OrderItem)this.daoSupport.queryForObject(sql, OrderItem.class, new Object[] { Integer.valueOf(itemid) });
/*      */ 
/*  713 */     return item;
/*      */   }
/*      */ 
/*      */   public IAdminUserManager getAdminUserManager()
/*      */   {
/*  718 */     return this.adminUserManager;
/*      */   }
/*      */ 
/*      */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/*  722 */     this.adminUserManager = adminUserManager;
/*      */   }
/*      */ 
/*      */   public IPermissionManager getPermissionManager() {
/*  726 */     return this.permissionManager;
/*      */   }
/*      */ 
/*      */   public void setPermissionManager(IPermissionManager permissionManager) {
/*  730 */     this.permissionManager = permissionManager;
/*      */   }
/*      */ 
/*      */   public IRoleManager getRoleManager() {
/*  734 */     return this.roleManager;
/*      */   }
/*      */ 
/*      */   public void setRoleManager(IRoleManager roleManager) {
/*  738 */     this.roleManager = roleManager;
/*      */   }
/*      */ 
/*      */   public int getMemberOrderNum(int member_id, int payStatus)
/*      */   {
/*  747 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=? AND pay_status=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(payStatus) });
/*      */   }
/*      */ 
/*      */   public List<Map> getItemsByOrderid(Integer order_id)
/*      */   {
/*  758 */     String sql = "select * from order_items where order_id=?";
/*  759 */     return this.baseDaoSupport.queryForList(sql, new Object[] { order_id });
/*      */   }
/*      */ 
/*      */   public void refuseReturn(String orderSn)
/*      */   {
/*  764 */     this.baseDaoSupport.execute("update order set state = -5 where sn = ?", new Object[] { orderSn });
/*      */   }
/*      */ 
/*      */   public void updateOrderPrice(double price, int orderid)
/*      */   {
/*  773 */     this.baseDaoSupport.execute("update order set order_amount = order_amount-?,goods_amount = goods_amount- ? where order_id = ?", new Object[] { Double.valueOf(price), Double.valueOf(price), Integer.valueOf(orderid) });
/*      */   }
/*      */ 
/*      */   public String queryLogiNameById(Integer logi_id)
/*      */   {
/*  784 */     return (String)this.baseDaoSupport.queryForObject("select name from logi_company where id=?", new StringMapper(), new Object[] { logi_id });
/*      */   }
/*      */ 
/*      */   public Page searchForGuest(int pageNo, int pageSize, String ship_name, String ship_tel)
/*      */   {
/*  794 */     String sql = "select * from order where ship_name=? AND (ship_mobile=? OR ship_tel=?) ORDER BY order_id DESC";
/*  795 */     Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { ship_name, ship_tel, ship_tel });
/*      */ 
/*  797 */     return page;
/*      */   }
/*      */ 
/*      */   public Page listByStatus(int pageNo, int pageSize, int status, int memberid) {
/*  801 */     String filedname = "status";
/*  802 */     if (status == 0)
/*      */     {
/*  804 */       filedname = " status!=8 AND pay_status";
/*      */     }
/*      */ 
/*  807 */     String sql = new StringBuilder().append("select * from order where ").append(filedname).append("=? AND member_id=? ORDER BY order_id DESC").toString();
/*      */ 
/*  809 */     Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });
/*      */ 
/*  811 */     return page;
/*      */   }
/*      */ 
/*      */   public List<Order> listByStatus(int status, int memberid)
/*      */   {
/*  816 */     String filedname = "status";
/*  817 */     if (status == 0)
/*      */     {
/*  819 */       filedname = " status!=8 AND pay_status";
/*      */     }
/*      */ 
/*  822 */     String sql = new StringBuilder().append("select * from order where ").append(filedname).append("=? AND member_id=? ORDER BY order_id DESC").toString();
/*      */ 
/*  825 */     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });
/*      */   }
/*      */ 
/*      */   public int getMemberOrderNum(int member_id)
/*      */   {
/*  830 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=?", new Object[] { Integer.valueOf(member_id) });
/*      */   }
/*      */ 
/*      */   public Page search(int pageNO, int pageSize, int disabled, String sn, String logi_no, String uname, String ship_name, int status)
/*      */   {
/*  837 */     StringBuffer sql = new StringBuffer(new StringBuilder().append("select * from ").append(getTableName("order")).append(" where disabled=?  ").toString());
/*      */ 
/*  839 */     if (status != -100) {
/*  840 */       sql.append(new StringBuilder().append(" and status = ").append(status).append(" ").toString());
/*      */     }
/*  842 */     if (!StringUtil.isEmpty(sn)) {
/*  843 */       sql.append(new StringBuilder().append(" and sn = '").append(sn).append("' ").toString());
/*      */     }
/*  845 */     if (!StringUtil.isEmpty(uname)) {
/*  846 */       sql.append(new StringBuilder().append(" and member_id  in ( SELECT  member_id FROM ").append(getTableName("member")).append(" where uname = '").append(uname).append("' )  ").toString());
/*      */     }
/*      */ 
/*  849 */     if (!StringUtil.isEmpty(ship_name)) {
/*  850 */       sql.append(new StringBuilder().append(" and  ship_name = '").append(ship_name).append("' ").toString());
/*      */     }
/*  852 */     if (!StringUtil.isEmpty(logi_no)) {
/*  853 */       sql.append(new StringBuilder().append(" and order_id in (SELECT order_id FROM ").append(getTableName("delivery")).append(" where logi_no = '").append(logi_no).append("') ").toString());
/*      */     }
/*      */ 
/*  856 */     sql.append(" order by create_time desc ");
/*  857 */     Page page = this.daoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });
/*      */ 
/*  859 */     return page;
/*      */   }
/*      */ 
/*      */   public Order getNext(String next, Integer orderId, Integer status, int disabled, String sn, String logi_no, String uname, String ship_name)
/*      */   {
/*  865 */     StringBuffer sql = new StringBuffer(new StringBuilder().append("select * from ").append(getTableName("order")).append(" where  1=1  ").toString());
/*      */ 
/*  868 */     StringBuffer depotsql = new StringBuffer("  ");
/*  869 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  870 */     if (adminUser.getFounder() != 1)
/*      */     {
/*  872 */       boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));
/*      */ 
/*  875 */       boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));
/*      */ 
/*  877 */       boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));
/*      */ 
/*  879 */       if ((isShiper) && (!haveAllo) && (!haveOrder)) {
/*  880 */         DepotUser depotUser = (DepotUser)adminUser;
/*  881 */         int depotid = depotUser.getDepotid().intValue();
/*  882 */         depotsql.append(new StringBuilder().append(" and depotid=").append(depotid).append("  ").toString());
/*      */       }
/*      */     }
/*      */ 
/*  886 */     StringBuilder sbsql = new StringBuilder("  ");
/*  887 */     if ((status != null) && (status.intValue() != -100)) {
/*  888 */       sbsql.append(new StringBuilder().append(" and status = ").append(status).append(" ").toString());
/*      */     }
/*  890 */     if (!StringUtil.isEmpty(sn)) {
/*  891 */       sbsql.append(new StringBuilder().append(" and sn = '").append(sn.trim()).append("' ").toString());
/*      */     }
/*  893 */     if (!StringUtil.isEmpty(uname)) {
/*  894 */       sbsql.append(new StringBuilder().append(" and member_id  in ( SELECT  member_id FROM ").append(getTableName("member")).append(" where uname = '").append(uname).append("' )  ").toString());
/*      */     }
/*      */ 
/*  897 */     if (!StringUtil.isEmpty(ship_name)) {
/*  898 */       sbsql.append(new StringBuilder().append("  and  ship_name = '").append(ship_name.trim()).append("'  ").toString());
/*      */     }
/*  900 */     if (!StringUtil.isEmpty(logi_no)) {
/*  901 */       sbsql.append(new StringBuilder().append("  and order_id in (SELECT order_id FROM ").append(getTableName("delivery")).append(" where logi_no = '").append(logi_no).append("')  ").toString());
/*      */     }
/*  903 */     if (next.equals("previous")) {
/*  904 */       sql.append(new StringBuilder().append("  and order_id IN (SELECT CASE WHEN SIGN(order_id - ").append(orderId).append(") < 0 THEN MAX(order_id)  END AS order_id FROM ").append(getTableName("order")).append(" WHERE order_id <> ").append(orderId).append(depotsql.toString()).append(" and disabled=? ").append(sbsql.toString()).append(" GROUP BY SIGN(order_id - ").append(orderId).append(") ORDER BY SIGN(order_id - ").append(orderId).append("))   ").toString());
/*      */     }
/*  910 */     else if (next.equals("next")) {
/*  911 */       sql.append(new StringBuilder().append("  and  order_id in (SELECT CASE WHEN SIGN(order_id - ").append(orderId).append(") > 0 THEN MIN(order_id) END AS order_id FROM ").append(getTableName("order")).append(" WHERE order_id <> ").append(orderId).append(depotsql.toString()).append(" and disabled=? ").append(sbsql.toString()).append(" GROUP BY SIGN(order_id - ").append(orderId).append(") ORDER BY SIGN(order_id - ").append(orderId).append("))   ").toString());
/*      */     }
/*      */     else
/*      */     {
/*  917 */       return null;
/*      */     }
/*  919 */     sql.append(" order by create_time desc ");
/*      */ 
/*  921 */     Order order = (Order)this.daoSupport.queryForObject(sql.toString(), Order.class, new Object[] { Integer.valueOf(disabled) });
/*      */ 
/*  923 */     return order;
/*      */   }
/*      */ 
/*      */   private double getOrderTotal(String sessionid)
/*      */   {
/*  933 */     List goodsItemList = this.cartManager.listGoods(sessionid);
/*  934 */     double orderTotal = 0.0D;
/*  935 */     if ((goodsItemList != null) && (goodsItemList.size() > 0)) {
/*  936 */       for (int i = 0; i < goodsItemList.size(); i++) {
/*  937 */         CartItem cartItem = (CartItem)goodsItemList.get(i);
/*  938 */         orderTotal += cartItem.getCoupPrice().doubleValue() * cartItem.getNum();
/*      */       }
/*      */     }
/*  941 */     return orderTotal;
/*      */   }
/*      */ 
/*      */   private OrderItem getOrderItem(Integer itemid)
/*      */   {
/*  950 */     return (OrderItem)this.baseDaoSupport.queryForObject("select * from order_items where item_id = ?", OrderItem.class, new Object[] { itemid });
/*      */   }
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public boolean delItem(Integer itemid, Integer itemnum) {
/*  955 */     OrderItem item = getOrderItem(itemid);
/*  956 */     Order order = get(item.getOrder_id());
/*  957 */     boolean flag = false;
/*  958 */     int paymentid = order.getPayment_id().intValue();
/*  959 */     int status = order.getStatus().intValue();
/*  960 */     if (((paymentid == 1) || (paymentid == 3) || (paymentid == 4) || (paymentid == 5)) && ((status == 0) || (status == 1) || (status == 2) || (status == 3) || (status == 4))) {
/*  961 */       flag = true;
/*      */     }
/*  963 */     if ((paymentid == 2) && ((status == 0) || (status == 9) || (status == 3) || (status == 4))) {
/*  964 */       flag = true;
/*      */     }
/*  966 */     if (flag) {
/*      */       try {
/*  968 */         if (itemnum.intValue() <= item.getNum().intValue()) {
/*  969 */           Goods goods = this.goodsManager.getGoods(item.getGoods_id());
/*  970 */           double order_amount = order.getOrder_amount().doubleValue();
/*  971 */           double itemprice = item.getPrice().doubleValue() * itemnum.intValue();
/*  972 */           double leftprice = CurrencyUtil.sub(order_amount, itemprice);
/*  973 */           int difpoint = (int)Math.floor(leftprice);
/*  974 */           Double[] dlyprice = this.dlyTypeManager.countPrice(order.getShipping_id(), Double.valueOf(order.getWeight().doubleValue() - goods.getWeight().doubleValue() * itemnum.intValue()), Double.valueOf(leftprice), order.getShip_regionid().toString(), false);
/*  975 */           double sumdlyprice = dlyprice[0].doubleValue();
/*  976 */           this.baseDaoSupport.execute("update order set goods_amount = goods_amount- ?,shipping_amount = ?,order_amount =  ?,weight =  weight - ?,gainedpoint =  ? where order_id = ?", new Object[] { Double.valueOf(itemprice), Double.valueOf(sumdlyprice), Double.valueOf(leftprice), Double.valueOf(goods.getWeight().doubleValue() * itemnum.intValue()), Integer.valueOf(difpoint), order.getOrder_id() });
/*      */ 
/*  978 */           this.baseDaoSupport.execute("update freeze_point set mp =?,point =?  where orderid = ? and type = ?", new Object[] { Integer.valueOf(difpoint), Integer.valueOf(difpoint), order.getOrder_id(), "buygoods" });
/*  979 */           if (itemnum.intValue() == item.getNum().intValue())
/*  980 */             this.baseDaoSupport.execute("delete from order_items where item_id = ?", new Object[] { itemid });
/*      */           else
/*  982 */             this.baseDaoSupport.execute("update order_items set num = num - ? where item_id = ?", new Object[] { Integer.valueOf(itemnum.intValue()), itemid });
/*      */         }
/*      */         else
/*      */         {
/*  986 */           return false;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  990 */         e.printStackTrace();
/*  991 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  995 */     return flag;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public boolean saveAddrDetail(String addr, int orderid) {
/* 1001 */     Order order = get(Integer.valueOf(orderid));
/* 1002 */     String oldAddr = order.getShip_addr();
/* 1003 */     if ((addr == null) || (StringUtil.isEmpty(addr))) {
/* 1004 */       return false;
/*      */     }
/* 1006 */     this.baseDaoSupport.execute("update order set ship_addr=?  where order_id=?", new Object[] { addr, Integer.valueOf(orderid) });
/* 1007 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 1008 */     log(Integer.valueOf(orderid), new StringBuilder().append("收货人详细地址从['").append(oldAddr).append("']修改为['").append(addr).append("']").toString(), null, adminUser.getUsername());
/* 1009 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean saveShipInfo(String remark, String ship_day, String ship_name, String ship_tel, String ship_mobile, String ship_zip, int orderid)
/*      */   {
/* 1015 */     Order order = get(Integer.valueOf(orderid));
/* 1016 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*      */     try {
/* 1018 */       if ((ship_day != null) && (!StringUtil.isEmpty(ship_day))) {
/* 1019 */         String oldShip_day = order.getShip_day();
/* 1020 */         this.baseDaoSupport.execute("update order set ship_day=?  where order_id=?", new Object[] { ship_day, Integer.valueOf(orderid) });
/* 1021 */         if ((remark != null) && (!StringUtil.isEmpty(remark)) && (!remark.equals("undefined"))) {
/* 1022 */           StringBuilder sb = new StringBuilder("");
/* 1023 */           sb.append("【配送时间：");
/* 1024 */           sb.append(remark.trim());
/* 1025 */           sb.append("】");
/* 1026 */           this.baseDaoSupport.execute(new StringBuilder().append("update order set remark= concat(remark,'").append(sb.toString()).append("')   where order_id=?").toString(), new Object[] { Integer.valueOf(orderid) });
/*      */         }
/* 1028 */         log(Integer.valueOf(orderid), new StringBuilder().append("收货日期从['").append(oldShip_day).append("']修改为['").append(ship_day).append("']").toString(), null, adminUser.getUsername());
/* 1029 */         return true;
/*      */       }
/* 1031 */       if ((ship_name != null) && (!StringUtil.isEmpty(ship_name))) {
/* 1032 */         String oldship_name = order.getShip_name();
/* 1033 */         this.baseDaoSupport.execute("update order set ship_name=?  where order_id=?", new Object[] { ship_name, Integer.valueOf(orderid) });
/* 1034 */         log(Integer.valueOf(orderid), new StringBuilder().append("收货人姓名从['").append(oldship_name).append("']修改为['").append(ship_name).append("']").toString(), null, adminUser.getUsername());
/* 1035 */         return true;
/*      */       }
/* 1037 */       if ((ship_tel != null) && (!StringUtil.isEmpty(ship_tel))) {
/* 1038 */         String oldship_tel = order.getShip_tel();
/* 1039 */         this.baseDaoSupport.execute("update order set ship_tel=?  where order_id=?", new Object[] { ship_tel, Integer.valueOf(orderid) });
/* 1040 */         log(Integer.valueOf(orderid), new StringBuilder().append("收货人电话从['").append(oldship_tel).append("']修改为['").append(ship_tel).append("']").toString(), null, adminUser.getUsername());
/* 1041 */         return true;
/*      */       }
/* 1043 */       if ((ship_mobile != null) && (!StringUtil.isEmpty(ship_mobile))) {
/* 1044 */         String oldship_mobile = order.getShip_mobile();
/* 1045 */         this.baseDaoSupport.execute("update order set ship_mobile=?  where order_id=?", new Object[] { ship_mobile, Integer.valueOf(orderid) });
/* 1046 */         log(Integer.valueOf(orderid), new StringBuilder().append("收货人手机从['").append(oldship_mobile).append("']修改为['").append(ship_mobile).append("']").toString(), null, adminUser.getUsername());
/* 1047 */         return true;
/*      */       }
/* 1049 */       if ((ship_zip != null) && (!StringUtil.isEmpty(ship_zip))) {
/* 1050 */         String oldship_zip = order.getShip_zip();
/* 1051 */         this.baseDaoSupport.execute("update order set ship_zip=?  where order_id=?", new Object[] { ship_zip, Integer.valueOf(orderid) });
/* 1052 */         log(Integer.valueOf(orderid), new StringBuilder().append("收货人邮编从['").append(oldship_zip).append("']修改为['").append(ship_zip).append("']").toString(), null, adminUser.getUsername());
/* 1053 */         return true;
/*      */       }
/* 1055 */       return false;
/*      */     } catch (Exception e) {
/* 1057 */       e.printStackTrace();
/* 1058 */     }return false;
/*      */   }
/*      */ 
/*      */   public void updatePayMethod(int orderid, int payid, String paytype, String payname)
/*      */   {
/* 1066 */     this.baseDaoSupport.execute("update order set payment_id=?,payment_type=?,payment_name=? where order_id=?", new Object[] { Integer.valueOf(payid), paytype, payname, Integer.valueOf(orderid) });
/*      */   }
/*      */ 
/*      */   public boolean checkProInOrder(int productid)
/*      */   {
/* 1076 */     String sql = "select count(0) from order_items where product_id=?";
/* 1077 */     return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(productid) }) > 0;
/*      */   }
/*      */ 
/*      */   public boolean checkGoodsInOrder(int goodsid)
/*      */   {
/* 1086 */     String sql = "select count(0) from order_items where goods_id=?";
/* 1087 */     return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(goodsid) }) > 0;
/*      */   }
/*      */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.OrderManager
 * JD-Core Version:    0.6.0
 */