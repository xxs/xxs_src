/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class Base64
/*     */ {
/* 151 */   private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
/*     */ 
/* 157 */   private static byte[] codes = new byte[256];
/*     */ 
/*     */   public static char[] encode(byte[] data)
/*     */   {
/*  40 */     char[] out = new char[(data.length + 2) / 3 * 4];
/*     */ 
/*  46 */     int i = 0; for (int index = 0; i < data.length; index += 4) {
/*  47 */       boolean quad = false;
/*  48 */       boolean trip = false;
/*     */ 
/*  50 */       int val = 0xFF & data[i];
/*  51 */       val <<= 8;
/*  52 */       if (i + 1 < data.length) {
/*  53 */         val |= 0xFF & data[(i + 1)];
/*  54 */         trip = true;
/*     */       }
/*  56 */       val <<= 8;
/*  57 */       if (i + 2 < data.length) {
/*  58 */         val |= 0xFF & data[(i + 2)];
/*  59 */         quad = true;
/*     */       }
/*  61 */       out[(index + 3)] = alphabet[64];
/*  62 */       val >>= 6;
/*  63 */       out[(index + 2)] = alphabet[64];
/*  64 */       val >>= 6;
/*  65 */       out[(index + 1)] = alphabet[(val & 0x3F)];
/*  66 */       val >>= 6;
/*  67 */       out[(index + 0)] = alphabet[(val & 0x3F)];
/*     */ 
/*  46 */       i += 3;
/*     */     }
/*     */ 
/*  69 */     return out;
/*     */   }
/*     */ 
/*     */   public static byte[] decode(char[] data)
/*     */   {
/*  91 */     int tempLen = data.length;
/*  92 */     for (int ix = 0; ix < data.length; ix++) {
/*  93 */       if ((data[ix] > 'ÿ') || (codes[data[ix]] < 0)) {
/*  94 */         tempLen--;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     int len = tempLen / 4 * 3;
/* 102 */     if (tempLen % 4 == 3)
/* 103 */       len += 2;
/* 104 */     if (tempLen % 4 == 2) {
/* 105 */       len++;
/*     */     }
/* 107 */     byte[] out = new byte[len];
/*     */ 
/* 110 */     int shift = 0;
/* 111 */     int accum = 0;
/* 112 */     int index = 0;
/*     */ 
/* 115 */     for (int ix = 0; ix < data.length; ix++) {
/* 116 */       int value = data[ix] > 'ÿ' ? -1 : codes[data[ix]];
/*     */ 
/* 118 */       if (value < 0)
/*     */         continue;
/* 120 */       accum <<= 6;
/* 121 */       shift += 6;
/* 122 */       accum |= value;
/* 123 */       if (shift < 8)
/*     */         continue;
/* 125 */       shift -= 8;
/* 126 */       out[(index++)] = (byte)(accum >> shift & 0xFF);
/*     */     }
/*     */ 
/* 139 */     if (index != out.length) {
/* 140 */       throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
/*     */     }
/*     */ 
/* 144 */     return out;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 181 */     boolean decode = false;
/*     */ 
/* 183 */     if (args.length == 0) {
/* 184 */       System.out.println("usage:  java Base64 [-d[ecode]] filename");
/* 185 */       System.exit(0);
/*     */     }
/* 187 */     for (int i = 0; i < args.length; i++) {
/* 188 */       if ("-decode".equalsIgnoreCase(args[i]))
/* 189 */         decode = true;
/* 190 */       else if ("-d".equalsIgnoreCase(args[i])) {
/* 191 */         decode = true;
/*     */       }
/*     */     }
/* 194 */     String filename = args[(args.length - 1)];
/* 195 */     File file = new File(filename);
/* 196 */     if (!file.exists()) {
/* 197 */       System.out.println("Error:  file '" + filename + "' doesn't exist!");
/* 198 */       System.exit(0);
/*     */     }
/*     */ 
/* 201 */     if (decode) {
/* 202 */       char[] encoded = readChars(file);
/* 203 */       byte[] decoded = decode(encoded);
/* 204 */       writeBytes(file, decoded);
/*     */     }
/*     */     else {
/* 207 */       byte[] decoded = readBytes(file);
/* 208 */       char[] encoded = encode(decoded);
/* 209 */       writeChars(file, encoded);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static byte[] readBytes(File file) {
/* 214 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/* 216 */       InputStream fis = new FileInputStream(file);
/* 217 */       InputStream is = new BufferedInputStream(fis);
/* 218 */       int count = 0;
/* 219 */       byte[] buf = new byte[16384];
/* 220 */       while ((count = is.read(buf)) != -1) {
/* 221 */         if (count > 0)
/* 222 */           baos.write(buf, 0, count);
/*     */       }
/* 224 */       is.close();
/*     */     }
/*     */     catch (Exception e) {
/* 227 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 230 */     return baos.toByteArray();
/*     */   }
/*     */ 
/*     */   private static char[] readChars(File file) {
/* 234 */     CharArrayWriter caw = new CharArrayWriter();
/*     */     try {
/* 236 */       Reader fr = new FileReader(file);
/* 237 */       Reader in = new BufferedReader(fr);
/* 238 */       int count = 0;
/* 239 */       char[] buf = new char[16384];
/* 240 */       while ((count = in.read(buf)) != -1) {
/* 241 */         if (count > 0)
/* 242 */           caw.write(buf, 0, count);
/*     */       }
/* 244 */       in.close();
/*     */     }
/*     */     catch (Exception e) {
/* 247 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 250 */     return caw.toCharArray();
/*     */   }
/*     */ 
/*     */   private static void writeBytes(File file, byte[] data) {
/*     */     try {
/* 255 */       OutputStream fos = new FileOutputStream(file);
/* 256 */       OutputStream os = new BufferedOutputStream(fos);
/* 257 */       os.write(data);
/* 258 */       os.close();
/*     */     }
/*     */     catch (Exception e) {
/* 261 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeChars(File file, char[] data) {
/*     */     try {
/* 267 */       Writer fos = new FileWriter(file);
/* 268 */       Writer os = new BufferedWriter(fos);
/* 269 */       os.write(data);
/* 270 */       os.close();
/*     */     }
/*     */     catch (Exception e) {
/* 273 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 160 */     for (int i = 0; i < 256; i++)
/* 161 */       codes[i] = -1;
/* 162 */     for (int i = 65; i <= 90; i++)
/* 163 */       codes[i] = (byte)(i - 65);
/* 164 */     for (int i = 97; i <= 122; i++)
/* 165 */       codes[i] = (byte)(26 + i - 97);
/* 166 */     for (int i = 48; i <= 57; i++)
/* 167 */       codes[i] = (byte)(52 + i - 48);
/* 168 */     codes[43] = 62;
/* 169 */     codes[47] = 63;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.Base64
 * JD-Core Version:    0.6.0
 */