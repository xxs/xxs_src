/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.io.File;
/*     */ 
/*     */ public class UploadAction extends WWAction
/*     */ {
/*     */   private String fileFileName;
/*     */   private File file;
/*  20 */   private int createThumb = 0;
/*     */   private String subFolder;
/*     */   private int ajax;
/*     */   private String type;
/*     */   private String picname;
/*     */   private int width;
/*     */   private int height;
/*     */ 
/*     */   public String getPicname()
/*     */   {
/*  33 */     return this.picname;
/*     */   }
/*     */ 
/*     */   public void setPicname(String picname) {
/*  37 */     this.picname = picname;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/*  42 */     return "input";
/*     */   }
/*     */ 
/*     */   public String upload() {
/*  46 */     if ((this.file != null) && (this.fileFileName != null))
/*     */     {
/*  49 */       String path = null;
/*     */ 
/*  51 */       if (this.createThumb == 1) {
/*  52 */         path = UploadUtil.upload(this.file, this.fileFileName, this.subFolder, this.width, this.height)[0];
/*     */       }
/*     */       else {
/*  55 */         path = UploadUtil.upload(this.file, this.fileFileName, this.subFolder);
/*     */       }
/*     */ 
/*  58 */       if (path != null) {
/*  59 */         path = UploadUtil.replacePath(path);
/*     */       }
/*  61 */       if (this.ajax == 1) {
/*  62 */         this.json = ("{'result':1,'path':'" + path + "',filename:'" + this.fileFileName + "'}");
/*     */ 
/*  64 */         return "json_message";
/*     */       }
/*     */     }
/*  67 */     return "success";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*  71 */     UploadUtil.deleteFile(this.picname);
/*  72 */     this.json = "{'result':0}";
/*  73 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getFileFileName() {
/*  77 */     return this.fileFileName;
/*     */   }
/*     */ 
/*     */   public void setFileFileName(String fileFileName) {
/*  81 */     this.fileFileName = fileFileName;
/*     */   }
/*     */ 
/*     */   public File getFile() {
/*  85 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void setFile(File file) {
/*  89 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public int getCreateThumb() {
/*  93 */     return this.createThumb;
/*     */   }
/*     */ 
/*     */   public void setCreateThumb(int createThumb) {
/*  97 */     this.createThumb = createThumb;
/*     */   }
/*     */ 
/*     */   public String getSubFolder() {
/* 101 */     return this.subFolder;
/*     */   }
/*     */ 
/*     */   public void setSubFolder(String subFolder) {
/* 105 */     this.subFolder = subFolder;
/*     */   }
/*     */ 
/*     */   public int getAjax() {
/* 109 */     return this.ajax;
/*     */   }
/*     */ 
/*     */   public void setAjax(int ajax) {
/* 113 */     this.ajax = ajax;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 117 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(int width) {
/* 121 */     this.width = width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 125 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(int height) {
/* 129 */     this.height = height;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.UploadAction
 * JD-Core Version:    0.6.0
 */