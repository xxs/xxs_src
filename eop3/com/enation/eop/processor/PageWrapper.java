/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ public class PageWrapper
/*    */   implements IPagePaser
/*    */ {
/*    */   protected IPagePaser pagePaser;
/*    */ 
/*    */   public PageWrapper(IPagePaser paser)
/*    */   {
/* 12 */     this.pagePaser = paser;
/*    */   }
/*    */ 
/*    */   public String pase(String url)
/*    */   {
/* 17 */     return this.pagePaser.pase(url);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.PageWrapper
 * JD-Core Version:    0.6.0
 */