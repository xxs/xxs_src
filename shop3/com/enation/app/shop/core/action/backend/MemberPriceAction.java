/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MemberPriceAction extends WWAction
/*     */ {
/*     */   private Double price;
/*     */   private int goodsid;
/*     */   private int productid;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IMemberPriceManager memberPriceManager;
/*     */   private List<MemberLv> lvList;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  26 */     processLv();
/*  27 */     return "list";
/*     */   }
/*     */ 
/*     */   public String disLvInput()
/*     */   {
/*  35 */     processLv();
/*  36 */     return "dis_lv_input";
/*     */   }
/*     */ 
/*     */   public String getLvPriceJson()
/*     */   {
/*     */     try {
/*  42 */       List priceList = this.memberPriceManager.listPriceByGid(this.goodsid);
/*  43 */       JSONArray jsonArray = JSONArray.fromObject(priceList);
/*  44 */       this.json = ("{result:1,priceData:" + jsonArray.toString() + "}");
/*     */     } catch (RuntimeException e) {
/*  46 */       this.logger.error(e.getMessage(), e.fillInStackTrace());
/*  47 */       this.json = ("{result:0,message:" + e.getMessage() + "}");
/*     */     }
/*  49 */     return "json_message";
/*     */   }
/*     */ 
/*     */   private void processLv() {
/*  53 */     this.lvList = this.memberLvManager.list();
/*  54 */     this.price = Double.valueOf(this.price == null ? 0.0D : this.price.doubleValue());
/*  55 */     for (MemberLv lv : this.lvList) {
/*  56 */       double discount = lv.getDiscount().intValue() / 100.0D;
/*     */ 
/*  58 */       double lvprice = CurrencyUtil.mul(this.price.doubleValue(), discount).doubleValue();
/*  59 */       lv.setLvPrice(Double.valueOf(lvprice));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Double getPrice()
/*     */   {
/*  66 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double price) {
/*  70 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public int getGoodsid() {
/*  74 */     return this.goodsid;
/*     */   }
/*     */ 
/*     */   public void setGoodsid(int goodsid) {
/*  78 */     this.goodsid = goodsid;
/*     */   }
/*     */ 
/*     */   public int getProductid() {
/*  82 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(int productid) {
/*  86 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/*  90 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/*  94 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public List<MemberLv> getLvList() {
/*  98 */     return this.lvList;
/*     */   }
/*     */ 
/*     */   public void setLvList(List<MemberLv> lvList) {
/* 102 */     this.lvList = lvList;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager() {
/* 106 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
/* 110 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.MemberPriceAction
 * JD-Core Version:    0.6.0
 */