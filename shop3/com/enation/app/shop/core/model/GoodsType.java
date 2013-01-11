/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class GoodsType
/*    */   implements Serializable
/*    */ {
/*    */   private Integer type_id;
/*    */   private String name;
/*    */   private String props;
/*    */   private String params;
/*    */   private int disabled;
/*    */   private int is_physical;
/*    */   private int have_prop;
/*    */   private int have_parm;
/*    */   private int join_brand;
/*    */   private int have_field;
/*    */   private Integer[] brand_ids;
/*    */ 
/*    */   public int getDisabled()
/*    */   {
/* 29 */     return this.disabled;
/*    */   }
/*    */   public void setDisabled(int disabled) {
/* 32 */     this.disabled = disabled;
/*    */   }
/*    */   public int getHave_parm() {
/* 35 */     return this.have_parm;
/*    */   }
/*    */   public void setHave_parm(int have_parm) {
/* 38 */     this.have_parm = have_parm;
/*    */   }
/*    */   public int getHave_prop() {
/* 41 */     return this.have_prop;
/*    */   }
/*    */   public void setHave_prop(int have_prop) {
/* 44 */     this.have_prop = have_prop;
/*    */   }
/*    */   public int getIs_physical() {
/* 47 */     return this.is_physical;
/*    */   }
/*    */   public void setIs_physical(int is_physical) {
/* 50 */     this.is_physical = is_physical;
/*    */   }
/*    */   public int getJoin_brand() {
/* 53 */     return this.join_brand;
/*    */   }
/*    */   public void setJoin_brand(int join_brand) {
/* 56 */     this.join_brand = join_brand;
/*    */   }
/*    */   public String getName() {
/* 59 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 62 */     this.name = name;
/*    */   }
/*    */   public String getParams() {
/* 65 */     return this.params;
/*    */   }
/*    */   public void setParams(String params) {
/* 68 */     this.params = params;
/*    */   }
/*    */   public String getProps() {
/* 71 */     return this.props;
/*    */   }
/*    */   public void setProps(String props) {
/* 74 */     this.props = props;
/*    */   }
/* 78 */   @PrimaryKeyField
/*    */   public Integer getType_id() { return this.type_id; }
/*    */ 
/*    */   public void setType_id(Integer type_id) {
/* 81 */     this.type_id = type_id;
/*    */   }
/*    */   public Integer[] getBrand_ids() {
/* 84 */     return this.brand_ids;
/*    */   }
/*    */   public void setBrand_ids(Integer[] brand_ids) {
/* 87 */     this.brand_ids = brand_ids;
/*    */   }
/*    */   public int getHave_field() {
/* 90 */     return this.have_field;
/*    */   }
/*    */   public void setHave_field(int haveField) {
/* 93 */     this.have_field = haveField;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.GoodsType
 * JD-Core Version:    0.6.0
 */