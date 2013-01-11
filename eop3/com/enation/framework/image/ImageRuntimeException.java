/*   */ package com.enation.framework.image;
/*   */ 
/*   */ public class ImageRuntimeException extends RuntimeException
/*   */ {
/*   */   public ImageRuntimeException(String path, String proesstype)
/*   */   {
/* 5 */     super("对图片" + path + "进行" + proesstype + "出错");
/*   */   }
/*   */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.image.ImageRuntimeException
 * JD-Core Version:    0.6.0
 */