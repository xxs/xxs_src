/*    */ package com.enation.app.shop.component.widget.brand;
/*    */ 
/*    */ import com.enation.app.base.component.widget.nav.Nav;
/*    */ import com.enation.app.shop.core.model.Brand;
/*    */ import com.enation.app.shop.core.service.IBrandManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("brand_detail")
/*    */ @Scope("prototype")
/*    */ public class BrandDetailWidget extends AbstractWidget
/*    */ {
/*    */   private IBrandManager brandManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 39 */     setPageName("brandDetail");
/* 40 */     Integer brand_id = getBrandId();
/* 41 */     Brand brand = this.brandManager.get(brand_id);
/* 42 */     putData("brand", brand);
/* 43 */     Nav nav = new Nav();
/* 44 */     nav.setTitle("品牌专区");
/* 45 */     nav.setLink("brand.html");
/* 46 */     nav.setTips("品牌专区");
/* 47 */     putNav(nav);
/*    */ 
/* 49 */     Nav nav2 = new Nav();
/* 50 */     nav2.setTitle(brand.getName());
/* 51 */     putNav(nav2);
/*    */   }
/*    */ 
/*    */   private Integer getBrandId() {
/* 55 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/* 56 */     String url = RequestUtil.getRequestUrl(httpRequest);
/*    */ 
/* 58 */     if (url.startsWith("/widget")) return Integer.valueOf(0);
/*    */ 
/* 60 */     String goods_id = paseBrandId(url);
/*    */ 
/* 62 */     return Integer.valueOf(goods_id);
/*    */   }
/*    */ 
/*    */   private static String paseBrandId(String url) {
/* 66 */     String pattern = "/(.*)-(\\d+).html(.*)";
/* 67 */     String value = null;
/* 68 */     Pattern p = Pattern.compile(pattern, 34);
/* 69 */     Matcher m = p.matcher(url);
/* 70 */     if (m.find()) {
/* 71 */       value = m.replaceAll("$2");
/*    */     }
/* 73 */     return value;
/*    */   }
/*    */ 
/*    */   public IBrandManager getBrandManager() {
/* 77 */     return this.brandManager;
/*    */   }
/*    */ 
/*    */   public void setBrandManager(IBrandManager brandManager) {
/* 81 */     this.brandManager = brandManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.brand.BrandDetailWidget
 * JD-Core Version:    0.6.0
 */