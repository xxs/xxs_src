/*     */ package com.enation.app.shop.component.payment.plugin.bill.encrypt;
/*     */ 
/*     */ public class MD5
/*     */ {
/*     */   static final int S11 = 7;
/*     */   static final int S12 = 12;
/*     */   static final int S13 = 17;
/*     */   static final int S14 = 22;
/*     */   static final int S21 = 5;
/*     */   static final int S22 = 9;
/*     */   static final int S23 = 14;
/*     */   static final int S24 = 20;
/*     */   static final int S31 = 4;
/*     */   static final int S32 = 11;
/*     */   static final int S33 = 16;
/*     */   static final int S34 = 23;
/*     */   static final int S41 = 6;
/*     */   static final int S42 = 10;
/*     */   static final int S43 = 15;
/*     */   static final int S44 = 21;
/*  42 */   static final byte[] PADDING = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*  52 */   private long[] state = new long[4];
/*  53 */   private long[] count = new long[2];
/*  54 */   private byte[] buffer = new byte[64];
/*     */   public String digestHexStr;
/*  63 */   private byte[] digest = new byte[16];
/*     */ 
/*     */   public String getMD5ofStr(String inbuf)
/*     */   {
/*  70 */     md5Init();
/*  71 */     md5Update(inbuf.getBytes(), inbuf.length());
/*  72 */     md5Final();
/*  73 */     this.digestHexStr = "";
/*  74 */     for (int i = 0; i < 16; i++) {
/*  75 */       this.digestHexStr += byteHEX(this.digest[i]);
/*     */     }
/*  77 */     return this.digestHexStr;
/*     */   }
/*     */ 
/*     */   public MD5()
/*     */   {
/*  83 */     md5Init();
/*     */   }
/*     */ 
/*     */   private void md5Init()
/*     */   {
/*  90 */     this.count[0] = 0L;
/*  91 */     this.count[1] = 0L;
/*     */ 
/*  94 */     this.state[0] = 1732584193L;
/*  95 */     this.state[1] = 4023233417L;
/*  96 */     this.state[2] = 2562383102L;
/*  97 */     this.state[3] = 271733878L;
/*     */   }
/*     */ 
/*     */   private long F(long x, long y, long z)
/*     */   {
/* 107 */     return x & y | (x ^ 0xFFFFFFFF) & z;
/*     */   }
/*     */ 
/*     */   private long G(long x, long y, long z)
/*     */   {
/* 112 */     return x & z | y & (z ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private long H(long x, long y, long z)
/*     */   {
/* 117 */     return x ^ y ^ z;
/*     */   }
/*     */ 
/*     */   private long I(long x, long y, long z) {
/* 121 */     return y ^ (x | z ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private long FF(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 132 */     a += F(b, c, d) + x + ac;
/* 133 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 134 */     a += b;
/* 135 */     return a;
/*     */   }
/*     */ 
/*     */   private long GG(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 140 */     a += G(b, c, d) + x + ac;
/* 141 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 142 */     a += b;
/* 143 */     return a;
/*     */   }
/*     */ 
/*     */   private long HH(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 148 */     a += H(b, c, d) + x + ac;
/* 149 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 150 */     a += b;
/* 151 */     return a;
/*     */   }
/*     */ 
/*     */   private long II(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 156 */     a += I(b, c, d) + x + ac;
/* 157 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 158 */     a += b;
/* 159 */     return a;
/*     */   }
/*     */ 
/*     */   private void md5Update(byte[] inbuf, int inputLen)
/*     */   {
/* 169 */     byte[] block = new byte[64];
/* 170 */     int index = (int)(this.count[0] >>> 3) & 0x3F;
/*     */ 
/* 172 */     if (this.count[0] += (inputLen << 3) < inputLen << 3)
/* 173 */       this.count[1] += 1L;
/* 174 */     this.count[1] += (inputLen >>> 29);
/*     */ 
/* 176 */     int partLen = 64 - index;
/*     */     int i;
/* 179 */     if (inputLen >= partLen) {
/* 180 */       md5Memcpy(this.buffer, inbuf, index, 0, partLen);
/* 181 */       md5Transform(this.buffer);
/*     */ 
/* 183 */       for (int i = partLen; i + 63 < inputLen; i += 64)
/*     */       {
/* 185 */         md5Memcpy(block, inbuf, 0, i, 64);
/* 186 */         md5Transform(block);
/*     */       }
/* 188 */       index = 0;
/*     */     }
/*     */     else
/*     */     {
/* 193 */       i = 0;
/*     */     }
/*     */ 
/* 196 */     md5Memcpy(this.buffer, inbuf, index, i, inputLen - i);
/*     */   }
/*     */ 
/*     */   private void md5Final()
/*     */   {
/* 204 */     byte[] bits = new byte[8];
/*     */ 
/* 208 */     Encode(bits, this.count, 8);
/*     */ 
/* 211 */     int index = (int)(this.count[0] >>> 3) & 0x3F;
/* 212 */     int padLen = index < 56 ? 56 - index : 120 - index;
/* 213 */     md5Update(PADDING, padLen);
/*     */ 
/* 216 */     md5Update(bits, 8);
/*     */ 
/* 219 */     Encode(this.digest, this.state, 16);
/*     */   }
/*     */ 
/*     */   private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len)
/*     */   {
/* 231 */     for (int i = 0; i < len; i++)
/* 232 */       output[(outpos + i)] = input[(inpos + i)];
/*     */   }
/*     */ 
/*     */   private void md5Transform(byte[] block)
/*     */   {
/* 239 */     long a = this.state[0]; long b = this.state[1]; long c = this.state[2]; long d = this.state[3];
/* 240 */     long[] x = new long[16];
/*     */ 
/* 242 */     Decode(x, block, 64);
/*     */ 
/* 245 */     a = FF(a, b, c, d, x[0], 7L, 3614090360L);
/* 246 */     d = FF(d, a, b, c, x[1], 12L, 3905402710L);
/* 247 */     c = FF(c, d, a, b, x[2], 17L, 606105819L);
/* 248 */     b = FF(b, c, d, a, x[3], 22L, 3250441966L);
/* 249 */     a = FF(a, b, c, d, x[4], 7L, 4118548399L);
/* 250 */     d = FF(d, a, b, c, x[5], 12L, 1200080426L);
/* 251 */     c = FF(c, d, a, b, x[6], 17L, 2821735955L);
/* 252 */     b = FF(b, c, d, a, x[7], 22L, 4249261313L);
/* 253 */     a = FF(a, b, c, d, x[8], 7L, 1770035416L);
/* 254 */     d = FF(d, a, b, c, x[9], 12L, 2336552879L);
/* 255 */     c = FF(c, d, a, b, x[10], 17L, 4294925233L);
/* 256 */     b = FF(b, c, d, a, x[11], 22L, 2304563134L);
/* 257 */     a = FF(a, b, c, d, x[12], 7L, 1804603682L);
/* 258 */     d = FF(d, a, b, c, x[13], 12L, 4254626195L);
/* 259 */     c = FF(c, d, a, b, x[14], 17L, 2792965006L);
/* 260 */     b = FF(b, c, d, a, x[15], 22L, 1236535329L);
/*     */ 
/* 263 */     a = GG(a, b, c, d, x[1], 5L, 4129170786L);
/* 264 */     d = GG(d, a, b, c, x[6], 9L, 3225465664L);
/* 265 */     c = GG(c, d, a, b, x[11], 14L, 643717713L);
/* 266 */     b = GG(b, c, d, a, x[0], 20L, 3921069994L);
/* 267 */     a = GG(a, b, c, d, x[5], 5L, 3593408605L);
/* 268 */     d = GG(d, a, b, c, x[10], 9L, 38016083L);
/* 269 */     c = GG(c, d, a, b, x[15], 14L, 3634488961L);
/* 270 */     b = GG(b, c, d, a, x[4], 20L, 3889429448L);
/* 271 */     a = GG(a, b, c, d, x[9], 5L, 568446438L);
/* 272 */     d = GG(d, a, b, c, x[14], 9L, 3275163606L);
/* 273 */     c = GG(c, d, a, b, x[3], 14L, 4107603335L);
/* 274 */     b = GG(b, c, d, a, x[8], 20L, 72720877L);
/* 275 */     a = GG(a, b, c, d, x[13], 5L, 2850285829L);
/* 276 */     d = GG(d, a, b, c, x[2], 9L, 4243563512L);
/* 277 */     c = GG(c, d, a, b, x[7], 14L, 1735328473L);
/* 278 */     b = GG(b, c, d, a, x[12], 20L, 2368359562L);
/*     */ 
/* 281 */     a = HH(a, b, c, d, x[5], 4L, 4294588738L);
/* 282 */     d = HH(d, a, b, c, x[8], 11L, 2272392833L);
/* 283 */     c = HH(c, d, a, b, x[11], 16L, 1839030562L);
/* 284 */     b = HH(b, c, d, a, x[14], 23L, 4259657740L);
/* 285 */     a = HH(a, b, c, d, x[1], 4L, 2763975236L);
/* 286 */     d = HH(d, a, b, c, x[4], 11L, 1272893353L);
/* 287 */     c = HH(c, d, a, b, x[7], 16L, 4139469664L);
/* 288 */     b = HH(b, c, d, a, x[10], 23L, 3200236656L);
/* 289 */     a = HH(a, b, c, d, x[13], 4L, 681279174L);
/* 290 */     d = HH(d, a, b, c, x[0], 11L, 3936430074L);
/* 291 */     c = HH(c, d, a, b, x[3], 16L, 3572445317L);
/* 292 */     b = HH(b, c, d, a, x[6], 23L, 76029189L);
/* 293 */     a = HH(a, b, c, d, x[9], 4L, 3654602809L);
/* 294 */     d = HH(d, a, b, c, x[12], 11L, 3873151461L);
/* 295 */     c = HH(c, d, a, b, x[15], 16L, 530742520L);
/* 296 */     b = HH(b, c, d, a, x[2], 23L, 3299628645L);
/*     */ 
/* 299 */     a = II(a, b, c, d, x[0], 6L, 4096336452L);
/* 300 */     d = II(d, a, b, c, x[7], 10L, 1126891415L);
/* 301 */     c = II(c, d, a, b, x[14], 15L, 2878612391L);
/* 302 */     b = II(b, c, d, a, x[5], 21L, 4237533241L);
/* 303 */     a = II(a, b, c, d, x[12], 6L, 1700485571L);
/* 304 */     d = II(d, a, b, c, x[3], 10L, 2399980690L);
/* 305 */     c = II(c, d, a, b, x[10], 15L, 4293915773L);
/* 306 */     b = II(b, c, d, a, x[1], 21L, 2240044497L);
/* 307 */     a = II(a, b, c, d, x[8], 6L, 1873313359L);
/* 308 */     d = II(d, a, b, c, x[15], 10L, 4264355552L);
/* 309 */     c = II(c, d, a, b, x[6], 15L, 2734768916L);
/* 310 */     b = II(b, c, d, a, x[13], 21L, 1309151649L);
/* 311 */     a = II(a, b, c, d, x[4], 6L, 4149444226L);
/* 312 */     d = II(d, a, b, c, x[11], 10L, 3174756917L);
/* 313 */     c = II(c, d, a, b, x[2], 15L, 718787259L);
/* 314 */     b = II(b, c, d, a, x[9], 21L, 3951481745L);
/*     */ 
/* 316 */     this.state[0] += a;
/* 317 */     this.state[1] += b;
/* 318 */     this.state[2] += c;
/* 319 */     this.state[3] += d;
/*     */   }
/*     */ 
/*     */   private void Encode(byte[] output, long[] input, int len)
/*     */   {
/* 329 */     int i = 0; for (int j = 0; j < len; j += 4) {
/* 330 */       output[j] = (byte)(int)(input[i] & 0xFF);
/* 331 */       output[(j + 1)] = (byte)(int)(input[i] >>> 8 & 0xFF);
/* 332 */       output[(j + 2)] = (byte)(int)(input[i] >>> 16 & 0xFF);
/* 333 */       output[(j + 3)] = (byte)(int)(input[i] >>> 24 & 0xFF);
/*     */ 
/* 329 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void Decode(long[] output, byte[] input, int len)
/*     */   {
/* 343 */     int i = 0; for (int j = 0; j < len; j += 4) {
/* 344 */       output[i] = (b2iu(input[j]) | b2iu(input[(j + 1)]) << 8 | b2iu(input[(j + 2)]) << 16 | b2iu(input[(j + 3)]) << 24);
/*     */ 
/* 343 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static long b2iu(byte b)
/*     */   {
/* 356 */     return b;
/*     */   }
/*     */ 
/*     */   public static String byteHEX(byte ib)
/*     */   {
/* 364 */     char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/* 367 */     char[] ob = new char[2];
/* 368 */     ob[0] = Digit[(ib >>> 4 & 0xF)];
/* 369 */     ob[1] = Digit[(ib & 0xF)];
/* 370 */     String s = new String(ob);
/* 371 */     return s;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.payment.plugin.bill.encrypt.MD5
 * JD-Core Version:    0.6.0
 */