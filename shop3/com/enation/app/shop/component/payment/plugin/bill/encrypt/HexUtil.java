/*    */ package com.enation.app.shop.component.payment.plugin.bill.encrypt;
/*    */ 
/*    */ public class HexUtil
/*    */ {
/*    */   private static final String HEX_CHARS = "0123456789abcdef";
/*    */ 
/*    */   public static String toHexString(byte[] b)
/*    */   {
/* 23 */     StringBuffer sb = new StringBuffer();
/* 24 */     for (int i = 0; i < b.length; i++) {
/* 25 */       sb.append("0123456789abcdef".charAt(b[i] >>> 4 & 0xF));
/* 26 */       sb.append("0123456789abcdef".charAt(b[i] & 0xF));
/*    */     }
/* 28 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static byte[] toByteArray(String s)
/*    */   {
/* 39 */     byte[] buf = new byte[s.length() / 2];
/* 40 */     int j = 0;
/* 41 */     for (int i = 0; i < buf.length; i++) {
/* 42 */       buf[i] = (byte)(Character.digit(s.charAt(j++), 16) << 4 | Character.digit(s.charAt(j++), 16));
/*    */     }
/*    */ 
/* 45 */     return buf;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.bill.encrypt.HexUtil
 * JD-Core Version:    0.6.0
 */