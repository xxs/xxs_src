/*    */ package com.enation.app.shop.component.widget.search;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IBrandManager;
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("search")
/*    */ @Scope("prototype")
/*    */ public class SearchWidget extends AbstractWidget
/*    */ {
/*    */   private IBrandManager brandManager;
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 37 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 38 */     String action = request.getParameter("action");
/* 39 */     if ((action == null) || ("".equals(action))) {
/* 40 */       setPageName("search");
/* 41 */       List cat_tree = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/* 42 */       putData("cat_tree", cat_tree);
/*    */     }
/*    */ 
/* 45 */     if ("brand".equals(action))
/* 46 */       brandList();
/*    */   }
/*    */ 
/*    */   private void brandList()
/*    */   {
/* 53 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 54 */     Integer catid = Integer.valueOf(request.getParameter("cat_id"));
/* 55 */     List brandList = this.brandManager.listByCatId(catid);
/* 56 */     putData("brandList", brandList);
/* 57 */     setPageName("brandList");
/*    */   }
/*    */ 
/*    */   public IBrandManager getBrandManager()
/*    */   {
/* 62 */     return this.brandManager;
/*    */   }
/*    */ 
/*    */   public void setBrandManager(IBrandManager brandManager) {
/* 66 */     this.brandManager = brandManager;
/*    */   }
/*    */ 
/*    */   public IGoodsCatManager getGoodsCatManager() {
/* 70 */     return this.goodsCatManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 74 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.search.SearchWidget
 * JD-Core Version:    0.6.0
 */