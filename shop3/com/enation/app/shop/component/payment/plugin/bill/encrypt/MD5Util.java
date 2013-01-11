/*    */ package com.enation.app.shop.component.payment.plugin.bill.encrypt;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ public class MD5Util
/*    */ {
/*    */   static MessageDigest getDigest()
/*    */   {
/*    */     try
/*    */     {
/* 30 */       return MessageDigest.getInstance("MD5"); } catch (NoSuchAlgorithmException e) {
/*    */     }
/* 32 */     throw new RuntimeException(e);
/*    */   }
/*    */ 
/*    */   public static byte[] md5(byte[] data)
/*    */   {
/* 45 */     return getDigest().digest(data);
/*    */   }
/*    */ 
/*    */   public static byte[] md5(String data)
/*    */   {
/* 57 */     return md5(data.getBytes());
/*    */   }
/*    */ 
/*    */   public static String md5Hex(byte[] data)
/*    */   {
/* 69 */     return HexUtil.toHexString(md5(data));
/*    */   }
/*    */ 
/*    */   public static String md5Hex(String data)
/*    */   {
/* 81 */     return HexUtil.toHexString(md5(data));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.bill.encrypt.MD5Util
 * JD-Core Version:    0.6.0
 */