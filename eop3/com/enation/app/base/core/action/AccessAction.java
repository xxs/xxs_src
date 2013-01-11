/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.service.IAccessRecorder;
/*     */ import com.enation.eop.resource.model.Link;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AccessAction extends WWAction
/*     */ {
/*     */   private IAccessRecorder accessRecorder;
/*     */   private int startday;
/*     */   private int endday;
/*     */   private String runmodel;
/*     */   private Map sData;
/*     */   private String daytime;
/*     */   private String ip;
/*     */   private List accessList;
/*     */   private List<Link> linkList;
/*     */ 
/*     */   public String list()
/*     */   {
/*  25 */     Calendar cal = Calendar.getInstance();
/*  26 */     cal.setTime(new Date());
/*  27 */     int year = cal.get(1);
/*  28 */     int month = cal.get(2) + 1;
/*     */ 
/*  30 */     String starttime = null;
/*  31 */     String endtime = null;
/*     */ 
/*  33 */     if (this.startday != 0) {
/*  34 */       starttime = year + "-" + month + "-" + this.startday;
/*     */     }
/*  36 */     if (this.endday != 0) {
/*  37 */       endtime = year + "-" + month + "-" + this.endday;
/*     */     }
/*     */ 
/*  40 */     this.webpage = this.accessRecorder.list(starttime, endtime, getPage(), 50);
/*     */ 
/*  44 */     this.sData = this.accessRecorder.census();
/*  45 */     this.runmodel = EopSetting.RUNMODE;
/*  46 */     return "list";
/*     */   }
/*     */ 
/*     */   public String detaillist()
/*     */   {
/*  62 */     this.accessList = this.accessRecorder.detaillist(this.ip, this.daytime);
/*  63 */     this.runmodel = EopSetting.RUNMODE;
/*  64 */     return "detaillist";
/*     */   }
/*     */ 
/*     */   public String history()
/*     */   {
/*  71 */     this.linkList = new ArrayList();
/*  72 */     String target = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/access";
/*     */ 
/*  74 */     File file = new File(target);
/*  75 */     if (file.exists()) {
/*  76 */       String[] reportList = file.list();
/*  77 */       for (String name : reportList) {
/*  78 */         Link link = new Link();
/*  79 */         link.setLink(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/access/" + name);
/*     */ 
/*  82 */         link.setText(name);
/*  83 */         this.linkList.add(link);
/*     */       }
/*     */     }
/*  86 */     return "history";
/*     */   }
/*     */ 
/*     */   public IAccessRecorder getAccessRecorder() {
/*  90 */     return this.accessRecorder;
/*     */   }
/*     */ 
/*     */   public void setAccessRecorder(IAccessRecorder accessRecorder) {
/*  94 */     this.accessRecorder = accessRecorder;
/*     */   }
/*     */ 
/*     */   public int getStartday() {
/*  98 */     return this.startday;
/*     */   }
/*     */ 
/*     */   public void setStartday(int startday) {
/* 102 */     this.startday = startday;
/*     */   }
/*     */ 
/*     */   public int getEndday() {
/* 106 */     return this.endday;
/*     */   }
/*     */ 
/*     */   public void setEndday(int endday) {
/* 110 */     this.endday = endday;
/*     */   }
/*     */ 
/*     */   public List<Link> getLinkList() {
/* 114 */     return this.linkList;
/*     */   }
/*     */ 
/*     */   public void setLinkList(List<Link> linkList) {
/* 118 */     this.linkList = linkList;
/*     */   }
/*     */ 
/*     */   public String getDaytime() {
/* 122 */     return this.daytime;
/*     */   }
/*     */ 
/*     */   public void setDaytime(String daytime) {
/* 126 */     this.daytime = daytime;
/*     */   }
/*     */ 
/*     */   public String getIp() {
/* 130 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/* 134 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public List getAccessList() {
/* 138 */     return this.accessList;
/*     */   }
/*     */ 
/*     */   public void setAccessList(List accessList) {
/* 142 */     this.accessList = accessList;
/*     */   }
/*     */ 
/*     */   public String getRunmodel() {
/* 146 */     return this.runmodel;
/*     */   }
/*     */ 
/*     */   public void setRunmodel(String runmodel) {
/* 150 */     this.runmodel = runmodel;
/*     */   }
/*     */ 
/*     */   public Map getsData() {
/* 154 */     return this.sData;
/*     */   }
/*     */ 
/*     */   public void setsData(Map sData) {
/* 158 */     this.sData = sData;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.AccessAction
 * JD-Core Version:    0.6.0
 */