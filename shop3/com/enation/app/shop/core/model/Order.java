/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.app.shop.core.model.support.OrderPrice;
/*     */ import com.enation.app.shop.core.service.OrderStatus;
/*     */ import com.enation.framework.database.DynamicField;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.util.CurrencyUtil;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Order extends DynamicField
/*     */   implements Serializable
/*     */ {
/*     */   private Integer order_id;
/*     */   private String sn;
/*     */   private Integer member_id;
/*     */   private Integer status;
/*     */   private Integer pay_status;
/*     */   private Integer ship_status;
/*     */   private String shipStatus;
/*     */   private String payStatus;
/*     */   private String orderStatus;
/*     */   private Integer regionid;
/*     */   private Integer shipping_id;
/*     */   private String shipping_type;
/*     */   private String shipping_area;
/*     */   private String goods;
/*     */   private Long create_time;
/*     */   private String ship_name;
/*     */   private String ship_addr;
/*     */   private String ship_zip;
/*     */   private String ship_email;
/*     */   private String ship_mobile;
/*     */   private String ship_tel;
/*     */   private String ship_day;
/*     */   private String ship_time;
/*     */   private Integer is_protect;
/*     */   private Double protect_price;
/*     */   private Double goods_amount;
/*     */   private Double shipping_amount;
/*     */   private Double discount;
/*     */   private Double order_amount;
/*     */   private Double weight;
/*     */   private Double paymoney;
/*     */   private String remark;
/*     */   private Integer disabled;
/*     */   private Integer payment_id;
/*     */   private String payment_name;
/*     */   private String payment_type;
/*     */   private Integer goods_num;
/*     */   private int gainedpoint;
/*     */   private int consumepoint;
/*     */   private Integer depotid;
/*     */   private String cancel_reason;
/*     */   private int sale_cmpl;
/*     */   private Integer sale_cmpl_time;
/*     */   private Integer ship_provinceid;
/*     */   private Integer ship_cityid;
/*     */   private Integer ship_regionid;
/*     */   private Integer signing_time;
/*     */   private String the_sign;
/*     */   private Long allocation_time;
/*     */   private String admin_remark;
/*     */   private OrderPrice orderprice;
/*     */ 
/*     */   public Long getAllocation_time()
/*     */   {
/* 112 */     return this.allocation_time;
/*     */   }
/*     */ 
/*     */   public void setAllocation_time(Long allocation_time) {
/* 116 */     this.allocation_time = allocation_time;
/*     */   }
/*     */ 
/*     */   public Integer getGoods_num() {
/* 120 */     return this.goods_num;
/*     */   }
/*     */ 
/*     */   public void setGoods_num(Integer goodsNum) {
/* 124 */     this.goods_num = goodsNum;
/*     */   }
/*     */ 
/*     */   public Long getCreate_time() {
/* 128 */     return this.create_time;
/*     */   }
/*     */ 
/*     */   public void setCreate_time(Long create_time) {
/* 132 */     this.create_time = create_time;
/*     */   }
/*     */ 
/*     */   public String getGoods() {
/* 136 */     return this.goods;
/*     */   }
/*     */ 
/*     */   public void setGoods(String goods) {
/* 140 */     this.goods = goods;
/*     */   }
/*     */ 
/*     */   public Double getGoods_amount() {
/* 144 */     return this.goods_amount;
/*     */   }
/*     */ 
/*     */   public void setGoods_amount(Double goods_amount) {
/* 148 */     this.goods_amount = goods_amount;
/*     */   }
/*     */ 
/*     */   public Integer getIs_protect() {
/* 152 */     this.is_protect = Integer.valueOf(this.is_protect == null ? 0 : this.is_protect.intValue());
/* 153 */     return this.is_protect;
/*     */   }
/*     */ 
/*     */   public void setIs_protect(Integer is_protect) {
/* 157 */     this.is_protect = is_protect;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/* 161 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer member_id) {
/* 165 */     this.member_id = member_id;
/*     */   }
/*     */ 
/*     */   public Double getOrder_amount()
/*     */   {
/* 170 */     return Double.valueOf(this.order_amount == null ? 0.0D : this.order_amount.doubleValue());
/*     */   }
/*     */ 
/*     */   public void setOrder_amount(Double order_amount) {
/* 174 */     this.order_amount = order_amount;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getOrder_id() {
/* 179 */     return this.order_id;
/*     */   }
/*     */ 
/*     */   public void setOrder_id(Integer order_id) {
/* 183 */     this.order_id = order_id;
/*     */   }
/*     */ 
/*     */   public Integer getPay_status() {
/* 187 */     return this.pay_status;
/*     */   }
/*     */ 
/*     */   public void setPay_status(Integer pay_status) {
/* 191 */     this.pay_status = pay_status;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 195 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 199 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getShip_addr() {
/* 203 */     return this.ship_addr;
/*     */   }
/*     */ 
/*     */   public void setShip_addr(String ship_addr) {
/* 207 */     this.ship_addr = ship_addr;
/*     */   }
/*     */ 
/*     */   public String getShip_day() {
/* 211 */     return this.ship_day;
/*     */   }
/*     */ 
/*     */   public void setShip_day(String ship_day) {
/* 215 */     this.ship_day = ship_day;
/*     */   }
/*     */ 
/*     */   public String getShip_email() {
/* 219 */     return this.ship_email;
/*     */   }
/*     */ 
/*     */   public void setShip_email(String ship_email) {
/* 223 */     this.ship_email = ship_email;
/*     */   }
/*     */ 
/*     */   public String getShip_mobile() {
/* 227 */     return this.ship_mobile;
/*     */   }
/*     */ 
/*     */   public void setShip_mobile(String ship_mobile) {
/* 231 */     this.ship_mobile = ship_mobile;
/*     */   }
/*     */ 
/*     */   public String getShip_name() {
/* 235 */     return this.ship_name;
/*     */   }
/*     */ 
/*     */   public void setShip_name(String ship_name) {
/* 239 */     this.ship_name = ship_name;
/*     */   }
/*     */ 
/*     */   public Integer getShip_status() {
/* 243 */     return this.ship_status;
/*     */   }
/*     */ 
/*     */   public void setShip_status(Integer ship_status) {
/* 247 */     this.ship_status = ship_status;
/*     */   }
/*     */ 
/*     */   public String getShip_tel() {
/* 251 */     return this.ship_tel;
/*     */   }
/*     */ 
/*     */   public void setShip_tel(String ship_tel) {
/* 255 */     this.ship_tel = ship_tel;
/*     */   }
/*     */ 
/*     */   public String getShip_time() {
/* 259 */     return this.ship_time;
/*     */   }
/*     */ 
/*     */   public void setShip_time(String ship_time) {
/* 263 */     this.ship_time = ship_time;
/*     */   }
/*     */ 
/*     */   public String getShip_zip() {
/* 267 */     return this.ship_zip;
/*     */   }
/*     */ 
/*     */   public void setShip_zip(String ship_zip) {
/* 271 */     this.ship_zip = ship_zip;
/*     */   }
/*     */ 
/*     */   public Double getShipping_amount() {
/* 275 */     return this.shipping_amount;
/*     */   }
/*     */ 
/*     */   public void setShipping_amount(Double shipping_amount) {
/* 279 */     this.shipping_amount = shipping_amount;
/*     */   }
/*     */ 
/*     */   public String getShipping_area() {
/* 283 */     return this.shipping_area;
/*     */   }
/*     */ 
/*     */   public void setShipping_area(String shipping_area) {
/* 287 */     this.shipping_area = shipping_area;
/*     */   }
/*     */ 
/*     */   public Integer getShipping_id() {
/* 291 */     return this.shipping_id;
/*     */   }
/*     */ 
/*     */   public void setShipping_id(Integer shipping_id) {
/* 295 */     this.shipping_id = shipping_id;
/*     */   }
/*     */ 
/*     */   public String getShipping_type() {
/* 299 */     return this.shipping_type;
/*     */   }
/*     */ 
/*     */   public void setShipping_type(String shipping_type) {
/* 303 */     this.shipping_type = shipping_type;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 307 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 311 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public Integer getStatus() {
/* 315 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status) {
/* 319 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Double getWeight() {
/* 323 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(Double weight) {
/* 327 */     this.weight = weight;
/*     */   }
/*     */ 
/*     */   public Double getProtect_price() {
/* 331 */     return this.protect_price;
/*     */   }
/*     */ 
/*     */   public void setProtect_price(Double protect_price) {
/* 335 */     this.protect_price = protect_price;
/*     */   }
/*     */ 
/*     */   public Integer getDisabled() {
/* 339 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Integer disabled) {
/* 343 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Integer getPayment_id() {
/* 347 */     return this.payment_id;
/*     */   }
/*     */ 
/*     */   public void setPayment_id(Integer payment_id) {
/* 351 */     this.payment_id = payment_id;
/*     */   }
/*     */ 
/*     */   public String getPayment_name() {
/* 355 */     return this.payment_name;
/*     */   }
/*     */ 
/*     */   public void setPayment_name(String payment_name) {
/* 359 */     this.payment_name = payment_name;
/*     */   }
/*     */ 
/*     */   public Double getPaymoney() {
/* 363 */     return Double.valueOf(this.paymoney == null ? 0.0D : this.paymoney.doubleValue());
/*     */   }
/*     */ 
/*     */   public void setPaymoney(Double paymoney) {
/* 367 */     this.paymoney = paymoney;
/*     */   }
/*     */ 
/*     */   public int getGainedpoint() {
/* 371 */     return this.gainedpoint;
/*     */   }
/*     */ 
/*     */   public void setGainedpoint(int gainedpoint) {
/* 375 */     this.gainedpoint = gainedpoint;
/*     */   }
/*     */ 
/*     */   public int getConsumepoint() {
/* 379 */     return this.consumepoint;
/*     */   }
/*     */ 
/*     */   public void setConsumepoint(int consumepoint) {
/* 383 */     this.consumepoint = consumepoint;
/*     */   }
/*     */   @NotDbField
/*     */   public Integer getRegionid() {
/* 388 */     return this.regionid;
/*     */   }
/*     */ 
/*     */   public void setRegionid(Integer regionid) {
/* 392 */     this.regionid = regionid;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getShipStatus() {
/* 398 */     this.shipStatus = OrderStatus.getShipStatusText(this.ship_status.intValue());
/* 399 */     return this.shipStatus;
/*     */   }
/*     */ 
/*     */   public void setShipStatus(String shipStatus) {
/* 403 */     this.shipStatus = shipStatus;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getPayStatus()
/*     */   {
/* 410 */     this.payStatus = OrderStatus.getPayStatusText(this.pay_status.intValue());
/*     */ 
/* 412 */     return this.payStatus;
/*     */   }
/*     */ 
/*     */   public void setPayStatus(String payStatus) {
/* 416 */     this.payStatus = payStatus;
/*     */   }
/*     */   @NotDbField
/*     */   public String getOrderStatus() {
/* 421 */     this.orderStatus = OrderStatus.getOrderStatusText(this.status.intValue());
/* 422 */     return this.orderStatus;
/*     */   }
/*     */ 
/*     */   public void setOrderStatus(String orderStatus) {
/* 426 */     this.orderStatus = orderStatus;
/*     */   }
/*     */ 
/*     */   public String getPayment_type() {
/* 430 */     return this.payment_type;
/*     */   }
/*     */ 
/*     */   public void setPayment_type(String paymentType) {
/* 434 */     this.payment_type = paymentType;
/*     */   }
/*     */ 
/*     */   public Double getDiscount() {
/* 438 */     return this.discount;
/*     */   }
/*     */ 
/*     */   public void setDiscount(Double discount) {
/* 442 */     this.discount = discount;
/*     */   }
/*     */ 
/*     */   public Integer getDepotid()
/*     */   {
/* 448 */     return this.depotid;
/*     */   }
/*     */ 
/*     */   public void setDepotid(Integer depotid) {
/* 452 */     this.depotid = depotid;
/*     */   }
/*     */ 
/*     */   public String getCancel_reason() {
/* 456 */     return this.cancel_reason;
/*     */   }
/*     */ 
/*     */   public void setCancel_reason(String cancel_reason) {
/* 460 */     this.cancel_reason = cancel_reason;
/*     */   }
/*     */ 
/*     */   public int getSale_cmpl() {
/* 464 */     return this.sale_cmpl;
/*     */   }
/*     */ 
/*     */   public void setSale_cmpl(int sale_cmpl) {
/* 468 */     this.sale_cmpl = sale_cmpl;
/*     */   }
/*     */ 
/*     */   public Integer getShip_provinceid()
/*     */   {
/* 473 */     return this.ship_provinceid;
/*     */   }
/*     */ 
/*     */   public void setShip_provinceid(Integer ship_provinceid) {
/* 477 */     this.ship_provinceid = ship_provinceid;
/*     */   }
/*     */ 
/*     */   public Integer getShip_cityid() {
/* 481 */     return this.ship_cityid;
/*     */   }
/*     */ 
/*     */   public void setShip_cityid(Integer ship_cityid) {
/* 485 */     this.ship_cityid = ship_cityid;
/*     */   }
/*     */ 
/*     */   public Integer getShip_regionid() {
/* 489 */     return this.ship_regionid;
/*     */   }
/*     */ 
/*     */   public void setShip_regionid(Integer ship_regionid) {
/* 493 */     this.ship_regionid = ship_regionid;
/*     */   }
/*     */ 
/*     */   public Integer getSale_cmpl_time() {
/* 497 */     return this.sale_cmpl_time;
/*     */   }
/*     */ 
/*     */   public void setSale_cmpl_time(Integer sale_cmpl_time) {
/* 501 */     this.sale_cmpl_time = sale_cmpl_time;
/*     */   }
/*     */ 
/*     */   public Integer getSigning_time() {
/* 505 */     return this.signing_time;
/*     */   }
/*     */ 
/*     */   public void setSigning_time(Integer signing_time) {
/* 509 */     this.signing_time = signing_time;
/*     */   }
/*     */ 
/*     */   public String getThe_sign() {
/* 513 */     return this.the_sign;
/*     */   }
/*     */ 
/*     */   public void setThe_sign(String the_sign) {
/* 517 */     this.the_sign = the_sign;
/*     */   }
/*     */ 
/*     */   public String getAdmin_remark()
/*     */   {
/* 523 */     return this.admin_remark;
/*     */   }
/*     */ 
/*     */   public void setAdmin_remark(String admin_remark) {
/* 527 */     this.admin_remark = admin_remark;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public OrderPrice getOrderprice() {
/* 533 */     return this.orderprice;
/*     */   }
/*     */ 
/*     */   public void setOrderprice(OrderPrice orderprice) {
/* 537 */     this.orderprice = orderprice;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public boolean getIsCod()
/*     */   {
/* 549 */     return "cod".equals(getPayment_type());
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public boolean getIsOnlinePay()
/*     */   {
/* 568 */     return (!"offline".equals(this.payment_type)) && (!"deposit".equals(this.payment_type)) && (!"cod".equals(this.payment_type));
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public Double getNeedPayMoney()
/*     */   {
/* 580 */     return Double.valueOf(CurrencyUtil.sub(getOrder_amount().doubleValue(), getPaymoney().doubleValue()));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Order
 * JD-Core Version:    0.6.0
 */