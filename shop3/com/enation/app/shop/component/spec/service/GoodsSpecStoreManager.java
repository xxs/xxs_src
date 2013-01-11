/*     */ package com.enation.app.shop.component.spec.service;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.core.model.Depot;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsSpecStoreManager extends BaseSupport
/*     */   implements IGoodsSpecStoreManager
/*     */ {
/*     */   private IDepotManager depotManager;
/*     */   private IProductManager productManager;
/*     */   private IPermissionManager permissionManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */ 
/*     */   public List<Map> listGoodsStore(int goodsid)
/*     */   {
/*  43 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/*  44 */     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/*  46 */     if ((!isSuperAdmin) && (!isDepotAdmin)) {
/*  47 */       throw new RuntimeException("没有维护库存的权限");
/*     */     }
/*     */ 
/*  54 */     List prolist = this.productManager.list(Integer.valueOf(goodsid));
/*     */ 
/*  57 */     String sql = "select * from product_store where goodsid=?";
/*  58 */     List storeList = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid) });
/*     */ 
/*  61 */     List depotList = this.depotManager.list();
/*     */ 
/*  63 */     List list = new ArrayList();
/*     */ 
/*  67 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*  68 */     Integer depotid = null;
/*  69 */     if (!isSuperAdmin) {
/*  70 */       DepotUser depotUser = (DepotUser)adminUser;
/*  71 */       depotid = depotUser.getDepotid();
/*     */     }
/*     */ 
/*  74 */     for (Depot depot : depotList) {
/*  75 */       if ((!isSuperAdmin) && (
/*  76 */         (depotid == null) || (depot.getId().intValue() != depotid.intValue())))
/*     */         continue;
/*  78 */       Map depotMap = new HashMap();
/*  79 */       depotMap.put("depotid", depot.getId());
/*  80 */       depotMap.put("depotname", depot.getName());
/*     */ 
/*  82 */       List pList = getProductList(depot.getId().intValue(), prolist, storeList);
/*  83 */       depotMap.put("productList", pList);
/*  84 */       list.add(depotMap);
/*     */     }
/*     */ 
/*  88 */     return list;
/*     */   }
/*     */ 
/*     */   private List<Map> getProductList(int depotid, List<Product> productList, List<Map> storeList)
/*     */   {
/*  95 */     List pList = new ArrayList();
/*     */ 
/*  97 */     for (Product product : productList) {
/*  98 */       Map pro = new HashMap();
/*     */ 
/* 100 */       pro.put("specList", product.getSpecList());
/* 101 */       pro.put("sn", product.getSn());
/* 102 */       pro.put("product_id", product.getProduct_id());
/* 103 */       pro.put("storeid", Integer.valueOf(0));
/* 104 */       pro.put("store", Integer.valueOf(0));
/* 105 */       for (Map store : storeList)
/*     */       {
/* 108 */         if ((depotid == ((Integer)store.get("depotid")).intValue()) && (product.getProduct_id().intValue() == ((Integer)store.get("productid")).intValue()))
/*     */         {
/* 113 */           pro.put("storeid", (Integer)store.get("storeid"));
/* 114 */           pro.put("store", (Integer)store.get("store"));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       pList.add(pro);
/*     */     }
/*     */ 
/* 123 */     return pList;
/*     */   }
/*     */ 
/*     */   public int stock(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
/*     */   {
/* 136 */     int total = 0;
/* 137 */     for (int i = 0; i < storeidAr.length; i++) {
/* 138 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 139 */       int store = StringUtil.toInt(storeAr[i], true);
/* 140 */       int depotid = StringUtil.toInt(depotidAr[i], true);
/* 141 */       int productid = StringUtil.toInt(productidAr[i], true);
/*     */ 
/* 143 */       if (storeid == 0)
/* 144 */         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
/*     */       else {
/* 146 */         this.baseDaoSupport.execute("update product_store set  store=store+? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */       }
/*     */ 
/* 150 */       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });
/*     */ 
/* 152 */       total += store;
/*     */     }
/*     */ 
/* 156 */     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });
/*     */ 
/* 158 */     return total;
/*     */   }
/*     */ 
/*     */   public int saveStore(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
/*     */   {
/* 167 */     int total = 0;
/* 168 */     for (int i = 0; i < storeidAr.length; i++) {
/* 169 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 170 */       int store = StringUtil.toInt(storeAr[i], true);
/* 171 */       int depotid = StringUtil.toInt(depotidAr[i], true);
/* 172 */       int productid = StringUtil.toInt(productidAr[i], true);
/*     */ 
/* 174 */       if (storeid == 0)
/* 175 */         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
/*     */       else {
/* 177 */         this.baseDaoSupport.execute("update product_store set  store=? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */       }
/*     */ 
/* 181 */       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });
/*     */ 
/* 183 */       total += store;
/*     */     }
/*     */ 
/* 187 */     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });
/*     */ 
/* 189 */     return total;
/*     */   }
/*     */ 
/*     */   public int ship(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
/*     */   {
/* 199 */     int total = 0;
/* 200 */     for (int i = 0; i < storeidAr.length; i++) {
/* 201 */       int storeid = StringUtil.toInt(storeidAr[i], true);
/* 202 */       int store = StringUtil.toInt(storeAr[i], true);
/* 203 */       int depotid = StringUtil.toInt(depotidAr[i], true);
/* 204 */       int productid = StringUtil.toInt(productidAr[i], true);
/*     */ 
/* 206 */       if (storeid == 0)
/* 207 */         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
/*     */       else {
/* 209 */         this.baseDaoSupport.execute("update product_store set  store=store-? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
/*     */       }
/*     */ 
/* 213 */       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });
/*     */ 
/* 215 */       total += store;
/*     */     }
/*     */ 
/* 219 */     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });
/*     */ 
/* 221 */     return total;
/*     */   }
/*     */ 
/*     */   public IDepotManager getDepotManager()
/*     */   {
/* 227 */     return this.depotManager;
/*     */   }
/*     */ 
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 231 */     this.depotManager = depotManager;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager()
/*     */   {
/* 236 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager)
/*     */   {
/* 241 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager()
/*     */   {
/* 246 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager)
/*     */   {
/* 251 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/* 256 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager)
/*     */   {
/* 261 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.GoodsSpecStoreManager
 * JD-Core Version:    0.6.0
 */