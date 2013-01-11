/*    */ package com.enation.app.shop.component.widget.brand.group;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IBrandManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("brandGroup")
/*    */ @Scope("prototype")
/*    */ public class BrandGroupWidget extends AbstractWidget
/*    */ {
/*    */   private IBrandManager brandManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 29 */     List list = this.brandManager.groupByCat();
/* 30 */     putData("listCat", list);
/*    */   }
/*    */ 
/*    */   public IBrandManager getBrandManager() {
/* 34 */     return this.brandManager;
/*    */   }
/*    */ 
/*    */   public void setBrandManager(IBrandManager brandManager) {
/* 38 */     this.brandManager = brandManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.brand.group.BrandGroupWidget
 * JD-Core Version:    0.6.0
 */