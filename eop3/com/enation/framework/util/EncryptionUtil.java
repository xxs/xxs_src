/*    */ package com.enation.framework.util;
/*    */ 
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ public class EncryptionUtil
/*    */ {
/* 16 */   public static String GLOBAL_AUTH_KEY = "e317b362fafa0c96c20b8543d054b850";
/*    */ 
/*    */   public static final String authCode(String str, String operation)
/*    */   {
/* 31 */     String key = GLOBAL_AUTH_KEY;
/* 32 */     String coded = "";
/* 33 */     int keylength = key.length();
/*    */     try {
/* 35 */       str = operation == "DECODE" ? new String(Base64.decodeBase64(str.getBytes())) : str;
/*    */ 
/* 38 */       byte[] codeList = new byte[str.getBytes().length];
/* 39 */       int index = 0;
/* 40 */       for (int i = 0; i < str.length(); i += keylength) {
/* 41 */         int tmp = i + keylength < str.length() ? i + keylength : str.length();
/*    */ 
/* 43 */         byte[] codedbyte = phpXor(str.substring(i, tmp), key);
/* 44 */         coded = coded + codedbyte;
/* 45 */         System.arraycopy(codedbyte, 0, codeList, index, codedbyte.length);
/*    */ 
/* 47 */         index = codedbyte.length;
/*    */       }
/* 49 */       coded = operation == "ENCODE" ? new String(Base64.encodeBase64(codeList)) : new String(codeList);
/*    */ 
/* 51 */       return coded;
/*    */     } catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   public static final byte[] phpXor(String a, String b)
/*    */   {
/*    */     try
/*    */     {
/* 71 */       byte[] as = a.getBytes();
/* 72 */       byte[] bs = b.getBytes();
/* 73 */       int len = 0;
/* 74 */       if (as.length > bs.length)
/* 75 */         len = bs.length;
/*    */       else {
/* 77 */         len = as.length;
/*    */       }
/* 79 */       byte[] cs = new byte[len];
/* 80 */       for (int i = 0; i < len; i++) {
/* 81 */         cs[i] = (byte)(as[i] ^ bs[i]);
/*    */       }
/* 83 */       return cs;
/*    */     } catch (Exception e) {
/* 85 */       e.printStackTrace();
/*    */     }
/* 87 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.EncryptionUtil
 * JD-Core Version:    0.6.0
 */