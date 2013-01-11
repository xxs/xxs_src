/*     */ package com.enation.framework.image.impl;
/*     */ 
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ImageRuntimeException;
/*     */ import com.sun.image.codec.jpeg.ImageFormatException;
/*     */ import com.sun.image.codec.jpeg.JPEGCodec;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ public class JavaImageIOCreator
/*     */   implements IThumbnailCreator
/*     */ {
/*     */   private String srcFile;
/*     */   private String destFile;
/*     */   private int width;
/*     */   private int height;
/*     */   private Image img;
/*     */ 
/*     */   public JavaImageIOCreator(String sourcefile, String targetFile)
/*     */   {
/*  36 */     File _file = new File(sourcefile);
/*  37 */     this.srcFile = _file.getName();
/*  38 */     this.destFile = targetFile;
/*     */     try {
/*  40 */       this.img = ImageIO.read(_file);
/*     */     } catch (IOException e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*  44 */     this.width = this.img.getWidth(null);
/*  45 */     this.height = this.img.getHeight(null);
/*     */   }
/*     */ 
/*     */   public void resize(int w, int h)
/*     */   {
/*  52 */     int x = 0; int y = 0;
/*  53 */     x = y = 0;
/*  54 */     int target_w = w;
/*  55 */     int target_h = h;
/*     */ 
/*  58 */     if (this.width / this.height > w / h) {
/*  59 */       target_w = w;
/*  60 */       target_h = target_w * this.height / this.width;
/*  61 */       x = 0;
/*  62 */       y = (h - target_h) / 2;
/*     */     }
/*     */ 
/*  66 */     if (this.width / this.height < w / h) {
/*  67 */       target_h = h;
/*  68 */       target_w = target_h * this.width / this.height;
/*  69 */       y = 0;
/*  70 */       x = (w - target_w) / 2;
/*     */     }
/*     */ 
/*  75 */     BufferedImage _image = new BufferedImage(w, h, 1);
/*     */ 
/*  77 */     Graphics graphics = _image.getGraphics();
/*  78 */     graphics.setColor(Color.white);
/*  79 */     graphics.fillRect(0, 0, _image.getWidth(), _image.getHeight());
/*  80 */     graphics.drawImage(this.img, x, y, target_w, target_h, null);
/*     */     try
/*     */     {
/*  83 */       FileOutputStream out = new FileOutputStream(this.destFile);
/*  84 */       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
/*  85 */       encoder.encode(_image);
/*  86 */       out.close();
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  89 */       e.printStackTrace();
/*  90 */       throw new ImageRuntimeException(this.srcFile, "生成缩略图");
/*     */     }
/*     */     catch (ImageFormatException e) {
/*  93 */       e.printStackTrace();
/*  94 */       throw new ImageRuntimeException(this.srcFile, "生成缩略图");
/*     */     } catch (IOException e) {
/*  96 */       e.printStackTrace();
/*  97 */       throw new ImageRuntimeException(this.srcFile, "生成缩略图");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 103 */     JavaImageIOCreator creator = new JavaImageIOCreator("e:/IMG_1068.JPG", "e:/IMG_1068_new.JPG");
/* 104 */     creator.resize(800, 800);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.image.impl.JavaImageIOCreator
 * JD-Core Version:    0.6.0
 */