/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GuestBook
/*     */ {
/*     */   private Integer id;
/*     */   private Integer parentid;
/*     */   private String title;
/*     */   private String content;
/*     */   private Integer issubject;
/*     */   private Long dateline;
/*     */   private String username;
/*     */   private String email;
/*     */   private String qq;
/*     */   private String tel;
/*     */   private Integer sex;
/*     */   private String ip;
/*     */   private String area;
/*     */   private String addtime;
/*     */   private List replyList;
/*     */ 
/*     */   public GuestBook()
/*     */   {
/*  37 */     this.replyList = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void addReply(GuestBook reply)
/*     */   {
/*  42 */     this.replyList.add(reply);
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
/*     */   public String getTitle()
/*     */   {
/*  57 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title) {
/*  61 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  65 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  69 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public Integer getParentid()
/*     */   {
/*  77 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(Integer parentid) {
/*  81 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public Integer getIssubject() {
/*  85 */     return this.issubject;
/*     */   }
/*     */ 
/*     */   public void setIssubject(Integer issubject) {
/*  89 */     this.issubject = issubject;
/*     */   }
/*     */ 
/*     */   public Long getDateline() {
/*  93 */     return this.dateline;
/*     */   }
/*     */ 
/*     */   public void setDateline(Long dateline) {
/*  97 */     this.dateline = dateline;
/*     */   }
/*     */ 
/*     */   public void setSex(Integer sex) {
/* 101 */     this.sex = sex;
/*     */   }
/*     */   @NotDbField
/*     */   public String getAddtime() {
/* 106 */     this.addtime = DateUtil.toString(new Date(this.dateline.longValue() * 1000L), "MM-dd hh:mm");
/*     */ 
/* 108 */     return this.addtime;
/*     */   }
/*     */ 
/*     */   public Integer getSex() {
/* 112 */     return this.sex;
/*     */   }
/*     */ 
/*     */   public void setAddtime(String addtime) {
/* 116 */     this.addtime = addtime;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public List getReplyList() {
/* 122 */     return this.replyList;
/*     */   }
/*     */ 
/*     */   public void setReplyList(List replyList) {
/* 126 */     this.replyList = replyList;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 130 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 134 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 138 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 142 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getQq() {
/* 146 */     return this.qq;
/*     */   }
/*     */ 
/*     */   public void setQq(String qq) {
/* 150 */     this.qq = qq;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/* 154 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/* 158 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/* 163 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/* 167 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public String getArea() {
/* 171 */     return this.area;
/*     */   }
/*     */ 
/*     */   public void setArea(String area) {
/* 175 */     this.area = area;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.GuestBook
 * JD-Core Version:    0.6.0
 */