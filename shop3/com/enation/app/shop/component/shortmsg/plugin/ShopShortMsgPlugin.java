/*     */ package com.enation.app.shop.component.shortmsg.plugin;
/*     */ 
/*     */ import com.enation.app.base.core.model.ShortMsg;
/*     */ import com.enation.app.base.core.plugin.shortmsg.IShortMessageEvent;
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.base.core.service.IShortMsgManager;
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.IRoleManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.core.model.Allocation;
/*     */ import com.enation.app.shop.core.model.AllocationItem;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.service.IDepotUserManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class ShopShortMsgPlugin extends AutoRegisterPlugin
/*     */   implements IShortMessageEvent
/*     */ {
/*     */   private ISettingService settingService;
/*     */   private IShortMsgManager shortMsgManager;
/*     */   private IDepotUserManager depotUserManager;
/*     */   private IRoleManager roleManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IPermissionManager permissionManager;
/*     */   private IDaoSupport daoSupport;
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private IDBRouter baseDBRouter;
/*     */ 
/*     */   public List<ShortMsg> getMessage()
/*     */   {
/*  49 */     List msgList = new ArrayList();
/*  50 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  51 */     boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));
/*  52 */     boolean haveDepotAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_allocation"));
/*  53 */     boolean haveDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*  54 */     boolean haveShip = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));
/*  55 */     boolean haveDepotSupper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_supper"));
/*  56 */     boolean haveFinance = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("finance"));
/*  57 */     boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));
/*  58 */     boolean haveCustomerService = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("customer_service"));
/*     */ 
/*  61 */     if ((haveOrder) || (haveCustomerService)) {
/*  62 */       ShortMsg msg = getReturnsOrderMessage();
/*     */ 
/*  64 */       if (msg != null) {
/*  65 */         msgList.add(msg);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  71 */     if (haveCustomerService) {
/*  72 */       ShortMsg discuss = getDiscussMessage();
/*  73 */       ShortMsg ask = getAskMessage();
/*  74 */       ShortMsg confirm = getOrderConfirm();
/*  75 */       if (discuss != null) {
/*  76 */         msgList.add(discuss);
/*     */       }
/*  78 */       if (ask != null) {
/*  79 */         msgList.add(ask);
/*     */       }
/*  81 */       if (confirm != null) {
/*  82 */         msgList.add(confirm);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  87 */     if (haveAllo) {
/*  88 */       ShortMsg msg = getAlloMessage();
/*  89 */       if (msg != null) {
/*  90 */         msgList.add(msg);
/*     */       }
/*     */     }
/*     */ 
/*  94 */     if (haveDepotAllo)
/*     */     {
/*  96 */       ShortMsg msg = getDepotAlloMessage(adminUser);
/*  97 */       if (msg != null) {
/*  98 */         msgList.add(msg);
/*     */       }
/*     */     }
/*     */ 
/* 102 */     if (haveShip)
/*     */     {
/* 104 */       ShortMsg msg = getShipMessage(adminUser);
/* 105 */       if (msg != null) {
/* 106 */         msgList.add(msg);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 111 */     if (haveDepotAdmin)
/*     */     {
/* 113 */       ShortMsg msg = getStockMessage(adminUser);
/* 114 */       if (msg != null) {
/* 115 */         msgList.add(msg);
/*     */       }
/*     */     }
/*     */ 
/* 119 */     if (haveDepotSupper) {
/* 120 */       ShortMsg msg = getStoreAlermMessage();
/* 121 */       if (msg != null) {
/* 122 */         msgList.add(msg);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 127 */     if (haveFinance) {
/* 128 */       ShortMsg msg = getPayConfirmMessage();
/* 129 */       if (msg != null) {
/* 130 */         msgList.add(msg);
/*     */       }
/*     */     }
/* 133 */     return msgList;
/*     */   }
/*     */ 
/*     */   private ShortMsg getOrderConfirm()
/*     */   {
/* 141 */     String sql = "select count(0) from order where status=? and   payment_type = 'cod'";
/* 142 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(0) });
/* 143 */     if (count > 0) {
/* 144 */       ShortMsg msg = new ShortMsg();
/* 145 */       msg.setUrl("/shop/admin/order!listbyship.do?state=0&shipping_id=2");
/* 146 */       msg.setTitle("有" + count + "个订单需要完成确认订单任务");
/* 147 */       msg.setTarget("ajax");
/* 148 */       return msg;
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getPayConfirmMessage()
/*     */   {
/* 158 */     String sql = "select count(0) from order where  disabled=0 and status=? ";
/* 159 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(1) });
/* 160 */     String sql_1 = "select count(0) from order where disabled=0 and status=? and payment_type = 'cod' ";
/* 161 */     int count_1 = this.baseDaoSupport.queryForInt(sql_1, new Object[] { Integer.valueOf(5) });
/* 162 */     if (count + count_1 > 0) {
/* 163 */       ShortMsg msg = new ShortMsg();
/* 164 */       msg.setUrl("/shop/admin/order!listConfirmPay.do");
/* 165 */       msg.setTitle("有" + (count + count_1) + "个订单需要完成确认付款任务");
/* 166 */       msg.setTarget("ajax");
/* 167 */       return msg;
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getStoreAlermMessage()
/*     */   {
/* 179 */     String num_str = this.settingService.getSetting("shop", "goods_alerm_num");
/* 180 */     int alerm_num = 10;
/* 181 */     if (!StringUtil.isEmpty(num_str)) {
/* 182 */       alerm_num = StringUtil.toInt(num_str, true);
/*     */     }
/*     */ 
/* 186 */     String sql_5 = "select count(0) from  (select s.* from (select goodsid,productid,sum(store) sumstore from " + this.baseDBRouter.getTableName("product_store") + " group by goodsid,productid   ) s  " + "   left join " + this.baseDBRouter.getTableName("warn_num") + " wn on wn.goods_id = s.goodsid  where s.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) store";
/*     */ 
/* 188 */     int count_5 = this.daoSupport.queryForInt(sql_5, new Object[] { Integer.valueOf(alerm_num) });
/*     */ 
/* 190 */     int sum = count_5;
/*     */ 
/* 192 */     if (sum > 0) {
/* 193 */       ShortMsg msg = new ShortMsg();
/* 194 */       msg.setUrl("/shop/admin/depotMonitor!storeWarn.do");
/* 195 */       msg.setTitle("有" + sum + "个商品库存不足");
/* 196 */       return msg;
/*     */     }
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getShipMessage(AdminUser adminUser)
/*     */   {
/* 208 */     int count = 0;
/* 209 */     if (adminUser.getFounder() == 0) {
/* 210 */       DepotUser depotUser = (DepotUser)adminUser;
/* 211 */       String sql = "select count(0) from order where status=? and depotid=? ";
/* 212 */       count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(4), depotUser.getDepotid() });
/*     */     } else {
/* 214 */       String sql = "select count(0) from order where status=?  ";
/* 215 */       count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(4) });
/*     */     }
/*     */ 
/* 218 */     if (count > 0) {
/* 219 */       ShortMsg msg = new ShortMsg();
/* 220 */       msg.setUrl("/shop/admin/order!list.do?state=4");
/* 221 */       msg.setTitle("有" + count + "个订单需要完成发货任务");
/* 222 */       msg.setTarget("ajax");
/* 223 */       return msg;
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getStockMessage(AdminUser adminUser)
/*     */   {
/* 237 */     int count = 0;
/* 238 */     if (adminUser.getFounder() == 0) {
/* 239 */       DepotUser depotUser = (DepotUser)adminUser;
/* 240 */       String sql = "select count(0) from " + this.baseDBRouter.getTableName("goods_depot") + " gd left join " + this.baseDBRouter.getTableName("goods") + " g on gd.goodsid=g.goods_id where iscmpl=0 and depotid=? and g.disabled=0";
/* 241 */       count = this.daoSupport.queryForInt(sql, new Object[] { depotUser.getDepotid() });
/*     */     } else {
/* 243 */       String sql = "select count(0) from " + this.baseDBRouter.getTableName("goods_depot") + " gd left join " + this.baseDBRouter.getTableName("goods") + " g on gd.goodsid=g.goods_id where iscmpl=0 and g.disabled=0";
/* 244 */       count = this.daoSupport.queryForInt(sql, new Object[0]);
/*     */     }
/* 246 */     if (count > 0) {
/* 247 */       ShortMsg msg = new ShortMsg();
/* 248 */       msg.setUrl("/shop/admin/goods!list.do?optype=stock");
/* 249 */       msg.setTitle("有" + count + "个商品需要您完成进货任务");
/*     */ 
/* 251 */       return msg;
/*     */     }
/*     */ 
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getAlloMessage()
/*     */   {
/* 264 */     String sql = "select count(0) from order where status=? ";
/* 265 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(2) });
/*     */ 
/* 267 */     if (count > 0) {
/* 268 */       ShortMsg msg = new ShortMsg();
/* 269 */       msg.setUrl("/shop/admin/order!list.do?state=2");
/* 270 */       msg.setTitle("有" + count + "个订单需要您下达配货任务");
/* 271 */       msg.setTarget("ajax");
/* 272 */       return msg;
/*     */     }
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getReturnsOrderMessage()
/*     */   {
/* 282 */     String sql = "select count(0) from returns_order where state =? ";
/* 283 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(0) });
/*     */ 
/* 285 */     if (count > 0) {
/* 286 */       ShortMsg msg = new ShortMsg();
/* 287 */       msg.setUrl("/shop/admin/returnOrder!returnsApplyList.do?state=0");
/* 288 */       msg.setTitle("有" + count + "个退换货申请单需要您处理");
/* 289 */       return msg;
/*     */     }
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getDiscussMessage()
/*     */   {
/* 300 */     String sql = "select count(0) from member_comment where type=1 and status=0 ";
/* 301 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/* 303 */     if (count > 0) {
/* 304 */       ShortMsg msg = new ShortMsg();
/* 305 */       msg.setUrl("/shop/admin/comments!msgList.do?type=1&status=0");
/* 306 */       msg.setTitle("有" + count + "个评论需要您处理");
/* 307 */       return msg;
/*     */     }
/* 309 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getAskMessage()
/*     */   {
/* 317 */     String sql = "select count(0) from member_comment where type=2 and status=0 ";
/* 318 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/* 320 */     if (count > 0) {
/* 321 */       ShortMsg msg = new ShortMsg();
/* 322 */       msg.setUrl("/shop/admin/comments!msgList.do?type=2&status=0");
/* 323 */       msg.setTitle("有" + count + "个咨询需要您处理");
/* 324 */       return msg;
/*     */     }
/* 326 */     return null;
/*     */   }
/*     */ 
/*     */   private ShortMsg getDepotAlloMessage(AdminUser adminUser)
/*     */   {
/* 337 */     int count = 0;
/* 338 */     if (adminUser.getFounder() == 0) {
/* 339 */       DepotUser depotUser = (DepotUser)adminUser;
/* 340 */       String sql = "select count(0) from allocation_item where iscmpl=0 and depotid=?";
/* 341 */       count = this.baseDaoSupport.queryForInt(sql, new Object[] { depotUser.getDepotid() });
/*     */     } else {
/* 343 */       String sql = "select count(0) from allocation_item where iscmpl=0 ";
/* 344 */       count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */     }
/*     */ 
/* 348 */     if (count > 0) {
/* 349 */       ShortMsg msg = new ShortMsg();
/* 350 */       msg.setUrl("/shop/admin/orderReport!allocationList.do");
/* 351 */       msg.setTitle("有" + count + "个商品需要您完成配货任务");
/* 352 */       return msg;
/*     */     }
/* 354 */     return null;
/*     */   }
/*     */ 
/*     */   public void onAllocation(Allocation allocation)
/*     */   {
/*     */   }
/*     */ 
/*     */   private int findItemCount(int depotid, List<AllocationItem> itemList)
/*     */   {
/* 418 */     int count = 0;
/* 419 */     for (AllocationItem item : itemList) {
/* 420 */       if (depotid == item.getDepotid()) {
/* 421 */         count++;
/*     */       }
/*     */     }
/*     */ 
/* 425 */     return count;
/*     */   }
/*     */ 
/*     */   public IShortMsgManager getShortMsgManager()
/*     */   {
/* 433 */     return this.shortMsgManager;
/*     */   }
/*     */ 
/*     */   public void setShortMsgManager(IShortMsgManager shortMsgManager) {
/* 437 */     this.shortMsgManager = shortMsgManager;
/*     */   }
/*     */ 
/*     */   public IDepotUserManager getDepotUserManager() {
/* 441 */     return this.depotUserManager;
/*     */   }
/*     */ 
/*     */   public void setDepotUserManager(IDepotUserManager depotUserManager) {
/* 445 */     this.depotUserManager = depotUserManager;
/*     */   }
/*     */ 
/*     */   public IRoleManager getRoleManager()
/*     */   {
/* 450 */     return this.roleManager;
/*     */   }
/*     */ 
/*     */   public void setRoleManager(IRoleManager roleManager) {
/* 454 */     this.roleManager = roleManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager() {
/* 458 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 462 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 466 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 470 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport() {
/* 474 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 478 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport() {
/* 482 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 486 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public ISettingService getSettingService() {
/* 490 */     return this.settingService;
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService) {
/* 494 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseDBRouter() {
/* 498 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 502 */     this.baseDBRouter = baseDBRouter;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.shortmsg.plugin.ShopShortMsgPlugin
 * JD-Core Version:    0.6.0
 */