/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ 
/*    */ public class Help
/*    */ {
/*    */   private Integer id;
/*    */   private String helpid;
/*    */   private String title;
/*    */   private String content;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 18 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 22 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getHelpid() {
/* 26 */     return this.helpid;
/*    */   }
/*    */ 
/*    */   public void setHelpid(String helpid) {
/* 30 */     this.helpid = helpid;
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 34 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 38 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public String getContent() {
/* 42 */     return this.content;
/*    */   }
/*    */ 
/*    */   public void setContent(String content) {
/* 46 */     this.content = content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Help
 * JD-Core Version:    0.6.0
 */