/*     */ package com.enation.app.shop.component.promotion.plugin;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.app.shop.core.plugin.cart.ICartItemFilter;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class DiscountCartPricePlugin extends AutoRegisterPlugin
/*     */   implements ICartItemFilter
/*     */ {
/*     */   private IPromotionManager promotionManager;
/*     */   private IMemberPriceManager memberPriceManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IGoodsManager goodsManager;
/*     */ 
/*     */   public void filter(List<CartItem> list, String sessionid)
/*     */   {
/*  44 */     IUserService userService = UserServiceFactory.getUserService();
/*  45 */     Member member = userService.getCurrentMember();
/*  46 */     List memPriceList = new ArrayList();
/*  47 */     double discount = 1.0D;
/*     */     List discountList;
/*  48 */     if ((member != null) && (member.getLv_id() != null)) {
/*  49 */       this.promotionManager.applyGoodsPmt(list, member.getLv_id());
/*  50 */       memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
/*  51 */       MemberLv lv = this.memberLvManager.get(member.getLv_id());
/*  52 */       discount = lv.getDiscount().intValue() / 100.0D;
/*     */ 
/*  57 */       discountList = this.memberLvManager.getCatDiscountByLv(member.getLv_id().intValue());
/*  58 */       double framePrice = 0.0D;
/*  59 */       for (CartItem item : list)
/*     */       {
/*  61 */         double oldprice = item.getPrice().doubleValue();
/*  62 */         double price = CurrencyUtil.mul(item.getPrice().doubleValue(), discount).doubleValue();
/*  63 */         if (((item.getIstejia() != null) && (item.getIstejia().intValue() == 1)) || (
/*  66 */           (item.getNo_discount() != null) && (item.getNo_discount().intValue() == 1))) {
/*     */           continue;
/*     */         }
/*  69 */         double memPrice = getMemberPrice(memPriceList, item.getGoods_id().intValue());
/*  70 */         if (memPrice != 0.0D)
/*     */         {
/*  74 */           item.setCoupPrice(Double.valueOf(memPrice));
/*  75 */           continue;
/*     */         }
/*  77 */         item.setCoupPrice(Double.valueOf(price));
/*     */ 
/*  79 */         int zhekou = getCatDicount(discountList, item.getCatid());
/*  80 */         if (zhekou > 0)
/*     */         {
/*  82 */           discount = zhekou / 100.0D;
/*  83 */           item.setCoupPrice(CurrencyUtil.mul(item.getPrice().doubleValue(), discount));
/*  84 */           continue;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private double getMemberPrice(List<GoodsLvPrice> memPriceList, int goodsId)
/*     */   {
/* 100 */     for (GoodsLvPrice lvPrice : memPriceList) {
/* 101 */       if (goodsId == lvPrice.getGoodsid()) {
/* 102 */         return lvPrice.getPrice().doubleValue();
/*     */       }
/*     */     }
/* 105 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   private int getCatDicount(List discountList, int catId)
/*     */   {
/* 114 */     for (int i = 0; i < discountList.size(); i++) {
/* 115 */       Map map = (Map)discountList.get(i);
/* 116 */       Integer cat_id = (Integer)map.get("cat_id");
/* 117 */       Integer discount = (Integer)map.get("discount");
/* 118 */       if (cat_id.intValue() == catId) {
/* 119 */         return discount.intValue();
/*     */       }
/*     */     }
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */   private void applyMemPrice(List<CartItem> itemList, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 132 */     for (CartItem item : itemList) {
/* 133 */       double oldprice = item.getPrice().doubleValue();
/* 134 */       double price = CurrencyUtil.mul(item.getPrice().doubleValue(), discount).doubleValue();
/* 135 */       for (GoodsLvPrice lvPrice : memPriceList) {
/* 136 */         if (item.getProduct_id().intValue() == lvPrice.getProductid()) {
/* 137 */           price = lvPrice.getPrice().doubleValue();
/*     */         }
/*     */       }
/*     */ 
/* 141 */       item.setPrice(Double.valueOf(oldprice));
/* 142 */       item.setCoupPrice(Double.valueOf(price));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager()
/*     */   {
/* 148 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 152 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager() {
/* 156 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
/* 160 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 164 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 168 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager()
/*     */   {
/* 174 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 178 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.promotion.plugin.DiscountCartPricePlugin
 * JD-Core Version:    0.6.0
 */