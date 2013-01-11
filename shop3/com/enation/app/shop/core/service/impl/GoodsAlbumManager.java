/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.service.IGoodsAlbumManager;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ThumbnailCreatorFactory;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.ImageMagickMaskUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class GoodsAlbumManager extends BaseSupport<Goods>
/*     */   implements IGoodsAlbumManager
/*     */ {
/*     */   private IGoodsManager goodsManager;
/*     */   private ISettingService settingService;
/*     */ 
/*     */   public void delete(String photoName)
/*     */   {
/*  36 */     if ((photoName != null) && (!photoName.startsWith("http://static.enationsoft.com/attachment/"))) {
/*  37 */       photoName = UploadUtil.replacePath(photoName);
/*  38 */       photoName = photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/*  39 */       FileUtil.delete(photoName);
/*  40 */       FileUtil.delete(UploadUtil.getThumbPath(photoName, "_thumbnail"));
/*  41 */       FileUtil.delete(UploadUtil.getThumbPath(photoName, "_small"));
/*  42 */       FileUtil.delete(UploadUtil.getThumbPath(photoName, "_big"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Integer[] goodsid) {
/*  47 */     String id_str = StringUtil.arrayToString(goodsid, ",");
/*  48 */     String sql = "select image_file from goods where goods_id in (" + id_str + ")";
/*  49 */     List goodsList = this.baseDaoSupport.queryForList(sql, new Object[0]);
/*  50 */     for (Map goods : goodsList) {
/*  51 */       String name = (String)goods.get("image_file");
/*  52 */       if ((name != null) && (!name.equals(""))) {
/*  53 */         String[] pname = name.split(",");
/*  54 */         for (String n : pname)
/*  55 */           delete(n);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] upload(File file, String fileFileName)
/*     */   {
/*  70 */     String fileName = null;
/*  71 */     String filePath = "";
/*     */ 
/*  73 */     String[] path = new String[2];
/*     */ 
/*  75 */     if ((file != null) && (fileFileName != null)) {
/*  76 */       String ext = FileUtil.getFileExt(fileFileName);
/*  77 */       fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
/*  78 */       filePath = EopSetting.IMG_SERVER_PATH + getContextFolder() + "/attachment/goods/";
/*  79 */       path[0] = (EopSetting.IMG_SERVER_DOMAIN + getContextFolder() + "/attachment/goods/" + fileName);
/*  80 */       filePath = filePath + fileName;
/*  81 */       FileUtil.createFile(file, filePath);
/*     */ 
/*  83 */       String watermark = this.settingService.getSetting("photo", "watermark");
/*  84 */       String marktext = this.settingService.getSetting("photo", "marktext");
/*  85 */       String markpos = this.settingService.getSetting("photo", "markpos");
/*  86 */       String markcolor = this.settingService.getSetting("photo", "markcolor");
/*  87 */       String marksize = this.settingService.getSetting("photo", "marksize");
/*     */ 
/*  89 */       marktext = StringUtil.isEmpty(marktext) ? "水印文字" : marktext;
/*  90 */       marksize = StringUtil.isEmpty(marksize) ? "12" : marksize;
/*     */ 
/*  92 */       if ((watermark != null) && (watermark.equals("on"))) {
/*  93 */         ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
/*  94 */         magickMask.mask(filePath, marktext, markcolor, Integer.valueOf(marksize).intValue(), Integer.valueOf(markpos).intValue(), EopSetting.EOP_PATH + "/font/st.TTF");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  99 */     return path;
/*     */   }
/*     */ 
/*     */   public static String getContextFolder() {
/* 103 */     return EopContext.getContext().getContextPath();
/*     */   }
/*     */ 
/*     */   public void createThumb(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
/*     */   {
/* 112 */     String imgDomain = "http://static.enationsoft.com/attachment/";
/* 113 */     if ((filepath != null) && (!filepath.startsWith(imgDomain))) {
/* 114 */       filepath = filepath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/* 115 */       thumbName = thumbName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/* 116 */       File tempFile = new File(thumbName);
/* 117 */       if (!tempFile.exists())
/*     */       {
/* 119 */         IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
/* 120 */         thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTotal()
/*     */   {
/* 127 */     return this.goodsManager.list().size();
/*     */   }
/*     */ 
/*     */   public void recreate(int start, int end)
/*     */   {
/* 132 */     int thumbnail_pic_width = 107;
/* 133 */     int thumbnail_pic_height = 107;
/* 134 */     int detail_pic_width = 320;
/* 135 */     int detail_pic_height = 240;
/* 136 */     int album_pic_width = 550;
/* 137 */     int album_pic_height = 412;
/*     */     try
/*     */     {
/* 143 */       thumbnail_pic_width = Integer.valueOf(getSettingValue("thumbnail_pic_width").toString()).intValue();
/* 144 */       thumbnail_pic_height = Integer.valueOf(getSettingValue("thumbnail_pic_height").toString()).intValue();
/*     */ 
/* 146 */       detail_pic_width = Integer.valueOf(getSettingValue("detail_pic_width").toString()).intValue();
/* 147 */       detail_pic_height = Integer.valueOf(getSettingValue("detail_pic_height").toString()).intValue();
/*     */ 
/* 149 */       album_pic_width = Integer.valueOf(getSettingValue("album_pic_width").toString()).intValue();
/* 150 */       album_pic_height = Integer.valueOf(getSettingValue("album_pic_height").toString()).intValue();
/*     */     } catch (NumberFormatException e) {
/* 152 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 155 */     List goodsList = this.goodsManager.list();
/* 156 */     for (int i = start - 1; i < end; i++) {
/* 157 */       Map goods = (Map)goodsList.get(i);
/* 158 */       String imageFile = (String)goods.get("image_file");
/* 159 */       if (imageFile != null) {
/* 160 */         String[] imgFileAr = imageFile.split(",");
/* 161 */         System.out.println("Create thumbnail image, the index:" + (i + 1));
/* 162 */         for (String imgFile : imgFileAr) {
/* 163 */           String realPath = UploadUtil.replacePath(imgFile);
/* 164 */           realPath = realPath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/* 165 */           System.out.println("Image file:" + realPath);
/*     */ 
/* 167 */           String thumbName = UploadUtil.getThumbPath(realPath, "_thumbnail");
/* 168 */           createThumb1(realPath, thumbName, thumbnail_pic_width, thumbnail_pic_height);
/*     */ 
/* 171 */           thumbName = UploadUtil.getThumbPath(realPath, "_small");
/* 172 */           createThumb1(realPath, thumbName, detail_pic_width, detail_pic_height);
/*     */ 
/* 175 */           thumbName = UploadUtil.getThumbPath(realPath, "_big");
/* 176 */           createThumb1(realPath, thumbName, album_pic_width, album_pic_height);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getSettingValue(String code) {
/* 183 */     return this.settingService.getSetting("photo", code);
/*     */   }
/*     */ 
/*     */   private void createThumb1(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
/*     */   {
/* 194 */     if (!StringUtil.isEmpty(filepath)) {
/* 195 */       String ctx = EopContext.getContext().getContextPath();
/* 196 */       filepath = filepath.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
/* 197 */       thumbName = thumbName.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
/* 198 */       IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
/* 199 */       thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ISettingService getSettingService() {
/* 204 */     return this.settingService;
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService) {
/* 208 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 212 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 216 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsAlbumManager
 * JD-Core Version:    0.6.0
 */