/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Administrator
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private String uname;
/*    */   private String name;
/*    */   private String pwd;
/*    */   private Integer status;
/*    */   private Long last_login;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 25 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 29 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getUname() {
/* 33 */     return this.uname;
/*    */   }
/*    */ 
/*    */   public void setUname(String uname) {
/* 37 */     this.uname = uname;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 41 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 45 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getPwd() {
/* 49 */     return this.pwd;
/*    */   }
/*    */ 
/*    */   public void setPwd(String pwd) {
/* 53 */     this.pwd = pwd;
/*    */   }
/*    */ 
/*    */   public Integer getStatus()
/*    */   {
/* 58 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void setStatus(Integer status) {
/* 62 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public Long getLast_login() {
/* 66 */     return this.last_login;
/*    */   }
/*    */ 
/*    */   public void setLast_login(Long last_login) {
/* 70 */     this.last_login = last_login;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Administrator
 * JD-Core Version:    0.6.0
 */