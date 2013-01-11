/*    */ package com.enation.app.shop.component.widget.complex;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsComplexManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class GoodsComplexWidget extends AbstractWidget
/*    */ {
/*    */   private IGoodsComplexManager goodsComplexManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 37 */     Integer goods_id = getGoodsId();
/* 38 */     List complexList = this.goodsComplexManager.listAllComplex(goods_id.intValue());
/* 39 */     boolean hasComplex = (complexList != null) && (!complexList.isEmpty());
/* 40 */     putData("hasComplex", Boolean.valueOf(hasComplex));
/* 41 */     putData("complexList", complexList);
/* 42 */     putData("GoodsPic", new ImageUrlDirectiveModel());
/* 43 */     setPageName("complex");
/*    */   }
/*    */ 
/*    */   private Integer getGoodsId() {
/* 47 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/* 48 */     String url = RequestUtil.getRequestUrl(httpRequest);
/*    */ 
/* 50 */     if (url.startsWith("/widget")) return Integer.valueOf(0);
/*    */ 
/* 52 */     String goods_id = paseGoodsId(url);
/*    */ 
/* 54 */     return Integer.valueOf(goods_id);
/*    */   }
/*    */ 
/*    */   private static String paseGoodsId(String url) {
/* 58 */     String pattern = "/(.*)-(\\d+).html(.*)";
/* 59 */     String value = null;
/* 60 */     Pattern p = Pattern.compile(pattern, 34);
/* 61 */     Matcher m = p.matcher(url);
/* 62 */     if (m.find()) {
/* 63 */       value = m.replaceAll("$2");
/*    */     }
/* 65 */     return value;
/*    */   }
/*    */ 
/*    */   public IGoodsComplexManager getGoodsComplexManager() {
/* 69 */     return this.goodsComplexManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsComplexManager(IGoodsComplexManager goodsComplexManager) {
/* 73 */     this.goodsComplexManager = goodsComplexManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.complex.GoodsComplexWidget
 * JD-Core Version:    0.6.0
 */