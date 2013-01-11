/*     */ package com.enation.app.shop.component.goodscore.widget.goods.detail;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("vip_price")
/*     */ @Scope("prototype")
/*     */ public class VipPriceWidget extends AbstractGoodsDetailWidget
/*     */ {
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IMemberPriceManager memberPriceManager;
/*     */ 
/*     */   public void execute(Map<String, String> params, Map goods)
/*     */   {
/*  34 */     double price = Double.valueOf("" + goods.get("price")).doubleValue();
/*  35 */     double vipprice = price;
/*  36 */     if (price == 0.0D) {
/*  37 */       goods.put("vipprice", Integer.valueOf(0));
/*     */     }
/*     */ 
/*  40 */     List memberLvList = this.memberLvManager.list();
/*  41 */     if ((memberLvList != null) && (memberLvList.size() > 0))
/*     */     {
/*  44 */       List glpList = this.memberPriceManager.listPriceByGid(Integer.valueOf(goods.get("goods_id").toString()).intValue());
/*  45 */       if ((glpList != null) && (glpList.size() > 0))
/*     */       {
/*  47 */         double discount = 1.0D;
/*  48 */         for (MemberLv lv : memberLvList) {
/*  49 */           double lvprice1 = 0.0D;
/*  50 */           if (lv.getDiscount() != null) {
/*  51 */             discount = lv.getDiscount().intValue() / 100.0D;
/*  52 */             lvprice1 = CurrencyUtil.mul(price, discount).doubleValue();
/*     */           }
/*  54 */           double lvPrice = getMemberPrice(lv.getLv_id().intValue(), glpList);
/*     */ 
/*  56 */           if (lvPrice == 0.0D) {
/*  57 */             lv.setLvPrice(Double.valueOf(lvprice1));
/*  58 */             lvPrice = lvprice1;
/*     */           } else {
/*  60 */             lv.setLvPrice(Double.valueOf(lvPrice));
/*     */           }
/*  62 */           if (vipprice > lvPrice) {
/*  63 */             vipprice = lvPrice;
/*     */           }
/*     */         }
/*     */ 
/*  67 */         goods.put("vipprice", Double.valueOf(vipprice));
/*  68 */         putData("vipPriceList", memberLvList);
/*     */       }
/*     */       else {
/*  71 */         double discount = 1.0D;
/*  72 */         for (MemberLv lv : memberLvList)
/*     */         {
/*  74 */           if (lv.getDiscount() != null) {
/*  75 */             discount = lv.getDiscount().intValue() / 100.0D;
/*  76 */             double lvprice = CurrencyUtil.mul(price, discount).doubleValue();
/*  77 */             lv.setLvPrice(Double.valueOf(lvprice));
/*  78 */             if (vipprice > lvprice) {
/*  79 */               vipprice = lvprice;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  86 */         goods.put("vipprice", Double.valueOf(vipprice));
/*  87 */         putData("vipPriceList", memberLvList);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getLvDiscount(int lv_id, List list)
/*     */   {
/* 176 */     for (int i = 0; i < list.size(); i++) {
/* 177 */       Map map = (Map)list.get(i);
/* 178 */       Integer lvid = (Integer)map.get("lv_id");
/* 179 */       Integer discount = (Integer)map.get("discount");
/* 180 */       if (lvid.intValue() == lv_id) {
/* 181 */         return discount.intValue();
/*     */       }
/*     */     }
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */   private double getMemberPrice(int lv_id, List<GoodsLvPrice> memPriceList)
/*     */   {
/* 194 */     for (GoodsLvPrice lvPrice : memPriceList) {
/* 195 */       if (lv_id == lvPrice.getLvid()) {
/* 196 */         return lvPrice.getPrice().doubleValue();
/*     */       }
/*     */     }
/*     */ 
/* 200 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 209 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager() {
/* 213 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
/* 217 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.detail.VipPriceWidget
 * JD-Core Version:    0.6.0
 */