/*    */ package com.enation.eop.resource.dto;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.EopSiteAdmin;
/*    */ import com.enation.eop.resource.model.EopSiteDomain;
/*    */ 
/*    */ public class SiteDTO
/*    */ {
/*    */   private EopSite site;
/*    */   private EopSiteDomain domain;
/*    */   private EopSiteAdmin siteAdmin;
/*    */ 
/*    */   public void vaild()
/*    */   {
/* 28 */     if (this.domain == null) {
/* 29 */       throw new IllegalArgumentException("站点至少要有一个域名！");
/*    */     }
/*    */ 
/* 32 */     if (this.siteAdmin == null)
/* 33 */       throw new IllegalArgumentException("站点至少应该指定一位管理员！");
/*    */   }
/*    */ 
/*    */   public void setUserId(Integer userid)
/*    */   {
/* 38 */     this.site.setUserid(userid);
/* 39 */     this.domain.setUserid(userid);
/* 40 */     this.siteAdmin.setUserid(userid);
/*    */   }
/*    */ 
/*    */   public void setSiteId(Integer siteid) {
/* 44 */     this.domain.setSiteid(siteid);
/* 45 */     this.siteAdmin.setSiteid(siteid);
/*    */   }
/*    */ 
/*    */   public void setManagerid(Integer managerid) {
/* 49 */     this.siteAdmin.setManagerid(managerid);
/*    */   }
/*    */ 
/*    */   public EopSite getSite() {
/* 53 */     return this.site;
/*    */   }
/*    */ 
/*    */   public void setSite(EopSite site) {
/* 57 */     this.site = site;
/*    */   }
/*    */ 
/*    */   public EopSiteDomain getDomain() {
/* 61 */     return this.domain;
/*    */   }
/*    */ 
/*    */   public void setDomain(EopSiteDomain domain) {
/* 65 */     this.domain = domain;
/*    */   }
/*    */ 
/*    */   public EopSiteAdmin getSiteAdmin() {
/* 69 */     return this.siteAdmin;
/*    */   }
/*    */ 
/*    */   public void setSiteAdmin(EopSiteAdmin siteAdmin) {
/* 73 */     this.siteAdmin = siteAdmin;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.dto.SiteDTO
 * JD-Core Version:    0.6.0
 */