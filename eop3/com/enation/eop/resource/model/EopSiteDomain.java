/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class EopSiteDomain
/*    */ {
/*    */   private Integer id;
/*    */   private String domain;
/*    */   private Integer domaintype;
/*    */   private Integer siteid;
/*    */   private Integer userid;
/*    */   private Integer status;
/*    */ 
/*    */   public Integer getStatus()
/*    */   {
/* 21 */     return this.status;
/*    */   }
/*    */   public void setStatus(Integer status) {
/* 24 */     this.status = status;
/*    */   }
/*    */   public Integer getUserid() {
/* 27 */     return this.userid;
/*    */   }
/*    */   public void setUserid(Integer userid) {
/* 30 */     this.userid = userid;
/*    */   }
/* 34 */   @PrimaryKeyField
/*    */   public Integer getId() { return this.id; }
/*    */ 
/*    */   public void setId(Integer id) {
/* 37 */     this.id = id;
/*    */   }
/*    */   public String getDomain() {
/* 40 */     return this.domain;
/*    */   }
/*    */   public void setDomain(String domain) {
/* 43 */     this.domain = domain;
/*    */   }
/*    */   public Integer getDomaintype() {
/* 46 */     return this.domaintype;
/*    */   }
/*    */   public void setDomaintype(Integer domaintype) {
/* 49 */     this.domaintype = domaintype;
/*    */   }
/*    */   public Integer getSiteid() {
/* 52 */     return this.siteid;
/*    */   }
/*    */   public void setSiteid(Integer siteid) {
/* 55 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopSiteDomain
 * JD-Core Version:    0.6.0
 */