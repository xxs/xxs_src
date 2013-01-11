/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class StyleFileFilter
/*    */   implements FileFilter
/*    */ {
/*  9 */   private static final String[] EXTS = { "css", "js", "jpg", "png", "gif", "bmp" };
/*    */ 
/* 11 */   public boolean accept(File pathname) { String fileName = pathname.getName().toLowerCase();
/*    */ 
/* 13 */     if (pathname.isDirectory()) {
/* 14 */       if (fileName.equals("borders")) return false;
/* 15 */       if (fileName.equals("common")) return false;
/* 16 */       if (fileName.equals("custompage")) return false;
/* 17 */       return !fileName.equals(".svn");
/*    */     }
/*    */ 
/* 20 */     String ext = FileUtil.getFileExt(fileName).toLowerCase();
/* 21 */     for (String e : EXTS) {
/* 22 */       if (ext.equals(e)) {
/* 23 */         return true;
/*    */       }
/*    */     }
/* 26 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.StyleFileFilter
 * JD-Core Version:    0.6.0
 */