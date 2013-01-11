/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.eop.resource.model.Access;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.ExcelUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AccessExporter
/*     */   implements Runnable
/*     */ {
/*     */   private IDaoSupport daoSupport;
/*     */   private Integer userid;
/*     */   private Integer siteid;
/*     */ 
/*     */   public void setContext(Integer userid, Integer siteid)
/*     */   {
/*  27 */     this.userid = userid;
/*  28 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  33 */     String tablename = "es_access";
/*  34 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  35 */       tablename = tablename + "_" + this.userid + "_" + this.siteid;
/*     */     }
/*     */ 
/*  39 */     ExcelUtil excelUtil = new ExcelUtil();
/*     */ 
/*  42 */     InputStream in = FileUtil.getResourceAsStream("com/enation/eop/resource/access.xls");
/*  43 */     excelUtil.openModal(in);
/*     */ 
/*  46 */     String[] lastMonth = DateUtil.getLastMonth();
/*  47 */     int start = (int)(DateUtil.toDate(lastMonth[0], "yyyy-MM-dd").getTime() / 1000L);
/*  48 */     int end = (int)(DateUtil.toDate(lastMonth[1], "yyyy-MM-dd").getTime() / 1000L);
/*     */ 
/*  50 */     String sql = "select * from " + tablename + " where access_time>=? and access_time<=? order by access_time";
/*  51 */     List list = this.daoSupport.queryForList(sql, Access.class, new Object[] { Integer.valueOf(start), Integer.valueOf(end) });
/*     */ 
/*  53 */     if ((list != null) && (!list.isEmpty()))
/*     */     {
/*  55 */       int i = 1;
/*  56 */       for (Access access : list) {
/*  57 */         excelUtil.writeStringToCell(i, 0, access.getIp());
/*  58 */         excelUtil.writeStringToCell(i, 1, access.getArea());
/*  59 */         excelUtil.writeStringToCell(i, 2, access.getPage());
/*  60 */         excelUtil.writeStringToCell(i, 3, "" + access.getStay_time());
/*  61 */         excelUtil.writeStringToCell(i, 4, DateUtil.toString(new Date(access.getAccess_time() * 1000L), "yyyy-MM-dd hh:mm:ss"));
/*  62 */         excelUtil.writeStringToCell(i, 5, "" + access.getPoint());
/*  63 */         i++;
/*     */       }
/*  65 */       String target = EopSetting.IMG_SERVER_PATH;
/*     */ 
/*  67 */       if ("2".equals(EopSetting.RUNMODE))
/*  68 */         target = target + "/user/" + this.userid + "/" + this.siteid + "/access";
/*     */       else {
/*  70 */         target = target + "/access";
/*     */       }
/*  72 */       File file = new File(target);
/*  73 */       if (!file.exists()) file.mkdirs();
/*     */ 
/*  75 */       excelUtil.writeToFile(target + "/access" + lastMonth[0].replaceAll("-01", "") + ".xls");
/*     */ 
/*  80 */       sql = "select sum(point) point  from " + tablename + " where access_time>=? and access_time<=?";
/*  81 */       Long sumpoint = Long.valueOf(this.daoSupport.queryForLong(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }));
/*  82 */       this.daoSupport.execute("update eop_site set sumpoint=sumpoint+?  where id=?", new Object[] { sumpoint, this.siteid });
/*     */ 
/*  85 */       sql = "select count(0) c  from " + tablename + " where access_time>=? and access_time<=?";
/*  86 */       Long sumaccess = Long.valueOf(this.daoSupport.queryForLong(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }));
/*  87 */       this.daoSupport.execute("update eop_site set sumaccess=sumaccess+?  where id=?", new Object[] { sumaccess, this.siteid });
/*     */ 
/*  91 */       this.daoSupport.execute("delete  from " + tablename + " where access_time>=? and access_time<=?", new Object[] { Integer.valueOf(start), Integer.valueOf(end) });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  96 */     String date = "2010-06-03";
/*  97 */     System.out.println(DateUtil.toDate(date, "yyyy-MM-dd").getTime() / 1000L);
/*     */   }
/*     */ 
/*     */   public Integer getUserid()
/*     */   {
/* 104 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/* 108 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public Integer getSiteid() {
/* 112 */     return this.siteid;
/*     */   }
/*     */ 
/*     */   public void setSiteid(Integer siteid) {
/* 116 */     this.siteid = siteid;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport() {
/* 120 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 124 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.AccessExporter
 * JD-Core Version:    0.6.0
 */