/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Smtp
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4645737054149076379L;
/*    */   private Integer id;
/*    */   private String host;
/*    */   private String username;
/*    */   private String password;
/*    */   private long last_send_time;
/*    */   private int send_count;
/*    */   private int max_count;
/*    */   private String mail_from;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 30 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 33 */     this.id = id;
/*    */   }
/*    */   public String getHost() {
/* 36 */     return this.host;
/*    */   }
/*    */   public void setHost(String host) {
/* 39 */     this.host = host;
/*    */   }
/*    */   public String getUsername() {
/* 42 */     return this.username;
/*    */   }
/*    */   public void setUsername(String username) {
/* 45 */     this.username = username;
/*    */   }
/*    */   public String getPassword() {
/* 48 */     return this.password;
/*    */   }
/*    */   public void setPassword(String password) {
/* 51 */     this.password = password;
/*    */   }
/*    */ 
/*    */   public long getLast_send_time() {
/* 55 */     return this.last_send_time;
/*    */   }
/*    */   public void setLast_send_time(long last_send_time) {
/* 58 */     this.last_send_time = last_send_time;
/*    */   }
/*    */   public int getSend_count() {
/* 61 */     return this.send_count;
/*    */   }
/*    */   public void setSend_count(int send_count) {
/* 64 */     this.send_count = send_count;
/*    */   }
/*    */   public int getMax_count() {
/* 67 */     return this.max_count;
/*    */   }
/*    */   public void setMax_count(int max_count) {
/* 70 */     this.max_count = max_count;
/*    */   }
/*    */   public String getMail_from() {
/* 73 */     return this.mail_from;
/*    */   }
/*    */   public void setMail_from(String mail_from) {
/* 76 */     this.mail_from = mail_from;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Smtp
 * JD-Core Version:    0.6.0
 */