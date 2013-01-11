/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class EopUserDetail
/*    */ {
/*    */   private Integer id;
/*    */   private Integer userid;
/*    */   private String bussinessscope;
/*    */   private String regaddress;
/*    */   private Long regdate;
/*    */   private Integer corpscope;
/*    */   private String corpdescript;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 22 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 25 */     this.id = id;
/*    */   }
/*    */   public Integer getUserid() {
/* 28 */     return this.userid;
/*    */   }
/*    */   public void setUserid(Integer userid) {
/* 31 */     this.userid = userid;
/*    */   }
/*    */   public String getBussinessscope() {
/* 34 */     return this.bussinessscope;
/*    */   }
/*    */   public void setBussinessscope(String bussinessscope) {
/* 37 */     this.bussinessscope = bussinessscope;
/*    */   }
/*    */   public String getRegaddress() {
/* 40 */     return this.regaddress;
/*    */   }
/*    */   public void setRegaddress(String regaddress) {
/* 43 */     this.regaddress = regaddress;
/*    */   }
/*    */ 
/*    */   public Long getRegdate() {
/* 47 */     return this.regdate;
/*    */   }
/*    */   public void setRegdate(Long regdate) {
/* 50 */     this.regdate = regdate;
/*    */   }
/*    */   public Integer getCorpscope() {
/* 53 */     return this.corpscope;
/*    */   }
/*    */   public void setCorpscope(Integer corpscope) {
/* 56 */     this.corpscope = corpscope;
/*    */   }
/*    */   public String getCorpdescript() {
/* 59 */     return this.corpdescript;
/*    */   }
/*    */   public void setCorpdescript(String corpdescript) {
/* 62 */     this.corpdescript = corpdescript;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopUserDetail
 * JD-Core Version:    0.6.0
 */