/*    */ package com.enation.app.shop.core.service.impl.promotion;
/*    */ 
/*    */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*    */ import com.enation.app.shop.core.service.promotion.IReduceFreightBehavior;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class FreeFreightMethod
/*    */   implements IPromotionMethod, IReduceFreightBehavior
/*    */ {
/*    */   public String getInputHtml(Integer pmtid, String solution)
/*    */   {
/* 19 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 20 */     freeMarkerPaser.setClz(getClass());
/* 21 */     freeMarkerPaser.putData("free", solution);
/* 22 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 28 */     return "free";
/*    */   }
/*    */ 
/*    */   public String onPromotionSave(Integer pmtid)
/*    */   {
/* 34 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 35 */     String free = request.getParameter("free");
/* 36 */     return free == null ? "" : free;
/*    */   }
/*    */ 
/*    */   public Double reducedPrice(Double freight)
/*    */   {
/* 42 */     return Double.valueOf(0.0D);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.FreeFreightMethod
 * JD-Core Version:    0.6.0
 */