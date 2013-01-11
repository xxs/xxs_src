/*     */ package com.enation.app.shop.component.goodscore.widget.goods.list;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.mapper.GoodsListMapper;
/*     */ import com.enation.app.shop.core.model.support.GoodsView;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.StringMapper;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsDataProvider extends BaseSupport
/*     */ {
/*     */   private IMemberPriceManager memberPriceManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IPromotionManager promotionManager;
/*     */   private IGoodsCatManager goodsCatManager;
/*  39 */   private static Map<Integer, String> orderMap = new HashMap();
/*     */ 
/*     */   public List list(Term term, int num)
/*     */   {
/*  52 */     StringBuffer sql = new StringBuffer();
/*  53 */     sql.append("select * from goods where disabled=0 and market_enable=1 and goods_type='normal'");
/*  54 */     String tagids = term.getTagid();
/*  55 */     String brandids = term.getBrandid();
/*  56 */     String catids = term.getCatid();
/*  57 */     Integer typeid = term.getTypeid();
/*  58 */     String keyword = term.getKeyword();
/*     */ 
/*  60 */     if (!StringUtil.isEmpty(keyword)) {
/*  61 */       sql.append(" and (name like '%" + keyword + "%' or brief like '%" + keyword + "%' or intro like '%" + keyword + "%'   )");
/*     */     }
/*     */ 
/*  65 */     if (typeid != null) {
/*  66 */       sql.append(" and type_id =" + typeid);
/*     */     }
/*     */ 
/*  69 */     if (!StringUtil.isEmpty(brandids)) {
/*  70 */       sql.append(" and brand_id in(" + brandids + ")");
/*     */     }
/*     */ 
/*  73 */     if (!StringUtil.isEmpty(catids)) {
/*  74 */       String str_catids = "0";
/*  75 */       String[] ids = StringUtils.split(catids, ",");
/*  76 */       for (String cat_id : ids) {
/*  77 */         Cat cat = this.goodsCatManager.getById(Integer.valueOf(cat_id).intValue());
/*  78 */         List catIdList = this.baseDaoSupport.queryForList("select cat_id from goods_cat where cat_path like '" + cat.getCat_path() + "%'", new StringMapper(), new Object[0]);
/*  79 */         str_catids = str_catids + "," + StringUtil.listToString(catIdList, ",");
/*     */       }
/*     */ 
/*  82 */       sql.append(" and cat_id in(" + str_catids + ")");
/*     */     }
/*     */ 
/*  86 */     if (!StringUtil.isEmpty(tagids)) {
/*  87 */       String filter = goodsIdInTags(tagids);
/*  88 */       filter = filter.equals("") ? "-1" : filter;
/*  89 */       sql.append(" and goods_id in(" + filter + ")");
/*     */     }
/*     */ 
/*  92 */     if (term.getMaxprice() != null) {
/*  93 */       sql.append(" and price<=" + term.getMaxprice());
/*     */     }
/*     */ 
/*  96 */     if (term.getMinprice() != null) {
/*  97 */       sql.append(" and price>=" + term.getMinprice());
/*     */     }
/*     */ 
/* 100 */     Integer order = term.getOrder();
/* 101 */     if (order == null) {
/* 102 */       order = Integer.valueOf(1);
/*     */     }
/*     */ 
/* 105 */     sql.append(" order by " + (String)orderMap.get(order));
/*     */ 
/* 107 */     List list = this.baseDaoSupport.queryForList(sql.toString(), 1, num, new GoodsListMapper());
/* 108 */     if ((list != null) && (!list.isEmpty())) {
/* 109 */       ((GoodsView)list.get(list.size() - 1)).setIsLast(true);
/* 110 */       ((GoodsView)list.get(0)).setIsFirst(true);
/*     */     }
/* 112 */     list = list == null ? new ArrayList() : list;
/*     */ 
/* 116 */     IUserService userService = UserServiceFactory.getUserService();
/* 117 */     Member member = userService.getCurrentMember();
/* 118 */     List memPriceList = new ArrayList();
/* 119 */     double discount = 1.0D;
/* 120 */     if ((member != null) && (member.getLv_id() != null))
/*     */     {
/* 122 */       memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
/* 123 */       MemberLv lv = this.memberLvManager.get(member.getLv_id());
/* 124 */       discount = lv.getDiscount().intValue() / 100.0D;
/* 125 */       applyMemPrice(list, memPriceList, discount);
/*     */     }
/*     */ 
/* 128 */     return list;
/*     */   }
/*     */ 
/*     */   private void applyMemPrice(List<GoodsView> itemList, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 139 */     for (GoodsView item : itemList) {
/* 140 */       double price = item.getPrice().doubleValue() * discount;
/* 141 */       for (GoodsLvPrice lvPrice : memPriceList) {
/* 142 */         if ((item.getHasSpec() == 0) && (item.getGoods_id().intValue() == lvPrice.getGoodsid())) {
/* 143 */           price = lvPrice.getPrice().doubleValue();
/*     */         }
/*     */       }
/*     */ 
/* 147 */       item.setPrice(Double.valueOf(price));
/*     */     }
/*     */   }
/*     */ 
/*     */   private String goodsIdInTags(String tags)
/*     */   {
/* 170 */     String sql = "select rel_id from tag_rel where tag_id in (" + tags + ")";
/* 171 */     List goodsIdList = this.baseDaoSupport.queryForList(sql, new StringMapper(), new Object[0]);
/* 172 */     return StringUtil.listToString(goodsIdList, ",");
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 177 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 181 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager()
/*     */   {
/* 186 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
/* 190 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 194 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 198 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager()
/*     */   {
/* 203 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager)
/*     */   {
/* 208 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  40 */     orderMap.put(Integer.valueOf(0), " goods_id desc");
/*  41 */     orderMap.put(Integer.valueOf(1), " last_modify desc");
/*  42 */     orderMap.put(Integer.valueOf(2), " last_modify asc");
/*  43 */     orderMap.put(Integer.valueOf(3), " price desc");
/*  44 */     orderMap.put(Integer.valueOf(4), " view_count desc");
/*  45 */     orderMap.put(Integer.valueOf(5), " buy_count desc");
/*  46 */     orderMap.put(Integer.valueOf(6), " price asc");
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.list.GoodsDataProvider
 * JD-Core Version:    0.6.0
 */