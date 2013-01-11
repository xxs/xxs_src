/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_intro")
/*    */ @Scope("prototype")
/*    */ public class GoodsIntroWidget extends AbstractWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 28 */     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/*    */ 
/* 30 */     if (goods == null) throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
/* 31 */     String intro = (String)goods.get("intro");
/* 32 */     intro = intro == null ? "" : intro;
/* 33 */     putData("intro", intro);
/* 34 */     setPageName("intro");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsIntroWidget
 * JD-Core Version:    0.6.0
 */