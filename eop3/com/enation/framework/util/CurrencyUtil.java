/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigDecimal;
/*     */ 
/*     */ public final class CurrencyUtil
/*     */ {
/*     */   private static final int DEF_DIV_SCALE = 2;
/*     */ 
/*     */   public static double add(double v1, double v2)
/*     */   {
/*  26 */     BigDecimal b1 = new BigDecimal(Double.toString(v1));
/*  27 */     BigDecimal b2 = new BigDecimal(Double.toString(v2));
/*  28 */     return b1.add(b2).doubleValue();
/*     */   }
/*     */ 
/*     */   public static double sub(double v1, double v2)
/*     */   {
/*  41 */     BigDecimal b1 = new BigDecimal(Double.toString(v1));
/*  42 */     BigDecimal b2 = new BigDecimal(Double.toString(v2));
/*  43 */     return b1.subtract(b2).doubleValue();
/*     */   }
/*     */ 
/*     */   public static Double mul(double v1, double v2)
/*     */   {
/*  56 */     BigDecimal b1 = new BigDecimal(Double.toString(v1));
/*  57 */     BigDecimal b2 = new BigDecimal(Double.toString(v2));
/*  58 */     return Double.valueOf(b1.multiply(b2).doubleValue());
/*     */   }
/*     */ 
/*     */   public static double div(double v1, double v2)
/*     */   {
/*  71 */     return div(v1, v2, 2);
/*     */   }
/*     */ 
/*     */   public static double div(double v1, double v2, int scale)
/*     */   {
/*  86 */     if (scale < 0) {
/*  87 */       throw new IllegalArgumentException("The scale must be a positive integer or zero");
/*     */     }
/*     */ 
/*  90 */     BigDecimal b1 = new BigDecimal(Double.toString(v1));
/*  91 */     BigDecimal b2 = new BigDecimal(Double.toString(v2));
/*  92 */     return b1.divide(b2, scale, 4).doubleValue();
/*     */   }
/*     */ 
/*     */   public static double round(double v, int scale)
/*     */   {
/* 105 */     if (scale < 0) {
/* 106 */       throw new IllegalArgumentException("The scale must be a positive integer or zero");
/*     */     }
/*     */ 
/* 109 */     BigDecimal b = new BigDecimal(Double.toString(v));
/* 110 */     BigDecimal one = new BigDecimal("1");
/* 111 */     return b.divide(one, scale, 4).doubleValue();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 131 */     System.out.println(sub(2.0D, 10.0D));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.CurrencyUtil
 * JD-Core Version:    0.6.0
 */