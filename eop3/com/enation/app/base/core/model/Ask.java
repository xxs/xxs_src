/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Ask
/*    */ {
/*    */   private Integer askid;
/*    */   private String title;
/*    */   private String content;
/*    */   private Long dateline;
/*    */   private Integer isreply;
/*    */   private Integer userid;
/*    */   private Integer siteid;
/*    */   private String domain;
/*    */   private String sitename;
/*    */   private String username;
/*    */   private List replyList;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getAskid()
/*    */   {
/* 30 */     return this.askid;
/*    */   }
/*    */   public void setAskid(Integer askid) {
/* 33 */     this.askid = askid;
/*    */   }
/*    */   public String getTitle() {
/* 36 */     return this.title;
/*    */   }
/*    */   public void setTitle(String title) {
/* 39 */     this.title = title;
/*    */   }
/*    */   public String getContent() {
/* 42 */     return this.content;
/*    */   }
/*    */   public void setContent(String content) {
/* 45 */     this.content = content;
/*    */   }
/*    */   public Long getDateline() {
/* 48 */     return this.dateline;
/*    */   }
/*    */   public void setDateline(Long dateline) {
/* 51 */     this.dateline = dateline;
/*    */   }
/*    */   public Integer getIsreply() {
/* 54 */     return this.isreply;
/*    */   }
/*    */   public void setIsreply(Integer isreply) {
/* 57 */     this.isreply = isreply;
/*    */   }
/*    */   public Integer getUserid() {
/* 60 */     return this.userid;
/*    */   }
/*    */   public void setUserid(Integer userid) {
/* 63 */     this.userid = userid;
/*    */   }
/*    */   public Integer getSiteid() {
/* 66 */     return this.siteid;
/*    */   }
/*    */   public void setSiteid(Integer siteid) {
/* 69 */     this.siteid = siteid;
/*    */   }
/*    */   public String getDomain() {
/* 72 */     return this.domain;
/*    */   }
/*    */   public void setDomain(String domain) {
/* 75 */     this.domain = domain;
/*    */   }
/*    */   public String getSitename() {
/* 78 */     return this.sitename;
/*    */   }
/*    */   public void setSitename(String sitename) {
/* 81 */     this.sitename = sitename;
/*    */   }
/*    */   public String getUsername() {
/* 84 */     return this.username;
/*    */   }
/*    */   public void setUsername(String username) {
/* 87 */     this.username = username;
/*    */   }
/*    */   @NotDbField
/*    */   public List getReplyList() {
/* 92 */     return this.replyList;
/*    */   }
/*    */   public void setReplyList(List replyList) {
/* 95 */     this.replyList = replyList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Ask
 * JD-Core Version:    0.6.0
 */