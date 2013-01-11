/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.service.IAccessRecorder;
/*     */ import com.enation.eop.resource.model.Access;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.ThemeUri;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.ip.IPSeeker;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class AccessRecorder extends BaseSupport
/*     */   implements IAccessRecorder
/*     */ {
/*     */   public int record(ThemeUri themeUri)
/*     */   {
/*  37 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  39 */     Access access = new Access();
/*  40 */     access.setAccess_time((int)(System.currentTimeMillis() / 1000L));
/*  41 */     access.setIp(request.getRemoteAddr());
/*     */ 
/*  43 */     access.setPage(themeUri.getPagename());
/*  44 */     access.setUrl(RequestUtil.getRequestUrl(request));
/*  45 */     access.setPoint(themeUri.getPoint());
/*  46 */     access.setArea(new IPSeeker().getCountry(access.getIp()));
/*     */ 
/*  48 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  49 */     if (member != null) {
/*  50 */       access.setMembername(member.getUname());
/*     */     }
/*  52 */     Access last_access = (Access)ThreadContextHolder.getSessionContext().getAttribute("user_access");
/*     */ 
/*  54 */     if (last_access != null) {
/*  55 */       int stay_time = access.getAccess_time() - last_access.getAccess_time();
/*     */ 
/*  57 */       last_access.setStay_time(stay_time);
/*  58 */       int last = (int)(System.currentTimeMillis() / 1000L - 3600L);
/*  59 */       String sql = "select count(0)  from access where ip=? and url=? and access_time>=?";
/*     */ 
/*  61 */       int count = this.baseDaoSupport.queryForInt(sql, new Object[] { last_access.getIp(), last_access.getUrl(), Integer.valueOf(last) });
/*     */ 
/*  63 */       if (count == 0) {
/*  64 */         EopSite site = EopContext.getContext().getCurrentSite();
/*  65 */         int point = site.getPoint();
/*  66 */         if ((point == -1) || (site.getIsimported() == 1)) {
/*  67 */           this.baseDaoSupport.insert("access", last_access);
/*  68 */           return 1;
/*     */         }
/*  70 */         if (point > access.getPoint())
/*     */         {
/*  72 */           this.daoSupport.execute("update eop_site set point=point-? where id=?", new Object[] { Integer.valueOf(last_access.getPoint()), site.getId() });
/*     */ 
/*  75 */           this.baseDaoSupport.insert("access", last_access);
/*  76 */           site.setPoint(site.getPoint() - last_access.getPoint());
/*     */         } else {
/*  78 */           return 0;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  84 */     ThreadContextHolder.getSessionContext().setAttribute("user_access", access);
/*     */ 
/*  86 */     return 1;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  99 */     int todaystart = (int)(DateUtil.toDate(DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"), "yyyy-MM-dd mm:ss").getTime() / 1000L);
/*     */ 
/* 102 */     System.out.println((int)(DateUtil.toDate("2010-11-01 00:00", "yyyy-MM-dd mm:ss").getTime() / 1000L));
/*     */ 
/* 104 */     System.out.println(todaystart);
/* 105 */     System.out.println(System.currentTimeMillis() / 1000L);
/*     */   }
/*     */ 
/*     */   public Page list(String starttime, String endtime, int pageNo, int pageSize)
/*     */   {
/* 111 */     int now = (int)(System.currentTimeMillis() / 1000L);
/*     */ 
/* 114 */     int stime = now - 2592000;
/*     */ 
/* 117 */     if (starttime != null) {
/* 118 */       stime = (int)(DateUtil.toDate(starttime, "yyyy-MM-dd").getTime() / 1000L);
/*     */     }
/*     */ 
/* 122 */     if (endtime != null) {
/* 123 */       now = (int)(DateUtil.toDate(endtime, "yyyy-MM-dd").getTime() / 1000L);
/*     */     }
/*     */ 
/* 126 */     String sql = "select ip,max(access_time) access_time,max(membername) mname,floor(access_time/86400) daytime,count(0) count,sum(stay_time) sum_stay_time,max(access_time) maxtime,min(access_time) mintime,sum(point) point from access where access_time>=? and access_time<=? group by ip,floor(access_time/86400) order by access_time desc";
/* 127 */     sql = this.baseDaoSupport.buildPageSql(sql, pageNo, pageSize);
/* 128 */     List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(stime), Integer.valueOf(now) });
/* 129 */     sql = "select count(0) from (select access_time from access where access_time>=? and access_time<=? group by ip, floor(access_time/86400)) tb";
/*     */ 
/* 131 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(stime), Integer.valueOf(now) });
/* 132 */     Page page = new Page(0L, count, pageSize, list);
/*     */ 
/* 134 */     return page;
/*     */   }
/*     */ 
/*     */   public List detaillist(String ip, String daytime)
/*     */   {
/* 145 */     String sql = "select * from access where ip=? and floor(access_time/86400)=? order by access_time asc ";
/* 146 */     return this.baseDaoSupport.queryForList(sql, new Object[] { ip, daytime });
/*     */   }
/*     */ 
/*     */   public void export()
/*     */   {
/* 152 */     String sql = "select * from eop_site ";
/* 153 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/*     */ 
/* 156 */     for (Map map : list) {
/* 157 */       AccessExporter accessExporter = (AccessExporter)SpringContextHolder.getBean("accessExporter");
/*     */ 
/* 159 */       accessExporter.setContext(Integer.valueOf(map.get("userid").toString()), Integer.valueOf(map.get("id").toString()));
/*     */ 
/* 162 */       Thread thread = new Thread(accessExporter);
/* 163 */       thread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Long> census()
/*     */   {
/* 172 */     int todaystart = (int)(DateUtil.toDate(DateUtil.toString(new Date(), "yyyy-MM-dd 00:00"), "yyyy-MM-dd mm:ss").getTime() / 1000L);
/*     */ 
/* 176 */     int todayend = (int)(System.currentTimeMillis() / 1000L);
/*     */ 
/* 179 */     String sql = "select count(0) from access where access_time>=?  and access_time<=?";
/* 180 */     long todayaccess = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(todaystart), Integer.valueOf(todayend) });
/*     */ 
/* 184 */     sql = "select sum(point) from access where access_time>=?  and access_time<=?";
/* 185 */     long todaypoint = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(todaystart), Integer.valueOf(todayend) });
/*     */ 
/* 189 */     String[] currentMonth = DateUtil.getCurrentMonth();
/* 190 */     int monthstart = (int)(DateUtil.toDate(currentMonth[0], "yyyy-MM-dd").getTime() / 1000L);
/*     */ 
/* 192 */     int monthend = (int)(DateUtil.toDate(currentMonth[1], "yyyy-MM-dd").getTime() / 1000L);
/*     */ 
/* 196 */     sql = "select count(0) from access where access_time>=? and access_time<=?";
/* 197 */     long monthaccess = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(monthstart), Integer.valueOf(monthend) });
/*     */ 
/* 201 */     sql = "select sum(point) from access where access_time>=? and access_time<=?";
/* 202 */     long monthpoint = this.baseDaoSupport.queryForLong(sql, new Object[] { Integer.valueOf(monthstart), Integer.valueOf(monthend) });
/*     */ 
/* 208 */     sql = "select sumpoint,sumaccess from eop_site where id=?";
/* 209 */     List list = this.daoSupport.queryForList(sql, new Object[] { EopContext.getContext().getCurrentSite().getId() });
/*     */ 
/* 211 */     if ((list.isEmpty()) || (list == null) || (list.size() == 0)) {
/* 212 */       throw new RuntimeException("站点[" + EopContext.getContext().getCurrentSite().getId() + "]不存在");
/*     */     }
/*     */ 
/* 215 */     Map siteData = (Map)list.get(0);
/* 216 */     long sumaccess = Long.valueOf("" + siteData.get("sumaccess")).longValue();
/* 217 */     long sumpoint = Long.valueOf("" + siteData.get("sumpoint")).longValue();
/*     */ 
/* 220 */     sumaccess += monthaccess;
/* 221 */     sumpoint += monthpoint;
/*     */ 
/* 224 */     Map sData = new HashMap();
/* 225 */     sData.put("todayaccess", Long.valueOf(todayaccess));
/* 226 */     sData.put("todaypoint", Long.valueOf(todaypoint));
/* 227 */     sData.put("monthaccess", Long.valueOf(monthaccess));
/* 228 */     sData.put("monthpoint", Long.valueOf(monthpoint));
/* 229 */     sData.put("sumaccess", Long.valueOf(sumaccess));
/* 230 */     sData.put("sumpoint", Long.valueOf(sumpoint));
/* 231 */     return sData;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.AccessRecorder
 * JD-Core Version:    0.6.0
 */