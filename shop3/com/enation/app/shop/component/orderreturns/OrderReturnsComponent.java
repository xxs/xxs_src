/*    */ package com.enation.app.shop.component.orderreturns;
/*    */ 
/*    */ import com.enation.app.base.core.service.auth.IAuthActionManager;
/*    */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*    */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*    */ import com.enation.eop.resource.IMenuManager;
/*    */ import com.enation.eop.resource.model.Menu;
/*    */ import com.enation.framework.component.IComponent;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class OrderReturnsComponent
/*    */   implements IComponent
/*    */ {
/*    */   private IMenuManager menuManager;
/*    */   private IAuthActionManager authActionManager;
/*    */ 
/*    */   public void install()
/*    */   {
/* 26 */     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
/* 27 */     int orderAuthId = PermissionConfig.getAuthId("order");
/*    */ 
/* 29 */     Menu parentMenu = this.menuManager.get("订单");
/*    */ 
/* 31 */     Menu menu = new Menu();
/* 32 */     menu.setTitle("退换申请");
/* 33 */     menu.setPid(parentMenu.getId());
/* 34 */     menu.setUrl("#");
/* 35 */     menu.setSorder(Integer.valueOf(55));
/* 36 */     menu.setMenutype(Integer.valueOf(2));
/* 37 */     int pmenuid = this.menuManager.add(menu).intValue();
/*    */ 
/* 40 */     Menu cmenu = new Menu();
/* 41 */     cmenu.setTitle("退货申请");
/* 42 */     cmenu.setPid(Integer.valueOf(pmenuid));
/* 43 */     cmenu.setUrl("/shop/admin/returnOrder!returnsList.do");
/* 44 */     cmenu.setSorder(Integer.valueOf(55));
/* 45 */     cmenu.setMenutype(Integer.valueOf(2));
/* 46 */     int menuid = this.menuManager.add(cmenu).intValue();
/*    */ 
/* 48 */     this.authActionManager.addMenu(superAdminAuthId, new Integer[] { Integer.valueOf(pmenuid), Integer.valueOf(menuid) });
/* 49 */     this.authActionManager.addMenu(orderAuthId, new Integer[] { Integer.valueOf(pmenuid), Integer.valueOf(menuid) });
/*    */ 
/* 51 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/orderreturns/orderreturns_install.xml", "es_");
/*    */   }
/*    */ 
/*    */   public void unInstall()
/*    */   {
/* 58 */     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
/* 59 */     int orderAuthId = PermissionConfig.getAuthId("order");
/*    */ 
/* 61 */     int menuid = this.menuManager.get("退货申请").getId().intValue();
/* 62 */     this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] { Integer.valueOf(menuid) });
/* 63 */     this.authActionManager.deleteMenu(orderAuthId, new Integer[] { Integer.valueOf(menuid) });
/*    */ 
/* 65 */     this.menuManager.delete("退货申请");
/* 66 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/orderreturns/orderreturns_uninstall.xml", "es_");
/*    */   }
/*    */ 
/*    */   public IMenuManager getMenuManager()
/*    */   {
/* 71 */     return this.menuManager;
/*    */   }
/*    */ 
/*    */   public void setMenuManager(IMenuManager menuManager) {
/* 75 */     this.menuManager = menuManager;
/*    */   }
/*    */ 
/*    */   public IAuthActionManager getAuthActionManager() {
/* 79 */     return this.authActionManager;
/*    */   }
/*    */ 
/*    */   public void setAuthActionManager(IAuthActionManager authActionManager) {
/* 83 */     this.authActionManager = authActionManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.orderreturns.OrderReturnsComponent
 * JD-Core Version:    0.6.0
 */