/*     */ package com.enation.app.shop.component.widget.gift;
/*     */ 
/*     */ import com.enation.app.shop.core.model.FreeOffer;
/*     */ import com.enation.app.shop.core.model.FreeOfferCategory;
/*     */ import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class GiftWidget extends AbstractWidget
/*     */ {
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private IFreeOfferCategoryManager freeOfferCategoryManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private HttpServletRequest request;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  40 */     this.request = ThreadContextHolder.getHttpRequest();
/*  41 */     String showtype = (String)params.get("showtype");
/*  42 */     if ("list".equals(showtype)) {
/*  43 */       list();
/*     */     }
/*     */ 
/*  46 */     if ("detail".equals(showtype))
/*  47 */       detail();
/*     */   }
/*     */ 
/*     */   private void detail()
/*     */   {
/*  53 */     String giftid = paseGiftId(this.request.getServletPath());
/*  54 */     FreeOffer gift = this.freeOfferManager.get(Integer.valueOf(giftid).intValue());
/*  55 */     FreeOfferCategory cat = this.freeOfferCategoryManager.get(gift.getFo_category_id().intValue());
/*  56 */     gift.setCat_name(cat.getCat_name());
/*  57 */     List lvList = this.memberLvManager.list(gift.getLv_ids());
/*  58 */     putData("lvList", lvList);
/*  59 */     putData("gift", gift);
/*  60 */     setPageName("GiftDetail");
/*  61 */     putData("pagetitle", gift.getFo_name());
/*     */   }
/*     */ 
/*     */   private void list()
/*     */   {
/*  66 */     Page page = this.freeOfferManager.list(1, 20);
/*  67 */     putData("webpage", page);
/*  68 */     setPageName("GiftList");
/*     */   }
/*     */ 
/*     */   private String paseGiftId(String url)
/*     */   {
/*  73 */     String pattern = "/gift-(\\d+).html";
/*  74 */     String value = null;
/*  75 */     Pattern p = Pattern.compile(pattern, 34);
/*  76 */     Matcher m = p.matcher(url);
/*  77 */     if (m.find()) {
/*  78 */       value = m.replaceAll("$1");
/*     */     }
/*  80 */     return value;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager() {
/*  84 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/*  88 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
/*  92 */     return this.freeOfferCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferCategoryManager(IFreeOfferCategoryManager freeOfferCategoryManager)
/*     */   {
/*  97 */     this.freeOfferCategoryManager = freeOfferCategoryManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 101 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 105 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.gift.GiftWidget
 * JD-Core Version:    0.6.0
 */