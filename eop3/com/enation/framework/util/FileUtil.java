/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.tools.ant.Project;
/*     */ import org.apache.tools.ant.taskdefs.Delete;
/*     */ import org.apache.tools.ant.taskdefs.Expand;
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*     */   public static void createFile(InputStream in, String filePath)
/*     */   {
/*  32 */     if (in == null) throw new RuntimeException("create file error: inputstream is null");
/*  33 */     int potPos = filePath.lastIndexOf('/') + 1;
/*  34 */     String folderPath = filePath.substring(0, potPos);
/*  35 */     createFolder(folderPath);
/*  36 */     FileOutputStream outputStream = null;
/*     */     try {
/*  38 */       outputStream = new FileOutputStream(filePath);
/*  39 */       byte[] by = new byte[1024];
/*     */       int c;
/*  41 */       while ((c = in.read(by)) != -1)
/*  42 */         outputStream.write(by, 0, c);
/*     */     }
/*     */     catch (IOException e) {
/*  45 */       e.getStackTrace().toString();
/*     */     }
/*     */     try {
/*  48 */       outputStream.close();
/*     */     } catch (IOException e) {
/*  50 */       e.printStackTrace();
/*     */     }
/*     */     try {
/*  53 */       in.close();
/*     */     } catch (IOException e) {
/*  55 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isAllowUp(String logoFileName)
/*     */   {
/*  65 */     logoFileName = logoFileName.toLowerCase();
/*  66 */     String allowTYpe = "gif,jpg,bmp,swf,png,rar,doc,docx,xls,xlsx,pdf,zip,ico,txt";
/*  67 */     if ((!logoFileName.trim().equals("")) && (logoFileName.length() > 0)) {
/*  68 */       String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
/*     */ 
/*  73 */       return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
/*     */     }
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   public static void write(String filePath, String fileContent)
/*     */   {
/*     */     try
/*     */     {
/*  88 */       FileOutputStream fo = new FileOutputStream(filePath);
/*  89 */       OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");
/*     */ 
/*  91 */       out.write(fileContent);
/*     */ 
/*  93 */       out.close();
/*     */     }
/*     */     catch (IOException ex) {
/*  96 */       System.err.println("Create File Error!");
/*  97 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String read(String filePath, String code)
/*     */   {
/* 108 */     if ((code == null) || (code.equals(""))) {
/* 109 */       code = "UTF-8";
/*     */     }
/* 111 */     String fileContent = "";
/* 112 */     File file = new File(filePath);
/*     */     try {
/* 114 */       InputStreamReader read = new InputStreamReader(new FileInputStream(file), code);
/*     */ 
/* 116 */       BufferedReader reader = new BufferedReader(read);
/*     */       String line;
/* 118 */       while ((line = reader.readLine()) != null) {
/* 119 */         fileContent = fileContent + line + "\n";
/*     */       }
/* 121 */       read.close();
/* 122 */       read = null;
/* 123 */       reader.close();
/* 124 */       read = null;
/*     */     } catch (Exception ex) {
/* 126 */       ex.printStackTrace();
/* 127 */       fileContent = "";
/*     */     }
/* 129 */     return fileContent;
/*     */   }
/*     */ 
/*     */   public static void delete(String filePath)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       File file = new File(filePath);
/* 140 */       if (file.exists())
/* 141 */         if (file.isDirectory())
/* 142 */           FileUtils.deleteDirectory(file);
/*     */         else
/* 144 */           file.delete();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 148 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean exist(String filepath) {
/* 153 */     File file = new File(filepath);
/*     */ 
/* 155 */     return file.exists();
/*     */   }
/*     */ 
/*     */   public static void createFolder(String filePath)
/*     */   {
/*     */     try
/*     */     {
/* 165 */       File file = new File(filePath);
/* 166 */       if (!file.exists())
/* 167 */         file.mkdirs();
/*     */     }
/*     */     catch (Exception ex) {
/* 170 */       System.err.println("Make Folder Error:" + ex.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void renameFile(String from, String to)
/*     */   {
/*     */     try
/*     */     {
/* 181 */       File file = new File(from);
/* 182 */       if (file.exists())
/* 183 */         file.renameTo(new File(to));
/*     */     }
/*     */     catch (Exception ex) {
/* 186 */       System.err.println("Rename File/Folder Error:" + ex.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getFileExt(String fileName)
/*     */   {
/* 198 */     int potPos = fileName.lastIndexOf('.') + 1;
/* 199 */     String type = fileName.substring(potPos, fileName.length());
/* 200 */     return type;
/*     */   }
/*     */ 
/*     */   public static void createFile(File file, String filePath)
/*     */   {
/* 210 */     int potPos = filePath.lastIndexOf('/') + 1;
/* 211 */     String folderPath = filePath.substring(0, potPos);
/* 212 */     createFolder(folderPath);
/* 213 */     FileOutputStream outputStream = null;
/* 214 */     FileInputStream fileInputStream = null;
/*     */     try {
/* 216 */       outputStream = new FileOutputStream(filePath);
/* 217 */       fileInputStream = new FileInputStream(file);
/* 218 */       byte[] by = new byte[1024];
/*     */       int c;
/* 220 */       while ((c = fileInputStream.read(by)) != -1)
/* 221 */         outputStream.write(by, 0, c);
/*     */     }
/*     */     catch (IOException e) {
/* 224 */       e.getStackTrace().toString();
/*     */     }
/*     */     try {
/* 227 */       outputStream.close();
/*     */     } catch (IOException e) {
/* 229 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 232 */       fileInputStream.close();
/*     */     } catch (IOException e) {
/* 234 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String readFile(String resource)
/*     */   {
/* 243 */     InputStream stream = getResourceAsStream(resource);
/* 244 */     String content = readStreamToString(stream);
/*     */ 
/* 246 */     return content;
/*     */   }
/*     */ 
/*     */   public static InputStream getResourceAsStream(String resource)
/*     */   {
/* 251 */     String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
/*     */ 
/* 254 */     InputStream stream = null;
/* 255 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */ 
/* 257 */     if (classLoader != null) {
/* 258 */       stream = classLoader.getResourceAsStream(stripped);
/*     */     }
/*     */ 
/* 262 */     return stream;
/*     */   }
/*     */ 
/*     */   public static String readStreamToString(InputStream stream) {
/* 266 */     String fileContent = "";
/*     */     try
/*     */     {
/* 269 */       InputStreamReader read = new InputStreamReader(stream, "utf-8");
/* 270 */       BufferedReader reader = new BufferedReader(read);
/*     */       String line;
/* 272 */       while ((line = reader.readLine()) != null) {
/* 273 */         fileContent = fileContent + line + "\n";
/*     */       }
/* 275 */       read.close();
/* 276 */       read = null;
/* 277 */       reader.close();
/* 278 */       read = null;
/*     */     } catch (Exception ex) {
/* 280 */       fileContent = "";
/*     */     }
/* 282 */     return fileContent;
/*     */   }
/*     */ 
/*     */   public static void removeFile(File path)
/*     */   {
/* 292 */     if (path.isDirectory())
/*     */       try {
/* 294 */         FileUtils.deleteDirectory(path);
/*     */       } catch (IOException e) {
/* 296 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void copyFile(String srcFile, String destFile)
/*     */   {
/*     */     try
/*     */     {
/* 304 */       if (exist(srcFile))
/* 305 */         FileUtils.copyFile(new File(srcFile), new File(destFile));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 309 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copyFolder(String sourceFolder, String destinationFolder)
/*     */   {
/*     */     try
/*     */     {
/* 317 */       File sourceF = new File(sourceFolder);
/* 318 */       if (sourceF.exists())
/* 319 */         FileUtils.copyDirectory(new File(sourceFolder), new File(destinationFolder));
/*     */     }
/*     */     catch (Exception e) {
/* 322 */       e.printStackTrace();
/* 323 */       throw new RuntimeException("copy file error");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copyNewFile(String sourceFolder, String targetFolder)
/*     */   {
/*     */     try
/*     */     {
/* 338 */       File sourceF = new File(sourceFolder);
/*     */ 
/* 340 */       if (!targetFolder.endsWith("/")) targetFolder = targetFolder + "/";
/*     */ 
/* 342 */       if (sourceF.exists()) {
/* 343 */         File[] filelist = sourceF.listFiles();
/* 344 */         for (File f : filelist) {
/* 345 */           File targetFile = new File(targetFolder + f.getName());
/*     */ 
/* 347 */           if (!f.isFile())
/*     */             continue;
/* 349 */           if ((!targetFile.exists()) || (FileUtils.isFileNewer(f, targetFile))) {
/* 350 */             FileUtils.copyFileToDirectory(f, new File(targetFolder));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 361 */       e.printStackTrace();
/* 362 */       throw new RuntimeException("copy file error");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void unZip(String zipPath, String targetFolder, boolean cleanZip)
/*     */   {
/* 368 */     File folderFile = new File(targetFolder);
/* 369 */     File zipFile = new File(zipPath);
/* 370 */     Project prj = new Project();
/* 371 */     Expand expand = new Expand();
/* 372 */     expand.setEncoding("UTF-8");
/* 373 */     expand.setProject(prj);
/* 374 */     expand.setSrc(zipFile);
/* 375 */     expand.setOverwrite(true);
/* 376 */     expand.setDest(folderFile);
/* 377 */     expand.execute();
/*     */ 
/* 379 */     if (cleanZip)
/*     */     {
/* 381 */       Delete delete = new Delete();
/* 382 */       delete.setProject(prj);
/* 383 */       delete.setDir(zipFile);
/* 384 */       delete.execute();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] arg)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.FileUtil
 * JD-Core Version:    0.6.0
 */