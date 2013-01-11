/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.model.Image;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DataLog
/*     */ {
/*     */   private Integer id;
/*     */   private String content;
/*     */   private String url;
/*     */   private String pics;
/*     */   private String sitename;
/*     */   private String domain;
/*     */   private String logtype;
/*     */   private String optype;
/*     */   private Long dateline;
/*     */   private Integer userid;
/*     */   private Integer siteid;
/*     */   private List<Image> picList;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getId()
/*     */   {
/*  31 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  35 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  39 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  43 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  47 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/*  51 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getPics() {
/*  55 */     return this.pics;
/*     */   }
/*     */ 
/*     */   public void setPics(String pics) {
/*  59 */     this.pics = pics;
/*     */   }
/*     */ 
/*     */   public String getSitename() {
/*  63 */     return this.sitename;
/*     */   }
/*     */ 
/*     */   public void setSitename(String sitename) {
/*  67 */     this.sitename = sitename;
/*     */   }
/*     */ 
/*     */   public String getLogtype() {
/*  71 */     return this.logtype;
/*     */   }
/*     */ 
/*     */   public void setLogtype(String logtype) {
/*  75 */     this.logtype = logtype;
/*     */   }
/*     */ 
/*     */   public String getOptype() {
/*  79 */     return this.optype;
/*     */   }
/*     */ 
/*     */   public void setOptype(String optype) {
/*  83 */     this.optype = optype;
/*     */   }
/*     */ 
/*     */   public Long getDateline() {
/*  87 */     return this.dateline;
/*     */   }
/*     */ 
/*     */   public void setDateline(Long dateline) {
/*  91 */     this.dateline = dateline;
/*     */   }
/*     */ 
/*     */   public Integer getUserid() {
/*  95 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/*  99 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public Integer getSiteid() {
/* 103 */     return this.siteid;
/*     */   }
/*     */ 
/*     */   public void setSiteid(Integer siteid) {
/* 107 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public String getDomain() {
/* 111 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(String domain) {
/* 115 */     this.domain = domain;
/*     */   }
/*     */   @NotDbField
/*     */   public List<Image> getPicList() {
/* 120 */     return this.picList;
/*     */   }
/*     */ 
/*     */   public void setPicList(List<Image> picList) {
/* 124 */     this.picList = picList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.DataLog
 * JD-Core Version:    0.6.0
 */