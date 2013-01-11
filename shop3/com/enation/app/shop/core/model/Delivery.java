/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Delivery
/*     */   implements Serializable
/*     */ {
/*     */   private Integer delivery_id;
/*     */   private Integer order_id;
/*     */   private Integer type;
/*     */   private Integer member_id;
/*     */   private String member_name;
/*     */   private String sn;
/*     */   private Double money;
/*     */   private String ship_type;
/*     */   private int is_protect;
/*     */   private Double protect_price;
/*     */   private Integer logi_id;
/*     */   private String logi_name;
/*     */   private String logi_code;
/*     */   private String logi_no;
/*     */   private String ship_name;
/*     */   private int province_id;
/*     */   private int city_id;
/*     */   private int region_id;
/*     */   private String province;
/*     */   private String city;
/*     */   private String region;
/*     */   private String ship_addr;
/*     */   private String ship_zip;
/*     */   private String ship_tel;
/*     */   private String ship_mobile;
/*     */   private String ship_email;
/*     */   private String op_name;
/*     */   private String remark;
/*     */   private Long create_time;
/*     */   private String reason;
/*     */   private Order order;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getDelivery_id()
/*     */   {
/*  75 */     return this.delivery_id;
/*     */   }
/*     */ 
/*     */   public void setDelivery_id(Integer delivery_id) {
/*  79 */     this.delivery_id = delivery_id;
/*     */   }
/*     */ 
/*     */   public Integer getLogi_id()
/*     */   {
/*  84 */     return this.logi_id;
/*     */   }
/*     */ 
/*     */   public void setLogi_id(Integer logi_id) {
/*  88 */     this.logi_id = logi_id;
/*     */   }
/*     */ 
/*     */   public String getLogi_name() {
/*  92 */     return this.logi_name;
/*     */   }
/*     */ 
/*     */   public void setLogi_name(String logi_name) {
/*  96 */     this.logi_name = logi_name;
/*     */   }
/*     */ 
/*     */   public String getLogi_no() {
/* 100 */     return this.logi_no;
/*     */   }
/*     */ 
/*     */   public void setLogi_no(String logi_no) {
/* 104 */     this.logi_no = logi_no;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/* 108 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer member_id) {
/* 112 */     this.member_id = member_id;
/*     */   }
/*     */ 
/*     */   public Double getMoney() {
/* 116 */     return this.money;
/*     */   }
/*     */ 
/*     */   public void setMoney(Double money) {
/* 120 */     this.money = money;
/*     */   }
/*     */ 
/*     */   public String getOp_name() {
/* 124 */     return this.op_name;
/*     */   }
/*     */ 
/*     */   public void setOp_name(String op_name) {
/* 128 */     this.op_name = op_name;
/*     */   }
/*     */ 
/*     */   public Integer getOrder_id() {
/* 132 */     return this.order_id;
/*     */   }
/*     */ 
/*     */   public void setOrder_id(Integer order_id) {
/* 136 */     this.order_id = order_id;
/*     */   }
/*     */ 
/*     */   public Double getProtect_price() {
/* 140 */     return this.protect_price;
/*     */   }
/*     */ 
/*     */   public void setProtect_price(Double protect_price) {
/* 144 */     this.protect_price = protect_price;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 148 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 152 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public int getProvince_id() {
/* 156 */     return this.province_id;
/*     */   }
/*     */ 
/*     */   public void setProvince_id(int provinceId) {
/* 160 */     this.province_id = provinceId;
/*     */   }
/*     */ 
/*     */   public int getCity_id() {
/* 164 */     return this.city_id;
/*     */   }
/*     */ 
/*     */   public void setCity_id(int cityId) {
/* 168 */     this.city_id = cityId;
/*     */   }
/*     */ 
/*     */   public int getRegion_id() {
/* 172 */     return this.region_id;
/*     */   }
/*     */ 
/*     */   public void setRegion_id(int regionId) {
/* 176 */     this.region_id = regionId;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/* 180 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/* 184 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getCity() {
/* 188 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String city) {
/* 192 */     this.city = city;
/*     */   }
/*     */ 
/*     */   public String getRegion() {
/* 196 */     return this.region;
/*     */   }
/*     */ 
/*     */   public void setRegion(String region) {
/* 200 */     this.region = region;
/*     */   }
/*     */ 
/*     */   public String getShip_addr() {
/* 204 */     return this.ship_addr;
/*     */   }
/*     */ 
/*     */   public void setShip_addr(String ship_addr) {
/* 208 */     this.ship_addr = ship_addr;
/*     */   }
/*     */ 
/*     */   public String getShip_email() {
/* 212 */     return this.ship_email;
/*     */   }
/*     */ 
/*     */   public void setShip_email(String ship_email) {
/* 216 */     this.ship_email = ship_email;
/*     */   }
/*     */ 
/*     */   public String getShip_mobile() {
/* 220 */     return this.ship_mobile;
/*     */   }
/*     */ 
/*     */   public void setShip_mobile(String ship_mobile) {
/* 224 */     this.ship_mobile = ship_mobile;
/*     */   }
/*     */ 
/*     */   public String getShip_name() {
/* 228 */     return this.ship_name;
/*     */   }
/*     */ 
/*     */   public void setShip_name(String ship_name) {
/* 232 */     this.ship_name = ship_name;
/*     */   }
/*     */ 
/*     */   public String getShip_tel() {
/* 236 */     return this.ship_tel;
/*     */   }
/*     */ 
/*     */   public void setShip_tel(String ship_tel) {
/* 240 */     this.ship_tel = ship_tel;
/*     */   }
/*     */ 
/*     */   public String getShip_type() {
/* 244 */     return this.ship_type;
/*     */   }
/*     */ 
/*     */   public void setShip_type(String ship_type) {
/* 248 */     this.ship_type = ship_type;
/*     */   }
/*     */ 
/*     */   public String getShip_zip() {
/* 252 */     return this.ship_zip;
/*     */   }
/*     */ 
/*     */   public void setShip_zip(String ship_zip) {
/* 256 */     this.ship_zip = ship_zip;
/*     */   }
/*     */ 
/*     */   public Integer getType() {
/* 260 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(Integer type) {
/* 264 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Long getCreate_time() {
/* 268 */     return this.create_time;
/*     */   }
/*     */ 
/*     */   public void setCreate_time(Long create_time) {
/* 272 */     this.create_time = create_time;
/*     */   }
/*     */ 
/*     */   public int getIs_protect() {
/* 276 */     return this.is_protect;
/*     */   }
/*     */ 
/*     */   public void setIs_protect(int isProtect) {
/* 280 */     this.is_protect = isProtect;
/*     */   }
/*     */ 
/*     */   public String getReason() {
/* 284 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(String reason) {
/* 288 */     this.reason = reason;
/*     */   }
/*     */   @NotDbField
/*     */   public String getMember_name() {
/* 293 */     return this.member_name;
/*     */   }
/*     */ 
/*     */   public void setMember_name(String memberName) {
/* 297 */     this.member_name = memberName;
/*     */   }
/*     */   @NotDbField
/*     */   public String getSn() {
/* 302 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 306 */     this.sn = sn;
/*     */   }
/*     */   @NotDbField
/*     */   public Order getOrder() {
/* 311 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(Order order) {
/* 315 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getLogi_code() {
/* 319 */     return this.logi_code;
/*     */   }
/*     */ 
/*     */   public void setLogi_code(String logi_code) {
/* 323 */     this.logi_code = logi_code;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Delivery
 * JD-Core Version:    0.6.0
 */