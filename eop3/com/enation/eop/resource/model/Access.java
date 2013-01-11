/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Access
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4339848792738875940L;
/*    */   private Integer id;
/*    */   private String ip;
/*    */   private String url;
/*    */   private String page;
/*    */   private String area;
/*    */   private int access_time;
/*    */   private int stay_time;
/*    */   private int point;
/*    */   private String membername;
/*    */ 
/*    */   public String getIp()
/*    */   {
/* 25 */     return this.ip;
/*    */   }
/*    */   public void setIp(String ip) {
/* 28 */     this.ip = ip;
/*    */   }
/*    */   public String getUrl() {
/* 31 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 34 */     this.url = url;
/*    */   }
/*    */   public String getPage() {
/* 37 */     return this.page;
/*    */   }
/*    */   public void setPage(String page) {
/* 40 */     this.page = page;
/*    */   }
/*    */   public String getArea() {
/* 43 */     return this.area;
/*    */   }
/*    */   public void setArea(String area) {
/* 46 */     this.area = area;
/*    */   }
/*    */   public int getAccess_time() {
/* 49 */     return this.access_time;
/*    */   }
/*    */ 
/*    */   public void setAccess_time(int accessTime) {
/* 53 */     this.access_time = accessTime;
/*    */   }
/*    */   public int getStay_time() {
/* 56 */     return this.stay_time;
/*    */   }
/*    */   public void setStay_time(int stayTime) {
/* 59 */     this.stay_time = stayTime;
/*    */   }
/* 63 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 66 */     this.id = id;
/*    */   }
/*    */   public int getPoint() {
/* 69 */     return this.point;
/*    */   }
/*    */   public void setPoint(int point) {
/* 72 */     this.point = point;
/*    */   }
/*    */   public String getMembername() {
/* 75 */     return this.membername;
/*    */   }
/*    */   public void setMembername(String membername) {
/* 78 */     this.membername = membername;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Access
 * JD-Core Version:    0.6.0
 */