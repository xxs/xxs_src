/*    */ package com.enation.app.shop.component.goodscore.widget.goodscat;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_cat")
/*    */ @Scope("prototype")
/*    */ public class GoodsCatWidget extends AbstractWidget
/*    */ {
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 25 */     setPageName("GoodsCat");
/* 26 */     List cat_tree = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/* 27 */     String catimage = (String)params.get("catimage");
/* 28 */     boolean showimage = (catimage != null) && (catimage.equals("on"));
/* 29 */     putData("showimg", Boolean.valueOf(showimage));
/* 30 */     putData("cat_tree", cat_tree);
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IGoodsCatManager getGoodsCatManager()
/*    */   {
/* 40 */     return this.goodsCatManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 44 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goodscat.GoodsCatWidget
 * JD-Core Version:    0.6.0
 */