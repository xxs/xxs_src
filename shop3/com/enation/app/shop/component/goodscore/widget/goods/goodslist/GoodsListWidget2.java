/*    */ package com.enation.app.shop.component.goodscore.widget.goods.goodslist;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.model.mapper.GoodsListMapper;
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goodslist2")
/*    */ @Scope("prototype")
/*    */ public class GoodsListWidget2 extends AbstractWidget
/*    */ {
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 33 */     String catid = (String)params.get("catid");
/* 34 */     String tagid = (String)params.get("tagid");
/* 35 */     String goodsnum = (String)params.get("goodsnum");
/*    */ 
/* 37 */     if ((catid == null) || (catid.equals(""))) {
/* 38 */       String uri = ThreadContextHolder.getHttpRequest().getServletPath();
/* 39 */       catid = UrlUtils.getParamStringValue(uri, "cat");
/*    */     }
/*    */ 
/* 42 */     if ((catid == null) || (catid.equals("")) || (catid.equals("0"))) {
/* 43 */       Map goodsMap = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/* 44 */       if ((goodsMap != null) && (goodsMap.get("cat_id") != null)) {
/* 45 */         catid = goodsMap.get("cat_id").toString();
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 50 */     List goodsList = listGoods(catid, tagid, goodsnum);
/* 51 */     putData("goodsList", goodsList);
/*    */   }
/*    */ 
/*    */   private List listGoods(String catid, String tagid, String goodsnum)
/*    */   {
/* 59 */     int num = 10;
/* 60 */     if (!StringUtil.isEmpty(goodsnum)) {
/* 61 */       num = Integer.valueOf(goodsnum).intValue();
/*    */     }
/*    */ 
/* 64 */     StringBuffer sql = new StringBuffer();
/* 65 */     sql.append("select g.* from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
/*    */ 
/* 67 */     if (!StringUtil.isEmpty(catid)) {
/* 68 */       Cat cat = this.goodsCatManager.getById(Integer.valueOf(catid).intValue());
/* 69 */       String cat_path = cat.getCat_path();
/* 70 */       if (cat_path != null) {
/* 71 */         sql.append(" and  g.cat_id in(");
/* 72 */         sql.append("select c.cat_id from " + getTableName("goods_cat") + " ");
/* 73 */         sql.append(" c where c.cat_path like '" + cat_path + "%')");
/*    */       }
/*    */     }
/*    */ 
/* 77 */     if (!StringUtil.isEmpty(tagid)) {
/* 78 */       sql.append(" AND r.tag_id=" + tagid + "");
/*    */     }
/*    */ 
/* 81 */     sql.append(" order by r.ordernum desc");
/*    */ 
/* 83 */     List list = this.daoSupport.queryForList(sql.toString(), 1, num, new GoodsListMapper());
/*    */ 
/* 85 */     return list;
/*    */   }
/*    */ 
/*    */   public IGoodsCatManager getGoodsCatManager() {
/* 89 */     return this.goodsCatManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 93 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.goodslist.GoodsListWidget2
 * JD-Core Version:    0.6.0
 */