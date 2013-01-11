/*    */ package com.enation.app.shop.component.spec;
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
/*    */ public class GoodsSpecComponent
/*    */   implements IComponent
/*    */ {
/*    */   private IMenuManager menuManager;
/*    */   private IAuthActionManager authActionManager;
/*    */ 
/*    */   public void install()
/*    */   {
/* 23 */     installMenu();
/* 24 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/spec/spec_install.xml", "es_");
/*    */   }
/*    */ 
/*    */   public void unInstall()
/*    */   {
/* 31 */     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
/* 32 */     int addmenuid = this.menuManager.get("添加规格").getId().intValue();
/* 33 */     int listmenuid = this.menuManager.get("规格列表").getId().intValue();
/* 34 */     int menuid = this.menuManager.get("商品设置").getId().intValue();
/*    */ 
/* 36 */     this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] { Integer.valueOf(menuid), Integer.valueOf(listmenuid), Integer.valueOf(addmenuid) });
/*    */ 
/* 38 */     this.menuManager.delete("添加规格");
/* 39 */     this.menuManager.delete("规格列表");
/* 40 */     this.menuManager.delete("商品设置");
/*    */ 
/* 42 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/spec/spec_uninstall.xml", "es_");
/*    */   }
/*    */ 
/*    */   private void installMenu()
/*    */   {
/* 49 */     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
/*    */ 
/* 51 */     Menu parentMenu = this.menuManager.get("商品设置");
/*    */ 
/* 53 */     Menu menu = new Menu();
/* 54 */     menu.setTitle("规格管理");
/* 55 */     menu.setPid(parentMenu.getId());
/* 56 */     menu.setUrl("#");
/* 57 */     menu.setSorder(Integer.valueOf(55));
/* 58 */     menu.setMenutype(Integer.valueOf(2));
/* 59 */     int menuid = this.menuManager.add(menu).intValue();
/*    */ 
/* 62 */     Menu listMenu = new Menu();
/* 63 */     listMenu.setPid(Integer.valueOf(menuid));
/* 64 */     listMenu.setTitle("规格列表");
/* 65 */     listMenu.setUrl("/shop/admin/spec!list.do");
/* 66 */     listMenu.setSorder(Integer.valueOf(1));
/* 67 */     listMenu.setTarget("ajax");
/* 68 */     listMenu.setMenutype(Integer.valueOf(2));
/* 69 */     int listmenuid = this.menuManager.add(listMenu).intValue();
/*    */ 
/* 71 */     Menu addMenu = new Menu();
/* 72 */     addMenu.setPid(Integer.valueOf(menuid));
/* 73 */     addMenu.setTitle("添加规格");
/* 74 */     addMenu.setUrl("/shop/admin/spec!add.do");
/* 75 */     addMenu.setSorder(Integer.valueOf(1));
/* 76 */     addMenu.setTarget("ajax");
/* 77 */     addMenu.setMenutype(Integer.valueOf(2));
/* 78 */     int addmenuid = this.menuManager.add(addMenu).intValue();
/*    */ 
/* 80 */     this.authActionManager.addMenu(superAdminAuthId, new Integer[] { Integer.valueOf(menuid), Integer.valueOf(listmenuid), Integer.valueOf(addmenuid) });
/*    */   }
/*    */ 
/*    */   public IMenuManager getMenuManager()
/*    */   {
/* 85 */     return this.menuManager;
/*    */   }
/*    */ 
/*    */   public void setMenuManager(IMenuManager menuManager) {
/* 89 */     this.menuManager = menuManager;
/*    */   }
/*    */ 
/*    */   public IAuthActionManager getAuthActionManager() {
/* 93 */     return this.authActionManager;
/*    */   }
/*    */ 
/*    */   public void setAuthActionManager(IAuthActionManager authActionManager) {
/* 97 */     this.authActionManager = authActionManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.GoodsSpecComponent
 * JD-Core Version:    0.6.0
 */