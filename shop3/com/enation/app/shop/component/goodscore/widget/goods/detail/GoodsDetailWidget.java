/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.ObjectNotFoundException;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_detail")
/*    */ @Scope("prototype")
/*    */ public class GoodsDetailWidget extends AbstractWidget
/*    */ {
/*    */   protected void display(Map<String, String> params)
/*    */   {
/*    */     try
/*    */     {
/* 36 */       Map goodsMap = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/*    */ 
/* 38 */       if ((goodsMap.get("page_title") != null) && (!goodsMap.get("page_title").equals("")))
/* 39 */         putData("pagetitle", goodsMap.get("page_title"));
/*    */       else {
/* 41 */         putData("pagetitle", goodsMap.get("name"));
/*    */       }
/* 43 */       if ((goodsMap.get("meta_keywords") != null) && (!goodsMap.get("meta_keywords").equals(""))) {
/* 44 */         putData("keywords", goodsMap.get("meta_keywords"));
/*    */       }
/* 46 */       if ((goodsMap.get("meta_description") != null) && (!goodsMap.get("meta_description").equals(""))) {
/* 47 */         putData("description", goodsMap.get("meta_description"));
/*    */       }
/*    */     }
/*    */     catch (ObjectNotFoundException e)
/*    */     {
/* 52 */       setPageName("notfound");
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params) {
/* 57 */     this.showHtml = false;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsDetailWidget
 * JD-Core Version:    0.6.0
 */