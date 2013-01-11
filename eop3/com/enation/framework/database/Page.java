/*     */ package com.enation.framework.database;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Page
/*     */   implements Serializable
/*     */ {
/*  15 */   private static int DEFAULT_PAGE_SIZE = 20;
/*     */ 
/*  17 */   private int pageSize = DEFAULT_PAGE_SIZE;
/*     */   private long start;
/*     */   private Object data;
/*     */   private long totalCount;
/*     */ 
/*     */   public Page()
/*     */   {
/*  29 */     this(0L, 0L, DEFAULT_PAGE_SIZE, new ArrayList());
/*     */   }
/*     */ 
/*     */   public Page(long start, long totalSize, int pageSize, Object data)
/*     */   {
/*  45 */     setParam(start, totalSize, pageSize, data);
/*     */   }
/*     */ 
/*     */   public void setParam(long start, long totalSize, int pageSize, Object data) {
/*  49 */     this.pageSize = pageSize;
/*  50 */     this.start = start;
/*  51 */     this.totalCount = totalSize;
/*  52 */     this.data = data;
/*     */   }
/*     */ 
/*     */   public long getTotalCount()
/*     */   {
/*  59 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public long getTotalPageCount()
/*     */   {
/*  66 */     if (this.totalCount % this.pageSize == 0L) {
/*  67 */       return this.totalCount / this.pageSize;
/*     */     }
/*  69 */     return this.totalCount / this.pageSize + 1L;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  76 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public Object getResult()
/*     */   {
/*  83 */     return this.data;
/*     */   }
/*     */ 
/*     */   public long getCurrentPageNo()
/*     */   {
/*  90 */     return this.start / this.pageSize + 1L;
/*     */   }
/*     */ 
/*     */   public boolean hasNextPage()
/*     */   {
/*  97 */     return getCurrentPageNo() < getTotalPageCount() - 1L;
/*     */   }
/*     */ 
/*     */   public boolean hasPreviousPage()
/*     */   {
/* 104 */     return getCurrentPageNo() > 1L;
/*     */   }
/*     */ 
/*     */   protected static int getStartOfPage(int pageNo)
/*     */   {
/* 113 */     return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
/*     */   }
/*     */ 
/*     */   public static int getStartOfPage(int pageNo, int pageSize)
/*     */   {
/* 126 */     return (pageNo - 1) * pageSize;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.Page
 * JD-Core Version:    0.6.0
 */