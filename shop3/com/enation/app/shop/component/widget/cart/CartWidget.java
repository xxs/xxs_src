/*     */ package com.enation.app.shop.component.widget.cart;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Cart;
/*     */ import com.enation.app.shop.core.model.FreeOffer;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.app.shop.core.service.ICartManager;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IGnotifyManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("shop_cart")
/*     */ @Scope("prototype")
/*     */ public class CartWidget extends AbstractMemberWidget
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private ICartManager cartManager;
/*     */   private IPromotionManager promotionManager;
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private IProductManager productManager;
/*     */   private IGnotifyManager gnotifyManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean cacheAble()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  61 */     this.request = ThreadContextHolder.getHttpRequest();
/*  62 */     String step = this.request.getParameter("step");
/*  63 */     if ((step == null) || ("".equals(step)))
/*  64 */       execute();
/*     */   }
/*     */ 
/*     */   private void execute()
/*     */   {
/*  74 */     if ("add".equals(this.action)) {
/*  75 */       add();
/*     */     }
/*  77 */     if ("delete".equals(this.action)) {
/*  78 */       delete();
/*     */     }
/*  80 */     if ("clean".equals(this.action)) {
/*  81 */       clean();
/*     */     }
/*  83 */     if ("update".equals(this.action)) {
/*  84 */       updateNum();
/*     */     }
/*  86 */     if ("getTotal".equals(this.action)) {
/*  87 */       getTotal();
/*     */     }
/*     */ 
/*  91 */     if ((this.action == null) || (this.action.equals(""))) {
/*  92 */       enableCustomPage();
/*  93 */       list();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void selfAdd()
/*     */   {
/* 101 */     String productid = this.request.getParameter("productid");
/* 102 */     String itemtype = this.request.getParameter("itemtype");
/* 103 */     String sessionid = this.request.getSession().getId();
/* 104 */     itemtype = itemtype == null ? "0" : itemtype;
/*     */ 
/* 106 */     String num = this.request.getParameter("num");
/* 107 */     num = (num == null) || (num.equals("")) ? "1" : num;
/* 108 */     if ((productid == null) || ("".equals(productid))) throw new RuntimeException("productid is null");
/*     */ 
/* 111 */     if (itemtype.equals("2")) {
/* 112 */       boolean r = addGiftToCart(Integer.valueOf(productid), Integer.valueOf(num), sessionid);
/* 113 */       if (!r) return;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 119 */       addGoodsToCart(Integer.valueOf(productid), Integer.valueOf(num), sessionid, Integer.valueOf(itemtype));
/*     */     }
/*     */ 
/* 122 */     ThreadContextHolder.getHttpRequest().getSession().setAttribute("site_key", EopContext.getContext().getCurrentSite());
/*     */   }
/*     */ 
/*     */   private void add()
/*     */   {
/* 132 */     String ajax = this.request.getParameter("ajax");
/* 133 */     if ("yes".equals(ajax)) {
/*     */       try {
/* 135 */         selfAdd();
/* 136 */         showSuccessJson("商品成功添加至购物车");
/* 137 */         return;
/*     */       } catch (RuntimeException e) {
/* 139 */         showErrorJson(e.getMessage());
/* 140 */         return;
/*     */       }
/*     */     }
/* 143 */     selfAdd();
/* 144 */     list();
/*     */   }
/*     */ 
/*     */   private void addGoodsToCart(Integer productid, Integer num, String sessionid, Integer itemtype)
/*     */   {
/* 159 */     Product product = null;
/* 160 */     if (itemtype.intValue() == 0) {
/* 161 */       product = this.productManager.get(productid);
/*     */     }
/*     */ 
/* 164 */     if (itemtype.intValue() == 1) {
/* 165 */       product = this.productManager.getByGoodsId(productid);
/*     */     }
/*     */ 
/* 168 */     if (itemtype.intValue() == 4) {
/* 169 */       product = this.productManager.getByGoodsId(productid);
/* 170 */       itemtype = Integer.valueOf(0);
/*     */     }
/*     */ 
/* 173 */     if (product != null) {
/* 174 */       Cart cart = new Cart();
/* 175 */       cart.setGoods_id(product.getGoods_id());
/* 176 */       cart.setProduct_id(product.getProduct_id());
/* 177 */       cart.setSession_id(sessionid);
/* 178 */       cart.setNum(num);
/* 179 */       cart.setItemtype(itemtype);
/* 180 */       cart.setWeight(product.getWeight());
/* 181 */       cart.setPrice(product.getPrice());
/* 182 */       cart.setName(product.getName());
/*     */ 
/* 184 */       this.cartManager.add(cart);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean addGiftToCart(Integer giftid, Integer num, String sessionid)
/*     */   {
/* 198 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 200 */     if (member == null) {
/*     */       try {
/* 202 */         showError("您尚未登录不能兑换赠品", "点击此处到登录页面", "member_login.html?forward=" + URLEncoder.encode(new StringBuilder().append("cart.html?action=add&productid=").append(giftid).append("&itemtype=2").toString(), "UTF-8"));
/*     */       } catch (UnsupportedEncodingException e) {
/* 204 */         e.printStackTrace();
/*     */       }
/* 206 */       return false;
/*     */     }
/* 208 */     FreeOffer freeOffer = this.freeOfferManager.get(Integer.valueOf(giftid.intValue()).intValue());
/* 209 */     if ((freeOffer.getLv_ids() == null) || (member.getLv_id() == null))
/*     */     {
/* 211 */       showError("您的会员等级不能兑换此赠品", "赠品列表", "giftlist.html");
/* 212 */       return false;
/*     */     }
/* 214 */     String[] lvAr = freeOffer.getLv_ids().split(",");
/* 215 */     if (!StringUtil.isInArray(member.getLv_id().intValue(), lvAr)) {
/* 216 */       showError("您的会员等级不能兑换此赠品", "赠品列表", "giftlist.html");
/* 217 */       return false;
/*     */     }
/*     */ 
/* 220 */     if (member.getPoint().intValue() < freeOffer.getScore().intValue()) {
/* 221 */       showError("您的积分不足，不能兑换此赠品", "赠品列表", "giftlist.html");
/* 222 */       return false;
/*     */     }
/* 224 */     Cart cart = new Cart();
/* 225 */     cart.setProduct_id(giftid);
/* 226 */     cart.setSession_id(sessionid);
/* 227 */     cart.setNum(num);
/* 228 */     cart.setItemtype(Integer.valueOf(2));
/* 229 */     cart.setWeight(freeOffer.getWeight());
/* 230 */     cart.setName(freeOffer.getFo_name());
/* 231 */     cart.setPrice(Double.valueOf(freeOffer.getScore().intValue()));
/* 232 */     this.cartManager.add(cart);
/*     */ 
/* 235 */     return true;
/*     */   }
/*     */ 
/*     */   private void list()
/*     */   {
/* 264 */     String sessionid = this.request.getSession().getId();
/* 265 */     List goodsList = this.cartManager.listGoods(sessionid);
/*     */ 
/* 268 */     setPageName("cart");
/* 269 */     putData("goodsItemList", goodsList);
/*     */ 
/* 271 */     putData("GoodsPic", new ImageUrlDirectiveModel());
/*     */ 
/* 273 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 274 */     if (member == null)
/* 275 */       putData("isLogin", Boolean.valueOf(false));
/*     */     else
/* 277 */       putData("isLogin", Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   public void delete()
/*     */   {
/*     */     try
/*     */     {
/* 288 */       String cartid = this.request.getParameter("cartid");
/* 289 */       this.cartManager.delete(this.request.getSession().getId(), Integer.valueOf(cartid));
/* 290 */       showSuccessJson("删除成功");
/*     */     } catch (RuntimeException e) {
/* 292 */       this.logger.error("删除购物项失败", e);
/* 293 */       showErrorJson("删除购物项失败");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clean()
/*     */   {
/*     */     try {
/* 300 */       this.cartManager.clean(this.request.getSession().getId());
/* 301 */       showSuccessJson("清空购物车成功");
/*     */     } catch (RuntimeException e) {
/* 303 */       this.logger.error("清空购物车", e);
/* 304 */       showErrorJson(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateNum()
/*     */   {
/*     */     String json;
/*     */     try
/*     */     {
/* 315 */       String cartid = this.request.getParameter("cartid");
/* 316 */       String num = this.request.getParameter("num");
/* 317 */       num = StringUtil.isEmpty(num) ? "1" : num;
/* 318 */       this.cartManager.updateNum(this.request.getSession().getId(), Integer.valueOf(cartid), Integer.valueOf(num));
/* 319 */       json = "{result:0}";
/*     */     } catch (RuntimeException e) {
/* 321 */       e.printStackTrace();
/* 322 */       json = "{result:1}";
/*     */     }
/* 324 */     putData("json", json);
/* 325 */     setPageName("cartJson");
/*     */   }
/*     */ 
/*     */   public void getTotal()
/*     */   {
/* 332 */     String sessionid = this.request.getSession().getId();
/*     */ 
/* 364 */     OrderPrice orderprice = this.cartManager.countPrice(sessionid, null, null, null);
/* 365 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 366 */     if (member != null) {
/* 367 */       List pmtList = this.promotionManager.list(orderprice.getGoodsPrice(), member.getLv_id());
/* 368 */       putData("pmtList", pmtList);
/*     */     }
/*     */ 
/* 371 */     putData("orderPrice", orderprice);
/* 372 */     setActionPageName("cartTotal");
/*     */   }
/*     */ 
/*     */   public ICartManager getCartManager()
/*     */   {
/* 377 */     return this.cartManager;
/*     */   }
/*     */ 
/*     */   public void setCartManager(ICartManager cartManager) {
/* 381 */     this.cartManager = cartManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager()
/*     */   {
/* 387 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 391 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager()
/*     */   {
/* 397 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/* 401 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 406 */     NumberFormat format = new DecimalFormat("#0.00");
/* 407 */     double a = 1.09D; double b = 0.03D;
/* 408 */     double d = Double.valueOf(format.format(a + b)).doubleValue();
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager()
/*     */   {
/* 414 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 418 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public IGnotifyManager getGnotifyManager()
/*     */   {
/* 424 */     return this.gnotifyManager;
/*     */   }
/*     */ 
/*     */   public void setGnotifyManager(IGnotifyManager gnotifyManager)
/*     */   {
/* 429 */     this.gnotifyManager = gnotifyManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.cart.CartWidget
 * JD-Core Version:    0.6.0
 */