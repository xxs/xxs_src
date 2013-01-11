/*     */ package com.enation.app.shop.core.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class CodeLines
/*     */ {
/*  10 */   private static long sums = 0L;
/*     */   private static String[] suffixs;
/*     */   private static String target;
/*  15 */   private static int FLUSH_FLAG = 65536;
/*     */ 
/*  17 */   private static StringBuffer statistics = new StringBuffer();
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/*  27 */     args = new String[] { "E:/workspace/Java/eop", "java", "xml", "properties", "html", "jsp", "css", "js" };
/*     */ 
/*  29 */     long startTimes = System.currentTimeMillis();
/*  30 */     if (args.length > 1) {
/*  31 */       suffixs = new String[args.length - 1];
/*     */     } else {
/*  33 */       System.out.println("As that : targetLocation , fileSuffix , fileSuffix . . .");
/*     */ 
/*  35 */       return;
/*     */     }
/*     */ 
/*  38 */     for (int i = 0; i < args.length; i++) {
/*  39 */       if (i == 0)
/*  40 */         target = args[i];
/*     */       else {
/*  42 */         suffixs[(i - 1)] = args[i];
/*     */       }
/*     */     }
/*     */ 
/*  46 */     File targetFile = new File(target);
/*  47 */     if (targetFile.exists()) {
/*  48 */       statistic(targetFile);
/*  49 */       System.out.print(statistics.toString());
/*  50 */       System.out.println("All completement. U write [" + sums + "] lines code. did great job!");
/*     */     }
/*     */     else {
/*  53 */       System.out.println("File or Dir not exist : " + target);
/*     */     }
/*     */ 
/*  56 */     System.out.println("Total times " + (System.currentTimeMillis() - startTimes) + " ms");
/*     */   }
/*     */ 
/*     */   private static void statistic(File file)
/*     */     throws IOException
/*     */   {
/*  72 */     if (file.isDirectory()) {
/*  73 */       File[] files = file.listFiles();
/*  74 */       for (int i = 0; i < files.length; i++) {
/*  75 */         statistic(files[i]);
/*     */       }
/*     */     }
/*     */ 
/*  79 */     if ((file.isFile()) && 
/*  80 */       (isMatchSuffixs(file)))
/*  81 */       sums += countFileTextLines(file);
/*     */   }
/*     */ 
/*     */   private static boolean isMatchSuffixs(File file)
/*     */   {
/*  99 */     String fileName = file.getName();
/* 100 */     if (fileName.indexOf(".") != -1) {
/* 101 */       String extName = fileName.substring(fileName.indexOf(".") + 1);
/* 102 */       for (int i = 0; i < suffixs.length; i++) {
/* 103 */         if (suffixs[i].equals(extName)) {
/* 104 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   private static long countFileTextLines(File file)
/*     */     throws IOException
/*     */   {
/* 120 */     long result = 0L;
/* 121 */     if (statistics.length() > FLUSH_FLAG) {
/* 122 */       System.out.print(statistics.toString());
/* 123 */       statistics = new StringBuffer();
/*     */     }
/* 125 */     statistics.append("Counting in ").append(file.getAbsolutePath());
/* 126 */     BufferedReader br = new BufferedReader(new FileReader(file));
/* 127 */     while (br.readLine() != null)
/* 128 */       result += 1L;
/* 129 */     br.close();
/* 130 */     statistics.append("   -  ").append(result).append("\n");
/* 131 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.utils.CodeLines
 * JD-Core Version:    0.6.0
 */