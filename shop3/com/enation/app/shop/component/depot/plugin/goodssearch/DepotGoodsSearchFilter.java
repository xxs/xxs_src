/*    */ package com.enation.app.shop.component.depot.plugin.goodssearch;
/*    */ 
/*    */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*    */ import com.enation.app.shop.core.model.DepotUser;
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsSearchFilter;
/*    */ import com.enation.eop.resource.model.AdminUser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class DepotGoodsSearchFilter extends AutoRegisterPlugin
/*    */   implements IGoodsSearchFilter
/*    */ {
/*    */   private IAdminUserManager adminUserManager;
/*    */ 
/*    */   public String getSelector()
/*    */   {
/* 29 */     return "";
/*    */   }
/*    */ 
/*    */   public String getFrom()
/*    */   {
/* 34 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 35 */     String optype = request.getParameter("optype");
/*    */ 
/* 37 */     AdminUser user = this.adminUserManager.getCurrentUser();
/* 38 */     if ((("mng".equals(optype)) || ("stock".equals(optype))) && (user.getFounder() == 0))
/* 39 */       return " inner join es_goods_depot gd on g.goods_id=gd.goodsid ";
/* 40 */     if ("monitor".equals(optype)) {
/* 41 */       return " inner join es_goods_depot gd on g.goods_id=gd.goodsid ";
/*    */     }
/* 43 */     return "";
/*    */   }
/*    */ 
/*    */   public void filter(StringBuffer sql)
/*    */   {
/* 48 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 49 */     String optype = request.getParameter("optype");
/*    */ 
/* 51 */     AdminUser user = this.adminUserManager.getCurrentUser();
/* 52 */     if (("stock".equals(optype)) && (user.getFounder() == 0)) {
/* 53 */       DepotUser depotUser = (DepotUser)user;
/*    */ 
/* 55 */       sql.append(" and gd.iscmpl=0 and gd.depotid=" + depotUser.getDepotid());
/*    */     }
/* 57 */     else if (("mng".equals(optype)) && (user.getFounder() == 0)) {
/* 58 */       DepotUser depotUser = (DepotUser)user;
/*    */ 
/* 60 */       sql.append(" and gd.iscmpl=1 and gd.depotid=" + depotUser.getDepotid());
/*    */     }
/* 63 */     else if ("monitor".equals(optype)) {
/* 64 */       String depotid = request.getParameter("depotid");
/* 65 */       sql.append(" and gd.iscmpl=0 and gd.depotid=" + depotid);
/*    */     }
/*    */   }
/*    */ 
/*    */   public IAdminUserManager getAdminUserManager()
/*    */   {
/* 71 */     return this.adminUserManager;
/*    */   }
/*    */ 
/*    */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 75 */     this.adminUserManager = adminUserManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.depot.plugin.goodssearch.DepotGoodsSearchFilter
 * JD-Core Version:    0.6.0
 */