/*     */ package com.enation.app.shop.component.goodscore.plugin.album;
/*     */ 
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class DefaultAlbumPlugin extends AbstractGoodsPlugin
/*     */ {
/*     */   public void addTabs()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*  31 */     String[] picnames = request.getParameterValues("picnames");
/*  32 */     proessPhoto(picnames, goods);
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
/*  36 */     String[] picnames = request.getParameterValues("picnames");
/*  37 */     proessPhoto(picnames, goods);
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/*  48 */     String contextPath = request.getContextPath();
/*     */ 
/*  50 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  53 */     String image_default = null;
/*  54 */     if ((goods.get("image_file") != null) && (goods.get("image_file") != "")) {
/*  55 */       String image_file = goods.get("image_file").toString();
/*  56 */       String[] thumbnail_images = StringUtils.split(image_file, ",");
/*     */ 
/*  58 */       image_default = (String)goods.get("image_default");
/*  59 */       if (!StringUtil.isEmpty(image_default)) {
/*  60 */         image_default = UploadUtil.replacePath(image_default);
/*     */       }
/*     */ 
/*  64 */       freeMarkerPaser.putData("ctx", contextPath);
/*  65 */       freeMarkerPaser.putData("image_default", image_default);
/*  66 */       freeMarkerPaser.putData("thumbnail_images", thumbnail_images);
/*     */     }
/*     */ 
/*  69 */     freeMarkerPaser.setPageName("album");
/*  70 */     String html = freeMarkerPaser.proessPageContent();
/*  71 */     return html;
/*     */   }
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request) {
/*  75 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  76 */     freeMarkerPaser.setPageName("album");
/*  77 */     freeMarkerPaser.putData("image_default", null);
/*  78 */     freeMarkerPaser.putData("thumbnail_images", null);
/*  79 */     String html = freeMarkerPaser.proessPageContent();
/*  80 */     return html;
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void proessPhoto(String[] picnames, Map goods)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 101 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 105 */     return "default_album";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 110 */     return "默认商品相册插件";
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 114 */     return "";
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 118 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.album.DefaultAlbumPlugin
 * JD-Core Version:    0.6.0
 */