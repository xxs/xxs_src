/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MemberAddress
/*     */   implements Serializable
/*     */ {
/*     */   private Integer addr_id;
/*     */   private Integer member_id;
/*     */   private String name;
/*     */   private String country;
/*     */   private Integer province_id;
/*     */   private Integer city_id;
/*     */   private Integer region_id;
/*     */   private String province;
/*     */   private String city;
/*     */   private String region;
/*     */   private String addr;
/*     */   private String zip;
/*     */   private String tel;
/*     */   private String mobile;
/*     */   private Integer def_addr;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  32 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  36 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getCountry() {
/*  40 */     return this.country;
/*     */   }
/*     */ 
/*     */   public void setCountry(String country) {
/*  44 */     this.country = country;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getAddr_id() {
/*  49 */     return this.addr_id;
/*     */   }
/*     */ 
/*     */   public void setAddr_id(Integer addr_id) {
/*  53 */     this.addr_id = addr_id;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/*  57 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer member_id) {
/*  61 */     this.member_id = member_id;
/*     */   }
/*     */ 
/*     */   public Integer getProvince_id() {
/*  65 */     return this.province_id;
/*     */   }
/*     */ 
/*     */   public void setProvince_id(Integer province_id) {
/*  69 */     this.province_id = province_id;
/*     */   }
/*     */ 
/*     */   public Integer getCity_id() {
/*  73 */     return this.city_id;
/*     */   }
/*     */ 
/*     */   public void setCity_id(Integer city_id) {
/*  77 */     this.city_id = city_id;
/*     */   }
/*     */ 
/*     */   public Integer getRegion_id() {
/*  81 */     return this.region_id;
/*     */   }
/*     */ 
/*     */   public void setRegion_id(Integer region_id) {
/*  85 */     this.region_id = region_id;
/*     */   }
/*     */ 
/*     */   public Integer getDef_addr() {
/*  89 */     return this.def_addr;
/*     */   }
/*     */ 
/*     */   public void setDef_addr(Integer def_addr) {
/*  93 */     this.def_addr = def_addr;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/*  97 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/* 101 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getCity() {
/* 105 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String city) {
/* 109 */     this.city = city;
/*     */   }
/*     */ 
/*     */   public String getRegion() {
/* 113 */     return this.region;
/*     */   }
/*     */ 
/*     */   public void setRegion(String region) {
/* 117 */     this.region = region;
/*     */   }
/*     */ 
/*     */   public String getAddr() {
/* 121 */     return this.addr;
/*     */   }
/*     */ 
/*     */   public void setAddr(String addr) {
/* 125 */     this.addr = addr;
/*     */   }
/*     */ 
/*     */   public String getZip() {
/* 129 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String zip) {
/* 133 */     this.zip = zip;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/* 137 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/* 141 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getMobile() {
/* 145 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile) {
/* 149 */     this.mobile = mobile;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.MemberAddress
 * JD-Core Version:    0.6.0
 */