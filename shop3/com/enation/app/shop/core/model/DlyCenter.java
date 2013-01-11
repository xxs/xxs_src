/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DlyCenter
/*     */   implements Serializable
/*     */ {
/*     */   private Integer dly_center_id;
/*     */   private String name;
/*     */   private String address;
/*     */   private String province;
/*     */   private String city;
/*     */   private String region;
/*     */   private Integer province_id;
/*     */   private Integer city_id;
/*     */   private Integer region_id;
/*     */   private String zip;
/*     */   private String phone;
/*     */   private String uname;
/*     */   private String cellphone;
/*     */   private String sex;
/*     */   private String memo;
/*     */   private String disabled;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getDly_center_id()
/*     */   {
/*  32 */     return this.dly_center_id;
/*     */   }
/*     */   public void setDly_center_id(Integer dlyCenterId) {
/*  35 */     this.dly_center_id = dlyCenterId;
/*     */   }
/*     */   public String getName() {
/*  38 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  41 */     this.name = name;
/*     */   }
/*     */   public String getAddress() {
/*  44 */     return this.address;
/*     */   }
/*     */   public void setAddress(String address) {
/*  47 */     this.address = address;
/*     */   }
/*     */   public String getProvince() {
/*  50 */     return this.province;
/*     */   }
/*     */   public void setProvince(String province) {
/*  53 */     this.province = province;
/*     */   }
/*     */   public String getCity() {
/*  56 */     return this.city;
/*     */   }
/*     */   public void setCity(String city) {
/*  59 */     this.city = city;
/*     */   }
/*     */   public String getRegion() {
/*  62 */     return this.region;
/*     */   }
/*     */   public void setRegion(String region) {
/*  65 */     this.region = region;
/*     */   }
/*     */ 
/*     */   public Integer getProvince_id() {
/*  69 */     return this.province_id;
/*     */   }
/*     */   public void setProvince_id(Integer provinceId) {
/*  72 */     this.province_id = provinceId;
/*     */   }
/*     */   public Integer getCity_id() {
/*  75 */     return this.city_id;
/*     */   }
/*     */   public void setCity_id(Integer cityId) {
/*  78 */     this.city_id = cityId;
/*     */   }
/*     */   public Integer getRegion_id() {
/*  81 */     return this.region_id;
/*     */   }
/*     */   public void setRegion_id(Integer regionId) {
/*  84 */     this.region_id = regionId;
/*     */   }
/*     */   public String getZip() {
/*  87 */     return this.zip;
/*     */   }
/*     */   public void setZip(String zip) {
/*  90 */     this.zip = zip;
/*     */   }
/*     */   public String getPhone() {
/*  93 */     return this.phone;
/*     */   }
/*     */   public void setPhone(String phone) {
/*  96 */     this.phone = phone;
/*     */   }
/*     */   public String getUname() {
/*  99 */     return this.uname;
/*     */   }
/*     */   public void setUname(String uname) {
/* 102 */     this.uname = uname;
/*     */   }
/*     */   public String getCellphone() {
/* 105 */     return this.cellphone;
/*     */   }
/*     */   public void setCellphone(String cellphone) {
/* 108 */     this.cellphone = cellphone;
/*     */   }
/*     */   public String getSex() {
/* 111 */     return this.sex;
/*     */   }
/*     */   public void setSex(String sex) {
/* 114 */     this.sex = sex;
/*     */   }
/*     */   public String getMemo() {
/* 117 */     return this.memo;
/*     */   }
/*     */   public void setMemo(String memo) {
/* 120 */     this.memo = memo;
/*     */   }
/*     */   public String getDisabled() {
/* 123 */     return this.disabled;
/*     */   }
/*     */   public void setDisabled(String disabled) {
/* 126 */     this.disabled = disabled;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.DlyCenter
 * JD-Core Version:    0.6.0
 */