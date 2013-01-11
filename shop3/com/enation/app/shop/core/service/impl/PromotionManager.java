/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Promotion;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.app.shop.core.model.support.DiscountPrice;
/*     */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*     */ import com.enation.app.shop.core.plugin.promotion.PromotionPluginBundle;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.app.shop.core.service.promotion.IDiscountBehavior;
/*     */ import com.enation.app.shop.core.service.promotion.IGiveGiftBehavior;
/*     */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*     */ import com.enation.app.shop.core.service.promotion.IReduceFreightBehavior;
/*     */ import com.enation.app.shop.core.service.promotion.IReducePriceBehavior;
/*     */ import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.IntegerMapper;
/*     */ import com.enation.framework.database.ObjectNotFoundException;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class PromotionManager extends BaseSupport
/*     */   implements IPromotionManager
/*     */ {
/*     */   private PromotionPluginBundle promotionPluginBundle;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer add(Promotion promotion, Integer[] memberLvIdArray, Integer[] goodsIdArray)
/*     */   {
/*  43 */     if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
/*  44 */     if (promotion.getPmt_describe() == null) throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
/*  45 */     if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
/*  46 */     if (promotion.getPmt_time_begin() == null) throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
/*  47 */     if (promotion.getPmt_time_end() == null) throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");
/*     */ 
/*  51 */     promotion.setPmt_update_time(Long.valueOf(new Date().getTime()));
/*  52 */     promotion.setDisabled("false");
/*  53 */     IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());
/*  54 */     promotion.setPmt_basic_type(plugin.getType());
/*     */ 
/*  56 */     this.baseDaoSupport.insert("promotion", promotion);
/*  57 */     Integer pmtid = Integer.valueOf(this.baseDaoSupport.getLastId("promotion"));
/*  58 */     promotion.setPmt_id(pmtid.intValue());
/*     */ 
/*  62 */     if (memberLvIdArray != null) {
/*  63 */       saveMemberLv(pmtid, memberLvIdArray);
/*     */     }
/*     */ 
/*  66 */     if (goodsIdArray != null) {
/*  67 */       saveGoods(pmtid, goodsIdArray);
/*     */     }
/*     */ 
/*  70 */     pluginSave(promotion);
/*  71 */     return pmtid;
/*     */   }
/*     */ 
/*     */   public Integer add(Promotion promotion, Integer[] memberLvIdArray, int type, Integer[] goodsIdArray, Integer[] goodsCatIdArray, Integer[] tagIdArray) {
/*  75 */     Integer result = null;
/*  76 */     if (type == 1) {
/*  77 */       result = add(promotion, memberLvIdArray, goodsIdArray);
/*     */     }
/*  79 */     if (type == 2);
/*  80 */     return result;
/*     */   }
/*     */ 
/*     */   public void edit(Promotion promotion, Integer[] memberLvIdArray, Integer[] goodsIdArray)
/*     */   {
/*  86 */     if (promotion == null) throw new IllegalArgumentException("param promotion is NULL");
/*  87 */     if (promotion.getPmt_describe() == null) throw new IllegalArgumentException("param promotion 's Pmt_describe is NULL");
/*  88 */     if (promotion.getPmts_id() == null) throw new IllegalArgumentException("param promotion 's Pmts_id is NULL");
/*  89 */     if (promotion.getPmt_time_begin() == null) throw new IllegalArgumentException("param promotion 's pmt_time_begin is NULL");
/*  90 */     if (promotion.getPmt_time_end() == null) throw new IllegalArgumentException("param promotion 's Pmt_time_end is NULL");
/*     */ 
/*  92 */     promotion.setPmt_update_time(Long.valueOf(new Date().getTime()));
/*  93 */     promotion.setDisabled("false");
/*  94 */     IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());
/*  95 */     promotion.setPmt_basic_type(plugin.getType());
/*  96 */     Integer pmtid = Integer.valueOf(promotion.getPmt_id());
/*  97 */     this.baseDaoSupport.update("promotion", promotion, "pmt_id=" + pmtid);
/*  98 */     promotion.setPmt_id(pmtid.intValue());
/*     */ 
/* 102 */     if (memberLvIdArray != null) {
/* 103 */       this.baseDaoSupport.execute("delete from pmt_member_lv where pmt_id=?", new Object[] { pmtid });
/* 104 */       saveMemberLv(pmtid, memberLvIdArray);
/*     */     }
/*     */ 
/* 107 */     if (goodsIdArray != null) {
/* 108 */       this.baseDaoSupport.execute("delete from pmt_goods where pmt_id=?", new Object[] { pmtid });
/* 109 */       saveGoods(pmtid, goodsIdArray);
/*     */     }
/*     */ 
/* 112 */     pluginSave(promotion);
/*     */   }
/*     */ 
/*     */   public List<Promotion> list(Integer goodsid, Integer memberLvId)
/*     */   {
/* 119 */     if (goodsid == null) throw new IllegalArgumentException("goodsid is NULL");
/* 120 */     if (memberLvId == null) throw new IllegalArgumentException("memberLvId is NULL");
/* 121 */     long now = System.currentTimeMillis();
/* 122 */     String sql = "select * from  " + getTableName("promotion") + " where pmt_basic_type='goods'" + " and  pmt_time_begin<" + now + " and  pmt_time_end>" + now + " and pmt_id in  (select pmt_id from " + getTableName("pmt_goods") + " where goods_id=?)" + " and pmt_id in (select pmt_id from " + getTableName("pmt_member_lv") + " where lv_id =? )" + " and pmt_type='0' ";
/*     */ 
/* 135 */     return this.daoSupport.queryForList(sql, Promotion.class, new Object[] { goodsid, memberLvId });
/*     */   }
/*     */ 
/*     */   public List<Promotion> list(Double orderPrice, Integer memberLvId)
/*     */   {
/* 143 */     long now = System.currentTimeMillis();
/*     */ 
/* 146 */     String sql = "select * from  " + getTableName("promotion") + " where pmt_basic_type='order' " + " and pmt_time_begin<" + now + " and  pmt_time_end>" + now + " and order_money_from<=? and order_money_to>=?" + " and pmt_id in (select pmt_id from " + getTableName("pmt_member_lv") + " where lv_id =? ) " + " and pmt_type='0' ";
/*     */ 
/* 159 */     return this.daoSupport.queryForList(sql, Promotion.class, new Object[] { orderPrice, orderPrice, memberLvId });
/*     */   }
/*     */ 
/*     */   private Integer getCoupPmtId(String coupcode) {
/* 163 */     String sql = "select  c.pmt_id from  " + getTableName("member_coupon") + " mc ," + getTableName("coupons") + " c where mc.cpns_id = c.cpns_id and mc.memc_code=? ";
/* 164 */     return Integer.valueOf(this.daoSupport.queryForInt(sql, new Object[] { coupcode }));
/*     */   }
/*     */ 
/*     */   public void applyGoodsPmt(List<CartItem> list, Integer memberLvId)
/*     */   {
/* 170 */     if ((list == null) || (memberLvId == null)) return;
/*     */ 
/* 172 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { item = (CartItem)i$.next();
/*     */ 
/* 174 */       Integer goodsid = item.getGoods_id();
/*     */ 
/* 177 */       List pmtList = list(goodsid, memberLvId);
/* 178 */       item.setPmtList(pmtList);
/*     */ 
/* 180 */       for (Promotion pmt : pmtList)
/*     */       {
/* 183 */         String pluginBeanId = pmt.getPmts_id();
/* 184 */         IPromotionPlugin plugin = getPlugin(pluginBeanId);
/*     */ 
/* 187 */         if (plugin == null) {
/* 188 */           this.logger.error("plugin[" + pluginBeanId + "] not found ");
/* 189 */           throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
/*     */         }
/*     */ 
/* 193 */         String methodBeanName = plugin.getMethods();
/* 194 */         if (this.logger.isDebugEnabled()) {
/* 195 */           this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */         }
/* 197 */         IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 198 */         if (promotionMethod == null) {
/* 199 */           this.logger.error("plugin[" + methodBeanName + "] not found ");
/* 200 */           throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */         }
/*     */ 
/* 203 */         if ((promotionMethod instanceof IDiscountBehavior)) {
/* 204 */           IDiscountBehavior discountBehavior = (IDiscountBehavior)promotionMethod;
/* 205 */           Double newPrice = discountBehavior.discount(pmt, item.getCoupPrice());
/* 206 */           item.setCoupPrice(newPrice);
/*     */         }
/*     */ 
/* 211 */         if ((promotionMethod instanceof ITimesPointBehavior)) {
/* 212 */           Integer point = item.getPoint();
/* 213 */           ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
/* 214 */           point = timesPointBehavior.countPoint(pmt, point);
/* 215 */           item.setPoint(point);
/*     */         }
/*     */       }
/*     */     }
/*     */     CartItem item;
/*     */   }
/*     */ 
/*     */   public DiscountPrice applyOrderPmt(Double orderPrice, Double shipFee, Integer point, Integer memberLvId)
/*     */   {
/* 228 */     List pmtList = list(orderPrice, memberLvId);
/* 229 */     for (Promotion pmt : pmtList)
/*     */     {
/* 232 */       String pluginBeanId = pmt.getPmts_id();
/* 233 */       IPromotionPlugin plugin = getPlugin(pluginBeanId);
/* 234 */       if (plugin == null) {
/* 235 */         this.logger.error("plugin[" + pluginBeanId + "] not found ");
/* 236 */         throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
/*     */       }
/*     */ 
/* 240 */       String methodBeanName = plugin.getMethods();
/* 241 */       if (this.logger.isDebugEnabled()) {
/* 242 */         this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */       }
/* 244 */       IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 245 */       if (promotionMethod == null) {
/* 246 */         this.logger.error("plugin[" + methodBeanName + "] not found ");
/* 247 */         throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */       }
/*     */ 
/* 251 */       if ((promotionMethod instanceof IReducePriceBehavior)) {
/* 252 */         IReducePriceBehavior reducePriceBehavior = (IReducePriceBehavior)promotionMethod;
/* 253 */         orderPrice = reducePriceBehavior.reducedPrice(pmt, orderPrice);
/*     */       }
/*     */ 
/* 257 */       if ((promotionMethod instanceof IDiscountBehavior)) {
/* 258 */         IDiscountBehavior discountBehavior = (IDiscountBehavior)promotionMethod;
/* 259 */         orderPrice = discountBehavior.discount(pmt, orderPrice);
/*     */       }
/*     */ 
/* 263 */       if ((promotionMethod instanceof IReduceFreightBehavior)) {
/* 264 */         IReduceFreightBehavior reduceFreightBehavior = (IReduceFreightBehavior)promotionMethod;
/* 265 */         shipFee = reduceFreightBehavior.reducedPrice(shipFee);
/*     */       }
/*     */ 
/* 269 */       if ((promotionMethod instanceof ITimesPointBehavior)) {
/* 270 */         ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
/* 271 */         point = timesPointBehavior.countPoint(pmt, point);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 276 */     DiscountPrice discountPrice = new DiscountPrice();
/* 277 */     discountPrice.setOrderPrice(orderPrice);
/* 278 */     discountPrice.setShipFee(shipFee);
/* 279 */     discountPrice.setPoint(point);
/* 280 */     return discountPrice;
/*     */   }
/*     */ 
/*     */   public void applyOrderPmt(Integer orderId, Double orderPrice, Integer memberLvId)
/*     */   {
/* 286 */     List pmtList = list(orderPrice, memberLvId);
/* 287 */     for (Promotion pmt : pmtList)
/*     */     {
/* 290 */       String pluginBeanId = pmt.getPmts_id();
/* 291 */       IPromotionPlugin plugin = getPlugin(pluginBeanId);
/* 292 */       if (plugin == null) {
/* 293 */         this.logger.error("plugin[" + pluginBeanId + "] not found ");
/* 294 */         throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
/*     */       }
/*     */ 
/* 298 */       String methodBeanName = plugin.getMethods();
/* 299 */       if (this.logger.isDebugEnabled()) {
/* 300 */         this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */       }
/* 302 */       IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 303 */       if (promotionMethod == null) {
/* 304 */         this.logger.error("plugin[" + methodBeanName + "] not found ");
/* 305 */         throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */       }
/*     */ 
/* 309 */       if ((promotionMethod instanceof IGiveGiftBehavior)) {
/* 310 */         IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior)promotionMethod;
/* 311 */         giveGiftBehavior.giveGift(pmt, orderId);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List listGift(List<Promotion> pmtList)
/*     */   {
/* 320 */     List giftList = new ArrayList();
/* 321 */     for (Promotion pmt : pmtList)
/*     */     {
/* 324 */       String pluginBeanId = pmt.getPmts_id();
/* 325 */       IPromotionPlugin plugin = getPlugin(pluginBeanId);
/* 326 */       if (plugin == null) {
/* 327 */         this.logger.error("plugin[" + pluginBeanId + "] not found ");
/* 328 */         throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
/*     */       }
/*     */ 
/* 332 */       String methodBeanName = plugin.getMethods();
/* 333 */       if (this.logger.isDebugEnabled()) {
/* 334 */         this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */       }
/* 336 */       IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 337 */       if (promotionMethod == null) {
/* 338 */         this.logger.error("plugin[" + methodBeanName + "] not found ");
/* 339 */         throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */       }
/*     */ 
/* 342 */       if ((promotionMethod instanceof IGiveGiftBehavior)) {
/* 343 */         IGiveGiftBehavior giveGiftBehavior = (IGiveGiftBehavior)promotionMethod;
/* 344 */         List list = giveGiftBehavior.getGiftList(pmt);
/* 345 */         giftList.addAll(list);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 350 */     return giftList;
/*     */   }
/*     */ 
/*     */   public List listByActivityId(Integer activityid)
/*     */   {
/* 360 */     String sql = "select * from promotion where disabled='false' and pmta_id=?";
/* 361 */     return this.baseDaoSupport.queryForList(sql, Promotion.class, new Object[] { activityid });
/*     */   }
/*     */ 
/*     */   private void pluginSave(Promotion promotion)
/*     */   {
/* 372 */     if (this.logger.isDebugEnabled()) {
/* 373 */       this.logger.debug("find promotion plugin[" + promotion.getPmts_id() + "]");
/*     */     }
/* 375 */     IPromotionPlugin plugin = getPlugin(promotion.getPmts_id());
/*     */ 
/* 377 */     if (plugin == null) {
/* 378 */       this.logger.error("plugin[" + promotion.getPmts_id() + "] not found ");
/* 379 */       throw new ObjectNotFoundException("plugin[" + promotion.getPmts_id() + "] not found ");
/*     */     }
/*     */ 
/* 383 */     String methodBeanName = plugin.getMethods();
/* 384 */     if (this.logger.isDebugEnabled()) {
/* 385 */       this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */     }
/* 387 */     IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 388 */     if (promotionMethod == null) {
/* 389 */       this.logger.error("plugin[" + methodBeanName + "] not found ");
/* 390 */       throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */     }
/*     */ 
/* 393 */     String solution = promotionMethod.onPromotionSave(Integer.valueOf(promotion.getPmt_id()));
/* 394 */     this.baseDaoSupport.execute("update promotion set pmt_solution =? where pmt_id=?", new Object[] { solution, Integer.valueOf(promotion.getPmt_id()) });
/*     */   }
/*     */ 
/*     */   private void saveMemberLv(Integer pmtid, Integer[] memberLvIdArray)
/*     */   {
/* 410 */     for (Integer memberLvId : memberLvIdArray)
/*     */     {
/* 412 */       this.baseDaoSupport.execute("insert into pmt_member_lv(pmt_id,lv_id)values(?,?)", new Object[] { pmtid, memberLvId });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveGoods(Integer pmtid, Integer[] goodsIdArray)
/*     */   {
/* 424 */     for (Integer goodsid : goodsIdArray)
/* 425 */       this.baseDaoSupport.execute("insert into pmt_goods(pmt_id,goods_id)values(?,?)", new Object[] { pmtid, goodsid });
/*     */   }
/*     */ 
/*     */   public List listPmtPlugins()
/*     */   {
/* 432 */     return this.promotionPluginBundle.getPluginList();
/*     */   }
/*     */ 
/*     */   public IPromotionPlugin getPlugin(String pluginid)
/*     */   {
/* 443 */     List pluginList = this.promotionPluginBundle.getPluginList();
/*     */ 
/* 445 */     for (IPlugin plugin : pluginList)
/*     */     {
/* 447 */       if (((plugin instanceof IPromotionPlugin)) && 
/* 448 */         (((IPromotionPlugin)plugin).getId().equals(pluginid))) {
/* 449 */         return (IPromotionPlugin)plugin;
/*     */       }
/*     */     }
/*     */ 
/* 453 */     return null;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] pmtidArray) {
/* 459 */     if ((pmtidArray == null) || (pmtidArray.length == 0)) return;
/* 460 */     String idStr = StringUtil.arrayToString(pmtidArray, ",");
/* 461 */     String sql = "delete from pmt_goods where pmt_id in(" + idStr + ")";
/* 462 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 464 */     sql = "delete from pmt_member_lv where pmt_id in(" + idStr + ")";
/* 465 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 467 */     sql = "delete from promotion where pmt_id in(" + idStr + ")";
/* 468 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Promotion get(Integer pmtid)
/*     */   {
/* 475 */     return (Promotion)this.baseDaoSupport.queryForObject("select * from promotion where pmt_id =?", Promotion.class, new Object[] { pmtid });
/*     */   }
/*     */ 
/*     */   public List listGoodsId(Integer pmtid)
/*     */   {
/* 481 */     String sql = "select * from  " + getTableName("goods") + " where goods_id in(select goods_id from " + getTableName("pmt_goods") + " where pmt_id =? )";
/* 482 */     return this.daoSupport.queryForList(sql, new Object[] { pmtid });
/*     */   }
/*     */ 
/*     */   public List listMemberLvId(Integer pmtid)
/*     */   {
/* 488 */     String sql = "select lv_id from pmt_member_lv where pmt_id =? ";
/* 489 */     return this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[] { pmtid });
/*     */   }
/*     */ 
/*     */   public PromotionPluginBundle getPromotionPluginBundle()
/*     */   {
/* 496 */     return this.promotionPluginBundle;
/*     */   }
/*     */   public void setPromotionPluginBundle(PromotionPluginBundle promotionPluginBundle) {
/* 499 */     this.promotionPluginBundle = promotionPluginBundle;
/*     */   }
/*     */ 
/*     */   public List<Map> listOrderPmt(Integer orderid)
/*     */   {
/* 504 */     String sql = "select * from order_pmt where order_id = ?";
/* 505 */     return this.baseDaoSupport.queryForList(sql, new Object[] { orderid });
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.PromotionManager
 * JD-Core Version:    0.6.0
 */