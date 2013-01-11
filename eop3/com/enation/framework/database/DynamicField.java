/*    */ package com.enation.framework.database;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DynamicField
/*    */ {
/*    */   private Map<String, Object> fields;
/*    */ 
/*    */   public DynamicField()
/*    */   {
/* 16 */     this.fields = new HashMap();
/*    */   }
/*    */ 
/*    */   public void addField(String name, Object value) {
/* 20 */     this.fields.put(name, value);
/*    */   }
/*    */   @NotDbField
/*    */   public Map<String, Object> getFields() {
/* 25 */     return this.fields;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.DynamicField
 * JD-Core Version:    0.6.0
 */