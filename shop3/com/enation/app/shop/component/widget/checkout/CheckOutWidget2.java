/*     */ package com.enation.app.shop.component.widget.checkout;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberAddress;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.app.shop.core.service.ICartManager;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.IMemberAddressManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPaymentManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
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
/*     */ @Component
/*     */ @Scope("prototype")
/*     */ public class CheckOutWidget2 extends AbstractWidget
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private IMemberAddressManager memberAddressManager;
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   protected ICartManager cartManager;
/*     */   private IPaymentManager paymentManager;
/*     */   private IOrderManager orderManager;
/*     */   private IPromotionManager promotionManager;
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
/*  62 */     this.request = ThreadContextHolder.getHttpRequest();
/*  63 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/*  65 */     String sessionid = this.request.getSession().getId();
/*     */ 
/*  67 */     if ((this.action == null) || (this.action.equals("")))
/*     */     {
/*  69 */       setPageName("checkout");
/*     */ 
/*  71 */       if (member != null)
/*     */       {
/*  73 */         List addressList = this.memberAddressManager.listAddress();
/*  74 */         MemberAddress defaultAddress = getDefaultAddress(addressList);
/*     */ 
/*  77 */         putData("defaultAddress", defaultAddress);
/*  78 */         putData("addressList", addressList);
/*  79 */         putData("isLogin", Boolean.valueOf(true));
/*     */       }
/*     */       else {
/*  82 */         putData("addressList", new ArrayList());
/*  83 */         putData("isLogin", Boolean.valueOf(false));
/*     */       }
/*     */ 
/*  88 */       List goodsItemList = this.cartManager.listGoods(sessionid);
/*     */ 
/*  90 */       if ((goodsItemList == null) || (goodsItemList.isEmpty()))
/*     */       {
/*  92 */         showError("您的购物车中暂无商品", "首页", "index.html");
/*  93 */         return;
/*     */       }
/*     */ 
/*  96 */       putData("goodsItemList", goodsItemList);
/*     */ 
/*  98 */       putData("GoodsPic", new ImageUrlDirectiveModel());
/*     */ 
/* 100 */       if ((goodsItemList == null) || (goodsItemList.isEmpty()))
/* 101 */         putData("hasGoods", Boolean.valueOf(false));
/*     */       else {
/* 103 */         putData("hasGoods", Boolean.valueOf(true));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     if ("showAddress".equals(this.action)) {
/* 110 */       showAddress();
/*     */     }
/*     */ 
/* 117 */     if ("shopPaymentList".equals(this.action)) {
/* 118 */       showPaymentList();
/*     */     }
/*     */ 
/* 123 */     if ("showDlyType".equals(this.action))
/*     */     {
/* 125 */       Double orderPrice = this.cartManager.countGoodsTotal(sessionid);
/* 126 */       Double weight = this.cartManager.countGoodsWeight(sessionid);
/*     */ 
/* 128 */       List dlyTypeList = this.dlyTypeManager.list(weight, orderPrice, this.request.getParameter("regionid"));
/* 129 */       putData("dlyTypeList", dlyTypeList);
/* 130 */       setActionPageName("dlytype_list");
/*     */     }
/*     */ 
/* 137 */     if ("showOrderTotal".equals(this.action)) {
/* 138 */       showOrderTotal(sessionid);
/*     */     }
/*     */ 
/* 144 */     if ("createOrder".equals(this.action))
/*     */       try {
/* 146 */         Order order = createOrder();
/* 147 */         showJson("{result:1,ordersn:" + order.getSn() + "}");
/*     */       }
/*     */       catch (RuntimeException e) {
/* 150 */         showJson("{result:0,message:'" + e.getMessage() + "'}");
/*     */       }
/*     */   }
/*     */ 
/*     */   private void showAddress()
/*     */   {
/* 160 */     Integer addr_id = Integer.valueOf(this.request.getParameter("addr_id"));
/* 161 */     MemberAddress address = this.memberAddressManager.getAddress(addr_id.intValue());
/* 162 */     showJson(JSONObject.fromObject(address).toString());
/*     */   }
/*     */ 
/*     */   private void showPaymentList()
/*     */   {
/* 173 */     String iscod = this.request.getParameter("iscod");
/*     */ 
/* 176 */     List paymentList = this.paymentManager.list();
/* 177 */     putData("paymentList", paymentList);
/*     */ 
/* 179 */     setActionPageName("payment_list");
/*     */   }
/*     */ 
/*     */   private void showOrderTotal(String sessionid)
/*     */   {
/* 184 */     Integer typeId = Integer.valueOf(this.request.getParameter("typeId"));
/* 185 */     String regionId = this.request.getParameter("regionId");
/* 186 */     boolean isProtected = "1".equals(this.request.getParameter("isProtected"));
/*     */ 
/* 188 */     OrderPrice orderPrice = this.cartManager.countPrice(sessionid, typeId, regionId, Boolean.valueOf(isProtected));
/*     */ 
/* 190 */     putData("orderPrice", orderPrice);
/*     */ 
/* 192 */     setActionPageName("checkout_total");
/*     */   }
/*     */ 
/*     */   private MemberAddress getDefaultAddress(List<MemberAddress> addressList)
/*     */   {
/* 207 */     if ((addressList != null) && (!addressList.isEmpty())) {
/* 208 */       for (MemberAddress address : addressList) {
/* 209 */         if ((address.getDef_addr() != null) && (address.getDef_addr().intValue() == 1))
/*     */         {
/* 211 */           address.setDef_addr(Integer.valueOf(1));
/* 212 */           return address;
/*     */         }
/*     */       }
/*     */ 
/* 216 */       MemberAddress defAddress = (MemberAddress)addressList.get(0);
/* 217 */       defAddress.setDef_addr(Integer.valueOf(1));
/* 218 */       return defAddress;
/*     */     }
/*     */ 
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   private Order createOrder()
/*     */   {
/* 226 */     Integer shippingId = getIntParam("typeId");
/* 227 */     if (shippingId == null) throw new RuntimeException("配送方式不能为空");
/*     */ 
/* 229 */     Integer paymentId = getIntParam("paymentId");
/* 230 */     if (paymentId == null) throw new RuntimeException("支付方式不能为空");
/*     */ 
/* 232 */     Order order = new Order();
/* 233 */     order.setShipping_id(shippingId);
/* 234 */     order.setPayment_id(paymentId);
/* 235 */     Integer addressId = Integer.valueOf(StringUtil.toInt(this.request.getParameter("addressId"), false));
/*     */ 
/* 237 */     MemberAddress address = null;
/*     */ 
/* 239 */     address = createAddress();
/*     */ 
/* 241 */     order.setShip_provinceid(address.getProvince_id());
/* 242 */     order.setShip_cityid(address.getCity_id());
/* 243 */     order.setShip_regionid(address.getRegion_id());
/*     */ 
/* 245 */     order.setShip_addr(address.getAddr());
/* 246 */     order.setShip_mobile(address.getMobile());
/* 247 */     order.setShip_tel(address.getTel());
/* 248 */     order.setShip_zip(address.getZip());
/* 249 */     order.setShipping_area(address.getProvince() + "-" + address.getCity() + "-" + address.getRegion());
/* 250 */     order.setShip_name(address.getName());
/* 251 */     order.setRegionid(address.getRegion_id());
/*     */ 
/* 255 */     if (("yes".equals(this.request.getParameter("saveAddress"))) && 
/* 256 */       (UserServiceFactory.getUserService().getCurrentMember() != null)) {
/* 257 */       address.setAddr_id(null);
/* 258 */       this.memberAddressManager.addAddress(address);
/*     */     }
/*     */ 
/* 263 */     order.setShip_day(getStringParam("shipDay"));
/* 264 */     order.setShip_time(getStringParam("shipTime"));
/* 265 */     order.setRemark(getStringParam("remark"));
/* 266 */     return this.orderManager.add(order, this.request.getSession().getId());
/*     */   }
/*     */ 
/*     */   private MemberAddress createAddress()
/*     */   {
/* 272 */     MemberAddress address = new MemberAddress();
/*     */ 
/* 275 */     String name = this.request.getParameter("shipName");
/* 276 */     address.setName(name);
/*     */ 
/* 278 */     String tel = this.request.getParameter("shipTel");
/* 279 */     address.setTel(tel);
/*     */ 
/* 281 */     String mobile = this.request.getParameter("shipMobile");
/* 282 */     address.setMobile(mobile);
/*     */ 
/* 284 */     String province_id = this.request.getParameter("province_id");
/* 285 */     address.setProvince_id(Integer.valueOf(province_id));
/*     */ 
/* 287 */     String city_id = this.request.getParameter("city_id");
/* 288 */     address.setCity_id(Integer.valueOf(city_id));
/*     */ 
/* 290 */     String region_id = this.request.getParameter("region_id");
/* 291 */     address.setRegion_id(Integer.valueOf(region_id));
/*     */ 
/* 293 */     String province = this.request.getParameter("province");
/* 294 */     address.setProvince(province);
/*     */ 
/* 296 */     String city = this.request.getParameter("city");
/* 297 */     address.setCity(city);
/*     */ 
/* 299 */     String region = this.request.getParameter("region");
/* 300 */     address.setRegion(region);
/*     */ 
/* 302 */     String addr = this.request.getParameter("shipAddr");
/* 303 */     address.setAddr(addr);
/*     */ 
/* 305 */     String zip = this.request.getParameter("shipZip");
/* 306 */     address.setZip(zip);
/*     */ 
/* 308 */     return address;
/*     */   }
/*     */ 
/*     */   private String getStringParam(String name)
/*     */   {
/* 314 */     return this.request.getParameter(name);
/*     */   }
/*     */ 
/*     */   private Integer getIntParam(String name) {
/*     */     try {
/* 319 */       return Integer.valueOf(this.request.getParameter(name));
/*     */     } catch (RuntimeException e) {
/*     */     }
/* 322 */     return null;
/*     */   }
/*     */ 
/*     */   public IMemberAddressManager getMemberAddressManager()
/*     */   {
/* 327 */     return this.memberAddressManager;
/*     */   }
/*     */ 
/*     */   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
/* 331 */     this.memberAddressManager = memberAddressManager;
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager() {
/* 335 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/* 339 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public ICartManager getCartManager() {
/* 343 */     return this.cartManager;
/*     */   }
/*     */ 
/*     */   public void setCartManager(ICartManager cartManager) {
/* 347 */     this.cartManager = cartManager;
/*     */   }
/*     */ 
/*     */   public IPaymentManager getPaymentManager() {
/* 351 */     return this.paymentManager;
/*     */   }
/*     */ 
/*     */   public void setPaymentManager(IPaymentManager paymentManager) {
/* 355 */     this.paymentManager = paymentManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 359 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 363 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager() {
/* 367 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 371 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.checkout.CheckOutWidget2
 * JD-Core Version:    0.6.0
 */