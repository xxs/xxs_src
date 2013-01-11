/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.statistics.DayAmount;
/*     */ import com.enation.app.shop.core.model.statistics.MonthAmount;
/*     */ import com.enation.app.shop.core.service.IStatisticsManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class StatisticsAction extends WWAction
/*     */ {
/*     */   private List<MonthAmount> month_AmountList;
/*     */   private List<DayAmount> day_AmountList;
/*     */   private IStatisticsManager statisticsManager;
/*     */   private String year;
/*     */   private String month;
/*     */   private int daycount;
/*     */   private List<Map> orderStatList;
/*     */ 
/*     */   public String monthamount()
/*     */   {
/*  31 */     if ((this.year == null) || (this.year.equals(""))) {
/*  32 */       Date date = new Date();
/*  33 */       SimpleDateFormat sdfyear = new SimpleDateFormat("yyyy");
/*  34 */       this.year = sdfyear.format(date);
/*  35 */       SimpleDateFormat sdfmonth = new SimpleDateFormat("MM");
/*  36 */       this.month = sdfmonth.format(date);
/*     */     }
/*  38 */     this.month_AmountList = this.statisticsManager.statisticsMonth_Amount(this.year + "-" + this.month);
/*  39 */     this.day_AmountList = this.statisticsManager.statisticsDay_Amount(this.year + "-" + this.month);
/*  40 */     this.daycount = this.day_AmountList.size();
/*  41 */     return "monthamount";
/*     */   }
/*     */ 
/*     */   public String statByPayment()
/*     */   {
/*  46 */     this.orderStatList = this.statisticsManager.orderStatByPayment();
/*  47 */     return "payment";
/*     */   }
/*     */ 
/*     */   public String statByShip() {
/*  51 */     this.orderStatList = this.statisticsManager.orderStatByShip();
/*  52 */     return "ship";
/*     */   }
/*     */ 
/*     */   public List<MonthAmount> getMonth_AmountList()
/*     */   {
/*  57 */     return this.month_AmountList;
/*     */   }
/*     */ 
/*     */   public void setMonth_AmountList(List<MonthAmount> monthAmountList)
/*     */   {
/*  63 */     this.month_AmountList = monthAmountList;
/*     */   }
/*     */ 
/*     */   public List<DayAmount> getDay_AmountList() {
/*  67 */     return this.day_AmountList;
/*     */   }
/*     */ 
/*     */   public void setDay_AmountList(List<DayAmount> dayAmountList) {
/*  71 */     this.day_AmountList = dayAmountList;
/*     */   }
/*     */ 
/*     */   public IStatisticsManager getStatisticsManager() {
/*  75 */     return this.statisticsManager;
/*     */   }
/*     */ 
/*     */   public void setStatisticsManager(IStatisticsManager statisticsManager) {
/*  79 */     this.statisticsManager = statisticsManager;
/*     */   }
/*     */ 
/*     */   public String getYear() {
/*  83 */     return this.year;
/*     */   }
/*     */ 
/*     */   public void setYear(String year) {
/*  87 */     this.year = year;
/*     */   }
/*     */ 
/*     */   public String getMonth() {
/*  91 */     return this.month;
/*     */   }
/*     */ 
/*     */   public void setMonth(String month) {
/*  95 */     this.month = month;
/*     */   }
/*     */ 
/*     */   public int getDaycount() {
/*  99 */     return this.daycount;
/*     */   }
/*     */ 
/*     */   public void setDaycount(int daycount) {
/* 103 */     this.daycount = daycount;
/*     */   }
/*     */ 
/*     */   public List<Map> getOrderStatList()
/*     */   {
/* 108 */     return this.orderStatList;
/*     */   }
/*     */ 
/*     */   public void setOrderStatList(List<Map> orderStatList)
/*     */   {
/* 113 */     this.orderStatList = orderStatList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.StatisticsAction
 * JD-Core Version:    0.6.0
 */