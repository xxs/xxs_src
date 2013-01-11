/*    */ package com.enation.app.shop.component.goodscore.widget.gallery;
/*    */ 
/*    */ import com.enation.app.base.core.service.ISettingService;
/*    */ import com.enation.app.shop.component.goodscore.widget.goods.AbstractGoodsDetailWidget;
/*    */ import com.enation.app.shop.core.model.Gallery;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_gallery")
/*    */ @Scope("prototype")
/*    */ public class GoodsGalleryWidget2 extends AbstractGoodsDetailWidget
/*    */ {
/*    */   private ISettingService settingService;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void execute(Map<String, String> params, Map goods)
/*    */   {
/* 35 */     String image_file = goods.get("image_file") == null ? "" : goods.get("image_file").toString();
/* 36 */     String default_img = (String)goods.get("image_default");
/* 37 */     int defaultindex = 0;
/* 38 */     List galleryList = new ArrayList();
/* 39 */     if ((image_file != null) && (!image_file.equals("")))
/*    */     {
/* 41 */       String[] imgs = StringUtils.split(image_file, ",");
/* 42 */       int i = 0;
/* 43 */       for (String img : imgs)
/*    */       {
/* 45 */         Gallery gallery = new Gallery();
/* 46 */         gallery.setSmall(UploadUtil.getThumbPath(img, "_small"));
/* 47 */         gallery.setBig(UploadUtil.getThumbPath(img, "_big"));
/* 48 */         gallery.setThumbnail(UploadUtil.getThumbPath(img, "_thumbnail"));
/* 49 */         galleryList.add(gallery);
/* 50 */         if (img.equals(default_img)) {
/* 51 */           defaultindex = i;
/*    */         }
/* 53 */         i++;
/*    */       }
/*    */     }
/*    */     else {
/* 57 */       String img = EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/* 58 */       Gallery gallery = new Gallery();
/* 59 */       gallery.setSmall(UploadUtil.getThumbPath(img, "_small"));
/* 60 */       gallery.setBig(img);
/* 61 */       gallery.setThumbnail(UploadUtil.getThumbPath(img, "_thumbnail"));
/* 62 */       galleryList.add(gallery);
/*    */     }
/*    */ 
/* 65 */     String album_pic_width = this.settingService.getSetting("photo", "detail_pic_width");
/* 66 */     String album_pic_height = this.settingService.getSetting("photo", "detail_pic_height");
/*    */ 
/* 68 */     putData("album_pic_width", album_pic_width);
/* 69 */     putData("album_pic_height", album_pic_height);
/*    */ 
/* 71 */     putData("galleryList", galleryList);
/*    */ 
/* 73 */     putData("GoodsPic", new ImageUrlDirectiveModel());
/* 74 */     putData("defaultindex", Integer.valueOf(defaultindex));
/* 75 */     putData("image_default", default_img);
/*    */   }
/*    */ 
/*    */   public ISettingService getSettingService()
/*    */   {
/* 82 */     return this.settingService;
/*    */   }
/*    */ 
/*    */   public void setSettingService(ISettingService settingService) {
/* 86 */     this.settingService = settingService;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.gallery.GoodsGalleryWidget2
 * JD-Core Version:    0.6.0
 */