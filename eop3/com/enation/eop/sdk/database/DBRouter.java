/*    */ package com.enation.eop.sdk.database;
/*    */ 
/*    */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.database.IDBRouter;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ 
/*    */ public class DBRouter
/*    */   implements IDBRouter
/*    */ {
/*    */   public static final String EXECUTECHAR = "!-->";
/* 27 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   private JdbcTemplate jdbcTemplate;
/*    */   private String prefix;
/*    */ 
/*    */   public void setPrefix(String prefix)
/*    */   {
/* 34 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */   public void doSaasInstall(String xmlFile) {
/* 38 */     if ("1".equals(EopSetting.RUNMODE)) {
/* 39 */       return;
/*    */     }
/*    */ 
/* 42 */     this.prefix = (this.prefix == null ? "" : this.prefix);
/* 43 */     DBSolutionFactory.dbImport(xmlFile, this.prefix);
/*    */   }
/*    */ 
/*    */   public String getTableName(String moudle)
/*    */   {
/* 48 */     String result = StringUtil.addPrefix(moudle, this.prefix);
/* 49 */     if ("1".equals(EopSetting.RUNMODE)) {
/* 50 */       return result;
/*    */     }
/*    */ 
/* 53 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 54 */     Integer userid = site.getUserid();
/* 55 */     Integer siteid = site.getId();
/*    */ 
/* 57 */     return StringUtil.addSuffix(result, "_" + userid + "_" + siteid);
/*    */   }
/*    */ 
/*    */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/* 61 */     this.jdbcTemplate = jdbcTemplate;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.database.DBRouter
 * JD-Core Version:    0.6.0
 */