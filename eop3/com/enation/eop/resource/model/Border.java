/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Border extends Resource
/*    */ {
/*    */   private Integer id;
/*    */   private String borderid;
/*    */   private String bordername;
/*    */   private String themepath;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 18 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 21 */     this.id = id;
/*    */   }
/*    */   public String getBorderid() {
/* 24 */     return this.borderid;
/*    */   }
/*    */   public void setBorderid(String borderid) {
/* 27 */     this.borderid = borderid;
/*    */   }
/*    */   public String getBordername() {
/* 30 */     return this.bordername;
/*    */   }
/*    */   public void setBordername(String bordername) {
/* 33 */     this.bordername = bordername;
/*    */   }
/*    */   public String getThemepath() {
/* 36 */     return this.themepath;
/*    */   }
/*    */   public void setThemepath(String themepath) {
/* 39 */     this.themepath = themepath;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Border
 * JD-Core Version:    0.6.0
 */