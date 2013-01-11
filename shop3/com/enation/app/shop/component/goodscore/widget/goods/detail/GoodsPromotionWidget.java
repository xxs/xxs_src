/*    */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.app.shop.core.service.IPromotionManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GoodsPromotionWidget extends AbstractGoodsDetailWidget
/*    */ {
/*    */   private IPromotionManager promotionManager;
/*    */ 
/*    */   protected void execute(Map<String, String> params, Map goods)
/*    */   {
/* 23 */     IUserService userService = UserServiceFactory.getUserService();
/* 24 */     Member member = userService.getCurrentMember();
/* 25 */     if (member == null) {
/* 26 */       this.showHtml = false; return;
/*    */     }
/*    */ 
/* 30 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 31 */     List pmtList = this.promotionManager.list(goodsid, member.getLv_id());
/* 32 */     if ((pmtList == null) || (pmtList.size() == 0)) {
/* 33 */       this.showHtml = false; return;
/*    */     }
/* 35 */     putData("pmtList", pmtList);
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IPromotionManager getPromotionManager()
/*    */   {
/* 45 */     return this.promotionManager;
/*    */   }
/*    */ 
/*    */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 49 */     this.promotionManager = promotionManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.GoodsPromotionWidget
 * JD-Core Version:    0.6.0
 */