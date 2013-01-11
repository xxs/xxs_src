/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.core.model.Depot;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.model.WarnNum;
/*     */ import com.enation.app.shop.core.plugin.goods.GoodsStorePluginBundle;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IGoodsStoreManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class GoodsStoreManager extends BaseSupport
/*     */   implements IGoodsStoreManager
/*     */ {
/*     */   private GoodsStorePluginBundle goodsStorePluginBundle;
/*     */   private IDepotManager depotManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IPermissionManager permissionManager;
/*     */ 
/*     */   public List<Map> listProductStore(Integer productid)
/*     */   {
/*  35 */     List depotList = this.depotManager.list();
/*  36 */     List depotStoreList = new ArrayList();
/*     */ 
/*  39 */     String sql = "select d.*,p.storeid,p.goodsid ,p.productid,p.store from  " + getTableName("depot") + " d left join " + getTableName("product_store") + " p on d.id=  p.depotid where p.productid=?";
/*  40 */     List storeList = this.daoSupport.queryForList(sql, new Object[] { productid });
/*  41 */     for (Depot depot : depotList) {
/*  42 */       Map depotStore = new HashMap();
/*  43 */       depotStore.put("storeid", Integer.valueOf(0));
/*  44 */       depotStore.put("store", Integer.valueOf(0));
/*  45 */       depotStore.put("goodsid", Integer.valueOf(0));
/*  46 */       depotStore.put("productid", Integer.valueOf(0));
/*  47 */       if ((storeList != null) && (!storeList.isEmpty())) {
/*  48 */         for (Map store : storeList) {
/*  49 */           int depotid = Integer.parseInt(store.get("id").toString());
/*  50 */           if (depotid == depot.getId().intValue()) {
/*  51 */             depotStore.put("storeid", store.get("storeid"));
/*  52 */             depotStore.put("store", store.get("store"));
/*  53 */             depotStore.put("goodsid", store.get("goodsid"));
/*  54 */             depotStore.put("productid", store.get("productid"));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  59 */       depotStore.put("name", depot.getName());
/*  60 */       depotStore.put("depotid", depot.getId());
/*     */ 
/*  62 */       depotStoreList.add(depotStore);
/*     */     }
/*     */ 
/*  65 */     return depotStoreList;
/*     */   }
/*     */ 
/*     */   public List<Map> ListProductDepotStore(Integer productid, Integer finddepotid)
/*     */   {
/*  71 */     List depotList = this.depotManager.list();
/*  72 */     List depotStoreList = new ArrayList();
/*     */ 
/*  74 */     String sql = "select d.*,p.storeid,p.goodsid ,p.productid,p.store from  " + getTableName("depot") + " d left join " + getTableName("product_store") + " p on d.id=  p.depotid where p.productid=?";
/*  75 */     List storeList = this.daoSupport.queryForList(sql, new Object[] { productid });
/*     */ 
/*  77 */     for (Depot depot : depotList)
/*     */     {
/*  79 */       if (finddepotid.intValue() != depot.getId().intValue())
/*     */         continue;
/*  81 */       Map depotStore = new HashMap();
/*  82 */       depotStore.put("storeid", Integer.valueOf(0));
/*  83 */       depotStore.put("store", Integer.valueOf(0));
/*  84 */       depotStore.put("goodsid", Integer.valueOf(0));
/*  85 */       depotStore.put("productid", Integer.valueOf(0));
/*     */ 
/*  87 */       if ((storeList != null) && (!storeList.isEmpty())) {
/*  88 */         for (Map store : storeList) {
/*  89 */           int depotid = ((Integer)store.get("id")).intValue();
/*  90 */           if (depotid == depot.getId().intValue()) {
/*  91 */             depotStore.put("storeid", store.get("storeid"));
/*  92 */             depotStore.put("store", store.get("store"));
/*  93 */             depotStore.put("goodsid", store.get("goodsid"));
/*  94 */             depotStore.put("productid", store.get("productid"));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  99 */       depotStore.put("name", depot.getName());
/* 100 */       depotStore.put("depotid", depot.getId());
/*     */ 
/* 102 */       depotStoreList.add(depotStore);
/*     */     }
/*     */ 
/* 106 */     return depotStoreList;
/*     */   }
/*     */ 
/*     */   public List<Map> listProductAllo(Integer orderid, Integer itemid)
/*     */   {
/* 111 */     String sql = "select d.name,a.num from  " + getTableName("depot") + " d , " + getTableName("allocation_item") + " a where a.orderid=? and  d.id=  a.depotid and a.itemid=?";
/*     */ 
/* 113 */     return this.daoSupport.queryForList(sql, new Object[] { orderid, itemid });
/*     */   }
/*     */ 
/*     */   public String getStoreHtml(Integer goodsid)
/*     */   {
/* 122 */     Map goods = getGoods(goodsid.intValue());
/*     */ 
/* 124 */     return this.goodsStorePluginBundle.getStoreHtml(goods);
/*     */   }
/*     */ 
/*     */   public String getStockHtml(Integer goodsid)
/*     */   {
/* 133 */     Map goods = getGoods(goodsid.intValue());
/* 134 */     return this.goodsStorePluginBundle.getStockHtml(goods);
/*     */   }
/*     */ 
/*     */   public String getWarnHtml(Integer goodsid)
/*     */   {
/* 142 */     Map goods = getGoods(goodsid.intValue());
/* 143 */     return this.goodsStorePluginBundle.getWarnHtml(goods);
/*     */   }
/*     */ 
/*     */   public String getShipHtml(Integer goodsid)
/*     */   {
/* 152 */     Map goods = getGoods(goodsid.intValue());
/* 153 */     return this.goodsStorePluginBundle.getShipHtml(goods);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveStore(int goodsid)
/*     */   {
/* 160 */     Map goods = getGoods(goodsid);
/* 161 */     this.goodsStorePluginBundle.onStoreSave(goods);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveStock(int goodsid)
/*     */   {
/* 171 */     Map goods = getGoods(goodsid);
/* 172 */     this.goodsStorePluginBundle.onStockSave(goods);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveWarn(int goodsid)
/*     */   {
/* 181 */     Map goods = getGoods(goodsid);
/* 182 */     this.goodsStorePluginBundle.onWarnSave(goods);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveShip(int goodsid)
/*     */   {
/* 192 */     Map goods = getGoods(goodsid);
/* 193 */     this.goodsStorePluginBundle.onShipSave(goods);
/*     */   }
/*     */ 
/*     */   public void saveCmpl(int goodsid)
/*     */   {
/* 201 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/* 202 */     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/* 204 */     if ((!isSuperAdmin) && (!isDepotAdmin)) {
/* 205 */       throw new RuntimeException("没有维护库存的权限");
/*     */     }
/*     */ 
/* 209 */     if (isSuperAdmin) {
/* 210 */       this.baseDaoSupport.execute("update goods_depot set iscmpl=1  where goodsid=?", new Object[] { Integer.valueOf(goodsid) });
/*     */     }
/*     */     else {
/* 213 */       DepotUser user = (DepotUser)this.adminUserManager.getCurrentUser();
/* 214 */       this.baseDaoSupport.execute("update goods_depot set iscmpl=1  where goodsid=? and depotid=? ", new Object[] { Integer.valueOf(goodsid), user.getDepotid() });
/*     */     }
/*     */   }
/*     */ 
/*     */   private Map getGoods(int goodsid)
/*     */   {
/* 221 */     String sql = "select * from goods  where goods_id=?";
/* 222 */     Map goods = this.baseDaoSupport.queryForMap(sql, new Object[] { Integer.valueOf(goodsid) });
/* 223 */     return goods;
/*     */   }
/*     */ 
/*     */   public IDepotManager getDepotManager()
/*     */   {
/* 229 */     return this.depotManager;
/*     */   }
/*     */ 
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 233 */     this.depotManager = depotManager;
/*     */   }
/*     */ 
/*     */   public GoodsStorePluginBundle getGoodsStorePluginBundle() {
/* 237 */     return this.goodsStorePluginBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsStorePluginBundle(GoodsStorePluginBundle goodsStorePluginBundle)
/*     */   {
/* 242 */     this.goodsStorePluginBundle = goodsStorePluginBundle;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 247 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager)
/*     */   {
/* 252 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public List<WarnNum> listWarns(Integer goods_id)
/*     */   {
/* 261 */     String sql = "select * from warn_num where  goods_id=?";
/* 262 */     List list = this.baseDaoSupport.queryForList(sql, WarnNum.class, new Object[] { goods_id });
/*     */ 
/* 264 */     List warnList = new ArrayList();
/* 265 */     if ((list != null) && (!list.isEmpty())) {
/* 266 */       for (WarnNum warnNum : list)
/* 267 */         warnList.add(warnNum);
/*     */     }
/*     */     else {
/* 270 */       WarnNum warnNum = new WarnNum();
/* 271 */       warnNum.setId(Integer.valueOf(0));
/* 272 */       warnNum.setGoods_id(goods_id);
/* 273 */       warnNum.setWarn_num(Integer.valueOf(0));
/* 274 */       warnList.add(warnNum);
/*     */     }
/* 276 */     return warnList;
/*     */   }
/*     */ 
/*     */   public List<Map> getDegreeDepotStore(int goodsid, int depotid)
/*     */   {
/* 282 */     String sql = "select p.* from  product_store p where p.goodsid=? and p.depotid=?";
/* 283 */     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid), Integer.valueOf(depotid) });
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager()
/*     */   {
/* 288 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager)
/*     */   {
/* 293 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsStoreManager
 * JD-Core Version:    0.6.0
 */