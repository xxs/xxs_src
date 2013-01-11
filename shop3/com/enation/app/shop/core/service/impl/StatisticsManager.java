/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.statistics.DayAmount;
/*     */ import com.enation.app.shop.core.model.statistics.MonthAmount;
/*     */ import com.enation.app.shop.core.service.IStatisticsManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class StatisticsManager extends BaseSupport<Order>
/*     */   implements IStatisticsManager
/*     */ {
/*     */   public List<MonthAmount> statisticsMonth_Amount()
/*     */   {
/*  31 */     SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy");
/*  32 */     String year = sdfInput.format(new Date());
/*  33 */     String sql = "";
/*  34 */     if (EopSetting.DBTYPE.equals("1"))
/*  35 */       sql = "select sum(a.order_amount) as amount, Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m') as mo from " + getTableName("order") + " a where Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y') = ?  group by mo";
/*  36 */     else if (EopSetting.DBTYPE.equals("3")) {
/*  37 */       sql = "select sum(a.order_amount) as amount, substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7) as mo from " + getTableName("order") + " a where substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,4) = ?  group by substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7)";
/*     */     }
/*     */     else
/*     */     {
/*  55 */       sql = "select sum(a.order_amount) as amount, to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm') as mo from " + getTableName("order") + " a where to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy') = ?  group by to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm')";
/*     */     }
/*  57 */     List list = this.daoSupport.queryForList(sql, new Object[] { year });
/*  58 */     List target = new ArrayList();
/*  59 */     List monthList = getMonthList();
/*  60 */     for (String month : monthList) {
/*  61 */       MonthAmount ma = new MonthAmount();
/*  62 */       ma.setMonth(month);
/*  63 */       ma.setAmount(new Double(0.0D));
/*  64 */       for (Map mapdata : list) {
/*  65 */         if (mapdata.get("mo").equals(month)) {
/*  66 */           ma.setAmount(Double.valueOf(mapdata.get("amount").toString()));
/*     */         }
/*     */       }
/*  69 */       target.add(ma);
/*     */     }
/*  71 */     return target;
/*     */   }
/*     */ 
/*     */   public List<MonthAmount> statisticsMonth_Amount(String monthinput)
/*     */   {
/*  76 */     String year = monthinput.substring(0, 4);
/*  77 */     String sql = "";
/*  78 */     if ("1".equals(EopSetting.DBTYPE))
/*  79 */       sql = "select sum(a.order_amount) as amount, Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m') as mo from " + getTableName("order") + " a where Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y') = ?  group by mo";
/*  80 */     else if ("2".equals(EopSetting.DBTYPE))
/*  81 */       sql = "select sum(a.order_amount) as amount, to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm') as mo from " + getTableName("order") + " a where to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy') = ?  group by to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm')";
/*  82 */     else if ("3".equals(EopSetting.DBTYPE)) {
/*  83 */       sql = "select sum(order_amount) as amount, substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7) as mo from " + getTableName("order") + " where substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7) = ? group by substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7)";
/*     */     }
/*  85 */     List list = this.daoSupport.queryForList(sql, new Object[] { year });
/*  86 */     List target = new ArrayList();
/*  87 */     List monthList = getMonthList(monthinput);
/*  88 */     for (String month : monthList) {
/*  89 */       MonthAmount ma = new MonthAmount();
/*  90 */       ma.setMonth(month);
/*  91 */       ma.setAmount(new Double(0.0D));
/*  92 */       for (Map mapdata : list) {
/*  93 */         if (mapdata.get("mo").equals(month)) {
/*  94 */           ma.setAmount(Double.valueOf(mapdata.get("amount").toString()));
/*     */         }
/*     */       }
/*  97 */       target.add(ma);
/*     */     }
/*  99 */     return target;
/*     */   }
/*     */ 
/*     */   public List<DayAmount> statisticsDay_Amount()
/*     */   {
/* 104 */     SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM");
/* 105 */     String year = sdfInput.format(new Date());
/* 106 */     String sql = "";
/* 107 */     if (EopSetting.DBTYPE.equals("1"))
/* 108 */       sql = "select sum(a.order_amount) as amount, Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m-%d') as mo from " + getTableName("order") + " a where a.status = 3 and Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m') = ?  group by mo";
/* 109 */     else if (EopSetting.DBTYPE.equals("2"))
/* 110 */       sql = "select sum(a.order_amount) as amount, to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm-dd') as mo from " + getTableName("order") + " a where to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm') = ?  group by to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm-dd')";
/*     */     else {
/* 112 */       sql = "select sum(a.order_amount) as amount, substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,10) as mo from " + getTableName("order") + " a where a.status = 3 and substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7) = ?  group by substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,10)";
/*     */     }
/* 114 */     List list = this.daoSupport.queryForList(sql, new Object[] { year });
/* 115 */     List target = new ArrayList();
/* 116 */     List dayList = getDayList();
/* 117 */     for (String day : dayList) {
/* 118 */       DayAmount da = new DayAmount();
/* 119 */       da.setDay(day);
/* 120 */       da.setAmount(new Double(0.0D));
/* 121 */       for (Map mapdata : list) {
/* 122 */         if (mapdata.get("mo").equals(day)) {
/* 123 */           da.setAmount(Double.valueOf(mapdata.get("amount").toString()));
/*     */         }
/*     */       }
/* 126 */       target.add(da);
/*     */     }
/* 128 */     return target;
/*     */   }
/*     */ 
/*     */   public List<DayAmount> statisticsDay_Amount(String monthinput)
/*     */   {
/* 133 */     String sql = "";
/* 134 */     if ("1".equals(EopSetting.DBTYPE))
/* 135 */       sql = "select sum(a.order_amount) as amount, Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m-%d') as mo from " + getTableName("order") + " a where a.status = 3 and Date_format(FROM_UNIXTIME(a.create_time / 1000),'%Y-%m') = ?   group by mo";
/* 136 */     else if ("2".equals(EopSetting.DBTYPE))
/* 137 */       sql = "select sum(a.order_amount) as amount, to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm-dd') as mo from " + getTableName("order") + " a where a.status = 3 and to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm') = ?   group by to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(a.create_time / 1000),'yyyy-mm-dd')";
/* 138 */     else if ("3".equals(EopSetting.DBTYPE)) {
/* 139 */       sql = "select sum(order_amount) as amount, substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,10) as mo from " + getTableName("order") + " where status = 3 and substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,7) = ? group by substring(convert(varchar(10),dateadd(ss,create_time/1000 + 28800,'1970-01-01'),120),1,10)";
/*     */     }
/* 141 */     List list = this.daoSupport.queryForList(sql, new Object[] { monthinput });
/* 142 */     List target = new ArrayList();
/* 143 */     List dayList = getDayList(monthinput);
/* 144 */     for (String day : dayList) {
/* 145 */       DayAmount da = new DayAmount();
/* 146 */       da.setDay(day);
/* 147 */       da.setAmount(new Double(0.0D));
/* 148 */       for (Map mapdata : list) {
/* 149 */         if (mapdata.get("mo").equals(day)) {
/* 150 */           da.setAmount(Double.valueOf(mapdata.get("amount").toString()));
/*     */         }
/*     */       }
/* 153 */       target.add(da);
/*     */     }
/* 155 */     return target;
/*     */   }
/*     */ 
/*     */   private static List<String> getMonthList()
/*     */   {
/* 160 */     List monthList = new ArrayList();
/* 161 */     SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy");
/* 162 */     String year = sdfInput.format(new Date());
/* 163 */     DecimalFormat df = new DecimalFormat("00");
/* 164 */     for (int i = 1; i <= 12; i++) {
/* 165 */       monthList.add(year + "-" + df.format(i));
/*     */     }
/* 167 */     return monthList;
/*     */   }
/*     */ 
/*     */   private static List<String> getMonthList(String monthinput) {
/* 171 */     List monthList = new ArrayList();
/* 172 */     String year = monthinput.substring(0, 4);
/* 173 */     DecimalFormat df = new DecimalFormat("00");
/* 174 */     for (int i = 1; i <= 12; i++) {
/* 175 */       monthList.add(year + "-" + df.format(i));
/*     */     }
/* 177 */     return monthList;
/*     */   }
/*     */ 
/*     */   private static List<String> getDayList() {
/* 181 */     List dayList = new ArrayList();
/* 182 */     Date date = new Date();
/* 183 */     Calendar cal = Calendar.getInstance();
/* 184 */     cal.setTime(date);
/* 185 */     int year = cal.get(1);
/* 186 */     int month = cal.get(2) + 1;
/* 187 */     SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM");
/* 188 */     String str_month = sdfInput.format(date);
/* 189 */     DecimalFormat df = new DecimalFormat("00");
/* 190 */     int count = days(year, month);
/* 191 */     for (int i = 1; i <= count; i++) {
/* 192 */       dayList.add(str_month + "-" + df.format(i));
/*     */     }
/* 194 */     return dayList;
/*     */   }
/*     */ 
/*     */   private static List<String> getDayList(String monthinput) {
/* 198 */     List dayList = new ArrayList();
/*     */ 
/* 200 */     Date date = DateUtil.toDate(monthinput + "-01", "yyyy-MM-dd");
/*     */ 
/* 202 */     Calendar cal = Calendar.getInstance();
/* 203 */     cal.setTime(date);
/* 204 */     int year = cal.get(1);
/* 205 */     int month = cal.get(2) + 1;
/* 206 */     String str_month = monthinput;
/* 207 */     DecimalFormat df = new DecimalFormat("00");
/* 208 */     int count = days(year, month);
/* 209 */     for (int i = 1; i <= count; i++) {
/* 210 */       dayList.add(str_month + "-" + df.format(i));
/*     */     }
/* 212 */     return dayList;
/*     */   }
/*     */ 
/*     */   public static int days(int year, int month) {
/* 216 */     int days = 0;
/* 217 */     if (month != 2) {
/* 218 */       switch (month) { case 1:
/*     */       case 3:
/*     */       case 5:
/*     */       case 7:
/*     */       case 8:
/*     */       case 10:
/*     */       case 12:
/* 225 */         days = 31; break;
/*     */       case 4:
/*     */       case 6:
/*     */       case 9:
/*     */       case 11:
/* 229 */         days = 30;
/*     */       case 2:
/*     */       }
/*     */     }
/* 233 */     else if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
/* 234 */       days = 29;
/*     */     else {
/* 236 */       days = 28;
/*     */     }
/* 238 */     return days;
/*     */   }
/*     */ 
/*     */   public List<Map> orderStatByPayment()
/*     */   {
/* 247 */     String sql = "select count(0) num,sum(order_amount) amount,max(payment_name) payment_name from order where disabled=0 group by shipping_id";
/* 248 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<Map> orderStatByShip()
/*     */   {
/* 258 */     String sql = "select count(0) num,sum(order_amount) amount,max(shipping_type) shipping_type from order where disabled=0 group by shipping_id";
/* 259 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 264 */     List list = getDayList("2010-02");
/* 265 */     for (String month : list)
/* 266 */       System.out.println(month);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.StatisticsManager
 * JD-Core Version:    0.6.0
 */