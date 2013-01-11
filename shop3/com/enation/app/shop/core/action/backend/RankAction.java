/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IRankManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RankAction extends WWAction
/*     */ {
/*     */   private static final int PAGESIZE = 20;
/*     */   private IRankManager rankManager;
/*     */   private String order;
/*     */   private String beginTime;
/*     */   private String endTime;
/*     */   private List list;
/*     */   private Map rankall;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  29 */     String condition = "";
/*  30 */     if ((this.beginTime != null) && (!this.beginTime.equals(""))) {
/*  31 */       condition = condition + " and orders.create_time > " + DateUtil.toDate(this.beginTime, "yyyy-MM-dd").getTime();
/*     */     }
/*  33 */     if ((this.endTime != null) && (!this.endTime.equals(""))) {
/*  34 */       condition = condition + " and orders.create_time <" + DateUtil.toDate(this.endTime, "yyyy-MM-dd").getTime();
/*     */     }
/*  36 */     this.list = this.rankManager.rank_goods(1, 20, condition, this.order);
/*  37 */     return "success";
/*     */   }
/*     */ 
/*     */   public String rankmember() {
/*  41 */     String condition = "";
/*  42 */     if ((this.beginTime != null) && (!this.beginTime.equals(""))) {
/*  43 */       condition = condition + " and orders.create_time > " + DateUtil.toDate(this.beginTime, "yyyy-MM-dd").getTime();
/*     */     }
/*  45 */     if ((this.endTime != null) && (!this.endTime.equals(""))) {
/*  46 */       condition = condition + " and orders.create_time <" + DateUtil.toDate(this.endTime, "yyyy-MM-dd").getTime();
/*     */     }
/*  48 */     this.list = this.rankManager.rank_member(1, 20, condition, this.order);
/*  49 */     return "rankmember";
/*     */   }
/*     */ 
/*     */   public String rankbuy() {
/*  53 */     this.list = this.rankManager.rank_buy(1, 20, this.order);
/*  54 */     return "rankbuy";
/*     */   }
/*     */ 
/*     */   public String rankall() {
/*  58 */     this.rankall = this.rankManager.rank_all();
/*  59 */     return "rankall";
/*     */   }
/*     */ 
/*     */   public IRankManager getRankManager() {
/*  63 */     return this.rankManager;
/*     */   }
/*     */ 
/*     */   public void setRankManager(IRankManager rankManager) {
/*  67 */     this.rankManager = rankManager;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/*  71 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/*  75 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getBeginTime() {
/*  79 */     return this.beginTime;
/*     */   }
/*     */ 
/*     */   public void setBeginTime(String beginTime) {
/*  83 */     this.beginTime = beginTime;
/*     */   }
/*     */ 
/*     */   public String getEndTime() {
/*  87 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(String endTime) {
/*  91 */     this.endTime = endTime;
/*     */   }
/*     */ 
/*     */   public List getList() {
/*  95 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List list) {
/*  99 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public Map getRankall() {
/* 103 */     return this.rankall;
/*     */   }
/*     */ 
/*     */   public void setRankall(Map rankall) {
/* 107 */     this.rankall = rankall;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.RankAction
 * JD-Core Version:    0.6.0
 */