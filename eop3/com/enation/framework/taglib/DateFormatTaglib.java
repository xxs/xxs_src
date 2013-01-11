/*    */ package com.enation.framework.taglib;
/*    */ 
/*    */ import com.enation.framework.util.DateUtil;
/*    */ import java.util.Date;
/*    */ import javax.servlet.jsp.JspException;
/*    */ 
/*    */ public class DateFormatTaglib extends EnationTagSupport
/*    */ {
/*    */   private Long time;
/*    */   private String times;
/*    */   private String pattern;
/*    */ 
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 21 */     this.time = (this.times == null ? this.time : Long.valueOf(this.times));
/* 22 */     if (this.time.longValue() > 0L) {
/* 23 */       if (this.time.toString().length() == 10) {
/* 24 */         this.time = Long.valueOf(this.time.longValue() * 1000L);
/*    */       }
/* 26 */       Date date = new Date(this.time.longValue());
/* 27 */       String str = DateUtil.toString(date, this.pattern);
/* 28 */       print(str);
/*    */     } else {
/* 30 */       print("");
/*    */     }
/* 32 */     return 1;
/*    */   }
/*    */ 
/*    */   public int doStartTag() throws JspException
/*    */   {
/* 37 */     return 6;
/*    */   }
/*    */ 
/*    */   public Long getTime() {
/* 41 */     return this.time;
/*    */   }
/*    */ 
/*    */   public void setTime(Long time) {
/* 45 */     this.time = time;
/*    */   }
/*    */ 
/*    */   public String getPattern() {
/* 49 */     return this.pattern;
/*    */   }
/*    */ 
/*    */   public void setPattern(String pattern) {
/* 53 */     this.pattern = pattern;
/*    */   }
/*    */ 
/*    */   public String getTimes() {
/* 57 */     return this.times;
/*    */   }
/*    */ 
/*    */   public void setTimes(String times) {
/* 61 */     this.times = times;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.taglib.DateFormatTaglib
 * JD-Core Version:    0.6.0
 */