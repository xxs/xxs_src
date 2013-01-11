/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ 
/*     */ public class MemberComment
/*     */ {
/*     */   private int comment_id;
/*     */   private int goods_id;
/*     */   private Integer member_id;
/*     */   private String content;
/*     */   private String img;
/*     */   private long dateline;
/*     */   private String ip;
/*     */   private String reply;
/*     */   private long replytime;
/*     */   private int status;
/*     */   private int type;
/*     */   private int replystatus;
/*     */   private int grade;
/*     */   private String imgPath;
/*     */ 
/*     */   public MemberComment()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MemberComment(int comment_id, int goods_id, Integer member_id, String content, String img, long dateline, String ip, String reply, long replytime, int status, int type, int replystatus, int grade)
/*     */   {
/*  32 */     this.comment_id = comment_id;
/*  33 */     this.goods_id = goods_id;
/*  34 */     this.member_id = member_id;
/*  35 */     this.content = content;
/*  36 */     this.img = img;
/*  37 */     this.dateline = dateline;
/*  38 */     this.ip = ip;
/*  39 */     this.reply = reply;
/*  40 */     this.replytime = replytime;
/*  41 */     this.status = status;
/*  42 */     this.type = type;
/*  43 */     this.replystatus = replystatus;
/*  44 */     this.grade = grade;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public int getComment_id() {
/*  49 */     return this.comment_id;
/*     */   }
/*     */ 
/*     */   public void setComment_id(int comment_id) {
/*  53 */     this.comment_id = comment_id;
/*     */   }
/*     */ 
/*     */   public int getGoods_id() {
/*  57 */     return this.goods_id;
/*     */   }
/*     */ 
/*     */   public void setGoods_id(int goods_id) {
/*  61 */     this.goods_id = goods_id;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/*  65 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer member_id) {
/*  69 */     this.member_id = member_id;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  73 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  77 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/*  81 */     return this.img;
/*     */   }
/*     */ 
/*     */   public void setImg(String img) {
/*  85 */     this.img = img;
/*     */   }
/*     */ 
/*     */   public long getDateline() {
/*  89 */     return this.dateline;
/*     */   }
/*     */ 
/*     */   public void setDateline(long dateline) {
/*  93 */     this.dateline = dateline;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/*  97 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/* 101 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public String getReply() {
/* 105 */     return this.reply;
/*     */   }
/*     */ 
/*     */   public void setReply(String reply) {
/* 109 */     this.reply = reply;
/*     */   }
/*     */ 
/*     */   public long getReplytime() {
/* 113 */     return this.replytime;
/*     */   }
/*     */ 
/*     */   public void setReplytime(long replytime) {
/* 117 */     this.replytime = replytime;
/*     */   }
/*     */ 
/*     */   public int getStatus() {
/* 121 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(int status) {
/* 125 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 129 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 133 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getReplystatus() {
/* 137 */     return this.replystatus;
/*     */   }
/*     */ 
/*     */   public void setReplystatus(int replystatus) {
/* 141 */     this.replystatus = replystatus;
/*     */   }
/*     */ 
/*     */   public int getGrade() {
/* 145 */     return this.grade;
/*     */   }
/*     */ 
/*     */   public void setGrade(int grade) {
/* 149 */     this.grade = grade;
/*     */   }
/*     */   @NotDbField
/*     */   public String getImgPath() {
/* 154 */     return this.imgPath;
/*     */   }
/*     */ 
/*     */   public void setImgPath(String imgPath) {
/* 158 */     this.imgPath = imgPath;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.MemberComment
 * JD-Core Version:    0.6.0
 */