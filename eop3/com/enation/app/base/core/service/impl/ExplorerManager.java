/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.FileNode;
/*     */ import com.enation.app.base.core.service.IExplorerManager;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ExplorerManager
/*     */   implements IExplorerManager
/*     */ {
/*     */   private String rootPath;
/*     */ 
/*     */   public ExplorerManager(String rootPath)
/*     */   {
/*  24 */     if (rootPath == null) throw new IllegalArgumentException("param rootPath is NULL");
/*  25 */     this.rootPath = rootPath;
/*     */   }
/*     */ 
/*     */   public void add(FileNode node) throws IOException
/*     */   {
/*  30 */     if (node == null) throw new IllegalArgumentException("param FileNode is NULL");
/*     */ 
/*  32 */     String filePath = this.rootPath + "/" + node.getName();
/*  33 */     File file = new File(filePath);
/*     */ 
/*  35 */     if (file.exists()) {
/*  36 */       throw new RuntimeException("文件" + node.getName() + "已存在");
/*     */     }
/*     */ 
/*  40 */     if (node.getIsfolder().intValue() == 1) {
/*  41 */       file.mkdirs();
/*     */     } else {
/*  43 */       file.createNewFile();
/*  44 */       FileUtil.write(filePath, node.getContent());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(String name)
/*     */   {
/*  50 */     FileUtil.delete(this.rootPath + name);
/*     */   }
/*     */ 
/*     */   public void edit(FileNode node) {
/*  54 */     FileUtil.write(this.rootPath + node.getName(), node.getContent());
/*     */   }
/*     */ 
/*     */   public List<FileNode> list(FileFilter filter) {
/*  58 */     List list = new ArrayList();
/*  59 */     File file = new File(this.rootPath);
/*     */ 
/*  61 */     File[] files = null;
/*  62 */     if (filter != null)
/*  63 */       files = file.listFiles(filter);
/*     */     else {
/*  65 */       files = file.listFiles();
/*     */     }
/*  67 */     if (files == null) return list;
/*  68 */     for (File f : files)
/*     */     {
/*  70 */       FileNode node = fileToNode(f);
/*  71 */       list.add(node);
/*     */     }
/*  73 */     return list;
/*     */   }
/*     */ 
/*     */   public void move(String name, String oldFolder, String newFolder)
/*     */   {
/*  78 */     String oldRoot = this.rootPath;
/*  79 */     if ((newFolder.startsWith("/")) && (!oldFolder.equals("/"))) {
/*  80 */       this.rootPath = this.rootPath.replaceAll(oldFolder, "");
/*     */     }
/*     */ 
/*  83 */     if ((newFolder.startsWith("/")) && (oldFolder.equals("/"))) {
/*  84 */       newFolder = newFolder.substring(1, newFolder.length());
/*     */     }
/*     */ 
/*  87 */     if (!newFolder.endsWith("/")) {
/*  88 */       newFolder = newFolder + "/";
/*     */     }
/*     */ 
/*  91 */     String target = this.rootPath + newFolder + name;
/*  92 */     if (!new File(this.rootPath + newFolder).exists()) {
/*  93 */       throw new RuntimeException(newFolder + " 不存在");
/*     */     }
/*     */ 
/*  96 */     FileUtil.copyFile(oldRoot + name, target);
/*  97 */     FileUtil.delete(oldRoot + name);
/*     */   }
/*     */ 
/*     */   public void rename(String oldname, String newname)
/*     */   {
/* 102 */     File file = new File(this.rootPath + oldname);
/* 103 */     File newFile = new File(this.rootPath + newname);
/* 104 */     boolean result = file.renameTo(newFile);
/*     */   }
/*     */ 
/*     */   public FileNode get(String name)
/*     */   {
/* 109 */     File file = new File(this.rootPath + name);
/* 110 */     FileNode node = fileToNode(file);
/* 111 */     node.setContent(FileUtil.read(this.rootPath + name, "UTF-8"));
/* 112 */     return node;
/*     */   }
/*     */ 
/*     */   private FileNode fileToNode(File f) {
/* 116 */     FileNode node = new FileNode();
/* 117 */     node.setName(f.getName());
/* 118 */     node.setIsfolder(Integer.valueOf(f.isDirectory() ? 1 : 0));
/* 119 */     node.setSize(Long.valueOf(f.length()));
/* 120 */     node.setLastmodify(Long.valueOf(f.lastModified()));
/* 121 */     if (node.getIsfolder().intValue() == 0) {
/* 122 */       node.setExt(FileUtil.getFileExt(node.getName()).toLowerCase());
/*     */     }
/*     */ 
/* 125 */     return node;
/*     */   }
/*     */ 
/*     */   public void upload(File file, String fileFileName)
/*     */   {
/* 130 */     String filePath = this.rootPath + fileFileName;
/* 131 */     File temp = new File(filePath);
/* 132 */     if (temp.exists()) throw new RuntimeException("文件" + fileFileName + "已经存在");
/* 133 */     FileUtil.createFile(file, filePath);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.ExplorerManager
 * JD-Core Version:    0.6.0
 */