/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Message
/*     */   implements Serializable
/*     */ {
/*     */   private Integer msg_id;
/*     */   private Integer for_id;
/*     */   private String msg_from;
/*     */   private Integer from_id;
/*     */   private Integer from_type;
/*     */   private Integer to_id;
/*     */   private String msg_to;
/*     */   private Integer to_type;
/*     */   private String unread;
/*     */   private String folder;
/*     */   private String email;
/*     */   private String tel;
/*     */   private String subject;
/*     */   private String message;
/*     */   private Integer rel_order;
/*     */   private Long date_line;
/*     */   private String is_sec;
/*     */   private String del_status;
/*     */   private String disabled;
/*     */   private String msg_ip;
/*     */   private String msg_type;
/*     */ 
/*     */   public String getMsg_from()
/*     */   {
/*  40 */     return this.msg_from;
/*     */   }
/*     */ 
/*     */   public void setMsg_from(String msgFrom) {
/*  44 */     this.msg_from = msgFrom;
/*     */   }
/*     */ 
/*     */   public String getMsg_to() {
/*  48 */     return this.msg_to;
/*     */   }
/*     */ 
/*     */   public void setMsg_to(String msgTo) {
/*  52 */     this.msg_to = msgTo;
/*     */   }
/*     */ 
/*     */   public String getUnread() {
/*  56 */     return this.unread;
/*     */   }
/*     */ 
/*     */   public void setUnread(String unread) {
/*  60 */     this.unread = unread;
/*     */   }
/*     */ 
/*     */   public String getFolder() {
/*  64 */     return this.folder;
/*     */   }
/*     */ 
/*     */   public void setFolder(String folder) {
/*  68 */     this.folder = folder;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  72 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/*  76 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/*  80 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/*  84 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public String getSubject() {
/*  88 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject) {
/*  92 */     this.subject = subject;
/*     */   }
/*     */ 
/*     */   public String getMessage() {
/*  96 */     return this.message;
/*     */   }
/*     */ 
/*     */   public void setMessage(String message) {
/* 100 */     this.message = message;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getMsg_id() {
/* 105 */     return this.msg_id;
/*     */   }
/*     */ 
/*     */   public void setMsg_id(Integer msg_id) {
/* 109 */     this.msg_id = msg_id;
/*     */   }
/*     */ 
/*     */   public Integer getFor_id() {
/* 113 */     return this.for_id;
/*     */   }
/*     */ 
/*     */   public void setFor_id(Integer for_id) {
/* 117 */     this.for_id = for_id;
/*     */   }
/*     */ 
/*     */   public Integer getFrom_id() {
/* 121 */     return this.from_id;
/*     */   }
/*     */ 
/*     */   public void setFrom_id(Integer from_id) {
/* 125 */     this.from_id = from_id;
/*     */   }
/*     */ 
/*     */   public Integer getFrom_type() {
/* 129 */     return this.from_type;
/*     */   }
/*     */ 
/*     */   public void setFrom_type(Integer from_type) {
/* 133 */     this.from_type = from_type;
/*     */   }
/*     */ 
/*     */   public Integer getTo_id() {
/* 137 */     return this.to_id;
/*     */   }
/*     */ 
/*     */   public void setTo_id(Integer to_id) {
/* 141 */     this.to_id = to_id;
/*     */   }
/*     */ 
/*     */   public Integer getTo_type() {
/* 145 */     return this.to_type;
/*     */   }
/*     */ 
/*     */   public void setTo_type(Integer to_type) {
/* 149 */     this.to_type = to_type;
/*     */   }
/*     */ 
/*     */   public Integer getRel_order() {
/* 153 */     return this.rel_order;
/*     */   }
/*     */ 
/*     */   public void setRel_order(Integer rel_order) {
/* 157 */     this.rel_order = rel_order;
/*     */   }
/*     */ 
/*     */   public Long getDate_line() {
/* 161 */     return this.date_line;
/*     */   }
/*     */ 
/*     */   public void setDate_line(Long dateLine) {
/* 165 */     this.date_line = dateLine;
/*     */   }
/*     */ 
/*     */   public String getIs_sec() {
/* 169 */     return this.is_sec;
/*     */   }
/*     */ 
/*     */   public void setIs_sec(String isSec) {
/* 173 */     this.is_sec = isSec;
/*     */   }
/*     */ 
/*     */   public String getDel_status() {
/* 177 */     return this.del_status;
/*     */   }
/*     */ 
/*     */   public void setDel_status(String delStatus) {
/* 181 */     this.del_status = delStatus;
/*     */   }
/*     */ 
/*     */   public String getDisabled() {
/* 185 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(String disabled) {
/* 189 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getMsg_ip() {
/* 193 */     return this.msg_ip;
/*     */   }
/*     */ 
/*     */   public void setMsg_ip(String msgIp) {
/* 197 */     this.msg_ip = msgIp;
/*     */   }
/*     */ 
/*     */   public String getMsg_type() {
/* 201 */     return this.msg_type;
/*     */   }
/*     */ 
/*     */   public void setMsg_type(String msgType) {
/* 205 */     this.msg_type = msgType;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Message
 * JD-Core Version:    0.6.0
 */