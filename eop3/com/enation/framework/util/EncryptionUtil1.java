/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.net.URLEncoder;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ 
/*     */ public class EncryptionUtil1
/*     */ {
/*  16 */   public static String GLOBAL_AUTH_KEY = "e317b362fafa0c96c20b8543d054b850";
/*     */ 
/*     */   public static String authcode(String $string, String $operation, String $key, int $expiry)
/*     */   {
/*  37 */     if (($string != null) && 
/*  38 */       ($operation.equals("DECODE")))
/*     */       try {
/*  40 */         $string = $string.replaceAll("\\.0\\.", " ");
/*  41 */         $string = $string.replaceAll("\\.1\\.", "=");
/*  42 */         $string = $string.replaceAll("\\.2\\.", "+");
/*  43 */         $string = $string.replaceAll("\\.3\\.", "/");
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*  48 */     int $ckey_length = 4;
/*     */ 
/*  53 */     $key = md5($key != null ? $key : GLOBAL_AUTH_KEY);
/*  54 */     String $keya = md5(substr($key, 0, 16));
/*  55 */     String $keyb = md5(substr($key, 16, 16));
/*  56 */     String $keyc = $ckey_length > 0 ? substr(md5(microtime()), -$ckey_length) : $operation.equals("DECODE") ? substr($string, 0, $ckey_length) : "";
/*     */ 
/*  58 */     String $cryptkey = new StringBuilder().append($keya).append(md5(new StringBuilder().append($keya).append($keyc).toString())).toString();
/*  59 */     int $key_length = $cryptkey.length();
/*     */ 
/*  61 */     $string = $operation.equals("DECODE") ? base64_decode(substr($string, $ckey_length)) : new StringBuilder().append(sprintf("%010d", $expiry > 0 ? $expiry + time() : 0L)).append(substr(md5(new StringBuilder().append($string).append($keyb).toString()), 0, 16)).append($string).toString();
/*  62 */     int $string_length = $string.length();
/*     */ 
/*  64 */     StringBuffer $result1 = new StringBuffer();
/*     */ 
/*  66 */     int[] $box = new int[256];
/*  67 */     for (int i = 0; i < 256; i++) {
/*  68 */       $box[i] = i;
/*     */     }
/*     */ 
/*  71 */     int[] $rndkey = new int[256];
/*  72 */     for (int $i = 0; $i <= 255; $i++) {
/*  73 */       $rndkey[$i] = $cryptkey.charAt($i % $key_length);
/*     */     }
/*     */ 
/*  76 */     int $j = 0;
/*  77 */     for (int $i = 0; $i < 256; $i++) {
/*  78 */       $j = ($j + $box[$i] + $rndkey[$i]) % 256;
/*  79 */       int $tmp = $box[$i];
/*  80 */       $box[$i] = $box[$j];
/*  81 */       $box[$j] = $tmp;
/*     */     }
/*     */ 
/*  84 */     $j = 0;
/*  85 */     int $a = 0;
/*  86 */     for (int $i = 0; $i < $string_length; $i++) {
/*  87 */       $a = ($a + 1) % 256;
/*  88 */       $j = ($j + $box[$a]) % 256;
/*  89 */       int $tmp = $box[$a];
/*  90 */       $box[$a] = $box[$j];
/*  91 */       $box[$j] = $tmp;
/*     */ 
/*  93 */       $result1.append((char)($string.charAt($i) ^ $box[(($box[$a] + $box[$j]) % 256)]));
/*     */     }
/*     */ 
/*  97 */     if ($operation.equals("DECODE")) {
/*  98 */       String $result = $result1.substring(0, $result1.length());
/*  99 */       if (((Integer.parseInt(substr($result.toString(), 0, 10)) == 0) || (Long.parseLong(substr($result.toString(), 0, 10)) - time() > 0L)) && (substr($result.toString(), 10, 16).equals(substr(md5(new StringBuilder().append(substr($result.toString(), 26)).append($keyb).toString()), 0, 16)))) {
/* 100 */         return substr($result.toString(), 26);
/*     */       }
/* 102 */       return "";
/*     */     }
/*     */ 
/* 105 */     String str = new StringBuilder().append($keyc).append(base64_encode($result1.toString())).toString();
/*     */     try {
/* 107 */       str = str.replaceAll(" ", ".0.");
/* 108 */       str = str.replaceAll("=", ".1.");
/* 109 */       str = str.replaceAll("\\+", ".2.");
/* 110 */       str = str.replaceAll("\\/", ".3.");
/*     */     } catch (Exception ex) {
/*     */     }
/* 113 */     return str;
/*     */   }
/*     */ 
/*     */   private static String urlencode(String value)
/*     */   {
/* 118 */     return URLEncoder.encode(value);
/*     */   }
/*     */   private static String md5(String input) {
/*     */     MessageDigest md;
/*     */     try { md = MessageDigest.getInstance("MD5");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 125 */       e.printStackTrace();
/* 126 */       return null;
/*     */     }
/* 128 */     return byte2hex(md.digest(input.getBytes()));
/*     */   }
/*     */   private static String md5(long input) {
/* 131 */     return md5(String.valueOf(input));
/*     */   }
/*     */ 
/*     */   private static String base64_decode(String input) {
/*     */     try {
/* 136 */       return new String(Base64.decode(input.toCharArray()), "iso-8859-1"); } catch (Exception e) {
/*     */     }
/* 138 */     return e.getMessage();
/*     */   }
/*     */ 
/*     */   private static String base64_encode(String input)
/*     */   {
/*     */     try {
/* 144 */       return new String(Base64.encode(input.getBytes("iso-8859-1"))); } catch (Exception e) {
/*     */     }
/* 146 */     return e.getMessage();
/*     */   }
/*     */ 
/*     */   private static String byte2hex(byte[] b) {
/* 150 */     StringBuffer hs = new StringBuffer();
/* 151 */     String stmp = "";
/* 152 */     for (int n = 0; n < b.length; n++) {
/* 153 */       stmp = Integer.toHexString(b[n] & 0xFF);
/* 154 */       if (stmp.length() == 1)
/* 155 */         hs.append("0").append(stmp);
/*     */       else
/* 157 */         hs.append(stmp);
/*     */     }
/* 159 */     return hs.toString();
/*     */   }
/*     */   private static String substr(String input, int begin, int length) {
/* 162 */     return input.substring(begin, begin + length);
/*     */   }
/*     */   private static String substr(String input, int begin) {
/* 165 */     if (begin > 0) {
/* 166 */       return input.substring(begin);
/*     */     }
/* 168 */     return input.substring(input.length() + begin);
/*     */   }
/*     */ 
/*     */   private static long microtime() {
/* 172 */     return System.currentTimeMillis();
/*     */   }
/*     */   private static long time() {
/* 175 */     return System.currentTimeMillis() / 1000L;
/*     */   }
/*     */   private static String sprintf(String format, long input) {
/* 178 */     String temp = new StringBuilder().append("0000000000").append(input).toString();
/* 179 */     return temp.substring(temp.length() - 10);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 191 */     System.out.println(authcode("9,1319258194668", "ENCODE", "", 0));
/* 192 */     System.out.println(authcode("0fb7Ys0fSwwMXGtXZhtVN9GqUQ7z7r.3.nlfiTBCPSSd.2.d1MmDRFCq8AaOh5I.1.", "DECODE", "", 0));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.EncryptionUtil1
 * JD-Core Version:    0.6.0
 */