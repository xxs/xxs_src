/*    */ package com.enation.framework.util.ip;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class IpUtil
/*    */ {
/*    */   public static byte[] getIpByteArrayFromString(String ip)
/*    */   {
/* 18 */     byte[] ret = new byte[4];
/* 19 */     StringTokenizer st = new StringTokenizer(ip, ".");
/*    */     try {
/* 21 */       ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
/* 22 */       ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
/* 23 */       ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
/* 24 */       ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
/*    */     } catch (Exception e) {
/* 26 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 29 */     return ret;
/*    */   }
/*    */ 
/*    */   public static String getIpStringFromBytes(byte[] ip)
/*    */   {
/* 36 */     StringBuilder sb = new StringBuilder();
/* 37 */     sb.append(ip[0] & 0xFF);
/* 38 */     sb.append('.');
/* 39 */     sb.append(ip[1] & 0xFF);
/* 40 */     sb.append('.');
/* 41 */     sb.append(ip[2] & 0xFF);
/* 42 */     sb.append('.');
/* 43 */     sb.append(ip[3] & 0xFF);
/* 44 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static String getString(byte[] b, int offset, int len, String encoding)
/*    */   {
/*    */     try
/*    */     {
/* 57 */       return new String(b, offset, len, encoding); } catch (UnsupportedEncodingException e) {
/*    */     }
/* 59 */     return new String(b, offset, len);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ip.IpUtil
 * JD-Core Version:    0.6.0
 */