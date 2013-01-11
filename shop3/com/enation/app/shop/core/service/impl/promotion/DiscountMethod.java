/*    */ package com.enation.app.shop.core.service.impl.promotion;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Promotion;
/*    */ import com.enation.app.shop.core.service.promotion.IDiscountBehavior;
/*    */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.CurrencyUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class DiscountMethod
/*    */   implements IPromotionMethod, IDiscountBehavior
/*    */ {
/*    */   public String getInputHtml(Integer pmtid, String solution)
/*    */   {
/* 25 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 26 */     freeMarkerPaser.setClz(getClass());
/* 27 */     freeMarkerPaser.putData("discount", solution);
/* 28 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 33 */     return "discount";
/*    */   }
/*    */ 
/*    */   public String onPromotionSave(Integer pmtid)
/*    */   {
/* 38 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 39 */     String discount = request.getParameter("discount");
/* 40 */     return discount == null ? "" : discount;
/*    */   }
/*    */ 
/*    */   public Double discount(Promotion promotion, Double goodsPrice)
/*    */   {
/* 45 */     String solution = promotion.getPmt_solution();
/* 46 */     Double discount = Double.valueOf(solution);
/* 47 */     return CurrencyUtil.mul(goodsPrice.doubleValue(), discount.doubleValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.DiscountMethod
 * JD-Core Version:    0.6.0
 */