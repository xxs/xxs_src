/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.app.shop.core.model.support.ParamGroup;
/*    */ import com.enation.app.shop.core.service.GoodsTypeUtil;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_params")
/*    */ @Scope("prototype")
/*    */ public class GoodsParamsWidget extends AbstractGoodsDetailWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void execute(Map<String, String> params, Map goods)
/*    */   {
/* 31 */     String goodParams = (String)goods.get("params");
/* 32 */     if ((goodParams != null) && (!goodParams.equals(""))) {
/* 33 */       ParamGroup[] paramList = GoodsTypeUtil.converFormString(goodParams);
/* 34 */       putData("paramList", paramList);
/*    */ 
/* 36 */       if ((paramList != null) && (paramList.length > 0))
/* 37 */         putData("hasParam", Boolean.valueOf(true));
/*    */       else
/* 39 */         putData("hasParam", Boolean.valueOf(false));
/*    */     } else {
/* 41 */       putData("hasParam", Boolean.valueOf(false));
/*    */     }
/*    */ 
/* 44 */     setPageName("params");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsParamsWidget
 * JD-Core Version:    0.6.0
 */