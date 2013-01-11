/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.io.PrintStream;
/*     */ import magick.DrawInfo;
/*     */ import magick.ImageInfo;
/*     */ import magick.MagickException;
/*     */ import magick.MagickImage;
/*     */ import magick.PixelPacket;
/*     */ 
/*     */ public class ImageMagickMaskUtil
/*     */ {
/*     */   public void mask(String source, String text, String fontcolor, int fontsize, int pos, String fontPath)
/*     */   {
/*     */     try
/*     */     {
/*  33 */       System.out.println(source);
/*  34 */       ImageInfo info = new ImageInfo(source);
/*  35 */       MagickImage aImage = new MagickImage(info);
/*     */ 
/*  37 */       Double width = Double.valueOf(aImage.getDimension().getWidth());
/*  38 */       Double height = Double.valueOf(aImage.getDimension().getHeight());
/*     */ 
/*  40 */       DrawInfo aInfo = new DrawInfo(info);
/*     */ 
/*  42 */       aInfo.setFill(PixelPacket.queryColorDatabase(fontcolor));
/*  43 */       aInfo.setOpacity(0);
/*  44 */       aInfo.setPointsize(fontsize);
/*     */ 
/*  46 */       if (fontPath != null)
/*  47 */         aInfo.setFont(fontPath);
/*  48 */       Font f = new Font("宋体", 1, fontsize);
/*     */ 
/*  50 */       aInfo.setTextAntialias(true);
/*  51 */       aInfo.setText(text);
/*     */ 
/*  55 */       int textwidth = 100;
/*  56 */       int textheight = 20;
/*  57 */       int[] xy = getXy(width.intValue(), height.intValue(), textwidth, textheight, pos);
/*  58 */       aInfo.setGeometry("+" + xy[0] + "+" + xy[1]);
/*     */ 
/*  60 */       aImage.annotateImage(aInfo);
/*     */ 
/*  62 */       aImage.setFileName(source);
/*  63 */       aImage.writeImage(info);
/*  64 */       aImage.destroyImages();
/*  65 */       aImage = null;
/*     */     } catch (MagickException e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private int[] getXy(int width, int height, int textwidth, int textheight, int pos)
/*     */   {
/*  73 */     System.out.println("width[" + width + "]height[" + height + "]textwidth[" + textwidth + "]");
/*  74 */     int x = 0; int y = 0;
/*  75 */     int margin = 20;
/*  76 */     switch (pos) {
/*     */     case 1:
/*  78 */       x = margin;
/*  79 */       y = margin;
/*  80 */       break;
/*     */     case 2:
/*  82 */       y = margin;
/*  83 */       x = width / 2 - textwidth / 2;
/*  84 */       break;
/*     */     case 3:
/*  86 */       y = margin;
/*  87 */       x = width - margin - textwidth;
/*  88 */       break;
/*     */     case 4:
/*  90 */       y = height / 2 - textheight / 2;
/*  91 */       x = margin;
/*  92 */       break;
/*     */     case 5:
/*  94 */       y = height / 2 - textheight / 2;
/*  95 */       x = width / 2 - textwidth / 2;
/*  96 */       break;
/*     */     case 6:
/*  98 */       y = height / 2 - textheight / 2;
/*  99 */       x = width - margin - textwidth;
/* 100 */       break;
/*     */     case 7:
/* 102 */       x = margin;
/* 103 */       y = height - textheight;
/* 104 */       break;
/*     */     case 8:
/* 106 */       x = width / 2 - textwidth / 2;
/* 107 */       y = height - textheight;
/* 108 */       break;
/*     */     case 9:
/* 110 */       x = width - margin - textwidth;
/* 111 */       y = height - textheight;
/* 112 */       break;
/*     */     }
/*     */ 
/* 116 */     System.out.println("x[" + x + "]y[" + y + "]");
/* 117 */     return new int[] { x, y };
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 121 */     ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
/* 122 */     magickMask.mask("d:/temp.jpg", "易族智汇", "#000000", 16, 5, "e:/st.TTF");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  19 */     System.setProperty("jmagick.systemclassloader", "no");
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ImageMagickMaskUtil
 * JD-Core Version:    0.6.0
 */