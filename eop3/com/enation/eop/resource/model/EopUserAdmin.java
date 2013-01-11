/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class EopUserAdmin
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5111983361750207866L;
/*     */   private Integer id;
/*     */   private Integer userid;
/*     */   private String username;
/*     */   private String realname;
/*     */   private String password;
/*     */   private String tel;
/*     */   private String mobile;
/*     */   private String email;
/*     */   private String qq;
/*     */   private Integer defaultsiteid;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getId()
/*     */   {
/*  36 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  40 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer getUserid() {
/*  44 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/*  48 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/*  52 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/*  56 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getRealname() {
/*  60 */     return this.realname;
/*     */   }
/*     */ 
/*     */   public void setRealname(String realname) {
/*  64 */     this.realname = realname;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  68 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/*  72 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/*  76 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/*  80 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getMobile() {
/*  84 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile) {
/*  88 */     this.mobile = mobile;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  92 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/*  96 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getQq() {
/* 100 */     return this.qq;
/*     */   }
/*     */ 
/*     */   public void setQq(String qq) {
/* 104 */     this.qq = qq;
/*     */   }
/*     */ 
/*     */   public Integer getDefaultsiteid() {
/* 108 */     return this.defaultsiteid;
/*     */   }
/*     */ 
/*     */   public void setDefaultsiteid(Integer defaultsiteid) {
/* 112 */     this.defaultsiteid = defaultsiteid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopUserAdmin
 * JD-Core Version:    0.6.0
 */