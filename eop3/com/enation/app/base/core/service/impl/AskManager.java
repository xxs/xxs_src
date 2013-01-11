/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Ask;
/*     */ import com.enation.app.base.core.model.Reply;
/*     */ import com.enation.app.base.core.service.IAskManager;
/*     */ import com.enation.eop.resource.ISiteManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class AskManager extends BaseSupport<Ask>
/*     */   implements IAskManager
/*     */ {
/*     */   private ISiteManager siteManager;
/*     */   private IUserManager userManager;
/*     */ 
/*     */   public void add(Ask ask)
/*     */   {
/*  35 */     HttpServletRequest request = EopContext.getHttpRequest();
/*  36 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  37 */     EopUser user = this.userManager.get(site.getUserid());
/*  38 */     ask.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
/*  39 */     ask.setUserid(site.getUserid());
/*  40 */     ask.setSiteid(site.getId());
/*  41 */     ask.setIsreply(Integer.valueOf(0));
/*  42 */     ask.setSitename(site.getSitename());
/*  43 */     ask.setDomain(request.getServerName());
/*  44 */     ask.setUsername(user.getUsername());
/*     */ 
/*  46 */     this.daoSupport.insert("eop_ask", ask);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] id) {
/*  52 */     if ((id == null) || (id.length == 0)) return;
/*     */ 
/*  54 */     String idstr = StringUtil.arrayToString(id, ",");
/*  55 */     String sql = "delete from eop_reply where askid in(" + idstr + ")";
/*  56 */     this.daoSupport.execute(sql, new Object[0]);
/*     */ 
/*  58 */     sql = "delete from eop_ask where askid in(" + idstr + ")";
/*  59 */     this.daoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void edit(Ask ask)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Page list()
/*     */   {
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   public ISiteManager getSiteManager() {
/*  73 */     return this.siteManager;
/*     */   }
/*     */ 
/*     */   public void setSiteManager(ISiteManager siteManager) {
/*  77 */     this.siteManager = siteManager;
/*     */   }
/*     */ 
/*     */   public void reply(Reply reply)
/*     */   {
/*  82 */     if (reply.getUsername().equals("客服"))
/*  83 */       this.daoSupport.execute("update eop_ask set isreply=1 where askid=?", new Object[] { reply.getAskid() });
/*     */     else {
/*  85 */       this.daoSupport.execute("update eop_ask set isreply=0 where askid=?", new Object[] { reply.getAskid() });
/*     */     }
/*  87 */     reply.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
/*  88 */     this.daoSupport.insert("eop_reply", reply);
/*     */   }
/*     */ 
/*     */   public Page listAllAsk(String keyword, Date startTime, Date endTime, int pageNo, int pageSize)
/*     */   {
/*  99 */     endTime = endTime == null ? new Date() : endTime;
/*     */ 
/* 101 */     StringBuffer sql = new StringBuffer();
/* 102 */     sql.append("select * from eop_ask where ");
/*     */ 
/* 106 */     sql.append("  dateline<=");
/* 107 */     sql.append(endTime.getTime() / 1000L);
/*     */ 
/* 111 */     if (startTime != null) {
/* 112 */       sql.append(" and dateline>=");
/* 113 */       sql.append(startTime.getTime() / 1000L);
/*     */     }
/*     */ 
/* 118 */     if (!StringUtil.isEmpty(keyword)) {
/* 119 */       sql.append(" and  ");
/* 120 */       sql.append("(");
/*     */ 
/* 123 */       sql.append("title like '%");
/* 124 */       sql.append(keyword);
/* 125 */       sql.append("%'");
/*     */ 
/* 128 */       sql.append(" or content like '%");
/* 129 */       sql.append(keyword);
/* 130 */       sql.append("%'");
/*     */ 
/* 133 */       sql.append(" or username like '%");
/* 134 */       sql.append(keyword);
/* 135 */       sql.append("%'");
/*     */ 
/* 138 */       sql.append(" or sitename like '%");
/* 139 */       sql.append(keyword);
/* 140 */       sql.append("%'");
/*     */ 
/* 143 */       sql.append(" or domain  like '%");
/* 144 */       sql.append(keyword);
/* 145 */       sql.append("%'");
/*     */ 
/* 147 */       sql.append(")");
/*     */     }
/*     */ 
/* 152 */     sql.append(" order by isreply asc,dateline desc");
/*     */ 
/* 154 */     return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page listMyAsk(String keyword, Date startTime, Date endTime, int pageNo, int pageSize)
/*     */   {
/* 163 */     StringBuffer sql = new StringBuffer();
/* 164 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 165 */     sql.append("select * from eop_ask where userid =");
/* 166 */     sql.append(site.getUserid());
/* 167 */     sql.append(" and siteid=");
/* 168 */     sql.append(site.getId());
/*     */ 
/* 171 */     if (!StringUtil.isEmpty(keyword)) {
/* 172 */       sql.append(" and  ");
/* 173 */       sql.append("(");
/*     */ 
/* 176 */       sql.append("title like '%");
/* 177 */       sql.append(keyword);
/* 178 */       sql.append("%'");
/*     */ 
/* 181 */       sql.append(" or content like '%");
/* 182 */       sql.append(keyword);
/* 183 */       sql.append("%'");
/*     */ 
/* 186 */       sql.append(")");
/*     */     }
/*     */ 
/* 189 */     if (startTime != null) {
/* 190 */       sql.append(" and dateline>=");
/* 191 */       sql.append(startTime.getTime() / 1000L);
/*     */     }
/*     */ 
/* 195 */     if (endTime != null) {
/* 196 */       sql.append(" and dateline<=");
/* 197 */       sql.append(endTime.getTime() / 1000L);
/*     */     }
/*     */ 
/* 201 */     sql.append(" order by isreply asc,dateline desc");
/*     */ 
/* 203 */     return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Ask get(Integer askid)
/*     */   {
/* 209 */     String sql = "select * from eop_ask where askid=?";
/* 210 */     Ask ask = (Ask)this.daoSupport.queryForObject(sql, Ask.class, new Object[] { askid });
/*     */ 
/* 213 */     sql = "select * from eop_reply where askid=? order by dateline asc";
/* 214 */     List replylist = this.daoSupport.queryForList(sql, Reply.class, new Object[] { askid });
/* 215 */     ask.setReplyList(replylist);
/*     */ 
/* 217 */     return ask;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager()
/*     */   {
/* 222 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager)
/*     */   {
/* 227 */     this.userManager = userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.AskManager
 * JD-Core Version:    0.6.0
 */