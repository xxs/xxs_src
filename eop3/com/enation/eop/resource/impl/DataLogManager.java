/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.DataLog;
/*    */ import com.enation.app.base.core.model.DataLogMapper;
/*    */ import com.enation.eop.resource.IDataLogManager;
/*    */ import com.enation.eop.resource.IDomainManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.EopSiteDomain;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.util.DateUtil;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DataLogManager extends BaseSupport
/*    */   implements IDataLogManager
/*    */ {
/*    */   private IDomainManager domainManager;
/*    */ 
/*    */   public void add(DataLog datalog)
/*    */   {
/* 30 */     if (datalog == null) throw new IllegalArgumentException("参数datalog为空");
/* 31 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 33 */     datalog.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
/* 34 */     datalog.setUserid(site.getUserid());
/* 35 */     datalog.setSiteid(site.getId());
/* 36 */     datalog.setSitename(site.getSitename());
/*    */ 
/* 38 */     List domains = this.domainManager.listSiteDomain(site.getId());
/* 39 */     if ((domains != null) && (domains.size() > 0)) {
/* 40 */       datalog.setDomain(((EopSiteDomain)domains.get(0)).getDomain());
/*    */     }
/* 42 */     this.daoSupport.insert("eop_data_log", datalog);
/*    */   }
/*    */ 
/*    */   public Page list(String start, String end, int pageNo, int pageSize)
/*    */   {
/* 47 */     Date startDate = start == null ? null : DateUtil.toDate(start, "yyyy-MM-dd");
/* 48 */     Date endDate = end == null ? null : DateUtil.toDate(end, "yyyy-MM-dd");
/*    */ 
/* 51 */     endDate = endDate == null ? new Date() : endDate;
/*    */ 
/* 53 */     Integer startSec = null;
/* 54 */     if (startDate != null)
/* 55 */       startSec = Integer.valueOf((int)(startDate.getTime() / 1000L));
/* 56 */     int endSec = (int)(endDate.getTime() / 1000L);
/*    */ 
/* 58 */     String sql = "select * from eop_data_log where dateline<=" + endSec;
/* 59 */     if (startDate != null) sql = sql + " and dateline>=" + startSec;
/* 60 */     sql = sql + " order by dateline desc";
/*    */ 
/* 63 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, new DataLogMapper(), new Object[0]);
/*    */   }
/*    */ 
/*    */   public IDomainManager getDomainManager() {
/* 67 */     return this.domainManager;
/*    */   }
/*    */ 
/*    */   public void setDomainManager(IDomainManager domainManager) {
/* 71 */     this.domainManager = domainManager;
/*    */   }
/*    */ 
/*    */   public void delete(Integer[] ids) {
/* 75 */     String idstr = StringUtil.arrayToString(ids, ",");
/* 76 */     this.daoSupport.execute("delete from eop_data_log where id in(" + idstr + ")", new Object[0]);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.DataLogManager
 * JD-Core Version:    0.6.0
 */