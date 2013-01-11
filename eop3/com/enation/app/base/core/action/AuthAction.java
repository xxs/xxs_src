/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAuthActionManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AuthAction extends WWAction
/*     */ {
/*     */   private String name;
/*     */   private int authid;
/*     */   private int isEdit;
/*     */   private IAuthActionManager authActionManager;
/*     */   private Integer[] menuids;
/*     */   private com.enation.app.base.core.model.AuthAction auth;
/*     */ 
/*     */   public String add()
/*     */   {
/*  20 */     this.isEdit = 0;
/*  21 */     return "input";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  25 */     this.isEdit = 1;
/*  26 */     this.auth = this.authActionManager.get(this.authid);
/*  27 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save() {
/*  31 */     if (this.isEdit == 0) {
/*  32 */       return saveAdd();
/*     */     }
/*  34 */     return saveEdit();
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try
/*     */     {
/*  42 */       com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
/*  43 */       act.setName(this.name);
/*  44 */       act.setType("menu");
/*  45 */       act.setActid(Integer.valueOf(this.authid));
/*  46 */       act.setObjvalue(StringUtil.arrayToString(this.menuids, ","));
/*  47 */       this.authActionManager.edit(act);
/*  48 */       this.json = ("{result:1,authid:'" + this.authid + "'}");
/*     */     } catch (RuntimeException e) {
/*  50 */       this.logger.error(e.getMessage(), e.fillInStackTrace());
/*  51 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  53 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  58 */       com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
/*  59 */       act.setName(this.name);
/*  60 */       act.setType("menu");
/*  61 */       act.setObjvalue(StringUtil.arrayToString(this.menuids, ","));
/*  62 */       this.authid = this.authActionManager.add(act);
/*  63 */       this.json = ("{result:1,authid:'" + this.authid + "'}");
/*     */     } catch (RuntimeException e) {
/*  65 */       this.logger.error(e.getMessage(), e.fillInStackTrace());
/*  66 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  68 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  73 */       this.authActionManager.delete(this.authid);
/*  74 */       this.json = ("{result:1,authid:'" + this.authid + "'}");
/*     */     } catch (RuntimeException e) {
/*  76 */       this.logger.error(e.getMessage(), e.fillInStackTrace());
/*  77 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  79 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  84 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  88 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public int getAuthid() {
/*  92 */     return this.authid;
/*     */   }
/*     */ 
/*     */   public void setAuthid(int authid) {
/*  96 */     this.authid = authid;
/*     */   }
/*     */ 
/*     */   public int getIsEdit() {
/* 100 */     return this.isEdit;
/*     */   }
/*     */ 
/*     */   public void setIsEdit(int isEdit) {
/* 104 */     this.isEdit = isEdit;
/*     */   }
/*     */ 
/*     */   public IAuthActionManager getAuthActionManager() {
/* 108 */     return this.authActionManager;
/*     */   }
/*     */ 
/*     */   public void setAuthActionManager(IAuthActionManager authActionManager) {
/* 112 */     this.authActionManager = authActionManager;
/*     */   }
/*     */ 
/*     */   public Integer[] getMenuids() {
/* 116 */     return this.menuids;
/*     */   }
/*     */ 
/*     */   public void setMenuids(Integer[] menuids) {
/* 120 */     this.menuids = menuids;
/*     */   }
/*     */ 
/*     */   public com.enation.app.base.core.model.AuthAction getAuth() {
/* 124 */     return this.auth;
/*     */   }
/*     */ 
/*     */   public void setAuth(com.enation.app.base.core.model.AuthAction auth) {
/* 128 */     this.auth = auth;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.AuthAction
 * JD-Core Version:    0.6.0
 */