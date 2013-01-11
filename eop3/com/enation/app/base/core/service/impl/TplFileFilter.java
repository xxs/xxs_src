/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class TplFileFilter
/*    */   implements FileFilter
/*    */ {
/* 13 */   private static final String[] EXTS = { "html", "xml" };
/*    */ 
/* 15 */   public boolean accept(File pathname) { String fileName = pathname.getName();
/*    */ 
/* 17 */     if (pathname.isDirectory()) {
/* 18 */       if (fileName.equals("images")) return false;
/* 19 */       if (fileName.equals("css")) return false;
/* 20 */       if (fileName.equals("js")) return false;
/* 21 */       return !fileName.equals(".svn");
/*    */     }
/*    */ 
/* 24 */     String ext = FileUtil.getFileExt(fileName).toLowerCase();
/* 25 */     for (String e : EXTS) {
/* 26 */       if (ext.equals(e)) {
/* 27 */         return true;
/*    */       }
/*    */     }
/* 30 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.TplFileFilter
 * JD-Core Version:    0.6.0
 */