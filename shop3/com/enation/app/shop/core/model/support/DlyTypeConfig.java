/*     */ package com.enation.app.shop.core.model.support;
/*     */ 
/*     */ public class DlyTypeConfig
/*     */ {
/*     */   private Integer firstunit;
/*     */   private Integer continueunit;
/*     */   private Integer is_same;
/*     */   private Integer defAreaFee;
/*     */   private Double firstprice;
/*     */   private Double continueprice;
/*     */   private String expression;
/*     */   private Integer useexp;
/*     */   private Integer have_cod;
/*     */ 
/*     */   public Integer getFirstunit()
/*     */   {
/*  70 */     return this.firstunit;
/*     */   }
/*     */ 
/*     */   public void setFirstunit(Integer firstunit)
/*     */   {
/*  75 */     this.firstunit = firstunit;
/*     */   }
/*     */ 
/*     */   public Integer getContinueunit()
/*     */   {
/*  80 */     return this.continueunit;
/*     */   }
/*     */ 
/*     */   public void setContinueunit(Integer continueunit)
/*     */   {
/*  85 */     this.continueunit = continueunit;
/*     */   }
/*     */ 
/*     */   public Integer getIs_same()
/*     */   {
/*  90 */     return this.is_same;
/*     */   }
/*     */ 
/*     */   public void setIs_same(Integer isSame)
/*     */   {
/*  95 */     this.is_same = isSame;
/*     */   }
/*     */ 
/*     */   public Integer getDefAreaFee()
/*     */   {
/* 101 */     return Integer.valueOf(this.defAreaFee == null ? 0 : this.defAreaFee.intValue());
/*     */   }
/*     */ 
/*     */   public void setDefAreaFee(Integer defAreaFee)
/*     */   {
/* 106 */     this.defAreaFee = defAreaFee;
/*     */   }
/*     */ 
/*     */   public Double getFirstprice()
/*     */   {
/* 111 */     return this.firstprice;
/*     */   }
/*     */ 
/*     */   public void setFirstprice(Double firstprice)
/*     */   {
/* 116 */     this.firstprice = firstprice;
/*     */   }
/*     */ 
/*     */   public Double getContinueprice()
/*     */   {
/* 121 */     return this.continueprice;
/*     */   }
/*     */ 
/*     */   public void setContinueprice(Double continueprice)
/*     */   {
/* 126 */     this.continueprice = continueprice;
/*     */   }
/*     */ 
/*     */   public String getExpression()
/*     */   {
/* 131 */     return this.expression;
/*     */   }
/*     */ 
/*     */   public void setExpression(String expression)
/*     */   {
/* 136 */     this.expression = expression;
/*     */   }
/*     */ 
/*     */   public Integer getUseexp()
/*     */   {
/* 141 */     this.useexp = Integer.valueOf(this.useexp == null ? 0 : this.useexp.intValue());
/* 142 */     return this.useexp;
/*     */   }
/*     */ 
/*     */   public void setUseexp(Integer useexp)
/*     */   {
/* 147 */     this.useexp = useexp;
/*     */   }
/*     */ 
/*     */   public Integer getHave_cod()
/*     */   {
/* 152 */     return this.have_cod;
/*     */   }
/*     */ 
/*     */   public void setHave_cod(Integer haveCod)
/*     */   {
/* 157 */     this.have_cod = haveCod;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.DlyTypeConfig
 * JD-Core Version:    0.6.0
 */