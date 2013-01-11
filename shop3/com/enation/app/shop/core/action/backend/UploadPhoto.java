/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IGoodsAlbumManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class UploadPhoto extends WWAction
/*     */ {
/*     */   private IGoodsAlbumManager goodsAlbumManager;
/*     */   private File filedata;
/*     */   private String filedataFileName;
/*     */   private String photoName;
/*     */ 
/*     */   public void delFile(String fileName)
/*     */   {
/*  19 */     if ((fileName != null) && (!fileName.trim().equals(""))) {
/*  20 */       File file = new File(StringUtil.getRootPath() + "/" + fileName);
/*  21 */       file.delete();
/*  22 */       File fileThumb = new File(StringUtil.getRootPath() + getThumbpath(fileName));
/*     */ 
/*  24 */       if (fileThumb.exists())
/*  25 */         fileThumb.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getThumbpath(String file) {
/*  30 */     String fStr = "";
/*  31 */     if (!file.trim().equals("")) {
/*  32 */       String[] arr = file.split("/");
/*  33 */       fStr = "/" + arr[0] + "/" + arr[1] + "/thumb/" + arr[2];
/*     */     }
/*  35 */     return fStr;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/*  43 */     if (this.filedata != null)
/*     */     {
/*  45 */       String[] names = this.goodsAlbumManager.upload(this.filedata, this.filedataFileName);
/*     */ 
/*  47 */       this.json = (names[0] + "," + names[0]);
/*     */     }
/*     */ 
/*  50 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delPhoto()
/*     */   {
/*  55 */     this.goodsAlbumManager.delete(this.photoName);
/*  56 */     this.json = "{'result':0,'message:':'图片删除成功'}";
/*  57 */     return "json_message";
/*     */   }
/*     */ 
/*     */   private static String getName(String path) {
/*  61 */     String regEx = "(/goods/)(.*)";
/*  62 */     Pattern p = Pattern.compile(regEx);
/*  63 */     Matcher m = p.matcher(path);
/*  64 */     String name = "";
/*     */ 
/*  66 */     while (m.find()) {
/*  67 */       name = m.group();
/*     */     }
/*  69 */     return name;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  75 */     String path = "http://www.javashop.com/attachment/goods/200901020201052381.jpg";
/*     */ 
/*  78 */     String name = getName(path);
/*     */   }
/*     */   public File getFiledata() {
/*  81 */     return this.filedata;
/*     */   }
/*     */ 
/*     */   public void setFiledata(File filedata) {
/*  85 */     this.filedata = filedata;
/*     */   }
/*     */ 
/*     */   public String getFiledataFileName() {
/*  89 */     return this.filedataFileName;
/*     */   }
/*     */ 
/*     */   public void setFiledataFileName(String filedataFileName) {
/*  93 */     this.filedataFileName = filedataFileName;
/*     */   }
/*     */ 
/*     */   public String getPhotoName()
/*     */   {
/*  98 */     return this.photoName;
/*     */   }
/*     */ 
/*     */   public void setPhotoName(String photoName)
/*     */   {
/* 103 */     this.photoName = photoName;
/*     */   }
/*     */ 
/*     */   public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
/* 107 */     this.goodsAlbumManager = goodsAlbumManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.UploadPhoto
 * JD-Core Version:    0.6.0
 */