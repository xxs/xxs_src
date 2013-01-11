/*    */ package com.enation.framework.jms;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EmailModel
/*    */ {
/* 11 */   private Map data = new HashMap();
/*    */ 
/* 13 */   private String title = "";
/*    */ 
/* 15 */   private String to = "";
/*    */ 
/* 17 */   private String template = "";
/*    */   private EopContext eopContext;
/*    */ 
/*    */   public EmailModel()
/*    */   {
/* 24 */     this.eopContext = EopContext.getContext();
/*    */   }
/*    */ 
/*    */   public EmailModel(Map data, String title, String to, String template)
/*    */   {
/* 32 */     this.data = data;
/* 33 */     this.title = title;
/* 34 */     this.to = to;
/* 35 */     this.template = template;
/*    */   }
/*    */ 
/*    */   public Map getData() {
/* 39 */     return this.data;
/*    */   }
/*    */ 
/*    */   public void setData(Map data) {
/* 43 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 47 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 51 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public String getTo() {
/* 55 */     return this.to;
/*    */   }
/*    */ 
/*    */   public void setTo(String to) {
/* 59 */     this.to = to;
/*    */   }
/*    */ 
/*    */   public String getTemplate() {
/* 63 */     return this.template;
/*    */   }
/*    */ 
/*    */   public void setTemplate(String template) {
/* 67 */     this.template = template;
/*    */   }
/*    */ 
/*    */   public EopContext getEopContext()
/*    */   {
/* 72 */     return this.eopContext;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.jms.EmailModel
 * JD-Core Version:    0.6.0
 */