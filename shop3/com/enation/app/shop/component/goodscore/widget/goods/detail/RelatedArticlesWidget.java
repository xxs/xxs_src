/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RelatedArticlesWidget extends AbstractGoodsDetailWidget
/*    */ {
/*    */   private IDaoSupport basedDaoSupport;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void execute(Map<String, String> params, Map goods)
/*    */   {
/* 24 */     String sql = "select * from goods_articles where goodsid=?";
/* 25 */     List articleList = this.baseDaoSupport.queryForList(sql, new Object[] { goods.get("goods_id") });
/* 26 */     putData("articleList", articleList);
/*    */   }
/*    */ 
/*    */   public IDaoSupport getBasedDaoSupport() {
/* 30 */     return this.basedDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBasedDaoSupport(IDaoSupport basedDaoSupport) {
/* 34 */     this.basedDaoSupport = basedDaoSupport;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.RelatedArticlesWidget
 * JD-Core Version:    0.6.0
 */