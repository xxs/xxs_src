/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class SiteAppView
/*    */ {
/*    */   private Integer id;
/*    */   private Integer siteid;
/*    */   private Integer userid;
/*    */   private String appid;
/*    */   private String app_name;
/*    */   private String descript;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 22 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 26 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 30 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 34 */     this.siteid = siteid;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 38 */     return this.userid;
/*    */   }
/*    */ 
/*    */   public void setUserid(Integer userid) {
/* 42 */     this.userid = userid;
/*    */   }
/*    */ 
/*    */   public String getAppid() {
/* 46 */     return this.appid;
/*    */   }
/*    */ 
/*    */   public void setAppid(String appid) {
/* 50 */     this.appid = appid;
/*    */   }
/*    */ 
/*    */   public String getApp_name() {
/* 54 */     return this.app_name;
/*    */   }
/*    */ 
/*    */   public void setApp_name(String appName) {
/* 58 */     this.app_name = appName;
/*    */   }
/*    */ 
/*    */   public String getDescript() {
/* 62 */     return this.descript;
/*    */   }
/*    */ 
/*    */   public void setDescript(String descript) {
/* 66 */     this.descript = descript;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.SiteAppView
 * JD-Core Version:    0.6.0
 */