/*    */ package com.enation.eop.processor.httpcache;
/*    */ 
/*    */ public class HttpCacheStatus
/*    */ {
/*    */   private boolean isModified;
/*    */   private long lasyModified;
/*    */ 
/*    */   public boolean isModified()
/*    */   {
/* 14 */     return this.isModified;
/*    */   }
/*    */   public void setModified(boolean isModified) {
/* 17 */     this.isModified = isModified;
/*    */   }
/*    */   public long getLasyModified() {
/* 20 */     return this.lasyModified;
/*    */   }
/*    */   public void setLasyModified(long lasyModified) {
/* 23 */     this.lasyModified = lasyModified;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.httpcache.HttpCacheStatus
 * JD-Core Version:    0.6.0
 */