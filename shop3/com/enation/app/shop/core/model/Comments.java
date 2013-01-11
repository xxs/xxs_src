/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Comments
/*     */ {
/*     */   private Integer comment_id;
/*     */   private Integer for_comment_id;
/*     */   private Integer object_id;
/*     */   private String object_type;
/*     */   private Integer author_id;
/*     */   private String author;
/*     */   private String levelname;
/*     */   private String contact;
/*     */   private String mem_read_status;
/*     */   private String adm_read_status;
/*     */   private Long time;
/*     */   private Long lastreply;
/*     */   private String reply_name;
/*     */   private String title;
/*     */   private String acomment;
/*     */   private String ip;
/*     */   private String display;
/*     */   private Integer p_index;
/*     */   private String disabled;
/*     */   private String commenttype;
/*     */   private int grade;
/*     */   private String img;
/*     */   private int deal_complete;
/*     */ 
/*     */   public int getDeal_complete()
/*     */   {
/*  45 */     return this.deal_complete;
/*     */   }
/*     */ 
/*     */   public void setDeal_complete(int deal_complete) {
/*  49 */     this.deal_complete = deal_complete;
/*     */   }
/*     */   @NotDbField
/*     */   public Date getDate() {
/*  54 */     return new Date(getTime().longValue());
/*     */   }
/*     */ 
/*     */   public String getCommenttype() {
/*  58 */     return this.commenttype;
/*     */   }
/*     */ 
/*     */   public void setCommenttype(String commenttype) {
/*  62 */     this.commenttype = commenttype;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getComment_id() {
/*  67 */     return this.comment_id;
/*     */   }
/*     */ 
/*     */   public void setComment_id(Integer commentId) {
/*  71 */     this.comment_id = commentId;
/*     */   }
/*     */ 
/*     */   public Integer getFor_comment_id() {
/*  75 */     return this.for_comment_id;
/*     */   }
/*     */ 
/*     */   public void setFor_comment_id(Integer forCommentId) {
/*  79 */     this.for_comment_id = forCommentId;
/*     */   }
/*     */ 
/*     */   public Integer getObject_id() {
/*  83 */     return this.object_id;
/*     */   }
/*     */ 
/*     */   public void setObject_id(Integer objectId) {
/*  87 */     this.object_id = objectId;
/*     */   }
/*     */ 
/*     */   public String getObject_type() {
/*  91 */     return this.object_type;
/*     */   }
/*     */ 
/*     */   public void setObject_type(String objectType) {
/*  95 */     this.object_type = objectType;
/*     */   }
/*     */ 
/*     */   public Integer getAuthor_id() {
/*  99 */     return this.author_id;
/*     */   }
/*     */ 
/*     */   public void setAuthor_id(Integer authorId) {
/* 103 */     this.author_id = authorId;
/*     */   }
/*     */ 
/*     */   public String getAuthor() {
/* 107 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author) {
/* 111 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public String getLevelname() {
/* 115 */     return this.levelname;
/*     */   }
/*     */ 
/*     */   public void setLevelname(String levelname) {
/* 119 */     this.levelname = levelname;
/*     */   }
/*     */ 
/*     */   public String getContact() {
/* 123 */     return this.contact;
/*     */   }
/*     */ 
/*     */   public void setContact(String contact) {
/* 127 */     this.contact = contact;
/*     */   }
/*     */ 
/*     */   public String getMem_read_status() {
/* 131 */     return this.mem_read_status;
/*     */   }
/*     */ 
/*     */   public void setMem_read_status(String memReadStatus) {
/* 135 */     this.mem_read_status = memReadStatus;
/*     */   }
/*     */ 
/*     */   public String getAdm_read_status() {
/* 139 */     return this.adm_read_status;
/*     */   }
/*     */ 
/*     */   public void setAdm_read_status(String admReadStatus) {
/* 143 */     this.adm_read_status = admReadStatus;
/*     */   }
/*     */ 
/*     */   public Long getTime() {
/* 147 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Long time) {
/* 151 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public Long getLastreply() {
/* 155 */     return this.lastreply;
/*     */   }
/*     */ 
/*     */   public void setLastreply(Long lastreply) {
/* 159 */     this.lastreply = lastreply;
/*     */   }
/*     */ 
/*     */   public String getReply_name() {
/* 163 */     return this.reply_name;
/*     */   }
/*     */ 
/*     */   public void setReply_name(String replyName) {
/* 167 */     this.reply_name = replyName;
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/* 171 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title) {
/* 175 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getAcomment() {
/* 179 */     return this.acomment;
/*     */   }
/*     */ 
/*     */   public void setAcomment(String acomment) {
/* 183 */     this.acomment = acomment;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/* 187 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/* 191 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public String getDisplay() {
/* 195 */     return this.display;
/*     */   }
/*     */ 
/*     */   public void setDisplay(String display) {
/* 199 */     this.display = display;
/*     */   }
/*     */ 
/*     */   public Integer getP_index() {
/* 203 */     return this.p_index;
/*     */   }
/*     */ 
/*     */   public void setP_index(Integer pIndex) {
/* 207 */     this.p_index = pIndex;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/* 211 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/* 215 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public int getGrade() {
/* 219 */     return this.grade;
/*     */   }
/*     */ 
/*     */   public void setGrade(int grade) {
/* 223 */     this.grade = grade;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/* 227 */     return this.img;
/*     */   }
/*     */ 
/*     */   public void setImg(String img) {
/* 231 */     this.img = img;
/*     */   }
/*     */   @NotDbField
/*     */   public String getImage() {
/* 236 */     this.img = UploadUtil.replacePath(this.img);
/* 237 */     return this.img;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Comments
 * JD-Core Version:    0.6.0
 */