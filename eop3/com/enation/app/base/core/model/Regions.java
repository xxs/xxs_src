/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Regions
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8793615515414923123L;
/*    */   private Integer region_id;
/*    */   private Integer p_region_id;
/*    */   private String region_path;
/*    */   private Integer region_grade;
/*    */   private String local_name;
/*    */   private String zipcode;
/*    */   private Integer cod;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getRegion_id()
/*    */   {
/* 27 */     return this.region_id;
/*    */   }
/*    */   public void setRegion_id(Integer region_id) {
/* 30 */     this.region_id = region_id;
/*    */   }
/*    */   public Integer getRegion_grade() {
/* 33 */     return this.region_grade;
/*    */   }
/*    */   public void setRegion_grade(Integer region_grade) {
/* 36 */     this.region_grade = region_grade;
/*    */   }
/*    */   public Integer getCod() {
/* 39 */     return this.cod;
/*    */   }
/*    */   public void setCod(Integer cod) {
/* 42 */     this.cod = cod;
/*    */   }
/*    */   public Integer getP_region_id() {
/* 45 */     return this.p_region_id;
/*    */   }
/*    */   public void setP_region_id(Integer pRegionId) {
/* 48 */     this.p_region_id = pRegionId;
/*    */   }
/*    */   public String getRegion_path() {
/* 51 */     return this.region_path;
/*    */   }
/*    */   public void setRegion_path(String regionPath) {
/* 54 */     this.region_path = regionPath;
/*    */   }
/*    */   public String getLocal_name() {
/* 57 */     return this.local_name;
/*    */   }
/*    */   public void setLocal_name(String localName) {
/* 60 */     this.local_name = localName;
/*    */   }
/*    */   public String getZipcode() {
/* 63 */     return this.zipcode;
/*    */   }
/*    */   public void setZipcode(String zipcode) {
/* 66 */     this.zipcode = zipcode;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Regions
 * JD-Core Version:    0.6.0
 */