/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class BLAKEBigCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6: 42 */   private static final int[] SIGMA = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3, 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4, 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8, 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13, 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9, 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11, 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10, 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5, 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3, 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4, 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8, 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13, 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 };
/*   7: 61 */   private static final long[] CB = { 2611923443488327891L, 1376283091369227076L, -6626703657320631856L, 589684135938649225L, 4983270260364809079L, -4732044268327596948L, -4563226453097033507L, 4577018097722394903L, -7919907764393346277L, -3372901835766516308L, 3458046377305235383L, -5124621466747896170L, -5008970055469465703L, 2639559389850201335L, 577009281997405206L, 7163292796296056425L };
/*   8:    */   private long h0;
/*   9:    */   private long h1;
/*  10:    */   private long h2;
/*  11:    */   private long h3;
/*  12:    */   private long h4;
/*  13:    */   private long h5;
/*  14:    */   private long h6;
/*  15:    */   private long h7;
/*  16:    */   private long s0;
/*  17:    */   private long s1;
/*  18:    */   private long s2;
/*  19:    */   private long s3;
/*  20:    */   private long t0;
/*  21:    */   private long t1;
/*  22:    */   private long[] tmpM;
/*  23:    */   private byte[] tmpBuf;
/*  24:    */   
/*  25:    */   public int getBlockLength()
/*  26:    */   {
/*  27: 88 */     return 128;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected Digest copyState(BLAKEBigCore paramBLAKEBigCore)
/*  31:    */   {
/*  32: 94 */     paramBLAKEBigCore.h0 = this.h0;
/*  33: 95 */     paramBLAKEBigCore.h1 = this.h1;
/*  34: 96 */     paramBLAKEBigCore.h2 = this.h2;
/*  35: 97 */     paramBLAKEBigCore.h3 = this.h3;
/*  36: 98 */     paramBLAKEBigCore.h4 = this.h4;
/*  37: 99 */     paramBLAKEBigCore.h5 = this.h5;
/*  38:100 */     paramBLAKEBigCore.h6 = this.h6;
/*  39:101 */     paramBLAKEBigCore.h7 = this.h7;
/*  40:102 */     paramBLAKEBigCore.s0 = this.s0;
/*  41:103 */     paramBLAKEBigCore.s1 = this.s1;
/*  42:104 */     paramBLAKEBigCore.s2 = this.s2;
/*  43:105 */     paramBLAKEBigCore.s3 = this.s3;
/*  44:106 */     paramBLAKEBigCore.t0 = this.t0;
/*  45:107 */     paramBLAKEBigCore.t1 = this.t1;
/*  46:108 */     return super.copyState(paramBLAKEBigCore);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void engineReset()
/*  50:    */   {
/*  51:114 */     long[] arrayOfLong = getInitVal();
/*  52:115 */     this.h0 = arrayOfLong[0];
/*  53:116 */     this.h1 = arrayOfLong[1];
/*  54:117 */     this.h2 = arrayOfLong[2];
/*  55:118 */     this.h3 = arrayOfLong[3];
/*  56:119 */     this.h4 = arrayOfLong[4];
/*  57:120 */     this.h5 = arrayOfLong[5];
/*  58:121 */     this.h6 = arrayOfLong[6];
/*  59:122 */     this.h7 = arrayOfLong[7];
/*  60:123 */     this.s0 = (this.s1 = this.s2 = this.s3 = 0L);
/*  61:124 */     this.t0 = (this.t1 = 0L);
/*  62:    */   }
/*  63:    */   
/*  64:    */   abstract long[] getInitVal();
/*  65:    */   
/*  66:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  67:    */   {
/*  68:137 */     int i = flush();
/*  69:138 */     int j = i << 3;
/*  70:139 */     long l1 = this.t1;
/*  71:140 */     long l2 = this.t0 + j;
/*  72:141 */     this.tmpBuf[i] = Byte.MIN_VALUE;
/*  73:142 */     if (i == 0)
/*  74:    */     {
/*  75:143 */       this.t0 = -1024L;
/*  76:144 */       this.t1 = -1L;
/*  77:    */     }
/*  78:145 */     else if (this.t0 == 0L)
/*  79:    */     {
/*  80:146 */       this.t0 = (-1024 + j);
/*  81:147 */       this.t1 -= 1L;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:149 */       this.t0 -= 1024 - j;
/*  86:    */     }
/*  87:    */     int k;
/*  88:151 */     if (i < 112)
/*  89:    */     {
/*  90:152 */       for (k = i + 1; k < 112; k++) {
/*  91:153 */         this.tmpBuf[k] = 0;
/*  92:    */       }
/*  93:154 */       if (getDigestLength() == 64)
/*  94:    */       {
/*  95:155 */         byte[] tmp151_146 = this.tmpBuf;tmp151_146[111] = ((byte)(tmp151_146[111] | 0x1));
/*  96:    */       }
/*  97:156 */       encodeBELong(l1, this.tmpBuf, 112);
/*  98:157 */       encodeBELong(l2, this.tmpBuf, 120);
/*  99:158 */       update(this.tmpBuf, i, 128 - i);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:160 */       for (k = i + 1; k < 128; k++) {
/* 104:161 */         this.tmpBuf[k] = 0;
/* 105:    */       }
/* 106:162 */       update(this.tmpBuf, i, 128 - i);
/* 107:163 */       this.t0 = -1024L;
/* 108:164 */       this.t1 = -1L;
/* 109:165 */       for (k = 0; k < 112; k++) {
/* 110:166 */         this.tmpBuf[k] = 0;
/* 111:    */       }
/* 112:167 */       if (getDigestLength() == 64) {
/* 113:168 */         this.tmpBuf[111] = 1;
/* 114:    */       }
/* 115:169 */       encodeBELong(l1, this.tmpBuf, 112);
/* 116:170 */       encodeBELong(l2, this.tmpBuf, 120);
/* 117:171 */       update(this.tmpBuf, 0, 128);
/* 118:    */     }
/* 119:173 */     encodeBELong(this.h0, paramArrayOfByte, paramInt + 0);
/* 120:174 */     encodeBELong(this.h1, paramArrayOfByte, paramInt + 8);
/* 121:175 */     encodeBELong(this.h2, paramArrayOfByte, paramInt + 16);
/* 122:176 */     encodeBELong(this.h3, paramArrayOfByte, paramInt + 24);
/* 123:177 */     encodeBELong(this.h4, paramArrayOfByte, paramInt + 32);
/* 124:178 */     encodeBELong(this.h5, paramArrayOfByte, paramInt + 40);
/* 125:179 */     if (getDigestLength() == 64)
/* 126:    */     {
/* 127:180 */       encodeBELong(this.h6, paramArrayOfByte, paramInt + 48);
/* 128:181 */       encodeBELong(this.h7, paramArrayOfByte, paramInt + 56);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void doInit()
/* 133:    */   {
/* 134:188 */     this.tmpM = new long[16];
/* 135:189 */     this.tmpBuf = new byte[''];
/* 136:190 */     engineReset();
/* 137:    */   }
/* 138:    */   
/* 139:    */   private static final void encodeBELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/* 140:    */   {
/* 141:204 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong >>> 56));
/* 142:205 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/* 143:206 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/* 144:207 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/* 145:208 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/* 146:209 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/* 147:210 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/* 148:211 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)paramLong);
/* 149:    */   }
/* 150:    */   
/* 151:    */   private static final long decodeBELong(byte[] paramArrayOfByte, int paramInt)
/* 152:    */   {
/* 153:224 */     return (paramArrayOfByte[paramInt] & 0xFF) << 56 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 7)] & 0xFF;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private static long circularRight(long paramLong, int paramInt)
/* 157:    */   {
/* 158:245 */     return paramLong >>> paramInt | paramLong << 64 - paramInt;
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 162:    */   {
/* 163:251 */     this.t0 += 1024L;
/* 164:252 */     if ((this.t0 & 0xFFFFFC00) == 0L) {
/* 165:253 */       this.t1 += 1L;
/* 166:    */     }
/* 167:254 */     long l1 = this.h0;
/* 168:255 */     long l2 = this.h1;
/* 169:256 */     long l3 = this.h2;
/* 170:257 */     long l4 = this.h3;
/* 171:258 */     long l5 = this.h4;
/* 172:259 */     long l6 = this.h5;
/* 173:260 */     long l7 = this.h6;
/* 174:261 */     long l8 = this.h7;
/* 175:262 */     long l9 = this.s0 ^ 0x85A308D3;
/* 176:263 */     long l10 = this.s1 ^ 0x3707344;
/* 177:264 */     long l11 = this.s2 ^ 0x299F31D0;
/* 178:265 */     long l12 = this.s3 ^ 0xEC4E6C89;
/* 179:266 */     long l13 = this.t0 ^ 0x38D01377;
/* 180:267 */     long l14 = this.t0 ^ 0x34E90C6C;
/* 181:268 */     long l15 = this.t1 ^ 0xC97C50DD;
/* 182:269 */     long l16 = this.t1 ^ 0xB5470917;
/* 183:270 */     long[] arrayOfLong = this.tmpM;
/* 184:271 */     for (int i = 0; i < 16; i++) {
/* 185:272 */       arrayOfLong[i] = decodeBELong(paramArrayOfByte, 8 * i);
/* 186:    */     }
/* 187:273 */     for (i = 0; i < 16; i++)
/* 188:    */     {
/* 189:274 */       int j = SIGMA[((i << 4) + 0)];
/* 190:275 */       int k = SIGMA[((i << 4) + 1)];
/* 191:276 */       l1 += l5 + (arrayOfLong[j] ^ CB[k]);
/* 192:277 */       l13 = circularRight(l13 ^ l1, 32);
/* 193:278 */       l9 += l13;
/* 194:279 */       l5 = circularRight(l5 ^ l9, 25);
/* 195:280 */       l1 += l5 + (arrayOfLong[k] ^ CB[j]);
/* 196:281 */       l13 = circularRight(l13 ^ l1, 16);
/* 197:282 */       l9 += l13;
/* 198:283 */       l5 = circularRight(l5 ^ l9, 11);
/* 199:284 */       j = SIGMA[((i << 4) + 2)];
/* 200:285 */       k = SIGMA[((i << 4) + 3)];
/* 201:286 */       l2 += l6 + (arrayOfLong[j] ^ CB[k]);
/* 202:287 */       l14 = circularRight(l14 ^ l2, 32);
/* 203:288 */       l10 += l14;
/* 204:289 */       l6 = circularRight(l6 ^ l10, 25);
/* 205:290 */       l2 += l6 + (arrayOfLong[k] ^ CB[j]);
/* 206:291 */       l14 = circularRight(l14 ^ l2, 16);
/* 207:292 */       l10 += l14;
/* 208:293 */       l6 = circularRight(l6 ^ l10, 11);
/* 209:294 */       j = SIGMA[((i << 4) + 4)];
/* 210:295 */       k = SIGMA[((i << 4) + 5)];
/* 211:296 */       l3 += l7 + (arrayOfLong[j] ^ CB[k]);
/* 212:297 */       l15 = circularRight(l15 ^ l3, 32);
/* 213:298 */       l11 += l15;
/* 214:299 */       l7 = circularRight(l7 ^ l11, 25);
/* 215:300 */       l3 += l7 + (arrayOfLong[k] ^ CB[j]);
/* 216:301 */       l15 = circularRight(l15 ^ l3, 16);
/* 217:302 */       l11 += l15;
/* 218:303 */       l7 = circularRight(l7 ^ l11, 11);
/* 219:304 */       j = SIGMA[((i << 4) + 6)];
/* 220:305 */       k = SIGMA[((i << 4) + 7)];
/* 221:306 */       l4 += l8 + (arrayOfLong[j] ^ CB[k]);
/* 222:307 */       l16 = circularRight(l16 ^ l4, 32);
/* 223:308 */       l12 += l16;
/* 224:309 */       l8 = circularRight(l8 ^ l12, 25);
/* 225:310 */       l4 += l8 + (arrayOfLong[k] ^ CB[j]);
/* 226:311 */       l16 = circularRight(l16 ^ l4, 16);
/* 227:312 */       l12 += l16;
/* 228:313 */       l8 = circularRight(l8 ^ l12, 11);
/* 229:314 */       j = SIGMA[((i << 4) + 8)];
/* 230:315 */       k = SIGMA[((i << 4) + 9)];
/* 231:316 */       l1 += l6 + (arrayOfLong[j] ^ CB[k]);
/* 232:317 */       l16 = circularRight(l16 ^ l1, 32);
/* 233:318 */       l11 += l16;
/* 234:319 */       l6 = circularRight(l6 ^ l11, 25);
/* 235:320 */       l1 += l6 + (arrayOfLong[k] ^ CB[j]);
/* 236:321 */       l16 = circularRight(l16 ^ l1, 16);
/* 237:322 */       l11 += l16;
/* 238:323 */       l6 = circularRight(l6 ^ l11, 11);
/* 239:324 */       j = SIGMA[((i << 4) + 10)];
/* 240:325 */       k = SIGMA[((i << 4) + 11)];
/* 241:326 */       l2 += l7 + (arrayOfLong[j] ^ CB[k]);
/* 242:327 */       l13 = circularRight(l13 ^ l2, 32);
/* 243:328 */       l12 += l13;
/* 244:329 */       l7 = circularRight(l7 ^ l12, 25);
/* 245:330 */       l2 += l7 + (arrayOfLong[k] ^ CB[j]);
/* 246:331 */       l13 = circularRight(l13 ^ l2, 16);
/* 247:332 */       l12 += l13;
/* 248:333 */       l7 = circularRight(l7 ^ l12, 11);
/* 249:334 */       j = SIGMA[((i << 4) + 12)];
/* 250:335 */       k = SIGMA[((i << 4) + 13)];
/* 251:336 */       l3 += l8 + (arrayOfLong[j] ^ CB[k]);
/* 252:337 */       l14 = circularRight(l14 ^ l3, 32);
/* 253:338 */       l9 += l14;
/* 254:339 */       l8 = circularRight(l8 ^ l9, 25);
/* 255:340 */       l3 += l8 + (arrayOfLong[k] ^ CB[j]);
/* 256:341 */       l14 = circularRight(l14 ^ l3, 16);
/* 257:342 */       l9 += l14;
/* 258:343 */       l8 = circularRight(l8 ^ l9, 11);
/* 259:344 */       j = SIGMA[((i << 4) + 14)];
/* 260:345 */       k = SIGMA[((i << 4) + 15)];
/* 261:346 */       l4 += l5 + (arrayOfLong[j] ^ CB[k]);
/* 262:347 */       l15 = circularRight(l15 ^ l4, 32);
/* 263:348 */       l10 += l15;
/* 264:349 */       l5 = circularRight(l5 ^ l10, 25);
/* 265:350 */       l4 += l5 + (arrayOfLong[k] ^ CB[j]);
/* 266:351 */       l15 = circularRight(l15 ^ l4, 16);
/* 267:352 */       l10 += l15;
/* 268:353 */       l5 = circularRight(l5 ^ l10, 11);
/* 269:    */     }
/* 270:355 */     this.h0 ^= this.s0 ^ l1 ^ l9;
/* 271:356 */     this.h1 ^= this.s1 ^ l2 ^ l10;
/* 272:357 */     this.h2 ^= this.s2 ^ l3 ^ l11;
/* 273:358 */     this.h3 ^= this.s3 ^ l4 ^ l12;
/* 274:359 */     this.h4 ^= this.s0 ^ l5 ^ l13;
/* 275:360 */     this.h5 ^= this.s1 ^ l6 ^ l14;
/* 276:361 */     this.h6 ^= this.s2 ^ l7 ^ l15;
/* 277:362 */     this.h7 ^= this.s3 ^ l8 ^ l16;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String toString()
/* 281:    */   {
/* 282:368 */     return "BLAKE-" + (getDigestLength() << 3);
/* 283:    */   }
/* 284:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BLAKEBigCore
 * JD-Core Version:    0.7.1
 */