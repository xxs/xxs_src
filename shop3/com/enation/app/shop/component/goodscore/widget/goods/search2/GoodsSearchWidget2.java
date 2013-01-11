/*    */ package com.enation.app.shop.component.goodscore.widget.goods.search2;
/*    */ 
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.search2.seo.SearchSeoParser;
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.service.IGoodsSearchManager2;
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_search2")
/*    */ @Scope("prototype")
/*    */ public class GoodsSearchWidget2 extends AbstractWidget
/*    */ {
/*    */   private IGoodsSearchManager2 goodsSearchManager2;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 38 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 39 */     String uri = request.getServletPath();
/*    */ 
/* 42 */     Cat cat = null;
/*    */ 
/* 45 */     cat = this.goodsSearchManager2.getCat(uri);
/*    */ 
/* 48 */     String pagesizes = (String)params.get("pagesize");
/* 49 */     Integer pageSize = Integer.valueOf(pagesizes == null ? 20 : Integer.valueOf(pagesizes).intValue());
/*    */ 
/* 52 */     String page_str = UrlUtils.getParamStringValue(uri, "page");
/* 53 */     int page = 1;
/* 54 */     if ((page_str != null) && (!page_str.equals(""))) {
/* 55 */       page = Integer.valueOf(page_str).intValue();
/*    */     }
/*    */ 
/* 58 */     Page webpage = this.goodsSearchManager2.search(page, pageSize.intValue(), uri);
/* 59 */     Map selectorMap = this.goodsSearchManager2.getSelector(uri);
/*    */ 
/* 61 */     String tag = UrlUtils.getParamStringValue(uri, "tag");
/*    */ 
/* 65 */     SearchSeoParser seoParser = new SearchSeoParser(selectorMap, cat, tag);
/* 66 */     seoParser.parse();
/* 67 */     putData("pagetitle", seoParser.getTitle());
/* 68 */     putData("keywords", seoParser.getKeywords());
/* 69 */     putData("description", seoParser.getDescription());
/*    */ 
/* 71 */     Map pluginParams = new HashMap();
/* 72 */     this.goodsSearchManager2.putParams(pluginParams, uri);
/*    */ 
/* 74 */     putData("uri", uri);
/* 75 */     putData(pluginParams);
/* 76 */     putData("goodsSelector", selectorMap);
/* 77 */     putData("webpage", webpage);
/* 78 */     putData("pageno", Integer.valueOf(page));
/* 79 */     putData("pagesize", pageSize);
/* 80 */     putData("total", Long.valueOf(webpage.getTotalCount()));
/*    */   }
/*    */ 
/*    */   public IGoodsSearchManager2 getGoodsSearchManager2() {
/* 84 */     return this.goodsSearchManager2;
/*    */   }
/*    */ 
/*    */   public void setGoodsSearchManager2(IGoodsSearchManager2 goodsSearchManager2) {
/* 88 */     this.goodsSearchManager2 = goodsSearchManager2;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search2.GoodsSearchWidget2
 * JD-Core Version:    0.6.0
 */