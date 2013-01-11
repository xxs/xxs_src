/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Specification
/*    */ {
/*    */   private Integer spec_id;
/*    */   private String spec_name;
/*    */   private Integer spec_show_type;
/*    */   private Integer spec_type;
/*    */   private String spec_memo;
/*    */   private List<SpecValue> valueList;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getSpec_id()
/*    */   {
/* 24 */     return this.spec_id;
/*    */   }
/*    */   public void setSpec_id(Integer specId) {
/* 27 */     this.spec_id = specId;
/*    */   }
/*    */   public String getSpec_name() {
/* 30 */     return this.spec_name;
/*    */   }
/*    */   public void setSpec_name(String specName) {
/* 33 */     this.spec_name = specName;
/*    */   }
/*    */   public Integer getSpec_show_type() {
/* 36 */     return this.spec_show_type;
/*    */   }
/*    */   public void setSpec_show_type(Integer specShowType) {
/* 39 */     this.spec_show_type = specShowType;
/*    */   }
/*    */   public Integer getSpec_type() {
/* 42 */     return this.spec_type;
/*    */   }
/*    */   public void setSpec_type(Integer specType) {
/* 45 */     this.spec_type = specType;
/*    */   }
/*    */   public String getSpec_memo() {
/* 48 */     return this.spec_memo;
/*    */   }
/*    */   public void setSpec_memo(String specMemo) {
/* 51 */     this.spec_memo = specMemo;
/*    */   }
/*    */   @NotDbField
/*    */   public List<SpecValue> getValueList() {
/* 56 */     return this.valueList;
/*    */   }
/*    */   public void setValueList(List<SpecValue> valueList) {
/* 59 */     this.valueList = valueList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Specification
 * JD-Core Version:    0.6.0
 */