/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BaseInfoAction extends WWAction
/*     */ {
/*     */   private EopUser eopUser;
/*     */   private Integer userid;
/*     */   private IUserManager userManager;
/*     */   private File cologo;
/*     */   private String cologoFileName;
/*     */   private File license;
/*     */   private String licenseFileName;
/*     */ 
/*     */   public File getCologo()
/*     */   {
/*  28 */     return this.cologo;
/*     */   }
/*     */ 
/*     */   public void setCologo(File cologo) {
/*  32 */     this.cologo = cologo;
/*     */   }
/*     */ 
/*     */   public String getCologoFileName() {
/*  36 */     return this.cologoFileName;
/*     */   }
/*     */ 
/*     */   public void setCologoFileName(String cologoFileName) {
/*  40 */     this.cologoFileName = cologoFileName;
/*     */   }
/*     */ 
/*     */   public File getLicense()
/*     */   {
/*  48 */     return this.license;
/*     */   }
/*     */ 
/*     */   public void setLicense(File license) {
/*  52 */     this.license = license;
/*     */   }
/*     */ 
/*     */   public String getLicenseFileName() {
/*  56 */     return this.licenseFileName;
/*     */   }
/*     */ 
/*     */   public void setLicenseFileName(String licenseFileName) {
/*  60 */     this.licenseFileName = licenseFileName;
/*     */   }
/*     */ 
/*     */   public EopUser getEopUser()
/*     */   {
/*  65 */     return this.eopUser;
/*     */   }
/*     */ 
/*     */   public void setEopUser(EopUser eopUser) {
/*  69 */     this.eopUser = eopUser;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */     throws Exception
/*     */   {
/*  75 */     this.userid = EopContext.getContext().getCurrentSite().getUserid();
/*  76 */     this.eopUser = this.userManager.get(this.userid);
/*  77 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save() throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  84 */       if (this.cologo != null) {
/*  85 */         String logoPath = UploadUtil.upload(this.cologo, this.cologoFileName, "user");
/*     */ 
/*  87 */         this.eopUser.setLogofile(logoPath);
/*     */       }
/*     */ 
/*  91 */       if (this.license != null)
/*     */       {
/*  93 */         String licensePath = UploadUtil.upload(this.license, this.licenseFileName, "user");
/*     */ 
/*  95 */         this.eopUser.setLicensefile(licensePath);
/*     */       }
/*     */ 
/*  99 */       this.userManager.edit(this.eopUser);
/* 100 */       this.msgs.add("修改成功");
/*     */     }
/*     */     catch (RuntimeException e) {
/* 103 */       this.msgs.add(e.getMessage());
/*     */     }
/* 105 */     this.urls.put("用户信息页面", "baseInfo.do");
/* 106 */     return "message";
/*     */   }
/*     */ 
/*     */   public Integer getUserid() {
/* 110 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/* 114 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 118 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 122 */     this.userManager = userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.BaseInfoAction
 * JD-Core Version:    0.6.0
 */