/*     */ package com.enation.app.shop.core.service.impl.cache;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MemberLvCacheProxy extends AbstractCacheProxy<List<MemberLv>>
/*     */   implements IMemberLvManager
/*     */ {
/*     */   private IMemberLvManager memberLvManager;
/*     */   public static final String CACHE_KEY = "member_lv";
/*     */ 
/*     */   public MemberLvCacheProxy(IMemberLvManager memberLvManager)
/*     */   {
/*  24 */     super("member_lv");
/*  25 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   private void cleanCache() {
/*  29 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  30 */     this.cache.remove("member_lv_" + site.getUserid() + "_" + site.getId() + "_0");
/*     */   }
/*     */ 
/*     */   public void add(MemberLv lv) {
/*  34 */     this.memberLvManager.add(lv);
/*  35 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void edit(MemberLv lv)
/*     */   {
/*  40 */     this.memberLvManager.edit(lv);
/*  41 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public Integer getDefaultLv()
/*     */   {
/*  46 */     return this.memberLvManager.getDefaultLv();
/*     */   }
/*     */ 
/*     */   public Page list(String order, int page, int pageSize)
/*     */   {
/*  51 */     return this.memberLvManager.list(order, page, pageSize);
/*     */   }
/*     */ 
/*     */   public MemberLv get(Integer lvId)
/*     */   {
/*  56 */     return this.memberLvManager.get(lvId);
/*     */   }
/*     */ 
/*     */   public void delete(String id)
/*     */   {
/*  61 */     this.memberLvManager.delete(id);
/*  62 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public List<MemberLv> list()
/*     */   {
/*  67 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  68 */     List memberLvList = (List)this.cache.get("member_lv_" + site.getUserid() + "_" + site.getId());
/*  69 */     if (memberLvList == null) {
/*  70 */       memberLvList = this.memberLvManager.list();
/*  71 */       this.cache.put("member_lv_" + site.getUserid() + "_" + site.getId(), memberLvList);
/*  72 */       if (this.logger.isDebugEnabled()) {
/*  73 */         this.logger.debug("load memberLvList from database");
/*     */       }
/*     */     }
/*  76 */     else if (this.logger.isDebugEnabled()) {
/*  77 */       this.logger.debug("load memberLvList from cache");
/*     */     }
/*     */ 
/*  80 */     return memberLvList;
/*     */   }
/*     */ 
/*     */   public MemberLv getNextLv(int point) {
/*  84 */     return this.memberLvManager.getNextLv(point);
/*     */   }
/*     */ 
/*     */   public List<MemberLv> list(String ids)
/*     */   {
/*  89 */     return this.memberLvManager.list(ids);
/*     */   }
/*     */ 
/*     */   public MemberLv getByPoint(int point)
/*     */   {
/*  94 */     return this.memberLvManager.getByPoint(point);
/*     */   }
/*     */ 
/*     */   public List getCatDiscountByLv(int lv_id)
/*     */   {
/* 103 */     return this.memberLvManager.getCatDiscountByLv(lv_id);
/*     */   }
/*     */ 
/*     */   public List getHaveCatDiscountByLv(int lv_id)
/*     */   {
/* 112 */     return this.memberLvManager.getHaveCatDiscountByLv(lv_id);
/*     */   }
/*     */ 
/*     */   public void saveCatDiscountByLv(int[] cat_ids, int[] discounts, int lv_id)
/*     */   {
/* 122 */     this.memberLvManager.saveCatDiscountByLv(cat_ids, discounts, lv_id);
/*     */   }
/*     */ 
/*     */   public List getHaveLvDiscountByCat(int cat_id)
/*     */   {
/* 129 */     return this.memberLvManager.getHaveLvDiscountByCat(cat_id);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.cache.MemberLvCacheProxy
 * JD-Core Version:    0.6.0
 */