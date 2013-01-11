/*    */ package com.enation.app.shop.core.service.impl.promotion;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Promotion;
/*    */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*    */ import com.enation.app.shop.core.service.promotion.IReducePriceBehavior;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.CurrencyUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ReducePriceMethod
/*    */   implements IPromotionMethod, IReducePriceBehavior
/*    */ {
/*    */   public String getInputHtml(Integer pmtid, String solution)
/*    */   {
/* 24 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 25 */     freeMarkerPaser.setClz(getClass());
/* 26 */     freeMarkerPaser.putData("lessMoney", solution);
/* 27 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 32 */     return "reducePrice";
/*    */   }
/*    */ 
/*    */   public String onPromotionSave(Integer pmtid)
/*    */   {
/* 37 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 38 */     String reducePrice = request.getParameter("lessMoney");
/* 39 */     return reducePrice == null ? "" : reducePrice;
/*    */   }
/*    */ 
/*    */   public Double reducedPrice(Promotion pmt, Double price)
/*    */   {
/* 44 */     String solution = pmt.getPmt_solution();
/* 45 */     Double lessMoney = Double.valueOf(solution);
/* 46 */     return Double.valueOf(CurrencyUtil.sub(price.doubleValue(), lessMoney.doubleValue()));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.ReducePriceMethod
 * JD-Core Version:    0.6.0
 */