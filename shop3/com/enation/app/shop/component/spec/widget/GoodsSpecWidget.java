/*    */ package com.enation.app.shop.component.spec.widget;
/*    */ 
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.app.shop.core.model.Product;
/*    */ import com.enation.app.shop.core.service.IProductManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_spec")
/*    */ @Scope("prototype")
/*    */ public class GoodsSpecWidget extends AbstractGoodsDetailWidget
/*    */ {
/*    */   private IProductManager productManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IProductManager getProductManager()
/*    */   {
/* 31 */     return this.productManager;
/*    */   }
/*    */ 
/*    */   public void setProductManager(IProductManager productManager) {
/* 35 */     this.productManager = productManager;
/*    */   }
/*    */ 
/*    */   protected void execute(Map<String, String> params, Map goods)
/*    */   {
/* 41 */     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
/* 42 */     List productList = this.productManager.list(goods_id);
/*    */ 
/* 44 */     if (("" + goods.get("have_spec")).equals("0")) {
/* 45 */       putData("productid", ((Product)productList.get(0)).getProduct_id());
/* 46 */       putData("productList", productList);
/*    */     } else {
/* 48 */       List specList = this.productManager.listSpecs(goods_id);
/*    */ 
/* 50 */       putData("productList", productList);
/* 51 */       putData("specList", specList);
/*    */     }
/* 53 */     putData("have_spec", goods.get("have_spec"));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.widget.GoodsSpecWidget
 * JD-Core Version:    0.6.0
 */