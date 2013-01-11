/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class EopApp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 9088172178237139695L;
/*     */   private Integer id;
/*     */   private String appid;
/*     */   private String app_name;
/*     */   private String author;
/*     */   private String descript;
/*     */   private int deployment;
/*     */   private String path;
/*     */   private String installuri;
/*     */   private String version;
/*     */   private List<String> updateLogList;
/*     */   private String authorizationcode;
/*     */ 
/*     */   public String getAppid()
/*     */   {
/*  38 */     return this.appid;
/*     */   }
/*     */ 
/*     */   public void setAppid(String appid) {
/*  42 */     this.appid = appid;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getId() {
/*  47 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  51 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getPath() {
/*  55 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path) {
/*  59 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getApp_name()
/*     */   {
/*  68 */     return this.app_name;
/*     */   }
/*     */ 
/*     */   public void setApp_name(String appName)
/*     */   {
/*  75 */     this.app_name = appName;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  82 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/*  89 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public String getDescript()
/*     */   {
/*  96 */     return this.descript;
/*     */   }
/*     */ 
/*     */   public void setDescript(String descript)
/*     */   {
/* 103 */     this.descript = descript;
/*     */   }
/*     */ 
/*     */   public int getDeployment()
/*     */   {
/* 110 */     return this.deployment;
/*     */   }
/*     */ 
/*     */   public void setDeployment(int deployment)
/*     */   {
/* 117 */     this.deployment = deployment;
/*     */   }
/*     */ 
/*     */   public String getAuthorizationcode()
/*     */   {
/* 124 */     return this.authorizationcode;
/*     */   }
/*     */ 
/*     */   public void setAuthorizationcode(String authorizationcode)
/*     */   {
/* 131 */     this.authorizationcode = authorizationcode;
/*     */   }
/*     */ 
/*     */   public String getInstalluri() {
/* 135 */     return this.installuri;
/*     */   }
/*     */ 
/*     */   public void setInstalluri(String installuri) {
/* 139 */     this.installuri = installuri;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 143 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 147 */     this.version = version;
/*     */   }
/*     */   @NotDbField
/*     */   public List<String> getUpdateLogList() {
/* 152 */     return this.updateLogList;
/*     */   }
/*     */ 
/*     */   public void setUpdateLogList(List<String> updateLogList) {
/* 156 */     this.updateLogList = updateLogList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopApp
 * JD-Core Version:    0.6.0
 */