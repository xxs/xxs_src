/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MultiSite
/*    */   implements Serializable
/*    */ {
/*    */   private Integer siteid;
/*    */   private Integer parentid;
/*    */   private Integer code;
/*    */   private String name;
/*    */   private String domain;
/*    */   private Integer themeid;
/*    */   private Integer sitelevel;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getSiteid()
/*    */   {
/* 18 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 22 */     this.siteid = siteid;
/*    */   }
/*    */ 
/*    */   public Integer getParentid() {
/* 26 */     return this.parentid;
/*    */   }
/*    */ 
/*    */   public void setParentid(Integer parentid) {
/* 30 */     this.parentid = parentid;
/*    */   }
/*    */ 
/*    */   public Integer getSitelevel() {
/* 34 */     return this.sitelevel;
/*    */   }
/*    */ 
/*    */   public void setSitelevel(Integer sitelevel) {
/* 38 */     this.sitelevel = sitelevel;
/*    */   }
/*    */ 
/*    */   public Integer getCode() {
/* 42 */     return this.code;
/*    */   }
/*    */ 
/*    */   public void setCode(Integer code) {
/* 46 */     this.code = code;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 50 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 54 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getDomain() {
/* 58 */     return this.domain;
/*    */   }
/*    */ 
/*    */   public void setDomain(String domain) {
/* 62 */     this.domain = domain;
/*    */   }
/*    */ 
/*    */   public Integer getThemeid() {
/* 66 */     return this.themeid;
/*    */   }
/*    */ 
/*    */   public void setThemeid(Integer themeid) {
/* 70 */     this.themeid = themeid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.MultiSite
 * JD-Core Version:    0.6.0
 */