/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.Message;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMessageManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MemberMessageWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMessageManager messageManager;
/*     */   private IMemberManager memberManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  46 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  47 */     this.action = (this.action == null ? "" : this.action);
/*  48 */     if (this.action.equals("inbox")) {
/*  49 */       setPageName("message_inbox");
/*  50 */       String page = request.getParameter("page");
/*  51 */       page = (page == null) || (page.equals("")) ? "1" : page;
/*  52 */       int pageSize = 10;
/*  53 */       Page inboxPage = this.messageManager.pageMessage(Integer.valueOf(page).intValue(), pageSize, "inbox");
/*  54 */       Long totalCount = Long.valueOf(inboxPage.getTotalCount());
/*  55 */       Long pageCount = Long.valueOf(inboxPage.getTotalPageCount());
/*     */ 
/*  57 */       List inboxList = (List)inboxPage.getResult();
/*  58 */       inboxList = inboxList == null ? new ArrayList() : inboxList;
/*  59 */       putData("totalCount", totalCount);
/*  60 */       putData("pageSize", Integer.valueOf(pageSize));
/*  61 */       putData("page", page);
/*  62 */       putData("inboxList", inboxList);
/*  63 */       putData("pageCount", pageCount);
/*  64 */     } else if (this.action.equals("outbox")) {
/*  65 */       setPageName("message_outbox");
/*  66 */       String page = request.getParameter("page");
/*  67 */       page = (page == null) || (page.equals("")) ? "1" : page;
/*  68 */       int pageSize = 10;
/*  69 */       Page outboxPage = this.messageManager.pageMessage(Integer.valueOf(page).intValue(), pageSize, "outbox");
/*  70 */       Long totalCount = Long.valueOf(outboxPage.getTotalCount());
/*  71 */       Long pageCount = Long.valueOf(outboxPage.getTotalPageCount());
/*     */ 
/*  73 */       List outboxList = (List)outboxPage.getResult();
/*  74 */       outboxList = outboxList == null ? new ArrayList() : outboxList;
/*  75 */       putData("totalCount", totalCount);
/*  76 */       putData("pageSize", Integer.valueOf(pageSize));
/*  77 */       putData("page", page);
/*  78 */       putData("outboxList", outboxList);
/*  79 */       putData("pageCount", pageCount);
/*  80 */     } else if (this.action.equals("send")) {
/*  81 */       setPageName("message_send");
/*  82 */     } else if (this.action.equals("sendSave")) {
/*  83 */       IUserService userService = UserServiceFactory.getUserService();
/*  84 */       Member member = userService.getCurrentMember();
/*  85 */       Message message = new Message();
/*  86 */       message.setFrom_id(member.getMember_id());
/*  87 */       message.setMsg_from(member.getUname());
/*     */ 
/*  89 */       String subject = request.getParameter("subject");
/*  90 */       message.setSubject(subject);
/*     */ 
/*  92 */       String msg = request.getParameter("message");
/*  93 */       message.setMessage(msg);
/*     */ 
/*  95 */       message.setDate_line(Long.valueOf(new Date().getTime()));
/*     */ 
/*  97 */       String msg_to = request.getParameter("msg_to");
/*  98 */       msg_to = msg_to.replace(" ", ",");
/*  99 */       String[] msg_to_array = StringUtils.split(msg_to, ",");
/* 100 */       String returnmessage = "";
/*     */       try
/*     */       {
/* 103 */         for (String uname : msg_to_array) {
/* 104 */           Member m = this.memberManager.getMemberByUname(uname);
/* 105 */           if (m != null) {
/* 106 */             message.setTo_id(m.getMember_id());
/* 107 */             message.setMsg_to(m.getUname());
/* 108 */             this.messageManager.addMessage(message);
/* 109 */             returnmessage = returnmessage + "[" + uname + "] ";
/*     */           }
/*     */         }
/* 112 */         showSuccess("消息已发送给" + returnmessage, "发送消息", "member_message.html?action=send");
/*     */       } catch (Exception e) {
/* 114 */         if (this.logger.isDebugEnabled()) {
/* 115 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 117 */         showError("发送失败", "发送消息", "member_message.html?action=send");
/*     */       }
/* 119 */     } else if (this.action.equals("indel")) {
/* 120 */       String[] ids = request.getParameterValues("delete");
/*     */       try {
/* 122 */         this.messageManager.delinbox(StringUtil.arrayToString(ids, ","));
/* 123 */         showSuccess("删除成功", "收件箱", "member_message.html?action=inbox");
/*     */       } catch (Exception e) {
/* 125 */         if (this.logger.isDebugEnabled()) {
/* 126 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 128 */         showError("删除失败", "收件箱", "member_message.html?action=inbox");
/*     */       }
/* 130 */     } else if (this.action.equals("outdel")) {
/* 131 */       String[] ids = request.getParameterValues("delete");
/*     */       try {
/* 133 */         this.messageManager.deloutbox(StringUtil.arrayToString(ids, ","));
/* 134 */         showSuccess("删除成功", "发件箱", "member_message.html?action=outbox");
/*     */       } catch (Exception e) {
/* 136 */         if (this.logger.isDebugEnabled()) {
/* 137 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 139 */         showError("删除失败", "发件箱", "member_message.html?action=outbox");
/*     */       }
/* 141 */     } else if (this.action.equals("reply")) {
/* 142 */       setPageName("message_reply");
/* 143 */       String msg_to = request.getParameter("msg_to");
/* 144 */       String subject = request.getParameter("subject");
/* 145 */       putData("msg_to", StringUtil.to(msg_to, "utf-8"));
/* 146 */       putData("subject", StringUtil.to(subject, "utf-8"));
/* 147 */     } else if (this.action.equals("read")) {
/* 148 */       putData("json", "{message:'ok'}");
/* 149 */       setPageName("json");
/* 150 */       Message m = new Message();
/* 151 */       String msg_id = request.getParameter("msg_id");
/* 152 */       this.messageManager.setMessage_read(Integer.valueOf(msg_id).intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public IMessageManager getMessageManager() {
/* 157 */     return this.messageManager;
/*     */   }
/*     */ 
/*     */   public void setMessageManager(IMessageManager messageManager) {
/* 161 */     this.messageManager = messageManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 165 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 169 */     this.memberManager = memberManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberMessageWidget
 * JD-Core Version:    0.6.0
 */