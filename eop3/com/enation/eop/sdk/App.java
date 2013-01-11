/*     */ package com.enation.eop.sdk;
/*     */ 
/*     */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.dom4j.Document;
/*     */ 
/*     */ public abstract class App
/*     */   implements IApp
/*     */ {
/*  25 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*  27 */   protected boolean dataOnly = true;
/*     */   protected List<String> tables;
/*     */   protected int userid;
/*     */   protected int siteid;
/*     */ 
/*     */   protected boolean exceptTable(String table)
/*     */   {
/*  41 */     table = table.toLowerCase();
/*     */ 
/*  43 */     if (table.startsWith("eop_"))
/*  44 */       return true;
/*  45 */     if ((table.endsWith("menu")) && (!table.endsWith("site_menu")))
/*  46 */       return true;
/*  47 */     if (table.endsWith("themeuri"))
/*  48 */       return true;
/*  49 */     if (table.endsWith("theme")) {
/*  50 */       return true;
/*     */     }
/*  52 */     return table.endsWith("admintheme");
/*     */   }
/*     */ 
/*     */   protected String[] toArray(List<String> list)
/*     */   {
/*  63 */     String[] values = new String[list.size()];
/*  64 */     return (String[])list.toArray(values);
/*     */   }
/*     */ 
/*     */   public App() {
/*  68 */     this.tables = new ArrayList();
/*     */   }
/*     */ 
/*     */   protected void doInstall(String xmlFile)
/*     */   {
/*  73 */     if (this.logger.isDebugEnabled()) {
/*  74 */       this.logger.debug(getName() + " 开始执行 " + xmlFile + "...");
/*     */     }
/*  76 */     DBSolutionFactory.dbImport(xmlFile, "es_");
/*     */ 
/*  83 */     if (this.logger.isDebugEnabled()) {
/*  84 */       this.logger.debug(getName() + " 执行 " + xmlFile + "成功！");
/*     */     }
/*     */ 
/*  87 */     cleanCache();
/*     */   }
/*     */   protected void cleanCache() {
/*  90 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  91 */     this.userid = site.getUserid().intValue();
/*  92 */     this.siteid = site.getId().intValue();
/*     */   }
/*     */ 
/*     */   public String dumpXml(Document setup) {
/*  96 */     List dataTable = new ArrayList();
/*  97 */     int i = 0; for (int len = this.tables.size(); i < len; i++) {
/*  98 */       String table = (String)this.tables.get(i);
/*  99 */       if (!exceptTable(table))
/* 100 */         dataTable.add(table);
/*     */     }
/* 102 */     StringBuffer xml = new StringBuffer();
/* 103 */     xml.append(DBSolutionFactory.dbExport(toArray(dataTable), this.dataOnly, "es_"));
/* 104 */     return xml.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.App
 * JD-Core Version:    0.6.0
 */