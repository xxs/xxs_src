/*    */ package com.enation.eop.resource.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Dns
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7525130004L;
/*    */   private String domain;
/*    */   private EopSite site;
/*    */ 
/*    */   public String getDomain()
/*    */   {
/* 21 */     return this.domain;
/*    */   }
/*    */   public void setDomain(String domain) {
/* 24 */     this.domain = domain;
/*    */   }
/*    */   public EopSite getSite() {
/* 27 */     return this.site;
/*    */   }
/*    */   public void setSite(EopSite site) {
/* 30 */     this.site = site;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Dns
 * JD-Core Version:    0.6.0
 */