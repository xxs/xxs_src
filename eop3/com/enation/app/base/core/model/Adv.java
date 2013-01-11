/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class Adv
/*     */ {
/*     */   private Integer aid;
/*     */   private Integer acid;
/*     */   private Integer atype;
/*     */   private Long begintime;
/*     */   private Long endtime;
/*     */   private Integer isclose;
/*     */   private String attachment;
/*     */   private String atturl;
/*     */   private String url;
/*     */   private String aname;
/*     */   private Integer clickcount;
/*     */   private String disabled;
/*     */   private String linkman;
/*     */   private String company;
/*     */   private String contact;
/*     */   private String cname;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getAid()
/*     */   {
/*  40 */     return this.aid;
/*     */   }
/*     */ 
/*     */   public void setAid(Integer aid) {
/*  44 */     this.aid = aid;
/*     */   }
/*     */ 
/*     */   public Integer getAcid() {
/*  48 */     return this.acid;
/*     */   }
/*     */ 
/*     */   public void setAcid(Integer acid) {
/*  52 */     this.acid = acid;
/*     */   }
/*     */ 
/*     */   public Integer getAtype() {
/*  56 */     return this.atype;
/*     */   }
/*     */ 
/*     */   public void setAtype(Integer atype) {
/*  60 */     this.atype = atype;
/*     */   }
/*     */ 
/*     */   public Long getBegintime() {
/*  64 */     return this.begintime;
/*     */   }
/*     */ 
/*     */   public void setBegintime(Long begintime) {
/*  68 */     this.begintime = begintime;
/*     */   }
/*     */ 
/*     */   public Long getEndtime() {
/*  72 */     return this.endtime;
/*     */   }
/*     */ 
/*     */   public void setEndtime(Long endtime) {
/*  76 */     this.endtime = endtime;
/*     */   }
/*     */ 
/*     */   public Integer getIsclose() {
/*  80 */     return this.isclose;
/*     */   }
/*     */ 
/*     */   public void setIsclose(Integer isclose) {
/*  84 */     this.isclose = isclose;
/*     */   }
/*     */ 
/*     */   public String getAttachment() {
/*  88 */     return this.attachment;
/*     */   }
/*     */ 
/*     */   public void setAttachment(String attachment) {
/*  92 */     this.attachment = attachment;
/*     */   }
/*     */ 
/*     */   public String getAtturl() {
/*  96 */     return this.atturl;
/*     */   }
/*     */ 
/*     */   public void setAtturl(String atturl) {
/* 100 */     this.atturl = atturl;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 104 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/* 108 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getAname() {
/* 112 */     return this.aname;
/*     */   }
/*     */ 
/*     */   public void setAname(String aname) {
/* 116 */     this.aname = aname;
/*     */   }
/*     */ 
/*     */   public Integer getClickcount() {
/* 120 */     return this.clickcount;
/*     */   }
/*     */ 
/*     */   public void setClickcount(Integer clickcount) {
/* 124 */     this.clickcount = clickcount;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/* 128 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/* 132 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getLinkman() {
/* 136 */     return this.linkman;
/*     */   }
/*     */ 
/*     */   public void setLinkman(String linkman) {
/* 140 */     this.linkman = linkman;
/*     */   }
/*     */ 
/*     */   public String getCompany() {
/* 144 */     return this.company;
/*     */   }
/*     */ 
/*     */   public void setCompany(String company) {
/* 148 */     this.company = company;
/*     */   }
/*     */ 
/*     */   public String getContact() {
/* 152 */     return this.contact;
/*     */   }
/*     */ 
/*     */   public void setContact(String contact) {
/* 156 */     this.contact = contact;
/*     */   }
/*     */   @NotDbField
/*     */   public String getCname() {
/* 161 */     return this.cname;
/*     */   }
/*     */ 
/*     */   public void setCname(String cname) {
/* 165 */     this.cname = cname;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Adv
 * JD-Core Version:    0.6.0
 */