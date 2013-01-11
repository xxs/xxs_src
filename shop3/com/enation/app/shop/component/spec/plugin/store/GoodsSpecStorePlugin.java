/*     */ package com.enation.app.shop.component.spec.plugin.store;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.app.shop.component.spec.service.IGoodsSpecStoreManager;
/*     */ import com.enation.app.shop.core.model.DepotUser;
/*     */ import com.enation.app.shop.core.model.StoreLog;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsStorePlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.app.shop.core.service.IStoreLogManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsSpecStorePlugin extends AbstractGoodsStorePlugin
/*     */   implements IGoodsDeleteEvent
/*     */ {
/*     */   private IGoodsSpecStoreManager goodsSpecStoreManager;
/*     */   private IProductManager productManager;
/*     */   private IStoreLogManager storeLogManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IPermissionManager permissionManager;
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getStoreHtml(Map goods)
/*     */   {
/*  51 */     Integer goodsid = (Integer)goods.get("goods_id");
/*  52 */     List depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
/*  53 */     List specNameList = this.productManager.listSpecName(goodsid.intValue());
/*     */ 
/*  55 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  56 */     freeMarkerPaser.putData("depotList", depotList);
/*  57 */     freeMarkerPaser.putData("specNameList", specNameList);
/*  58 */     freeMarkerPaser.setPageName("store_input");
/*  59 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getStockHtml(Map goods)
/*     */   {
/*  69 */     Integer goodsid = (Integer)goods.get("goods_id");
/*  70 */     List depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
/*  71 */     List specNameList = this.productManager.listSpecName(goodsid.intValue());
/*     */ 
/*  73 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  74 */     freeMarkerPaser.putData("depotList", depotList);
/*  75 */     freeMarkerPaser.putData("specNameList", specNameList);
/*  76 */     freeMarkerPaser.setPageName("stock_input");
/*  77 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getShipHtml(Map goods)
/*     */   {
/*  84 */     Integer goodsid = (Integer)goods.get("goods_id");
/*  85 */     List depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
/*  86 */     List specNameList = this.productManager.listSpecName(goodsid.intValue());
/*     */ 
/*  88 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  89 */     freeMarkerPaser.putData("depotList", depotList);
/*  90 */     freeMarkerPaser.putData("specNameList", specNameList);
/*  91 */     freeMarkerPaser.setPageName("ship_input");
/*  92 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onStoreSave(Map goods)
/*     */   {
/*  97 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/*  98 */     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/* 102 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 104 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 105 */     String[] idAr = request.getParameterValues("id");
/* 106 */     String[] productidAr = request.getParameterValues("productid");
/* 107 */     String[] depotidAr = request.getParameterValues("depotid");
/* 108 */     String[] storeAr = request.getParameterValues("store");
/*     */ 
/* 110 */     int total = this.goodsSpecStoreManager.saveStore(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);
/*     */ 
/* 114 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*     */ 
/* 116 */     if (isSuperAdmin)
/*     */     {
/* 118 */       for (String deptoid : depotidAr)
/*     */       {
/* 120 */         int did = StringUtil.toInt(deptoid, true);
/* 121 */         StoreLog storeLog = new StoreLog();
/* 122 */         storeLog.setGoodsid(goodsid);
/* 123 */         storeLog.setGoodsname(goods.get("name").toString());
/* 124 */         storeLog.setDepot_type(Integer.valueOf(0));
/* 125 */         storeLog.setOp_type(Integer.valueOf(EopSetting.getDepotType(did)));
/* 126 */         storeLog.setDepotid(Integer.valueOf(did));
/* 127 */         storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 128 */         storeLog.setNum(Integer.valueOf(total));
/* 129 */         storeLog.setUserid(adminUser.getUserid());
/* 130 */         storeLog.setUsername(adminUser.getUsername());
/* 131 */         this.storeLogManager.add(storeLog);
/*     */       }
/*     */ 
/*     */     }
/* 135 */     else if (isDepotAdmin)
/*     */     {
/* 137 */       DepotUser depotUser = (DepotUser)adminUser;
/* 138 */       StoreLog storeLog = new StoreLog();
/* 139 */       storeLog.setGoodsid(goodsid);
/* 140 */       storeLog.setGoodsname(goods.get("name").toString());
/* 141 */       storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
/* 142 */       storeLog.setOp_type(Integer.valueOf(0));
/* 143 */       storeLog.setDepotid(depotUser.getDepotid());
/* 144 */       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 145 */       storeLog.setNum(Integer.valueOf(total));
/* 146 */       storeLog.setUserid(adminUser.getUserid());
/* 147 */       storeLog.setUsername(adminUser.getUsername());
/* 148 */       this.storeLogManager.add(storeLog);
/*     */     }
/*     */     else {
/* 151 */       throw new RuntimeException("没有操作库存的权限");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onStockSave(Map goods)
/*     */   {
/* 165 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/* 166 */     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/* 170 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 172 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 173 */     String[] idAr = request.getParameterValues("id");
/* 174 */     String[] productidAr = request.getParameterValues("productid");
/* 175 */     String[] depotidAr = request.getParameterValues("depotid");
/* 176 */     String[] storeAr = request.getParameterValues("store");
/*     */ 
/* 178 */     int total = this.goodsSpecStoreManager.stock(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);
/*     */ 
/* 182 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*     */ 
/* 184 */     if (isSuperAdmin)
/*     */     {
/* 186 */       for (String deptoid : depotidAr)
/*     */       {
/* 188 */         int did = StringUtil.toInt(deptoid, true);
/* 189 */         StoreLog storeLog = new StoreLog();
/* 190 */         storeLog.setGoodsid(goodsid);
/* 191 */         storeLog.setGoodsname(goods.get("name").toString());
/* 192 */         storeLog.setDepot_type(Integer.valueOf(0));
/* 193 */         storeLog.setOp_type(Integer.valueOf(EopSetting.getDepotType(did)));
/* 194 */         storeLog.setDepotid(Integer.valueOf(did));
/* 195 */         storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 196 */         storeLog.setNum(Integer.valueOf(total));
/* 197 */         storeLog.setUserid(adminUser.getUserid());
/* 198 */         storeLog.setUsername(adminUser.getUsername());
/* 199 */         this.storeLogManager.add(storeLog);
/*     */       }
/*     */ 
/*     */     }
/* 203 */     else if (isDepotAdmin)
/*     */     {
/* 205 */       DepotUser depotUser = (DepotUser)adminUser;
/* 206 */       StoreLog storeLog = new StoreLog();
/* 207 */       storeLog.setGoodsid(goodsid);
/* 208 */       storeLog.setGoodsname(goods.get("name").toString());
/* 209 */       storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
/* 210 */       storeLog.setOp_type(Integer.valueOf(0));
/* 211 */       storeLog.setDepotid(depotUser.getDepotid());
/* 212 */       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 213 */       storeLog.setNum(Integer.valueOf(total));
/* 214 */       storeLog.setUserid(adminUser.getUserid());
/* 215 */       storeLog.setUsername(adminUser.getUsername());
/* 216 */       this.storeLogManager.add(storeLog);
/*     */     }
/*     */     else {
/* 219 */       throw new RuntimeException("没有操作库存的权限");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onShipSave(Map goods)
/*     */   {
/* 231 */     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
/* 232 */     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
/*     */ 
/* 234 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/* 236 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 237 */     String[] idAr = request.getParameterValues("id");
/* 238 */     String[] productidAr = request.getParameterValues("productid");
/* 239 */     String[] depotidAr = request.getParameterValues("depotid");
/* 240 */     String[] storeAr = request.getParameterValues("store");
/*     */ 
/* 242 */     int total = this.goodsSpecStoreManager.ship(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);
/*     */ 
/* 246 */     AdminUser adminUser = this.adminUserManager.getCurrentUser();
/*     */ 
/* 248 */     if (isSuperAdmin)
/*     */     {
/* 250 */       for (String deptoid : depotidAr)
/*     */       {
/* 252 */         int did = StringUtil.toInt(deptoid, true);
/* 253 */         StoreLog storeLog = new StoreLog();
/* 254 */         storeLog.setGoodsid(goodsid);
/* 255 */         storeLog.setGoodsname(goods.get("name").toString());
/* 256 */         storeLog.setDepot_type(Integer.valueOf(0));
/* 257 */         storeLog.setOp_type(Integer.valueOf(EopSetting.getDepotType(did)));
/* 258 */         storeLog.setDepotid(Integer.valueOf(did));
/* 259 */         storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 260 */         storeLog.setNum(Integer.valueOf(total));
/* 261 */         storeLog.setUserid(adminUser.getUserid());
/* 262 */         storeLog.setUsername(adminUser.getUsername());
/* 263 */         this.storeLogManager.add(storeLog);
/*     */       }
/*     */ 
/*     */     }
/* 267 */     else if (isDepotAdmin)
/*     */     {
/* 269 */       DepotUser depotUser = (DepotUser)adminUser;
/* 270 */       StoreLog storeLog = new StoreLog();
/* 271 */       storeLog.setGoodsid(goodsid);
/* 272 */       storeLog.setGoodsname(goods.get("name").toString());
/* 273 */       storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
/* 274 */       storeLog.setOp_type(Integer.valueOf(0));
/* 275 */       storeLog.setDepotid(depotUser.getDepotid());
/* 276 */       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
/* 277 */       storeLog.setNum(Integer.valueOf(total));
/* 278 */       storeLog.setUserid(adminUser.getUserid());
/* 279 */       storeLog.setUsername(adminUser.getUsername());
/* 280 */       this.storeLogManager.add(storeLog);
/*     */     }
/*     */     else {
/* 283 */       throw new RuntimeException("没有操作库存的权限");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBeExecute(Map goods)
/*     */   {
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */   public String getWarnNumHtml(Map goods)
/*     */   {
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */   public void onWarnSave(Map goods)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IGoodsSpecStoreManager getGoodsSpecStoreManager()
/*     */   {
/* 310 */     return this.goodsSpecStoreManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsSpecStoreManager(IGoodsSpecStoreManager goodsSpecStoreManager)
/*     */   {
/* 315 */     this.goodsSpecStoreManager = goodsSpecStoreManager;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager() {
/* 319 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 323 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager() {
/* 327 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 331 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public IStoreLogManager getStoreLogManager() {
/* 335 */     return this.storeLogManager;
/*     */   }
/*     */ 
/*     */   public void setStoreLogManager(IStoreLogManager storeLogManager) {
/* 339 */     this.storeLogManager = storeLogManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 343 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 347 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.store.GoodsSpecStorePlugin
 * JD-Core Version:    0.6.0
 */