/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopApp;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ 
/*    */ public class FacadePage
/*    */ {
/*    */   private Integer id;
/*    */   private EopSite site;
/*    */   private String uri;
/*    */   private EopApp app;
/*    */ 
/*    */   public FacadePage()
/*    */   {
/*    */   }
/*    */ 
/*    */   public FacadePage(EopSite site)
/*    */   {
/* 25 */     this.site = site;
/*    */   }
/*    */ 
/*    */   public EopSite getSite() {
/* 29 */     return this.site;
/*    */   }
/*    */ 
/*    */   public void setSite(EopSite site) {
/* 33 */     this.site = site;
/*    */   }
/*    */ 
/*    */   public String getUri() {
/* 37 */     return this.uri;
/*    */   }
/*    */ 
/*    */   public void setUri(String uri) {
/* 41 */     this.uri = uri;
/*    */   }
/*    */ 
/*    */   public Integer getId() {
/* 45 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 49 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public EopApp getApp() {
/* 53 */     return this.app;
/*    */   }
/*    */ 
/*    */   public void setApp(EopApp app) {
/* 57 */     this.app = app;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.FacadePage
 * JD-Core Version:    0.6.0
 */