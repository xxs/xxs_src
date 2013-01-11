/*    */ package com.enation.app.shop.component.visited.widget;
/*    */ 
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("visited_goods")
/*    */ @Scope("prototype")
/*    */ public class VisitedGoodsWidget extends AbstractWidget
/*    */ {
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 26 */     WebSessionContext sessionContext = ThreadContextHolder.getSessionContext();
/* 27 */     List visitedGoods = (List)sessionContext.getAttribute("visitedGoods");
/* 28 */     if (visitedGoods == null) visitedGoods = new ArrayList();
/* 29 */     putData("visitedGoods", visitedGoods);
/* 30 */     putData("GoodsPic", new ImageUrlDirectiveModel());
/* 31 */     setPageName("visited");
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.visited.widget.VisitedGoodsWidget
 * JD-Core Version:    0.6.0
 */