/*    */ package com.enation.app.shop.component.widget.partzone;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Article;
/*    */ import com.enation.app.shop.core.service.IArticleManager;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("partZone")
/*    */ @Scope("prototype")
/*    */ public class PartZoneWidget extends AbstractWidget
/*    */ {
/*    */   private IArticleManager articleManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/* 28 */     setPageName("partzone_comfig");
/* 29 */     List articleList = this.articleManager.listByCatId(Integer.valueOf(9999));
/* 30 */     putData("articleList", articleList);
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 36 */     setPageName("partzone");
/* 37 */     String article_id = (String)params.get("article_id");
/* 38 */     article_id = article_id == null ? "0" : article_id;
/* 39 */     Article article = this.articleManager.get(Integer.valueOf(article_id));
/* 40 */     putData("articleContent", article.getContent());
/*    */   }
/*    */ 
/*    */   public IArticleManager getArticleManager()
/*    */   {
/* 45 */     return this.articleManager;
/*    */   }
/*    */ 
/*    */   public void setArticleManager(IArticleManager articleManager) {
/* 49 */     this.articleManager = articleManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.partzone.PartZoneWidget
 * JD-Core Version:    0.6.0
 */