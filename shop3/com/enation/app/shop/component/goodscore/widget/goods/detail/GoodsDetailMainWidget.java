/*     */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*     */ 
/*     */ import com.enation.app.shop.core.plugin.cart.ICartAddEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.eop.processor.core.UrlNotFoundException;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.ObjectNotFoundException;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("goods_detail_main")
/*     */ @Scope("prototype")
/*     */ public class GoodsDetailMainWidget extends AbstractWidget
/*     */ {
/*     */   private IGoodsManager goodsManager;
/*     */   private ICartAddEvent testGroovy;
/*     */   private Map goodsMap;
/*     */   private GoodsPluginBundle goodsPluginBundle;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*     */     try
/*     */     {
/*  48 */       Integer goods_id = getGoodsId();
/*  49 */       this.goodsMap = this.goodsManager.get(goods_id);
/*     */ 
/*  54 */       if (this.goodsMap == null) {
/*  55 */         throw new UrlNotFoundException();
/*     */       }
/*     */ 
/*  60 */       if (this.goodsMap.get("market_enable").toString().equals("0")) {
/*  61 */         throw new UrlNotFoundException();
/*     */       }
/*     */ 
/*  66 */       if (this.goodsMap.get("disabled").toString().equals("1")) {
/*  67 */         throw new UrlNotFoundException();
/*     */       }
/*  69 */       if ("1".equals(params.get("shownav")))
/*  70 */         this.goodsManager.getNavdata(this.goodsMap);
/*  71 */       ThreadContextHolder.getHttpRequest().setAttribute("goods", this.goodsMap);
/*     */ 
/*  73 */       if ((this.goodsMap.get("page_title") != null) && (!this.goodsMap.get("page_title").equals("")))
/*  74 */         putData("pagetitle", this.goodsMap.get("page_title"));
/*     */       else {
/*  76 */         putData("pagetitle", this.goodsMap.get("name"));
/*     */       }
/*  78 */       if ((this.goodsMap.get("meta_keywords") != null) && (!this.goodsMap.get("meta_keywords").equals(""))) {
/*  79 */         putData("keywords", this.goodsMap.get("meta_keywords"));
/*     */       }
/*  81 */       if ((this.goodsMap.get("meta_description") != null) && (!this.goodsMap.get("meta_description").equals(""))) {
/*  82 */         putData("description", this.goodsMap.get("meta_description"));
/*     */       }
/*  84 */       putData("goods", this.goodsMap);
/*  85 */       this.freeMarkerPaser.setClz(getClass());
/*     */     }
/*     */     catch (ObjectNotFoundException e)
/*     */     {
/*  90 */       throw new UrlNotFoundException();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Integer getGoodsId() {
/*  95 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  96 */     String url = RequestUtil.getRequestUrl(httpRequest);
/*  97 */     String goods_id = paseGoodsId(url);
/*     */ 
/*  99 */     return Integer.valueOf(goods_id);
/*     */   }
/*     */ 
/*     */   private static String paseGoodsId(String url) {
/* 103 */     String pattern = "/(.*)goods-(\\d+)(.*)";
/* 104 */     String value = null;
/* 105 */     Pattern p = Pattern.compile(pattern, 34);
/* 106 */     Matcher m = p.matcher(url);
/* 107 */     if (m.find()) {
/* 108 */       value = m.replaceAll("$2");
/*     */     }
/* 110 */     return value;
/*     */   }
/*     */ 
/*     */   public void update(Map<String, String> params)
/*     */   {
/* 118 */     this.goodsPluginBundle.onVisit(this.goodsMap);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 123 */     String uri = "/yxgoods-1.html";
/* 124 */     String goodsid = paseGoodsId(uri);
/* 125 */     System.out.println(goodsid);
/*     */   }
/*     */   public IGoodsManager getGoodsManager() {
/* 128 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 132 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public ICartAddEvent getTestGroovy() {
/* 136 */     return this.testGroovy;
/*     */   }
/*     */ 
/*     */   public void setTestGroovy(ICartAddEvent testGroovy) {
/* 140 */     this.testGroovy = testGroovy;
/*     */   }
/*     */ 
/*     */   public Map getGoodsMap() {
/* 144 */     return this.goodsMap;
/*     */   }
/*     */ 
/*     */   public void setGoodsMap(Map goodsMap) {
/* 148 */     this.goodsMap = goodsMap;
/*     */   }
/*     */ 
/*     */   public GoodsPluginBundle getGoodsPluginBundle() {
/* 152 */     return this.goodsPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
/* 156 */     this.goodsPluginBundle = goodsPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsDetailMainWidget
 * JD-Core Version:    0.6.0
 */