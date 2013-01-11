/*    */ package com.enation.app.base.core.service.dbsolution;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.DataSource;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ 
/*    */ public class DBSolutionFactory
/*    */ {
/*    */   public static IDBSolution getDBSolution()
/*    */   {
/* 21 */     IDBSolution result = null;
/* 22 */     if (EopSetting.DBTYPE.equals("1"))
/* 23 */       result = (IDBSolution)SpringContextHolder.getBean("mysqlSolution");
/* 24 */     else if (EopSetting.DBTYPE.equals("2"))
/* 25 */       result = (IDBSolution)SpringContextHolder.getBean("oracleSolution");
/* 26 */     else if (EopSetting.DBTYPE.equals("3"))
/* 27 */       result = (IDBSolution)SpringContextHolder.getBean("sqlserverSolution");
/*    */     else {
/* 29 */       throw new RuntimeException("未知的数据库类型");
/*    */     }
/* 31 */     return result;
/*    */   }
/*    */ 
/*    */   public static Connection getConnection(JdbcTemplate jdbcTemplate) {
/* 35 */     if (jdbcTemplate == null)
/* 36 */       jdbcTemplate = (JdbcTemplate)SpringContextHolder.getBean("jdbcTemplate");
/*    */     try
/*    */     {
/* 39 */       return jdbcTemplate.getDataSource().getConnection();
/*    */     } catch (SQLException e) {
/* 41 */       e.printStackTrace();
/* 42 */     }return null;
/*    */   }
/*    */ 
/*    */   public static boolean dbImport(String xml, String prefix)
/*    */   {
/* 47 */     Connection conn = getConnection(null);
/* 48 */     IDBSolution dbsolution = getDBSolution();
/* 49 */     dbsolution.setPrefix(prefix);
/* 50 */     dbsolution.setConnection(conn);
/*    */     boolean result;
/*    */     boolean result;
/* 52 */     if (EopSetting.RUNMODE.equals("1")) {
/* 53 */       result = dbsolution.dbImport(xml);
/*    */     } else {
/* 55 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 56 */       Integer userid = site.getUserid();
/* 57 */       Integer siteid = site.getId();
/* 58 */       result = dbsolution.dbSaasImport(xml, userid.intValue(), siteid.intValue());
/*    */     }
/*    */     try {
/* 61 */       conn.close();
/*    */     } catch (SQLException e) {
/* 63 */       e.printStackTrace();
/* 64 */       return false;
/*    */     }
/* 66 */     return result;
/*    */   }
/*    */ 
/*    */   public static String dbExport(String[] tables, boolean dataOnly, String prefix) {
/* 70 */     Connection conn = getConnection(null);
/* 71 */     IDBSolution dbsolution = getDBSolution();
/* 72 */     dbsolution.setPrefix(prefix);
/* 73 */     dbsolution.setConnection(conn);
/* 74 */     String result = "";
/* 75 */     if (EopSetting.RUNMODE.equals("1")) {
/* 76 */       result = dbsolution.dbExport(tables, dataOnly);
/*    */     } else {
/* 78 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 79 */       Integer userid = site.getUserid();
/* 80 */       Integer siteid = site.getId();
/* 81 */       result = dbsolution.dbSaasExport(tables, dataOnly, userid.intValue(), siteid.intValue());
/*    */     }
/*    */     try {
/* 84 */       conn.close();
/*    */     } catch (SQLException e) {
/* 86 */       e.printStackTrace();
/* 87 */       return null;
/*    */     }
/* 89 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.DBSolutionFactory
 * JD-Core Version:    0.6.0
 */