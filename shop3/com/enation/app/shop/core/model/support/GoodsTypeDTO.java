/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Attribute;
/*    */ import com.enation.app.shop.core.model.GoodsType;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GoodsTypeDTO extends GoodsType
/*    */ {
/*    */   private List<Attribute> propList;
/*    */   private ParamGroup[] paramGroups;
/*    */   private List brandList;
/*    */ 
/*    */   public ParamGroup[] getParamGroups()
/*    */   {
/* 15 */     return this.paramGroups;
/*    */   }
/*    */   public void setParamGroups(ParamGroup[] paramGroups) {
/* 18 */     this.paramGroups = paramGroups;
/*    */   }
/*    */   public List<Attribute> getPropList() {
/* 21 */     return this.propList;
/*    */   }
/*    */   public void setPropList(List<Attribute> propList) {
/* 24 */     this.propList = propList;
/*    */   }
/*    */   public List getBrandList() {
/* 27 */     return this.brandList;
/*    */   }
/*    */   public void setBrandList(List brandList) {
/* 30 */     this.brandList = brandList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.GoodsTypeDTO
 * JD-Core Version:    0.6.0
 */