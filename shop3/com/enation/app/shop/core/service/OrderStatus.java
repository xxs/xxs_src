/*     */ package com.enation.app.shop.core.service;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class OrderStatus
/*     */ {
/*     */   public static final int ORDER_CHANGED = -7;
/*     */   public static final int ORDER_CHANGE_REFUSE = -6;
/*     */   public static final int ORDER_RETURN_REFUSE = -5;
/*     */   public static final int ORDER_CHANGE_APPLY = -4;
/*     */   public static final int ORDER_RETURN_APPLY = -3;
/*     */   public static final int ORDER_CANCEL_SHIP = -2;
/*     */   public static final int ORDER_CANCEL_PAY = -1;
/*     */   public static final int ORDER_NOT_PAY = 0;
/*     */   public static final int ORDER_PAY = 1;
/*     */   public static final int ORDER_PAY_CONFIRM = 2;
/*     */   public static final int ORDER_ALLOCATION = 3;
/*     */   public static final int ORDER_ALLOCATION_YES = 4;
/*     */   public static final int ORDER_SHIP = 5;
/*     */   public static final int ORDER_ROG = 6;
/*     */   public static final int ORDER_COMPLETE = 7;
/*     */   public static final int ORDER_CANCELLATION = 8;
/*     */   public static final int ORDER_CONFIRM = 9;
/*     */   public static final int PAY_NO = 0;
/*     */   public static final int PAY_YES = 1;
/*     */   public static final int PAY_CONFIRM = 2;
/*     */   public static final int PAY_CANCEL = 3;
/*     */   public static final int PAY_PARTIAL_REFUND = 4;
/*     */   public static final int PAY_PARTIAL_PAYED = 5;
/*     */   public static final int SHIP_ALLOCATION_NO = 0;
/*     */   public static final int SHIP_ALLOCATION_YES = 1;
/*     */   public static final int SHIP_NO = 2;
/*     */   public static final int SHIP_YES = 3;
/*     */   public static final int SHIP_CANCEL = 4;
/*     */   public static final int SHIP_PARTIAL_SHIPED = 5;
/*     */   public static final int SHIP_PARTIAL_CANCEL = 6;
/*     */   public static final int SHIP_PARTIAL_CHANGE = 7;
/*     */   public static final int SHIP_CHANED = 8;
/*     */ 
/*     */   public static Map<String, Object> getOrderStatusMap()
/*     */   {
/*  75 */     Map map = new HashMap(17);
/*     */ 
/*  78 */     map.put("ORDER_CHANGED", Integer.valueOf(-7));
/*  79 */     map.put("ORDER_CHANGE_REFUSE", Integer.valueOf(-6));
/*  80 */     map.put("ORDER_RETURN_REFUSE", Integer.valueOf(-5));
/*  81 */     map.put("ORDER_CHANGE_APPLY", Integer.valueOf(-4));
/*  82 */     map.put("ORDER_RETURN_APPLY", Integer.valueOf(-3));
/*  83 */     map.put("ORDER_CANCEL_SHIP", Integer.valueOf(-2));
/*  84 */     map.put("ORDER_CANCEL_PAY", Integer.valueOf(-1));
/*  85 */     map.put("ORDER_NOT_PAY", Integer.valueOf(0));
/*  86 */     map.put("ORDER_PAY", Integer.valueOf(1));
/*  87 */     map.put("ORDER_PAY_CONFIRM", Integer.valueOf(2));
/*  88 */     map.put("ORDER_ALLOCATION", Integer.valueOf(3));
/*  89 */     map.put("ORDER_ALLOCATION_YES", Integer.valueOf(4));
/*  90 */     map.put("ORDER_SHIP", Integer.valueOf(5));
/*  91 */     map.put("ORDER_ROG", Integer.valueOf(6));
/*  92 */     map.put("ORDER_COMPLETE", Integer.valueOf(7));
/*  93 */     map.put("ORDER_CANCELLATION", Integer.valueOf(8));
/*  94 */     map.put("ORDER_CONFIRM", Integer.valueOf(9));
/*     */ 
/*  98 */     map.put("PAY_NO", Integer.valueOf(0));
/*  99 */     map.put("PAY_YES", Integer.valueOf(1));
/* 100 */     map.put("PAY_CONFIRM", Integer.valueOf(2));
/* 101 */     map.put("PAY_CANCEL", Integer.valueOf(3));
/* 102 */     map.put("PAY_PARTIAL_REFUND", Integer.valueOf(4));
/* 103 */     map.put("PAY_PARTIAL_PAYED", Integer.valueOf(5));
/*     */ 
/* 107 */     map.put("SHIP_ALLOCATION_NO", Integer.valueOf(0));
/* 108 */     map.put("SHIP_ALLOCATION_YES", Integer.valueOf(1));
/* 109 */     map.put("SHIP_NO", Integer.valueOf(2));
/* 110 */     map.put("SHIP_YES", Integer.valueOf(3));
/* 111 */     map.put("SHIP_CANCEL", Integer.valueOf(4));
/* 112 */     map.put("SHIP_PARTIAL_SHIPED", Integer.valueOf(5));
/* 113 */     map.put("SHIP_PARTIAL_CANCEL", Integer.valueOf(3));
/* 114 */     map.put("SHIP_PARTIAL_CHANGE", Integer.valueOf(4));
/* 115 */     map.put("SHIP_CHANED", Integer.valueOf(8));
/*     */ 
/* 119 */     return map;
/*     */   }
/*     */ 
/*     */   public static String getOrderStatusText(int status)
/*     */   {
/* 128 */     String text = "";
/*     */ 
/* 130 */     switch (status) {
/*     */     case -7:
/* 132 */       text = "已换货";
/* 133 */       break;
/*     */     case -6:
/* 135 */       text = "换货被拒绝";
/* 136 */       break;
/*     */     case -5:
/* 139 */       text = "退货被拒绝";
/* 140 */       break;
/*     */     case -4:
/* 143 */       text = "申请换货";
/* 144 */       break;
/*     */     case -3:
/* 147 */       text = "申请退货";
/* 148 */       break;
/*     */     case -2:
/* 150 */       text = "退货";
/* 151 */       break;
/*     */     case -1:
/* 153 */       text = "退款";
/* 154 */       break;
/*     */     case 0:
/* 157 */       text = "等待付款";
/* 158 */       break;
/*     */     case 9:
/* 161 */       text = "已确认";
/* 162 */       break;
/*     */     case 1:
/* 165 */       text = "已付款待确认";
/* 166 */       break;
/*     */     case 2:
/* 169 */       text = "已付款";
/* 170 */       break;
/*     */     case 3:
/* 173 */       text = "配货中";
/* 174 */       break;
/*     */     case 4:
/* 177 */       text = "配货完成待发货";
/* 178 */       break;
/*     */     case 5:
/* 181 */       text = "已发货";
/* 182 */       break;
/*     */     case 7:
/* 185 */       text = "已完成";
/* 186 */       break;
/*     */     case 6:
/* 189 */       text = "已收货";
/* 190 */       break;
/*     */     case 8:
/* 193 */       text = "已取消";
/* 194 */       break;
/*     */     default:
/* 197 */       text = "错误状态";
/*     */     }
/*     */ 
/* 201 */     return text;
/*     */   }
/*     */ 
/*     */   public static String getPayStatusText(int status)
/*     */   {
/* 212 */     String text = "";
/*     */ 
/* 214 */     switch (status) {
/*     */     case 0:
/* 216 */       text = "未付款";
/* 217 */       break;
/*     */     case 1:
/* 219 */       text = "已付款待确认";
/* 220 */       break;
/*     */     case 2:
/* 222 */       text = "已确认付款";
/* 223 */       break;
/*     */     case 3:
/* 226 */       text = "已经退款";
/* 227 */       break;
/*     */     case 4:
/* 229 */       text = "部分退款";
/* 230 */       break;
/*     */     case 5:
/* 232 */       text = "部分付款";
/* 233 */       break;
/*     */     default:
/* 235 */       text = "错误状态";
/*     */     }
/*     */ 
/* 238 */     return text;
/*     */   }
/*     */ 
/*     */   public static String getShipStatusText(int status)
/*     */   {
/* 250 */     String text = "";
/*     */ 
/* 252 */     switch (status) {
/*     */     case 0:
/* 254 */       text = "未配货";
/* 255 */       break;
/*     */     case 1:
/* 257 */       text = "配货中 ";
/* 258 */       break;
/*     */     case 2:
/* 261 */       text = "未发货";
/* 262 */       break;
/*     */     case 3:
/* 265 */       text = "已发货";
/* 266 */       break;
/*     */     case 4:
/* 269 */       text = "已退货";
/* 270 */       break;
/*     */     case 5:
/* 272 */       text = "部分发货";
/* 273 */       break;
/*     */     case 6:
/* 275 */       text = "部分退货";
/* 276 */       break;
/*     */     case 7:
/* 279 */       text = "部分换货";
/* 280 */       break;
/*     */     case 8:
/* 283 */       text = " 已换货";
/* 284 */       break;
/*     */     default:
/* 288 */       text = "错误状态";
/*     */     }
/*     */ 
/* 292 */     return text;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.OrderStatus
 * JD-Core Version:    0.6.0
 */