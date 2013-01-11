/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class EopSiteAdmin
/*    */ {
/*    */   private Integer id;
/*    */   private Integer managerid;
/*    */   private Integer siteid;
/*    */   private Integer userid;
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
/*    */   public Integer getManagerid() {
/* 30 */     return this.managerid;
/*    */   }
/*    */ 
/*    */   public void setManagerid(Integer managerid) {
/* 34 */     this.managerid = managerid;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 38 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 42 */     this.siteid = siteid;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 46 */     return this.userid;
/*    */   }
/*    */ 
/*    */   public void setUserid(Integer userid) {
/* 50 */     this.userid = userid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopSiteAdmin
 * JD-Core Version:    0.6.0
 */