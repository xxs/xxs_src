/*     */ package com.enation.app.shop.component.widget.cart.checkout;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberAddress;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PaymentLog;
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.app.shop.core.service.ICartManager;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.IMemberAddressManager;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Deprecated
/*     */ @Component("checkOutWidget")
/*     */ @Scope("prototype")
/*     */ public class CheckOutWidget extends AbstractWidget
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private IMemberAddressManager memberAddressManager;
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   protected ICartManager cartManager;
/*     */   private IPaymentManager paymentManager;
/*     */   private IOrderManager orderManager;
/*     */   private IPromotionManager promotionManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  57 */     this.request = ThreadContextHolder.getHttpRequest();
/*  58 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/*  62 */     String sessionid = this.request.getSession().getId();
/*     */ 
/*  64 */     if ((this.action == null) || (this.action.equals("")))
/*     */     {
/*  66 */       setPageName("checkout");
/*     */ 
/*  69 */       if (member != null)
/*     */       {
/*  71 */         List addressList = this.memberAddressManager.listAddress();
/*  72 */         putData("addressList", addressList);
/*  73 */         putData("isLogin", Boolean.valueOf(true));
/*     */       } else {
/*  75 */         putData("addressList", new ArrayList());
/*  76 */         putData("isLogin", Boolean.valueOf(false));
/*     */       }
/*     */ 
/*  81 */       List goodsItemList = this.cartManager.listGoods(sessionid);
/*     */ 
/*  89 */       putData("goodsItemList", goodsItemList);
/*     */ 
/*  93 */       putData("GoodsPic", new ImageUrlDirectiveModel());
/*     */ 
/*  95 */       if ((goodsItemList == null) || (goodsItemList.isEmpty()))
/*  96 */         putData("hasGoods", Boolean.valueOf(false));
/*     */       else {
/*  98 */         putData("hasGoods", Boolean.valueOf(true));
/*     */       }
/*     */ 
/* 120 */       List paymentList = this.paymentManager.list();
/* 121 */       putData("paymentList", paymentList);
/*     */ 
/* 124 */       if (member != null)
/*     */       {
/* 126 */         Double originalTotal = this.cartManager.countGoodsTotal(sessionid);
/* 127 */         List pmtList = this.promotionManager.list(originalTotal, member.getLv_id());
/* 128 */         putData("pmtList", pmtList);
/*     */ 
/* 131 */         List giftList = this.promotionManager.listGift(pmtList);
/* 132 */         putData("giftList", giftList);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if ("loginBuy".equals(this.action))
/*     */     {
/* 140 */       setActionPageName("loginBuy");
/*     */     }
/*     */ 
/* 143 */     if ("showAddress".equals(this.action)) {
/* 144 */       Integer addr_id = Integer.valueOf(this.request.getParameter("addr_id"));
/* 145 */       MemberAddress address = this.memberAddressManager.getAddress(addr_id.intValue());
/* 146 */       showJson(JSONObject.fromObject(address).toString());
/*     */     }
/*     */ 
/* 151 */     if ("showDlyType".equals(this.action))
/*     */     {
/* 153 */       Double orderPrice = this.cartManager.countGoodsTotal(sessionid);
/* 154 */       Double weight = this.cartManager.countGoodsWeight(sessionid);
/*     */ 
/* 156 */       List dlyTypeList = this.dlyTypeManager.list(weight, orderPrice, this.request.getParameter("regionid"));
/* 157 */       putData("dlyTypeList", dlyTypeList);
/* 158 */       setActionPageName("dlyType");
/*     */     }
/*     */ 
/* 162 */     if ("showOrderTotal".equals(this.action))
/*     */     {
/* 164 */       Integer typeId = Integer.valueOf(this.request.getParameter("typeId"));
/* 165 */       String regionId = this.request.getParameter("regionId");
/* 166 */       boolean isProtected = "1".equals(this.request.getParameter("isProtected"));
/*     */ 
/* 168 */       OrderPrice orderPrice = this.cartManager.countPrice(sessionid, typeId, regionId, Boolean.valueOf(isProtected));
/*     */ 
/* 171 */       putData("orderPrice", orderPrice);
/*     */ 
/* 173 */       setActionPageName("checkoutTotal");
/*     */     }
/*     */ 
/* 178 */     if ("createOrder".equals(this.action)) {
/*     */       try {
/* 180 */         Order order = createOrder();
/* 181 */         showJson("{result:1,ordersn:" + order.getSn() + "}");
/*     */       }
/*     */       catch (RuntimeException e) {
/* 184 */         showJson("{result:0,message:'" + e.getMessage() + "'}");
/*     */       }
/*     */     }
/*     */ 
/* 188 */     if ("finish".equals(this.action)) {
/* 189 */       String sn = this.request.getParameter("sn");
/* 190 */       if ((sn == null) || (sn.equals(""))) {
/* 191 */         showError("订单号错误，请确认后再提交付款信息！");
/* 192 */         return;
/*     */       }
/* 194 */       Order order = this.orderManager.get(sn);
/* 195 */       if (order == null) {
/* 196 */         showError("订单号错误，请确认后再提交付款信息！");
/* 197 */         return;
/*     */       }
/* 199 */       if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/* 200 */         showError("订单状态错误，请确认后再提交付款信息！");
/* 201 */         return;
/*     */       }
/* 203 */       putData("isLogin", Boolean.valueOf(member != null));
/* 204 */       putData("order", order);
/* 205 */       putData("currentDate", Long.valueOf(System.currentTimeMillis()));
/* 206 */       setActionPageName("finish");
/*     */     }
/*     */ 
/* 212 */     if ("savepay".equals(this.action)) {
/* 213 */       String ordersn = this.request.getParameter("ordersn");
/* 214 */       String bank = this.request.getParameter("bank");
/* 215 */       String paydate = this.request.getParameter("paydate");
/* 216 */       String sn = this.request.getParameter("sn");
/* 217 */       String paymoney = this.request.getParameter("paymoney");
/* 218 */       String remark = this.request.getParameter("remark");
/* 219 */       String from = this.request.getParameter("from");
/* 220 */       if (StringUtil.isEmpty(paymoney)) {
/* 221 */         showError("付款金额不能为空，请确认后再提交付款信息！");
/* 222 */         return;
/*     */       }
/* 224 */       if (!StringUtil.checkFloat(paymoney, "0+")) {
/* 225 */         showError("付款金额格式不正确，请确认后再提交付款信息！");
/* 226 */         return;
/*     */       }
/* 228 */       if ((ordersn == null) || (ordersn.equals(""))) {
/* 229 */         showError("订单号错误，请确认后再提交付款信息！");
/* 230 */         return;
/*     */       }
/* 232 */       Order order = this.orderManager.get(ordersn);
/* 233 */       if (order == null) {
/* 234 */         showError("订单号错误，请确认后再提交付款信息！");
/* 235 */         return;
/*     */       }
/* 237 */       if ((order.getStatus() == null) || (order.getStatus().intValue() != 0)) {
/* 238 */         showError("订单状态错误，请确认后再提交付款信息！");
/* 239 */         return;
/*     */       }
/*     */ 
/* 245 */       PaymentLog paymentLog = new PaymentLog();
/*     */ 
/* 247 */       if (member != null) {
/* 248 */         paymentLog.setMember_id(member.getMember_id());
/* 249 */         paymentLog.setPay_user(member.getUname());
/*     */       } else {
/* 251 */         paymentLog.setPay_user("匿名购买者");
/*     */       }
/* 253 */       paymentLog.setPay_date(DateUtil.getDatelineLong(paydate));
/* 254 */       paymentLog.setRemark(remark);
/* 255 */       paymentLog.setMoney(Double.valueOf(paymoney));
/* 256 */       paymentLog.setOrder_sn(order.getSn());
/* 257 */       paymentLog.setPay_method(bank);
/* 258 */       paymentLog.setSn(sn);
/* 259 */       paymentLog.setOrder_id(order.getOrder_id().intValue());
/* 260 */       this.orderFlowManager.pay(paymentLog, false);
/*     */ 
/* 262 */       String url = null;
/* 263 */       if (member != null)
/* 264 */         url = "member_orderdetail_" + ordersn + ".html";
/*     */       else {
/* 266 */         url = "orderdetail_" + ordersn + ".html";
/*     */       }
/* 268 */       showSuccess("提交付款记录成功！", "查看订单", url);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Order createOrder()
/*     */   {
/* 278 */     Integer addressId = getIntParam("addressId");
/*     */ 
/* 280 */     if ((addressId == null) && (addressId.intValue() != 0)) throw new RuntimeException("收货地址不能为空");
/*     */ 
/* 282 */     Integer shippingId = getIntParam("typeId");
/* 283 */     if (shippingId == null) throw new RuntimeException("配送方式不能为空");
/*     */ 
/* 285 */     Integer paymentId = getIntParam("paymentId");
/* 286 */     if (paymentId == null) throw new RuntimeException("支付方式不能为空");
/*     */ 
/* 288 */     Order order = new Order();
/* 289 */     order.setShipping_id(shippingId);
/* 290 */     order.setPayment_id(paymentId);
/*     */ 
/* 292 */     MemberAddress address = null;
/* 293 */     if (addressId.intValue() == 0)
/* 294 */       address = createAddress();
/*     */     else {
/* 296 */       address = this.memberAddressManager.getAddress(addressId.intValue());
/*     */     }
/*     */ 
/* 300 */     order.setShip_addr(address.getAddr());
/* 301 */     order.setShip_mobile(address.getMobile());
/* 302 */     order.setShip_tel(address.getTel());
/* 303 */     order.setShip_zip(address.getZip());
/* 304 */     order.setShipping_area(address.getProvince() + "-" + address.getCity() + "-" + address.getRegion());
/* 305 */     order.setShip_name(address.getName());
/* 306 */     order.setRegionid(address.getRegion_id());
/* 307 */     if (addressId.intValue() == 0)
/*     */     {
/* 309 */       if (UserServiceFactory.getUserService().getCurrentMember() != null) {
/* 310 */         this.memberAddressManager.addAddress(address);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 315 */     order.setShip_day(getStringParam("shipDay"));
/* 316 */     order.setShip_time(getStringParam("shipTime"));
/* 317 */     order.setRemark(getStringParam("remark"));
/* 318 */     return this.orderManager.add(order, this.request.getSession().getId());
/*     */   }
/*     */ 
/*     */   private MemberAddress createAddress()
/*     */   {
/* 324 */     MemberAddress address = new MemberAddress();
/*     */ 
/* 327 */     String name = this.request.getParameter("shipName");
/* 328 */     address.setName(name);
/*     */ 
/* 330 */     String tel = this.request.getParameter("shipTel");
/* 331 */     address.setTel(tel);
/*     */ 
/* 333 */     String mobile = this.request.getParameter("shipMobile");
/* 334 */     address.setMobile(mobile);
/*     */ 
/* 336 */     String province_id = this.request.getParameter("province_id");
/* 337 */     address.setProvince_id(Integer.valueOf(province_id));
/*     */ 
/* 339 */     String city_id = this.request.getParameter("city_id");
/* 340 */     address.setCity_id(Integer.valueOf(city_id));
/*     */ 
/* 342 */     String region_id = this.request.getParameter("region_id");
/* 343 */     address.setRegion_id(Integer.valueOf(region_id));
/*     */ 
/* 345 */     String province = this.request.getParameter("province");
/* 346 */     address.setProvince(province);
/*     */ 
/* 348 */     String city = this.request.getParameter("city");
/* 349 */     address.setCity(city);
/*     */ 
/* 351 */     String region = this.request.getParameter("region");
/* 352 */     address.setRegion(region);
/*     */ 
/* 354 */     String addr = this.request.getParameter("shipAddr");
/* 355 */     address.setAddr(addr);
/*     */ 
/* 357 */     String zip = this.request.getParameter("shipZip");
/* 358 */     address.setZip(zip);
/*     */ 
/* 360 */     return address;
/*     */   }
/*     */ 
/*     */   private String getStringParam(String name)
/*     */   {
/* 366 */     return this.request.getParameter(name);
/*     */   }
/*     */ 
/*     */   private Integer getIntParam(String name) {
/*     */     try {
/* 371 */       return Integer.valueOf(this.request.getParameter(name));
/*     */     } catch (RuntimeException e) {
/* 373 */       e.printStackTrace();
/* 374 */     }return null;
/*     */   }
/*     */ 
/*     */   public IMemberAddressManager getMemberAddressManager()
/*     */   {
/* 379 */     return this.memberAddressManager;
/*     */   }
/*     */ 
/*     */   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
/* 383 */     this.memberAddressManager = memberAddressManager;
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager() {
/* 387 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/* 391 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public ICartManager getCartManager() {
/* 395 */     return this.cartManager;
/*     */   }
/*     */ 
/*     */   public void setCartManager(ICartManager cartManager) {
/* 399 */     this.cartManager = cartManager;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager() {
/* 403 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 407 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 411 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 415 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager() {
/* 419 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 423 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager()
/*     */   {
/* 428 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager)
/*     */   {
/* 433 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.cart.checkout.CheckOutWidget
 * JD-Core Version:    0.6.0
 */