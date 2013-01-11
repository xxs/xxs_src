/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class AuthAction
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4831369457068242964L;
/*    */   private Integer actid;
/*    */   private String name;
/*    */   private String type;
/*    */   private String objvalue;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getActid()
/*    */   {
/* 25 */     return this.actid;
/*    */   }
/*    */   public void setActid(Integer actid) {
/* 28 */     this.actid = actid;
/*    */   }
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 34 */     this.name = name;
/*    */   }
/*    */   public String getType() {
/* 37 */     return this.type;
/*    */   }
/*    */   public void setType(String type) {
/* 40 */     this.type = type;
/*    */   }
/*    */   public String getObjvalue() {
/* 43 */     return this.objvalue;
/*    */   }
/*    */   public void setObjvalue(String objvalue) {
/* 46 */     this.objvalue = objvalue;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.AuthAction
 * JD-Core Version:    0.6.0
 */