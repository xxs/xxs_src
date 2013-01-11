/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.MultiSite;
/*    */ import com.enation.eop.resource.IThemeManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.Theme;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ThemeManagerImpl extends BaseSupport<Theme>
/*    */   implements IThemeManager
/*    */ {
/*    */   public void clean()
/*    */   {
/* 16 */     this.baseDaoSupport.execute("truncate table theme", new Object[0]);
/*    */   }
/*    */ 
/*    */   public Theme getTheme(Integer themeid) {
/* 20 */     return (Theme)this.baseDaoSupport.queryForObject("select * from theme where id=?", Theme.class, new Object[] { themeid });
/*    */   }
/*    */ 
/*    */   public List<Theme> list()
/*    */   {
/* 25 */     if (EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 0) {
/* 26 */       return this.baseDaoSupport.queryForList("select * from theme where siteid = 0", Theme.class, new Object[0]);
/*    */     }
/* 28 */     return this.baseDaoSupport.queryForList("select * from theme where siteid = ?", Theme.class, new Object[] { EopContext.getContext().getCurrentChildSite().getSiteid() });
/*    */   }
/*    */ 
/*    */   public List<Theme> list(int siteid)
/*    */   {
/* 39 */     return this.baseDaoSupport.queryForList("select * from theme where siteid = 0", Theme.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public void addBlank(Theme theme)
/*    */   {
/*    */     try
/*    */     {
/* 46 */       String basePath = EopSetting.APP_DATA_STORAGE_PATH;
/* 47 */       basePath = basePath + "/themes";
/*    */ 
/* 50 */       theme.setSiteid(Integer.valueOf(0));
/* 51 */       String contextPath = EopContext.getContext().getContextPath();
/* 52 */       String targetPath = EopSetting.IMG_SERVER_PATH + contextPath + "/themes/" + theme.getPath();
/* 53 */       FileUtil.copyFolder(basePath + "/blank/images", targetPath + "/images");
/* 54 */       FileUtil.copyFile(basePath + "/blank/preview.png", targetPath + "/preview.png");
/* 55 */       FileUtil.copyFolder(basePath + "/blank/css", targetPath + "/css");
/* 56 */       FileUtil.copyFolder(basePath + "/blank/js", targetPath + "/js");
/* 57 */       FileUtil.copyFolder(basePath + "/blank", EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath());
/*    */ 
/* 60 */       this.baseDaoSupport.insert("theme", theme);
/*    */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/* 63 */       throw new RuntimeException("创建主题出错");
/*    */     }
/*    */   }
/*    */ 
/*    */   public Integer add(Theme theme, boolean isCommon) {
/*    */     try {
/* 69 */       copy(theme, isCommon);
/*    */ 
/* 71 */       this.baseDaoSupport.insert("theme", theme);
/* 72 */       return Integer.valueOf(this.baseDaoSupport.getLastId("theme"));
/*    */     }
/*    */     catch (Exception e) {
/* 75 */       e.printStackTrace();
/* 76 */     }throw new RuntimeException("安装主题出错");
/*    */   }
/*    */ 
/*    */   private void copy(Theme theme, boolean isCommon)
/*    */     throws Exception
/*    */   {
/* 84 */     String basePath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + theme.getProductId();
/* 85 */     basePath = basePath + "/themes";
/*    */ 
/* 88 */     String contextPath = EopContext.getContext().getContextPath();
/*    */ 
/* 96 */     FileUtil.copyFolder(basePath + "/" + theme.getPath(), EopSetting.EOP_PATH + contextPath + "/themes/" + theme.getPath());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.ThemeManagerImpl
 * JD-Core Version:    0.6.0
 */