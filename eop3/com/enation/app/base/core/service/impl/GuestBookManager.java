/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.DataLog;
/*     */ import com.enation.app.base.core.model.GuestBook;
/*     */ import com.enation.app.base.core.service.IGuestBookManager;
/*     */ import com.enation.eop.resource.IDataLogManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class GuestBookManager extends BaseSupport<GuestBook>
/*     */   implements IGuestBookManager
/*     */ {
/*     */   private IDataLogManager dataLogManager;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(GuestBook guestbook)
/*     */   {
/*  29 */     if (guestbook == null)
/*  30 */       throw new IllegalArgumentException("param guestbook is NULL");
/*  31 */     guestbook.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
/*  32 */     guestbook.setIssubject(Integer.valueOf(1));
/*  33 */     guestbook.setParentid(Integer.valueOf(0));
/*  34 */     this.baseDaoSupport.insert("guestbook", guestbook);
/*  35 */     DataLog datalog = new DataLog();
/*  36 */     datalog.setContent("标题:" + guestbook.getTitle() + "<br>内容：" + guestbook.getContent());
/*     */ 
/*  38 */     datalog.setLogtype("留言");
/*  39 */     datalog.setOptype("添加");
/*  40 */     this.dataLogManager.add(datalog);
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] idArray) {
/*  45 */     if ((idArray == null) || (idArray.length == 0))
/*  46 */       return;
/*  47 */     String ids = StringUtil.arrayToString(idArray, ",");
/*  48 */     this.baseDaoSupport.execute("delete from guestbook where id in(" + ids + ")", new Object[0]);
/*     */ 
/*  50 */     this.baseDaoSupport.execute("delete from guestbook where parentid in(" + ids + ")", new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page list(String keyword, int pageNo, int pageSize)
/*     */   {
/*  57 */     String sql = "select * from guestbook where issubject=1";
/*  58 */     StringBuffer term = new StringBuffer();
/*     */ 
/*  61 */     if (!StringUtil.isEmpty(keyword)) {
/*  62 */       term.append(" and  (");
/*  63 */       term.append(" title like'%" + keyword + "%'");
/*  64 */       term.append(" or content like'%" + keyword + "%'");
/*  65 */       term.append(" or username like'%" + keyword + "%'");
/*  66 */       term.append(")");
/*     */     }
/*     */ 
/*  69 */     sql = sql + term + " order by dateline desc";
/*  70 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, GuestBook.class, new Object[0]);
/*     */ 
/*  72 */     List subjectList = (List)page.getResult();
/*     */ 
/*  75 */     sql = "select * from guestbook where issubject=0 and parentid in (select id from " + getTableName("guestbook") + " where issubject =1 " + term + ") order by dateline asc ";
/*     */ 
/*  77 */     List replyList = this.baseDaoSupport.queryForList(sql, GuestBook.class, new Object[0]);
/*     */ 
/*  81 */     for (GuestBook reply : replyList) {
/*  82 */       addtoSubject(subjectList, reply);
/*     */     }
/*     */ 
/*  85 */     return page;
/*     */   }
/*     */ 
/*     */   private void addtoSubject(List<GuestBook> subjectList, GuestBook reply)
/*     */   {
/*  95 */     for (GuestBook subject : subjectList) {
/*  96 */       int id = subject.getId().intValue();
/*  97 */       int pid = reply.getParentid().intValue();
/*  98 */       if (id == pid) {
/*  99 */         subject.addReply(reply);
/* 100 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reply(GuestBook guestbook) {
/* 106 */     if (guestbook == null)
/* 107 */       throw new IllegalArgumentException("param guestbook is NULL");
/* 108 */     guestbook.setDateline(Long.valueOf(DateUtil.getDatelineLong()));
/* 109 */     guestbook.setIssubject(Integer.valueOf(0));
/* 110 */     this.baseDaoSupport.insert("guestbook", guestbook);
/*     */   }
/*     */ 
/*     */   public void edit(int id, String content)
/*     */   {
/* 115 */     this.baseDaoSupport.execute("update guestbook set content=? where id=?", new Object[] { content, Integer.valueOf(id) });
/*     */   }
/*     */ 
/*     */   public GuestBook get(int id)
/*     */   {
/* 120 */     GuestBook guestbook = (GuestBook)this.baseDaoSupport.queryForObject("select * from guestbook where id=?", GuestBook.class, new Object[] { Integer.valueOf(id) });
/*     */ 
/* 122 */     List replyList = this.baseDaoSupport.queryForList("select * from guestbook where parentid=? order by dateline asc", GuestBook.class, new Object[] { Integer.valueOf(id) });
/*     */ 
/* 126 */     guestbook.setReplyList(replyList);
/* 127 */     return guestbook;
/*     */   }
/*     */ 
/*     */   public IDataLogManager getDataLogManager() {
/* 131 */     return this.dataLogManager;
/*     */   }
/*     */ 
/*     */   public void setDataLogManager(IDataLogManager dataLogManager) {
/* 135 */     this.dataLogManager = dataLogManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.GuestBookManager
 * JD-Core Version:    0.6.0
 */