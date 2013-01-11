/*    */ package com.enation.app.shop.component.depot.plugin.goods;
/*    */ 
/*    */ import com.enation.app.base.core.service.IShortMsgManager;
/*    */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*    */ import com.enation.app.base.core.service.auth.IRoleManager;
/*    */ import com.enation.app.shop.core.model.Depot;
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*    */ import com.enation.app.shop.core.service.IDepotManager;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsDepotCreatePlugin extends AutoRegisterPlugin
/*    */   implements IGoodsAfterAddEvent, IGoodsDeleteEvent
/*    */ {
/*    */   private IDepotManager depotManager;
/*    */   private IDaoSupport baseDaoSupport;
/*    */   private IShortMsgManager shortMsgManager;
/*    */   private IAdminUserManager adminUserManager;
/*    */   private IRoleManager roleManager;
/*    */ 
/*    */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*    */     throws RuntimeException
/*    */   {
/* 38 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 39 */     List depotList = this.depotManager.list();
/* 40 */     for (Depot depot : depotList)
/* 41 */       this.baseDaoSupport.execute("insert into goods_depot(goodsid,depotid,iscmpl)values(?,?,?)", new Object[] { goodsid, depot.getId(), Integer.valueOf(0) });
/*    */   }
/*    */ 
/*    */   public void onGoodsDelete(Integer[] goodsid)
/*    */   {
/* 52 */     if ((goodsid == null) || (goodsid.length == 0)) return;
/* 53 */     String goodsidstr = StringUtil.arrayToString(goodsid, ",");
/* 54 */     this.baseDaoSupport.execute("delete from goods_depot where goodsid in (" + goodsidstr + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public IDepotManager getDepotManager()
/*    */   {
/* 59 */     return this.depotManager;
/*    */   }
/*    */ 
/*    */   public void setDepotManager(IDepotManager depotManager) {
/* 63 */     this.depotManager = depotManager;
/*    */   }
/*    */ 
/*    */   public IDaoSupport getBaseDaoSupport()
/*    */   {
/* 68 */     return this.baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
/*    */   {
/* 73 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public IShortMsgManager getShortMsgManager()
/*    */   {
/* 78 */     return this.shortMsgManager;
/*    */   }
/*    */ 
/*    */   public void setShortMsgManager(IShortMsgManager shortMsgManager) {
/* 82 */     this.shortMsgManager = shortMsgManager;
/*    */   }
/*    */ 
/*    */   public IAdminUserManager getAdminUserManager() {
/* 86 */     return this.adminUserManager;
/*    */   }
/*    */ 
/*    */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 90 */     this.adminUserManager = adminUserManager;
/*    */   }
/*    */ 
/*    */   public IRoleManager getRoleManager() {
/* 94 */     return this.roleManager;
/*    */   }
/*    */ 
/*    */   public void setRoleManager(IRoleManager roleManager) {
/* 98 */     this.roleManager = roleManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.depot.plugin.goods.GoodsDepotCreatePlugin
 * JD-Core Version:    0.6.0
 */