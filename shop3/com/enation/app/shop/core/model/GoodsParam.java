/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GoodsParam
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */   private List valueList;
/*    */ 
/*    */   public void addValue(String _value)
/*    */   {
/* 21 */     if (this.valueList == null) this.valueList = new ArrayList();
/* 22 */     this.valueList.add(_value);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 26 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 29 */     this.name = name;
/*    */   }
/*    */   public String getValue() {
/* 32 */     return this.value;
/*    */   }
/*    */   public void setValue(String value) {
/* 35 */     this.value = value;
/*    */   }
/*    */   public List getValueList() {
/* 38 */     return this.valueList;
/*    */   }
/*    */   public void setValueList(List valueList) {
/* 41 */     this.valueList = valueList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.GoodsParam
 * JD-Core Version:    0.6.0
 */