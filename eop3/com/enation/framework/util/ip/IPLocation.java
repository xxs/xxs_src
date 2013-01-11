/*    */ package com.enation.framework.util.ip;
/*    */ 
/*    */ public class IPLocation
/*    */ {
/*    */   private String country;
/*    */   private String area;
/*    */ 
/*    */   public IPLocation()
/*    */   {
/*  8 */     this.country = (this.area = "");
/*    */   }
/*    */ 
/*    */   public IPLocation getCopy() {
/* 12 */     IPLocation ret = new IPLocation();
/* 13 */     ret.country = this.country;
/* 14 */     ret.area = this.area;
/* 15 */     return ret;
/*    */   }
/*    */ 
/*    */   public String getCountry() {
/* 19 */     return this.country;
/*    */   }
/*    */ 
/*    */   public void setCountry(String country) {
/* 23 */     this.country = country;
/*    */   }
/*    */ 
/*    */   public String getArea() {
/* 27 */     return this.area;
/*    */   }
/*    */ 
/*    */   public void setArea(String area)
/*    */   {
/* 32 */     if (area.trim().equals("CZ88.NET"))
/* 33 */       this.area = "本机或本网络";
/*    */     else
/* 35 */       this.area = area;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ip.IPLocation
 * JD-Core Version:    0.6.0
 */