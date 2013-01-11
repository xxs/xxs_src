/*    */ package com.enation.app.shop.component.widget.brand;
/*    */ 
/*    */ import com.enation.app.base.component.widget.nav.Nav;
/*    */ import com.enation.app.shop.core.service.IBrandManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("brand_list")
/*    */ @Scope("prototype")
/*    */ public class BrandListWidget extends AbstractWidget
/*    */ {
/*    */   private IBrandManager brandManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 31 */     setPageName("brand");
/* 32 */     List listBrand = this.brandManager.list();
/* 33 */     putData("listBrand", listBrand);
/* 34 */     Nav nav = new Nav();
/* 35 */     nav.setTitle("品牌专区");
/* 36 */     putNav(nav);
/*    */   }
/*    */ 
/*    */   public IBrandManager getBrandManager() {
/* 40 */     return this.brandManager;
/*    */   }
/*    */ 
/*    */   public void setBrandManager(IBrandManager brandManager) {
/* 44 */     this.brandManager = brandManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.brand.BrandListWidget
 * JD-Core Version:    0.6.0
 */