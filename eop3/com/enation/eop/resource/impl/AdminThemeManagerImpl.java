/*     */ package com.enation.eop.resource.impl;
/*     */ 
/*     */ import com.enation.eop.resource.IAdminThemeManager;
/*     */ import com.enation.eop.resource.model.AdminTheme;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class AdminThemeManagerImpl extends BaseSupport<AdminTheme>
/*     */   implements IAdminThemeManager
/*     */ {
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer add(AdminTheme theme, boolean isCommon)
/*     */   {
/*     */     try
/*     */     {
/*  35 */       this.baseDaoSupport.insert("admintheme", theme);
/*  36 */       return Integer.valueOf(this.baseDaoSupport.getLastId("admintheme"));
/*     */     } catch (Exception e) {
/*  38 */       e.printStackTrace();
/*  39 */     }throw new RuntimeException("安装后台主题出错");
/*     */   }
/*     */ 
/*     */   public void clean()
/*     */   {
/*  45 */     this.baseDaoSupport.execute("truncate table admintheme", new Object[0]);
/*     */   }
/*     */ 
/*     */   private void copy(AdminTheme theme, boolean isCommon)
/*     */     throws Exception
/*     */   {
/*  51 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/*  54 */     String basePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + theme.getProductId();
/*  55 */     basePath = basePath + "/adminthemes";
/*     */ 
/*  58 */     String contextPath = EopContext.getContext().getContextPath();
/*     */ 
/*  60 */     String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/adminthemes/" + theme.getPath();
/*  61 */     FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/images", targetPath + "/images");
/*  62 */     FileUtil.copyFile(basePath + "/" + theme.getPath() + "/preview.png", targetPath + "/preview.png");
/*  63 */     FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/css", targetPath + "/css");
/*  64 */     FileUtil.copyFolder(basePath + "/" + theme.getPath() + "/js", targetPath + "/js");
/*     */ 
/*  67 */     FileUtil.copyFolder(basePath + "/" + theme.getPath(), EopSetting.EOP_PATH + contextPath + "/adminthemes/" + theme.getPath());
/*     */   }
/*     */ 
/*     */   public AdminTheme get(Integer themeid)
/*     */   {
/*  97 */     List list = this.baseDaoSupport.queryForList("select * from admintheme where id=?", AdminTheme.class, new Object[] { themeid });
/*  98 */     if ((list == null) || (list.isEmpty())) return null;
/*     */ 
/* 100 */     return (AdminTheme)list.get(0);
/*     */   }
/*     */ 
/*     */   public List<AdminTheme> list()
/*     */   {
/* 106 */     return this.baseDaoSupport.queryForList("select * from admintheme ", AdminTheme.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public IDaoSupport<AdminTheme> getDaoSupport() {
/* 110 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport<AdminTheme> daoSupport) {
/* 114 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.AdminThemeManagerImpl
 * JD-Core Version:    0.6.0
 */