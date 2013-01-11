/*    */ package com.enation.eop.sdk.user;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class UserContext
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 752513002L;
/*    */   public static final String CONTEXT_KEY = "usercontext";
/*    */   private Integer userid;
/*    */   private Integer siteid;
/*    */   private Integer managerid;
/*    */ 
/*    */   public UserContext()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserContext(Integer userid, Integer siteid, Integer managerid)
/*    */   {
/* 23 */     this.userid = userid;
/* 24 */     this.siteid = siteid;
/* 25 */     this.managerid = managerid;
/*    */   }
/*    */ 
/*    */   public Integer getUserid()
/*    */   {
/* 30 */     return this.userid;
/*    */   }
/*    */   public void setUserid(Integer userid) {
/* 33 */     this.userid = userid;
/*    */   }
/*    */   public Integer getSiteid() {
/* 36 */     return this.siteid;
/*    */   }
/*    */   public void setSiteid(Integer siteid) {
/* 39 */     this.siteid = siteid;
/*    */   }
/*    */ 
/*    */   public Integer getManagerid() {
/* 43 */     return this.managerid;
/*    */   }
/*    */ 
/*    */   public void setManagerid(Integer managerid) {
/* 47 */     this.managerid = managerid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.user.UserContext
 * JD-Core Version:    0.6.0
 */