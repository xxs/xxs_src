/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.app.shop.core.model.Attribute;
/*    */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*    */ import com.enation.app.shop.core.service.ILimitBuyManager;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_props")
/*    */ @Scope("prototype")
/*    */ public class GoodsPropsWidget extends AbstractGoodsDetailWidget
/*    */ {
/*    */   private IGoodsTypeManager goodsTypeManager;
/*    */   private ILimitBuyManager limitBuyManager;
/*    */ 
/*    */   public void execute(Map<String, String> params, Map goods)
/*    */   {
/* 28 */     Integer type_id = (Integer)goods.get("type_id");
/* 29 */     if (type_id == null) { this.showHtml = false; return;
/*    */     }
/* 31 */     List attrList = this.goodsTypeManager.getAttrListByTypeId(type_id.intValue());
/*    */ 
/* 33 */     int i = 1;
/* 34 */     for (Attribute attr : attrList) {
/* 35 */       String value = "" + goods.get(new StringBuilder().append("p").append(i).toString());
/* 36 */       attr.setValue(value);
/* 37 */       goods.put("p" + i + "_text", attr.getValStr());
/* 38 */       i++;
/*    */     }
/*    */ 
/* 44 */     List goodsList = this.limitBuyManager.listEnableGoods();
/*    */ 
/* 46 */     for (Map limitgoods : goodsList) {
/* 47 */       Integer goodsid = (Integer)limitgoods.get("goods_id");
/* 48 */       if (goodsid.compareTo((Integer)goods.get("goods_id")) == 0) {
/* 49 */         putData("limitprice", limitgoods.get("limitprice"));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 56 */     setPageName("props");
/*    */ 
/* 58 */     putData("goods", goods);
/* 59 */     putData("attrList", attrList);
/*    */   }
/*    */ 
/*    */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
/*    */   {
/* 64 */     this.goodsTypeManager = goodsTypeManager;
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public ILimitBuyManager getLimitBuyManager()
/*    */   {
/* 73 */     return this.limitBuyManager;
/*    */   }
/*    */ 
/*    */   public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
/* 77 */     this.limitBuyManager = limitBuyManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsPropsWidget
 * JD-Core Version:    0.6.0
 */