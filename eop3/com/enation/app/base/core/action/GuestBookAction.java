/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.GuestBook;
/*     */ import com.enation.app.base.core.service.IGuestBookManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class GuestBookAction extends WWAction
/*     */ {
/*     */   private IGuestBookManager guestBookManager;
/*     */   private String keyword;
/*     */   private int parentid;
/*     */   private int id;
/*     */   private String content;
/*     */   private GuestBook book;
/*     */   private Integer[] idArray;
/*     */ 
/*     */   public String list()
/*     */   {
/*  18 */     if (!StringUtil.isEmpty(this.keyword)) this.keyword = StringUtil.toUTF8(this.keyword);
/*  19 */     this.webpage = this.guestBookManager.list(this.keyword, getPage(), getPageSize());
/*  20 */     return "list";
/*     */   }
/*     */ 
/*     */   public String reply() {
/*  24 */     GuestBook guestbook = new GuestBook();
/*  25 */     guestbook.setContent(this.content);
/*  26 */     guestbook.setParentid(Integer.valueOf(this.parentid));
/*  27 */     this.guestBookManager.reply(guestbook);
/*  28 */     this.msgs.add("回复成功");
/*  29 */     this.urls.put("查看此条留言", "guestBook!detail.do?id=" + this.parentid);
/*  30 */     return "message";
/*     */   }
/*     */ 
/*     */   public String detail()
/*     */   {
/*  35 */     this.book = this.guestBookManager.get(this.id);
/*  36 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  40 */     this.guestBookManager.edit(this.id, this.content);
/*  41 */     this.json = "{result:1}";
/*  42 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  47 */       this.guestBookManager.delete(this.idArray);
/*  48 */       this.json = "{result:0,message:'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  50 */       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
/*     */     }
/*  52 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IGuestBookManager getGuestBookManager() {
/*  56 */     return this.guestBookManager;
/*     */   }
/*     */   public void setGuestBookManager(IGuestBookManager guestBookManager) {
/*  59 */     this.guestBookManager = guestBookManager;
/*     */   }
/*     */ 
/*     */   public String getKeyword() {
/*  63 */     return this.keyword;
/*     */   }
/*     */ 
/*     */   public void setKeyword(String keyword) {
/*  67 */     this.keyword = keyword;
/*     */   }
/*     */ 
/*     */   public int getParentid() {
/*  71 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(int parentid) {
/*  75 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public int getId() {
/*  79 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/*  83 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  87 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  91 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public GuestBook getBook() {
/*  95 */     return this.book;
/*     */   }
/*     */ 
/*     */   public void setBook(GuestBook book) {
/*  99 */     this.book = book;
/*     */   }
/*     */ 
/*     */   public Integer[] getIdArray() {
/* 103 */     return this.idArray;
/*     */   }
/*     */ 
/*     */   public void setIdArray(Integer[] idArray) {
/* 107 */     this.idArray = idArray;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.GuestBookAction
 * JD-Core Version:    0.6.0
 */