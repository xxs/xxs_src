/*     */ package com.enation.app.shop.component.product.plugin.store;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.IRoleManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.model.StoreLog;
/*     */ import com.enation.app.shop.core.model.WarnNum;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsStorePlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsStoreManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IStoreLogManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.IntegerMapper;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Component
/*     */ public class GenericGoodsStorePlugin extends AbstractGoodsStorePlugin
/*     */   implements IGoodsDeleteEvent
/*     */ {
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IPermissionManager permissionManager;
/*     */   private IGoodsStoreManager goodsStoreManager;
/*     */   private IRoleManager roleManager;
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private IDaoSupport daoSupport;
/*     */   private IDBRouter baseDBRouter;
/*     */   private IStoreLogManager storeLogManager;
/*     */   private IOrderManager orderManager;
/*     */ 
/*     */   public String getStoreHtml(Map goods)
/*     */   {
/*  59 */     Integer goodsid = (Integer)goods.get("goods_id");
/*  60 */     Integer productid = getProductId(goodsid);
/*     */ 
/*  62 */     List storeList = null;
/*  63 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  64 */     if (adminUser.getFounder() != 1) {
/*  65 */       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/*  67 */       if (isStorer) {
/*  68 */         DepotUser depotUser = (DepotUser)adminUser;
/*  69 */         int depotid = depotUser.getDepotid().intValue();
/*  70 */         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
/*     */       } else {
/*  72 */         return "您没有修改库存的权限";
/*     */       }
/*     */     } else {
/*  75 */       storeList = this.goodsStoreManager.listProductStore(productid);
/*     */     }
/*     */ 
/*  78 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  79 */     freeMarkerPaser.putData("storeList", storeList);
/*  80 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getStockHtml(Map goods)
/*     */   {
/*  86 */     Integer goodsid = (Integer)goods.get("goods_id");
/*  87 */     Integer productid = getProductId(goodsid);
/*     */ 
/*  89 */     List storeList = null;
/*  90 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  91 */     if (adminUser.getFounder() != 1) {
/*  92 */       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*  93 */       if (isStorer) {
/*  94 */         DepotUser depotUser = (DepotUser)adminUser;
/*  95 */         int depotid = depotUser.getDepotid().intValue();
/*  96 */         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
/*     */       } else {
/*  98 */         return "您没有修改库存的权限";
/*     */       }
/*     */     } else {
/* 101 */       storeList = this.goodsStoreManager.listProductStore(productid);
/*     */     }
/*     */ 
/* 104 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 105 */     freeMarkerPaser.setPageName("stockList");
/* 106 */     freeMarkerPaser.putData("storeList", storeList);
/* 107 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getShipHtml(Map goods)
/*     */   {
/* 114 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 115 */     Integer productid = getProductId(goodsid);
/*     */ 
/* 117 */     List storeList = null;
/* 118 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 119 */     if (adminUser.getFounder() != 1) {
/* 120 */       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/* 121 */       if (isStorer) {
/* 122 */         DepotUser depotUser = (DepotUser)adminUser;
/* 123 */         int depotid = depotUser.getDepotid().intValue();
/* 124 */         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
/*     */       } else {
/* 126 */         return "您没有修改库存的权限";
/*     */       }
/*     */     } else {
/* 129 */       storeList = this.goodsStoreManager.listProductStore(productid);
/*     */     }
/*     */ 
/* 132 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 133 */     freeMarkerPaser.setPageName("shipList");
/* 134 */     freeMarkerPaser.putData("storeList", storeList);
/* 135 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void onStockSave(Map goods)
/*     */   {
/* 143 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 144 */     Integer productid = getProductId(goodsid);
/*     */ 
/* 146 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 147 */     String[] storeidAr = request.getParameterValues("storeid");
/* 148 */     String[] storeAr = request.getParameterValues("store");
/* 149 */     String[] depotidAr = request.getParameterValues("depotid");
/*     */ 
/* 151 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 152 */     boolean isFounder = adminUser.getFounder() == 1;
/* 153 */     boolean isStorer = false;
/* 154 */     if (!isFounder) {
/* 155 */       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */     }
/*     */ 
/* 158 */     if ((!isFounder) && (!isStorer)) {
/* 159 */       throw new RuntimeException("您没有权限维护库存");
/*     */     }
/* 161 */     int total = 0;
/* 162 */     for (int i = 0; i < storeidAr.length; i++) {
/* 163 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 164 */       int store = StringUtil.toInt(storeAr[i], true);
/* 165 */       int depotid = StringUtil.toInt(depotidAr[i], true);
/*     */ 
/* 167 */       if (storeid == 0)
/* 168 */         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { goodsid, productid, Integer.valueOf(depotid), Integer.valueOf(store) });
/*     */       else {
/* 170 */         this.baseDaoSupport.execute("update product_store set store=store+? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */       }
/* 172 */       total += store;
/*     */     }
/*     */ 
/* 176 */     this.baseDaoSupport.execute("update goods_depot set iscmpl=1 where goodsid=? and depotid=?", new Object[] { goodsid, depotidAr[0] });
/*     */ 
/* 179 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });
/* 180 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });
/*     */ 
/* 183 */     DepotUser depotUser = (DepotUser)adminUser;
/* 184 */     StoreLog storeLog = new StoreLog();
/* 185 */     storeLog.setGoodsid(goodsid);
/* 186 */     storeLog.setGoodsname(goods.get("name").toString());
/* 187 */     storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
/* 188 */     storeLog.setOp_type(Integer.valueOf(0));
/* 189 */     storeLog.setDepotid(depotUser.getDepotid());
/* 190 */     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 191 */     storeLog.setNum(Integer.valueOf(total));
/* 192 */     storeLog.setUserid(adminUser.getUserid());
/* 193 */     storeLog.setUsername(adminUser.getUsername());
/* 194 */     this.storeLogManager.add(storeLog);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void onShipSave(Map goods)
/*     */   {
/* 206 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 207 */     Integer productid = getProductId(goodsid);
/*     */ 
/* 209 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 210 */     String[] storeidAr = request.getParameterValues("storeid");
/* 211 */     String[] storeAr = request.getParameterValues("store");
/*     */ 
/* 213 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 214 */     boolean isFounder = adminUser.getFounder() == 1;
/* 215 */     boolean isStorer = false;
/* 216 */     if (!isFounder) {
/* 217 */       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */     }
/*     */ 
/* 220 */     if ((!isFounder) && (!isStorer)) {
/* 221 */       throw new RuntimeException("您没有权限维护库存");
/*     */     }
/* 223 */     int total = 0;
/* 224 */     for (int i = 0; i < storeidAr.length; i++) {
/* 225 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 226 */       int store = StringUtil.toInt(storeAr[i], true);
/*     */ 
/* 228 */       if (storeid == 0) {
/* 229 */         throw new RuntimeException("商品库存为0，不能出货");
/*     */       }
/* 231 */       this.baseDaoSupport.execute("update product_store set store=store-? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */ 
/* 233 */       total += store;
/*     */     }
/*     */ 
/* 236 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });
/* 237 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });
/*     */ 
/* 239 */     DepotUser depotUser = (DepotUser)adminUser;
/* 240 */     StoreLog storeLog = new StoreLog();
/* 241 */     storeLog.setGoodsid(goodsid);
/* 242 */     storeLog.setGoodsname(goods.get("name").toString());
/* 243 */     storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
/* 244 */     storeLog.setOp_type(Integer.valueOf(1));
/* 245 */     storeLog.setDepotid(depotUser.getDepotid());
/* 246 */     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 247 */     storeLog.setNum(Integer.valueOf(total));
/* 248 */     storeLog.setUserid(adminUser.getUserid());
/* 249 */     storeLog.setUsername(adminUser.getUsername());
/* 250 */     this.storeLogManager.add(storeLog);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void onStoreSave(Map goods)
/*     */   {
/* 260 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 261 */     Integer productid = getProductId(goodsid);
/*     */ 
/* 264 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 265 */     String[] storeidAr = request.getParameterValues("storeid");
/* 266 */     String[] storeAr = request.getParameterValues("store");
/* 267 */     String[] depotidAr = request.getParameterValues("depotid");
/*     */ 
/* 269 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 270 */     boolean isFounder = adminUser.getFounder() == 1;
/* 271 */     boolean isStorer = false;
/* 272 */     if (!isFounder) {
/* 273 */       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */     }
/*     */ 
/* 276 */     if ((!isFounder) && (!isStorer)) {
/* 277 */       throw new RuntimeException("您没有权限维护库存");
/*     */     }
/*     */ 
/* 280 */     for (int i = 0; i < storeidAr.length; i++) {
/* 281 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 282 */       int store = StringUtil.toInt(storeAr[i], true);
/* 283 */       int depotid = StringUtil.toInt(depotidAr[i], true);
/*     */ 
/* 285 */       if (storeid == 0)
/* 286 */         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { goodsid, productid, Integer.valueOf(depotid), Integer.valueOf(store) });
/*     */       else {
/* 288 */         this.baseDaoSupport.execute("update product_store set store=? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 294 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });
/* 295 */     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/* 302 */     if ((goodsid == null) || (goodsid.length == 0)) return;
/* 303 */     this.baseDaoSupport.execute("delete from product_store where goodsid in(" + StringUtil.arrayToString(goodsid, ",") + ")", new Object[0]);
/*     */   }
/*     */ 
/*     */   private Integer getProductId(Integer goodsid)
/*     */   {
/* 308 */     String sql = "select product_id from product where goods_id = ?";
/* 309 */     List productidList = this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[] { goodsid });
/* 310 */     Integer productid = (Integer)productidList.get(0);
/* 311 */     return productid;
/*     */   }
/*     */ 
/*     */   public boolean canBeExecute(Map goods)
/*     */   {
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 328 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 332 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 336 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 340 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ 
/*     */   public IRoleManager getRoleManager() {
/* 344 */     return this.roleManager;
/*     */   }
/*     */ 
/*     */   public void setRoleManager(IRoleManager roleManager) {
/* 348 */     this.roleManager = roleManager;
/*     */   }
/*     */ 
/*     */   public IGoodsStoreManager getGoodsStoreManager() {
/* 352 */     return this.goodsStoreManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
/* 356 */     this.goodsStoreManager = goodsStoreManager;
/*     */   }
/*     */   public IDaoSupport getBaseDaoSupport() {
/* 359 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
/*     */   {
/* 364 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport()
/*     */   {
/* 369 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport)
/*     */   {
/* 374 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseDBRouter()
/*     */   {
/* 379 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseDBRouter(IDBRouter baseDBRouter)
/*     */   {
/* 384 */     this.baseDBRouter = baseDBRouter;
/*     */   }
/*     */ 
/*     */   public IStoreLogManager getStoreLogManager()
/*     */   {
/* 389 */     return this.storeLogManager;
/*     */   }
/*     */ 
/*     */   public void setStoreLogManager(IStoreLogManager storeLogManager)
/*     */   {
/* 394 */     this.storeLogManager = storeLogManager;
/*     */   }
/*     */ 
/*     */   public String getWarnNumHtml(Map goods)
/*     */   {
/* 400 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 401 */     int foud = adminUser.getFounder();
/* 402 */     if (foud != 1) {
/* 403 */       return "您没有修改报警数的权限";
/*     */     }
/* 405 */     Integer goodsid = Integer.valueOf(goods.get("goods_id").toString());
/* 406 */     List warnList = this.goodsStoreManager.listWarns(goodsid);
/* 407 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 408 */     freeMarkerPaser.setPageName("warn_num");
/* 409 */     freeMarkerPaser.putData("warnList", warnList);
/* 410 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onWarnSave(Map goods)
/*     */   {
/* 416 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 417 */     Integer goodsid = (Integer)goods.get("goods_id");
/*     */ 
/* 419 */     String[] idArr = request.getParameterValues("warnid");
/* 420 */     String[] warn_numAr = request.getParameterValues("warn_num");
/* 421 */     if ((idArr == null) || (idArr.length == 0)) return;
/* 422 */     for (int i = 0; i < idArr.length; i++) {
/* 423 */       int id = StringUtil.toInt(idArr[i], true);
/* 424 */       int warn_num = StringUtil.toInt(warn_numAr[i], true);
/*     */ 
/* 426 */       WarnNum warnNum = new WarnNum();
/* 427 */       warnNum.setId(Integer.valueOf(id));
/* 428 */       warnNum.setGoods_id(goodsid);
/* 429 */       warnNum.setWarn_num(Integer.valueOf(warn_num));
/* 430 */       if (warnNum.getId().intValue() == 0)
/* 431 */         this.baseDaoSupport.insert("warn_num", warnNum);
/*     */       else
/* 433 */         this.baseDaoSupport.execute("update warn_num set warn_num = ? where id=?", new Object[] { warnNum.getWarn_num(), warnNum.getId() });
/*     */     }
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 440 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager)
/*     */   {
/* 445 */     this.orderManager = orderManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.product.plugin.store.GenericGoodsStorePlugin
 * JD-Core Version:    0.6.0
 */