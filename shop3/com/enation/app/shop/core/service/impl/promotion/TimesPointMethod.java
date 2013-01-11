/*    */ package com.enation.app.shop.core.service.impl.promotion;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Promotion;
/*    */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*    */ import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class TimesPointMethod
/*    */   implements IPromotionMethod, ITimesPointBehavior
/*    */ {
/*    */   public String getInputHtml(Integer pmtid, String solution)
/*    */   {
/* 20 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 21 */     freeMarkerPaser.setClz(getClass());
/* 22 */     freeMarkerPaser.putData("multiple", solution);
/* 23 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 29 */     return "timesPoint";
/*    */   }
/*    */ 
/*    */   public String onPromotionSave(Integer pmtid)
/*    */   {
/* 34 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 35 */     String multiple = request.getParameter("multiple");
/* 36 */     return multiple == null ? "" : multiple;
/*    */   }
/*    */ 
/*    */   public Integer countPoint(Promotion promotion, Integer point)
/*    */   {
/* 41 */     String solution = promotion.getPmt_solution();
/* 42 */     Integer multiple = Integer.valueOf(solution);
/*    */ 
/* 44 */     return Integer.valueOf(point.intValue() * multiple.intValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.TimesPointMethod
 * JD-Core Version:    0.6.0
 */