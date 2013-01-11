/*     */ package com.enation.framework.util.ip;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileChannel.MapMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ 
/*     */ public class IPSeeker
/*     */ {
/*  24 */   private static final String IP_FILE = EopSetting.EOP_PATH + "/WEB-INF/QQWry.Dat";
/*     */   private static final int IP_RECORD_LENGTH = 7;
/*     */   private static final byte REDIRECT_MODE_1 = 1;
/*     */   private static final byte REDIRECT_MODE_2 = 2;
/*     */   private Map<String, IPLocation> ipCache;
/*     */   private RandomAccessFile ipFile;
/*     */   private MappedByteBuffer mbb;
/*     */   private long ipBegin;
/*     */   private long ipEnd;
/*     */   private IPLocation loc;
/*     */   private byte[] buf;
/*     */   private byte[] b4;
/*     */   private byte[] b3;
/*     */   private static IPSeeker instance;
/*     */ 
/*     */   public IPSeeker()
/*     */   {
/*  49 */     this.ipCache = new HashMap();
/*  50 */     this.loc = new IPLocation();
/*  51 */     this.buf = new byte[100];
/*  52 */     this.b4 = new byte[4];
/*  53 */     this.b3 = new byte[3];
/*     */     try {
/*  55 */       this.ipFile = new RandomAccessFile(IP_FILE, "r");
/*     */     } catch (FileNotFoundException e) {
/*  57 */       LogFactory.log("IP地址信息文件没有找到，IP显示功能将无法使用", Level.ERROR, e);
/*     */     }
/*     */ 
/*  60 */     if (this.ipFile != null)
/*     */       try {
/*  62 */         this.ipBegin = readLong4(0L);
/*  63 */         this.ipEnd = readLong4(4L);
/*  64 */         if ((this.ipBegin == -1L) || (this.ipEnd == -1L)) {
/*  65 */           this.ipFile.close();
/*  66 */           this.ipFile = null;
/*     */         }
/*     */       } catch (IOException e) {
/*  69 */         LogFactory.log("IP地址信息文件格式有错误，IP显示功能将无法使用", Level.ERROR, e);
/*  70 */         this.ipFile = null;
/*     */       }
/*     */   }
/*     */ 
/*     */   public List getIPEntriesDebug(String s)
/*     */   {
/*  82 */     List ret = new ArrayList();
/*  83 */     long endOffset = this.ipEnd + 4L;
/*  84 */     for (long offset = this.ipBegin + 4L; offset <= endOffset; offset += 7L)
/*     */     {
/*  86 */       long temp = readLong3(offset);
/*     */ 
/*  88 */       if (temp != -1L) {
/*  89 */         IPLocation ipLoc = getIPLocation(temp);
/*     */ 
/*  91 */         if ((ipLoc.getCountry().indexOf(s) != -1) || (ipLoc.getArea().indexOf(s) != -1)) {
/*  92 */           IPEntry entry = new IPEntry();
/*  93 */           entry.country = ipLoc.getCountry();
/*  94 */           entry.area = ipLoc.getArea();
/*     */ 
/*  96 */           readIP(offset - 4L, this.b4);
/*  97 */           entry.beginIp = IpUtil.getIpStringFromBytes(this.b4);
/*     */ 
/*  99 */           readIP(temp, this.b4);
/* 100 */           entry.endIp = IpUtil.getIpStringFromBytes(this.b4);
/*     */ 
/* 102 */           ret.add(entry);
/*     */         }
/*     */       }
/*     */     }
/* 106 */     return ret;
/*     */   }
/*     */ 
/*     */   public IPLocation getIPLocation(String ip) {
/* 110 */     IPLocation location = new IPLocation();
/* 111 */     location.setArea(getArea(ip));
/* 112 */     location.setCountry(getCountry(ip));
/* 113 */     return location;
/*     */   }
/*     */ 
/*     */   public List<IPEntry> getIPEntries(String s)
/*     */   {
/* 122 */     List ret = new ArrayList();
/*     */     try
/*     */     {
/* 125 */       if (this.mbb == null) {
/* 126 */         FileChannel fc = this.ipFile.getChannel();
/* 127 */         this.mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0L, this.ipFile.length());
/* 128 */         this.mbb.order(ByteOrder.LITTLE_ENDIAN);
/*     */       }
/*     */ 
/* 131 */       int endOffset = (int)this.ipEnd;
/* 132 */       for (int offset = (int)this.ipBegin + 4; offset <= endOffset; offset += 7) {
/* 133 */         int temp = readInt3(offset);
/* 134 */         if (temp != -1) {
/* 135 */           IPLocation ipLoc = getIPLocation(temp);
/*     */ 
/* 137 */           if ((ipLoc.getCountry().indexOf(s) != -1) || (ipLoc.getArea().indexOf(s) != -1)) {
/* 138 */             IPEntry entry = new IPEntry();
/* 139 */             entry.country = ipLoc.getCountry();
/* 140 */             entry.area = ipLoc.getArea();
/*     */ 
/* 142 */             readIP(offset - 4, this.b4);
/* 143 */             entry.beginIp = IpUtil.getIpStringFromBytes(this.b4);
/*     */ 
/* 145 */             readIP(temp, this.b4);
/* 146 */             entry.endIp = IpUtil.getIpStringFromBytes(this.b4);
/*     */ 
/* 148 */             ret.add(entry);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 153 */       LogFactory.log("", Level.ERROR, e);
/*     */     }
/* 155 */     return ret;
/*     */   }
/*     */ 
/*     */   private int readInt3(int offset)
/*     */   {
/* 164 */     this.mbb.position(offset);
/* 165 */     return this.mbb.getInt() & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   private int readInt3()
/*     */   {
/* 173 */     return this.mbb.getInt() & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   public String getCountry(byte[] ip)
/*     */   {
/* 183 */     if (this.ipFile == null) {
/* 184 */       return "IP地址库文件错误";
/*     */     }
/* 186 */     String ipStr = IpUtil.getIpStringFromBytes(ip);
/*     */ 
/* 188 */     if (this.ipCache.containsKey(ipStr)) {
/* 189 */       IPLocation ipLoc = (IPLocation)this.ipCache.get(ipStr);
/* 190 */       return ipLoc.getCountry();
/*     */     }
/* 192 */     IPLocation ipLoc = getIPLocation(ip);
/* 193 */     this.ipCache.put(ipStr, ipLoc.getCopy());
/* 194 */     return ipLoc.getCountry();
/*     */   }
/*     */ 
/*     */   public String getCountry(String ip)
/*     */   {
/* 204 */     return getCountry(IpUtil.getIpByteArrayFromString(ip));
/*     */   }
/*     */ 
/*     */   public String getArea(byte[] ip)
/*     */   {
/* 214 */     if (this.ipFile == null) {
/* 215 */       return "IP地址库文件错误";
/*     */     }
/* 217 */     String ipStr = IpUtil.getIpStringFromBytes(ip);
/*     */ 
/* 219 */     if (this.ipCache.containsKey(ipStr)) {
/* 220 */       IPLocation ipLoc = (IPLocation)this.ipCache.get(ipStr);
/* 221 */       return ipLoc.getArea();
/*     */     }
/* 223 */     IPLocation ipLoc = getIPLocation(ip);
/* 224 */     this.ipCache.put(ipStr, ipLoc.getCopy());
/* 225 */     return ipLoc.getArea();
/*     */   }
/*     */ 
/*     */   public String getArea(String ip)
/*     */   {
/* 235 */     return getArea(IpUtil.getIpByteArrayFromString(ip));
/*     */   }
/*     */ 
/*     */   private IPLocation getIPLocation(byte[] ip)
/*     */   {
/* 244 */     IPLocation info = null;
/* 245 */     long offset = locateIP(ip);
/* 246 */     if (offset != -1L)
/* 247 */       info = getIPLocation(offset);
/* 248 */     if (info == null) {
/* 249 */       info = new IPLocation();
/* 250 */       info.setCountry("未知国家");
/* 251 */       info.setArea("未知地区");
/*     */     }
/* 253 */     return info;
/*     */   }
/*     */ 
/*     */   private long readLong4(long offset)
/*     */   {
/* 263 */     long ret = 0L;
/*     */     try {
/* 265 */       this.ipFile.seek(offset);
/* 266 */       ret |= this.ipFile.readByte() & 0xFF;
/* 267 */       ret |= this.ipFile.readByte() << 8 & 0xFF00;
/* 268 */       ret |= this.ipFile.readByte() << 16 & 0xFF0000;
/* 269 */       ret |= this.ipFile.readByte() << 24 & 0xFF000000;
/* 270 */       return ret; } catch (IOException e) {
/*     */     }
/* 272 */     return -1L;
/*     */   }
/*     */ 
/*     */   private long readLong3(long offset)
/*     */   {
/* 283 */     long ret = 0L;
/*     */     try {
/* 285 */       this.ipFile.seek(offset);
/* 286 */       this.ipFile.readFully(this.b3);
/* 287 */       ret |= this.b3[0] & 0xFF;
/* 288 */       ret |= this.b3[1] << 8 & 0xFF00;
/* 289 */       ret |= this.b3[2] << 16 & 0xFF0000;
/* 290 */       return ret; } catch (IOException e) {
/*     */     }
/* 292 */     return -1L;
/*     */   }
/*     */ 
/*     */   private long readLong3()
/*     */   {
/* 301 */     long ret = 0L;
/*     */     try {
/* 303 */       this.ipFile.readFully(this.b3);
/* 304 */       ret |= this.b3[0] & 0xFF;
/* 305 */       ret |= this.b3[1] << 8 & 0xFF00;
/* 306 */       ret |= this.b3[2] << 16 & 0xFF0000;
/* 307 */       return ret; } catch (IOException e) {
/*     */     }
/* 309 */     return -1L;
/*     */   }
/*     */ 
/*     */   private void readIP(long offset, byte[] ip)
/*     */   {
/*     */     try
/*     */     {
/* 321 */       this.ipFile.seek(offset);
/* 322 */       this.ipFile.readFully(ip);
/* 323 */       byte temp = ip[0];
/* 324 */       ip[0] = ip[3];
/* 325 */       ip[3] = temp;
/* 326 */       temp = ip[1];
/* 327 */       ip[1] = ip[2];
/* 328 */       ip[2] = temp;
/*     */     } catch (IOException e) {
/* 330 */       LogFactory.log("", Level.ERROR, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readIP(int offset, byte[] ip)
/*     */   {
/* 341 */     this.mbb.position(offset);
/* 342 */     this.mbb.get(ip);
/* 343 */     byte temp = ip[0];
/* 344 */     ip[0] = ip[3];
/* 345 */     ip[3] = temp;
/* 346 */     temp = ip[1];
/* 347 */     ip[1] = ip[2];
/* 348 */     ip[2] = temp;
/*     */   }
/*     */ 
/*     */   private int compareIP(byte[] ip, byte[] beginIp)
/*     */   {
/* 358 */     for (int i = 0; i < 4; i++) {
/* 359 */       int r = compareByte(ip[i], beginIp[i]);
/* 360 */       if (r != 0)
/* 361 */         return r;
/*     */     }
/* 363 */     return 0;
/*     */   }
/*     */ 
/*     */   private int compareByte(byte b1, byte b2)
/*     */   {
/* 373 */     if ((b1 & 0xFF) > (b2 & 0xFF))
/* 374 */       return 1;
/* 375 */     if ((b1 ^ b2) == 0) {
/* 376 */       return 0;
/*     */     }
/* 378 */     return -1;
/*     */   }
/*     */ 
/*     */   private long locateIP(byte[] ip)
/*     */   {
/* 388 */     long m = 0L;
/*     */ 
/* 391 */     readIP(this.ipBegin, this.b4);
/* 392 */     int r = compareIP(ip, this.b4);
/* 393 */     if (r == 0) return this.ipBegin;
/* 394 */     if (r < 0) return -1L;
/*     */ 
/* 396 */     long i = this.ipBegin; for (long j = this.ipEnd; i < j; ) {
/* 397 */       m = getMiddleOffset(i, j);
/* 398 */       readIP(m, this.b4);
/* 399 */       r = compareIP(ip, this.b4);
/*     */ 
/* 401 */       if (r > 0) {
/* 402 */         i = m; continue;
/* 403 */       }if (r < 0) {
/* 404 */         if (m == j) {
/* 405 */           j -= 7L;
/* 406 */           m = j; continue;
/*     */         }
/* 408 */         j = m; continue;
/*     */       }
/* 410 */       return readLong3(m + 4L);
/*     */     }
/*     */ 
/* 414 */     m = readLong3(m + 4L);
/* 415 */     readIP(m, this.b4);
/* 416 */     r = compareIP(ip, this.b4);
/* 417 */     if (r <= 0) return m;
/* 418 */     return -1L;
/*     */   }
/*     */ 
/*     */   private long getMiddleOffset(long begin, long end)
/*     */   {
/* 428 */     long records = (end - begin) / 7L;
/* 429 */     records >>= 1;
/* 430 */     if (records == 0L) records = 1L;
/* 431 */     return begin + records * 7L;
/*     */   }
/*     */ 
/*     */   private IPLocation getIPLocation(long offset)
/*     */   {
/*     */     try
/*     */     {
/* 442 */       this.ipFile.seek(offset + 4L);
/*     */ 
/* 444 */       byte b = this.ipFile.readByte();
/* 445 */       if (b == 1)
/*     */       {
/* 447 */         long countryOffset = readLong3();
/*     */ 
/* 449 */         this.ipFile.seek(countryOffset);
/*     */ 
/* 451 */         b = this.ipFile.readByte();
/* 452 */         if (b == 2) {
/* 453 */           this.loc.setCountry(readString(readLong3()));
/* 454 */           this.ipFile.seek(countryOffset + 4L);
/*     */         } else {
/* 456 */           this.loc.setCountry(readString(countryOffset));
/*     */         }
/* 458 */         this.loc.setArea(readArea(this.ipFile.getFilePointer()));
/* 459 */       } else if (b == 2) {
/* 460 */         this.loc.setCountry(readString(readLong3()));
/* 461 */         this.loc.setArea(readArea(offset + 8L));
/*     */       } else {
/* 463 */         this.loc.setCountry(readString(this.ipFile.getFilePointer() - 1L));
/* 464 */         this.loc.setArea(readArea(this.ipFile.getFilePointer()));
/*     */       }
/* 466 */       return this.loc; } catch (IOException e) {
/*     */     }
/* 468 */     return null;
/*     */   }
/*     */ 
/*     */   private IPLocation getIPLocation(int offset)
/*     */   {
/* 479 */     this.mbb.position(offset + 4);
/*     */ 
/* 481 */     byte b = this.mbb.get();
/* 482 */     if (b == 1)
/*     */     {
/* 484 */       int countryOffset = readInt3();
/*     */ 
/* 486 */       this.mbb.position(countryOffset);
/*     */ 
/* 488 */       b = this.mbb.get();
/* 489 */       if (b == 2) {
/* 490 */         this.loc.setCountry(readString(readInt3()));
/* 491 */         this.mbb.position(countryOffset + 4);
/*     */       } else {
/* 493 */         this.loc.setCountry(readString(countryOffset));
/*     */       }
/* 495 */       this.loc.setArea(readArea(this.mbb.position()));
/* 496 */     } else if (b == 2) {
/* 497 */       this.loc.setCountry(readString(readInt3()));
/* 498 */       this.loc.setArea(readArea(offset + 8));
/*     */     } else {
/* 500 */       this.loc.setCountry(readString(this.mbb.position() - 1));
/* 501 */       this.loc.setArea(readArea(this.mbb.position()));
/*     */     }
/* 503 */     return this.loc;
/*     */   }
/*     */ 
/*     */   private String readArea(long offset)
/*     */     throws IOException
/*     */   {
/* 513 */     this.ipFile.seek(offset);
/* 514 */     byte b = this.ipFile.readByte();
/* 515 */     if ((b == 1) || (b == 2)) {
/* 516 */       long areaOffset = readLong3(offset + 1L);
/* 517 */       if (areaOffset == 0L) {
/* 518 */         return "未知地区";
/*     */       }
/* 520 */       return readString(areaOffset);
/*     */     }
/* 522 */     return readString(offset);
/*     */   }
/*     */ 
/*     */   private String readArea(int offset)
/*     */   {
/* 530 */     this.mbb.position(offset);
/* 531 */     byte b = this.mbb.get();
/* 532 */     if ((b == 1) || (b == 2)) {
/* 533 */       int areaOffset = readInt3();
/* 534 */       if (areaOffset == 0) {
/* 535 */         return "未知地区";
/*     */       }
/* 537 */       return readString(areaOffset);
/*     */     }
/* 539 */     return readString(offset);
/*     */   }
/*     */ 
/*     */   private String readString(long offset)
/*     */   {
/*     */     try
/*     */     {
/* 549 */       this.ipFile.seek(offset);
/*     */ 
/* 551 */       int i = 0; for (this.buf[i] = this.ipFile.readByte(); this.buf[i] != 0; this.buf[i] = this.ipFile.readByte()) i++;
/* 552 */       if (i != 0)
/* 553 */         return IpUtil.getString(this.buf, 0, i, "GBK");
/*     */     } catch (IOException e) {
/* 555 */       LogFactory.log("", Level.ERROR, e);
/*     */     }
/* 557 */     return "";
/*     */   }
/*     */ 
/*     */   private String readString(int offset)
/*     */   {
/*     */     try
/*     */     {
/* 567 */       this.mbb.position(offset);
/*     */ 
/* 569 */       int i = 0; for (this.buf[i] = this.mbb.get(); this.buf[i] != 0; this.buf[i] = this.mbb.get()) i++;
/* 570 */       if (i != 0)
/* 571 */         return IpUtil.getString(this.buf, 0, i, "GBK");
/*     */     } catch (IllegalArgumentException e) {
/* 573 */       LogFactory.log("", Level.ERROR, e);
/*     */     }
/* 575 */     return "";
/*     */   }
/*     */ 
/*     */   public static IPSeeker getInstance()
/*     */   {
/* 580 */     if (instance == null) instance = new IPSeeker();
/* 581 */     return instance;
/*     */   }
/*     */   public static void main(String[] args) {
/* 584 */     IPSeeker ip = getInstance();
/*     */ 
/* 598 */     String country = ip.getIPLocation("127.0.0.1").getCountry();
/* 599 */     int end = country.indexOf("省");
/* 600 */     if (end == -1) {
/* 601 */       end = country.indexOf("市");
/*     */     }
/* 603 */     if (end != -1)
/* 604 */       country = country.substring(0, end);
/* 605 */     System.out.println(country);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ip.IPSeeker
 * JD-Core Version:    0.6.0
 */