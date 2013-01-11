/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SiteMapUrl
/*    */   implements Serializable
/*    */ {
/*    */   private String loc;
/*    */   private Long lastmod;
/*    */   private String changefreq;
/*    */   private String priority;
/*    */ 
/*    */   public String getLoc()
/*    */   {
/* 18 */     return this.loc;
/*    */   }
/*    */ 
/*    */   public void setLoc(String loc) {
/* 22 */     this.loc = loc;
/*    */   }
/*    */ 
/*    */   public Long getLastmod() {
/* 26 */     return this.lastmod;
/*    */   }
/*    */ 
/*    */   public void setLastmod(Long lastmod) {
/* 30 */     this.lastmod = lastmod;
/*    */   }
/*    */ 
/*    */   public String getChangefreq() {
/* 34 */     return this.changefreq;
/*    */   }
/*    */ 
/*    */   public void setChangefreq(String changefreq) {
/* 38 */     this.changefreq = changefreq;
/*    */   }
/*    */ 
/*    */   public String getPriority() {
/* 42 */     return this.priority;
/*    */   }
/*    */ 
/*    */   public void setPriority(String priority) {
/* 46 */     this.priority = priority;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.SiteMapUrl
 * JD-Core Version:    0.6.0
 */