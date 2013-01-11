/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.app.base.core.model.AuthAction;
/*     */ import com.enation.framework.database.DynamicField;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AdminUser extends DynamicField
/*     */   implements Serializable
/*     */ {
/*     */   private Integer userid;
/*     */   private String username;
/*     */   private String password;
/*     */   private int state;
/*     */   private String realname;
/*     */   private String userno;
/*     */   private String userdept;
/*     */   private String remark;
/*     */   private Long dateline;
/*     */   private int[] roleids;
/*     */   private int founder;
/*     */   private Integer siteid;
/*     */   private List<AuthAction> authList;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getUserid()
/*     */   {
/*  43 */     return this.userid;
/*     */   }
/*     */   public void setUserid(Integer userid) {
/*  46 */     this.userid = userid;
/*     */   }
/*     */   public String getUsername() {
/*  49 */     return this.username;
/*     */   }
/*     */   public void setUsername(String username) {
/*  52 */     this.username = username;
/*     */   }
/*     */   public String getPassword() {
/*  55 */     return this.password;
/*     */   }
/*     */   public void setPassword(String password) {
/*  58 */     this.password = password;
/*     */   }
/*     */   public int getState() {
/*  61 */     return this.state;
/*     */   }
/*     */   public void setState(int state) {
/*  64 */     this.state = state;
/*     */   }
/*     */   public String getRealname() {
/*  67 */     return this.realname;
/*     */   }
/*     */   public void setRealname(String realname) {
/*  70 */     this.realname = realname;
/*     */   }
/*     */   public String getUserno() {
/*  73 */     return this.userno;
/*     */   }
/*     */   public void setUserno(String userno) {
/*  76 */     this.userno = userno;
/*     */   }
/*     */   public String getUserdept() {
/*  79 */     return this.userdept;
/*     */   }
/*     */   public void setUserdept(String userdept) {
/*  82 */     this.userdept = userdept;
/*     */   }
/*     */   public String getRemark() {
/*  85 */     return this.remark;
/*     */   }
/*     */   public void setRemark(String remark) {
/*  88 */     this.remark = remark;
/*     */   }
/*     */   public Long getDateline() {
/*  91 */     return this.dateline;
/*     */   }
/*     */   public void setDateline(Long dateline) {
/*  94 */     this.dateline = dateline;
/*     */   }
/*     */ 
/*     */   public int getFounder() {
/*  98 */     return this.founder;
/*     */   }
/*     */   public void setFounder(int founder) {
/* 101 */     this.founder = founder;
/*     */   }
/* 105 */   @NotDbField
/*     */   public int[] getRoleids() { return this.roleids; }
/*     */ 
/*     */   public void setRoleids(int[] roleids) {
/* 108 */     this.roleids = roleids;
/*     */   }
/*     */   public Integer getSiteid() {
/* 111 */     return this.siteid;
/*     */   }
/*     */   public void setSiteid(Integer siteid) {
/* 114 */     this.siteid = siteid;
/*     */   }
/* 118 */   @NotDbField
/*     */   public List<AuthAction> getAuthList() { return this.authList; }
/*     */ 
/*     */   public void setAuthList(List<AuthAction> authList) {
/* 121 */     this.authList = authList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.AdminUser
 * JD-Core Version:    0.6.0
 */