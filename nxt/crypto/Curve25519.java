/*   1:    */ package nxt.crypto;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ 
/*   5:    */ final class Curve25519
/*   6:    */ {
/*   7:    */   public static final int KEY_SIZE = 32;
/*   8: 18 */   public static final byte[] ZERO = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*   9: 24 */   public static final byte[] PRIME = { -19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Byte.MAX_VALUE };
/*  10: 36 */   public static final byte[] ORDER = { -19, -45, -11, 92, 26, 99, 18, 88, -42, -100, -9, -94, -34, -7, -34, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16 };
/*  11:    */   private static final int P25 = 33554431;
/*  12:    */   private static final int P26 = 67108863;
/*  13:    */   
/*  14:    */   public static void clamp(byte[] paramArrayOfByte)
/*  15:    */   {
/*  16: 54 */     paramArrayOfByte[31] = ((byte)(paramArrayOfByte[31] & 0x7F));
/*  17: 55 */     paramArrayOfByte[31] = ((byte)(paramArrayOfByte[31] | 0x40)); int 
/*  18: 56 */       tmp22_21 = 0;paramArrayOfByte[tmp22_21] = ((byte)(paramArrayOfByte[tmp22_21] & 0xF8));
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void keygen(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/*  22:    */   {
/*  23: 68 */     clamp(paramArrayOfByte3);
/*  24: 69 */     core(paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void curve(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/*  28:    */   {
/*  29: 78 */     core(paramArrayOfByte1, null, paramArrayOfByte2, paramArrayOfByte3);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static boolean sign(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/*  33:    */   {
/*  34:128 */     byte[] arrayOfByte1 = new byte[32];byte[] arrayOfByte2 = new byte[32];
/*  35:129 */     byte[] arrayOfByte3 = new byte[64];
/*  36:130 */     byte[] arrayOfByte4 = new byte[64];
/*  37:    */     
/*  38:    */ 
/*  39:133 */     cpy32(arrayOfByte1, paramArrayOfByte2);
/*  40:134 */     cpy32(arrayOfByte2, paramArrayOfByte3);
/*  41:    */     
/*  42:    */ 
/*  43:137 */     byte[] arrayOfByte5 = new byte[32];
/*  44:138 */     divmod(arrayOfByte5, arrayOfByte1, 32, ORDER, 32);
/*  45:139 */     divmod(arrayOfByte5, arrayOfByte2, 32, ORDER, 32);
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:145 */     mula_small(paramArrayOfByte1, arrayOfByte2, 0, arrayOfByte1, 32, -1);
/*  52:146 */     mula_small(paramArrayOfByte1, paramArrayOfByte1, 0, ORDER, 32, 1);
/*  53:    */     
/*  54:    */ 
/*  55:149 */     mula32(arrayOfByte3, paramArrayOfByte1, paramArrayOfByte4, 32, 1);
/*  56:150 */     divmod(arrayOfByte4, arrayOfByte3, 64, ORDER, 32);
/*  57:    */     
/*  58:152 */     int i = 0;
/*  59:152 */     for (int j = 0; j < 32; j++) {
/*  60:153 */       i |= (paramArrayOfByte1[j] = arrayOfByte3[j]);
/*  61:    */     }
/*  62:154 */     return i != 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void verify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/*  66:    */   {
/*  67:165 */     byte[] arrayOfByte = new byte[32];
/*  68:    */     
/*  69:167 */     long10[] arrayOflong101 = { new long10(), new long10() };
/*  70:168 */     long10[] arrayOflong102 = { new long10(), new long10() };
/*  71:169 */     long10[] arrayOflong103 = { new long10(), new long10(), new long10() };
/*  72:170 */     long10[] arrayOflong104 = { new long10(), new long10(), new long10() };
/*  73:171 */     long10[] arrayOflong105 = { new long10(), new long10(), new long10() };
/*  74:172 */     long10[] arrayOflong106 = { new long10(), new long10(), new long10() };
/*  75:    */     
/*  76:174 */     int i = 0;int j = 0;int k = 0;int m = 0;
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:178 */     set(arrayOflong101[0], 9);
/*  81:179 */     unpack(arrayOflong101[1], paramArrayOfByte4);
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:186 */     x_to_y2(arrayOflong105[0], arrayOflong106[0], arrayOflong101[1]);
/*  89:187 */     sqrt(arrayOflong105[0], arrayOflong106[0]);
/*  90:188 */     int i1 = is_negative(arrayOflong105[0]);
/*  91:189 */     arrayOflong106[0]._0 += 39420360L;
/*  92:190 */     mul(arrayOflong106[1], BASE_2Y, arrayOflong105[0]);
/*  93:191 */     sub(arrayOflong105[i1], arrayOflong106[0], arrayOflong106[1]);
/*  94:192 */     add(arrayOflong105[(1 - i1)], arrayOflong106[0], arrayOflong106[1]);
/*  95:193 */     cpy(arrayOflong106[0], arrayOflong101[1]);
/*  96:194 */     arrayOflong106[0]._0 -= 9L;
/*  97:195 */     sqr(arrayOflong106[1], arrayOflong106[0]);
/*  98:196 */     recip(arrayOflong106[0], arrayOflong106[1], 0);
/*  99:197 */     mul(arrayOflong102[0], arrayOflong105[0], arrayOflong106[0]);
/* 100:198 */     sub(arrayOflong102[0], arrayOflong102[0], arrayOflong101[1]);
/* 101:199 */     arrayOflong102[0]._0 -= 486671L;
/* 102:200 */     mul(arrayOflong102[1], arrayOflong105[1], arrayOflong106[0]);
/* 103:201 */     sub(arrayOflong102[1], arrayOflong102[1], arrayOflong101[1]);
/* 104:202 */     arrayOflong102[1]._0 -= 486671L;
/* 105:203 */     mul_small(arrayOflong102[0], arrayOflong102[0], 1L);
/* 106:204 */     mul_small(arrayOflong102[1], arrayOflong102[1], 1L);
/* 107:208 */     for (int n = 0; n < 32; n++)
/* 108:    */     {
/* 109:209 */       i = i >> 8 ^ paramArrayOfByte2[n] & 0xFF ^ (paramArrayOfByte2[n] & 0xFF) << 1;
/* 110:210 */       j = j >> 8 ^ paramArrayOfByte3[n] & 0xFF ^ (paramArrayOfByte3[n] & 0xFF) << 1;
/* 111:211 */       m = i ^ j ^ 0xFFFFFFFF;
/* 112:212 */       k = m & (k & 0x80) >> 7 ^ i;
/* 113:213 */       k ^= m & (k & 0x1) << 1;
/* 114:214 */       k ^= m & (k & 0x2) << 1;
/* 115:215 */       k ^= m & (k & 0x4) << 1;
/* 116:216 */       k ^= m & (k & 0x8) << 1;
/* 117:217 */       k ^= m & (k & 0x10) << 1;
/* 118:218 */       k ^= m & (k & 0x20) << 1;
/* 119:219 */       k ^= m & (k & 0x40) << 1;
/* 120:220 */       arrayOfByte[n] = ((byte)k);
/* 121:    */     }
/* 122:223 */     k = (m & (k & 0x80) << 1 ^ i) >> 8;
/* 123:    */     
/* 124:    */ 
/* 125:226 */     set(arrayOflong103[0], 1);
/* 126:227 */     cpy(arrayOflong103[1], arrayOflong101[k]);
/* 127:228 */     cpy(arrayOflong103[2], arrayOflong102[0]);
/* 128:229 */     set(arrayOflong104[0], 0);
/* 129:230 */     set(arrayOflong104[1], 1);
/* 130:231 */     set(arrayOflong104[2], 1);
/* 131:    */     
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:239 */     i = 0;
/* 139:240 */     j = 0;
/* 140:243 */     for (n = 32; n-- != 0;)
/* 141:    */     {
/* 142:244 */       i = i << 8 | paramArrayOfByte2[n] & 0xFF;
/* 143:245 */       j = j << 8 | paramArrayOfByte3[n] & 0xFF;
/* 144:246 */       k = k << 8 | arrayOfByte[n] & 0xFF;
/* 145:248 */       for (i1 = 8; i1-- != 0;)
/* 146:    */       {
/* 147:249 */         mont_prep(arrayOflong105[0], arrayOflong106[0], arrayOflong103[0], arrayOflong104[0]);
/* 148:250 */         mont_prep(arrayOflong105[1], arrayOflong106[1], arrayOflong103[1], arrayOflong104[1]);
/* 149:251 */         mont_prep(arrayOflong105[2], arrayOflong106[2], arrayOflong103[2], arrayOflong104[2]);
/* 150:    */         
/* 151:253 */         i2 = ((i ^ i >> 1) >> i1 & 0x1) + ((j ^ j >> 1) >> i1 & 0x1);
/* 152:    */         
/* 153:255 */         mont_dbl(arrayOflong103[2], arrayOflong104[2], arrayOflong105[i2], arrayOflong106[i2], arrayOflong103[0], arrayOflong104[0]);
/* 154:    */         
/* 155:257 */         i2 = k >> i1 & 0x2 ^ (k >> i1 & 0x1) << 1;
/* 156:258 */         mont_add(arrayOflong105[1], arrayOflong106[1], arrayOflong105[i2], arrayOflong106[i2], arrayOflong103[1], arrayOflong104[1], arrayOflong101[(k >> i1 & 0x1)]);
/* 157:    */         
/* 158:    */ 
/* 159:261 */         mont_add(arrayOflong105[2], arrayOflong106[2], arrayOflong105[0], arrayOflong106[0], arrayOflong103[2], arrayOflong104[2], arrayOflong102[(((i ^ j) >> i1 & 0x2) >> 1)]);
/* 160:    */       }
/* 161:    */     }
/* 162:266 */     int i2 = (i & 0x1) + (j & 0x1);
/* 163:267 */     recip(arrayOflong105[0], arrayOflong104[i2], 0);
/* 164:268 */     mul(arrayOflong105[1], arrayOflong103[i2], arrayOflong105[0]);
/* 165:    */     
/* 166:270 */     pack(arrayOflong105[1], paramArrayOfByte1);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static boolean isCanonicalSignature(byte[] paramArrayOfByte)
/* 170:    */   {
/* 171:274 */     byte[] arrayOfByte1 = Arrays.copyOfRange(paramArrayOfByte, 0, 32);
/* 172:275 */     byte[] arrayOfByte2 = new byte[32];
/* 173:276 */     divmod(arrayOfByte2, arrayOfByte1, 32, ORDER, 32);
/* 174:277 */     for (int i = 0; i < 32; i++) {
/* 175:278 */       if (paramArrayOfByte[i] != arrayOfByte1[i]) {
/* 176:279 */         return false;
/* 177:    */       }
/* 178:    */     }
/* 179:281 */     return true;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static boolean isCanonicalPublicKey(byte[] paramArrayOfByte)
/* 183:    */   {
/* 184:285 */     if (paramArrayOfByte.length != 32) {
/* 185:286 */       return false;
/* 186:    */     }
/* 187:288 */     long10 locallong10 = new long10();
/* 188:289 */     unpack(locallong10, paramArrayOfByte);
/* 189:290 */     byte[] arrayOfByte = new byte[32];
/* 190:291 */     pack(locallong10, arrayOfByte);
/* 191:292 */     for (int i = 0; i < 32; i++) {
/* 192:293 */       if (arrayOfByte[i] != paramArrayOfByte[i]) {
/* 193:294 */         return false;
/* 194:    */       }
/* 195:    */     }
/* 196:297 */     return true;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static final class long10
/* 200:    */   {
/* 201:    */     public long _0;
/* 202:    */     public long _1;
/* 203:    */     public long _2;
/* 204:    */     public long _3;
/* 205:    */     public long _4;
/* 206:    */     public long _5;
/* 207:    */     public long _6;
/* 208:    */     public long _7;
/* 209:    */     public long _8;
/* 210:    */     public long _9;
/* 211:    */     
/* 212:    */     public long10() {}
/* 213:    */     
/* 214:    */     public long10(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10)
/* 215:    */     {
/* 216:311 */       this._0 = paramLong1;this._1 = paramLong2;this._2 = paramLong3;
/* 217:312 */       this._3 = paramLong4;this._4 = paramLong5;this._5 = paramLong6;
/* 218:313 */       this._6 = paramLong7;this._7 = paramLong8;this._8 = paramLong9;
/* 219:314 */       this._9 = paramLong10;
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private static void cpy32(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/* 224:    */   {
/* 225:323 */     for (int i = 0; i < 32; i++) {
/* 226:324 */       paramArrayOfByte1[i] = paramArrayOfByte2[i];
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static int mula_small(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, byte[] paramArrayOfByte3, int paramInt2, int paramInt3)
/* 231:    */   {
/* 232:331 */     int i = 0;
/* 233:332 */     for (int j = 0; j < paramInt2; j++)
/* 234:    */     {
/* 235:333 */       i += (paramArrayOfByte2[(j + paramInt1)] & 0xFF) + paramInt3 * (paramArrayOfByte3[j] & 0xFF);
/* 236:334 */       paramArrayOfByte1[(j + paramInt1)] = ((byte)i);
/* 237:335 */       i >>= 8;
/* 238:    */     }
/* 239:337 */     return i;
/* 240:    */   }
/* 241:    */   
/* 242:    */   private static int mula32(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2)
/* 243:    */   {
/* 244:345 */     int i = 0;
/* 245:346 */     for (int j = 0; j < paramInt1; j++)
/* 246:    */     {
/* 247:348 */       int k = paramInt2 * (paramArrayOfByte3[j] & 0xFF);
/* 248:349 */       i += mula_small(paramArrayOfByte1, paramArrayOfByte1, j, paramArrayOfByte2, 31, k) + (paramArrayOfByte1[(j + 31)] & 0xFF) + k * (paramArrayOfByte2[31] & 0xFF);
/* 249:    */       
/* 250:351 */       paramArrayOfByte1[(j + 31)] = ((byte)i);
/* 251:352 */       i >>= 8;
/* 252:    */     }
/* 253:354 */     paramArrayOfByte1[(j + 31)] = ((byte)(i + (paramArrayOfByte1[(j + 31)] & 0xFF)));
/* 254:355 */     return i >> 8;
/* 255:    */   }
/* 256:    */   
/* 257:    */   private static void divmod(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, byte[] paramArrayOfByte3, int paramInt2)
/* 258:    */   {
/* 259:364 */     int i = 0;
/* 260:365 */     int j = (paramArrayOfByte3[(paramInt2 - 1)] & 0xFF) << 8;
/* 261:366 */     if (paramInt2 > 1) {
/* 262:367 */       j |= paramArrayOfByte3[(paramInt2 - 2)] & 0xFF;
/* 263:    */     }
/* 264:369 */     while (paramInt1-- >= paramInt2)
/* 265:    */     {
/* 266:370 */       int k = i << 16 | (paramArrayOfByte2[paramInt1] & 0xFF) << 8;
/* 267:371 */       if (paramInt1 > 0) {
/* 268:372 */         k |= paramArrayOfByte2[(paramInt1 - 1)] & 0xFF;
/* 269:    */       }
/* 270:374 */       k /= j;
/* 271:375 */       i += mula_small(paramArrayOfByte2, paramArrayOfByte2, paramInt1 - paramInt2 + 1, paramArrayOfByte3, paramInt2, -k);
/* 272:376 */       paramArrayOfByte1[(paramInt1 - paramInt2 + 1)] = ((byte)(k + i & 0xFF));
/* 273:377 */       mula_small(paramArrayOfByte2, paramArrayOfByte2, paramInt1 - paramInt2 + 1, paramArrayOfByte3, paramInt2, -i);
/* 274:378 */       i = paramArrayOfByte2[paramInt1] & 0xFF;
/* 275:379 */       paramArrayOfByte2[paramInt1] = 0;
/* 276:    */     }
/* 277:381 */     paramArrayOfByte2[(paramInt2 - 1)] = ((byte)i);
/* 278:    */   }
/* 279:    */   
/* 280:    */   private static int numsize(byte[] paramArrayOfByte, int paramInt)
/* 281:    */   {
/* 282:385 */     while ((paramInt-- != 0) && (paramArrayOfByte[paramInt] == 0)) {}
/* 283:387 */     return paramInt + 1;
/* 284:    */   }
/* 285:    */   
/* 286:    */   private static byte[] egcd32(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 287:    */   {
/* 288:396 */     int j = 32;
/* 289:397 */     for (int m = 0; m < 32; m++) {
/* 290:398 */       paramArrayOfByte1[m] = (paramArrayOfByte2[m] = 0);
/* 291:    */     }
/* 292:399 */     paramArrayOfByte1[0] = 1;
/* 293:400 */     int i = numsize(paramArrayOfByte3, 32);
/* 294:401 */     if (i == 0) {
/* 295:402 */       return paramArrayOfByte2;
/* 296:    */     }
/* 297:403 */     byte[] arrayOfByte = new byte[32];
/* 298:    */     for (;;)
/* 299:    */     {
/* 300:405 */       int k = j - i + 1;
/* 301:406 */       divmod(arrayOfByte, paramArrayOfByte4, j, paramArrayOfByte3, i);
/* 302:407 */       j = numsize(paramArrayOfByte4, j);
/* 303:408 */       if (j == 0) {
/* 304:409 */         return paramArrayOfByte1;
/* 305:    */       }
/* 306:410 */       mula32(paramArrayOfByte2, paramArrayOfByte1, arrayOfByte, k, -1);
/* 307:    */       
/* 308:412 */       k = i - j + 1;
/* 309:413 */       divmod(arrayOfByte, paramArrayOfByte3, i, paramArrayOfByte4, j);
/* 310:414 */       i = numsize(paramArrayOfByte3, i);
/* 311:415 */       if (i == 0) {
/* 312:416 */         return paramArrayOfByte2;
/* 313:    */       }
/* 314:417 */       mula32(paramArrayOfByte1, paramArrayOfByte2, arrayOfByte, k, -1);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   private static void unpack(long10 paramlong10, byte[] paramArrayOfByte)
/* 319:    */   {
/* 320:428 */     paramlong10._0 = (paramArrayOfByte[0] & 0xFF | (paramArrayOfByte[1] & 0xFF) << 8 | (paramArrayOfByte[2] & 0xFF) << 16 | (paramArrayOfByte[3] & 0xFF & 0x3) << 24);
/* 321:    */     
/* 322:430 */     paramlong10._1 = ((paramArrayOfByte[3] & 0xFF & 0xFFFFFFFC) >> 2 | (paramArrayOfByte[4] & 0xFF) << 6 | (paramArrayOfByte[5] & 0xFF) << 14 | (paramArrayOfByte[6] & 0xFF & 0x7) << 22);
/* 323:    */     
/* 324:432 */     paramlong10._2 = ((paramArrayOfByte[6] & 0xFF & 0xFFFFFFF8) >> 3 | (paramArrayOfByte[7] & 0xFF) << 5 | (paramArrayOfByte[8] & 0xFF) << 13 | (paramArrayOfByte[9] & 0xFF & 0x1F) << 21);
/* 325:    */     
/* 326:434 */     paramlong10._3 = ((paramArrayOfByte[9] & 0xFF & 0xFFFFFFE0) >> 5 | (paramArrayOfByte[10] & 0xFF) << 3 | (paramArrayOfByte[11] & 0xFF) << 11 | (paramArrayOfByte[12] & 0xFF & 0x3F) << 19);
/* 327:    */     
/* 328:436 */     paramlong10._4 = ((paramArrayOfByte[12] & 0xFF & 0xFFFFFFC0) >> 6 | (paramArrayOfByte[13] & 0xFF) << 2 | (paramArrayOfByte[14] & 0xFF) << 10 | (paramArrayOfByte[15] & 0xFF) << 18);
/* 329:    */     
/* 330:438 */     paramlong10._5 = (paramArrayOfByte[16] & 0xFF | (paramArrayOfByte[17] & 0xFF) << 8 | (paramArrayOfByte[18] & 0xFF) << 16 | (paramArrayOfByte[19] & 0xFF & 0x1) << 24);
/* 331:    */     
/* 332:440 */     paramlong10._6 = ((paramArrayOfByte[19] & 0xFF & 0xFFFFFFFE) >> 1 | (paramArrayOfByte[20] & 0xFF) << 7 | (paramArrayOfByte[21] & 0xFF) << 15 | (paramArrayOfByte[22] & 0xFF & 0x7) << 23);
/* 333:    */     
/* 334:442 */     paramlong10._7 = ((paramArrayOfByte[22] & 0xFF & 0xFFFFFFF8) >> 3 | (paramArrayOfByte[23] & 0xFF) << 5 | (paramArrayOfByte[24] & 0xFF) << 13 | (paramArrayOfByte[25] & 0xFF & 0xF) << 21);
/* 335:    */     
/* 336:444 */     paramlong10._8 = ((paramArrayOfByte[25] & 0xFF & 0xFFFFFFF0) >> 4 | (paramArrayOfByte[26] & 0xFF) << 4 | (paramArrayOfByte[27] & 0xFF) << 12 | (paramArrayOfByte[28] & 0xFF & 0x3F) << 20);
/* 337:    */     
/* 338:446 */     paramlong10._9 = ((paramArrayOfByte[28] & 0xFF & 0xFFFFFFC0) >> 6 | (paramArrayOfByte[29] & 0xFF) << 2 | (paramArrayOfByte[30] & 0xFF) << 10 | (paramArrayOfByte[31] & 0xFF) << 18);
/* 339:    */   }
/* 340:    */   
/* 341:    */   private static boolean is_overflow(long10 paramlong10)
/* 342:    */   {
/* 343:452 */     return ((paramlong10._0 > 67108844L) && ((paramlong10._1 & paramlong10._3 & paramlong10._5 & paramlong10._7 & paramlong10._9) == 33554431L) && ((paramlong10._2 & paramlong10._4 & paramlong10._6 & paramlong10._8) == 67108863L)) || (paramlong10._9 > 33554431L);
/* 344:    */   }
/* 345:    */   
/* 346:    */   private static void pack(long10 paramlong10, byte[] paramArrayOfByte)
/* 347:    */   {
/* 348:465 */     int i = 0;int j = 0;
/* 349:    */     
/* 350:467 */     i = (is_overflow(paramlong10) ? 1 : 0) - (paramlong10._9 < 0L ? 1 : 0);
/* 351:468 */     j = i * -33554432;
/* 352:469 */     i *= 19;
/* 353:470 */     long l = i + paramlong10._0 + (paramlong10._1 << 26);
/* 354:471 */     paramArrayOfByte[0] = ((byte)(int)l);
/* 355:472 */     paramArrayOfByte[1] = ((byte)(int)(l >> 8));
/* 356:473 */     paramArrayOfByte[2] = ((byte)(int)(l >> 16));
/* 357:474 */     paramArrayOfByte[3] = ((byte)(int)(l >> 24));
/* 358:475 */     l = (l >> 32) + (paramlong10._2 << 19);
/* 359:476 */     paramArrayOfByte[4] = ((byte)(int)l);
/* 360:477 */     paramArrayOfByte[5] = ((byte)(int)(l >> 8));
/* 361:478 */     paramArrayOfByte[6] = ((byte)(int)(l >> 16));
/* 362:479 */     paramArrayOfByte[7] = ((byte)(int)(l >> 24));
/* 363:480 */     l = (l >> 32) + (paramlong10._3 << 13);
/* 364:481 */     paramArrayOfByte[8] = ((byte)(int)l);
/* 365:482 */     paramArrayOfByte[9] = ((byte)(int)(l >> 8));
/* 366:483 */     paramArrayOfByte[10] = ((byte)(int)(l >> 16));
/* 367:484 */     paramArrayOfByte[11] = ((byte)(int)(l >> 24));
/* 368:485 */     l = (l >> 32) + (paramlong10._4 << 6);
/* 369:486 */     paramArrayOfByte[12] = ((byte)(int)l);
/* 370:487 */     paramArrayOfByte[13] = ((byte)(int)(l >> 8));
/* 371:488 */     paramArrayOfByte[14] = ((byte)(int)(l >> 16));
/* 372:489 */     paramArrayOfByte[15] = ((byte)(int)(l >> 24));
/* 373:490 */     l = (l >> 32) + paramlong10._5 + (paramlong10._6 << 25);
/* 374:491 */     paramArrayOfByte[16] = ((byte)(int)l);
/* 375:492 */     paramArrayOfByte[17] = ((byte)(int)(l >> 8));
/* 376:493 */     paramArrayOfByte[18] = ((byte)(int)(l >> 16));
/* 377:494 */     paramArrayOfByte[19] = ((byte)(int)(l >> 24));
/* 378:495 */     l = (l >> 32) + (paramlong10._7 << 19);
/* 379:496 */     paramArrayOfByte[20] = ((byte)(int)l);
/* 380:497 */     paramArrayOfByte[21] = ((byte)(int)(l >> 8));
/* 381:498 */     paramArrayOfByte[22] = ((byte)(int)(l >> 16));
/* 382:499 */     paramArrayOfByte[23] = ((byte)(int)(l >> 24));
/* 383:500 */     l = (l >> 32) + (paramlong10._8 << 12);
/* 384:501 */     paramArrayOfByte[24] = ((byte)(int)l);
/* 385:502 */     paramArrayOfByte[25] = ((byte)(int)(l >> 8));
/* 386:503 */     paramArrayOfByte[26] = ((byte)(int)(l >> 16));
/* 387:504 */     paramArrayOfByte[27] = ((byte)(int)(l >> 24));
/* 388:505 */     l = (l >> 32) + (paramlong10._9 + j << 6);
/* 389:506 */     paramArrayOfByte[28] = ((byte)(int)l);
/* 390:507 */     paramArrayOfByte[29] = ((byte)(int)(l >> 8));
/* 391:508 */     paramArrayOfByte[30] = ((byte)(int)(l >> 16));
/* 392:509 */     paramArrayOfByte[31] = ((byte)(int)(l >> 24));
/* 393:    */   }
/* 394:    */   
/* 395:    */   private static void cpy(long10 paramlong101, long10 paramlong102)
/* 396:    */   {
/* 397:514 */     paramlong101._0 = paramlong102._0;paramlong101._1 = paramlong102._1;
/* 398:515 */     paramlong101._2 = paramlong102._2;paramlong101._3 = paramlong102._3;
/* 399:516 */     paramlong101._4 = paramlong102._4;paramlong101._5 = paramlong102._5;
/* 400:517 */     paramlong101._6 = paramlong102._6;paramlong101._7 = paramlong102._7;
/* 401:518 */     paramlong101._8 = paramlong102._8;paramlong101._9 = paramlong102._9;
/* 402:    */   }
/* 403:    */   
/* 404:    */   private static void set(long10 paramlong10, int paramInt)
/* 405:    */   {
/* 406:523 */     paramlong10._0 = paramInt;paramlong10._1 = 0L;
/* 407:524 */     paramlong10._2 = 0L;paramlong10._3 = 0L;
/* 408:525 */     paramlong10._4 = 0L;paramlong10._5 = 0L;
/* 409:526 */     paramlong10._6 = 0L;paramlong10._7 = 0L;
/* 410:527 */     paramlong10._8 = 0L;paramlong10._9 = 0L;
/* 411:    */   }
/* 412:    */   
/* 413:    */   private static void add(long10 paramlong101, long10 paramlong102, long10 paramlong103)
/* 414:    */   {
/* 415:534 */     paramlong102._0 += paramlong103._0;paramlong102._1 += paramlong103._1;
/* 416:535 */     paramlong102._2 += paramlong103._2;paramlong102._3 += paramlong103._3;
/* 417:536 */     paramlong102._4 += paramlong103._4;paramlong102._5 += paramlong103._5;
/* 418:537 */     paramlong102._6 += paramlong103._6;paramlong102._7 += paramlong103._7;
/* 419:538 */     paramlong102._8 += paramlong103._8;paramlong102._9 += paramlong103._9;
/* 420:    */   }
/* 421:    */   
/* 422:    */   private static void sub(long10 paramlong101, long10 paramlong102, long10 paramlong103)
/* 423:    */   {
/* 424:541 */     paramlong102._0 -= paramlong103._0;paramlong102._1 -= paramlong103._1;
/* 425:542 */     paramlong102._2 -= paramlong103._2;paramlong102._3 -= paramlong103._3;
/* 426:543 */     paramlong102._4 -= paramlong103._4;paramlong102._5 -= paramlong103._5;
/* 427:544 */     paramlong102._6 -= paramlong103._6;paramlong102._7 -= paramlong103._7;
/* 428:545 */     paramlong102._8 -= paramlong103._8;paramlong102._9 -= paramlong103._9;
/* 429:    */   }
/* 430:    */   
/* 431:    */   private static long10 mul_small(long10 paramlong101, long10 paramlong102, long paramLong)
/* 432:    */   {
/* 433:553 */     long l = paramlong102._8 * paramLong;
/* 434:554 */     paramlong101._8 = (l & 0x3FFFFFF);
/* 435:555 */     l = (l >> 26) + paramlong102._9 * paramLong;
/* 436:556 */     paramlong101._9 = (l & 0x1FFFFFF);
/* 437:557 */     l = 19L * (l >> 25) + paramlong102._0 * paramLong;
/* 438:558 */     paramlong101._0 = (l & 0x3FFFFFF);
/* 439:559 */     l = (l >> 26) + paramlong102._1 * paramLong;
/* 440:560 */     paramlong101._1 = (l & 0x1FFFFFF);
/* 441:561 */     l = (l >> 25) + paramlong102._2 * paramLong;
/* 442:562 */     paramlong101._2 = (l & 0x3FFFFFF);
/* 443:563 */     l = (l >> 26) + paramlong102._3 * paramLong;
/* 444:564 */     paramlong101._3 = (l & 0x1FFFFFF);
/* 445:565 */     l = (l >> 25) + paramlong102._4 * paramLong;
/* 446:566 */     paramlong101._4 = (l & 0x3FFFFFF);
/* 447:567 */     l = (l >> 26) + paramlong102._5 * paramLong;
/* 448:568 */     paramlong101._5 = (l & 0x1FFFFFF);
/* 449:569 */     l = (l >> 25) + paramlong102._6 * paramLong;
/* 450:570 */     paramlong101._6 = (l & 0x3FFFFFF);
/* 451:571 */     l = (l >> 26) + paramlong102._7 * paramLong;
/* 452:572 */     paramlong101._7 = (l & 0x1FFFFFF);
/* 453:573 */     l = (l >> 25) + paramlong101._8;
/* 454:574 */     paramlong101._8 = (l & 0x3FFFFFF);
/* 455:575 */     paramlong101._9 += (l >> 26);
/* 456:576 */     return paramlong101;
/* 457:    */   }
/* 458:    */   
/* 459:    */   private static long10 mul(long10 paramlong101, long10 paramlong102, long10 paramlong103)
/* 460:    */   {
/* 461:587 */     long l1 = paramlong102._0;long l2 = paramlong102._1;long l3 = paramlong102._2;long l4 = paramlong102._3;long l5 = paramlong102._4;
/* 462:588 */     long l6 = paramlong102._5;long l7 = paramlong102._6;long l8 = paramlong102._7;long l9 = paramlong102._8;long l10 = paramlong102._9;
/* 463:    */     
/* 464:590 */     long l11 = paramlong103._0;long l12 = paramlong103._1;long l13 = paramlong103._2;long l14 = paramlong103._3;long l15 = paramlong103._4;
/* 465:591 */     long l16 = paramlong103._5;long l17 = paramlong103._6;long l18 = paramlong103._7;long l19 = paramlong103._8;long l20 = paramlong103._9;
/* 466:    */     
/* 467:593 */     long l21 = l1 * l19 + l3 * l17 + l5 * l15 + l7 * l13 + l9 * l11 + 2L * (l2 * l18 + l4 * l16 + l6 * l14 + l8 * l12) + 38L * (l10 * l20);
/* 468:    */     
/* 469:    */ 
/* 470:    */ 
/* 471:597 */     paramlong101._8 = (l21 & 0x3FFFFFF);
/* 472:598 */     l21 = (l21 >> 26) + l1 * l20 + l2 * l19 + l3 * l18 + l4 * l17 + l5 * l16 + l6 * l15 + l7 * l14 + l8 * l13 + l9 * l12 + l10 * l11;
/* 473:    */     
/* 474:    */ 
/* 475:    */ 
/* 476:602 */     paramlong101._9 = (l21 & 0x1FFFFFF);
/* 477:603 */     l21 = l1 * l11 + 19L * ((l21 >> 25) + l3 * l19 + l5 * l17 + l7 * l15 + l9 * l13) + 38L * (l2 * l20 + l4 * l18 + l6 * l16 + l8 * l14 + l10 * l12);
/* 478:    */     
/* 479:    */ 
/* 480:    */ 
/* 481:607 */     paramlong101._0 = (l21 & 0x3FFFFFF);
/* 482:608 */     l21 = (l21 >> 26) + l1 * l12 + l2 * l11 + 19L * (l3 * l20 + l4 * l19 + l5 * l18 + l6 * l17 + l7 * l16 + l8 * l15 + l9 * l14 + l10 * l13);
/* 483:    */     
/* 484:    */ 
/* 485:    */ 
/* 486:612 */     paramlong101._1 = (l21 & 0x1FFFFFF);
/* 487:613 */     l21 = (l21 >> 25) + l1 * l13 + l3 * l11 + 19L * (l5 * l19 + l7 * l17 + l9 * l15) + 2L * (l2 * l12) + 38L * (l4 * l20 + l6 * l18 + l8 * l16 + l10 * l14);
/* 488:    */     
/* 489:    */ 
/* 490:    */ 
/* 491:617 */     paramlong101._2 = (l21 & 0x3FFFFFF);
/* 492:618 */     l21 = (l21 >> 26) + l1 * l14 + l2 * l13 + l3 * l12 + l4 * l11 + 19L * (l5 * l20 + l6 * l19 + l7 * l18 + l8 * l17 + l9 * l16 + l10 * l15);
/* 493:    */     
/* 494:    */ 
/* 495:    */ 
/* 496:622 */     paramlong101._3 = (l21 & 0x1FFFFFF);
/* 497:623 */     l21 = (l21 >> 25) + l1 * l15 + l3 * l13 + l5 * l11 + 19L * (l7 * l19 + l9 * l17) + 2L * (l2 * l14 + l4 * l12) + 38L * (l6 * l20 + l8 * l18 + l10 * l16);
/* 498:    */     
/* 499:    */ 
/* 500:    */ 
/* 501:627 */     paramlong101._4 = (l21 & 0x3FFFFFF);
/* 502:628 */     l21 = (l21 >> 26) + l1 * l16 + l2 * l15 + l3 * l14 + l4 * l13 + l5 * l12 + l6 * l11 + 19L * (l7 * l20 + l8 * l19 + l9 * l18 + l10 * l17);
/* 503:    */     
/* 504:    */ 
/* 505:    */ 
/* 506:632 */     paramlong101._5 = (l21 & 0x1FFFFFF);
/* 507:633 */     l21 = (l21 >> 25) + l1 * l17 + l3 * l15 + l5 * l13 + l7 * l11 + 19L * (l9 * l19) + 2L * (l2 * l16 + l4 * l14 + l6 * l12) + 38L * (l8 * l20 + l10 * l18);
/* 508:    */     
/* 509:    */ 
/* 510:    */ 
/* 511:637 */     paramlong101._6 = (l21 & 0x3FFFFFF);
/* 512:638 */     l21 = (l21 >> 26) + l1 * l18 + l2 * l17 + l3 * l16 + l4 * l15 + l5 * l14 + l6 * l13 + l7 * l12 + l8 * l11 + 19L * (l9 * l20 + l10 * l19);
/* 513:    */     
/* 514:    */ 
/* 515:    */ 
/* 516:642 */     paramlong101._7 = (l21 & 0x1FFFFFF);
/* 517:643 */     l21 = (l21 >> 25) + paramlong101._8;
/* 518:644 */     paramlong101._8 = (l21 & 0x3FFFFFF);
/* 519:645 */     paramlong101._9 += (l21 >> 26);
/* 520:646 */     return paramlong101;
/* 521:    */   }
/* 522:    */   
/* 523:    */   private static long10 sqr(long10 paramlong101, long10 paramlong102)
/* 524:    */   {
/* 525:652 */     long l1 = paramlong102._0;long l2 = paramlong102._1;long l3 = paramlong102._2;long l4 = paramlong102._3;long l5 = paramlong102._4;
/* 526:653 */     long l6 = paramlong102._5;long l7 = paramlong102._6;long l8 = paramlong102._7;long l9 = paramlong102._8;long l10 = paramlong102._9;
/* 527:    */     
/* 528:655 */     long l11 = l5 * l5 + 2L * (l1 * l9 + l3 * l7) + 38L * (l10 * l10) + 4L * (l2 * l8 + l4 * l6);
/* 529:    */     
/* 530:657 */     paramlong101._8 = (l11 & 0x3FFFFFF);
/* 531:658 */     l11 = (l11 >> 26) + 2L * (l1 * l10 + l2 * l9 + l3 * l8 + l4 * l7 + l5 * l6);
/* 532:    */     
/* 533:660 */     paramlong101._9 = (l11 & 0x1FFFFFF);
/* 534:661 */     l11 = 19L * (l11 >> 25) + l1 * l1 + 38L * (l3 * l9 + l5 * l7 + l6 * l6) + 76L * (l2 * l10 + l4 * l8);
/* 535:    */     
/* 536:    */ 
/* 537:664 */     paramlong101._0 = (l11 & 0x3FFFFFF);
/* 538:665 */     l11 = (l11 >> 26) + 2L * (l1 * l2) + 38L * (l3 * l10 + l4 * l9 + l5 * l8 + l6 * l7);
/* 539:    */     
/* 540:667 */     paramlong101._1 = (l11 & 0x1FFFFFF);
/* 541:668 */     l11 = (l11 >> 25) + 19L * (l7 * l7) + 2L * (l1 * l3 + l2 * l2) + 38L * (l5 * l9) + 76L * (l4 * l10 + l6 * l8);
/* 542:    */     
/* 543:    */ 
/* 544:671 */     paramlong101._2 = (l11 & 0x3FFFFFF);
/* 545:672 */     l11 = (l11 >> 26) + 2L * (l1 * l4 + l2 * l3) + 38L * (l5 * l10 + l6 * l9 + l7 * l8);
/* 546:    */     
/* 547:674 */     paramlong101._3 = (l11 & 0x1FFFFFF);
/* 548:675 */     l11 = (l11 >> 25) + l3 * l3 + 2L * (l1 * l5) + 38L * (l7 * l9 + l8 * l8) + 4L * (l2 * l4) + 76L * (l6 * l10);
/* 549:    */     
/* 550:    */ 
/* 551:678 */     paramlong101._4 = (l11 & 0x3FFFFFF);
/* 552:679 */     l11 = (l11 >> 26) + 2L * (l1 * l6 + l2 * l5 + l3 * l4) + 38L * (l7 * l10 + l8 * l9);
/* 553:    */     
/* 554:681 */     paramlong101._5 = (l11 & 0x1FFFFFF);
/* 555:682 */     l11 = (l11 >> 25) + 19L * (l9 * l9) + 2L * (l1 * l7 + l3 * l5 + l4 * l4) + 4L * (l2 * l6) + 76L * (l8 * l10);
/* 556:    */     
/* 557:    */ 
/* 558:685 */     paramlong101._6 = (l11 & 0x3FFFFFF);
/* 559:686 */     l11 = (l11 >> 26) + 2L * (l1 * l8 + l2 * l7 + l3 * l6 + l4 * l5) + 38L * (l9 * l10);
/* 560:    */     
/* 561:688 */     paramlong101._7 = (l11 & 0x1FFFFFF);
/* 562:689 */     l11 = (l11 >> 25) + paramlong101._8;
/* 563:690 */     paramlong101._8 = (l11 & 0x3FFFFFF);
/* 564:691 */     paramlong101._9 += (l11 >> 26);
/* 565:692 */     return paramlong101;
/* 566:    */   }
/* 567:    */   
/* 568:    */   private static void recip(long10 paramlong101, long10 paramlong102, int paramInt)
/* 569:    */   {
/* 570:700 */     long10 locallong101 = new long10();
/* 571:701 */     long10 locallong102 = new long10();
/* 572:702 */     long10 locallong103 = new long10();
/* 573:703 */     long10 locallong104 = new long10();
/* 574:704 */     long10 locallong105 = new long10();
/* 575:    */     
/* 576:    */ 
/* 577:707 */     sqr(locallong102, paramlong102);
/* 578:708 */     sqr(locallong103, locallong102);
/* 579:709 */     sqr(locallong101, locallong103);
/* 580:710 */     mul(locallong103, locallong101, paramlong102);
/* 581:711 */     mul(locallong101, locallong103, locallong102);
/* 582:712 */     sqr(locallong102, locallong101);
/* 583:713 */     mul(locallong104, locallong102, locallong103);
/* 584:    */     
/* 585:715 */     sqr(locallong102, locallong104);
/* 586:716 */     sqr(locallong103, locallong102);
/* 587:717 */     sqr(locallong102, locallong103);
/* 588:718 */     sqr(locallong103, locallong102);
/* 589:719 */     sqr(locallong102, locallong103);
/* 590:720 */     mul(locallong103, locallong102, locallong104);
/* 591:721 */     sqr(locallong102, locallong103);
/* 592:722 */     sqr(locallong104, locallong102);
/* 593:723 */     for (int i = 1; i < 5; i++)
/* 594:    */     {
/* 595:724 */       sqr(locallong102, locallong104);
/* 596:725 */       sqr(locallong104, locallong102);
/* 597:    */     }
/* 598:727 */     mul(locallong102, locallong104, locallong103);
/* 599:728 */     sqr(locallong104, locallong102);
/* 600:729 */     sqr(locallong105, locallong104);
/* 601:730 */     for (i = 1; i < 10; i++)
/* 602:    */     {
/* 603:731 */       sqr(locallong104, locallong105);
/* 604:732 */       sqr(locallong105, locallong104);
/* 605:    */     }
/* 606:734 */     mul(locallong104, locallong105, locallong102);
/* 607:735 */     for (i = 0; i < 5; i++)
/* 608:    */     {
/* 609:736 */       sqr(locallong102, locallong104);
/* 610:737 */       sqr(locallong104, locallong102);
/* 611:    */     }
/* 612:739 */     mul(locallong102, locallong104, locallong103);
/* 613:740 */     sqr(locallong103, locallong102);
/* 614:741 */     sqr(locallong104, locallong103);
/* 615:742 */     for (i = 1; i < 25; i++)
/* 616:    */     {
/* 617:743 */       sqr(locallong103, locallong104);
/* 618:744 */       sqr(locallong104, locallong103);
/* 619:    */     }
/* 620:746 */     mul(locallong103, locallong104, locallong102);
/* 621:747 */     sqr(locallong104, locallong103);
/* 622:748 */     sqr(locallong105, locallong104);
/* 623:749 */     for (i = 1; i < 50; i++)
/* 624:    */     {
/* 625:750 */       sqr(locallong104, locallong105);
/* 626:751 */       sqr(locallong105, locallong104);
/* 627:    */     }
/* 628:753 */     mul(locallong104, locallong105, locallong103);
/* 629:754 */     for (i = 0; i < 25; i++)
/* 630:    */     {
/* 631:755 */       sqr(locallong105, locallong104);
/* 632:756 */       sqr(locallong104, locallong105);
/* 633:    */     }
/* 634:758 */     mul(locallong103, locallong104, locallong102);
/* 635:759 */     sqr(locallong102, locallong103);
/* 636:760 */     sqr(locallong103, locallong102);
/* 637:761 */     if (paramInt != 0)
/* 638:    */     {
/* 639:762 */       mul(paramlong101, paramlong102, locallong103);
/* 640:    */     }
/* 641:    */     else
/* 642:    */     {
/* 643:764 */       sqr(locallong102, locallong103);
/* 644:765 */       sqr(locallong103, locallong102);
/* 645:766 */       sqr(locallong102, locallong103);
/* 646:767 */       mul(paramlong101, locallong102, locallong101);
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   private static int is_negative(long10 paramlong10)
/* 651:    */   {
/* 652:773 */     return (int)(((is_overflow(paramlong10)) || (paramlong10._9 < 0L) ? 1 : 0) ^ paramlong10._0 & 1L);
/* 653:    */   }
/* 654:    */   
/* 655:    */   private static void sqrt(long10 paramlong101, long10 paramlong102)
/* 656:    */   {
/* 657:778 */     long10 locallong101 = new long10();long10 locallong102 = new long10();long10 locallong103 = new long10();
/* 658:779 */     add(locallong102, paramlong102, paramlong102);
/* 659:780 */     recip(locallong101, locallong102, 1);
/* 660:781 */     sqr(paramlong101, locallong101);
/* 661:782 */     mul(locallong103, locallong102, paramlong101);
/* 662:783 */     locallong103._0 -= 1L;
/* 663:784 */     mul(locallong102, locallong101, locallong103);
/* 664:785 */     mul(paramlong101, paramlong102, locallong102);
/* 665:    */   }
/* 666:    */   
/* 667:    */   private static void mont_prep(long10 paramlong101, long10 paramlong102, long10 paramlong103, long10 paramlong104)
/* 668:    */   {
/* 669:795 */     add(paramlong101, paramlong103, paramlong104);
/* 670:796 */     sub(paramlong102, paramlong103, paramlong104);
/* 671:    */   }
/* 672:    */   
/* 673:    */   private static void mont_add(long10 paramlong101, long10 paramlong102, long10 paramlong103, long10 paramlong104, long10 paramlong105, long10 paramlong106, long10 paramlong107)
/* 674:    */   {
/* 675:806 */     mul(paramlong105, paramlong102, paramlong103);
/* 676:807 */     mul(paramlong106, paramlong101, paramlong104);
/* 677:808 */     add(paramlong101, paramlong105, paramlong106);
/* 678:809 */     sub(paramlong102, paramlong105, paramlong106);
/* 679:810 */     sqr(paramlong105, paramlong101);
/* 680:811 */     sqr(paramlong101, paramlong102);
/* 681:812 */     mul(paramlong106, paramlong101, paramlong107);
/* 682:    */   }
/* 683:    */   
/* 684:    */   private static void mont_dbl(long10 paramlong101, long10 paramlong102, long10 paramlong103, long10 paramlong104, long10 paramlong105, long10 paramlong106)
/* 685:    */   {
/* 686:820 */     sqr(paramlong101, paramlong103);
/* 687:821 */     sqr(paramlong102, paramlong104);
/* 688:822 */     mul(paramlong105, paramlong101, paramlong102);
/* 689:823 */     sub(paramlong102, paramlong101, paramlong102);
/* 690:824 */     mul_small(paramlong106, paramlong102, 121665L);
/* 691:825 */     add(paramlong101, paramlong101, paramlong106);
/* 692:826 */     mul(paramlong106, paramlong101, paramlong102);
/* 693:    */   }
/* 694:    */   
/* 695:    */   private static void x_to_y2(long10 paramlong101, long10 paramlong102, long10 paramlong103)
/* 696:    */   {
/* 697:832 */     sqr(paramlong101, paramlong103);
/* 698:833 */     mul_small(paramlong102, paramlong103, 486662L);
/* 699:834 */     add(paramlong101, paramlong101, paramlong102);
/* 700:835 */     paramlong101._0 += 1L;
/* 701:836 */     mul(paramlong102, paramlong101, paramlong103);
/* 702:    */   }
/* 703:    */   
/* 704:    */   private static void core(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 705:    */   {
/* 706:842 */     long10 locallong101 = new long10();
/* 707:843 */     long10 locallong102 = new long10();
/* 708:844 */     long10 locallong103 = new long10();
/* 709:845 */     long10 locallong104 = new long10();
/* 710:846 */     long10 locallong105 = new long10();
/* 711:    */     
/* 712:848 */     long10[] arrayOflong101 = { new long10(), new long10() };
/* 713:849 */     long10[] arrayOflong102 = { new long10(), new long10() };
/* 714:853 */     if (paramArrayOfByte4 != null) {
/* 715:854 */       unpack(locallong101, paramArrayOfByte4);
/* 716:    */     } else {
/* 717:856 */       set(locallong101, 9);
/* 718:    */     }
/* 719:859 */     set(arrayOflong101[0], 1);
/* 720:860 */     set(arrayOflong102[0], 0);
/* 721:    */     
/* 722:    */ 
/* 723:863 */     cpy(arrayOflong101[1], locallong101);
/* 724:864 */     set(arrayOflong102[1], 1);
/* 725:866 */     for (int i = 32; i-- != 0;)
/* 726:    */     {
/* 727:867 */       if (i == 0) {
/* 728:868 */         i = 0;
/* 729:    */       }
/* 730:870 */       for (j = 8; j-- != 0;)
/* 731:    */       {
/* 732:872 */         int k = (paramArrayOfByte3[i] & 0xFF) >> j & 0x1;
/* 733:873 */         int m = (paramArrayOfByte3[i] & 0xFF ^ 0xFFFFFFFF) >> j & 0x1;
/* 734:874 */         localObject = arrayOflong101[m];
/* 735:875 */         long10 locallong106 = arrayOflong102[m];
/* 736:876 */         long10 locallong107 = arrayOflong101[k];
/* 737:877 */         long10 locallong108 = arrayOflong102[k];
/* 738:    */         
/* 739:    */ 
/* 740:    */ 
/* 741:881 */         mont_prep(locallong102, locallong103, (long10)localObject, locallong106);
/* 742:882 */         mont_prep(locallong104, locallong105, locallong107, locallong108);
/* 743:883 */         mont_add(locallong102, locallong103, locallong104, locallong105, (long10)localObject, locallong106, locallong101);
/* 744:884 */         mont_dbl(locallong102, locallong103, locallong104, locallong105, locallong107, locallong108);
/* 745:    */       }
/* 746:    */     }
/* 747:    */     int j;
/* 748:    */     Object localObject;
/* 749:888 */     recip(locallong102, arrayOflong102[0], 0);
/* 750:889 */     mul(locallong101, arrayOflong101[0], locallong102);
/* 751:890 */     pack(locallong101, paramArrayOfByte1);
/* 752:893 */     if (paramArrayOfByte2 != null)
/* 753:    */     {
/* 754:894 */       x_to_y2(locallong103, locallong102, locallong101);
/* 755:895 */       recip(locallong104, arrayOflong102[1], 0);
/* 756:896 */       mul(locallong103, arrayOflong101[1], locallong104);
/* 757:897 */       add(locallong103, locallong103, locallong101);
/* 758:898 */       locallong103._0 += 486671L;
/* 759:899 */       locallong101._0 -= 9L;
/* 760:900 */       sqr(locallong104, locallong101);
/* 761:901 */       mul(locallong101, locallong103, locallong104);
/* 762:902 */       sub(locallong101, locallong101, locallong102);
/* 763:903 */       locallong101._0 -= 39420360L;
/* 764:904 */       mul(locallong102, locallong101, BASE_R2Y);
/* 765:905 */       if (is_negative(locallong102) != 0) {
/* 766:906 */         cpy32(paramArrayOfByte2, paramArrayOfByte3);
/* 767:    */       } else {
/* 768:908 */         mula_small(paramArrayOfByte2, ORDER_TIMES_8, 0, paramArrayOfByte3, 32, -1);
/* 769:    */       }
/* 770:915 */       byte[] arrayOfByte1 = new byte[32];
/* 771:916 */       byte[] arrayOfByte2 = new byte[64];
/* 772:917 */       localObject = new byte[64];
/* 773:918 */       cpy32(arrayOfByte1, ORDER);
/* 774:919 */       cpy32(paramArrayOfByte2, egcd32(arrayOfByte2, (byte[])localObject, paramArrayOfByte2, arrayOfByte1));
/* 775:920 */       if ((paramArrayOfByte2[31] & 0x80) != 0) {
/* 776:921 */         mula_small(paramArrayOfByte2, paramArrayOfByte2, 0, ORDER, 32, 1);
/* 777:    */       }
/* 778:    */     }
/* 779:    */   }
/* 780:    */   
/* 781:926 */   private static final byte[] ORDER_TIMES_8 = { 104, -97, -82, -25, -46, 24, -109, -64, -78, -26, -68, 23, -11, -50, -9, -90, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Byte.MIN_VALUE };
/* 782:938 */   private static final long10 BASE_2Y = new long10(39999547L, 18689728L, 59995525L, 1648697L, 57546132L, 24010086L, 19059592L, 5425144L, 63499247L, 16420658L);
/* 783:942 */   private static final long10 BASE_R2Y = new long10(5744L, 8160848L, 4790893L, 13779497L, 35730846L, 12541209L, 49101323L, 30047407L, 40071253L, 6226132L);
/* 784:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.crypto.Curve25519
 * JD-Core Version:    0.7.1
 */