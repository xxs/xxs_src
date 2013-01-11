/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class EopUser
/*     */   implements Serializable
/*     */ {
/*     */   private Integer id;
/*     */   private String username;
/*     */   private String companyname;
/*     */   private String password;
/*     */   private String address;
/*     */   private String legalrepresentative;
/*     */   private String linkman;
/*     */   private String tel;
/*     */   private String mobile;
/*     */   private String email;
/*     */   private String logofile;
/*     */   private String licensefile;
/*     */   private Integer defaultsiteid;
/*     */   private Integer usertype;
/*     */   private EopUserDetail userDetail;
/*     */ 
/*     */   public Integer getDefaultsiteid()
/*     */   {
/*  39 */     return this.defaultsiteid;
/*     */   }
/*     */ 
/*     */   public void setDefaultsiteid(Integer defaultsiteid) {
/*  43 */     this.defaultsiteid = defaultsiteid;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getId() {
/*  48 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  52 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/*  56 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/*  60 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/*  64 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address) {
/*  68 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getLegalrepresentative() {
/*  72 */     return this.legalrepresentative;
/*     */   }
/*     */ 
/*     */   public void setLegalrepresentative(String legalrepresentative) {
/*  76 */     this.legalrepresentative = legalrepresentative;
/*     */   }
/*     */ 
/*     */   public String getLinkman() {
/*  80 */     return this.linkman;
/*     */   }
/*     */ 
/*     */   public void setLinkman(String linkman) {
/*  84 */     this.linkman = linkman;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/*  88 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/*  92 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getMobile() {
/*  96 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile) {
/* 100 */     this.mobile = mobile;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 104 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 108 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getLogofile() {
/* 112 */     return this.logofile;
/*     */   }
/*     */ 
/*     */   public void setLogofile(String logofile) {
/* 116 */     this.logofile = logofile;
/*     */   }
/*     */ 
/*     */   public String getLicensefile() {
/* 120 */     return this.licensefile;
/*     */   }
/*     */ 
/*     */   public void setLicensefile(String licensefile) {
/* 124 */     this.licensefile = licensefile;
/*     */   }
/*     */ 
/*     */   public Integer getUsertype() {
/* 128 */     return this.usertype;
/*     */   }
/*     */ 
/*     */   public void setUsertype(Integer usertype) {
/* 132 */     this.usertype = usertype;
/*     */   }
/*     */   @NotDbField
/*     */   public EopUserDetail getUserDetail() {
/* 137 */     return this.userDetail;
/*     */   }
/*     */ 
/*     */   public void setUserDetail(EopUserDetail userDetail) {
/* 141 */     this.userDetail = userDetail;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/* 145 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/* 149 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public String getCompanyname() {
/* 153 */     return this.companyname;
/*     */   }
/*     */ 
/*     */   public void setCompanyname(String companyname) {
/* 157 */     this.companyname = companyname;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopUser
 * JD-Core Version:    0.6.0
 */