/*    */ package com.enation.eop.resource.dto;
/*    */ 
/*    */ import com.enation.eop.processor.core.EopException;
/*    */ import com.enation.eop.resource.model.AdminUser;
/*    */ import com.enation.eop.resource.model.EopUser;
/*    */ import com.enation.eop.resource.model.EopUserDetail;
/*    */ 
/*    */ public class UserDTO
/*    */ {
/*    */   private EopUser user;
/*    */   private EopUserDetail userDetail;
/*    */   private AdminUser userAdmin;
/*    */   private SiteDTO siteDTO;
/*    */   private Integer siteid;
/*    */ 
/*    */   public void vaild()
/*    */   {
/* 31 */     if (this.userAdmin == null) {
/* 32 */       throw new EopException("用户管理员不能为空！");
/*    */     }
/* 34 */     if (this.userDetail == null) {
/* 35 */       throw new EopException("用户详细信息不能为空！");
/*    */     }
/* 37 */     if (this.siteDTO == null) {
/* 38 */       throw new EopException("用户站点不能为空！");
/*    */     }
/* 40 */     this.siteDTO.vaild();
/*    */   }
/*    */ 
/*    */   public void setUserId(Integer userid) {
/* 44 */     this.userDetail.setUserid(userid);
/* 45 */     this.userAdmin.setUserid(userid);
/* 46 */     this.siteDTO.setUserId(userid);
/*    */   }
/*    */ 
/*    */   public EopUser getUser() {
/* 50 */     return this.user;
/*    */   }
/*    */ 
/*    */   public void setUser(EopUser user) {
/* 54 */     this.user = user;
/*    */   }
/*    */ 
/*    */   public EopUserDetail getUserDetail() {
/* 58 */     return this.userDetail;
/*    */   }
/*    */ 
/*    */   public void setUserDetail(EopUserDetail userDetail) {
/* 62 */     this.userDetail = userDetail;
/*    */   }
/*    */ 
/*    */   public AdminUser getUserAdmin() {
/* 66 */     return this.userAdmin;
/*    */   }
/*    */ 
/*    */   public void setUserAdmin(AdminUser userAdmin) {
/* 70 */     this.userAdmin = userAdmin;
/*    */   }
/*    */ 
/*    */   public SiteDTO getSiteDTO() {
/* 74 */     return this.siteDTO;
/*    */   }
/*    */ 
/*    */   public void setSiteDTO(SiteDTO siteDTO) {
/* 78 */     this.siteDTO = siteDTO;
/*    */   }
/*    */ 
/*    */   public Integer getSiteid() {
/* 82 */     return this.siteid;
/*    */   }
/*    */ 
/*    */   public void setSiteid(Integer siteid) {
/* 86 */     this.siteDTO.setSiteId(siteid);
/* 87 */     this.siteid = siteid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.dto.UserDTO
 * JD-Core Version:    0.6.0
 */