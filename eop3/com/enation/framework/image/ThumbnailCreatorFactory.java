/*    */ package com.enation.framework.image;
/*    */ 
/*    */ import com.enation.framework.image.impl.ImageMagickCreator;
/*    */ import com.enation.framework.image.impl.JavaImageIOCreator;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public abstract class ThumbnailCreatorFactory
/*    */ {
/* 16 */   public static String CREATORTYPE = "javaimageio";
/*    */ 
/*    */   public static final IThumbnailCreator getCreator(String source, String target)
/*    */   {
/* 27 */     if (CREATORTYPE.equals("javaimageio")) {
/* 28 */       return new JavaImageIOCreator(source, target);
/*    */     }
/*    */ 
/* 31 */     if (CREATORTYPE.equals("imagemagick")) {
/*    */       try {
/* 33 */         return new ImageMagickCreator(source, target);
/*    */       }
/*    */       catch (IOException e) {
/* 36 */         e.printStackTrace();
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 41 */     return new JavaImageIOCreator(source, target);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.image.ThumbnailCreatorFactory
 * JD-Core Version:    0.6.0
 */