/*     */ package com.enation.app.shop.component.widget.nav;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("detail_nav")
/*     */ @Scope("prototype")
/*     */ public class DetailNavWidget extends AbstractWidget
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  31 */     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/*  32 */     if (goods == null) throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
/*     */ 
/*  34 */     StringBuffer navHtml = new StringBuffer();
/*  35 */     int catid = ((Integer)goods.get("cat_id")).intValue();
/*  36 */     Integer brandid = (Integer)goods.get("brand_id");
/*     */ 
/*  39 */     String catname = this.goodsCatManager.getById(catid).getName();
/*  40 */     String brandname = (String)goods.get("brand_name");
/*     */ 
/*  43 */     String catstr = getCatStr(catid);
/*  44 */     String brandstr = getBrandStr(brandid);
/*     */ 
/*  46 */     navHtml.append("<span><a href=\"index.html\">首页</a></span>&gt;");
/*     */ 
/*  50 */     navHtml.append("<span><a href='");
/*  51 */     navHtml.append(catstr);
/*  52 */     navHtml.append(".html'>");
/*  53 */     navHtml.append(catname);
/*  54 */     navHtml.append("</a></span>");
/*     */ 
/*  56 */     navHtml.append("&gt;");
/*     */ 
/*  58 */     if (brandid != null)
/*     */     {
/*  60 */       navHtml.append("<span><a href='");
/*  61 */       navHtml.append(catstr);
/*  62 */       navHtml.append("-");
/*  63 */       navHtml.append(brandstr);
/*  64 */       navHtml.append(".html'>");
/*  65 */       navHtml.append(brandname);
/*  66 */       navHtml.append("</a></span>&gt;");
/*     */     }
/*     */ 
/*  69 */     navHtml.append("<span class=\"last\">");
/*  70 */     navHtml.append(goods.get("name"));
/*  71 */     navHtml.append("</span>");
/*  72 */     putData("navHtml", navHtml);
/*     */   }
/*     */ 
/*     */   private String getCatStr(int catid) {
/*  76 */     StringBuffer navHtml = new StringBuffer();
/*  77 */     navHtml.append("search-cat-");
/*  78 */     navHtml.append(catid);
/*  79 */     return navHtml.toString();
/*     */   }
/*     */ 
/*     */   private String getBrandStr(Integer brandid)
/*     */   {
/*  84 */     if (brandid == null) return "";
/*  85 */     StringBuffer navHtml = new StringBuffer();
/*  86 */     navHtml.append("brand-");
/*  87 */     navHtml.append(brandid);
/*  88 */     return navHtml.toString();
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/*  96 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 100 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.nav.DetailNavWidget
 * JD-Core Version:    0.6.0
 */