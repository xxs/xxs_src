/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Role
/*    */ {
/*    */   private Integer roleid;
/*    */   private String rolename;
/*    */   private String rolememo;
/*    */   private int[] actids;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getRoleid()
/*    */   {
/* 19 */     return this.roleid;
/*    */   }
/*    */ 
/*    */   public void setRoleid(Integer roleid) {
/* 23 */     this.roleid = roleid;
/*    */   }
/*    */ 
/*    */   public String getRolename() {
/* 27 */     return this.rolename;
/*    */   }
/*    */ 
/*    */   public void setRolename(String rolename) {
/* 31 */     this.rolename = rolename;
/*    */   }
/*    */ 
/*    */   public String getRolememo() {
/* 35 */     return this.rolememo;
/*    */   }
/*    */ 
/*    */   public void setRolememo(String rolememo) {
/* 39 */     this.rolememo = rolememo;
/*    */   }
/*    */   @NotDbField
/*    */   public int[] getActids() {
/* 44 */     return this.actids;
/*    */   }
/*    */ 
/*    */   public void setActids(int[] actids) {
/* 48 */     this.actids = actids;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Role
 * JD-Core Version:    0.6.0
 */