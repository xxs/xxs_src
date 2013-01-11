/*     */ package com.enation.app.shop.component.goodscore.plugin.album;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsAlbumManager;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GalleryAdminPlugin extends DefaultAlbumPlugin
/*     */   implements IGoodsDeleteEvent, IGoodsTabShowEvent
/*     */ {
/*     */   private ISettingService settingService;
/*     */   private IGoodsAlbumManager goodsAlbumManager;
/*     */ 
/*     */   private String getSettingValue(String code)
/*     */   {
/*  33 */     return this.settingService.getSetting("photo", code);
/*     */   }
/*     */ 
/*     */   protected void proessPhoto(String[] picnames, Map goods)
/*     */   {
/*  47 */     if (picnames == null)
/*     */     {
/*  50 */       return;
/*     */     }
/*     */ 
/*  53 */     String imgsString = "";
/*  54 */     for (int i = 0; i < picnames.length; i++) {
/*  55 */       String filepath = picnames[i];
/*     */ 
/*  57 */       if (i != 0) {
/*  58 */         imgsString = imgsString + ",";
/*     */       }
/*  60 */       imgsString = imgsString + picnames[i];
/*     */ 
/*  63 */       int thumbnail_pic_width = 107;
/*  64 */       int thumbnail_pic_height = 107;
/*  65 */       int detail_pic_width = 320;
/*  66 */       int detail_pic_height = 240;
/*  67 */       int album_pic_width = 550;
/*  68 */       int album_pic_height = 412;
/*     */       try
/*     */       {
/*  74 */         thumbnail_pic_width = Integer.valueOf(getSettingValue("thumbnail_pic_width").toString()).intValue();
/*  75 */         thumbnail_pic_height = Integer.valueOf(getSettingValue("thumbnail_pic_height").toString()).intValue();
/*     */ 
/*  77 */         detail_pic_width = Integer.valueOf(getSettingValue("detail_pic_width").toString()).intValue();
/*  78 */         detail_pic_height = Integer.valueOf(getSettingValue("detail_pic_height").toString()).intValue();
/*     */ 
/*  80 */         album_pic_width = Integer.valueOf(getSettingValue("album_pic_width").toString()).intValue();
/*  81 */         album_pic_height = Integer.valueOf(getSettingValue("album_pic_height").toString()).intValue();
/*     */       } catch (NumberFormatException e) {
/*  83 */         e.printStackTrace();
/*     */       }
/*     */ 
/*  86 */       String thumbName = null;
/*     */       try
/*     */       {
/*  91 */         thumbName = getThumbPath(filepath, "_thumbnail");
/*  92 */         this.goodsAlbumManager.createThumb(filepath, thumbName, thumbnail_pic_width, thumbnail_pic_height);
/*     */ 
/*  95 */         thumbName = getThumbPath(filepath, "_small");
/*  96 */         this.goodsAlbumManager.createThumb(filepath, thumbName, detail_pic_width, detail_pic_height);
/*     */ 
/* 100 */         thumbName = getThumbPath(filepath, "_big");
/* 101 */         this.goodsAlbumManager.createThumb(filepath, thumbName, album_pic_width, album_pic_height);
/*     */       }
/*     */       catch (RuntimeException e) {
/* 104 */         e.printStackTrace();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 112 */     imgsString = imgsString.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
/* 113 */     goods.put("image_file", imgsString);
/* 114 */     String image_default = (String)goods.get("image_default");
/* 115 */     if (image_default != null) {
/* 116 */       image_default = image_default.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
/* 117 */       goods.put("image_default", image_default);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getThumbPath(String filePath, String shortName) {
/* 122 */     return UploadUtil.getThumbPath(filePath, shortName);
/*     */   }
/*     */ 
/*     */   public Map<String, String> beforeSettingSave(HttpServletRequest request)
/*     */   {
/* 134 */     Map settingMap = new HashMap();
/*     */ 
/* 136 */     String thumbnail_pic_width = request.getParameter("photo.thumbnail_pic_width");
/* 137 */     String thumbnail_pic_height = request.getParameter("photo.thumbnail_pic_height");
/* 138 */     String detail_pic_width = request.getParameter("photo.detail_pic_width");
/* 139 */     String detail_pic_height = request.getParameter("photo.detail_pic_height");
/* 140 */     String album_pic_height = request.getParameter("photo.album_pic_height");
/* 141 */     String album_pic_width = request.getParameter("photo.album_pic_width");
/*     */ 
/* 143 */     settingMap.put("thumbnail_pic_width", thumbnail_pic_width);
/* 144 */     settingMap.put("thumbnail_pic_height", thumbnail_pic_height);
/*     */ 
/* 146 */     settingMap.put("detail_pic_width", detail_pic_width);
/* 147 */     settingMap.put("detail_pic_height", detail_pic_height);
/*     */ 
/* 149 */     settingMap.put("album_pic_height", album_pic_height);
/* 150 */     settingMap.put("album_pic_width", album_pic_width);
/*     */ 
/* 152 */     return settingMap;
/*     */   }
/*     */ 
/*     */   public void onGoodsDelete(Integer[] goodsid)
/*     */   {
/* 161 */     this.goodsAlbumManager.delete(goodsid);
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 169 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 173 */     return "album";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 178 */     return "jmagick商品相册插件";
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 182 */     return "";
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 186 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params) {
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService) {
/* 193 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager)
/*     */   {
/* 198 */     this.goodsAlbumManager = goodsAlbumManager;
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 204 */     return "相册";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 210 */     return 3;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.album.GalleryAdminPlugin
 * JD-Core Version:    0.6.0
 */