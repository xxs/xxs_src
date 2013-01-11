/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Tag
/*    */   implements Serializable
/*    */ {
/*    */   private Integer tag_id;
/*    */   private String tag_name;
/*    */   private Integer rel_count;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getTag_id()
/*    */   {
/* 17 */     return this.tag_id;
/*    */   }
/*    */   public void setTag_id(Integer tagId) {
/* 20 */     this.tag_id = tagId;
/*    */   }
/*    */   public String getTag_name() {
/* 23 */     return this.tag_name;
/*    */   }
/*    */   public void setTag_name(String tagName) {
/* 26 */     this.tag_name = tagName;
/*    */   }
/*    */   public Integer getRel_count() {
/* 29 */     return this.rel_count;
/*    */   }
/*    */   public void setRel_count(Integer relCount) {
/* 32 */     this.rel_count = relCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Tag
 * JD-Core Version:    0.6.0
 */