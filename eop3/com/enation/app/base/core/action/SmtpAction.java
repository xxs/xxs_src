/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.Smtp;
/*     */ import com.enation.app.base.core.service.ISmtpManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SmtpAction extends WWAction
/*     */ {
/*     */   private ISmtpManager smtpManager;
/*     */   private Smtp smtp;
/*     */   private Integer[] idAr;
/*     */   private int id;
/*     */   private int isedit;
/*     */   private List<Smtp> smtpList;
/*     */ 
/*     */   public String add()
/*     */   {
/*  26 */     this.isedit = 0;
/*  27 */     return "input";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  31 */     this.isedit = 1;
/*  32 */     this.smtp = this.smtpManager.get(this.id);
/*  33 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*     */     try
/*     */     {
/*  40 */       this.smtpManager.add(this.smtp);
/*  41 */       this.msgs.add("smtp添加成功");
/*  42 */       this.urls.put("smtp列表", "smtp!list.do");
/*     */     } catch (RuntimeException e) {
/*  44 */       this.logger.error("smtp修改失败", e);
/*  45 */       this.msgs.add("smtp添加失败" + e.getMessage());
/*     */     }
/*     */ 
/*  48 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try {
/*  54 */       this.smtpManager.edit(this.smtp);
/*  55 */       this.msgs.add("smtp修改成功");
/*  56 */       this.urls.put("smtp列表", "smtp!list.do");
/*     */     } catch (RuntimeException e) {
/*  58 */       this.logger.error("smtp修改失败", e);
/*  59 */       this.msgs.add("smtp修改失败" + e.getMessage());
/*     */     }
/*     */ 
/*  62 */     return "message";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  67 */     this.smtpList = this.smtpManager.list();
/*  68 */     return "list";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  73 */       this.smtpManager.delete(this.idAr);
/*  74 */       this.json = "{\"result\":0,\"message\":\"smtp删除成功\"}";
/*     */     } catch (RuntimeException e) {
/*  76 */       this.logger.error("smtp删除失败", e);
/*  77 */       showSuccessJson("smtp删除失败[" + e.getMessage() + "]");
/*     */     }
/*  79 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public ISmtpManager getSmtpManager() {
/*  83 */     return this.smtpManager;
/*     */   }
/*     */ 
/*     */   public void setSmtpManager(ISmtpManager smtpManager) {
/*  87 */     this.smtpManager = smtpManager;
/*     */   }
/*     */ 
/*     */   public Smtp getSmtp() {
/*  91 */     return this.smtp;
/*     */   }
/*     */ 
/*     */   public void setSmtp(Smtp smtp) {
/*  95 */     this.smtp = smtp;
/*     */   }
/*     */ 
/*     */   public Integer[] getIdAr() {
/*  99 */     return this.idAr;
/*     */   }
/*     */ 
/*     */   public void setIdAr(Integer[] idAr) {
/* 103 */     this.idAr = idAr;
/*     */   }
/*     */ 
/*     */   public int getId() {
/* 107 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/* 111 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public int getIsedit() {
/* 115 */     return this.isedit;
/*     */   }
/*     */ 
/*     */   public void setIsedit(int isedit) {
/* 119 */     this.isedit = isedit;
/*     */   }
/*     */ 
/*     */   public List<Smtp> getSmtpList() {
/* 123 */     return this.smtpList;
/*     */   }
/*     */ 
/*     */   public void setSmtpList(List<Smtp> smtpList) {
/* 127 */     this.smtpList = smtpList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.SmtpAction
 * JD-Core Version:    0.6.0
 */