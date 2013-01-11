/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Cart;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.Promotion;
/*     */ import com.enation.app.shop.core.model.mapper.CartItemMapper;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.app.shop.core.model.support.DiscountPrice;
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.app.shop.core.plugin.cart.CartPluginBundle;
/*     */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*     */ import com.enation.app.shop.core.service.ICartManager;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*     */ import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
/*     */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.DoubleMapper;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ObjectNotFoundException;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class CartManager extends BaseSupport
/*     */   implements ICartManager
/*     */ {
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   private CartPluginBundle cartPluginBundle;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IPromotionManager promotionManager;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int add(Cart cart)
/*     */   {
/*  53 */     HttpCacheManager.sessionChange();
/*     */ 
/*  59 */     this.cartPluginBundle.onAdd(cart);
/*     */ 
/*  63 */     String sql = "select count(0) from cart where  product_id=? and session_id=? and itemtype=? ";
/*     */ 
/*  65 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });
/*  66 */     if (count > 0) {
/*  67 */       this.baseDaoSupport.execute("update cart set num=num+? where  product_id=? and session_id=? and itemtype=? ", new Object[] { cart.getNum(), cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });
/*     */ 
/*  69 */       return 0;
/*     */     }
/*     */ 
/*  73 */     this.baseDaoSupport.insert("cart", cart);
/*     */ 
/*  76 */     Integer cartid = Integer.valueOf(this.baseDaoSupport.getLastId("cart"));
/*  77 */     cart.setCart_id(cartid);
/*     */ 
/*  80 */     return cartid.intValue();
/*     */   }
/*     */ 
/*     */   public Cart get(int cart_id)
/*     */   {
/*  89 */     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE cart_id=?", Cart.class, new Object[] { Integer.valueOf(cart_id) });
/*     */   }
/*     */ 
/*     */   public Cart getCartByProductId(int productId, String sessionid) {
/*  93 */     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid });
/*     */   }
/*     */ 
/*     */   public Cart getCartByProductId(int productId, String sessionid, String addon) {
/*  97 */     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=? AND addon=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid, addon });
/*     */   }
/*     */ 
/*     */   public Integer countItemNum(String sessionid) {
/* 101 */     String sql = "select count(0) from cart where session_id =?";
/* 102 */     return Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { sessionid }));
/*     */   }
/*     */ 
/*     */   public List<CartItem> listGoods(String sessionid)
/*     */   {
/* 108 */     StringBuffer sql = new StringBuffer();
/*     */ 
/* 110 */     sql.append("select g.cat_id as catid,g.goods_id,g.image_default,g.istejia,g.no_discount, c.name ,  p.sn, p.specs  ,g.mktprice,g.unit,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon  from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");
/* 111 */     sql.append("where c.itemtype=0 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");
/* 112 */     List list = this.daoSupport.queryForList(sql.toString(), new CartItemMapper(), new Object[] { sessionid });
/*     */ 
/* 114 */     this.cartPluginBundle.filterList(list, sessionid);
/*     */ 
/* 117 */     return list;
/*     */   }
/*     */ 
/*     */   private void applyMemPrice(List<CartItem> itemList, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 130 */     for (CartItem item : itemList) {
/* 131 */       double price = item.getCoupPrice().doubleValue() * discount;
/* 132 */       for (GoodsLvPrice lvPrice : memPriceList) {
/* 133 */         if (item.getProduct_id().intValue() == lvPrice.getProductid()) {
/* 134 */           price = lvPrice.getPrice().doubleValue();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 139 */       item.setCoupPrice(Double.valueOf(price));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clean(String sessionid)
/*     */   {
/* 152 */     String sql = "delete from cart where session_id=?";
/*     */ 
/* 154 */     this.baseDaoSupport.execute(sql, new Object[] { sessionid });
/* 155 */     HttpCacheManager.sessionChange();
/*     */   }
/*     */ 
/*     */   public void clean(String sessionid, Integer userid, Integer siteid)
/*     */   {
/* 160 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 161 */       String sql = "delete from es_cart_" + userid + "_" + siteid + " where session_id=?";
/*     */ 
/* 163 */       this.daoSupport.execute(sql, new Object[] { sessionid });
/*     */     }
/*     */     else {
/* 166 */       String sql = "delete from cart where session_id=?";
/* 167 */       this.baseDaoSupport.execute(sql, new Object[] { sessionid });
/*     */     }
/*     */ 
/* 170 */     if (this.logger.isDebugEnabled()) {
/* 171 */       this.logger.debug("clean cart sessionid[" + sessionid + "]");
/*     */     }
/* 173 */     HttpCacheManager.sessionChange();
/*     */   }
/*     */ 
/*     */   public void delete(String sessionid, Integer cartid) {
/* 177 */     String sql = "delete from cart where session_id=? and cart_id=?";
/* 178 */     this.baseDaoSupport.execute(sql, new Object[] { sessionid, cartid });
/* 179 */     this.cartPluginBundle.onDelete(sessionid, cartid);
/* 180 */     this.cartPluginBundle.onDelete(sessionid, cartid);
/* 181 */     HttpCacheManager.sessionChange();
/*     */   }
/*     */ 
/*     */   public void updateNum(String sessionid, Integer cartid, Integer num) {
/* 185 */     String sql = "update cart set num=? where session_id =? and cart_id=?";
/* 186 */     this.baseDaoSupport.execute(sql, new Object[] { num, sessionid, cartid });
/*     */   }
/*     */ 
/*     */   public Double countGoodsTotal(String sessionid) {
/* 190 */     StringBuffer sql = new StringBuffer();
/* 191 */     sql.append("select sum( c.price * c.num ) as num from cart c ");
/* 192 */     sql.append("where  c.session_id=? and c.itemtype=0 ");
/* 193 */     Double price = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });
/*     */ 
/* 195 */     return price;
/*     */   }
/*     */ 
/*     */   public Double countGoodsDiscountTotal(String sessionid)
/*     */   {
/* 204 */     List itemList = listGoods(sessionid);
/*     */ 
/* 206 */     double price = 0.0D;
/* 207 */     for (CartItem item : itemList)
/*     */     {
/* 209 */       price = CurrencyUtil.add(price, item.getSubtotal().doubleValue());
/*     */     }
/*     */ 
/* 212 */     return Double.valueOf(price);
/*     */   }
/*     */ 
/*     */   public Integer countPoint(String sessionid)
/*     */   {
/* 218 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 219 */     if (member != null) {
/* 220 */       Integer memberLvId = member.getLv_id();
/* 221 */       StringBuffer sql = new StringBuffer();
/* 222 */       sql.append("select c.*, g.goods_id, g.point from " + getTableName("cart") + " c," + getTableName("goods") + " g, " + getTableName("product") + " p where p.product_id = c.product_id and g.goods_id = p.goods_id and c.session_id = ?");
/*     */ 
/* 230 */       List list = this.daoSupport.queryForList(sql.toString(), new Object[] { sessionid });
/*     */ 
/* 232 */       Integer result = Integer.valueOf(0);
/* 233 */       for (Iterator i$ = list.iterator(); i$.hasNext(); ) { map = (Map)i$.next();
/* 234 */         Integer goodsid = Integer.valueOf(StringUtil.toInt(map.get("goods_id").toString()));
/*     */ 
/* 236 */         List pmtList = new ArrayList();
/*     */ 
/* 238 */         if (memberLvId != null) {
/* 239 */           pmtList = this.promotionManager.list(goodsid, memberLvId);
/*     */         }
/*     */ 
/* 242 */         for (Promotion pmt : pmtList)
/*     */         {
/* 245 */           String pluginBeanId = pmt.getPmts_id();
/* 246 */           IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginBeanId);
/*     */ 
/* 249 */           if (plugin == null) {
/* 250 */             this.logger.error("plugin[" + pluginBeanId + "] not found ");
/* 251 */             throw new ObjectNotFoundException("plugin[" + pluginBeanId + "] not found ");
/*     */           }
/*     */ 
/* 256 */           String methodBeanName = plugin.getMethods();
/* 257 */           if (this.logger.isDebugEnabled()) {
/* 258 */             this.logger.debug("find promotion method[" + methodBeanName + "]");
/*     */           }
/*     */ 
/* 261 */           IPromotionMethod promotionMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/*     */ 
/* 263 */           if (promotionMethod == null) {
/* 264 */             this.logger.error("plugin[" + methodBeanName + "] not found ");
/*     */ 
/* 266 */             throw new ObjectNotFoundException("promotion method[" + methodBeanName + "] not found ");
/*     */           }
/*     */ 
/* 271 */           if ((promotionMethod instanceof ITimesPointBehavior)) {
/* 272 */             Integer point = Integer.valueOf(StringUtil.toInt(map.get("point").toString()));
/*     */ 
/* 274 */             ITimesPointBehavior timesPointBehavior = (ITimesPointBehavior)promotionMethod;
/* 275 */             point = timesPointBehavior.countPoint(pmt, point);
/* 276 */             result = Integer.valueOf(result.intValue() + point.intValue());
/*     */           }
/*     */         }
/*     */       }
/*     */       Map map;
/* 281 */       return result;
/*     */     }
/* 283 */     StringBuffer sql = new StringBuffer();
/* 284 */     sql.append("select  sum(g.point * c.num) from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");
/*     */ 
/* 288 */     sql.append("where (c.itemtype=0  or c.itemtype=1)  and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");
/*     */ 
/* 291 */     return Integer.valueOf(this.daoSupport.queryForInt(sql.toString(), new Object[] { sessionid }));
/*     */   }
/*     */ 
/*     */   public Double countGoodsWeight(String sessionid)
/*     */   {
/* 296 */     StringBuffer sql = new StringBuffer("select sum( c.weight * c.num )  from cart c where c.session_id=?");
/*     */ 
/* 298 */     Double weight = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });
/*     */ 
/* 300 */     return weight;
/*     */   }
/*     */ 
/*     */   public OrderPrice countPrice(String sessionid, Integer shippingid, String regionid, Boolean isProtected)
/*     */   {
/* 311 */     OrderPrice orderPrice = new OrderPrice();
/*     */ 
/* 320 */     Double weight = countGoodsWeight(sessionid);
/*     */ 
/* 323 */     Integer point = countPoint(sessionid);
/*     */ 
/* 328 */     Double originalPrice = countGoodsTotal(sessionid);
/*     */ 
/* 331 */     Double coupPrice = originalPrice;
/*     */ 
/* 334 */     Double orderTotal = Double.valueOf(0.0D);
/*     */ 
/* 337 */     Double dlyPrice = Double.valueOf(0.0D);
/*     */ 
/* 340 */     Double protectPrice = Double.valueOf(0.0D);
/*     */ 
/* 343 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 347 */     if (member != null) {
/* 348 */       coupPrice = countGoodsDiscountTotal(sessionid);
/*     */     }
/*     */ 
/* 358 */     if ((regionid != null) && (shippingid != null) && (isProtected != null))
/*     */     {
/* 360 */       Double[] priceArray = this.dlyTypeManager.countPrice(shippingid, weight, originalPrice, regionid, isProtected.booleanValue());
/* 361 */       dlyPrice = priceArray[0];
/*     */ 
/* 363 */       if (member != null)
/*     */       {
/* 365 */         DiscountPrice discountPrice = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice, point, member.getLv_id());
/* 366 */         coupPrice = discountPrice.getOrderPrice();
/* 367 */         dlyPrice = discountPrice.getShipFee();
/* 368 */         point = discountPrice.getPoint();
/*     */       }
/*     */ 
/* 374 */       if (isProtected.booleanValue()) {
/* 375 */         protectPrice = priceArray[1];
/*     */ 
/* 377 */         orderPrice.setProtectPrice(protectPrice);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 390 */     Double reducePrice = Double.valueOf(CurrencyUtil.sub(originalPrice.doubleValue(), coupPrice.doubleValue()));
/*     */ 
/* 393 */     orderTotal = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), dlyPrice.doubleValue()));
/* 394 */     orderTotal = Double.valueOf(CurrencyUtil.add(orderTotal.doubleValue(), protectPrice.doubleValue()));
/*     */ 
/* 396 */     orderPrice.setDiscountPrice(reducePrice);
/* 397 */     orderPrice.setGoodsPrice(coupPrice);
/* 398 */     orderPrice.setShippingPrice(dlyPrice);
/* 399 */     orderPrice.setPoint(point);
/* 400 */     orderPrice.setOriginalPrice(originalPrice);
/* 401 */     orderPrice.setOrderPrice(orderTotal);
/* 402 */     orderPrice.setWeight(weight);
/* 403 */     orderPrice.setNeedPayMoney(orderTotal);
/* 404 */     orderPrice = this.cartPluginBundle.coutPrice(orderPrice);
/* 405 */     return orderPrice;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager()
/*     */   {
/* 425 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public CartPluginBundle getCartPluginBundle()
/*     */   {
/* 430 */     return this.cartPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setCartPluginBundle(CartPluginBundle cartPluginBundle) {
/* 434 */     this.cartPluginBundle = cartPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 439 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager() {
/* 443 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/* 447 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 451 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 455 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.CartManager
 * JD-Core Version:    0.6.0
 */