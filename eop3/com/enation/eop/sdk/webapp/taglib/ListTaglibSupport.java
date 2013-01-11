/*     */ package com.enation.eop.sdk.webapp.taglib;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ 
/*     */ public abstract class ListTaglibSupport extends BaseTaglibSupport
/*     */ {
/*  11 */   private boolean isFirst = true;
/*     */   protected String item;
/*     */   protected IListTaglibParam param;
/*     */   protected IListTaglibProvider tagProvider;
/*     */   private Iterator listIterator;
/*  21 */   private boolean hasNext = false;
/*     */   private int index;
/*     */ 
/*     */   protected String postStart()
/*     */   {
/*  27 */     return "";
/*     */   }
/*     */ 
/*     */   protected String postEnd()
/*     */   {
/*  32 */     return "";
/*     */   }
/*     */ 
/*     */   protected String postStartOnce() {
/*  36 */     return "";
/*     */   }
/*     */ 
/*     */   protected String postEndOnce() {
/*  40 */     return "";
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException
/*     */   {
/*  45 */     print(postStartOnce());
/*     */ 
/*  47 */     init();
/*     */ 
/*  49 */     List list = this.tagProvider.getData(this.param, this.pageContext);
/*  50 */     this.listIterator = list.iterator();
/*     */ 
/*  52 */     if (this.listIterator.hasNext()) {
/*  53 */       this.hasNext = true;
/*  54 */       setSope();
/*     */     }
/*     */ 
/*  57 */     if (this.hasNext) {
/*  58 */       print(postStart());
/*  59 */       return 1;
/*     */     }
/*  61 */     print(postEndOnce());
/*  62 */     return 0;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  67 */     this.index = 0;
/*  68 */     this.hasNext = false;
/*     */ 
/*  71 */     loadProvider();
/*     */   }
/*     */ 
/*     */   protected abstract void loadProvider();
/*     */ 
/*     */   protected void setSope() {
/*  78 */     Object row = this.listIterator.next();
/*  79 */     this.pageContext.setAttribute("index", Integer.valueOf(this.index));
/*  80 */     this.pageContext.setAttribute(this.item, row);
/*  81 */     this.pageContext.getRequest().setAttribute(this.item, row);
/*  82 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */   {
/*  87 */     print(postEnd());
/*     */ 
/*  89 */     if (this.listIterator.hasNext()) {
/*  90 */       print(postStart());
/*  91 */       setSope();
/*  92 */       return 2;
/*     */     }
/*  94 */     this.pageContext.removeAttribute(this.item);
/*  95 */     print(postEndOnce());
/*  96 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getItem()
/*     */   {
/* 101 */     return this.item;
/*     */   }
/*     */ 
/*     */   public void setItem(String item) {
/* 105 */     this.item = item;
/*     */   }
/*     */ 
/*     */   public int getIndex() {
/* 109 */     return this.index;
/*     */   }
/*     */ 
/*     */   public void setIndex(int index) {
/* 113 */     this.index = index;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.ListTaglibSupport
 * JD-Core Version:    0.6.0
 */