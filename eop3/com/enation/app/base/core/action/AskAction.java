/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.Ask;
/*     */ import com.enation.app.base.core.model.Reply;
/*     */ import com.enation.app.base.core.service.IAskManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AskAction extends WWAction
/*     */ {
/*     */   private IAskManager askManager;
/*     */   private IUserManager userManager;
/*     */   private Integer askid;
/*     */   private Ask ask;
/*     */   private Integer[] id;
/*     */   private String keyword;
/*     */   private String startTime;
/*     */   private String endTime;
/*     */   private String title;
/*     */   private String content;
/*     */ 
/*     */   public String listAll()
/*     */   {
/*  45 */     this.keyword = (StringUtil.isEmpty(this.keyword) ? null : StringUtil.toUTF8(this.keyword));
/*  46 */     Date start = StringUtil.isEmpty(this.startTime) ? null : DateUtil.toDate(this.startTime, "yyyy-MM-dd");
/*  47 */     Date end = StringUtil.isEmpty(this.endTime) ? null : DateUtil.toDate(this.endTime, "yyyy-MM-dd");
/*  48 */     this.webpage = this.askManager.listAllAsk(this.keyword, start, end, getPage(), getPageSize());
/*     */ 
/*  50 */     return "alllist";
/*     */   }
/*     */ 
/*     */   public String listMy()
/*     */   {
/*  59 */     this.keyword = (StringUtil.isEmpty(this.keyword) ? null : StringUtil.toUTF8(this.keyword));
/*  60 */     Date start = StringUtil.isEmpty(this.startTime) ? null : DateUtil.toDate(this.startTime, "yyyy-MM-dd");
/*  61 */     Date end = StringUtil.isEmpty(this.endTime) ? null : DateUtil.toDate(this.endTime, "yyyy-MM-dd");
/*  62 */     this.webpage = this.askManager.listMyAsk(this.keyword, start, end, getPage(), getPageSize());
/*     */ 
/*  64 */     return "mylist";
/*     */   }
/*     */ 
/*     */   public String userview()
/*     */   {
/*  73 */     this.ask = this.askManager.get(this.askid);
/*  74 */     return "user_view";
/*     */   }
/*     */ 
/*     */   public String adminview()
/*     */   {
/*  83 */     this.ask = this.askManager.get(this.askid);
/*  84 */     return "admin_view";
/*     */   }
/*     */ 
/*     */   public String adminReply()
/*     */   {
/*  94 */     Reply reply = new Reply();
/*  95 */     reply.setAskid(this.askid);
/*  96 */     reply.setContent(this.content);
/*  97 */     reply.setUsername("客服");
/*  98 */     this.askManager.reply(reply);
/*  99 */     this.msgs.add("回答已经提交");
/* 100 */     this.urls.put("问题列表", "ask!listAll.do");
/* 101 */     return "message";
/*     */   }
/*     */ 
/*     */   public String userReply()
/*     */   {
/* 111 */     IUserService userService = UserServiceFactory.getUserService();
/* 112 */     Integer userid = userService.getCurrentUserId();
/* 113 */     EopUser user = this.userManager.get(userid);
/*     */ 
/* 115 */     Reply reply = new Reply();
/* 116 */     reply.setAskid(this.askid);
/* 117 */     reply.setContent(this.content);
/* 118 */     reply.setUsername(user.getUsername());
/* 119 */     this.askManager.reply(reply);
/* 120 */     this.msgs.add("回答已经提交");
/* 121 */     this.urls.put("问题列表", "ask!listMy.do");
/* 122 */     return "message";
/*     */   }
/*     */ 
/*     */   public String toAsk()
/*     */   {
/* 127 */     return "ask";
/*     */   }
/*     */ 
/*     */   public String ask() {
/* 131 */     Ask ask = new Ask();
/* 132 */     ask.setTitle(this.title);
/* 133 */     ask.setContent(this.content);
/* 134 */     this.askManager.add(ask);
/* 135 */     this.msgs.add("问题已经提交");
/* 136 */     this.urls.put("问题列表", "ask!listMy.do");
/* 137 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/* 142 */       this.askManager.delete(this.id);
/* 143 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 145 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 147 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 151 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id)
/*     */   {
/* 156 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public IAskManager getAskManager()
/*     */   {
/* 161 */     return this.askManager;
/*     */   }
/*     */ 
/*     */   public void setAskManager(IAskManager askManager) {
/* 165 */     this.askManager = askManager;
/*     */   }
/*     */ 
/*     */   public String getKeyword() {
/* 169 */     return this.keyword;
/*     */   }
/*     */ 
/*     */   public void setKeyword(String keyword) {
/* 173 */     this.keyword = keyword;
/*     */   }
/*     */ 
/*     */   public String getStartTime() {
/* 177 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(String startTime) {
/* 181 */     this.startTime = startTime;
/*     */   }
/*     */ 
/*     */   public String getEndTime() {
/* 185 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(String endTime) {
/* 189 */     this.endTime = endTime;
/*     */   }
/*     */ 
/*     */   public Ask getAsk()
/*     */   {
/* 194 */     return this.ask;
/*     */   }
/*     */ 
/*     */   public void setAsk(Ask ask)
/*     */   {
/* 199 */     this.ask = ask;
/*     */   }
/*     */ 
/*     */   public Integer getAskid()
/*     */   {
/* 204 */     return this.askid;
/*     */   }
/*     */ 
/*     */   public void setAskid(Integer askid)
/*     */   {
/* 209 */     this.askid = askid;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager()
/*     */   {
/* 214 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager)
/*     */   {
/* 219 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 224 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 229 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 234 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 239 */     this.title = title;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.AskAction
 * JD-Core Version:    0.6.0
 */