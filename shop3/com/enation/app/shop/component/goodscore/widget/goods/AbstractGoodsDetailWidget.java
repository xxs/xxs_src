/*    */ package com.enation.app.shop.component.goodscore.widget.goods;
/*    */ 
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public abstract class AbstractGoodsDetailWidget extends AbstractWidget
/*    */ {
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 19 */     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/* 20 */     if (goods == null) throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
/* 21 */     execute(params, goods);
/*    */   }
/*    */ 
/*    */   protected abstract void execute(Map<String, String> paramMap, Map paramMap1);
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget
 * JD-Core Version:    0.6.0
 */