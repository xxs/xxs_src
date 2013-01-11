/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.IBatchSqlExecutor;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class BatchSqlExecutor
/*     */   implements IBatchSqlExecutor
/*     */ {
/*  27 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private ISiteManager siteManager;
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */   private IDaoSupport daoSupport;
/*     */ 
/*     */   public void exeucte(String sql)
/*     */   {
/*  41 */     String baseSql = sql.replaceAll("_<userid>", "");
/*  42 */     baseSql = baseSql.replaceAll("_<siteid>", "");
/*  43 */     this.sqlFileExecutor.execute(baseSql);
/*     */ 
/*  45 */     sql = getSiteUpdateSql(sql);
/*     */ 
/*  47 */     List siteList = this.siteManager.list();
/*     */ 
/*  49 */     int i = 0; for (int len = siteList.size(); i < len; i++)
/*     */     {
/*  51 */       Map site = (Map)siteList.get(i);
/*  52 */       String exesql = sql.replaceAll("<userid>", site.get("userid").toString());
/*  53 */       exesql = exesql.replaceAll("<siteid>", site.get("id").toString());
/*     */       try {
/*  55 */         this.sqlFileExecutor.execute(exesql);
/*     */       } catch (RuntimeException e) {
/*  57 */         this.logger.error("为站点userid[" + site.get("userid") + "]siteid[" + site.get("id") + "]执行sql出错，跳过...");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getSiteUpdateSql(String content)
/*     */   {
/*  69 */     StringBuffer sql = new StringBuffer();
/*     */ 
/*  71 */     content = StringUtil.delSqlComment(content);
/*     */ 
/*  74 */     content = content.replaceAll("\r", "");
/*  75 */     String[] sql_ar = content.split(";\n");
/*     */ 
/*  78 */     for (String s : sql_ar) {
/*  79 */       if ((s != null) && ((s.indexOf("<userid>") > 0) || (s.indexOf("<siteid>") > 0))) {
/*  80 */         sql.append(s + ";\n");
/*     */       }
/*     */     }
/*  83 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  87 */     String content = FileUtil.read("D:/work/eopnew/docs/version/2.2/update.sql", "UTF-8");
/*     */   }
/*     */ 
/*     */   public void executeForCms(String sql)
/*     */   {
/*  98 */     List siteList = this.siteManager.list();
/*  99 */     int i = 0; for (int len = siteList.size(); i < len; i++) {
/* 100 */       Map site = (Map)siteList.get(i);
/* 101 */       String userid = site.get("userid").toString();
/* 102 */       String siteid = site.get("id").toString();
/* 103 */       if (this.logger.isDebugEnabled()) {
/* 104 */         this.logger.debug("为站点userid[" + userid + "]siteid[" + siteid + "]执行cms sql ");
/*     */       }
/* 106 */       executeForCms(sql, userid, siteid);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void executeForCms(String sql, String userid, String siteid)
/*     */   {
/*     */     try
/*     */     {
/* 120 */       List modelList = listCmsDataModel(userid, siteid);
/* 121 */       for (Map model : modelList) {
/* 122 */         String tablename = model.get("english_name").toString();
/*     */ 
/* 124 */         if ("2".equals(EopSetting.RUNMODE)) {
/* 125 */           String exesql = sql.replaceAll("<tbname>", "es_" + tablename + "_" + userid + "_" + siteid);
/* 126 */           this.sqlFileExecutor.execute(exesql);
/*     */         } else {
/* 128 */           String exesql = sql.replaceAll("<tbname>", "es_" + tablename);
/* 129 */           if (this.logger.isDebugEnabled()) {
/* 130 */             this.logger.debug("执行sql[" + exesql + "] ");
/*     */           }
/* 132 */           this.sqlFileExecutor.execute(exesql);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 136 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<Map> listCmsDataModel(String userid, String siteid)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       if ("2".equals(EopSetting.RUNMODE)) {
/* 151 */         String sql = "select english_name from es_data_model_" + userid + "_" + siteid;
/* 152 */         return this.daoSupport.queryForList(sql, new Object[0]);
/*     */       }
/* 154 */       String sql = "select english_name from es_data_model";
/* 155 */       return this.daoSupport.queryForList(sql, new Object[0]);
/*     */     }
/*     */     catch (Exception e) {
/* 158 */       e.printStackTrace();
/* 159 */     }return new ArrayList();
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager()
/*     */   {
/* 164 */     return this.siteManager;
/*     */   }
/*     */   public void setSiteManager(ISiteManager siteManager) {
/* 167 */     this.siteManager = siteManager;
/*     */   }
/*     */   public ISqlFileExecutor getSqlFileExecutor() {
/* 170 */     return this.sqlFileExecutor;
/*     */   }
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 173 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport() {
/* 177 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 181 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.BatchSqlExecutor
 * JD-Core Version:    0.6.0
 */