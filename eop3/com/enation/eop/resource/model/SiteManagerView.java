/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class SiteManagerView
/*    */ {
/*    */   private Integer id;
/*    */   private Integer userid;
/*    */   private String sitename;
/*    */   private Integer siteid;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 18 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 21 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 25 */     return this.userid;
/*    */   }
/*    */   public void setUserid(Integer userid) {
/* 28 */     this.userid = userid;
/*    */   }
/*    */   public String getSitename() {
/* 31 */     return this.sitename;
/*    */   }
/*    */   public void setSitename(String sitename) {
/* 34 */     this.sitename = sitename;
/*    */   }
/*    */   public Integer getSiteid() {
/* 37 */     return this.siteid;
/*    */   }
/*    */   public void setSiteid(Integer siteid) {
/* 40 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.SiteManagerView
 * JD-Core Version:    0.6.0
 */