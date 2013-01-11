/*     */ package com.enation.app.shop.core.service.impl.batchimport.util;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ThumbnailCreatorFactory;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GoodsImageReader
/*     */ {
/*  16 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private ISettingService settingService;
/*     */ 
/*     */   private String getSettingValue(String code)
/*     */   {
/*  19 */     return this.settingService.getSetting("photo", code);
/*     */   }
/*     */   public String[] read(String folder, String goodsid, String excludeStr) {
/*  22 */     String[] exclude = null;
/*     */ 
/*  24 */     if (!StringUtil.isEmpty(excludeStr)) {
/*  25 */       exclude = excludeStr.split(",");
/*     */     }
/*  27 */     int thumbnail_pic_width = 107;
/*  28 */     int thumbnail_pic_height = 107;
/*  29 */     int detail_pic_width = 320;
/*  30 */     int detail_pic_height = 240;
/*  31 */     int album_pic_width = 550;
/*  32 */     int album_pic_height = 412;
/*     */     try
/*     */     {
/*  38 */       thumbnail_pic_width = Integer.valueOf(getSettingValue("thumbnail_pic_width").toString()).intValue();
/*  39 */       thumbnail_pic_height = Integer.valueOf(getSettingValue("thumbnail_pic_height").toString()).intValue();
/*     */ 
/*  41 */       detail_pic_width = Integer.valueOf(getSettingValue("detail_pic_width").toString()).intValue();
/*  42 */       detail_pic_height = Integer.valueOf(getSettingValue("detail_pic_height").toString()).intValue();
/*     */ 
/*  44 */       album_pic_width = Integer.valueOf(getSettingValue("album_pic_width").toString()).intValue();
/*  45 */       album_pic_height = Integer.valueOf(getSettingValue("album_pic_height").toString()).intValue();
/*     */     } catch (NumberFormatException e) {
/*  47 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  52 */     if (this.logger.isDebugEnabled()) {
/*  53 */       this.logger.debug("开始导入商品[" + goodsid + "]图片...");
/*     */     }
/*     */ 
/*  56 */     String imgsString = "";
/*  57 */     String defaultImg = "";
/*  58 */     String datafolder = folder + "/images";
/*  59 */     File file = new File(datafolder);
/*     */ 
/*  61 */     if (!file.exists()) {
/*  62 */       if (this.logger.isDebugEnabled()) {
/*  63 */         this.logger.debug("商品[" + goodsid + "]图片目录不存在跳过");
/*     */       }
/*  65 */       return null;
/*     */     }
/*     */ 
/*  68 */     String[] imgNames = file.list();
/*  69 */     for (String imgname : imgNames) {
/*  70 */       String lowname = imgname.toLowerCase();
/*  71 */       if ((!lowname.endsWith(".jpg")) && (!lowname.endsWith(".gif")) && (!lowname.endsWith(".jpeg")) && (!lowname.endsWith(".bmp")) && (!lowname.endsWith(".png")))
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/*  82 */       if (this.logger.isDebugEnabled()) {
/*  83 */         this.logger.debug("处理图片[" + imgname + "]...");
/*     */       }
/*     */ 
/*  88 */       if (isExclude(exclude, imgname))
/*     */       {
/*  90 */         if (this.logger.isDebugEnabled()) {
/*  91 */           this.logger.debug("图片[" + imgname + "]在排除范围，跳过.");
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  97 */         String sourcePath = datafolder + "/" + imgname;
/*     */ 
/* 100 */         if (new File(sourcePath).isDirectory())
/*     */           continue;
/* 102 */         imgname = imgname.toLowerCase();
/* 103 */         FileUtil.copyFile(sourcePath, EopSetting.IMG_SERVER_PATH + "/attachment/goods/" + goodsid + "/" + imgname);
/* 104 */         String goodsImg = "fs:/attachment/goods/" + goodsid + "/" + imgname;
/*     */ 
/* 111 */         if (this.logger.isDebugEnabled()) {
/* 112 */           this.logger.debug("为图片[" + imgname + "]生成各种缩略图...");
/*     */         }
/* 114 */         String targetPath = getThumbPath(imgname, "_thumbnail", goodsid);
/* 115 */         createThumb(sourcePath, targetPath, thumbnail_pic_width, thumbnail_pic_height);
/*     */ 
/* 117 */         targetPath = getThumbPath(imgname, "_small", goodsid);
/* 118 */         createThumb(sourcePath, targetPath, detail_pic_width, detail_pic_height);
/*     */ 
/* 120 */         targetPath = getThumbPath(imgname, "_big", goodsid);
/* 121 */         createThumb(sourcePath, targetPath, album_pic_width, album_pic_height);
/*     */ 
/* 131 */         if (!imgsString.equals("")) imgsString = imgsString + ",";
/* 132 */         imgsString = imgsString + goodsImg;
/*     */ 
/* 134 */         if (imgname.startsWith("default")) {
/* 135 */           defaultImg = goodsImg;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     if ((defaultImg.equals("")) && (imgsString != null)) defaultImg = imgsString.split(",")[0];
/*     */ 
/* 145 */     if (this.logger.isDebugEnabled()) {
/* 146 */       this.logger.debug("缩略图生成完毕，图片字串信息为image_file[" + imgsString + "],defaultImg[" + defaultImg + "]");
/*     */     }
/*     */ 
/* 150 */     if (this.logger.isDebugEnabled()) {
/* 151 */       this.logger.debug(" 商品[" + goodsid + "]图片导入完成");
/*     */     }
/*     */ 
/* 154 */     return new String[] { imgsString, defaultImg };
/*     */   }
/*     */ 
/*     */   private boolean isExclude(String[] exclude, String imageName)
/*     */   {
/* 165 */     if (exclude == null) return false;
/* 166 */     for (String ex : exclude) {
/* 167 */       if (imageName.startsWith(ex)) return true;
/*     */     }
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   private void createThumb(String sourcePath, String targetPath, int width, int height)
/*     */   {
/* 174 */     IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(sourcePath, targetPath);
/* 175 */     thumbnailCreator.resize(width, height);
/*     */   }
/*     */   private String getThumbPath(String filename, String shortName, String goodsid) {
/* 178 */     String name = UploadUtil.getThumbPath(filename, shortName);
/* 179 */     String path = EopSetting.IMG_SERVER_PATH + "/attachment/goods/" + goodsid;
/* 180 */     File file = new File(path);
/*     */ 
/* 182 */     if (!file.exists()) file.mkdir();
/*     */ 
/* 184 */     path = path + "/" + name;
/* 185 */     return path;
/*     */   }
/*     */   public ISettingService getSettingService() {
/* 188 */     return this.settingService;
/*     */   }
/*     */   public void setSettingService(ISettingService settingService) {
/* 191 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 195 */     System.out.println("aaa.jpg".endsWith(".jpg"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.util.GoodsImageReader
 * JD-Core Version:    0.6.0
 */