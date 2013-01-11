/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.framework.database.NotDbField;
/*    */ 
/*    */ public class TypeArea
/*    */ {
/*    */   private Integer type_id;
/*    */   private String area_id_group;
/*    */   private String area_name_group;
/*    */   private String expressions;
/*    */   private Integer has_cod;
/*    */   private String config;
/*    */   private TypeAreaConfig typeAreaConfig;
/*    */ 
/*    */   public Integer getType_id()
/*    */   {
/* 21 */     return this.type_id;
/*    */   }
/*    */   public void setType_id(Integer typeId) {
/* 24 */     this.type_id = typeId;
/*    */   }
/*    */   public String getArea_id_group() {
/* 27 */     return this.area_id_group;
/*    */   }
/*    */   public void setArea_id_group(String areaIdGroup) {
/* 30 */     this.area_id_group = areaIdGroup;
/*    */   }
/*    */   public String getArea_name_group() {
/* 33 */     return this.area_name_group;
/*    */   }
/*    */   public void setArea_name_group(String areaNameGroup) {
/* 36 */     this.area_name_group = areaNameGroup;
/*    */   }
/*    */   public String getExpressions() {
/* 39 */     return this.expressions;
/*    */   }
/*    */   public void setExpressions(String expressions) {
/* 42 */     this.expressions = expressions;
/*    */   }
/*    */   public Integer getHas_cod() {
/* 45 */     return this.has_cod;
/*    */   }
/*    */   public void setHas_cod(Integer hasCod) {
/* 48 */     this.has_cod = hasCod;
/*    */   }
/*    */   public String getConfig() {
/* 51 */     return this.config;
/*    */   }
/*    */   public void setConfig(String config) {
/* 54 */     this.config = config;
/*    */   }
/*    */   @NotDbField
/*    */   public TypeAreaConfig getTypeAreaConfig() {
/* 59 */     return this.typeAreaConfig;
/*    */   }
/*    */   public void setTypeAreaConfig(TypeAreaConfig typeAreaConfig) {
/* 62 */     this.typeAreaConfig = typeAreaConfig;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.TypeArea
 * JD-Core Version:    0.6.0
 */