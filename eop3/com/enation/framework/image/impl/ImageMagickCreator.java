/*     */ package com.enation.framework.image.impl;
/*     */ 
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ImageRuntimeException;
/*     */ import java.awt.Dimension;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import magick.ImageInfo;
/*     */ import magick.MagickApiException;
/*     */ import magick.MagickException;
/*     */ import magick.MagickImage;
/*     */ 
/*     */ public class ImageMagickCreator
/*     */   implements IThumbnailCreator
/*     */ {
/*     */   private String source;
/*     */   private String target;
/*     */   private ImageInfo info;
/*     */   private MagickImage image;
/*     */   private double width;
/*     */   private double height;
/*     */ 
/*     */   public ImageMagickCreator(String _source, String _target)
/*     */     throws IOException
/*     */   {
/*  40 */     this.source = _source;
/*  41 */     this.target = _target;
/*  42 */     File f = new File(_source);
/*  43 */     FileInputStream fis = new FileInputStream(f);
/*     */     try
/*     */     {
/*  48 */       byte[] b = new byte[(int)f.length()];
/*  49 */       fis.read(b);
/*     */ 
/*  51 */       this.info = new ImageInfo();
/*  52 */       this.image = new MagickImage(this.info, b);
/*     */ 
/*  55 */       this.width = this.image.getDimension().getWidth();
/*  56 */       this.height = this.image.getDimension().getHeight();
/*     */     }
/*     */     catch (Exception e) {
/*  59 */       e.printStackTrace();
/*  60 */       throw new ImageRuntimeException(this.source, "构造jmagickutils");
/*     */     } finally {
/*  62 */       if (fis != null)
/*  63 */         fis.close();
/*  64 */       fis = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resize(int w, int h)
/*     */   {
/*  72 */     int x = 0; int y = 0;
/*  73 */     x = y = 0;
/*  74 */     int target_w = w;
/*  75 */     int target_h = h;
/*     */ 
/*  77 */     MagickImage scaled = null;
/*     */     try
/*     */     {
/*  81 */       if (this.width / this.height > w / h) {
/*  82 */         target_w = w;
/*  83 */         target_h = (int)(target_w * this.height / this.width);
/*  84 */         x = 0;
/*  85 */         y = (h - target_h) / 2;
/*     */       }
/*     */ 
/*  89 */       if (this.width / this.height < w / h) {
/*  90 */         target_h = h;
/*  91 */         target_w = (int)(target_h * this.width / this.height);
/*  92 */         y = 0;
/*  93 */         x = (w - target_w) / 2;
/*     */       }
/*  95 */       MagickImage thumb_img = this.image.scaleImage(target_w, target_h);
/*  96 */       MagickImage blankImage = new MagickImage();
/*     */ 
/*  98 */       byte[] pixels = new byte[w * h * 4];
/*  99 */       for (int i = 0; i < w * h; i++) {
/* 100 */         pixels[(4 * i)] = -1;
/* 101 */         pixels[(4 * i + 1)] = -1;
/* 102 */         pixels[(4 * i + 2)] = -1;
/* 103 */         pixels[(4 * i + 3)] = -1;
/*     */       }
/*     */ 
/* 106 */       blankImage.constituteImage(w, h, "RGBA", pixels);
/* 107 */       blankImage.compositeImage(3, thumb_img, x, y);
/*     */ 
/* 109 */       blankImage.setFileName(this.target);
/* 110 */       blankImage.writeImage(this.info);
/*     */     }
/*     */     catch (MagickApiException ex) {
/* 113 */       ex.printStackTrace();
/* 114 */       throw new ImageRuntimeException(this.source, "生成缩略图");
/*     */     } catch (MagickException ex) {
/* 116 */       ex.printStackTrace();
/* 117 */       throw new ImageRuntimeException(this.source, "生成缩略图");
/*     */     } finally {
/* 119 */       if (scaled != null) {
/* 120 */         scaled.destroyImages();
/*     */       }
/* 122 */       if (this.image != null)
/* 123 */         this.image.destroyImages();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 132 */     ImageMagickCreator creator = new ImageMagickCreator("d:/1.jpg", "d:/2.jpg");
/* 133 */     creator.resize(200, 200);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  24 */     System.setProperty("jmagick.systemclassloader", "no");
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.image.impl.ImageMagickCreator
 * JD-Core Version:    0.6.0
 */