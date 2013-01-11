/*     */ package com.enation.app.shop.component.goodscore.widget.goods.list;
/*     */ 
/*     */ public class Settings
/*     */ {
/*     */   private static final String OFF = "off";
/*     */   private static final String ON = "on";
/*     */   private String type;
/*     */   private String showGoodsDesc;
/*     */   private String showGoodsImg;
/*     */   private String showGoodsName;
/*     */   private String goodsImgPosition;
/*     */   private Integer goodsNum;
/*     */   private Integer columnnum;
/*     */   private Integer columnwidth;
/*     */   private String thumbnail_pic_width;
/*     */   private String thumbnail_pic_height;
/*     */   private String showTitleImg;
/*     */   private String showTitle;
/*     */   private String titleImgHref;
/*     */   private String titleImgSrc;
/*     */   private String titleImgPosition;
/*     */   private String titleImgAlt;
/*     */   private String titleDesc;
/*     */   private String titleImgWidth;
/*     */   private Integer changeEffect;
/*     */ 
/*     */   public Settings()
/*     */   {
/*  16 */     this.goodsNum = Integer.valueOf(10);
/*  17 */     this.columnnum = Integer.valueOf(4);
/*  18 */     this.goodsImgPosition = "top";
/*  19 */     this.titleImgPosition = "left";
/*     */ 
/*  21 */     this.showGoodsImg = "on";
/*  22 */     this.showGoodsName = "on";
/*  23 */     this.showGoodsDesc = "on";
/*  24 */     this.showTitleImg = "off";
/*  25 */     this.showTitle = "off";
/*     */ 
/*  27 */     this.titleImgSrc = "";
/*  28 */     this.titleImgAlt = "";
/*  29 */     this.titleImgHref = "";
/*  30 */     this.titleImgWidth = "100%";
/*     */   }
/*     */ 
/*     */   public String getShowTitle()
/*     */   {
/*  59 */     return this.showTitle;
/*     */   }
/*     */   public void setShowTitle(String showTitle) {
/*  62 */     this.showTitle = showTitle;
/*     */   }
/*     */   public String getShowGoodsDesc() {
/*  65 */     return this.showGoodsDesc;
/*     */   }
/*     */   public void setShowGoodsDesc(String showGoodsDesc) {
/*  68 */     this.showGoodsDesc = showGoodsDesc;
/*     */   }
/*     */   public String getShowGoodsImg() {
/*  71 */     return this.showGoodsImg;
/*     */   }
/*     */   public void setShowGoodsImg(String showGoodsImg) {
/*  74 */     this.showGoodsImg = showGoodsImg;
/*     */   }
/*     */   public String getShowGoodsName() {
/*  77 */     return this.showGoodsName;
/*     */   }
/*     */   public void setShowGoodsName(String showGoodsName) {
/*  80 */     this.showGoodsName = showGoodsName;
/*     */   }
/*     */   public String getGoodsImgPosition() {
/*  83 */     return this.goodsImgPosition;
/*     */   }
/*     */   public void setGoodsImgPosition(String goodsImgPosition) {
/*  86 */     this.goodsImgPosition = goodsImgPosition;
/*     */   }
/*     */   public Integer getColumnnum() {
/*  89 */     return this.columnnum;
/*     */   }
/*     */   public void setColumnnum(Integer columnnum) {
/*  92 */     this.columnnum = columnnum;
/*     */   }
/*     */   public Integer getColumnwidth() {
/*  95 */     return this.columnwidth;
/*     */   }
/*     */   public void setColumnwidth(Integer columnwidth) {
/*  98 */     this.columnwidth = columnwidth;
/*     */   }
/*     */ 
/*     */   public String getTitleImgHref()
/*     */   {
/* 103 */     return this.titleImgHref;
/*     */   }
/*     */   public void setTitleImgHref(String titleImgHref) {
/* 106 */     this.titleImgHref = titleImgHref;
/*     */   }
/*     */   public String getTitleImgSrc() {
/* 109 */     return this.titleImgSrc;
/*     */   }
/*     */   public void setTitleImgSrc(String titleImgSrc) {
/* 112 */     this.titleImgSrc = titleImgSrc;
/*     */   }
/*     */   public String getTitleImgPosition() {
/* 115 */     return this.titleImgPosition;
/*     */   }
/*     */   public void setTitleImgPosition(String titleImgPosition) {
/* 118 */     this.titleImgPosition = titleImgPosition;
/*     */   }
/*     */   public String getTitleImgAlt() {
/* 121 */     return this.titleImgAlt;
/*     */   }
/*     */   public void setTitleImgAlt(String titleImgAlt) {
/* 124 */     this.titleImgAlt = titleImgAlt;
/*     */   }
/*     */   public String getShowTitleImg() {
/* 127 */     return this.showTitleImg;
/*     */   }
/*     */   public void setShowTitleImg(String showTitleImg) {
/* 130 */     this.showTitleImg = showTitleImg;
/*     */   }
/*     */   public Integer getGoodsNum() {
/* 133 */     return this.goodsNum;
/*     */   }
/*     */   public void setGoodsNum(Integer goodsNum) {
/* 136 */     this.goodsNum = goodsNum;
/*     */   }
/*     */   public String getTitleDesc() {
/* 139 */     return this.titleDesc;
/*     */   }
/*     */   public void setTitleDesc(String titleDesc) {
/* 142 */     this.titleDesc = titleDesc;
/*     */   }
/*     */   public String getThumbnail_pic_width() {
/* 145 */     return this.thumbnail_pic_width;
/*     */   }
/*     */   public void setThumbnail_pic_width(String thumbnailPicWidth) {
/* 148 */     this.thumbnail_pic_width = thumbnailPicWidth;
/*     */   }
/*     */   public String getThumbnail_pic_height() {
/* 151 */     return this.thumbnail_pic_height;
/*     */   }
/*     */   public void setThumbnail_pic_height(String thumbnailPicHeight) {
/* 154 */     this.thumbnail_pic_height = thumbnailPicHeight;
/*     */   }
/*     */   public String getTitleImgWidth() {
/* 157 */     return this.titleImgWidth;
/*     */   }
/*     */   public void setTitleImgWidth(String titleImgWidth) {
/* 160 */     this.titleImgWidth = titleImgWidth;
/*     */   }
/*     */   public Integer getChangeEffect() {
/* 163 */     return this.changeEffect;
/*     */   }
/*     */   public void setChangeEffect(Integer changeEffect) {
/* 166 */     this.changeEffect = changeEffect;
/*     */   }
/*     */   public String getType() {
/* 169 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/* 172 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.list.Settings
 * JD-Core Version:    0.6.0
 */